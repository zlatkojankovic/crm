INSERT INTO t_user (id,login,password,first_name,last_name,email,activated,lang_key,activation_key,created_by,created_date,last_modified_by,last_modified_date) VALUES (1,'system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG',NULL,'System',NULL,'1','en',NULL,'system',sysdate,NULL,NULL);
INSERT INTO t_user (id,login,password,first_name,last_name,email,activated,lang_key,activation_key,created_by,created_date,last_modified_by,last_modified_date) VALUES (2,'anonymousUser','$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO','Anonymous','User',NULL,'1','en',NULL,'system',sysdate,NULL,NULL);
INSERT INTO t_user (id,login,password,first_name,last_name,email,activated,lang_key,activation_key,created_by,created_date,last_modified_by,last_modified_date) VALUES (3,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC',NULL,'Administrator',NULL,'1','en',NULL,'system',sysdate,NULL,NULL);
INSERT INTO t_user (id,login,password,first_name,last_name,email,activated,lang_key,activation_key,created_by,created_date,last_modified_by,last_modified_date) VALUES (4,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K',NULL,'User',NULL,'1','en',NULL,'system',sysdate,NULL,NULL);

INSERT INTO t_authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO t_authority (name) VALUES ('ROLE_USER');


INSERT INTO t_user_authority (user_id,authority_name) VALUES (1,'ROLE_ADMIN');
INSERT INTO t_user_authority (user_id,authority_name) VALUES (3,'ROLE_ADMIN');
INSERT INTO t_user_authority (user_id,authority_name) VALUES (1,'ROLE_USER');
INSERT INTO t_user_authority (user_id,authority_name) VALUES (3,'ROLE_USER');
INSERT INTO t_user_authority (user_id,authority_name) VALUES (4,'ROLE_USER');
