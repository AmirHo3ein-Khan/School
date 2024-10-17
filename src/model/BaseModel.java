package model;

import java.util.Objects;

public abstract class BaseModel {
    private Long id;

    public BaseModel(Long id) {
        this.id = id;
    }

    public BaseModel() {
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BaseModel baseModel = (BaseModel) object;
        return Objects.equals(id, baseModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setId(Long id) {
        this.id = id;
    }
}
