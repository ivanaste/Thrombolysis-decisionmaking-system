package com.ftn.sbnz.service.services.procena_rizika_od_MU;

import com.ftn.sbnz.model.models.*;
import lombok.RequiredArgsConstructor;
import org.drools.core.BeliefSystemType;
import org.drools.core.SessionConfiguration;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoadKieSession {

    public KieSession execute() throws IOException {
        KieSession kSession = createKieSessionFromDRL();
        printDrl(kSession);
        return kSession;
    }

    private KieSession createKieSessionFromDRL() throws IOException {
        KieBaseConfiguration config = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();

        config.setOption(EventProcessingOption.STREAM);

        KieHelper kieHelper = new KieHelper();
        getResourceFolderFiles(kieHelper);

        kieHelper.addContent(generisiNivoRizikaTemplate(), ResourceType.DRL);
        kieHelper.addContent(generisiOtkucajiSrcaTemplate(), ResourceType.DRL);

        Results results = kieHelper.verify();

        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)) {
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: " + message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }
        KieBase kieBase = kieHelper.build(config);

        KieSessionConfiguration ksConf = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
        ((SessionConfiguration) ksConf).setBeliefSystemType(BeliefSystemType.DEFEASIBLE);
        return kieBase.newKieSession(ksConf, null);
    }

    private static void getResourceFolderFiles(KieHelper kieHelper) throws IOException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader().getClass().getClassLoader();
        ResourcePatternResolver resolver = new
                PathMatchingResourcePatternResolver(cl);
        Resource[] resources = resolver.getResources("/rules/**/*.drl");
        for (Resource resource : resources) {
            kieHelper.addResource(ResourceFactory.newFileResource(resource.getFile()),
                    ResourceType.DRL);
        }
    }

    public String generisiNivoRizikaTemplate() {
        InputStream nivo_rizika_template = ProcenaRizikaOdMUService.class.getResourceAsStream("/rules/template/nivo_rizika_template.drt");

        List<NivoRizikaTemplateModel> data = new ArrayList<>();
        data.add(new NivoRizikaTemplateModel(0, 4, 50, NivoRizikaOdMU.PROCENA_U_TOKU, NivoRizikaOdMU.NIZAK_RIZIK));
        data.add(new NivoRizikaTemplateModel(4, 6, 50, NivoRizikaOdMU.PROCENA_U_TOKU, NivoRizikaOdMU.NIZAK_RIZIK));
        data.add(new NivoRizikaTemplateModel(6, 8, 100, NivoRizikaOdMU.PROCENA_U_TOKU, NivoRizikaOdMU.VISOK_RIZIK));

        ObjectDataCompiler converter = new ObjectDataCompiler();
        return converter.compile(data, nivo_rizika_template);
    }

    public String generisiOtkucajiSrcaTemplate() {
        InputStream otkucaji_srca_template = ProcenaRizikaOdMUService.class.getResourceAsStream("/rules/template/otkucaji_srca_template.drt");

        List<OtkucajiSrcaTemplateModel> data = new ArrayList<>();
        data.add(new OtkucajiSrcaTemplateModel(20, 60, RadSrca.USPOREN));
        data.add(new OtkucajiSrcaTemplateModel(100, 200, RadSrca.UBRZAN));

        ObjectDataCompiler converter = new ObjectDataCompiler();
        return converter.compile(data, otkucaji_srca_template);
    }

    public void printDrl(KieSession kSession) {
        KieBase kieBase = kSession.getKieBase();
        Iterable<KiePackage> packages = kieBase.getKiePackages();

        for (KiePackage kiePackage : packages) {
            Collection<Rule> rules = kiePackage.getRules();

            for (Rule rule : rules) {
                String ruleName = rule.getName();
                System.out.println("Rule name: " + ruleName);
            }
        }
    }

}
