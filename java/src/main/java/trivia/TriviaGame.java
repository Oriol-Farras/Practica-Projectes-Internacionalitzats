package trivia;

import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private GLabel playAgainLabelText;

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

    private void setupGame() {
        removeAll();
        optionBoxes.clear();
        optionLabels.clear();
        playAgainButton = null;
        playAgainLabelText = null;

        score = 0;
        currentQuestionIndex = 0;
        loadQuestions();
        setupUI();
    }
    
    private void setupUI() {
        questionLabel = new GLabel("");
        questionLabel.setFont(QUESTION_FONT);
        add(questionLabel, 50, 80);

        GLabel scoreTextLabel = new GLabel("Puntuación:");
        scoreTextLabel.setFont(SCORE_FONT);
        add(scoreTextLabel, getWidth() - 150, 40);

        scoreValueLabel = new GLabel(String.valueOf(score));
        scoreValueLabel.setFont(SCORE_FONT);
        add(scoreValueLabel, getWidth() - 70, 40);

        feedbackLabel = new GLabel("");
        feedbackLabel.setFont(FEEDBACK_FONT);
        add(feedbackLabel);
        
        double startY = 150;
        for (int i = 0; i < 4; i++) {
            double y = startY + i * (BUTTON_HEIGHT + BUTTON_SPACING);
            
            GRect optionBox = new GRect((getWidth() - BUTTON_WIDTH) / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT);
            optionBox.setFilled(true);
            optionBox.setFillColor(BUTTON_COLOR);
            optionBoxes.add(optionBox);
            add(optionBox);

            GLabel optionLabel = new GLabel("");
            optionLabel.setFont(OPTION_FONT);
            optionLabel.setColor(BUTTON_TEXT_COLOR);
            optionLabels.add(optionLabel);
            add(optionLabel);
        }
    }

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

    private void displayCurrentQuestion() {
        Question q = questions.get(currentQuestionIndex);

        questionLabel.setLabel(q.getQuestionText());
        
        for (int i = 0; i < q.getOptions().size(); i++) {
            GRect box = optionBoxes.get(i);
            GLabel label = optionLabels.get(i);
            
            box.setFillColor(BUTTON_COLOR);
            box.setVisible(true);
            label.setLabel(q.getOptions().get(i));
            
            double labelX = box.getX() + (box.getWidth() - label.getWidth()) / 2;
            double labelY = box.getY() + (box.getHeight() + label.getAscent()) / 2 - 5;
            label.setLocation(labelX, labelY);
            label.setVisible(true);
        }

        feedbackLabel.setLabel("");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // LA LÍNEA CRÍTICA ESTÁ AQUÍ, YA CORREGIDA
        GObject obj = getElementAt(e.getX(), e.getY());

        if (playAgainButton != null && (obj == playAgainButton || obj == playAgainLabelText)) {
            run();
        } else {
            for (int i = 0; i < optionBoxes.size(); i++) {
                if (obj == optionBoxes.get(i) || obj == optionLabels.get(i)) {
                    if (feedbackLabel.getLabel().equals("")) {
                        handleAnswer(i);
                    }
                    return;
                }
            }
        }
    }

    private void handleAnswer(int selectedIndex) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        
        if (currentQuestion.isCorrect(selectedIndex)) {
            score++;
            scoreValueLabel.setLabel(String.valueOf(score));
            optionBoxes.get(selectedIndex).setFillColor(CORRECT_COLOR);
            feedbackLabel.setLabel("¡Correcto!");
            feedbackLabel.setColor(CORRECT_COLOR);
        } else {
            optionBoxes.get(selectedIndex).setFillColor(INCORRECT_COLOR);
            optionBoxes.get(currentQuestion.isCorrect(0) ? 0 : currentQuestion.isCorrect(1) ? 1 : currentQuestion.isCorrect(2) ? 2 : 3).setFillColor(CORRECT_COLOR);
            feedbackLabel.setLabel("Incorrecto");
            feedbackLabel.setColor(INCORRECT_COLOR);
        }

        feedbackLabel.setLocation((getWidth() - feedbackLabel.getWidth()) / 2, 480);
        
        pause(1500);
        
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            displayCurrentQuestion();
        } else {
            showFinalScreen();
        }
    }

    private void showFinalScreen() {
        removeAll();
        GLabel gameOverLabel = new GLabel("Juego Terminado");
        gameOverLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        add(gameOverLabel, (getWidth() - gameOverLabel.getWidth()) / 2, 150);

        GLabel finalScoreLabel = new GLabel("Tu puntuación final: " + score + " / " + questions.size());
        finalScoreLabel.setFont(QUESTION_FONT);
        add(finalScoreLabel, (getWidth() - finalScoreLabel.getWidth()) / 2, 250);

        playAgainButton = new GRect(200, 70);
        playAgainButton.setFilled(true);
        playAgainButton.setFillColor(BUTTON_COLOR);
        add(playAgainButton, (getWidth() - playAgainButton.getWidth()) / 2, 350);

        playAgainLabelText = new GLabel("Jugar de Nuevo");
        playAgainLabelText.setFont(OPTION_FONT);
        playAgainLabelText.setColor(BUTTON_TEXT_COLOR);
        add(playAgainLabelText, (getWidth() - playAgainLabelText.getWidth()) / 2, 350 + (playAgainButton.getHeight() + playAgainLabelText.getAscent()) / 2 - 5);
    }
}