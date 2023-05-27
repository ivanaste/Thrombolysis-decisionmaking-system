package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.model.dto.request.ProcenaRizikaOdMURequest;
import com.ftn.sbnz.model.models.ProcenaRizikaOdMU;
import com.ftn.sbnz.service.services.procena_rizika_od_MU.ProcenaRizikaOdMUService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nivoRizika")
public class ProcenaRizikaOdMUController {

    private final ProcenaRizikaOdMUService procenaRizikaOdMUService;

    @PostMapping(value = "/rizikOdMU", produces = "application/json")
    public String utvrdjivanjeNivoaRizikaOdMUTemplejt(@RequestBody final ProcenaRizikaOdMURequest procenaRizikaOdMURequest) throws IOException {

        return procenaRizikaOdMUService.utvrdiNivoRizikaTemplejt(procenaRizikaOdMURequest);
    }

    @GetMapping("/all")
    public List<ProcenaRizikaOdMU> getAll() {
        return procenaRizikaOdMUService.getAll();
    }

}
