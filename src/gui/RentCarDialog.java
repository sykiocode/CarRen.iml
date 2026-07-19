package gui;

import api.Car;
import api.Customer;
import api.RentalService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentCarDialog extends JDialog {
    private RentalService service;

    private JComboBox<String> boxCustomers;
    private JComboBox<String> boxCars;

    private JTextField txtStartDate;
    private JTextField txtEndDate;

    private JTextField txtSearchCust;
    private JTextField txtSearchCar;
    private JButton btnSearchCust;
    private JButton btnSearchCar;

    private List<Customer> customerList;
    private List<Car> availableCarList;

    public RentCarDialog(JFrame parent,RentalService service){
        super(parent,"Νέα Ενοικίαση",true);
        this.service=service;

        setSize(500,600);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(7,2,10,10));

        add(new JLabel("Αναζήτηση Πελάτη:",SwingConstants.RIGHT));
        JPanel custSearchPanel=new JPanel(new BorderLayout());
        txtSearchCust=new JTextField();
        btnSearchCust=new JButton("Ψάξε");
        custSearchPanel.add(txtSearchCust,BorderLayout.CENTER);
        custSearchPanel.add(btnSearchCust,BorderLayout.EAST);
        add(custSearchPanel);

        add(new JLabel("Επιλογή Πελάτη:",SwingConstants.RIGHT));
        boxCustomers=new JComboBox<>();
        add(boxCustomers);

        add(new JLabel("Αναζήτηση Αυτοκινήτου:",SwingConstants.RIGHT));
        JPanel carSearchPanel=new JPanel(new BorderLayout());
        txtSearchCar=new JTextField();
        btnSearchCar=new JButton("Ψάξε");
        carSearchPanel.add(txtSearchCar,BorderLayout.CENTER);
        carSearchPanel.add(btnSearchCar,BorderLayout.EAST);
        add(carSearchPanel);

        add(new JLabel("Επιλογή Αυτοκινήτου:",SwingConstants.RIGHT));
        boxCars=new JComboBox<>();
        add(boxCars);

        add(new JLabel("Ημορομηνία Έναρξης (DD/MM/YYYY):",SwingConstants.RIGHT));
        txtStartDate=new JTextField();
        add(txtStartDate);


        add(new JLabel("Ημορομηνία Λήξης (DD/MM/YYYY):",SwingConstants.RIGHT));
        txtEndDate=new JTextField();
        add(txtEndDate);

        JButton btnRent=new JButton("Ενοικίαση");
        JButton btnCancel=new JButton("Ακύρωση");
        add(btnRent);
        add(btnCancel);

        filterCustomers("");
        filterCars("");

        btnSearchCust.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterCustomers(txtSearchCust.getText().trim());
            }
        });

        btnSearchCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterCars(txtSearchCar.getText().trim());
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnRent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(boxCustomers.getSelectedIndex()==-1 || boxCars.getSelectedIndex()==-1){
                    JOptionPane.showMessageDialog(RentCarDialog.this,"Παρακαλώ επιλέξτε πελάτη και όχημα.");
                    return;
                }

                String start=txtStartDate.getText().trim();
                String end=txtEndDate.getText().trim();

                if(start.isEmpty() || end.isEmpty()){
                    JOptionPane.showMessageDialog(RentCarDialog.this,"Παρακαλώ συμπληρώστε τις ημερομηνίες.");
                    return;
                }

                SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);

                try{
                    Date startDate =sdf.parse(start);
                    Date endDate=sdf.parse(end);

                    if(endDate.before(startDate)){
                        JOptionPane.showMessageDialog(RentCarDialog.this,
                                "Η ημερομηνία λήξης δεν μπορεί να είναι πριν την έναρξη!",
                                "Λάθος Ημερομηνία",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(RentCarDialog.this,
                            "Λάθος μορφή ημερομηνίας!\nΠαρακαλώ γράψτε τη μορφή: ΗΗ/ΜΜ/ΕΕΕΕ",
                            "Σφάλμα",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Customer selectedCustomer=customerList.get(boxCustomers.getSelectedIndex());
                Car selectedCar=availableCarList.get(boxCars.getSelectedIndex());

                try{
                    service.rentACar(selectedCustomer,selectedCar,start,end);

                    JOptionPane.showMessageDialog(RentCarDialog.this,"Η ενοικίαση ολοκληρώθηκε επιτυχώς!");
                    dispose();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(RentCarDialog.this,ex.getMessage());
                }
            }
        });

    }

    private void loadData(){
        customerList= new ArrayList<>(service.getCustomers());
        boxCustomers.removeAllItems();

        for (Customer customer : customerList) {
            boxCustomers.addItem(customer.getName()+" "+customer.getSurname()+" ("+customer.getAfm()+")");
        }

        availableCarList=new ArrayList<>();
        boxCars.removeAllItems();

        for (Car car : service.getCars()) {
            if(car.isAvailable()){
                availableCarList.add(car);
                boxCars.addItem(car.getBrand()+" "+car.getModel()+" "+car.getYear()+" ("+car.getPlate()+")");
            }
        }

        if(availableCarList.isEmpty()){
            boxCars.addItem("Δεν υπάρχουν διαθέσιμα αυτοκίνητα");
            boxCars.setEnabled(false);
        }
    }

    private void filterCustomers(String s){
        customerList=new ArrayList<>();
        boxCustomers.removeAllItems();
        String key=s.toLowerCase();


        for (Customer customer : service.getCustomers()) {
            String displayStr=customer.getName()+" "+customer.getSurname()+" ("+customer.getAfm()+")";
            if(key.isEmpty()|| displayStr.toLowerCase().contains(key)) {
                customerList.add(customer);
                boxCustomers.addItem(displayStr);
            }
        }
    }

    private void filterCars(String s){
        availableCarList=new ArrayList<>();
        boxCars.removeAllItems();

        String key=s.toLowerCase();

        for(Car car: service.getCars()){
            if(car.isAvailable()){
                String displayStr= car.getBrand()+" "+car.getModel()+" ("+ car.getPlate()+")";
                if(key.isEmpty()|| displayStr.toLowerCase().contains(key)){
                    availableCarList.add(car);
                    boxCars.addItem(displayStr);
                }
            }
        }

        if(availableCarList.isEmpty()){
            boxCars.addItem("Δεν βρέθηκαν αυτοκίνητα");
            boxCars.setEnabled(false);
        }else
            boxCars.setEnabled(true);
    }
}
