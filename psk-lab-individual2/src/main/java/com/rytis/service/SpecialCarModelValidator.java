package com.rytis.service;

import com.rytis.interceptors.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;

@Specializes
@ApplicationScoped
public class SpecialCarModelValidator extends CarModelValidator {
    @Override
    @Logger
    public boolean isCarModelValid(String name) {
        return name == "Audi" || name == "BMW";
    }
}
