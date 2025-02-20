CREATE SCHEMA kailua;

CREATE TABLE rentalcontract (
  id int NOT NULL,
  renter_id int NOT NULL,
  car_id int NOT NULL,
  from_date_time datetime NOT NULL,
  to_date_time datetime NOT NULL,
  max_km int NOT NULL,
  start_odometer int NOT NULL,
  registration_plate varchar(20) NOT NULL,
  is_contract_completed tinyint DEFAULT NULL,
  PRIMARY KEY (id),
  KEY renter_id (renter_id),
  KEY car_id (car_id),
  KEY registration_plate (registration_plate),
  CONSTRAINT rentalcontract_1 FOREIGN KEY (renter_id) REFERENCES renter (id),
  CONSTRAINT rentalcontract_2 FOREIGN KEY (car_id) REFERENCES car (id),
  CONSTRAINT rentalcontract_3 FOREIGN KEY (registration_plate) REFERENCES car (registration_plate)
);

CREATE TABLE car (
  id int NOT NULL,
  brand varchar(50) NOT NULL,
  model varchar(50) NOT NULL,
  fuel_type enum('DIESEL','GASOLINE','HYBRID','ELECTRIC') NOT NULL,
  registration_plate varchar(20) NOT NULL,
  registration_year_month varchar(7) NOT NULL,
  odometer int NOT NULL,
  engine_cc int NOT NULL,
  is_manual tinyint(1) NOT NULL,
  has_aircon tinyint(1) NOT NULL,
  has_cruise_control tinyint(1) NOT NULL,
  has_leather_seats tinyint(1) NOT NULL,
  seats int NOT NULL,
  horsepower int NOT NULL,
  car_type enum('SPORT','FAMILY','LUXURY') NOT NULL,
  is_available tinyint(1) DEFAULT 1 NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY registration_plate (registration_plate)
);

CREATE TABLE renter (
id int NOT NULL,
name varchar(100) NOT NULL,
address varchar(255) NOT NULL,
zip varchar(10) NOT NULL,
city varchar(100) NOT NULL,
mobile_phone varchar(20) NOT NULL,
phone varchar(20) DEFAULT NULL,
email varchar(100) NOT NULL,
driver_license_number varchar(30) NOT NULL,
driver_since date NOT NULL,
PRIMARY KEY (id),
UNIQUE KEY email (email),
UNIQUE KEY driver_license_number (driver_license_number)
);
