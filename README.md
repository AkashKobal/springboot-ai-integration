# AI Integration (Spring Boot + Ollama)

A clean, production-ready Spring Boot application integrating **Ollama** for:

- ✅ Text generation
- ✅ Image + text (multimodal) generation
- ✅ Separate REST APIs
- ✅ Separate Thymeleaf UI pages
- ✅ Fully separated controllers and services

---

## 🚀 Tech Stack

- Java 17+
- Spring Boot 3+
- Thymeleaf
- RestTemplate
- Ollama
- Maven

---

## 🏗 Architecture

Fully separated structure:

/api/text        → TextController (REST)  
/api/image       → ImageController (REST)

/text            → TextPageController (UI)  
/image           → ImagePageController (UI)

TextService  
ImageService  
DTO Layer  
GlobalExceptionHandler

No shared controllers. No endpoint conflicts.

---

## ⚙️ Configuration

### application.yml

```yaml
spring:
  application:
    name: ai-integration

server:
  port: 5644

ai:
  api:
    url: http://localhost:11434/api/chat
    model: gpt-oss:120b-cloud
    timeout: 30
```

---

## 🧠 Setup Ollama

### 1️⃣ Install Ollama
https://ollama.com

### 2️⃣ Pull model
```bash
ollama pull gpt-oss:120b-cloud
```

### 3️⃣ Run model (optional test)
```bash
ollama run gpt-oss:120b-cloud
```

---

## ▶️ Run Application

```bash
mvn clean install
mvn spring-boot:run
```

App runs at:
```
http://localhost:5644
```

---

## 🌐 UI Pages

Text UI:
```
http://localhost:5644/text
```

Image UI:
```
http://localhost:5644/image
```

---

## 📡 REST APIs

### Text Generation

POST `/api/text`  
Content-Type: application/json

Request:
```json
{
  "prompt": "Explain microservices architecture"
}
```

Response:
```json
{
  "response": "..."
}
```

---

### Image + Text Generation

POST `/api/image`  
Content-Type: multipart/form-data

Form fields:
- prompt (String)
- image (File)

Response:
```json
{
  "response": "..."
}
```

---

## 🛡 Production Features

- ✔ Timeout configuration
- ✔ Input validation
- ✔ Clean error handling
- ✔ Separate services
- ✔ DTO-based payload
- ✔ REST + UI separation
- ✔ Scalable architecture

---

## 📂 Project Structure

```
config/
  RestClientConfig.java

controller/
  TextController.java
  ImageController.java
  TextPageController.java
  ImagePageController.java

service/
  TextService.java
  ImageService.java

dto/
  ClientRequest.java
  ClientResponse.java
  Message.java
  RequestPayload.java
  ApiResponse.java

exception/
  GlobalExceptionHandler.java

templates/
  text.html
  image.html
```

---

## 🔄 Switching Models

Change model in `application.yml`:

```yaml
model: llama3
```

No code changes required.

---

## 🧩 Future Improvements

- Streaming responses
- Chat history (Redis)
- Model switch UI
- Multi-provider support (Ollama + OpenAI)
- Docker support
- Rate limiting

---

## 📜 License

MIT