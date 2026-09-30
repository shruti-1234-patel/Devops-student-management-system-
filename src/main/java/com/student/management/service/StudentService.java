package com.student.management.service;

import com.student.management.model.Student;
import com.student.management.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student createStudent(Student student) {
        return repository.save(student);
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
    }

    public Student updateStudent(Long id, Student student) {

        Student existingStudent = getStudentById(id);

        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setCourse(student.getCourse());
        existingStudent.setAge(student.getAge());

        return repository.save(existingStudent);
    }

    public void deleteStudent(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Student not found with ID: " + id);
        }

        repository.deleteById(id);
    }
}