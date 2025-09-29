import com.google.gson.Gson;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Classe per effettuare le richieste Http al client
public class ApiClient {
    private final HttpClient client =  HttpClient.newHttpClient();

    // Creazione e invio della richiesta
    public String fetchQuestions(int amount, String type, String difficulty) {
        // Struttura url = https://opentdb.com/api.php?amount=5&difficulty=easy&type=multiple
        String url = "https://opentdb.com/api.php?amount=" + amount + "&difficulty=" + difficulty + "&type=" +  type;

        HttpRequest request = HttpRequest.newBuilder()      // newBuilder() è un oggetto che permette di costruire passo passo una richiesta HTTP personalizzata
                .header("Content-Type", "application/json")     // Tipo di contenuto della richiesta
                .uri(java.net.URI.create(url))  // L'URI (ind. della risorsa) della richiesta, con URI.create(url) trasfoma l'url in un oggetto URI valido
                .GET()                          // Il metodo della richiesta HTTP è GET (lettura dal server)
                .build();                       // Costruisce l'oggetto finale HttpRequest

        HttpResponse<String> response;
        try { // Invio della richiesta HTTP
            response = client.send(request, HttpResponse.BodyHandlers.ofString()); // Il client dice al server che la risposta deve essere gestita come una stringa
        } catch(IOException | InterruptedException e) { // Errore di comunicazione o thread interrotto durante l'attesa
            throw new RuntimeException("Errore nella richiesta API! Impossibile recuperare le domande: " +  e.getMessage(), e);
        }

        if(response == null){
            throw new RuntimeException("Nessuna risposta ricevuta dall'API!");
        }

        // Parsing della risposta con Gson

        // Creazione di un oggetto Gson per conversione JSON -> Java Objects
        Gson gson = new Gson();
        // Prende il corpo della risposta (JSON come stringa), lo converte in un oggetto ApiResponse, con .class indica come e' fatto l'oggetto
        ApiResponse apiResponse = gson.fromJson(response.body(), ApiResponse.class);

        // Ora possiamo accedere alle domande come oggetti Java
        for (ApiQuestion question : apiResponse.results) {
            System.out.println(question.question);
            System.out.println("Risposta corretta: " + question.correct_answer);
        }

        return response.body();
    }
}
