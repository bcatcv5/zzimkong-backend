CREATE TABLE space_upload_file
(
    `upload_id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` VARCHAR(45) NOT NULL,
    `store_file_url` VARCHAR(255) NOT NULL,
    `upload_file_name` VARCHAR(255) NOT NULL,
    `created_date` DATETIME NOT NULL,
    `last_modified_date` DATETIME NOT NULL,
    PRIMARY KEY (`upload_id`)
);

CREATE TABLE furniture_upload_file
(
    `upload_id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` VARCHAR(45) NOT NULL,
    `store_file_url` VARCHAR(255) NOT NULL,
    `upload_file_name` VARCHAR(255) NOT NULL,
    `created_date` DATETIME NOT NULL,
    `last_modified_date` DATETIME NOT NULL,
    PRIMARY KEY (`upload_id`)
);

CREATE TABLE users (
   `user_id` BIGINT AUTO_INCREMENT,
   `provider` VARCHAR(255) NOT NULL,
   `oauth_id` VARCHAR(255) NOT NULL,
   `name` VARCHAR(255) NOT NULL,
   `email` VARCHAR(255) NOT NULL,
   `photo_url` VARCHAR(255) NOT NULL,
   PRIMARY KEY (`user_id`)
);

CREATE TABLE space_model_result (
    `model_result_id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` bigint NOT NULL,
    `message_id` bigint NOT NULL,
    `status_code` VARCHAR(45) NOT NULL,
    `status_message` VARCHAR(255) DEFAULT NULL,
    `store_file_url` VARCHAR(255) DEFAULT NULL,
    `upload_file_name` VARCHAR(255) NOT NULL,
    `status_pushed` BOOLEAN NOT NULL,
    `thumbnail_file_url` VARCHAR(255) DEFAULT NULL,
    `created_date` DATETIME NOT NULL,
    `last_modified_date` DATETIME NOT NULL,
    `learned_date` DATETIME DEFAULT NULL,
    `finished_date` DATETIME DEFAULT NULL,
    PRIMARY KEY (`model_result_id`),
    FOREIGN KEY (`user_id`)
        REFERENCES `users` (`user_id`)
);

CREATE TABLE furniture_model_result (
    `furniture_result_id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` bigint NOT NULL,
    `message_id` bigint NOT NULL,
    `status_code` VARCHAR(45) NOT NULL,
    `status_message` VARCHAR(255) DEFAULT NULL,
    `store_file_url` VARCHAR(255) DEFAULT NULL,
    `upload_file_name` VARCHAR(255) NOT NULL,
    `status_pushed` BOOLEAN NOT NULL,
    `thumbnail_file_url` VARCHAR(255) DEFAULT NULL,
    `created_date` DATETIME NOT NULL,
    `last_modified_date` DATETIME NOT NULL,
    `learned_date` DATETIME DEFAULT NULL,
    `finished_date` DATETIME DEFAULT NULL,
    PRIMARY KEY (`furniture_result_id`),
    FOREIGN KEY (`user_id`)
        REFERENCES `users` (`user_id`)
);