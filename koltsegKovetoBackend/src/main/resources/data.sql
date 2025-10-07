-- Biztonság kedvéért ürítünk (H2-ben működik)
SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE transactions;
TRUNCATE TABLE categories;
SET REFERENTIAL_INTEGRITY TRUE;

-- Kategóriák
INSERT INTO categories(name) VALUES ('Élelmiszer');
INSERT INTO categories(name) VALUES ('Lakbér');
INSERT INTO categories(name) VALUES ('Közlekedés');
INSERT INTO categories(name) VALUES ('Szórakozás');
INSERT INTO categories(name) VALUES ('Fizetés');
INSERT INTO categories(name) VALUES ('Egészség');
INSERT INTO categories(name) VALUES ('Rezsi');
INSERT INTO categories(name) VALUES ('Egyéb');

-- 2025-08 (augusztus)
INSERT INTO transactions(amount, date, description, category_id)
VALUES (-12500.00, DATE '2025-08-02', 'Aldi bevásárlás', (SELECT id FROM categories WHERE name='Élelmiszer'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-50000.00, DATE '2025-08-05', 'Lakbér (rész)', (SELECT id FROM categories WHERE name='Lakbér'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (350000.00, DATE '2025-08-10', 'Havi fizetés', (SELECT id FROM categories WHERE name='Fizetés'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-4200.00, DATE '2025-08-12', 'BKV bérlet', (SELECT id FROM categories WHERE name='Közlekedés'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-8900.00, DATE '2025-08-15', 'Mozi + popcorn', (SELECT id FROM categories WHERE name='Szórakozás'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-16500.00, DATE '2025-08-18', 'DM, drogéria', (SELECT id FROM categories WHERE name='Egyéb'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-14800.00, DATE '2025-08-20', 'Lidl bevásárlás', (SELECT id FROM categories WHERE name='Élelmiszer'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-17800.00, DATE '2025-08-22', 'Villanyszámla', (SELECT id FROM categories WHERE name='Rezsi'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-9500.00, DATE '2025-08-24', 'Gyógyszertár', (SELECT id FROM categories WHERE name='Egészség'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-50000.00, DATE '2025-08-28', 'Lakbér (rész)', (SELECT id FROM categories WHERE name='Lakbér'));

-- 2025-09 (szeptember)
INSERT INTO transactions(amount, date, description, category_id)
VALUES (355000.00, DATE '2025-09-10', 'Havi fizetés', (SELECT id FROM categories WHERE name='Fizetés'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-15200.00, DATE '2025-09-02', 'Aldi heti bevásárlás', (SELECT id FROM categories WHERE name='Élelmiszer'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-4200.00, DATE '2025-09-02', 'BKV bérlet', (SELECT id FROM categories WHERE name='Közlekedés'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-100000.00, DATE '2025-09-05', 'Lakbér (teljes)', (SELECT id FROM categories WHERE name='Lakbér'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-9800.00, DATE '2025-09-06', 'Netflix + Spotify', (SELECT id FROM categories WHERE name='Szórakozás'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-18900.00, DATE '2025-09-08', 'Tesco bevásárlás', (SELECT id FROM categories WHERE name='Élelmiszer'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-21500.00, DATE '2025-09-14', 'Gázszámla', (SELECT id FROM categories WHERE name='Rezsi'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-8700.00, DATE '2025-09-16', 'Kávézó + sütik', (SELECT id FROM categories WHERE name='Szórakozás'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-9300.00, DATE '2025-09-18', 'Gyógyszertár', (SELECT id FROM categories WHERE name='Egészség'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-13800.00, DATE '2025-09-20', 'Lidl bevásárlás', (SELECT id FROM categories WHERE name='Élelmiszer'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-6200.00, DATE '2025-09-22', 'Taxi (eső)', (SELECT id FROM categories WHERE name='Közlekedés'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-24500.00, DATE '2025-09-24', 'Víz + csatorna', (SELECT id FROM categories WHERE name='Rezsi'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (25000.00, DATE '2025-09-25', 'Használt eladás - Jófogás', (SELECT id FROM categories WHERE name='Egyéb'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-11200.00, DATE '2025-09-27', 'Auchan bevásárlás', (SELECT id FROM categories WHERE name='Élelmiszer'));

INSERT INTO transactions(amount, date, description, category_id)
VALUES (-14900.00, DATE '2025-09-29', 'Színházjegy', (SELECT id FROM categories WHERE name='Szórakozás'));
