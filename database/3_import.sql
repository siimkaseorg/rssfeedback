INSERT INTO rss.role (id, name) VALUES (default, 'admin');
INSERT INTO rss.role (id, name) VALUES (default, 'customer');

INSERT INTO rss."user" (id, role_id, username, password, status) VALUES (default, 1, 'admin', '123', 'A');
INSERT INTO rss."user" (id, role_id, username, password, status) VALUES (default, 2, 'customer', '123', 'A');

INSERT INTO rss.category (id, name) VALUES (default, 'Sport');
INSERT INTO rss.category (id, name) VALUES (default, 'Poliitika');
INSERT INTO rss.category (id, name) VALUES (default, 'Kultuur');
INSERT INTO rss.category (id, name) VALUES (default, 'Krimi');
INSERT INTO rss.category (id, name) VALUES (default, 'Uudised');
INSERT INTO rss.category (id, name) VALUES (default, 'Välismaa');
INSERT INTO rss.category (id, name) VALUES (default, 'Majandus');

INSERT INTO rss.portal (id, name, xml_link, status) VALUES (default, 'ERR', 'errxmllink', 'A');
INSERT INTO rss.portal (id, name, xml_link, status) VALUES (default, 'Delfi', 'delfixmllink', 'A');
INSERT INTO rss.portal (id, name, xml_link, status) VALUES (default, 'Postimees', 'postimeesxmllink', 'A');
INSERT INTO rss.portal (id, name, xml_link, status) VALUES (default, 'Äripäev', 'aripäevxmllink', 'A');
