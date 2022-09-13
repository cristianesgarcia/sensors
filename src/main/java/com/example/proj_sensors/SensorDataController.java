package com.example.proj_sensors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/sensors")
public class SensorDataController {
    @Autowired
    private SensorDataRepository sensorDataRepository;
    @Autowired
    private SensorRepository sensorRepository;

    @PostMapping(path = "/{id}/samples")
    public @ResponseBody String addNewSensorData(
            @PathVariable Integer id,
            @RequestBody SensorData newSensorData ) {
        sensorDataRepository.save(newSensorData);
        return "Saved";
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
