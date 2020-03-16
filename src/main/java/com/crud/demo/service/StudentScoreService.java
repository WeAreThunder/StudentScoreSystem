package com.crud.demo.service;

import com.crud.demo.entity.Student;
import com.crud.demo.entity.StudentScore;
import com.crud.demo.mapper.StudentScoreMapper;
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

    public List<StudentScore> getStudentScoreList(){return studentScoreMapper.getStudentScoreList();}

    public List<StudentScore> getStudentScoreListByName(String sName){return studentScoreMapper.getStudentScoreListByName(sName);}

    public void createStudentScore(StudentScore studentScore){studentScoreMapper.createStudentScore(studentScore);}

    public void updateStudentScoreById(StudentScore studentScore){studentScoreMapper.updateStudentScoreById(studentScore);}

    public void delStudentScoreById(Integer id){studentScoreMapper.delStudentScoreById(id);}

    public StudentScore getStudentScoreById(Integer id){return studentScoreMapper.getStudentScoreById(id); }

    public void addStudentScoreByExcel(MultipartFile file) throws IOException{
        List<StudentScore> studentScoreList = new ArrayList<>();
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        InputStream ins = file.getInputStream();
        Workbook wb = null;
        if(suffix.equals("xlsx")){

            wb = new XSSFWorkbook(ins);

        }else{
            wb = new HSSFWorkbook(ins);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null){
            //从第二行开始
            for (int line = 1; line <= sheet.getLastRowNum(); line++){
                StudentScore studentScore = new StudentScore();
                Row row = sheet.getRow(line);
                if(null == row){
                    continue;
                }
                //将所得到的所有数据都转换为String
                for (int i = 0; i<=7; i++){
                    row.getCell(i).setCellType(CellType.STRING);
                }
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
            for (StudentScore studentScore:studentScoreList){
                Integer id = studentScore.getId();
                if (studentScoreMapper.getStudentScoreById(id)==null){
                    studentScoreMapper.createStudentScore(studentScore);
                }else {
                    studentScoreMapper.updateStudentScoreById(studentScore);
                }
            }
        }

    }
}
