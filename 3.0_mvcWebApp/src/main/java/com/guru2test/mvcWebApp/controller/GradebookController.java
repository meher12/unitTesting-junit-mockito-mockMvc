package com.guru2test.mvcWebApp.controller;

import com.guru2test.mvcWebApp.models.CollegeStudent;
import com.guru2test.mvcWebApp.models.Gradebook;
import com.guru2test.mvcWebApp.models.GradebookCollegeStudent;
import com.guru2test.mvcWebApp.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradebookController {

    @Autowired
    private Gradebook gradebook;

    @Autowired
    private StudentAndGradeService studentAndGradeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getStudents(Model m) {
        Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradebook();

        //  got a list of student and add as model attribute
        m.addAttribute("students", collegeStudents);
        return "index";
    }

    @PostMapping(value = "/")
    public String createStudent(@ModelAttribute("student") CollegeStudent student, Model m) {
        studentAndGradeService.createStudent(student.getFirstname(), student.getLastname(),
                student.getEmailAddress());
        // After we create a student, got a list of student and add as model attribute
        Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradebook();
        m.addAttribute("students", collegeStudents);
        return "index";
    }

    @GetMapping("/delete/student/{id}")
    public String deleteStudent(@PathVariable int id, Model m) {

        if (!studentAndGradeService.checkIfStudentIsNull(id)) {
            return "error";
        }
        studentAndGradeService.deleteStudent(id);
        Iterable<CollegeStudent> collegeStudents = studentAndGradeService.getGradebook();

        //update a data source in index page
        m.addAttribute("students", collegeStudents);
        return "index";
    }

    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id, Model m) {
        //If student doesn't exist then return "error"
        if (!studentAndGradeService.checkIfStudentIsNull(id)) {
            return "error";
        }

        GradebookCollegeStudent studentEntity = studentAndGradeService.studentInformation(id);
        m.addAttribute("student", studentEntity);
        // Add math average to the model
        if (studentEntity.getStudentGrades().getMathGradeResults().size() > 0) {
            m.addAttribute("mathAverage", studentEntity.getStudentGrades().findGradePointAverage(
                  studentEntity.getStudentGrades().getMathGradeResults()
            ));
        } else{
            m.addAttribute("mathAverage","N/A");
        }

        // Add science average to the model
        if (studentEntity.getStudentGrades().getScienceGradeResults().size() > 0) {
            m.addAttribute("scienceAverage", studentEntity.getStudentGrades().findGradePointAverage(
                    studentEntity.getStudentGrades().getScienceGradeResults()
            ));
        } else{
            m.addAttribute("scienceAverage","N/A");
        }


        // Add history average to the model
        if (studentEntity.getStudentGrades().getScienceGradeResults().size() > 0) {
            m.addAttribute("historyAverage", studentEntity.getStudentGrades().findGradePointAverage(
                    studentEntity.getStudentGrades().getHistoryGradeResults()
            ));
        } else{
            m.addAttribute("historyAverage","N/A");
        }

        return "studentInformation";
    }

}