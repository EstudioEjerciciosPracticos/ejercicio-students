package co.com.wfnar.api_student.infraestructure.entrypoints;

import co.com.wfnar.api_student.domain.model.Student;
import co.com.wfnar.api_student.domain.usecase.StudentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentEntryPoint {

    private final StudentUseCase studentUseCase;

    @GetMapping("/students")
    public List<Student> getStudents() {
        List<Student> students = studentUseCase.getAllStudents();
        return students;
    }

    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable Integer id) {

        List<Student> students = studentUseCase.getAllStudents();

        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
