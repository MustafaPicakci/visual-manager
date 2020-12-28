insert into Roles (name) values('ROLE_ADMIN');
 
insert into Users(email,password,username) values ('mustafa@gmail.com','$2a$10$ZSmPKtamWiOXA8GHSzi0jOBnotUecqYGgstNlOkDs1pJiCoMgT73a','mustafa');
insert into user_roles(user_id,role_id) values(1,1);