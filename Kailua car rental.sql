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

CREATE TABLE car (
  id int NOT NULL,
  brand varchar(50) NOT NULL,
  model varchar(50) NOT NULL,
  fuel_type enum('DIESEL','GASOLINE','HYBRID','ELECTRIC') NOT NULL,
  registration_plate varchar(20) NOT NULL,
  registration_year_month varchar(7) NOT NULL,
  odometer int NOT NULL,
  engine_cc int NOT NULL,
  is_manual boolean NOT NULL,
  has_aircon boolean NOT NULL,
  has_cruise_control boolean NOT NULL,
  has_leather_seats boolean NOT NULL,  
  seats int NOT NULL,
  horsepower int NOT NULL,
  car_type enum('SPORT','FAMILY','LUXURY') NOT NULL,
  is_available boolean DEFAULT 1 NOT NULL, 
  PRIMARY KEY (id),
  UNIQUE KEY registration_plate (registration_plate)
);


CREATE TABLE rentalcontract (
  id int NOT NULL,
  renter_id int NOT NULL,
  car_id int NOT NULL,
  from_date_time datetime NOT NULL,
  to_date_time datetime NOT NULL,
  max_km int NOT NULL,
  start_odometer int NOT NULL,
  registration_plate varchar(20) NOT NULL,
  is_contract_completed boolean DEFAULT 0,
  PRIMARY KEY (id),
  KEY renter_id (renter_id),
  KEY car_id (car_id),
  KEY registration_plate (registration_plate),
  FOREIGN KEY (renter_id) REFERENCES renter (id),
  FOREIGN KEY (car_id) REFERENCES car (id),
  FOREIGN KEY (registration_plate) REFERENCES car (registration_plate)
);

INSERT INTO kailua.car (id, brand, model, fuel_type, registration_plate, registration_year_month, odometer, engine_cc, is_manual, has_aircon, has_cruise_control, has_leather_seats, seats, horsepower, car_type, is_available) VALUES (1, 'Mazda', 'MX5', 'GASOLINE', 'JK12345', '2025-02', 0, 200, 1, 0, 0, 0, 2, 300, 'SPORT', 1);
INSERT INTO kailua.car (id, brand, model, fuel_type, registration_plate, registration_year_month, odometer, engine_cc, is_manual, has_aircon, has_cruise_control, has_leather_seats, seats, horsepower, car_type, is_available) VALUES (2, 'Toyota', 'Astra', 'HYBRID', 'GG12345', '2025-02', 0, 3000, 1, 1, 0, 0, 7, 1000, 'FAMILY', 1);
INSERT INTO kailua.car (id, brand, model, fuel_type, registration_plate, registration_year_month, odometer, engine_cc, is_manual, has_aircon, has_cruise_control, has_leather_seats, seats, horsepower, car_type, is_available) VALUES (3, 'Bentley', 'Bentayaga', 'HYBRID', 'GG44667', '2025-02', 0, 6000, 0, 1, 1, 1, 7, 1000, 'LUXURY', 1);

INSERT INTO kailua.renter (id, name, address, zip, city, mobile_phone, phone, email, driver_license_number, driver_since) VALUES (1, 'Valdemar', 'valdevej 19', '2222', 'København', '22113344', '', 'valde@valde.dk', 'valde222', '2024-02-19');
INSERT INTO kailua.renter (id, name, address, zip, city, mobile_phone, phone, email, driver_license_number, driver_since) VALUES (2, 'Mathias', 'Ådalsvej 7', '2635', 'Ishoej', '30569972', '', 'mamo0010@stud.kea.dk', 'daskop', '2022-04-20');
INSERT INTO kailua.renter (id, name, address, zip, city, mobile_phone, phone, email, driver_license_number, driver_since) VALUES (3, 'Sebastian', 'Østergaarden 66', '2635', 'Ishøj', '93960307', '', 'seb@gmail.com', 'BBL243556', '2022-04-20');

INSERT INTO kailua.rentalcontract (id, renter_id, car_id, from_date_time, to_date_time, max_km, start_odometer, registration_plate, is_contract_completed) VALUES (1, 1, 1, '2024-02-21 00:00:00', '2025-02-21 00:00:00', 1000, 0, 'JK12345', null);
INSERT INTO kailua.rentalcontract (id, renter_id, car_id, from_date_time, to_date_time, max_km, start_odometer, registration_plate, is_contract_completed) VALUES (2, 2, 2, '2024-02-21 00:00:00', '2025-03-21 00:00:00', 1000, 0, 'GG12345', null);
INSERT INTO kailua.rentalcontract (id, renter_id, car_id, from_date_time, to_date_time, max_km, start_odometer, registration_plate, is_contract_completed) VALUES (3, 3, 3, '2024-02-24 00:00:00', '2026-03-21 00:00:00', 2000, 0, 'GG44667', null);