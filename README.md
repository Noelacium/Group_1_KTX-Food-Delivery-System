
# 🍱 KTX Food Delivery System

Hệ thống đặt và quản lý đồ ăn dành cho sinh viên trong khu Ký túc xá (KTX). Dự án được xây dựng theo kiến trúc **client-server** tách biệt: **backend** bằng Java Spring Boot và **frontend** bằng React (Vite).

## 📖 Giới thiệu

**KTX Food Delivery System** giúp sinh viên trong ký túc xá:
- Đăng nhập / xác thực tài khoản (sinh viên & quản trị viên)
- Xem thực đơn (menu) món ăn
- Thêm món vào giỏ hàng và đặt hàng
- Thanh toán bằng nhiều hình thức: tiền mặt, chuyển khoản ngân hàng, hoặc ví điện tử nội bộ
- Theo dõi trạng thái đơn hàng
- Quản trị viên (Admin) quản lý danh sách món ăn và đơn hàng

## 🏗️ Cấu trúc dự án

```
KTX-Food-Delivery-System/
├── backend/                          # Java Spring Boot API
│   ├── src/main/java/com/ktxfood/
│   │   ├── config/                   # Cấu hình (CORS, khởi tạo dữ liệu admin mẫu)
│   │   ├── controller/               # REST controllers (Auth, Food, Order, Payment, Student)
│   │   ├── dto/                      # Request/Response DTOs
│   │   ├── exception/                # Custom exceptions (giỏ hàng rỗng, hết hàng, sai thông tin đăng nhập...)
│   │   ├── handler/                  # Global exception handler
│   │   ├── model/                    # Entity/model (Admin, Student, Food, Cart, Order...)
│   │   ├── payment/                  # Chiến lược thanh toán (Cash, Bank Transfer, Wallet)
│   │   ├── repository/               # Tầng truy xuất dữ liệu
│   │   ├── service/                  # Business logic
│   │   ├── utils/                    # Tiện ích (sinh ID, mã hoá mật khẩu, validate)
│   │   └── KtxFoodApplication.java   # Entry point
│   ├── src/main/resources/
│   │   ├── data/                     # Dữ liệu lưu dạng JSON (admins, foods, orders, students)
│   │   └── application.properties
│   └── pom.xml
│
├── frontend/                         # React + Vite
│   ├── src/
│   │   ├── components/               # CartItem, FoodCard, Navbar, ProtectedRoute
│   │   ├── hooks/                    # useCart.js
│   │   ├── pages/                    # HomePage, MenuPage, CartPage, OrderPage, AuthPage, AdminFoodPage
│   │   ├── services/                 # api.js (gọi API backend)
│   │   ├── styles/                   # main.css
│   │   ├── App.jsx
│   │   └── main.jsx
│   ├── index.html
│   ├── vite.config.js
│   └── package.json
│
└── README.md
```

## 🛠️ Công nghệ sử dụng

| Thành phần | Công nghệ |
|---|---|
| Backend | Java, Spring Boot, Maven |
| Frontend | React, Vite |
| Lưu trữ dữ liệu | File JSON (`admins.json`, `foods.json`, `orders.json`, `students.json`) |
| Kiến trúc thanh toán | Strategy pattern — `CashPayment`, `BankTransferPayment`, `WalletPayment` |
| Xử lý lỗi | `GlobalExceptionHandler` với các exception nghiệp vụ tuỳ biến |

## ✨ Tính năng chính

- 🔐 **Xác thực**: Đăng nhập cho Admin (`AdminAuthController`) và Sinh viên (`StudentController`)
- 🍔 **Quản lý món ăn**: CRUD món ăn qua `FoodController` (dành cho Admin)
- 🛒 **Giỏ hàng & đặt hàng**: Thêm/xoá món, đặt hàng qua `OrderController`
- 💳 **Thanh toán đa hình thức**: Tiền mặt, chuyển khoản, ví điện tử qua `PaymentController`
- ⚠️ **Xử lý lỗi nghiệp vụ**: Giỏ hàng rỗng, hết hàng, số dư không đủ, số lượng không hợp lệ, sai thông tin đăng nhập
- 🧭 **Route bảo vệ phía frontend**: `ProtectedRoute` giới hạn truy cập trang theo vai trò đăng nhập

## 🚀 Cài đặt và chạy dự án

### Yêu cầu
- JDK 17+ (hoặc phiên bản phù hợp với Spring Boot đang dùng)
- Maven
- Node.js (khuyến nghị v18+) và npm

### 1. Clone dự án

```bash
git clone https://github.com/khanh-bitt/KTX-Food-Delivery-System.git
cd KTX-Food-Delivery-System
```

### 2. Chạy Backend (Spring Boot)

```bash
cd backend
mvn spring-boot:run
```

Backend mặc định sẽ chạy trên cổng được cấu hình trong `src/main/resources/application.properties` (ví dụ `http://localhost:8080`).

> Dữ liệu mẫu (`admins.json`, `foods.json`, `orders.json`, `students.json`) nằm trong `src/main/resources/data/` và được khởi tạo qua `AdminSeeder` khi ứng dụng chạy lần đầu.

### 3. Chạy Frontend (React + Vite)

```bash
cd frontend
npm install
npm run dev
```

Frontend mặc định sẽ chạy trên `http://localhost:5173` (Vite). Đảm bảo `src/services/api.js` trỏ đúng đến địa chỉ backend.

## ⚙️ Cấu hình

Kiểm tra và chỉnh sửa `backend/src/main/resources/application.properties` để cấu hình cổng chạy, CORS (`CorsConfig.java`), hoặc các thông số khác nếu cần.

## 🤝 Đóng góp

1. Fork dự án
2. Tạo nhánh mới (`git checkout -b feature/ten-tinh-nang`)
3. Commit thay đổi (`git commit -m 'Thêm tính năng...'`)
4. Push lên nhánh (`git push origin feature/ten-tinh-nang`)
5. Tạo Pull Request

## 📄 Giấy phép

Dự án hiện chưa có giấy phép cụ thể. Thêm file `LICENSE` nếu bạn muốn công khai điều khoản sử dụng.

## 👤 Tác giả

=======
# 🍱 KTX Food Delivery System

Hệ thống đặt và quản lý đồ ăn dành cho sinh viên trong khu Ký túc xá (KTX). Dự án được xây dựng theo kiến trúc **client-server** tách biệt: **backend** bằng Java Spring Boot và **frontend** bằng React (Vite).

## 📖 Giới thiệu

**KTX Food Delivery System** giúp sinh viên trong ký túc xá:
- Đăng nhập / xác thực tài khoản (sinh viên & quản trị viên)
- Xem thực đơn (menu) món ăn
- Thêm món vào giỏ hàng và đặt hàng
- Thanh toán bằng nhiều hình thức: tiền mặt, chuyển khoản ngân hàng, hoặc ví điện tử nội bộ
- Theo dõi trạng thái đơn hàng
- Quản trị viên (Admin) quản lý danh sách món ăn và đơn hàng

## 🏗️ Cấu trúc dự án

```
KTX-Food-Delivery-System/
├── backend/                          # Java Spring Boot API
│   ├── src/main/java/com/ktxfood/
│   │   ├── config/                   # Cấu hình (CORS, khởi tạo dữ liệu admin mẫu)
│   │   ├── controller/               # REST controllers (Auth, Food, Order, Payment, Student)
│   │   ├── dto/                      # Request/Response DTOs
│   │   ├── exception/                # Custom exceptions (giỏ hàng rỗng, hết hàng, sai thông tin đăng nhập...)
│   │   ├── handler/                  # Global exception handler
│   │   ├── model/                    # Entity/model (Admin, Student, Food, Cart, Order...)
│   │   ├── payment/                  # Chiến lược thanh toán (Cash, Bank Transfer, Wallet)
│   │   ├── repository/               # Tầng truy xuất dữ liệu
│   │   ├── service/                  # Business logic
│   │   ├── utils/                    # Tiện ích (sinh ID, mã hoá mật khẩu, validate)
│   │   └── KtxFoodApplication.java   # Entry point
│   ├── src/main/resources/
│   │   ├── data/                     # Dữ liệu lưu dạng JSON (admins, foods, orders, students)
│   │   └── application.properties
│   └── pom.xml
│
├── frontend/                         # React + Vite
│   ├── src/
│   │   ├── components/               # CartItem, FoodCard, Navbar, ProtectedRoute
│   │   ├── hooks/                    # useCart.js
│   │   ├── pages/                    # HomePage, MenuPage, CartPage, OrderPage, AuthPage, AdminFoodPage
│   │   ├── services/                 # api.js (gọi API backend)
│   │   ├── styles/                   # main.css
│   │   ├── App.jsx
│   │   └── main.jsx
│   ├── index.html
│   ├── vite.config.js
│   └── package.json
│
└── README.md
```

## 🛠️ Công nghệ sử dụng

| Thành phần | Công nghệ |
|---|---|
| Backend | Java, Spring Boot, Maven |
| Frontend | React, Vite |
| Lưu trữ dữ liệu | File JSON (`admins.json`, `foods.json`, `orders.json`, `students.json`) |
| Kiến trúc thanh toán | Strategy pattern — `CashPayment`, `BankTransferPayment`, `WalletPayment` |
| Xử lý lỗi | `GlobalExceptionHandler` với các exception nghiệp vụ tuỳ biến |

## ✨ Tính năng chính

- 🔐 **Xác thực**: Đăng nhập cho Admin (`AdminAuthController`) và Sinh viên (`StudentController`)
- 🍔 **Quản lý món ăn**: CRUD món ăn qua `FoodController` (dành cho Admin)
- 🛒 **Giỏ hàng & đặt hàng**: Thêm/xoá món, đặt hàng qua `OrderController`
- 💳 **Thanh toán đa hình thức**: Tiền mặt, chuyển khoản, ví điện tử qua `PaymentController`
- ⚠️ **Xử lý lỗi nghiệp vụ**: Giỏ hàng rỗng, hết hàng, số dư không đủ, số lượng không hợp lệ, sai thông tin đăng nhập
- 🧭 **Route bảo vệ phía frontend**: `ProtectedRoute` giới hạn truy cập trang theo vai trò đăng nhập

## 🚀 Cài đặt và chạy dự án

### Yêu cầu
- JDK 17+ (hoặc phiên bản phù hợp với Spring Boot đang dùng)
- Maven
- Node.js (khuyến nghị v18+) và npm

### 1. Clone dự án

```bash
git clone https://github.com/khanh-bitt/KTX-Food-Delivery-System.git
cd KTX-Food-Delivery-System
```

### 2. Chạy Backend (Spring Boot)

```bash
cd backend
mvn spring-boot:run
```

Backend mặc định sẽ chạy trên cổng được cấu hình trong `src/main/resources/application.properties` (ví dụ `http://localhost:8080`).

> Dữ liệu mẫu (`admins.json`, `foods.json`, `orders.json`, `students.json`) nằm trong `src/main/resources/data/` và được khởi tạo qua `AdminSeeder` khi ứng dụng chạy lần đầu.

### 3. Chạy Frontend (React + Vite)

```bash
cd frontend
npm install
npm run dev
```

Frontend mặc định sẽ chạy trên `http://localhost:5173` (Vite). Đảm bảo `src/services/api.js` trỏ đúng đến địa chỉ backend.

## ⚙️ Cấu hình

Kiểm tra và chỉnh sửa `backend/src/main/resources/application.properties` để cấu hình cổng chạy, CORS (`CorsConfig.java`), hoặc các thông số khác nếu cần.
