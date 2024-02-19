insert into USUARIOS( id, username,password, role) values (100,'ana@gmail.com','$2a$12$4G1.TGGdDn2BeBBjA/bXZ.xpAIcyjGqupKqYKLG55wxCZsKXgPuIu','ROLE_ADMIN');
insert into USUARIOS( id, username,password, role) values (101,'JOAO@gmail.com','$2a$12$4G1.TGGdDn2BeBBjA/bXZ.xpAIcyjGqupKqYKLG55wxCZsKXgPuIu','ROLE_CLIENTE');
insert into USUARIOS( id, username,password, role) values (102,'davi@gmail.com','$2a$12$4G1.TGGdDn2BeBBjA/bXZ.xpAIcyjGqupKqYKLG55wxCZsKXgPuIu','ROLE_CLIENTE');
insert into USUARIOS( id, username,password, role) values (103,'reis@gmail.com','$2a$12$4G1.TGGdDn2BeBBjA/bXZ.xpAIcyjGqupKqYKLG55wxCZsKXgPuIu','ROLE_ADMIN');

insert into CLIENTES(id,nome, cpf, id_usuario) values(21, 'Fernandinho Beiramar', '59575966392', 101);
insert into CLIENTES(id,nome, cpf, id_usuario) values(22, 'Alexander Barboza', '17526942360', 102);


insert into VAGAS(id, codigo,status) VALUES (100,'A-01', 'OCUPADA');
insert into VAGAS(id, codigo,status) VALUES (200,'A-02', 'OCUPADA');
insert into VAGAS(id, codigo,status) VALUES (300,'A-03', 'OCUPADA');
insert into VAGAS(id, codigo,status) VALUES (400,'A-04', 'OCUPADA');
insert into VAGAS(id, codigo,status) VALUES (500,'A-05', 'OCUPADA');



-- Caso 1: Cliente 10 estaciona na vaga 10
insert into CLIENTES_TEM_VAGAS(numero_recibo,placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
values ('20230313-101301', 'GOL1234', 'VW', 'Gol', 'BRANCO', '2023-03-11 11:30:00',22,100);

-- Caso 2: Cliente 20 estaciona na vaga 20
insert into CLIENTES_TEM_VAGAS(numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
values ('20230313-101302', 'CIVI2987', 'HONDA', 'Civic', 'PRATA', '2023-03-12 12:45:00',21,200);

insert into CLIENTES_TEM_VAGAS(numero_recibo, placa, marca, modelo , cor, data_entrada, id_cliente, id_vaga) values('20230313-101300','FIT2A69', 'FIAT', 'PALIO', 'AZUL', '2023-03-10 10:15:00',22,300);

insert into CLIENTES_TEM_VAGAS(numero_recibo, placa, marca, modelo , cor, data_entrada, id_cliente, id_vaga) values('20230313-101320','FIT2269', 'FIAT', 'PALIO', 'AZUL', '2023-03-10 10:12:00',22,400);

insert into CLIENTES_TEM_VAGAS(numero_recibo, placa, marca, modelo , cor, data_entrada, id_cliente, id_vaga) values('20230313-102320','FIT2469', 'FIAT', 'PALIO', 'AZUL', '2024-03-10 10:12:00',22,500);
