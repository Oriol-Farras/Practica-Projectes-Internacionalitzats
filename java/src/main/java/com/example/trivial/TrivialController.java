package com.example.trivial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.*;

public class TrivialController {

    @FXML
    private Label questionLabel;
    @FXML
    private GridPane optionsGrid;
    @FXML
    private Label resultLabel;
    @FXML
    private Button nextButton;
    @FXML
    private ChoiceBox<Locale> languageSelector;
    
    private MainApp mainApp;
    private ResourceBundle bundle;
    private int currentQuestionIndex = 0;
    private int totalQuestions;
    private final List<Button> optionButtons = new ArrayList<>();

    @FXML
    public void initialize() {

        bundle = ResourceBundle.getBundle("com.example.trivial.bundles.messages");
        totalQuestions = Integer.parseInt(bundle.getString("total.questions"));
        
        setupLanguageSelector();
        createAnswerButtons();
        updateUITexts();
        loadQuestion();
    }

    /**
     * Allows MainApp to pass a reference of itself to the controller.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Configures the language selector, its options, and its behavior.
     */
    private void setupLanguageSelector() {

        languageSelector.getItems().addAll(new Locale("es"), new Locale("en"), new Locale("ca"));
        
        languageSelector.setConverter(new StringConverter<>() {
            @Override
            public String toString(Locale locale) {
                if (locale.getLanguage().equals("es")) return "Español";
                if (locale.getLanguage().equals("en")) return "English";
                if (locale.getLanguage().equals("ca")) return "Català";
                return null;
            }
            @Override
            public Locale fromString(String string) { return null; }
        });

        languageSelector.setValue(Locale.getDefault());
        
        languageSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                try {
                    mainApp.loadTrivialScene(newValue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Updates UI texts that do not change with each question (e.g., the "Next" button).
     */
    private void updateUITexts() {
        nextButton.setText(bundle.getString("button.next"));
        questionLabel.setText(bundle.getString("game.loading"));
    }
    
    /**
     * Creates the 4 answer buttons and adds them to the grid. This is done only once.
     */
    private void createAnswerButtons() {
        for (int i = 0; i < 4; i++) {
            Button button = new Button();
            button.setOnAction(this::handleAnswerSelection);
            optionButtons.add(button);
            optionsGrid.add(button, i % 2, i / 2); // Adds to a 2x2 grid
        }
    }
    
    /**
     * Loads the data for the current question from the ResourceBundle.
     */
    private void loadQuestion() {
        resetButtonStyles();
        resultLabel.setText("");
        nextButton.setVisible(false);
        optionsGrid.setDisable(false);
        
        String keyBase = "question." + (currentQuestionIndex + 1);
        questionLabel.setText(bundle.getString(keyBase + ".statement"));

        String correctAnswer = bundle.getString(keyBase + ".correct");
        List<String> options = new ArrayList<>();
        options.add(correctAnswer);
        options.add(bundle.getString(keyBase + ".wrong1"));
        options.add(bundle.getString(keyBase + ".wrong2"));
        options.add(bundle.getString(keyBase + ".wrong3"));
        
        Collections.shuffle(options);

        for (int i = 0; i < optionButtons.size(); i++) {
            Button button = optionButtons.get(i);
            String currentOption = options.get(i);
            button.setText(currentOption);
            button.setUserData(currentOption.equals(correctAnswer));
        }
    }
    
    /**
     * Handles the user clicking an answer button.
     */
    private void handleAnswerSelection(ActionEvent event) {
        optionsGrid.setDisable(true);
        nextButton.setVisible(true);

        Button selectedButton = (Button) event.getSource();
        boolean isCorrect = (boolean) selectedButton.getUserData();

        if (isCorrect) {
            resultLabel.setText(bundle.getString("feedback.correct"));
            resultLabel.setStyle("-fx-text-fill: #2ECC71;");
        } else {
            resultLabel.setText(bundle.getString("feedback.incorrect"));
            resultLabel.setStyle("-fx-text-fill: #E74C3C;");
            selectedButton.getStyleClass().add("wrong-answer");
        }

        for (Button btn : optionButtons) {
            if ((boolean) btn.getUserData()) {
                btn.getStyleClass().add("correct-answer");
                break;
            }
        }
    }

    /**
     * Handles the "Next" button click.
     */
    @FXML
    void nextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < totalQuestions) {
            loadQuestion();
        } else {
            questionLabel.setText(bundle.getString("game.end.title"));
            optionsGrid.setVisible(false);
            nextButton.setVisible(false);
            resultLabel.setText(bundle.getString("game.end.subtitle"));
            resultLabel.setStyle("-fx-text-fill: #F39C12;");
        }
    }
    
    /**
     * Clears feedback styles from the buttons before loading a new question.
     */
    private void resetButtonStyles() {
        for (Button btn : optionButtons) {
            btn.getStyleClass().removeAll("correct-answer", "wrong-answer");
        }
    }
}