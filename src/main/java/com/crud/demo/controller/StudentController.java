package com.crud.demo.controller;

import com.crud.demo.entity.Student;
import com.crud.demo.service.StudentService;
import com.sun.deploy.net.HttpResponse;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/studentList")
    public String studentList(Model model){
        List<Student> studentList = studentService.getStudentList();
        model.addAttribute("students",studentList);
        return "studentList";
    }

    @GetMapping("/studentAdd")
    public String getStudentAdd(Model model){
        model.addAttribute("student",new Student());
        return "studentAdd";
    }
    @PostMapping("/studentAdd")
    public String postStudentAdd(@ModelAttribute Student student){
        studentService.create(student);
        return "redirect:/studentList";
    }
    //更新学生信息
    @GetMapping("/studentUpdate/student/{sNumber}")
    public String getStudentUpdate(@PathVariable("sNumber") String sNumber,
                                   Model model){
        Student studentByNumber = studentService.getStudentByNumber(sNumber);
        model.addAttribute("student",studentByNumber);
        return "studentUpdate";
    }
    //更新后返回
    @PostMapping("/studentUpdate")
    public String studentUpdate(@ModelAttribute Student student){
        studentService.updateStudentByNumber(student);
        return "redirect:/studentList";
    }
    //删除学生信息
    @GetMapping("/studentDel/{sNumber}")
    public String delStudentByNumber(@PathVariable("sNumber") String sNumber){
        studentService.delStudentByNumber(sNumber);
        return "redirect:/studentList";
    }
    //查询学生信息
    @GetMapping("/student/searchByName")
    public String getStudentsByNumber(@RequestParam("sName") String sName,
                                      Model model){
        List<Student> studentListByName = studentService.getStudentListByName(sName);
        System.out.println(studentListByName.toString());
        model.addAttribute("students",studentListByName);
        return "studentList";
    }
    //导入excel表
    @RequestMapping("/student/upExcel")
    public String studentUpExcel(@RequestParam("filename")MultipartFile file,
                                 HttpSession session) throws IOException {
        studentService.addStudentByExcel(file);
        return "redirect:/studentList";
    }
    //导出excel表
    @RequestMapping("/student/putExcel")
    public void putStudentExcel(HttpServletResponse response) throws IOException{
        List<Student> studentList = studentService.getStudentList();
        //表头数据
        String[] header = {"ID","学号", "姓名", "性别", "出生日期", "班级名称", "手机号码", "籍贯"};

        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格，设置表格名称为"学生表"
        HSSFSheet sheet = workbook.createSheet("学生表");
        //创建第一行表头
        HSSFRow headrow = sheet.createRow(0);
        //遍历添加表头(下面模拟遍历学生，也是同样的操作过程)
        for (int i = 0; i < header.length; i++) {
            //创建一个单元格
            HSSFCell cell = headrow.createCell(i);
            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(header[i]);
            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
        }
        //遍历添加数据
        int j=1;
        for (Student student : studentList){
            HSSFRow row = sheet.createRow(j);
            for (int i = 0; i < 8; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = null;
                switch (i){
                    case 0:text = new HSSFRichTextString(String.valueOf(student.getId()));break;
                    case 1:text = new HSSFRichTextString(String.valueOf(student.getSNumber()));break;
                    case 2:text = new HSSFRichTextString(String.valueOf(student.getSName()));break;
                    case 3:text = new HSSFRichTextString(String.valueOf(student.getSex()));break;
                    case 4:text = new HSSFRichTextString(String.valueOf(student.getBirthday()));break;
                    case 5:text = new HSSFRichTextString(String.valueOf(student.getClassName()));break;
                    case 6:text = new HSSFRichTextString(String.valueOf(student.getPhone()));break;
                    case 7:text = new HSSFRichTextString(String.valueOf(student.getAddress()));break;
                }
                cell.setCellValue(text);
            }
            j++;
       }
        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/octet-stream");
        //这后面可以设置导出Excel的名称，此例中名为student.xls
        response.setHeader("Content-disposition", "attachment;filename=student.xls");
        //刷新缓冲
        response.flushBuffer();
        //workbook将Excel写入到response的输出流中，供页面下载
        workbook.write(response.getOutputStream());
    }

}
