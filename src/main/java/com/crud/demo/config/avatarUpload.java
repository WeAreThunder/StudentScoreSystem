package com.crud.demo.config;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Data
public class avatarUpload {
    private String filename;
    private MultipartFile file;

    public String uploadUserAvatar(MultipartFile file) throws IOException {
        this.file = file;
        if(!file.isEmpty()) {
            // 上传文件路径
            String path = "D://MyWork//upload//";
            System.out.println("上传路径为：path = " + path);
            // 上传文件名
            filename = file.getOriginalFilename();
            //随机生成文件名，使用replace，把随机数中的-去掉
            filename = UUID.randomUUID().toString().replace("-","") + filename;
            File filepath = new File(path, filename);
            System.out.println("文件名为："+filename);

            String temp[]=filename.split("\\.");
            String filenameLast=temp[temp.length-1];
            System.out.println("文件后缀名为："+filenameLast);

            if (filenameLast.equals("jpg")||filenameLast.equals("jpeg")||filenameLast.equals("png")||filenameLast.equals("gif")){
                // 判断路径是否存在，如果不存在就创建一个
                if (!filepath.getParentFile().exists()) {
                    filepath.getParentFile().mkdirs();
                }
                // 将上传文件保存到一个目标文件当中
                file.transferTo(new File(path + File.separator + filename));
                return filename;
            }   else {
                return "格式不符";
            }
        } else {
            return "文件为空";
        }
    }

}
