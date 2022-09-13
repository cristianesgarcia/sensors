package com.example.proj_sensors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SensorDataRepository extends CrudRepository<SensorData, Integer> {
    @Query(" select s " +
            "from SensorData s " +
            "where s.sensor = ?1 " +
            "and s.id > (select max(sd.id) - ?2*2 " +
            "               from SensorData sd " +
            "               where sd.sensor = ?1)")
    Iterable<SensorData> findLastSamples(Sensor sensor, Integer qty);

    @Query(" select s " +
            "from SensorData s " +
            "where s.sensor = ?3" +
            "and s.id between ?2-?1 and ?2")
    Iterable<SensorData> findSamples(Integer qty, Integer last, Sensor sensor);

}
