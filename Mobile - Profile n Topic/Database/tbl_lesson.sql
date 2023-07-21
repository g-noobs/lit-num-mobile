-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: Jul 08, 2023 at 12:51 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_tagakaulo`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_lesson`
--

CREATE TABLE `tbl_lesson` (
  `lesson_id` int(11) NOT NULL,
  `lesson_name` varchar(125) NOT NULL,
  `objective_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL,
  `addedby_ID` int(11) NOT NULL,
  `date_added` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_lesson`
--

INSERT INTO `tbl_lesson` (`lesson_id`, `lesson_name`, `objective_id`, `topic_id`, `addedby_ID`, `date_added`) VALUES
(0, 'Numbers', 2, 2, 1, '2023-06-24'),
(0, '', 2, 2, 1, '0000-00-00'),
(1001, 'Vowels', 0, 0, 1, '2023-06-07'),
(1002, 'Consonant', 0, 1, 0, '2023-06-07'),
(4001, 'Family', 0, 0, 1, '2023-07-07'),
(4002, 'Animals', 0, 0, 1, '2023-07-07'),
(3001, 'Hapi Cat', 0, 0, 1, '2023-07-07');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_lesson`
--
ALTER TABLE `tbl_lesson`
  ADD KEY `objective_id` (`objective_id`),
  ADD KEY `topic_id` (`topic_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_lesson`
--
ALTER TABLE `tbl_lesson`
  ADD CONSTRAINT `objective_id` FOREIGN KEY (`objective_id`) REFERENCES `tbl_objective` (`objective_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `topic_id` FOREIGN KEY (`topic_id`) REFERENCES `tbl_topic` (`topic_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
