# Actividad 2 - Internacionalización

Este repositorio contiene varias aplicaciones de ejemplo para practicar la **internacionalización** (i18n).  
Actualmente incluye:

- **Aplicación Flask**: Juego tipo Wordle internacionalizado con soporte de idiomas (es, en, ca).
- **Aplicación Java**: Un juego de Trivial de escritorio multilingüe (español, inglés y catalán) construido con JavaFX y Maven.  

---

# Aplicación Flask: Mini-Wordle Multilingüe

Esta es una aplicación web inspirada en *Wordle*, desarrollada con
**Flask** y con soporte de idiomas mediante **Flask-Babel**.\
El usuario puede jugar adivinando una palabra de 5 letras, con un máximo
de 6 intentos, y elegir entre **inglés**, **español** y **catalán**.

## Estructura del proyecto

    project/
    ├── app.py                    
    ├── requirements.txt          
    ├── templates/
    │   └── index.html            
    ├── static/                   
    │   └── styles.css
    └── translations/            
        ├── es/LC_MESSAGES/messages.po
        ├── en/LC_MESSAGES/messages.po
        └── ca/LC_MESSAGES/messages.po

## Requisitos

-   Python **3.12+**
-   pip
-   Flask\
-   Flask-Babel\

## Instalación y ejecución (modo local)

### 1. Ir al directorio del proyecto

``` bash
cd project
```

### 2. Crear un entorno virtual (recomendado)

``` bash
python3 -m venv venv
source venv/bin/activate      # Linux / macOS
# Windows:
# venv\Scripts\activate
```

### 3. Instalar dependencias

``` bash
pip install -r requirements.txt
```

### 4. Ejecutar la aplicación Flask

``` bash
python app.py
```

Luego abre en tu navegador:

    http://localhost:5000

## Selección de idioma

El idioma se elige automáticamente mediante el parámetro `?lang=` en la
URL:

-   Español:

        http://localhost:5000?lang=es

-   Inglés:

        http://localhost:5000?lang=en

-   Catalán:

        http://localhost:5000?lang=ca

Flask almacena el idioma en la sesión del usuario.

## ¿Cómo funciona el juego?

-   El usuario envía una palabra de **5 letras**.
-   Cada letra se marca como:
    -   **correct** → posición y letra correcta\
    -   **present** → letra correcta en posición incorrecta\
    -   **absent** → la letra no está en la palabra\
-   El jugador tiene **6 intentos**.
-   El juego termina al acertar o agotar intentos.
-   Se puede reiniciar en `/reset`.


## Notas

-   Las traducciones se ubican en `translations/`.\
-   Puedes añadir más idiomas generando nuevos `.po` / `.mo` con Babel.\
-   La palabra objetivo está definida en el diccionario `TARGET_WORDS`
    dentro de `app.py`.


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