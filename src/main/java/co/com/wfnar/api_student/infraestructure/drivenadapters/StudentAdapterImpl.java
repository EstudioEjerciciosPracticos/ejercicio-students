package co.com.wfnar.api_student.infraestructure.drivenadapters;

import co.com.wfnar.api_student.domain.model.Student;
import co.com.wfnar.api_student.domain.model.gateway.StudentRepositoy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;


@Component
public class StudentAdapterImpl implements StudentRepositoy {

    private final RestClient restClient;

    public StudentAdapterImpl(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://dummyjson.com")
                .build();
    }

    @Override
    public List<Student> getAllStudents() {
        Student[] students = restClient.get()
                .uri("/c/87fe-26b3-447a-a3cc")
                .retrieve()
                .body(Student[].class);

        return Arrays.asList(students);
    }
}
