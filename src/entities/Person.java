package entities;

public class Person {

    protected String name;
    protected String email;
    protected String phoneNumber;

    public Person() {
    }

    public Person(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email.");
        }
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be empty.");
        }
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", Email: " + email +
                ", Phone: " + phoneNumber;
    }
}
