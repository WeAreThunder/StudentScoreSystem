package com.crud.demo.controller;

import com.crud.demo.config.avatarUpload;
import com.crud.demo.entity.User;
import com.crud.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class UserController {


    @Autowired
    UserService userService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /*查询用户列表*/
    @RequestMapping("/list")
    public String userList(Model model) {
        model.addAttribute("users", userService.userListByNew());
        return "list";
    }

    /*删除用户*/
    @RequestMapping("/del")
    public String deleteUser(Integer id) {
        userService.delete(id);
        return "redirect:/list";
    }

    /*添加用户页面*/
    @RequestMapping("/add")
    public String addUser(ModelMap map) {
        map.addAttribute("user", new User());
        return "add";
    }
    /*添加完用户后重定向到list页面*/
    @PostMapping("/saveI")
    public String saveI(@ModelAttribute User user,
                        @RequestParam("file") MultipartFile file) throws IOException {
        avatarUpload avatarUpload = new avatarUpload();
        String result = avatarUpload.uploadUserAvatar(file);
        if (result.equals("文件为空")){
            System.out.println(result);
        }else if(result.equals("格式不符")){
            System.out.println(result);
        }else {
            user.setAvatar(result);
        }
        userService.insert(user);
        return "redirect:/list";
    }


    /*更新用户页面*/
    @RequestMapping("/update")
    public String updateUser(ModelMap map) {
        map.addAttribute("user", new User());

        return "update";
    }

    @GetMapping("/update/user/{id}")
    public String updateUserbyId(@PathVariable("id") Integer id,
                                 Model model){
        User user = userService.getById(id);
        model.addAttribute("user",user);
        return "update";
    }

    /*更新完用户后重定向到list页面*/
    @RequestMapping("/saveU")
    public String saveU(@ModelAttribute User user,
                        @RequestParam("file") MultipartFile file) throws IOException {
//        if(!file.isEmpty()) {
//            // 上传文件路径
//            //得到tomcat的上传路径
////            String path = request.getServletContext().getRealPath("/upload/");
//            String path = "D://MyWork//upload//";
//            System.out.println("上传路径为：path = " + path);
//            // 上传文件名
//            String filename = file.getOriginalFilename();
//            File filepath = new File(path, filename);
//            System.out.println("文件名为："+filename);
//
//            String temp[]=filename.split("\\.");
//            String filenameLast=temp[temp.length-1];
//            System.out.println("文件后缀名为："+filenameLast);
//
//            // 判断路径是否存在，如果不存在就创建一个
//            if (!filepath.getParentFile().exists()) {
//                filepath.getParentFile().mkdirs();
//            }
//            // 将上传文件保存到一个目标文件当中
//            file.transferTo(new File(path + File.separator + filename));
//            user.setAvatar(filename);
//        } else {
//            System.out.println("文件为空");
//        }


        avatarUpload avatarUpload = new avatarUpload();
        String result = avatarUpload.uploadUserAvatar(file);
        if (result.equals("文件为空")){
            System.out.println(result);
        }else if(result.equals("格式不符")){
            System.out.println(result);
        }else {
            user.setAvatar(result);
        }

        userService.updateByPrimaryKeySelective(user);
        return "redirect:/list";
    }
}
