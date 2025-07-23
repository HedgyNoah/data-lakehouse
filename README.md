# 🏗️ Data Lakehouse Microservices System

A modular microservices architecture designed for managing user identities, profiles, workspaces, notifications, and data lakehouse units. Built with Spring Boot, Spring Cloud Gateway, Keycloak, Docker, and MongoDB/MySQL.

---

## 🧩 Project Structure

| Service                 | Description                                        |
|------------------------|----------------------------------------------------|
| `api-gateway`          | Entry point for routing requests to all services   |
| `identity-service`     | Handles user authentication, roles, and tokens     |
| `profile-service`      | Manages user profile information                   |
| `workspace-service`    | Manages user workspaces                            |
| `notification-service` | Handles sending and receiving user notifications   |
| `unit-service`         | Manages data units in the data lakehouse           |
| `keycloak`             | Identity and Access Management (IAM)               |
| `docker-compose.yml`   | Orchestrates all services in a containerized setup |

---

## 🚀 Features

- 🔐 OAuth2 authentication via **Keycloak**
- 📬 Notification service (email/SSE support)
- 🧑‍💼 Role-based access control (RBAC)
- 🧱 Scalable microservices using Spring Boot
- 📦 API Gateway for routing and token filtering
- 🐳 Dockerized services for easy deployment
- 🗄️ Storage via **MySQL**, **MongoDB**, **Neo4j**

---

## 📦 Tech Stack

- **Backend**: Spring Boot, Spring Cloud Gateway
- **Authentication**: Keycloak, OAuth2, JWT
- **Databases**: MySQL, MongoDB, Neo4j
- **Containerization**: Docker, Docker Compose
- **Messaging/Eventing**: Kafka (planned or included)
- **Frontend** (see separate repo): Angular (planned)

---

## 🛠️ Getting Started

### 📋 Prerequisites

- Docker & Docker Compose
- Java 17+
- Maven
- (Optional) Postman for testing APIs

### 🧰 Setup

```bash
# Clone the repository
git clone https://github.com/HedgyNoah/data-lakehouse.git
cd data-lakehouse

# Start all services using Docker
docker-compose up --build
