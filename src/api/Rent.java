package api;

import java.io.Serializable;

/**
 * Η κλάση {@code Rent} αναπαριστά μια συναλλαγή ενοικίασης στο σύστημα.
 * <p>
 * Συνδέει ένα συγκεκριμένο {@link Car} με έναν {@link Customer} για ένα καθορισμένο χρονικό διάστημα
 * (ημερομηνία έναρξης και λήξης). Καταγράφει επίσης τον {@link Employee} που διαχειρίστηκε
 * την ενοικίαση και την τρέχουσα κατάστασή της (αν είναι ενεργή ή ολοκληρωμένη).
 * Υλοποιεί τη διεπαφή {@link Serializable} για αποθήκευση σε αρχείο.
 * </p>
 */
public class Rent implements Serializable {
    private String code;
    private Car car;
    private Customer customer;
    private String startDate;
    private String endDate;
    private Employee employee;
    private boolean active;

    /**
     * Κατασκευαστής της κλάσης Rent.
     * Δημιουργεί μια νέα εγγραφή ενοικίασης.
     *
     * @param code      Ο μοναδικός κωδικός της ενοικίασης.
     * @param car       Το αντικείμενο του αυτοκινήτου που ενοικιάζεται.
     * @param customer  Το αντικείμενο του πελάτη που κάνει την ενοικίαση.
     * @param startDate Η ημερομηνία έναρξης της ενοικίασης (ως String).
     * @param endDate   Η ημερομηνία λήξης της ενοικίασης (ως String).
     * @param employee  Ο υπάλληλος που καταχώρησε την ενοικίαση.
     * @param active    Η κατάσταση της ενοικίασης ({@code true} αν είναι ενεργή).
     */
    public Rent(String code, Car car, Customer customer, String startDate, String endDate, Employee employee,boolean active) {
        this.code = code;
        this.car = car;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employee = employee;
        this.active=active;
    }

    /**
     * Ελέγχει αν η ενοικίαση είναι ενεργή.
     *
     * @return {@code true} αν το αυτοκίνητο δεν έχει επιστραφεί ακόμα, αλλιώς {@code false}.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Ενημερώνει την κατάσταση της ενοικίασης.
     * <p>
     * Χρησιμοποιείται κυρίως κατά την επιστροφή του οχήματος για να τεθεί σε {@code false}.
     * </p>
     *
     * @param active Η νέα κατάσταση της ενοικίασης.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Επιστρέφει τον μοναδικό κωδικό της ενοικίασης.
     *
     * @return Ο κωδικός ενοικίασης.
     */
    public String getCode() {
        return code;
    }

    /**
     * Επιστρέφει το αυτοκίνητο της ενοικίασης.
     *
     * @return Το αντικείμενο {@link Car}.
     */
    public Car getCar() {
        return car;
    }

    /**
     * Επιστρέφει τον πελάτη της ενοικίασης.
     *
     * @return Το αντικείμενο {@link Customer}.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Επιστρέφει την ημερομηνία έναρξης.
     *
     * @return Η ημερομηνία ως String.
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Επιστρέφει την ημερομηνία λήξης.
     *
     * @return Η ημερομηνία ως String.
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Επιστρέφει τον υπάλληλο που διαχειρίστηκε την ενοικίαση.
     *
     * @return Το αντικείμενο {@link Employee}.
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Ενημερώνει τον κωδικό της ενοικίασης.
     *
     * @param code Ο νέος κωδικός.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Ενημερώνει το αυτοκίνητο της ενοικίασης.
     *
     * @param car Το νέο αντικείμενο αυτοκινήτου.
     */
    public void setCar(Car car) {
        this.car = car;
    }

    /**
     * Ενημερώνει τον πελάτη της ενοικίασης.
     *
     * @param customer Το νέο αντικείμενο πελάτη.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Ενημερώνει την ημερομηνία έναρξης.
     *
     * @param startDate Η νέα ημερομηνία έναρξης.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Ενημερώνει την ημερομηνία λήξης.
     *
     * @param endDate Η νέα ημερομηνία λήξης.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Ενημερώνει τον υπάλληλο της ενοικίασης.
     *
     * @param employee Το νέο αντικείμενο υπαλλήλου.
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
