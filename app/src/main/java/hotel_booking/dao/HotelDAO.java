package hotel_booking.dao;

import hotel_booking.model.Hotel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class HotelDAO {
    private String filePath = "/Users/ali/Developer/resmgmt/db/hotels.json";

    public List<Hotel> getHotels() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray hotelsArray = new JSONArray(content);
            List<Hotel> hotels = new ArrayList<>();

            for (int i = 0; i < hotelsArray.length(); i++) {
                JSONObject hotelObject = hotelsArray.getJSONObject(i);
                Hotel hotel = new Hotel(); // Assuming Room is a class with appropriate fields and setters
                hotel.setHotelID(hotelObject.getInt("hotelID"));
                hotel.setName(hotelObject.getString("name"));
                hotel.setCity(hotelObject.getString("city"));
                hotel.setStarCount(hotelObject.getInt("starCount"));
                hotels.add(hotel);
            }
            return hotels;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    public Hotel getHotelById(int id) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray hotelsArray = new JSONArray(content);
            for (int i = 0; i < hotelsArray.length(); i++) {
                JSONObject hotelObject = hotelsArray.getJSONObject(i);
                if (hotelObject.getInt("hotelID") == id) {
                    Hotel hotel = new Hotel(); // Assuming Hotel is a class with appropriate fields and setters
                    hotel.setHotelID(hotelObject.getInt("hotelID"));
                    hotel.setName(hotelObject.getString("name"));
                    hotel.setCity(hotelObject.getString("city"));
                    hotel.setStarCount(hotelObject.getInt("starCount"));
                    return hotel;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}