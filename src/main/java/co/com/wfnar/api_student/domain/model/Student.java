package co.com.wfnar.api_student.domain.model;

public class Student {
        private Integer id;
        private String firstName;
        private String lastName;
        private Integer age;
        private String gender;
        private Integer grade;
        private String group;
        private String ethnicGroup;

    public Student(Integer id, String firstName, String lastName, Integer age, String gender, Integer grade, String group, String ethnicGroup) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.grade = grade;
        this.group = group;
        this.ethnicGroup = ethnicGroup;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Integer getGrade() {
        return grade;
    }

    public String getGroup() {
        return group;
    }

    public String getEthnicGroup() {
        return ethnicGroup;
    }
}
