
CREATE TABLE teamdSchema.Node();
CREATE TABLE teamdSchema.LocationName();
CREATE TABLE teamdSchema.Move();

ALTER ROLE teamd SET search_path TO teamdschema, teamdtestschema,public

delete from employee;
insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER, USERNAME)
values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Wong Paradise', 'wwong2@wpi.edu', 'ADMIN', 'Wilson', 'Wong', 'password', 9999555955, 'wwong2');
insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER, USERNAME)
values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'SAE House', 'aschecter@wpi.edu', 'ADMIN', 'Ari', 'Schecter', 'password', 9999555955, 'ari');
insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER, USERNAME)
values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Cowboy House', 'bclukens@wpi.edu', 'ADMIN', 'Bryce', 'Lukens', 'password', 9999555955, 'bryce');
insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER, USERNAME)
values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Cowboy House', 'lperez@wpi.edu', 'ADMIN', 'Liv', 'Perez', 'password', 9999555955, 'liv');
insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER, USERNAME)
values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Dorm', 'aralbuquerque@wpi.edu', 'STAFF', 'Abby', 'Albuquerque', 'password', 9999555955, 'abby');
insert into employee (BIRTHDAY, ACCOUNTCREATED, ADDRESS, EMAIL, EMPLOYEETYPE, FIRSTNAME, LASTNAME, PASSWORD, PHONENUMBER, USERNAME)
values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Dorm', 'azimmerman@wpi.edu', 'STAFF', 'Annie', 'Zimmerman', 'password', 9999555955, 'annie');

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
