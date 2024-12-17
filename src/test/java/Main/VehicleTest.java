package Main;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    @Test
    void searchVehicleByID() {
        for(int i = 1; i<10; i++) {
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            Random random = new Random();
            StringBuilder buffer = new StringBuilder(6);
            for (int j = 0; j < 6; j++) {
                int randomLimitedInt = leftLimit + (int)
                        (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }
            String generatedString = buffer.toString();
            new Vehicle(i, generatedString, 32, 1);
        }
        new Vehicle(13, "VN45YMA", 3, 1);
        Vehicle v = Vehicle.searchVehicleByID(13);
        assertEquals(v.getPlate(), "VN45YMA");
    }

    @Test
    void searchVehicleByPlate() {
        for(int i = 1; i<10; i++) {
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            Random random = new Random();
            StringBuilder buffer = new StringBuilder(6);
            for (int j = 0; j < 6; j++) {
                int randomLimitedInt = leftLimit + (int)
                        (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }
            String generatedString = buffer.toString();
            new Vehicle(i, generatedString, 32, 1);
        }
        Vehicle v = new Vehicle(13, "VN45YMA", 3, 1);
        v = Vehicle.searchVehicleByPlate("VN45YMA");
        assertEquals(v.getID(), 13);
    }
}