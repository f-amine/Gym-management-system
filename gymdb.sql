-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 01, 2023 at 04:43 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gymdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `employeeId` int(11) NOT NULL,
  `username` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phoneNumber` varchar(255) NOT NULL,
  `address` text NOT NULL,
  `role` varchar(255) NOT NULL,
  `paymentInformation` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`employeeId`, `username`, `password`, `firstName`, `lastName`, `email`, `phoneNumber`, `address`, `role`, `paymentInformation`) VALUES
(1, 'a', '0cc175b9c0f1b6a831c399e269772661', 'Amine', 'g', 'aminefrira@gmail.com', '0697992092', 'Iziki 1', 'trainor', 'RIB : 100052121515 BANK: CIH'),
(3, 'trainor', '114f21df2ce1ede108dc5d2226edd56c', 'a', 'a', 'a', 'a', 'aa', 'trainor', 'a'),
(4, 'trainor', '114f21df2ce1ede108dc5d2226edd56c', 'amine', 'frira', 'aminefriri', 'frifnna', 'fnirnmfr', 'trainor', 'fnrifnr'),
(6, 'w', 'w', 'w', 'ww', 'w', 'w', 'w', 'trainor', 'w'),
(50, 'receptionist', '0a9b3767c8b9b69cea129110e8daeda2', 'f', 'a', 'a', 'a', 'a', 'receptionist', 'a');

-- --------------------------------------------------------

--
-- Table structure for table `equipment`
--

CREATE TABLE `equipment` (
  `equipmentId` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `pricePerUnit` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `equipment`
--

INSERT INTO `equipment` (`equipmentId`, `name`, `quantity`, `pricePerUnit`) VALUES
(1, 'a', 10, '20.60'),
(25, 'boba', 5, '10.00');

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `memberId` int(11) NOT NULL,
  `membershiptype` varchar(255) DEFAULT NULL,
  `program` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phoneNumber` varchar(255) NOT NULL,
  `address` text NOT NULL,
  `paymentInformation` text DEFAULT NULL,
  `emergencyContactInfo` text DEFAULT NULL,
  `membershipExpiration` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `member`
--

INSERT INTO `member` (`memberId`, `membershiptype`, `program`, `firstname`, `lastname`, `email`, `phoneNumber`, `address`, `paymentInformation`, `emergencyContactInfo`, `membershipExpiration`) VALUES
(1, '1', 'test1', 'q', 'q', 'test', 'test', 'test', 'test', 'test', '2022-12-29'),
(2, '2', 'q', 'q', 'q', 'q', 'q', 'q', NULL, NULL, '2023-12-20'),
(3, '3', 'q', 'q', 'q', 'q', 'q', 'q', NULL, NULL, '2022-11-27'),
(4, '2', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', '2022-09-02'),
(5, '1', '11', '1', 'test', '1', '11', '1', ' 11', '1', '2023-01-26');

-- --------------------------------------------------------

--
-- Table structure for table `membershipoffer`
--

CREATE TABLE `membershipoffer` (
  `membershipOfferId` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `duration` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `membershipoffer`
--

INSERT INTO `membershipoffer` (`membershipOfferId`, `name`, `duration`, `price`) VALUES
(1, 'test1', 12, '100.30'),
(2, 'q', 10, '10.30'),
(3, 'hh', 1, '500.30');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `paymentId` int(11) NOT NULL,
  `member` int(11) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `date` date NOT NULL,
  `paymentMethod` varchar(255) NOT NULL,
  `paymentStatus` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `program`
--

CREATE TABLE `program` (
  `programId` int(11) NOT NULL,
  `programName` varchar(255) NOT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `program`
--

INSERT INTO `program` (`programId`, `programName`, `description`) VALUES
(1, 'TEST', 'fr'),
(7, 'a', 'a'),
(8, 'test1', 'tesssst'),
(11, 'ag', 'hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`employeeId`);

--
-- Indexes for table `equipment`
--
ALTER TABLE `equipment`
  ADD PRIMARY KEY (`equipmentId`);

--
-- Indexes for table `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`memberId`);

--
-- Indexes for table `membershipoffer`
--
ALTER TABLE `membershipoffer`
  ADD PRIMARY KEY (`membershipOfferId`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`paymentId`);

--
-- Indexes for table `program`
--
ALTER TABLE `program`
  ADD PRIMARY KEY (`programId`),
  ADD UNIQUE KEY `programName` (`programName`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `employeeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `equipment`
--
ALTER TABLE `equipment`
  MODIFY `equipmentId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `member`
--
ALTER TABLE `member`
  MODIFY `memberId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `membershipoffer`
--
ALTER TABLE `membershipoffer`
  MODIFY `membershipOfferId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `paymentId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `program`
--
ALTER TABLE `program`
  MODIFY `programId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
