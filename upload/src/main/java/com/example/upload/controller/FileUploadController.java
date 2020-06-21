package com.example.upload.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class FileUploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);
    @Value("${file.path}")
    private String filePath;

    @RequestMapping("/toupload")
    public String toUpload(){
        return "upload";
    }

    @RequestMapping("/doUpload")
    public String doUpload(@RequestParam("file")MultipartFile multipartFile, Model model){

        if (multipartFile.isEmpty()){
            model.addAttribute("message","请选择文件");
        }
        File file=new File(filePath,multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(file);
            LOGGER.info("上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("上传失败");
        }

        model.addAttribute("message","上传成功");
        return "result";
    }

    @RequestMapping("/toMultiUpload")
    public String toMultiUpload(){
        return "multiUpload";
    }

    @RequestMapping("/multiUpload")
    public String mutiUpload(HttpServletRequest request,Model model){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        for (int i = 0; i < files.size(); i++) {
            MultipartFile multipartFile = files.get(i);
            String fileName=multipartFile.getOriginalFilename();
            File file=new File(filePath,fileName);
            try {
                multipartFile.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("message","上传成功");
        return "result";
    }
}