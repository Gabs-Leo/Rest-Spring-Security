package com.gabs.security.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gabs.security.entities.Student;

@RestController
public class StudentController {
	
	public static final List<Student> students = Arrays.asList(
		new Student(1, "Gabirle"),
		new Student(2, "Aylon"),
		new Student(3, "Lorrana")
	);
	
	@GetMapping("/students")
	public List<Student> getAllStudents(){
		return students;
	}
	
	@GetMapping("/students/{studentId}")
	public Student getStudent(@PathVariable("studentId")Integer studentId) {
		return students.stream()
		.filter(student -> studentId.equals(student.getStudentId()))
		.findFirst()
		.orElseThrow(() -> new IllegalStateException("Student " + studentId + " does not exist."));
	}
}
