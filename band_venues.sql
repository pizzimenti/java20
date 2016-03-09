--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.1
-- Dumped by pg_dump version 9.5.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: bands; Type: TABLE; Schema: public; Owner: brad
--

CREATE TABLE bands (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE bands OWNER TO brad;

--
-- Name: bands_id_seq; Type: SEQUENCE; Schema: public; Owner: brad
--

CREATE SEQUENCE bands_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bands_id_seq OWNER TO brad;

--
-- Name: bands_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: brad
--

ALTER SEQUENCE bands_id_seq OWNED BY bands.id;


--
-- Name: bands_venues; Type: TABLE; Schema: public; Owner: brad
--

CREATE TABLE bands_venues (
    id integer NOT NULL,
    band_id integer,
    venue_id integer
);


ALTER TABLE bands_venues OWNER TO brad;

--
-- Name: bands_venues_id_seq; Type: SEQUENCE; Schema: public; Owner: brad
--

CREATE SEQUENCE bands_venues_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bands_venues_id_seq OWNER TO brad;

--
-- Name: bands_venues_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: brad
--

ALTER SEQUENCE bands_venues_id_seq OWNED BY bands_venues.id;


--
-- Name: venues; Type: TABLE; Schema: public; Owner: brad
--

CREATE TABLE venues (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE venues OWNER TO brad;

--
-- Name: venues_id_seq; Type: SEQUENCE; Schema: public; Owner: brad
--

CREATE SEQUENCE venues_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE venues_id_seq OWNER TO brad;

--
-- Name: venues_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: brad
--

ALTER SEQUENCE venues_id_seq OWNED BY venues.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: brad
--

ALTER TABLE ONLY bands ALTER COLUMN id SET DEFAULT nextval('bands_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: brad
--

ALTER TABLE ONLY bands_venues ALTER COLUMN id SET DEFAULT nextval('bands_venues_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: brad
--

ALTER TABLE ONLY venues ALTER COLUMN id SET DEFAULT nextval('venues_id_seq'::regclass);


--
-- Data for Name: bands; Type: TABLE DATA; Schema: public; Owner: brad
--

COPY bands (id, name) FROM stdin;
89	The Who
91	Pink Martini
92	Iron and Wine
\.


--
-- Name: bands_id_seq; Type: SEQUENCE SET; Schema: public; Owner: brad
--

SELECT pg_catalog.setval('bands_id_seq', 92, true);


--
-- Data for Name: bands_venues; Type: TABLE DATA; Schema: public; Owner: brad
--

COPY bands_venues (id, band_id, venue_id) FROM stdin;
32	41	45
33	42	46
34	42	47
35	43	48
36	44	49
37	45	49
38	46	52
39	49	53
40	50	54
41	50	55
42	51	56
43	52	57
44	53	57
46	57	61
47	58	62
48	58	63
49	59	64
50	60	65
51	61	65
53	65	69
54	66	70
55	66	71
56	67	72
57	68	73
58	69	73
60	73	77
61	74	78
62	74	79
63	75	80
64	76	81
65	77	81
68	82	86
69	83	87
70	83	88
71	84	89
72	85	90
73	86	90
76	89	94
77	89	95
81	92	96
82	91	96
83	91	94
\.


--
-- Name: bands_venues_id_seq; Type: SEQUENCE SET; Schema: public; Owner: brad
--

SELECT pg_catalog.setval('bands_venues_id_seq', 83, true);


--
-- Data for Name: venues; Type: TABLE DATA; Schema: public; Owner: brad
--

COPY venues (id, name) FROM stdin;
94	Rose Quarter
95	Expo Center
96	Tiffany Center
\.


--
-- Name: venues_id_seq; Type: SEQUENCE SET; Schema: public; Owner: brad
--

SELECT pg_catalog.setval('venues_id_seq', 96, true);


--
-- Name: bands_pkey; Type: CONSTRAINT; Schema: public; Owner: brad
--

ALTER TABLE ONLY bands
    ADD CONSTRAINT bands_pkey PRIMARY KEY (id);


--
-- Name: bands_venues_pkey; Type: CONSTRAINT; Schema: public; Owner: brad
--

ALTER TABLE ONLY bands_venues
    ADD CONSTRAINT bands_venues_pkey PRIMARY KEY (id);


--
-- Name: venues_pkey; Type: CONSTRAINT; Schema: public; Owner: brad
--

ALTER TABLE ONLY venues
    ADD CONSTRAINT venues_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: brad
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM brad;
GRANT ALL ON SCHEMA public TO brad;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

