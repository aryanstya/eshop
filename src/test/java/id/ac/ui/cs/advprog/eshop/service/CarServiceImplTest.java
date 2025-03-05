package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCar() {
        Car car = new Car();
        when(carRepository.create(car)).thenReturn(car);

        Car createdCar = carService.create(car);

        assertNotNull(createdCar);
        verify(carRepository).create(car);
    }

    @Test
    void testFindAllCars() {
        List<Car> mockCarList = new ArrayList<>();
        Car car1 = new Car();
        car1.setCarId("1");
        Car car2 = new Car();
        car2.setCarId("2");
        mockCarList.add(car1);
        mockCarList.add(car2);

        Iterator<Car> iterator = mockCarList.iterator();
        when(carRepository.findAll()).thenReturn(iterator);

        List<Car> result = carService.findAll();

        assertEquals(2, result.size());
        verify(carRepository).findAll();
    }

    @Test
    void testFindByIdCarExists() {
        Car car = new Car();
        car.setCarId("1234");

        when(carRepository.findById("1234")).thenReturn(car);

        Car foundCar = carService.findById("1234");

        assertNotNull(foundCar);
        assertEquals("1234", foundCar.getCarId());
        verify(carRepository).findById("1234");
    }

    @Test
    void testFindByIdCarNotExists() {
        when(carRepository.findById("9999")).thenReturn(null);

        Car foundCar = carService.findById("9999");

        assertNull(foundCar);
        verify(carRepository).findById("9999");
    }

    @Test
    void testUpdateCar() {
        Car updatedCar = new Car();
        updatedCar.setCarId("1234");

        when(carRepository.update("1234", updatedCar)).thenReturn(updatedCar);

        carService.update("1234", updatedCar);

        verify(carRepository).update("1234", updatedCar);
    }

    @Test
    void testDeleteCar() {
        doNothing().when(carRepository).delete("1234");

        carService.deleteCarById("1234");

        verify(carRepository).delete("1234");
    }
}