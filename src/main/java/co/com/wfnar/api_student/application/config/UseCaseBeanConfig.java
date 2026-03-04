package co.com.wfnar.api_student.application.config;

import co.com.wfnar.api_student.domain.model.gateway.StudentRepositoy;
import co.com.wfnar.api_student.domain.usecase.StudentUseCase;
import co.com.wfnar.api_student.infraestructure.drivenadapters.StudentAdapterImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class UseCaseBeanConfig {

    @Bean
    public StudentUseCase studentUseCase(StudentRepositoy studentRepositoy) {
        return new StudentUseCase(studentRepositoy);
    }

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }
}
