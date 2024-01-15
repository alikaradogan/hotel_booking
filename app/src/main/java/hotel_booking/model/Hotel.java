package hotel_booking.model;

public class Hotel {
    private int hotelID;
    private String name;
    private String city;
    private int starCount;

    public int getHotelID() {
        return hotelID;
    }

    public String getName() {
        return name;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
