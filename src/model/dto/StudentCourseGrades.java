package model.dto;

public class StudentCourseGrades {
    private String courseTitle;
    private Double grade;

    public StudentCourseGrades(String courseTitle, Double grade) {
        this.courseTitle = courseTitle;
        this.grade = grade;
    }

    public StudentCourseGrades() {
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return String.format("%-12s | %-12s \n",
                courseTitle, grade);
    }
}
