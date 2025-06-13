-- Insert sample geo data
INSERT INTO geo (lat, lng) VALUES
('-37.3159', '81.1496'),
('-43.9509', '-34.4618'),
('-68.6102', '-47.0653'),
('29.4572', '-164.2990'),
('-31.8129', '62.5342'),
('-71.4197', '71.7478'),
('24.8918', '21.8984'),
('-14.3990', '-120.7677'),
('24.6463', '-168.8889'),
('-38.2386', '57.2232');

-- Insert sample address data
INSERT INTO address (street, suite, city, zipcode, geo_id) VALUES
('Kulas Light', 'Apt. 556', 'Gwenborough', '92998-3874', 1),
('Victor Plains', 'Suite 879', 'Wisokyburgh', '90566-7771', 2),
('Douglas Extension', 'Suite 847', 'McKenziehaven', '59590-4157', 3),
('Hoeger Mall', 'Apt. 692', 'South Elvis', '53919-4257', 4),
('Skiles Walks', 'Suite 351', 'Roscoeview', '33263', 5),
('Norberto Crossing', 'Apt. 950', 'South Christy', '23505-1337', 6),
('Rex Trail', 'Suite 280', 'Howemouth', '58804-1099', 7),
('Ellsworth Summit', 'Suite 729', 'Aliyaview', '45169', 8),
('Dayna Park', 'Suite 449', 'Bartholomebury', '76495-3109', 9),
('Kattie Turnpike', 'Suite 198', 'Lebsackbury', '31428-2261', 10);

-- Insert sample company data
INSERT INTO company (name, catch_phrase, bs) VALUES
('Romaguera-Crona', 'Multi-layered client-server neural-net', 'harness real-time e-markets'),
('Deckow-Crist', 'Proactive didactic contingency', 'synergize scalable supply-chains'),
('Romaguera-Jacobson', 'Face to face bifurcated interface', 'e-enable strategic applications'),
('Robel-Corkery', 'Multi-tiered zero tolerance productivity', 'transition cutting-edge web services'),
('Keebler LLC', 'User-centric fault-tolerant solution', 'revolutionize end-to-end systems'),
('Considine-Lockman', 'Synchronised bottom-line interface', 'e-enable innovative applications'),
('Johns Group', 'Configurable multimedia task-force', 'generate enterprise e-tailers'),
('Abernathy Group', 'Implemented secondary concept', 'e-enable extensible e-tailers'),
('Yost and Sons', 'Switchable contextually-based project', 'aggregate real-time technologies'),
('Hoeger LLC', 'Centralized empowering task-force', 'target end-to-end models');

-- Insert sample users data
INSERT INTO users (name, username, email, address_id, phone, website, company_id) VALUES
('Leanne Graham', 'Bret', 'Sincere@april.biz', 1, '1-770-736-8031 x56442', 'hildegard.org', 1),
('Ervin Howell', 'Antonette', 'Shanna@melissa.tv', 2, '010-692-6593 x09125', 'anastasia.net', 2),
('Clementine Bauch', 'Samantha', 'Nathan@yesenia.net', 3, '1-463-123-4447', 'ramiro.info', 3),
('Patricia Lebsack', 'Karianne', 'Julianne.OConner@kory.org', 4, '493-170-9623 x156', 'kale.biz', 4),
('Chelsey Dietrich', 'Kamren', 'Lucio_Hettinger@annie.ca', 5, '(254)954-1289', 'demarco.info', 5),
('Mrs. Dennis Schulist', 'Leopoldo_Corkery', 'Karley_Dach@jasper.info', 6, '1-477-935-8478 x6430', 'ola.org', 6),
('Kurtis Weissnat', 'Elwyn.Skiles', 'Telly.Hoeger@billy.biz', 7, '210.067.6132', 'elvis.io', 7),
('Nicholas Runolfsdottir V', 'Maxime_Nienow', 'Sherwood@rosamond.me', 8, '586.493.6943 x140', 'jacynthe.com', 8),
('Glenna Reichert', 'Delphine', 'Chaim_McDermott@dana.io', 9, '(775)976-6794 x41206', 'conrad.com', 9),
('Clementina DuBuque', 'Moriah.Stanton', 'Rey.Padberg@karina.biz', 10, '024-648-3804', 'ambrose.net', 10); 