package gui;

import api.Car;
import api.RentalService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCarDialog extends JDialog {
    private RentalService service;

    private JTextField txtId;
    private JTextField txtPlate;
    private JTextField txtBrand;
    private JTextField txtModel;
    private JTextField txtType;
    private JTextField txtYear;
    private JTextField txtColor;

    private JButton btnSave;
    private JButton btnCancel;

    public AddCarDialog(JFrame parent,RentalService service){
        super(parent,"Προσθήκη Αυτοκινητού",true);
        this.service=service;

        setSize(400,500);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(0,2,10,10));

        add(new JLabel("ID:", SwingConstants.RIGHT));
        txtId = new JTextField();
        add(txtId);

        // 2. Πινακίδα
        add(new JLabel("Πινακίδα:", SwingConstants.RIGHT));
        txtPlate = new JTextField();
        add(txtPlate);

        // 3. Μάρκα
        add(new JLabel("Μάρκα:", SwingConstants.RIGHT));
        txtBrand = new JTextField();
        add(txtBrand);

        // 4. Μοντέλο
        add(new JLabel("Μοντέλο:", SwingConstants.RIGHT));
        txtModel = new JTextField();
        add(txtModel);

        // 5. Τύπος
        add(new JLabel("Τύπος:", SwingConstants.RIGHT));
        txtType = new JTextField();
        add(txtType);

        // 6. Έτος
        add(new JLabel("Έτος:", SwingConstants.RIGHT));
        txtYear = new JTextField();
        add(txtYear);

        // 7. Χρώμα
        add(new JLabel("Χρώμα:", SwingConstants.RIGHT));
        txtColor = new JTextField();
        add(txtColor);

        btnSave =new JButton("Αποθήκευση");
        btnCancel =new JButton("Πίσω");

        add(btnSave);
        add(btnCancel);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtId.getText().trim();
                String plate = txtPlate.getText().trim();
                String brand = txtBrand.getText().trim();
                String model = txtModel.getText().trim();
                String type = txtType.getText().trim();
                String year = txtYear.getText().trim();
                String color = txtColor.getText().trim();

                if (id.isEmpty() || plate.isEmpty() || brand.isEmpty() || model.isEmpty()|| type.isEmpty() || year.isEmpty() ||color.isEmpty()) {
                    JOptionPane.showMessageDialog(AddCarDialog.this,
                            "Παρακαλώ συμπληρώστε όλα τα βασικά πεδία!",
                            "Ελλιπή Στοιχεία",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try{
                    Integer.parseInt(id);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AddCarDialog.this,
                            "Το id πρέπει να είναι ακέραιος!",
                            "Λάθος ID",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try{
                    Integer.parseInt(year);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AddCarDialog.this,
                            "Το year πρέπει να είναι ακέραιος!",
                            "Λάθος Year",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(!RentalService.checkString(color)){
                    JOptionPane.showMessageDialog(AddCarDialog.this,
                            "Το χρώμα πρέπει να περιέχει μόνο γράμματα!");
                    return;
                }

                try{
                    Car car=new Car(id,plate,brand,type,model,year,color,true);

                    service.addCar(car);

                    JOptionPane.showMessageDialog(AddCarDialog.this,"Το αυτοκίνητο αποθηκεύτικε!");
                    dispose();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(AddCarDialog.this,ex.getMessage());
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
