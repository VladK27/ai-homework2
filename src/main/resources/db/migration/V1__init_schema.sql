-- Create auth table
CREATE TABLE auth (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Create geo table
CREATE TABLE geo (
    id BIGSERIAL PRIMARY KEY,
    lat VARCHAR(50) NOT NULL,
    lng VARCHAR(50) NOT NULL
);

-- Create address table
CREATE TABLE address (
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    suite VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    zipcode VARCHAR(20) NOT NULL,
    geo_id BIGINT REFERENCES geo(id)
);

-- Create company table
CREATE TABLE company (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    catch_phrase TEXT NOT NULL,
    bs TEXT NOT NULL
);

-- Create users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    address_id BIGINT REFERENCES address(id),
    phone VARCHAR(50) NOT NULL,
    website VARCHAR(255) NOT NULL,
    company_id BIGINT REFERENCES company(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_auth_email ON auth(email); 