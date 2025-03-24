
# GeeksInnovation-1on1Bet

## Overview

GeeksInnovation-1on1Bet is an application built as a monorepo that includes both the backend and frontend. The backend is developed using Java Spring Boot, and the frontend is developed with React and TypeScript. CI/CD is set up with Docker and GitHub Actions.

## Backend (1on1Bet-Backend)

The backend of the application is built using Java Spring Boot and contains multiple Spring Boot microservices.

### Prerequisites
- JDK 17 or above
- Maven

### Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/GeeksInnovation/gi-1on1bet.git
   cd gi-1on1bet
   ```

2. Navigate to the backend directory:
   ```bash
   cd 1on1bet-be
   ```

3. Run the following command to install the required dependencies and build the backend:
   ```bash
   mvn clean install
   ```

4. You will see multiple Spring Boot projects under the `1on1bet-be` directory.

### Running the Backend Locally
To run a specific Spring Boot service locally, use the following Maven command within the project folder:
```bash
mvn spring-boot:run
```

---

## Frontend (1on1Bet-Frontend)

The frontend is built using React and TypeScript.

### Prerequisites
- Node.js and npm

### Setup Instructions
1. Navigate to the frontend directory:
   ```bash
   cd 1on1bet-fe
   ```

2. Install the necessary dependencies:
   ```bash
   npm install
   ```

3. Start the frontend application in development mode:
   ```bash
   npm run dev
   ```

4. The React application should now be running locally on `http://localhost:5173`.

---

## CI/CD Setup

The project uses Docker and GitHub Actions for continuous integration and continuous deployment.

### Docker Setup
- Docker images are used for both backend and frontend to standardize the development and deployment environments.

### GitHub Actions
- GitHub Actions is used to automate builds, tests, and deployments whenever there are changes pushed to the repository.

---

## Getting Access

1. Clone the repository:
   ```bash
   git clone https://github.com/GeeksInnovation/gi-1on1bet.git
   ```

2. Follow the setup instructions above to run both the backend and frontend locally.
