package co.com.wfnar.api_student.infraestructure.entrypoints;

import co.com.wfnar.api_student.domain.model.Student;
import co.com.wfnar.api_student.domain.usecase.StudentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentEntryPoint {

    private final StudentUseCase studentUseCase;

    @GetMapping
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

    //Operaciones básicas

    @GetMapping("/basicas/grado11")
    public List<Student> getStudentsGrado11() {
        List<Student> students = studentUseCase.getAllStudents().stream()
                .filter( student -> student.getGrade() == 11)
                .toList();
        return students;
    }

    @GetMapping("/basicas/nombres")
    public List<String> getNameStudents() {
        List<String> students = studentUseCase.getAllStudents().stream()
                .map( student -> student.getFirstName())
                .toList();
        return students;
    }

    @GetMapping("/basicas/mayora15")
    public List<Student> getStudentsMayorThat15() {
        List<Student> students = studentUseCase.getAllStudents().stream()
                .filter( student -> student.getAge() > 15)
                .toList();
        return students;
    }


    // Transformación y ordenamiento
    @GetMapping("/transformacion/menoramayor")
    public List<Student> getStudentsOrderByAge() {
        List<Student> students = studentUseCase.getAllStudents().stream()
                .sorted( Comparator.comparing(Student::getAge))
                .toList();
        return students;
    }

    @GetMapping("/transformacion/grupos")
    public List<String> getGroups() {
        List<String> groups = studentUseCase.getAllStudents().stream()
                .map( student -> student.getGroup())
                .distinct()
                .toList();
        return groups;
    }

    // Operaciones de agregación
    @GetMapping("/agregacion/ageaverage")
    public Double getAgeAverage() {
        Double AgeAverage = studentUseCase.getAllStudents().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
        return AgeAverage;
    }

    @GetMapping("/agregacion/studentsbygrade")
    public Map<Integer, Long> getTotalByGrade() {
        Map<Integer, Long> totalByGroup = studentUseCase.getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getGrade , Collectors.counting()));


        return totalByGroup;
    }

    @GetMapping("/agregacion/studentsbyethnic")
    public Map<String, Long> getTotalEthnicGroup() {
        Map<String, Long> totalByEthnicGroup = studentUseCase.getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getEthnicGroup , Collectors.counting()));


        return totalByEthnicGroup;
    }

    //Agrupaciones
    @GetMapping("/agrupacion/studentsbygrade")
    public Map<Integer, List<Student>> getStudentsByGrade() {
        Map<Integer, List<Student>> studentsByGroup = studentUseCase.getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getGrade , Collectors.toList()));


        return studentsByGroup;
    }

    @GetMapping("/agrupacion/studentsbygradeandgender")
    public Map<Integer, Map<String,List<Student>>> getStudentsByGradeAndGender() {
        Map<Integer, Map<String,List<Student>>> studentsByGroupAndGender = studentUseCase.getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getGrade , (Collectors.groupingBy(Student::getGender, Collectors.toList()))));


        return studentsByGroupAndGender;
    }

    //Transformaciones
    @GetMapping("/transformavanzadas/namebygrade")
    public Map<Integer, List<String>> getNameByGrade() {
        Map<Integer, List<String>> namesByGroup = studentUseCase.getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getGrade , (Collectors.mapping(Student::getFirstName, Collectors.toList()))));


        return namesByGroup;
    }

    @GetMapping("/transformavanzadas/mayorbygrade")
    public Map<Integer, Optional<Student>> getMayorAgeByGrade() {
        Map<Integer, Optional<Student>> mayorAgeByGroup = studentUseCase.getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getGrade , (Collectors.maxBy(Comparator.comparing(Student::getAge)))));


        return mayorAgeByGroup;
    }
}
