-- Test podaci za Explore funkcionalnost

-- Dodaj destinacije (samo ako već ne postoje)
INSERT INTO destination (name, country, region, city, description, image, latitude, longitude, created_at, updated_at) VALUES
('Fruška Gora', 'Srbija', 'Vojvodina', 'Novi Sad', 'Nacionalni park sa prelepim hiking trail-ovima', 'https://example.com/fruska-gora.jpg', 45.1234, 19.5678, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Kopaonik', 'Srbija', 'Centralna Srbija', 'Kraljevo', 'Najviši planinski masiv u Srbiji', 'https://example.com/kopaonik.jpg', 43.2345, 20.6789, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tara', 'Srbija', 'Zapadna Srbija', 'Bajina Bašta', 'Planina sa prelepim vidikovcima', 'https://example.com/tara.jpg', 43.3456, 19.7890, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Zlatibor', 'Srbija', 'Zapadna Srbija', 'Čajetina', 'Popularna turistička destinacija', 'https://example.com/zlatibor.jpg', 43.4567, 19.8901, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;

-- Dodaj aktivnosti ako ne postoje
INSERT INTO activity (name, description, created_at) VALUES
('Hiking', 'Pešačenje po planinskim stazama', CURRENT_TIMESTAMP),
('Biking', 'Biciklizam po trail-ovima', CURRENT_TIMESTAMP),
('Running', 'Trčanje po prirodnim stazama', CURRENT_TIMESTAMP),
('Walking', 'Lagano pešačenje', CURRENT_TIMESTAMP)
ON CONFLICT (name) DO NOTHING;

-- Dodaj trail-ove (samo ako već ne postoje)
INSERT INTO trail (name, destination_id, length, elevation_gain, estimated_time, trail_type, difficulty, description, latitude, longitude, center_latitude, center_longitude, coordinates, main_image, rating, review_count, best_time, dogs_allowed, active, created_at, updated_at) VALUES
('Stara Planina Trail', 1, 8.5, 450, 180, 'loop', 'moderate', 'Prelep trail kroz šumu sa vidikovcima', 45.1234, 19.5678, 45.1234, 19.5678, '[{"lat": 45.1234, "lng": 19.5678}, {"lat": 45.1334, "lng": 19.5778}]', 'https://example.com/trail1.jpg', 4.2, 15, 'Spring, Fall', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Vrh Fruške Gore', 1, 12.0, 650, 240, 'out-and-back', 'hard', 'Težak trail do najvišeg vrha', 45.1334, 19.5778, 45.1334, 19.5778, '[{"lat": 45.1334, "lng": 19.5778}, {"lat": 45.1434, "lng": 19.5878}]', 'https://example.com/trail2.jpg', 4.5, 8, 'Summer', false, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Kopaonik Summit', 2, 15.0, 800, 300, 'linear', 'hard', 'Trail do vrha Kopaonika', 43.2345, 20.6789, 43.2345, 20.6789, '[{"lat": 43.2345, "lng": 20.6789}, {"lat": 43.2445, "lng": 20.6889}]', 'https://example.com/trail3.jpg', 4.8, 22, 'Summer', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tara Viewpoint', 3, 6.0, 300, 120, 'loop', 'easy', 'Lagani trail sa prelepim vidikovcima', 43.3456, 19.7890, 43.3456, 19.7890, '[{"lat": 43.3456, "lng": 19.7890}, {"lat": 43.3556, "lng": 19.7990}]', 'https://example.com/trail4.jpg', 4.0, 12, 'All year', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Zlatibor Lake Trail', 4, 4.5, 150, 90, 'loop', 'easy', 'Trail oko jezera na Zlatiboru', 43.4567, 19.8901, 43.4567, 19.8901, '[{"lat": 43.4567, "lng": 19.8901}, {"lat": 43.4667, "lng": 19.9001}]', 'https://example.com/trail5.jpg', 3.8, 18, 'Spring, Summer', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;

-- Poveži trail-ove sa aktivnostima (samo ako već ne postoje)
INSERT INTO trail_activity (trail_id, activity_id) VALUES
(1, 1), -- Stara Planina Trail - Hiking
(1, 4), -- Stara Planina Trail - Walking
(2, 1), -- Vrh Fruške Gore - Hiking
(3, 1), -- Kopaonik Summit - Hiking
(3, 2), -- Kopaonik Summit - Biking
(4, 1), -- Tara Viewpoint - Hiking
(4, 4), -- Tara Viewpoint - Walking
(5, 1), -- Zlatibor Lake Trail - Hiking
(5, 4) -- Zlatibor Lake Trail - Walking
ON CONFLICT (trail_id, activity_id) DO NOTHING; 