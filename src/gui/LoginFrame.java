package gui;

import api.RentalService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private RentalService service;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginFrame(RentalService service){
        this.service=service;

        setTitle("Σύνδεση");
        setSize(300,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3,2,10,10));

        JLabel nameLabel=new JLabel("Username:",SwingConstants.CENTER);
        add(nameLabel);
        txtUsername =new JTextField();
        add(txtUsername);

        JLabel passLabel=new JLabel("Password:",SwingConstants.CENTER);
        add(passLabel);
        txtPassword =new JPasswordField();
        add(txtPassword);

        add(new JLabel(""));
        btnLogin =new JButton("Σύνδεση");
        add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });
    }

    private void doLogin(){
        String user= txtUsername.getText();
        String pass=new String(txtPassword.getPassword());

        try{
            service.login(user,pass);

            this.dispose();

            new MainMenuFrame(service).setVisible(true);
        }catch (Exception ex){
            JOptionPane.showMessageDialog(this,ex.getMessage());
        }
    }

}
