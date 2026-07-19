package gui;

import api.Car;
import api.RentalService;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchandEditCarDialog extends JDialog {
    private RentalService service;
    private JTextField txtSearch;
    private java.util.List<Car> displayedCars;

    public SearchandEditCarDialog(JFrame parent, RentalService service){
        super(parent,"Αναζήτηση και Επεξεργασία Αυτοκινήτων",true);
        this.service=service;

        this.displayedCars=new ArrayList<>(service.getCars());


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
            String[] columnNames={"ID","Πινακίδα","Μάρκα","Μοντέλο","Τύπος","Έτος","Χρώμα","Κατάσταση"};
            @Override
            public int getRowCount() {
                return displayedCars.size();
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
                Car car=displayedCars.get(rowIndex);
                switch (columnIndex){
                    case 0: return car.getId();
                    case 1: return car.getPlate();
                    case 2: return car.getBrand();
                    case 3: return car.getModel();
                    case 4: return car.getType();
                    case 5: return car.getYear();
                    case 6: return car.getColor();
                    case 7: return car.isAvailable();
                    default: return null;
                }
            }

            public Class<?> getColumnClass(int columnIndex){
                if(columnIndex==7)
                    return Boolean.class;
                return String.class;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex!=0 && columnIndex!=1;
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                Car car=displayedCars.get(rowIndex);
                try{
                    switch (columnIndex){
                        case 2: car.setBrand((String) aValue); break;
                        case 3: car.setModel((String) aValue); break;
                        case 4: car.setType((String) aValue); break;
                        case 5: car.setYear((String) aValue); break;
                        case 6: car.setColor((String) aValue); break;
                        case 7:
                            if(service.isCarRented(car))
                                throw new Exception("Το αυτοκίνητο είναι σε ενεργή ενοικίαση!\nΠρέπει πρώτα να γίνει Επιστροφή για να αλλάξει η κατάσταση.");
                            else
                                car.setAvailable((Boolean) aValue);
                            break;
                    }
                    service.saveData();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(SearchandEditCarDialog.this,e.getMessage());
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
                    displayedCars=new ArrayList<>(service.getCars());
                }
                else{
                    String[] s=txt.split("\\s+");

                    displayedCars=service.searchCars(s);
                }
                model.fireTableDataChanged();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtSearch.setText("");
                displayedCars=new ArrayList<>(service.getCars());
                model.fireTableDataChanged();
            }
        });

        pack();
    }
}
