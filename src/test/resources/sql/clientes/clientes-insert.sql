insert into USUARIOS( id, username,password, role) values (100,'ana@gmail.com','$2a$12$4G1.TGGdDn2BeBBjA/bXZ.xpAIcyjGqupKqYKLG55wxCZsKXgPuIu','ROLE_ADMIN');
insert into USUARIOS( id, username,password, role) values (101,'JOAO@gmail.com','$2a$12$4G1.TGGdDn2BeBBjA/bXZ.xpAIcyjGqupKqYKLG55wxCZsKXgPuIu','ROLE_CLIENTE');
insert into USUARIOS( id, username,password, role) values (102,'davi@gmail.com','$2a$12$4G1.TGGdDn2BeBBjA/bXZ.xpAIcyjGqupKqYKLG55wxCZsKXgPuIu','ROLE_CLIENTE');
insert into USUARIOS( id, username,password, role) values (103,'TONY@gmail.com','$2a$12$4G1.TGGdDn2BeBBjA/bXZ.xpAIcyjGqupKqYKLG55wxCZsKXgPuIu','ROLE_CLIENTE');

insert into CLIENTES(id,nome, cpf, id_usuario) values(10, 'Fernandinho Beiramar', '59575966392', 101);
insert into CLIENTES(id,nome, cpf, id_usuario) values(20, 'Alexander Barboza', '17526942360', 102);