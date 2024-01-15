package hotel_booking.screen;

import javax.swing.*;

import hotel_booking.model.Reservation;
import hotel_booking.model.User;
import hotel_booking.service.ReservationService;
import hotel_booking.service.UserService;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProfileScreen extends JFrame {

    // Kullanıcı bilgileri için form elemanları
    private JTextField username;
    private JPasswordField password;
    private JButton btnUpdateInfo;
    private JButton btnNewReservation;
    private JButton btnLogOut;
    private JButton btnDeleteReservation;

    // Geçmiş rezervasyonları göstermek için liste veya tablo
    private JList<Reservation> listReservations;

    public ProfileScreen(User user) {
        // Kullanıcı bilgilerini form alanlarına yükle
        username = new JTextField(user.getUsername(), 20);
        password = new JPasswordField(user.getPassword(), 20);
        btnUpdateInfo = new JButton("Bilgileri Güncelle");
        btnNewReservation = new JButton("Yeni Rezervasyon Yap");
        btnLogOut = new JButton("Çıkış Yap");
        btnDeleteReservation = new JButton("Rezervasyonu İptal Et");

        // Bilgileri güncelle butonu eylemi
        btnUpdateInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUserProfile(user);
            }
        });

        btnNewReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReservationScreen();
                dispose();
            }
        });

        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainScreen();
                dispose();
            }
        });

        btnDeleteReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reservation reservation = listReservations.getSelectedValue();
                if (reservation == null) {
                    JOptionPane.showMessageDialog(null, "Lütfen bir rezervasyon seçin!");
                    return;
                }
                ReservationService reservationService = new ReservationService();
                try {
                    reservationService.cancelReservation(reservation.getReservationID());
                    JOptionPane.showMessageDialog(null, "Rezervasyon iptal edildi!");
                    loadUserReservations(user);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Geçmiş rezervasyonları yükle
        listReservations = new JList<>();
        loadUserReservations(user);
        JPanel panel = new JPanel();
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        panel.add(new JLabel("Rezervasyonlar:"));
        panel.add(new JScrollPane(listReservations));

        panel.add(btnDeleteReservation);

        username.setMaximumSize(new Dimension(300, 30));
        password.setMaximumSize(new Dimension(300, 30));

        panel.add(new JLabel("Kullanıcı Bilgileri"));
        panel.add(new JLabel("Username:"));
        panel.add(username);
        panel.add(new JLabel("Password:"));
        panel.add(password);
        panel.add(btnUpdateInfo);
        panel.add(btnNewReservation);
        panel.add(btnLogOut);

        panel.setPreferredSize(new Dimension(450, 500));
        panel.setMaximumSize(new Dimension(450, 500));
        panel.setBorder(BorderFactory.createEmptyBorder());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);

        this.setTitle("Kullanıcı Profili");
        this.add(panel);
        this.setSize(550, 550);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);

    }

    private void updateUserProfile(User user) {
        // Form alanlarından bilgileri al ve kullanıcı nesnesini güncelle
        user.setUsername(username.getText());
        user.setPassword(new String(password.getPassword()));

        // UserService ile kullanıcı bilgilerini güncelle
        UserService userService = UserService.getInstance();

        try {
            userService.updateUser(user);
            JOptionPane.showMessageDialog(this, "Bilgiler güncellendi!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

    }

    private void loadUserReservations(User user) {
        // Kullanıcının geçmiş rezervasyonlarını yükle
        ReservationService reservationService = new ReservationService();
        List<Reservation> userReservations = reservationService.getUserReservations(user.getUserID());
        // Geçmiş rezervasyonları JList'e ekle

        listReservations.setListData(userReservations.toArray(new Reservation[0]));
    }

    public static void main(String[] args) {
        UserService userService = UserService.getInstance();
        User user = userService.currentUser;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProfileScreen(user);
            }
        });
    }

}