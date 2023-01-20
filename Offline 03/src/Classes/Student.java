package Classes;

import java.util.HashSet;
import java.util.Set;

public class Student {
    private final int studentNo;
    private final Set<Course> courses;

    public Student(int studentNo) {
        this.studentNo = studentNo;
        courses = new HashSet<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    @Override
    public int hashCode() {
        return studentNo;
    }

    @Override
    public String toString() {
        return "Classes.Student{" +
                "studentNo=" + studentNo +
                ", noOfCourses=" + courses.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student student)) return false;
        return studentNo == student.studentNo;
    }
}
