CREATE TABLE public.user (
	id SERIAL NOT NULL,
	uid VARCHAR(60) UNIQUE,
	firstname VARCHAR(45) NOT NULL,
	lastname VARCHAR(45) NOT NULL,
	phonenumber VARCHAR(45) NOT NULL,
	email VARCHAR(45) NOT NULL,
	password character varying(45) NOT NULL,
    confirmpassword character varying(45) NOT NULL,
	carnumber VARCHAR(45) NOT NULL,
	carname VARCHAR(45) NOT NULL,
	createdby VARCHAR(45) NULL,
	createdon TIMESTAMP NULL,
	enabled Boolean null,
	PRIMARY KEY (id)
);

CREATE TABLE public.address
(
    id SERIAL NOT NULL,
    type VARCHAR(15) NOT NULL,
    uid integer NOT NULL,
    name VARCHAR(60) NOT NULL,
    flatno VARCHAR(60) NOT NULL,
    street VARCHAR(60) NOT NULL,
    pincode VARCHAR(15) NOT NULL,
    city VARCHAR(15) NOT NULL,
    state VARCHAR(15) NOT NULL,
    country VARCHAR(15) NOT NULL,
    PRIMARY KEY (id)
);


ALTER TABLE IF EXISTS public.address
    ADD CONSTRAINT "User" FOREIGN KEY (uid)
    REFERENCES public."user" (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE;