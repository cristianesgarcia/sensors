package com.example.proj_sensors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@ExposesResourceFor(SensorController.class)
@RequestMapping(path = "/sensors")
public class SensorController {
    @Autowired
    private SensorRepository sensorRepository;

    @GetMapping(path = "/{id}")
    public @ResponseBody EntityModel<Sensor> getSensorId(@PathVariable Integer id) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(()-> new SensorNotFoundException(id));
        return EntityModel.of(sensor,
                linkTo(methodOn(SensorController.class).getSensorId(id)).withSelfRel(),
                linkTo(methodOn(SensorDataController.class).getSensorDataId(id,20,-1)).withRel("samples"),
                linkTo(methodOn(SensorController.class).getAll()).withRel("sensors"));
    }

    @GetMapping(path = "/")
    public @ResponseBody Iterable<EntityModel<Sensor>> getAll() {
        ArrayList<EntityModel<Sensor>> sensors = new ArrayList<>();
        sensorRepository.findAll().forEach((s) -> sensors.add(EntityModel.of(s,
                linkTo(methodOn(SensorController.class).getSensorId(s.getId())).withSelfRel(),
                linkTo(methodOn(SensorDataController.class).getSensorDataId(s.getId(),20,-1)).withRel("samples"),
                linkTo(methodOn(SensorController.class).getAll()).withRel("sensors"))));
        return sensors;
    }

    @PostMapping(path = "/add")
    public @ResponseBody EntityModel<Sensor> addNewSensor(@RequestBody Sensor newSensor) {
        sensorRepository.save(newSensor);
        return EntityModel.of(newSensor,
                linkTo(methodOn(SensorController.class).getSensorId(newSensor.getId())).withSelfRel(),
                linkTo(methodOn(SensorDataController.class).getSensorDataId(newSensor.getId(),20,-1)).withRel("samples"),
                linkTo(methodOn(SensorController.class).getAll()).withRel("sensors"));
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody EntityModel<Sensor> updateSensor(@RequestBody Sensor sensor, @PathVariable Integer id) {
        return sensorRepository.findById(id)
                .map(s -> {
                    s.setName(sensor.getName());
                    s.setType(sensor.getType());
                    sensorRepository.save(s);
                    return EntityModel.of(s,
                            linkTo(methodOn(SensorController.class).getSensorId(s.getId())).withSelfRel(),
                            linkTo(methodOn(SensorDataController.class).getSensorDataId(s.getId(),20,-1)).withRel("samples"),
                            linkTo(methodOn(SensorController.class).getAll()).withRel("sensors"));
                })
                .orElseThrow(()-> new SensorNotFoundException(id));
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody String deleteSensor(@PathVariable Integer id) {
        return sensorRepository.findById(id)
                .map(sensor -> {
                    sensorRepository.deleteById(id);
                    return "";
                })
                .orElseThrow(() -> new SensorNotFoundException(id));
    }
}
