package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);
        assertEquals("CreateProduct", viewName);
        verify(model).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void testCreateProductPost() {
        Product product = new Product();
        String viewName = productController.createProductPost(product, model);
        verify(productService).create(any(Product.class));
        assertEquals("redirect:list", viewName);
        verify(productService).create(argThat(p -> p.getProductId() != null && !p.getProductId().isEmpty()));
    }

    @Test
    void testProductListPage() {
        when(productService.findAll()).thenReturn(new ArrayList<>());
        String viewName = productController.productListPage(model);
        assertEquals("ProductList", viewName);
        verify(model).addAttribute(eq("products"), anyList());
    }

    @Test
    void testEditProductPost() {
        Product product = new Product();
        String viewName = productController.editProductPost(product);
        verify(productService).update(product);
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testDeleteProduct() {
        String id = UUID.randomUUID().toString();
        String viewName = productController.deleteProduct(id);
        verify(productService).delete(id);
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testEditProductPage_ProductNotFound() {
        String id = UUID.randomUUID().toString();
        when(productService.findAll()).thenReturn(new ArrayList<>());

        String viewName = productController.editProductPage(id, model);

        assertEquals("redirect:/product/list", viewName);
        verify(model, never()).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void testEditProductPage_NullId() {
        String viewName = productController.editProductPage(null, model);
        assertEquals("redirect:/product/list", viewName);
        verify(model, never()).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void testEditProductPage_EmptyId() {
        String viewName = productController.editProductPage("", model);
        assertEquals("redirect:/product/list", viewName);
        verify(model, never()).addAttribute(eq("product"), any(Product.class));
    }
}