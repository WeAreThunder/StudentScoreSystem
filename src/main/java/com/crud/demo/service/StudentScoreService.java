package com.crud.demo.service;

import com.crud.demo.dto.StudentScoreQueryWrapper;
import com.crud.demo.entity.Course;
import com.crud.demo.entity.Student;
import com.crud.demo.entity.StudentScore;
import com.crud.demo.entity.Teacher;
import com.crud.demo.mapper.CourseMapper;
import com.crud.demo.mapper.StudentMapper;
import com.crud.demo.mapper.StudentScoreMapper;
import com.crud.demo.mapper.TeacherMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.schema.StscChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentScoreService {
    @Autowired
    private StudentScoreMapper studentScoreMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    public List<StudentScore> getStudentScoreList() {
        return studentScoreMapper.getStudentScoreList();
    }

    public List<StudentScore> getStudentScoreListByName(String sName) {
        return studentScoreMapper.getStudentScoreListByName(sName);
    }

    public void createStudentScore(StudentScore studentScore) {
        List<Course> courseList = courseMapper.selectByCourseNumber(studentScore.getCourseNumber());
        studentScore.setCourseName(courseList.get(0).getCourseName());
        Teacher teacherByNumber = teacherMapper.getTeacherByNumber(studentScore.getTNumber());
        studentScore.setTName(teacherByNumber.getTName());
        Student studentByNumber = studentMapper.getStudentByNumber(studentScore.getSNumber());
        studentScore.setSName(studentByNumber.getSName());
        studentScoreMapper.createStudentScore(studentScore);
    }

    public void updateStudentScoreById(StudentScore studentScore) {
        List<Course> courseList = courseMapper.selectByCourseNumber(studentScore.getCourseNumber());
        studentScore.setCourseName(courseList.get(0).getCourseName());
        Teacher teacherByNumber = teacherMapper.getTeacherByNumber(studentScore.getTNumber());
        studentScore.setTName(teacherByNumber.getTName());
        Student studentByNumber = studentMapper.getStudentByNumber(studentScore.getSNumber());
        studentScore.setSName(studentByNumber.getSName());
        studentScoreMapper.updateStudentScoreById(studentScore);
    }

    public void delStudentScoreById(Integer id) {
        studentScoreMapper.delStudentScoreById(id);
    }

    public StudentScore getStudentScoreById(Integer id) {
        return studentScoreMapper.getStudentScoreById(id);
    }

    public void addStudentScoreByExcel(MultipartFile file) throws IOException {
        List<StudentScore> studentScoreList = new ArrayList<>();
        String fileName = file.getOriginalFilename();
        //得到文件后缀名
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        InputStream ins = file.getInputStream();
        Workbook wb = null;
        if (suffix.equals("xlsx")) {
            wb = new XSSFWorkbook(ins);
        } else {
            wb = new HSSFWorkbook(ins);
        }
        //得到表格中的第一个sheet
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            //从第二行开始
            for (int line = 1; line <= sheet.getLastRowNum(); line++) {
                StudentScore studentScore = new StudentScore();
                Row row = sheet.getRow(line);
                if (null == row) {
                    continue;
                }
                //将所得到的所有数据都转换为String
                for (int i = 0; i <= 7; i++) {
                    row.getCell(i).setCellType(CellType.STRING);
                }
                //将数据读取后加入对象studentScore
                Integer id = Integer.valueOf(row.getCell(0).getStringCellValue());
                String courseNumber = row.getCell(1).getStringCellValue();
                String courseName = row.getCell(2).getStringCellValue();
                String sNumber = row.getCell(3).getStringCellValue();
                String sName = row.getCell(4).getStringCellValue();
                Integer score = Integer.valueOf(row.getCell(5).getStringCellValue());
                String tNumber = row.getCell(6).getStringCellValue();
                String tName = row.getCell(7).getStringCellValue();

                studentScore.setId(id);
                studentScore.setCourseNumber(courseNumber);
                studentScore.setCourseName(courseName);
                studentScore.setSNumber(sNumber);
                studentScore.setSName(sName);
                studentScore.setScore(score);
                studentScore.setTNumber(tNumber);
                studentScore.setTName(tName);
                //将每一行数据加入List中
                studentScoreList.add(studentScore);
                //在控制台显示得到的每一行数据
                System.out.println(studentScore.toString());
            }
            for (StudentScore studentScore : studentScoreList) {
                Integer id = studentScore.getId();
                //判断从表格中读取的数据是否和数据库中的数据有重复，不重复则插入，重复则更新
                if (studentScoreMapper.getStudentScoreById(id) == null) {
                    studentScoreMapper.createStudentScore(studentScore);
                } else {
                    studentScoreMapper.updateStudentScoreById(studentScore);
                }
            }
        }

    }

    public int deleteByPrimaryKey(Integer id) {
        return studentScoreMapper.deleteByPrimaryKey(id);
    }

    public int insert(StudentScore record) {
        return studentScoreMapper.insert(record);
    }

    public int insertSelective(StudentScore record) {
        return studentScoreMapper.insertSelective(record);
    }

    public StudentScore selectByPrimaryKey(Integer id) {
        return studentScoreMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(StudentScore record) {
        return studentScoreMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(StudentScore record) {
        return studentScoreMapper.updateByPrimaryKey(record);
    }

    public List<StudentScore> selectByStudentScoreQueryWrapper(StudentScoreQueryWrapper studentScoreQueryWrapper) {
        return studentScoreMapper.selectByStudentScoreQueryWrapper(studentScoreQueryWrapper);
    }

}

