package com.wequan.bu.service.impl;

import com.wequan.bu.controller.vo.UploadFileWrapper;
import com.wequan.bu.controller.vo.TutorApplicationVo;
import com.wequan.bu.repository.dao.TutorApplicationEducationBackgroundMapper;
import com.wequan.bu.repository.dao.TutorApplicationMapper;
import com.wequan.bu.repository.model.TutorApplication;
import com.wequan.bu.repository.model.TutorApplicationEducationBackground;
import com.wequan.bu.repository.model.extend.TutorApplicationFullInfo;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.MaterialService;
import com.wequan.bu.service.TutorAdminService;
import com.wequan.bu.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private TutorApplicationEducationBackgroundMapper educationBackgroundMapper;

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
    public List<TutorApplicationFullInfo> findByUserId(Integer userId) {
        return tutorApplicationMapper.selectByUserId(userId);
    }

    @Async
    @Override
    public void update(TutorApplicationVo tutorApplicationVo, List<UploadFileWrapper> uploadFileWrapperList) throws IOException {
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

        materialService.deleteById(tutorApplicationVo.getDeletedSupportMaterialId());
        educationBackgroundMapper.deleteByPrimaryKey(tutorApplicationVo.getDeletedEducationBackgroundId());

        updateTutorApplication(tutorApplicationVo, smIds, ebIds);

    }

    @Override
    public List<TutorApplication> findStatusByUserId(Integer userId) {
        return tutorApplicationMapper.selectStatusByUserId(userId);
    }

    @Override
    public void approve(Integer id) {
        TutorApplication tutorApplication = tutorApplicationMapper.selectByPrimaryKey(id);
        tutorApplication.setStatus((short) 1);
        tutorApplication.setUpdateTime(LocalDateTime.now());
        tutorApplicationMapper.updateByPrimaryKeySelective(tutorApplication);
        tutorService.approveTutorApplication(tutorApplication);
    }

    @Override
    public void disapprove(Integer id){
        TutorApplication tutorApplication = new TutorApplication();
        tutorApplication.setId(id);
        tutorApplication.setStatus((short) -1);
        tutorApplication.setUpdateTime(LocalDateTime.now());
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
    private String joinIds(List<Integer> ids){
         return ids.stream()
                    .map(v->String.valueOf(v))
                    .collect(Collectors.joining(","));
    }

    private List<Integer> insertEducationBackground(List<TutorApplicationEducationBackground> educationBackgroundList){
        educationBackgroundMapper.insertList(educationBackgroundList);
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
        tutorApplication.setStatus((short) 0);
        tutorApplicationMapper.insertSelective(tutorApplication);
    }

    private void updateTutorApplication(TutorApplicationVo tutorApplicationVo, String smIds, String ebIds){
        TutorApplication tutorApplication = new TutorApplication(tutorApplicationVo);
        tutorApplication.setCreateTime(tutorApplicationVo.getCreateTime());
        tutorApplication.setUpdateTime(LocalDateTime.now());
        tutorApplication.setSupportMaterialIds(tutorApplicationVo.getSupportMaterialIds() + "," + smIds);
        tutorApplication.setEducationBackgroundIds(tutorApplicationVo.getEducationBackgroundIds()+ "," + ebIds);
        tutorApplication.setStatus((short) 0);
        tutorApplicationMapper.updateByPrimaryKeySelective(tutorApplication);
    }
}
