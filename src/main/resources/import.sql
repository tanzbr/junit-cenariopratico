-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

-- INSERT INTO Image (caption, url, priority) values('Imagem muito legal', 'url da imagem', 1);
-- INSERT INTO GiftCompany (name, cnpj, logo_id) values('Google', '111.111.111-0001/10', 1);
-- INSERT INTO GiftCode (id, code, giftcard_id, giftstate) values(1, 'codigo1', 1, 'AVAILABLE');
-- INSERT INTO GiftCard (id, name, description, tags, price, giftcompany_id, visible)
--    values(1, 'Gift Card Google Play - 40 reais', 'Gift card de 40 reais para usar na google playe'
--    '{tag1,tag2}', 40, 1, true);

-- INSERT INTO giftcard_giftcode(giftcard_id, giftcodes_id) values (1, 1);
-- INSERT INTO giftcard_image(giftcard_id, images_id) values (1, 1);