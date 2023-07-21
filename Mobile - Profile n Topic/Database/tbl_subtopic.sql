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
-- Table structure for table `tbl_subtopic`
--

CREATE TABLE `tbl_subtopic` (
  `subtopic_id` int(11) NOT NULL,
  `subtopic_title` varchar(125) NOT NULL,
  `subtopic_content` varchar(125) NOT NULL,
  `content_imagepath_id` int(11) DEFAULT NULL,
  `audio_id` int(11) DEFAULT NULL,
  `video_id` int(11) DEFAULT NULL,
  `lesson_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_subtopic`
--

INSERT INTO `tbl_subtopic` (`subtopic_id`, `subtopic_title`, `subtopic_content`, `content_imagepath_id`, `audio_id`, `video_id`, `lesson_id`) VALUES
(1, 'A', 'Letter A', 1, 1, 0, 1001),
(2, 'E', 'Letter E', 2, 2, 0, 1001),
(3, 'É', 'Letter É', 3, 3, 0, 1001),
(4, 'I', 'Letter I', 4, 4, 0, 1001),
(5, 'O', 'Letter O', 5, 5, 0, 1001),
(6, 'U', 'Letter U', 6, 6, 0, 1001),
(7, 'B', 'Letter B', 7, 7, 0, 1002),
(8, 'Mother', 'Mother Content', 8, 8, 0, 4001),
(9, 'Frog', 'Frog Content', 9, 9, 0, 4001),
(10, 'Hapi Cat', 'The chants of a hapi cat to deter the bad omen', 10, 0, 10, 3001);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_subtopic`
--
ALTER TABLE `tbl_subtopic`
  ADD PRIMARY KEY (`subtopic_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_subtopic`
--
ALTER TABLE `tbl_subtopic`
  MODIFY `subtopic_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
