CREATE DATABASE sandbox;

USE sandbox;

CREATE TABLE Users (
  id CHAR(36) NOT NULL,
  username VARCHAR(32) UNIQUE NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password BINARY(60) NOT NULL,
  active BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE Books (
  id CHAR(36) NOT NULL,
  user CHAR(36) NOT NULL,
  publisher VARCHAR(255) NOT NULL,
  name VARCHAR(255) UNIQUE NOT NULL,
  cover_price DECIMAL(6,2) NOT NULL,
  page_count INT NOT NULL,
  published_at DATE NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user) REFERENCES Users (id)
);

DELIMITER $$
CREATE PROCEDURE IF NOT EXISTS SaveUser(
  IN _id CHAR(36),
  IN _username VARCHAR(32),
  IN _email VARCHAR(255),
  IN _password BINARY(60)
)
BEGIN
  INSERT INTO Users (id, username, email, password) VALUES (_id, _username, _email, _password);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE IF NOT EXISTS UpdateUser(
  IN _id CHAR(36),
  IN _username VARCHAR(32),
  IN _password BINARY(60)
)
BEGIN
  UPDATE Users SET username = _username, password = _password WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE IF NOT EXISTS RemoveUser(
  IN _id CHAR(36)
)
BEGIN
  UPDATE Users SET active = FALSE WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE IF NOT EXISTS QueryUsers()
BEGIN
  SELECT * FROM Users WHERE active = TRUE ORDER BY created_at;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE IF NOT EXISTS QueryUser(
  IN _id CHAR(36)
)
BEGIN
  SELECT * FROM Users WHERE id = _id ORDER BY created_at;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE IF NOT EXISTS QueryUserByUsername(
  IN _username VARCHAR(32)
)
BEGIN
  SELECT * FROM Users WHERE username = _username;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE IF NOT EXISTS QueryUserBooks(
  IN _id CHAR(36)
)
BEGIN
  SELECT * FROM Books WHERE user = _id ORDER BY created_at;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE IF NOT EXISTS SaveBook(
  IN _id CHAR(36),
  IN _user CHAR(36),
  IN _publisher VARCHAR(255),
  IN _name VARCHAR(255),
  IN _cover_price DECIMAL(6,2),
  IN _page_count INT,
  IN _published_at TIMESTAMP
)
BEGIN
  INSERT INTO Books (id, user, publisher, name, cover_price, page_count, published_at) VALUES (_id, _user, _publisher, _name, _cover_price, _page_count, _published_at);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE IF NOT EXISTS UpdateBook (
  IN _id CHAR(36),
  IN _user CHAR(36),
  IN _publisher VARCHAR(255),
  IN _name VARCHAR(255),
  IN _cover_price DECIMAL(6,2),
  IN _page_count INT,
  IN _published_at TIMESTAMP
)
BEGIN
  UPDATE Books SET publisher = _publisher, name = _name, cover_price = _cover_price, page_count = _page_count, published_at = _published_at WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE IF NOT EXISTS RemoveBook (
  IN _id CHAR(36)
)
BEGIN
  UPDATE Books SET active = FALSE WHERE id = _id;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE IF NOT EXISTS QueryBooks (
)
BEGIN
  SELECT * FROM Books WHERE active = TRUE ORDER BY created_at;
END $$
DELIMITER ;
