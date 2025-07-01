# Train Management System

This is a Train Management System project built using Java Spring Boot for the backend and MySQL for the database. The system allows for train ticket booking, reservation management and other additional functions.

---

## Features
- Detail: [Instructions documents](https://github.com/ThunSuy/TrainBooking_Backend/blob/main/TaiLieuHuongDan.pdf)

---

## Prerequisites

### Tools and Software Required:
1. [Java 17+](https://www.oracle.com/java/technologies/javase-downloads.html)
2. [Spring Boot 3+](https://spring.io/projects/spring-boot)
3. [MySQL 8+](https://dev.mysql.com/downloads/)
4. [Maven](https://maven.apache.org/download.cgi)
5. [Git](https://git-scm.com/)

---

## Installation and Setup

### Step 1: Clone the Repository
```bash
git clone https://github.com/ThunSuy/TrainBooking_Backend.git
cd TrainBooking_Backend
```

### Step 2: Set Up MySQL Database
1. Open your MySQL client and create a database:
   ```sql
   CREATE DATABASE trainbooker;
   ```
2. Import the provided database script (`trainbooker.sql`) located in the `database` folder:
   ```bash
   mysql -u <username> -p trainbooker < database/trainbooker.sql
   ```

### Step 3: Configure Application Properties
Update the `src/main/resources/application.properties` file with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/trainbooker
spring.datasource.username=<your-mysql-username>
spring.datasource.password=<your-mysql-password>
spring.jpa.hibernate.ddl-auto=update
```

### Step 4: Build and Run the Application
1. Build the project using Maven:
   ```bash
   mvn clean install
   ```
2. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

### Step 5: Access the Application
- The application will be available at: `http://localhost:8081`

---

## Fontend
**Git**: [Fontend here](https://github.com/ThunSuy/TrainBooking_Fontend)

## Documentation
- [1] https://dsvn.vn
- [2] https://k.vnticketonline.vn
- [3] https://o7planning.org/11201/java-web-services
- [4] https://o7planning.org/11669/spring-boot
- [5] https://spring.io/guides/tutorials/spring-boot-oauth2
- [6] https://developers.google.com/codelabs/maps-platform/maps-platform-101-react-js#0


---
