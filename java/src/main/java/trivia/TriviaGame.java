package trivia;

import java.util.List;

/**
 * Representa una única pregunta del juego de trivia.
 * Esta clase es un "modelo de datos", no contiene lógica gráfica.
 */
public class Question {
    private final String questionText;
    private final List<String> options;
    private final int correctAnswerIndex;

    /**
     * Construye una nueva pregunta.
     * @param questionText El texto de la pregunta.
     * @param options Una lista de respuestas posibles (String).
     * @param correctAnswerIndex El índice (empezando en 0) de la respuesta correcta en la lista.
     */
    public Question(String questionText, List<String> options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    /**
     * Comprueba si el índice de la respuesta proporcionada es el correcto.
     * @param index El índice de la respuesta seleccionada por el usuario.
     * @return true si es la respuesta correcta, false en caso contrario.
     */
    public boolean isCorrect(int index) {
        return index == correctAnswerIndex;
    }
}