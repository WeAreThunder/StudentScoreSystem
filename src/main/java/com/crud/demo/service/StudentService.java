package com.crud.demo.service;

import com.crud.demo.entity.Student;
import com.crud.demo.entity.StudentScore;
import com.crud.demo.entity.User;
import com.crud.demo.mapper.StudentMapper;
import com.crud.demo.mapper.StudentScoreMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StudentScoreMapper studentScoreMapper;

    public List<Student> getStudentList() {
        return studentMapper.getStudentList();
    }

    public List<Student> getStudentListByName(String sName) {
        return studentMapper.getStudentListByName(sName);
    }

    public void create(Student student) {
        studentMapper.create(student);
    }

    public void updateStudentByNumber(Student student) {
        studentMapper.updateByNumber(student);
    }

    //更新学生时同步更新成绩表
    public void updateStudentAndScoreBySNumber(Student student) {
        studentMapper.updateByPrimaryKeySelective(student);
        Student studentByNumber = studentMapper.getStudentByNumber(student.getSNumber());
        //根据学生编号得到成绩表中该学生所有的成绩信息
        List<StudentScore> studentScoreList = studentScoreMapper.selectBySNumber(studentByNumber.getSNumber());
        //遍历成绩信息，更改学生名
        for (StudentScore studentScore : studentScoreList) {
            studentScore.setSName(studentByNumber.getSName());
            studentScoreMapper.updateByPrimaryKey(studentScore);
        }
    }


    public void delStudentByNumber(String sNumber) {
        studentMapper.delByNumber(sNumber);
    }

    public Student getStudentByNumber(String sNumber) {
        Student studentByNumber = studentMapper.getStudentByNumber(sNumber);
        return studentByNumber;
    }

    //导入excel表
    public boolean addStudentByExcel(MultipartFile file) throws IOException {
        List<Student> studentList = new ArrayList<>();
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        //判断是否为excel文件
        if (!(suffix.equals("xlsx") || suffix.equals("xls"))){
            System.out.println("文件格式不符");
            return false;
        }
        InputStream ins = file.getInputStream();
        Workbook wb = null;
        if (suffix.equals("xlsx")) {

            wb = new XSSFWorkbook(ins);

        } else {
            wb = new HSSFWorkbook(ins);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            //从第二行开始
            for (int line = 1; line <= sheet.getLastRowNum(); line++) {
                Student student = new Student();
                Row row = sheet.getRow(line);
                if (null == row) {
                    continue;
                }
                //将所得到的所有数据都转换为String
                for (int i = 0; i <= 6; i++) {
                    row.getCell(i).setCellType(CellType.STRING);
                }
                //获取该行每一列单元格的内容
                Integer id = Integer.valueOf(row.getCell(0).getStringCellValue());
                String number = row.getCell(1).getStringCellValue();
                String name = row.getCell(2).getStringCellValue();
                String sex = row.getCell(3).getStringCellValue();
                String birthday = row.getCell(4).getStringCellValue();
                String className = row.getCell(5).getStringCellValue();
                String phone = row.getCell(6).getStringCellValue();
                String address = row.getCell(7).getStringCellValue();

                student.setId(id);
                student.setSNumber(number);
                student.setSName(name);
                student.setSex(sex);
                student.setBirthday(birthday);
                student.setClassName(className);
                student.setPhone(phone);
                student.setAddress(address);
                //将每一行数据加入List中
                studentList.add(student);
                //在控制台显示得到的每一行数据
                System.out.println(student.toString());
            }
            for (Student student : studentList) {
                String sNumber = student.getSNumber();
                if (studentMapper.getStudentByNumber(sNumber) == null) {
                    studentMapper.insertSelective(student);
                } else {
                    studentMapper.updateByPrimaryKeySelective(student);
                }
            }
        }
        return true;
    }

    public int deleteByPrimaryKey(Integer id) {
        return studentMapper.deleteByPrimaryKey(id);
    }

    public int insert(Student record) {
        return studentMapper.insert(record);
    }

    public int insertSelective(Student record) {
        return studentMapper.insertSelective(record);
    }

    public Student selectByPrimaryKey(Integer id) {
        return studentMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Student record) {
        return studentMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Student record) {
        return studentMapper.updateByPrimaryKey(record);
    }

    public List<Student> getStudentListBySNameForShow(String sName){
        List<Student> studentList = new ArrayList<>();
        if (sName.equals("")){
            studentList = studentMapper.getStudentList();
        }else {
            studentList = studentMapper.getStudentListByName(sName);
        }
        return studentList;
    }
}

