package com.ftn.sbnz.service;

import com.ftn.sbnz.model.models.Pacijent;
import com.ftn.sbnz.service.repository.PacijentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EntityScan(basePackages = {"com.ftn.sbnz.model.models", "com.ftn.sbnz.kjar"})  // scan JPA entities
@EnableScheduling
@ConfigurationPropertiesScan("com.ftn.sbnz.service.configProperties")
@RequiredArgsConstructor
public class ServiceApplication {

    private final PacijentRepository korisnikRepository;

    private static final Logger log = LoggerFactory.getLogger(ServiceApplication.class);

    private final Map<String, Pacijent> pacijentiNaEKGu = new HashMap<>();
    
    public static void main(final String[] args) {
        final ApplicationContext ctx = SpringApplication.run(ServiceApplication.class, args);

        final String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);

//        final StringBuilder sb = new StringBuilder("Application beans:\n");
//        for (final String beanName : beanNames) {
//            sb.append(beanName + "\n");
//        }
//        log.info(sb.toString());
    }


    @Bean
    public Map<String, Pacijent> PacijentiNaEKGu() {
        return pacijentiNaEKGu;
    }

}
