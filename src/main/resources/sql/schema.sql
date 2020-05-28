--
-- PostgreSQL database dump
--

-- Dumped from database version 10.11
-- Dumped by pg_dump version 10.11

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE wequan; Type: COMMENT; Schema: -; Owner: wequan_admin
--

COMMENT ON DATABASE wequan IS 'this is the database for wequan app';


--
-- Name: bu; Type: SCHEMA; Schema: -; Owner: wequan_admin
--

CREATE SCHEMA bu;


ALTER SCHEMA bu OWNER TO wequan_admin;

--
-- Name: SCHEMA bu; Type: COMMENT; Schema: -; Owner: wequan_admin
--

COMMENT ON SCHEMA bu IS 'this is the schema for wequan app';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: wq_apply_tutor_record; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_apply_tutor_record (
    id integer NOT NULL,
    user_id integer NOT NULL,
    brief_introduction text NOT NULL,
    resume_path character varying(200) NOT NULL,
    transcript_path character varying(200) NOT NULL,
    other_proof_path character varying(200),
    current_school_id smallint NOT NULL,
    current_degree_id smallint NOT NULL,
    current_location character varying(100) NOT NULL,
    apply_tutor_courses character varying(200) NOT NULL,
    create_time timestamp without time zone NOT NULL,
    update_time timestamp without time zone,
    admin_action smallint,
    admin_action_time timestamp without time zone,
    status smallint DEFAULT 0 NOT NULL
);


ALTER TABLE bu.wq_apply_tutor_record OWNER TO quanzi_admin;

--
-- Name: COLUMN wq_apply_tutor_record.apply_tutor_courses; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON COLUMN bu.wq_apply_tutor_record.apply_tutor_courses IS '形式：1,2,3';


--
-- Name: COLUMN wq_apply_tutor_record.admin_action; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON COLUMN bu.wq_apply_tutor_record.admin_action IS '1, approve; -1, reject';


--
-- Name: COLUMN wq_apply_tutor_record.status; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON COLUMN bu.wq_apply_tutor_record.status IS '0, default with apply; 1, complete(admin approve or reject); -1, delete (user delete)';


--
-- Name: wq_apply_tutor_record_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_apply_tutor_record_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_apply_tutor_record_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_apply_tutor_record_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_apply_tutor_record_id_seq OWNED BY bu.wq_apply_tutor_record.id;


--
-- Name: wq_appointment; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_appointment (
    id integer NOT NULL,
    organizer integer NOT NULL,
    subject character varying(200) NOT NULL,
    participant integer NOT NULL,
    create_time timestamp without time zone NOT NULL,
    update_time timestamp without time zone,
    status smallint NOT NULL
);


ALTER TABLE bu.wq_appointment OWNER TO quanzi_admin;

--
-- Name: wq_appointment_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_appointment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_appointment_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_appointment_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_appointment_id_seq OWNED BY bu.wq_appointment.id;


--
-- Name: wq_appointment_rate; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_appointment_rate (
    id integer NOT NULL,
    appointment_id integer NOT NULL,
    user_id integer NOT NULL,
    score real NOT NULL,
    evaluation character varying(300) NOT NULL
);


ALTER TABLE bu.wq_appointment_rate OWNER TO quanzi_admin;

--
-- Name: wq_appointment_rate_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_appointment_rate_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_appointment_rate_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_appointment_rate_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_appointment_rate_id_seq OWNED BY bu.wq_appointment_rate.id;


--
-- Name: wq_cancellation_policy; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_cancellation_policy (
    id smallint NOT NULL,
    label character varying(20) NOT NULL,
    description character varying(300),
    refund_ratio real DEFAULT 0 NOT NULL
);


ALTER TABLE bu.wq_cancellation_policy OWNER TO quanzi_admin;

--
-- Name: wq_cancellation_policy_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_cancellation_policy_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_cancellation_policy_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_cancellation_policy_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_cancellation_policy_id_seq OWNED BY bu.wq_cancellation_policy.id;


--
-- Name: wq_course; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_course (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    brief_description character varying(500) NOT NULL,
    school_id smallint NOT NULL,
    department_id integer NOT NULL,
    code_first character varying(5) NOT NULL,
    code_second character varying(10) NOT NULL
);


ALTER TABLE bu.wq_course OWNER TO quanzi_admin;

--
-- Name: wq_course_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_course_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_course_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_course_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_course_id_seq OWNED BY bu.wq_course.id;


--
-- Name: wq_course_material; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_course_material (
    id integer NOT NULL,
    type smallint NOT NULL,
    store_directory character varying(200) NOT NULL,
    file_name character varying(100) NOT NULL,
    source_from text NOT NULL,
    subject_id integer NOT NULL,
    course_id integer NOT NULL,
    professor_id integer NOT NULL,
    study_points_required smallint DEFAULT 0 NOT NULL,
    term character varying(30) NOT NULL,
    file_type character varying(10) NOT NULL,
    upload_by integer NOT NULL,
    upload_time timestamp without time zone NOT NULL
);


ALTER TABLE bu.wq_course_material OWNER TO quanzi_admin;

--
-- Name: COLUMN wq_course_material.type; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON COLUMN bu.wq_course_material.type IS '1, homework; 2, quiz; 3, exam; 4, notes; 5, syllabus; 6, others';


--
-- Name: wq_course_material_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_course_material_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_course_material_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_course_material_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_course_material_id_seq OWNED BY bu.wq_course_material.id;


--
-- Name: wq_course_material_unlock_record; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_course_material_unlock_record (
    id integer NOT NULL,
    course_material_id integer NOT NULL,
    user_id integer NOT NULL,
    unlock_time timestamp without time zone NOT NULL,
    study_points_cost smallint DEFAULT 0 NOT NULL
);


ALTER TABLE bu.wq_course_material_unlock_record OWNER TO quanzi_admin;

--
-- Name: wq_course_material_purchase_record_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_course_material_purchase_record_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_course_material_purchase_record_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_course_material_purchase_record_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_course_material_purchase_record_id_seq OWNED BY bu.wq_course_material_unlock_record.id;


--
-- Name: wq_course_material_type; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_course_material_type (
    id smallint NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE bu.wq_course_material_type OWNER TO quanzi_admin;

--
-- Name: wq_course_material_type_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_course_material_type_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_course_material_type_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_course_material_type_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_course_material_type_id_seq OWNED BY bu.wq_course_material_type.id;


--
-- Name: wq_course_material_view_history; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_course_material_view_history (
    id integer NOT NULL,
    user_id integer NOT NULL,
    course_material_id integer NOT NULL,
    view_time timestamp without time zone NOT NULL
);


ALTER TABLE bu.wq_course_material_view_history OWNER TO quanzi_admin;

--
-- Name: wq_course_material_view_history_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_course_material_view_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_course_material_view_history_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_course_material_view_history_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_course_material_view_history_id_seq OWNED BY bu.wq_course_material_view_history.id;


--
-- Name: wq_course_view_history; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_course_view_history (
    id integer NOT NULL,
    user_id integer NOT NULL,
    course_id integer NOT NULL,
    view_time timestamp without time zone NOT NULL
);


ALTER TABLE bu.wq_course_view_history OWNER TO quanzi_admin;

--
-- Name: wq_course_view_history_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_course_view_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_course_view_history_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_course_view_history_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_course_view_history_id_seq OWNED BY bu.wq_course_view_history.id;


--
-- Name: wq_degree; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_degree (
    id smallint NOT NULL,
    name character varying(20) NOT NULL
);


ALTER TABLE bu.wq_degree OWNER TO quanzi_admin;

--
-- Name: TABLE wq_degree; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON TABLE bu.wq_degree IS 'this is the basic table for degree';


--
-- Name: wq_degree_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_degree_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_degree_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_degree_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_degree_id_seq OWNED BY bu.wq_degree.id;


--
-- Name: wq_department; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_department (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    school_id smallint NOT NULL
);


ALTER TABLE bu.wq_department OWNER TO quanzi_admin;

--
-- Name: wq_department_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_department_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_department_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_department_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_department_id_seq OWNED BY bu.wq_department.id;


--
-- Name: wq_discussion_channel; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_discussion_channel (
    id integer NOT NULL,
    name character varying(200) NOT NULL,
    user_id integer NOT NULL,
    group_manager_id integer NOT NULL,
    brief_introduction text NOT NULL,
    category smallint NOT NULL,
    group_capacity integer NOT NULL,
    create_time timestamp without time zone NOT NULL,
    update_time timestamp without time zone,
    status smallint DEFAULT 1 NOT NULL
);


ALTER TABLE bu.wq_discussion_channel OWNER TO quanzi_admin;

--
-- Name: COLUMN wq_discussion_channel.status; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON COLUMN bu.wq_discussion_channel.status IS '-1, unavailable; 1, available';


--
-- Name: wq_discussion_channel_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_discussion_channel_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_discussion_channel_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_discussion_channel_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_discussion_channel_id_seq OWNED BY bu.wq_discussion_channel.id;


--
-- Name: wq_favorite_course; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_favorite_course (
    user_id integer NOT NULL,
    course_id integer NOT NULL
);


ALTER TABLE bu.wq_favorite_course OWNER TO quanzi_admin;

--
-- Name: wq_favorite_course_material; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_favorite_course_material (
    user_id integer NOT NULL,
    course_material_id integer NOT NULL
);


ALTER TABLE bu.wq_favorite_course_material OWNER TO quanzi_admin;

--
-- Name: wq_favorite_professor; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_favorite_professor (
    user_id integer NOT NULL,
    professor_id integer NOT NULL
);


ALTER TABLE bu.wq_favorite_professor OWNER TO quanzi_admin;

--
-- Name: wq_favorite_thread; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_favorite_thread (
    user_id integer NOT NULL,
    thread_id integer NOT NULL
);


ALTER TABLE bu.wq_favorite_thread OWNER TO quanzi_admin;

--
-- Name: wq_group_discussion; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_group_discussion (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    user_id integer NOT NULL,
    brief_introduction text NOT NULL,
    category integer NOT NULL,
    price real DEFAULT 0 NOT NULL,
    location smallint DEFAULT 0 NOT NULL,
    scheduled timestamp without time zone NOT NULL,
    create_time timestamp without time zone NOT NULL,
    update_time timestamp without time zone,
    status smallint DEFAULT 1
);


ALTER TABLE bu.wq_group_discussion OWNER TO quanzi_admin;

--
-- Name: COLUMN wq_group_discussion.price; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON COLUMN bu.wq_group_discussion.price IS '0 for free or other amount';


--
-- Name: COLUMN wq_group_discussion.location; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON COLUMN bu.wq_group_discussion.location IS '0, online; 1, f2f';


--
-- Name: COLUMN wq_group_discussion.status; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON COLUMN bu.wq_group_discussion.status IS '-1, unavailable; 1, available';


--
-- Name: wq_group_discussion_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_group_discussion_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_group_discussion_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_group_discussion_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_group_discussion_id_seq OWNED BY bu.wq_group_discussion.id;


--
-- Name: wq_group_discussion_member; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_group_discussion_member (
    group_discussion_id integer NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE bu.wq_group_discussion_member OWNER TO quanzi_admin;

--
-- Name: wq_invite_friend_record; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_invite_friend_record (
    id integer NOT NULL,
    user_id integer NOT NULL,
    friend_email character varying(50) NOT NULL,
    invite_time timestamp without time zone NOT NULL,
    status smallint DEFAULT 0 NOT NULL,
    study_points_accquired smallint DEFAULT 0 NOT NULL
);


ALTER TABLE bu.wq_invite_friend_record OWNER TO quanzi_admin;

--
-- Name: COLUMN wq_invite_friend_record.status; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON COLUMN bu.wq_invite_friend_record.status IS '-1, unaccept; 0, initial; 1, accept';


--
-- Name: wq_invite_friend_record_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_invite_friend_record_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_invite_friend_record_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_invite_friend_record_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_invite_friend_record_id_seq OWNED BY bu.wq_invite_friend_record.id;


--
-- Name: wq_late_policy; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_late_policy (
    id smallint NOT NULL,
    label character varying(20) NOT NULL,
    description character varying(300),
    overdue_allowed smallint DEFAULT 0 NOT NULL
);


ALTER TABLE bu.wq_late_policy OWNER TO quanzi_admin;

--
-- Name: wq_late_policy_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_late_policy_id_seq
    AS smallint
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_late_policy_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_late_policy_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_late_policy_id_seq OWNED BY bu.wq_late_policy.id;


--
-- Name: wq_payment_setting; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_payment_setting (
    id integer NOT NULL,
    user_id integer NOT NULL,
    payment_type smallint NOT NULL
);


ALTER TABLE bu.wq_payment_setting OWNER TO quanzi_admin;

--
-- Name: wq_professor_course; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_professor_course (
    professor_id integer NOT NULL,
    course_id integer NOT NULL
);


ALTER TABLE bu.wq_professor_course OWNER TO quanzi_admin;

--
-- Name: wq_professor_course_rate; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_professor_course_rate (
    id integer NOT NULL,
    professor_id integer NOT NULL,
    course_id integer NOT NULL,
    user_id integer NOT NULL,
    quality_score real NOT NULL,
    difficult_score real NOT NULL,
    number_of_exams smallint DEFAULT 0 NOT NULL,
    number_of_quizzes smallint DEFAULT 0 NOT NULL,
    number_of_homeworks smallint DEFAULT 0 NOT NULL,
    number_of_projects smallint DEFAULT 0 NOT NULL,
    number_of_papers smallint DEFAULT 0 NOT NULL,
    grade_received real,
    tags smallint NOT NULL,
    comment character varying(300) NOT NULL,
    anonymous boolean DEFAULT false NOT NULL,
    using_textbook boolean NOT NULL,
    attendance boolean NOT NULL
);


ALTER TABLE bu.wq_professor_course_rate OWNER TO quanzi_admin;

--
-- Name: COLUMN wq_professor_course_rate.tags; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON COLUMN bu.wq_professor_course_rate.tags IS 'refer to basic table';


--
-- Name: wq_professor_course_rate_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_professor_course_rate_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_professor_course_rate_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_professor_course_rate_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_professor_course_rate_id_seq OWNED BY bu.wq_professor_course_rate.id;


--
-- Name: wq_professor_profile; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_professor_profile (
    id integer NOT NULL,
    first_name character varying(20) NOT NULL,
    last_name character varying(20) NOT NULL,
    school_id smallint NOT NULL,
    overall_score real DEFAULT 0 NOT NULL,
    create_time timestamp without time zone NOT NULL,
    create_by integer NOT NULL,
    department_id integer NOT NULL
);


ALTER TABLE bu.wq_professor_profile OWNER TO quanzi_admin;

--
-- Name: COLUMN wq_professor_profile.create_by; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON COLUMN bu.wq_professor_profile.create_by IS 'own user or admin can change';


--
-- Name: wq_professor_profile_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_professor_profile_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_professor_profile_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_professor_profile_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_professor_profile_id_seq OWNED BY bu.wq_professor_profile.id;


--
-- Name: wq_professor_view_history; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_professor_view_history (
    id integer NOT NULL,
    user_id integer NOT NULL,
    professor_id integer NOT NULL,
    view_time timestamp without time zone NOT NULL
);


ALTER TABLE bu.wq_professor_view_history OWNER TO quanzi_admin;

--
-- Name: wq_professor_view_history_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_professor_view_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_professor_view_history_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_professor_view_history_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_professor_view_history_id_seq OWNED BY bu.wq_professor_view_history.id;


--
-- Name: wq_realtime_class; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_realtime_class (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    tutor_id integer NOT NULL,
    brief_introduction text NOT NULL,
    category smallint NOT NULL,
    price real DEFAULT 0 NOT NULL,
    location smallint DEFAULT 0 NOT NULL,
    scheduled timestamp without time zone NOT NULL,
    create_time timestamp without time zone NOT NULL,
    update_time timestamp without time zone,
    status smallint DEFAULT 1 NOT NULL
);


ALTER TABLE bu.wq_realtime_class OWNER TO quanzi_admin;

--
-- Name: COLUMN wq_realtime_class.price; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON COLUMN bu.wq_realtime_class.price IS '0 for free or other amount';


--
-- Name: COLUMN wq_realtime_class.location; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON COLUMN bu.wq_realtime_class.location IS '0, online; 1, f2f';


--
-- Name: COLUMN wq_realtime_class.status; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON COLUMN bu.wq_realtime_class.status IS '-1, unavailable; 1, available';


--
-- Name: wq_realtime_class_group_member; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_realtime_class_group_member (
    realtime_class_id integer NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE bu.wq_realtime_class_group_member OWNER TO quanzi_admin;

--
-- Name: wq_realtime_class_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_realtime_class_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_realtime_class_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_realtime_class_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_realtime_class_id_seq OWNED BY bu.wq_realtime_class.id;


--
-- Name: wq_school; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_school (
    id smallint NOT NULL,
    name character varying(50)
);


ALTER TABLE bu.wq_school OWNER TO quanzi_admin;

--
-- Name: TABLE wq_school; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON TABLE bu.wq_school IS 'this is the basic table for school';


--
-- Name: wq_school_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_school_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_school_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_school_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_school_id_seq OWNED BY bu.wq_school.id;


--
-- Name: wq_subject_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_subject_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;


ALTER TABLE bu.wq_subject_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_subject; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_subject (
    id integer DEFAULT nextval('bu.wq_subject_id_seq'::regclass) NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE bu.wq_subject OWNER TO quanzi_admin;

--
-- Name: TABLE wq_subject; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON TABLE bu.wq_subject IS 'this is the basic table for subjects';


--
-- Name: wq_thread; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_thread (
    id integer NOT NULL,
    user_id integer NOT NULL,
    title character varying(500) NOT NULL,
    category smallint NOT NULL,
    tag smallint NOT NULL,
    content text NOT NULL,
    study_points_bonus smallint DEFAULT 0 NOT NULL,
    create_time timestamp without time zone NOT NULL,
    update_time timestamp without time zone,
    likes integer DEFAULT 0 NOT NULL,
    dislikes integer DEFAULT 0 NOT NULL
);


ALTER TABLE bu.wq_thread OWNER TO quanzi_admin;

--
-- Name: wq_thread_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_thread_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_thread_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_thread_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_thread_id_seq OWNED BY bu.wq_thread.id;


--
-- Name: wq_thread_stream; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_thread_stream (
    id integer NOT NULL,
    user_id integer NOT NULL,
    thread_id integer NOT NULL,
    comment text NOT NULL,
    to_stream_id integer,
    create_time timestamp without time zone NOT NULL,
    update_time timestamp without time zone
);


ALTER TABLE bu.wq_thread_stream OWNER TO quanzi_admin;

--
-- Name: wq_thread_stream_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_thread_stream_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_thread_stream_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_thread_stream_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_thread_stream_id_seq OWNED BY bu.wq_thread_stream.id;


--
-- Name: wq_thread_view_history; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_thread_view_history (
    id integer NOT NULL,
    user_id integer NOT NULL,
    thread_id integer NOT NULL,
    view_time timestamp without time zone NOT NULL
);


ALTER TABLE bu.wq_thread_view_history OWNER TO quanzi_admin;

--
-- Name: wq_thread_view_history_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_thread_view_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_thread_view_history_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_thread_view_history_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_thread_view_history_id_seq OWNED BY bu.wq_thread_view_history.id;


--
-- Name: wq_tutor_course; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_tutor_course (
    id integer NOT NULL,
    tutor_id integer NOT NULL,
    course_id integer NOT NULL,
    online boolean DEFAULT true NOT NULL,
    create_time timestamp without time zone NOT NULL,
    update_time timestamp without time zone
);


ALTER TABLE bu.wq_tutor_course OWNER TO quanzi_admin;

--
-- Name: wq_tutor_course_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_tutor_course_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_tutor_course_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_tutor_course_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_tutor_course_id_seq OWNED BY bu.wq_tutor_course.id;


--
-- Name: wq_tutor_inquiry; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_tutor_inquiry (
    id integer NOT NULL,
    user_id integer NOT NULL,
    subject_id integer NOT NULL,
    course_id integer NOT NULL,
    brief_description text NOT NULL,
    online boolean DEFAULT true NOT NULL,
    create_time timestamp without time zone NOT NULL,
    update_time timestamp without time zone,
    status smallint DEFAULT 1 NOT NULL
);


ALTER TABLE bu.wq_tutor_inquiry OWNER TO quanzi_admin;

--
-- Name: COLUMN wq_tutor_inquiry.status; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON COLUMN bu.wq_tutor_inquiry.status IS '-1, delete; 1, raise';


--
-- Name: wq_tutor_inquiry_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_tutor_inquiry_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_tutor_inquiry_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_tutor_inquiry_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_tutor_inquiry_id_seq OWNED BY bu.wq_tutor_inquiry.id;


--
-- Name: wq_tutor_profile; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_tutor_profile (
    id integer NOT NULL,
    user_id integer NOT NULL,
    brief_introduction text NOT NULL,
    resume_path character varying(200) NOT NULL,
    transcript_path character varying(200) NOT NULL,
    other_proof_path character varying(200),
    current_school_id smallint NOT NULL,
    current_degree_id smallint NOT NULL,
    current_location character varying(100) NOT NULL,
    create_time timestamp without time zone NOT NULL,
    update_time timestamp without time zone,
    pay_rate real NOT NULL,
    status smallint DEFAULT 1 NOT NULL,
    late_policy_id smallint NOT NULL,
    cancellation_policy_id smallint NOT NULL
);


ALTER TABLE bu.wq_tutor_profile OWNER TO quanzi_admin;

--
-- Name: COLUMN wq_tutor_profile.status; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON COLUMN bu.wq_tutor_profile.status IS '1, default; -1, user quit';


--
-- Name: wq_tutor_profile_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_tutor_profile_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bu.wq_tutor_profile_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_tutor_profile_id_seq; Type: SEQUENCE OWNED BY; Schema: bu; Owner: quanzi_admin
--

ALTER SEQUENCE bu.wq_tutor_profile_id_seq OWNED BY bu.wq_tutor_profile.id;


--
-- Name: wq_tutor_subject; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_tutor_subject (
    tutor_id integer NOT NULL,
    subject_id integer NOT NULL
);


ALTER TABLE bu.wq_tutor_subject OWNER TO quanzi_admin;

--
-- Name: wq_user_follower; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_user_follower (
    user_id integer NOT NULL,
    follower_id integer NOT NULL
);


ALTER TABLE bu.wq_user_follower OWNER TO quanzi_admin;

--
-- Name: wq_user_profile_id_seq; Type: SEQUENCE; Schema: bu; Owner: quanzi_admin
--

CREATE SEQUENCE bu.wq_user_profile_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;


ALTER TABLE bu.wq_user_profile_id_seq OWNER TO quanzi_admin;

--
-- Name: wq_user_profile; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_user_profile (
    id integer DEFAULT nextval('bu.wq_user_profile_id_seq'::regclass) NOT NULL,
    nickname character varying(20) NOT NULL,
    avatar character varying(200),
    email character varying(50) NOT NULL,
    phone character varying(15),
    address character varying(100),
    zip_code character varying(10),
    credential character varying(50) NOT NULL,
    school_id smallint NOT NULL,
    study_points smallint NOT NULL,
    access_token character varying(500),
    first_name character varying(20) NOT NULL,
    last_name character varying(20) NOT NULL
);


ALTER TABLE bu.wq_user_profile OWNER TO quanzi_admin;

--
-- Name: wq_user_subject; Type: TABLE; Schema: bu; Owner: quanzi_admin
--

CREATE TABLE bu.wq_user_subject (
    user_id integer NOT NULL,
    subject_id integer NOT NULL
);


ALTER TABLE bu.wq_user_subject OWNER TO quanzi_admin;

--
-- Name: TABLE wq_user_subject; Type: COMMENT; Schema: bu; Owner: quanzi_admin
--

COMMENT ON TABLE bu.wq_user_subject IS 'one user has multiple majors/topics';


--
-- Name: wq_apply_tutor_record id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_apply_tutor_record ALTER COLUMN id SET DEFAULT nextval('bu.wq_apply_tutor_record_id_seq'::regclass);


--
-- Name: wq_appointment id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_appointment ALTER COLUMN id SET DEFAULT nextval('bu.wq_appointment_id_seq'::regclass);


--
-- Name: wq_appointment_rate id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_appointment_rate ALTER COLUMN id SET DEFAULT nextval('bu.wq_appointment_rate_id_seq'::regclass);


--
-- Name: wq_cancellation_policy id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_cancellation_policy ALTER COLUMN id SET DEFAULT nextval('bu.wq_cancellation_policy_id_seq'::regclass);


--
-- Name: wq_course id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course ALTER COLUMN id SET DEFAULT nextval('bu.wq_course_id_seq'::regclass);


--
-- Name: wq_course_material id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_material ALTER COLUMN id SET DEFAULT nextval('bu.wq_course_material_id_seq'::regclass);


--
-- Name: wq_course_material_type id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_material_type ALTER COLUMN id SET DEFAULT nextval('bu.wq_course_material_type_id_seq'::regclass);


--
-- Name: wq_course_material_unlock_record id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_material_unlock_record ALTER COLUMN id SET DEFAULT nextval('bu.wq_course_material_purchase_record_id_seq'::regclass);


--
-- Name: wq_course_material_view_history id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_material_view_history ALTER COLUMN id SET DEFAULT nextval('bu.wq_course_material_view_history_id_seq'::regclass);


--
-- Name: wq_course_view_history id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_view_history ALTER COLUMN id SET DEFAULT nextval('bu.wq_course_view_history_id_seq'::regclass);


--
-- Name: wq_degree id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_degree ALTER COLUMN id SET DEFAULT nextval('bu.wq_degree_id_seq'::regclass);


--
-- Name: wq_department id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_department ALTER COLUMN id SET DEFAULT nextval('bu.wq_department_id_seq'::regclass);


--
-- Name: wq_discussion_channel id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_discussion_channel ALTER COLUMN id SET DEFAULT nextval('bu.wq_discussion_channel_id_seq'::regclass);


--
-- Name: wq_group_discussion id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_group_discussion ALTER COLUMN id SET DEFAULT nextval('bu.wq_group_discussion_id_seq'::regclass);


--
-- Name: wq_invite_friend_record id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_invite_friend_record ALTER COLUMN id SET DEFAULT nextval('bu.wq_invite_friend_record_id_seq'::regclass);


--
-- Name: wq_late_policy id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_late_policy ALTER COLUMN id SET DEFAULT nextval('bu.wq_late_policy_id_seq'::regclass);


--
-- Name: wq_professor_course_rate id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_professor_course_rate ALTER COLUMN id SET DEFAULT nextval('bu.wq_professor_course_rate_id_seq'::regclass);


--
-- Name: wq_professor_profile id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_professor_profile ALTER COLUMN id SET DEFAULT nextval('bu.wq_professor_profile_id_seq'::regclass);


--
-- Name: wq_professor_view_history id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_professor_view_history ALTER COLUMN id SET DEFAULT nextval('bu.wq_professor_view_history_id_seq'::regclass);


--
-- Name: wq_realtime_class id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_realtime_class ALTER COLUMN id SET DEFAULT nextval('bu.wq_realtime_class_id_seq'::regclass);


--
-- Name: wq_school id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_school ALTER COLUMN id SET DEFAULT nextval('bu.wq_school_id_seq'::regclass);


--
-- Name: wq_thread id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_thread ALTER COLUMN id SET DEFAULT nextval('bu.wq_thread_id_seq'::regclass);


--
-- Name: wq_thread_stream id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_thread_stream ALTER COLUMN id SET DEFAULT nextval('bu.wq_thread_stream_id_seq'::regclass);


--
-- Name: wq_thread_view_history id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_thread_view_history ALTER COLUMN id SET DEFAULT nextval('bu.wq_thread_view_history_id_seq'::regclass);


--
-- Name: wq_tutor_course id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_course ALTER COLUMN id SET DEFAULT nextval('bu.wq_tutor_course_id_seq'::regclass);


--
-- Name: wq_tutor_inquiry id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_inquiry ALTER COLUMN id SET DEFAULT nextval('bu.wq_tutor_inquiry_id_seq'::regclass);


--
-- Name: wq_tutor_profile id; Type: DEFAULT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_profile ALTER COLUMN id SET DEFAULT nextval('bu.wq_tutor_profile_id_seq'::regclass);


--
-- Data for Name: wq_apply_tutor_record; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_apply_tutor_record (id, user_id, brief_introduction, resume_path, transcript_path, other_proof_path, current_school_id, current_degree_id, current_location, apply_tutor_courses, create_time, update_time, admin_action, admin_action_time, status) FROM stdin;
\.


--
-- Data for Name: wq_appointment; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_appointment (id, organizer, subject, participant, create_time, update_time, status) FROM stdin;
\.


--
-- Data for Name: wq_appointment_rate; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_appointment_rate (id, appointment_id, user_id, score, evaluation) FROM stdin;
\.


--
-- Data for Name: wq_cancellation_policy; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_cancellation_policy (id, label, description, refund_ratio) FROM stdin;
\.


--
-- Data for Name: wq_course; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_course (id, name, brief_description, school_id, department_id, code_first, code_second) FROM stdin;
\.


--
-- Data for Name: wq_course_material; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_course_material (id, type, store_directory, file_name, source_from, subject_id, course_id, professor_id, study_points_required, term, file_type, upload_by, upload_time) FROM stdin;
\.


--
-- Data for Name: wq_course_material_type; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_course_material_type (id, name) FROM stdin;
\.


--
-- Data for Name: wq_course_material_unlock_record; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_course_material_unlock_record (id, course_material_id, user_id, unlock_time, study_points_cost) FROM stdin;
\.


--
-- Data for Name: wq_course_material_view_history; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_course_material_view_history (id, user_id, course_material_id, view_time) FROM stdin;
\.


--
-- Data for Name: wq_course_view_history; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_course_view_history (id, user_id, course_id, view_time) FROM stdin;
\.


--
-- Data for Name: wq_degree; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_degree (id, name) FROM stdin;
\.


--
-- Data for Name: wq_department; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_department (id, name, school_id) FROM stdin;
\.


--
-- Data for Name: wq_discussion_channel; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_discussion_channel (id, name, user_id, group_manager_id, brief_introduction, category, group_capacity, create_time, update_time, status) FROM stdin;
\.


--
-- Data for Name: wq_favorite_course; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_favorite_course (user_id, course_id) FROM stdin;
\.


--
-- Data for Name: wq_favorite_course_material; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_favorite_course_material (user_id, course_material_id) FROM stdin;
\.


--
-- Data for Name: wq_favorite_professor; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_favorite_professor (user_id, professor_id) FROM stdin;
\.


--
-- Data for Name: wq_favorite_thread; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_favorite_thread (user_id, thread_id) FROM stdin;
\.


--
-- Data for Name: wq_group_discussion; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_group_discussion (id, name, user_id, brief_introduction, category, price, location, scheduled, create_time, update_time, status) FROM stdin;
\.


--
-- Data for Name: wq_group_discussion_member; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_group_discussion_member (group_discussion_id, user_id) FROM stdin;
\.


--
-- Data for Name: wq_invite_friend_record; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_invite_friend_record (id, user_id, friend_email, invite_time, status, study_points_accquired) FROM stdin;
\.


--
-- Data for Name: wq_late_policy; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_late_policy (id, label, description, overdue_allowed) FROM stdin;
\.


--
-- Data for Name: wq_payment_setting; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_payment_setting (id, user_id, payment_type) FROM stdin;
\.


--
-- Data for Name: wq_professor_course; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_professor_course (professor_id, course_id) FROM stdin;
\.


--
-- Data for Name: wq_professor_course_rate; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_professor_course_rate (id, professor_id, course_id, user_id, quality_score, difficult_score, number_of_exams, number_of_quizzes, number_of_homeworks, number_of_projects, number_of_papers, grade_received, tags, comment, anonymous, using_textbook, attendance) FROM stdin;
\.


--
-- Data for Name: wq_professor_profile; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_professor_profile (id, first_name, last_name, school_id, overall_score, create_time, create_by, department_id) FROM stdin;
\.


--
-- Data for Name: wq_professor_view_history; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_professor_view_history (id, user_id, professor_id, view_time) FROM stdin;
\.


--
-- Data for Name: wq_realtime_class; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_realtime_class (id, name, tutor_id, brief_introduction, category, price, location, scheduled, create_time, update_time, status) FROM stdin;
\.


--
-- Data for Name: wq_realtime_class_group_member; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_realtime_class_group_member (realtime_class_id, user_id) FROM stdin;
\.


--
-- Data for Name: wq_school; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_school (id, name) FROM stdin;
\.


--
-- Data for Name: wq_subject; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_subject (id, name) FROM stdin;
\.


--
-- Data for Name: wq_thread; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_thread (id, user_id, title, category, tag, content, study_points_bonus, create_time, update_time, likes, dislikes) FROM stdin;
\.


--
-- Data for Name: wq_thread_stream; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_thread_stream (id, user_id, thread_id, comment, to_stream_id, create_time, update_time) FROM stdin;
\.


--
-- Data for Name: wq_thread_view_history; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_thread_view_history (id, user_id, thread_id, view_time) FROM stdin;
\.


--
-- Data for Name: wq_tutor_course; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_tutor_course (id, tutor_id, course_id, online, create_time, update_time) FROM stdin;
\.


--
-- Data for Name: wq_tutor_inquiry; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_tutor_inquiry (id, user_id, subject_id, course_id, brief_description, online, create_time, update_time, status) FROM stdin;
\.


--
-- Data for Name: wq_tutor_profile; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_tutor_profile (id, user_id, brief_introduction, resume_path, transcript_path, other_proof_path, current_school_id, current_degree_id, current_location, create_time, update_time, pay_rate, status, late_policy_id, cancellation_policy_id) FROM stdin;
\.


--
-- Data for Name: wq_tutor_subject; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_tutor_subject (tutor_id, subject_id) FROM stdin;
\.


--
-- Data for Name: wq_user_follower; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_user_follower (user_id, follower_id) FROM stdin;
\.


--
-- Data for Name: wq_user_profile; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_user_profile (id, nickname, avatar, email, phone, address, zip_code, credential, school_id, study_points, access_token, first_name, last_name) FROM stdin;
\.


--
-- Data for Name: wq_user_subject; Type: TABLE DATA; Schema: bu; Owner: quanzi_admin
--

COPY bu.wq_user_subject (user_id, subject_id) FROM stdin;
\.


--
-- Name: wq_apply_tutor_record_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_apply_tutor_record_id_seq', 1, false);


--
-- Name: wq_appointment_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_appointment_id_seq', 1, false);


--
-- Name: wq_appointment_rate_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_appointment_rate_id_seq', 1, false);


--
-- Name: wq_cancellation_policy_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_cancellation_policy_id_seq', 1, false);


--
-- Name: wq_course_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_course_id_seq', 1, false);


--
-- Name: wq_course_material_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_course_material_id_seq', 1, false);


--
-- Name: wq_course_material_purchase_record_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_course_material_purchase_record_id_seq', 1, false);


--
-- Name: wq_course_material_type_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_course_material_type_id_seq', 1, false);


--
-- Name: wq_course_material_view_history_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_course_material_view_history_id_seq', 1, false);


--
-- Name: wq_course_view_history_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_course_view_history_id_seq', 1, false);


--
-- Name: wq_degree_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_degree_id_seq', 1, false);


--
-- Name: wq_department_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_department_id_seq', 1, false);


--
-- Name: wq_discussion_channel_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_discussion_channel_id_seq', 1, false);


--
-- Name: wq_group_discussion_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_group_discussion_id_seq', 1, false);


--
-- Name: wq_invite_friend_record_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_invite_friend_record_id_seq', 1, false);


--
-- Name: wq_late_policy_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_late_policy_id_seq', 1, false);


--
-- Name: wq_professor_course_rate_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_professor_course_rate_id_seq', 1, false);


--
-- Name: wq_professor_profile_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_professor_profile_id_seq', 1, false);


--
-- Name: wq_professor_view_history_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_professor_view_history_id_seq', 1, false);


--
-- Name: wq_realtime_class_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_realtime_class_id_seq', 1, false);


--
-- Name: wq_school_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_school_id_seq', 1, false);


--
-- Name: wq_subject_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_subject_id_seq', 1, false);


--
-- Name: wq_thread_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_thread_id_seq', 1, false);


--
-- Name: wq_thread_stream_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_thread_stream_id_seq', 1, false);


--
-- Name: wq_thread_view_history_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_thread_view_history_id_seq', 1, false);


--
-- Name: wq_tutor_course_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_tutor_course_id_seq', 1, false);


--
-- Name: wq_tutor_inquiry_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_tutor_inquiry_id_seq', 1, false);


--
-- Name: wq_tutor_profile_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_tutor_profile_id_seq', 1, false);


--
-- Name: wq_user_profile_id_seq; Type: SEQUENCE SET; Schema: bu; Owner: quanzi_admin
--

SELECT pg_catalog.setval('bu.wq_user_profile_id_seq', 1, false);


--
-- Name: wq_apply_tutor_record apply_tutor_record_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_apply_tutor_record
    ADD CONSTRAINT apply_tutor_record_pk_id PRIMARY KEY (id);


--
-- Name: wq_appointment appointment_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_appointment
    ADD CONSTRAINT appointment_pk_id PRIMARY KEY (id);


--
-- Name: wq_appointment_rate appointment_rate_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_appointment_rate
    ADD CONSTRAINT appointment_rate_pk_id PRIMARY KEY (id);


--
-- Name: wq_cancellation_policy cancellation_policy_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_cancellation_policy
    ADD CONSTRAINT cancellation_policy_pk_id PRIMARY KEY (id);


--
-- Name: wq_course_material course_material_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_material
    ADD CONSTRAINT course_material_pk_id PRIMARY KEY (id);


--
-- Name: wq_course_material_unlock_record course_material_purchase_record_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_material_unlock_record
    ADD CONSTRAINT course_material_purchase_record_pk_id PRIMARY KEY (id);


--
-- Name: wq_course_material_type course_material_type_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_material_type
    ADD CONSTRAINT course_material_type_pk_id PRIMARY KEY (id);


--
-- Name: wq_course_material_view_history course_material_view_history_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_material_view_history
    ADD CONSTRAINT course_material_view_history_pk_id PRIMARY KEY (id);


--
-- Name: wq_course course_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course
    ADD CONSTRAINT course_pk_id PRIMARY KEY (id);


--
-- Name: wq_course_view_history course_view_history_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_view_history
    ADD CONSTRAINT course_view_history_pk_id PRIMARY KEY (id);


--
-- Name: wq_degree degree_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_degree
    ADD CONSTRAINT degree_pk_id PRIMARY KEY (id);


--
-- Name: wq_department department_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_department
    ADD CONSTRAINT department_pk_id PRIMARY KEY (id);


--
-- Name: wq_discussion_channel discussion_channel_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_discussion_channel
    ADD CONSTRAINT discussion_channel_pk_id PRIMARY KEY (id);


--
-- Name: wq_favorite_course_material favorite_course_material_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_favorite_course_material
    ADD CONSTRAINT favorite_course_material_pk_id PRIMARY KEY (user_id, course_material_id);


--
-- Name: wq_favorite_course favorite_course_pk_user_course_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_favorite_course
    ADD CONSTRAINT favorite_course_pk_user_course_id PRIMARY KEY (user_id, course_id);


--
-- Name: wq_favorite_professor favorite_professor_pk_user_professor_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_favorite_professor
    ADD CONSTRAINT favorite_professor_pk_user_professor_id PRIMARY KEY (user_id, professor_id);


--
-- Name: wq_favorite_thread favorite_thread_pk_user_thread_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_favorite_thread
    ADD CONSTRAINT favorite_thread_pk_user_thread_id PRIMARY KEY (user_id, thread_id);


--
-- Name: wq_group_discussion_member group_discussion_member_pk_group_user_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_group_discussion_member
    ADD CONSTRAINT group_discussion_member_pk_group_user_id PRIMARY KEY (group_discussion_id, user_id);


--
-- Name: wq_group_discussion group_discussion_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_group_discussion
    ADD CONSTRAINT group_discussion_pk_id PRIMARY KEY (id);


--
-- Name: wq_invite_friend_record invite_friend_record_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_invite_friend_record
    ADD CONSTRAINT invite_friend_record_pk_id PRIMARY KEY (id);


--
-- Name: wq_late_policy late_policy_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_late_policy
    ADD CONSTRAINT late_policy_pk_id PRIMARY KEY (id);


--
-- Name: wq_payment_setting payment_setting_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_payment_setting
    ADD CONSTRAINT payment_setting_pk_id PRIMARY KEY (id);


--
-- Name: wq_professor_course professor_course_pk_professor_course_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_professor_course
    ADD CONSTRAINT professor_course_pk_professor_course_id PRIMARY KEY (professor_id, course_id);


--
-- Name: wq_professor_course_rate professor_course_rate_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_professor_course_rate
    ADD CONSTRAINT professor_course_rate_pk_id PRIMARY KEY (id);


--
-- Name: wq_professor_profile professor_profile_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_professor_profile
    ADD CONSTRAINT professor_profile_pk_id PRIMARY KEY (id);


--
-- Name: wq_professor_view_history professor_view_history_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_professor_view_history
    ADD CONSTRAINT professor_view_history_pk_id PRIMARY KEY (id);


--
-- Name: wq_realtime_class_group_member realtime_class_group_member_pk_group_user_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_realtime_class_group_member
    ADD CONSTRAINT realtime_class_group_member_pk_group_user_id PRIMARY KEY (realtime_class_id, user_id);


--
-- Name: wq_realtime_class realtime_class_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_realtime_class
    ADD CONSTRAINT realtime_class_pk_id PRIMARY KEY (id);


--
-- Name: wq_school school_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_school
    ADD CONSTRAINT school_pk_id PRIMARY KEY (id);


--
-- Name: wq_subject subject_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_subject
    ADD CONSTRAINT subject_pk_id PRIMARY KEY (id);


--
-- Name: wq_thread thread_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_thread
    ADD CONSTRAINT thread_pk_id PRIMARY KEY (id);


--
-- Name: wq_thread_stream thread_stream_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_thread_stream
    ADD CONSTRAINT thread_stream_pk_id PRIMARY KEY (id);


--
-- Name: wq_thread_view_history thread_view_history_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_thread_view_history
    ADD CONSTRAINT thread_view_history_pk_id PRIMARY KEY (id);


--
-- Name: wq_tutor_course tutor_course_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_course
    ADD CONSTRAINT tutor_course_pk_id PRIMARY KEY (id);


--
-- Name: wq_tutor_inquiry tutor_inquiry_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_inquiry
    ADD CONSTRAINT tutor_inquiry_pk_id PRIMARY KEY (id);


--
-- Name: wq_tutor_profile tutor_profile_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_profile
    ADD CONSTRAINT tutor_profile_pk_id PRIMARY KEY (id);


--
-- Name: wq_tutor_subject tutor_subject_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_subject
    ADD CONSTRAINT tutor_subject_pk_id PRIMARY KEY (tutor_id, subject_id);


--
-- Name: wq_degree unique_degree_name; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_degree
    ADD CONSTRAINT unique_degree_name UNIQUE (name);


--
-- Name: wq_subject unique_major_name; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_subject
    ADD CONSTRAINT unique_major_name UNIQUE (name);


--
-- Name: wq_school unique_school_name; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_school
    ADD CONSTRAINT unique_school_name UNIQUE (name);


--
-- Name: wq_user_follower unique_user_follower_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_user_follower
    ADD CONSTRAINT unique_user_follower_id UNIQUE (user_id, follower_id);


--
-- Name: wq_user_profile unique_user_profile_email; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_user_profile
    ADD CONSTRAINT unique_user_profile_email UNIQUE (email);


--
-- Name: wq_user_profile user_profile_pk_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_user_profile
    ADD CONSTRAINT user_profile_pk_id PRIMARY KEY (id);


--
-- Name: wq_user_subject user_subject_pk_user_subject_id; Type: CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_user_subject
    ADD CONSTRAINT user_subject_pk_user_subject_id PRIMARY KEY (user_id, subject_id);


--
-- Name: wq_apply_tutor_record apply_tutor_record_fk_degree_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_apply_tutor_record
    ADD CONSTRAINT apply_tutor_record_fk_degree_id FOREIGN KEY (current_degree_id) REFERENCES bu.wq_degree(id) NOT VALID;


--
-- Name: wq_apply_tutor_record apply_tutor_record_fk_school_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_apply_tutor_record
    ADD CONSTRAINT apply_tutor_record_fk_school_id FOREIGN KEY (current_school_id) REFERENCES bu.wq_school(id) NOT VALID;


--
-- Name: wq_apply_tutor_record apply_tutor_record_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_apply_tutor_record
    ADD CONSTRAINT apply_tutor_record_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id) NOT VALID;


--
-- Name: wq_appointment appointment_organizer_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_appointment
    ADD CONSTRAINT appointment_organizer_fk_user_id FOREIGN KEY (organizer) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_appointment appointment_participant_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_appointment
    ADD CONSTRAINT appointment_participant_fk_user_id FOREIGN KEY (participant) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_appointment_rate appointment_rate_fk_appointment_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_appointment_rate
    ADD CONSTRAINT appointment_rate_fk_appointment_id FOREIGN KEY (appointment_id) REFERENCES bu.wq_appointment(id);


--
-- Name: wq_appointment_rate appointment_rate_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_appointment_rate
    ADD CONSTRAINT appointment_rate_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_course course_fk_department_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course
    ADD CONSTRAINT course_fk_department_id FOREIGN KEY (department_id) REFERENCES bu.wq_department(id) NOT VALID;


--
-- Name: wq_course course_fk_school_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course
    ADD CONSTRAINT course_fk_school_id FOREIGN KEY (school_id) REFERENCES bu.wq_school(id);


--
-- Name: wq_course_material course_material_fk_course_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_material
    ADD CONSTRAINT course_material_fk_course_id FOREIGN KEY (course_id) REFERENCES bu.wq_course(id);


--
-- Name: wq_course_material course_material_fk_course_material_type_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_material
    ADD CONSTRAINT course_material_fk_course_material_type_id FOREIGN KEY (type) REFERENCES bu.wq_course_material_type(id) NOT VALID;


--
-- Name: wq_course_material course_material_fk_professor_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_material
    ADD CONSTRAINT course_material_fk_professor_id FOREIGN KEY (professor_id) REFERENCES bu.wq_professor_profile(id) NOT VALID;


--
-- Name: wq_course_material course_material_fk_subject_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_material
    ADD CONSTRAINT course_material_fk_subject_id FOREIGN KEY (subject_id) REFERENCES bu.wq_subject(id);


--
-- Name: wq_course_material_unlock_record course_material_purchase_record_fk_course_material_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_material_unlock_record
    ADD CONSTRAINT course_material_purchase_record_fk_course_material_id FOREIGN KEY (course_material_id) REFERENCES bu.wq_course_material(id);


--
-- Name: wq_course_material_unlock_record course_material_purchase_record_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_material_unlock_record
    ADD CONSTRAINT course_material_purchase_record_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_course_material_view_history course_material_view_history_fk_course_material_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_material_view_history
    ADD CONSTRAINT course_material_view_history_fk_course_material_id FOREIGN KEY (course_material_id) REFERENCES bu.wq_course_material(id);


--
-- Name: wq_course_material_view_history course_material_view_history_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_material_view_history
    ADD CONSTRAINT course_material_view_history_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_course_view_history course_view_history_fk_course_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_view_history
    ADD CONSTRAINT course_view_history_fk_course_id FOREIGN KEY (course_id) REFERENCES bu.wq_course(id);


--
-- Name: wq_course_view_history course_view_history_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_course_view_history
    ADD CONSTRAINT course_view_history_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_department department_fk_school_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_department
    ADD CONSTRAINT department_fk_school_id FOREIGN KEY (school_id) REFERENCES bu.wq_school(id);


--
-- Name: wq_discussion_channel discussion_channel_group_manager_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_discussion_channel
    ADD CONSTRAINT discussion_channel_group_manager_fk_user_id FOREIGN KEY (group_manager_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_discussion_channel discussion_channel_user_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_discussion_channel
    ADD CONSTRAINT discussion_channel_user_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_favorite_course favorite_course_fk_course_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_favorite_course
    ADD CONSTRAINT favorite_course_fk_course_id FOREIGN KEY (course_id) REFERENCES bu.wq_course(id);


--
-- Name: wq_favorite_course favorite_course_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_favorite_course
    ADD CONSTRAINT favorite_course_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_favorite_course_material favorite_course_material_fk_course_material_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_favorite_course_material
    ADD CONSTRAINT favorite_course_material_fk_course_material_id FOREIGN KEY (course_material_id) REFERENCES bu.wq_course_material(id);


--
-- Name: wq_favorite_course_material favorite_course_material_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_favorite_course_material
    ADD CONSTRAINT favorite_course_material_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_favorite_professor favorite_professor_fk_professor_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_favorite_professor
    ADD CONSTRAINT favorite_professor_fk_professor_id FOREIGN KEY (professor_id) REFERENCES bu.wq_professor_profile(id) NOT VALID;


--
-- Name: wq_favorite_professor favorite_professor_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_favorite_professor
    ADD CONSTRAINT favorite_professor_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_favorite_thread favorite_thread_fk_thread_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_favorite_thread
    ADD CONSTRAINT favorite_thread_fk_thread_id FOREIGN KEY (thread_id) REFERENCES bu.wq_thread(id);


--
-- Name: wq_favorite_thread favorite_thread_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_favorite_thread
    ADD CONSTRAINT favorite_thread_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_group_discussion group_discussion_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_group_discussion
    ADD CONSTRAINT group_discussion_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_group_discussion_member group_discussion_member_fk_group_discussion_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_group_discussion_member
    ADD CONSTRAINT group_discussion_member_fk_group_discussion_id FOREIGN KEY (group_discussion_id) REFERENCES bu.wq_group_discussion(id);


--
-- Name: wq_group_discussion_member group_discussion_member_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_group_discussion_member
    ADD CONSTRAINT group_discussion_member_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_invite_friend_record invite_friend_record_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_invite_friend_record
    ADD CONSTRAINT invite_friend_record_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_payment_setting payment_setting_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_payment_setting
    ADD CONSTRAINT payment_setting_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_professor_course professor_course_fk_course_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_professor_course
    ADD CONSTRAINT professor_course_fk_course_id FOREIGN KEY (course_id) REFERENCES bu.wq_course(id);


--
-- Name: wq_professor_course professor_course_fk_professor_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_professor_course
    ADD CONSTRAINT professor_course_fk_professor_id FOREIGN KEY (professor_id) REFERENCES bu.wq_professor_profile(id);


--
-- Name: wq_professor_course_rate professor_course_rate_fk_course_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_professor_course_rate
    ADD CONSTRAINT professor_course_rate_fk_course_id FOREIGN KEY (course_id) REFERENCES bu.wq_course(id);


--
-- Name: wq_professor_course_rate professor_course_rate_fk_professor_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_professor_course_rate
    ADD CONSTRAINT professor_course_rate_fk_professor_id FOREIGN KEY (professor_id) REFERENCES bu.wq_professor_profile(id);


--
-- Name: wq_professor_course_rate professor_course_rate_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_professor_course_rate
    ADD CONSTRAINT professor_course_rate_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_professor_profile professor_profile_fk_school_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_professor_profile
    ADD CONSTRAINT professor_profile_fk_school_id FOREIGN KEY (school_id) REFERENCES bu.wq_school(id);


--
-- Name: wq_professor_profile professor_profile_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_professor_profile
    ADD CONSTRAINT professor_profile_fk_user_id FOREIGN KEY (create_by) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_professor_view_history professor_view_history_fk_professor_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_professor_view_history
    ADD CONSTRAINT professor_view_history_fk_professor_id FOREIGN KEY (professor_id) REFERENCES bu.wq_professor_profile(id);


--
-- Name: wq_professor_view_history professor_view_history_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_professor_view_history
    ADD CONSTRAINT professor_view_history_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_realtime_class realtime_class_fk_tutor_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_realtime_class
    ADD CONSTRAINT realtime_class_fk_tutor_id FOREIGN KEY (tutor_id) REFERENCES bu.wq_tutor_profile(id);


--
-- Name: wq_realtime_class_group_member realtime_class_group_member_fk_realtime_class_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_realtime_class_group_member
    ADD CONSTRAINT realtime_class_group_member_fk_realtime_class_id FOREIGN KEY (realtime_class_id) REFERENCES bu.wq_realtime_class(id);


--
-- Name: wq_realtime_class_group_member realtime_class_group_member_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_realtime_class_group_member
    ADD CONSTRAINT realtime_class_group_member_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_thread thread_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_thread
    ADD CONSTRAINT thread_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_thread_stream thread_stream_fk_thread_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_thread_stream
    ADD CONSTRAINT thread_stream_fk_thread_id FOREIGN KEY (thread_id) REFERENCES bu.wq_thread(id);


--
-- Name: wq_thread_stream thread_stream_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_thread_stream
    ADD CONSTRAINT thread_stream_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_thread_view_history thread_view_history_fk_thread_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_thread_view_history
    ADD CONSTRAINT thread_view_history_fk_thread_id FOREIGN KEY (thread_id) REFERENCES bu.wq_thread(id);


--
-- Name: wq_thread_view_history thread_view_history_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_thread_view_history
    ADD CONSTRAINT thread_view_history_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_tutor_course tutor_course_fk_course_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_course
    ADD CONSTRAINT tutor_course_fk_course_id FOREIGN KEY (course_id) REFERENCES bu.wq_course(id) NOT VALID;


--
-- Name: wq_tutor_course tutor_course_fk_tutor_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_course
    ADD CONSTRAINT tutor_course_fk_tutor_id FOREIGN KEY (tutor_id) REFERENCES bu.wq_tutor_profile(id);


--
-- Name: wq_tutor_profile tutor_fk_cancellation_policy_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_profile
    ADD CONSTRAINT tutor_fk_cancellation_policy_id FOREIGN KEY (cancellation_policy_id) REFERENCES bu.wq_cancellation_policy(id) NOT VALID;


--
-- Name: wq_tutor_profile tutor_fk_degree_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_profile
    ADD CONSTRAINT tutor_fk_degree_id FOREIGN KEY (current_degree_id) REFERENCES bu.wq_degree(id) NOT VALID;


--
-- Name: wq_tutor_profile tutor_fk_late_policy_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_profile
    ADD CONSTRAINT tutor_fk_late_policy_id FOREIGN KEY (late_policy_id) REFERENCES bu.wq_late_policy(id) NOT VALID;


--
-- Name: wq_tutor_profile tutor_fk_school_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_profile
    ADD CONSTRAINT tutor_fk_school_id FOREIGN KEY (current_school_id) REFERENCES bu.wq_school(id) NOT VALID;


--
-- Name: wq_tutor_profile tutor_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_profile
    ADD CONSTRAINT tutor_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id) NOT VALID;


--
-- Name: wq_tutor_inquiry tutor_inquiry_fk_course_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_inquiry
    ADD CONSTRAINT tutor_inquiry_fk_course_id FOREIGN KEY (course_id) REFERENCES bu.wq_course(id) NOT VALID;


--
-- Name: wq_tutor_inquiry tutor_inquiry_fk_subject_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_inquiry
    ADD CONSTRAINT tutor_inquiry_fk_subject_id FOREIGN KEY (subject_id) REFERENCES bu.wq_subject(id);


--
-- Name: wq_tutor_inquiry tutor_inquiry_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_inquiry
    ADD CONSTRAINT tutor_inquiry_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_tutor_subject tutor_subject_fk_subject_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_subject
    ADD CONSTRAINT tutor_subject_fk_subject_id FOREIGN KEY (subject_id) REFERENCES bu.wq_subject(id);


--
-- Name: wq_tutor_subject tutor_subject_fk_tutor_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_tutor_subject
    ADD CONSTRAINT tutor_subject_fk_tutor_id FOREIGN KEY (tutor_id) REFERENCES bu.wq_tutor_profile(id);


--
-- Name: wq_user_follower user_followers_fk_follower_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_user_follower
    ADD CONSTRAINT user_followers_fk_follower_id FOREIGN KEY (follower_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_user_follower user_followers_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_user_follower
    ADD CONSTRAINT user_followers_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: wq_user_profile user_profile_fk_school_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_user_profile
    ADD CONSTRAINT user_profile_fk_school_id FOREIGN KEY (id) REFERENCES bu.wq_school(id) NOT VALID;


--
-- Name: wq_user_subject user_subject_fk_subject_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_user_subject
    ADD CONSTRAINT user_subject_fk_subject_id FOREIGN KEY (subject_id) REFERENCES bu.wq_subject(id);


--
-- Name: wq_user_subject user_subject_fk_user_id; Type: FK CONSTRAINT; Schema: bu; Owner: quanzi_admin
--

ALTER TABLE ONLY bu.wq_user_subject
    ADD CONSTRAINT user_subject_fk_user_id FOREIGN KEY (user_id) REFERENCES bu.wq_user_profile(id);


--
-- Name: SCHEMA bu; Type: ACL; Schema: -; Owner: wequan_admin
--

REVOKE ALL ON SCHEMA bu FROM wequan_admin;
GRANT ALL ON SCHEMA bu TO wequan_admin WITH GRANT OPTION;


--
-- PostgreSQL database dump complete
--

