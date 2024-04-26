-- This script was generated by the ERD tool in pgAdmin 4.
-- Please log an issue at https://redmine.postgresql.org/projects/pgadmin4/issues/new if you find any bugs, including reproduction steps.
BEGIN;


CREATE TABLE IF NOT EXISTS public.carta
(
    id_producto integer NOT NULL,
    n_carta integer,
    nombre character varying(50),
    categoria character varying(30),
    CONSTRAINT carta_pkey PRIMARY KEY (id_producto),
    CONSTRAINT carta_n_carta_key UNIQUE (n_carta) 
);

CREATE TABLE IF NOT EXISTS public.sobre
(
    id_producto integer NOT NULL,
    nombre character varying(50),
    cantidad_cartas integer,
    CONSTRAINT sobre_pkey PRIMARY KEY (id_producto)
);

CREATE TABLE IF NOT EXISTS public.catalogo
(
    id_producto integer NOT NULL,
    tipo_producto character varying(20),
	nombre character varying(50),
    precio double precision,
    CONSTRAINT catalogo_pkey PRIMARY KEY (id_producto),
	CONSTRAINT fk_id_producto_carta FOREIGN KEY(id_producto) REFERENCES carta(id_producto),
	CONSTRAINT fk_id_producto_sobre FOREIGN KEY(id_producto) REFERENCES sobre(id_producto)
);

CREATE TABLE IF NOT EXISTS public.luchador(
	n_carta integer PRIMARY KEY,
	fuerza int,
	resistencia int,
	velocidad int,
	carisma int
);


END;

ALTER TABLE carta ADD CONSTRAINT fk_n_carta FOREIGN KEY (n_carta) REFERENCES luchador(n_carta);

