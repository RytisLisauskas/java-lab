package com.rytis.persistence;

import com.rytis.entities.Car;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class CarsDAO {

    @Setter
    @Inject
    private EntityManager entityManager;

    public void persist(Car car) {
        entityManager.persist(car);
    }

    public Car findOne(Integer id) {
        return entityManager.find(Car.class, id);
    }

    public Car update(Car car) {
        return entityManager.merge(car);
    }

    public List<Car> findAll() {
        return entityManager.createNamedQuery("Car.findAll", Car.class).getResultList();
    }
}
