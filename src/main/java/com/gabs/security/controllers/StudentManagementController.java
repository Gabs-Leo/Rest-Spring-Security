package com.gabs.security.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabs.security.entities.Student;

@RestController
@RequestMapping("/management/api/school/students")
public class StudentManagementController {
	public static final List<Student> students = Arrays.asList(
			new Student(1, "Gabirle"),
			new Student(2, "Aylon"),
			new Student(3, "Lorrana")
	);
	
	
	//hasRole('ROLE_'), hasAnyRole('ROLE_'), 
	//hasAuthority('permission'), hasAnyAuthority('permission')
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
	public List<Student> getAllStudents(){
		return students;
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('student:write')")
	public void addNewStudent(@RequestBody Student student) {
		System.out.println(student);
	}
	
	@DeleteMapping(path = "{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void deleteStudent(@PathVariable("studentId") Integer studentId) {
		System.out.println(studentId);
	}
	
	@PutMapping(path = "{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
		System.out.println(studentId + " " + student);
	}
}
