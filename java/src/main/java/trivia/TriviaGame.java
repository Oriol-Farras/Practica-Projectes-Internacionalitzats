package trivia;

import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Un juego de preguntas y respuestas profesional implementado con acm.graphics.
 * Demuestra la separación de la lógica del juego y la interfaz gráfica.
 */
public class TriviaGame extends GraphicsProgram {

    // --- Constantes para la configuración de la UI ---
    public static final int APPLICATION_WIDTH = 800;
    public static final int APPLICATION_HEIGHT = 600;

    private static final Font QUESTION_FONT = new Font("SansSerif", Font.BOLD, 24);
    private static final Font OPTION_FONT = new Font("SansSerif", Font.PLAIN, 18);
    private static final Font FEEDBACK_FONT = new Font("SansSerif", Font.BOLD, 22);
    private static final Font SCORE_FONT = new Font("SansSerif", Font.PLAIN, 16);

    private static final double BUTTON_WIDTH = 600;
    private static final double BUTTON_HEIGHT = 50;
    private static final double BUTTON_SPACING = 15;

    private static final Color CORRECT_COLOR = new Color(46, 204, 113);
    private static final Color INCORRECT_COLOR = new Color(231, 76, 60);
    private static final Color BUTTON_COLOR = new Color(52, 152, 219);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

    // --- Variables de estado del juego ---
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;

    // --- Objetos gráficos (GObjects) ---
    private GLabel questionLabel;
    private GLabel scoreValueLabel;
    private GLabel feedbackLabel;
    private List<GRect> optionBoxes = new ArrayList<>();
    private List<GLabel> optionLabels = new ArrayList<>();
    private GRect playAgainButton;

    @Override
    public void init() {
        setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        addMouseListeners();
    }

    @Override
    public void run() {
        setupGame();
        displayCurrentQuestion();
    }

    /**
     * Inicializa o reinicia el estado del juego y la interfaz de usuario.
     */
    private void setupGame() {
        removeAll(); // Limpia la pantalla para un nuevo juego
        optionBoxes.clear();
        optionLabels.clear();
        
        score = 0;
        currentQuestionIndex = 0;
        loadQuestions();
        setupUI();
    }
    
    /**
     * Crea y posiciona todos los objetos gráficos iniciales en la pantalla.
     */
    private void setupUI() {
        // Etiqueta para la pregunta
        questionLabel = new GLabel("");
        questionLabel.setFont(QUESTION_FONT);
        add(questionLabel, 50, 80);

        // Etiquetas para la puntuación
        GLabel scoreTextLabel = new GLabel("Puntuación:");
        scoreTextLabel.setFont(SCORE_FONT);
        add(scoreTextLabel, getWidth() - 150, 40);

        scoreValueLabel = new GLabel(String.valueOf(score));
        scoreValueLabel.setFont(SCORE_FONT);
        add(scoreValueLabel, getWidth() - 70, 40);

        // Etiqueta para la retroalimentación (Correcto/Incorrecto)
        feedbackLabel = new GLabel("");
        feedbackLabel.setFont(FEEDBACK_FONT);
        add(feedbackLabel); // Se posicionará más tarde
        
        // Crear botones para las 4 opciones de respuesta
        double startY = 150;
        for (int i = 0; i < 4; i++) {
            double y = startY + i * (BUTTON_HEIGHT + BUTTON_SPACING);
            
            // El rectángulo del botón
            GRect optionBox = new GRect((getWidth() - BUTTON_WIDTH) / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT);
            optionBox.setFilled(true);
            optionBox.setFillColor(BUTTON_COLOR);
            optionBoxes.add(optionBox);
            add(optionBox);

            // El texto del botón
            GLabel optionLabel = new GLabel("");
            optionLabel.setFont(OPTION_FONT);
            optionLabel.setColor(BUTTON_TEXT_COLOR);
            optionLabels.add(optionLabel);
            add(optionLabel); // Se posiciona junto con el texto
        }
    }

    /**
     * Carga las preguntas del juego. En una aplicación real, esto podría venir de un archivo.
     */
    private void loadQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("¿Cuál es la capital de Francia?",
                Arrays.asList("Londres", "Berlín", "París", "Madrid"), 2));
        questions.add(new Question("¿Qué elemento químico tiene el símbolo 'O'?",
                Arrays.asList("Oro", "Oxígeno", "Osmio", "Plata"), 1));
        questions.add(new Question("¿En qué año comenzó la Segunda Guerra Mundial?",
                Arrays.asList("1939", "1941", "1945", "1914"), 0));
        questions.add(new Question("¿Quién escribió 'Cien años de soledad'?",
                Arrays.asList("Mario Vargas Llosa", "Julio Cortázar", "Jorge Luis Borges", "Gabriel García Márquez"), 3));
    }

    /**
     * Actualiza la pantalla con la pregunta actual.
     */
    private void displayCurrentQuestion() {
        Question q = questions.get(currentQuestionIndex);

        // Actualizar el texto de la pregunta
        questionLabel.setLabel(q.getQuestionText());
        
        // Actualizar el texto y la apariencia de los botones de opción
        for (int i = 0; i < q.getOptions().size(); i++) {
            GRect box = optionBoxes.get(i);
            GLabel label = optionLabels.get(i);
            
            box.setFillColor(BUTTON_COLOR);
            label.setLabel(q.getOptions().get(i));
            // Centrar el texto dentro del botón
            double labelX = box.getX() + (box.getWidth() - label.getWidth()) / 2;
            double labelY = box.getY() + (box.getHeight() + label.getAscent()) / 2 - 5;
            label.setLocation(labelX, labelY);
        }

        feedbackLabel.setLabel(""); // Limpiar la retroalimentación anterior
    }

    @Override
    public void mousePressed(MouseEvent e) {
        GObject obj = getElementAt(e.getX(), e.getY());

        if (playAgainButton != null && playAgainButton.contains(e.getPoint())) {
            // Si se hace clic en "Jugar de Nuevo"
            run();
        } else {
            // Si se hace clic en una opción de respuesta
            for (int i = 0; i < optionBoxes.size(); i++) {
                if (obj == optionBoxes.get(i) || obj == optionLabels.get(i)) {
                    handleAnswer(i);
                    return; // Salir después de procesar la respuesta
                }
            }
        }
    }

    /**
     * Procesa la respuesta seleccionada por el usuario.
     * @param selectedIndex El índice de la opción en la que se hizo clic.
     */
    private void handleAnswer(int selectedIndex) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        GRect selectedBox = optionBoxes.get(selectedIndex);

        if (currentQuestion.isCorrect(selectedIndex)) {
            score++;
            scoreValueLabel.setLabel(String.valueOf(score));
            selectedBox.setFillColor(CORRECT_COLOR);
            feedbackLabel.setLabel("¡Correcto!");
            feedbackLabel.setColor(CORRECT_COLOR);
        } else {
            selectedBox.setFillColor(INCORRECT_COLOR);
            // Mostrar la respuesta correcta
            optionBoxes.get(currentQuestion.isCorrect(0) ? 0 : currentQuestion.isCorrect(1) ? 1 : currentQuestion.isCorrect(2) ? 2 : 3).setFillColor(CORRECT_COLOR);
            feedbackLabel.setLabel("Incorrecto");
            feedbackLabel.setColor(INCORRECT_COLOR);
        }

        // Centrar la etiqueta de retroalimentación
        feedbackLabel.setLocation((getWidth() - feedbackLabel.getWidth()) / 2, 480);
        
        pause(1500); // Pausa para que el usuario vea la retroalimentación
        
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            displayCurrentQuestion();
        } else {
            showFinalScreen();
        }
    }

    /**
     * Muestra la pantalla de fin de juego con la puntuación final.
     */
    private void showFinalScreen() {
        removeAll();
        GLabel gameOverLabel = new GLabel("Juego Terminado");
        gameOverLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        add(gameOverLabel, (getWidth() - gameOverLabel.getWidth()) / 2, 150);

        GLabel finalScoreLabel = new GLabel("Tu puntuación final: " + score + " / " + questions.size());
        finalScoreLabel.setFont(QUESTION_FONT);
        add(finalScoreLabel, (getWidth() - finalScoreLabel.getWidth()) / 2, 250);

        // Botón para jugar de nuevo
        playAgainButton = new GRect(200, 70);
        playAgainButton.setFilled(true);
        playAgainButton.setFillColor(BUTTON_COLOR);
        add(playAgainButton, (getWidth() - playAgainButton.getWidth()) / 2, 350);

        GLabel playAgainLabel = new GLabel("Jugar de Nuevo");
        playAgainLabel.setFont(OPTION_FONT);
        playAgainLabel.setColor(BUTTON_TEXT_COLOR);
        add(playAgainLabel, (getWidth() - playAgainLabel.getWidth()) / 2, 350 + (playAgainButton.getHeight() + playAgainLabel.getAscent()) / 2 - 5);
    }
}