package com.guru2test.mvcWebApp.service;

import com.guru2test.mvcWebApp.models.CollegeStudent;
import com.guru2test.mvcWebApp.models.MathGrade;
import com.guru2test.mvcWebApp.repository.MathGradesDao;
import com.guru2test.mvcWebApp.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    @Qualifier("mathGrades")
    private MathGrade mathGrade;

    @Autowired
    private MathGradesDao mathGradesDao;

    public void createStudent(String firstName, String lastName, String emailAddress){
        CollegeStudent student = new CollegeStudent(firstName, lastName, emailAddress);
        student.setId(0);
        studentDao.save(student);
    }

    public boolean checkIfStudentIsNull(int id) {
        Optional<CollegeStudent> student =  studentDao.findById(id);
        if(student.isPresent()){
            return true;
        }
        return false;
    }

    public void deleteStudent(int id) {
        if(checkIfStudentIsNull(id)){
            studentDao.deleteById(id);
        }
    }

    public Iterable<CollegeStudent> getGradebook() {
        Iterable<CollegeStudent> collegeStudents = studentDao.findAll();
        return collegeStudents;
    }

    public boolean createGrade(double grade, int studentId, String gradeType) {
        if(! checkIfStudentIsNull(studentId)){
            return false;
        }

        if(grade >=0 && grade<=100){
            if(gradeType.equals("math")){
                // @Qualifier("mathGrades")
                mathGrade.setId(0);
                mathGrade.setGrade(grade);
                mathGrade.setStudentId(studentId);
                mathGradesDao.save(mathGrade);
                return true;
            }
        }
      return false;
    }
}
