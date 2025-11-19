# 🏥 Clínica DONTAR – Sistema de Gestión de Turnos Odontologicos

Aplicación creada para administrar pacientes, médicos, turnos, consultas y tratamientos dentro de una clínica médica.  
Incluye autenticación con JWT, control de roles y conexión a MySQL.

## 📌 Descripción

Clínica DONTAR es un sistema backend desarrollado para gestionar:

- **Pacientes**: historia clínica, datos personales, teléfonos.
- **Médicos**: matrícula, especialidad, datos básicos.
- **Turnos / Consultas**: fecha, motivo, observaciones.
- **Tratamientos**: medicamentos recetados (dosis, frecuencia).
- **Autenticación** con JWT.
- **Autorización por roles** (admin, médico, paciente).

El objetivo es ofrecer una API REST clara, organizada y segura para el manejo de una clínica odontologica, pero puede escalar a cualquier tipo de especialidad.

## 🛠️ Tecnologías utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Security (JWT)**
- **Spring Data JPA**
- **MySQL**
- **Hibernate**
- **Maven**
- **Postman** (para pruebas)

## 🔐 Autenticación y Autorización (JWT)

El sistema utiliza **JSON Web Tokens** para autenticar a los usuarios.

### 🧾 Autenticación
1. El usuario envía email y contraseña al endpoint:  

POST /auth/login

2. Si las credenciales son válidas, se genera un JWT firmado.
3. El token contiene:
- ID del usuario
- Rol (admin, médico, paciente)
- Expiración

El token debe enviarse en cada request:

Authorization: Bearer <token>


### 🔒 Autorización
El rol incluido en el JWT determina qué puede hacer el usuario:

| Rol | Permisos |
|-----|----------|
| **Admin** | Gestionar médicos, pacientes, turnos |
| **Médico** | Ver y gestionar sus consultas y tratamientos |
| **Paciente** | Ver sus turnos, solicitar turnos |

Respuestas de seguridad:
- `401 Unauthorized`: Token faltante o inválido
- `403 Forbidden`: Usuario sin permisos

---

## 🔑 Variables de entorno (.env)

Crear un archivo `.env` en la raíz con:
DB_URL=jdbc:mysql://localhost:3306/dontar_demo?useSSL=false&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=tu_contraseña

Clave para firmar JWT — cada persona debe usar la suya

JWT_SECRET=mi_clave_super_secreta

SERVER_PORT=8081

## 👤 Usuarios de prueba

Podés usar estos usuarios para probar la API localmente:

### 👨‍💼 Administrador
- Email: clinicadontar@hotmail.com
- Contraseña: Admin1234

## 🚀 Instalación y ejecución

1️⃣ **Clonar el repositorio**
```bash
git clone https://github.com/camilapiergentili/TurnosMedicos.git
cd TurnosMedicos

2️⃣ **Crear el archivo .env**

Usar las variables indicadas arriba.

3️⃣ Configurar la base de datos MySQL

CREATE DATABASE dontar_demo;


4️⃣ Instalar dependencias

mvn clean install


5️⃣ Ejecutar el proyecto

mvn spring-boot:run


6️⃣ La API se levantará en:

http://localhost:8081
