package gui;

import api.Employee;
import api.RentalService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEmployeeDialog extends JDialog {
    private RentalService service;

    private JTextField txtName;
    private JTextField txtSurname;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JTextField txtPassword;

    private JButton btnSave;
    private JButton btnCancel;

    public AddEmployeeDialog(JFrame parent,RentalService service){
        super(parent,"Προσθήκη Χρήστη",true);
        this.service=service;

        setSize(400,500);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(0,2,10,10));



        add(new JLabel("Όνομα:", SwingConstants.RIGHT));
        txtName = new JTextField();
        add(txtName);

        add(new JLabel("Επώνυμο:", SwingConstants.RIGHT));
        txtSurname = new JTextField();
        add(txtSurname);

        add(new JLabel("Username:", SwingConstants.RIGHT));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Email:", SwingConstants.RIGHT));
        txtEmail = new JTextField();
        add(txtEmail);

        add(new JLabel("Password",SwingConstants.RIGHT));
        txtPassword=new JTextField();
        add(txtPassword);

        btnSave =new JButton("Αποθήκευση");
        btnCancel =new JButton("Πίσω");

        add(btnSave);
        add(btnCancel);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText().trim();
                String surname = txtSurname.getText().trim();
                String username = txtUsername.getText().trim();
                String email = txtEmail.getText().trim();
                String password=txtPassword.getText().trim();


                if (password.isEmpty() || name.isEmpty() || surname.isEmpty() || username.isEmpty()|| email.isEmpty()) {
                    JOptionPane.showMessageDialog(AddEmployeeDialog.this,
                            "Παρακαλώ συμπληρώστε όλα τα βασικά πεδία!",
                            "Ελλιπή Στοιχεία",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(!service.checkString(name)){
                    JOptionPane.showMessageDialog(AddEmployeeDialog.this,
                            "Το όνομα πρέπει να περιέχει μόνο γράμματα!");
                    return;
                }

                if(!service.checkString(surname)){
                    JOptionPane.showMessageDialog(AddEmployeeDialog.this,
                            "Το επώνυμο πρέπει να περιέχει μόνο γράμματα!");
                    return;
                }

                try{
                    Employee employee=new Employee(name,surname,username,email,password);

                    service.addEmployee(employee);

                    JOptionPane.showMessageDialog(AddEmployeeDialog.this,"Χρήστης αποθηκεύτικε!");
                    dispose();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(AddEmployeeDialog.this,ex.getMessage());
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
