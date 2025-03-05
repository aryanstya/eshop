package id.ac.ui.cs.advprog.eshop.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import id.ac.ui.cs.advprog.eshop.controller.CarController;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

class CarControllerTest {

    @Mock
    private CarService carService;

    @Mock
    private Model model;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCarPage() {
        String view = carController.createCarPage(model);
        assertEquals("createCar", view);
        verify(model).addAttribute(eq("car"), any(Car.class));
    }

    @Test
    void testCreateCarPost() {
        Car car = new Car();
        String view = carController.createCarPost(car, model);
        assertEquals("redirect:listCar", view);
        verify(carService).create(car);
    }

    @Test
    void testCarListPage() {
        when(carService.findAll()).thenReturn(Collections.emptyList());
        String view = carController.carListPage(model);
        assertEquals("carList", view);
        verify(model).addAttribute("cars", Collections.emptyList());
    }

    @Test
    void testEditCarPage() {
        Car car = new Car();
        when(carService.findById("1")).thenReturn(car);
        String view = carController.editCarPage("1", model);
        assertEquals("editCar", view);
        verify(model).addAttribute("car", car);
    }



    @Test
    void testDeleteCar() {
        String view = carController.deleteCar("1");
        assertEquals("redirect:listCar", view);
        verify(carService).deleteCarById("1");
    }
}

