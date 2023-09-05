CREATE TABLE IF NOT EXISTS `category`
(
    `id`          INT         NOT NULL AUTO_INCREMENT,
    `create_time` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `name`        VARCHAR(45) NOT NULL,
    `parent_id`   INT         NOT NULL DEFAULT 0 COMMENT '父category的id',
    PRIMARY KEY (`id`)
);

insert into `category`(`id`, `name`, `parent_id`)
values (1, '爷爷节点', 0);
insert into `category`(`id`, `name`, `parent_id`)
values (2, '父亲节点1', 1);
insert into `category`(`id`, `name`, `parent_id`)
values (3, '父亲节点2', 1);
insert into `category`(`id`, `name`, `parent_id`)
values (4, '父亲节点3', 1);


CREATE TABLE IF NOT EXISTS `category_mapping`
(
    `id`          INT        NOT NULL AUTO_INCREMENT,
    `create_time` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `category_id` INT        NOT NULL,
    `type`        TINYINT(2) NOT NULL DEFAULT 1 COMMENT 'Type: \n1 for metrics',
    `ref_id`      INT        NOT NULL,
    PRIMARY KEY (`id`)
);

insert into `category_mapping`(`category_id`, `type`, `ref_id`)
values (2, 1, 1),
       (2, 1, 2),
       (2, 1, 3);

-- -----------------------------------------------------
-- Table `role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `role`
(
    `id`          INT         NOT NULL AUTO_INCREMENT,
    `create_time` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `name`        VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);


-- -----------------------------------------------------
-- Table `subject_role_mapping`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `subject_role_mapping`
(
    `id`           INT         NOT NULL AUTO_INCREMENT,
    `create_time`  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `role_id`      INT         NOT NULL,
    `subject_type` VARCHAR(20) NOT NULL COMMENT 'subject_type: USER , USER_GROUP,\n表示这个role给了 用户组 or 用户',
    `subject_id`   VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);


-- -----------------------------------------------------
-- Table `permission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `permission`
(
    `id`          INT         NOT NULL AUTO_INCREMENT,
    `create_time` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `object_type` VARCHAR(20) NOT NULL COMMENT 'USER, USER_GROUP, CATEGORY, DATASOURCE, MODEL, METRIC',
    `object_id`   INT         NOT NULL DEFAULT 0 COMMENT '暂时不对特定的object做权限控制',
    `privilege`   VARCHAR(45) NOT NULL COMMENT 'MENU, READ, WRITE, CREATE, EDIT, DELETE',
    PRIMARY KEY (`id`)
);

INSERT INTO `permission` (`object_type`, `privilege`)
VALUES ('USER', 'MENU'),
       ('USER', 'READ'),
       ('USER', 'WRITE'),
       ('USER', 'CREATE'),
       ('USER', 'EDIT'),
       ('USER', 'DELETE');
INSERT INTO `permission` (`object_type`, `privilege`)
VALUES ('USER_GROUP', 'MENU'),
       ('USER_GROUP', 'READ'),
       ('USER_GROUP', 'WRITE'),
       ('USER_GROUP', 'CREATE'),
       ('USER_GROUP', 'EDIT'),
       ('USER_GROUP', 'DELETE');
INSERT INTO `permission` (`object_type`, `privilege`)
VALUES ('CATEGORY', 'MENU'),
       ('CATEGORY', 'READ'),
       ('CATEGORY', 'WRITE'),
       ('CATEGORY', 'CREATE'),
       ('CATEGORY', 'EDIT'),
       ('CATEGORY', 'DELETE');
INSERT INTO `permission` (`object_type`, `privilege`)
VALUES ('DATASOURCE', 'MENU'),
       ('DATASOURCE', 'READ'),
       ('DATASOURCE', 'WRITE'),
       ('DATASOURCE', 'CREATE'),
       ('DATASOURCE', 'EDIT'),
       ('DATASOURCE', 'DELETE');
INSERT INTO `permission` (`object_type`, `privilege`)
VALUES ('MODEL', 'MENU'),
       ('MODEL', 'READ'),
       ('MODEL', 'WRITE'),
       ('MODEL', 'CREATE'),
       ('MODEL', 'EDIT'),
       ('MODEL', 'DELETE');
INSERT INTO `permission` (`object_type`, `privilege`)
VALUES ('METRIC', 'MENU'),
       ('METRIC', 'READ'),
       ('METRIC', 'WRITE'),
       ('METRIC', 'CREATE'),
       ('METRIC', 'EDIT'),
       ('METRIC', 'DELETE');


-- -----------------------------------------------------
-- Table `role_permission_mapping`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `role_permission_mapping`
(
    `id`            INT       NOT NULL AUTO_INCREMENT,
    `create_time`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `role_id`       INT       NOT NULL,
    `permission_id` INT       NOT NULL,
    PRIMARY KEY (`id`)
);
