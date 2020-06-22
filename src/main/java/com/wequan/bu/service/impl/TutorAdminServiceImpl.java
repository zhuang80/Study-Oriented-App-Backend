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

    @Async
    @Override
    public void apply(TutorApplicationVo tutorApplicationVo, List<UploadFileWrapper> uploadFileWrapperList) throws IOException {
        List<Integer> idList = new ArrayList<>();
        List<Integer> ebList = new ArrayList<>();

        for(UploadFileWrapper file: uploadFileWrapperList){
            idList.addAll(materialService.uploadSupportMaterial(file));
        }

        String smIds = idList
                    .stream()
                    .map(v->String.valueOf(v))
                    .collect(Collectors.joining(","));
        String ebIds;

        List<TutorApplicationEducationBackground> educationBackgrounds = tutorApplicationVo.getEducationBackgrounds();

        educationBackgroundMapper.insertList(tutorApplicationVo.getEducationBackgrounds());
        ebList.addAll(tutorApplicationVo.getEducationBackgrounds()
                .stream()
                .map(v -> v.getId())
                .collect(Collectors.toList()));

        ebIds = ebList
                .stream()
                .map(v->String.valueOf(v))
                .collect(Collectors.joining(","));

        for(TutorApplicationEducationBackground t: tutorApplicationVo.getEducationBackgrounds()){
            System.out.println(t.getId() + "    gpa "+t.getGpa());
        }

        TutorApplication tutorApplication = new TutorApplication(tutorApplicationVo);
        tutorApplication.setCreateTime(LocalDateTime.now());
        tutorApplication.setSupportMaterialIds(smIds);
        tutorApplication.setEducationBackgroundIds(ebIds);
        tutorApplication.setStatus((short) 0);
        tutorApplicationMapper.insertSelective(tutorApplication);
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

    private UploadFileWrapper transferAndWrap(MultipartFile[] multipartFiles, short type, Integer userId) throws IOException {
        List<File> files = materialService.uploadFiles(multipartFiles, OUTPUT_PATH);
        return (files == null ? null : new UploadFileWrapper(type, userId, files));
    }


}
