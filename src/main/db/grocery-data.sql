--
-- Sample data for table `category`
--

INSERT INTO `category` (category_id, name)
VALUES (1001, 'Fresh Produce');

INSERT INTO `category` (category_id, name)
VALUES (1002, 'Meat');

INSERT INTO `category` (category_id, name)
VALUES (1003, 'Dairy');

INSERT INTO `category` (category_id, name)
VALUES (1004, 'Bakery');

--
-- Sample data for table `product`
--
INSERT INTO `product` (`name`, price, points, description, category_id)
VALUES ('Bell Pepper', 170, 1, 'organic red juicy', 1001);
INSERT INTO `product` (`name`, price, points, description, category_id)
VALUES ('Broccoli', 239, 2, 'whole with stalks', 1001);
INSERT INTO `product` (`name`, price, points, description, category_id)
VALUES ('Cauliflower', 109, 3, 'whole, not riced', 1001);
INSERT INTO `product` (`name`, price, points, description, category_id)
VALUES ('Carrots', 176, 4,'baby size', 1001);

INSERT INTO `product` (`name`, price, points, description, category_id)
VALUES ('Meat Patties', 229, 1, '2 patties (1/2 lb)', 1002);
INSERT INTO `product` (`name`, price, points, description, category_id)
VALUES ('Sausages', 349, 2, 'organic bratwurst (1 lb)', 1002);
INSERT INTO `product` (`name`, price, points, description, category_id)
VALUES ('Chicken Drumsticks', 259, 3, 'free range (1/2 lb)', 1002);
INSERT INTO `product` (`name`, price, points, description, category_id)
VALUES ('Pork Chops', 355, 4, 'trimmed, reduced fat (1 lb)', 1002);

INSERT INTO `product` (`name`, price, points, description, category_id)
VALUES ('Whole Milk', 189, 1, '1 gallon', 1003);
INSERT INTO `product` (`name`, price, points, description, category_id)
VALUES ('Cheese', 119, 2, 'cheddar 1/2 pound', 1003);
INSERT INTO `product` (`name`, price, points, description, category_id)
VALUES ('Greek Yogurt', 115, 3, 'plain flavor 8oz', 1003);
INSERT INTO `product` (`name`, price, points, description, category_id)
VALUES ('Ice Cream in a Cone', 239, 4, 'sugar waffle cone, assorted flavors', 1003);

INSERT INTO `product` (`name`, price, points, description, category_id)
VALUES ('Bagels', 159, 1, 'sliced', 1004);
INSERT INTO `product` (`name`, price, points, description, category_id)
VALUES ('Croissants', 249, 2, '', 1004);
INSERT INTO `product` (`name`, price, points, description, category_id)
VALUES ('Doughnuts', 129, 3, 'assorted flavors', 1004);
INSERT INTO `product` (`name`, price, points, description, category_id)
VALUES ('Chocolate Cake', 149, 4, 'with chocolate filling', 1004);
