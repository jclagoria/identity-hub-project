## Proposed Hexagonal and Microservice-Oriented Architecture

### 1. General Description
The architecture is designed to be modular, scalable, and maintainable. It uses hexagonal architecture principles to separate business logic from other layers and leverages microservices to split functionalities into independent components.

Each microservice includes:

Domain: Contains the business logic and system rules.
Adapters:
Inbound: REST controllers or event consumers.
Outbound: Implementations for data repositories, external services, or APIs.
Ports: Interfaces that connect domain logic with external adapters.

### 2. Main Components

- API Gateway:

Single entry point for external requests.
Responsible for basic request authentication and load balancing.

- Authentication Service:

Manages login/logout, JWT issuance, and validation.
Supports social login with OAuth2.

- User Management Service:

Manages users, profiles, and roles.
Handles tasks like registration, email verification, and password reset.

- Token Service:

Handles the issuance, storage, and revocation of refresh tokens.
Stores metadata associated with the tokens.

- Notification Service (Optional):

Sends emails or notifications about key events (e.g., email verification, password reset).
Audit and Logging Service:

Centralizes logs and audit records.
Useful for tracking failed access attempts or configuration changes.

### 3. Inter-Microservice Communication

REST APIs: For synchronous requests (e.g., the Authentication Service consulting the User Management Service).
Asynchronous Messaging: Using Kafka or RabbitMQ for events such as “user registered” or “token invalidated.”

### 4. Architecture Diagram

                               +--------------------+
                               |    API Gateway     |
                               +--------------------+
                                         |
               --------------------------------------------------------
               |                               |                     |
     +---------------------+        +--------------------+   +---------------------+
     | Authentication      |        | User Management    |   | Notification Service|
     | Service             |        | Service            |   | (Optional)          |
     +---------------------+        +--------------------+   +---------------------+
               |                               |                     |
      +------------------+             +------------------+     +------------------+
      | OAuth Integration|             | User Repository  |     | Email/SMS        |
      +------------------+             +------------------+     +------------------+
               |
      +------------------+
      | Token Service    |
      +------------------+
               |
      +------------------+
      | Token Repository |
      +------------------+

### 5. Modifications Made

Separation of Responsibilities:

Each microservice has a unique responsibility.
Use of ports and adapters to ensure domain independence.
Asynchronous Communication:

An asynchronous messaging mechanism was added for critical events (e.g., “user registered”).
Ensures decoupling between microservices.
Integration of an Audit and Notification Service:

The audit service tracks important events to improve security and traceability.
The notification service enhances the user experience.
Reorganization of the Token Flow:

Centralization of token logic in a dedicated microservice.
Improves scalability and maintainability.
