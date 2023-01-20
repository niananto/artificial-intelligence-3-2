package Classes;

public class Course {
    private final int courseNo;
    private final int noOfStudents;
//    private ArrayList<Classes.Student> students;

    public Course(int courseNo, int noOfStudents) {
        this.courseNo = courseNo;
        this.noOfStudents = noOfStudents;
//        students = new ArrayList<>();
    }

//    public void addStudent(Classes.Student student) {
//        students.add(student);
//    }

//    public void removeStudent(Classes.Student student) {
//        students.remove(student);
//    }

    public int getCourseNo() {
        return courseNo;
    }

    public int getNoOfStudents() {
        return noOfStudents;
    }
    //    public ArrayList<Classes.Student> getStudents() {
//        return students;
//    }

    @Override
    public int hashCode() {
        return courseNo;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Course course)) return false;
        return courseNo == course.courseNo;
    }

    @Override
    public String toString() {
        return "Classes.Course{" +
                "courseNo=" + courseNo +
                ", noOfStudents=" + noOfStudents +
                '}';
    }
}
