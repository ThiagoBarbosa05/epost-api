CREATE TABLE users (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    profile_img TEXT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);