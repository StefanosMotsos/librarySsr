-- Insert roles
INSERT INTO roles (name)
VALUES
    ('ADMIN'),
    ('EMPLOYEE');

-- Insert capabilities
INSERT INTO capabilities (name, description)
VALUES
    ('INSERT_BOOK', 'Add a new book'),
    ('VIEW_BOOKS', 'View book list and details'),
    ('EDIT_BOOKS', 'Modify existing book'),
    ('DELETE_BOOKS', 'Remove a book');

-- Assign capabilities to ADMIN (all capabilities)
INSERT INTO roles_capabilities (role_id, capability_id)
SELECT r.id, c.id
FROM roles r
JOIN capabilities c
WHERE r.name = 'ADMIN';

-- Assign limited capabilities to EMPLOYEE
INSERT INTO roles_capabilities (role_id, capability_id)
SELECT r.id, c.id
FROM roles r
JOIN capabilities c
WHERE r.name = 'EMPLOYEE'
  AND c.name IN ('VIEW_TEACHERS');
