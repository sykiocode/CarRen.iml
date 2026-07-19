package gui;

import api.Customer;
import api.RentalService;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchandEditCustomerDialog extends JDialog {
    private RentalService service;
    private JTextField txtSearch;
    private java.util.List<Customer> displayedCustomers;

    public SearchandEditCustomerDialog(JFrame parent, RentalService service){
        super(parent,"Αναζήτηση και Επεξεργασία Πελατών",true);
        this.service=service;

        this.displayedCustomers =new ArrayList<>(service.getCustomers());


        setLocation(parent.getX()+50, parent.getY()+50);
        setLayout(new BorderLayout());

        JPanel searchPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Αναζήτηση: "));
        txtSearch=new JTextField(25);
        searchPanel.add(txtSearch);
        JButton btnSearch=new JButton("Ok");
        searchPanel.add(btnSearch);
        JButton btnBack=new JButton("Πίσω");
        searchPanel.add(btnBack);
        add(searchPanel,BorderLayout.PAGE_START);

        AbstractTableModel model=new AbstractTableModel() {
            String[] columnNames={"Όνομα","Επώνυμο","Email","Τηλέφωνο","ΑΦΜ"};
            @Override
            public int getRowCount() {
                return displayedCustomers.size();
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
                Customer customer= displayedCustomers.get(rowIndex);
                switch (columnIndex){
                    case 0: return customer.getName();
                    case 1: return customer.getSurname();
                    case 2: return customer.getEmail();
                    case 3: return customer.getPhone();
                    case 4: return customer.getAfm();
                    default: return null;
                }
            }

            public Class<?> getColumnClass(int columnIndex){
                return String.class;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex!=4;
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                Customer customer= displayedCustomers.get(rowIndex);
                try{
                    switch (columnIndex){
                        case 0: customer.setName((String) aValue); break;
                        case 1: customer.setSurname((String) aValue); break;
                        case 2: customer.setEmail((String) aValue); break;
                        case 3: customer.setPhone((String) aValue); break;
                    }
                    service.saveData();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(SearchandEditCustomerDialog.this,e.getMessage());
                }
                fireTableCellUpdated(rowIndex,columnIndex);
            }
        };

        JTable table=new JTable(model);

        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        add(scrollPane, BorderLayout.CENTER);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txt=txtSearch.getText().trim().toLowerCase();
                if(txt.isEmpty()){
                    displayedCustomers =new ArrayList<>(service.getCustomers());
                }
                else{
                    String[] s=txt.split("\\s+");

                    displayedCustomers =service.searchCustomer(s);
                }
                model.fireTableDataChanged();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtSearch.setText("");
                displayedCustomers =new ArrayList<>(service.getCustomers());
                model.fireTableDataChanged();
            }
        });

        pack();
    }

}
