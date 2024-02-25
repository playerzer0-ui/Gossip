-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 25, 2024 at 04:17 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 7.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gossiptest`
--
CREATE DATABASE IF NOT EXISTS `gossiptest` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gossiptest`;

-- --------------------------------------------------------

--
-- Table structure for table `blockedusers`
--

CREATE TABLE IF NOT EXISTS `blockedusers` (
  `userId` int(11) NOT NULL COMMENT 'the user that did the blocking',
  `blockedId` int(11) NOT NULL COMMENT 'the user that was blocked',
  KEY `userId` (`userId`),
  KEY `blockedId` (`blockedId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `blockedusers`
--

INSERT INTO `blockedusers` (`userId`, `blockedId`) VALUES
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
  PRIMARY KEY (`inboxId`),
  KEY `adminId` (`adminId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `inbox`
--

INSERT INTO `inbox` (`inboxId`, `inboxType`, `adminId`, `groupName`, `groupProfilePicture`) VALUES
(1, 1, NULL, '', NULL),
(2, 2, 1, 'Football chat', NULL);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `inboxparticipants`
--

INSERT INTO `inboxparticipants` (`userId`, `inboxId`, `deletedState`, `unseenMessages`, `isOpen`, `timeSent`) VALUES
(1, 1, 0, 0, 0, '2023-02-08 14:35:41.000000'),
(2, 1, 0, 0, 0, '2022-02-05 14:36:09.000000'),
(1, 2, 0, 0, 0, '2022-02-08 14:36:17.000000'),
(2, 2, 0, 0, 0, '2024-02-16 08:29:59.000000'),
(3, 2, 0, 0, 0, '2024-02-08 08:30:03.000000');

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
  PRIMARY KEY (`messageId`),
  KEY `senderId` (`senderId`),
  KEY `inboxId` (`inboxId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`messageId`, `inboxId`, `senderId`, `message`, `messageType`, `timeSent`, `deletedState`, `messageKey`) VALUES
(1, 1, 1, 'hello', 1, '2024-01-31 21:57:14', 0, 128),
(2, 1, 2, 'hi', 1, '2024-01-31 21:58:28', 0, 128),
(3, 2, 2, 'hello', 1, '2024-01-31 22:12:59', 0, 128),
(4, 2, 3, 'how is everyone doing', 1, '2024-01-31 22:14:00', 0, 128),
(5, 2, 1, 'we are fine', 1, '2024-01-31 22:14:32', 0, 128);

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  PRIMARY KEY (`userId`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userId`, `email`, `userName`, `profilePicture`, `password`, `dateOfBirth`, `userType`, `suspended`, `bio`, `online`) VALUES
(1, 'joe@gmail.com', 'joseph', 'default.png', '$2a$10$rJf3amWgGq0g5AQ90XCPq.1oASojmit/aOI/W7H9hlOvuEnq7TPqa', '2000-08-02', 1, 0, '', 0),
(2, 'paul@gmail.com', 'paul', 'default.png', '$2a$10$z/vsk2OsTLeL2opWutyIxehqEHnoUb7qXaV2kI8PmkR4Q2AthyZJu', '2001-07-02', 1, 0, '', 0),
(3, 'jacob@gmail.com', 'jacob', 'default.png', '$2a$10$AGBePXjpT0OYza399OtQ/e6hor3KKv7qzdsekRPUKJKiVTnFwmh.6', '2006-09-02', 1, 0, '', 0),
(4, 'kelly@gmail.com', 'kelly', 'default.png', '$2a$10$2wrSV7XQavi8VSplujV7VOIWYUX8xqyrVdZ/dr.67Xb6jQt.Ollhy', '2005-08-02', 1, 0, '', 0),
(5, 'angel@gmail.com', 'angel', 'default.png', '$2a$10$mX.rCUPojcnX/rX3CaXN9.yUaEIC4rO7mtw.R.UQjXdtBlle9ktgK', '1990-08-02', 2, 0, '', 0);

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
