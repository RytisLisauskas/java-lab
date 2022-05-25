package com.rytis.service;

import com.rytis.interceptors.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

import static java.lang.Character.isUpperCase;

@Alternative
@ApplicationScoped
public class CarModelValidator implements ICarModelValidator {

    @Override
    @Logger
    public boolean isCarModelValid(String name) {
        for(int i = 0; i< name.length(); i++){
            Character c = name.charAt(i);
            if(!Character.isLetter(c)){
                return false;
            }
        }
        return true;
    }
}
