package id.ac.ui.cs.advprog.eshop.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.UUID;
import id.ac.ui.cs.advprog.eshop.model.Car;

public class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateCarWithNullId() {
        Car car = new Car();
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        Car createdCar = carRepository.create(car);

        assertNotNull(createdCar.getCarId());
        assertEquals("Toyota", createdCar.getCarName());
        assertEquals("Red", createdCar.getCarColor());
        assertEquals(5, createdCar.getCarQuantity());
    }

    @Test
    void testCreateCarWithExistingId() {
        Car car = new Car();
        car.setCarId(UUID.randomUUID().toString());
        car.setCarName("Honda");

        Car createdCar = carRepository.create(car);
        assertEquals(car.getCarId(), createdCar.getCarId());
    }

    @Test
    void testFindAllReturnsIterator() {
        Car car1 = new Car();
        Car car2 = new Car();
        carRepository.create(car1);
        carRepository.create(car2);

        Iterator<Car> iterator = carRepository.findAll();
        assertNotNull(iterator);
        assertTrue(iterator.hasNext());
    }

    @Test
    void testFindByIdCarExists() {
        Car car = new Car();
        car.setCarId("1234");
        carRepository.create(car);

        Car foundCar = carRepository.findById("1234");

        assertNotNull(foundCar);
        assertEquals("1234", foundCar.getCarId());
    }

    @Test
    void testFindByIdCarNotExists() {
        Car existingCar = new Car();
        existingCar.setCarId("existing-id");
        carRepository.create(existingCar);

        // Now search for an ID that does not exist
        Car foundCar = carRepository.findById("unknown-id");

        // This forces the method to iterate over at least one car before hitting `return null;`
        assertNull(foundCar);
    }

    @Test
    void testUpdateCarExists() {
        Car car = new Car();
        car.setCarId("1234");
        car.setCarName("Toyota");
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("Honda");
        updatedCar.setCarColor("Blue");
        updatedCar.setCarQuantity(10);

        Car result = carRepository.update("1234", updatedCar);

        assertNotNull(result);
        assertEquals("Honda", result.getCarName());
        assertEquals("Blue", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }

    @Test
    void testUpdateCarNotExists() {
        Car existingCar = new Car();
        existingCar.setCarId("existing-id");
        carRepository.create(existingCar);

        Car updatedCar = new Car();
        updatedCar.setCarName("Updated Car");
        updatedCar.setCarColor("Blue");
        updatedCar.setCarQuantity(10);

        Car result = carRepository.update("unknown-id", updatedCar);

        assertNull(result);
    }

    @Test
    void testDeleteCarExists() {
        Car car = new Car();
        car.setCarId("1234");
        carRepository.create(car);

        carRepository.delete("1234");

        Car foundCar = carRepository.findById("1234");
        assertNull(foundCar);
    }

    @Test
    void testDeleteCarNotExists() {
        carRepository.delete("5678");
        // No exception should be thrown
    }

    @Test
    void testFindByIdReturnsNullForNonExistingCar() {
        Car foundCar = carRepository.findById("non-existent-id");
        assertNull(foundCar);
    }

    @Test
    void testUpdateNonExistingCarReturnsNull() {
        Car updatedCar = new Car();
        updatedCar.setCarName("Updated Car");
        updatedCar.setCarColor("Green");
        updatedCar.setCarQuantity(10);

        Car result = carRepository.update("non-existent-id", updatedCar);
        assertNull(result);
    }

    @Test
    void testDeleteNonExistingCar() {
        // Add at least one car before trying to delete a non-existing one
        Car existingCar = new Car();
        existingCar.setCarId("existing-id");
        carRepository.create(existingCar);

        // Try deleting a car that does not exist
        carRepository.delete("non-existent-id");

        // Ensure the existing car is still there
        assertNotNull(carRepository.findById("existing-id"));
    }

}