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
        verify(productService).create(argThat(p -> p.getProductId() != null && !p.getProductId().isEmpty()));
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
    @Test
    void testEditProductPage_ProductNotFound() {
        String id = UUID.randomUUID().toString();
        List<Product> productList = new ArrayList<>(); // Produk kosong

        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.editProductPage(id, model);

        assertEquals("redirect:/product/list", viewName); // Harus redirect jika tidak ditemukan
        verify(model, never()).addAttribute(eq("product"), any(Product.class));
    }
    @Test
    void testEditProductPage_NullId() {
        String id = null;  // Simulasi id null
        List<Product> productList = new ArrayList<>();
        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.editProductPage(id, model);

        assertEquals("redirect:/product/list", viewName); // Pastikan tetap redirect
        verify(model, never()).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void testEditProductPage_MultipleProductsButNotFound() {
        String id = UUID.randomUUID().toString();
        List<Product> productList = new ArrayList<>();
        productList.add(new Product()); // Produk dengan ID berbeda
        productList.add(new Product()); // Produk lain dengan ID berbeda

        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.editProductPage(id, model);

        assertEquals("redirect:/product/list", viewName);
        verify(model, never()).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void testEditProductPage_FirstProductMatches() {
        String id = UUID.randomUUID().toString();
        Product matchingProduct = new Product();
        matchingProduct.setProductId(id);
        List<Product> productList = new ArrayList<>();
        productList.add(matchingProduct); // Produk pertama cocok
        productList.add(new Product()); // Produk kedua tidak cocok

        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.editProductPage(id, model);

        verify(model).addAttribute("product", matchingProduct);
        assertEquals("editProduct", viewName);
    }
    @Test
    void testDeleteProduct_NullId() {
        String id = null; // Simulasi id null
        String viewName = productController.deleteProduct(id);

        assertEquals("redirect:/product/list", viewName);
        verify(productService, never()).delete(anyString());
    }

    @Test
    void testEditProductPage_EmptyId() {
        String id = ""; // Simulasi id kosong
        List<Product> productList = new ArrayList<>();
        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.editProductPage(id, model);

        assertEquals("redirect:/product/list", viewName);
        verify(model, never()).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void testEditProductPage_MultipleProductsOneMatches() {
        String id = UUID.randomUUID().toString();
        Product matchingProduct = new Product();
        matchingProduct.setProductId(id);

        Product nonMatchingProduct = new Product();
        nonMatchingProduct.setProductId(UUID.randomUUID().toString());

        List<Product> productList = List.of(nonMatchingProduct, matchingProduct);

        when(productService.findAll()).thenReturn(productList);

        String viewName = productController.editProductPage(id, model);

        verify(model).addAttribute("product", matchingProduct);
        assertEquals("editProduct", viewName);
    }

}
