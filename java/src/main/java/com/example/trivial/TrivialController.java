package com.example.trivial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrivialController {

    @FXML
    private Label preguntaLabel;
    @FXML
    private GridPane opcionesGrid;
    @FXML
    private Label resultadoLabel;
    @FXML
    private Button siguienteButton;

    private int preguntaActual = 0;
    private List<Button> botonesOpcion = new ArrayList<>();

    private static class Pregunta {
        String enunciado;
        String respuestaCorrecta;
        List<String> respuestasIncorrectas;

        Pregunta(String enunciado, String respuestaCorrecta, List<String> respuestasIncorrectas) {
            this.enunciado = enunciado;
            this.respuestaCorrecta = respuestaCorrecta;
            this.respuestasIncorrectas = respuestasIncorrectas;
        }
    }

    private final Pregunta[] preguntas = new Pregunta[]{
        new Pregunta("¿Cuál es la capital de Japón?", "Tokio", List.of("Kioto", "Osaka", "Nagoya")),
        new Pregunta("¿Qué elemento químico tiene el símbolo 'O'?", "Oxígeno", List.of("Oro", "Osmio", "Oganesón")),
        new Pregunta("¿En qué año finalizó la Segunda Guerra Mundial?", "1945", List.of("1942", "1948", "1939")),
        new Pregunta("¿Quién escribió 'Cien años de soledad'?", "Gabriel García Márquez", List.of("Mario Vargas Llosa", "Julio Cortázar", "Pablo Neruda"))
    };

    @FXML
    public void initialize() {
        crearBotones();
        cargarPregunta();
    }

    private void crearBotones() {
        for (int i = 0; i < 4; i++) {
            Button button = new Button();
            button.setOnAction(this::opcionSeleccionada);
            botonesOpcion.add(button);
            opcionesGrid.add(button, i % 2, i / 2);
        }
    }
    
    private void cargarPregunta() {
        // Resetear estado visual
        resetearBotones();
        resultadoLabel.setText("");
        siguienteButton.setVisible(false);
        opcionesGrid.setDisable(false);

        // Cargar datos de la nueva pregunta
        Pregunta p = preguntas[preguntaActual];
        preguntaLabel.setText(p.enunciado);

        // Mezclar opciones de respuesta
        List<String> opciones = new ArrayList<>(p.respuestasIncorrectas);
        opciones.add(p.respuestaCorrecta);
        Collections.shuffle(opciones);

        for (int i = 0; i < botonesOpcion.size(); i++) {
            Button button = botonesOpcion.get(i);
            button.setText(opciones.get(i));
            // Guardamos la respuesta correcta en el botón correcto para identificarla después
            button.setUserData(opciones.get(i).equals(p.respuestaCorrecta));
        }
    }
    
    private void opcionSeleccionada(ActionEvent event) {
        opcionesGrid.setDisable(true); // Bloquear más respuestas
        siguienteButton.setVisible(true);

        Button botonSeleccionado = (Button) event.getSource();
        boolean esCorrecto = (boolean) botonSeleccionado.getUserData();

        if (esCorrecto) {
            resultadoLabel.setText("¡Correcto!");
            resultadoLabel.setStyle("-fx-text-fill: #2ECC71;"); // Verde
        } else {
            resultadoLabel.setText("¡Incorrecto!");
            resultadoLabel.setStyle("-fx-text-fill: #E74C3C;"); // Rojo
            botonSeleccionado.getStyleClass().add("wrong-answer");
        }

        // Resaltar la respuesta correcta en verde
        for (Button btn : botonesOpcion) {
            if ((boolean) btn.getUserData()) {
                btn.getStyleClass().add("correct-answer");
                break;
            }
        }
    }

    @FXML
    void siguientePregunta() {
        preguntaActual++;
        if (preguntaActual < preguntas.length) {
            cargarPregunta();
        } else {
            preguntaLabel.setText("¡Has completado el Trivial!");
            opcionesGrid.setVisible(false);
            siguienteButton.setVisible(false);
            resultadoLabel.setText("¡Gracias por jugar!");
            resultadoLabel.setStyle("-fx-text-fill: #F39C12;");
        }
    }
    
    private void resetearBotones() {
        for (Button btn : botonesOpcion) {
            btn.getStyleClass().removeAll("correct-answer", "wrong-answer");
        }
    }
}