/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MariaDB
 Source Server Version : 100432 (10.4.32-MariaDB)
 Source Host           : localhost:3306
 Source Schema         : clothing_store_db

 Target Server Type    : MariaDB
 Target Server Version : 100432 (10.4.32-MariaDB)
 File Encoding         : 65001

 Date: 08/01/2025 14:06:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blogs
-- ----------------------------
DROP TABLE IF EXISTS `blogs`;
CREATE TABLE `blogs`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `authorId` int(11) NOT NULL,
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `thumbnailImage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `createdDate` datetime NOT NULL,
  `updatedDate` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `authorId`(`authorId`) USING BTREE,
  CONSTRAINT `blogs_ibfk_1` FOREIGN KEY (`authorId`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blogs
-- ----------------------------

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `createdDate` datetime NOT NULL DEFAULT current_timestamp(),
  `updatedDate` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE,
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------

-- ----------------------------
-- Table structure for cartdetail
-- ----------------------------
DROP TABLE IF EXISTS `cartdetail`;
CREATE TABLE `cartdetail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cartId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `couponId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unitPrice` decimal(10, 2) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `cartId`(`cartId`) USING BTREE,
  INDEX `productId`(`productId`) USING BTREE,
  INDEX `couponId`(`couponId`) USING BTREE,
  CONSTRAINT `cartdetail_ibfk_1` FOREIGN KEY (`cartId`) REFERENCES `cart` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `cartdetail_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `cartdetail_ibfk_3` FOREIGN KEY (`couponId`) REFERENCES `coupons` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cartdetail
-- ----------------------------

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `typeId` int(11) NOT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `typeId`(`typeId`) USING BTREE,
  CONSTRAINT `categories_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `types` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES (1, 1, 'Nam');
INSERT INTO `categories` VALUES (2, 1, 'Nữ');
INSERT INTO `categories` VALUES (3, 1, 'Trẻ em');
INSERT INTO `categories` VALUES (4, 2, 'Nam');
INSERT INTO `categories` VALUES (5, 2, 'Nữ');
INSERT INTO `categories` VALUES (6, 2, 'Trẻ em');

-- ----------------------------
-- Table structure for collections
-- ----------------------------
DROP TABLE IF EXISTS `collections`;
CREATE TABLE `collections`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `collectionsName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `collectionsDescription` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `startDate` date NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collections
-- ----------------------------

-- ----------------------------
-- Table structure for collections_product
-- ----------------------------
DROP TABLE IF EXISTS `collections_product`;
CREATE TABLE `collections_product`  (
  `productId` int(11) NOT NULL,
  `collectionId` int(11) NOT NULL,
  INDEX `productId`(`productId`) USING BTREE,
  INDEX `collectionId`(`collectionId`) USING BTREE,
  CONSTRAINT `collections_product_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `collections_product_ibfk_2` FOREIGN KEY (`collectionId`) REFERENCES `collections` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collections_product
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `rating` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE,
  INDEX `productId`(`productId`) USING BTREE,
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for coupons
-- ----------------------------
DROP TABLE IF EXISTS `coupons`;
CREATE TABLE `coupons`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `remainingCount` int(11) NOT NULL,
  `discount` float NOT NULL,
  `decription` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupons
-- ----------------------------

-- ----------------------------
-- Table structure for favourite
-- ----------------------------
DROP TABLE IF EXISTS `favourite`;
CREATE TABLE `favourite`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE,
  INDEX `productId`(`productId`) USING BTREE,
  CONSTRAINT `favourite_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `favourite_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of favourite
-- ----------------------------

-- ----------------------------
-- Table structure for notifications
-- ----------------------------
DROP TABLE IF EXISTS `notifications`;
CREATE TABLE `notifications`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `notificationDate` datetime NOT NULL DEFAULT current_timestamp(),
  `isRead` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE,
  CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notifications
-- ----------------------------

-- ----------------------------
-- Table structure for orderitem
-- ----------------------------
DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE `orderitem`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `orderDate` datetime NOT NULL,
  `quantity` int(11) NOT NULL,
  `unitPrice` decimal(10, 2) NOT NULL,
  `discount` float NOT NULL DEFAULT 0,
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `orderId`(`orderId`) USING BTREE,
  INDEX `productId`(`productId`) USING BTREE,
  CONSTRAINT `orderitem_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orderitem_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderitem
-- ----------------------------

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `paymentId` int(11) NOT NULL,
  `couponId` int(11) NULL DEFAULT NULL,
  `orderDate` datetime NOT NULL,
  `totalPrice` decimal(10, 2) NOT NULL,
  `status` bit(1) NOT NULL,
  `shippingFee` decimal(10, 2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE,
  INDEX `paymentId`(`paymentId`) USING BTREE,
  INDEX `couponId`(`couponId`) USING BTREE,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`paymentId`) REFERENCES `payments` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`couponId`) REFERENCES `coupons` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for payments
-- ----------------------------
DROP TABLE IF EXISTS `payments`;
CREATE TABLE `payments`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `paymentMethod` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of payments
-- ----------------------------

-- ----------------------------
-- Table structure for product_details
-- ----------------------------
DROP TABLE IF EXISTS `product_details`;
CREATE TABLE `product_details`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productId` int(11) NULL DEFAULT NULL,
  `size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `stock` int(11) NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `productId_fk`(`productId`) USING BTREE,
  CONSTRAINT `proid_fk` FOREIGN KEY (`productId`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_details
-- ----------------------------
INSERT INTO `product_details` VALUES (1, 1, 'M', 10, 'p1n1.webp', 'Nâu dạ');
INSERT INTO `product_details` VALUES (2, 1, 'L', 11, 'p1n2.webp', 'Nâu dạ');
INSERT INTO `product_details` VALUES (3, 1, 'XL', 7, 'p1n5.webp', 'Nâu dạ');
INSERT INTO `product_details` VALUES (4, 1, 'XXL', 4, 'p1n7.webp', 'Nâu dạ');
INSERT INTO `product_details` VALUES (5, 1, 'M', 8, 'p1n3.webp', 'Đen xám');
INSERT INTO `product_details` VALUES (6, 1, 'L', 5, 'p1n6.webp', 'Đen xám');
INSERT INTO `product_details` VALUES (7, 2, 'M', 12, 'p2n1.webp', 'Nâu nhạt');
INSERT INTO `product_details` VALUES (8, 2, 'L', 4, 'p2n6.webp', 'Nâu nhạt');
INSERT INTO `product_details` VALUES (9, 2, 'XL', 9, 'p2n2.webp', 'Trắng');
INSERT INTO `product_details` VALUES (10, 2, 'XXL', 5, 'p2n3.webp', 'Trắng');
INSERT INTO `product_details` VALUES (11, 2, 'M', 14, 'p2n4.webp', 'Xanh rêu');
INSERT INTO `product_details` VALUES (12, 2, 'L', 13, 'p2n5.webp', 'Xanh rêu');
INSERT INTO `product_details` VALUES (13, 3, 'M', 11, 'p3n1.webp', 'Đen');
INSERT INTO `product_details` VALUES (14, 3, 'L', 17, 'p3n2.webp', 'Đen');
INSERT INTO `product_details` VALUES (15, 3, 'XL', 14, 'p3n3.webp', 'Đen');
INSERT INTO `product_details` VALUES (16, 3, 'XXL', 18, 'p3n4.webp', 'Đen');
INSERT INTO `product_details` VALUES (17, 3, 'XXXL', 4, 'p3n5.webp', 'Đen');
INSERT INTO `product_details` VALUES (18, 3, '4XL', 8, 'p3n6.webp', 'Đen');
INSERT INTO `product_details` VALUES (19, 3, 'M', 19, 'p3n7.webp', 'Nâu chocolate');
INSERT INTO `product_details` VALUES (20, 3, 'L', 9, 'p3n8.webp', 'Nâu chocolate');
INSERT INTO `product_details` VALUES (21, 4, 'M', 6, 'p4n1.webp', 'Xám');
INSERT INTO `product_details` VALUES (22, 4, 'L', 7, 'p4n2.webp', 'Trắng');
INSERT INTO `product_details` VALUES (23, 4, 'XL', 9, 'p4n3.webp', 'Nâu');
INSERT INTO `product_details` VALUES (24, 4, 'XXL', 15, 'p4n4.webp', 'Kem');
INSERT INTO `product_details` VALUES (25, 4, 'M', 7, 'p4n5.webp', 'Đen');
INSERT INTO `product_details` VALUES (26, 4, 'XL', 9, 'p4n6.webp', 'Trắng');
INSERT INTO `product_details` VALUES (27, 4, 'XXL', 10, 'p4n7.webp', 'Trắng');
INSERT INTO `product_details` VALUES (28, 5, 'M', 11, 'p5n1.webp', 'Xám');
INSERT INTO `product_details` VALUES (29, 5, 'L', 9, 'p5n2.webp', 'Đen');
INSERT INTO `product_details` VALUES (30, 5, 'XL', 10, 'p5n3.webp', 'Xám');
INSERT INTO `product_details` VALUES (31, 5, 'XXL', 11, 'p5n4.webp', 'Xám');
INSERT INTO `product_details` VALUES (32, 5, 'M', 7, 'p5n2.webp', 'Đen');
INSERT INTO `product_details` VALUES (33, 5, 'XL', 19, 'p5n5.webp', 'Đen');
INSERT INTO `product_details` VALUES (34, 5, 'XXL', 9, 'p5n5.webp', 'Đen');
INSERT INTO `product_details` VALUES (35, 5, 'L', 16, 'p5n6.webp', 'Xám');

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `typeId` int(11) NOT NULL,
  `categoryId` int(11) NOT NULL,
  `supplierId` int(11) NOT NULL,
  `productName` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `releaseDate` date NOT NULL,
  `unitSold` int(11) NOT NULL DEFAULT 0,
  `unitPrice` decimal(10, 2) NOT NULL,
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `typeId`(`typeId`) USING BTREE,
  INDEX `categoryId`(`categoryId`) USING BTREE,
  INDEX `products_ibfk_3`(`supplierId`) USING BTREE,
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `types` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`categoryId`) REFERENCES `categories` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `products_ibfk_3` FOREIGN KEY (`supplierId`) REFERENCES `suppliers` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES (1, 2, 4, 1, 'Quần tây PEALO form cạp cao có xếp li', 'Quần đùi xà lỏn của nhân dón mặc', '2024-12-15', 50, 274550.00, b'1');
INSERT INTO `products` VALUES (2, 2, 4, 1, 'Quần kaki ống suông nam PEALO', 'Mô tả sản phẩm: Quần kaki ống suông nam PEALO casual pants trơn basic chất liệu mới Loose Pants phong cách Minimalism Menswear... (Rút gọn nếu cần)', '2024-12-17', 40, 280000.00, b'1');
INSERT INTO `products` VALUES (3, 2, 4, 2, 'Quần tây quần vải cạp ống rộng', 'Quần tây quần vải cạp ống rộng - B Brown Wide Fit Trousers... (Rút gọn nếu cần)', '2024-10-14', 100, 470000.00, b'1');
INSERT INTO `products` VALUES (4, 2, 4, 3, 'Quần Dài Ống Rộng Hàn Quốc Hàng Cao Cấp', 'Tên sản phẩm: Quần Dài Ống Rộng Hàn Quốc Hàng Cao Cấp BBrand... (Rút gọn nếu cần)', '2024-11-19', 80, 490000.00, b'1');
INSERT INTO `products` VALUES (5, 2, 4, 4, 'QUẦN DÁNG XUÔNG XẾP LY BY RUYCH.', 'Size: M, L, XL...\r\n... (Rút gọn nếu cần)', '2024-10-07', 110, 369000.00, b'1');

-- ----------------------------
-- Table structure for resetpasswordtokens
-- ----------------------------
DROP TABLE IF EXISTS `resetpasswordtokens`;
CREATE TABLE `resetpasswordtokens`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT current_timestamp(),
  `expiredAt` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE,
  CONSTRAINT `resetpasswordtokens_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resetpasswordtokens
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, 'Admin');
INSERT INTO `roles` VALUES (2, 'User');

-- ----------------------------
-- Table structure for sales
-- ----------------------------
DROP TABLE IF EXISTS `sales`;
CREATE TABLE `sales`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `salesName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `salesDescription` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sales
-- ----------------------------

-- ----------------------------
-- Table structure for sales_product
-- ----------------------------
DROP TABLE IF EXISTS `sales_product`;
CREATE TABLE `sales_product`  (
  `productId` int(11) NOT NULL,
  `saleId` int(11) NOT NULL,
  INDEX `productId`(`productId`) USING BTREE,
  INDEX `saleId`(`saleId`) USING BTREE,
  CONSTRAINT `sales_product_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sales_product_ibfk_2` FOREIGN KEY (`saleId`) REFERENCES `sales` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sales_product
-- ----------------------------

-- ----------------------------
-- Table structure for shipping
-- ----------------------------
DROP TABLE IF EXISTS `shipping`;
CREATE TABLE `shipping`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL,
  `shippingAddress` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `shippingMethod` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `shippingStatus` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `shippingDate` datetime NOT NULL DEFAULT current_timestamp(),
  `deliveryDate` datetime NULL DEFAULT NULL,
  `trackingNumber` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `orderId`(`orderId`) USING BTREE,
  CONSTRAINT `shipping_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shipping
-- ----------------------------

-- ----------------------------
-- Table structure for suppliers
-- ----------------------------
DROP TABLE IF EXISTS `suppliers`;
CREATE TABLE `suppliers`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `supplierName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `supplierImg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of suppliers
-- ----------------------------
INSERT INTO `suppliers` VALUES (1, 'PEALO', '');
INSERT INTO `suppliers` VALUES (2, 'B Brown Studio', '');
INSERT INTO `suppliers` VALUES (3, 'BBRAND', '');
INSERT INTO `suppliers` VALUES (4, 'RUYCH STUDIO', '');

-- ----------------------------
-- Table structure for types
-- ----------------------------
DROP TABLE IF EXISTS `types`;
CREATE TABLE `types`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of types
-- ----------------------------
INSERT INTO `types` VALUES (1, 'Áo');
INSERT INTO `types` VALUES (2, 'Quần');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `firstName` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `lastName` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` bit(1) NOT NULL,
  `createdAt` datetime NULL DEFAULT current_timestamp(),
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `roleId`(`roleId`) USING BTREE,
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', 'admin', 'Mạnh', 'Hoàng Lê Nguyên', 'admin@gmail.com', 'anhday', 'DakNong', '099999999', b'1', '2024-12-24 22:34:35', 1);
INSERT INTO `users` VALUES (2, 'user1', 'user1', 'Luật', 'Hoàng Quốc', 'luatday@gmail.com', 'luatday', 'SaiGon', '011111111', b'1', '2024-12-24 22:35:54', 2);
INSERT INTO `users` VALUES (3, 'user2', 'user2', 'Nam', 'Phạm Quốc Phương', 'namday@gmai.com', 'namday', 'SaiGon', '022222222', b'1', '2024-12-24 22:37:22', 2);

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for types
-- ----------------------------

DROP TABLE IF EXISTS `UserDetails`;
CREATE TABLE `UserDetails` (
    `user_id` int(11) NOT NULL,
    `gender` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `birth_date` date NOT NULL,
    `height` decimal(5,2) NOT NULL,
    `weight` decimal(5,2) NOT NULL,
    PRIMARY KEY (`user_id`),
    CONSTRAINT `UserDetails_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

