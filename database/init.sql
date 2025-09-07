CREATE DATABASE db_cy_application_d;

CREATE TABLE "public"."applications" (
                                  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
                                  "document_number" varchar NOT NULL,
                                  "amount" float8 NOT NULL,
                                  "term" integer NOT NULL,
                                  "loan_type_id" uuid NOT NULL,
                                  "state_id" uuid NOT NULL,
                                  "active" bool NOT NULL DEFAULT true,
                                  PRIMARY KEY ("id")
);

CREATE TABLE "public"."cat_loan_type" (
                                      "id" uuid NOT NULL DEFAULT gen_random_uuid(),
                                      "code" varchar NOT NULL,
                                      "name" varchar NOT NULL,
                                      "active" bool NOT NULL DEFAULT true,
                                      PRIMARY KEY ("id")
);

INSERT INTO "public"."cat_loan_type" ("id", "code", "name", "active") VALUES (gen_random_uuid(), 'PER', 'Préstamo Personal', TRUE);
INSERT INTO "public"."cat_loan_type" ("id", "code", "name", "active") VALUES (gen_random_uuid(), 'HIP', 'Préstamo Hipotecario', TRUE);
INSERT INTO "public"."cat_loan_type" ("id", "code", "name", "active") VALUES (gen_random_uuid(), 'EST', 'Préstamo de Estudios', TRUE);
INSERT INTO "public"."cat_loan_type" ("id", "code", "name", "active") VALUES (gen_random_uuid(), 'COM', 'Préstamo Comercial', TRUE);
INSERT INTO "public"."cat_loan_type" ("id", "code", "name", "active") VALUES (gen_random_uuid(), 'MIC', 'Préstamo Microcrédito', TRUE);
INSERT INTO "public"."cat_loan_type" ("id", "code", "name", "active") VALUES (gen_random_uuid(), 'PYM', 'Préstamo PYMES', TRUE);


CREATE TABLE "public"."cat_state_application" (
                                          "id" uuid NOT NULL DEFAULT gen_random_uuid(),
                                          "code" varchar NOT NULL,
                                          "name" varchar NOT NULL,
                                          "active" bool NOT NULL DEFAULT true,
                                          PRIMARY KEY ("id")
);


INSERT INTO "public"."cat_state_application" ("id", "code", "name", "active") VALUES (gen_random_uuid(), 'PEN', 'Pendiente', TRUE);
INSERT INTO "public"."cat_state_application" ("id", "code", "name", "active") VALUES (gen_random_uuid(), 'PRE', 'Pre Aprobado', TRUE);
INSERT INTO "public"."cat_state_application" ("id", "code", "name", "active") VALUES (gen_random_uuid(), 'APR', 'Aprobado', TRUE);
INSERT INTO "public"."cat_state_application" ("id", "code", "name", "active") VALUES (gen_random_uuid(), 'REC', 'Rechazado', TRUE);

