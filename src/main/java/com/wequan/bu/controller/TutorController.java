package com.wequan.bu.controller;

import com.wequan.bu.exception.NotImplementedException;
import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.service.TutorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

/**
 * @author ChrisChen
 */
@RestController
@RequestMapping("/tutor")
@Api(tags = "Tutor")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @GetMapping("/all")
    @ApiOperation(value = "Get all tutors", notes = "返回Tutor列表")
    public List<Tutor> getAll() {
        System.out.println(tutorService.findAll().stream().count());
        List<Tutor> allTutors = tutorService.findAll();
        return allTutors;
    }

    @GetMapping("/info")
    @ApiOperation(value = "Get tutor info", notes = "返回Tutor详情")
    public List<Tutor> getInfo(@RequestParam("id") String id) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @GetMapping("/appointments")
    public List<Tutor> getAppointments(@RequestParam("id") String id) throws NoHandlerFoundException {
        throw new NoHandlerFoundException("get", "example.com", null);
    }

    @GetMapping("/tutors")
    @ApiOperation(value = "Available tutors", notes = "返回Tutor列表，按评分/加入时间倒序")
    public List<Tutor> getTutors(@RequestParam(value = "subject_id", required = false) Integer subjectId) {
        return tutorService.findTutors(subjectId);
    }

    @GetMapping("/tutors/popular")
    @ApiOperation(value = "Popular tutors", notes = "返回Tutor列表，按评分/被查看次数排序")
    public List<Tutor> getPopularTutors(@RequestParam(value = "subject", required = false) String subject) {
        return null;
    }

}
