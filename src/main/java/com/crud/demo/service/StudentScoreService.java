package com.crud.demo.service;

import com.crud.demo.dto.StudentScoreQueryWrapper;
import com.crud.demo.dto.studentDTO;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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


    public List<studentDTO> getStudentDtoList(String className){
        List<studentDTO> studentDTOList = new ArrayList<>();
        List<Student> studentList = new ArrayList<>();
        if (className.equals("all")){
            studentList = studentMapper.getStudentList();
        }else {
            studentList = studentMapper.getStudentListByClassName(className);
        }
        List<StudentScore> studentScoreList = studentScoreMapper.getStudentScoreList();
        for (Student student : studentList) {
            studentDTO studentDTO = new studentDTO();
            List<StudentScore> studentDtoScoreList = new ArrayList<>();
            studentDTO.setStudent(student);
            for (StudentScore studentScore : studentScoreList) {
                if (studentScore.getSNumber().equals(student.getSNumber())){
                    studentDtoScoreList.add(studentScore);
                }
            }
            studentDTO.setStudentScoreList(studentDtoScoreList);
            studentDTOList.add(studentDTO);
        }
        return studentDTOList;
    }

    public List<StudentScore> getStudentScoreList() {
        return studentScoreMapper.getStudentScoreList();
    }

    public List<StudentScore> getStudentScoreListByName(String sName) {
        return studentScoreMapper.getStudentScoreListByName(sName);
    }

    public void createStudentScore(StudentScore studentScore) {
        //创建学生成绩时，填充课程名，教师号，教师名，学生名
        List<Course> courseList = courseMapper.selectByCourseNumber(studentScore.getCourseNumber());
        Course course = courseList.get(0);
        studentScore.setCourseName(course.getCourseName());
        studentScore.setTNumber(course.getTNumber());
        studentScore.setTName(course.getTName());
        Student studentByNumber = studentMapper.getStudentByNumber(studentScore.getSNumber());
        studentScore.setSName(studentByNumber.getSName());
        studentScoreMapper.createStudentScore(studentScore);
    }

    public void updateStudentScoreById(StudentScore studentScore) {
        List<Course> courseList = courseMapper.selectByCourseNumber(studentScore.getCourseNumber());
        Course course = courseList.get(0);
        studentScore.setCourseName(course.getCourseName());
        studentScore.setTNumber(course.getTNumber());
        studentScore.setTName(course.getTName());
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

    public boolean addStudentScoreByExcel(MultipartFile file) throws IOException {
        List<StudentScore> studentScoreList = new ArrayList<>();
        String fileName = file.getOriginalFilename();
        //得到文件后缀名
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        //判断是否为excel文件
        if (!(suffix.equals("xlsx") || suffix.equals("xls"))){
            System.out.println("文件格式不符");
            return false;
        }
        InputStream ins = file.getInputStream();
        Workbook wb = null;
//       POI 提供了对2003版本的Excel的支持 ---- HSSFWorkbook
//　　　　POI 提供了对2007版本以及更高版本的支持 ---- XSSFWorkbook
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
        return true;

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

    public List<StudentScore> selectBySNumber(String sNumber) {
        return studentScoreMapper.selectBySNumber(sNumber);
    }

    public int deleteBySNumber(String sNumber) {
        return studentScoreMapper.deleteBySNumber(sNumber);
    }
}

