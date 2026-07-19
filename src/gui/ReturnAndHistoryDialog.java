package gui;

import api.Car;
import api.Customer;
import api.Rent;
import api.RentalService;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ReturnAndHistoryDialog extends JDialog {
    private RentalService service;

    private List<Rent> displayedRents;

    private List<Customer> filterCustomers;
    private List<Car> filterCars;

    private JTextField txtSearchCust;
    private JTextField txtSearchCar;

    private JComboBox<String> boxCustomers;
    private JComboBox<String> boxCars;
    private AbstractTableModel model;

    public ReturnAndHistoryDialog(JFrame parent, RentalService service){
        super(parent,"Διαχείρηση & Ιστορικό Ενοικίασεων",true);
        this.service=service;

        this.displayedRents =new ArrayList<>(service.getRents());

        setSize(1000,600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel filterPanel=new JPanel(new GridLayout(3,1,5,5));

        JPanel custPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
        custPanel.add(new JLabel("Αναζήτηση Πελάτη:"));
        txtSearchCust=new JTextField(15);
        custPanel.add(txtSearchCust);
        JButton btnSearchCust=new JButton("Ψάξε");
        custPanel.add(btnSearchCust);

        custPanel.add(new JLabel("Επιλογή Πελάτη:"));
        boxCustomers=new JComboBox<>();
        boxCustomers.setPreferredSize(new Dimension(250,25));
        custPanel.add(boxCustomers);
        filterPanel.add(custPanel);

        JPanel carPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
        carPanel.add(new JLabel("Αναζήτηση Αυτοκινήτου:"));
        txtSearchCar=new JTextField(15);
        carPanel.add(txtSearchCar);
        JButton btnSearchCar=new JButton("Ψάξε");
        carPanel.add(btnSearchCar);

        carPanel.add(new JLabel("Επιλογή Αυτοκινήτου:"));
        boxCars=new JComboBox<>();
        boxCars.setPreferredSize(new Dimension(250,25));
        carPanel.add(boxCars);
        filterPanel.add(carPanel);

        JPanel btnPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnFilter=new JButton("Φιλτράρισμα");
        JButton btnReset=new JButton("Επαναφορά");
        btnPanel.add(btnFilter);
        btnPanel.add(btnReset);
        filterPanel.add(btnPanel);

        add(filterPanel,BorderLayout.NORTH);

        model=new AbstractTableModel() {
            String[] columnNames={"Κώδικος","Πελάτης","ΑΦΜ","Αυτοκίνητο","Πινακίδα","Έναρξη","Λήξη","Κατάσταση","Username Υπαλλήλου"};
            @Override
            public int getRowCount() {
                return displayedRents.size();
            }

            public String getColumnName(int col) {
                return columnNames[col];
            }

            @Override
            public int getColumnCount() {
                return columnNames.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Rent rent= displayedRents.get(rowIndex);
                switch (columnIndex){
                    case 0: return rent.getCode();
                    case 1: return rent.getCustomer().getSurname()+" "+rent.getCustomer().getName();
                    case 2: return rent.getCustomer().getAfm();
                    case 3: return rent.getCar().getBrand()+" "+rent.getCar().getModel();
                    case 4: return rent.getCar().getPlate();
                    case 5: return rent.getStartDate();
                    case 6: return rent.getEndDate();
                    case 7:
                        if(rent.isActive())
                            return "Ενεργή";
                        else
                            return "Ολοκληρωμένη";
                    case 8: return rent.getEmployee().getUsername();
                    default: return null;
                }
            }
        };

        JTable table=new JTable(model);
        add(new JScrollPane(table),BorderLayout.CENTER);

        JPanel returnPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnReturnCar=new JButton("Επιστροφή Επιλεγμένου");

        returnPanel.add(btnReturnCar);
        add(returnPanel,BorderLayout.SOUTH);

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

        btnFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFilters();
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boxCustomers.setSelectedIndex(0);
                boxCars.setSelectedIndex(0);
                txtSearchCar.setText("");
                txtSearchCust.setText("");
                filterCustomers("");
                filterCars("");
                displayedRents =new ArrayList<>(service.getRents());
                model.fireTableDataChanged();
            }
        });


        btnReturnCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=table.getSelectedRow();
                if(row!=-1){
                    Rent selectedRent=displayedRents.get(row);

                    if(!selectedRent.isActive()){
                        JOptionPane.showMessageDialog(ReturnAndHistoryDialog.this,
                                "Η ενοικίαση αυτή έχει ήδη ολοκληρωθεί",
                                "Σφάλμα",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    int choice=JOptionPane.showConfirmDialog(ReturnAndHistoryDialog.this,
                            "Θέλεις σίγουρα να επιστρέφεις το αυτοκίνητο με πινακίδα: "+selectedRent.getCar().getPlate(),
                            "Επιστροφή",
                            JOptionPane.YES_NO_OPTION);

                    if(choice==JOptionPane.YES_OPTION){
                        try{
                            service.returnACar(selectedRent);
                            model.fireTableDataChanged();
                            JOptionPane.showMessageDialog(ReturnAndHistoryDialog.this, "Η επιστροφή ολοκληρώθηκε με επιτυχία!");
                        }catch (Exception ex){
                            JOptionPane.showMessageDialog(ReturnAndHistoryDialog.this,ex.getMessage());
                        }
                    }
                }
                else
                    JOptionPane.showMessageDialog(ReturnAndHistoryDialog.this,"Επιλέξτε μια ενοικίαση.");
            }
        });
    }

    private void applyFilters(){
        int custIndex=boxCustomers.getSelectedIndex();
        int carIndex=boxCars.getSelectedIndex();

        if(custIndex==0&&carIndex==0){
            displayedRents =new ArrayList<>(service.getRents());
            model.fireTableDataChanged();
            return;
        }

        Customer selectedCustomer=null;
        if(custIndex>0 && (custIndex-1)<filterCustomers.size())
            selectedCustomer=filterCustomers.get(custIndex-1);
        Car selectedCar=null;
        if(carIndex>0 && (carIndex-1)<filterCars.size())
            selectedCar=filterCars.get(carIndex-1);

        List<Rent> results=new ArrayList<>();

        for (Rent rent : service.getRents()) {
            boolean matchesCustomer=false;
            boolean matchesCar=false;

            if(selectedCustomer==null||rent.getCustomer().getAfm().equals(selectedCustomer.getAfm()))
                matchesCustomer=true;

            if (selectedCar==null||rent.getCar().getId().equals(selectedCar.getId())) {
                matchesCar=true;
            }

            if (matchesCustomer&&matchesCar) {
                results.add(rent);
            }
        }
        displayedRents =results;
        model.fireTableDataChanged();
        if(displayedRents.isEmpty()){
            JOptionPane.showMessageDialog(this,"Δεν βρέθηκαν ενοικιάσης με αυτά τα φίλτρα.");
        }
    }

    private void filterCustomers(String s){
        filterCustomers=new ArrayList<>();
        boxCustomers.removeAllItems();
        boxCustomers.addItem("- Όλοι -");

        String key=s.toLowerCase();


        for (Customer customer : service.getCustomers()) {
            String displayStr=customer.getName()+" "+customer.getSurname()+" ("+customer.getAfm()+")";
            if(key.isEmpty()|| displayStr.toLowerCase().contains(key)) {
                filterCustomers.add(customer);
                boxCustomers.addItem(displayStr);
            }
        }
    }

    private void filterCars(String s){
        filterCars=new ArrayList<>();
        boxCars.removeAllItems();
        boxCars.addItem("- Όλα -");

        String key=s.toLowerCase();

        for(Car car: service.getCars()){
            String displayStr= car.getBrand()+" "+car.getModel()+" ("+ car.getPlate()+")";
            if(key.isEmpty()|| displayStr.toLowerCase().contains(key)){
                filterCars.add(car);
                boxCars.addItem(displayStr);
            }
        }
    }
}
