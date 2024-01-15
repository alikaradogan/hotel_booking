package hotel_booking.service;

import java.sql.Date;
import java.util.List;

import hotel_booking.dao.ReservationDAO;
import hotel_booking.dao.RoomDAO;
import hotel_booking.enums.RoomType;
import hotel_booking.model.Reservation;
import hotel_booking.model.Room;

public class RoomService {

    private RoomDAO roomDAO;
    private ReservationDAO resevationDAO;

    public RoomService() {
        this.roomDAO = new RoomDAO();
        this.resevationDAO = new ReservationDAO();
    }

    public List<Room> getAvailableRooms(Date startDate, Date endDate) {
        List<Room> rooms = roomDAO.getRooms();
        List<Reservation> reservations = resevationDAO.getReservations();
        for (Reservation reservation : reservations) {
            if ((reservation.getStartDate().before(startDate) || reservation.getStartDate().before(endDate))
                    && (reservation.getEndDate().after(endDate) || reservation.getEndDate().after(startDate))) {
                rooms.removeIf(room -> room.getRoomID() == reservation.getRoomID()
                        && room.getHotelID() == reservation.getHotelID());
            }
        }
        return rooms;
    }

    public boolean isRoomAvailable(int roomID, Date startDate, Date endDate) {
        List<Reservation> reservations = resevationDAO.getReservations();
        for (Reservation reservation : reservations) {
            if (reservation.getRoomID() == roomID) {
                if (reservation.getStartDate().before(startDate) && reservation.getEndDate().after(endDate)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Room getRoomById(int roomID, int hotelID) {
        return roomDAO.getRoomById(roomID, hotelID);
    }

    public Room getRoomByHotelAndRoomType(Date startDate, Date endDate, int hotelID, RoomType roomType) {
        return roomDAO.getAvailableRoomByHotelAndRoomType(startDate, endDate, hotelID, roomType);
    }
}
