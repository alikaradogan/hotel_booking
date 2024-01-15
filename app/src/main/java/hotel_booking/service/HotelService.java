package hotel_booking.service;

import java.util.List;

import hotel_booking.dao.HotelDAO;
import hotel_booking.enums.HotelRating;
import hotel_booking.model.Hotel;
import hotel_booking.model.HotelCity;

import java.util.ArrayList;

public class HotelService {

    private HotelDAO hotelDAO;
    public List<String> hotelCities = new ArrayList<String>();
    public List<Integer> hotelStars = new ArrayList<Integer>();

    public HotelService() {
        this.hotelDAO = new HotelDAO();

    }

    public List<Hotel> getHotels() {
        List<Hotel> hotels = hotelDAO.getHotels();
        for (Hotel hotel : hotels) {
            if (!hotelCities.contains(hotel.getCity())) {
                hotelCities.add(hotel.getCity());
            }
        }
        for (Hotel hotel : hotels) {
            if (!hotelStars.contains(hotel.getStarCount())) {
                hotelStars.add(hotel.getStarCount());
            }
        }
        return hotels;

    }

    public List<Hotel> getHotelsByCityAndRating(HotelCity city, HotelRating rating) {
        List<Hotel> hotels = hotelDAO.getHotels();
        List<Hotel> filteredHotels = new ArrayList<Hotel>();
        for (Hotel hotel : hotels) {
            if (hotel.getCity().equals(city.toString()) && hotel.getStarCount() == rating.getRating()) {
                filteredHotels.add(hotel);
            }
        }
        return filteredHotels;
    }

    public Hotel getHotelById(int id) {
        return hotelDAO.getHotelById(id);
    }
}
