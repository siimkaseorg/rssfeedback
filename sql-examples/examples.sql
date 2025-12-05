
-- View portal category mappings
SELECT
    p.name AS portal,
    c.name AS category,
    pc.portal_category_name AS portal_category
FROM rss.portal_category pc
         JOIN rss.portal p
              ON pc.portal_id = p.id
         JOIN rss.category c
              ON pc.category_id = c.id;
