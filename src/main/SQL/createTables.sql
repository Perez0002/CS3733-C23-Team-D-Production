CREATE TABLE teamdSchema.Node();
CREATE TABLE teamdSchema.LocationName();
CREATE TABLE teamdSchema.Move();

ALTER ROLE teamd SET search_path TO teamdschema, teamdtestschema,public;

-- update employee set password = 'admin' where employeeid = 37;
-- update employee set password = 'staff' where firstname = 'Staff';



-- delete from securityservicerequest;
-- delete from avrequest;
-- delete from computerservicerequest;
-- delete from patienttransportrequest;
-- delete from sanitationrequest;
-- delete from servicerequest;

-- delete from servicerequest;
-- delete from employee;
-- insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER)
-- values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Wong Paradise', 'wwong2@wpi.edu', 'ADMIN', 'Wilson', 'Wong', 'password', 9999555955);
-- insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER)
-- values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'SAE House', 'aschecter@wpi.edu', 'ADMIN', 'Ari', 'Schecter', 'password', 9999555955);
-- insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER)
-- values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Cowboy House', 'bclukens@wpi.edu', 'ADMIN', 'Bryce', 'Lukens', 'password', 9999555955);
-- insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER)
-- values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Cowboy House', 'lperez@wpi.edu', 'ADMIN', 'Liv', 'Perez', 'password', 9999555955);
-- insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER)
-- values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Dorm', 'aralbuquerque@wpi.edu', 'STAFF', 'Abby', 'Albuquerque', 'password', 9999555955);
-- insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER)
-- values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Dorm', 'azimmerman@wpi.edu', 'STAFF', 'Annie', 'Zimmerman', 'password', 9999555955);
-- insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER)
-- values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Homeless', 'admin', 'ADMIN', 'Admin', 'Admin', 'admin', 9999555955);
-- insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER)
-- values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Homeless', 'staff', 'STAFF', 'Staff', 'Staff', 'staff', 9999555955);
-- insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER)
-- values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '100 Institute Road', 'gphilips@wpi.edu', 'STAFF', 'Gibson', 'Philips', 'password', 9999555955);
-- insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER)
-- values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Wyatt House', 'wharris@wpi.edu', 'STAFF', 'Wyatt', 'Harris', 'password', 9845077315);
-- insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER)
-- values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Jonathan House', 'jasher@wpi.edu', 'STAFF', 'Jonathan', 'Asher', 'password', 9845077315);
-- insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER)
-- values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Theo House', 'thouse@wpi.edu', 'STAFF', 'Theo', 'House', 'password', 9845077315);
-- insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER)
-- values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Mike House', 'mwilkinson@wpi.edu', 'STAFF', 'Mike', 'Wilkinson', 'password', 9845077315);
-- insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER)
-- values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Qui House', 'qnguyen@wpi.edu', 'ADMIN', 'Qui', 'Nguyen', 'password', 234798234);
-- insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER)
-- values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Michael House', 'mconnor@wpi.edu', 'ADMIN', 'Michael', 'Connor', 'password', 234798234);
--
-- insert into setting (confetti, darkmode, employeeid)  values (1,0,16);
-- insert into setting (confetti, darkmode, employeeid)  values (1,0,17);
-- insert into setting (confetti, darkmode, employeeid)  values (1,0,18);
-- insert into setting (confetti, darkmode, employeeid)  values (1,0,19);
-- insert into setting (confetti, darkmode, employeeid)  values (1,0,20);
-- insert into setting (confetti, darkmode, employeeid)  values (1,0,21);
-- insert into setting (confetti, darkmode, employeeid)  values (1,0,22);
-- insert into setting (confetti, darkmode, employeeid)  values (1,0,23);
-- insert into setting (confetti, darkmode, employeeid)  values (1,0,24);
-- insert into setting (confetti, darkmode, employeeid)  values (1,0,25);
-- insert into setting (confetti, darkmode, employeeid)  values (1,0,26);
-- insert into setting (confetti, darkmode, employeeid)  values (1,0,27);
-- insert into setting (confetti, darkmode, employeeid)  values (1,0,28);
-- insert into setting (confetti, darkmode, employeeid)  values (1,0,29);
-- insert into setting (confetti, darkmode, employeeid)  values (1,0,30);

/*
drop table Move;
drop table Edge;
drop table Node;
drop table LocationName;
drop table PatientTransportData;
create table Node(
                     nodeID varchar(12) primary key,
                     xcoord integer NOT NULL,
                     ycoord integer NOT NULL,
                     floor varchar(2) NOT NULL,
                     building varchar(255) NOT NULL
);
create table LocationName(
                             longName varchar(255) primary key,
                             shortName varchar(255) NOT NULL,
                             locationType char(4) NOT NULL
);
create table Move(
                     nodeID varchar(12),
                     longName varchar(255),
                     moveDate timestamp primary key,
                     CONSTRAINT fkey_nodeID
                         FOREIGN KEY (nodeID) REFERENCES Node(nodeID),
                     CONSTRAINT fkey_longName
                         FOREIGN KEY (longName) REFERENCES LocationName(longName)
);
create table Edge(
                     node1 varchar(12),
                     node2 varchar(12),
                     CONSTRAINT fkey_node1
                         FOREIGN KEY (node1) REFERENCES Node(nodeID),
                     CONSTRAINT fkey_node2
                         FOREIGN KEY (node2) REFERENCES Node(nodeID)
);
create type stat as enum ('DONE', 'BLANK', 'PROCESSING');
create table PatientTransportData(
                                     patientID varchar(255) NOT NULL,
                                     patientTransportID SERIAL primary key,
                                     startRoom varchar(255) NOT NULL,
                                     endRoom varchar(255) NOT NULL,
                                     equipment varchar(255) NOT NULL,
                                     reason varchar(255) NOT NULL,
                                     sendTo varchar(255) NOT NULL,
                                     status stat NOT NULL,
);
*/