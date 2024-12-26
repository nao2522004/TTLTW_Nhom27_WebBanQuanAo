-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 26, 2024 lúc 10:58 AM
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
-- Cơ sở dữ liệu: `clothes_shop_db`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `blogs`
--

CREATE TABLE `blogs` (
  `blogid` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  `title` varchar(200) NOT NULL,
  `content` text NOT NULL,
  `thumbnail_image` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cart`
--

CREATE TABLE `cart` (
  `cartid` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_date` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cartdetail`
--

CREATE TABLE `cartdetail` (
  `cartdetailid` int(11) NOT NULL,
  `cart_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `coupon_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unitprice` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories`
--

CREATE TABLE `categories` (
  `categoryid` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `NAME` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `categories`
--

INSERT INTO `categories` (`categoryid`, `type_id`, `NAME`) VALUES
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
  `collectionsid` int(11) NOT NULL,
  `collections_name` varchar(100) NOT NULL,
  `collections_description` text NOT NULL,
  `start_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `collections_product`
--

CREATE TABLE `collections_product` (
  `product_id` int(11) NOT NULL,
  `collections_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `comment`
--

CREATE TABLE `comment` (
  `commentid` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `rating` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `coupons`
--

CREATE TABLE `coupons` (
  `couponid` int(11) NOT NULL,
  `code` varchar(10) NOT NULL,
  `remaining_count` int(11) NOT NULL,
  `discount` float NOT NULL,
  `decription` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `favourite`
--

CREATE TABLE `favourite` (
  `favouriteid` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `notifications`
--

CREATE TABLE `notifications` (
  `notificationid` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `message` text NOT NULL,
  `notification_date` datetime NOT NULL DEFAULT current_timestamp(),
  `is_read` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orderitem`
--

CREATE TABLE `orderitem` (
  `orderitemid` int(11) NOT NULL,
  `orders_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `orderdate` datetime NOT NULL,
  `quantity` int(11) NOT NULL,
  `unitprice` decimal(10,2) NOT NULL,
  `discount` float NOT NULL DEFAULT 0,
  `status` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `ordersid` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `payment_id` int(11) NOT NULL,
  `coupon_id` int(11) DEFAULT NULL,
  `order_date` datetime NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `STATUS` bit(1) NOT NULL,
  `shipping_fee` decimal(10,2) NOT NULL DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `payments`
--

CREATE TABLE `payments` (
  `paymentid` int(11) NOT NULL,
  `payment_method` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

CREATE TABLE `products` (
  `proid` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `supplier_id` int(11) NOT NULL,
  `productname` text NOT NULL,
  `DESCRIPTION` text NOT NULL,
  `releasedate` date NOT NULL,
  `unitSold` int(11) NOT NULL DEFAULT 0,
  `unitprice` decimal(10,2) NOT NULL,
  `STATUS` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`proid`, `type_id`, `category_id`, `supplier_id`, `productname`, `DESCRIPTION`, `releasedate`, `unitSold`, `unitprice`, `STATUS`) VALUES
(1, 2, 4, 1, 'Quần tây PEALO form cạp cao có xếp li', '', '2024-12-15', 50, 274550.00, b'1'),
(2, 2, 4, 1, 'Quần kaki ống suông nam PEALO', 'Mô tả sản phẩm :Quần kaki ống suông nam PEALO casual pants trơn basic chất liệu mới Loose Pants phong cách Minimalism Menswear\r\n_ Trắng - Đen- Be- Xanh Than...v..v\r\nChất liệu:\r\n_ 95% vài kaki, 5% SPANDEX ( CAO CẤP )\r\nXuất sứ: \r\n_ Việt Nam\r\nƯu điểm quần kaki dài :\r\n- Vải Kaki nhung mềm mịn, co giãn tốt giúp bạn thoải mái vận động, di chuyển.\r\n- Màu sắc trắng dễ mặc dễ phối đồ, quần dài kaki trắng phù hợp với tất cả các áo sáng màu lẫn tối màu, có thể phối cùng polo, áo thun, sơ mi, áo vest,...\r\n- Phần eo có đai chỉnh kích thước, giúp eo vừa vặn khi sử dụng\r\n- Nút quần và dây kéo chắc chắn,  đường may kĩ càng.\r\n- Hai túi phía trước được may vát chéo, thêm 2 túi sau sâu rộng để được nhiều đồ, tiện lợi.\r\nKích cỡ: Size S, M, L, XL,XXL\r\nHƯỚNG DẪN CÁCH ĐẶT HÀNG \r\nBước 1: Chọn sản phẩm yêu thích \r\n- Bấm chọn từng sản phẩm cần mua rồi thêm vào giỏ hàng\r\n- Bấm Chat Ngay nếu bạn cần hỏi Người bán thêm thông tin về sản phẩm \r\nBước 2: Bấm vào biểu tượng giỏ hàng > Kiểm tra lại sản phẩm bạn muốn mua > Bấm Mua hàng - Chọn mã miễn phí vận chuyển và mã giảm giá (nếu có) \r\nBước 3: Chọn địa chỉ nhận hàng, đơn vị vận chuyển, phương thức thanh toán phù hợp \r\nBước 4: Bấm Đặt hàng và chờ nhận hàng\r\nHƯỚNG DẪN BẢO QUẢN SẢN PHẨM\r\n- Lộn trái sản phẩm khi giặt, không giặt chung sản phẩm trắng với quần áo tối màu.\r\n- Sử dụng xà phòng trung tính,không sử dụng xà phòng có chất tẩy mạnh.\r\n- Không sử dụng chất tẩy, không ngâm sản phẩm.\r\n- Phơi ngang, không treo móc khi sản phẩm ướt, không phơi trực tiếp dưới ánh nắng mặt trời.\r\n- Bảo quản nơi khô ráo, thoáng mát.\r\n- Đối với các sản phẩm đặc thù: Veston nam nữ, áo blazer, áo phao,... Quý khách vui lòng giặt khô để đảm bảo chất lượng sản phẩm.\r\nCHÍNH SÁCH ĐỔI TRẢ HÀNG \r\n- Hỗ trợ đổi hàng trong vòng 7 ngày kể từ khi giao hàng thành công.\r\n- Sản phẩm được ĐỔI HÀNG bao gồm: đầy đủ tem mác được gắn trên sản phẩm, sản phẩm trong tình trạng mới, sạch sẽ, không bị thay đổi. Trường hợp sản phẩm đã bị cắt tem, mác giá treo trên sản phẩm sẽ không thể đổi hàng.\r\n- Quý khách vui lòng giữ sản phẩm nguyên vẹn, CHỤP lại ảnh sản phẩm còn nguyên tem, mác và gửi cho bên CSKH tại phần tin nhắn với PEALO\r\n- Không hỗ trợ đổi hàng thuộc chương trình khuyến mãi, trừ những sản phẩm có lỗi từ nhà sản xuất.', '2024-12-17', 40, 280000.00, b'1'),
(3, 2, 4, 2, 'Quần tây quần vải cạp ống rộng', 'Quần tây quần vải cạp ống rộng - B Brown Wide Fit Trousers\r\nChi tiết sản phẩm:\r\n- Chẩt liệu vải cao cấp, bền màu khi giặt, cảm giác sử dụng thoải mái và mát mẻ\r\n- Form: Cạp cao, Wide Fit - độ rộng ống quần vừa phải, chiều dài ống vừa qua mắt cá chân\r\n- Màu sắc: Đen, Nâu chocolate\r\nBảng size :\r\nSize S : 1m65 - 1m71, 57 - 62kg\r\nSize M : 1m71 - 1m74, 62 - 67kg\r\nSize L : 1m74 - 1m78, 67 - 74kg\r\nSize XL : 1m78 - 1m86, 75 - 82kg\r\nB Brown khuyến khích các bạn nhắn tin cho shop để được tư vấn size chuẩn nhất.\r\nSHOP CAM KẾT\r\n• Hình ảnh do shop tự chụp và sở hữu bản quyền.\r\n• Chất lượng sản phẩm giống ảnh 100% (do độ sáng màn hình có thể sai số không đáng kể)\r\n• Shop hỗ trợ đổi trả sản phẩm trong các trường hợp nhầm size hoặc sản phẩm bị lỗi.\r\nMàu sắc : Nâu vàng, Nâu chocolate\r\nQuý khách cần thêm thông tin gì cứ nhắn tin cho shop sẽ tư vấn tận tình ạ.', '2024-10-14', 100, 470000.00, b'1'),
(4, 2, 4, 3, 'Quần Dài Ống Rộng Hàn Quốc Hàng Cao Cấp', 'Tên sản phẩm: Quần Dài Ống Rộng Hàn Quốc Hàng Cao Cấp BBrand - Wide Leg Pants\r\nII. Mô tả sản phẩm:\r\n1. Xuất xứ: Hàn Quốc\r\n2. Chất liệu: Hàng cao cấp, chất liệu thoáng mát, dày dặn và bền đẹp\r\n3. Kiểu dáng: Quần dài ống rộng theo phong cách Hàn Quốc hiện đại và thời thượng\r\n4. Màu sắc: Đa dạng màu sắc phong phú, dễ dàng phối hợp với nhiều trang phục khác nhau\r\n5. Size: Có nhiều size để phục vụ cho mọi đối tượng khách hàng, vui lòng xem bảng size đính kèm\r\n6. Thích hợp: Phù hợp với nhiều dịp khác nhau, từ đi chơi, dự tiệc đến văn phòng\r\nIII. Đặc điểm nổi bật:\r\n1. Thiết kế theo xu hướng thời trang mới nhất\r\n2. Chất liệu cao cấp, thoải mái khi mặc\r\n3. Form quần ôm vừa vặn, tôn dáng cho người mặc\r\n4. Dễ dàng phối hợp với áo thun, sơ mi, blazer,...\r\nIV. Lợi ích khi mua sản phẩm:\r\n1. Tạo điểm nhấn cho outfit của bạn\r\n2. Tạo cảm giác thoải mái và tự tin khi diện sản phẩm\r\n3. Sản phẩm phù hợp với nhiều phong cách thời trang khác nhau\r\n4. Sự lựa chọn hoàn hảo cho phong cách thời trang cá nhân của bạn\r\nMàu sắc có thể sẽ khác trên ảnh một chút do điều kiện ánh sáng.', '2024-11-19', 80, 490000.00, b'1'),
(5, 2, 4, 4, 'QUẦN DÁNG XUÔNG XẾP LY BY RUYCH.', 'size : m l xl\r\n- M : dài 100 , bụng dưới 75, ống 25 >62kg \r\n- L : dài 103 , bụng dưới 80 , ống 25 > 67Kg\r\n- Xl : dài 104 , bụng dưới 85 , ống 26 >77kg\r\n• màu : đen / xám \r\n• xếp ly siêu tinh tế và thanh lịch\r\n• hack chiều cao siêu dễ + giấu được khuyết điểm như chân cong , chân gầy , chân bắp đùi to \r\n• phối siêu dễ tee / shirt / polo / ….\r\n• Form : nam và nữ \r\n• mang đi học / đi làm / đi chơi siêu thoải mái còn tự tin lắm các bác ạ !\r\n- ruych đang bị hạn chế tin nhắn nên các cậu (.) để ruych sẽ inbox trước !\r\n- Các cậu mua hàng cẩn thận Các shop Fake nhé.', '2024-10-07', 110, 369000.00, b'1');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product_details`
--

CREATE TABLE `product_details` (
  `detailid` int(11) NOT NULL,
  `pro_id` int(11) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `product_details`
--

INSERT INTO `product_details` (`detailid`, `pro_id`, `size`, `stock`, `image`, `color`) VALUES
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
  `tokenid` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `expired_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `roleid` int(11) NOT NULL,
  `role_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`roleid`, `role_name`) VALUES
(1, 'Admin'),
(2, 'User');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sales`
--

CREATE TABLE `sales` (
  `salesid` int(11) NOT NULL,
  `sales_name` varchar(100) NOT NULL,
  `sales_description` text NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sales_product`
--

CREATE TABLE `sales_product` (
  `product_id` int(11) NOT NULL,
  `sales_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `shipping`
--

CREATE TABLE `shipping` (
  `shippingid` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `shipping_address` text NOT NULL,
  `shipping_method` varchar(50) NOT NULL,
  `shipping_status` varchar(50) NOT NULL,
  `shipping_date` datetime NOT NULL DEFAULT current_timestamp(),
  `delivery_date` datetime DEFAULT NULL,
  `tracking_number` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `suppliers`
--

CREATE TABLE `suppliers` (
  `suppliersid` int(11) NOT NULL,
  `suppliersname` varchar(100) NOT NULL,
  `suppliersimg` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `suppliers`
--

INSERT INTO `suppliers` (`suppliersid`, `suppliersname`, `suppliersimg`) VALUES
(1, 'PEALO', ''),
(2, 'B Brown Studio', ''),
(3, 'BBRAND', ''),
(4, 'RUYCH STUDIO', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `types`
--

CREATE TABLE `types` (
  `typeid` int(11) NOT NULL,
  `NAME` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `types`
--

INSERT INTO `types` (`typeid`, `NAME`) VALUES
(1, 'Áo'),
(2, 'Quần');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `userid` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `role_id` int(11) NOT NULL,
  `firstname` text NOT NULL,
  `lastname` text NOT NULL,
  `email` text NOT NULL,
  `avatar` varchar(200) NOT NULL,
  `password` varchar(64) NOT NULL,
  `address` text NOT NULL,
  `phone` text NOT NULL,
  `status` bit(1) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`userid`, `username`, `role_id`, `firstname`, `lastname`, `email`, `avatar`, `password`, `address`, `phone`, `status`, `created_at`) VALUES
(1, 'admin', 1, 'Mạnh', 'Hoàng Lê Nguyên', 'admin@gmail.com', 'anhday', 'admin', 'DakNong', '099999999', b'1', '2024-12-24 22:34:35'),
(2, 'user1', 2, 'Luật', 'Hoàng Quốc', 'luatday@gmail.com', 'luatday', 'user1', 'SaiGon', '011111111', b'1', '2024-12-24 22:35:54'),
(3, 'user2', 2, 'Nam', 'Phạm Quốc Phương', 'namday@gmai.com', 'namday', 'user2', 'SaiGon', '022222222', b'1', '2024-12-24 22:37:22');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `blogs`
--
ALTER TABLE `blogs`
  ADD PRIMARY KEY (`blogid`),
  ADD KEY `author_id` (`author_id`);

--
-- Chỉ mục cho bảng `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`cartid`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `cartdetail`
--
ALTER TABLE `cartdetail`
  ADD PRIMARY KEY (`cartdetailid`),
  ADD KEY `cart_id` (`cart_id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `coupon_id` (`coupon_id`);

--
-- Chỉ mục cho bảng `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`categoryid`),
  ADD KEY `type_id` (`type_id`);

--
-- Chỉ mục cho bảng `collections`
--
ALTER TABLE `collections`
  ADD PRIMARY KEY (`collectionsid`);

--
-- Chỉ mục cho bảng `collections_product`
--
ALTER TABLE `collections_product`
  ADD KEY `product_id` (`product_id`),
  ADD KEY `collections_id` (`collections_id`);

--
-- Chỉ mục cho bảng `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`commentid`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Chỉ mục cho bảng `coupons`
--
ALTER TABLE `coupons`
  ADD PRIMARY KEY (`couponid`);

--
-- Chỉ mục cho bảng `favourite`
--
ALTER TABLE `favourite`
  ADD PRIMARY KEY (`favouriteid`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Chỉ mục cho bảng `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`notificationid`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `orderitem`
--
ALTER TABLE `orderitem`
  ADD PRIMARY KEY (`orderitemid`),
  ADD KEY `orders_id` (`orders_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Chỉ mục cho bảng `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`ordersid`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `payment_id` (`payment_id`),
  ADD KEY `coupon_id` (`coupon_id`);

--
-- Chỉ mục cho bảng `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`paymentid`) USING BTREE;

--
-- Chỉ mục cho bảng `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`proid`),
  ADD KEY `type_id` (`type_id`),
  ADD KEY `category_id` (`category_id`),
  ADD KEY `products_ibfk_3` (`supplier_id`);

--
-- Chỉ mục cho bảng `product_details`
--
ALTER TABLE `product_details`
  ADD PRIMARY KEY (`detailid`),
  ADD KEY `proid_fk` (`pro_id`);

--
-- Chỉ mục cho bảng `resetpasswordtokens`
--
ALTER TABLE `resetpasswordtokens`
  ADD PRIMARY KEY (`tokenid`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`roleid`) USING BTREE;

--
-- Chỉ mục cho bảng `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`salesid`);

--
-- Chỉ mục cho bảng `sales_product`
--
ALTER TABLE `sales_product`
  ADD KEY `product_id` (`product_id`),
  ADD KEY `sales_id` (`sales_id`);

--
-- Chỉ mục cho bảng `shipping`
--
ALTER TABLE `shipping`
  ADD PRIMARY KEY (`shippingid`),
  ADD KEY `order_id` (`order_id`);

--
-- Chỉ mục cho bảng `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`suppliersid`) USING BTREE;

--
-- Chỉ mục cho bảng `types`
--
ALTER TABLE `types`
  ADD PRIMARY KEY (`typeid`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userid`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `blogs`
--
ALTER TABLE `blogs`
  MODIFY `blogid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `cart`
--
ALTER TABLE `cart`
  MODIFY `cartid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `cartdetail`
--
ALTER TABLE `cartdetail`
  MODIFY `cartdetailid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `categories`
--
ALTER TABLE `categories`
  MODIFY `categoryid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `collections`
--
ALTER TABLE `collections`
  MODIFY `collectionsid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `comment`
--
ALTER TABLE `comment`
  MODIFY `commentid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `coupons`
--
ALTER TABLE `coupons`
  MODIFY `couponid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `favourite`
--
ALTER TABLE `favourite`
  MODIFY `favouriteid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `notifications`
--
ALTER TABLE `notifications`
  MODIFY `notificationid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `orderitem`
--
ALTER TABLE `orderitem`
  MODIFY `orderitemid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `orders`
--
ALTER TABLE `orders`
  MODIFY `ordersid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `payments`
--
ALTER TABLE `payments`
  MODIFY `paymentid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `products`
--
ALTER TABLE `products`
  MODIFY `proid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `product_details`
--
ALTER TABLE `product_details`
  MODIFY `detailid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT cho bảng `resetpasswordtokens`
--
ALTER TABLE `resetpasswordtokens`
  MODIFY `tokenid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `roles`
--
ALTER TABLE `roles`
  MODIFY `roleid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `sales`
--
ALTER TABLE `sales`
  MODIFY `salesid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `shipping`
--
ALTER TABLE `shipping`
  MODIFY `shippingid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `suppliersid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `types`
--
ALTER TABLE `types`
  MODIFY `typeid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `userid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `blogs`
--
ALTER TABLE `blogs`
  ADD CONSTRAINT `blogs_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `users` (`userid`);

--
-- Các ràng buộc cho bảng `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`userid`);

--
-- Các ràng buộc cho bảng `cartdetail`
--
ALTER TABLE `cartdetail`
  ADD CONSTRAINT `cartdetail_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cartid`),
  ADD CONSTRAINT `cartdetail_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`proid`),
  ADD CONSTRAINT `cartdetail_ibfk_3` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`couponid`);

--
-- Các ràng buộc cho bảng `categories`
--
ALTER TABLE `categories`
  ADD CONSTRAINT `categories_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `types` (`typeid`);

--
-- Các ràng buộc cho bảng `collections_product`
--
ALTER TABLE `collections_product`
  ADD CONSTRAINT `collections_product_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`proid`),
  ADD CONSTRAINT `collections_product_ibfk_2` FOREIGN KEY (`collections_id`) REFERENCES `collections` (`collectionsid`);

--
-- Các ràng buộc cho bảng `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`userid`),
  ADD CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`proid`);

--
-- Các ràng buộc cho bảng `favourite`
--
ALTER TABLE `favourite`
  ADD CONSTRAINT `favourite_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`userid`),
  ADD CONSTRAINT `favourite_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`proid`);

--
-- Các ràng buộc cho bảng `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`userid`);

--
-- Các ràng buộc cho bảng `orderitem`
--
ALTER TABLE `orderitem`
  ADD CONSTRAINT `orderitem_ibfk_1` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`ordersid`),
  ADD CONSTRAINT `orderitem_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`proid`);

--
-- Các ràng buộc cho bảng `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`userid`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`payment_id`) REFERENCES `payments` (`paymentid`),
  ADD CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`couponid`);

--
-- Các ràng buộc cho bảng `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `types` (`typeid`),
  ADD CONSTRAINT `products_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `categories` (`categoryid`),
  ADD CONSTRAINT `products_ibfk_3` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`suppliersid`);

--
-- Các ràng buộc cho bảng `product_details`
--
ALTER TABLE `product_details`
  ADD CONSTRAINT `proid_fk` FOREIGN KEY (`pro_id`) REFERENCES `products` (`proid`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `resetpasswordtokens`
--
ALTER TABLE `resetpasswordtokens`
  ADD CONSTRAINT `resetpasswordtokens_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`userid`);

--
-- Các ràng buộc cho bảng `sales_product`
--
ALTER TABLE `sales_product`
  ADD CONSTRAINT `sales_product_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`proid`),
  ADD CONSTRAINT `sales_product_ibfk_2` FOREIGN KEY (`sales_id`) REFERENCES `sales` (`salesid`);

--
-- Các ràng buộc cho bảng `shipping`
--
ALTER TABLE `shipping`
  ADD CONSTRAINT `shipping_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`ordersid`);

--
-- Các ràng buộc cho bảng `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`roleid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
