package hotel_booking.enums;

public enum HotelRating {
    ONE_STAR(1),
    TWO_STARS(2),
    THREE_STARS(3),
    FOUR_STARS(4),
    FIVE_STARS(5);

    private int rating;

    public int getRating() {
        return rating;
    }

    HotelRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.valueOf(this.rating + " stars");
    }

    public static HotelRating getHotelRatingFromRating(int rating) {
        for (HotelRating hotelRating : HotelRating.values()) {
            if (hotelRating.rating == rating) {
                return hotelRating;
            }
        }
        return null;
    }

}
