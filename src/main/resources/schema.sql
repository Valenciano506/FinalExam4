-- Script de base de datos - Concesionario Lamborghini
-- @author Valenciano

CREATE TABLE IF NOT EXISTS users (
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    email        VARCHAR(150) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    role         VARCHAR(20)  NOT NULL DEFAULT 'USER',
    balance      FLOAT,
    dni          BIGINT,
    age          INTEGER,
    tlf          BIGINT,
    address      VARCHAR(255),
    license_type VARCHAR(10),
    nationality  VARCHAR(80)
);

CREATE TABLE IF NOT EXISTS products (
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(200) NOT NULL,
    description  TEXT,
    price        DOUBLE PRECISION NOT NULL,
    stock        INTEGER NOT NULL DEFAULT 0,
    category     VARCHAR(100) NOT NULL,
    year         INTEGER,
    horsepower   INTEGER,
    transmission VARCHAR(30),
    color        VARCHAR(50)
);

-- Admin por defecto (contraseña: admin123)
INSERT INTO users (name, email, password, role)
VALUES ('Admin', 'admin@lamborghini.com',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        'ADMIN')
ON CONFLICT (email) DO NOTHING;

-- Coches de ejemplo
INSERT INTO products (name, description, price, stock, category, year, horsepower, transmission, color)
VALUES
('Huracán EVO', 'El icónico superdeportivo de Lamborghini. Motor V10 atmosférico.', 215000, 3, 'Huracán', 2024, 640, 'Automático', 'Amarillo Belenus'),
('Huracán Sterrato', 'El primer Huracán off-road. Aventura sin límites.', 262000, 2, 'Huracán', 2024, 610, 'Automático', 'Grigio Lynx'),
('Urus S', 'El SUV súper deportivo. Potencia y lujo familiar.', 230000, 5, 'Urus', 2024, 666, 'Automático', 'Nero Noctis'),
('Urus Performante', 'La versión más extrema del Urus. Circuito y carretera.', 270000, 2, 'Urus', 2024, 666, 'Automático', 'Bianco Monocerus'),
('Revuelto', 'El sucesor del Aventador. Híbrido enchufable V12 + 3 motores eléctricos.', 517000, 1, 'Revuelto', 2024, 1015, 'Automático', 'Rosso Mars'),
('Temerario', 'El nuevo V8 híbrido. El futuro de Lamborghini.', 340000, 2, 'Temerario', 2025, 920, 'Automático', 'Blu Astraeus')
ON CONFLICT DO NOTHING;