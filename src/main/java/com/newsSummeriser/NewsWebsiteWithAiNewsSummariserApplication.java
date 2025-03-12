package com.newsSummeriser;


import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.newsSummeriser.service.StateCityService;





@SpringBootApplication
@EnableScheduling
public class NewsWebsiteWithAiNewsSummariserApplication {
 @Autowired
    private StateCityService stateCityService;
	public static void main(String[] args) {
		SpringApplication.run(NewsWebsiteWithAiNewsSummariserApplication.class, args);
	}
	
	

}
