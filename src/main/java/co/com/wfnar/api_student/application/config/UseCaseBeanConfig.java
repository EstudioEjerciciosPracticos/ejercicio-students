package co.com.wfnar.api_student.application.config;

import co.com.wfnar.api_student.domain.model.gateway.StudentRepositoy;
import co.com.wfnar.api_student.domain.usecase.StudentUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanConfig {

    @Bean
    public StudentUseCase studentUseCase(StudentRepositoy studentRepositoy) {
        return new StudentUseCase(studentRepositoy);
    }
}
