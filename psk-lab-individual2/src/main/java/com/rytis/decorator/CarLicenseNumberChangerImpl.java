package com.rytis.decorator;

import com.rytis.interceptors.Logger;

import javax.enterprise.context.ApplicationScoped;

import static java.lang.Integer.parseInt;

@ApplicationScoped
public class CarLicenseNumberChangerImpl implements CarLicenseNumberChanger {
    @Override
    @Logger
    public Integer RefactorCarID(int id) {
        String temp = Integer.toString(id);
        temp = Character.toString(temp.charAt(0));
        temp = temp + "123";
        return parseInt(temp);
    }
}
