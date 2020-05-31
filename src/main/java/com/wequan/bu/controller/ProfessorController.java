package com.wequan.bu.controller;

import com.wequan.bu.repository.model.Professor;
import com.wequan.bu.service.ProfessorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Zhaochao Huang
 */
@RestController
@Api(value = "Operations for Professor", tags="Professor Rest API")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/professor")
    @ApiOperation(value="findAll", notes="return a list of professors")
    public List<Professor> findAll(){
        return professorService.findAll();
    }

    @GetMapping("/professor/{id}")
    @ApiOperation(value="findById", notes="find a specific professor by its id")
    public Professor findById(@PathVariable("id") Integer id) { return professorService.findById(id); }

    @GetMapping("/search/professor")
    @ApiOperation(value="", notes="")
    public List<Professor> findAllWithRateByName(@RequestParam("name") String name){
        return professorService.findAllWithRateByName(3, name);
    }
}
