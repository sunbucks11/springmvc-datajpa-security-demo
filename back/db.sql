delete from roles;
delete from users;

INSERT INTO users (id,dob,email,name,password) VALUES 
 (1,NULL,'admin@gmail.com','Administrator','admin'),
 (2,NULL,'siva@gmail.com','Siva','siva');

INSERT INTO roles (role_id,role_name,user_id) VALUES 
 (1,'ROLE_ADMIN',1),
 (2,'ROLE_USER',1),
 (3,'ROLE_USER',2);
 
 
 

INSERT INTO users (id,dob,email,enabled,isAuthenticated,isResetTwoFactorAuth,isVerified,isVerifiedError,name,password,secretKey,twoFactorAuthInitialised) VALUES 
(1,NULL,'admin@gmail.com',false,false,false,false,false,'Administrator','admin123',NULL,false);




use springmvc_datajpa_security_demo;
DROP TABLE `springmvc_datajpa_security_demo`.`users_roles`;
DROP TABLE `springmvc_datajpa_security_demo`.`item`;
DROP TABLE `springmvc_datajpa_security_demo`.`blog`;
DROP TABLE `springmvc_datajpa_security_demo`.`roles`;
DROP TABLE `springmvc_datajpa_security_demo`.`users`;


delete from roles where user_id = 1;
delete from users where id = 1;