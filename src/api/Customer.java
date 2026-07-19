package api;


import java.util.ArrayList;
import java.util.List;

/**
 * Η κλάση {@code Customer} αναπαριστά έναν πελάτη της εταιρείας ενοικιάσεων.
 * <p>
 * Κληρονομεί από την κλάση {@link Person} και αποθηκεύει επιπλέον πληροφορίες
 * απαραίτητες για την ενοικίαση, όπως το ΑΦΜ και το τηλέφωνο επικοινωνίας.
 * </p>
 */
public class Customer extends Person {
    private String afm;
    private String phone;

    /**
     * Κατασκευαστής της κλάσης Customer.
     * Αρχικοποιεί έναν νέο πελάτη με όλα τα απαραίτητα στοιχεία.
     *
     * @param name    Το όνομα του πελάτη.
     * @param surname Το επώνυμο του πελάτη.
     * @param email   Το email του πελάτη.
     * @param afm     Ο Αριθμός Φορολογικού Μητρώου (ΑΦΜ) του πελάτη.
     * @param phone   Ο αριθμός τηλεφώνου του πελάτη.
     */
    public Customer(String name,String surname, String email, String afm, String phone) {
        super(name,surname, email);
        this.afm = afm;
        this.phone = phone;
    }

    /**
     * Επιστρέφει το ΑΦΜ του πελάτη.
     *
     * @return Το ΑΦΜ ως String.
     */
    public String getAfm() {
        return afm;
    }

    /**
     * Επιστρέφει το τηλέφωνο του πελάτη.
     *
     * @return Το τηλέφωνο ως String.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Ενημερώνει το ΑΦΜ του πελάτη.
     *
     * @param afm Το νέο ΑΦΜ.
     */
    public void setAfm(String afm){
        this.afm = afm;
    }

    /**
     * Ενημερώνει το τηλέφωνο του ατόμου μετά από έλεγχο εγκυρότητας.
     *
     * @param phone Το νέο τηλέφωνο.
     * @throws Exception Εάν το πεδίο είναι κενό ή αν περιέχει μη αριθμητικούς χαρακτήρες.
     */
    public void setPhone(String phone)throws Exception {
        if(phone==null||phone.trim().isEmpty())
            throw new Exception("Το πεδίο δεν μπορεί να είναι κενό");
        try{
            Long.parseLong(phone);
        }catch (Exception ex){
            throw new Exception("Το πεδίο 'Τηλέφωνο' πρέπει να είναι ακέραιος");
        }
        this.phone = phone;
    }
}
