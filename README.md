# Practica-Projectes-Internacionalitzats

Este repositorio contiene varias aplicaciones de ejemplo para practicar la **internacionalización** (i18n).  
Actualmente incluye:

- **Aplicación Python**: Gestor de tareas de terminal internacionalizado.  
- **Aplicación Java**: *(en desarrollo)*  

---

## Aplicación Python: Gestor de Tareas Internacionalizado

Esta aplicación de terminal permite crear, listar, completar y eliminar tareas, y soporta **varios idiomas**: inglés (por defecto), español y catalán.  
La internacionalización se realiza usando **gettext**.

### Estructura del proyecto Python

```
python/
├── app.py           # Aplicación principal
├── tasks.py         # Lógica de gestión de tareas
├── storage.json     # Archivo JSON donde se guardan las tareas
├── locale/          # Traducciones
│   ├── en/LC_MESSAGES/messages.po
│   ├── es/LC_MESSAGES/messages.po
│   └── ca/LC_MESSAGES/messages.po
└── requirements.txt # Dependencias de Python
```

### Requisitos

- Python 3.11+  
- pip  
- Babel 

---

### Instalación y ejecución

1. Dirigete al directorio de la aplicación:

```bash
cd Practica-Projectes-Internacionalitzats/python
```

2. Crea un entorno virtual (recomendado):

```bash
python3 -m venv venv
source venv/bin/activate  # Linux / Mac
# o en Windows: venv\Scripts\activate
```

3. Instala las dependencias:

```bash
pip install -r requirements.txt
```

4. Ejecuta la aplicación:

```bash
python3 app.py
```

5. Selecciona el idioma cuando se te pregunte y comienza a usar el gestor de tareas.

---

### Notas

- Las tareas se guardan en `storage.json`. Si el archivo está vacío o no existe, la aplicación lo inicializa automáticamente.  
- Puedes añadir nuevos idiomas creando las carpetas correspondientes en `locale/` y generando los archivos `.po` y `.mo`.  


---

## Aplicación Java

*(Sección reservada para la aplicación Java, en desarrollo)*
