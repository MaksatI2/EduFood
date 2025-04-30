--changeset liquibase:I.Maksat
INSERT INTO authorities (authority) VALUES ('USER');

INSERT INTO users (username, password, email, account_type)
SELECT
    'Maksat',
    '$2a$12$B5Rvf3QpG5Vlv4se8.NDQuIe.sxoG.UVWV8eQRpbwMK980dX/V1ZK',
    'zer0icemax@gmail.com',
    (SELECT id FROM authorities WHERE authority = 'USER')
UNION ALL
SELECT
    'Sezim',
    '$2a$12$B5Rvf3QpG5Vlv4se8.NDQuIe.sxoG.UVWV8eQRpbwMK980dX/V1ZK',
    'sezimanurlanovna03@gmail.com',
    (SELECT id FROM authorities WHERE authority = 'USER')
UNION ALL
SELECT
    'Aruush',
    '$2a$12$B5Rvf3QpG5Vlv4se8.NDQuIe.sxoG.UVWV8eQRpbwMK980dX/V1ZK',
    'aruuke.toktosunovaa@gmail.com',
    (SELECT id FROM authorities WHERE authority = 'USER')