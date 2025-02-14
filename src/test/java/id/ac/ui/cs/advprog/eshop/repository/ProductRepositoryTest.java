package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9646e-90b1-437d-a0bf-d0821dde9098");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    // Update an existing product
    @Test
    void testUpdateProduct_Success() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63444");
        product.setProductName("Old Name");
        product.setProductQuantity(50);

        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63444");
        updatedProduct.setProductName("Available Product");
        updatedProduct.setProductQuantity(75);

        productRepository.update(updatedProduct);
        Product modifiedProduct = productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63444");

        assertEquals("Available Product", modifiedProduct.getProductName());
        assertEquals(75, modifiedProduct.getProductQuantity());
    }

    // Update a non-existent product
    @Test
    void testUpdateProduct_Fail_ProductNotFound() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("a0f9646e-9022-437d-a0bf-d0821dde9098");
        updatedProduct.setProductName("No Product");
        updatedProduct.setProductQuantity(20);

        productRepository.update(updatedProduct);
        assertNull(productRepository.findById("a0f9646e-9022-437d-a0bf-d0821dde9098"));
    }

    // Delete an existing product
    @Test
    void testDeleteProduct_Success() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63444");
        product.setProductName("Un-Add Product");
        product.setProductQuantity(50);

        productRepository.create(product);
        assertNotNull(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63444"));

        productRepository.delete("eb558e9f-1c39-460e-8860-71af6af63444");
        assertNull(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63444"));
    }

    // Delete a non-existent product
    @Test
    void testDeleteProduct_Fail_ProductNotFound() {
        productRepository.delete("a0f9646e-9022-437d-a0bf-d0821dde9098");
        assertNull(productRepository.findById("a0f9646e-9022-437d-a0bf-d0821dde9098"));
    }

}