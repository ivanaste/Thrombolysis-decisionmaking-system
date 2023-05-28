package com.ftn.sbnz.service.services.procena_rizika_od_MU;

import com.ftn.sbnz.model.models.NivoRizikaOdMU;
import com.ftn.sbnz.model.models.NivoRizikaTemplateModel;
import com.ftn.sbnz.model.models.OtkucajiSrcaTemplateModel;
import com.ftn.sbnz.model.models.RadSrca;
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
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.internal.utils.KieHelper;
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
        List<InputStream> inputStreams = new ArrayList<>();

        inputStreams.add(ProcenaRizikaOdMUService.class.getResourceAsStream("/rules/cep/cep.drl"));
        inputStreams.add(ProcenaRizikaOdMUService.class.getResourceAsStream("/rules/forward/nivo_rizika_od_mu.drl"));
        inputStreams.add(ProcenaRizikaOdMUService.class.getResourceAsStream("/rules/forward/anamneza.drl"));
        inputStreams.add(ProcenaRizikaOdMUService.class.getResourceAsStream("/rules/forward/laboratorija.drl"));
        inputStreams.add(ProcenaRizikaOdMUService.class.getResourceAsStream("/rules/forward/nastanakSimptoma.drl"));
        inputStreams.add(ProcenaRizikaOdMUService.class.getResourceAsStream("/rules/forward/neuroloski_pregled.drl"));
        inputStreams.add(ProcenaRizikaOdMUService.class.getResourceAsStream("/rules/forward/nihhsSkor.drl"));

        for (InputStream inputStream : inputStreams) {
            kieHelper.addContent(readInputStreamAsString(inputStream), ResourceType.DRL);
        }

        kieHelper.addContent(generisiNivoRizikaTemplate(), ResourceType.DRL);

        String templateDrl = generisiOtkucajiSrcaTemplate();
        System.out.println(templateDrl);
        kieHelper.addContent(templateDrl, ResourceType.DRL);

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


    private String readInputStreamAsString(InputStream inputStream) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
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

        // Iterate over each package and retrieve the rules
        for (KiePackage kiePackage : packages) {
            // Retrieve the rules from the package
            Collection<Rule> rules = kiePackage.getRules();

            // Iterate over each rule and perform necessary operations
            for (Rule rule : rules) {
                String ruleName = rule.getName();
                // Do something with the rule, such as printing the name
                System.out.println("Rule name: " + ruleName);
            }
        }
    }

}
