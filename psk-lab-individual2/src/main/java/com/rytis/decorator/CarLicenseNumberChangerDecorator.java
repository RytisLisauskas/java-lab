package com.rytis.decorator;

import com.rytis.interceptors.Logger;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import static java.lang.Integer.parseInt;

@Decorator
public abstract class CarLicenseNumberChangerDecorator implements CarLicenseNumberChanger {

    @Inject
    @Delegate
    @Any
    private CarLicenseNumberChanger carLicenseNumberChanger;

    @Override
    @Logger
    public Integer RefactorCarID(int id) {
        return parseInt(carLicenseNumberChanger.RefactorCarID(id) + "321");
    }
}
