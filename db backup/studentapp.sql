-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 22, 2020 at 12:42 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `studentapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `departments`
--

CREATE TABLE `departments`
(
    `id`         int(11)     NOT NULL,
    `department` varchar(32) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

--
-- Dumping data for table `departments`
--

INSERT INTO `departments` (`id`, `department`)
VALUES (1, 'Engineering And Tech'),
       (2, 'Pure & Applied Sciences'),
       (3, 'Education'),
       (4, 'Mathematics'),
       (5, 'Medicine & Surgery'),
       (31, 'sleeping');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students`
(
    `id`               int(11)     NOT NULL,
    `firstname`        varchar(32) NOT NULL,
    `lastname`         varchar(32) NOT NULL,
    `mobilenumber`     varchar(32) NOT NULL,
    `email`            varchar(32) NOT NULL,
    `location`         varchar(32) NOT NULL,
    `gender`           varchar(32) NOT NULL,
    `registrationdate` datetime DEFAULT NULL,
    `level`            varchar(32) NOT NULL,
    `department`       varchar(32) NOT NULL,
    `coursename`       varchar(32) NOT NULL,
    `amount`           varchar(32) NOT NULL,
    `balance`          varchar(32) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`id`, `firstname`, `lastname`, `mobilenumber`, `email`, `location`, `gender`,
                        `registrationdate`, `level`, `department`, `coursename`, `amount`, `balance`)
VALUES (29, 'Ahmad', 'Muhammad', '09050273391', 'ahmadmmk5@gmail.com', 'Kaduna', 'Male', '2020-06-21 18:57:08',
        'Degree', 'Engineering And Tech', 'Software Engineer', '200000', 'N 0.00'),
       (30, 'Mahmud', 'Muhammad', '08123874123', 'mahmudmk@gmail.com', 'kano', 'Male', '2020-06-21 18:57:55', 'Degree',
        'Education', 'Health Education', '100000', 'N 100000.00');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users`
(
    `id`           int(11)      NOT NULL,
    `username`     varchar(32)  NOT NULL,
    `firstname`    varchar(32)  NOT NULL,
    `lastname`     varchar(32)  NOT NULL,
    `email`        varchar(32)  NOT NULL,
    `mobilenumber` varchar(32)  NOT NULL,
    `role`         varchar(32)  NOT NULL,
    `password`     varchar(255) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `firstname`, `lastname`, `email`, `mobilenumber`, `role`, `password`)
VALUES (1, 'admin', 'Ahmad', 'Muhammad', 'ahmadmuhammadmak5@gmail.com', '09050273391', 'admin',
        'd033e22ae348aeb5660fc2140aec35850c4da997'),
       (2, 'andromadus', 'Ali', 'Phalcon', 'Andromadus@gmail.com', '0905454545', 'staff',
        'b42a6d93d796915222f6ffb2ffdd6137d93c1cdb'),
       (3, 'maryam', 'Maryam', 'Muhammad', 'mmk@gmail.com', '09032438952', 'staff',
        '3aeeb73948b9aadc5ff68851c321fe632dfd9d98'),
       (5, 'najiu', 'Najiu', 'Ahmad', 'najiu@gmail.com', '09076465578', 'staff',
        '86acff79ca329ec4e51d0c06270d23161f955f12');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `departments`
--
ALTER TABLE `departments`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
    ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `departments`
--
ALTER TABLE `departments`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 32;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 32;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
