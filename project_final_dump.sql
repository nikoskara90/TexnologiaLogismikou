-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Εξυπηρετητής: 127.0.0.1
-- Χρόνος δημιουργίας: 21 Ιαν 2024 στις 15:28:09
-- Έκδοση διακομιστή: 10.4.27-MariaDB
-- Έκδοση PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Βάση δεδομένων: `project`
--

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `conference`
--

CREATE TABLE `conference` (
  `conference_id` int(11) NOT NULL,
  `creation_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `pc_chair_id` int(11) NOT NULL,
  `papers` text DEFAULT NULL,
  `pc_members` text DEFAULT NULL,
  `state` enum('CREATED','SUBMISSION','ASSIGNMENT','REVIEW','DECISION','FINAL_SUBMISSION','FINAL') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `conference`
--

INSERT INTO `conference` (`conference_id`, `creation_date`, `name`, `description`, `pc_chair_id`, `papers`, `pc_members`, `state`) VALUES
(1, '2024-01-12 10:44:00', 'Paok', 'dialekksi', 1, '1, 3', '1,2,', 'ASSIGNMENT'),
(2, '2024-01-13 12:24:04', '1', '1', 1, '1,', '1,', 'ASSIGNMENT'),
(3, '2024-01-13 15:19:21', 'review', 'review', 1, '1,2,3,', '1,', 'REVIEW'),
(4, '2024-01-13 15:36:41', 'desicion', 'desicion', 1, '1,2,3,', '1,', 'DECISION'),
(5, '2024-01-13 16:24:02', 'final submission', 'final submission', 1, '1, 4', '1,', 'FINAL_SUBMISSION'),
(6, '2024-01-13 17:12:32', 'teliko', 'final', 1, '1,', '1,', 'FINAL'),
(8, '2024-01-14 13:31:09', '2', '2', 1, '1,', '', 'SUBMISSION');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `paper`
--

CREATE TABLE `paper` (
  `paper_id` bigint(20) UNSIGNED NOT NULL,
  `creation_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `title` varchar(255) NOT NULL,
  `abstract_text` varchar(255) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `author_names` varchar(255) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `reviewer1_id` int(11) DEFAULT NULL,
  `reviewer2_id` int(11) DEFAULT NULL,
  `reviewer1_comments` text DEFAULT NULL,
  `reviewer2_comments` text DEFAULT NULL,
  `reviewer1_score` int(11) DEFAULT NULL,
  `reviewer2_score` int(11) DEFAULT NULL,
  `state` enum('CREATED','SUBMITTED','REVIEWED','REJECTED','APPROVED','ACCEPTED') DEFAULT 'CREATED'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `paper`
--

INSERT INTO `paper` (`paper_id`, `creation_date`, `title`, `abstract_text`, `content`, `author_names`, `user_id`, `reviewer1_id`, `reviewer2_id`, `reviewer1_comments`, `reviewer2_comments`, `reviewer1_score`, `reviewer2_score`, `state`) VALUES
(1, '2024-01-09 09:59:23', 'TL', 'lab', 'kritikos', 'Nikos Karagiannis', 1, 1, NULL, 'aaa', NULL, 5, NULL, 'ACCEPTED'),
(2, '2024-01-12 10:42:35', 'aek', 'doulevei', 'ontos', 'Nikos Karagiannis', 1, NULL, NULL, NULL, NULL, NULL, NULL, 'SUBMITTED'),
(4, '2024-01-13 16:18:20', 'ewqe', 'ewq', 'correct', 'Swtiris Flaskis', 2, NULL, NULL, 'accept', NULL, NULL, NULL, 'ACCEPTED');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `paper_co_authors`
--

CREATE TABLE `paper_co_authors` (
  `paper_paper_id` bigint(20) DEFAULT NULL,
  `co_authors_user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `role`
--

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `role_name` enum('VISITOR','AUTHOR','PC_MEMBER','PC_CHAIR') NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `role`
--

INSERT INTO `role` (`role_id`, `role_name`, `user_id`) VALUES
(1, 'AUTHOR', 1),
(2, 'VISITOR', 1),
(3, 'PC_MEMBER', 1),
(4, 'PC_CHAIR', 1),
(5, 'AUTHOR', 2),
(6, 'PC_MEMBER', 2),
(7, 'VISITOR', 18);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `full_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `user`
--

INSERT INTO `user` (`user_id`, `username`, `password`, `full_name`) VALUES
(1, 'Nikos', 'Karagiannis', 'Nikos Karagiannis'),
(2, 'Swtiris', 'Flaskis', 'Swtiris Flaskis'),
(10, 'java', 'javax', 'success'),
(11, 'Panos', '123', 'success!'),
(12, 'Nikos', '555', 'RACKATTACK'),
(18, '789', '7890', 'paok');

--
-- Ευρετήρια για άχρηστους πίνακες
--

--
-- Ευρετήρια για πίνακα `conference`
--
ALTER TABLE `conference`
  ADD PRIMARY KEY (`conference_id`),
  ADD UNIQUE KEY `name` (`name`),
  ADD UNIQUE KEY `unique_description` (`description`) USING HASH,
  ADD KEY `fk_pc_chair` (`pc_chair_id`);

--
-- Ευρετήρια για πίνακα `paper`
--
ALTER TABLE `paper`
  ADD PRIMARY KEY (`paper_id`),
  ADD UNIQUE KEY `title` (`title`),
  ADD KEY `fk_user_id` (`user_id`),
  ADD KEY `fk_reviewer1_user` (`reviewer1_id`),
  ADD KEY `fk_reviewer2_user` (`reviewer2_id`);

--
-- Ευρετήρια για πίνακα `paper_co_authors`
--
ALTER TABLE `paper_co_authors`
  ADD UNIQUE KEY `UK_1ga4o0uxeb30l24dcjd4tqs6x` (`co_authors_user_id`);

--
-- Ευρετήρια για πίνακα `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`),
  ADD KEY `fk_user_role` (`user_id`);

--
-- Ευρετήρια για πίνακα `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT για άχρηστους πίνακες
--

--
-- AUTO_INCREMENT για πίνακα `conference`
--
ALTER TABLE `conference`
  MODIFY `conference_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT για πίνακα `paper`
--
ALTER TABLE `paper`
  MODIFY `paper_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT για πίνακα `role`
--
ALTER TABLE `role`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT για πίνακα `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Περιορισμοί για άχρηστους πίνακες
--

--
-- Περιορισμοί για πίνακα `conference`
--
ALTER TABLE `conference`
  ADD CONSTRAINT `fk_pc_chair` FOREIGN KEY (`pc_chair_id`) REFERENCES `user` (`user_id`);

--
-- Περιορισμοί για πίνακα `paper`
--
ALTER TABLE `paper`
  ADD CONSTRAINT `FK96m901967df6xbsrg5ms0avcn` FOREIGN KEY (`reviewer2_id`) REFERENCES `role` (`role_id`),
  ADD CONSTRAINT `FKegl8fa4gkeg7o4aka5gh45rbo` FOREIGN KEY (`reviewer1_id`) REFERENCES `role` (`role_id`),
  ADD CONSTRAINT `fk_reviewer1_user` FOREIGN KEY (`reviewer1_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `fk_reviewer2_user` FOREIGN KEY (`reviewer2_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Περιορισμοί για πίνακα `role`
--
ALTER TABLE `role`
  ADD CONSTRAINT `fk_user_role` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
