package com.josework.service;

import com.josework.domain.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    int addStudent(Student student);

    List<Student> findStudents();
}
