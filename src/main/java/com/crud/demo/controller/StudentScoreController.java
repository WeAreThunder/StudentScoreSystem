package com.crud.demo.controller;

import com.crud.demo.dto.StudentScoreQueryWrapper;
import com.crud.demo.entity.Course;
import com.crud.demo.entity.Student;
import com.crud.demo.entity.StudentScore;
import com.crud.demo.entity.Teacher;
import com.crud.demo.service.CourseService;
import com.crud.demo.service.StudentScoreService;
import com.crud.demo.service.StudentService;
import com.crud.demo.service.TeacherService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class StudentScoreController {
    @Autowired
    private StudentScoreService studentScoreService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/studentScoreList")
    public String StudentScoreList(Model model,
                                   @RequestParam(name = "page",defaultValue="1")Integer page,
                                   @RequestParam(name = "size",defaultValue="10")Integer size,
                                   @RequestParam(name = "name",defaultValue = "") String name,
                                   @RequestParam(name = "courseName",defaultValue = "") String courseName,
                                   @RequestParam(name = "minScore",defaultValue = "0") int minScore,
                                   @RequestParam(name = "maxScore",defaultValue = "0") int maxScore
                                   ){
        //组装查询条件
        StudentScoreQueryWrapper studentScoreQueryWrapper = new StudentScoreQueryWrapper();
        studentScoreQueryWrapper.setName(name);
        studentScoreQueryWrapper.setCourseName(courseName);
        studentScoreQueryWrapper.setMinScore(minScore);
        studentScoreQueryWrapper.setMaxScore(maxScore);


        Page pageHelper = PageHelper.startPage(page, size);
        //离pageHelper最近的一条查询语句会被分页
        List<StudentScore> studentScoreList = studentScoreService.selectByStudentScoreQueryWrapper(studentScoreQueryWrapper);
        //默认的返回值为Long,不过我们没有这么多数据，int足够了
        int total = (int)pageHelper.getTotal();
        int pageCount;
        if ((total % size)==0){
            pageCount = total / size;
        }else {
            pageCount = total /size + 1;
        }
        //向前端传输总页数
        model.addAttribute("pageCount",pageCount);
        //向前端传输当前页数
        model.addAttribute("pageForNow",page);
        //传递学生成绩列表
        model.addAttribute("studentsScore",studentScoreList);

        //传递条件
        model.addAttribute("studentScoreQueryWrapper",studentScoreQueryWrapper);

        return "studentScoreList";
    }


    //添加学生成绩信息
    @GetMapping("/studentScoreAdd")
    public String getStudentScoreAdd(Model model){
        List<Course> courseList = courseService.selectAll();
        List<Teacher> teacherList = teacherService.getTeacherList();
        List<Student> studentList = studentService.getStudentList();
        model.addAttribute("courseList",courseList);
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("studentList",studentList);
        model.addAttribute("studentScore",new StudentScore());
        return "studentScoreAdd";
    }
    @PostMapping("/studentScoreAdd")
    public String postStudentScoreAdd(@ModelAttribute StudentScore studentScore){
        studentScoreService.createStudentScore(studentScore);
        return "redirect:/studentScoreList";
    }
    //更新学生成绩信息
    @GetMapping("/studentScoreUpdate/studentScore/{id}")
    public String getStudentScoreUpdate(@PathVariable("id") Integer id,
                                   Model model){
        StudentScore studentScoreById = studentScoreService.getStudentScoreById(id);
        List<Course> courseList = courseService.selectAll();
        List<Teacher> teacherList = teacherService.getTeacherList();
        List<Student> studentList = studentService.getStudentList();
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("studentList",studentList);
        model.addAttribute("courseList",courseList);
        model.addAttribute("studentScore",studentScoreById);
        return "studentScoreUpdate";
    }
    //更新后返回
    @PostMapping("/studentScoreUpdate")
    public String studentScoreUpdate(@ModelAttribute StudentScore studentScore){
        studentScoreService.updateStudentScoreById(studentScore);
        return "redirect:/studentScoreList";
    }
    //删除学生信息
    @GetMapping("/studentScoreDel/{id}")
    public String delStudentScoreById(@PathVariable("id") Integer id){
        studentScoreService.delStudentScoreById(id);
        return "redirect:/studentScoreList";
    }
    //查询学生信息，这里已经用不着了，留着看看自己以前写的代码有多垃圾吧
    @GetMapping("/studentScore/searchByName")
    public String getStudentsByNumber(//@RequestParam("sName") String sName,
                                      @ModelAttribute StudentScoreQueryWrapper studentScoreQueryWrapper,
                                      Model model){
//        List<StudentScore> studentScoreListByName = studentScoreService.getStudentScoreListByName(sName);
        List<StudentScore> studentScoreList = studentScoreService.selectByStudentScoreQueryWrapper(studentScoreQueryWrapper);
//        System.out.println(studentScoreListByName.toString());
//        model.addAttribute("studentsScore",studentScoreListByName);
        model.addAttribute("studentsScore",studentScoreList);
        return "studentScoreList";
    }
    //导入excel表
    @RequestMapping("/studentScore/upExcel")
    public String studentUpExcel(@RequestParam("filename") MultipartFile file,
                                 HttpSession session) throws IOException {
        studentScoreService.addStudentScoreByExcel(file);
        return "redirect:/studentScoreList";
    }
    //导出excel表
    @RequestMapping("/studentScore/putExcel")
    public void putStudentExcel(HttpServletResponse response) throws IOException{
        List<StudentScore> studentScoreList = studentScoreService.getStudentScoreList();
        //表头数据
        String[] header = {"ID","课程号", "课程名", "学号", "学生姓名", "成绩", "教师工号", "教师名"};

        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个sheet表格，设置表格名称为"学生表"
        HSSFSheet sheet = workbook.createSheet("学生成绩表");
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
        for (StudentScore studentScore : studentScoreList){
            HSSFRow row = sheet.createRow(j);
            for (int i = 0; i < 8; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = null;
                switch (i){
                    case 0:text = new HSSFRichTextString(String.valueOf(studentScore.getId()));break;
                    case 1:text = new HSSFRichTextString(String.valueOf(studentScore.getCourseNumber()));break;
                    case 2:text = new HSSFRichTextString(String.valueOf(studentScore.getCourseName()));break;
                    case 3:text = new HSSFRichTextString(String.valueOf(studentScore.getSNumber()));break;
                    case 4:text = new HSSFRichTextString(String.valueOf(studentScore.getSName()));break;
                    case 5:text = new HSSFRichTextString(String.valueOf(studentScore.getScore()));break;
                    case 6:text = new HSSFRichTextString(String.valueOf(studentScore.getTNumber()));break;
                    case 7:text = new HSSFRichTextString(String.valueOf(studentScore.getTName()));break;
                }
                cell.setCellValue(text);
            }
            j++;
        }
        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/octet-stream");
        //这后面可以设置导出Excel的名称，此例中名为studentScore.xls
        response.setHeader("Content-disposition", "attachment;filename=studentScore.xls");
        //刷新缓冲
        response.flushBuffer();
        //workbook将Excel写入到response的输出流中，供页面下载
        workbook.write(response.getOutputStream());
    }
}
