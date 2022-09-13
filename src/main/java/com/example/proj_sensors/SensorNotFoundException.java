package com.example.proj_sensors;

public class SensorNotFoundException extends RuntimeException {
    SensorNotFoundException(Integer id) {
        super("Could not found sensor: " + id);
    }
}
