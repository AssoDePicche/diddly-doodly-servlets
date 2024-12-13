CREATE DATABASE diddy-doodly-servlets DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

USE diddly-doodly-servlets;

CREATE TABLE Creators (
  id CHAR(36) NOT NULL,
  name VARCHAR(255) UNIQUE NOT NULL,
  biography TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE Series (
  id CHAR(36) NOT NULL,
  name VARCHAR(255) UNIQUE NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE Publishers (
  id CHAR(36) NOT NULL,
  name VARCHAR(255) UNIQUE NOT NULL,
  biography TEXT NOT NULL,
  founded_at YEAR NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE Books (
  id CHAR(36) NOT NULL,
  publisher CHAR(36) NOT NULL,
  name VARCHAR(255) UNIQUE NOT NULL,
  description TEXT NOT NULL,
  cover_price DECIMAL(6, 2) NOT NULL,
  page_count INT NOT NULL,
  stratification ENUM('Books', 'Comic Books') NOT NULL,
  published_at DATE NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (publisher) REFERENCES Publishers (id)
);

CREATE TABLE CreatorWork (
  book CHAR(36) NOT NULL,
  creator CHAR(36) NOT NULL,
  work ENUM('Writer', 'Illustrator', 'Penciller', 'Inker', 'Colorist', 'Letterer'),
  PRIMARY KEY (book, creator),
  FOREIGN KEY (book) REFERENCES Books (id),
  FOREIGN KEY (creator) REFERENCES Creators (id)
);

CREATE TABLE Users (
  id CHAR(36) NOT NULL,
  username VARCHAR(32) UNIQUE NOT NULL,
  display_name VARCHAR(50) NOT NULL,
  biography VARCHAR(160) DEFAULT 'Hello there!',
  email VARCHAR(255) UNIQUE NOT NULL,
  password BINARY(60) NOT NULL,
  active BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE UserBook (
  user CHAR(36) NOT NULL,
  book CHAR(36) NOT NULL,
  finished_reading BOOLEAN DEFAULT FALSE,
  purchased_for DECIMAL(6, 2) NOT NULL,
  purchased_in ENUM('Bookshop', 'Webshop', 'Literary Event', "Newsagent's shop") NOT NULL,
  purchased_at DATE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user, book),
  FOREIGN KEY (user) REFERENCES Users (id),
  FOREIGN KEY (book) REFERENCES Books (id)
);

CREATE TABLE UserWhishlist (
  user CHAR(36) NOT NULL,
  book CHAR(36) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user, book),
  FOREIGN KEY (user) REFERENCES Users (id),
  FOREIGN KEY (book) REFERENCES Books (id)
);
