package com.josework.dao;

import com.josework.domain.Student;

import java.util.List;

public interface StudentDao {

    int insertStudent(Student student);

    List<Student> selectStudents();




}
