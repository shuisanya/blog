CREATE TABLE `blog_with_label`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `label_code` varchar(50) NOT NULL COMMENT '标签id',
  `blog_code` varchar(50) NOT NULL COMMENT '文章id',
  PRIMARY KEY (`id`)
);

CREATE TABLE `t_blog`  (
  `title` varchar(50) NOT NULL COMMENT '标题',
  `code` varchar(50) NOT NULL COMMENT '唯一id',
  `content` text NOT NULL COMMENT '内容',
  `img` varchar(100) NULL COMMENT '首图',
  `read_num` int NULL COMMENT '浏览次数',
  `recommend_num` int NULL COMMENT '推荐次数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最近更新时间',
  `comment_is` int NULL COMMENT '评论是否开启',
  `release` int NOT NULL COMMENT '是否发布',
  `user_code` varchar(50) NOT NULL COMMENT '发布人',
  `classify_code` varchar(50) NOT NULL COMMENT '类型id',
  PRIMARY KEY (`code`)
);

CREATE TABLE `t_classify`  (
  `classify_code` varchar(50) NOT NULL COMMENT '类型id',
  `classify_name` varchar(100) NOT NULL COMMENT '类型名称',
  `classify_create_time` datetime NULL COMMENT '类型创建时间',
  `classify_update_time` datetime NULL COMMENT '类型更新时间',
  PRIMARY KEY (`classify_code`)
);

CREATE TABLE `t_comment`  (
  `comment_code` varchar(50) NOT NULL COMMENT '评论的id',
  `comment_name` varchar(255) NULL COMMENT '评论名称',
  `comment_content` varchar(1000) NOT NULL COMMENT '评论内容',
  `comment_time` datetime NOT NULL COMMENT '评论时间',
  `blog_code` varchar(50) NOT NULL COMMENT '评论的博客',
  PRIMARY KEY (`comment_code`)
);

CREATE TABLE `t_label`  (
  `label_code` varchar(50) NOT NULL COMMENT '标签id',
  `label_name` varchar(100) NOT NULL COMMENT '标签名称',
  `label_create_time` datetime NULL COMMENT '标签的创建时间',
  `label_update_time` datetime NULL COMMENT '标签的更新时间',
  PRIMARY KEY (`label_code`)
);

CREATE TABLE `t_user`  (
  `user_code` varchar(50) NOT NULL COMMENT '用户id',
  `user_name` varchar(50) NOT NULL COMMENT '用户姓名',
  `user_password` varchar(50) NOT NULL COMMENT '用户密码',
  `user_email` varchar(50) NULL COMMENT '用户邮箱',
  `img` varchar(100) NULL COMMENT '用户头像',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_code`)
);

ALTER TABLE `blog_with_label` ADD CONSTRAINT `blog_code` FOREIGN KEY (`blog_code`) REFERENCES `t_blog` (`code`);
ALTER TABLE `blog_with_label` ADD CONSTRAINT `label_code` FOREIGN KEY (`label_code`) REFERENCES `t_label` (`label_code`);
ALTER TABLE `t_blog` ADD CONSTRAINT `user_code` FOREIGN KEY (`user_code`) REFERENCES `t_user` (`user_code`);
ALTER TABLE `t_blog` ADD CONSTRAINT `classify_code` FOREIGN KEY (`classify_code`) REFERENCES `t_classify` (`classify_code`);
ALTER TABLE `t_comment` ADD CONSTRAINT `blog_code` FOREIGN KEY (`blog_code`) REFERENCES `t_blog` (`code`);

