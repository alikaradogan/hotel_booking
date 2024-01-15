package hotel_booking.screen;

import javax.swing.*;

import hotel_booking.constants.WindowSizes;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame {

    public MainScreen() {
        JButton btnLogin = new JButton("Giriş Yap");
        JButton btnRegister = new JButton("Kayıt Ol");
        JButton btnExit = new JButton("Çıkış");

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginScreen();
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterScreen();
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel panel = new JPanel();
        panel.add(btnLogin);
        panel.add(btnRegister);
        panel.add(btnExit);

        panel.setPreferredSize(new Dimension(WindowSizes.maxInnerWidth, WindowSizes.maxInnerHeight));
        panel.setMaximumSize(new Dimension(WindowSizes.maxInnerWidth, WindowSizes.maxInnerHeight));
        panel.setBorder(BorderFactory.createEmptyBorder());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);

        this.setTitle("Rezarvasyon Sistemi");
        this.add(panel);
        this.setSize(WindowSizes.maxWidth, WindowSizes.maxHeight);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void showLoginScreen() {
        new LoginScreen();
        this.dispose();
    }

    private void showRegisterScreen() {
        new RegisterScreen();
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainScreen();
            }
        });
    }
}
