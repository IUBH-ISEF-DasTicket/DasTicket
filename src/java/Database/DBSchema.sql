/*
* CreateSchema.sql
* Erstellung des Datenbankschema
*
* Version 1.5
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
*
* Editiert von: David
* Editiert am: 12.07.16
* Info/Notizen: Kursnamen angepasst und #Allgemein Benutzer eingefügt
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

-- Default User
insert into USER values (1,'#Allgemein','#Allgemein2016#','Allgemein@iubh.de',3);

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
INSERT INTO COURSES (name,id_user) VALUES
        ('#Allgemein',1),
	('IMT101',1),
	('BWIR01',1),
	('BWIR02',1),
	('BBWL01',1),
	('BBWL02',1),
	('IGIS01',1),
	('IOBP01',1),
	('IOBP02',1),
	('IMT102',1),
	('IPWA01',1),
	('IPWA02',1),
	('IREN01',1),
	('BKLR01',1),
	('BKLR02',1),
	('BFIN01',1),
	('BFIN02',1),
	('BREC01',1),
	('BREC02',1),
	('BMAR01',1),
	('BMAR02',1),
	('IDBS01',1),
	('ISPE01',1),
	('IQSS01',1),
	('BDMG01',1),
	('BPUE01',1),
	('BANW01',1),
	('BURE01',1),
	('ISEF01',1),
	('IPMG01',1),
	('IAMG01',1),
	('BBLO01',1),
	('ISSE01',1),
	('IWNF01',1),
	('IWNF02',1),
	('IWMB01',1),
	('IWMB02',1),
	('IWSM01',1),
	('IWSM02',1),
	('IWBI01',1),
	('IWBI02',1),
	('BWMI01',1),
	('BWMI02',1),
	('BWAV01',1),
	('BWAV02',1),
	('BWAF01',1),
	('BWAF02',1),
	('BWEC01',1),
	('BWEC02',1),
	('BWCN01',1),
	('BWCN02',1),
	('BWBC01',1),
	('BWBC02',1),
	('BWTO01',1),
	('BWTO02',1),
	('BWHO01',1),
	('BWHO02',1),
	('BWSM01',1),
	('BWSM02',1),
	('BWFS01',1),
	('BWFS02',1),
	('BWLM01',1),
	('BWLM02',1),
	('BWGM01',1),
	('BWGM02',1),
	('BWSC01',1),
	('BWSC02',1),
	('BWIM01',1),
	('BWIM02',1),
	('BWEM01',1),
	('BWEM02',1),
	('BUFG01',1),
	('BUGR01',1),
	('BBAK01',1),
	('BBAK02',1)
;

-- Default User für "#Allgemein" Anfragen
update courses set id_user = 1 where name = '#Allgemein' and id = 1;

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
    creationDate date,
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


