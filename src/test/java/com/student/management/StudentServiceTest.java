package com.student.management;

import com.student.management.model.Student;
import com.student.management.repository.StudentRepository;
import com.student.management.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService service;

    @Test
    void testCreateStudent() {

        Student student =
                new Student("Rahul Patel",
                        "rahul@gmail.com",
                        "MSc IT",
                        23);

        when(repository.save(student)).thenReturn(student);

        Student result = service.createStudent(student);

        assertNotNull(result);
        assertEquals("Rahul Patel", result.getName());
        assertEquals("rahul@gmail.com", result.getEmail());

        verify(repository, times(1)).save(student);
    }

    @Test
    void testGetAllStudents() {

        Student student1 =
                new Student("Rahul Patel",
                        "rahul@gmail.com",
                        "MSc IT",
                        23);

        Student student2 =
                new Student("Priya Shah",
                        "priya@gmail.com",
                        "MCA",
                        22);

        when(repository.findAll())
                .thenReturn(Arrays.asList(student1, student2));

        List<Student> students = service.getAllStudents();

        assertEquals(2, students.size());

        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetStudentById() {

        Student student =
                new Student("Rahul Patel",
                        "rahul@gmail.com",
                        "MSc IT",
                        23);

        when(repository.findById(1L))
                .thenReturn(Optional.of(student));

        Student result = service.getStudentById(1L);

        assertNotNull(result);
        assertEquals("Rahul Patel", result.getName());
    }

    @Test
    void testUpdateStudent() {

        Student existing =
                new Student("Rahul Patel",
                        "old@gmail.com",
                        "MSc IT",
                        23);

        Student updated =
                new Student("Rahul Updated",
                        "new@gmail.com",
                        "MCA",
                        24);

        when(repository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(repository.save(existing))
                .thenReturn(existing);

        Student result = service.updateStudent(1L, updated);

        assertEquals("Rahul Updated", result.getName());
        assertEquals("new@gmail.com", result.getEmail());
        assertEquals("MCA", result.getCourse());
        assertEquals(24, result.getAge());
    }

    @Test
    void testDeleteStudent() {

        when(repository.existsById(1L))
                .thenReturn(true);

        service.deleteStudent(1L);

        verify(repository, times(1))
                .deleteById(1L);
    }
}