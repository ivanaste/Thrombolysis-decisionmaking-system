insert into person (id, created_at, email, name, password, role, surname) values ('3f1d4d2b-568f-469a-ae1e-3c902131a3e5', CURRENT_TIMESTAMP, 'milicftn@gmail.com', 'Mico', '$2a$10$5tykbOhp3Uo2QrY2t3/uCOmsuwvEZl4KgEgZgN3At6JB3HQX.Z75y', 'DOCTOR', 'MILIC');

-- insert into pacijent (id, created_at, datum_rodjenja, jmbg, role) values ('a3ee4f83-b5e5-4560-8174-33989ece2e3d', CURRENT_TIMESTAMP, '2000-01-01', '3112999000000', 'PATIENT');

-- insert into bolesti (id, created_at, vrsta_bolesti, pacijent_id, datum_desavanja) values ('3a835eb5-6403-49b8-8739-026f1c655fa6', CURRENT_TIMESTAMP, 'GASTROINTESTINALNO_KRVARENJE', 'a3ee4f83-b5e5-4560-8174-33989ece2e3d', '2022-06-29');
-- insert into bolesti (id, created_at, vrsta_bolesti, pacijent_id, datum_desavanja) values ('747fee19-a692-49e6-afe6-3efffb1c67b9', CURRENT_TIMESTAMP, 'INTRAKRANIJALNA_HEMORAGIJA', 'a3ee4f83-b5e5-4560-8174-33989ece2e3d', '2022-06-01');