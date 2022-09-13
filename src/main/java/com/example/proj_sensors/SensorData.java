package com.example.proj_sensors;

import com.sun.istack.NotNull;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
public class SensorData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor.id")
    private Sensor sensor;

    @NonNull
    @Column(nullable = false)
    private String date_sampled;
//    @NotNull
//    @Column(nullable = false)
//    private Integer data_type;
    @NotNull
    @Column(nullable = false)
    private Integer sample;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public String getDate_sampled() {
        return date_sampled;
    }

    public void setDate_sampled(@NonNull String date_sampled) {
        this.date_sampled = date_sampled;
    }

//    public Integer getData_type() {
//        return data_type;
//    }
//
//    public void setData_type(Integer data_type) {
//        this.data_type = data_type;
//    }

    public Integer getSample() {
        return sample;
    }

    public void setSample(Integer sample) {
        this.sample = sample;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
