# Microservices-based Store App
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/hoffmann-g/shopping-app/blob/main/LICENSE)

# About
This project is a microservices-architected store app developed with a REST API developed using Spring Boot. It is designed to provide a comprehensive shopping experience where customers can:
- Browse a list of products
- Login and register with token based authentication
- Manage products in their respective cart
- Apply discount coupons
- Place orders (payment not implemented)

Each service in this application is isolated and contains its own business logic, ensuring modularity and scalability.

# Architecture
The application follows a microservices architecture, where each service is responsible for a specific part of the business logic. The services communicate with each other to fulfill the overall functionality of the application.

- **API Gateway:** Centralizes routing for all services.
- **Cart Service:** Manages cart items, prices, and applies coupons.
- **Coupon Service:** Handles creation and management of discount coupons.
- **Customer Service:** Manages customer data (excluding authentication data).
- **Discovery Server:** Enables service registry and discovery.
- **Order Service:** Processes and retrieves orders.
- **Payment Service:** Receives order placement messages (logic pending).
- **Product Service:** Manages product lifecycle (create, update, delete).
- **Security Service:** Manages user authentication and token validation.
- **Stock Service:** Oversees product stock management.

<img src="https://github.com/user-attachments/assets/64f24408-180e-47c9-9fc1-7d22abb4f575" width="750">

Each service REST API is divided into public and private controllers where the public are for user acccess, and private for inter-service communication requirements.

# Technologies used
- Java
- Spring Boot
- Spring Cloud
- Spring Security
- Netflix Eureka
- Apache Kafka
- Resilience4J
- JWT
- OpenFeign
- JPA / Hibernate
- MySQL
- mongoDB
- MockMVC
- JUnit
- Docker

# How to Run
To run this project locally, ensure that you have Docker installed. Clone the repository, go to the 'backend' folder and run the following command:

```
# inicialize project in containers
docker compose up
```
(The gateway is located in the port 8060 by default)

# Contributing
Contributions are welcome! If you have suggestions or find issues, feel free to create a pull request or open an issue.

# Author
Guilherme Hoffmann Batistti dos Santos

[LinkedIn Profile](https://www.linkedin.com/in/hoffmann-g/)
