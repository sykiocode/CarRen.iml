package gui;

import api.RentalService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuFrame extends JFrame {
    private RentalService service;

    public MainMenuFrame(RentalService service){
        this.service=service;
        setTitle("Car Rent Menu");
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JMenuBar menu=new JMenuBar();

        JMenu menuCars=new JMenu("Αυτοκίνητα");
        JMenu menuCustomers=new JMenu("Πελάτες");
        JMenu menuRents=new JMenu("Ενοικιάσεις");
        JMenu menuEmployees=new JMenu("Υπάλληλοι");

        JMenuItem addCar = new JMenuItem("Προσθήκη Αυτοκινήτου");
        JMenuItem searchCar = new JMenuItem("Αναζήτηση / Επεξεργασία");
        menuCars.add(addCar);
        menuCars.add(searchCar);

        JMenuItem addCustomer = new JMenuItem("Προσθήκη Πελάτη");
        JMenuItem searchCustomer = new JMenuItem("Αναζήτηση / Επεξεργασία");
        menuCustomers.add(addCustomer);
        menuCustomers.add(searchCustomer);

        JMenuItem newRent = new JMenuItem("Νέα Ενοικίαση");
        JMenuItem history=new JMenuItem("Επιστροφή & Ιστορικό");
        menuRents.add(newRent);
        menuRents.add(history);

        JMenuItem addEmployee=new JMenuItem("Προσθήκη Χρήστη");
        JMenuItem removeEmployee=new JMenuItem("Διαγραφή Χρήστη");
        menuEmployees.add(addEmployee);
        menuEmployees.add(removeEmployee);



        menu.add(menuCars);
        menu.add(menuCustomers);
        menu.add(menuRents);
        menu.add(menuEmployees);

        setJMenuBar(menu);

        JLabel welcomeLabel=new JLabel("Καλωσήρθατε στο Κεντρικό Μενού",SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(welcomeLabel, BorderLayout.CENTER);

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logout = new JButton("Αποσύνδεση");

        logout.setBackground(Color.RED);
        logout.setForeground(Color.WHITE);

        logoutPanel.add(logout);
        add(logoutPanel,BorderLayout.SOUTH);

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(MainMenuFrame.this,
                        "Είστε σίγουρος ότι θέλετε να αποσυνδεθείτε;",
                        "Αποσύνδεση",
                        JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    service.logout();
                    dispose();
                    new LoginFrame(service).setVisible(true);
                }
            }
        });

        addCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCarDialog(MainMenuFrame.this,service).setVisible(true);
            }
        });


        searchCar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchandEditCarDialog(MainMenuFrame.this,service).setVisible(true);
            }
        });

        addCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCustomerDialog(MainMenuFrame.this,service).setVisible(true);
            }
        });

        searchCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchandEditCustomerDialog(MainMenuFrame.this,service).setVisible(true);
            }
        });

        addEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEmployeeDialog(MainMenuFrame.this,service).setVisible(true);
            }
        });

        removeEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemoveEmployeeDialog(MainMenuFrame.this,service).setVisible(true);
            }
        });

        newRent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RentCarDialog(MainMenuFrame.this,service).setVisible(true);
            }
        });

        history.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReturnAndHistoryDialog(MainMenuFrame.this,service).setVisible(true);
            }
        });

    }
}
