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

https://www.youtube.com/watch?v=9y3jmTpeZ4c  

Team Member Contributions:
Daniel Hajjar – ER Model, Relational Diagram, Most database programming, Testing 
Christopher Toeller – ER Model, Sequel Statements, Some database programming, Testing
John Campbell – Relational Diagram Connections, Videos, ReadMe File Instructions

Create and Insert Statements:

CREATE TABLE TITLE (
TitleId INT NOT NULL,
Name VARCHAR(30) NOT NULL,
PRIMARY KEY (TitleId));

CREATE TABLE USER (
UserId INT NOT NULL,
Email VARCHAR(100) NOT NULL,
PRIMARY KEY (UserId));

CREATE TABLE SHOOT_LOCATION (
TitleId INT NOT NULL,
City VARCHAR(15) NOT NULL, 
State VARCHAR(15) NOT NULL,
Country CHAR(9) NOT NULL,
PRIMARY KEY (TitleId, City, State, Country),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId));

CREATE TABLE GENRE (
Name VARCHAR(15) NOT NULL,
Description VARCHAR(600) NOT NULL, 
PRIMARY KEY (Name));

CREATE TABLE PARTICIPANT (
ParticipantId INT NOT NULL,
FirstName VARCHAR(15), 
MiddleName VARCHAR(15),
LastName VARCHAR(15),
PRIMARY KEY (ParticipantId));

CREATE TABLE TRAILER (
TrailerId INT NOT NULL,
TitleId INT NOT NULL,
Name VARCHAR(50), 
Description VARCHAR(600),
Duration DECIMAL(4,2),
PRIMARY KEY (TrailerId),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId));

CREATE TABLE REVIEW (
TitleId INT NOT NULL, 
UserId INT NOT NULL,
StarRating INT NOT NULL,
Description VARCHAR(600),
PRIMARY KEY (TitleId, UserId),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId),
FOREIGN KEY (UserId) REFERENCES USER(UserId));

CREATE TABLE THEATER (
Name VARCHAR(30) NOT NULL, 
City VARCHAR(15),
Street VARCHAR(15),
State VARCHAR(15),
Country VARCHAR(15),
Zip INT NOT NULL,
PRIMARY KEY (Name, Zip));

CREATE TABLE STREAM_SERVICE (
Name VARCHAR(20) NOT NULL,
URL VARCHAR(600) NOT NULL,
PRIMARY KEY (Name));



CREATE TABLE MOVIE (
TitleId INT NOT NULL,
Country VARCHAR(15), 
AgeRating VARCHAR(5),
Day INT,
Month INT,
Year INT,
Duration DECIMAL(6,2),
PRIMARY KEY (TitleId),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId));

CREATE TABLE TV_SHOW (
TitleId INT NOT NULL,
Country VARCHAR(15),
PRIMARY KEY (TitleId),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId));

CREATE TABLE SEASON (
TitleId INT NOT NULL,
SeasonNumber INT NOT NULL,
PRIMARY KEY (TitleId, SeasonNumber),
FOREIGN KEY (TitleId) REFERENCES TV_SHOW(TitleId));

CREATE TABLE EPISODE (
TitleId INT NOT NULL,
SeasonNumber INT NOT NULL,
EpisodeNumber INT NOT NULL,
Name VARCHAR(100),
Day INT,
Month INT,
Year INT,
AgeRating VARCHAR(5),
Duration DECIMAL(6,2),
PRIMARY KEY (TitleId, SeasonNumber, EpisodeNumber),
FOREIGN KEY (TitleId, SeasonNumber) REFERENCES SEASON(TitleId, SeasonNumber));

CREATE TABLE USER_LIST (
UserId INT NOT NULL,
ListNumber INT NOT NULL,
Watched BIT NOT NULL,
ToWatch BIT NOT NULL,
PRIMARY KEY (UserId, ListNumber),
FOREIGN KEY (UserId) REFERENCES USER(UserId));



CREATE TABLE ADMIN (
AdminId INT NOT NULL,
Email VARCHAR(100) NOT NULL,
PRIMARY KEY (AdminId));

CREATE TABLE PARTICIPATES_IN (
TitleId INT NOT NULL,
ParticipantId INT NOT NULL,
IsActor BIT NOT NULL,
IsWriter BIT NOT NULL,
IsDirector BIT NOT NULL,
PRIMARY KEY (TitleId, ParticipantId),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId),
FOREIGN KEY (ParticipantId) REFERENCES PARTICIPANT(ParticipantId));

CREATE TABLE PLAYING_AT (
TitleId INT NOT NULL,
TheaterName VARCHAR(30) NOT NULL, 
TheaterZip INT NOT NULL,
PRIMARY KEY (TitleId, TheaterName, TheaterZip),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId),
FOREIGN KEY (TheaterName, TheaterZip) REFERENCES THEATER(name, Zip));


CREATE TABLE STREAMING_FROM (
TitleId INT NOT NULL,
ServiceName VARCHAR(20) NOT NULL,
PRIMARY KEY (TitleId, ServiceName),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId),
FOREIGN KEY (ServiceName) REFERENCES STREAM_SERVICE(Name));

CREATE TABLE ADDED_TO (
TitleId INT NOT NULL,
UserId INT NOT NULL,
ListNumber INT NOT NULL,
PRIMARY KEY (TitleId, UserId, ListNumber),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId),
FOREIGN KEY (UserId, ListNumber) REFERENCES USER_LIST(UserId, ListNumber));

CREATE TABLE BELONGS_TO (
TitleId INT NOT NULL,
GenreName VARCHAR(15) NOT NULL,
PRIMARY KEY (TitleId, GenreName),
FOREIGN KEY (TitleId) REFERENCES TITLE(TitleId),
FOREIGN KEY (GenreName) REFERENCES GENRE(Name));


INSERT INTO TITLE
    (`TitleId`, `Name`)
VALUES
    (123, 'The Seven Samurai'),
    (234, 'Bonnie and Clyde'),
    (345, 'Reservoir Dogs'),
	(456, 'The Simpsons'),
	(567, 'Cheers'),
	(678, '30 Rock');

INSERT INTO USER
    (`UserId`,`Email`)
VALUES
    (1, 'user1@email.com'),
    (2, 'user2@email.com'),
    (3, 'user3@email.com'),
	(4, 'user4@email.com');

INSERT INTO SHOOT_LOCATION
    (`TitleId`, `City`, `State`, `Country`)
VALUES
    (123, 'Tokyo', 'Kanto', 'Japan'),
    (234, 'Dallas', 'Texas', 'USA'),
    (234, 'Rowlett', 'Texas', 'USA'),
	(345, 'Los Angeles', 'California', 'USA'),
	(567, 'Boston', 'Massachusetts', 'USA'),
	(678, 'Manhattan', 'New York', 'USA');
	
INSERT INTO GENRE
    (`Name`, `Description`)
VALUES
    ('Action', 'Contains events that typically include violence, extended fighting, physical feats and frantic chases'),
    ('Adventure', 'Stories where the protagonist goes on an epic journey, either personally or geographically'),
    ('Biography', 'Portraying the experiences of events occurring in the life of a person, mostly in a chronological order'),
	('Crime', 'Genre of fiction that deals with crimes, their detection, criminals, and their motives'),
	('Drama', 'Genre that relies on the emotional and relational development of realistic characters'),
	('Thriller', 'Genre that characterized and defined by the moods they elicit, giving viewers heightened feelings of suspense, excitement, surprise, anticipation and anxiety'),
	('Animation', 'Includes movies in which individual drawings, paintings, or illustrations are photographed frame by frame'),
	('Comedy', 'Genre that is intended to incite laughter and amusement');
	
INSERT INTO PARTICIPANT
	(`ParticipantId`, `FirstName`,`MiddleName`,`LastName`)
VALUES
    (1, 'Akira', NULL, 'Kurosawa'),
    (2, 'Toshiro', NULL, 'Mifune'),
    (3, 'Takashi', NULL, 'Shimura'),
	(4, 'Arthur', NULL, 'Penn'),
	(5, 'David', NULL, 'Newman'),
	(6, 'Robert', NULL, 'Benton'),
	(7, 'Warren', NULL, 'Beatty'),
	(8, 'Faye', NULL, 'Dunaway'),
	(9, 'Quentin', NULL, 'Tarantino'),
	(10, 'Harvey', NULL, 'Keitel'),
	(11, 'Tim', NULL, 'Roth'),
	(12, 'James', 'L.', 'Brooks'),
	(13, 'Matt', NULL, 'Groening'),
	(14, 'Dan', NULL, 'Castellaneta'),
	(15, 'James', NULL, 'Burrows'),
	(16, 'Glen', NULL, 'Charles'),
	(17, 'Ted', NULL, 'Danson'),
	(18, 'Rhea', NULL, 'Perlman'),
	(19, 'Tina', NULL, 'Fey'),
	(20, 'Alec', NULL, 'Baldwin'),
	(21, 'Tracy', NULL, 'Morgan');
	
INSERT INTO TRAILER
    (`TrailerId`,`TitleId`,`Name`,`Description`,`Duration`)
VALUES
    (1, 123, 'The Seven Samurai Preview', 'Preview Clip', 2),
    (2, 234, 'Bonnie and Clyde Trailer', 'Preview Clip', 3),
    (3, 345, 'Reservoir Dogs Trailer', 'Preview Clip', 2),
	(4, 345, 'Reservoir Dogs Extended Preview', 'Preview Clip', 4),
	(5, 456, 'The Simpsons Trailer', 'Preview Video', 3),
	(6, 567, 'Cheers Preview', 'Preview Clip', 3),
	(7, 678, '30 Rock - Season 1 Trailer', 'Season Preview', 3);

INSERT INTO REVIEW
    (`TitleId`,`UserId`,`StarRating`,`Description`)
VALUES
    (234, 1, 5, 'This one is a must watch classic.'),
    (456, 3, 4, 'Amuzing show to watch when there is nothing else on TV.'),
    (567, 4, 5, 'Great show!'),
	(678, 2, 3, 'Sometimes funny, but most of the jokes go way over my head.');
	
INSERT INTO THEATER
    (`Name`,`City`,`Street`,`State`,`Country`,`Zip`)
VALUES
    ('Edwards Cinema', 'Alhambra', 'Main Street', 'CA', 'USA', 91105),
    ('The Big Screen', 'Springfield', 'Mulberry Lane', 'MO', 'USA', 52366),
    ('AMC', 'Orlando', 'Dolphin Street', 'FL', 'USA', 32789);
	
INSERT INTO STREAM_SERVICE
    (`Name`,`URL`)
VALUES
    ('Netflix', 'http://www.netflix.com'),
    ('Amazon Prime', 'http://www.amazon.com/prime'),
    ('Hulu', 'http://www.hulu.com');
	

	
INSERT INTO MOVIE
    (`TitleId`,`Country`,`AgeRating`,`Day`,`Month`,`Year`,`Duration`)
VALUES
    (123, 'Japan', 'NR', 26, 4, 1954, 207),
    (234, 'USA', 'R', 14, 8, 1967, 111),
    (345, 'USA', 'R', 2, 9, 1992, 99);
	
INSERT INTO TV_SHOW
    (`TitleId`,`Country`)
VALUES
    (456, 'USA'),
    (567, 'USA'),
    (678, 'USA');
	
INSERT INTO SEASON
    (`TitleId`,`SeasonNumber`)
VALUES
    (456,1),
    (456,2),
    (456,3),
	(567,1),
	(567,2),
	(678,1),
	(678,5);
	
INSERT INTO EPISODE
    (`TitleId`,`SeasonNumber`,`EpisodeNumber`,`Name`,`Day`,`Month`,`Year`,`AgeRating`,`Duration`)
VALUES
    (456, 1, 1, 'Simpsons Roast', 17, 12, 1989, 'TV-PG', 21),
    (456, 1, 2, 'Bart the Genius', 14, 1, 1990, 'TV-PG', 21),
    (456, 1, 3, 'Homer\'s Odyssey', 21, 1, 1990, 'TV-PG', 21),
	(456, 2, 17, 'Old Money', 28, 3, 1991, 'TV-PG', 21),
	(456, 2, 12, 'The Way We Was', 31, 1, 1991, 'TV-PG', 21),
	(456, 3, 14, 'Lisa the Greek', 23, 1, 1992, 'TV-PG', 21),
	(456, 3, 19, 'Dog of Death', 12, 3, 1992, 'TV-PG', 21),
	(456, 3, 22, 'The Otto Show', 23, 4, 1992, 'TV-PG', 21),
	(567, 1, 11, 'One for the Book', 9, 12, 1982, 'TV-PG', 22),
	(567, 1, 12, 'The Spy Who Came in for a Cold One', 16, 12, 1982, 'TV-PG', 22),
	(567, 1, 13, 'Now Pitching, Sam Malone', 6, 1, 1983, 'TV-PG', 22),
	(567, 2, 19, 'Coach buries a Grudge', 16, 2, 1984, 'TV-PG', 22),
	(567, 2, 7, 'Old Flames', 17, 11, 1983, 'TV-PG', 22),
	(678, 1, 10, 'The Rurual Juror', 11, 1, 2007, 'TV-14', 22),
	(678, 1, 11, 'The Head and the Hair', 18, 1, 2007, 'TV-14', 22),
	(678, 5, 8, 'College', 18, 11, 2010, 'TV-14', 22),
	(678, 5, 9, 'Chain Reaction', 2, 12, 2010, 'TV-14', 22);
	
	
INSERT INTO USER_LIST
    (`UserId`,`ListNumber`,`Watched`,`ToWatch`)
VALUES
    (1, 1, 1, 0),
	(1, 2, 0, 1),
	(2, 1, 1, 0),
    (2, 2, 0, 1),
    (3, 1, 1, 0),
	(3, 2, 0, 1),
	(4, 1, 1, 0),
	(4, 2, 0, 1);
	

	
INSERT INTO ADMIN
    (`AdminId`,`Email`)
VALUES
    (1, 'admin1@email.com'),
    (2, 'admin2@email.com'),
    (3, 'admin3@email.com');
	
INSERT INTO PARTICIPATES_IN
    (`TitleId`,`ParticipantId`,`IsActor`,`IsWriter`,`IsDirector`)
VALUES
    (123, 1, 0, 1, 1),
    (123, 2, 1, 0, 0),
    (123, 3, 1, 0, 0),
	(234, 4, 0, 0, 1),
	(234, 5, 0, 1, 0),
	(234, 6, 0, 1, 0),
	(234, 7, 1, 0, 0),
	(234, 8, 1, 0, 0),
	(345, 9, 1, 1, 0),
	(345, 10, 0, 0, 1),
	(345, 11, 0, 0, 1),
	(456, 12, 0, 1, 0),
	(456, 13, 0, 1, 0),
	(456, 14, 0, 0, 1),
	(567, 15, 0, 1, 0),
	(567, 16, 0, 1, 0),
	(567, 17, 0, 0, 1),
	(567, 18, 0, 0, 1),
	(678, 19, 0, 1, 1),
	(678, 20, 0, 0, 1),
	(678, 21, 0, 0, 1);
	
INSERT INTO PLAYING_AT
    (`TitleId`,`TheaterName`,`TheaterZip`)
VALUES
    (123, 'Edwards Cinema', 91105),
    (234, 'The Big Screen', 52366),
    (345, 'AMC', 32789);
	
INSERT INTO STREAMING_FROM
    (`TitleId`,`ServiceName`)
VALUES
    (123, 'Netflix'),
    (234, 'Amazon Prime'),
    (234, 'Netflix'),
	(345, 'Hulu'),
	(456, 'Netflix'),
	(456, 'Hulu'),
	(567, 'Netflix'),
	(567, 'Amazon Prime'),
	(678, 'Hulu');
	
INSERT INTO ADDED_TO
    (`TitleId`,`UserId`,`ListNumber`)
VALUES
    (123, 2, 1),
    (567, 4, 1),
    (678, 3, 1),
	(234, 3, 2),
	(456, 1, 2);
	
INSERT INTO BELONGS_TO
    (`TitleId`,`GenreName`)
VALUES
    (123, 'Action'),
    (123, 'Adventure'),
    (234, 'Action'),
	(234, 'Biography'),
	(345, 'Crime'),
	(345, 'Drama'),
	(345, 'Thriller'),
	(456, 'Animation'),
	(456, 'Comedy'),
	(567, 'Comedy'),
	(567, 'Drama'),
	(678, 'Comedy');

