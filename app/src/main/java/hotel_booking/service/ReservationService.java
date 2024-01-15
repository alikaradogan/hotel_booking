package hotel_booking.service;

import java.util.List;

import hotel_booking.dao.ReservationDAO;
import hotel_booking.enums.RoomType;
import hotel_booking.model.Reservation;
import hotel_booking.model.User;

import java.sql.Date;
import java.time.LocalDate;

public class ReservationService {

    private ReservationDAO reservationDAO;

    public ReservationService() {
        this.reservationDAO = new ReservationDAO();
    }

    public void findRoom(User user, Date startDate, Date endDate, int starCount, String city,
            RoomType roomType) throws Exception {
        Reservation reservation = reservationDAO.findRoom(user, startDate, endDate, starCount, city, roomType);
        createReservation(reservation);
    }

    public void createReservation(Reservation reservation) throws Exception {
        if (reservation.getStartDate().after(reservation.getEndDate())) {
            throw new Exception("Başlangıç tarihi bitiş tarihinden sonra olamaz.");
        }

        if (reservation.getStartDate().before(Date.valueOf(LocalDate.now().toString()))) {
            throw new Exception("Başlangıç tarihi bugünden önce olamaz.");
        }

        if (reservation.getEndDate()
                .before(Date.valueOf(reservation.getStartDate().toLocalDate().plusDays(1).toString()))) {
            throw new Exception("Bitiş tarihi başlangıç tarihinde en az bir gün sonra olmalıdır.");
        }

        reservationDAO.addReservation(reservation);
    }

    public void cancelReservation(int reservationID) {
        reservationDAO.deleteReservation(reservationID);
    }

    public List<Reservation> getUserReservations(int userID) {
        return reservationDAO.getUserReservations(userID);
    }

}
