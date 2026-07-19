package api;

import java.io.Serializable;

/**
 * Η αφηρημένη κλάση {@code Person} αναπαριστά μια γενική οντότητα ατόμου στο σύστημα.
 * <p>
 * Λειτουργεί ως γονική κλάση (base class) για τους {@link Customer} και {@link Employee},
 * διατηρώντας τα κοινά χαρακτηριστικά όπως όνομα, επώνυμο και email.
 * Υλοποιεί τη διεπαφή {@link Serializable} ώστε τα αντικείμενα να μπορούν να αποθηκευτούν σε αρχείο.
 * </p>
 */
public abstract class Person implements Serializable {
    private String name;
    private String surname;
    private String email;


    /**
     * Κατασκευαστής της κλάσης Person.
     * Αρχικοποιεί ένα νέο άτομο με τα βασικά του στοιχεία.
     * @param name     Το όνομα του ατόμου.
     * @param surname  Το επώνυμο του ατόμου.
     * @param email    Η διεύθυνση ηλεκτρονικού ταχυδρομείου του ατόμου.
     */
    public Person(String name,String surname,String email){
        this.name=name;
        this.surname=surname;
        this.email=email;
    }

    /**
     * Επιστρέφει το όνομα του ατόμου
     *
     * @return Το όνομα ως String.
     */
    public String getName() {
        return name;
    }

    /**
     * Επιστρέφει το επώνυμο του ατόμου.
     *
     * @return Το επώνυμο ως String.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Επιστρέφει το email του ατόμου.
     *
     * @return Το email ως String.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Ενημερώνει το όνομα του ατόμου μετά από έλεγχο εγκυρότητας.
     *
     * @param name Το νέο όνομα.
     * @throws Exception Εάν το όνομα είναι κενό (null/empty) ή περιέχει μη επιτρεπτούς χαρακτήρες
     * (π.χ. αριθμούς), όπως ορίζεται από τη μέθοδο {@link RentalService#checkString(String)}.
     */
    public void setName(String name)throws Exception {
        if(name==null||name.trim().isEmpty())
            throw new Exception("Το πεδίο δεν μπορεί να είναι κενό");
        if(!RentalService.checkString(name))
            throw new Exception("Το όνομα πρέπει να περιέχει μόνο γράμματα!");
        this.name = name;
    }

    /**
     * Ενημερώνει το επώνυμο του ατόμου μετά από έλεγχο εγκυρότητας.
     *
     * @param surname Το νέο επώνυμο.
     * @throws Exception Εάν το επώνυμο είναι κενό (null/empty) ή περιέχει μη επιτρεπτούς χαρακτήρες
     * (π.χ. αριθμούς), όπως ορίζεται από τη μέθοδο {@link RentalService#checkString(String)}.
     */
    public void setSurname(String surname)throws Exception {
        if(surname==null || surname.trim().isEmpty())
            throw new Exception("Το πεδίο δεν μπορεί να είναι κενό");
        if(!RentalService.checkString(surname))
            throw new Exception("Το επώνυμο πρέπει να περιέχει μόνο γράμματα!");
        this.surname = surname;
    }

    /**
     * Ενημερώνει το email του ατόμου.
     *
     * @param email Το νέο email.
     * @throws Exception Εάν το πεδίο του email είναι κενό (null/empty).
     */
    public void setEmail(String email)throws Exception {
        if(email==null||email.trim().isEmpty())
            throw new Exception("Το πεδίο δεν μπορεί να είναι κενό");
        this.email = email;
    }
}
