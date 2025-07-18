-- Render Migration Script za Explore funkcionalnost
-- Ova skripta će se pokrenuti na Render deployment

-- 1. Dodaj center koordinate u trail tabelu
ALTER TABLE trail ADD COLUMN IF NOT EXISTS center_latitude NUMERIC(10, 8);
ALTER TABLE trail ADD COLUMN IF NOT EXISTS center_longitude NUMERIC(11, 8);

-- 2. Dodaj coordinates polje za JSON array koordinata
ALTER TABLE trail ADD COLUMN IF NOT EXISTS coordinates TEXT;

-- 3. Indeksi za bolje performanse
CREATE INDEX IF NOT EXISTS idx_trail_destination_id ON trail(destination_id);
CREATE INDEX IF NOT EXISTS idx_trail_center_coordinates ON trail(center_latitude, center_longitude);
CREATE INDEX IF NOT EXISTS idx_destination_region ON destination(region);
CREATE INDEX IF NOT EXISTS idx_destination_coordinates ON destination(latitude, longitude);

-- 4. Dodaj test podaci ako ne postoje
INSERT INTO destination (name, country, region, city, description, image, latitude, longitude, created_at, updated_at) VALUES
('Fruška Gora', 'Srbija', 'Vojvodina', 'Novi Sad', 'Nacionalni park sa prelepim hiking trail-ovima', 'https://example.com/fruska-gora.jpg', 45.1234, 19.5678, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Kopaonik', 'Srbija', 'Centralna Srbija', 'Kraljevo', 'Najviši planinski masiv u Srbiji', 'https://example.com/kopaonik.jpg', 43.2345, 20.6789, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tara', 'Srbija', 'Zapadna Srbija', 'Bajina Bašta', 'Planina sa prelepim vidikovcima', 'https://example.com/tara.jpg', 43.3456, 19.7890, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Zlatibor', 'Srbija', 'Zapadna Srbija', 'Čajetina', 'Popularna turistička destinacija', 'https://example.com/zlatibor.jpg', 43.4567, 19.8901, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;

-- 5. Dodaj aktivnosti ako ne postoje
INSERT INTO activity (name, description, created_at) VALUES
('Hiking', 'Pešačenje po planinskim stazama', CURRENT_TIMESTAMP),
('Biking', 'Biciklizam po trail-ovima', CURRENT_TIMESTAMP),
('Running', 'Trčanje po prirodnim stazama', CURRENT_TIMESTAMP),
('Walking', 'Lagano pešačenje', CURRENT_TIMESTAMP)
ON CONFLICT (name) DO NOTHING;

-- 6. Dodaj trail-ove sa center koordinatama
INSERT INTO trail (name, destination_id, length, elevation_gain, estimated_time, trail_type, difficulty, description, latitude, longitude, center_latitude, center_longitude, coordinates, main_image, rating, review_count, best_time, dogs_allowed, active, created_at, updated_at) VALUES
('Stara Planina Trail', 1, 8.5, 450, 180, 'loop', 'moderate', 'Prelep trail kroz šumu sa vidikovcima', 45.1234, 19.5678, 45.1234, 19.5678, '[{"lat": 45.1234, "lng": 19.5678}, {"lat": 45.1334, "lng": 19.5778}]', 'https://example.com/trail1.jpg', 4.2, 15, 'Spring, Fall', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Vrh Fruške Gore', 1, 12.0, 650, 240, 'out-and-back', 'hard', 'Težak trail do najvišeg vrha', 45.1334, 19.5778, 45.1334, 19.5778, '[{"lat": 45.1334, "lng": 19.5778}, {"lat": 45.1434, "lng": 19.5878}]', 'https://example.com/trail2.jpg', 4.5, 8, 'Summer', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Kopaonik Summit', 2, 15.0, 800, 300, 'linear', 'hard', 'Trail do vrha Kopaonika', 43.2345, 20.6789, 43.2345, 20.6789, '[{"lat": 43.2345, "lng": 20.6789}, {"lat": 43.2445, "lng": 20.6889}]', 'https://example.com/trail3.jpg', 4.8, 22, 'Summer', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tara Viewpoint', 3, 6.0, 300, 120, 'loop', 'easy', 'Lagani trail sa prelepim vidikovcima', 43.3456, 19.7890, 43.3456, 19.7890, '[{"lat": 43.3456, "lng": 19.7890}, {"lat": 43.3556, "lng": 19.7990}]', 'https://example.com/trail4.jpg', 4.0, 12, 'All year', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Zlatibor Lake Trail', 4, 4.5, 150, 90, 'loop', 'easy', 'Trail oko jezera na Zlatiboru', 43.4567, 19.8901, 43.4567, 19.8901, '[{"lat": 43.4567, "lng": 19.8901}, {"lat": 43.4667, "lng": 19.9001}]', 'https://example.com/trail5.jpg', 3.8, 18, 'Spring, Summer', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;

-- 7. Poveži trail-ove sa aktivnostima
INSERT INTO trail_activity (trail_id, activity_id) VALUES
(1, 1), (1, 4), (2, 1), (3, 1), (3, 2), (4, 1), (4, 4), (5, 1), (5, 4)
ON CONFLICT (trail_id, activity_id) DO NOTHING;

-- 8. Komentari za dokumentaciju
COMMENT ON COLUMN trail.center_latitude IS 'Centar latitude za trail marker na mapi';
COMMENT ON COLUMN trail.center_longitude IS 'Centar longitude za trail marker na mapi';
COMMENT ON COLUMN trail.coordinates IS 'JSON array koordinata za rutu trail-a';
COMMENT ON COLUMN destination.region IS 'Regija destinacije za grupisanje na mapi';
COMMENT ON COLUMN destination.image IS 'URL slike destinacije za map marker'; 