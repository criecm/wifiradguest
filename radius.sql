--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: delete_expire_account(); Type: FUNCTION; Schema: public; Owner: radius
--

CREATE FUNCTION delete_expire_account() RETURNS integer
    LANGUAGE plpgsql
    AS $$DECLARE 
rowcount int;
rawcount int;
tmp int;
c radadmin%ROWTYPE;
d radcheck%ROWTYPE;

BEGIN

FOR d IN
SELECT * from radcheck left join radadmin ON firstaccount = substring(username from 1 for length(firstaccount)) where expiration < now()
LOOP
RAISE LOG 'Compte(s) Supprimé(s) %',d.username;
END LOOP;

SELECT COUNT(*) INTO tmp username from radcheck left join radadmin ON firstaccount = substring(username from 1 for length(firstaccount)) where expiration < now();

RAISE LOG 'Total de Ligne(s) Supprimé(s) %',tmp;

IF ( tmp > 0) THEN
delete from radcheck where username IN 
(SELECT username from radcheck left join radadmin ON firstaccount = substring(username from 1 for length(firstaccount)) where expiration < now());
END IF;
 
FOR c IN 
SELECT * FROM radadmin WHERE expiration < NOW()
LOOP
RAISE LOG 'Ligne(s) radadmin supprimée(s) %',c.firstaccount;
END LOOP;

SELECT COUNT(*) INTO tmp FROM radadmin WHERE expiration < NOW();

RAISE LOG 'Nombre de Ligne(s) supprimée(s) %',tmp;

IF ( tmp > 0 ) THEN
DELETE  FROM radadmin WHERE expiration < NOW();
END IF;

RETURN tmp;
END;$$;


ALTER FUNCTION public.delete_expire_account() OWNER TO radius;

--
-- Name: set_expiration(); Type: FUNCTION; Schema: public; Owner: radius
--

CREATE FUNCTION set_expiration() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
tmp character varying(253);

BEGIN

IF ( NEW.username LIKE 'Guest%' or NEW.username LIKE 'ECM%') then

IF NOT ( select exists (select username from radacct where username= NEW.username )) then
select into tmp value from radcheck where ( username = NEW.username and attribute = 'Expire-After');
UPDATE radadmin set expiration = ( NEW.acctstarttime + cast (tmp AS interval) ) where firstaccount = substring (NEW.username from 1 for length(firstaccount));
END IF;

END IF;
RETURN NEW;
END;
$$;


ALTER FUNCTION public.set_expiration() OWNER TO radius;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: radacct; Type: TABLE; Schema: public; Owner: radius; Tablespace: 
--

CREATE TABLE radacct (
    radacctid bigint NOT NULL,
    acctsessionid character varying(64) NOT NULL,
    acctuniqueid character varying(32) NOT NULL,
    username character varying(253),
    groupname character varying(253),
    realm character varying(64),
    nasipaddress inet NOT NULL,
    nasportid character varying(15),
    nasporttype character varying(32),
    acctstarttime timestamp with time zone,
    acctstoptime timestamp with time zone,
    acctsessiontime bigint,
    acctauthentic character varying(32),
    connectinfo_start character varying(50),
    connectinfo_stop character varying(50),
    acctinputoctets bigint,
    acctoutputoctets bigint,
    calledstationid character varying(50),
    callingstationid character varying(50),
    acctterminatecause character varying(32),
    servicetype character varying(32),
    xascendsessionsvrkey character varying(10),
    framedprotocol character varying(32),
    framedipaddress inet,
    acctstartdelay integer,
    acctstopdelay integer
);


ALTER TABLE radacct OWNER TO radius;

--
-- Name: radacct_radacctid_seq; Type: SEQUENCE; Schema: public; Owner: radius
--

CREATE SEQUENCE radacct_radacctid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE radacct_radacctid_seq OWNER TO radius;

--
-- Name: radacct_radacctid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: radius
--

ALTER SEQUENCE radacct_radacctid_seq OWNED BY radacct.radacctid;


--
-- Name: radadmin; Type: TABLE; Schema: public; Owner: radius; Tablespace: 
--

CREATE TABLE radadmin (
    id integer NOT NULL,
    evenement character varying(255) DEFAULT ''::character varying NOT NULL,
    nbcompte integer DEFAULT 0 NOT NULL,
    firstaccount character varying(64) DEFAULT ''::character varying NOT NULL,
    expiration timestamp without time zone,
    createur character varying(255) DEFAULT ''::character varying NOT NULL,
    status boolean,
    refevnt character(1),
    firstlogin timestamp without time zone,
    infos character varying(255) DEFAULT ''::character varying NOT NULL
);


ALTER TABLE radadmin OWNER TO radius;

--
-- Name: radadmin_id_seq; Type: SEQUENCE; Schema: public; Owner: radius
--

CREATE SEQUENCE radadmin_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE radadmin_id_seq OWNER TO radius;

--
-- Name: radadmin_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: radius
--

ALTER SEQUENCE radadmin_id_seq OWNED BY radadmin.id;


--
-- Name: radcheck; Type: TABLE; Schema: public; Owner: radius; Tablespace: 
--

CREATE TABLE radcheck (
    id integer NOT NULL,
    username character varying(64) DEFAULT ''::character varying NOT NULL,
    attribute character varying(64) DEFAULT ''::character varying NOT NULL,
    op character(2) DEFAULT '=='::bpchar NOT NULL,
    value character varying(253) DEFAULT ''::character varying NOT NULL
);


ALTER TABLE radcheck OWNER TO radius;

--
-- Name: radcheck_id_seq; Type: SEQUENCE; Schema: public; Owner: radius
--

CREATE SEQUENCE radcheck_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE radcheck_id_seq OWNER TO radius;

--
-- Name: radcheck_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: radius
--

ALTER SEQUENCE radcheck_id_seq OWNED BY radcheck.id;


--
-- Name: radgroupcheck; Type: TABLE; Schema: public; Owner: radius; Tablespace: 
--

CREATE TABLE radgroupcheck (
    id integer NOT NULL,
    groupname character varying(64) DEFAULT ''::character varying NOT NULL,
    attribute character varying(64) DEFAULT ''::character varying NOT NULL,
    op character(2) DEFAULT '=='::bpchar NOT NULL,
    value character varying(253) DEFAULT ''::character varying NOT NULL
);


ALTER TABLE radgroupcheck OWNER TO radius;

--
-- Name: radgroupcheck_id_seq; Type: SEQUENCE; Schema: public; Owner: radius
--

CREATE SEQUENCE radgroupcheck_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE radgroupcheck_id_seq OWNER TO radius;

--
-- Name: radgroupcheck_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: radius
--

ALTER SEQUENCE radgroupcheck_id_seq OWNED BY radgroupcheck.id;


--
-- Name: radgroupreply; Type: TABLE; Schema: public; Owner: radius; Tablespace: 
--

CREATE TABLE radgroupreply (
    id integer NOT NULL,
    groupname character varying(64) DEFAULT ''::character varying NOT NULL,
    attribute character varying(64) DEFAULT ''::character varying NOT NULL,
    op character(2) DEFAULT '='::bpchar NOT NULL,
    value character varying(253) DEFAULT ''::character varying NOT NULL
);


ALTER TABLE radgroupreply OWNER TO radius;

--
-- Name: radgroupreply_id_seq; Type: SEQUENCE; Schema: public; Owner: radius
--

CREATE SEQUENCE radgroupreply_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE radgroupreply_id_seq OWNER TO radius;

--
-- Name: radgroupreply_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: radius
--

ALTER SEQUENCE radgroupreply_id_seq OWNED BY radgroupreply.id;


--
-- Name: radpostauth; Type: TABLE; Schema: public; Owner: radius; Tablespace: 
--

CREATE TABLE radpostauth (
    id bigint NOT NULL,
    username character varying(253) NOT NULL,
    pass character varying(128),
    reply character varying(32),
    calledstationid character varying(50),
    callingstationid character varying(50),
    authdate timestamp with time zone DEFAULT '2014-02-03 12:03:24.060919+01'::timestamp with time zone NOT NULL
);


ALTER TABLE radpostauth OWNER TO radius;

--
-- Name: radpostauth_id_seq; Type: SEQUENCE; Schema: public; Owner: radius
--

CREATE SEQUENCE radpostauth_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE radpostauth_id_seq OWNER TO radius;

--
-- Name: radpostauth_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: radius
--

ALTER SEQUENCE radpostauth_id_seq OWNED BY radpostauth.id;


--
-- Name: radreply; Type: TABLE; Schema: public; Owner: radius; Tablespace: 
--

CREATE TABLE radreply (
    id integer NOT NULL,
    username character varying(64) DEFAULT ''::character varying NOT NULL,
    attribute character varying(64) DEFAULT ''::character varying NOT NULL,
    op character(2) DEFAULT '='::bpchar NOT NULL,
    value character varying(253) DEFAULT ''::character varying NOT NULL
);


ALTER TABLE radreply OWNER TO radius;

--
-- Name: radreply_id_seq; Type: SEQUENCE; Schema: public; Owner: radius
--

CREATE SEQUENCE radreply_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE radreply_id_seq OWNER TO radius;

--
-- Name: radreply_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: radius
--

ALTER SEQUENCE radreply_id_seq OWNED BY radreply.id;


--
-- Name: radusergroup; Type: TABLE; Schema: public; Owner: radius; Tablespace: 
--

CREATE TABLE radusergroup (
    username character varying(64) DEFAULT ''::character varying NOT NULL,
    groupname character varying(64) DEFAULT ''::character varying NOT NULL,
    priority integer DEFAULT 0 NOT NULL
);


ALTER TABLE radusergroup OWNER TO radius;

--
-- Name: radacctid; Type: DEFAULT; Schema: public; Owner: radius
--

ALTER TABLE ONLY radacct ALTER COLUMN radacctid SET DEFAULT nextval('radacct_radacctid_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: radius
--

ALTER TABLE ONLY radadmin ALTER COLUMN id SET DEFAULT nextval('radadmin_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: radius
--

ALTER TABLE ONLY radcheck ALTER COLUMN id SET DEFAULT nextval('radcheck_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: radius
--

ALTER TABLE ONLY radgroupcheck ALTER COLUMN id SET DEFAULT nextval('radgroupcheck_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: radius
--

ALTER TABLE ONLY radgroupreply ALTER COLUMN id SET DEFAULT nextval('radgroupreply_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: radius
--

ALTER TABLE ONLY radpostauth ALTER COLUMN id SET DEFAULT nextval('radpostauth_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: radius
--

ALTER TABLE ONLY radreply ALTER COLUMN id SET DEFAULT nextval('radreply_id_seq'::regclass);


--
-- Name: radacct_acctuniqueid_key; Type: CONSTRAINT; Schema: public; Owner: radius; Tablespace: 
--

ALTER TABLE ONLY radacct
    ADD CONSTRAINT radacct_acctuniqueid_key UNIQUE (acctuniqueid);


--
-- Name: radacct_pkey; Type: CONSTRAINT; Schema: public; Owner: radius; Tablespace: 
--

ALTER TABLE ONLY radacct
    ADD CONSTRAINT radacct_pkey PRIMARY KEY (radacctid);


--
-- Name: radadmin_pkey; Type: CONSTRAINT; Schema: public; Owner: radius; Tablespace: 
--

ALTER TABLE ONLY radadmin
    ADD CONSTRAINT radadmin_pkey PRIMARY KEY (id);


--
-- Name: radcheck_pkey; Type: CONSTRAINT; Schema: public; Owner: radius; Tablespace: 
--

ALTER TABLE ONLY radcheck
    ADD CONSTRAINT radcheck_pkey PRIMARY KEY (id);


--
-- Name: radgroupcheck_pkey; Type: CONSTRAINT; Schema: public; Owner: radius; Tablespace: 
--

ALTER TABLE ONLY radgroupcheck
    ADD CONSTRAINT radgroupcheck_pkey PRIMARY KEY (id);


--
-- Name: radgroupreply_pkey; Type: CONSTRAINT; Schema: public; Owner: radius; Tablespace: 
--

ALTER TABLE ONLY radgroupreply
    ADD CONSTRAINT radgroupreply_pkey PRIMARY KEY (id);


--
-- Name: radpostauth_pkey; Type: CONSTRAINT; Schema: public; Owner: radius; Tablespace: 
--

ALTER TABLE ONLY radpostauth
    ADD CONSTRAINT radpostauth_pkey PRIMARY KEY (id);


--
-- Name: radreply_pkey; Type: CONSTRAINT; Schema: public; Owner: radius; Tablespace: 
--

ALTER TABLE ONLY radreply
    ADD CONSTRAINT radreply_pkey PRIMARY KEY (id);


--
-- Name: radacct_active_user_idx; Type: INDEX; Schema: public; Owner: radius; Tablespace: 
--

CREATE INDEX radacct_active_user_idx ON radacct USING btree (username, nasipaddress, acctsessionid) WHERE (acctstoptime IS NULL);


--
-- Name: radacct_start_user_idx; Type: INDEX; Schema: public; Owner: radius; Tablespace: 
--

CREATE INDEX radacct_start_user_idx ON radacct USING btree (acctstarttime, username);


--
-- Name: radcheck_username; Type: INDEX; Schema: public; Owner: radius; Tablespace: 
--

CREATE INDEX radcheck_username ON radcheck USING btree (username, attribute);


--
-- Name: radgroupcheck_groupname; Type: INDEX; Schema: public; Owner: radius; Tablespace: 
--

CREATE INDEX radgroupcheck_groupname ON radgroupcheck USING btree (groupname, attribute);


--
-- Name: radgroupreply_groupname; Type: INDEX; Schema: public; Owner: radius; Tablespace: 
--

CREATE INDEX radgroupreply_groupname ON radgroupreply USING btree (groupname, attribute);


--
-- Name: radreply_username; Type: INDEX; Schema: public; Owner: radius; Tablespace: 
--

CREATE INDEX radreply_username ON radreply USING btree (username, attribute);


--
-- Name: radusergroup_username; Type: INDEX; Schema: public; Owner: radius; Tablespace: 
--

CREATE INDEX radusergroup_username ON radusergroup USING btree (username);


--
-- Name: detect_insert_radacct; Type: TRIGGER; Schema: public; Owner: radius
--

CREATE TRIGGER detect_insert_radacct BEFORE INSERT ON radacct FOR EACH ROW EXECUTE PROCEDURE set_expiration();


--
-- Name: public; Type: ACL; Schema: -; Owner: pgcri
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM pgcri;
GRANT ALL ON SCHEMA public TO pgcri;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

