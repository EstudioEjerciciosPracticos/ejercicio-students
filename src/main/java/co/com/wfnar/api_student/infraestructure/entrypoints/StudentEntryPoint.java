package co.com.wfnar.api_student.infraestructure.entrypoints;

import co.com.wfnar.api_student.domain.model.Student;
import co.com.wfnar.api_student.domain.usecase.StudentUseCase;
import lombok.RequiredArgsConstructor;
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
        return studentUseCase.getAllStudents();
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
        return studentUseCase.getAllStudents().stream()
                .filter( student -> student.getGrade() == 11)
                .toList();
    }

    @GetMapping("/basicas/nombres")
    public List<String> getNameStudents() {
        return studentUseCase.getAllStudents().stream()
                .map(Student::getFirstName)
                .toList();
    }

    @GetMapping("/basicas/mayora15")
    public List<Student> getStudentsMayorThat15() {
        return studentUseCase.getAllStudents().stream()
                .filter( student -> student.getAge() > 15)
                .toList();
    }


    // Transformación y ordenamiento
    @GetMapping("/transformacion/menoramayor")
    public List<Student> getStudentsOrderByAge() {
        return studentUseCase.getAllStudents().stream()
                .sorted( Comparator.comparing(Student::getAge))
                .toList();
    }

    @GetMapping("/transformacion/grupos")
    public List<String> getGroups() {
        return studentUseCase.getAllStudents().stream()
                .map(Student::getGroup)
                .distinct()
                .toList();
    }

    // Operaciones de agregación
    @GetMapping("/agregacion/ageaverage")
    public Double getAgeAverage() {
        return studentUseCase.getAllStudents().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    @GetMapping("/agregacion/studentsbygrade")
    public Map<Integer, Long> getTotalByGrade() {

        return studentUseCase.getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getGrade , Collectors.counting()));
    }

    @GetMapping("/agregacion/studentsbyethnic")
    public Map<String, Long> getTotalEthnicGroup() {

        return studentUseCase.getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getEthnicGroup , Collectors.counting()));
    }

    //Agrupaciones
    @GetMapping("/agrupacion/studentsbygrade")
    public Map<Integer, List<Student>> getStudentsByGrade() {

        return studentUseCase.getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getGrade , Collectors.toList()));
    }

    @GetMapping("/agrupacion/studentsbygradeandgender")
    public Map<Integer, Map<String,List<Student>>> getStudentsByGradeAndGender() {

        return studentUseCase.getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getGrade , (Collectors.groupingBy(Student::getGender, Collectors.toList()))));
    }

    //Transformaciones
    @GetMapping("/transformavanzadas/namebygrade")
    public Map<Integer, List<String>> getNameByGrade() {


        return studentUseCase.getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getGrade , (Collectors.mapping(Student::getFirstName, Collectors.toList()))));
    }

    @GetMapping("/transformavanzadas/mayorbygrade")
    public Map<Integer, Optional<Student>> getMayorAgeByGrade() {

        return studentUseCase.getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getGrade , (Collectors.maxBy(Comparator.comparing(Student::getAge)))));
    }

    //Particiones
    @GetMapping("/partitions")
    public Map<Boolean, List<Student>> getStudentsByAge() {

        return studentUseCase.getAllStudents().stream()
                .collect(Collectors.partitioningBy(student -> student.getAge() > 15));
    }

    //busquedas
    @GetMapping("/busquedas/findfirst")
    public Student getfirstStudent() {

        return studentUseCase.getAllStudents().stream()
                .filter(student -> student.getGrade() == 1)
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/busquedas/findanyindigenous")
    public Boolean isAnyIndigenous() {

        return studentUseCase.getAllStudents().stream()
                .anyMatch(student -> student.getEthnicGroup().equals("Indigenous"));
    }

    @GetMapping("/busquedas/mayorfive")
    public Boolean areStudentsMayorFive() {

        return studentUseCase.getAllStudents().stream()
                .allMatch(student -> student.getAge() > 5);
    }

    //Operaciones mas Avanzadas
    @GetMapping("/operavanzadas/tenMoreYoung")
    public List<Student> getStudentsMoreYoung() {

        return studentUseCase.getAllStudents().stream()
                .sorted( Comparator.comparing(Student::getAge))
                .limit( 10)
                .toList();
    }

    @GetMapping("/operavanzadas/porcentajeporetnia")
    public Map<String, Double> getPercentageByEthnicGroup() {
        List<Student> students = studentUseCase.getAllStudents();
        int total = students.size();

        Map<String, Long> countByEthnic = students.stream()
                .collect(Collectors.groupingBy(Student::getEthnicGroup , Collectors.counting()));


        return countByEthnic.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (double) ((entry.getValue() * 100)/total)
                ));
    }

    @GetMapping("/operavanzadas/promedioedad")
    public Map<Integer, Double> getAgeAverageByGroup() {

        return studentUseCase.getAllStudents().stream()
                .collect(Collectors.groupingBy(Student::getGrade , Collectors.averagingInt(Student::getAge)));

    }

    @GetMapping("/operavanzadas/totalgeneroporcadagrado")
    public Map<Integer, Map<String, Long>> getTotalByGenderInEachGrade() {
        List<Student> students = studentUseCase.getAllStudents();


        return students.stream()
                .collect(Collectors.groupingBy(Student::getGrade , Collectors.groupingBy(Student::getGender, Collectors.counting())));

    }

}
