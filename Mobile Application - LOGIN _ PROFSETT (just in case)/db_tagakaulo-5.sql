-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 29, 2023 at 06:34 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

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
-- Table structure for table `tbl_addedby`
--

CREATE TABLE `tbl_addedby` (
  `added_byID` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_addedby`
--

INSERT INTO `tbl_addedby` (`added_byID`, `role_id`) VALUES
(2, 1),
(3, 2),
(4, 3),
(5, 4);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_admin_account`
--

CREATE TABLE `tbl_admin_account` (
  `admin_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `gender` enum('MALE','FEMALE','LGBTQIA+','') NOT NULL,
  `email` varchar(50) NOT NULL,
  `birthdate` date NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `status` int(11) NOT NULL,
  `added_byID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `tbl_admin_account`
--

INSERT INTO `tbl_admin_account` (`admin_id`, `name`, `gender`, `email`, `birthdate`, `username`, `password`, `status`, `added_byID`) VALUES
(1, 'Alsent', 'MALE', 'alsentkeith@gmail.com', '2023-06-04', 'pixy', 'password', 1, 2),
(2, 'Mel', 'MALE', 'meljames@gmail.com', '2023-06-16', 'meljames', 'password', 2, 3),
(3, 'Roxanne', 'FEMALE', 'roxanne@gmail.com', '2023-06-12', 'rzhuee', 'password', 4, 4),
(4, 'Jude', '', 'jude@gmail.com', '2023-06-17', 'jude', 'password', 5, 5);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_admin_propic`
--

CREATE TABLE `tbl_admin_propic` (
  `admin_id` int(11) NOT NULL,
  `image_path` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_admin_propic`
--

INSERT INTO `tbl_admin_propic` (`admin_id`, `image_path`) VALUES
(1, 0),
(4, 0),
(2, 0),
(3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_area`
--

CREATE TABLE `tbl_area` (
  `area_id` int(11) NOT NULL,
  `area` varchar(125) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_area`
--

INSERT INTO `tbl_area` (`area_id`, `area`) VALUES
(1, 'area1'),
(2, 'area2'),
(3, 'area3'),
(4, 'area4');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_audio`
--

CREATE TABLE `tbl_audio` (
  `audio_id` int(11) NOT NULL,
  `audio_path` varchar(125) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_audio`
--

INSERT INTO `tbl_audio` (`audio_id`, `audio_path`) VALUES
(1, 'audiopath1'),
(2, 'audiopath2'),
(3, 'audiopath3'),
(4, 'audiopath4');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_badge`
--

CREATE TABLE `tbl_badge` (
  `badge_id` int(11) NOT NULL,
  `badge_name` varchar(125) NOT NULL,
  `badge_path` int(11) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_badge`
--

INSERT INTO `tbl_badge` (`badge_id`, `badge_name`, `badge_path`, `status`) VALUES
(1, 'congratulations!', 1, 1),
(2, 'excellent!', 2, 2),
(3, 'good Job!', 3, 3),
(4, 'Fabulous!', 4, 4);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_category`
--

CREATE TABLE `tbl_category` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(125) NOT NULL,
  `addedby_ID` int(11) NOT NULL,
  `date_added` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_category`
--

INSERT INTO `tbl_category` (`category_id`, `category_name`, `addedby_ID`, `date_added`) VALUES
(1, 'basic', 2, '2023-06-01'),
(2, 'intermediate', 3, '2023-06-05'),
(3, 'advance', 5, '2023-06-04');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_class`
--

CREATE TABLE `tbl_class` (
  `class_id` int(11) NOT NULL,
  `class` varchar(125) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_class`
--

INSERT INTO `tbl_class` (`class_id`, `class`) VALUES
(1, 'class1'),
(2, 'class2'),
(3, 'class3'),
(4, 'class4');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_content_image`
--

CREATE TABLE `tbl_content_image` (
  `content_imagepath_id` int(11) NOT NULL,
  `image_path` varchar(125) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_content_image`
--

INSERT INTO `tbl_content_image` (`content_imagepath_id`, `image_path`) VALUES
(1, 'imagepath1'),
(2, 'imagepath2'),
(3, 'imagepath3'),
(4, 'imagepath4');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_credentials`
--

CREATE TABLE `tbl_credentials` (
  `credentials_id` varchar(30) NOT NULL,
  `uname` varchar(50) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `user_info_id` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_credentials`
--

INSERT INTO `tbl_credentials` (`credentials_id`, `uname`, `pass`, `user_info_id`) VALUES
('CRED100001', 'admin@email.com', '%Dw0+(&EYi', 'USR100001');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_gradebook`
--

CREATE TABLE `tbl_gradebook` (
  `gradebook_id` int(11) NOT NULL,
  `learner_progress_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_learner`
--

CREATE TABLE `tbl_learner` (
  `learner_id` int(11) NOT NULL,
  `name` text NOT NULL,
  `birthdate` date NOT NULL,
  `gender` enum('MALE','FEMALE','LGBTQIA+','') NOT NULL,
  `address` varchar(225) NOT NULL,
  `mobile_number` varchar(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `status` int(11) NOT NULL,
  `added_byID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_learner_progress`
--

CREATE TABLE `tbl_learner_progress` (
  `lsp_id` int(11) NOT NULL,
  `lgp_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_learner_propic`
--

CREATE TABLE `tbl_learner_propic` (
  `learner_id` int(11) NOT NULL,
  `image_path` int(11) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_learner_quiz_progress`
--

CREATE TABLE `tbl_learner_quiz_progress` (
  `lgp_id` int(11) NOT NULL,
  `learner_id` int(11) NOT NULL,
  `date_taken` date NOT NULL,
  `score` varchar(125) NOT NULL,
  `badge_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_learner_quiz_progress`
--

INSERT INTO `tbl_learner_quiz_progress` (`lgp_id`, `learner_id`, `date_taken`, `score`, `badge_id`) VALUES
(1, 1, '0000-00-00', '12', 1),
(2, 1, '2023-06-12', '12', 1),
(3, 3, '2023-06-17', '15', 2),
(4, 4, '2023-06-10', '30', 3),
(5, 2, '2023-06-30', '100', 4);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_learner_story_progress`
--

CREATE TABLE `tbl_learner_story_progress` (
  `lsp_id` int(11) NOT NULL,
  `learner_id` int(11) NOT NULL,
  `story_id` int(11) NOT NULL,
  `date_started` date NOT NULL,
  `date_completed` date NOT NULL,
  `badge_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(0, '', 2, 2, 1, '0000-00-00');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_objective`
--

CREATE TABLE `tbl_objective` (
  `objective_id` int(11) NOT NULL,
  `objective` varchar(225) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_objective`
--

INSERT INTO `tbl_objective` (`objective_id`, `objective`) VALUES
(0, 'Understanding'),
(1, 'Creating'),
(2, 'Analyzing'),
(3, 'Remembering');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_quiz`
--

CREATE TABLE `tbl_quiz` (
  `quiz_id` int(11) NOT NULL,
  `quiz_question` varchar(123) NOT NULL,
  `quiz_selectionA` int(11) NOT NULL,
  `quiz_selectionB` int(11) NOT NULL,
  `quiz_selectionC` int(11) NOT NULL,
  `quiz_selectionD` int(11) NOT NULL,
  `story_id` int(11) NOT NULL,
  `writer_id` int(11) NOT NULL,
  `score` varchar(125) NOT NULL,
  `date_created` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_quiz_image`
--

CREATE TABLE `tbl_quiz_image` (
  `quiz_id` int(11) NOT NULL,
  `image_path` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_role`
--

CREATE TABLE `tbl_role` (
  `role_id` int(11) NOT NULL,
  `role_description` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_status`
--

CREATE TABLE `tbl_status` (
  `status_id` int(11) NOT NULL,
  `status` varchar(125) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_status`
--

INSERT INTO `tbl_status` (`status_id`, `status`) VALUES
(0, 'Inactive'),
(1, 'Active');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_teacher`
--

CREATE TABLE `tbl_teacher` (
  `teacher_id` varchar(30) NOT NULL,
  `teacher_first_name` varchar(30) NOT NULL,
  `teacher_last_name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_teacher_classlearner`
--

CREATE TABLE `tbl_teacher_classlearner` (
  `teacher_id` int(11) NOT NULL,
  `learner_id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `area_id` int(11) NOT NULL,
  `addedby_ID` int(11) NOT NULL,
  `date_created` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_teacher_lesson`
--

CREATE TABLE `tbl_teacher_lesson` (
  `teacher_id` int(11) NOT NULL,
  `lesson_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_topic`
--

CREATE TABLE `tbl_topic` (
  `topic_id` int(11) NOT NULL,
  `topic_title` varchar(125) NOT NULL,
  `topic_content` varchar(125) NOT NULL,
  `content_imagepath_Id` int(11) NOT NULL,
  `audio_id` int(11) NOT NULL,
  `video_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_topic`
--

INSERT INTO `tbl_topic` (`topic_id`, `topic_title`, `topic_content`, `content_imagepath_Id`, `audio_id`, `video_id`) VALUES
(0, 'Reading Title', 'Reading Content', 0, 0, 0),
(1, 'Fluency Title', 'Fluency Content', 1, 1, 1),
(2, 'Grammar Title', 'Grammar Content', 2, 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user_contactaddress`
--

CREATE TABLE `tbl_user_contactaddress` (
  `user_contactaddress_id` int(11) NOT NULL,
  `email` varchar(225) NOT NULL,
  `contactNum` bigint(11) NOT NULL,
  `city` varchar(225) NOT NULL,
  `barangay` varchar(225) NOT NULL,
  `street` varchar(225) NOT NULL,
  `postalcode` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_user_contactaddress`
--

INSERT INTO `tbl_user_contactaddress` (`user_contactaddress_id`, `email`, `contactNum`, `city`, `barangay`, `street`, `postalcode`) VALUES
(1, 'alsentkeith@gmail.com', 9452505116, 'General Santos City', 'Mabuhay', 'Klinan 5', '9500'),
(2, 'Roxanne Uy', 9684568232, 'Polomolok', 'DatuPuti', 'Maabini', '9567'),
(3, 'Jude Roger', 9345725671, 'Marbel', 'Conel', 'Phase 4', '9465'),
(4, 'Mel James', 9354673626, 'Kalibo', 'Calumpang', 'Phase 3', '0934');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user_info`
--

CREATE TABLE `tbl_user_info` (
  `user_info_id` varchar(50) NOT NULL,
  `name` varchar(225) NOT NULL,
  `gender` enum('MALE','FEMALE','LGBTQIA+','') NOT NULL,
  `email` varchar(225) NOT NULL,
  `birthdate` date NOT NULL,
  `user_contactaddress_id` int(11) NOT NULL,
  `added_byID` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  `user_level_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_user_info`
--

INSERT INTO `tbl_user_info` (`user_info_id`, `name`, `gender`, `email`, `birthdate`, `user_contactaddress_id`, `added_byID`, `status_id`, `user_level_id`) VALUES
('USR100001', 'glenAdmin', 'MALE', 'admin@email.com', '2023-06-01', 0, 0, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user_level`
--

CREATE TABLE `tbl_user_level` (
  `user_level_id` int(11) NOT NULL,
  `user_level_description` varchar(225) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_user_level`
--

INSERT INTO `tbl_user_level` (`user_level_id`, `user_level_description`) VALUES
(0, 'Admin'),
(1, 'Teacher'),
(2, 'Writer'),
(3, 'Learner');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_video`
--

CREATE TABLE `tbl_video` (
  `tbl_video_id` int(11) NOT NULL,
  `tbl_video_path` varchar(225) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_video`
--

INSERT INTO `tbl_video` (`tbl_video_id`, `tbl_video_path`) VALUES
(1, 'videopath1'),
(2, 'videopath2'),
(3, 'videopath3'),
(4, 'videopath4');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_writer`
--

CREATE TABLE `tbl_writer` (
  `writer_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `gender` enum('MALE','FEMALE','LGBTQIA+','') NOT NULL,
  `email` varchar(50) NOT NULL,
  `birthdate` date NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `status` int(11) NOT NULL,
  `added_byID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_writer`
--

INSERT INTO `tbl_writer` (`writer_id`, `name`, `gender`, `email`, `birthdate`, `username`, `password`, `status`, `added_byID`) VALUES
(1, 'Vivien', 'FEMALE', 'vivien@gmail.com', '2023-06-05', 'vien', 'password', 1, 2),
(2, 'Romea', 'MALE', 'romeo@gmail.com', '2023-06-24', 'romeo', 'password', 2, 3),
(3, 'Xander', 'MALE', 'xander@gmail.com', '2023-06-22', 'xander', 'password', 3, 4),
(4, 'divine', 'FEMALE', 'divine@gmail.com', '2023-06-26', 'divine', 'password', 4, 5);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_writer_propic`
--

CREATE TABLE `tbl_writer_propic` (
  `writer_id` int(11) NOT NULL,
  `image_path` int(11) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_writer_propic`
--

INSERT INTO `tbl_writer_propic` (`writer_id`, `image_path`, `status`) VALUES
(4, 1, 1),
(2, 2, 2),
(1, 3, 3),
(3, 4, 4);

-- --------------------------------------------------------

--
-- Stand-in structure for view `user_info_view`
-- (See below for the actual view)
--
CREATE TABLE `user_info_view` (
`user_info_id` varchar(50)
,`name` varchar(225)
,`gender` enum('MALE','FEMALE','LGBTQIA+','')
,`email` varchar(225)
,`birthdate` date
,`user_level_description` varchar(225)
,`status` varchar(125)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `user_info_view_admin_active`
-- (See below for the actual view)
--
CREATE TABLE `user_info_view_admin_active` (
`user_info_id` varchar(50)
,`name` varchar(225)
,`gender` enum('MALE','FEMALE','LGBTQIA+','')
,`email` varchar(225)
,`birthdate` date
,`user_level_description` varchar(225)
,`status` varchar(125)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `view_userinfo_creds`
-- (See below for the actual view)
--
CREATE TABLE `view_userinfo_creds` (
`user_info_id` varchar(50)
,`name` varchar(225)
,`gender` enum('MALE','FEMALE','LGBTQIA+','')
,`email` varchar(225)
,`birthdate` date
,`user_contactaddress_id` int(11)
,`added_byID` int(11)
,`status_id` int(11)
,`user_level_id` int(11)
,`credentials_id` varchar(30)
,`uname` varchar(50)
,`pass` varchar(50)
);

-- --------------------------------------------------------

--
-- Structure for view `user_info_view`
--
DROP TABLE IF EXISTS `user_info_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_info_view`  AS SELECT `tbl_user_info`.`user_info_id` AS `user_info_id`, `tbl_user_info`.`name` AS `name`, `tbl_user_info`.`gender` AS `gender`, `tbl_user_info`.`email` AS `email`, `tbl_user_info`.`birthdate` AS `birthdate`, `tbl_user_level`.`user_level_description` AS `user_level_description`, `tbl_status`.`status` AS `status` FROM ((`tbl_user_info` join `tbl_status` on(`tbl_user_info`.`status_id` = `tbl_status`.`status_id`)) join `tbl_user_level` on(`tbl_user_info`.`user_level_id` = `tbl_user_level`.`user_level_id`)) ORDER BY `tbl_user_info`.`status_id` DESC ;

-- --------------------------------------------------------

--
-- Structure for view `user_info_view_admin_active`
--
DROP TABLE IF EXISTS `user_info_view_admin_active`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_info_view_admin_active`  AS SELECT `tbl_user_info`.`user_info_id` AS `user_info_id`, `tbl_user_info`.`name` AS `name`, `tbl_user_info`.`gender` AS `gender`, `tbl_user_info`.`email` AS `email`, `tbl_user_info`.`birthdate` AS `birthdate`, `tbl_user_level`.`user_level_description` AS `user_level_description`, `tbl_status`.`status` AS `status` FROM ((`tbl_user_info` join `tbl_status` on(`tbl_user_info`.`status_id` = `tbl_status`.`status_id`)) join `tbl_user_level` on(`tbl_user_info`.`user_level_id` = `tbl_user_level`.`user_level_id`)) WHERE `tbl_user_info`.`status_id` = 1 AND `tbl_user_info`.`user_level_id` = 0 ;

-- --------------------------------------------------------

--
-- Structure for view `view_userinfo_creds`
--
DROP TABLE IF EXISTS `view_userinfo_creds`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_userinfo_creds`  AS SELECT `tbl_user_info`.`user_info_id` AS `user_info_id`, `tbl_user_info`.`name` AS `name`, `tbl_user_info`.`gender` AS `gender`, `tbl_user_info`.`email` AS `email`, `tbl_user_info`.`birthdate` AS `birthdate`, `tbl_user_info`.`user_contactaddress_id` AS `user_contactaddress_id`, `tbl_user_info`.`added_byID` AS `added_byID`, `tbl_user_info`.`status_id` AS `status_id`, `tbl_user_info`.`user_level_id` AS `user_level_id`, `tbl_credentials`.`credentials_id` AS `credentials_id`, `tbl_credentials`.`uname` AS `uname`, `tbl_credentials`.`pass` AS `pass` FROM (`tbl_user_info` join `tbl_credentials` on(`tbl_user_info`.`user_info_id` = `tbl_credentials`.`user_info_id`)) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_addedby`
--
ALTER TABLE `tbl_addedby`
  ADD PRIMARY KEY (`added_byID`);

--
-- Indexes for table `tbl_admin_account`
--
ALTER TABLE `tbl_admin_account`
  ADD PRIMARY KEY (`admin_id`),
  ADD KEY `fk_admin_account_addedby` (`added_byID`);

--
-- Indexes for table `tbl_admin_propic`
--
ALTER TABLE `tbl_admin_propic`
  ADD KEY `admin_id` (`admin_id`);

--
-- Indexes for table `tbl_area`
--
ALTER TABLE `tbl_area`
  ADD PRIMARY KEY (`area_id`);

--
-- Indexes for table `tbl_audio`
--
ALTER TABLE `tbl_audio`
  ADD PRIMARY KEY (`audio_id`);

--
-- Indexes for table `tbl_badge`
--
ALTER TABLE `tbl_badge`
  ADD PRIMARY KEY (`badge_id`);

--
-- Indexes for table `tbl_category`
--
ALTER TABLE `tbl_category`
  ADD PRIMARY KEY (`category_id`),
  ADD KEY `addedby_ID` (`addedby_ID`);

--
-- Indexes for table `tbl_class`
--
ALTER TABLE `tbl_class`
  ADD PRIMARY KEY (`class_id`);

--
-- Indexes for table `tbl_content_image`
--
ALTER TABLE `tbl_content_image`
  ADD PRIMARY KEY (`content_imagepath_id`);

--
-- Indexes for table `tbl_credentials`
--
ALTER TABLE `tbl_credentials`
  ADD PRIMARY KEY (`credentials_id`),
  ADD KEY `user_info_id` (`user_info_id`);

--
-- Indexes for table `tbl_gradebook`
--
ALTER TABLE `tbl_gradebook`
  ADD PRIMARY KEY (`gradebook_id`);

--
-- Indexes for table `tbl_learner`
--
ALTER TABLE `tbl_learner`
  ADD PRIMARY KEY (`learner_id`),
  ADD KEY `fk_learner_addedBy` (`added_byID`);

--
-- Indexes for table `tbl_learner_quiz_progress`
--
ALTER TABLE `tbl_learner_quiz_progress`
  ADD PRIMARY KEY (`lgp_id`),
  ADD KEY `fk_lqp_learner` (`learner_id`),
  ADD KEY `fk_lqp_badge` (`badge_id`);

--
-- Indexes for table `tbl_lesson`
--
ALTER TABLE `tbl_lesson`
  ADD KEY `objective_id` (`objective_id`),
  ADD KEY `topic_id` (`topic_id`);

--
-- Indexes for table `tbl_objective`
--
ALTER TABLE `tbl_objective`
  ADD PRIMARY KEY (`objective_id`);

--
-- Indexes for table `tbl_role`
--
ALTER TABLE `tbl_role`
  ADD PRIMARY KEY (`role_id`);

--
-- Indexes for table `tbl_status`
--
ALTER TABLE `tbl_status`
  ADD PRIMARY KEY (`status_id`);

--
-- Indexes for table `tbl_topic`
--
ALTER TABLE `tbl_topic`
  ADD PRIMARY KEY (`topic_id`);

--
-- Indexes for table `tbl_user_contactaddress`
--
ALTER TABLE `tbl_user_contactaddress`
  ADD PRIMARY KEY (`user_contactaddress_id`);

--
-- Indexes for table `tbl_user_info`
--
ALTER TABLE `tbl_user_info`
  ADD PRIMARY KEY (`user_info_id`),
  ADD KEY `status_id` (`status_id`),
  ADD KEY `user_level_id` (`user_level_id`);

--
-- Indexes for table `tbl_user_level`
--
ALTER TABLE `tbl_user_level`
  ADD PRIMARY KEY (`user_level_id`);

--
-- Indexes for table `tbl_video`
--
ALTER TABLE `tbl_video`
  ADD PRIMARY KEY (`tbl_video_id`);

--
-- Indexes for table `tbl_writer`
--
ALTER TABLE `tbl_writer`
  ADD PRIMARY KEY (`writer_id`),
  ADD KEY `fk_writer_addedby` (`added_byID`);

--
-- Indexes for table `tbl_writer_propic`
--
ALTER TABLE `tbl_writer_propic`
  ADD KEY `fk_writerpropic_imagepath` (`image_path`),
  ADD KEY `fk_propic_writerID` (`writer_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_addedby`
--
ALTER TABLE `tbl_addedby`
  MODIFY `added_byID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_admin_account`
--
ALTER TABLE `tbl_admin_account`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_area`
--
ALTER TABLE `tbl_area`
  MODIFY `area_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_audio`
--
ALTER TABLE `tbl_audio`
  MODIFY `audio_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_badge`
--
ALTER TABLE `tbl_badge`
  MODIFY `badge_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_category`
--
ALTER TABLE `tbl_category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tbl_class`
--
ALTER TABLE `tbl_class`
  MODIFY `class_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_content_image`
--
ALTER TABLE `tbl_content_image`
  MODIFY `content_imagepath_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_learner`
--
ALTER TABLE `tbl_learner`
  MODIFY `learner_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_learner_quiz_progress`
--
ALTER TABLE `tbl_learner_quiz_progress`
  MODIFY `lgp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_user_contactaddress`
--
ALTER TABLE `tbl_user_contactaddress`
  MODIFY `user_contactaddress_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_video`
--
ALTER TABLE `tbl_video`
  MODIFY `tbl_video_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_writer`
--
ALTER TABLE `tbl_writer`
  MODIFY `writer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_admin_account`
--
ALTER TABLE `tbl_admin_account`
  ADD CONSTRAINT `fk_admin_account_addedby` FOREIGN KEY (`added_byID`) REFERENCES `tbl_addedby` (`added_byID`) ON DELETE CASCADE;

--
-- Constraints for table `tbl_admin_propic`
--
ALTER TABLE `tbl_admin_propic`
  ADD CONSTRAINT `tbl_admin_propic_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `tbl_admin_account` (`admin_id`) ON UPDATE CASCADE;

--
-- Constraints for table `tbl_category`
--
ALTER TABLE `tbl_category`
  ADD CONSTRAINT `tbl_category_ibfk_1` FOREIGN KEY (`addedby_ID`) REFERENCES `tbl_addedby` (`added_byID`) ON UPDATE CASCADE;

--
-- Constraints for table `tbl_credentials`
--
ALTER TABLE `tbl_credentials`
  ADD CONSTRAINT `user_info_id` FOREIGN KEY (`user_info_id`) REFERENCES `tbl_user_info` (`user_info_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tbl_learner`
--
ALTER TABLE `tbl_learner`
  ADD CONSTRAINT `fk_learner_addedBy` FOREIGN KEY (`added_byID`) REFERENCES `tbl_addedby` (`added_byID`) ON DELETE CASCADE;

--
-- Constraints for table `tbl_learner_quiz_progress`
--
ALTER TABLE `tbl_learner_quiz_progress`
  ADD CONSTRAINT `fk_lqp_badge` FOREIGN KEY (`badge_id`) REFERENCES `tbl_badge` (`badge_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_lqp_learner` FOREIGN KEY (`learner_id`) REFERENCES `tbl_learner` (`learner_id`) ON UPDATE CASCADE;

--
-- Constraints for table `tbl_lesson`
--
ALTER TABLE `tbl_lesson`
  ADD CONSTRAINT `objective_id` FOREIGN KEY (`objective_id`) REFERENCES `tbl_objective` (`objective_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `topic_id` FOREIGN KEY (`topic_id`) REFERENCES `tbl_topic` (`topic_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tbl_user_info`
--
ALTER TABLE `tbl_user_info`
  ADD CONSTRAINT `status_id` FOREIGN KEY (`status_id`) REFERENCES `tbl_status` (`status_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_level_id` FOREIGN KEY (`user_level_id`) REFERENCES `tbl_user_level` (`user_level_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tbl_writer`
--
ALTER TABLE `tbl_writer`
  ADD CONSTRAINT `fk_writer_addedby` FOREIGN KEY (`added_byID`) REFERENCES `tbl_addedby` (`added_byID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
