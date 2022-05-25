package com.rytis.service;

import com.rytis.interceptors.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@Alternative
@ApplicationScoped
public class LongerCarModelValidator implements ICarModelValidator {

    @Override
    @Logger
    public boolean isCarModelValid(String name) {
        return name.length() < 11;
    }
}
