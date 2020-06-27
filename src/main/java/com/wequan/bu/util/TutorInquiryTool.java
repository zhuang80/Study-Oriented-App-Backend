package com.wequan.bu.util;

import com.wequan.bu.controller.vo.TutorInquiryGroup;
import com.wequan.bu.repository.model.Subject;
import com.wequan.bu.repository.model.TutorInquiry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zhaochao Huang
 */
public class TutorInquiryTool {

    public static List<TutorInquiryGroup> groupTutorInquiryBySubject(List<TutorInquiry> tutorInquiryList){
        if(tutorInquiryList.size() == 0) return null;
        Map<Integer, List<TutorInquiry>> map = new HashMap<>();
        for(TutorInquiry t : tutorInquiryList){
            Integer subjectId = t.getSubject().getId();
            List<TutorInquiry> value = map.getOrDefault(subjectId, new ArrayList<TutorInquiry>());
            value.add(t);
            map.put(subjectId, value);
        }

        List<TutorInquiryGroup> result = new ArrayList<>();
        for(Map.Entry element: map.entrySet()){
            List<TutorInquiry> value = (List<TutorInquiry>) element.getValue();
            Subject subject = value.get(0).getSubject();
            TutorInquiryGroup tutorInquiryGroup = new TutorInquiryGroup();
            tutorInquiryGroup.setId(subject.getId());
            tutorInquiryGroup.setSubjectName(subject.getName());
            tutorInquiryGroup.setTutorInquiryList(value);
            result.add(tutorInquiryGroup);
        }
        return result;
    }
}
