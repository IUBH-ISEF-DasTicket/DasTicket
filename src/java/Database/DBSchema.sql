/*
* CreateSchema.sql
* Erstellung des Datenbankschema
*
* Version 1.3
*
* Ersteller: David Sterz
* Erstellungsdatum: 18.06.2016
* Info/Notizen: Erstellt alle notwendigen Tabellen und deren Spalten
*
* Editiert von: David
* Editiert am: 21.06.16
* Info/Notizen: Settings Tabelle hinzugefügt
*
* Editiert von: David
* Editiert am: 25.06.16
* Info/Notizen: Kurse eingeügt + Settings hinterlegt
*
* Editiert von: David
* Editiert am: 03.07.16
* Info/Notizen: Priority und Werte der Tabellen hinzugefügt
*
* Editiert von: David
* Editiert am: 09.07.16
* Info/Notizen: Foreign Key Priority + Ticket Description
*/

-- Variablendeklaration
SET @DBServer                   = 'localhost';
SET @DBName                     = 'Schema';
SET @DBUser                     = 'User';
SET @DBPassword                 = 'Password';
SET @PendingCloseTimeInDays	= 21;

-- Lösche evtl. vorhandenes Schema
drop schema if exists dasticket; 

-- Erstelle Zieldatenbank
create schema dasticket;

-- Verwende Zieldatenbank
use dasticket;

-- Category Tabelle
CREATE TABLE CATEGORY
(
	id INT(32) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name CHAR(20) not null
);

-- Category füllen
INSERT INTO CATEGORY (name) VALUES
	('Service Request'),
    ('Incident'),
    ('Question');

-- Usergroup Tabelle
CREATE TABLE USERGROUP
(
	id INT(32) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name CHAR(20) not null
);

-- Usergroup füllen
INSERT INTO USERGROUP (name) VALUES
	('Tutor'),
    ('Student'),
    ('Admin');
    
-- State Tabelle
CREATE TABLE STATE
(
	id INT(32) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name CHAR(20) not null
);

-- State füllen
INSERT INTO State (name) VALUES
	('New'),
    ('In Progess'),
    ('Pending Close'),
	('Closed');
    
-- Settings Tabelle
CREATE TABLE SETTINGS
(
	id INT(32) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	module CHAR(30) not null,
	param CHAR(30) not null,
	value CHAR(50) not null
);

-- DB Einstellungen einfügen
INSERT INTO SETTINGS (module, param, value) VALUES
    ('Database', 'Server' , @DBServer),
    ('Database', 'Name' , @DBName),
    ('Database', 'User' , @DBUser),
    ('Database', 'Password' , @DBPassword),
    ('Internal', 'PendingCloseTime' , @PendingCloseTimeInDays)
;

-- User Tabelle
CREATE TABLE USER
(
	id INT(32) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	username CHAR(30) not null,
	password CHAR(30) not null,
	email CHAR(50) not null,
    id_usergroup INT(32) UNSIGNED,
	FOREIGN KEY (id_usergroup) 
        REFERENCES usergroup(id)
        ON DELETE CASCADE
);

-- Courses Tabelle
CREATE TABLE COURSES
(
	id INT(32) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	name CHAR(100) not null,
    id_user INT(32) UNSIGNED,
	FOREIGN KEY (id_user) 
		REFERENCES USER(id)
		ON DELETE CASCADE
);
-- Kurse einfügen
INSERT INTO COURSES (name) VALUES
        ('#Allgemein'),
	('IMT101-Mathematik für Wirtschaftsinformatiker I'),
	('BWIR01-Einführung in das wissenschaftliche Arbeiten'),
	('BWIR02-Selbst- und Zeitmanagement'),
	('BBWL01-BWL I(Einführung, Grundlagen)'),
	('BBWL02-BWL II(Vertiefung)'),
	('IGIS01-Grundlagen der industriellen Softwaretechnik'),
	('IOBP01-Grundlagen der objektorientierten Programmierung mit Java'),
	('IOBP02-Datenstrukturen und Java-Klassenbibliothek'),
	('IMT102-01-Mathematik für Wirtschaftsinformatiker II'),
	('IPWA01-Programmierung von Web-Anwendungsoberflächen'),
	('IPWA02-Programmierung von industriellen Informationssystemen mit Java EE'),
	('IREN01-Requirements Engineering'),
	('BKLR01-Kosten- und Leistungsrechnung I(Einführung)'),
	('BKLR02-Kosten- und Leistungsrechnung II(Vertiefung)'),
	('BFIN01-Finanzierung I(Einführung)'),
	('BFIN02-Finanzierung II(Vertiefung)'),
	('BREC01-01-Recht I(Einführung)'),
	('BREC02-01-Recht II(Vertiefung)'),
	('BMAR01-Marketing I(Einführung)'),
	('BMAR02-Marketing II(Vertiefung)'),
	('IDBS01-Datenmodellierung und Datenbanksysteme'),
	('ISPE01-Spezifikationen'),
	('IQSS01-Qualitätssicherung im SW-Prozess'),
	('BDMG01-Dienstleistungsmanagement'),
	('BPUE01-Planen und Entscheiden'),
	('BANW01-Enterprise Resource Planning'),
	('BURE01-Unternehmensrecht'),
	('ISEF01-Software Engineering-Fallstudie'),
	('IPMG01-IT-Projektmanagement'),
	('IAMG01-IT-Architekturmanagement'),
	('BBLO01-Beschaffung und Logistik'),
	('ISSE01-Seminar Software Engineering'),
	('IWNF01-Techniken und Methoden der agilen SW-Entwicklung'),
	('IWNF02-Fallstudie Agiles Software Engineering'),
	('IWMB01-Mobile Software Engineering am Beispiel der Android-Plattform'),
	('IWMB02-Fallstudie Mobile Software Engineering'),
	('IWSM01-IT-Servicemanagement I'),
	('IWSM02-IT-Servicemanagement II'),
	('IWBI01-Business Intelligence I'),
	('IWBI02-Business Intelligence II'),
	('BWMI01-Internationales Marketing'),
	('BWMI02-Internationales Brand Management'),
	('BWAV01-Angewandter Vertrieb I(Einführung)'),
	('BWAV02-Angewandter Vertrieb II(Vertiefung)'),
	('BWAF01-Advanced Leadership I(Einführung)'),
	('BWAF02-Advanced Leadership II(Vertiefung)'),
	('BWEC01-E-Commerce I(Einführung)'),
	('BWEC02-E-Commerce II(Vertiefung)'),
	('BWCN01-Business Consulting I(Einführung)'),
	('BWCN02-Business Consulting II(Vertiefung)'),
	('BWBC01-Business Controlling I(Einführung)'),
	('BWBC02-Business Controlling II(Vertiefung)'),
	('BWTO01-Nachhaltiges Tourismusmanagement I(Einführung)'),
	('BWTO02-Nachhaltiges Tourismusmanagement II(Vertiefung)'),
	('BWHO01-Hotelmanagement I(Einführung)'),
	('BWHO02-Hotelmanagement II(Vertiefung)'),
	('BWSM01-Servicemanagement I(Einführung)'),
	('BWSM02-Servicemanagement II(Vertiefung)'),
	('BWFS01-Financial Services Management I'),
	('BWFS02-Financial Services Management II'),
	('BWLM01-Luftverkehrsmanagement I'),
	('BWLM02-Luftverkehrsmanagement II'),
	('BWGM01-Einführung in das Gesundheitsmanagement'),
	('BWGM02-Rahmenbedingungen des Gesundheitsmarktes'),
	('BWSC01-Supply Chain Management I'),
	('BWSC02-Supply Chain Management II'),
	('BWIM01-Immobilienmanagement I'),
	('BWIM02-Immobilienmanagement II'),
	('BWEM01-Eventmanagement I(Einführung)'),
	('BWEM02-Eventmanagement II(Vertiefung)'),
	('BUFG01-Unternehmensführung'),
	('BUGR01-Unternehmensgründung und Innovationsmanagement'),
	('BBAK01-Bachelorarbeit'),
	('BBAK02-Kolloquium')
;

-- Priority Tabelle
CREATE TABLE PRIORITY
(
	id INT(32) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name CHAR(20) not null
);

-- Priority füllen
INSERT INTO PRIORITY (name) VALUES
	('High'),
    ('Medium'),
    ('Low');

-- Ticket Tabelle
CREATE TABLE TICKET 
(
    id INT(32) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    title CHAR(50) not null,
    description TEXT not null,
    id_priority INT(32) UNSIGNED,
    id_category INT(32) UNSIGNED,
    id_courses INT(32) UNSIGNED,
    id_user INT(32) UNSIGNED,
    id_user2 INT(32) UNSIGNED,
    id_state INT(32) UNSIGNED,
	FOREIGN KEY (id_category) 
				REFERENCES CATEGORY(id)
				ON DELETE CASCADE,
	FOREIGN KEY (id_courses) 
				REFERENCES COURSES(id)
				ON DELETE CASCADE,
	FOREIGN KEY (id_user) 
                REFERENCES USER(id)
				ON DELETE CASCADE,
	FOREIGN KEY (id_user2)
                REFERENCES USER(id)
				ON DELETE CASCADE,
	FOREIGN KEY (id_state) 
				REFERENCES STATE(id)
				ON DELETE CASCADE,
	FOREIGN KEY (id_priority) 
                REFERENCES PRIORITY(id)
                ON DELETE CASCADE
);

-- Worknotes Tabelle
CREATE TABLE WORKNOTES
(
	id INT(32) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    internal bool,
    notes text not null,
    creationDate date,
    id_ticket INT(32) UNSIGNED,
	FOREIGN KEY (id_ticket) 
		REFERENCES TICKET(id)
		ON DELETE CASCADE
);
-- Attachments Tabelle
CREATE TABLE ATTACHMENTS
(
	id INT(32) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    file BLOB,
    id_ticket INT(32) UNSIGNED,
    FOREIGN KEY (id_ticket) 
		REFERENCES TICKET(id)
		ON DELETE CASCADE
    
);
-- Reportedtime Tabelle
CREATE TABLE REPORTEDTIME
(
	id INT(32) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    reportedTime float,
    id_ticket INT(32) UNSIGNED,
    FOREIGN KEY (id_ticket) 
		REFERENCES TICKET(id)
		ON DELETE CASCADE
);


