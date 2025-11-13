# Actividad 2 - Internacionalización

Este repositorio contiene varias aplicaciones de ejemplo para practicar la **internacionalización** (i18n).  
Actualmente incluye:

- **Aplicación Python**: Gestor de tareas de terminal internacionalizado.  
- **Aplicación Java**: Un juego de Trivial de escritorio multilingüe (español, inglés y catalán) construido con JavaFX y Maven.  

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

## Aplicación Java: Juego de Trivial Internacionalizado

Esta aplicación de escritorio es un juego de Trivial con una interfaz gráfica desarrollada en **JavaFX**. Soporta **varios idiomas** (español, inglés y catalán) que se pueden cambiar dinámicamente desde la propia aplicación. La internacionalización se gestiona mediante ficheros **ResourceBundle**.

### Estructura del proyecto Java

```
java/
├── pom.xml          
└── src/
    └── main/
        ├── java/
        │   └── com/example/trivial/
        │       ├── MainApp.java            
        │       └── TrivialController.java  
        └── resources/
            └── com/example/trivial/
                ├── Trivial.fxml            
                ├── styles.css              
                └── bundles/                
                    ├── messages_es.properties
                    ├── messages_en.properties
                    └── messages_ca.properties
```

### Requisitos

- JDK 17 o superior
- Maven 3.6+

---

### Instalación y ejecución

1. Dirígete al directorio de la aplicación:

    ```bash
    cd Practica-Projectes-Internacionalitzats/java
    ```

2. Maven gestionará la descarga de las dependencias de JavaFX automáticamente. Simplemente ejecuta el siguiente comando para compilar y lanzar la aplicación:

    ```bash
    mvn clean javafx:run
    ```

3. La aplicación se compilará y se lanzará en una nueva ventana. Puedes cambiar el idioma usando el selector en la esquina superior derecha.

---

### Notas

- Todo el texto visible (preguntas, respuestas, botones...) se carga desde los ficheros `.properties` ubicados en `src/main/resources/com/example/trivial/bundles/`.
- Puedes añadir nuevos idiomas creando un nuevo fichero `messages_xx.properties` (donde `xx` es el código del idioma) y añadiendo el `Locale` correspondiente en el `TrivialController.java`.````