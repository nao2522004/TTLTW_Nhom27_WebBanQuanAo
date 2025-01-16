-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th1 16, 2025 lúc 03:27 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `clothing_store_db`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `blogs`
--

CREATE TABLE `blogs` (
  `id` int(11) NOT NULL,
  `authorId` int(11) NOT NULL,
  `title` varchar(200) NOT NULL,
  `content` text NOT NULL,
  `thumbnailImage` varchar(255) NOT NULL,
  `createdDate` datetime NOT NULL,
  `updatedDate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cart`
--

CREATE TABLE `cart` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `createdDate` datetime NOT NULL DEFAULT current_timestamp(),
  `updatedDate` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cartdetail`
--

CREATE TABLE `cartdetail` (
  `id` int(11) NOT NULL,
  `cartId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `couponId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unitPrice` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `typeId` int(11) NOT NULL,
  `name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `categories`
--

INSERT INTO `categories` (`id`, `typeId`, `name`) VALUES
(1, 1, 'Nam'),
(2, 1, 'Nữ'),
(3, 1, 'Trẻ em'),
(4, 2, 'Nam'),
(5, 2, 'Nữ'),
(6, 2, 'Trẻ em');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `collections`
--

CREATE TABLE `collections` (
  `id` int(11) NOT NULL,
  `collectionsName` varchar(100) NOT NULL,
  `collectionsDescription` text NOT NULL,
  `startDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `collections_product`
--

CREATE TABLE `collections_product` (
  `productId` int(11) NOT NULL,
  `collectionId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `comment`
--

CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `content` text NOT NULL,
  `rating` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `coupons`
--

CREATE TABLE `coupons` (
  `id` int(11) NOT NULL,
  `code` varchar(10) NOT NULL,
  `remainingCount` int(11) NOT NULL,
  `discount` float NOT NULL,
  `decription` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `coupons`
--

INSERT INTO `coupons` (`id`, `code`, `remainingCount`, `discount`, `decription`) VALUES
(1, 'abc', 10, 0.2, 'Ưu đãi, giảm giá đặc biệt'),
(2, 'bcd', 10, 0.1, 'Ưu đãi, giảm giá đặc biệt'),
(3, 'def', 10, 0.3, 'Ưu đãi, giảm giá đặc biệt');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `favourite`
--

CREATE TABLE `favourite` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `productId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `notifications`
--

CREATE TABLE `notifications` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `message` text NOT NULL,
  `notificationDate` datetime NOT NULL DEFAULT current_timestamp(),
  `isRead` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orderitem`
--

CREATE TABLE `orderitem` (
  `id` int(11) NOT NULL,
  `orderId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unitPrice` decimal(10,2) NOT NULL,
  `discount` float NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `paymentId` int(11) NOT NULL,
  `couponId` int(11) DEFAULT NULL,
  `orderDate` datetime DEFAULT current_timestamp(),
  `totalPrice` decimal(10,2) NOT NULL,
  `status` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `orders`
--

INSERT INTO `orders` (`id`, `userId`, `paymentId`, `couponId`, `orderDate`, `totalPrice`, `status`) VALUES
(1, 2, 2, NULL, '2025-01-16 20:44:26', 100000.00, b'1'),
(2, 3, 2, NULL, '2025-01-16 20:44:47', 100000.00, b'1'),
(3, 3, 2, NULL, '2025-01-16 20:45:47', 500000.00, b'1');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `payments`
--

CREATE TABLE `payments` (
  `id` int(11) NOT NULL,
  `paymentMethod` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `payments`
--

INSERT INTO `payments` (`id`, `paymentMethod`) VALUES
(1, 'Thanh toán online'),
(2, 'Thanh toán tiền mặt');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `typeId` int(11) NOT NULL,
  `categoryId` int(11) NOT NULL,
  `supplierId` int(11) NOT NULL,
  `productName` text NOT NULL,
  `description` text NOT NULL,
  `releaseDate` date NOT NULL,
  `unitSold` int(11) NOT NULL DEFAULT 0,
  `unitPrice` decimal(10,2) NOT NULL,
  `status` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`id`, `typeId`, `categoryId`, `supplierId`, `productName`, `description`, `releaseDate`, `unitSold`, `unitPrice`, `status`) VALUES
(1, 2, 4, 1, 'Quần tây PEALO form cạp cao có xếp li', 'Quần đùi xà lỏn của nhân dón mặc', '2024-12-15', 50, 274550.00, b'1'),
(2, 2, 4, 1, 'Quần kaki ống suông nam PEALO', 'Mô tả sản phẩm: Quần kaki ống suông nam PEALO casual pants trơn basic chất liệu mới Loose Pants phong cách Minimalism Menswear... (Rút gọn nếu cần)', '2024-12-17', 40, 280000.00, b'1'),
(3, 2, 4, 2, 'Quần tây quần vải cạp ống rộng', 'Quần tây quần vải cạp ống rộng - B Brown Wide Fit Trousers... (Rút gọn nếu cần)', '2024-10-14', 100, 470000.00, b'1'),
(4, 2, 4, 3, 'Quần Dài Ống Rộng Hàn Quốc Hàng Cao Cấp', 'Tên sản phẩm: Quần Dài Ống Rộng Hàn Quốc Hàng Cao Cấp BBrand... (Rút gọn nếu cần)', '2024-11-19', 80, 490000.00, b'1'),
(5, 2, 4, 4, 'QUẦN DÁNG XUÔNG XẾP LY BY RUYCH.', 'Size: M, L, XL...\r\n... (Rút gọn nếu cần)', '2024-10-07', 110, 369000.00, b'1');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_details`
--

CREATE TABLE `product_details` (
  `id` int(11) NOT NULL,
  `productId` int(11) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `product_details`
--

INSERT INTO `product_details` (`id`, `productId`, `size`, `stock`, `image`, `color`) VALUES
(1, 1, 'M', 10, 'p1n1.webp', 'Nâu dạ'),
(2, 1, 'L', 11, 'p1n2.webp', 'Nâu dạ'),
(3, 1, 'XL', 7, 'p1n5.webp', 'Nâu dạ'),
(4, 1, 'XXL', 4, 'p1n7.webp', 'Nâu dạ'),
(5, 1, 'M', 8, 'p1n3.webp', 'Đen xám'),
(6, 1, 'L', 5, 'p1n6.webp', 'Đen xám'),
(7, 2, 'M', 12, 'p2n1.webp', 'Nâu nhạt'),
(8, 2, 'L', 4, 'p2n6.webp', 'Nâu nhạt'),
(9, 2, 'XL', 9, 'p2n2.webp', 'Trắng'),
(10, 2, 'XXL', 5, 'p2n3.webp', 'Trắng'),
(11, 2, 'M', 14, 'p2n4.webp', 'Xanh rêu'),
(12, 2, 'L', 13, 'p2n5.webp', 'Xanh rêu'),
(13, 3, 'M', 11, 'p3n1.webp', 'Đen'),
(14, 3, 'L', 17, 'p3n2.webp', 'Đen'),
(15, 3, 'XL', 14, 'p3n3.webp', 'Đen'),
(16, 3, 'XXL', 18, 'p3n4.webp', 'Đen'),
(17, 3, 'XXXL', 4, 'p3n5.webp', 'Đen'),
(18, 3, '4XL', 8, 'p3n6.webp', 'Đen'),
(19, 3, 'M', 19, 'p3n7.webp', 'Nâu chocolate'),
(20, 3, 'L', 9, 'p3n8.webp', 'Nâu chocolate'),
(21, 4, 'M', 6, 'p4n1.webp', 'Xám'),
(22, 4, 'L', 7, 'p4n2.webp', 'Trắng'),
(23, 4, 'XL', 9, 'p4n3.webp', 'Nâu'),
(24, 4, 'XXL', 15, 'p4n4.webp', 'Kem'),
(25, 4, 'M', 7, 'p4n5.webp', 'Đen'),
(26, 4, 'XL', 9, 'p4n6.webp', 'Trắng'),
(27, 4, 'XXL', 10, 'p4n7.webp', 'Trắng'),
(28, 5, 'M', 11, 'p5n1.webp', 'Xám'),
(29, 5, 'L', 9, 'p5n2.webp', 'Đen'),
(30, 5, 'XL', 10, 'p5n3.webp', 'Xám'),
(31, 5, 'XXL', 11, 'p5n4.webp', 'Xám'),
(32, 5, 'M', 7, 'p5n2.webp', 'Đen'),
(33, 5, 'XL', 19, 'p5n5.webp', 'Đen'),
(34, 5, 'XXL', 9, 'p5n5.webp', 'Đen'),
(35, 5, 'L', 16, 'p5n6.webp', 'Xám');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `resetpasswordtokens`
--

CREATE TABLE `resetpasswordtokens` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `token` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT current_timestamp(),
  `expiredAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `roleName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`id`, `roleName`) VALUES
(1, 'Admin'),
(2, 'User');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sales`
--

CREATE TABLE `sales` (
  `id` int(11) NOT NULL,
  `salesName` varchar(100) NOT NULL,
  `salesDescription` text NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sales_product`
--

CREATE TABLE `sales_product` (
  `productId` int(11) NOT NULL,
  `saleId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `suppliers`
--

CREATE TABLE `suppliers` (
  `id` int(11) NOT NULL,
  `supplierName` varchar(100) NOT NULL,
  `supplierImg` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `suppliers`
--

INSERT INTO `suppliers` (`id`, `supplierName`, `supplierImg`) VALUES
(1, 'PEALO', ''),
(2, 'B Brown Studio', ''),
(3, 'BBRAND', ''),
(4, 'RUYCH STUDIO', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `types`
--

CREATE TABLE `types` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `types`
--

INSERT INTO `types` (`id`, `name`) VALUES
(1, 'Áo'),
(2, 'Quần');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `userName` varchar(30) NOT NULL,
  `password` varchar(64) NOT NULL,
  `firstName` text NOT NULL,
  `lastName` text NOT NULL,
  `email` text NOT NULL,
  `avatar` varchar(200) NOT NULL,
  `address` text NOT NULL,
  `phone` text NOT NULL,
  `status` bit(1) NOT NULL,
  `createdAt` datetime DEFAULT current_timestamp(),
  `roleId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `userName`, `password`, `firstName`, `lastName`, `email`, `avatar`, `address`, `phone`, `status`, `createdAt`, `roleId`) VALUES
(1, 'admin', 'admin', 'Mạnh', 'Hoàng Lê Nguyên', 'admin@gmail.com', 'anhday', 'DakNong', '099999999', b'1', '2024-12-24 22:34:35', 1),
(2, 'user1', 'user1', 'Luật', 'Hoàng Quốc', 'luatday@gmail.com', 'luatday', 'SaiGon', '011111111', b'1', '2024-12-24 22:35:54', 2),
(3, 'user2', 'user2', 'Nam', 'Phạm Quốc Phương', 'namday@gmai.com', 'namday', 'SaiGon', '022222222', b'1', '2024-12-24 22:37:22', 2);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `blogs`
--
ALTER TABLE `blogs`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `authorId` (`authorId`) USING BTREE;

--
-- Chỉ mục cho bảng `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `userId` (`userId`) USING BTREE;

--
-- Chỉ mục cho bảng `cartdetail`
--
ALTER TABLE `cartdetail`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `cartId` (`cartId`) USING BTREE,
  ADD KEY `productId` (`productId`) USING BTREE,
  ADD KEY `couponId` (`couponId`) USING BTREE;

--
-- Chỉ mục cho bảng `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `typeId` (`typeId`) USING BTREE;

--
-- Chỉ mục cho bảng `collections`
--
ALTER TABLE `collections`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `collections_product`
--
ALTER TABLE `collections_product`
  ADD KEY `productId` (`productId`) USING BTREE,
  ADD KEY `collectionId` (`collectionId`) USING BTREE;

--
-- Chỉ mục cho bảng `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `userId` (`userId`) USING BTREE,
  ADD KEY `productId` (`productId`) USING BTREE;

--
-- Chỉ mục cho bảng `coupons`
--
ALTER TABLE `coupons`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `favourite`
--
ALTER TABLE `favourite`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `userId` (`userId`) USING BTREE,
  ADD KEY `productId` (`productId`) USING BTREE;

--
-- Chỉ mục cho bảng `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `userId` (`userId`) USING BTREE;

--
-- Chỉ mục cho bảng `orderitem`
--
ALTER TABLE `orderitem`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `orderId` (`orderId`) USING BTREE,
  ADD KEY `productId` (`productId`) USING BTREE;

--
-- Chỉ mục cho bảng `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `userId` (`userId`) USING BTREE,
  ADD KEY `paymentId` (`paymentId`) USING BTREE,
  ADD KEY `couponId` (`couponId`) USING BTREE;

--
-- Chỉ mục cho bảng `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `typeId` (`typeId`) USING BTREE,
  ADD KEY `categoryId` (`categoryId`) USING BTREE,
  ADD KEY `products_ibfk_3` (`supplierId`) USING BTREE;

--
-- Chỉ mục cho bảng `product_details`
--
ALTER TABLE `product_details`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `productId_fk` (`productId`) USING BTREE;

--
-- Chỉ mục cho bảng `resetpasswordtokens`
--
ALTER TABLE `resetpasswordtokens`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `userId` (`userId`) USING BTREE;

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `sales_product`
--
ALTER TABLE `sales_product`
  ADD KEY `productId` (`productId`) USING BTREE,
  ADD KEY `saleId` (`saleId`) USING BTREE;

--
-- Chỉ mục cho bảng `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `types`
--
ALTER TABLE `types`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD UNIQUE KEY `userName` (`userName`),
  ADD KEY `roleId` (`roleId`) USING BTREE;

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `blogs`
--
ALTER TABLE `blogs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `cart`
--
ALTER TABLE `cart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `cartdetail`
--
ALTER TABLE `cartdetail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `collections`
--
ALTER TABLE `collections`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `comment`
--
ALTER TABLE `comment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `coupons`
--
ALTER TABLE `coupons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `favourite`
--
ALTER TABLE `favourite`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `orderitem`
--
ALTER TABLE `orderitem`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `payments`
--
ALTER TABLE `payments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `product_details`
--
ALTER TABLE `product_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT cho bảng `resetpasswordtokens`
--
ALTER TABLE `resetpasswordtokens`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `sales`
--
ALTER TABLE `sales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `types`
--
ALTER TABLE `types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `blogs`
--
ALTER TABLE `blogs`
  ADD CONSTRAINT `blogs_ibfk_1` FOREIGN KEY (`authorId`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `cartdetail`
--
ALTER TABLE `cartdetail`
  ADD CONSTRAINT `cartdetail_ibfk_1` FOREIGN KEY (`cartId`) REFERENCES `cart` (`id`),
  ADD CONSTRAINT `cartdetail_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `cartdetail_ibfk_3` FOREIGN KEY (`couponId`) REFERENCES `coupons` (`id`);

--
-- Các ràng buộc cho bảng `categories`
--
ALTER TABLE `categories`
  ADD CONSTRAINT `categories_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `types` (`id`);

--
-- Các ràng buộc cho bảng `collections_product`
--
ALTER TABLE `collections_product`
  ADD CONSTRAINT `collections_product_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `collections_product_ibfk_2` FOREIGN KEY (`collectionId`) REFERENCES `collections` (`id`);

--
-- Các ràng buộc cho bảng `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `products` (`id`);

--
-- Các ràng buộc cho bảng `favourite`
--
ALTER TABLE `favourite`
  ADD CONSTRAINT `favourite_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `favourite_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `products` (`id`);

--
-- Các ràng buộc cho bảng `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `orderitem`
--
ALTER TABLE `orderitem`
  ADD CONSTRAINT `orderitem_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `orderitem_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `products` (`id`);

--
-- Các ràng buộc cho bảng `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`paymentId`) REFERENCES `payments` (`id`),
  ADD CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`couponId`) REFERENCES `coupons` (`id`);

--
-- Các ràng buộc cho bảng `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `types` (`id`),
  ADD CONSTRAINT `products_ibfk_2` FOREIGN KEY (`categoryId`) REFERENCES `categories` (`id`),
  ADD CONSTRAINT `products_ibfk_3` FOREIGN KEY (`supplierId`) REFERENCES `suppliers` (`id`);

--
-- Các ràng buộc cho bảng `product_details`
--
ALTER TABLE `product_details`
  ADD CONSTRAINT `proid_fk` FOREIGN KEY (`productId`) REFERENCES `products` (`id`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `resetpasswordtokens`
--
ALTER TABLE `resetpasswordtokens`
  ADD CONSTRAINT `resetpasswordtokens_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `sales_product`
--
ALTER TABLE `sales_product`
  ADD CONSTRAINT `sales_product_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `sales_product_ibfk_2` FOREIGN KEY (`saleId`) REFERENCES `sales` (`id`);

--
-- Các ràng buộc cho bảng `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
