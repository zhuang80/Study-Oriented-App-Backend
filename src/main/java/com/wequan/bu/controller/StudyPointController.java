package com.wequan.bu.controller;

import com.wequan.bu.controller.vo.StudyPoint;
import com.wequan.bu.controller.vo.StudyPointHistory;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/study_point")
@Api(tags = "Study Point")
public class StudyPointController {

    private static final Logger log = LoggerFactory.getLogger(StudyPointController.class);

    @GetMapping("/history")
    @ApiOperation(value = "a list of study point change history", notes = "返回用户学习积分变动历史记录")
    public Result<List<StudyPointHistory>> getStudyPointHistory(@RequestParam("userId") Integer userId,
                                                                @RequestParam("pageNum") Integer pageNum,
                                                                @RequestParam("pageSize") Integer pageSize) {
        List<StudyPointHistory> result = null;
        return ResultGenerator.success(result);
    }

    @GetMapping("/current")
    @ApiOperation(value = "user's current study point", notes = "返回用户当前学习积分")
    public Result<StudyPoint> getStudyPoint(@RequestParam("userId") Integer userId) {
        StudyPoint result = null;
        return ResultGenerator.success(result);
    }

    @PostMapping
    @ApiOperation(value = "user purchase study point", notes = "返回用户购买学习积分成功与否")
    public Result purchaseStudyPoint(@RequestParam("userId") Integer userId,
                                     @RequestParam("amount") Integer amount){
        return ResultGenerator.success();
    }

}
