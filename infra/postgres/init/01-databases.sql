-- Um database e um usuário por serviço. Cada usuário só consegue conectar no
-- próprio database, então um serviço não lê os dados de outro.
-- Senhas válidas só para desenvolvimento local.

CREATE USER card_user WITH PASSWORD 'card_pass';
CREATE DATABASE card_db OWNER card_user;
REVOKE ALL ON DATABASE card_db FROM PUBLIC;
