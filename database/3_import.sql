INSERT INTO rss.role (id, name) VALUES (default, 'admin');
INSERT INTO rss.role (id, name) VALUES (default, 'customer');

INSERT INTO rss."user" (id, role_id, username, password, status) VALUES (default, 1, 'admin', '123', 'A');
INSERT INTO rss."user" (id, role_id, username, password, status) VALUES (default, 2, 'customer', '123', 'A');
