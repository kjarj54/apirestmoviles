# EV Charging Station Network API

A Spring Boot-based REST API backend for an electric vehicle charging station network application. This API supports route planning, charging station management, and community features for EV owners.

## Features

### 🚗 Route Planning
- Calculate optimal routes between locations
- Find charging stations along routes
- Filter by charger types and compatibility

### 🔌 Charging Station Management
- Real-time station availability
- Detailed station information (prices, plug types, power output)
- Station ratings and reviews

### 👥 Community Features
- User authentication and profiles
- Forum posts and comments
- Local group management
- Station reviews and ratings

### 📊 Cost & Environmental Impact
- Trip cost calculations
- CO₂ emission savings estimates
- Comparison with fossil fuel vehicles

### 🔔 Notification System
- Charging reminders
- New station alerts
- Special offers and promotions

## Technical Stack

- Java 21
- Spring Boot 3.4.3
- PostgreSQL Database
- Spring Security + JWT Authentication
- OpenAPI Documentation (Swagger)

## Prerequisites

- JDK 21
- Maven 3.9.9+
- PostgreSQL 15+

## Environment Variables

```properties
DB_HOST=your_database_host
DB_NAME=your_database_name
DB_USER=your_database_user
DB_PASSWORD=your_database_password
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=3600000
```

## Getting Started

1. Clone the repository:
```bash
git clone https://github.com/kjarj54/apirestmoviles.git
```

2. Set up environment variables

3. Build the project:
```bash
./mvnw clean install
```

4. Run the application:
```bash
./mvnw spring-boot:run
```

The API will be available at http://localhost:8080

## API Documentation

Once the application is running, you can access the Swagger UI documentation at:
http://localhost:8080/swagger-ui.html

## Database Schema

- Users
- Charging Stations
- Reviews
- Forum Posts
- Local Groups
- Trip Plans
- Notifications

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

Kevin A 
Anthony A

Project Link: https://github.com/kjarj54/apirestmoviles