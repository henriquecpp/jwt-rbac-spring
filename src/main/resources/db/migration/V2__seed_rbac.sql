INSERT INTO permissions (name, description) VALUES
    ('admin:read',   'Leitura administrativa'),
    ('admin:write',  'Escrita administrativa'),
    ('admin:delete', 'Exclusão administrativa'),
    ('user:read',    'Leitura do próprio perfil'),
    ('user:write',   'Atualização do próprio perfil');

INSERT INTO roles (name) VALUES
    ('ADMIN'),
    ('MODERATOR'),
    ('USER');

-- ADMIN recebe todas as permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'ADMIN';

-- MODERATOR
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'MODERATOR'
  AND p.name IN ('admin:read', 'user:read', 'user:write');

-- USER
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'USER'
  AND p.name IN ('user:read', 'user:write');