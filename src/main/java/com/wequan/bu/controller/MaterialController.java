package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.Material;
import com.wequan.bu.service.MaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * @author Zhaochao Huang
 */
@RestController
@Api(tags = "Material")
@EnableAsync
public class MaterialController {

    private static final Logger log = LoggerFactory.getLogger(MaterialController.class);

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MessageHandler messageHandler;

    @PostMapping("/material")
    @ApiOperation(value="Add course material", notes="上传课程资料")
    public String uploadFile(@RequestParam MultipartFile[] files, HttpSession session) throws IOException {
        Long begin, end;
        String basePath = "C:\\Users\\huang\\Desktop\\Wequan\\LocalStorage\\";
        List<File> returnedFile = materialService.uploadFiles(files, basePath);
        materialService.convertPdfToImage(returnedFile, basePath);
        System.out.println("================================> Send response back to client. ");
        return "success";
    }

    @GetMapping("/material/{id}")
    @ApiOperation(value = "material detail", notes="根据material id获取课程资料详情")
    public Material getMaterialById(@PathVariable("id") Integer id){
        if(id < 0){
            messageHandler.getFailResponseMessage("40008");
            return null;
        }
        return materialService.findById(id);
    }

    @GetMapping("/materials")
    @ApiOperation(value = "a list of materials", notes = "根据professor id, course id获取基本课程资料列表")
    public Result<List<Material>> getMaterials(@RequestParam("professorId") Integer professorId,
                                               @RequestParam("courseId") Integer courseId,
                                               @RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize) {
        List<Material> result = null;
        if(courseId <0 || professorId <0){
            messageHandler.getFailResponseMessage("40008");
            return null;
        }
        result = materialService.findByCourseIdAndProfessorId(courseId, professorId, pageNum, pageSize);
        return ResultGenerator.success(result);
    }

    @PostMapping("/material/unlock")
    @ApiOperation(value = "unlock material", notes = "根据material id解锁课程资料")
    public Result<Material> unlockMaterial(@RequestParam("materialId") Integer materialId,
                                           @RequestParam("userId") Integer userId) {
        Result<Material> result = null;
        return result;
    }

    @PostMapping("/material/report")
    @ApiOperation(value = "report material", notes = "对课程资料进行举报")
    public Result<Material> reportMaterial(@PathVariable("id") Integer id,
                                           @RequestParam("userId") Integer userId) {
        Result<Material> result = null;
        return result;
    }

}
