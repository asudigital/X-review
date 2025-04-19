-- Drop the database if it exists
DROP DATABASE IF EXISTS xreview;

-- Create the database
CREATE DATABASE xreview;

-- Use the created database
USE xreview;

-- Create users table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create companies table
CREATE TABLE companies (
    company_id INT AUTO_INCREMENT PRIMARY KEY,
    owner_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES users(user_id)
);

-- Create reviews table
CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    company_id INT NOT NULL,
    review_text TEXT NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (company_id) REFERENCES companies(company_id) ON DELETE CASCADE,
    UNIQUE (review_id, user_id)
);

-- Create review_likes table
CREATE TABLE review_likes (
    review_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (review_id, user_id),
    FOREIGN KEY (review_id) REFERENCES reviews(review_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create review_replies table
CREATE TABLE review_replies (
    review_id INT NOT NULL,
    owner_id INT NOT NULL,
    reply_text TEXT NOT NULL,
    PRIMARY KEY (review_id, owner_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (review_id) REFERENCES reviews(review_id) ON DELETE CASCADE,
    FOREIGN KEY (owner_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- -- Example scenario demonstrating ON DELETE CASCADE

-- -- Insert a user
-- INSERT INTO users (username, password) VALUES ('john_doe', 'password123');

-- -- Insert a company owned by the user
-- INSERT INTO companies (owner_id, name, description) VALUES (1, 'Tech Solutions', 'Innovative tech company');

-- -- Insert a review for the company by the user
-- INSERT INTO reviews (user_id, company_id, review_text, rating) VALUES (1, 1, 'Great service!', 5);

-- -- Insert a like for the review by the user
-- INSERT INTO review_likes (review_id, user_id) VALUES (1, 1);

-- -- Insert a reply to the review by the user
-- INSERT INTO review_replies (review_id, owner_id, reply_text) VALUES (1, 1, 'Thank you for your feedback!');

-- -- Delete the user
-- DELETE FROM users WHERE user_id = 1;

-- -- Check the result
-- -- The review, like, and reply related to the user should be automatically deleted due to ON DELETE CASCADE
-- SELECT * FROM reviews;
-- SELECT * FROM review_likes;
-- SELECT * FROM review_replies;