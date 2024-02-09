CREATE TABLE space_upload_file
(
    `upload_id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` VARCHAR(45) NOT NULL,
    `store_file_name` VARCHAR(255) NOT NULL,
    `upload_file_name` VARCHAR(255) NOT NULL,
    `created_date` DATETIME NOT NULL,
    `last_modified_date` DATETIME NOT NULL,
    PRIMARY KEY (`upload_id`)
);

CREATE TABLE furniture_upload_file
(
    `upload_id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` VARCHAR(45) NOT NULL,
    `store_file_name` VARCHAR(255) NOT NULL,
    `upload_file_name` VARCHAR(255) NOT NULL,
    `created_date` DATETIME NOT NULL,
    `last_modified_date` DATETIME NOT NULL,
    PRIMARY KEY (`upload_id`)
);