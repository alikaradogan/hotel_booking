package hotel_booking.screen;

import javax.swing.*;

import hotel_booking.model.User;
import hotel_booking.service.UserService;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterScreen extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnRegister;
    private JButton btnBack;

    public RegisterScreen() {
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        btnRegister = new JButton("Kayıt Ol");
        btnBack = new JButton("Geri Dön");

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainScreen();
                dispose();
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRegistration();
            }
        });

        JPanel panel = new JPanel();
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        panel.add(new JLabel("Kullanıcı Adı:"));
        panel.add(txtUsername);
        panel.add(new JLabel("Şifre:"));
        panel.add(txtPassword);
        panel.add(btnRegister);
        panel.add(btnBack);

        txtUsername.setMaximumSize(new Dimension(300, 30));
        txtPassword.setMaximumSize(new Dimension(300, 30));

        panel.setPreferredSize(new Dimension(300, 500));
        panel.setMaximumSize(new Dimension(300, 500));
        panel.setBorder(BorderFactory.createEmptyBorder());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);

        this.setTitle("Kayıt Ol");
        this.add(panel);
        this.setSize(550, 550);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);
    }

    private void performRegistration() {
        User newUser = new User();
        newUser.setUsername(txtUsername.getText());
        newUser.setPassword(new String(txtPassword.getPassword()));

        UserService userService = UserService.getInstance();
        try {

            userService.registerUser(newUser);
            JOptionPane.showMessageDialog(this, "Kayıt Başarılı!");
            this.dispose();
            new ProfileScreen(newUser);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegisterScreen();
            }
        });
    }
}