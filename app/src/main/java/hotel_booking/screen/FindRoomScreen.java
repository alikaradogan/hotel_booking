package hotel_booking.screen;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hotel_booking.constants.WindowSizes;
import hotel_booking.enums.HotelRating;
import hotel_booking.enums.RoomType;
import hotel_booking.model.Hotel;
import hotel_booking.model.HotelCity;
import hotel_booking.model.Room;
import hotel_booking.model.User;
import hotel_booking.service.HotelService;
import hotel_booking.service.ReservationService;
import hotel_booking.service.RoomService;
import hotel_booking.service.UserService;

import java.util.List;
import java.util.ArrayList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public class FindRoomScreen extends JFrame {

    private JComboBox<HotelRating> comboStars;
    private JComboBox<HotelCity> comboHotelCities;
    private JComboBox<RoomType> comboRoomTypes;
    private HotelCity selectedCity;
    private HotelRating selectedStar;
    private RoomType selectedRoomType;
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private JButton btnMakeReservation;
    private JButton btnCancel;
    private Date startDate;
    private Date endDate;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private UserService userService;
    private HotelService hotelService;
    private JLabel lblTotalPrice;

    public FindRoomScreen() {
        comboStars = new JComboBox<HotelRating>();
        comboRoomTypes = new JComboBox<RoomType>();
        comboHotelCities = new JComboBox<HotelCity>();
        txtStartDate = new JTextField(10);
        txtEndDate = new JTextField(10);
        btnMakeReservation = new JButton("Bul ve Rezerve Et");
        btnCancel = new JButton("Geri Dön");
        userService = UserService.getInstance();
        hotelService = new HotelService();
        startDate = Date.valueOf(LocalDate.now().toString());
        endDate = new Date(startDate.getTime() + 24 * 60 * 60 * 1000);
        txtStartDate.setText(startDate.toString());
        txtEndDate.setText(endDate.toString());
        lblTotalPrice = new JLabel();

        getLists();
        selectedCity = (HotelCity) comboHotelCities.getSelectedItem();
        selectedStar = (HotelRating) comboStars.getSelectedItem();
        updateRoomList();
        selectedRoomType = (RoomType) comboRoomTypes.getSelectedItem();

        calculateTotalPrice(startDate, endDate);

        comboRoomTypes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedRoomType = (RoomType) comboRoomTypes.getSelectedItem();
                calculateTotalPrice(startDate, endDate);
            }
        });

        comboHotelCities.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCity = (HotelCity) comboHotelCities.getSelectedItem();
                updateRoomList();
                calculateTotalPrice(startDate, endDate);
            }
        });

        comboStars.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedStar = (HotelRating) comboStars.getSelectedItem();
                updateRoomList();
                calculateTotalPrice(startDate, endDate);
            }
        });

        btnMakeReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findReservations();
            }
        });

        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ReservationScreen();
                dispose();
            }
        });

        txtStartDate.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    startDate = Date.valueOf(txtStartDate.getText());
                    updateRoomList();
                    calculateTotalPrice(startDate, endDate);
                } catch (Exception error) {
                    System.err.println(error.getMessage());
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    startDate = Date.valueOf(txtStartDate.getText());
                    updateRoomList();
                    calculateTotalPrice(startDate, endDate);

                } catch (Exception error) {
                    System.err.println(error.getMessage());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    startDate = Date.valueOf(txtStartDate.getText());
                    updateRoomList();
                    calculateTotalPrice(startDate, endDate);
                } catch (Exception error) {
                    System.err.println(error.getMessage());
                }
            }
        });

        txtEndDate.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    endDate = Date.valueOf(txtEndDate.getText());
                    updateRoomList();
                    calculateTotalPrice(startDate, endDate);
                } catch (Exception error) {
                    System.err.println(error.getMessage());
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    endDate = Date.valueOf(txtEndDate.getText());
                    updateRoomList();
                    calculateTotalPrice(startDate, endDate);
                } catch (Exception error) {
                    System.err.println(error.getMessage());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    endDate = Date.valueOf(txtEndDate.getText());
                    updateRoomList();
                    calculateTotalPrice(startDate, endDate);
                } catch (Exception error) {
                    System.err.println(error.getMessage());
                }
            }
        });

        JPanel panel = new JPanel();
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        txtStartDate.setMaximumSize(new Dimension(300, 30));
        txtEndDate.setMaximumSize(new Dimension(300, 30));

        panel.add(new JLabel("Şehir Seçiniz:"));
        panel.add(comboHotelCities);
        panel.add(new JLabel("Puan Seçiniz:"));
        panel.add(comboStars);
        panel.add(new JLabel("Başlangıç Tarihi:"));
        panel.add(txtStartDate);
        panel.add(new JLabel("Bitiş Tarihi:"));
        panel.add(txtEndDate);
        panel.add(new JLabel("Oda Türü Seçiniz:"));
        panel.add(comboRoomTypes);
        panel.add(lblTotalPrice);
        panel.add(btnMakeReservation);
        panel.add(btnCancel);

        panel.setPreferredSize(new Dimension(WindowSizes.maxInnerWidth, WindowSizes.reservationsHeight));
        panel.setMaximumSize(new Dimension(WindowSizes.maxInnerWidth, WindowSizes.reservationsHeight));
        panel.setBorder(BorderFactory.createEmptyBorder());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setTitle("Rezervasyon Yap");
        this.add(panel);
        this.setSize(WindowSizes.maxWidth, WindowSizes.maxHeight);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(true);

    }

    private void getLists() {
        List<Hotel> hotels = hotelService.getHotels();
        for (Hotel hotel : hotels) {

            if (comboHotelCities.getItemCount() > 0 && comboStars.getItemCount() > 0) {
                for (int i = 0; i < comboHotelCities.getItemCount(); i++) {
                    if (comboHotelCities.getItemAt(i).getCity().equals(hotel.getCity())) {
                        break;
                    }
                    if (i == comboHotelCities.getItemCount() - 1) {
                        comboHotelCities.addItem(new HotelCity(hotel.getCity()));
                    }
                }
                for (int i = 0; i < comboStars.getItemCount(); i++) {
                    if (comboStars.getItemAt(i).getRating() == hotel.getStarCount()) {
                        break;
                    }
                    if (i == comboStars.getItemCount() - 1) {
                        comboStars.addItem(HotelRating.getHotelRatingFromRating(hotel.getStarCount()));
                    }
                }
                continue;
            }

            comboHotelCities.addItem(new HotelCity(hotel.getCity()));
            comboStars.addItem(HotelRating.getHotelRatingFromRating(hotel.getStarCount()));
            comboStars.setSelectedIndex(0);
        }
    }

    private void updateRoomList() {
        List<Hotel> hotels = hotelService.getHotelsByCityAndRating(selectedCity, selectedStar);
        RoomService roomService = new RoomService();
        System.out.println(hotels.size());
        List<Room> availableRooms = roomService.getAvailableRooms(startDate, endDate);
        for (Hotel hotel : hotels) {
            availableRooms.removeIf(room -> room.getHotelID() != hotel.getHotelID());
        }

        List<RoomType> availableRoomTypes = new ArrayList<RoomType>();
        for (Room room : availableRooms) {
            if (!availableRoomTypes.contains(room.getRoomType())) {
                availableRoomTypes.add(room.getRoomType());
            }
        }

        JComboBox<RoomType> newComboRooms = new JComboBox<>(availableRoomTypes.toArray(new RoomType[0]));
        comboRoomTypes.setModel(newComboRooms.getModel());
        comboRoomTypes.setSelectedIndex(0);
    }

    private void findReservations() {
        try {

            ReservationService reservationService = new ReservationService();
            User currentUser = userService.currentUser;

            reservationService.findRoom(currentUser, startDate, endDate, selectedStar.getRating(),
                    selectedCity.getCity(), selectedRoomType);
            JOptionPane.showMessageDialog(this, "Rezervasyonunuz yaklaşık 15 dakika içerisinde oluşturulacaktır.",
                    "Başarılı",
                    JOptionPane.INFORMATION_MESSAGE);
            new ProfileScreen(currentUser);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private BigDecimal calculateTotalPrice(Date startDate, Date endDate) {
        selectedRoomType = (RoomType) comboRoomTypes.getSelectedItem();
        double pricePerDay = selectedRoomType.getPrice();

        long diffInMillies = endDate.getTime() - startDate.getTime();
        double diffInDays = Math.ceil(Math.abs(diffInMillies / (24 * 60 * 60 * 1000)));
        totalPrice = BigDecimal.valueOf(diffInDays * pricePerDay);
        lblTotalPrice.setText("$" + totalPrice.toString());
        if (diffInMillies < 0 || diffInDays == 0) {
            lblTotalPrice.setText("");
        }

        return totalPrice;
    }

}