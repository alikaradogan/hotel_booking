package hotel_booking.dao;

import hotel_booking.enums.RoomType;
import hotel_booking.model.Hotel;
import hotel_booking.model.Reservation;
import hotel_booking.model.Room;
import hotel_booking.model.User;
import hotel_booking.service.RoomService;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;

public class ReservationDAO {
    private String filePath = "/Users/ali/Developer/resmgmt/db/reservations.json";

    public List<Reservation> getReservations() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray reservationsArray = new JSONArray(content);
            List<Reservation> reservations = new ArrayList<>();

            for (int i = 0; i < reservationsArray.length(); i++) {
                JSONObject reservationObject = reservationsArray.getJSONObject(i);
                Reservation reservation = new Reservation();
                reservation.setReservationID(reservationObject.getInt("reservationID"));
                reservation.setUserID(reservationObject.getInt("userID"));
                reservation.setHotelID(reservationObject.getInt("hotelID"));
                reservation.setRoomID(reservationObject.getInt("roomID"));
                reservation.setStartDate(Date.valueOf(reservationObject.getString("startDate")));
                reservation.setEndDate(Date.valueOf(reservationObject.getString("endDate")));
                reservations.add(reservation);
            }
            return reservations;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    public Reservation getReservationById(int id) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray reservationsArray = new JSONArray(content);
            for (int i = 0; i < reservationsArray.length(); i++) {
                JSONObject reservationObject = reservationsArray.getJSONObject(i);
                if (reservationObject.getInt("reservationID") == id) {
                    Reservation reservation = new Reservation(); // Assuming Reservation is a class with appropriate
                                                                 // fields and setters
                    reservation.setReservationID(reservationObject.getInt("reservationID"));
                    reservation.setUserID(reservationObject.getInt("userID"));
                    reservation.setHotelID(reservationObject.getInt("hotelID"));
                    reservation.setRoomID(reservationObject.getInt("roomID"));
                    reservation.setStartDate(Date.valueOf(reservationObject.getString("startDate")));
                    reservation.setEndDate(Date.valueOf(reservationObject.getString("endDate")));
                    return reservation;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addReservation(Reservation reservation) {
        try {
            List<Reservation> reservations = getReservations();
            reservations.sort((a, b) -> a.getReservationID() - b.getReservationID());
            if (reservations.size() == 0)
                reservation.setReservationID(1);
            else
                reservation.setReservationID(reservations.get(reservations.size() - 1).getReservationID() + 1);
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray reservationsArray = new JSONArray(content);

            JSONObject reservationObject = new JSONObject();
            reservationObject.put("reservationID", reservation.getReservationID());
            reservationObject.put("userID", reservation.getUserID());
            reservationObject.put("hotelID", reservation.getHotelID());
            reservationObject.put("roomID", reservation.getRoomID());
            reservationObject.put("startDate", reservation.getStartDate().toString());
            reservationObject.put("endDate", reservation.getEndDate().toString());
            reservationObject.put("totalPrice", reservation.getTotalPrice().toString());

            reservationsArray.put(reservationObject);

            Files.write(Paths.get(filePath), reservationsArray.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateReservation(Reservation reservation) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray reservationsArray = new JSONArray(content);

            for (int i = 0; i < reservationsArray.length(); i++) {
                JSONObject reservationObject = reservationsArray.getJSONObject(i);
                if (reservationObject.getInt("reservationID") == reservation.getReservationID()) {
                    reservationObject.put("userID", reservation.getUserID());
                    reservationObject.put("hotelID", reservation.getHotelID());
                    reservationObject.put("roomID", reservation.getRoomID());
                    reservationObject.put("startDate", reservation.getStartDate().toString());
                    reservationObject.put("endDate", reservation.getEndDate().toString());
                    break;
                }
            }

            Files.write(Paths.get(filePath), reservationsArray.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteReservation(int id) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray reservationsArray = new JSONArray(content);

            for (int i = 0; i < reservationsArray.length(); i++) {
                JSONObject reservationObject = reservationsArray.getJSONObject(i);
                if (reservationObject.getInt("reservationID") == id) {
                    reservationsArray.remove(i);
                    break;
                }
            }

            Files.write(Paths.get(filePath), reservationsArray.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Reservation> getUserReservations(int userID) {
        List<Reservation> userReservations = new ArrayList<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray reservationsArray = new JSONArray(content);

            for (int i = 0; i < reservationsArray.length(); i++) {
                JSONObject reservationObject = reservationsArray.getJSONObject(i);
                if (reservationObject.getInt("userID") == userID) {
                    Reservation reservation = new Reservation();
                    reservation.setReservationID(reservationObject.getInt("reservationID"));
                    reservation.setUserID(reservationObject.getInt("userID"));
                    reservation.setHotelID(reservationObject.getInt("hotelID"));
                    reservation.setRoomID(reservationObject.getInt("roomID"));
                    reservation.setStartDate(Date.valueOf(reservationObject.getString("startDate")));
                    reservation.setEndDate(Date.valueOf(reservationObject.getString("endDate")));
                    reservation.setTotalPrice(reservationObject.getBigDecimal("totalPrice"));
                    userReservations.add(reservation);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userReservations;
    }

    public Reservation findRoom(User user, Date startDate, Date endDate, int starCount, String city,
            RoomType roomType) {

        HotelDAO hotelDAO = new HotelDAO();
        List<Hotel> hotels = hotelDAO.getHotels();
        for (Hotel hotel : hotels) {

            if (hotel.getStarCount() == starCount && hotel.getCity().equals(city)) {

                RoomService roomService = new RoomService();
                Room room = roomService.getRoomByHotelAndRoomType(startDate, endDate, hotel.getHotelID(), roomType);
                Reservation reservation = new Reservation();

                reservation.setHotelID(hotel.getHotelID());
                reservation.setRoomID(room.getRoomID());
                reservation.setStartDate(startDate);
                reservation.setEndDate(endDate);
                reservation.setUserID(user.getUserID());

                int totalDays = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
                BigDecimal totalPrice = BigDecimal.valueOf(room.getPrice() * totalDays);
                reservation.setTotalPrice(totalPrice);

                return reservation;
            }
        }

        return null;

    }
}