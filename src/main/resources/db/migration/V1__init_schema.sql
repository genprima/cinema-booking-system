-- Insert sample data

-- Cities
INSERT INTO city (name, code, created_by, created_date, modified_by, modified_date, version)
VALUES 
('Jakarta', 'JKT', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Bandung', 'BDG', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Surabaya', 'SBY', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Medan', 'MDN', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Semarang', 'SMG', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Yogyakarta', 'JOG', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Malang', 'MLG', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Palembang', 'PLB', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Makassar', 'MKS', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Denpasar', 'DPS', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Manado', 'MND', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Balikpapan', 'BPP', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Padang', 'PDG', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Pekanbaru', 'PKB', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Banjarmasin', 'BJM', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Pontianak', 'PNK', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Samarinda', 'SMD', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Jayapura', 'JYP', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Ambon', 'AMB', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Mataram', 'MTR', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0);

-- Cinema
INSERT INTO cinema (name, address, cinema_code, created_by, created_date, modified_by, modified_date, version)
SELECT 
    cinema_name,
    address,
    cinema_code,
    'SYSTEM',
    CURRENT_TIMESTAMP,
    'SYSTEM',
    CURRENT_TIMESTAMP,
    0
FROM (
VALUES 
        -- Jakarta
        ('Cinema 21 Central Park', 'Central Park Mall Lt. 6, Jl. Letjen S. Parman Kav. 28, Jakarta Barat', 'C21JKTCPK'),
        ('Cinema 21 Grand Indonesia', 'Grand Indonesia Mall Lt. 3, Jl. M.H. Thamrin No.1, Jakarta Pusat', 'C21JKTGIN'),
        ('Cinema 21 Plaza Indonesia', 'Plaza Indonesia Lt. 5, Jl. M.H. Thamrin Kav. 28-30, Jakarta Pusat', 'C21JKTPIN'),
        ('Cinemaxx Pacific Place', 'Pacific Place Mall Lt. 5, Jl. Jend. Sudirman Kav. 52-53, Jakarta Selatan', 'CXXJKTPCP'),
        ('CGV Senayan City', 'Senayan City Mall Lt. 6, Jl. Asia Afrika No. 19, Jakarta Pusat', 'CGVJKTSNC'),
        
        -- Bandung
        ('Cinema 21 Paris Van Java', 'Paris Van Java Mall Lt. 3, Jl. Sukajadi No. 131-139, Bandung', 'C21BDGPVJ'),
        ('Cinema 21 Trans Studio Mall', 'Trans Studio Mall Lt. 2, Jl. Gatot Subroto No. 289, Bandung', 'C21BDGTSM'),
        ('Cinema 21 Festival Citylink', 'Festival Citylink Mall Lt. 3, Jl. Peta No. 241, Bandung', 'C21BDGFCL'),
        ('Cinemaxx Cihampelas Walk', 'Cihampelas Walk Mall Lt. 2, Jl. Cihampelas No. 160, Bandung', 'CXXBDGCWL'),
        ('CGV Bandung Indah Plaza', 'Bandung Indah Plaza Lt. 3, Jl. Merdeka No. 56, Bandung', 'CGVBDGBIP'),
        
        -- Surabaya
        ('Cinema 21 Tunjungan Plaza', 'Tunjungan Plaza Mall Lt. 4, Jl. Basuki Rahmat No. 8-12, Surabaya', 'C21SBYTPZ'),
        ('Cinema 21 Grand City', 'Grand City Mall Lt. 3, Jl. Walikota Mustajab No. 1, Surabaya', 'C21SBYGRC'),
        ('Cinema 21 Galaxy Mall', 'Galaxy Mall Lt. 4, Jl. Dharmahusada Indah Timur No. 35-37, Surabaya', 'C21SBYGXY'),
        ('Cinemaxx Pakuwon Mall', 'Pakuwon Mall Lt. 3, Jl. Puncak Indah Lontar No. 2, Surabaya', 'CXXSBYPKW'),
        ('CGV Royal Plaza', 'Royal Plaza Mall Lt. 2, Jl. Basuki Rahmat No. 16-18, Surabaya', 'CGVSBYRPZ'),
        
        -- Medan
        ('Cinema 21 Sun Plaza', 'Sun Plaza Mall Lt. 3, Jl. Zainul Arifin No. 7, Medan', 'C21MDNSPZ'),
        ('Cinema 21 Centre Point', 'Centre Point Mall Lt. 2, Jl. Timor No. 18, Medan', 'C21MDNCPT'),
        ('Cinema 21 Thamrin Plaza', 'Thamrin Plaza Mall Lt. 3, Jl. Thamrin No. 124, Medan', 'C21MDNTPZ'),
        ('Cinemaxx Medan Mall', 'Medan Mall Lt. 2, Jl. MT Haryono No. 17, Medan', 'CXXMDNMML'),
        ('CGV Cambridge City Square', 'Cambridge City Square Mall Lt. 3, Jl. S. Parman No. 217, Medan', 'CGVMDNCCS'),
        
        -- Semarang
        ('Cinema 21 Paragon Mall', 'Paragon Mall Lt. 4, Jl. Pemuda No. 118, Semarang', 'C21SMGPGN'),
        ('Cinema 21 DP Mall', 'DP Mall Lt. 3, Jl. Pemuda No. 150, Semarang', 'C21SMGDPM'),
        ('Cinema 21 Ciputra Mall', 'Ciputra Mall Lt. 2, Jl. Simpang Lima No. 1, Semarang', 'C21SMGCPT'),
        ('Cinemaxx Java Supermall', 'Java Supermall Lt. 3, Jl. MT Haryono No. 992, Semarang', 'CXXSMGJSM'),
        ('CGV Transmart', 'Transmart Mall Lt. 2, Jl. MT Haryono No. 1234, Semarang', 'CGVSMGTRM'),
        
        -- Yogyakarta
        ('Cinema 21 Malioboro Mall', 'Malioboro Mall Lt. 3, Jl. Malioboro No. 52-58, Yogyakarta', 'C21JOGMLB'),
        ('Cinema 21 Hartono Mall', 'Hartono Mall Lt. 2, Jl. Ring Road Utara No. 123, Yogyakarta', 'C21JOGHRT'),
        ('Cinema 21 Galeria Mall', 'Galeria Mall Lt. 3, Jl. Jend. Sudirman No. 99, Yogyakarta', 'C21JOGGLR'),
        ('Cinemaxx City Walk', 'City Walk Mall Lt. 2, Jl. Magelang No. 45, Yogyakarta', 'CXXJOGCWK'),
        ('CGV Plaza Ambarrukmo', 'Plaza Ambarrukmo Lt. 3, Jl. Laksda Adisucipto No. 1, Yogyakarta', 'CGVJOGAMB'),
        
        -- Malang
        ('Cinema 21 Sahid Raya', 'Sahid Raya Mall Lt. 2, Jl. Veteran No. 1, Malang', 'C21MLGSHD'),
        ('Cinema 21 Olympic Garden', 'Olympic Garden Mall Lt. 3, Jl. MT Haryono No. 10, Malang', 'C21MLGOLY'),
        ('Cinema 21 Mall Malang', 'Mall Malang Lt. 2, Jl. Veteran No. 2, Malang', 'C21MLGMML'),
        ('Cinemaxx Plaza Malang', 'Plaza Malang Lt. 3, Jl. Veteran No. 3, Malang', 'CXXMLGPLZ'),
        ('CGV Transmart Malang', 'Transmart Malang Lt. 2, Jl. Veteran No. 4, Malang', 'CGVMLGTRM'),
        
        -- Palembang
        ('Cinema 21 Palembang Square', 'Palembang Square Mall Lt. 3, Jl. Angkatan 45 No. 1, Palembang', 'C21PLBSQR'),
        ('Cinema 21 Palembang Icon', 'Palembang Icon Mall Lt. 2, Jl. Letkol Iskandar No. 1, Palembang', 'C21PLBICN'),
        ('Cinema 21 Palembang Trade Center', 'Palembang Trade Center Mall Lt. 3, Jl. Demang Lebar Daun No. 1, Palembang', 'C21PLBPTC'),
        ('Cinemaxx Palembang Indah Mall', 'Palembang Indah Mall Lt. 2, Jl. Veteran No. 1, Palembang', 'CXXPLBIND'),
        ('CGV Palembang City Center', 'Palembang City Center Mall Lt. 3, Jl. Veteran No. 2, Palembang', 'CGVPLBCCC'),
        
        -- Makassar
        ('Cinema 21 Trans Studio Mall', 'Trans Studio Mall Lt. 2, Jl. Metro Tanjung Bunga, Makassar', 'C21MKSTSM'),
        ('Cinema 21 Panakkukang Mall', 'Panakkukang Mall Lt. 3, Jl. Pengayoman No. 1, Makassar', 'C21MKSPNK'),
        ('Cinema 21 Nipah Mall', 'Nipah Mall Lt. 2, Jl. Nipah No. 1, Makassar', 'C21MKSNPH'),
        ('Cinemaxx Mall Ratu Indah', 'Mall Ratu Indah Lt. 3, Jl. Pengayoman No. 2, Makassar', 'CXXMKSRTI'),
        ('CGV Mall GTC', 'Mall GTC Lt. 2, Jl. Pengayoman No. 3, Makassar', 'CGVMKSGTC'),
        
        -- Denpasar
        ('Cinema 21 Beachwalk', 'Beachwalk Mall Lt. 3, Jl. Pantai Kuta No. 1, Denpasar', 'C21DPSBCH'),
        ('Cinema 21 Discovery Mall', 'Discovery Mall Lt. 2, Jl. Kartika Plaza No. 1, Denpasar', 'C21DPSDSC'),
        ('Cinema 21 Bali Collection', 'Bali Collection Mall Lt. 3, Jl. Ngurah Rai Bypass No. 1, Denpasar', 'C21DPSBLC'),
        ('Cinemaxx Bali Mall', 'Bali Mall Lt. 2, Jl. Sunset Road No. 1, Denpasar', 'CXXDPSBLM'),
        ('CGV Bali Galeria', 'Bali Galeria Mall Lt. 3, Jl. Sunset Road No. 2, Denpasar', 'CGVDPSBLG'),
        
        -- Manado
        ('Cinema 21 Manado Town Square', 'Manado Town Square Mall Lt. 2, Jl. Boulevard No. 1, Manado', 'C21MNDMTS'),
        ('Cinema 21 Mega Mall', 'Mega Mall Lt. 3, Jl. Piere Tendean No. 1, Manado', 'C21MNDMGM'),
        ('Cinema 21 IT Center', 'IT Center Mall Lt. 2, Jl. Piere Tendean No. 2, Manado', 'C21MNDITC'),
        ('Cinemaxx Bahagia Mall', 'Bahagia Mall Lt. 3, Jl. Piere Tendean No. 3, Manado', 'CXXMNDBHM'),
        ('CGV Manado Mall', 'Manado Mall Lt. 2, Jl. Piere Tendean No. 4, Manado', 'CGVMNDMML'),
        
        -- Balikpapan
        ('Cinema 21 Balikpapan Superblock', 'Balikpapan Superblock Mall Lt. 3, Jl. Sudirman No. 1, Balikpapan', 'C21BPPBLK'),
        ('Cinema 21 Balikpapan Trade Center', 'Balikpapan Trade Center Mall Lt. 2, Jl. Sudirman No. 2, Balikpapan', 'C21BPPBTC'),
        ('Cinema 21 Balikpapan City Center', 'Balikpapan City Center Mall Lt. 3, Jl. Sudirman No. 3, Balikpapan', 'C21BPPBCC'),
        ('Cinemaxx Balikpapan Plaza', 'Balikpapan Plaza Mall Lt. 2, Jl. Sudirman No. 4, Balikpapan', 'CXXBPPPLZ'),
        ('CGV Balikpapan Mall', 'Balikpapan Mall Lt. 3, Jl. Sudirman No. 5, Balikpapan', 'CGVBPPBML'),
        
        -- Padang
        ('Cinema 21 Padang City Center', 'Padang City Center Mall Lt. 2, Jl. Veteran No. 1, Padang', 'C21PDGCCC'),
        ('Cinema 21 Padang Trade Center', 'Padang Trade Center Mall Lt. 3, Jl. Veteran No. 2, Padang', 'C21PDGPTC'),
        ('Cinema 21 Padang Plaza', 'Padang Plaza Mall Lt. 2, Jl. Veteran No. 3, Padang', 'C21PDGPLZ'),
        ('Cinemaxx Padang Mall', 'Padang Mall Lt. 3, Jl. Veteran No. 4, Padang', 'CXXPDGMML'),
        ('CGV Padang Icon', 'Padang Icon Mall Lt. 2, Jl. Veteran No. 5, Padang', 'CGVPDGICN'),
        
        -- Pekanbaru
        ('Cinema 21 Pekanbaru Mall', 'Pekanbaru Mall Lt. 3, Jl. Sudirman No. 1, Pekanbaru', 'C21PKBMML'),
        ('Cinema 21 Pekanbaru Trade Center', 'Pekanbaru Trade Center Mall Lt. 2, Jl. Sudirman No. 2, Pekanbaru', 'C21PKBPTC'),
        ('Cinema 21 Pekanbaru City Center', 'Pekanbaru City Center Mall Lt. 3, Jl. Sudirman No. 3, Pekanbaru', 'C21PKBPCC'),
        ('Cinemaxx Pekanbaru Plaza', 'Pekanbaru Plaza Mall Lt. 2, Jl. Sudirman No. 4, Pekanbaru', 'CXXPKBPLZ'),
        ('CGV Pekanbaru Icon', 'Pekanbaru Icon Mall Lt. 3, Jl. Sudirman No. 5, Pekanbaru', 'CGVPKBICN'),
        
        -- Banjarmasin
        ('Cinema 21 Banjarmasin Mall', 'Banjarmasin Mall Lt. 2, Jl. A. Yani No. 1, Banjarmasin', 'C21BJMMML'),
        ('Cinema 21 Banjarmasin Trade Center', 'Banjarmasin Trade Center Mall Lt. 3, Jl. A. Yani No. 2, Banjarmasin', 'C21BJMPTC'),
        ('Cinema 21 Banjarmasin City Center', 'Banjarmasin City Center Mall Lt. 2, Jl. A. Yani No. 3, Banjarmasin', 'C21BJMPCC'),
        ('Cinemaxx Banjarmasin Plaza', 'Banjarmasin Plaza Mall Lt. 3, Jl. A. Yani No. 4, Banjarmasin', 'CXXBJMPLZ'),
        ('CGV Banjarmasin Icon', 'Banjarmasin Icon Mall Lt. 2, Jl. A. Yani No. 5, Banjarmasin', 'CGVBJMICN'),
        
        -- Pontianak
        ('Cinema 21 Pontianak Mall', 'Pontianak Mall Lt. 3, Jl. Gajah Mada No. 1, Pontianak', 'C21PNKMML'),
        ('Cinema 21 Pontianak Trade Center', 'Pontianak Trade Center Mall Lt. 2, Jl. Gajah Mada No. 2, Pontianak', 'C21PNKPTC'),
        ('Cinema 21 Pontianak City Center', 'Pontianak City Center Mall Lt. 3, Jl. Gajah Mada No. 3, Pontianak', 'C21PNKPCC'),
        ('Cinemaxx Pontianak Plaza', 'Pontianak Plaza Mall Lt. 2, Jl. Gajah Mada No. 4, Pontianak', 'CXXPNKPLZ'),
        ('CGV Pontianak Icon', 'Pontianak Icon Mall Lt. 3, Jl. Gajah Mada No. 5, Pontianak', 'CGVPNKICN'),
        
        -- Samarinda
        ('Cinema 21 Samarinda Mall', 'Samarinda Mall Lt. 2, Jl. Pangeran Samudera No. 1, Samarinda', 'C21SMDMML'),
        ('Cinema 21 Samarinda Trade Center', 'Samarinda Trade Center Mall Lt. 3, Jl. Pangeran Samudera No. 2, Samarinda', 'C21SMDPTC'),
        ('Cinema 21 Samarinda City Center', 'Samarinda City Center Mall Lt. 2, Jl. Pangeran Samudera No. 3, Samarinda', 'C21SMDPCC'),
        ('Cinemaxx Samarinda Plaza', 'Samarinda Plaza Mall Lt. 3, Jl. Pangeran Samudera No. 4, Samarinda', 'CXXSMDPLZ'),
        ('CGV Samarinda Icon', 'Samarinda Icon Mall Lt. 2, Jl. Pangeran Samudera No. 5, Samarinda', 'CGVSMDICN'),
        
        -- Jayapura
        ('Cinema 21 Jayapura Mall', 'Jayapura Mall Lt. 3, Jl. Sam Ratulangi No. 1, Jayapura', 'C21JYPMML'),
        ('Cinema 21 Jayapura Trade Center', 'Jayapura Trade Center Mall Lt. 2, Jl. Sam Ratulangi No. 2, Jayapura', 'C21JYPPTC'),
        ('Cinema 21 Jayapura City Center', 'Jayapura City Center Mall Lt. 3, Jl. Sam Ratulangi No. 3, Jayapura', 'C21JYPPCC'),
        ('Cinemaxx Jayapura Plaza', 'Jayapura Plaza Mall Lt. 2, Jl. Sam Ratulangi No. 4, Jayapura', 'CXXJYPPLZ'),
        ('CGV Jayapura Icon', 'Jayapura Icon Mall Lt. 3, Jl. Sam Ratulangi No. 5, Jayapura', 'CGVJYPICN'),
        
        -- Ambon
        ('Cinema 21 Ambon Mall', 'Ambon Mall Lt. 2, Jl. Pattimura No. 1, Ambon', 'C21AMBMML'),
        ('Cinema 21 Ambon Trade Center', 'Ambon Trade Center Mall Lt. 3, Jl. Pattimura No. 2, Ambon', 'C21AMBPTC'),
        ('Cinema 21 Ambon City Center', 'Ambon City Center Mall Lt. 2, Jl. Pattimura No. 3, Ambon', 'C21AMBPCC'),
        ('Cinemaxx Ambon Plaza', 'Ambon Plaza Mall Lt. 3, Jl. Pattimura No. 4, Ambon', 'CXXAMBPLZ'),
        ('CGV Ambon Icon', 'Ambon Icon Mall Lt. 2, Jl. Pattimura No. 5, Ambon', 'CGVAMBICN'),
        
        -- Mataram
        ('Cinema 21 Mataram Mall', 'Mataram Mall Lt. 3, Jl. Pejanggik No. 1, Mataram', 'C21MTRMML'),
        ('Cinema 21 Mataram Trade Center', 'Mataram Trade Center Mall Lt. 2, Jl. Pejanggik No. 2, Mataram', 'C21MTRPTC'),
        ('Cinema 21 Mataram City Center', 'Mataram City Center Mall Lt. 3, Jl. Pejanggik No. 3, Mataram', 'C21MTRPCC'),
        ('Cinemaxx Mataram Plaza', 'Mataram Plaza Mall Lt. 2, Jl. Pejanggik No. 4, Mataram', 'CXXMTRPLZ'),
        ('CGV Mataram Icon', 'Mataram Icon Mall Lt. 3, Jl. Pejanggik No. 5, Mataram', 'CGVMTRICN')
) AS cinemas(cinema_name, address, cinema_code);

-- City Cinema
INSERT INTO city_cinema (city_id, cinema_id, created_by, created_date, modified_by, modified_date, version)
SELECT 
    c.id as city_id,
    ci.id as cinema_id,
    'SYSTEM' as created_by,
    CURRENT_TIMESTAMP as created_date,
    'SYSTEM' as modified_by,
    CURRENT_TIMESTAMP as modified_date,
    0 as version
FROM city c
CROSS JOIN cinema ci
WHERE 
    CASE c.name
        WHEN 'Jakarta' THEN ci.cinema_code IN ('C21JKTCPK', 'C21JKTGIN', 'C21JKTPIN', 'CXXJKTPCP', 'CGVJKTSNC')
        WHEN 'Bandung' THEN ci.cinema_code IN ('C21BDGPVJ', 'C21BDGTSM', 'C21BDGFCL', 'CXXBDGCWL', 'CGVBDGBIP')
        WHEN 'Surabaya' THEN ci.cinema_code IN ('C21SBYTPZ', 'C21SBYGRC', 'C21SBYGXY', 'CXXSBYPKW', 'CGVSBYRPZ')
        WHEN 'Medan' THEN ci.cinema_code IN ('C21MDNSPZ', 'C21MDNCPT', 'C21MDNTPZ', 'CXXMDNMML', 'CGVMDNCCS')
        WHEN 'Semarang' THEN ci.cinema_code IN ('C21SMGPGN', 'C21SMGDPM', 'C21SMGCPT', 'CXXSMGJSM', 'CGVSMGTRM')
        WHEN 'Yogyakarta' THEN ci.cinema_code IN ('C21JOGMLB', 'C21JOGHRT', 'C21JOGGLR', 'CXXJOGCWK', 'CGVJOGAMB')
        WHEN 'Malang' THEN ci.cinema_code IN ('C21MLGSHD', 'C21MLGOLY', 'C21MLGMML', 'CXXMLGPLZ', 'CGVMLGTRM')
        WHEN 'Palembang' THEN ci.cinema_code IN ('C21PLBSQR', 'C21PLBICN', 'C21PLBPTC', 'CXXPLBIND', 'CGVPLBCCC')
        WHEN 'Makassar' THEN ci.cinema_code IN ('C21MKSTSM', 'C21MKSPNK', 'C21MKSNPH', 'CXXMKSRTI', 'CGVMKSGTC')
        WHEN 'Denpasar' THEN ci.cinema_code IN ('C21DPSBCH', 'C21DPSDSC', 'C21DPSBLC', 'CXXDPSBLM', 'CGVDPSBLG')
        WHEN 'Manado' THEN ci.cinema_code IN ('C21MNDMTS', 'C21MNDMGM', 'C21MNDITC', 'CXXMNDBHM', 'CGVMNDMML')
        WHEN 'Balikpapan' THEN ci.cinema_code IN ('C21BPPBLK', 'C21BPPBTC', 'C21BPPBCC', 'CXXBPPPLZ', 'CGVBPPBML')
        WHEN 'Padang' THEN ci.cinema_code IN ('C21PDGCCC', 'C21PDGPTC', 'C21PDGPLZ', 'CXXPDGMML', 'CGVPDGICN')
        WHEN 'Pekanbaru' THEN ci.cinema_code IN ('C21PKBMML', 'C21PKBPTC', 'C21PKBPCC', 'CXXPKBPLZ', 'CGVPKBICN')
        WHEN 'Banjarmasin' THEN ci.cinema_code IN ('C21BJMMML', 'C21BJMPTC', 'C21BJMPCC', 'CXXBJMPLZ', 'CGVBJMICN')
        WHEN 'Pontianak' THEN ci.cinema_code IN ('C21PNKMML', 'C21PNKPTC', 'C21PNKPCC', 'CXXPNKPLZ', 'CGVPNKICN')
        WHEN 'Samarinda' THEN ci.cinema_code IN ('C21SMDMML', 'C21SMDPTC', 'C21SMDPCC', 'CXXSMDPLZ', 'CGVSMDICN')
        WHEN 'Jayapura' THEN ci.cinema_code IN ('C21JYPMML', 'C21JYPPTC', 'C21JYPPCC', 'CXXJYPPLZ', 'CGVJYPICN')
        WHEN 'Ambon' THEN ci.cinema_code IN ('C21AMBMML', 'C21AMBPTC', 'C21AMBPCC', 'CXXAMBPLZ', 'CGVAMBICN')
        WHEN 'Mataram' THEN ci.cinema_code IN ('C21MTRMML', 'C21MTRPTC', 'C21MTRPCC', 'CXXMTRPLZ', 'CGVMTRICN')
    END;

-- Studio Layouts
INSERT INTO studio_layout (name, max_rows, max_columns, created_by, created_date, modified_by, modified_date, version)
VALUES 
('Layout 10x20', 10, 20, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Layout 15x30', 15, 30, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Layout 12x25', 12, 25, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0);

-- Studios
INSERT INTO studio (name, city_cinema_id, studio_layout_id, created_by, created_date, modified_by, modified_date, version)
SELECT 
    studio_name,
    cc.id as city_cinema_id,
    sl.id as studio_layout_id,
    'SYSTEM' as created_by,
    CURRENT_TIMESTAMP as created_date,
    'SYSTEM' as modified_by,
    CURRENT_TIMESTAMP as modified_date,
    0 as version
FROM city_cinema cc
JOIN (
    SELECT 
        name as studio_name,
        CASE name
            WHEN 'Premiere Hall' THEN 'Layout 10x20'
            WHEN 'Dolby Atmos' THEN 'Layout 15x30'
            WHEN 'IMAX' THEN 'Layout 12x25'
        END as layout_name
    FROM (VALUES 
        ('Premiere Hall'),
        ('Dolby Atmos'),
        ('IMAX')
    ) AS studios(name)
) s ON true
JOIN studio_layout sl ON sl.name = s.layout_name
ORDER BY cc.id, studio_name;

-- Movies
INSERT INTO movie (title, description, duration, synopsis, rating, director, created_by, created_date, modified_by, modified_date, version)
VALUES 
('The Matrix', 'A computer hacker learns about the true nature of reality', 150, 'A hacker discovers the world is a simulation and leads a rebellion.', 'R', 'Lana Wachowski', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Inception', 'A thief who steals corporate secrets through dream-sharing technology', 148, 'A skilled thief is given a chance at redemption if he can successfully perform inception.', 'PG-13', 'Christopher Nolan', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('The Dark Knight', 'When the menace known as the Joker wreaks havoc on Gotham City', 152, 'Batman faces the Joker, a criminal mastermind who plunges Gotham into chaos.', 'PG-13', 'Christopher Nolan', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Interstellar', 'A team of explorers travel through a wormhole in space', 169, 'Explorers travel through a wormhole in search of a new home for humanity.', 'PG-13', 'Christopher Nolan', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('The Shawshank Redemption', 'Two imprisoned men bond over a number of years', 142, 'Two men form a deep friendship while imprisoned, finding hope and redemption.', 'R', 'Frank Darabont', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Pulp Fiction', 'The lives of two mob hitmen, a boxer, a gangster and his wife intertwine', 154, 'Stories of crime and redemption intertwine in Los Angeles.', 'R', 'Quentin Tarantino', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('The Godfather', 'The aging patriarch of an organized crime dynasty transfers control to his reluctant son', 175, 'A crime drama about the Corleone family and their rise to power.', 'R', 'Francis Ford Coppola', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Forrest Gump', 'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal', 142, 'The life story of a man with a low IQ who witnesses and unwittingly influences several defining historical events.', 'PG-13', 'Robert Zemeckis', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('The Lord of the Rings', 'A meek Hobbit from the Shire and eight companions set out on a journey', 178, 'A group of heroes embark on a quest to destroy a powerful ring and save Middle-earth.', 'PG-13', 'Peter Jackson', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Fight Club', 'An insomniac office worker and a devil-may-care soapmaker form an underground fight club', 139, 'A man forms an underground fight club that evolves into something much bigger.', 'R', 'David Fincher', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('The Silence of the Lambs', 'A young FBI cadet must receive the help of an incarcerated cannibal killer', 118, 'A young FBI agent seeks the help of a brilliant but insane cannibal killer to catch another serial killer.', 'R', 'Jonathan Demme', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('The Green Mile', 'The lives of guards on Death Row are affected by one of their charges', 189, 'A supernatural tale set on death row in a Southern prison.', 'R', 'Frank Darabont', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('The Prestige', 'Two stage magicians engage in competitive one-upmanship', 130, 'Two rival magicians engage in increasingly dangerous competitions.', 'PG-13', 'Christopher Nolan', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('The Departed', 'An undercover cop and a mole in the police attempt to identify each other', 151, 'A story about the police and the mob in Boston, and an undercover cop and a mole in the police.', 'R', 'Martin Scorsese', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('The Pianist', 'A Polish Jewish musician struggles to survive the destruction of the Warsaw ghetto', 150, 'A true story of a pianist who survives the Holocaust.', 'R', 'Roman Polanski', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('The Lion King', 'Lion prince Simba and his father are targeted by his bitter uncle', 88, 'A young lion prince must overcome tragedy and take his rightful place as king.', 'G', 'Roger Allers', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Gladiator', 'A former Roman General sets out to exact vengeance against the corrupt emperor', 155, 'A Roman general seeks revenge against the emperor who murdered his family.', 'R', 'Ridley Scott', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('The Shining', 'A family heads to an isolated hotel for the winter', 146, 'A writer and his family are trapped in a haunted hotel during a snowstorm.', 'R', 'Stanley Kubrick', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('Back to the Future', 'A teenager is accidentally sent back in time', 116, 'A teenager travels back in time and must ensure his parents fall in love.', 'PG', 'Robert Zemeckis', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('The Good, the Bad and the Ugly', 'A bounty hunting scam joins two men in an uneasy alliance', 161, 'Three gunslingers compete to find a fortune in buried Confederate gold.', 'R', 'Sergio Leone', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0);

-- Insert seat types
INSERT INTO seat (seat_type, additional_price, created_by, created_date, modified_by, modified_date, version)
VALUES 
('REGULAR', 0, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('COMFORT', 50000, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0);

-- Insert studio seats for each studio
-- For studios with Layout 10x20
INSERT INTO studio_seat (studio_id, seat_id, row, number, x_coordinate, y_coordinate, created_by, created_date, modified_by, modified_date, version)
SELECT 
    s.id as studio_id,
    CASE 
        WHEN row_num < 5 THEN 1  -- First 5 rows are REGULAR
        ELSE 2                   -- Last 5 rows are COMFORT
    END as seat_id,
    CASE 
        WHEN row_num < 26 THEN CHR(65 + row_num)
        ELSE CHR(65 + (row_num - 26)/26) || CHR(65 + (row_num - 26)%26)
    END as row,
    col_num + 1 as number,
    col_num as x_coordinate,
    row_num as y_coordinate,
    'SYSTEM' as created_by,
    CURRENT_TIMESTAMP as created_date,
    'SYSTEM' as modified_by,
    CURRENT_TIMESTAMP as modified_date,
    0 as version
FROM (
    SELECT 
        a.N as row_num,
        b.N as col_num
    FROM (SELECT 0 as N UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) a,
         (SELECT 0 as N UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12 UNION SELECT 13 UNION SELECT 14 UNION SELECT 15 UNION SELECT 16 UNION SELECT 17 UNION SELECT 18 UNION SELECT 19) b
    WHERE a.N < 10 AND b.N < 20
    ORDER BY a.N, b.N
) numbers
CROSS JOIN studio s
JOIN studio_layout sl ON s.studio_layout_id = sl.id
WHERE sl.name = 'Layout 10x20';

-- For studios with Layout 15x30
INSERT INTO studio_seat (studio_id, seat_id, row, number, x_coordinate, y_coordinate, created_by, created_date, modified_by, modified_date, version)
SELECT 
    s.id as studio_id,
    CASE 
        WHEN row_num < 8 THEN 1  -- First 8 rows are REGULAR
        ELSE 2                   -- Last 7 rows are COMFORT
    END as seat_id,
    CASE 
        WHEN row_num < 26 THEN CHR(65 + row_num)
        ELSE CHR(65 + (row_num - 26)/26) || CHR(65 + (row_num - 26)%26)
    END as row,
    col_num + 1 as number,
    col_num as x_coordinate,
    row_num as y_coordinate,
    'SYSTEM' as created_by,
    CURRENT_TIMESTAMP as created_date,
    'SYSTEM' as modified_by,
    CURRENT_TIMESTAMP as modified_date,
    0 as version
FROM (
    SELECT 
        a.N as row_num,
        b.N as col_num
    FROM (SELECT 0 as N UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12 UNION SELECT 13 UNION SELECT 14) a,
         (SELECT 0 as N UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12 UNION SELECT 13 UNION SELECT 14 UNION SELECT 15 UNION SELECT 16 UNION SELECT 17 UNION SELECT 18 UNION SELECT 19 UNION SELECT 20 UNION SELECT 21 UNION SELECT 22 UNION SELECT 23 UNION SELECT 24 UNION SELECT 25 UNION SELECT 26 UNION SELECT 27 UNION SELECT 28 UNION SELECT 29) b
    WHERE a.N < 15 AND b.N < 30
    ORDER BY a.N, b.N
) numbers
CROSS JOIN studio s
JOIN studio_layout sl ON s.studio_layout_id = sl.id
WHERE sl.name = 'Layout 15x30';

-- For studios with Layout 12x25
INSERT INTO studio_seat (studio_id, seat_id, row, number, x_coordinate, y_coordinate, created_by, created_date, modified_by, modified_date, version)
SELECT 
    s.id as studio_id,
    CASE 
        WHEN row_num < 6 THEN 1  -- First 6 rows are REGULAR
        ELSE 2                   -- Last 6 rows are COMFORT
    END as seat_id,
    CASE 
        WHEN row_num < 26 THEN CHR(65 + row_num)
        ELSE CHR(65 + (row_num - 26)/26) || CHR(65 + (row_num - 26)%26)
    END as row,
    col_num + 1 as number,
    col_num as x_coordinate,
    row_num as y_coordinate,
    'SYSTEM' as created_by,
    CURRENT_TIMESTAMP as created_date,
    'SYSTEM' as modified_by,
    CURRENT_TIMESTAMP as modified_date,
    0 as version
FROM (
    SELECT 
        a.N as row_num,
        b.N as col_num
    FROM (SELECT 0 as N UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11) a,
         (SELECT 0 as N UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12 UNION SELECT 13 UNION SELECT 14 UNION SELECT 15 UNION SELECT 16 UNION SELECT 17 UNION SELECT 18 UNION SELECT 19 UNION SELECT 20 UNION SELECT 21 UNION SELECT 22 UNION SELECT 23 UNION SELECT 24) b
    WHERE a.N < 12 AND b.N < 25
    ORDER BY a.N, b.N
) numbers
CROSS JOIN studio s
JOIN studio_layout sl ON s.studio_layout_id = sl.id
WHERE sl.name = 'Layout 12x25';

-- Add movie_schedule entries for today and tomorrow for each studio and movie
DO $$
DECLARE
    d integer;
    m_id integer;
    s_id integer;
    base_date date := CURRENT_DATE;
    time_slots time[] := ARRAY['14:30'::time, '16:45'::time, '19:00'::time, '21:15'::time];
    t time;
BEGIN
    FOR d IN 0..3 LOOP
        FOR m_id IN (
            SELECT id FROM movie 
            LIMIT 5  -- Only use 5 movies
        ) LOOP
            FOR s_id IN (
                SELECT s.id 
                FROM studio s
                JOIN city_cinema cc ON s.city_cinema_id = cc.id
                JOIN city c ON cc.city_id = c.id
                WHERE c.name = 'Bandung'  -- Only Bandung
            ) LOOP
                FOREACH t IN ARRAY time_slots LOOP
                    INSERT INTO movie_schedule (secure_id, movie_id, studio_id, start_time, price, created_by, created_date, modified_by, modified_date, version)
                    VALUES (
                        gen_random_uuid(),
                        m_id,
                        s_id,
                        (base_date + d) + t,
                        50000 + (d * 5000),
                        'SYSTEM',
                        CURRENT_TIMESTAMP,
                        'SYSTEM',
                        CURRENT_TIMESTAMP,
                        0
                    );
                END LOOP;
            END LOOP;
        END LOOP;
    END LOOP;
END $$;

-- Create movie schedule seats for all schedules
DO $$
DECLARE
    batch_size INTEGER := 1000;
    total_records INTEGER;
    current_batch INTEGER := 0;
    start_time TIMESTAMP;
BEGIN
    start_time := clock_timestamp();
    RAISE NOTICE 'Starting movie_schedule_seat insertion at %', start_time;

    -- Create temporary table for the data we want to insert
    RAISE NOTICE 'Creating temporary table...';
    CREATE TEMP TABLE temp_movie_schedule_seats AS
    SELECT 
        gen_random_uuid() as secure_id,
        ms.id as movie_schedule_id,
        ss.id as studio_seat_id,
        'AVAILABLE' as status,
        0.0 as price_adjustment,
        'SYSTEM' as created_by,
        CURRENT_TIMESTAMP as created_date,
        'SYSTEM' as modified_by,
        CURRENT_TIMESTAMP as modified_date,
        0 as version
    FROM movie_schedule ms
    JOIN studio s ON ms.studio_id = s.id
    JOIN city_cinema cc ON s.city_cinema_id = cc.id
    JOIN city c ON cc.city_id = c.id
    JOIN studio_seat ss ON ss.studio_id = s.id
    WHERE c.name = 'Bandung';

    -- Get total count
    SELECT COUNT(*) INTO total_records FROM temp_movie_schedule_seats;
    RAISE NOTICE 'Total records to insert: %', total_records;
    
    -- Insert in batches
    WHILE current_batch * batch_size < total_records LOOP
        RAISE NOTICE 'Processing batch % of % (records % to %)', 
            current_batch + 1, 
            CEIL(total_records::float / batch_size),
            current_batch * batch_size + 1,
            LEAST((current_batch + 1) * batch_size, total_records);

        INSERT INTO movie_schedule_seat (
            secure_id, 
            movie_schedule_id, 
            studio_seat_id, 
            status, 
            price_adjustment, 
            created_by, 
            created_date, 
            modified_by, 
            modified_date, 
            version
        )
        SELECT * FROM temp_movie_schedule_seats
        LIMIT batch_size
        OFFSET current_batch * batch_size;
        
        current_batch := current_batch + 1;
    END LOOP;

    -- Drop temporary table
    RAISE NOTICE 'Dropping temporary table...';
    DROP TABLE temp_movie_schedule_seats;

    RAISE NOTICE 'Finished movie_schedule_seat insertion. Total time: %', 
        clock_timestamp() - start_time;
END $$;

-- User
INSERT INTO "users" (secure_id, username, password, address, role, created_by, created_date, modified_by, modified_date, version)
VALUES 
('550e8400-e29b-41d4-a716-446655440000', 'gen', 'password', 'Jakarta, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440001', 'admin', 'password', 'Jakarta, Indonesia', 'ADMIN', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440002', 'admin2', 'password', 'Bandung, Indonesia', 'ADMIN', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440003', 'john_doe', 'password', 'Surabaya, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440004', 'jane_smith', 'password', 'Medan, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440005', 'bob_wilson', 'password', 'Semarang, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440006', 'alice_brown', 'password', 'Yogyakarta, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440007', 'charlie_davis', 'password', 'Malang, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440008', 'emma_taylor', 'password', 'Palembang, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440009', 'michael_johnson', 'password', 'Makassar, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440010', 'sarah_miller', 'password', 'Denpasar, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440011', 'david_anderson', 'password', 'Manado, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440012', 'lisa_thomas', 'password', 'Balikpapan, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440013', 'james_white', 'password', 'Padang, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440014', 'mary_jackson', 'password', 'Pekanbaru, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440015', 'robert_harris', 'password', 'Banjarmasin, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440016', 'patricia_martin', 'password', 'Pontianak, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440017', 'william_thompson', 'password', 'Samarinda, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440018', 'elizabeth_garcia', 'password', 'Jayapura, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440019', 'richard_martinez', 'password', 'Ambon, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0),
('550e8400-e29b-41d4-a716-446655440020', 'jennifer_robinson', 'password', 'Mataram, Indonesia', 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 0); 