package api;
import java.io.Serializable;

/**
 * Η κλάση {@code Car} αναπαριστά ένα αυτοκίνητο προς ενοικίαση στο σύστημα.
 * <p>
 * Περιέχει όλες τις τεχνικές λεπτομέρειες του αυτοκινήτου (Μάρκα, Μοντέλο, Πινακίδα, κλπ.)
 * καθώς και την κατάσταση διαθεσιμότητάς του.
 * Υλοποιεί τη διεπαφή {@link Serializable} για να μπορεί να αποθηκευτεί σε αρχείο.
 * </p>
 */
public class Car implements Serializable {
    private String id;
    private String plate;
    private String brand;
    private String type;
    private String model;
    private String year;
    private String color;
    private boolean available;

    /**
     * Κατασκευαστής της κλάσης Car.
     * Δημιουργεί ένα νέο αυτοκίνητο με όλα τα χαρακτηριστικά του.
     *
     * @param id        Το μοναδικό αναγνωριστικό του οχήματος.
     * @param plate     Η πινακίδα κυκλοφορίας.
     * @param brand     Η μάρκα του αυτοκινήτου (π.χ. Toyota).
     * @param type      Ο τύπος του αυτοκινήτου (π.χ. SUV, Sedan).
     * @param model     Το μοντέλο του αυτοκινήτου (π.χ. Yaris).
     * @param year      Το έτος κατασκευής.
     * @param color     Το χρώμα του αυτοκινήτου.
     * @param available Η κατάσταση διαθεσιμότητας (true = διαθέσιμο, false = ενοικιασμένο).
     */
    public Car(String id, String plate, String brand, String type, String model, String year, String color, boolean available) {
        this.id = id;
        this.plate = plate;
        this.brand = brand;
        this.type = type;
        this.model = model;
        this.year = year;
        this.color = color;
        this.available = available;
    }

    /**
     * Επιστρέφει το μοναδικό ID του αυτοκινήτου.
     *
     * @return Το ID ως String.
     */
    public String getId() {
        return id;
    }

    /**
     * Επιστρέφει την πινακίδα κυκλοφορίας.
     *
     * @return Η πινακίδα ως String.
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Επιστρέφει τη μάρκα του αυτοκινήτου.
     *
     * @return Η μάρκα ως String.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Επιστρέφει τον τύπο του αυτοκινήτου.
     *
     * @return Ο τύπος ως String.
     */
    public String getType() {
        return type;
    }

    /**
     * Επιστρέφει το μοντέλο του αυτοκινήτου.
     *
     * @return Το μοντέλο ως String.
     */
    public String getModel() {
        return model;
    }

    /**
     * Επιστρέφει το έτος κατασκευής.
     *
     * @return Το έτος ως String.
     */
    public String getYear() {
        return year;
    }

    /**
     * Επιστρέφει το χρώμα του αυτοκινήτου.
     *
     * @return Το χρώμα ως String.
     */
    public String getColor() {
        return color;
    }

    /**
     * Ελέγχει αν το αυτοκίνητο είναι διαθέσιμο προς ενοικίαση.
     *
     * @return {@code true} αν είναι διαθέσιμο, αλλιώς {@code false}.
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Ενημερώνει τη μάρκα του αυτοκινήτου.
     *
     * @param brand Η νέα μάρκα.
     * @throws Exception Εάν το πεδίο είναι κενό.
     */
    public void setBrand(String brand) throws Exception {
        if(brand==null || brand.trim().isEmpty())
            throw new Exception("Το πεδίο δεν μπορεί να είναι κενό");
        this.brand = brand;
    }

    /**
     * Ενημερώνει τον τύπο του αυτοκινήτου.
     *
     * @param type Ο νέος τύπος.
     * @throws Exception Εάν το πεδίο είναι κενό.
     */
    public void setType(String type)throws Exception {
        if(type==null || type.trim().isEmpty())
            throw new Exception("Το πεδίο δεν μπορεί να είναι κενό");
        this.type = type;
    }

    /**
     * Ενημερώνει το μοντέλο του αυτοκινήτου.
     *
     * @param model Το νέο μοντέλο.
     * @throws Exception Εάν το πεδίο είναι κενό.
     */
    public void setModel(String model)throws Exception {
        if(model==null|| model.trim().isEmpty())
            throw new Exception("Το πεδίο δεν μπορεί να είναι κενό");
        this.model = model;
    }

    /**
     * Ενημερώνει το έτος κατασκευής μετά από έλεγχο εγκυρότητας.
     *
     * @param year Το έτος ως String.
     * @throws Exception Εάν το πεδίο είναι κενό ή δεν αποτελεί έγκυρο ακέραιο αριθμό.
     */
    public void setYear(String year)throws Exception {
        if(year==null || year.trim().isEmpty())
            throw new Exception("Το πεδίο δεν μπορεί να είναι κενό");
        try{
            Integer.parseInt(year);
        }catch (Exception ex){
            throw new Exception("Το πεδίο 'Έτος' πρέπει να είναι ακέραιος");
        }
        this.year = year;
    }

    /**
     * Ενημερώνει το χρώμα του αυτοκινήτου μετά από έλεγχο εγκυρότητας.
     *
     * @param color Το νέο χρώμα.
     * @throws Exception Εάν το χρώμα είναι κενό (null/empty) ή περιέχει μη επιτρεπτούς χαρακτήρες
     * (π.χ. αριθμούς), όπως ορίζεται από τη μέθοδο {@link RentalService#checkString(String)}.
     */
    public void setColor(String color)throws Exception {
        if(color==null|| color.trim().isEmpty())
            throw new Exception("Το πεδίο δεν μπορεί να είναι κενό");
        if(!RentalService.checkString(color))
            throw new Exception("Το χρώμα πρέπει να περιέχει μόνο γράμματα!");
        this.color = color;
    }

    /**
     * Ενημερώνει την κατάσταση διαθεσιμότητας του αυτοκινήτου.
     *
     * @param available {@code true} για διαθέσιμο, {@code false} για μη διαθέσιμο.
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }
}
