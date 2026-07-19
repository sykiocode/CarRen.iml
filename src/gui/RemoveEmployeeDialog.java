package gui;

import api.Customer;
import api.Employee;
import api.RentalService;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RemoveEmployeeDialog extends JDialog {
    private RentalService service;
    private JTextField txtSearch;
    private java.util.List<Employee> displayedEmployees;

    public RemoveEmployeeDialog(JFrame parent, RentalService service){
        super(parent,"Διαγραφή Χρήστη",true);
        this.service=service;

        this.displayedEmployees =new ArrayList<>(service.getEmployees());


        setLocation(parent.getX()+50, parent.getY()+50);
        setLayout(new BorderLayout());


        AbstractTableModel model=new AbstractTableModel() {
            String[] columnNames={"Όνομα","Επώνυμο","Username","Email"};
            @Override
            public int getRowCount() {
                return displayedEmployees.size();
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
                Employee employee= displayedEmployees.get(rowIndex);
                switch (columnIndex){
                    case 0: return employee.getName();
                    case 1: return employee.getSurname();
                    case 2: return employee.getUsername();
                    case 3: return employee.getEmail();
                    default: return null;
                }
            }

            public Class<?> getColumnClass(int columnIndex){
                return String.class;
            }

        };

        JTable table=new JTable(model);

        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel removePanel= new JPanel();
        JButton btnDel=new JButton("Διαγραφή");

        btnDel.setBackground(Color.RED);
        btnDel.setForeground(Color.WHITE);

        btnDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=table.getSelectedRow();

                if(row==-1){
                    JOptionPane.showMessageDialog(RemoveEmployeeDialog.this,
                            "Παρακαλώ επιλέξτε έναν χρήστη για διαγραφή.",
                            "Προσοχή",JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Employee empToDel=displayedEmployees.get(row);

                int choice = JOptionPane.showConfirmDialog(RemoveEmployeeDialog.this,
                        "Είστε σίγουρος ότι θέλετε να διαγράψετε τον χρήστη: " + empToDel.getUsername() + ";",
                        "Επιβεβαίωση Διαγραφής",
                        JOptionPane.YES_NO_OPTION);
                if(choice==JOptionPane.YES_OPTION){
                    try{
                        service.removeEmployee(empToDel);
                        displayedEmployees.remove(row);
                        model.fireTableDataChanged();
                        JOptionPane.showMessageDialog(RemoveEmployeeDialog.this,"Ο χρήστης διαγράφηκε επιτυχώς.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(RemoveEmployeeDialog.this,
                                ex.getMessage());
                    }
                }
            }
        });

        removePanel.add(btnDel);
        add(removePanel,BorderLayout.PAGE_END);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        add(scrollPane, BorderLayout.CENTER);


        pack();
    }


}
