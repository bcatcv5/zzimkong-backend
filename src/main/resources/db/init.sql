DROP TABLE IF EXISTS space_model_result;
DROP TABLE IF EXISTS furniture_model_result;
DROP TABLE IF EXISTS furniture_upload_file;
DROP TABLE IF EXISTS space_upload_file;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
       `user_id` BIGINT AUTO_INCREMENT,
       `name` VARCHAR(255) NOT NULL,
       PRIMARY KEY (`user_id`)
);

CREATE TABLE furniture_upload_file (
       `upload_id` BIGINT AUTO_INCREMENT,
       `user_id` bigint NOT NULL,
       `store_file_url` VARCHAR(255) NOT NULL,
       `upload_file_name` VARCHAR(255) NOT NULL,
       `created_date` datetime(6) NOT NULL,
       `last_modified_date` datetime(6) NOT NULL,
       PRIMARY KEY (`upload_id`),
       FOREIGN KEY (`user_id`)
       REFERENCES `users` (`user_id`)
);

CREATE TABLE space_upload_file (
       `upload_id` BIGINT AUTO_INCREMENT,
       `user_id` bigint NOT NULL,
       `store_file_url` VARCHAR(255) NOT NULL,
       `upload_file_name` VARCHAR(255) NOT NULL,
       `created_date` datetime(6) NOT NULL,
       `last_modified_date` datetime(6) NOT NULL,
       PRIMARY KEY (`upload_id`),
       FOREIGN KEY (`user_id`)
           REFERENCES `users` (`user_id`)
);

CREATE TABLE space_model_result (
       `model_result_id` BIGINT AUTO_INCREMENT,
       `user_id` bigint NOT NULL,
       `status_code` VARCHAR(45) NOT NULL,
       `status_message` VARCHAR(255) DEFAULT NULL,
       `store_file_url` VARCHAR(255) DEFAULT NULL,
       `created_date` datetime(6) NOT NULL,
       `last_modified_date` datetime(6) NOT NULL,
       PRIMARY KEY (`model_result_id`),
       FOREIGN KEY (`user_id`)
           REFERENCES `users` (`user_id`)
);

CREATE TABLE furniture_model_result (
        `model_result_id` BIGINT AUTO_INCREMENT,
        `user_id` bigint NOT NULL,
        `status_code` VARCHAR(45) NOT NULL,
        `status_message` VARCHAR(255) DEFAULT NULL,
        `store_file_url` VARCHAR(255) DEFAULT NULL,
        `created_date` datetime(6) NOT NULL,
        `last_modified_date` datetime(6) NOT NULL,
        PRIMARY KEY (`model_result_id`),
        FOREIGN KEY (`user_id`)
            REFERENCES `users` (`user_id`)
);

INSERT INTO users
VALUES (1, 'sangbeom');