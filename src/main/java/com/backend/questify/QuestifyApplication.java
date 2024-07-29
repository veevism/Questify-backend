package com.backend.questify;

import com.backend.questify.Entity.User;
import com.backend.questify.Model.Role;
import com.backend.questify.Service.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

@SpringBootApplication
public class QuestifyApplication {


    public static void main(String[] args) {

        SpringApplication.run(QuestifyApplication.class, args);
    }



    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
            }
        };
    }
// Todo 1.Logging 2.Time Assess And Scoring
    // Time Assess track only when is laboratory start and end
    // minus them will get all the times take
    // if exceed time the turn over will be late and status of the result submission will be late and appear in the "report" ( appear right after lab is start and can be refresh )
    // Todo some property shouldn't be in laboratory move it to question maybe

    // progress is the new name
    // progress use to display the code and how many test case pass ( do this ) and have place for professor to give score

    // Todo logging then when frontend user use key like alt+tab , ctrl c , ctrl v -> will mark the timestamp and how much of it in the report
    // if logging on will prevent right click and everything


}
