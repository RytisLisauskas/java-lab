package com.rytis.usecases;

import com.rytis.entities.Mechanic;
import com.rytis.persistence.MechanicsDAO;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Model
public class Mechanics implements Serializable {

    @Inject
    private MechanicsDAO dao;

    @Getter
    private List<Mechanic> allMechanics;

    @PostConstruct
    private void init() {
        loadAllMechanics();
    }

    private void loadAllMechanics() {
        allMechanics = dao.findAll();
    }


}
