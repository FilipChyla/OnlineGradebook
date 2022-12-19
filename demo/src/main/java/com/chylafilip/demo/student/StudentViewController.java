package com.chylafilip.demo.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentViewController {
    private final StudentService studentService;

    public StudentViewController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String homePage(){

        return "home";
    }

    @GetMapping("view/students")
    public String getStudents(Model model) {
        model.addAttribute("students", studentService.getStudents());

        return "studentsList";
    }

    @GetMapping("view/students/grades")
    public String addGrades(Model model){
        model.addAttribute("students", studentService.getStudents());
        return "addGrades";
    }
}
