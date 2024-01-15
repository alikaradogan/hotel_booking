package hotel_booking;

import javax.swing.SwingUtilities;

import hotel_booking.screen.MainScreen;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainScreen();
            }
        });
    }
}
