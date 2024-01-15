package hotel_booking.screen;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hotel_booking.constants.WindowSizes;
import hotel_booking.enums.RoomType;
import hotel_booking.model.Hotel;
import hotel_booking.model.Reservation;
import hotel_booking.model.Room;
import hotel_booking.service.HotelService;
import hotel_booking.service.ReservationService;
import hotel_booking.service.RoomService;
import hotel_booking.service.UserService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class ReservationScreen extends JFrame {

    private JComboBox<Hotel> comboHotels;
    private JComboBox<RoomType> comboRooms;
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private JButton btnMakeReservation;
    private JButton btnFind;
    private JButton btnCancel;
    private Date startDate;
    private Date endDate;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private UserService userService;
    private HotelService hotelService;
    private JLabel lblTotalPrice;
    private RoomType selectedRoomType;
    private JLabel lblRoomType;

    public ReservationScreen() {
        comboHotels = new JComboBox<Hotel>();
        comboRooms = new JComboBox<RoomType>();
        txtStartDate = new JTextField(10);
        txtEndDate = new JTextField(10);
        btnMakeReservation = new JButton("Rezervasyon Yap");
        btnFind = new JButton("Otel Bul");
        btnCancel = new JButton("İptal Et");
        userService = UserService.getInstance();
        hotelService = new HotelService();
        startDate = Date.valueOf(LocalDate.now().toString());
        endDate = new Date(startDate.getTime() + 24 * 60 * 60 * 1000);
        txtStartDate.setText(startDate.toString());
        txtEndDate.setText(endDate.toString());
        lblTotalPrice = new JLabel();
        lblRoomType = new JLabel();

        comboRooms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedRoomType = (RoomType) comboRooms.getSelectedItem();
                lblRoomType.setText(selectedRoomType.getTypeName());
                calculateTotalPrice(startDate, endDate);
            }
        });

        comboHotels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRoomList();
                calculateTotalPrice(startDate, endDate);
            }
        });

        btnMakeReservation.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                performReservation();
            }
        });

        btnFind.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new FindRoomScreen();
                dispose();
            }
        });

        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ProfileScreen(userService.currentUser);
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

        comboHotels.setModel(new DefaultComboBoxModel<>(hotelService.getHotels().toArray(new Hotel[0])));
        updateRoomList();
        calculateTotalPrice(startDate, endDate);

        JPanel panel = new JPanel();
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        txtStartDate.setMaximumSize(new Dimension(300, 30));
        txtEndDate.setMaximumSize(new Dimension(300, 30));

        panel.add(new JLabel("Otel Seçiniz:"));
        panel.add(comboHotels);
        panel.add(new JLabel("Başlangıç Tarihi:"));
        panel.add(txtStartDate);
        panel.add(new JLabel("Bitiş Tarihi:"));
        panel.add(txtEndDate);
        panel.add(new JLabel("Oda Tercinizi Seçiniz:"));
        panel.add(comboRooms);
        panel.add(btnMakeReservation);
        panel.add(btnFind);
        panel.add(lblRoomType);
        panel.add(lblTotalPrice);
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

    private void updateRoomList() {
        Hotel selectedHotel = (Hotel) comboHotels.getSelectedItem();
        RoomService roomService = new RoomService();
        List<Room> availableRooms = roomService.getAvailableRooms(startDate, endDate);
        availableRooms.removeIf(room -> room.getHotelID() != selectedHotel.getHotelID());

        List<RoomType> availableRoomTypes = new ArrayList<RoomType>();
        for (Room room : availableRooms) {
            if (!availableRoomTypes.contains(room.getRoomType())) {
                availableRoomTypes.add(room.getRoomType());
            }
        }

        JComboBox<RoomType> newComboRooms = new JComboBox<>(availableRoomTypes.toArray(new RoomType[0]));
        comboRooms.setModel(newComboRooms.getModel());
        comboRooms.setSelectedIndex(0);
    }

    private void performReservation() {
        try {
            RoomService roomService = new RoomService();
            List<Room> availableRooms = roomService.getAvailableRooms(startDate, endDate);

            Room selectedRoom = availableRooms.stream()
                    .filter(room -> room.getRoomType() == selectedRoomType
                            && room.getHotelID() == ((Hotel) comboHotels.getSelectedItem()).getHotelID())
                    .findFirst().get();

            Date startDate = Date.valueOf(txtStartDate.getText());
            Date endDate = Date.valueOf(txtEndDate.getText());

            Reservation newReservation = new Reservation();
            newReservation.setUserID(userService.currentUser.getUserID());
            newReservation.setHotelID(selectedRoom.getHotelID());
            newReservation.setRoomID(selectedRoom.getRoomID());
            newReservation.setStartDate(startDate);
            newReservation.setEndDate(endDate);
            newReservation.setTotalPrice(calculateTotalPrice(startDate, endDate));

            ReservationService reservationService = new ReservationService();
            reservationService.createReservation(newReservation);

            JOptionPane.showMessageDialog(this, "Rezervasyon Başarılı!");
            new ProfileScreen(userService.currentUser);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Rezervasyon Başarısız: " + e.getMessage());
        }
    }

    private BigDecimal calculateTotalPrice(Date startDate, Date endDate) {
        RoomType selectedRoom = (RoomType) comboRooms.getSelectedItem();
        double pricePerDay = selectedRoom.getPrice();

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