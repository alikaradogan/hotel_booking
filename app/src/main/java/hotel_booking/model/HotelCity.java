package hotel_booking.model;

public class HotelCity {
    private String city;

    public HotelCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return String.valueOf(this.city);
    }

}
