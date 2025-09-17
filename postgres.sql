
DROP TABLE IF EXISTS "public"."applications" CASCADE;
DROP TABLE IF EXISTS "public"."pets" CASCADE;


CREATE TABLE "public"."pets" (
    "pet_id" int8 NOT NULL,
    "age" int4 NOT NULL,
    "breed" varchar(255),
    "category" varchar(255),
    "color" varchar(255),
    "gender" varchar(255),
    "image_url" varchar(255),
    "location" varchar(255),
    "name" varchar(255),
    "weight" float8 NOT NULL,
    PRIMARY KEY ("pet_id")
);

CREATE TABLE "public"."applications" (
    "application_id" int8 NOT NULL,
    "city" varchar(255),
    "created_at" timestamp,
    "description" varchar(1000),
    "email" varchar(255),
    "first_name" varchar(255),
    "last_name" varchar(255),
    "phone_number" varchar(255),
    "pet_id" int8 NOT NULL,
    "status" varchar(255),
    CONSTRAINT "fkdq4qd0nsks530qdvppgdd1ui3" FOREIGN KEY ("pet_id") REFERENCES "public"."pets"("pet_id"),
    PRIMARY KEY ("application_id")
);


INSERT INTO "public"."pets" ("pet_id", "age", "breed", "category", "color", "gender", "image_url", "location", "name", "weight") VALUES
(1, 3, 'Labrador', 'DOG', 'Brown', 'Male', '/images/fido.jpg', 'Kigali', 'Fido', 15.5),
(4, 2, 'Siamese', 'CAT', 'White', 'Female', '/images/whiskers.jpeg', 'Turin', 'Whiskers', 5),
(6, 2, 'Siamese', 'CAT', 'White', 'Female', '/images/whiskers.jpeg', 'Turin', 'Whiskers', 5),
(5, 3, 'Corgi', 'DOG', 'Brown', 'Female', '/images/peggy.jpeg', 'Turin', 'Peggy', 25),
(7, 5, 'Golden retriever', 'DOG', 'Brown', 'Female', '/images/max.jpeg', 'Kigali', 'Max', 25);


INSERT INTO "public"."applications" ("application_id", "city", "created_at", "description", "email", "first_name", "last_name", "phone_number", "pet_id", "status") VALUES
(3, 'Turin', '2025-08-19 20:18:38.058935', 'I am good with pets', 'paolo@gmail.com', 'Paolo', 'Rossi', '1234567890', 5, NULL),
(4, 'Berlin', '2025-08-19 20:18:38.058935', 'I love pets', 'john@gmail.com', 'John', 'Doe', '0987654321', 6, NULL),
(6, 'Milano', '2025-08-20 00:59:48.878526', 'I would like more info on this pet!', 'marina@example.com', 'Marina', 'Brown', '326475857', 5, NULL),
(2, 'Torino', '2025-08-19 20:13:12.268394', 'I would love to adopt this pet!', 'alice@example.com', 'Alice', 'Brown', '3201234567', 4, 'ACCEPTED'),
(9, 'Torino', '2025-08-22 00:13:30.891827', 'test2', 'rmutagoma1@gmail.com', 'Raissa', 'Mutagoma', NULL, 1, 'NEW'),
(10, 'Torino', '2025-08-22 14:32:50.203707', 'test2', 'niwemarine@gmail.com', 'Marina', 'marina', NULL, 1, 'IN_REVIEW'),
(5, 'Kigali', '2025-08-19 23:58:58.918392', 'I really want to adopt this pet.', 'john.doe@example.com', 'John', 'Doe', '123456789', 6, 'IN_REVIEW'),
(7, 'Torino', '2025-08-20 01:15:19.131371', 'I am interested in this pet.', 'rmutagoma@gmail.com', 'Nicole', 'Nicoleta', '3201234567', 1, 'ACCEPTED'),
(13, 'Torino', '2025-09-12 20:50:45.551403', 'test2', 'rmutagoma1@gmail.com', 'Raissa', 'Mutagoma', '3519267250', 1, 'NEW'),
(12, 'Torino', '2025-09-12 17:14:00.641675', 'test', 'rmutagoma@gmail.com', 'Raissa', 'Mutagoma', '3519267250', 7, 'ACCEPTED'),
(14, 'Torino', '2025-09-12 22:18:43.375909', '', 'rmutagoma1@gmail.com', 'Raissa', 'Mutagoma', '3519267250', 1, 'NEW'),
(15, 'KIGALI', '2025-09-13 16:50:38.596418', 'test3', 'rmutagoma@gmail.com', 'R', 'Mutagoma', '0791846202', 7, 'NEW'),
(16, 'Torino', '2025-09-13 19:17:58.349945', '', 'rmutagoma@gmail.com', 'Raissa', 'Mutagoma', '3519267250', 1, 'NEW'),
(17, 'Torino', '2025-09-13 21:02:11.99834', 'test111', 'rmutagoma@gmail.com', 'Raissa', 'Mutagoma', '3519267250', 7, 'NEW');
