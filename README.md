# FlickiPedia
Team 7 – SER322 – Project Deliverable 4 – FlickiPedia
(This database comes bundled with a driver: com.mysql.jdbc.Driver)
Directional steps for launching and searching the FlickiPedia database:

1.	Download the .jar file to a folder on your computer.
2.	Navigate to the folder where the .jar file is located through the command prompt window.
3.	Enter the following statement into the command prompt window:

java -jar flickipedia.jar --url="jdbc:mysql://nailo.synology.me:3307/team7?connectTimeout=15000&socketTimeout=15000" --user="team7" --password="1dphYaeASmHuSnK!" --driver="com.mysql.jdbc.Driver"

4.	Once the database window opens, enter desired search criteria. Check the box next to the desired criteria.
5.	Click the “Search” button to view results.

*Note – Top left box is a program log window. Large box on the right is the output window. The pop out window is a Modal window and must be closed to regain control of the main program*

Directional steps for Inserting a new movie into the FlickiPedia database:
1.	Download the .jar file to a folder on your computer.
2.	Navigate to the folder where the .jar file is located through the command prompt window.
3.	Enter the following statement into the command prompt window:

java -jar flickipedia.jar --url="jdbc:mysql://nailo.synology.me:3307/team7?connectTimeout=15000&socketTimeout=15000" --user="team7" --password="1dphYaeASmHuSnK!" --driver="com.mysql.jdbc.Driver"

4.	Once the database window opens, click the “Insert New Movie” button and enter the listed criteria. *Note – ALL fields are required*
5.	Click “Insert” to submit new movie entry.

*Note – Top left box is a program log window. Large box on the right is the output window. The pop out window is a Modal window and must be closed to regain control of the main program*

Demonstration Video URL:

https://www.youtube.com/watch?v=U2HahOZR_vg   

Team Member Contributions:
Daniel Hajjar – ER Model, Relational Diagram, Most database programming, Testing 
Christopher Toeller – ER Model, Sequel Statements, Some database programming, Testing
John Campbell – Relational Diagram Connections, Videos, ReadMe File Instructions

Create and Insert Statements:

CREATE TABLE SHOOT_LOCATION (
TitleId INT NOT NULL,
City VARCHAR(15) NOT NULL, 
StateAbv VARCHAR(15) NOT NULL,
Country CHAR(9) NOT NULL,
PRIMARY KEY (TitleId, City, StateAbv, Country),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId)
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE GENRE (
Name VARCHAR(15) NOT NULL,
Description VARCHAR(MAX) NOT NULL, 
PRIMARY KEY (Name),
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE PARTICIPANT (
ParticipantId INT NOT NULL,
FirstName VARCHAR(15), 
MiddleName VARCHAR(15),
LastName VARCHAR(15),
PRIMARY KEY (ParticipantId),
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE TRAILER (
TrailerId INT NOT NULL,
TitleId INT NOT NULL,
Name VARCHAR(50), 
Description VARCHAR(MAX),
Duration DECIMAL(4,2),
PRIMARY KEY (TrailerId),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId)
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE REVIEW (
TitleId INT NOT NULL, 
UserId INT NOT NULL,
StarRating INT NOT NULL,
Description VARCHAR(MAX),
PRIMARY KEY (TitleId, UserId),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId),
FOREIGN KEY (UserId) REFERENCES USER(UserId),
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE THEATER (
Name VARCHAR(30) NOT NULL, 
City VARCHAR(15),
Street VARCHAR(15),
StateAbv VARCHAR(15),
Country VARCHAR(15),
Zip INT NOT NULL,
PRIMARY KEY (Name, Zip),
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE STREAM_SERVICE (
Name VARCHAR(20) NOT NULL,
URL VARCHAR(MAX) NOT NULL,
PRIMARY KEY (Name),
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE TITLE (
TitleId INT NOT NULL,
Genre VARCHAR(15) NOT NULL,
PRIMARY KEY (TitleId),
FOREIGN KEY (Genre) REFERENCES GENRE(Name),
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE MOVIE (
TitleId INT NOT NULL,
Country VARCHAR(15), 
AgeRating VARCHAR(5),
Day INT,
Month INT,
Year INT,
PRIMARY KEY (TitleId),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId),
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE TV_SHOW (
TitleId INT NOT NULL,
Country VARCHAR(15),
PRIMARY KEY (TitleId),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId),
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE SEASON (
TitleId INT NOT NULL,
SeasonNumber INT NOT NULL,
PRIMARY KEY (TitleId, SeasonNumber),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId),
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE EPISODE (
TitleId INT NOT NULL,
SeasonNumber INT NOT NULL,
EpisodeNumber INT NOT NULL,
Name VARCHAR(30),
Day INT,
Month INT,
Year INT,
AgeRating VARCHAR(5),
Duration DECIMAL(4,2),
PRIMARY KEY (TitleId, SeasonNumber, EpisodeNumber),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId),
FOREIGN KEY (SeasonNumber) REFERENCES SEASON(SeasonNumber),
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE USER_LIST (
UserId INT NOT NULL,
ListNumber INT NOT NULL,
Watched BIT NOT NULL,
ToWatch BIT NOT NULL,
PRIMARY KEY (UserId, ListNumber),
FOREIGN KEY (UserId) REFERENCES USER(UserId),
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE USER (
UserId INT NOT NULL,
Email VARCHAR(MAX) NOT NULL,
PRIMARY KEY (UserId),
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE ADMIN (
AdminId INT NOT NULL,
Email VARCHAR(MAX) NOT NULL,
PRIMARY KEY (AdminId),
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE PARTICIPATES_IN (
TitleId INT NOT NULL,
ParticipantId INT NOT NULL,
IsActor BIT NOT NULL,
IsWriter BIT NOT NULL,
IsDirector BIT NOT NULL,
PRIMARY KEY (TitleId, ParticipantId),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId),
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE PLAYING_AT (
TitleId INT NOT NULL,
TheaterName VARCHAR(30) NOT NULL, 
TheaterCity VARCHAR(15),
PRIMARY KEY (TitleId, TheaterName),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId),
FOREIGN KEY (TheaterName) REFERENCES THEATER(Name),
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE STREAMING_FROM (
TitleId INT NOT NULL,
ServiceName VARCHAR(20) NOT NULL,
PRIMARY KEY (TitleId, ServiceName),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId),
FOREIGN KEY (ServiceName) REFERENCES STREAM_SERVICE(Name),
ON DELETE SET NULL ON UPDATE CASCADE);

CREATE TABLE ADDED_TO (
TitleId INT NOT NULL,
UserId INT NOT NULL,
ListNumber INT NOT NULL,
PRIMARY KEY (TitleId, UserId, ListNumber),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId),
FOREIGN KEY (UserId) REFERENCES USER(UserId),
FOREIGN KEY (ListNumber) REFERENCES USER_LIST(ListNumber),
ON DELETE SET NULL ON UPDATE CASCADE);





INSERT INTO SHOOT_LOCATION
    (`TitleId`, `City`, `StateAbv`, `Country`)
VALUES
    (),
    (),
    ();
	
INSERT INTO GENRE
    (`Name`, `Description`)
VALUES
    (),
    (),
    ();
	
INSERT INTO PARTICIPANT
    ('ParticipantId', 'FirstName', 'MiddleName', 'LastName')
VALUES
    (),
    (),
    ();
	
INSERT INTO TRAILER
    ('TrailerId','TitleId','Name','Description','Duration')
VALUES
    (),
    (),
    ();

INSERT INTO REVIEW
    ('ReviewId','TitleId','UserId','StarRating','Description')
VALUES
    (),
    (),
    ();
	
INSERT INTO THEATER
    ('Name','City','Street','StateAbv','Country','Zip')
VALUES
    (),
    (),
    ();
	
INSERT INTO STREAM_SERVICE
    ('Name','URL')
VALUES
    (),
    (),
    ();
	
INSERT INTO TITLE
    ('TitleId')
VALUES
    (),
    (),
    ();
	
INSERT INTO MOVIE
    ('TitleId','Country','AgeRating','Day','Month','Year')
VALUES
    (),
    (),
    ();
	
INSERT INTO TV_SHOW
    ('TitleId','Country')
VALUES
    (),
    (),
    ();
	
INSERT INTO SEASON
    ('TitleId','SeasonNumber')
VALUES
    (),
    (),
    ();
	
INSERT INTO EPISODE
    ('TitleId','SeasonNumber','EpisodeNumber','Name','Day','Month','Year','AgeRating','Duration')
VALUES
    (),
    (),
    ();
	
INSERT INTO USER_LIST
    ('UserId','ListNumber','Watched','ToWatch')
VALUES
    (),
    (),
    ();
	
INSERT INTO USER
    ('UserId','Email')
VALUES
    (),
    (),
    ();
	
INSERT INTO ADMIN
    ('AdminId','Email')
VALUES
    (),
    (),
    ();
	
INSERT INTO PARTICIPATES_IN
    ('TitleId','ParticipantId','IsActor','IsWriter','IsDirector')
VALUES
    (),
    (),
    ();
	
INSERT INTO PLAYING_AT
    ('TitleId','TheaterName','TheaterCity')
VALUES
    (),
    (),
    ();
	
INSERT INTO STREAMING_FROM
    ('TitleId','ServiceName')
VALUES
    (),
    (),
    ();
	
INSERT INTO ADDED_TO
    ('TitleId','UserId','ListNumber')
VALUES
    (),
    (),
    ();



