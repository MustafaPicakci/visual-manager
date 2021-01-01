INSERT INTO roles (name)
SELECT 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT * FROM roles WHERE name='ROLE_ADMIN');

INSERT INTO roles (name)
SELECT 'ROLE_USER'
WHERE NOT EXISTS (SELECT * FROM roles WHERE name='ROLE_USER');

INSERT INTO users (email,password,username)
select 'mustafapicakci25@gmail.com','$2a$10$duud6MSgUNpQQoS7nsvB7eD1JB8WjTQNFrfU8A4aoYSM96n109RKq','mustafa'
WHERE NOT EXISTS (SELECT * FROM users WHERE username='mustafa');

INSERT INTO user_roles (user_id,role_id)
select 1,1
WHERE NOT EXISTS (SELECT * FROM user_roles WHERE user_id=1 and role_id=1);

INSERT INTO user_roles (user_id,role_id)
select 1,2
WHERE NOT EXISTS (SELECT * FROM user_roles WHERE user_id=1 and role_id=2);