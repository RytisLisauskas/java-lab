package com.rytis.service;

import org.apache.deltaspike.core.api.future.Futureable;

import javax.ejb.AsyncResult;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

@ApplicationScoped
public class LicenseNumberGenerator implements Serializable {

    @Futureable
    public Future<Integer> generateLicenseNumber() {
        try {
            sleep(2 * 1000);
        } catch (InterruptedException e) {
        }
        final Integer generatedLicenseNumber = new Random().nextInt(900)+100;
        return new AsyncResult<>(generatedLicenseNumber);
    }
}
