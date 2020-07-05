package com.wequan.bu.service.impl;

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
import org.springframework.web.multipart.MultipartFile;

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

    @Async
    @Override
    public void apply(TutorApplicationVo tutorApplicationVo, List<UploadFileWrapper> uploadFileWrapperList) throws IOException {
        List<Integer> smList = new ArrayList<>();
        List<Integer> ebList = new ArrayList<>();

        for(UploadFileWrapper file: uploadFileWrapperList){
            smList.addAll(materialService.uploadSupportMaterial(file));
        }
        //support material ids list string
        String smIds = joinIds(smList);

        //education background ids list string
        ebList.addAll(insertEducationBackground(tutorApplicationVo));
        String ebIds= joinIds(ebList);

        for(TutorApplicationEducationBackground t: tutorApplicationVo.getEducationBackgrounds()){
            System.out.println(t.getId() + "    gpa "+t.getGpa());
        }
        insertTutorApplication(tutorApplicationVo, smIds, ebIds);
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
    public void update(TutorApplicationVo tutorApplicationVo, List<UploadFileWrapper> uploadFileWrapperList) throws IOException {
        TutorApplication oldRecord = tutorApplicationMapper.selectByPrimaryKey(tutorApplicationVo.getId());

        //find deleted ids
        String deletedSupportMaterialIds = findDeletedIds(oldRecord.getSupportMaterialIds(),
                tutorApplicationVo.getSupportMaterialIds());
        String deletedEducationBackgroundIds = findDeletedIds(oldRecord.getEducationBackgroundIds(),
                tutorApplicationVo.getEducationBackgroundIds());

        System.out.println("================================ deleted support material ids "+ deletedSupportMaterialIds);
        System.out.println("================================ deleted education background ids "+ deletedEducationBackgroundIds);
        List<Integer> smList = new ArrayList<>();
        List<Integer> ebList = new ArrayList<>();

        for(UploadFileWrapper file: uploadFileWrapperList){
            smList.addAll(materialService.uploadSupportMaterial(file));
        }
        //added new support material ids list string
        String smIds = joinIds(smList);

        //added new education background ids list string
        ebList.addAll(insertEducationBackground(tutorApplicationVo));
        String ebIds= joinIds(ebList);

        //delete support material (S3, database)
        materialService.deleteSupportMaterialsByIds(deletedSupportMaterialIds);

        //delete education background (database)
        educationBackgroundService.deleteByIds(deletedEducationBackgroundIds);

        //update tutor application table
        updateTutorApplication(tutorApplicationVo, smIds, ebIds);
    }

    @Override
    public TutorApplication findCurrentStatusByUserId(Integer userId) {
        return tutorApplicationMapper.selectCurrentStatusByUserId(userId);
    }

    @Override
    public void approve(Integer id) {
        TutorApplication tutorApplication = tutorApplicationMapper.selectByPrimaryKey(id);
        tutorApplication.setStatus(TutorApplicationStatus.APPROVE.getValue());
        tutorApplication.setUpdateTime(LocalDateTime.now());
        tutorApplicationMapper.updateByPrimaryKeySelective(tutorApplication);
        tutorApplicationLogService.addTutorApplicationLog(tutorApplication, TutorApplicationStatus.APPROVE, null);
        tutorService.approveTutorApplication(tutorApplication);
    }

    @Override
    public void disapprove(Integer id){
        TutorApplication tutorApplication = new TutorApplication();
        tutorApplication.setId(id);
        tutorApplication.setStatus(TutorApplicationStatus.REJECT.getValue());
        tutorApplication.setUpdateTime(LocalDateTime.now());
        tutorApplicationMapper.updateByPrimaryKeySelective(tutorApplication);
        tutorApplicationLogService.addTutorApplicationLog(tutorApplication, TutorApplicationStatus.REJECT, null);
    }

    @Override
    public void requireAmend(Integer id, String comment) {
        TutorApplication tutorApplication = new TutorApplication();
        tutorApplication.setId(id);
        tutorApplication.setStatus(TutorApplicationStatus.REQUIRE_AMEND.getValue());
        tutorApplication.setUpdateTime(LocalDateTime.now());
        tutorApplicationMapper.updateByPrimaryKeySelective(tutorApplication);
        tutorApplicationLogService.addTutorApplicationLog(tutorApplication, TutorApplicationStatus.REQUIRE_AMEND, comment);
    }

    @Override
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
        if(ids == null){
            if(newIds == null){
                return "";
            }else{
                return newIds;
            }
        }else{
            if(newIds == null){
                return ids;
            }else{
                return ids + "," + newIds;
            }
        }
    }

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

    private void insertTutorApplication(TutorApplicationVo tutorApplicationVo, String smIds, String ebIds){
        TutorApplication tutorApplication = new TutorApplication(tutorApplicationVo);
        tutorApplication.setCreateTime(LocalDateTime.now());
        tutorApplication.setSupportMaterialIds(smIds);
        tutorApplication.setEducationBackgroundIds(ebIds);
        tutorApplication.setStatus(TutorApplicationStatus.PENDING.getValue());
        tutorApplicationMapper.insertSelective(tutorApplication);
        tutorApplicationLogService.addTutorApplicationLog(tutorApplication, TutorApplicationStatus.PENDING, null);
    }

    private void updateTutorApplication(TutorApplicationVo tutorApplicationVo, String smIds, String ebIds){
        TutorApplication tutorApplication = new TutorApplication(tutorApplicationVo);
        tutorApplication.setCreateTime(tutorApplicationVo.getCreateTime());
        tutorApplication.setUpdateTime(LocalDateTime.now());

        String supportMaterialIds = joinIds(tutorApplicationVo.getSupportMaterialIds(), smIds);
        String educationBackgroundIds = joinIds(tutorApplicationVo.getEducationBackgroundIds(), ebIds);

        tutorApplication.setSupportMaterialIds(supportMaterialIds);
        tutorApplication.setEducationBackgroundIds(educationBackgroundIds);

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
}
