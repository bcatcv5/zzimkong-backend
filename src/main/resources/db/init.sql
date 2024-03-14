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
       `status_pushed` bit(1) NOT NULL,
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
        `status_pushed` bit(1) NOT NULL,
        `created_date` datetime(6) NOT NULL,
        `last_modified_date` datetime(6) NOT NULL,
        PRIMARY KEY (`model_result_id`),
        FOREIGN KEY (`user_id`)
            REFERENCES `users` (`user_id`)
);


DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_SEQ;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_SEQ;
DROP TABLE IF EXISTS BATCH_JOB_SEQ;

DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_PARAMS;
DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_CONTEXT;
DROP TABLE IF EXISTS BATCH_STEP_EXECUTION;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_CONTEXT;
DROP TABLE IF EXISTS BATCH_JOB_EXECUTION;
DROP TABLE IF EXISTS BATCH_JOB_INSTANCE;

CREATE TABLE BATCH_STEP_EXECUTION_SEQ (ID BIGINT NOT NULL);
INSERT INTO BATCH_STEP_EXECUTION_SEQ values(0);
CREATE TABLE BATCH_JOB_EXECUTION_SEQ (ID BIGINT NOT NULL);
INSERT INTO BATCH_JOB_EXECUTION_SEQ values(0);
CREATE TABLE BATCH_JOB_SEQ (ID BIGINT NOT NULL);
INSERT INTO BATCH_JOB_SEQ values(0);

CREATE TABLE BATCH_JOB_INSTANCE  (
                                     JOB_INSTANCE_ID BIGINT  PRIMARY KEY ,
                                     VERSION BIGINT,
                                     JOB_NAME VARCHAR(100) NOT NULL ,
                                     JOB_KEY VARCHAR(32) NOT NULL
);

CREATE TABLE BATCH_JOB_EXECUTION  (
                                      JOB_EXECUTION_ID BIGINT  PRIMARY KEY ,
                                      VERSION BIGINT,
                                      JOB_INSTANCE_ID BIGINT NOT NULL,
                                      CREATE_TIME TIMESTAMP NOT NULL,
                                      START_TIME TIMESTAMP DEFAULT NULL,
                                      END_TIME TIMESTAMP DEFAULT NULL,
                                      STATUS VARCHAR(10),
                                      EXIT_CODE VARCHAR(20),
                                      EXIT_MESSAGE VARCHAR(2500),
                                      LAST_UPDATED TIMESTAMP,
                                      constraint JOB_INSTANCE_EXECUTION_FK foreign key (JOB_INSTANCE_ID)
                                          references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ;

CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
                                             JOB_EXECUTION_ID BIGINT NOT NULL ,
                                             PARAMETER_NAME VARCHAR(100) NOT NULL ,
                                             PARAMETER_TYPE VARCHAR(100) NOT NULL ,
                                             PARAMETER_VALUE VARCHAR(2500) ,
                                             IDENTIFYING CHAR(1) NOT NULL ,
                                             constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
                                                 references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
);

CREATE TABLE BATCH_STEP_EXECUTION  (
                                       STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY ,
                                       VERSION BIGINT NOT NULL,
                                       STEP_NAME VARCHAR(100) NOT NULL,
                                       JOB_EXECUTION_ID BIGINT NOT NULL,
                                       CREATE_TIME TIMESTAMP NOT NULL,
                                       START_TIME TIMESTAMP DEFAULT NULL ,
                                       END_TIME TIMESTAMP DEFAULT NULL,
                                       STATUS VARCHAR(10),
                                       COMMIT_COUNT BIGINT ,
                                       READ_COUNT BIGINT ,
                                       FILTER_COUNT BIGINT ,
                                       WRITE_COUNT BIGINT ,
                                       READ_SKIP_COUNT BIGINT ,
                                       WRITE_SKIP_COUNT BIGINT ,
                                       PROCESS_SKIP_COUNT BIGINT ,
                                       ROLLBACK_COUNT BIGINT ,
                                       EXIT_CODE VARCHAR(20) ,
                                       EXIT_MESSAGE VARCHAR(2500) ,
                                       LAST_UPDATED TIMESTAMP,
                                       constraint JOB_EXECUTION_STEP_FK foreign key (JOB_EXECUTION_ID)
                                           references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
                                              JOB_EXECUTION_ID BIGINT PRIMARY KEY,
                                              SHORT_CONTEXT VARCHAR(2500) NOT NULL,
                                              SERIALIZED_CONTEXT TEXT,
                                              constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
                                                  references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
                                               STEP_EXECUTION_ID BIGINT PRIMARY KEY,
                                               SHORT_CONTEXT VARCHAR(2500) NOT NULL,
                                               SERIALIZED_CONTEXT TEXT,
                                               constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
                                                   references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ;

INSERT INTO users
VALUES (1, 'sangbeom');

INSERT INTO space_model_result
VALUES (1, 1, 'PROCESSING', 'PROCESSING', null, 0, '2024-03-13 00:00:00', '2024-03-13 00:00:00');

INSERT INTO space_model_result
VALUES (2, 1, 'PROCESSING', 'PROCESSING', null, 0, '2024-03-13 00:00:00', '2024-03-13 00:00:00');

INSERT INTO space_model_result
VALUES (3, 1, 'FINISH', 'PROCESSING', '123', 0, '2024-03-13 00:00:00', '2024-03-13 00:00:00');

INSERT INTO space_model_result
VALUES (4, 1, 'FINISH', 'PROCESSING', '123', 0, '2024-03-13 00:00:00', '2024-03-13 00:00:00');

INSERT INTO furniture_model_result
VALUES (1, 1, 'PROCESSING', 'PROCESSING', null, 0, '2024-03-13 00:00:00', '2024-03-13 00:00:00');

INSERT INTO furniture_model_result
VALUES (2, 1, 'PROCESSING', 'PROCESSING', null, 0, '2024-03-13 00:00:00', '2024-03-13 00:00:00');

INSERT INTO furniture_model_result
VALUES (3, 1, 'FINISH', 'PROCESSING', '123', 0, '2024-03-13 00:00:00', '2024-03-13 00:00:00');

INSERT INTO furniture_model_result
VALUES (4, 1, 'FINISH', 'PROCESSING', '123', 0, '2024-03-13 00:00:00', '2024-03-13 00:00:00');

INSERT INTO furniture_model_result
VALUES (5, 1, 'FINISH', 'PROCESSING', '123', 0, '2024-03-13 00:00:00', '2024-03-13 00:00:00');