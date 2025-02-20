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
        verify(model).addAttribute(eq("product"), any(Product.class));
        assertEquals("createProduct", viewName);
    }

    @Test
    void testCreateProductPost() {
        Product product = new Product();
        String viewName = productController.createProductPost(product, model);
        verify(productService).create(any(Product.class));
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testProductListPage() {
        List<Product> productList = new ArrayList<>();
        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.productListPage(model);
        verify(model).addAttribute("products", productList);
        assertEquals("productList", viewName);
    }

    @Test
    void testEditProductPage() {
        String id = UUID.randomUUID().toString();
        Product product = new Product();
        product.setProductId(id);
        List<Product> productList = List.of(product);

        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.editProductPage(id, model);
        verify(model).addAttribute("product", product);
        assertEquals("editProduct", viewName);
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
}
