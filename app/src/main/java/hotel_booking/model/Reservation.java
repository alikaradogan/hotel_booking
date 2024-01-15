package hotel_booking.model;

import java.math.BigDecimal;
import java.sql.Date;

import hotel_booking.service.HotelService;
import hotel_booking.service.RoomService;

public class Reservation {
    private int reservationID;
    private int userID;
    private int hotelID;
    private int roomID;
    private Date startDate;
    private Date endDate;
    private BigDecimal totalPrice;

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    @Override
    public String toString() {
        HotelService hotelService = new HotelService();
        RoomService roomService = new RoomService();
        String hotelName = hotelService.getHotelById(getHotelID()).getName();
        String roomType = roomService.getRoomById(getRoomID(), getHotelID()).getRoomType().toString();
        return hotelName + " (" + startDate + " - " + endDate + ") | " + roomType;
    }

}
