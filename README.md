
Readme turnosmedicos · MD
Copiar

# 🦷 Clínica DONTAR – Sistema de Gestión de Turnos

API REST backend para la gestión integral de una clínica odontológica: pacientes, médicos, turnos, consultas y tratamientos. Incluye autenticación JWT y control de acceso por roles.

> **Stack:** Java 17 · Spring Boot · Spring Security · JWT · Spring Data JPA · Hibernate · MySQL · Docker

---

## ✨ ¿Qué hace este sistema?

Clínica DONTAR resuelve la gestión completa de una clínica médica desde el backend:

- **Pacientes** → historia clínica, datos personales y teléfonos de contacto
- **Médicos** → matrícula, especialidad y datos profesionales
- **Turnos y consultas** → fecha, motivo y observaciones por paciente
- **Tratamientos** → medicamentos recetados con dosis y frecuencia
- **Autenticación segura** → login con JWT, sesiones sin estado
- **Control de acceso por rol** → cada usuario solo ve y hace lo que le corresponde

---

## 🔐 Seguridad: JWT + Roles

El sistema implementa autenticación stateless con JSON Web Tokens. Cada token firmado contiene el ID del usuario, su rol y la fecha de expiración.

### Roles y permisos

| Rol | Capacidades |
|---|---|
| `ADMIN` | Gestión completa: médicos, pacientes y turnos |
| `MÉDICO` | Ver y gestionar sus propias consultas y tratamientos |
| `PACIENTE` | Ver sus turnos y solicitar nuevos |

### Flujo de autenticación

```
POST /auth/login
→ Body: { "email": "...", "password": "..." }
← Response: { "token": "eyJhbGci..." }
```

Luego, incluir en cada request:
```
Authorization: Bearer <token>
```

Respuestas de seguridad:
- `401 Unauthorized` → Token inválido o ausente
- `403 Forbidden` → El rol no tiene permiso para esa acción

---

## 🛠️ Tecnologías

| Tecnología | Uso |
|---|---|
| Java 17 | Lenguaje principal |
| Spring Boot | Framework base |
| Spring Security | Seguridad y autenticación |
| JWT | Tokens de sesión stateless |
| Spring Data JPA + Hibernate | Persistencia ORM |
| MySQL | Base de datos relacional |
| Maven | Gestión de dependencias |
| Docker / Docker Compose | Contenedores y orquestación |
| Postman | Testing de endpoints |

---

## 🌐 Demo en producción

Podés probar el sistema completo sin instalar nada:

| | URL |
|---|---|
| 🖥️ **Frontend** | [effervescent-pavlova-0b97a8.netlify.app](https://effervescent-pavlova-0b97a8.netlify.app) |
| ⚙️ **Backend API** | [turnosmedicos-production.up.railway.app](https://turnosmedicos-production.up.railway.app) |

**Credenciales de prueba (Admin):**
```
Email:      clinicadontar@hotmail.com
Contraseña: Admin1234
```

---

## 🚀 Cómo ejecutar el proyecto localmente

### Opción 1: Con Docker (recomendado)

```bash
git clone https://github.com/camilapiergentili/TurnosMedicos.git
cd TurnosMedicos
cp .env.example .env   # completar con tus variables
docker-compose up --build
```

### Opción 2: Local con Maven

**1. Clonar el repositorio**
```bash
git clone https://github.com/camilapiergentili/TurnosMedicos.git
cd TurnosMedicos
```

**2. Crear el archivo `.env` en la raíz del proyecto**
```env
DB_URL=jdbc:mysql://localhost:3306/dontar_demo?useSSL=false&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=tu_contraseña
JWT_SECRET=mi_clave_super_secreta
SERVER_PORT=8081
```

**3. Crear la base de datos**
```sql
CREATE DATABASE dontar_demo;
```

**4. Compilar y ejecutar**
```bash
mvn clean install
mvn spring-boot:run
```

La API quedará disponible en: `http://localhost:8081`

---

## 👤 Usuarios de prueba

Para probar los endpoints, podés usar el usuario administrador incluido:

| Rol | Email | Contraseña |
|---|---|---|
| Admin | clinicadontar@hotmail.com | Admin1234 |

---

## 📁 Estructura del proyecto

```
TurnosMedicos/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/dontar/
│       │       ├── auth/         # JWT y seguridad
│       │       ├── controllers/  # Endpoints REST
│       │       ├── models/       # Entidades JPA
│       │       ├── repositories/ # Acceso a datos
│       │       └── services/     # Lógica de negocio
│       └── resources/
│           └── application.properties
├── docker-compose.yml
├── pom.xml
└── .env
```

---

## 📌 Endpoints principales

| Método | Endpoint | Descripción | Rol requerido |
|---|---|---|---|
| `POST` | `/auth/login` | Autenticación y obtención de token | Público |
| `GET` | `/pacientes` | Listar todos los pacientes | Admin |
| `POST` | `/pacientes` | Registrar nuevo paciente | Admin |
| `GET` | `/medicos` | Listar médicos | Admin |
| `GET` | `/turnos` | Ver turnos | Admin / Médico |
| `POST` | `/turnos` | Crear turno | Admin / Paciente |
| `GET` | `/consultas` | Ver consultas | Admin / Médico |
| `POST` | `/tratamientos` | Registrar tratamiento | Médico |

---

## 👩‍💻 Autora

**Camila Piergentili**  
Técnica Universitaria en Programación · Profesora de Matemática  