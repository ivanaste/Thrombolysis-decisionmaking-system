package com.ftn.sbnz.service;

import java.util.*;

import com.ftn.sbnz.model.models.Pacijent;
import com.ftn.sbnz.service.repository.KorisnikRepository;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = { "com.ftn.sbnz.model.models" })  // scan JPA entities
@EnableScheduling
@RequiredArgsConstructor
public class ServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(ServiceApplication.class);

	private final Map<String, Pacijent> pacijentiNaEKGu = new HashMap<>();

	private final KorisnikRepository korisnikRepository;

	public static void main(final String[] args) {
		final ApplicationContext ctx = SpringApplication.run(ServiceApplication.class, args);

		final String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);

		final StringBuilder sb = new StringBuilder("Application beans:\n");
		for (final String beanName : beanNames) {
			sb.append(beanName + "\n");
		}
		log.info(sb.toString());
	}

	@Bean
	public KieContainer kieContainer() {
		final KieServices ks = KieServices.Factory.get();
		final KieContainer kContainer = ks
			.newKieContainer(ks.newReleaseId("com.ftn.sbnz", "kjar", "0.0.1-SNAPSHOT"));
		final KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(1000);
		return kContainer;
	}

	@Bean
	public KieSession kieSession() {
		final KieContainer kieContainer = kieContainer();
		//KieBase kieBase = kieContainer.getKieBase();
		return kieContainer.newKieSession();
	}

	@Bean
	public Map<String, Pacijent> PacijentiNaEKGu() {
		pacijentiNaEKGu.put("11051974565555", korisnikRepository.getPacijentByJmbg("11051974565555"));
		return pacijentiNaEKGu;
	}

}
