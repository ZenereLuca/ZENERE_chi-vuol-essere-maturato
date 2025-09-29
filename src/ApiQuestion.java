import java.util.List;

// Classe che rappresenta la struttura di una domanda JSON
public class ApiQuestion {
    public String category;
    public String type;         // "multiple" o "boolean"
    public String difficulty;   // "easy", "medium", "hard"
    public String question;
    public String correct_answer;
    public List<String> incorrect_answers;
}
