CREATE TABLE USER(
USER_ID INT NOT NULL AUTO_INCREMENT,
NAME VARCHAR(30),
SURNAME VARCHAR(30),
GENDER VARCHAR(10),
AGE int,

PRIMARY KEY(USER_ID)
);

UPDATE USER_INTEREST SET SCORE = 0;
 
SELECT * FROM USER WHERE mail = 'idil.utkusoy@ozu.edu.tr' AND password = 'MTIzNDU2'

ALTER TABLE USER MODIFY COLUMN user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY;

ALTER TABLE USER ADD MAIL VARCHAR (30),
ADD PASSWORD VARCHAR (30),
ADD FACULTY VARCHAR (40),
ADD CLASS INT;

UPDATE USER SET FACULTY = 'ENGINEERING';
UPDATE USER SET CLASS = 1;

update USER
SET MAIL = 'idil.utkusoy@ozu.edu.tr', PASSWORD = 'MTIzNDU2'
where USER_ID = 1;

update USER
SET MAIL = 'ali.yilmaz@ozu.edu.tr', PASSWORD = 'MTIzNDU='
where USER_ID = 2;

update USER
SET MAIL = 'ayse.ozturk@ozu.edu.tr', PASSWORD = 'MTIzNDU2Nw=='
where USER_ID = 3;

select * from USER;

CREATE TABLE INTEREST(
INTEREST_ID INT,
INTEREST_NAME VARCHAR(30),
PRIMARY KEY(INTEREST_ID)
);
CREATE TABLE EVENT(
EVENT_ID INT NOT NULL AUTO_INCREMENT,
VENUE VARCHAR(30),
EVENT_NAME VARCHAR(50),
ACTIVITY_DATE TIMESTAMP,
DURATION_TYPE VARCHAR(10),
DURATION INT,
PRIMARY KEY (EVENT_ID)
);
CREATE TABLE USER_INTEREST(
INTEREST_ID INT,
USER_ID INT,
INTEREST_RATE INT,
FOREIGN KEY(INTEREST_ID) REFERENCES INTEREST (INTEREST_ID),
FOREIGN KEY(USER_ID) REFERENCES USER (USER_ID)
);
CREATE TABLE EVENT_INTEREST(
INTEREST_ID INT,
EVENT_ID INT,
FOREIGN KEY(INTEREST_ID) REFERENCES INTEREST (INTEREST_ID),
FOREIGN KEY(EVENT_ID) REFERENCES EVENT (EVENT_ID)
);
CREATE TABLE LOCATION_LOG(
USER_ID INT,
TME TIMESTAMP,
LATITUDE DOUBLE,
LONGITUDE DOUBLE,
FOREIGN KEY(USER_ID) REFERENCES USER(USER_ID)
);
CREATE TABLE VENUE(
VENUE_ID INT NOT NULL,
VENUE_NAME VARCHAR(30),
VENUE_LAT DOUBLE,
VENUE_LON DOUBLE,
PRIMARY KEY (VENUE_ID)
);

SELECT VENUE_LAT, VENUE_LON FROM VENUE WHERE VENUE_NAME = 'Resat Aytac Oditoryumu';

INSERT INTO VENUE(VENUE_ID, VENUE_NAME, VENUE_LAT, VENUE_LON) VALUES (1, 'Harvest Cafe', 41.030460, 29.258986),
(2, 'Subway', 41.032738, 29.258407),
(3, 'Kasikla', 41.032823, 29.257575),
(4, 'Kahve Dunyasi Isletme', 41.032143, 29.257983),
(5, 'Cafe Nero', 41.031488, 29.259083),
(6, 'Sok Market', 41.031423, 29.259463),
(7, 'Yemekhane', 41.031815, 29.259163),
(8, 'Forum Alani', 41.031621, 29.259340),
(9, 'Resat Aytac Oditoryumu', 41.030848, 29.259179),
(10, 'Hukuk Oditoryumu', 41.032876, 29.257801);

INSERT INTO VENUE(VENUE_ID, VENUE_NAME, VENUE_LAT, VENUE_LON) VALUES (11, 'Spor Merkezi', 41.034002, 29.258050),
(12, 'Yuzevler Kebap', 41.032475, 29.258165),
(13, 'Marmaris Bufe', 41.033321, 29.257747),
(14, 'Caglayan Book Store', 41.031779, 29.258943),
(15, 'Ozu Sport Cafe', 41.033903, 29.258305),
(16, 'Dane&Yayik', 41.032175, 29.258675),
(17, 'Pizza Hot Slice',  41.032212, 29.258353);



INSERT INTO INTEREST(INTEREST_ID, INTEREST_NAME) VALUES (1,'Computer Science'), 
(2, 'Sports'),
(3, 'Literature'),
(4, 'Architecture'),
(5, 'Travel'),
(6, 'Cooking'),
(7, 'Music'),
(8, 'Politics'),
(9, 'Langugage'),
(10, 'Law');

INSERT INTO USER(USER_ID, NAME, SURNAME, GENDER, AGE) VALUES (1, 'Zeynep Idil', 'Utkusoy', 'F', 22),
(2, 'Ali', 'Yilmaz', 'M', 30),
(3, 'Ayse', 'Ozturk', 'F', 40);

INSERT INTO EVENT(VENUE, EVENT_NAME, ACTIVITY_DATE, DURATION_TYPE, DURATION) VALUES ('Resat Aytac Oditoryumu', 'Konser', '2017-07-28 21:00:00', 'HOUR', 3),
('Forum Alani', 'Milletvekili Soylesi', '2017-09-16 14:30:00', 'HOUR', 2),
('Spor Merkezi', 'Koc-OzU Voleybol Maci', '2017-09-17 12:00:00', 'HOUR', 2); 
INSERT INTO EVENT(VENUE, EVENT_NAME, ACTIVITY_DATE, DURATION_TYPE, DURATION) VALUES ('Resat Aytac Oditoryumu', 'Coding Lab', '2017-07-28 13:00:00', 'HOUR', 2);
INSERT INTO EVENT(VENUE, EVENT_NAME, ACTIVITY_DATE, DURATION_TYPE, DURATION) VALUES ('Spor Merkezi', 'Hentbol Maci', '2017-07-28 14:00:00', 'HOUR', 2);
INSERT INTO EVENT(VENUE, EVENT_NAME, ACTIVITY_DATE, DURATION_TYPE, DURATION) VALUES ('Hukuk Oditoryumu', 'Anayasa Soylesi', '2017-07-28 16:00:00', 'HOUR', 1);
INSERT INTO EVENT(VENUE, EVENT_NAME, ACTIVITY_DATE, DURATION_TYPE, DURATION) VALUES ('Forum Alani', 'Edebiyat Soylesi', '2017-07-29 13:00:00', 'HOUR', 2);
INSERT INTO EVENT(VENUE, EVENT_NAME, ACTIVITY_DATE, DURATION_TYPE, DURATION) VALUES ('Resat Aytac Oditoryumu', 'Gezi Rehberi Soylesi', '2017-07-29 15:00:00', 'HOUR', 1);
INSERT INTO EVENT(VENUE, EVENT_NAME, ACTIVITY_DATE, DURATION_TYPE, DURATION) VALUES ('Spor Merkezi', 'Voleybol Maci', '2017-07-29 14:30:00', 'HOUR', 2);
INSERT INTO EVENT(VENUE, EVENT_NAME, ACTIVITY_DATE, DURATION_TYPE, DURATION) VALUES ('Forum Alani', 'Mimarlik Soylesi', '2017-07-28 17:00:00', 'HOUR', 2);
INSERT INTO EVENT(VENUE, EVENT_NAME, ACTIVITY_DATE, DURATION_TYPE, DURATION) VALUES ('Spor Merkezi', 'Pilates', '2017-07-29 15:20:00', 'HOUR', 2);
INSERT INTO EVENT(VENUE, EVENT_NAME, ACTIVITY_DATE, DURATION_TYPE, DURATION) VALUES ('Caglayan Book Store', 'Imza Gunu', '2017-07-28 16:00:00', 'HOUR', 1);
INSERT INTO EVENT(VENUE, EVENT_NAME, ACTIVITY_DATE, DURATION_TYPE, DURATION) VALUES ('Pizza Hot Slice', 'Pizza Kursu', '2017-07-28 17:00:00', 'HOUR', 2);
INSERT INTO EVENT(VENUE, EVENT_NAME, ACTIVITY_DATE, DURATION_TYPE, DURATION) VALUES ('Resat Aytac Oditoryumu', 'Yemek Soylesi', '2017-07-28 16:30:00', 'HOUR', 1);

INSERT INTO EVENT(VENUE, EVENT_NAME, ACTIVITY_DATE, DURATION_TYPE, DURATION) VALUES ('Resat Aytac Oditoryumu', 'CS Talk', '2017-07-28 15:30:00', 'HOUR', 1);
SELECT * FROM EVENT;

INSERT INTO EVENT(VENUE, EVENT_NAME, ACTIVITY_DATE, DURATION_TYPE, DURATION) VALUES ('Resat Aytac Oditoryumu', 'Sunum', '2017-07-28 11:40:00', 'HOUR', 1);


select U.*, I.INTEREST_NAME from user_interest U, INTEREST I where user_id = 7 and I.INTEREST_ID = U.INTEREST_ID ;


select * from INTEREST  where INTEREST_ID NOT IN(select I.INTEREST_ID from user_interest U, INTEREST I where user_id = 7 and I.INTEREST_ID = U.INTEREST_ID group by I.INTEREST_ID);

select I.*, U.INTEREST_RATE from INTEREST I left outer join USER_INTEREST U on U.USER_ID = 7 and I.INTEREST_ID = U.INTEREST_ID  ;

select * from interest;

INSERT INTO EVENT(VENUE, EVENT_NAME, ACTIVITY_DATE, DURATION_TYPE, DURATION) VALUES ('Resat Aytac Oditoryumu', 'CS Talk', '2017-07-26 20:30:00', 'HOUR', 1);
INSERT INTO EVENT(VENUE, EVENT_NAME, ACTIVITY_DATE, DURATION_TYPE, DURATION) VALUES ('Pizza Hot Slice', 'pizza', '2017-07-26 20:15:00', 'HOUR', 1);


INSERT INTO EVENT_INTEREST(INTEREST_ID, EVENT_ID) VALUES (10,20), (2,21), (1,22), (2,23), (10,24), (3,25), (5,26), (2,27), (4,28), (2,29), (3,30), (6,31), (6,32), (1,33);

SELECT * FROM EVENT;
insert into event_interest (interest_id,event_id) values (1,35);
insert into event_interest (interest_id, event_id) values (2,38);

INSERT INTO EVENT_INTEREST(INTEREST_ID, EVENT_ID) VALUES (1,18);

INSERT INTO EVENT_INTEREST(INTEREST_ID, EVENT_ID) VALUES (5,17);

INSERT INTO USER_INTEREST(INTEREST_ID, USER_ID, INTEREST_RATE) VALUES (1,7,3), (2,7, 2), (5,7, 4);

 

 SELECT * FROM USER WHERE name = 'AYSE' AND surname = 'OZTURK';
 SELECT * FROM EVENT;
 UPDATE USER SET NAME = 'Idil' where user_id =1;
 
 select * from event where timestampdiff(second,activity_date,current_timestamp) = ( select min(timestampdiff(second,activity_date,current_timestamp)) from event where activity_date > current_timestamp and timestampdiff(hour,current_timestamp, activity_date)<=2) ;
 
 
 
 select * from event where sqrt(event_lat) = ( select min(datediff(activity_date,current_timestamp)) from event where activity_date > current_timestamp and timestampdiff(hour,current_timestamp, activity_date)<=2) ;
 
select event.event_id, venue.venue_lat, venue.venue_lon  from event  inner join venue on event.venue = venue.venue_name where event.activity_date > current_timestamp and timestampdiff(hour,current_timestamp, activity_date)<=5;
 
select min(sqrt(pow(41.031536 - tab.lat,2) + pow(29.258768- tab.lon,2))) from (select event.event_id as id, venue.venue_lat as lat, venue.venue_lon  as lon from event  inner join venue on event.venue = venue.venue_name where event.activity_date > current_timestamp and timestampdiff(hour,current_timestamp, activity_date)<=5) as tab;
 
select * from (select event.event_id as id, event.event_name as name, event.venue as venue, event.activity_date as date, venue.venue_lat as lat, venue.venue_lon  as lon from event  inner join venue on event.venue = venue.venue_name where event.activity_date > current_timestamp and timestampdiff(hour,current_timestamp, activity_date)<=5) as tab
where sqrt(pow(41.031536 - tab.lat,2) + pow(29.258768- tab.lon,2)) = (select min(sqrt(pow(41.031536 - tab2.lat,2) + pow(29.258768- tab2.lon,2))) from (select event.event_id as id, venue.venue_lat as lat, venue.venue_lon  as lon from event  inner join venue on event.venue = venue.venue_name where event.activity_date > current_timestamp and timestampdiff(hour,current_timestamp, activity_date)<=5) as tab2);

select min(timestampdiff(second,activity_date,current_timestamp)) from event where activity_date > current_timestamp and timestampdiff(hour,current_timestamp, activity_date)<=2;
select * from EVENT where activity_date > current_timestamp order by ACTIVITY_DATE LIMIT 1;

SELECT * FROM EVENT WHERE EVENT_ID in (5) and activity_date > current_timestamp and timestampdiff(hour,current_timestamp, activity_date)<=5
select * from (select event.event_id as id, event.event_name as name, event.venue as venue, event.activity_date as date, venue.venue_lat as lat, venue.venue_lon as lon from event inner join venue on event.venue = venue.venue_name where event.activity_date > current_timestamp and timestampdiff(hour,current_timestamp, activity_date)<=5) as tab where sqrt(pow(41.7846783 - tab.lat,2) + pow(29.478435- tab.lon,2)) = (select min(sqrt(pow(41.7846783 - tab2.lat,2) + pow(29.478435- tab2.lon,2))) from (select event.event_id as id, venue.venue_lat as lat, venue.venue_lon as lon from event inner join venue on event.venue = venue.venue_name where event.activity_date > current_timestamp and timestampdiff(hour,current_timestamp, activity_date)<=5) as tab2