package com.guru2test.mvcWebApp.controller;

import com.guru2test.mvcWebApp.models.CollegeStudent;
import com.guru2test.mvcWebApp.models.Gradebook;
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

        studentAndGradeService.configureStudentInformationModel(id, m);

        return "studentInformation";
    }

    // create a POST /grades mapping Params: grade, gradeType, studentId
    @PostMapping("/grades")
    public String createGrade(@RequestParam("grade") double grade,
                              @RequestParam("gradeType") String gradeType,
                              @RequestParam("studentId") int studentId, Model m) {

        // if student doesn't exist then return error
        if (!studentAndGradeService.checkIfStudentIsNull(studentId)) {
            return "error";
        }

        // create the grade
        boolean success = studentAndGradeService.createGrade(grade, studentId, gradeType);
        if (!success) {
            return "error";
        }

        studentAndGradeService.configureStudentInformationModel(studentId, m);
        return "studentInformation";
    }

    // Delete the grade via HTTP request GET /grades/{id}/{gradeType}
    @GetMapping("/grades/{id}/{gradeType}")
    public String deleteGrade(@PathVariable int id, @PathVariable String gradeType, Model m) {

        int studentId = studentAndGradeService.deleteGrade(id, gradeType);

        if (studentId == 0) {
            return "error";
        }

        studentAndGradeService.configureStudentInformationModel(studentId, m);

        return "studentInformation";
    }

}