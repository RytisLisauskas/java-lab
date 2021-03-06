package com.rytis.rest;

import com.rytis.decorator.CarLicenseNumberChanger;
import com.rytis.entities.Autoservice;
import com.rytis.entities.Car;
import com.rytis.persistence.AutoservicesDAO;
import com.rytis.persistence.CarsDAO;
import com.rytis.rest.resource.CarDto;
import com.rytis.service.CarModelValidator;
import com.rytis.service.ICarModelValidator;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static java.net.URI.create;
import static java.util.Objects.isNull;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.*;
import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
@Path("/cars")
public class CarsController {

    @Inject
    @Getter
    @Setter
    private CarsDAO carsDAO;

    @Inject
    private ICarModelValidator validator;

    @Inject
    private CarModelValidator carModelValidator;

    @Inject
    private CarLicenseNumberChanger idFixer;

    @Inject
    private AutoservicesDAO autoservicesDAO;

    @Context
    private UriInfo servletRequest;

    @POST
    @Transactional
    @Produces(APPLICATION_JSON)
    public Response createCar(CarDto carDto) {
        Autoservice autoservice = autoservicesDAO.findByName(carDto.getAutoserviceName());
        Car carToCreate = mapCarToEntity(carDto, autoservice);
        carsDAO.persist(carToCreate);
        final String createdUri = servletRequest.getAbsolutePath() + "/" + carToCreate.getId();
        return created(create(createdUri)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public Response getById(@PathParam("id") Integer id) {
        Car car = carsDAO.findOne(id);
        if (isNull(car)) {
            return status(NOT_FOUND).build();
        }
        CarDto carDto = new CarDto(car.getCarModel(), car.getLicenseNumber(), car.getAutoservice().getTitle());
        return ok(carDto).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") Integer id, CarDto carDto) {
        try {
            Car existingCar = carsDAO.findOne(id);
            if (isNull(existingCar)) {
                return status(NOT_FOUND).build();
            }
            boolean validation1 = carModelValidator.isCarModelValid(carDto.getCarModel());
            boolean validation2 = validator.isCarModelValid(carDto.getCarModel());

                existingCar.setCarModel(carDto.getCarModel());
                existingCar.setLicenseNumber(idFixer.RefactorCarID(carDto.getLicenseNumber()));
                carsDAO.update(existingCar);
                return ok().build();


        } catch (OptimisticLockException e) {
            return status(CONFLICT).build();
        }
    }

    private Car mapCarToEntity(CarDto carDto, Autoservice autoservice) {
        Car carToCreate = new Car();
        carToCreate.setLicenseNumber(carDto.getLicenseNumber());
        carToCreate.setCarModel(carDto.getCarModel());
        carToCreate.setAutoservice(autoservice);
        return carToCreate;
    }
}
