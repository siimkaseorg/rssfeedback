-- Created by Redgate Data Modeler (https://datamodeler.redgate-platform.com)
-- Last modification date: 2025-12-01 09:11:16.489

-- tables
-- Table: article
CREATE TABLE article (
                         id serial  NOT NULL,
                         portal_id int  NOT NULL,
                         category_id int  NOT NULL,
                         title varchar(255)  NOT NULL,
                         description varchar(1000)  NOT NULL,
                         article_link varchar(255)  NOT NULL,
                         image_link varchar(255)  NOT NULL,
                         status varchar(3)  NOT NULL,
                         guid varchar(500)  NOT NULL,
                         article_date date  NOT NULL,
                         created_at date  NOT NULL,
                         CONSTRAINT article_ak_1 UNIQUE (guid) NOT DEFERRABLE  INITIALLY IMMEDIATE,
                         CONSTRAINT article_pk PRIMARY KEY (id)
);

-- Table: category
CREATE TABLE category (
                          id serial  NOT NULL,
                          name varchar(255)  NOT NULL,
                          CONSTRAINT category_pk PRIMARY KEY (id)
);

-- Table: later_read
CREATE TABLE later_read (
                            id serial  NOT NULL,
                            user_id int  NOT NULL,
                            article_id int  NOT NULL,
                            CONSTRAINT later_read_pk PRIMARY KEY (id)
);

-- Table: portal
CREATE TABLE portal (
                        id serial  NOT NULL,
                        name varchar(255)  NOT NULL,
                        xml_link varchar(255)  NOT NULL,
                        status varchar(3)  NOT NULL,
                        CONSTRAINT portal_pk PRIMARY KEY (id)
);

-- Table: portal_category
CREATE TABLE portal_category (
                                 id serial  NOT NULL,
                                 portal_id int  NOT NULL,
                                 portal_category_name varchar(255)  NOT NULL,
                                 category_id int  NOT NULL,
                                 CONSTRAINT portal_category_pk PRIMARY KEY (id)
);

-- Table: role
CREATE TABLE role (
                      id serial  NOT NULL,
                      name varchar(20)  NOT NULL,
                      CONSTRAINT role_pk PRIMARY KEY (id)
);

-- Table: user
CREATE TABLE "user" (
                        id serial  NOT NULL,
                        role_id int  NOT NULL,
                        username varchar(255)  NOT NULL,
                        password varchar(255)  NOT NULL,
                        status varchar(3)  NOT NULL,
                        CONSTRAINT user_ak_1 UNIQUE (username) NOT DEFERRABLE  INITIALLY IMMEDIATE,
                        CONSTRAINT user_pk PRIMARY KEY (id)
);

-- Table: user_feed_selection
CREATE TABLE user_feed_selection (
                                     id serial  NOT NULL,
                                     user_id int  NOT NULL,
                                     portal_id int  NOT NULL,
                                     category_id int  NOT NULL,
                                     CONSTRAINT user_feed_selection_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: article_category (table: article)
ALTER TABLE article ADD CONSTRAINT article_category
    FOREIGN KEY (category_id)
        REFERENCES category (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: article_portal (table: article)
ALTER TABLE article ADD CONSTRAINT article_portal
    FOREIGN KEY (portal_id)
        REFERENCES portal (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: later_read_article (table: later_read)
ALTER TABLE later_read ADD CONSTRAINT later_read_article
    FOREIGN KEY (article_id)
        REFERENCES article (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: later_read_user (table: later_read)
ALTER TABLE later_read ADD CONSTRAINT later_read_user
    FOREIGN KEY (user_id)
        REFERENCES "user" (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: portal_category_category (table: portal_category)
ALTER TABLE portal_category ADD CONSTRAINT portal_category_category
    FOREIGN KEY (category_id)
        REFERENCES category (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: portal_category_portal (table: portal_category)
ALTER TABLE portal_category ADD CONSTRAINT portal_category_portal
    FOREIGN KEY (portal_id)
        REFERENCES portal (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: user_feed_selection_category (table: user_feed_selection)
ALTER TABLE user_feed_selection ADD CONSTRAINT user_feed_selection_category
    FOREIGN KEY (category_id)
        REFERENCES category (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: user_feed_selection_portal (table: user_feed_selection)
ALTER TABLE user_feed_selection ADD CONSTRAINT user_feed_selection_portal
    FOREIGN KEY (portal_id)
        REFERENCES portal (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: user_feed_selection_user (table: user_feed_selection)
ALTER TABLE user_feed_selection ADD CONSTRAINT user_feed_selection_user
    FOREIGN KEY (user_id)
        REFERENCES "user" (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: user_role (table: user)
ALTER TABLE "user" ADD CONSTRAINT user_role
    FOREIGN KEY (role_id)
        REFERENCES role (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- End of file.

