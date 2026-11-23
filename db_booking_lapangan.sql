-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 23, 2026 at 03:22 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `booking_lapangan`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `id_booking` int(11) NOT NULL,
  `tanggal_booking` date NOT NULL,
  `durasi` int(11) NOT NULL DEFAULT 1,
  `total_harga` double NOT NULL,
  `status_booking` varchar(50) DEFAULT 'Menunggu Pembayaran',
  `id_customer` int(11) NOT NULL,
  `id_lapangan` int(11) NOT NULL,
  `id_jadwal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`id_booking`, `tanggal_booking`, `durasi`, `total_harga`, `status_booking`, `id_customer`, `id_lapangan`, `id_jadwal`) VALUES
(1, '2026-06-11', 2, 200000, 'Dikonfirmasi', 2, 1, 1),
(2, '2026-06-11', 1, 75000, 'Dikonfirmasi', 2, 2, 2),
(3, '2026-06-11', 3, 180000, 'Dikonfirmasi', 2, 4, 4),
(4, '2026-06-18', 2, 120000, 'Dikonfirmasi', 2, 4, 5),
(5, '2026-06-18', 1, 100000, 'Menunggu Pembayaran', 2, 1, 3),
(6, '2026-06-18', 2, 200000, 'Dikonfirmasi', 2, 1, 6),
(7, '2026-06-18', 2, 120000, 'Menunggu Pembayaran', 2, 4, 7),
(8, '2026-06-18', 2, 200000, 'Menunggu Pembayaran', 2, 1, 9),
(9, '2026-06-18', 1, 100000, 'Menunggu Pembayaran', 2, 1, 8),
(10, '2026-06-18', 2, 200000, 'Menunggu Pembayaran', 2, 1, 10),
(11, '2026-06-18', 2, 200000, 'Menunggu Pembayaran', 2, 1, 11),
(12, '2026-11-23', 1, 100000, 'Dikonfirmasi', 2, 1, 14);

-- --------------------------------------------------------

--
-- Table structure for table `jadwal`
--

CREATE TABLE `jadwal` (
  `id_jadwal` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `jam_mulai` time NOT NULL,
  `jam_selesai` time NOT NULL,
  `status` varchar(30) NOT NULL DEFAULT 'Tersedia'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `jadwal`
--

INSERT INTO `jadwal` (`id_jadwal`, `tanggal`, `jam_mulai`, `jam_selesai`, `status`) VALUES
(1, '2025-07-10', '08:00:00', '09:00:00', 'Dibooking'),
(2, '2025-07-10', '10:00:00', '11:00:00', 'Dibooking'),
(3, '2025-07-10', '12:00:00', '13:00:00', 'Dibooking'),
(4, '2025-07-11', '08:00:00', '09:00:00', 'Dibooking'),
(5, '2025-07-11', '10:00:00', '11:00:00', 'Dibooking'),
(6, '2025-07-12', '08:00:00', '10:00:00', 'Dibooking'),
(7, '2026-06-20', '07:00:00', '09:00:00', 'Dibooking'),
(8, '2026-06-21', '07:00:00', '23:00:00', 'Dibooking'),
(9, '2026-06-22', '07:00:00', '20:00:00', 'Dibooking'),
(10, '2026-07-15', '00:00:07', '00:00:09', 'Dibooking'),
(11, '2026-06-18', '00:00:16', '00:00:17', 'Dibooking'),
(14, '2026-11-23', '09:00:00', '10:00:00', 'Dibooking'),
(15, '2026-11-23', '11:00:00', '12:00:00', 'Tersedia'),
(16, '2026-11-23', '12:00:00', '13:00:00', 'Tersedia'),
(17, '2026-11-23', '15:00:00', '16:00:00', 'Tersedia');

-- --------------------------------------------------------

--
-- Table structure for table `lapangan`
--

CREATE TABLE `lapangan` (
  `id_lapangan` int(11) NOT NULL,
  `nama_lapangan` varchar(100) NOT NULL,
  `jenis_olahraga` varchar(50) NOT NULL,
  `harga_per_jam` double NOT NULL,
  `status` varchar(30) NOT NULL DEFAULT 'Tersedia'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `lapangan`
--

INSERT INTO `lapangan` (`id_lapangan`, `nama_lapangan`, `jenis_olahraga`, `harga_per_jam`, `status`) VALUES
(1, 'Futsal A', 'Futsal', 100000, 'Tersedia'),
(2, 'Badminton A', 'Badminton', 75000, 'Tersedia'),
(4, 'Arena 1', 'Futsal', 60000, 'Tersedia'),
(5, 'Arena 2', 'Badminton', 75000, 'Tidak Tersedia');

-- --------------------------------------------------------

--
-- Table structure for table `notifikasi`
--

CREATE TABLE `notifikasi` (
  `id_notifikasi` int(11) NOT NULL,
  `pesan` text NOT NULL,
  `status` varchar(50) DEFAULT 'Belum Dibaca',
  `tanggal` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `notifikasi`
--

INSERT INTO `notifikasi` (`id_notifikasi`, `pesan`, `status`, `tanggal`) VALUES
(1, 'Booking baru masuk untuk Futsal A (ID Booking: #8)', 'Dibaca', '2026-06-18 14:49:16'),
(2, 'Pembayaran Baru masuk dari Booking ID #3', 'Dibaca', '2026-06-18 14:49:46'),
(3, 'Pembayaran Baru masuk dari Booking ID #4', 'Dibaca', '2026-06-18 15:03:58'),
(4, 'Booking baru masuk untuk Futsal A (ID Booking: #9)', 'Dibaca', '2026-06-18 15:48:39'),
(5, 'Booking baru masuk untuk Futsal A (ID Booking: #10)', 'Dibaca', '2026-06-18 15:54:10'),
(6, 'Pembayaran Baru masuk dari Booking ID #8', 'Dibaca', '2026-06-18 15:56:34'),
(7, 'Booking baru masuk untuk Futsal A (ID Booking: #11)', 'Belum Dibaca', '2026-06-18 16:06:55'),
(8, 'Pembayaran Baru masuk dari Booking ID #3', 'Belum Dibaca', '2026-06-18 16:07:43'),
(9, 'Booking baru masuk untuk Futsal A (ID Booking: #0)', 'Belum Dibaca', '2026-11-23 09:06:21'),
(10, 'Pembayaran baru masuk dari Booking ID #12', 'Belum Dibaca', '2026-11-23 09:06:58');

-- --------------------------------------------------------

--
-- Table structure for table `pembayaran`
--

CREATE TABLE `pembayaran` (
  `id_pembayaran` int(11) NOT NULL,
  `id_booking` int(11) NOT NULL,
  `total_bayar` double NOT NULL,
  `bukti_pembayaran` varchar(255) NOT NULL,
  `status` varchar(50) DEFAULT 'Menunggu Verifikasi'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `pembayaran`
--

INSERT INTO `pembayaran` (`id_pembayaran`, `id_booking`, `total_bayar`, `bukti_pembayaran`, `status`) VALUES
(1, 6, 200000, 'C:/users/mybook hype amd/OneDrive/Gambar/Camera Roll/IMG-20251212-WA0015.jpg', 'Dikonfirmasi'),
(2, 5, 100000, 'C:/users/mybook hype amd/OneDrive/Gambar/Roblox/RobloxScreenShot20260319_000844657.png', 'Ditolak'),
(3, 3, 180000, 'C:/users/mybook hype amd/OneDrive/Gambar/Screenshots/Screnshoot tugas semester 4/Screenshot 2026-02-03 210436.png', 'Ditolak'),
(4, 4, 120000, 'C:/users/mybook hype amd/OneDrive/Gambar/Roblox/RobloxScreenShot20260319_001056682.png', 'Dikonfirmasi'),
(5, 8, 200000, 'C:/Users/MyBook Hype AMD/OneDrive/Gambar/Bukti-Transfer-Bank-595x1024.jpg', 'Ditolak'),
(6, 3, 180000, 'C:/Users/MyBook Hype AMD/OneDrive/Gambar/Bukti-Transfer-Bank-595x1024.jpg', 'Dikonfirmasi'),
(7, 12, 100000, 'bukti_fab18df6f50e4a4f.png', 'Dikonfirmasi');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `tipe` enum('admin','customer') NOT NULL DEFAULT 'customer',
  `role` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `nama`, `email`, `password`, `no_hp`, `tipe`, `role`) VALUES
(1, 'Admin', 'admin@gmail.com', 'admin123', '08111111111', 'admin', 'Administrator'),
(2, 'Budi', 'budi@gmail.com', 'budi123', '08222222222', 'customer', NULL),
(3, 'Sheillo', 'sheillo@gmail.com', 'sheillo123', '085832726860', 'customer', NULL),
(6, 'Surya aji', 'surya@gmail.com', 'surya123', '08872941626', 'customer', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`id_booking`),
  ADD KEY `id_customer` (`id_customer`),
  ADD KEY `id_lapangan` (`id_lapangan`),
  ADD KEY `id_jadwal` (`id_jadwal`);

--
-- Indexes for table `jadwal`
--
ALTER TABLE `jadwal`
  ADD PRIMARY KEY (`id_jadwal`);

--
-- Indexes for table `lapangan`
--
ALTER TABLE `lapangan`
  ADD PRIMARY KEY (`id_lapangan`);

--
-- Indexes for table `notifikasi`
--
ALTER TABLE `notifikasi`
  ADD PRIMARY KEY (`id_notifikasi`);

--
-- Indexes for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD PRIMARY KEY (`id_pembayaran`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `id_booking` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `jadwal`
--
ALTER TABLE `jadwal`
  MODIFY `id_jadwal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `lapangan`
--
ALTER TABLE `lapangan`
  MODIFY `id_lapangan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `notifikasi`
--
ALTER TABLE `notifikasi`
  MODIFY `id_notifikasi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `pembayaran`
--
ALTER TABLE `pembayaran`
  MODIFY `id_pembayaran` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`id_customer`) REFERENCES `users` (`id_user`),
  ADD CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`id_lapangan`) REFERENCES `lapangan` (`id_lapangan`),
  ADD CONSTRAINT `booking_ibfk_3` FOREIGN KEY (`id_jadwal`) REFERENCES `jadwal` (`id_jadwal`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
