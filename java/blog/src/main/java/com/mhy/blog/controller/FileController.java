package com.mhy.blog.controller;

import com.mhy.blog.utils.ConstUtils;
import com.mhy.blog.utils.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
public class FileController {

    @PostMapping("/uploadImg")
    public String img(@RequestParam(value = "file") MultipartFile file){


        try{
            return FileUtils.uploadImg(file,
                    ConstUtils.SAVE_IMG_PATH,
                    Objects.requireNonNull(file.getOriginalFilename()),
                    ConstUtils.REQUEST_IMG_PATH);
        }catch (Exception e){
            e.printStackTrace();
            return ConstUtils.IMG_UPLOAD_FAILURE;
        }

    }
}
