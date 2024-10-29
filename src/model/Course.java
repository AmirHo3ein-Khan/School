package model;

public class Course extends BaseModel{
    private String title;
    private int unite;

    public Course(Long id, String title, int unite) {
        super(id);
        this.title = title;
        this.unite = unite;
    }

    public Course() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUnite() {
        return unite;
    }

    public void setUnite(int unite) {
        this.unite = unite;
    }

}
