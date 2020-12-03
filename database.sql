CREATE DATABASE photostudio;
USE photostudio;


-- CREATE TABLE Orders (
--     OrderID int NOT NULL,
--     OrderNumber int NOT NULL,
--     PersonID int,
--     PRIMARY KEY (OrderID),
--     foreign key (PersonID) references Persons(PersonID)
-- );



CREATE TABLE customer (
    customer_id int NOT NULL AUTO_INCREMENT,
    fname varchar(25) NOT NULL,
    lname varchar(25),
    landmark varchar(25),
    wardname varchar(25),
    city varchar(25),
    PRIMARY KEY (customer_id)
);

CREATE TABLE customer_contact (
	contact_no varchar(10) NOT NULL,
    customer_id int not null,
    PRIMARY KEY (contact_no),
     foreign key (customer_id) references customer(customer_id)
);

CREATE TABLE customer_email (
	email varchar(25) NOT NULL,
    customer_id int NOT NULL ,
    PRIMARY KEY (email),
     foreign key (customer_id) references customer(customer_id)
);


CREATE TABLE supplier (
    supplier_id int NOT NULL AUTO_INCREMENT,
    fname varchar(25) NOT NULL,
    lname varchar(25),
    landmark varchar(25),
    wardname varchar(25),
    city varchar(25),
    PRIMARY KEY (supplier_id)
);

CREATE TABLE supplier_contact (
	contact_no varchar(10) NOT NULL,
    supplier_id int NOT NULL,
    PRIMARY KEY (contact_no),
     foreign key (supplier_id) references supplier(supplier_id)
);

CREATE TABLE supplier_email (
	email varchar(25) NOT NULL,
    supplier_id int NOT NULL ,
    PRIMARY KEY (email),
     foreign key (supplier_id) references supplier(supplier_id)
);


CREATE TABLE employee (
    employee_id int NOT NULL AUTO_INCREMENT,
    fname varchar(25) NOT NULL,
    lname varchar(25),
    landmark varchar(25),
    wardname varchar(25),
    city varchar(25),
    dateofbirth date,
    PRIMARY KEY (employee_id)
);

CREATE TABLE employee_contact (
	contact_no varchar(10) NOT NULL,
    employee_id int NOT NULL,
    PRIMARY KEY (contact_no),
     foreign key (employee_id) references employee(employee_id)
);

CREATE TABLE employee_email (
	email varchar(25) NOT NULL,
    employee_id int NOT NULL ,
    PRIMARY KEY (email),
     foreign key (employee_id) references employee(employee_id)
);

CREATE TABLE orders(
    order_id int NOT NULL AUTO_INCREMENT,
    customer_id int NOT NULL,
    employee_id int NOT NULL,
    orderdate date,
    ordertime time,
    total_price int,
    PRIMARY KEY (order_id),
    foreign key (customer_id) references customer(customer_id),
    foreign key (employee_id) references employee(employee_id)

);

CREATE TABLE items (
    item_id int NOT NULL AUTO_INCREMENT,
    order_id int NOT NULL,
    quantity int,
    PRIMARY KEY (item_id),
    foreign key (order_id) references orders(order_id)

);

CREATE TABLE studio_photograph (
    photo_id int NOT NULL AUTO_INCREMENT,
    order_id int NOT NULL,
    photographer varchar(25),
    size varchar(25),
	price int,
    PRIMARY KEY (photo_id),
    foreign key (order_id) references orders(order_id)

);

CREATE TABLE photo_print (
    print_id int NOT NULL AUTO_INCREMENT,
    order_id int NOT NULL,
    print_size varchar(25),
	price int,
    PRIMARY KEY (print_id),
    foreign key (order_id) references orders(order_id)

);


CREATE TABLE outdoororder (
    outdoororder_id int NOT NULL AUTO_INCREMENT,
    order_id int NOT NULL,
    sdate date,
    edate date,
    stime time,
    etime time,
    ordertype varchar(25),
    service_req varchar(25),
	price int,
    PRIMARY KEY (outdoororder_id),
    foreign key (order_id) references orders(order_id)

);

CREATE TABLE service (
    service_id int NOT NULL AUTO_INCREMENT,
    order_id int NOT NULL,
    servicedate date,
    servicetime time,
    price int,
    PRIMARY KEY (service_id),
    foreign key (order_id) references orders(order_id)

);

CREATE TABLE invoice (
    invoice_id int NOT NULL AUTO_INCREMENT,
    order_id int NOT NULL,
    customer_id int NOT NULL,
    pdate date,
    ptime time,
    amount int,
    status varchar(25),
    PRIMARY KEY (invoice_id),
    foreign key (order_id) references orders(order_id),
    foreign key (customer_id) references customer(customer_id)

);


CREATE TABLE sendemail (
    emailkey int NOT NULL AUTO_INCREMENT,
    order_id int NOT NULL,
    customer_email varchar(25) NOT NULL,
    sdate date,
    stime time,
    status varchar(25),
    PRIMARY KEY (emailkey),
    foreign key (order_id) references orders(order_id),
    foreign key (customer_email) references customer_email(email)

);

CREATE TABLE sendsms (
    smskey int NOT NULL AUTO_INCREMENT,
    order_id int NOT NULL,
    customer_contact varchar(10) NOT NULL,
    sdate date,
    stime time,
    status varchar(25),
    PRIMARY KEY (smskey),
    foreign key (order_id) references orders(order_id),
    foreign key (customer_contact) references customer_contact(contact_no)

);

CREATE TABLE material (
    material_id int NOT NULL AUTO_INCREMENT,
    material_name varchar(25) NOT NULL,
    description varchar(25),
    unitprice int,
    available_quantity int,
    PRIMARY KEY (material_id)

);

CREATE TABLE material_order (
    morder_id int NOT NULL AUTO_INCREMENT,
    material_id int NOT NULL,
    employee_id int NOT NULL,
    supplier_id int NOT NULL,
    odate date,
    otime time,
    quantity int,
    price int,
    PRIMARY KEY (morder_id),
    foreign key (material_id) references material(material_id),
    foreign key (employee_id) references employee(employee_id),
    foreign key (supplier_id) references supplier(supplier_id)

);


CREATE TABLE material_supplier (
	material_id int NOT NULL,
    supplier_id int NOT NULL,
	price int
);

CREATE TABLE material_sold (
	materialsold_id int NOT NULL AUTO_INCREMENT,
    material_id int NOT NULL,
    order_id int NOT NULL,
	name varchar(25),
	price int,
    PRIMARY KEY (materialsold_id),
     foreign key (material_id) references material(material_id),
     foreign key (order_id) references orders(order_id)
);

create table userinfo(
    username varchar(25) primary key,
    password varchar(255) not NULL,
    employee_id int not NULL,
    role enum('ROLE_ADMIN','ROLE_EMPLOYEE') not NULL,
    constraint foreign key (employee_id) references employee(employee_id)
);

create table passwordtoken(
    username varchar(25),
    passwordtoken varchar(255) not NULL
);



