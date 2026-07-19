package gui;

import api.Car;
import api.Customer;
import api.RentalService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerDialog extends JDialog {
    private RentalService service;

    private JTextField txtAFM;
    private JTextField txtName;
    private JTextField txtSurname;
    private JTextField txtPhone;
    private JTextField txtEmail;

    private JButton btnSave;
    private JButton btnCancel;

    public AddCustomerDialog(JFrame parent,RentalService service){
        super(parent,"Προσθήκη Πελάτη",true);
        this.service=service;

        setSize(400,500);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(0,2,10,10));

        add(new JLabel("ΑΦΜ:", SwingConstants.RIGHT));
        txtAFM = new JTextField();
        add(txtAFM);

        // 2. Πινακίδα
        add(new JLabel("Όνομα:", SwingConstants.RIGHT));
        txtName = new JTextField();
        add(txtName);

        // 3. Μάρκα
        add(new JLabel("Επώνυμο:", SwingConstants.RIGHT));
        txtSurname = new JTextField();
        add(txtSurname);

        // 4. Μοντέλο
        add(new JLabel("Τηλέφωνο:", SwingConstants.RIGHT));
        txtPhone = new JTextField();
        add(txtPhone);

        // 5. Τύπος
        add(new JLabel("Email:", SwingConstants.RIGHT));
        txtEmail = new JTextField();
        add(txtEmail);

        btnSave =new JButton("Αποθήκευση");
        btnCancel =new JButton("Πίσω");

        add(btnSave);
        add(btnCancel);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String AFM = txtAFM.getText().trim();
                String name = txtName.getText().trim();
                String surname = txtSurname.getText().trim();
                String phone = txtPhone.getText().trim();
                String email = txtEmail.getText().trim();


                if (AFM.isEmpty() || name.isEmpty() || surname.isEmpty() || phone.isEmpty()|| email.isEmpty()) {
                    JOptionPane.showMessageDialog(AddCustomerDialog.this,
                            "Παρακαλώ συμπληρώστε όλα τα βασικά πεδία!",
                            "Ελλιπή Στοιχεία",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try{
                    Integer.parseInt(phone);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AddCustomerDialog.this,
                            "Το τηλέφωνο πρέπει να είναι ακέραιος!",
                            "Λάθος Τηλέφωνο",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(!service.checkString(name)){
                    JOptionPane.showMessageDialog(AddCustomerDialog.this,
                            "Το όνομα πρέπει να περιέχει μόνο γράμματα!");
                    return;
                }

                if(!service.checkString(surname)){
                    JOptionPane.showMessageDialog(AddCustomerDialog.this,
                            "Το επώνυμο πρέπει να περιέχει μόνο γράμματα!");
                    return;
                }

                try{
                    Integer.parseInt(AFM);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AddCustomerDialog.this,
                            "Το ΑΦΜ πρέπει να είναι ακέραιος!",
                            "Λάθος ΑΦΜ",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try{
                    Customer customer=new Customer(name,surname,email,AFM,phone);

                    service.addCustomer(customer);

                    JOptionPane.showMessageDialog(AddCustomerDialog.this,"Πελάτης αποθηκεύτικε!");
                    dispose();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(AddCustomerDialog.this,ex.getMessage());
                }


            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


    }
}


