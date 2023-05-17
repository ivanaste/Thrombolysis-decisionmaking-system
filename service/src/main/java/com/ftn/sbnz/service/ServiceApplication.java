package com.ftn.sbnz.service;

import java.util.Arrays;

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

@SpringBootApplication
@EntityScan(basePackages = { "com.ftn.sbnz.model.models" })  // scan JPA entities
public class ServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(ServiceApplication.class);

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
}
