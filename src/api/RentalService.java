package api;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Η κλάση {@code RentalService} αποτελεί τον κεντρικό ελεγκτή (Controller/Service) της εφαρμογής.
 * Διαχειρίζεται όλες τις λίστες δεδομένων (Αυτοκίνητα, Πελάτες, Υπάλληλοι, Ενοικιάσεις)
 * και περιέχει την επιχειρησιακή λογική (Business Logic) για:
 *
 * <ul>
 * <li>Την προσθήκη/επεξεργασία/διαγραφή οντοτήτων.</li>
 * <li>Τη διαδικασία ενοικίασης και επιστροφής αυτοκινήτων.</li>
 * <li>Την αναζήτηση δεδομένων.</li>
 * <li>Την αποθήκευση και φόρτωση δεδομένων σε αρχεία (Serialization and Text files).</li>
 * <li>Τη διαχείριση της συνεδρίας του χρήστη (Login/Logout).</li>
 * </ul>
 */
public class RentalService implements Serializable{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Employee> employees;
    private List<Rent> rents;
    private Employee loggedInEmployee;

    /**
     * Κατασκευαστής της κλάσης.
     * <p>
     * Φορτώνει τα αρχικά δεδομένα αυτοκινήτων και υπαλλήλων από αρχεία κειμένου (.txt),
     * αρχικοποιεί τις λίστες και αποθηκεύει την αρχική κατάσταση στο αρχείο δεδομένων (.dat).
     * </p>
     *
     * @throws IOException Εάν υπάρξει σφάλμα κατά την ανάγνωση των αρχείων.
     */
    public RentalService()throws IOException{
        startDataCars();
        startDataEmployees();
        this.customers = new ArrayList<>();
        this.rents = new ArrayList<>();
        customers.add(new Customer("Ege","Kots","egekoc@gmail.com","21345693","698123456"));
        customers.add(new Customer("Dimitris","Sykiotis","dimsykio@gmail.com","42147581","698765432"));
        saveData();
    }

    /**
     * Επιστρέφει τη λίστα με όλα τα αυτοκίνητα.
     * @return Λίστα αντικειμένων {@link Car}.
     */
    public List<Car> getCars() {
        return cars;
    }

    /**
     * Επιστρέφει τη λίστα με όλους τους πελάτες.
     * @return Λίστα αντικειμένων {@link Customer}.
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Επιστρέφει τη λίστα με όλους τους υπαλλήλους.
     * @return Λίστα αντικειμένων {@link Employee}.
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Επιστρέφει τη λίστα με το ιστορικό ενοικιάσεων.
     * @return Λίστα αντικειμένων {@link Rent}.
     */
    public List<Rent> getRents() {
        return rents;
    }

    /**
     * Στατική μέθοδος ελέγχου εγκυρότητας συμβολοσειράς.
     * Ελέγχει αν το string περιέχει μόνο αλφαβητικούς χαρακτήρες και κενά.
     *
     * @param s Η συμβολοσειρά προς έλεγχο.
     * @return {@code true} αν είναι έγκυρη, αλλιώς {@code false}.
     */
    static public boolean checkString(String s){
        for (char c : s.toCharArray()) {
            if(!Character.isAlphabetic(c)&&!Character.isWhitespace(c))
                return false;
        }
        return true;
    }

    /**
     * Βοηθητική μέθοδος που επιστρέφει μόνο τις ενεργές ενοικιάσεις.
     *
     * @return Λίστα με ενεργά {@link Rent}.
     */
    private List<Rent> getActiveRents(){
        List<Rent> temp=new ArrayList<>();
        for (Rent rent : rents) {
            if(rent.isActive())
                temp.add(rent);
        }
        return temp;
    }

    /**
     * Ελέγχει αν ένα συγκεκριμένο αυτοκίνητο βρίσκεται σε ενεργή ενοικίαση.
     *
     * @param car Το αυτοκίνητο προς έλεγχο.
     * @return {@code true} αν το αυτοκίνητο ενοικιάζεται αυτή τη στιγμή.
     */
    public boolean isCarRented(Car car){
        List<Rent> activeRents=getActiveRents();
        for (Rent activeRent : activeRents) {
            if(activeRent.getCar().getPlate().equals(car.getPlate()))
                return true;
        }
        return false;
    }

    /**
     * Επιστρέφει τον υπάλληλο που είναι συνδεδεμένος αυτή τη στιγμή.
     *
     * @return Το αντικείμενο {@link Employee} ή {@code null} αν δεν υπάρχει σύνδεση.
     */
    public Employee getLoggedInEmployee() {
        return loggedInEmployee;
    }


    /**
     * Διαβάζει τους υπαλλήλους από το αρχείο "users.txt" και γεμίζει τη λίστα.
     *
     * @throws IOException Σε περίπτωση σφάλματος ανάγνωσης αρχείου.
     */
    public void startDataEmployees() throws IOException {
        this.employees=new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader("users.txt"))) {
            String line = buffer.readLine();
            while (line != null) {
                String[] split = line.split(",");
                String name=split[0];
                String surname=split[1];
                String username=split[2];
                String email=split[3];
                String password=split[4];
                employees.add(new Employee(name,surname,username,email,password));
                line = buffer.readLine();
            }
        }
    }

    /**
     * Διαβάζει τα οχήματα από το αρχείο "vehicles_with_plates.txt" και γεμίζει τη λίστα.
     *
     * @throws IOException Σε περίπτωση σφάλματος ανάγνωσης αρχείου.
     */
    public void startDataCars() throws IOException{
        this.cars=new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader("vehicles_with_plates.txt"))) {
            String line = buffer.readLine();
            while (line != null) {
                String[] split = line.split(",");
                String id=split[0];
                String plate=split[1];
                String brand=split[2];
                String type=split[3];
                String model=split[4];
                String year=split[5];
                String color=split[6];
                String available=split[7];
                boolean temp;
                if(available.equals("Διαθέσιμο"))
                    temp=true;
                else
                    temp=false;
                cars.add(new Car(id,plate,brand,type,model,year,color,temp));
                line = buffer.readLine();
            }
        }
    }

    /**
     * Αποθηκεύει την τρέχουσα κατάσταση του {@code RentalService} στο αρχείο "data.dat".
     * Χρησιμοποιεί  Serialization.
     *
     * @throws IOException Σε περίπτωση σφάλματος εγγραφής στο δίσκο.
     */
    public void saveData()throws IOException{
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.dat"));) {
            oos.writeObject(this);
        }
    }

    /**
     * Φορτώνει την κατάσταση της εφαρμογής από το αρχείο "data.dat".
     *
     * @return Ένα αντικείμενο {@code RentalService} με τα αποθηκευμένα δεδομένα.
     * @throws IOException            Σε περίπτωση σφάλματος ανάγνωσης.
     * @throws ClassNotFoundException Σε περίπτωση ασυμβατότητας της κλάσης.
     */
    public static RentalService loadData()throws IOException,ClassNotFoundException{
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.dat"));) {
            return (RentalService) ois.readObject();
        }
    }

    /**
     * Προσθέτει ένα νέο αυτοκίνητο στο σύστημα.
     *
     * @param newCar Το νέο αυτοκίνητο.
     * @throws Exception Εάν το ID ή η πινακίδα υπάρχουν ήδη.
     */
    public void addCar(Car newCar)throws Exception{
        for (Car car : cars) {
            if(car.getId().equals(newCar.getId()))
                throw new Exception("Το id:"+newCar.getId()+" υπάρχει ήδη");
            if(car.getPlate().equals(newCar.getPlate()))
                throw new Exception("Η πινακίδα:"+newCar.getPlate()+" υπάρχει ήδη");
        }
        cars.add(newCar);
        saveData();
    }

    /**
     * Ενημερώνει τα στοιχεία ενός υπάρχοντος αυτοκινήτου.
     *
     * @param id         Το ID του αυτοκινήτου προς επεξεργασία.
     * @param changedCar Ένα προσωρινό αντικείμενο που περιέχει τα νέα στοιχεία.
     * @throws Exception Εάν δεν βρεθεί αυτοκίνητο με αυτό το ID.
     */
    public void changeCar(String id,Car changedCar)throws Exception{
        boolean flag=false;
        for (Car car : cars) {
            if(car.getId().equals(id)) {
                car.setBrand(changedCar.getBrand());
                car.setModel(changedCar.getModel());
                car.setType(changedCar.getType());
                car.setYear(changedCar.getYear());
                car.setColor(changedCar.getColor());
                car.setAvailable(changedCar.isAvailable());
                flag = true;
                break;
            }
        }
        if(!flag)
            throw new Exception("Δεν υπάρχει αυτοκίνητο με id:"+id);
        else
            saveData();
    }

    /**
     * Προσθέτει έναν νέο πελάτη στο σύστημα.
     *
     * @param newCustomer Ο νέος πελάτης.
     * @throws Exception Εάν το ΑΦΜ υπάρχει ήδη.
     */
    public void addCustomer(Customer newCustomer)throws Exception{
        for (Customer customer : customers) {
            if(customer.getAfm().equals(newCustomer.getAfm()))
                throw new Exception("Το ΑΦΜ:"+newCustomer.getAfm()+" υπάρχει ήδη");
        }
        customers.add(newCustomer);
        saveData();
    }

    /**
     * Ενημερώνει τα στοιχεία ενός υπάρχοντος πελάτη.
     *
     * @param afm             Το ΑΦΜ του πελάτη προς επεξεργασία.
     * @param changedCustomer Ένα προσωρινό αντικείμενο με τα νέα στοιχεία.
     * @throws Exception Εάν δεν βρεθεί πελάτης με αυτό το ΑΦΜ.
     */
    public void changeCustomer(String afm,Customer changedCustomer)throws Exception{
        boolean flag=false;

        for (Customer customer : customers) {
            if(customer.getAfm().equals(afm)){
                customer.setPhone(changedCustomer.getPhone());
                customer.setEmail(changedCustomer.getEmail());
                customer.setName(changedCustomer.getName());
                customer.setSurname(changedCustomer.getSurname());
                flag=true;
                break;
            }
        }

        if(!flag)
            throw new Exception("Δεν υπάρχει πελάτης με ΑΦΜ:"+afm);
        else
            saveData();
    }

    /**
     * Προσθέτει έναν νέο υπάλληλο στο σύστημα.
     *
     * @param newEmployee Ο νέος υπάλληλος.
     * @throws Exception Εάν το email ή το username υπάρχουν ήδη.
     */
    public void addEmployee(Employee newEmployee)throws Exception{
        for (Employee employee : employees) {
            if(employee.getEmail().equals(newEmployee.getEmail()))
                throw new Exception("Το email:"+newEmployee.getEmail()+" υπάρχει ήδη");
            if(employee.getUsername().equals(newEmployee.getUsername()))
                throw new Exception("Το username:"+newEmployee.getUsername()+" υπάρχει ήδη");
        }
        employees.add(newEmployee);
        saveData();
    }

    /**
     * Διαγράφει έναν υπάλληλο από το σύστημα.
     *
     * @param delEmployee Ο υπάλληλος προς διαγραφή.
     * @throws Exception Εάν ο συνδεδεμένος χρήστης προσπαθήσει να διαγράψει τον εαυτό του.
     */
    public void removeEmployee(Employee delEmployee)throws Exception{
        if(delEmployee.getUsername().equals(loggedInEmployee.getUsername()))
            throw new Exception("Δεν μπορείς να διαγράψεις τον εαυτό σου !");
        if(delEmployee.getEmail().equals(loggedInEmployee.getEmail()))
            throw new Exception("Δεν μπορείς να διαγράψεις τον εαυτό σου !");
        employees.remove(delEmployee);
        saveData();
    }

    /**
     * Βοηθητική μέθοδος για ασφαλή μετατροπή string σε lowercase για αναζήτηση.
     *
     * @param val Η τιμή εισόδου.
     * @return Το string σε πεζά ή κενό string αν η είσοδος είναι null.
     */
    private String safeCheck(String val) {
        if (val == null) return "";
        return val.toLowerCase();
    }

    /**
     * Αναζητά αυτοκίνητα με βάση λέξεις-κλειδιά.
     * Ψάχνει σε όλα τα πεδία του αυτοκινήτου (ID, Πινακίδα, Μάρκα, Μοντέλο, κ.λπ.).
     *
     * @param keywords Πίνακας με λέξεις-κλειδιά.
     * @return Λίστα με τα αυτοκίνητα που ταιριάζουν σε ΟΛΕΣ τις λέξεις-κλειδιά.
     */
    public List<Car> searchCars(String[] keywords) {
        List<Car> results = new ArrayList<>();
        for (Car c : cars) {
            boolean matchesAllKeywords = true;
            for (String keyword : keywords) {
                boolean keywordFoundInCar = safeCheck(c.getId()).contains(keyword) ||
                        safeCheck(c.getPlate()).contains(keyword) ||
                        safeCheck(c.getBrand()).contains(keyword) ||
                        safeCheck(c.getModel()).contains(keyword) ||
                        safeCheck(c.getColor()).contains(keyword) ||
                        safeCheck(c.getType()).contains(keyword) ||
                        safeCheck(c.getYear()).contains(keyword);

                if (!keywordFoundInCar) {
                    matchesAllKeywords = false;
                    break;
                }
            }
            if (matchesAllKeywords) {
                results.add(c);
            }
        }
        return results;
    }

    /**
     * Αναζητά πελάτες με βάση λέξεις-κλειδιά.
     * Ψάχνει σε όλα τα πεδία του πελάτη (Όνομα, Επώνυμο, Email, ΑΦΜ, Τηλέφωνο).
     *
     * @param keywords Πίνακας με λέξεις-κλειδιά.
     * @return Λίστα με τους πελάτες που ταιριάζουν σε ΟΛΕΣ τις λέξεις-κλειδιά.
     */
    public List<Customer> searchCustomer(String[] keywords) {
        List<Customer> results = new ArrayList<>();

        for (Customer c : customers) {
            boolean matchesAllKeywords = true;
            for (String keyword : keywords) {
                boolean keywordFoundInCustomer =
                        safeCheck(c.getName()).contains(keyword) ||
                                safeCheck(c.getSurname()).contains(keyword) ||
                                safeCheck(c.getEmail()).contains(keyword) ||
                                safeCheck(c.getAfm()).contains(keyword) ||
                                safeCheck(c.getPhone()).contains(keyword);

                if (!keywordFoundInCustomer) {
                    matchesAllKeywords = false;
                    break;
                }
            }
            if (matchesAllKeywords) {
                results.add(c);
            }
        }
        return results;
    }

    /**
     * Πραγματοποιεί είσοδο (login) ενός υπαλλήλου στο σύστημα.
     *
     * @param username Το όνομα χρήστη.
     * @param password Ο κωδικός πρόσβασης.
     * @throws Exception Εάν τα στοιχεία είναι λάθος.
     */
    public void login(String username,String password)throws Exception{
        boolean flag=false;
        for (Employee employee : employees) {
            if(employee.getUsername().equals(username) && employee.getPassword().equals(password)) {
                loggedInEmployee = employee;
                flag = true;
                break;
            }
        }
        if(!flag)
            throw new Exception("Το username ή κωδικός είναι λάθος");
    }

    /**
     * Αποσυνδέει τον τρέχοντα χρήστη.
     */
    public void logout(){
        loggedInEmployee=null;
    }

    /**
     * Καταχωρεί μια νέα ενοικίαση αυτοκινήτου.
     * Δημιουργεί αντικείμενο {@link Rent} και θέτει το αυτοκίνητο ως μη διαθέσιμο.
     *
     * @param customer  Ο πελάτης που ενοικιάζει.
     * @param car       Το αυτοκίνητο προς ενοικίαση.
     * @param startDate Η ημερομηνία έναρξης.
     * @param endDate   Η ημερομηνία λήξης.
     * @throws Exception Εάν το αυτοκίνητο δεν είναι διαθέσιμο.
     */
    public void rentACar(Customer customer,Car car,String startDate,String endDate)throws Exception{
        if(car.isAvailable()){
            String code=String.valueOf(rents.size()+1);
            Rent rent=new Rent(code,car,customer,startDate,endDate,loggedInEmployee,true);
            car.setAvailable(false);
            rents.add(rent);
            saveData();
        }
        else
            throw new Exception("Το αυτοκίνητο δεν είναι διαθέσιμο");
    }

    /**
     * Καταχωρεί την επιστροφή ενός ενοικιασμένου αυτοκινήτου.
     * Θέτει την ενοικίαση ως ολοκληρωμένη και το αυτοκίνητο ως διαθέσιμο.
     *
     * @param rent Η ενοικίαση που ολοκληρώνεται.
     * @throws Exception Εάν το αυτοκίνητο είναι ήδη διαθέσιμο (λάθος κατάσταση) ή δεν βρεθεί.
     */
    public void returnACar(Rent rent)throws Exception{
        boolean flag=false;
        for (Car car : cars) {
            if(car.getId().equals(rent.getCar().getId())){
                if(car.isAvailable())
                    throw new Exception("Το αυτοκίνητο είναι ήδη διαθέσιμο");
                car.setAvailable(true);
                rent.setActive(false);
                flag=true;
                break;
            }
        }
        if(!flag)
            throw new Exception("Το αυτοκίνητο δεν βρέθηκε στη βάση δεδομένων");
        saveData();
    }

    /**
     * Επιστρέφει το ιστορικό ενοικιάσεων για έναν συγκεκριμένο πελάτη.
     *
     * @param c Ο πελάτης.
     * @return Λίστα με τις ενοικιάσεις του πελάτη.
     */
    public List<Rent> customerHistory(Customer c){
        List<Rent> res=new ArrayList<>();
        for (Rent rent : rents) {
            if(rent.getCustomer().getAfm().equals(c.getAfm()))
                res.add(rent);
        }
        return res;
    }

    /**
     * Επιστρέφει το ιστορικό ενοικιάσεων για ένα συγκεκριμένο αυτοκίνητο.
     *
     * @param c Το αυτοκίνητο.
     * @return Λίστα με τις ενοικιάσεις του αυτοκινήτου.
     */
    public List<Rent> carHistory(Car c){
        List<Rent> res=new ArrayList<>();
        for (Rent rent : rents) {
            if(rent.getCar().getId().equals(c.getId()))
                res.add(rent);
        }
        return res;
    }

}
