-- Localisations INTELLCAP (CDC : FRA, LUX, JPN, MOR)
INSERT INTO locations (country, city, address, latitude, longitude, country_code, email, active)
VALUES
  ('France',     'Paris',      '15 Rue de la Paix, 75001 Paris',         48.8698,   2.3309,  'FRA', 'france@intellcap.fr',     true),
  ('Luxembourg', 'Luxembourg', '5 Avenue J.F. Kennedy, L-1855',          49.6116,   6.1319,  'LUX', 'luxembourg@intellcap.fr', true),
  ('Japan',      'Tokyo',      '1-1 Marunouchi, Chiyoda-ku, Tokyo',      35.6812,  139.7671, 'JPN', 'japan@intellcap.fr',      true),
  ('Morocco',    'Rabat',      'Avenue Mohammed V, Rabat',               33.9716,  -6.8498,  'MOR', 'maroc@intellcap.fr',      true)
ON CONFLICT DO NOTHING;

-- Programmes écosystème
INSERT INTO ecosystem_programs (name, description, external_url, active)
VALUES
('Elev8',       'Programme d acceleration de startups Deep Tech',         'https://innovdays.intellcap.eu/elev8',       true),
('InnovDays',   'Evenements d innovation et de networking international', 'https://innovdays.intellcap.eu/',   true),
('Constellium', 'Reseau de partenaires strategiques et investisseurs',    'https://constellium.intellcap.fr', true),
('Startups',    'Portfolio et incubation de startups technologiques',     'https://startups.intellcap.fr',    true)
ON CONFLICT DO NOTHING;

-- Projets phares
INSERT INTO featured_projects (name, domain, description, active)
VALUES
('Space Ambition',                 'Aerospatiale', 'Technologies spatiales disruptives pour la conquete de l espace', true),
('Air Robot Taxi',                 'Mobilite',     'Mobilite urbaine autonome par drone taxi intelligent',            true),
('NexGen Quantum System',          'Quantique',    'Informatique et capteurs quantiques nouvelle generation',         true),
('Health & Performance in Soccer', 'Sport & Tech', 'Technologies appliquees a la performance sportive',              true)
ON CONFLICT DO NOTHING;

-- Services consulting (demande client point 2)
INSERT INTO consulting_services (title, description, domain, active)
VALUES
('Technology Due Diligence', 'Evaluation technique approfondie de vos projets et actifs technologiques',          'STRATEGY',   true),
('Research & Technology',    'Accompagnement en R&T pour accelerer vos cycles d innovation',                     'AI',         true),
('Modeling & Design',        'Conception et modelisation de systemes complexes',                                  'AEROSPACE',  true),
('Digital Twin',             'Creation de jumeaux numeriques pour simulation et optimisation',                    'QUANTUM',    true),
('Prototyping',              'Developpement rapide de prototypes fonctionnels',                                   'ROBOTICS',   true),
('Tech Industrialization',   'Transfert technologique et industrialisation de vos innovations',                   'STRATEGY',   true),
('IP Management',            'Gestion et valorisation de votre portefeuille de propriete intellectuelle',         'STRATEGY',   true),
('Risk Management',          'Identification et mitigation des risques technologiques et operationnels',          'STRATEGY',   true)
ON CONFLICT DO NOTHING;
TRUNCATE locations, consulting_services, featured_projects, ecosystem_programs RESTART IDENTITY CASCADE;