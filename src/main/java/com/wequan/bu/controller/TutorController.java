package com.wequan.bu.controller;

import com.wequan.bu.repository.model.Tutor;
import com.wequan.bu.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ChrisChen
 */
@RestController
@RequestMapping("/tutor")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @GetMapping("/all")
    public List<Tutor> getAll() {
        System.out.println(tutorService.findAll().stream().count());
        List<Tutor> allTutors = tutorService.findAll();
        return allTutors;
    }
}
