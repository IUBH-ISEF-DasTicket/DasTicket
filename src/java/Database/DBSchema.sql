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

-- Default User für "#Allgemein" Anfragen
insert into USER (username,password,email,id_usergroup) values ('#Allgemein','#Allgemein2016#','Allgemein@iubh.de',3);

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
INSERT INTO COURSES (name) VALUES
        ('#Allgemein'),
	('IMT101'),
	('BWIR01'),
	('BWIR02'),
	('BBWL01'),
	('BBWL02'),
	('IGIS01'),
	('IOBP01'),
	('IOBP02'),
	('IMT102'),
	('IPWA01'),
	('IPWA02'),
	('IREN01'),
	('BKLR01'),
	('BKLR02'),
	('BFIN01'),
	('BFIN02'),
	('BREC01'),
	('BREC02'),
	('BMAR01'),
	('BMAR02'),
	('IDBS01'),
	('ISPE01'),
	('IQSS01'),
	('BDMG01'),
	('BPUE01'),
	('BANW01'),
	('BURE01'),
	('ISEF01'),
	('IPMG01'),
	('IAMG01'),
	('BBLO01'),
	('ISSE01'),
	('IWNF01'),
	('IWNF02'),
	('IWMB01'),
	('IWMB02'),
	('IWSM01'),
	('IWSM02'),
	('IWBI01'),
	('IWBI02'),
	('BWMI01'),
	('BWMI02'),
	('BWAV01'),
	('BWAV02'),
	('BWAF01'),
	('BWAF02'),
	('BWEC01'),
	('BWEC02'),
	('BWCN01'),
	('BWCN02'),
	('BWBC01'),
	('BWBC02'),
	('BWTO01'),
	('BWTO02'),
	('BWHO01'),
	('BWHO02'),
	('BWSM01'),
	('BWSM02'),
	('BWFS01'),
	('BWFS02'),
	('BWLM01'),
	('BWLM02'),
	('BWGM01'),
	('BWGM02'),
	('BWSC01'),
	('BWSC02'),
	('BWIM01'),
	('BWIM02'),
	('BWEM01'),
	('BWEM02'),
	('BUFG01'),
	('BUGR01'),
	('BBAK01'),
	('BBAK02')
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


