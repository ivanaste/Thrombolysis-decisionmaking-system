package com.ftn.sbnz.service.configuration;

import com.ftn.sbnz.service.services.procena_rizika_od_MU.LoadKieSession;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class KieConfig {
    private final LoadKieSession loadKieSession;

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
    public KieSession kieSession() throws IOException {
        return loadKieSession.execute();
    }
}
