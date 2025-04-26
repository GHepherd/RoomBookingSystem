# 会议室预订系统接口设计

## 用户模块

### 1. 用户注册
- **路径**: `/api/users/register`
- **方法**: POST
- **请求参数**:
  
  ```json
  {
    "username": "string",   // 账号
    "password": "string",   // 密码
    "name": "string",       // 姓名
    "phone": "string",      // 联系电话
    "role": "customer",     // 角色(固定为customer,管理员和员工不开放注册系统)
    "company": "string"     // 所属公司(客户必填)
  }


  后端在插入用户后，要更新一条数据到管理员提醒审核
  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "注册成功，请等待管理员审核",
    "data": null
  }
  ```

### 2. 用户登录
- **路径**: `/api/users/login`
- **方法**: POST
- **请求参数**:
  ```json
  {
    "username": "string",   // 账号
    "password": "string"    // 密码
  }
  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "登录成功",
    "data": {
      "token": "string",    // 登录令牌
      "status": 1           // 状态(0-待审核, 1-正常, 2-冻结)
    }
  }
  ```

### 3. 获取用户信息
- **路径**: `/api/users/info`
- **方法**: GET
- **请求头**: 
  ```
  Authorization: Bearer {token}
  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "获取成功",
    "data": {
      "username": "string",
      "name": "string",
      "phone": "string",
      "role": "customer",
      "company": "string",   // 客户特有字段
      "status": 1,
      "balance":5000     //用户账户余额
      "createTime": "string"
    }
  }
  ```

### 4. 修改用户信息
- **路径**: `/api/users/update`
- **方法**: PUT
- **请求头**: 
  ```
  Authorization: Bearer {token}
  ```
- **请求参数**:
  ```json
  {
    "phone": "string",      // 联系电话(可选)
    "name": "string",       // 姓名(可选)
    "company": "string",    // 所属公司(可选，仅客户)
    "password": "string"    // 新密码(可选)
  }
  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "更新成功",
    "data": null
  }
  ```


  ### 5. 用户充值
- **路径**: `/api/users/recharge`
- **方法**: PUT
- **请求头**: 
  ```
  Authorization: Bearer {token}
  ```
- **请求参数**:
  ```json
  {
    "amount":100  //充值金额
  }
  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "充值成功",
    "data": null
  }
  ```



## 管理员模块

### 2. 用户列表
- **路径**: `/api/admin/users`
- **方法**: GET
- **请求头**: 
  ```
  Authorization: Bearer {adminToken}
  ```
- **请求参数**:
```json
  {
      page: 1               // 页码
      pageSize: 10          // 每页数量
      status: 0             // 状态(可选)    状态(0-待审核, 1-正常, 2-冻结   3-所有)
      keyword: "string"     // 关键字搜索(可选) 以username为准
  }
```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "获取成功",
    "data": {
      "total": 100,
      "list": [
        {
          "userId": 1,
          "username": "string",
          "name": "string",
          "phone": "string",
          "company": "string",
          "status": 0,
          "createTime": "string"
        }
      ]
    }
  }
  ```

### 3. 冻结/解冻用户
- **路径**: `/api/admin/users/{userId}/{status}`    0解冻   1冻结
- **方法**: PUT
- **请求头**: 
  ```
  Authorization: Bearer {adminToken}
  ```
- **请求参数**:

- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "操作成功",
    "data": null
  }
  ```

### 4. 创建员工
- **路径**: `/api/admin/staff`
- **方法**: POST
- **请求头**: 
  ```
  Authorization: Bearer {adminToken}
  ```
- **请求参数**:
  
  ```json
  {
    "username": "string",   // 账号
    "password": "string",   // 密码
    "name": "string",       // 姓名
    "phone": "string" ,      // 联系电话
    "role": "staff"
  }
  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "创建成功",
    "data": null
  }
  ```


### 5. 创建会议室
- **路径**: `/api/admin/room`
- **方法**: POST
- **请求头**: 
  ```
  Authorization: Bearer {adminToken}
  ```
- **请求参数**:
  ```json
  {
    "name": "string",        // 会议室名称
    "type": "classroom",     // 类型(classroom-教室型, round-圆桌型)
    "capacity": 20,          // 座位数
    "hasProjector": true,    // 是否有投影仪   0无 1有
    "hasSound": true,        // 是否有音响
    "hasNetwork": true,      // 是否有网络
    "price": 100,            // 每小时价格
    "description": "string", // 描述
    "area": 50               // 面积(平方米)
    "status": 0              // 状态(0 free-空闲,1 locked-锁定,2 booked-已预订,3 occupied-使用中, 4 maintenance-维护)
    "startTime":8
    "endTime":23
  }
  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "创建成功",
    "data": null
  }
  ```

### 2. 修改会议室
- **路径**: `/api/admin/rooms/{roomId}`
- **方法**: PUT
- **请求头**: 
  ```
  Authorization: Bearer {adminToken}
  ```
- **请求参数**:
  ```json
  {
    "name": "string",        // 会议室名称
    "type": "classroom",     // 类型(classroom-教室型, round-圆桌型)
    "capacity": 20,          // 座位数
    "hasProjector": true,    // 是否有投影仪   0无 1有
    "hasSound": true,        // 是否有音响
    "hasNetwork": true,      // 是否有网络
    "price": 100,            // 每小时价格
    "description": "string", // 描述
    "area": 50               // 面积(平方米)
    "status": 0              // 0空闲   1 被锁定  2 被预定  3正在使用   4维护
    "startTime":8
    "endTime":23
  }
  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "更新成功",
    "data": null
  }
  ```

### 3. 删除会议室
- **路径**: `/api/admin/rooms/{roomId}`
- **方法**: DELETE
- **请求头**: 
  ```
  Authorization: Bearer {adminToken}
  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "删除成功",
    "data": null
  }
  ```

### 4. 获取会议室列表(管理员)
- **路径**: `/api/admin/rooms`
- **方法**: GET
- **请求头**: 
  ```
  Authorization: Bearer {adminToken}
  ```
- **请求参数**:
  ```json
  {
      page: 1               // 页码
      pageSize: 10          // 每页数量
      keyword: "string"     // 关键字搜索(可选)   会议室名称
  }
   
  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "获取成功",
    "data": {
      "total": 10,
      "list": [
        {
          "roomId": 1,
          "name": "string",
          "type": "classroom",
          "capacity": 20,
          "hasProjector": true,
          "hasSound": true,
          "hasNetwork": true,
          "price": 100,
          "description": "string",
          "area": 50,
          "status": 0,    // 0空闲   1 被锁定  2 被预定  3正在使用   4维护
           "startTime":8
          "endTime":23
        }
      ]
    }
  }
  ```







## 员工操作模块

### 1. 会议室状态管理
- **路径**: `/api/staff/rooms/{roomId}/status`
- **方法**: PUT
- **请求头**: 
  ```
  Authorization: Bearer {staffToken}
  ```
- **请求参数**:
  ```json
  {
    "status": "free",      // 状态(free-空闲, occupied-使用中, maintenance-维护)
  }
  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "更新成功",
    "data": null
  }
  ```

### 2. 获取全部会议室状态
- **路径**: `/api/staff/rooms/status`
- **方法**: GET
- **请求头**: 
  ```
  Authorization: Bearer {staffToken}
  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "获取成功",
    "data": [
      {
        "roomId": 1,
        "name": "string",
        "status": "free",          // 状态(free-空闲, occupied-使用中, maintenance-维护)
      }
    ]
  }
  ```

### 3. 处理退订申请
- **路径**: `/api/staff/cancellations/{cancellationId}/process`
- **方法**: POST
- **请求头**: 
  ```
  Authorization: Bearer {staffToken}
  ```
- **请求参数**:

- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "处理成功",
    "data": null
  }
  ```

### 4. 获取退订申请列表
- **路径**: `/api/staff/cancellations`
- **方法**: GET
- **请求头**: 
  ```
  Authorization: Bearer {staffToken}
  ```
- **请求参数**:
  ```
  page: 1               // 页码
  pageSize: 10          // 每页数量
  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "获取成功",
    "data": {
      "total": 10,
      "list": [
        {
          "cancellationId": 1,
          "bookingId": 2,
          "payId":2,
          "roomId": 3,
          "roomName": "string",
          "userId": 4,
          "userName": "string",
          "startTime": "string",
          "endTime": "string",
          "totalAmount": 100,
          "applyTime": "string",
          "refundAmount": 75.5
        }
      ]
    }
  }
  ```












## 预订模块

### 1. 根据条件获取会议室列表    ✓
- **路径**: `/api/booking/rooms`
- **方法**: Get
  **请求头**: 
  
  ```
  Authorization: Bearer {userToken}
  ```
- **请求参数**:   json
  ```
  page: 1               // 页码
  pageSize: 10          // 每页数量
  bookDay:date          //预约日期  年月日 具体到日
  startTime: 8		//起始时间 int类型
  endTime: 9
  
  capacity: 20          // 最小容纳人数(可选)
  hasProjector: true    // 是否有投影仪(可选)
  hasSound: true        // 是否有音响(可选)
  hasNetwork: true      // 是否有网络(可选)
  type: "classroom"     // 类型(可选)
  keyword: "string"     // 关键字搜索(可选)    房间名
   
  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "获取成功",
    "data": {
      "total": 10,
      "list": [
        {
          "roomId": 1,
          "name": "string",
          "type": "classroom",
          "capacity": 20,
          "hasProjector": true,
          "hasSound": true,
          "hasNetwork": true,
          "price": 100,
          "description": "string",
          "area": 50,
          "status": 0
          bookDay:date          //预约日期  年月日 具体到日
          startTime: date
         endTime:date
        }
      ]
    }
  }
  ```




### 2. 提交预约请求 ✓      用redis上锁   lock:roomId:Date(具体到日)  :  bitmap     成功预约后需要生成订单信息插入数据库。   lock：订单号： 
- **路径**: `/api/book/{roomId}`
- **方法**: POST
  **请求头**: 
  ```
  Authorization: Bearer {userToken}
  ```
  - **请求参数**:   json
  ```
  bookDay:date          //预约日期  年月日 具体到日
  startTime: date
  endTime:date


  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "成功",
    "data": null
  }
  ```




### 6. 获取我的成功预订列表      ✓
- **路径**: `/api/booking/bookings`
- **方法**: GET
- **请求头**: 
  ```
  Authorization: Bearer {token}
  ```
- **请求参数**:
  ```json
  page: 1               // 页码
  pageSize: 10          // 每页数量
  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "获取成功",
    "data": {
      "total": 10,
      "list": [
        {
          "bookingId": 1,
          "roomId": 2,
          "roomName": "string",
          "date": "2023-08-01",
          "startTime": "string",
          "endTime": "string",
          "totalAmount": 200,
          "status": "upcoming",     // upcoming, ongoing, completed,toBeReviewd
        }
      ]
    }
  }
  ```


### 8. 申请取消预订   ✓  在会议开始前的24h内不能取消，24-48h内退25%  48-72退75%   提前72小时退全款    推到员工处
- **路径**: `/api/booking/{bookingId}/cancel`
- **方法**: POST
- **请求头**: 
  ```
  Authorization: Bearer {token}
  ```
- **请求参数**:

- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "申请提交成功，等待审核",
    "data": {
      "cancellationId": 1,
      "estimatedRefund": 75.5,  // 预估退款金额
    }
  }
  ```




  ## 订单模块


  ### 5. 支付订单    order表   booking表
- **路径**: `/api/order/{orderId}`
- **方法**: POST
- **请求头**: 
  ```
  Authorization: Bearer {usertoken}
  ```
- **请求参数**:

- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "支付成功",
    "data": null
  }
  ```
   ```json
  {
    "code": 200,
    "message": "支付失败",
    "data": null
  }
   ```

 ### 5. 未支付且取消订单      order表
- **路径**: `/api/order/{orderId}`
- **方法**: POST
- **请求头**: 
  ```
  Authorization: Bearer {usertoken}
  ```
- **请求参数**:

- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "取消成功",
    "data": null
  }
  


### 6. 获取订单列表      ✓      order表
- **路径**: `/api/order/list`
- **方法**: GET
- **请求头**: 
  ```
  Authorization: Bearer {token}
  ```
- **请求参数**:
  ```json
  page: 1               // 页码
  pageSize: 10          // 每页数量
  ```
- **返回参数**:
  ```json
  {
    "code": 200,
    "message": "获取成功",
    "data": {
      "total": 10,
      "list": [
        {
          "orderId": 1,
          "roomName": "string",
          "date": "2023-08-01",
          "startTime": "string",
          "endTime": "string",
          "totalAmount": 200,
          "status": 0,     // 1 paid  2 cancled  0 unpaid
        }
      ]
    }
  }
  ```