package com.ftn.sbnz.service.services.alarm;

import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.service.repository.KorisnikRepository;
import com.ftn.sbnz.service.services.mail.SendMail;
import com.ftn.sbnz.service.translations.Codes;
import com.ftn.sbnz.service.translations.Translator;
import lombok.RequiredArgsConstructor;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AlarmListener extends DefaultAgendaEventListener {

    private final SendMail sendMail;
    private final KorisnikRepository korisnikRepository;

    @Override
    public void afterMatchFired(final AfterMatchFiredEvent event) {
        final Object matchedObject = event.getMatch().getObjects().get(0);
        if (matchedObject instanceof AlarmEKG ruleEvent) {
            final List<Korisnik> doctors = korisnikRepository.findAllByRoleIn(List.of(Role.DOCTOR));
            EmailDetails emailDetails = new EmailDetails();
            if (ruleEvent.isIregularanNIIHS()) {
                emailDetails = new EmailDetails("", Translator.toLocale(
                        Codes.ALARM_IREGULARAN_NIHHS, new String[]{ruleEvent.getJmbgPacijenta(), "NIHHS skor je iregularan."}), "NIHHS ALARM");
            }
            else {
                switch (ruleEvent.getRadSrca()) {
                    case UBRZAN -> {
                        emailDetails = new EmailDetails("", Translator.toLocale(
                                Codes.ALARM_RAD_SRCA, new String[]{ruleEvent.getJmbgPacijenta(), this.getMessage(RadSrca.UBRZAN)}), "EKG ALARM");
                    }
                    case USPOREN -> {
                        emailDetails = new EmailDetails("", Translator.toLocale(
                                Codes.ALARM_RAD_SRCA, new String[]{ruleEvent.getJmbgPacijenta(), this.getMessage(RadSrca.USPOREN)}), "EKG ALARM");
                    }
                    case ATRIJALNA_FIBRILACIJA -> {
                        emailDetails = new EmailDetails("", Translator.toLocale(
                                Codes.ALARM_RAD_SRCA, new String[]{ruleEvent.getJmbgPacijenta(), this.getMessage(RadSrca.ATRIJALNA_FIBRILACIJA)}), "EKG ALARM");
                    }
                }
            }
            for (Korisnik doctor : doctors) {
                emailDetails.setRecipient(doctor.getEmail());
//                sendMail.execute(emailDetails);
            }
        }
    }

    private String getMessage(RadSrca radSrca) {
        switch (radSrca) {
            case UBRZAN -> {
                return "primecen ubrzan rad srca!";
            }
            case USPOREN -> {
                return "primecen usporen rad srca!";
            }
            default -> {
                return "primecena atrijalna fibrilacija!";
            }
        }
    }
}
