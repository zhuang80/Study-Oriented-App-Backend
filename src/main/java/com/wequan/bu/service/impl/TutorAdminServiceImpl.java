package com.wequan.bu.service.impl;

import com.wequan.bu.controller.vo.MultipartFileWrapper;
import com.wequan.bu.controller.vo.TutorApplicationVo;
import com.wequan.bu.repository.model.TutorApplication;
import com.wequan.bu.service.AbstractService;
import com.wequan.bu.service.MaterialService;
import com.wequan.bu.service.TutorAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Service
public class TutorAdminServiceImpl extends AbstractService<TutorApplication> implements TutorAdminService {

    private static final String OUTPUT_PATH =  System.getProperty("user.dir") + "\\src\\test\\resources\\";

    @Autowired
    private MaterialService materialService;

    @Override
    public void apply(TutorApplicationVo tutorApplicationVo) throws IOException {
        materialService.uploadSupportMaterial(new MultipartFileWrapper((short) 1,
                tutorApplicationVo.getUserId(),
                tutorApplicationVo.getResumes()), OUTPUT_PATH);
       // materialService.uploadSupportMaterial(new MultipartFileWrapper(2, tutorApplicationVo.getTranscripts()), OUTPUT_PATH);
       // materialService.uploadSupportMaterial(new MultipartFileWrapper(3, tutorApplicationVo.getOthers()), OUTPUT_PATH);

    }
}
