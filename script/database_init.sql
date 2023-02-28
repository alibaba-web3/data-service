/******************************************/
/*   DatabaseName = service   */
/*   TableName = address_tag   */
/******************************************/
CREATE TABLE `address_tag` (
                               `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                               `creator` varchar(64) NOT NULL COMMENT '创建者',
                               `modifier` varchar(64) NOT NULL COMMENT '修改者',
                               `deleted` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除 0/1',
                               `address` varchar(64) NOT NULL COMMENT '打上标签的地址',
                               `tag_id` varchar(32) NOT NULL COMMENT '标签 id',
                               `category_id` varchar(32) NOT NULL COMMENT '标签分类 id',
                               `origin` varchar(32) NOT NULL COMMENT '标签来源：  user 用户打标 system 系统打标 spider 爬虫 nansen (具体外部平台名称)',
                               `name` varchar(32) DEFAULT NULL COMMENT '名称',
                               PRIMARY KEY (`id`),
                               KEY `idx_address` (`address`)
) ;

/******************************************/
/*   DatabaseName = service   */
/*   TableName = crawler_task   */
/******************************************/
CREATE TABLE `crawler_task` (
                                `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
                                `modifier` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '修改人',
                                `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                `deleted` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '是否删除',
                                `status` char(10) NOT NULL COMMENT '任务状态：Start|Running|Success|Failed',
                                `schedule_time` datetime DEFAULT NULL COMMENT '调度时间',
                                `processor` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务处理器名称',
                                `task_info` json DEFAULT NULL COMMENT '任务相关信息',
                                PRIMARY KEY (`id`)
) ;

/******************************************/
/*   DatabaseName = service   */
/*   TableName = tag   */
/******************************************/
CREATE TABLE `tag` (
                       `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                       `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
                       `modifier` varchar(64) DEFAULT NULL COMMENT '修改人',
                       `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                       `deleted` char(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
                       `name` varchar(32) DEFAULT NULL COMMENT '标签名称',
                       `category_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签分类 id',
                       `official` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '是否官方 0,1',
                       `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '标签描述',
                       PRIMARY KEY (`id`)
) ;

/******************************************/
/*   DatabaseName = service   */
/*   TableName = tag_category   */
/******************************************/
CREATE TABLE `tag_category` (
                                `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                `creator` varchar(64) NOT NULL COMMENT '创建人',
                                `modifier` varchar(64) DEFAULT NULL COMMENT '修改人',
                                `deleted` char(1) NOT NULL COMMENT '逻辑删除 0/1',
                                `name` varchar(32) DEFAULT NULL COMMENT '分类名称',
                                PRIMARY KEY (`id`)
) ;

/******************************************/
/*   DatabaseName = service   */
/*   TableName = t_tag_category_analysis   */
/******************************************/
CREATE TABLE `t_tag_category_analysis` (
                                           `name` varchar(32) DEFAULT NULL COMMENT '分类名称',
                                           `num` bigint(20) unsigned NOT NULL
) ;
