-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 14, 2022 at 01:05 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inixtraining`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_detail_kelas`
--

CREATE TABLE `tb_detail_kelas` (
  `id_detail_kls` int(10) NOT NULL,
  `id_kls` int(10) NOT NULL,
  `id_pst` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_detail_kelas`
--

INSERT INTO `tb_detail_kelas` (`id_detail_kls`, `id_kls`, `id_pst`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5),
(6, 1, 6),
(7, 1, 7),
(8, 1, 8),
(9, 1, 9),
(10, 1, 10),
(11, 1, 11),
(12, 1, 12),
(13, 1, 13),
(14, 1, 14),
(15, 1, 15),
(16, 2, 16),
(17, 2, 17),
(18, 2, 18),
(19, 2, 19),
(20, 2, 20),
(124, 122, 115);

-- --------------------------------------------------------

--
-- Table structure for table `tb_instruktur`
--

CREATE TABLE `tb_instruktur` (
  `id_ins` int(10) NOT NULL,
  `nama_ins` varchar(50) NOT NULL,
  `email_ins` varchar(50) NOT NULL,
  `hp_ins` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_instruktur`
--

INSERT INTO `tb_instruktur` (`id_ins`, `nama_ins`, `email_ins`, `hp_ins`) VALUES
(1, 'Instruktur_0001', 'Instruktur_0001@gmail.com', '620000000001'),
(2, 'Instruktur_0002', 'Instruktur_0002@gmail.com', '620000000002'),
(3, 'Instruktur_0003', 'Instruktur_0003@gmail.com', '620000000003'),
(4, 'Instruktur_0004', 'Instruktur_0004@gmail.com', '620000000004'),
(5, 'Instruktur_0005', 'Instruktur_0005@gmail.com', '620000000005'),
(6, 'Instruktur_0006', 'Instruktur_0006@gmail.com', '620000000006'),
(7, 'Instruktur_0007', 'Instruktur_0007@gmail.com', '620000000007'),
(8, 'Instruktur_0008', 'Instruktur_0008@gmail.com', '620000000008'),
(9, 'Instruktur_0009', 'Instruktur_0009@gmail.com', '620000000009'),
(10, 'Instruktur_0010', 'Instruktur_0010@gmail.com', '620000000010'),
(11, 'Instruktur_0011', 'Instruktur_0011@gmail.com', '620000000011'),
(12, 'Instruktur_0012', 'Instruktur_0012@gmail.com', '620000000012'),
(13, 'Instruktur_0013', 'Instruktur_0013@gmail.com', '620000000013'),
(14, 'Instruktur_0014', 'Instruktur_0014@gmail.com', '620000000014'),
(15, 'Instruktur_0015', 'Instruktur_0015@gmail.com', '620000000015'),
(16, 'Instruktur_0016', 'Instruktur_0016@gmail.com', '620000000016'),
(17, 'Instruktur_0017', 'Instruktur_0017@gmail.com', '620000000017'),
(18, 'Instruktur_0018', 'Instruktur_0018@gmail.com', '620000000018'),
(19, 'Instruktur_0019', 'Instruktur_0019@gmail.com', '620000000019'),
(20, 'Instruktur_0020', 'Instruktur_0020@gmail.com', '620000000020'),
(113, 'Instruktur Baru 113', 'email@gmail.com', '081234');

-- --------------------------------------------------------

--
-- Table structure for table `tb_kelas`
--

CREATE TABLE `tb_kelas` (
  `id_kls` int(10) NOT NULL,
  `tgl_mulai_kls` date NOT NULL,
  `tgl_akhir_kls` date NOT NULL,
  `id_ins` int(10) NOT NULL,
  `id_mat` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_kelas`
--

INSERT INTO `tb_kelas` (`id_kls`, `tgl_mulai_kls`, `tgl_akhir_kls`, `id_ins`, `id_mat`) VALUES
(1, '2022-01-01', '2022-06-01', 1, 1),
(2, '2022-01-01', '2022-06-02', 1, 2),
(3, '2022-01-01', '2022-06-03', 1, 3),
(4, '2022-01-01', '2022-06-04', 1, 4),
(5, '2022-01-01', '2022-06-05', 1, 5),
(6, '2022-02-02', '2022-06-06', 2, 1),
(7, '2022-01-01', '2022-06-01', 2, 2),
(8, '2022-01-01', '2022-06-01', 2, 3),
(9, '2022-01-01', '2022-06-01', 2, 4),
(10, '2022-01-01', '2022-06-01', 2, 5),
(11, '2022-01-01', '2022-06-01', 3, 1),
(12, '2022-01-01', '2022-06-01', 3, 2),
(13, '2022-01-01', '2022-06-01', 3, 3),
(14, '2022-01-01', '2022-06-01', 3, 4),
(15, '2022-01-01', '2022-06-01', 3, 5),
(16, '2022-01-01', '2022-06-01', 3, 1),
(17, '2022-01-01', '2022-06-01', 3, 2),
(18, '2022-01-01', '2022-06-01', 3, 3),
(19, '2022-01-01', '2022-06-01', 3, 4),
(20, '2022-01-01', '2022-06-01', 3, 5),
(121, '2024-02-14', '2025-02-14', 113, 113),
(122, '2024-02-14', '2025-02-14', 113, 113);

-- --------------------------------------------------------

--
-- Table structure for table `tb_materi`
--

CREATE TABLE `tb_materi` (
  `id_mat` int(10) NOT NULL,
  `nama_mat` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_materi`
--

INSERT INTO `tb_materi` (`id_mat`, `nama_mat`) VALUES
(1, 'Materi_0001'),
(2, 'Materi_0002'),
(3, 'Materi_0003'),
(4, 'Materi_0004'),
(5, 'Materi_0005'),
(6, 'Materi_0006'),
(7, 'Materi_0007'),
(8, 'Materi_0008'),
(9, 'Materi_0009'),
(10, 'Materi_00010'),
(11, 'Materi_00011'),
(12, 'Materi_00012'),
(13, 'Materi_00013'),
(14, 'Materi_00014'),
(15, 'Materi_00015'),
(16, 'Materi_00016'),
(17, 'Materi_00017'),
(18, 'Materi_00018'),
(19, 'Materi_00019'),
(20, 'Materi_00020'),
(113, 'Materi Baru 113');

-- --------------------------------------------------------

--
-- Table structure for table `tb_peserta`
--

CREATE TABLE `tb_peserta` (
  `id_pst` int(10) NOT NULL,
  `nama_pst` varchar(50) NOT NULL,
  `email_pst` varchar(50) NOT NULL,
  `hp_pst` varchar(13) NOT NULL,
  `instansi_pst` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_peserta`
--

INSERT INTO `tb_peserta` (`id_pst`, `nama_pst`, `email_pst`, `hp_pst`, `instansi_pst`) VALUES
(1, 'Peserta_0001', 'Peserta_0001@gmail.com', '620000000001', 'Instansi_0001'),
(2, 'Peserta_0002', 'Peserta_0002@gmail.com', '620000000002', 'Instansi_0001'),
(3, 'Peserta_0003', 'Peserta_0003@gmail.com', '620000000003', 'Instansi_0001'),
(4, 'Peserta_0004', 'Peserta_0004@gmail.com', '620000000004', 'Instansi_0001'),
(5, 'Peserta_0005', 'Peserta_0005@gmail.com', '620000000005', 'Instansi_0001'),
(6, 'Peserta_0006', 'Peserta_0006@gmail.com', '620000000006', 'Instansi_0001'),
(7, 'Peserta_0007', 'Peserta_0007@gmail.com', '620000000007', 'Instansi_0001'),
(8, 'Peserta_0008', 'Peserta_0008@gmail.com', '620000000008', 'Instansi_0001'),
(9, 'Peserta_0009', 'Peserta_0009@gmail.com', '620000000009', 'Instansi_0001'),
(10, 'Peserta_0010', 'Peserta_0010@gmail.com', '620000000010', 'Instansi_0001'),
(11, 'Peserta_0011', 'Peserta_0011@gmail.com', '620000000011', 'Instansi_0001'),
(12, 'Peserta_0012', 'Peserta_0012@gmail.com', '620000000012', 'Instansi_0001'),
(13, 'Peserta_0013', 'Peserta_0013@gmail.com', '620000000013', 'Instansi_0001'),
(14, 'Peserta_0014', 'Peserta_0014@gmail.com', '620000000014', 'Instansi_0001'),
(15, 'Peserta_0015', 'Peserta_0015@gmail.com', '620000000015', 'Instansi_0001'),
(16, 'Peserta_0016', 'Peserta_0016@gmail.com', '620000000016', 'Instansi_0001'),
(17, 'Peserta_0017', 'Peserta_0017@gmail.com', '620000000017', 'Instansi_0001'),
(18, 'Peserta_0018', 'Peserta_0018@gmail.com', '620000000018', 'Instansi_0001'),
(19, 'Peserta_0019', 'Peserta_0019@gmail.com', '620000000019', 'Instansi_0001'),
(20, 'Peserta_0020', 'Peserta_0020@gmail.com', '620000000020', 'Instansi_0002'),
(113, 'Peserta_0113', 'Peserta_0113@gmail.com', '620000000113', 'Instansi_0113'),
(114, 'Peserta_0114', 'email@gmail.com', '082214', 'Instansi_0114'),
(115, 'dito', 'email@gmail.com', '081234567', 'Instansi-1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_detail_kelas`
--
ALTER TABLE `tb_detail_kelas`
  ADD PRIMARY KEY (`id_detail_kls`),
  ADD KEY `id_kls` (`id_kls`),
  ADD KEY `id_pst` (`id_pst`);

--
-- Indexes for table `tb_instruktur`
--
ALTER TABLE `tb_instruktur`
  ADD PRIMARY KEY (`id_ins`);

--
-- Indexes for table `tb_kelas`
--
ALTER TABLE `tb_kelas`
  ADD PRIMARY KEY (`id_kls`),
  ADD KEY `id_ins` (`id_ins`),
  ADD KEY `id_mat` (`id_mat`);

--
-- Indexes for table `tb_materi`
--
ALTER TABLE `tb_materi`
  ADD PRIMARY KEY (`id_mat`);

--
-- Indexes for table `tb_peserta`
--
ALTER TABLE `tb_peserta`
  ADD PRIMARY KEY (`id_pst`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_detail_kelas`
--
ALTER TABLE `tb_detail_kelas`
  MODIFY `id_detail_kls` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=125;

--
-- AUTO_INCREMENT for table `tb_instruktur`
--
ALTER TABLE `tb_instruktur`
  MODIFY `id_ins` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=114;

--
-- AUTO_INCREMENT for table `tb_kelas`
--
ALTER TABLE `tb_kelas`
  MODIFY `id_kls` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=123;

--
-- AUTO_INCREMENT for table `tb_materi`
--
ALTER TABLE `tb_materi`
  MODIFY `id_mat` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=114;

--
-- AUTO_INCREMENT for table `tb_peserta`
--
ALTER TABLE `tb_peserta`
  MODIFY `id_pst` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=116;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_detail_kelas`
--
ALTER TABLE `tb_detail_kelas`
  ADD CONSTRAINT `tb_detail_kelas_ibfk_1` FOREIGN KEY (`id_kls`) REFERENCES `tb_kelas` (`id_kls`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_detail_kelas_ibfk_2` FOREIGN KEY (`id_pst`) REFERENCES `tb_peserta` (`id_pst`) ON UPDATE CASCADE;

--
-- Constraints for table `tb_kelas`
--
ALTER TABLE `tb_kelas`
  ADD CONSTRAINT `tb_kelas_ibfk_1` FOREIGN KEY (`id_ins`) REFERENCES `tb_instruktur` (`id_ins`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `tb_kelas_ibfk_2` FOREIGN KEY (`id_mat`) REFERENCES `tb_materi` (`id_mat`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
