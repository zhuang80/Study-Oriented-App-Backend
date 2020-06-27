package com.wequan.bu.repository.model.extend;

import com.wequan.bu.controller.vo.TutorApplicationVo;
import com.wequan.bu.repository.model.*;
import lombok.Data;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@Data
public class TutorApplicationFullInfo extends TutorApplication {
    private List<TutorApplicationSupportMaterial> supportMaterialList;
    private List<TutorApplicationEducationBackground> educationBackgroundList;
    private List<Topic> topicList;

    public TutorApplicationFullInfo(){};
    public TutorApplicationFullInfo(TutorApplicationVo tutorApplicationVo){
        super(tutorApplicationVo);
    }
}
