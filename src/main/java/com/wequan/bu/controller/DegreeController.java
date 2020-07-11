package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.Degree;
import com.wequan.bu.service.DegreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Zhaochao Hunag
 */
@RestController
@Api(tags = "Degree")
public class DegreeController {

    private static final Logger log = LoggerFactory.getLogger(DegreeController.class);

    @Autowired
    private DegreeService degreeService;

    @GetMapping("/degrees")
    @ApiOperation(value = "get all degrees", notes = "返回所有学位")
    public Result<List<Degree>> getAll(){
        return ResultGenerator.success(degreeService.findAll());
    }
}
