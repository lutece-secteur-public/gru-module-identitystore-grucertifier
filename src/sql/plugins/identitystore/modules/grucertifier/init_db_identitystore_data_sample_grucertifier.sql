-- Insert the GruCertifier to client applications for notifyGru. 
INSERT INTO identitystore_client_application( id_client_app , name , code ) SELECT MAX(id_client_app) + 1 , 'GruCertifier ' , 'GruCertifier' FROM identitystore_client_application;

-- Give it the right of read the email; if this field exists in identitystore
INSERT INTO identitystore_attribute_right ( id_client_app, id_attribute, readable, writable, certifiable ) VALUES ( 
(SELECT MAX(id_client_app) FROM identitystore_client_application client_app), 
(SELECT id_attribute FROM identitystore_attribute WHERE key_name='email'), 
1 , 0, 0);