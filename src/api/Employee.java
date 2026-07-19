package api;


import java.util.Objects;

/**
 * Η κλάση {@code Employee} αναπαριστά έναν υπάλληλο της εταιρείας ενοικιάσεων.
 * <p>
 * Κληρονομεί από την κλάση {@link Person} και προσθέτει λειτουργικότητα για την
 * πρόσβαση στο σύστημα (login), διατηρώντας ένα μοναδικό {@code username} και {@code password}.
 * </p>
 */
public class Employee extends Person {
    private String username;
    private String password;

    /**
     * Κατασκευαστής της κλάσης Employee.
     * Δημιουργεί έναν νέο υπάλληλο με τα προσωπικά του στοιχεία και τα στοιχεία σύνδεσης.
     *
     * @param name     Το όνομα του υπαλλήλου.
     * @param surname  Το επώνυμο του υπαλλήλου.
     * @param username Το μοναδικό όνομα χρήστη (για είσοδο στο σύστημα).
     * @param email    Το email του υπαλλήλου.
     * @param password Ο κωδικός πρόσβασης του υπαλλήλου.
     */
    public Employee(String name,String surname,String username,String email, String password) {
        super(name,surname, email);
        this.username = username;
        this.password = password;
    }

    /**
     * Επιστρέφει το όνομα χρήστη (username) του υπαλλήλου.
     *
     * @return Το username ως String.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Επιστρέφει τον κωδικό πρόσβασης (password) του υπαλλήλου.
     *
     * @return Το password ως String.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Ενημερώνει το όνομα χρήστη για τον υπάλληλο.
     *
     * @param username Το νέο username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Ενημερώνει το κωδικό πρόσβασης για τον υπάλληλο.
     *
     * @param password Ο νέος κωδικός πρόσβασης.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
