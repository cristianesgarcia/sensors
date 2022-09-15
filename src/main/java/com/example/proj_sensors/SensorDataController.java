package com.example.proj_sensors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@ExposesResourceFor(SensorDataController.class)
@RequestMapping(path = "/sensors")
public class SensorDataController {
    @Autowired
    private SensorDataRepository sensorDataRepository;
    @Autowired
    private SensorRepository sensorRepository;

    @PostMapping(path = "/{id}/samples")
    public @ResponseBody EntityModel<SensorData> addNewSensorData(
            @PathVariable Integer id,
            @RequestBody SensorData newSensorData ) {
        sensorDataRepository.save(newSensorData);
        newSensorData.setSensor(new Sensor(newSensorData.getSensor().getId()));
        return EntityModel.of(newSensorData,
                linkTo(methodOn(SensorController.class).getSensorId(newSensorData.getSensor().getId())).withRel("sensor"),
                linkTo(methodOn(SensorDataController.class).getSensorDataId(newSensorData.getSensor().getId(),20,-1)).withRel("samples"),
                linkTo(methodOn(SensorController.class).getAll()).withRel("sensors"));
    }

    @GetMapping(path = "/{id}/samples")
    public @ResponseBody Iterable<SensorData> getSensorDataId(
            @PathVariable Integer id,
            @RequestParam(value = "qty", defaultValue = "20") Integer qty,
            @RequestParam(value = "last", defaultValue = "-1") Integer last) {
        if (last > 0) {
            return sensorDataRepository.findSamples(qty, last, sensorRepository.findById(id).get());
        } else {
            return sensorDataRepository.findLastSamples(sensorRepository.findById(id).get(), qty);
        }
    }

}
