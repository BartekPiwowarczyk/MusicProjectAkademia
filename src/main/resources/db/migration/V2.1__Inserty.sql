INSERT INTO artists(name) VALUES ('Vows');

INSERT INTO songs(title,duration,remarks) VALUES ('Settle Down',257,'aus');
INSERT INTO songs(title,duration,remarks) VALUES ('Cameo Lover',242,'aus');
INSERT INTO songs(title,duration,remarks) VALUES ('Two Way Street',268,'aus');
INSERT INTO songs(title,duration,remarks) VALUES ('Old Flame',267,'aus');
INSERT INTO songs(title,duration,remarks) VALUES ('Good Intent',212,'aus');
INSERT INTO songs(title,duration,remarks) VALUES ('Plain Gold Ring',242,'aus');
INSERT INTO songs(title,duration,remarks) VALUES ('Call Me',272,'aus');
INSERT INTO songs(title,duration,remarks) VALUES ('Limbo',231,'aus');
INSERT INTO songs(title,duration,remarks) VALUES ('Wandering Limbs',326,'aus');
INSERT INTO songs(title,duration,remarks) VALUES ('Withdraw',246,'aus');
INSERT INTO songs(title,duration,remarks) VALUES ('The Build Up',502,'aus');

INSERT INTO songs(title,duration,remarks)
VALUES ('Settle Down',242,'eu'),
       ('Something in the Way You Are',263,'eu'),
       ('Cameo Lover',243,'eu'),
       ('Two Way Street',260,'eu'),
       ('Old Flame',270,'eu'),
       ('Good Intent',212,'eu'),
       ('Plain Gold Ring',272,'eu'),
       ('Come into My Head',279,'eu'),
       ('Sally I Can See You',238,'eu'),
       ('Posse',307,'eu'),
       ('Home',184,'eu'),
       ('The Build Up',306,'eu'),
       ('Warrior',256,'eu');


INSERT INTO albums(title,edition,ARTIST_id) VALUES ('Kimbra','the original',1);
INSERT INTO albums(title,edition,ARTIST_id) VALUES ('Kimbra','North American and European Tour',1);

INSERT INTO albums_songs(album_id, song_id, position)
VALUES (1,1,1),
       (1,2,2),
       (1,3,3),
       (1,4,4),
       (1,5,5),
       (1,6,6),
       (1,7,7),
       (1,8,8),
       (1,9,9),
       (1,10,10),
       (1,11,11);

INSERT INTO albums_songs(album_id, song_id, position)
VALUES (2,12,1),
       (2,13,2),
       (2,14,3),
       (2,15,4),
       (2,16,5),
       (2,17,6),
       (2,18,7),
       (2,19,8),
       (2,20,9),
       (2,21,10),
       (2,22,11),
       (2,23,12),
       (2,24,13);
