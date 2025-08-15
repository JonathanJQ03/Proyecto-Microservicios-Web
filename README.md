# Proyecto Microservicios Web

> Sistema web de gestión de clientes, pedidos y seguimiento de entregas con arquitectura de microservicios + orquestación mediante Docker Compose

**Autores:** Jaguaco Jonathan • Tipan Reishel • Stefy Díaz

---

## Descripción

Este proyecto implementa un sistema web completo de gestión empresarial desarrollado con **arquitectura de microservicios**. La solución incluye un frontend moderno en **Angular** y múltiples microservicios en **Spring Boot** que se comunican entre sí, respaldados por bases de datos PostgreSQL y MySQL, además de un servicio Redis para seguimiento en tiempo real.

### Características principales

- Registro y gestión completa de clientes
- Creación y seguimiento de pedidos en tiempo real
- Autenticación y autorización segura con JWT
- Dashboard interactivo con visualización del estado de pedidos
- Arquitectura escalable y modular
- Containerización completa con Docker

---

## Arquitectura del Sistema

```
┌─────────────────────┐
│   Angular Frontend  │  ←→  Usuario Web
│   (Port: 4200)     │
└─────────────────────┘
           │
           ▼
┌─────────────────────┐
│    API Gateway /    │
│   Frontend Client   │
└─────────────────────┘
           │
           ▼
┌─────────┬─────────┬─────────┬─────────┐
│  Auth   │ Client  │ Order   │Tracking │
│Service  │Service  │Service  │Service  │
│:8081    │:8088    │:8083    │:8085    │
└─────────┴─────────┴─────────┴─────────┘
     │         │         │         │
     ▼         ▼         ▼         ▼
┌─────────┐┌─────────┐┌─────────┐┌─────────┐
│PostgreSQL││PostgreSQL││  MySQL  ││MySQL+Red│
│(Auth DB)││(Client DB)││(Orders) ││(Track)  │
│  :5433  ││  :5432  ││  :3310  ││:6379+SQL│
└─────────┘└─────────┘└─────────┘└─────────┘
```

### Componentes del sistema

**Frontend**
- **Angular Frontend**: Interfaz de usuario moderna y responsiva

**Microservicios Backend**
- **Auth Service**: Gestión de usuarios y autenticación JWT
- **Client Service**: CRUD completo de clientes
- **Order Service**: Gestión y procesamiento de pedidos
- **Tracking Service**: Seguimiento en tiempo real de entregas

**Bases de datos**
- **PostgreSQL**: Almacena datos de autenticación y clientes
- **MySQL**: Registros de pedidos y seguimiento
- **Redis**: Cache y datos en tiempo real para tracking

---

## Tecnologías Utilizadas

### Frontend
- **Angular 19** - Framework principal
- **TypeScript** - Lenguaje de desarrollo
- **HTML/CSS** - Estructura y estilos

### Backend
- **Java** - Lenguaje de programación
- **Spring Boot** - Framework de microservicios
- **Spring Security** - Seguridad y autenticación
- **Spring Data JPA** - Persistencia de datos
- **Hibernate** - ORM

### Bases de datos
- **PostgreSQL 15** - Base de datos relacional
- **MySQL 8** - Base de datos relacional
- **Redis 7** - Cache y mensajería en tiempo real
  - RedisBloom - Filtros probabilísticos
  - RedisTimeSeries - Series temporales

### DevOps y herramientas
- **Docker** - Containerización
- **Docker Compose** - Orquestación de servicios
- **Git** - Control de versiones

---

## Requisitos del sistema

- **Docker** >= 20.10
- **Docker Compose** >= 2.0
- **Git** >= 2.30
- **Navegador web moderno** (Chrome, Firefox, Safari, Edge)
- **8GB RAM mínimo** recomendado
- **Puertos disponibles**: 3310, 4200, 5432, 5433, 6379, 8081, 8083, 8085, 8088

---

## Instalación y ejecución

### Clonar el repositorio
```bash
git clone https://github.com/JonathanJQ03/Proyecto-Microservicios-Web.git
cd proyecto-microservicios-web
```

### Levantar todos los servicios
```bash
# Construir y levantar todos los contenedores
docker-compose up --build

# O en modo background (recomendado)
docker-compose up --build -d
```

### Acceso a los servicios

Una vez levantados todos los contenedores, los servicios estarán disponibles en:

| Servicio | URL | Puerto |
|----------|-----|--------|
| **Frontend Angular** | http://localhost:4200 | 4200 |
| **Auth Service** | http://localhost:8081 | 8081 |
| **Client Service** | http://localhost:8088 | 8088 |
| **Order Service** | http://localhost:8083 | 8083 |
| **Tracking Service** | http://localhost:8085 | 8085 |
| **PostgreSQL (Auth)** | localhost:5433 | 5433 |
| **PostgreSQL (Client)** | localhost:5432 | 5432 |
| **MySQL (Orders)** | localhost:3310 | 3310 |
| **Redis (Tracking)** | localhost:6379 | 6379 |

---

## Comandos de administración

### Monitoreo de logs
```bash
# Ver logs de todos los servicios
docker-compose logs -f

# Ver logs de un servicio específico
docker-compose logs -f <service-name>
# Ejemplo: docker-compose logs -f auth-service
```

### Gestión de contenedores
```bash
# Verificar estado de los servicios
docker-compose ps

# Reiniciar un servicio específico
docker-compose restart <service-name>

# Detener todos los servicios
docker-compose stop

# Detener y eliminar contenedores, redes y volúmenes
docker-compose down -v

# Reconstruir un servicio específico
docker-compose up --build <service-name>
```

### Limpieza del sistema
```bash
# Eliminar contenedores, redes, imágenes no utilizadas
docker-compose down -v --rmi all

# Limpiar completamente Docker (usar con precaución)
docker system prune -a --volumes
```

---

## Variables de entorno

### Configuración general
```env
# Puertos de servicios
AUTH_PORT=8081
CLIENT_PORT=8088
ORDER_PORT=8083
TRACKING_PORT=8085
FRONTEND_PORT=4200

# Base de datos PostgreSQL (Auth)
AUTH_DB_HOST=auth-postgres
AUTH_DB_PORT=5432
AUTH_DB_NAME=auth_db
AUTH_DB_USER=postgres
AUTH_DB_PASSWORD=admin123

# Base de datos PostgreSQL (Client)
CLIENT_DB_HOST=client-postgres
CLIENT_DB_PORT=5432
CLIENT_DB_NAME=clients_db
CLIENT_DB_USER=postgres
CLIENT_DB_PASSWORD=admin123

# Base de datos MySQL (Orders)
ORDER_DB_HOST=order-mysql
ORDER_DB_PORT=3306
ORDER_DB_NAME=orders_db
ORDER_DB_USER=root
ORDER_DB_PASSWORD=admin123

# Redis (Tracking)
REDIS_HOST=tracking-redis
REDIS_PORT=6379
REDIS_PASSWORD=admin123

# Seguridad JWT
JWT_SECRET=mi-super-secreto-jwt-key-2024-proyecto-microservicios
JWT_EXPIRATION=86400000
```

---

## Estructura del proyecto

```
proyecto-microservicios-web/
├── frontend/                 # Angular Frontend
│   ├── src/
│   ├── angular.json
│   ├── package.json
│   └── Dockerfile
├── auth-service/            # Microservicio de autenticación
│   ├── src/main/java/
│   ├── pom.xml
│   └── Dockerfile
├── client-service/          # Microservicio de clientes
│   ├── src/main/java/
│   ├── pom.xml
│   └── Dockerfile
├── order-service/           # Microservicio de pedidos
│   ├── src/main/java/
│   ├── pom.xml
│   └── Dockerfile
├── tracking-service/        # Microservicio de seguimiento
│   ├── src/main/java/
│   ├── pom.xml
│   └── Dockerfile
├── docker-compose.yml       # Orquestación de servicios
├── .env                    # Variables de entorno
└── README.md               # Este archivo
```

---

## Solución de problemas

### Problemas comunes

**Error: Puerto ya en uso**
```bash
# Verificar qué proceso usa el puerto
lsof -i :PUERTO

# Cambiar el puerto en docker-compose.yml o detener el proceso
```

**Error: Sin espacio en disco**
```bash
# Limpiar imágenes no utilizadas
docker image prune -a

# Limpiar volúmenes no utilizados
docker volume prune
```

**Error: Contenedor no responde**
```bash
# Reiniciar contenedor específico
docker-compose restart <service-name>

# Ver logs detallados
docker-compose logs <service-name>
```


---


## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

---

## Contacto

**Desarrolladores:**
- Jaguaco Jonathan
- Tipan Reishel  
- Stefy Díaz

**Proyecto académico** - Universidad De Las Fuerzas Armadas ESPE - 2025)
