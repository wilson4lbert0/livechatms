# LiveChatMS

A real-time group chat application built with **Spring Boot** and **WebSockets (STOMP protocol)**. Messages are broadcast instantly to all connected clients with no page refresh required.

![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-brightgreen?logo=springboot)
![WebSocket](https://img.shields.io/badge/WebSocket-STOMP-blue)

---

## Features

- Real-time messaging via WebSocket (STOMP over WS)
- In-memory message broker — no database required
- Dark-themed, responsive UI built with Bootstrap 3
- XSS protection via server-side HTML escaping
- Configurable server port via environment variable

---

## Tech Stack

| Layer     | Technology                                |
|-----------|-------------------------------------------|
| Backend   | Java 25, Spring Boot 4.0.6, Spring WebSocket |
| Messaging | STOMP protocol, in-memory message broker  |
| Frontend  | HTML5, CSS3, JavaScript (ES6)             |
| UI libs   | Bootstrap 3.3.7, jQuery 3.1.1, STOMP.js 7.0.0 |
| Build     | Maven 4.0.0 (wrapper included)            |

---

## Architecture

```
Browser A ──┐
             │  STOMP over WebSocket
Browser B ──┤──► /buildrun-livechat-websocket ──► Spring Controller ──► /topics/livechat ──► All Clients
             │
Browser C ──┘
```

**Message flow:**

1. Client connects to the WebSocket endpoint `/buildrun-livechat-websocket`
2. Client subscribes to `/topics/livechat` to receive messages
3. Client publishes to `/app/new-message` with `{ user, message }` payload
4. `LiveChatController` receives, escapes HTML, and returns `{ content }` to `/topics/livechat`
5. Every subscribed client receives the broadcast instantly

---
### Prerequisites

- Java 25+
- Maven 3.9+ (or use the included `mvnw` wrapper)

### Run locally

```bash
# Clone the repository
git clone https://github.com/your-username/livechatms.git
cd livechatms

# Start the application (Unix/macOS)
./mvnw spring-boot:run

# Start the application (Windows)
mvnw.cmd spring-boot:run
```

### Custom port

```bash
SERVER_PORT=8080 ./mvnw spring-boot:run
```


## Project Structure

```
livechatms/
├── src/main/java/moriva/cv/livechatms/
│   ├── LivechatmsApplication.java      # Application entry point
│   ├── config/
│   │   └── WebSocketConfig.java        # STOMP/WebSocket configuration
│   ├── controller/
│   │   └── LiveChatController.java     # Handles incoming chat messages
│   └── domain/
│       ├── ChatInput.java              # Incoming message DTO (user, message)
│       └── ChatOutput.java             # Outgoing message DTO (content)
└── src/main/resources/
    ├── application.properties          # Server configuration
    └── static/
        ├── index.html                  # Chat UI
        ├── app.js                      # WebSocket client logic
        └── main.css                    # Dark theme styles
```

---

## WebSocket API

| Direction       | Destination                       | Payload                              |
|-----------------|-----------------------------------|--------------------------------------|
| Client → Server | `/app/new-message`                | `{ "user": "...", "message": "..." }`|
| Server → Client | `/topics/livechat` (subscription) | `{ "content": "user: message" }`     |

---

## Configuration

| Property      | Environment Variable | Default |
|---------------|----------------------|---------|
| Server port   | `SERVER_PORT`        | `5000`  |

---



