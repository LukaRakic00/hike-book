-- SQL skripta za Explore funkcionalnost
-- Dodaj koordinate u trail tabelu

-- Dodaj center koordinate u trail tabelu
ALTER TABLE trail ADD COLUMN IF NOT EXISTS center_latitude NUMERIC(10, 8);
ALTER TABLE trail ADD COLUMN IF NOT EXISTS center_longitude NUMERIC(11, 8);

-- Dodaj coordinates polje za JSON array koordinata
ALTER TABLE trail ADD COLUMN IF NOT EXISTS coordinates TEXT;

-- Napomena: destination tabela već postoji sa svim potrebnim poljima
-- destination tabela ima: id, name, country, region, city, description, image, latitude, longitude, created_at, updated_at

-- Indeksi za bolje performanse
CREATE INDEX IF NOT EXISTS idx_trail_destination_id ON trail(destination_id);
CREATE INDEX IF NOT EXISTS idx_trail_center_coordinates ON trail(center_latitude, center_longitude);
CREATE INDEX IF NOT EXISTS idx_destination_region ON destination(region);
CREATE INDEX IF NOT EXISTS idx_destination_coordinates ON destination(latitude, longitude);

-- Komentari za dokumentaciju
COMMENT ON COLUMN trail.center_latitude IS 'Centar latitude za trail marker na mapi';
COMMENT ON COLUMN trail.center_longitude IS 'Centar longitude za trail marker na mapi';
COMMENT ON COLUMN trail.coordinates IS 'JSON array koordinata za rutu trail-a';
COMMENT ON COLUMN destination.region IS 'Regija destinacije za grupisanje na mapi';
COMMENT ON COLUMN destination.image IS 'URL slike destinacije za map marker'; 