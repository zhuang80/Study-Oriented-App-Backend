package com.wequan.bu.service.impl;

import com.wequan.bu.controller.vo.TutorApplicationSubjectTopic;
import com.wequan.bu.controller.vo.UploadFileWrapper;
import com.wequan.bu.controller.vo.TutorApplicationVo;
import com.wequan.bu.repository.dao.TutorApplicationEducationBackgroundMapper;
import com.wequan.bu.repository.dao.TutorApplicationLogMapper;
import com.wequan.bu.repository.dao.TutorApplicationMapper;
import com.wequan.bu.repository.dao.TutorApplicationSupportMaterialMapper;
import com.wequan.bu.repository.model.TutorApplication;
import com.wequan.bu.repository.model.TutorApplicationEducationBackground;
import com.wequan.bu.repository.model.extend.TutorApplicationFullInfo;
import com.wequan.bu.service.*;
import com.wequan.bu.util.TutorApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.sound.midi.SysexMessage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zhaochao Huang
 */
@Service
public class TutorAdminServiceImpl extends AbstractService<TutorApplication> implements TutorAdminService {

    private static final String OUTPUT_PATH =  System.getProperty("user.dir") + "\\src\\test\\resources\\";

    @Autowired
    private MaterialService materialService;

    @Autowired
    private TutorApplicationMapper tutorApplicationMapper;

    @Autowired
    private TutorApplicationLogService tutorApplicationLogService;

    @Autowired
    private TutorApplicationEducationBackgroundService educationBackgroundService;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TutorApplicationSubjectTopicService subjectTopicService;

    @PostConstruct
    public void postConstruct(){
        this.setMapper(tutorApplicationMapper);
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void apply(TutorApplicationVo tutorApplicationVo, List<UploadFileWrapper> uploadFileWrapperList) throws IOException {
        List<Integer> smList = new ArrayList<>();
        List<Integer> ebList = new ArrayList<>();
        List<Integer> stList = new ArrayList<>();

        for(UploadFileWrapper file: uploadFileWrapperList){
            smList.addAll(materialService.uploadSupportMaterial(file));
        }
        //support material ids list string
        String smIds = joinIds(smList);

        //education background ids list string
        ebList.addAll(insertEducationBackground(tutorApplicationVo));
        String ebIds= joinIds(ebList);

        //subject topics ids list string
        stList.addAll(insertSubjectTopics(tutorApplicationVo));
        String stIds = joinIds(stList);

        insertTutorApplication(tutorApplicationVo, smIds, ebIds, stIds);
    }

    @Override
    public List<UploadFileWrapper> bufferUploadFile(TutorApplicationVo tutorApplicationVo) throws IOException {
        List<UploadFileWrapper> uploadFiles = new ArrayList<>();
        Integer userId = tutorApplicationVo.getUserId();

        UploadFileWrapper temp = null;
        temp = transferAndWrap(tutorApplicationVo.getResumes(), (short)1, userId);
        if(temp != null) uploadFiles.add(temp);
        temp = transferAndWrap(tutorApplicationVo.getTranscripts(), (short)2, userId);
        if(temp != null) uploadFiles.add(temp);
        temp = transferAndWrap(tutorApplicationVo.getOthers(), (short)3, userId);
        if(temp != null) uploadFiles.add(temp);
        return uploadFiles;
    }

    @Override
    public TutorApplicationFullInfo findCurrentApplicationByUserId(Integer userId) {
        return tutorApplicationMapper.selectCurrentApplicationByUserId(userId);
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TutorApplicationVo tutorApplicationVo, List<UploadFileWrapper> uploadFileWrapperList) throws IOException {
        TutorApplication oldRecord = tutorApplicationMapper.selectByPrimaryKey(tutorApplicationVo.getId());

        //find deleted ids
        String deletedSupportMaterialIds = findDeletedIds(oldRecord.getSupportMaterialIds(),
                tutorApplicationVo.getSupportMaterialIds());
        String deletedEducationBackgroundIds = findDeletedIds(oldRecord.getEducationBackgroundIds(),
                tutorApplicationVo.getEducationBackgroundIds());
        String deletedSubjectTopicIds = findDeletedIds(oldRecord.getSubjectTopicsIds(),
                tutorApplicationVo.getSupportMaterialIds());

        List<Integer> smList = new ArrayList<>();
        List<Integer> ebList = new ArrayList<>();
        List<Integer> stList = new ArrayList<>();

        for(UploadFileWrapper file: uploadFileWrapperList){
            smList.addAll(materialService.uploadSupportMaterial(file));
        }
        //added new support material ids list string
        String smIds = joinIds(smList);

        //added new education background ids list string
        ebList.addAll(insertEducationBackground(tutorApplicationVo));
        String ebIds= joinIds(ebList);

        //added new subject topics ids list string
        stList.addAll(insertSubjectTopics(tutorApplicationVo));
        String stIds = joinIds(stList);

        System.out.println("===================================> " + stList);
        System.out.println("===================================> " + tutorApplicationVo.getSubjectTopicsIds());

        //delete support material (S3, database)
        materialService.deleteSupportMaterialsByIds(deletedSupportMaterialIds);

        //delete education background (database)
        educationBackgroundService.deleteByIds(deletedEducationBackgroundIds);

        //update tutor application table
        updateTutorApplication(tutorApplicationVo, smIds, ebIds, stIds);
    }

    @Override
    public TutorApplication findCurrentStatusByUserId(Integer userId) {
        return tutorApplicationMapper.selectCurrentStatusByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(Integer id) {
        TutorApplication tutorApplication = tutorApplicationMapper.selectByPrimaryKey(id);
        tutorApplication.setStatus(TutorApplicationStatus.APPROVE.getValue());
        tutorApplication.setUpdateTime(LocalDateTime.now());
        tutorApplicationMapper.updateByPrimaryKeySelective(tutorApplication);
        tutorApplicationLogService.addTutorApplicationLog(tutorApplication, TutorApplicationStatus.APPROVE, null);
        tutorService.approveTutorApplication(tutorApplication);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disapprove(Integer id, String comment){
        TutorApplication tutorApplication = tutorApplicationMapper.selectByPrimaryKey(id);
        tutorApplication.setId(id);
        tutorApplication.setStatus(TutorApplicationStatus.REJECT.getValue());
        tutorApplication.setUpdateTime(LocalDateTime.now());
        tutorApplicationMapper.updateByPrimaryKeySelective(tutorApplication);
        tutorApplicationLogService.addTutorApplicationLog(tutorApplication, TutorApplicationStatus.REJECT, comment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void requireAmend(Integer id, String comment) {
        TutorApplication tutorApplication = tutorApplicationMapper.selectByPrimaryKey(id);
        tutorApplication.setId(id);
        tutorApplication.setStatus(TutorApplicationStatus.REQUIRE_AMEND.getValue());
        tutorApplication.setUpdateTime(LocalDateTime.now());
        tutorApplicationMapper.updateByPrimaryKeySelective(tutorApplication);
        tutorApplicationLogService.addTutorApplicationLog(tutorApplication, TutorApplicationStatus.REQUIRE_AMEND, comment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer applicationId){
        TutorApplication tutorApplication = tutorApplicationMapper.selectByPrimaryKey(applicationId);
        //delete education background
        educationBackgroundService.deleteByIds(tutorApplication.getEducationBackgroundIds());
        //delete support material (S3, database)
        materialService.deleteSupportMaterialsByIds(tutorApplication.getSupportMaterialIds());
        //update application log
        tutorApplicationLogService.addTutorApplicationLog(tutorApplication, TutorApplicationStatus.DELETED, null);
        //update application status
        tutorApplication.setStatus(TutorApplicationStatus.DELETED.getValue());
        tutorApplicationMapper.updateByPrimaryKeySelective(tutorApplication);
    }


    private UploadFileWrapper transferAndWrap(MultipartFile[] multipartFiles, short type, Integer userId) throws IOException {
        List<File> files = materialService.uploadFiles(multipartFiles, OUTPUT_PATH);
        return (files == null ? null : new UploadFileWrapper(type, userId, files));
    }

    /**
     * join ids using commas as delimiter
     * @param ids the id list
     * @return a id list string, ids separated by commas
     */
    private <T> String joinIds(List<T> ids){
         return ids.stream()
                    .map(v->String.valueOf(v))
                    .collect(Collectors.joining(","));
    }

    private String joinIds(String ids, String newIds){
        if(ids == null || ids.isEmpty()){
            if(newIds == null || newIds.isEmpty()){
                return "";
            }else{
                return newIds;
            }
        }else{
            if(newIds == null || newIds.isEmpty()){
                return ids;
            }else{
                return ids + "," + newIds;
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private List<Integer> insertEducationBackground(List<TutorApplicationEducationBackground> educationBackgroundList){
        educationBackgroundService.save(educationBackgroundList);
        return educationBackgroundList
                .stream()
                .map(v -> v.getId())
                .collect(Collectors.toList());
    }
    private List<Integer> insertEducationBackground(TutorApplicationVo tutorApplicationVo){
        return insertEducationBackground(tutorApplicationVo.getEducationBackgrounds());
    }

    @Transactional(rollbackFor = Exception.class)
    private void insertTutorApplication(TutorApplicationVo tutorApplicationVo, String smIds, String ebIds, String stIds){
        TutorApplication tutorApplication = new TutorApplication(tutorApplicationVo);
        tutorApplication.setCreateTime(LocalDateTime.now());

        String supportMaterialIds = joinIds(tutorApplicationVo.getSupportMaterialIds(), smIds);

        tutorApplication.setSupportMaterialIds(supportMaterialIds);
        tutorApplication.setEducationBackgroundIds(ebIds);
        tutorApplication.setSubjectTopicsIds(stIds);
        tutorApplication.setStatus(TutorApplicationStatus.PENDING.getValue());
        tutorApplicationMapper.insertSelective(tutorApplication);
        tutorApplicationLogService.addTutorApplicationLog(tutorApplication, TutorApplicationStatus.PENDING, null);
    }

    @Transactional(rollbackFor = Exception.class)
    private void updateTutorApplication(TutorApplicationVo tutorApplicationVo, String smIds, String ebIds, String stIds){
        TutorApplication tutorApplication = new TutorApplication(tutorApplicationVo);
        tutorApplication.setCreateTime(tutorApplicationVo.getCreateTime());
        tutorApplication.setUpdateTime(LocalDateTime.now());

        String supportMaterialIds = joinIds(tutorApplicationVo.getSupportMaterialIds(), smIds);
        String educationBackgroundIds = joinIds(tutorApplicationVo.getEducationBackgroundIds(), ebIds);
        String subjectTopicIds = joinIds(tutorApplicationVo.getSubjectTopicsIds(), stIds);

        tutorApplication.setSupportMaterialIds(supportMaterialIds);
        tutorApplication.setEducationBackgroundIds(educationBackgroundIds);
        tutorApplication.setSubjectTopicsIds(subjectTopicIds);

        tutorApplication.setStatus(TutorApplicationStatus.PENDING.getValue());
        tutorApplicationMapper.updateByPrimaryKeySelective(tutorApplication);
    }

    /**
     * to find deleted ids
     * @param ids old ids
     * @param newIds new ids
     * @return deleted ids string
     */
    private String findDeletedIds(String ids, String newIds){
        String[] idsArray = ids.split(",");
        String[] newIdsArray = newIds.split(",");
        List<String> result = new ArrayList<>();
        Set<String> set = new HashSet<>(Arrays.asList(newIdsArray));

        for(String id : idsArray){
            if(!set.contains(id)){
                result.add(id);
            }
        }

        return joinIds(result);
    }

    private List<Integer> insertSubjectTopics(List<TutorApplicationSubjectTopic> subjectTopics){
        subjectTopicService.save(subjectTopics);
        return subjectTopics
                .stream()
                .map(v -> v.getId())
                .collect(Collectors.toList());
    }

    private List<Integer> insertSubjectTopics(TutorApplicationVo tutorApplicationVo){
        return insertSubjectTopics(tutorApplicationVo.getSubjectTopics());
    }
}
