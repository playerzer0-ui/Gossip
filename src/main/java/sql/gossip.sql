-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 07, 2024 at 11:18 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gossip`
--
CREATE DATABASE IF NOT EXISTS `gossip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gossip`;

-- --------------------------------------------------------

--
-- Table structure for table `blockedusers`
--

CREATE TABLE IF NOT EXISTS `blockedusers` (
  `userId` int(11) NOT NULL COMMENT 'the user that did the blocking',
  `blockedId` int(11) NOT NULL COMMENT 'the user that was blocked',
  KEY `userId` (`userId`),
  KEY `blockedId` (`blockedId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `blockedusers`
--

INSERT INTO `blockedusers` (`userId`, `blockedId`) VALUES
(4, 1),
(4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `inbox`
--

CREATE TABLE IF NOT EXISTS `inbox` (
  `inboxId` int(11) NOT NULL AUTO_INCREMENT,
  `inboxType` int(11) NOT NULL DEFAULT 1,
  `adminId` int(11) DEFAULT NULL,
  `groupName` varchar(15) DEFAULT NULL,
  `groupProfilePicture` varchar(80) DEFAULT NULL,
  `searchCategory` varchar(1) NOT NULL DEFAULT 'g',
  PRIMARY KEY (`inboxId`),
  KEY `adminId` (`adminId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inbox`
--

INSERT INTO `inbox` (`inboxId`, `inboxType`, `adminId`, `groupName`, `groupProfilePicture`, `searchCategory`) VALUES
(1, 1, NULL, '', NULL, 'g'),
(2, 2, 1, 'Football chat', NULL, 'g'),
(3, 1, NULL, '', NULL, 'g');

-- --------------------------------------------------------

--
-- Table structure for table `inboxparticipants`
--

CREATE TABLE IF NOT EXISTS `inboxparticipants` (
  `userId` int(11) NOT NULL,
  `inboxId` int(11) NOT NULL,
  `deletedState` tinyint(1) NOT NULL DEFAULT 0,
  `unseenMessages` int(3) NOT NULL DEFAULT 0,
  `isOpen` tinyint(1) NOT NULL DEFAULT 0 COMMENT 'this signifies if the inbox is currently open',
  `timeSent` datetime(6) NOT NULL DEFAULT current_timestamp(6),
  KEY `userId` (`userId`),
  KEY `inboxId` (`inboxId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inboxparticipants`
--

INSERT INTO `inboxparticipants` (`userId`, `inboxId`, `deletedState`, `unseenMessages`, `isOpen`, `timeSent`) VALUES
(1, 1, 0, 0, 0, '2024-02-16 11:43:05.519809'),
(2, 1, 0, 0, 1, '2024-02-16 08:31:25.080636'),
(1, 2, 0, 0, 0, '2024-02-16 08:30:38.153926'),
(2, 2, 0, 3, 0, '2024-02-16 08:29:24.000000'),
(3, 2, 0, 3, 0, '2024-02-11 08:29:27.000000'),
(1, 3, 0, 0, 0, '2024-03-07 09:51:44.382465'),
(3, 3, 0, 2, 0, '2024-03-07 09:51:44.384762');

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE IF NOT EXISTS `messages` (
  `messageId` int(11) NOT NULL AUTO_INCREMENT,
  `inboxId` int(11) NOT NULL,
  `senderId` int(11) NOT NULL,
  `message` varchar(535) NOT NULL,
  `messageType` int(11) NOT NULL DEFAULT 1 COMMENT '1 for text,2 for picture,3 for video, 4 for any other file',
  `timeSent` datetime NOT NULL DEFAULT current_timestamp(),
  `deletedState` tinyint(1) NOT NULL DEFAULT 0 COMMENT 'FALSE(0) for not deleted and TRUE for deleted',
  `messageKey` int(3) NOT NULL DEFAULT 128,
  `originalFileName` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`messageId`),
  KEY `senderId` (`senderId`),
  KEY `inboxId` (`inboxId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`messageId`, `inboxId`, `senderId`, `message`, `messageType`, `timeSent`, `deletedState`, `messageKey`, `originalFileName`) VALUES
(1, 1, 1, '4K74EZMs/V3Utrfkwp+KeA==', 1, '2024-01-31 21:57:14', 0, 128, NULL),
(2, 1, 2, 'RJN3ZSrU+U00N7fEJYGXvw==', 1, '2024-01-31 21:58:28', 0, 128, NULL),
(3, 2, 2, '4K74EZMs/V3Utrfkwp+KeA==', 1, '2024-01-31 22:12:59', 0, 128, NULL),
(4, 2, 3, '+RbaEUk/+QFTdCUjcDCBkCDdUrD05oHod8n8Jdt8NT4=', 1, '2024-01-31 22:14:00', 0, 128, NULL),
(5, 2, 1, 'zh9DG5mhldvl9Lr0PXpvqQ==', 1, '2024-01-31 22:14:32', 0, 128, NULL),
(6, 1, 1, 'P/hBC52RONpsOBoWkfRMwA==', 1, '2024-02-16 08:31:17', 0, 128, NULL),
(7, 1, 1, 'Rkl4zoxOtqinD8jhcU08cQ==', 1, '2024-02-16 08:31:25', 0, 128, NULL),
(8, 1, 1, '7NBf3INMrJK9hvkHEDL2toGnt7wM9p+sguuPpDk/FTnRruBSKcmNqeWiEHWLycP2', 2, '2024-02-25 14:29:05', 0, 128, NULL),
(9, 1, 1, 'dR4pq0cTTN8TzyeKsic02ghMNCKPgNeoJxRpZ/IUfJ+jPeuJOMWHSzTPRfqZWDTI', 3, '2024-02-25 14:29:37', 0, 128, NULL),
(10, 1, 1, 'AZ2ml8vXlWvsOdLfcuzsig==', 1, '2024-02-25 14:40:09', 0, 192, NULL),
(11, 2, 1, 'r+nUKEMUsXj/yvjAwPjrLMx/oNNZqi3mfAERFNtf85h/wwMkuVSectG+D6SuTU3G', 2, '2024-02-25 15:12:37', 0, 256, NULL),
(12, 1, 1, 'KYuumKT0NqyQR7pCFWFkitj2Nvy01tQj06IXClQck+vx5As4For8EOPmMNTut4m0', 2, '2024-02-27 22:05:07', 0, 256, NULL),
(13, 1, 1, '9vDozu0tJhYc59Ja3LoY1xz9iOS1G1LyhroSKMyq9rx55cv/kzVXV5KLjQOrYYfx', 2, '2024-02-29 10:54:51', 0, 128, 'VZa+rCiAngSliVTFbqJqow=='),
(14, 1, 1, 'GhYwqiyqwiI7rJll9zWf7dbYH4bF6AjmWm7ARsiCmnezZffgc+l9dptqabl8Ai0A', 4, '2024-02-29 10:56:45', 0, 192, '04XOExsLDvVTxPK9PXzXf2Iz+QosA8CtcruyJkWhykg='),
(15, 1, 1, 'LAKVHy4IUONePuQcLPE/LaGcO2aZWtDW12yiUbX51BSGEajUa4OrsRvJuCWJcBIk', 4, '2024-02-29 10:57:52', 0, 256, '8n3gyCAPeMBIpMrlorUE7Y2nMQvib5JUcrZ5cDNwhVY='),
(16, 3, 1, 'Wm/ccVH5Omezi6GXe0cDOg==', 1, '2024-03-07 09:51:44', 0, 256, NULL),
(17, 3, 1, 'VHQL7uDAKC5FY7Qp1CpoHQ==', 1, '2024-03-07 09:52:33', 0, 128, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

CREATE TABLE IF NOT EXISTS `reports` (
  `reportId` int(11) NOT NULL AUTO_INCREMENT,
  `reporterId` int(11) NOT NULL,
  `userReportedId` int(11) NOT NULL,
  `reportReason` varchar(80) NOT NULL,
  `reportDate` datetime NOT NULL,
  `reportStatus` int(11) NOT NULL DEFAULT 1 COMMENT '1 for unsolved(unseen) 2 for solved, 3 for ignored,4 for inreview',
  PRIMARY KEY (`reportId`),
  KEY `reporterId` (`reporterId`),
  KEY `userReportedId` (`userReportedId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reports`
--

INSERT INTO `reports` (`reportId`, `reporterId`, `userReportedId`, `reportReason`, `reportDate`, `reportStatus`) VALUES
(1, 4, 1, 'he keeps on spamming me ', '2024-02-02 00:00:00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `stories`
--

CREATE TABLE IF NOT EXISTS `stories` (
  `storyId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `story` varchar(255) NOT NULL,
  `storyType` int(1) NOT NULL COMMENT '1 for picture, 2 for video',
  `dateTime` datetime NOT NULL DEFAULT current_timestamp(),
  `storyDescription` varchar(80) NOT NULL,
  PRIMARY KEY (`storyId`),
  KEY `userId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stories`
--

INSERT INTO `stories` (`storyId`, `userId`, `story`, `storyType`, `dateTime`, `storyDescription`) VALUES
(1, 2, 'story.png', 1, '2024-02-02 00:00:00', 'Good morining viewers');

-- --------------------------------------------------------

--
-- Table structure for table `storyviewers`
--

CREATE TABLE IF NOT EXISTS `storyviewers` (
  `storyId` int(11) NOT NULL,
  `viewerId` int(11) NOT NULL,
  `viewTime` datetime NOT NULL,
  KEY `storyId` (`storyId`),
  KEY `viewerId` (`viewerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `storyviewers`
--

INSERT INTO `storyviewers` (`storyId`, `viewerId`, `viewTime`) VALUES
(1, 1, '2024-02-02 08:30:00'),
(1, 3, '2024-02-02 10:30:00');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `userName` varchar(24) NOT NULL,
  `profilePicture` varchar(80) NOT NULL DEFAULT 'default.png',
  `password` varchar(80) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `userType` int(1) NOT NULL,
  `suspended` tinyint(1) NOT NULL DEFAULT 0,
  `bio` varchar(25) DEFAULT NULL,
  `online` tinyint(1) NOT NULL DEFAULT 0,
  `searchCategory` varchar(1) NOT NULL DEFAULT 'u',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userId`, `email`, `userName`, `profilePicture`, `password`, `dateOfBirth`, `userType`, `suspended`, `bio`, `online`, `searchCategory`) VALUES
(1, 'joe@gmail.com', 'joseph', '2024-02-25T151303.8813581001.jpg', '$2a$10$XGZhbTv7NCHf6AG/AF0k9uRht5vVWckZnM3hsYUxuc1y/3sVoZupa', '2000-08-02', 1, 0, '', 0, 'u'),
(2, 'paul@gmail.com', 'paul', 'default.png', '$2a$10$z/vsk2OsTLeL2opWutyIxehqEHnoUb7qXaV2kI8PmkR4Q2AthyZJu', '2001-07-02', 1, 0, '', 0, 'u'),
(3, 'jacob@gmail.com', 'jacob', 'default.png', '$2a$10$AGBePXjpT0OYza399OtQ/e6hor3KKv7qzdsekRPUKJKiVTnFwmh.6', '2006-09-02', 1, 0, '', 0, 'u'),
(4, 'kelly@gmail.com', 'kelly', 'default.png', '$2a$10$2wrSV7XQavi8VSplujV7VOIWYUX8xqyrVdZ/dr.67Xb6jQt.Ollhy', '2005-08-02', 1, 0, '', 0, 'u'),
(5, 'angel@gmail.com', 'angel', 'default.png', '$2a$10$mX.rCUPojcnX/rX3CaXN9.yUaEIC4rO7mtw.R.UQjXdtBlle9ktgK', '1990-08-02', 2, 0, '', 0, 'u');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `blockedusers`
--
ALTER TABLE `blockedusers`
  ADD CONSTRAINT `blockedusers_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`),
  ADD CONSTRAINT `blockedusers_ibfk_2` FOREIGN KEY (`blockedId`) REFERENCES `users` (`userId`);

--
-- Constraints for table `inbox`
--
ALTER TABLE `inbox`
  ADD CONSTRAINT `inbox_ibfk_1` FOREIGN KEY (`adminId`) REFERENCES `users` (`userId`);

--
-- Constraints for table `inboxparticipants`
--
ALTER TABLE `inboxparticipants`
  ADD CONSTRAINT `inboxparticipants_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`),
  ADD CONSTRAINT `inboxparticipants_ibfk_2` FOREIGN KEY (`inboxId`) REFERENCES `inbox` (`inboxId`);

--
-- Constraints for table `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`senderId`) REFERENCES `users` (`userId`),
  ADD CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`inboxId`) REFERENCES `inbox` (`inboxId`);

--
-- Constraints for table `reports`
--
ALTER TABLE `reports`
  ADD CONSTRAINT `reports_ibfk_1` FOREIGN KEY (`reporterId`) REFERENCES `users` (`userId`),
  ADD CONSTRAINT `reports_ibfk_2` FOREIGN KEY (`userReportedId`) REFERENCES `users` (`userId`);

--
-- Constraints for table `stories`
--
ALTER TABLE `stories`
  ADD CONSTRAINT `stories_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`);

--
-- Constraints for table `storyviewers`
--
ALTER TABLE `storyviewers`
  ADD CONSTRAINT `storyviewers_ibfk_1` FOREIGN KEY (`storyId`) REFERENCES `stories` (`storyId`),
  ADD CONSTRAINT `storyviewers_ibfk_2` FOREIGN KEY (`viewerId`) REFERENCES `users` (`userId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
