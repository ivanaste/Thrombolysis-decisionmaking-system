package com.ftn.sbnz.service.simulation;

import org.springframework.stereotype.Service;

import java.util.Random;

public class GenerisiRandomVrednosti {

    public static double execute(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }
}
