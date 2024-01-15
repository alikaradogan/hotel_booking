package hotel_booking.dao;

import hotel_booking.enums.RoomType;
import hotel_booking.model.Reservation;
import hotel_booking.model.Room;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoomDAO {
    private String filePath = "/Users/ali/Developer/resmgmt/db/rooms.json";

    public List<Room> getRooms() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray roomsArray = new JSONArray(content);
            List<Room> rooms = new ArrayList<>();

            for (int i = 0; i < roomsArray.length(); i++) {
                JSONObject roomObject = roomsArray.getJSONObject(i);
                Room room = new Room(); // Assuming Room is a class with appropriate fields and setters
                room.setRoomID(roomObject.getInt("roomID"));
                room.setHotelID(roomObject.getInt("hotelID"));
                room.setPrice(roomObject.getInt("price"));
                room.setRoomType(RoomType.getRoomTypeFromPrice(roomObject.getInt("price")));
                rooms.add(room);
            }
            return rooms;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Room getRoomById(int roomID, int hotelID) {
        List<Room> rooms = getRooms();
        for (Room room : rooms) {
            if (room.getRoomID() == roomID && room.getHotelID() == hotelID) {
                return room;
            }
        }
        return null;
    }

    public Room getAvailableRoomByHotelAndRoomType(Date startDate, Date endDate, int hotelID, RoomType roomType) {
        List<Room> rooms = getRooms();
        for (Room room : rooms) {
            if (room.getHotelID() == hotelID && room.getRoomType().equals(roomType)) {
                if (isRoomAvailable(room.getRoomID(), startDate, endDate)) {
                    return room;
                }
            }
        }
        return null;
    }

    private boolean isRoomAvailable(int roomID, Date startDate, Date endDate) {
        ReservationDAO reservationDAO = new ReservationDAO();
        List<Reservation> reservations = reservationDAO.getReservations();
        for (Reservation reservation : reservations) {
            if (reservation.getRoomID() == roomID) {
                if (reservation.getStartDate().before(startDate) && reservation.getEndDate().after(endDate)) {
                    return false;
                }
            }
        }
        return true;
    }

}