# Student Management System – End-to-End DevOps

## Project Description

This project develops a Student Management System with CRUD operations
and implements an end-to-end DevOps pipeline.

## Technologies Used

- Java 21
- Spring Boot
- Maven
- MySQL
- Git
- GitHub
- Jenkins
- JUnit
- Docker
- Docker Hub
- Kubernetes
- Prometheus
- Grafana
- Trivy

## CRUD Operations

- Create Student
- Read Student
- Update Student
- Delete Student

## REST APIs

POST /api/students

GET /api/students

GET /api/students/{id}

PUT /api/students/{id}

DELETE /api/students/{id}

## DevOps Implementation

1. Git branching and merging
2. Maven build
3. Automated unit testing
4. Jenkins CI/CD
5. Artifact versioning
6. Docker image creation
7. Docker Hub container registry
8. Kubernetes deployment
9. Application logging
10. Prometheus monitoring
11. Grafana dashboard
12. Trivy security scanning

## Build

mvn clean package

## Test

mvn test

## Run

mvn spring-boot:run

## Docker

docker build -t student-management:1.0.0 .

## Kubernetes

kubectl apply -f k8s/

## Monitoring

Prometheus and Grafana are deployed using Kubernetes.