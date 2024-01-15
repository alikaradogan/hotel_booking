package hotel_booking.screen;

import javax.swing.*;

import hotel_booking.constants.WindowSizes;
import hotel_booking.model.User;
import hotel_booking.service.UserService;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnBack;

    public LoginScreen() {
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        btnLogin = new JButton("Giriş Yap");
        btnBack = new JButton("Geri Dön");

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainScreen();
                dispose();
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        JPanel panel = new JPanel();
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        txtUsername.setMaximumSize(new Dimension(300, 30));
        txtPassword.setMaximumSize(new Dimension(300, 30));

        panel.add(new JLabel("Kullanıcı Adı:"));
        panel.add(txtUsername);
        panel.add(new JLabel("Şifre:"));
        panel.add(txtPassword);
        panel.add(btnLogin);
        panel.add(btnBack);

        panel.setPreferredSize(new Dimension(WindowSizes.maxInnerWidth, WindowSizes.maxInnerHeight));
        panel.setMaximumSize(new Dimension(WindowSizes.maxInnerWidth, WindowSizes.maxInnerHeight));
        panel.setBorder(BorderFactory.createEmptyBorder());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);

        this.setTitle("Giriş Yap");
        this.add(panel);
        this.setSize(WindowSizes.maxWidth, WindowSizes.maxHeight);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);

    }

    private void performLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        UserService userService = UserService.getInstance();
        User user = userService.loginUser(username, password);

        if (user != null) {
            this.setVisible(false);
            this.dispose();
            new ProfileScreen(user);
        } else {
            JOptionPane.showMessageDialog(this, "Giriş Başarısız. Lütfen bilgilerinizi kontrol edin.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginScreen();
            }
        });
    }
}
