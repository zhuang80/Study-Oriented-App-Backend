package com.wequan.bu.service;

import com.wequan.bu.controller.vo.ProfessorVo;
import com.wequan.bu.repository.model.Professor;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
public interface ProfessorService extends Service<Professor>{

    /**
     * @param limit the number of reviews showed for each course
     * @return a list of Professor whose courseRates field is present
     */
    List<Professor> findAllWithRateByName(Integer limit, String name, Integer pageNum, Integer pageSize);

    /**
     * check whether a professor with id teach a course with c_id
     * @param id the id of professor
     * @param c_id the id of course
     * @return
     */
    Boolean checkCourseForProfessor(Integer id, Integer c_id);

    /**
     * find professors who teach a certain course
     * @param id the course id
     * @return a list of professors who teach the required course
     */
    List<Professor> findProfessorsByCourseId(Integer id);

    void save(ProfessorVo professor) throws Exception;

    void updateOverallScore(Integer id);

    List<ProfessorVo> findTopProfessors(Integer schoolId, Integer subjectId, Integer pageNum, Integer pageSize);
}
