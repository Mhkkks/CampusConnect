# Campus Connect Backend

This is the backend implementation for the **[Campus Connect](https://github.com/LaavKush/campus-connect)** project. It provides a robust RESTful API for product management, cart operations, and user authentication, forming the core of the Campus Connect platform.

---

## Overview

The backend is built with **Spring Boot** and facilitates communication with the front-end to handle the following features:

- **Product Management**: Retrieve and update product information.
- **Cart Operations**: Save and manage user cart summaries.
- **User Authentication**: OTP-based user verification and login functionality.

---

## Features

### Product Management
- Fetch products by category.
- Update product quantities.

### Cart Management
- Save and retrieve cart summaries for users.

### User Authentication
- OTP generation and verification for secure access.
- User login with email and password.

### Email Notifications
- OTPs sent via email using Spring's `JavaMailSender`.

---

## API Endpoints

### **Product Management**

#### 1. Get Products by Category
**Endpoint**: `GET /api/product/4`  
Retrieves all products in category ID 4.

**Response**:  
- `200 OK`: Returns the list of products.  
- `404 Not Found`: No products found.

---

#### 2. Update Product Quantity
**Endpoint**: `PUT /api/product/{id}`  
Updates the quantity of a specific product.

**Request Body**:
```json
{
  "quantity_select": 10
}
