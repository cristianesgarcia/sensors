-- creates the new database
create database db_sensor;

-- creates the new user
create user 'sensoruser'@'%' identified by 'ThePassword';

-- gives all privileges to this new user
grant all on db_sensor.* to 'sensoruser'@'%';
