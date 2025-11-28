-- Kustutab public schema (mis põhimõtteliselt kustutab kõik tabelid)
DROP SCHEMA IF EXISTS rss CASCADE;
-- Loob uue public schema vajalikud õigused
CREATE SCHEMA rss
-- taastab vajalikud andmebaasi õigused
    GRANT ALL ON SCHEMA rss TO postgres;
GRANT ALL ON SCHEMA rss TO PUBLIC;