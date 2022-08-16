package com.mhy.blog.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Random;
import java.util.UUID;


public class FileUtils {

    public static String uploadImg(MultipartFile file, String path1, String name, String path2){
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-","");
        String imgName = uuid + name;
        String code = Integer.toString(new Random().nextInt(5) + 1);
        String imgPath = path1 + "img" + code + "\\";
        String requestPath = path2 + "img" + code + "/";

        try {
            File imgFile = new File(imgPath, imgName);
            file.transferTo(imgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return requestPath + imgName;
    }
}
