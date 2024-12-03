Campus Connect Backend
This is the backend implementation for the Campus Connect project. It provides a robust RESTful API for product management, cart operations, and user authentication, forming the core of the Campus Connect platform.

Overview
The backend is built with Spring Boot and facilitates communication with the front-end to handle the following features:

Product Management:
Retrieve and update product information.
Cart Operations:
Save and manage user cart summaries.
User Authentication:
OTP-based user verification.
Login functionality.
Features
Product Management
Fetch products by category.
Update product quantities.
Cart Management
Save and retrieve cart summaries for users.
User Authentication
OTP generation and verification for secure access.
User login with email and password.
Email Notifications
OTPs sent via email using Spring's JavaMailSender.
API Endpoints
Product Management
1. Get Products by Category
GET /api/product/4
Retrieves all products in category ID 4.

Response:

200 OK: Returns the list of products.
404 Not Found: No products found.
2. Update Product Quantity
PUT /api/product/{id}
Updates the quantity of a specific product.

Request Body:

json
Copy code
{
  "quantity_select": 10
}
Response:

200 OK: Quantity updated successfully.
404 Not Found: Product not found.
400 Bad Request: Invalid data.
Cart Management
Save Cart Summary
POST /api/save-cart-summary
Saves a cart summary for a specific user.

Request Body:

json
Copy code
{
  "userId": "123",
  "cartSummary": "Details of the cart"
}
Response:

200 OK: Cart summary saved successfully.
400 Bad Request: Missing or invalid data.
User Authentication
1. Send OTP
POST /api/send-otp
Generates and sends an OTP to the user's email.

Request Body:

json
Copy code
{
  "email": "user@example.com"
}
Response:

200 OK: OTP sent successfully.
500 Internal Server Error: Failed to send OTP.
2. Login
POST /api/login
Authenticates a user based on email and password.

Request Body:

json
Copy code
{
  "username": "user@example.com",
  "password": "password123"
}
Response:

200 OK: Login successful.
401 Unauthorized: Invalid email or password.
3. Verify OTP
POST /api/verify-otp
Verifies the OTP sent to the user's email.

Request Body:

json
Copy code
{
  "email": "user@example.com",
  "otp": "123456"
}
Response:

200 OK: OTP verified successfully.
400 Bad Request: Invalid OTP or missing data.
Project Structure
bash
Copy code
src/
├── main/
│   ├── java/
│   │   └── ai/verse/
│   │       ├── repo/            # Repository interfaces
│   │       ├── entity/          # Entity classes
│   │       ├── controller/      # REST API controllers
│   │       └── service/         # Business logic
│   └── resources/
│       └── application.properties # Configuration
Prerequisites
Dependencies:

Spring Boot Starter Web
Spring Boot Starter Data JPA
Spring Boot Starter Mail
Database Configuration:

Set up your database in application.properties:
properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/campus_connect
spring.datasource.username=root
spring.datasource.password=your_password
Email Configuration:

Configure email properties for OTP:
properties
Copy code
spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=your-email@example.com
spring.mail.password=your-password
How to Run
Clone the repository:
bash
Copy code
git clone https://github.com/LaavKush/campus-connect.git
Navigate to the backend directory.
Configure the database and email settings in application.properties.
Build and run the application:
bash
Copy code
mvn spring-boot:run
Access the API at http://localhost:8080/api.
Contributing
We welcome contributions! To contribute:

Fork the repository.
Create a feature branch.
Commit and push your changes.
Open a pull request for review.
