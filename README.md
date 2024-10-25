# 🌦️ Weather Monitoring System 



## Live Demo

You can see the live demo of the application at the following link:
[Live Demo](https://babureddynr.github.io/spring-html-pages/whetherapp.html)

## Screenshots

Here are some screenshots of the application:

### Home Page
![Home Page](https://your-screenshot-link.com/homepage.png)
## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [System Requirements](#system-requirements)
- [Installation & Setup](#installation--setup)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [Screenshots](#screenshots)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## 🎯 Overview
Real-time weather monitoring system that tracks weather conditions across major Indian cities. The system collects data from OpenWeatherMap API, processes it, and provides insights through daily summaries and alerts.

## ✨ Features
- 🔄 Real-time weather data collection
- 📊 Daily weather summaries
- ⚡ Automated alerts for extreme conditions
- 🌡️ Temperature conversion (Kelvin to Celsius)
- 📍 Covers 6 major Indian cities
  - Delhi
  - Mumbai
  - Chennai
  - Bangalore
  - Kolkata
  - Hyderabad

## 🛠️ Technologies Used
- 🎯 Java 17
- 🍃 Spring Boot 3.2.0
- 🗄️ MySQL Database
- 📦 Maven
- 🔧 Lombok
- 🌐 OpenWeatherMap API

## 💻 System Requirements
- JDK 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher
- Spring Tool Suite 4 or IntelliJ IDEA
- Git

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/weather-monitoring-system.git
cd weather-monitoring-system
```

### 2. Database Setup
```sql
CREATE DATABASE weather_monitor;
```

### 3. Configure Application Properties
Create `application.properties` in `src/main/resources`:
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/weather_monitor
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# OpenWeatherMap API Configuration
openweathermap.api.key=your_api_key

# Server Configuration
server.port=8080
```

### 4. Build & Run
```bash
mvn clean install
mvn spring-boot:run
```

## 📁 Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/weather/monitor/
│   │       ├── controller/
│   │       │   └── WeatherController.java
│   │       ├── entity/
│   │       │   ├── WeatherData.java
│   │       │   ├── DailySummary.java
│   │       │   └── WeatherAlert.java
│   │       ├── repository/
│   │       │   ├── WeatherDataRepository.java
│   │       │   ├── DailySummaryRepository.java
│   │       │   └── WeatherAlertRepository.java
│   │       ├── service/
│   │       │   └── WeatherService.java
│   │       └── WeatherMonitorApplication.java
│   └── resources/
│       └── application.properties
```

## 🌐 API Endpoints

### Get Current Weather
```http
GET /api/weather/current/{city}
```

### Get Daily Summaries
```http
GET /api/weather/summary/{city}
```

### Get Weather Alerts
```http
GET /api/weather/alerts/{city}
```

### Get All Cities
```http
GET /api/weather/cities
```

## 📸 Screenshots

### Current Weather Dashboard
![Weather Dashboard](screenshot_placeholder.png)
*Main dashboard showing current weather conditions*

### Daily Summary View
![Daily Summary](screenshot_placeholder.png)
*Daily weather summaries with statistics*

### Alert Monitoring
![Alerts](screenshot_placeholder.png)
*Active weather alerts and notifications*

## ❗ Troubleshooting

### Common Issues and Solutions

#### 🔴 Database Connection Error
```
Error: Unable to connect to MySQL database
```
**Solution:**
- Check if MySQL service is running
- Verify database credentials
- Ensure database exists

#### 🔴 API Key Error
```
Error: Invalid API key
```
**Solution:**
- Verify OpenWeatherMap API key
- Check if API key is properly set in application.properties

#### 🔴 Lombok Annotations Not Working
**Solution:**
1. Install Lombok plugin in STS
2. Update Maven project
3. Clean and rebuild project

## 🤝 Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License
This project is licensed under the MIT License - see the LICENSE.md file for details

## 👥 Authors
- BABU REDDY NR - *Initial work* - [YourGitHub](https://github.com/babureddynr)

## 🙏 Acknowledgments
- OpenWeatherMap API for weather data
- Spring Boot team for the amazing framework
- All contributors who help improve the project
