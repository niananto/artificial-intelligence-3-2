import java.util.ArrayList;

public class Student {
    private int studentNo;
    private ArrayList<Course> courses;

    public Student(int studentNo) {
        this.studentNo = studentNo;
        courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    @Override
    public int hashCode() {
        return studentNo;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentNo=" + studentNo +
                ", noOfCourses=" + courses.size() +
                '}';
    }
}
