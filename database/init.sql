CREATE DATABASE db_cy_application_d;

CREATE TABLE "public"."applications" (
                                  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
                                  "document_number" varchar NOT NULL,
                                  "amount" float8 NOT NULL,
                                  "term" integer NOT NULL,
                                  "loan_type" varchar NOT NULL,
                                  "state" varchar NOT NULL,
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

INSERT INTO "public"."applications" ("id", "document_number", "amount", "term", "loan_type", "state", "active") VALUES
    (gen_random_uuid(), '45678965', 1000000, 6, 'HIPOTECARIO','SOLICITADO', TRUE);

INSERT INTO "public"."cat_loan_type" ("id", "code", "name", "active") VALUES (gen_random_uuid(), 'PER', 'Préstamo Personal', TRUE);
INSERT INTO "public"."cat_loan_type" ("id", "code", "name", "active") VALUES (gen_random_uuid(), 'HIP', 'Préstamo Hipotecario', TRUE);
INSERT INTO "public"."cat_loan_type" ("id", "code", "name", "active") VALUES (gen_random_uuid(), 'EST', 'Préstamo de Estudios', TRUE);

INSERT INTO "public"."cat_loan_type" ("id", "code", "name", "active") VALUES (gen_random_uuid(), 'COM', 'Préstamo Comercial', TRUE);
INSERT INTO "public"."cat_loan_type" ("id", "code", "name", "active") VALUES (gen_random_uuid(), 'MIC', 'Préstamo Microcrédito', TRUE);
INSERT INTO "public"."cat_loan_type" ("id", "code", "name", "active") VALUES (gen_random_uuid(), 'PYM', 'Préstamo PYMES', TRUE);