
public class Course {
    private int courseNo;
    private int noOfStudents;
//    private ArrayList<Student> students;

    public Course(int courseNo, int noOfStudents) {
        this.courseNo = courseNo;
        this.noOfStudents = noOfStudents;
//        students = new ArrayList<>();
    }

//    public void addStudent(Student student) {
//        students.add(student);
//    }

//    public void removeStudent(Student student) {
//        students.remove(student);
//    }

    public int getCourseNo() {
        return courseNo;
    }

    public int getNoOfStudents() {
        return noOfStudents;
    }
    //    public ArrayList<Student> getStudents() {
//        return students;
//    }

    @Override
    public int hashCode() {
        return courseNo;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseNo=" + courseNo +
                ", noOfStudents=" + noOfStudents +
                '}';
    }
}
