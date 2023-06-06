package net.javaguides.sms.service;

import java.util.List;

import net.javaguides.sms.entity.Student;

public interface StudentService {
	List<Student> getAllStudents();
	
	void saveStudent(Student student);
	
	Student getStudentById(String id);
	
	Student updateStudent(Student student);
	
	void deleteStudentById(Long id);
}
