DROP TABLE IF EXISTS employee_shift;
DROP TABLE IF EXISTS store_shift;
DROP TABLE IF EXISTS shift;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS store;

CREATE TABLE store (
	store_id INT AUTO_INCREMENT NOT NULL,
	store_name VARCHAR(256) NOT NULL,
	store_address VARCHAR(256) NOT NULL,
	store_city VARCHAR(60),
	store_state VARCHAR(2),
	store_zip VARCHAR(20),
	store_phone VARCHAR(30),
	PRIMARY KEY (store_id)
);

CREATE TABLE employee (
	employee_id INT AUTO_INCREMENT NOT NULL,
	first_name VARCHAR(60) NOT NULL,
	last_name VARCHAR(60) NOT NULL,
	phone VARCHAR(30),
	pay DECIMAL(38,2),
	store_id INT NOT NULL,
	PRIMARY KEY (employee_id),
	FOREIGN KEY (store_id) REFERENCES store (store_id) ON DELETE CASCADE
);

CREATE TABLE shift (
	shift_id INT AUTO_INCREMENT NOT NULL,
	day_of_week VARCHAR(20),
	start_time TIME(6) NOT NULL,
	end_time TIME(6) NOT NULL,
	PRIMARY KEY (shift_id)
);

CREATE TABLE store_shift(
	store_id INT NOT NULL,
	shift_id INT NOT NULL,
	employee_id INT,
	FOREIGN KEY (store_id) REFERENCES store (store_id) ON DELETE CASCADE,
	FOREIGN KEY (shift_id) REFERENCES shift (shift_id) ON DELETE CASCADE
);

CREATE TABLE employee_shift (
	employee_id INT NOT NULL,
	shift_id INT NOT NULL,
	FOREIGN KEY (employee_id) REFERENCES employee (employee_id) ON DELETE CASCADE,
	FOREIGN KEY (shift_id) REFERENCES shift (shift_id) ON DELETE CASCADE
);