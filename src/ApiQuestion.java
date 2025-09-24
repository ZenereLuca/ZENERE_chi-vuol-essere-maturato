import java.util.List;

public class ApiQuestion {
    public String category;
    public String type;         // "multiple2 o "boolean"
    public String difficulty;   // "easy", "medium", "hard"
    public String question;
    public String correct_answer;
    public List<String> incorrect_answers;
}
