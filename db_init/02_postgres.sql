-- Drop tables if exist (for clean Docker initialization)
DROP TABLE IF EXISTS public.applications;
DROP TABLE IF EXISTS public.pets;

-- Pets table with auto-generated IDs
CREATE TABLE public.pets ( 
    pet_id BIGSERIAL PRIMARY KEY,
    age INT NOT NULL,
    breed VARCHAR(255),
    category VARCHAR(255),
    color VARCHAR(255),
    gender VARCHAR(255),
    image_url VARCHAR(255),
    location VARCHAR(255),
    name VARCHAR(255),
    weight FLOAT8 NOT NULL
);

-- Applications table referencing pets
CREATE TABLE public.applications (
    application_id BIGSERIAL PRIMARY KEY,
    city VARCHAR(255),
    created_at TIMESTAMP,
    description VARCHAR(1000),
    email VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(255),
    pet_id BIGINT NOT NULL,
    status VARCHAR(255),
    CONSTRAINT fk_pet FOREIGN KEY (pet_id) REFERENCES public.pets(pet_id)
);

-- Insert pets first (IDs auto-generated)
INSERT INTO public.pets (age, breed, category, color, gender, image_url, location, name, weight) VALUES
(3, 'Labrador', 'DOG', 'Brown', 'Male', '/images/fido.jpg', 'Kigali', 'Fido', 15.5),
(2, 'Siamese', 'CAT', 'White', 'Female', '/images/whiskers.jpeg', 'Turin', 'Whiskers', 5),
(3, 'Corgi', 'DOG', 'Brown', 'Female', '/images/peggy.jpeg', 'Turin', 'Peggy', 25),
(5, 'Golden retriever', 'DOG', 'Brown', 'Female', '/images/max.jpeg', 'Kigali', 'Max', 25);

-- Insert applications using valid pet_ids
-- Here we assume auto-generated pet_ids are 1,2,3,4
INSERT INTO public.applications (city, created_at, description, email, first_name, last_name, phone_number, pet_id, status) VALUES
('Turin', '2025-08-19 20:18:38', 'I am good with pets', 'paolo@gmail.com', 'Paolo', 'Rossi', '1234567890', 3, NULL),
('Berlin', '2025-08-19 20:18:38', 'I love pets', 'john@gmail.com', 'John', 'Doe', '0987654321', 2, NULL),
('Milano', '2025-08-20 00:59:48', 'I would like more info on this pet!', 'marina@example.com', 'Marina', 'Brown', '326475857', 3, NULL),
('Torino', '2025-08-19 20:13:12', 'I would love to adopt this pet!', 'alice@example.com', 'Alice', 'Brown', '3201234567', 2, 'ACCEPTED'),
('Torino', '2025-08-22 00:13:30', 'test2', 'rmutagoma1@gmail.com', 'Raissa', 'Mutagoma', NULL, 1, 'NEW'),
('Torino', '2025-08-22 14:32:50', 'test2', 'niwemarine@gmail.com', 'Marina', 'marina', NULL, 1, 'IN_REVIEW'),
('Kigali', '2025-08-19 23:58:58', 'I really want to adopt this pet.', 'john.doe@example.com', 'John', 'Doe', '123456789', 2, 'IN_REVIEW'),
('Torino', '2025-08-20 01:15:19', 'I am interested in this pet.', 'rmutagoma@gmail.com', 'Nicole', 'Nicoleta', '3201234567', 1, 'ACCEPTED');
