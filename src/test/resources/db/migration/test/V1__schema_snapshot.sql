CREATE TABLE person
(
    email character varying(255) NOT NULL,
    name  character varying(255)
);
ALTER TABLE ONLY person
    ADD CONSTRAINT person_pkey PRIMARY KEY (email);
