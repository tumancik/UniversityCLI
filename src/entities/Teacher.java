package entities;

import enums.TeacherPosition;

import java.util.Objects;

public class Teacher extends Person {

    private static int nextId = 1;

    private final String teacherId;
    private TeacherPosition position;

    public Teacher(String name,
                   String email,
                   String phoneNumber,
                   TeacherPosition position) {

        super(name, email, phoneNumber);

        this.teacherId = "TCH-" + nextId++;
        setPosition(position);
    }

    public String getTeacherId() {
        return teacherId;
    }

    public TeacherPosition getPosition() {
        return position;
    }

    public void setPosition(TeacherPosition position) {
        if (position == null) {
            throw new IllegalArgumentException(
                    "Teacher position cannot be empty."
            );
        }

        this.position = position;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId='" + teacherId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", position=" + position +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Teacher teacher)) {
            return false;
        }

        return Objects.equals(teacherId, teacher.teacherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherId);
    }
}
