CREATE schema IF NOT EXISTS petshop_autenticacao;
DROP TABLE IF EXISTS petshop_autenticacao.senha;
CREATE TABLE IF NOT EXISTS petshop_autenticacao.senha (
    id bigint UNIQUE primary key,
    usuario varchar(255) not null,
    senha varchar(255) not null,
    fk_id_usuario bigint
);

create
    unique index petshop_autenticacao_senha_id_uindex
    on petshop_autenticacao.senha (id);
