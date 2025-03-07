package com.wequan.bu.controller;

import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultCode;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.repository.model.Material;
import com.wequan.bu.service.MaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * @author Zhaochao Huang
 */
@RestController
@Api(tags = "Material")
@ApiIgnore
public class MaterialController {

    private static final Logger log = LoggerFactory.getLogger(MaterialController.class);

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MessageHandler messageHandler;

//    @PostMapping("/material")
//    @ApiOperation(value="Add course material", notes="上传课程资料")
//    public String uploadFile(@RequestParam MultipartFile[] files, ) throws IOException {
//        Long begin, end;
//        String basePath = "C:\\Users\\huang\\Desktop\\Wequan\\LocalStorage\\";
//        List<File> returnedFile = materialService.uploadFiles(files, basePath);
//        materialService.convertPdfToImage(returnedFile, basePath);
//        System.out.println("================================> Send response back to client. ");
//        return "success";
//    }

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
        Result result;
        List<Material> materialList = null;
        if(courseId <0 || professorId <0){
            String message = messageHandler.getFailResponseMessage("40008");
            result = ResultGenerator.fail(ResultCode.FAIL.code(), message);
            return null;
        }
        materialList = materialService.findByCourseIdAndProfessorId(courseId, professorId, pageNum, pageSize);
        return ResultGenerator.success(materialList);
    }

    @PostMapping("/material/unlock")
    @ApiOperation(value = "unlock material", notes = "根据material id解锁课程资料")
    public Result<Material> unlockMaterial(@RequestParam("materialId") Integer materialId,
                                           @RequestParam("userId") Integer userId) {
        Result<Material> result = null;
        return result;
    }

    @GetMapping("/materials/unlock/list")
    @ApiOperation(value = "a list of user's unlock materials", notes = "返回用户解锁成功的资料列表")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of cards describing a course material (title, material type, upload date, # of likes, # of views)")
    )
    public Result<List<Material>> unlockMaterial(@RequestParam("userId") Integer userId) {
        List<Material> materials = null;
        return ResultGenerator.success(materials);
    }

    @GetMapping("/materials/upload/list")
    @ApiOperation(value = "a list of user's upload materials", notes = "返回用户上传资料的列表")
    @ApiResponses(
            @ApiResponse(code = 200, message = "a list of cards describing a course material (title, material type, upload date, # of likes, # of views)")
    )
    public Result<List<Material>> uploadMaterial(@RequestParam("userId") Integer userId) {
        List<Material> materials = null;
        return ResultGenerator.success(materials);
    }

    @PostMapping("/material/report")
    @ApiOperation(value = "report material", notes = "对课程资料进行举报")
    public Result<Material> reportMaterial(@RequestParam("materialId") Integer materialId,
                                           @RequestParam("userId") Integer userId,
                                           @RequestParam("reason") String reason) {
        Result<Material> result = null;
        return result;
    }

}
