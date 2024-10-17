package model.dto;

public class CourseDto {
    private Long courseId;
    private String title;
    private int unit;

    public CourseDto(Long courseId, String title, int unite) {
        this.courseId = courseId;
        this.title = title;
        this.unit = unite;
    }

    public CourseDto() {
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return String.format("%-5d | %-12s | %-12s", courseId, title, unit);
    }
}
