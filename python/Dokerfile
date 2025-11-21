# -------------------------------
# 1️⃣ Builder: instala dependencias
# -------------------------------
FROM python:3.12-alpine AS builder

# Instalamos dependencias necesarias para compilar paquetes Python
RUN apk add --no-cache \
    build-base

WORKDIR /app

# Copiamos requirements
COPY requirements.txt .

# Creamos un entorno virtual separado
RUN python -m venv /venv && \
    /venv/bin/pip install --upgrade pip && \
    /venv/bin/pip install -r requirements.txt


# -------------------------------
# 2️⃣ Runtime: imagen final
# -------------------------------
FROM python:3.12-alpine

# Dependencias mínimas para ejecutar Python
RUN apk add --no-cache libstdc++

# Copiamos el entorno virtual ya instalado
COPY --from=builder /venv /venv

# Usamos siempre el entorno virtual
ENV PATH="/venv/bin:$PATH"

WORKDIR /app

# Copiamos toda la app (html, css, traducciones, python…)
COPY . .

# Puerto Flask típico
EXPOSE 5000

# Ejecutar Flask
CMD ["python", "app.py"]
