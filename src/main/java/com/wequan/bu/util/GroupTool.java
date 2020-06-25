package com.wequan.bu.util;

import com.wequan.bu.controller.vo.SubjectGroup;
import com.wequan.bu.repository.model.Subject;
import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.repository.model.extend.TutorRateInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupTool {

    public static <Tutor> List<SubjectGroup<List<Tutor>>> groupTutorBySubject(List<Tutor> list){
        if(list.size() == 0) return null;
        Map<Integer, List<Tutor>> map = new HashMap<>();
        Map<Integer, String> nameMap = new HashMap<>();
        for(Tutor e : list){
           TutorRateInfo tutor = (TutorRateInfo)e;
           for(Subject s : tutor.getSubjectList()){
               List<Tutor> value = map.getOrDefault(s.getId(), new ArrayList<Tutor>());
               if(!nameMap.containsKey(s.getId())) {
                   nameMap.put(s.getId(), s.getName());
               }
               value.add(e);
               map.put(s.getId(), value);
           }
        }

        List<SubjectGroup<List<Tutor>>> result = new ArrayList<>();
        for(Map.Entry element: map.entrySet()){
            List<Tutor> value = (List<Tutor>) element.getValue();
            SubjectGroup<List<Tutor>> tutorGroup = new SubjectGroup<List<Tutor>>();
            tutorGroup.setId((Integer) element.getKey());
            tutorGroup.setSubjectName(nameMap.get(element.getKey()));
            tutorGroup.setData(value);
            result.add(tutorGroup);
        }
        return result;
    }
}
