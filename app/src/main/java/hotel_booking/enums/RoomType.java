package hotel_booking.enums;

public enum RoomType {
    SINGLE(150.00),
    DOUBLE(250.00),
    TRIPLE(350.00),
    SUITE(400.00),
    KING(500.00);

    private double price;

    public double getPrice() {
        return price;
    }

    RoomType(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.valueOf(this.name() + " - " + "$" + this.price);
    }

    public String getTypeName() {
        return String.valueOf(this.name());
    }

    public static RoomType getRoomTypeFromPrice(double price) {
        for (RoomType roomType : RoomType.values()) {
            if (roomType.price == price) {
                return roomType;
            }
        }
        return null;
    }

}