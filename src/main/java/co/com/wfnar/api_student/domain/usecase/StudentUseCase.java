package co.com.wfnar.api_student.domain.usecase;

import co.com.wfnar.api_student.domain.model.Student;
import co.com.wfnar.api_student.domain.model.gateway.StudentRepositoy;

import java.util.List;

public class StudentUseCase {

    private final StudentRepositoy studentRepositoy;

    public StudentUseCase(StudentRepositoy studentRepositoy) {
        this.studentRepositoy = studentRepositoy;
    }

    public List<Student> getAllStudents() {
        return studentRepositoy.getAllStudents();
    }

}
