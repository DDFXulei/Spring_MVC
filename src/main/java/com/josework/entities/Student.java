package com.josework.entities;

public class Student {
    String name;
    int age;

    public Student() {
        System.out.println("调用无参构造方法！");
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("调用有参构造方法！");

    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
