package com.example.proj_sensors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/sensors")
public class SensorController {
    @Autowired
    private SensorRepository sensorRepository;

    @GetMapping(path = "/{id}")
    public @ResponseBody Optional<Sensor> getSensorId(@PathVariable Integer id) {
        return sensorRepository.findById(id);
    }

    @PostMapping(path = "/add")
    public @ResponseBody String addNewSensor(@RequestBody Sensor newSensor) {
        sensorRepository.save(newSensor);
        return "Saved";
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody String deleteSensor(@PathVariable Integer id) {
        return sensorRepository.findById(id)
                .map(sensor -> {
                    sensorRepository.deleteById(id);
                    return "Sensor deleted";
                })
                .orElseThrow(() -> new SensorNotFoundException(id));
    }
}
