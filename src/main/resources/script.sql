CREATE TABLE appendix (
	appendix_id int4 NOT NULL,
	appendix_name varchar(255) NOT NULL,
	appendix_path varchar(255) NOT NULL,
	CONSTRAINT appendix_pk PRIMARY KEY (appendix_id),
	CONSTRAINT appendix_un UNIQUE (appendix_path)
);

CREATE SEQUENCE appendix_id_gen
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE word (
	word_id int4 NOT NULL,
	word_text varchar(255) NOT NULL,
	CONSTRAINT word_pk PRIMARY KEY (word_id),
	CONSTRAINT word_un UNIQUE (word_text)
);

CREATE SEQUENCE word_id_gen
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE repeat (
	repeat_id int4 NOT NULL,
	word_id int4 NOT NULL,
	appendix_id int4 NOT NULL,
	repeat_count int4 NOT NULL,
	CONSTRAINT repeat_pk PRIMARY KEY (repeat_id),
	CONSTRAINT repeat_un UNIQUE (word_id, appendix_id),
	CONSTRAINT repeat_fk FOREIGN KEY (appendix_id) REFERENCES dbo.appendix(appendix_id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT repeat_fk_1 FOREIGN KEY (word_id) REFERENCES dbo.word(word_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE SEQUENCE repeat_id_gen
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;