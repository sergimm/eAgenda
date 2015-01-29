-- Schema: infogroup2eagenda
-- DESCOMENTAR DROP A PARTIR DE LA SEGONA EXECUCIO
DROP SCHEMA "infogroup2eagenda" CASCADE;

CREATE SCHEMA "infogroup2eagenda"
  AUTHORIZATION "USER";
  
set search_path to infogroup2eagenda;

CREATE TABLE "superadministrator" (
    "id" serial,
    "nif" varchar(9) NOT NULL,
    "email" varchar(75) NOT NULL,
    "password" varchar(25) NOT NULL,
    "name" varchar(25) NOT NULL,
    CONSTRAINT "Superadministrator_pkey" PRIMARY KEY (id),
    UNIQUE (nif),
    UNIQUE (email)
);

CREATE TABLE "address" (
    "id" serial,
    "street" varchar(25) NOT NULL,
    "number" varchar(4) NOT NULL,
    "city" varchar(25) NOT NULL,
    "state" varchar(25) NOT NULL,
    "country" varchar(25) NOT NULL,
    "zip" varchar(5) NOT NULL,
    CONSTRAINT "Address_pkey" PRIMARY KEY (id)
); 

CREATE TABLE "users" (
    "id" serial,
    "nif" varchar(9) NOT NULL,
    "name" varchar(50) NOT NULL,
    "surname" varchar(50) NOT NULL,
    "preferedLanguage" varchar(25) NOT NULL,
    "password" varchar(25) NOT NULL,
    "email" varchar(50) NOT NULL,
    "address_id" integer NOT NULL REFERENCES "address" ("id") DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT "User_pkey" PRIMARY KEY (id),		
    UNIQUE (nif),
    UNIQUE (email)
);

CREATE TABLE "company" (
    "id" serial,
    "name" varchar(25) NOT NULL,
    CONSTRAINT "Company_pkey" PRIMARY KEY (id),
    "superadministrator_id" integer NOT NULL REFERENCES "superadministrator" ("id") DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE "category" (
    "id" serial,
    "name" varchar(25) NOT NULL,
    "description" varchar(200) NOT NULL,
    CONSTRAINT "Category_pkey" PRIMARY KEY (id),
    "superadministrator_id" integer NOT NULL REFERENCES "superadministrator" ("id") DEFERRABLE INITIALLY DEFERRED
);  

CREATE TABLE "event" (
    "id" serial,
    "name" varchar(25) NOT NULL,
    "description" varchar(2000) NOT NULL,
    "initDate" timestamp with time zone NOT NULL,
    "endDate" timestamp with time zone NOT NULL,
    "address_id" integer NOT NULL REFERENCES "address" ("id") DEFERRABLE INITIALLY DEFERRED,    
    "ratting" NUMERIC(3, 1) NOT NULL,
    "picture" varchar(255) NOT NULL, 
    "company_id" integer NOT NULL REFERENCES "company" ("id") DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT "Event_pkey" PRIMARY KEY (id)
);

CREATE TABLE "words" (
    "id" serial,
    "name" varchar(100) NOT NULL,
    "eventwords_id" integer NOT NULL REFERENCES "event" ("id"),  
    CONSTRAINT "Words_pkey" PRIMARY KEY (id)
); 

CREATE TABLE "eventcategory" (
    "idcategory" integer NOT NULL REFERENCES "category" ("id"),
    "idevent" integer NOT NULL REFERENCES "event" ("id")
);

CREATE TABLE "orders" (
    "id" serial,
    "data" timestamp with time zone NOT NULL,
    "numberoftickets" integer NOT NULL,
    "eventorder_id" integer NOT NULL REFERENCES "event" ("id"),
    "userorder_id" integer NOT NULL REFERENCES "users" ("id"),  
    CONSTRAINT "Order_pkey" PRIMARY KEY (id)
);

CREATE TABLE "suggestion" (
    "id" serial,
    "email" varchar(100) NOT NULL,
    "text" varchar(200) NOT NULL,
    "eventsuggestion_id" integer NOT NULL REFERENCES "event" ("id"),
    "usersuggestion_id" integer NOT NULL REFERENCES "users" ("id"),  
    CONSTRAINT "Suggestion_pkey" PRIMARY KEY (id)
);

CREATE TABLE "comment" (
    "id" serial,
    "text" varchar(500) NOT NULL,
    "eventcomment_id" integer NOT NULL REFERENCES "event" ("id"),
    "usercomment_id" integer NOT NULL REFERENCES "users" ("id"),  
    CONSTRAINT "Comment_pkey" PRIMARY KEY (id)
); 

CREATE TABLE "ratting" (
    "id" serial,
    "ratting" NUMERIC(2, 0) NOT NULL,
    "eventratting_id" integer NOT NULL REFERENCES "event" ("id"),
    "userratting_id" integer NOT NULL REFERENCES "users" ("id"),  
    CONSTRAINT "Ratting_pkey" PRIMARY KEY (id)
);  

CREATE TABLE "favorites" (
    "id" serial,
    "user_id" integer NOT NULL REFERENCES "users" ("id"),
    "event_id" integer NOT NULL REFERENCES "event" ("id"),  
    CONSTRAINT "Favorites_pkey" PRIMARY KEY (id)
);


-- ==========================================================
-- ================= TRIGGERS (for Ratting) =================
-- ==========================================================
CREATE FUNCTION update_ratting() RETURNS trigger AS $update_ratting$
    BEGIN
	--DEBUG:
	--RAISE NOTICE 'NEW.id(%)', NEW.eventratting_id;

	-- when an insert or update happens on the table Rattings, we need to update
	-- the average ratting on the event affected.
	UPDATE infogroup2eagenda.event
	SET ratting = (
		SELECT coalesce(AVG(r.ratting),0) FROM infogroup2eagenda.ratting r
		WHERE r.eventratting_id = NEW.eventratting_id		
	)
	WHERE id = NEW.eventratting_id;

        
        RETURN NEW;
    END;
$update_ratting$ LANGUAGE plpgsql;

CREATE TRIGGER update_ratting AFTER INSERT OR UPDATE ON ratting
    FOR EACH ROW EXECUTE PROCEDURE update_ratting();

-- ==========================================================
-- ======================== INSERTS ========================
-- ==========================================================
-- Superadministrador
insert into superadministrator (nif,email,password,name) values ('00000000A','superadmin1@gmail.com','secret','admin1');
insert into superadministrator (nif,email,password,name) values ('00000001B','superadmin2@gmail.com','secret','admin2');
-- Address
insert into address (street,number,city,state,country,zip) values ('Carrer del vent','4','Pollensa','Illes Balears','Espanya','07560');
insert into address (street,number,city,state,country,zip) values ('Carrer Balmes','49','Barcelona','Catalunya','Espanya','08007');
insert into address (street,number,city,state,country,zip) values ('Carrer serrano','8','Madrid','Madrid','Espanya','28001');
insert into address (street,number,city,state,country,zip) values ('Carrer Major','3','Pollensa','Illes Balears','Espanya','07560');
insert into address (street,number,city,state,country,zip) values ('Carrer Lorem ipsum','100','Pollensa','Illes Balears','Espanya','07560');
insert into address (street,number,city,state,country,zip) values ('Lorem ipsum 1','1','Sit amet','Illes Lorem','Espanya','07560');
insert into address (street,number,city,state,country,zip) values ('Lorem ipsum 2','2','Sit amet','Illes Lorem','Espanya','07560');
-- Users (Inserts temporals, no existeixen tots els camps)
insert into users (nif,email,password,name,surname,"preferedLanguage",address_id) values ('99999999Z','user1@gmail.com','1111','User1','Surname1','EN',6);
insert into users (nif,email,password,name,surname,"preferedLanguage",address_id) values ('88888888Z','user2@gmail.com','2222','User2','Surname2','EN',7);
-- Company
insert into company (name,superadministrator_id) values ('Company1','1');
insert into company (name,superadministrator_id) values ('Company2','1');
insert into company (name,superadministrator_id) values ('Company3','1');
insert into company (name,superadministrator_id) values ('Company4','2');
insert into company (name,superadministrator_id) values ('Company5','2');
insert into company (name,superadministrator_id) values ('Company6','2');
insert into company (name,superadministrator_id) values ('Company7','2');
-- Category
insert into category (name,description,superadministrator_id) values ('Category1','desc1','1');
insert into category (name,description,superadministrator_id) values ('Category2','desc2','1');
insert into category (name,description,superadministrator_id) values ('Category3','desc3','1');
insert into category (name,description,superadministrator_id) values ('Category4','desc4','2');
insert into category (name,description,superadministrator_id) values ('Category5','desc5','2');
insert into category (name,description,superadministrator_id) values ('Category6','desc6','2');
insert into category (name,description,superadministrator_id) values ('Category7','desc7','1');
-- Event
-- Deprecated comment. There is a trigger now :). Note: Rattings on Event 1, 2 and 3 are not 0 because we will add 3 user ratting, one for each event with this score as an example.
insert into event (name,description,"initDate","endDate",address_id,ratting,picture,company_id) values ('Event 1','Lorem ipsum sit amet',current_timestamp,current_timestamp,'1',0.0,'event_image1.jpg',1);
insert into event (name,description,"initDate","endDate",address_id,ratting,picture,company_id) values ('Event 2','Lorem ipsum sit amet',current_timestamp,current_timestamp,'2',0.0,'event_image2.jpg',2);
insert into event (name,description,"initDate","endDate",address_id,ratting,picture,company_id) values ('Event 3','Lorem ipsum sit amet',current_timestamp,current_timestamp,'3',0.0,'event_image3.jpg',3);
insert into event (name,description,"initDate","endDate",address_id,ratting,picture,company_id) values ('Event 4','Lorem ipsum sit amet',current_timestamp,current_timestamp,'4',0.0,'event_image4.jpg',4);
insert into event (name,description,"initDate","endDate",address_id,ratting,picture,company_id) values ('Event 5','Lorem ipsum sit amet',current_timestamp,current_timestamp,'1',0.0,'event_image5.jpg',5);
-- Words
insert into words (name,"eventwords_id") values ('Lorem',1);
insert into words (name,"eventwords_id") values ('Ipsum',1);
insert into words (name,"eventwords_id") values ('Sit',1);
insert into words (name,"eventwords_id") values ('Amet',1);
insert into words (name,"eventwords_id") values ('Lorem',2);
insert into words (name,"eventwords_id") values ('Sit',2);
insert into words (name,"eventwords_id") values ('Amet',2);
insert into words (name,"eventwords_id") values ('Lorem',3);
insert into words (name,"eventwords_id") values ('Ipsum',3);
insert into words (name,"eventwords_id") values ('Amet',3);
insert into words (name,"eventwords_id") values ('Lorem',4);
insert into words (name,"eventwords_id") values ('Ipsum',4);
insert into words (name,"eventwords_id") values ('Sit',4);
insert into words (name,"eventwords_id") values ('Ipsum',5);
insert into words (name,"eventwords_id") values ('Sit',5);
insert into words (name,"eventwords_id") values ('Amet',5);
-- Category & Event
insert into eventcategory (idcategory,idevent) values (1,1);
insert into eventcategory (idcategory,idevent) values (2,1);
insert into eventcategory (idcategory,idevent) values (3,1);
insert into eventcategory (idcategory,idevent) values (4,2);
insert into eventcategory (idcategory,idevent) values (5,2);
insert into eventcategory (idcategory,idevent) values (6,2);
insert into eventcategory (idcategory,idevent) values (7,3);
insert into eventcategory (idcategory,idevent) values (1,4);
insert into eventcategory (idcategory,idevent) values (3,4);
insert into eventcategory (idcategory,idevent) values (6,4);
insert into eventcategory (idcategory,idevent) values (1,5);
insert into eventcategory (idcategory,idevent) values (2,5);
insert into eventcategory (idcategory,idevent) values (7,5);

-- Comments (User&Event)
insert into comment (text,eventcomment_id,usercomment_id) values ('Event 1 is Lorem ipsum sit amet!!',1,1);
insert into comment (text,eventcomment_id,usercomment_id) values ('Event 2 is Lorem ipsum sit amet!!',2,1);
insert into comment (text,eventcomment_id,usercomment_id) values ('Event 3 is Lorem ipsum sit amet!!',3,1);
insert into comment (text,eventcomment_id,usercomment_id) values ('I think that event 1 is consectetur adipiscing elit.',1,2);
insert into comment (text,eventcomment_id,usercomment_id) values ('I think that event 4 is eu nibh ut lectus imperdiet tincidunt',4,2);
-- Rattings (User&Event)
insert into ratting (ratting,eventratting_id,userratting_id) values (7,1,1);
insert into ratting (ratting,eventratting_id,userratting_id) values (8,2,1);
insert into ratting (ratting,eventratting_id,userratting_id) values (9,3,1);
-- Orders
insert into orders (data,numberoftickets,eventorder_id,userorder_id)  values (current_timestamp,2,1,1);
insert into orders (data,numberoftickets,eventorder_id,userorder_id)  values (current_timestamp,1,2,1);
insert into orders (data,numberoftickets,eventorder_id,userorder_id)  values (current_timestamp,5,2,2);
insert into orders (data,numberoftickets,eventorder_id,userorder_id)  values (current_timestamp,3,3,2);
-- Suggestion
insert into suggestion (email,text,eventsuggestion_id,usersuggestion_id) values ('user1@gmail.com','I recommend you to see this event!!',1,2);
-- Favorites
insert into favorites (user_id, event_id) values (1,1);
