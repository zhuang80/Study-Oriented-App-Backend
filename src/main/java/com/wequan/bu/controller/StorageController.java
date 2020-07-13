package com.wequan.bu.controller;

import com.wequan.bu.config.WeQuanConstants;
import com.wequan.bu.config.handler.MessageHandler;
import com.wequan.bu.controller.vo.StorageKeyCategory;
import com.wequan.bu.controller.vo.result.Result;
import com.wequan.bu.controller.vo.result.ResultGenerator;
import com.wequan.bu.security.CurrentUser;
import com.wequan.bu.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author ChrisChen
 */
@RestController
@RequestMapping("/storage")
@Api(tags = "Storage")
public class StorageController {

    private static final Logger log = LoggerFactory.getLogger(StorageController.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    private StorageService storageService;
    @Autowired
    private MessageHandler messageHandler;

    @GetMapping("/presigned_put_url")
    @ApiOperation(value = "acquire s3 presigned put url", notes = "返回S3 PUT类型预签名URL")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category", value = "1 -> profile; 2 -> tutor application; 3 -> thread; " +
                    "4 -> thread stream; 5 -> discussion group; 6 -> course material; 7 -> public class; 8 -> activity;")
    })
    public Result<Map<String, String>> acquirePresignedPutUrl(@RequestParam("fileName") String fileName,
                                                              @RequestParam("category") Integer category,
                                                              @CurrentUser Integer userId) {
        String storageKey = StorageKeyCategory.getStorageKey(category);
        if (Objects.isNull(storageKey) || !StringUtils.hasText(fileName)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        // only for test before token ready
        if (Objects.isNull(userId)) {
            // return ResultGenerator.fail(messageHandler.getMessage("40099"));
            userId = 5;
        }
        String key = System.currentTimeMillis() + "-" +fileName;
        String s3Key = WeQuanConstants.S3_TOP_PATH.concat(userId + "/").concat(storageKey + "/")
                .concat(DATE_TIME_FORMATTER.format(LocalDate.now()) + "/").concat(key);
        URL presignedUrl = storageService.getPresignedURL(s3Key, HttpMethod.PUT);
        if (Objects.isNull(presignedUrl)) {
            return ResultGenerator.fail(messageHandler.getMessage("40096"));
        }
        Map<String, String> result = new HashMap<>(2);
        result.put("url", presignedUrl.toString());
        result.put("s3Key", s3Key);
        return ResultGenerator.success(result);
    }

    @GetMapping("/presigned_get_url")
    @ApiOperation(value = "acquire s3 presigned get url", notes = "返回S3 GET类型预签名URL")
    public Result<String> acquirePresignedGetUrl(@RequestParam("s3Key") String s3Key) {
        if (!StringUtils.hasText(s3Key)) {
            return ResultGenerator.fail(messageHandler.getMessage("40098"));
        }
        URL presignedGetUrl = storageService.getPresignedURL(s3Key, HttpMethod.GET);
        if (Objects.isNull(presignedGetUrl)) {
            return ResultGenerator.fail(messageHandler.getMessage("40096"));
        }
        return ResultGenerator.success(presignedGetUrl.toString());
    }
}
