package com.wrial.controller;

import com.wrial.pojo.Bgm;
import com.wrial.service.VideoService;
import com.wrial.utils.MyJSONResult;
import com.wrial.utils.PagedResult;
import enums.VideoStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/*
 * @Author  Wrial
 * @Date Created in 16:07 2019/8/16
 * @Description
 */
@Controller
@RequestMapping("/video")
public class VideoController {


    /*
    添加BGM
     */
    @Autowired
    private VideoService videoService;
    @PostMapping("/addBgm")
    @ResponseBody
    public MyJSONResult addBgm(Bgm bgm) {
        videoService.addBgm(bgm);
        return MyJSONResult.ok();
    }

    /*
    去跳转到添加BGM页面
     */
    @GetMapping("/showAddBgm")
    public String login() {
        return "video/addBgm";
    }

    /*
    BGM上传
     */
    @PostMapping("/bgmUpload")
    @ResponseBody
    public MyJSONResult uploadBgm(@RequestParam("file") MultipartFile[] files) throws Exception {

        //保存在磁盘的根目录路径
        String fileSpace = "D:" + File.separator + "dev" + File.separator + "mvc-bgm";
        //保存在数据库的相对地址

        String dBPath = File.separator + "bgm";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (files != null && files.length > 0) {
                String filename = files[0].getOriginalFilename();
                if (!StringUtils.isBlank(filename)) {
                    //完整路径
                    String finalPath = fileSpace + dBPath + File.separator + filename;
                    dBPath += (File.separator + filename);
                    File file = new File(finalPath);
                    if (file.getParentFile() != null || !file.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        file.getParentFile().mkdirs();
                    }
                    inputStream = files[0].getInputStream();
                    fileOutputStream = new FileOutputStream(file);
                    StreamUtils.copy(inputStream, fileOutputStream);
                } else {
                    return MyJSONResult.errorMsg("上传出错...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return MyJSONResult.errorMsg("上传出错...");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        return MyJSONResult.ok(dBPath);
    }

    /*
    跳转到BgmList
     */
    @GetMapping("/showBgmList")
    public String showBgmList() {
        return "video/bgmList";
    }
    /*
    BgmList查询
     */
    @PostMapping("/queryBgmList")
    @ResponseBody
    public PagedResult queryBgmList(Integer page) {
        return videoService.queryBgmList(page, 10);
    }

    @PostMapping("/delBgm")
    @ResponseBody
    public MyJSONResult delBgm(String bgmId) {
        videoService.deleteBgm(bgmId);
        return MyJSONResult.ok();
    }

    @GetMapping("/showReportList")
    public String showReportList() {
        return "video/reportList";
    }

    @PostMapping("/reportList")
    @ResponseBody
    public PagedResult reportList(Integer page) {

        PagedResult result = videoService.queryReportList(page, 10);
        return result;
    }

    @PostMapping("/forbidVideo")
    @ResponseBody
    public MyJSONResult forbidVideo(String videoId) {

        videoService.updateVideoStatus(videoId, VideoStatusEnum.FORBID.value);
        return MyJSONResult.ok();
    }

}


