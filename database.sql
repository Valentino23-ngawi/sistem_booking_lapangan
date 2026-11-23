-- ============================================
-- DATABASE SISTEM BOOKING LAPANGAN
-- Import file ini di phpMyAdmin
-- ============================================

CREATE DATABASE IF NOT EXISTS booking_lapangan
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE booking_lapangan;

-- ============================================
-- TABEL USERS (Admin & Customer)
-- ============================================
CREATE TABLE IF NOT EXISTS users (
    id_user     INT AUTO_INCREMENT PRIMARY KEY,
    nama        VARCHAR(100) NOT NULL,
    email       VARCHAR(100) NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    no_hp       VARCHAR(20),
    tipe        ENUM('admin', 'customer') NOT NULL DEFAULT 'customer',
    role        VARCHAR(50) DEFAULT NULL  -- Khusus admin
);

-- ============================================
-- TABEL LAPANGAN
-- ============================================
CREATE TABLE IF NOT EXISTS lapangan (
    id_lapangan     INT AUTO_INCREMENT PRIMARY KEY,
    nama_lapangan   VARCHAR(100) NOT NULL,
    jenis_olahraga  VARCHAR(50)  NOT NULL,
    harga_per_jam   DOUBLE       NOT NULL,
    status          VARCHAR(30)  NOT NULL DEFAULT 'Tersedia'
);

-- ============================================
-- TABEL JADWAL
-- ============================================
CREATE TABLE IF NOT EXISTS jadwal (
    id_jadwal   INT AUTO_INCREMENT PRIMARY KEY,
    tanggal     DATE        NOT NULL,
    jam_mulai   TIME        NOT NULL,
    jam_selesai TIME        NOT NULL,
    status      VARCHAR(30) NOT NULL DEFAULT 'Tersedia'
);

-- ============================================
-- TABEL BOOKING
-- ============================================
CREATE TABLE IF NOT EXISTS booking (
    id_booking      INT AUTO_INCREMENT PRIMARY KEY,
    tanggal_booking DATE        NOT NULL,
    durasi          INT         NOT NULL DEFAULT 1,
    total_harga     DOUBLE      NOT NULL,
    status_booking  VARCHAR(50) NOT NULL DEFAULT 'Menunggu Pembayaran',
    id_customer     INT         NOT NULL,
    id_lapangan     INT         NOT NULL,
    id_jadwal       INT         NOT NULL,
    FOREIGN KEY (id_customer) REFERENCES users(id_user),
    FOREIGN KEY (id_lapangan) REFERENCES lapangan(id_lapangan),
    FOREIGN KEY (id_jadwal)   REFERENCES jadwal(id_jadwal)
);

-- ============================================
-- TABEL PEMBAYARAN
-- ============================================
CREATE TABLE IF NOT EXISTS pembayaran (
    id_pembayaran       INT AUTO_INCREMENT PRIMARY KEY,
    metode_pembayaran   VARCHAR(50)  NOT NULL,
    jumlah_bayar        DOUBLE       NOT NULL,
    bukti_pembayaran    VARCHAR(200) DEFAULT NULL,
    status_pembayaran   VARCHAR(50)  NOT NULL DEFAULT 'Menunggu Verifikasi',
    id_booking          INT          NOT NULL,
    FOREIGN KEY (id_booking) REFERENCES booking(id_booking)
);

-- ============================================
-- TABEL NOTIFIKASI
-- ============================================
CREATE TABLE IF NOT EXISTS notifikasi (
    id_notifikasi   INT AUTO_INCREMENT PRIMARY KEY,
    pesan           TEXT        NOT NULL,
    tanggal         DATE        NOT NULL,
    status          VARCHAR(30) NOT NULL DEFAULT 'Belum Dibaca',
    id_user         INT         NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id_user)
);

-- ============================================
-- DATA AWAL (SEED)
-- ============================================

-- Admin
INSERT INTO users (nama, email, password, no_hp, tipe, role) VALUES
('Admin', 'admin@gmail.com', 'admin123', '08111111111', 'admin', 'Administrator');

-- Customer contoh
INSERT INTO users (nama, email, password, no_hp, tipe, role) VALUES
('Budi', 'budi@gmail.com', 'budi123', '08222222222', 'customer', NULL);

-- Lapangan
INSERT INTO lapangan (nama_lapangan, jenis_olahraga, harga_per_jam, status) VALUES
('Futsal A',    'Futsal',    100000, 'Tersedia'),
('Badminton A', 'Badminton',  75000, 'Tersedia'),
('Basket A',    'Basket',    120000, 'Tersedia');

-- Jadwal
INSERT INTO jadwal (tanggal, jam_mulai, jam_selesai, status) VALUES
('2025-07-10', '08:00:00', '09:00:00', 'Tersedia'),
('2025-07-10', '10:00:00', '11:00:00', 'Tersedia'),
('2025-07-10', '12:00:00', '13:00:00', 'Tersedia'),
('2025-07-11', '08:00:00', '09:00:00', 'Tersedia'),
('2025-07-11', '10:00:00', '11:00:00', 'Tersedia');
