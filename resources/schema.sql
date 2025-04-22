-- 会议室预订系统数据库设计
create database roombooking
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 用户表
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(100) NOT NULL COMMENT '密码(加密)',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `phone` varchar(20) NOT NULL COMMENT '联系电话',
  `company` varchar(100) DEFAULT NULL COMMENT '所属公司(客户)',
  `role` varchar(20) NOT NULL COMMENT '角色(admin-管理员,staff-员工,customer-客户)',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态(0-待审核,1-正常,2-冻结)',
  `balance` decimal(10,2) NOT NULL DEFAULT '0' COMMENT '余额',
  `isDeleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';



-- 会议室表
CREATE TABLE `room` (
  `roomId` int(11) NOT NULL AUTO_INCREMENT COMMENT '会议室ID',
  `name` varchar(50) NOT NULL COMMENT '会议室名称',
  `type` varchar(20) NOT NULL COMMENT '类型(classroom-教室型,round-圆桌型)',
  `capacity` int(11) NOT NULL COMMENT '座位数',
  `area` decimal(10,2) NOT NULL COMMENT '面积(平方米)',
  `has_projector` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有投影仪(0-无,1-有)',
  `has_sound` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有音响(0-无,1-有)',
  `has_network` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有网络(0-无,1-有)',
  `price` decimal(10,2) NOT NULL COMMENT '每小时价格',
  `description` text COMMENT '描述',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态(free-空闲,occupied-使用中,maintenance-维护)',
  `isDeleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0-未删除,1-已删除)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`roomId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会议室表';



-- 预订表
CREATE TABLE `booking` (
  `bookingId` int(11) NOT NULL AUTO_INCREMENT COMMENT '预订ID',
  `userId` int(11) NOT NULL COMMENT '用户ID',
  `roomId` int(11) NOT NULL COMMENT '会议室ID',
  `date` date NOT NULL COMMENT '预订日期',
  `start_hour` int(11) NOT NULL COMMENT '开始小时(8-21)',
  `end_hour` int(11) NOT NULL COMMENT '结束小时(8-21)',
  `total_amount` decimal(10,2) NOT NULL COMMENT '总金额',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态(toBeReviewd-待审核)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`bookingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预订表';

-- 取消预订申请表
CREATE TABLE `cancellation` (
  `cancellationId` int(11) NOT NULL AUTO_INCREMENT COMMENT '取消ID',
  `booking_id` int(11) NOT NULL COMMENT '预订ID',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态(0-待处理,1-已同意)',
  `refund_amount` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `processor_id` int(11) DEFAULT NULL COMMENT '处理人ID',
  PRIMARY KEY (`cancellationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='取消预订申请表';

-- 支付记录表
CREATE TABLE `order` (
  `orderId` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bookingId` int(11) NOT NULL COMMENT '预订ID',
  `userId` int(11) NOT NULL COMMENT '用户ID',
  `totalAmount` decimal(10,2) NOT NULL COMMENT '支付金额',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态(0-未支付,1-已支付,2-已退款)',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付记录表';



