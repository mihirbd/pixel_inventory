-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 21, 2022 at 07:43 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `organic1_pixel_inventory`
--

-- --------------------------------------------------------

--
-- Table structure for table `add_brand`
--

CREATE TABLE `add_brand` (
  `Id` int(3) NOT NULL,
  `brand_name` varchar(30) NOT NULL,
  `brand_description` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `add_brand`
--

INSERT INTO `add_brand` (`Id`, `brand_name`, `brand_description`) VALUES
(1, 'Flame', 'A Electric Brand of Bangladesh'),
(2, 'Pixel', 'A Renoun Electric Brand');

-- --------------------------------------------------------

--
-- Table structure for table `add_category`
--

CREATE TABLE `add_category` (
  `id` int(5) NOT NULL,
  `category_name` varchar(30) NOT NULL,
  `description` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `add_category`
--

INSERT INTO `add_category` (`id`, `category_name`, `description`) VALUES
(1, 'LED BULB', 'A light-emitting diode'),
(2, 'Surface LED', 'semiconductor light source that emits ligh'),
(5, 'Ceilling Fan', 'To find the Best Fan Price in Bangladesh yes'),
(6, 'DIM Light', 'A Dimmable lignt that use in night'),
(7, 'Laptop', 'A machine that help us to do work easyly k'),
(8, 'Computer', 'habijabi'),
(9, 'Sound Box', 'Sound Box Sound Box Sound Box'),
(10, 'Mouse', 'Mouse Mouse Mouse'),
(11, 'Juice', 'Juice JuiceJuiceJuice'),
(12, 'Pendant Holder', 'Pendant HolderPendant HolderPendant Holder'),
(25, 'Holder', 'HOlder');

-- --------------------------------------------------------

--
-- Table structure for table `add_seller`
--

CREATE TABLE `add_seller` (
  `Id` int(11) NOT NULL,
  `saller_name` varchar(30) NOT NULL,
  `mobile` varchar(15) NOT NULL,
  `factory_name` varchar(50) NOT NULL,
  `country` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `add_seller`
--

INSERT INTO `add_seller` (`Id`, `saller_name`, `mobile`, `factory_name`, `country`) VALUES
(2, 'Mr. Wanzu Sam', '09924657568', 'Xyz China Company', 'China'),
(3, 'Raj Sankor', '099234568421', 'Kolkata Electric Co. Ltd.', 'India'),
(4, 'Omar Faquq Suzon', '09923456843', 'Pixel Electric Co. Ltd.', 'Bangladesh');

-- --------------------------------------------------------

--
-- Table structure for table `add_stock`
--

CREATE TABLE `add_stock` (
  `SL` int(5) NOT NULL,
  `product_id` varchar(30) NOT NULL,
  `product_name` varchar(100) NOT NULL,
  `stock` double(5,0) NOT NULL,
  `category` varchar(60) NOT NULL,
  `price` double(6,1) NOT NULL,
  `Seller_name` varchar(30) NOT NULL,
  `Seller_Country` varchar(20) NOT NULL,
  `Brand` varchar(20) NOT NULL,
  `Date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `add_stock`
--

INSERT INTO `add_stock` (`SL`, `product_id`, `product_name`, `stock`, `category`, `price`, `Seller_name`, `Seller_Country`, `Brand`, `Date`) VALUES
(9, 'LE18B2', 'Pixel LED Bulb 18W B22 ', 829, 'LED BULB', 220.0, 'Abul Kashem', 'China', 'Pixel', '2022-05-30'),
(10, 'LE05B2 ', 'Pixel LED Bulb 5W B22 ', 995, 'LED BULB', 95.0, 'Mr. Wanzu Sam', 'China', 'Pixel', '2022-05-31'),
(12, 'LE09B2 ', 'Pixel LED Bulb 9W B22 ', 150, 'LED BULB', 150.0, 'Raj Sankor', 'Bangladesh', 'Flame', '2022-06-06'),
(13, 'LE09E2 ', 'Pixel LED Bulb 9W E27 ', 1500, 'LED BULB', 150.0, 'Mr. Wanzu Sam', 'China', 'Pixel', '2022-05-30'),
(14, 'LE12B2 ', 'Pixel LED Bulb 12W B22 ', 502, 'LED BULB', 175.0, 'Mr. Wanzu Sam', 'China', 'Pixel', '2022-06-04'),
(15, 'LE15B2 ', 'Pixel LED Bulb 15W B22 ', 567, 'LED BULB', 200.0, 'Mr. Wanzu Sam', 'China', 'Pixel', '2022-05-30'),
(16, 'LE15E2 ', 'Pixel LED Bulb 15W E27 ', 700, 'LED BULB', 200.0, 'Mr. Wanzu Sam', 'China', 'Pixel', '2022-05-31'),
(17, 'LE18B2 ', 'Pixel LED Bulb 18W B22 ', 829, 'LED BULB', 200.0, 'Mr. Wanzu Sam', 'China', 'Pixel', '2022-05-31'),
(18, 'LE09B1 ', 'Pixel LED Bulb 9W B22 ', 433, 'LED BULB', 130.0, 'Mr. Wanzu Sam', 'China', 'Pixel', '2022-05-30'),
(19, 'LE12B1', 'Pixel LED Bulb 12W B22', 446, 'LED BULB', 150.0, 'Mr. Wanzu Sam', 'China', 'Pixel', '2022-05-30'),
(20, 'LE15B1', 'Pixel LED Bulb 15W B22', 345, 'LED BULB', 165.0, 'Mr. Wanzu Sam', 'China', 'Pixel', '2022-06-07'),
(21, 'LE15E1', 'Pixel LED Bulb 15W E27', 484, 'LED BULB', 165.0, 'Mr. Wanzu Sam', 'China', 'Pixel', '2022-05-31'),
(22, 'LE30E1', 'Pixel LED Bulb 30W E27', 529, 'LED BULB', 450.0, 'Mr. Wanzu Sam', 'China', 'Pixel', '2022-05-31'),
(23, 'GP0TVS', 'Pixel Premium Perk Tv Socket', 494, 'Holder', 195.0, 'Mr. Wanzu Sam', 'China', 'Pixel', '2022-06-01'),
(31, 'LE15B1', 'Pixel LED Bulb 15W B22', 345, 'LED BULB', 165.0, 'Mr. Wanzu Sam', 'China', 'Pixel', '2022-06-07');

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

CREATE TABLE `bill` (
  `invoice_no` int(4) NOT NULL,
  `customer_name` varchar(30) NOT NULL,
  `mobile` varchar(15) NOT NULL,
  `total_ammount` int(8) NOT NULL,
  `discount` int(3) NOT NULL,
  `net_amount` int(8) NOT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`invoice_no`, `customer_name`, `mobile`, `total_ammount`, `discount`, `net_amount`, `date`) VALUES
(22, 'Tuhin', '01828311225', 1000, 6, 940, '2022-06-01'),
(40, 'Tuhin', '01828311225', 250000, 5, 237500, '2022-06-01'),
(100, 'Tuhin', '01828311225', 20460, 9, 18619, '2022-06-01'),
(345, 'Mizan', '12345678901', 4075, 10, 3668, '2022-06-04'),
(345, 'Mizan', '12345678901', 9140, 10, 3668, '2022-06-04');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` int(5) NOT NULL,
  `cust_code` varchar(30) NOT NULL,
  `name` varchar(50) NOT NULL,
  `designation` varchar(30) NOT NULL,
  `shop_name` varchar(50) NOT NULL,
  `mobile` varchar(15) NOT NULL,
  `address` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `cust_code`, `name`, `designation`, `shop_name`, `mobile`, `address`) VALUES
(2, 'hello', 'All Mamun', 'Dealer', 'All Mamun Electric', '01781234567', 'Sundarban, Khulna'),
(5, '123', 'Mihir', 'Dealer', 'International shop', '56537568', 'Raipur');

-- --------------------------------------------------------

--
-- Table structure for table `deposit`
--

CREATE TABLE `deposit` (
  `id` int(3) NOT NULL,
  `custommer_name` varchar(50) NOT NULL,
  `due` int(8) NOT NULL,
  `deposit` int(8) NOT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `deposit`
--

INSERT INTO `deposit` (`id`, `custommer_name`, `due`, `deposit`, `date`) VALUES
(2, 'Mizan', 2000060, 100000, '2022-05-28'),
(1, 'Tuhin', 1354915, 50000, '2022-05-28'),
(1, 'Tuhin', 1354915, 10000, '2022-05-28'),
(1, 'Tuhin', 1354915, 3400, '2022-05-28'),
(2, 'Mizan', 2000060, 2300, '2022-05-31'),
(2, 'Mizan', 2000060, 10000, '2022-06-22'),
(1, 'Tuhin', 3402790, 4000, '2022-06-01');

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `inv_id` int(5) NOT NULL,
  `product_id1` varchar(50) NOT NULL,
  `product_name1` varchar(100) NOT NULL,
  `stock1` int(5) NOT NULL,
  `quentity` int(5) NOT NULL,
  `price1` double(10,1) NOT NULL,
  `total_price` double(10,1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `invoice`
--

INSERT INTO `invoice` (`inv_id`, `product_id1`, `product_name1`, `stock1`, `quentity`, `price1`, `total_price`) VALUES
(123, 'LEDB15', 'LED BULB 5W', 495, 5, 110.0, 550.0),
(1, 'CML20', 'Core i3 Laptop', 144, 56, 400000.0, 22400000.0),
(1, 'lmp32', 'Table Lamp', 496, 4, 340.0, 1360.0),
(12, 'lmp32', 'Table Lamp', 433, 67, 340.0, 22780.0);

-- --------------------------------------------------------

--
-- Table structure for table `login_details`
--

CREATE TABLE `login_details` (
  `id` int(3) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mobile` varchar(15) NOT NULL,
  `role` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `login_details`
--

INSERT INTO `login_details` (`id`, `user_name`, `email`, `mobile`, `role`, `password`) VALUES
(1, 'azhar', 'azhar@gmail.com', '1312432090', 'Admin', '2345'),
(2, 'nesar', 'nesar@gmail.com', '12345678901', 'Admin', '12345'),
(4, 'tareq', 'tareq@gmail.com', '12345678901', 'admin', '12345'),
(6, 'Akram', 'Akram@gmail.com', '12345678908', 'Stock Keeper', '54321');

-- --------------------------------------------------------

--
-- Table structure for table `new_invoice`
--

CREATE TABLE `new_invoice` (
  `invoice_id` int(5) NOT NULL,
  `products_id` varchar(30) NOT NULL,
  `products_name` varchar(150) NOT NULL,
  `quantity` double(4,1) NOT NULL,
  `total_price` double(5,1) NOT NULL,
  `client_name` varchar(50) NOT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `new_invoice`
--

INSERT INTO `new_invoice` (`invoice_id`, `products_id`, `products_name`, `quantity`, `total_price`, `client_name`, `date`) VALUES
(100, 'CML20', 'Core i3 Laptop', 56.0, 56.0, 'Mizan', '2022-05-27'),
(100, 'lmp32', 'Table Lamp', 4.0, 4.0, 'Mizan', '2022-05-27'),
(101, 'CML20', 'Core i3 Laptop', 56.0, 56.0, 'Tuhin', '2022-05-27'),
(101, 'CML20', 'Core i3 Laptop', 56.0, 56.0, 'Tuhin', '2022-05-27'),
(12, 'LEDB15', 'LED BULB 5W', 5.0, 5.0, 'Tuhin', '2022-05-27'),
(12, 'lmp32', 'Table Lamp', 67.0, 67.0, 'Tuhin', '2022-05-27'),
(12, 'lmp32', 'Table Lamp', 67.0, 67.0, 'Tuhin', '2022-05-27'),
(12, 'lmp32', 'Table Lamp', 67.0, 67.0, 'Tuhin', '2022-05-27'),
(12, 'lmp32', 'Table Lamp', 67.0, 67.0, 'Tuhin', '2022-05-27'),
(123, 'lmp32', 'Table Lamp', 34.0, 34.0, 'Tuhin', '2022-05-28'),
(13, 'CML20', 'Core i3 Laptop', 4.0, 4.0, 'Tuhin', '2022-05-28'),
(13, 'FAN56IN', 'Pixel Ceiling Fan 56', 4.0, 4.0, 'Tuhin', '2022-05-28'),
(13, 'lmp32', 'Table Lamp', 3.0, 3.0, 'Tuhin', '2022-05-28'),
(14, 'FAN56IN', 'Pixel Ceiling Fan 56', 5.0, 5.0, 'Tuhin', '2022-05-28'),
(15, 'FAN56IN', 'Pixel Ceiling Fan 56', 12.0, 9999.9, 'Tuhin', '2022-05-21'),
(12, 'FAN56IN', 'Pixel Ceiling Fan 56', 2.0, 5200.0, 'Tuhin', '2022-05-21'),
(12, 'FAN56IN', 'Pixel Ceiling Fan 56', 12.0, 9999.9, 'Tuhin', '2022-05-21'),
(3, 'FAN56IN', 'Pixel Ceiling Fan 56', 5.0, 9999.9, 'Tuhin', '2022-05-21'),
(3, 'FAN56IN', 'Pixel Ceiling Fan 56', 5.0, 9999.9, 'Tuhin', '2022-05-21'),
(3, 'lmp32', 'Table Lamp', 5.0, 1700.0, 'Tuhin', '2022-05-21'),
(3, 'CML20', 'Core i3 Laptop', 3.0, 9999.9, 'Tuhin', '2022-05-21'),
(5, 'lmp32', 'Table Lamp', 6.0, 2040.0, 'Tuhin', '2022-05-28'),
(5, 'FAN56IN', 'Pixel Ceiling Fan 56', 5.0, 9999.9, 'Tuhin', '2022-05-28'),
(5, 'lmp32', 'Table Lamp', 5.0, 1700.0, 'Tuhin', '2022-05-28'),
(24, 'FAN56IN', 'Pixel Ceiling Fan 56', 3.0, 7800.0, 'Tuhin', '2022-05-28'),
(24, 'lmp32', 'Table Lamp', 56.0, 9999.9, 'Tuhin', '2022-05-28'),
(6, 'FAN56IN', 'Pixel Ceiling Fan 56', 6.0, 9999.9, 'Tuhin', '2022-05-28'),
(12, 'CML20', 'Core i3 Laptop', 5.0, 9999.9, 'Mizan', '2022-05-28'),
(34, 'CML20', 'Core i3 Laptop', 5.0, 9999.9, 'Tuhin', '2022-05-28'),
(34, 'lmp32', 'Table Lamp', 56.0, 9999.9, 'Tuhin', '2022-05-28'),
(123, 'FAN56IN', 'Pixel Ceiling Fan 56', 5.0, 9999.9, 'Tuhin', '2022-05-30'),
(124, 'LE15B1', 'Pixel LED Bulb 15W B22', 45.0, 7425.0, 'Tuhin', '2022-05-31'),
(124, 'LE09B1 ', 'Pixel LED Bulb 9W B22 ', 57.0, 7410.0, 'Tuhin', '2022-05-31'),
(234, 'LE18B2 ', 'Pixel LED Bulb 18W B22 ', 56.0, 9999.9, '', '2022-05-31'),
(234, 'LE12B1', 'Pixel LED Bulb 12W B22', 67.0, 9999.9, '', '2022-05-31'),
(23, 'LE05B2 ', 'Pixel LED Bulb 5W B22 ', 5.0, 475.0, '', '2022-06-01'),
(23, 'LE12B1', 'Pixel LED Bulb 12W B22', 56.0, 8400.0, '', '2022-06-01'),
(23, 'LE30E1', 'Pixel LED Bulb 30W E27', 56.0, 9999.9, '', '2022-06-01'),
(22, 'LE18B2 ', 'Pixel LED Bulb 18W B22 ', 5.0, 1000.0, 'Tuhin', '2022-06-01'),
(455, 'LE12B1', 'Pixel LED Bulb 12W B22 ', 56.0, 8400.0, 'Mizan', '2022-06-01'),
(455, 'LE15B1', 'Pixel LED Bulb 15W B22', 6.0, 990.0, 'Mizan', '2022-06-01'),
(455, 'LE30E1', 'Pixel LED Bulb 30W E27', 5.0, 2250.0, 'Mizan', '2022-06-01'),
(40, 'ONEPLS8T', 'One Plus 8T', 5.0, 9999.9, 'Tuhin', '2022-06-01'),
(100, 'lmp32', 'Table Lamp', 60.0, 9999.9, 'Tuhin', '2022-06-01'),
(1000, 'LE12B1', 'Pixel LED Bulb 12W B22', 45.0, 6750.0, 'Tuhin', '2022-06-04'),
(1000, 'GP0TVS', 'Pixel Premium Perk Tv Socket', 6.0, 1170.0, 'Tuhin', '2022-06-04'),
(345, 'LE18B2 ', 'Pixel LED Bulb 18W B22 ', 5.0, 1000.0, 'Mizan', '2022-06-04'),
(345, 'LE15E1', 'Pixel LED Bulb 15W E27', 5.0, 825.0, 'Mizan', '2022-06-04'),
(345, 'LE30E1', 'Pixel LED Bulb 30W E27', 5.0, 2250.0, 'Mizan', '2022-06-04'),
(345, 'LE18B2 ', 'Pixel LED Bulb 18W B22 ', 5.0, 1000.0, 'Mizan', '2022-06-04'),
(345, 'LE15E1', 'Pixel LED Bulb 15W E27', 5.0, 825.0, 'Mizan', '2022-06-04'),
(345, 'LE30E1', 'Pixel LED Bulb 30W E27', 5.0, 2250.0, 'Mizan', '2022-06-04'),
(345, 'LE15E1', 'Pixel LED Bulb 15W E27', 6.0, 990.0, 'Mizan', '2022-06-04'),
(100, 'lmp32', 'Table Lamp', 6.0, 2040.0, 'Tuhin', '2022-06-05'),
(120, 'LE09B1 ', 'Pixel LED Bulb 9W B22 ', 5.0, 650.0, 'Mizan', '2022-06-05'),
(123, 'FAN56IN', 'Pixel Ceiling Fan 56', 3.0, 7800.0, '', '2022-06-06'),
(123, 'LE09B1 ', 'Pixel LED Bulb 9W B22 ', 5.0, 650.0, '', '2022-06-07'),
(123, 'LE15B1', 'Pixel LED Bulb 15W B22', 5.0, 825.0, '', '2022-06-07');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `add_brand`
--
ALTER TABLE `add_brand`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `add_category`
--
ALTER TABLE `add_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `add_seller`
--
ALTER TABLE `add_seller`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `add_stock`
--
ALTER TABLE `add_stock`
  ADD PRIMARY KEY (`SL`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `login_details`
--
ALTER TABLE `login_details`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `add_brand`
--
ALTER TABLE `add_brand`
  MODIFY `Id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `add_category`
--
ALTER TABLE `add_category`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `add_seller`
--
ALTER TABLE `add_seller`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `add_stock`
--
ALTER TABLE `add_stock`
  MODIFY `SL` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `login_details`
--
ALTER TABLE `login_details`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
