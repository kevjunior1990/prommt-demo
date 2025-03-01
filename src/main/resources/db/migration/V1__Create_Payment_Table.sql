-- PostgreSQL database dump

-- Name: prommt_payment_seq; Type: SEQUENCE; Schema: public; Owner: current_user

CREATE SEQUENCE public.prommt_payment_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.prommt_payment_seq OWNER TO current_user;

-- Name: prommt_payment; Type: TABLE; Schema: public; Owner: current_user

CREATE TABLE public.prommt_payment (
                                       id BIGINT NOT NULL DEFAULT NEXTVAL('public.prommt_payment_seq'),
                                       payer_email CHARACTER VARYING(255) NOT NULL,
                                       status CHARACTER VARYING(50) NOT NULL,
                                       currency CHARACTER VARYING(10) NOT NULL,
                                       amount NUMERIC(19, 4) NOT NULL,
                                       created_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                       paid_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                       PRIMARY KEY (id)
);

ALTER TABLE public.prommt_payment OWNER TO current_user;

-- Add any additional constraints or indexes if required
