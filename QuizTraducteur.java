import java.io.*;
import java.util.*;

/**
 * Programme de quiz de traduction du français vers l'anglais.
 * 
 * Ce programme lit un fichier contenant des phrases en français et leur traduction
 * en anglais, puis les affiche une par une de manière aléatoire. L'utilisateur doit
 * entrer la traduction anglaise, et le programme vérifie la réponse. À la fin, il
 * affiche le score et les erreurs.
 * 
 * Format du fichier `phrases.txt` :
 * <pre>
 * Bonjour=Hello
 * Comment ça va ?=How are you?
 * Merci=Thank you
 * </pre>
 * 
 * @author Legoshii
 * @version 1.0
 */
public class QuizTraducteur {

    /**
     * Charge les phrases du fichier texte et les stocke dans une Map.
     *
     * @param fichierNom Le nom du fichier contenant les phrases.
     * @return Une Map associant les phrases françaises à leur traduction anglaise.
     * @throws IOException Si une erreur survient lors de la lecture du fichier.
     */
    private static Map<String, String> chargerPhrases(String fichierNom) throws IOException {
        Map<String, String> phrases = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fichierNom))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parts = ligne.split("=", 2);
                if (parts.length == 2) {
                    phrases.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
        return phrases;
    }

    /**
     * Joue le quiz en demandant à l'utilisateur de traduire les phrases.
     *
     * @param phrases Une Map contenant les phrases françaises et leurs traductions.
     */
    private static void jouerQuiz(Map<String, String> phrases) {
        if (phrases.isEmpty()) {
            System.out.println("Aucune phrase trouvée dans le fichier.");
            return;
        }

        List<String> listePhrases = new ArrayList<>(phrases.keySet());
        Collections.shuffle(listePhrases);
        List<String> erreurs = new ArrayList<>();
        int score = 0, total = 0;

        Scanner scanner = new Scanner(System.in);

        for (String phrase : listePhrases) {
            System.out.println("\nTraduisez : " + phrase);
            String reponse = scanner.nextLine().trim();

            if (reponse.equalsIgnoreCase(phrases.get(phrase))) {
                System.out.println("✅ Correct !");
                score++;
            } else {
                System.out.println("❌ Mauvaise réponse. La bonne traduction est : " + phrases.get(phrase));
                erreurs.add(phrase + " → " + phrases.get(phrase) + " (Votre réponse : " + reponse + ")");
            }
            total++;
        }

        afficherResultats(score, total, erreurs);
        scanner.close();
    }

    /**
     * Affiche le score final et les erreurs éventuelles.
     *
     * @param score  Nombre de bonnes réponses.
     * @param total  Nombre total de questions.
     * @param erreurs Liste des erreurs avec leurs corrections.
     */
    private static void afficherResultats(int score, int total, List<String> erreurs) {
        System.out.println("\n🔹 Score final : " + score + "/" + total);
        if (!erreurs.isEmpty()) {
            System.out.println("\n❗ Erreurs :");
            for (String erreur : erreurs) {
                System.out.println(erreur);
            }
        } else {
            System.out.println("🎉 Bravo, aucune erreur !");
        }
    }

    /**
     * Point d'entrée principal du programme.
     *
     * @param args Arguments de la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        try {
            Map<String, String> phrases = chargerPhrases("phrases.txt");
            jouerQuiz(phrases);
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier : " + e.getMessage());
        }
    }
}
