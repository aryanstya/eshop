package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.UUID;

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
    @Test
    void testDeleteProduct_EmptyRepository() {
        assertFalse(productRepository.delete("random-id"));
    }
    @Test
    void testDeleteProduct_NullId() {
        Product product = new Product();
        product.setProductId(null);
        product.setProductName("No ID Product");
        product.setProductQuantity(20);

        productRepository.create(product);
        assertFalse(productRepository.delete("some-id"));
    }
    @Test
    void testUpdateProduct_NoChange() {
        Product product = new Product();
        product.setProductId("456");
        product.setProductName("Same Product");
        product.setProductQuantity(30);

        productRepository.create(product);
        Product sameProduct = new Product();
        sameProduct.setProductId("456");
        sameProduct.setProductName("Same Product");
        sameProduct.setProductQuantity(30);

        Product updatedProduct = productRepository.update(sameProduct);
        assertNotNull(updatedProduct);
        assertEquals("Same Product", updatedProduct.getProductName());
        assertEquals(30, updatedProduct.getProductQuantity());
    }
    @Test
    void testFindById_Success() {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        productRepository.create(product);

        Product foundProduct = productRepository.findById("123");
        assertNotNull(foundProduct);
        assertEquals("Test Product", foundProduct.getProductName());
    }
    @Test
    void testUpdateProduct_Fail_NullProductId() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId(null);
        updatedProduct.setProductName("Invalid Product");
        updatedProduct.setProductQuantity(30);

        Product result = productRepository.update(updatedProduct);
        assertNull(result);
    }
    @Test
    void testDeleteProduct_Fail_NullProductId() {
        boolean result = productRepository.delete(null);
        assertFalse(result);
    }
    @Test
    void testFindById_ProductIdIsNull() {
        Product product = new Product();
        product.setProductId(null);
        productRepository.create(product);

        Product result = productRepository.findById(null);
        assertNull(result);
    }
    @Test
    void testUpdateProduct_Fail_NullProduct() {
        Product result = productRepository.update(null);
        assertNull(result);
    }

    @Test
    void testUpdateProduct_Fail_EmptyRepository() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-id");
        updatedProduct.setProductName("Nonexistent Product");
        updatedProduct.setProductQuantity(50);

        Product result = productRepository.update(updatedProduct);

        // Seharusnya return null karena repository kosong
        assertNull(result);
    }
    @Test
    void testUpdateProduct_Fail_NullInput() {
        Product result = productRepository.update(null);

        // Seharusnya return null karena inputnya null
        assertNull(result);
    }
    @Test
    void testUpdateProduct_Fail_UpdatedProductIdIsNull() {
        Product product = new Product();
        product.setProductId("999");
        product.setProductName("Original Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(null); // ID null
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(20);

        Product result = productRepository.update(updatedProduct);

        // Seharusnya tidak mengubah data karena ID null
        assertNull(result);
        Product existingProduct = productRepository.findById("999");
        assertNotNull(existingProduct);
        assertEquals("Original Product", existingProduct.getProductName());
        assertEquals(10, existingProduct.getProductQuantity()); // Data tetap sama
    }

    @Test
    void testFindById_ProductExists() {
        Product product = new Product();
        String id = UUID.randomUUID().toString();
        product.setProductId(id);
        productRepository.create(product);

        Product foundProduct = productRepository.findById(id);

        assertNotNull(foundProduct, "Produk harus ditemukan.");
        assertEquals(id, foundProduct.getProductId(), "ID produk harus sesuai.");
    }

    @Test
    void testFindById_ProductNotFound() {
        Product foundProduct = productRepository.findById(UUID.randomUUID().toString());

        assertNull(foundProduct, "Produk tidak boleh ditemukan jika ID tidak ada.");
    }
    @Test
    void testFindById_ProductIdIsEmpty() {
        Product product = new Product();
        product.setProductId("");
        productRepository.create(product);
    }
    @Test
    void testDelete_ProductExists() {
        Product product = new Product();
        String id = UUID.randomUUID().toString();
        product.setProductId(id);
        productRepository.create(product);

        boolean isDeleted = productRepository.delete(id);

        assertTrue(isDeleted, "Produk harus berhasil dihapus.");
        assertNull(productRepository.findById(id), "Produk tidak boleh ditemukan setelah dihapus.");
    }
    @Test
    void testDelete_ProductNotFound() {
        boolean isDeleted = productRepository.delete(UUID.randomUUID().toString());
    }
    @Test
    void testDelete_ProductIdIsEmpty() {
        Product product = new Product();
        product.setProductId("");
        productRepository.create(product);
    }
    @Test
    void testDelete_ProductIsNull() {
        boolean isDeleted = productRepository.delete(null);
        assertFalse(isDeleted);
    }
    @Test
    void testDeleteProduct_Fail_EmptyStringId() {
        boolean result = productRepository.delete("");
        assertFalse(result, "Harus gagal menghapus jika ID kosong.");
    }
    @Test
    void testFindById_EmptyStringId() {
        Product result = productRepository.findById("");
        assertNull(result, "Harus mengembalikan null jika ID kosong.");
    }
    @Test
    void testDeleteProduct_SpacesOnlyId() {
        boolean result = productRepository.delete("   "); // ID hanya spasi
        assertFalse(result, "Harus gagal menghapus jika ID hanya berisi spasi.");
    }
    @Test
    void testFindById_SpacesOnlyId() {
        Product result = productRepository.findById("   "); // ID hanya spasi
        assertNull(result, "Harus mengembalikan null jika ID hanya berisi spasi.");
    }
    @Test
    void testDeleteProduct_SpecialCharacters() {
        boolean result = productRepository.delete("\n");
        assertFalse(result, "Harus gagal menghapus jika ID berisi karakter newline.");
    }
    @Test
    void testDelete_Debug() {
        System.out.println("Testing delete with ID: '   '");
        boolean result = productRepository.delete("   ");
        System.out.println("Result: " + result);
        assertFalse(result);
    }
    @Test
    void testDeleteProduct_LongId() {
        String longId = "a".repeat(200);
        boolean result = productRepository.delete(longId);
        assertFalse(result, "Harus gagal menghapus jika ID terlalu panjang.");
    }

    @Test
    void testCreateProduct_ZeroQuantity() {
        Product product = new Product();
        product.setProductId("zero-qty");
        product.setProductName("Empty Stock");
        product.setProductQuantity(0);

        productRepository.create(product);
        Product found = productRepository.findById("zero-qty");
        assertNotNull(found);
        assertEquals(0, found.getProductQuantity());
    }
    @Test
    void testFindAll_IteratorBeyondLimit() {
        Product product = new Product();
        product.setProductId("single");
        product.setProductName("Single Product");
        product.setProductQuantity(1);
        productRepository.create(product);

        Iterator<Product> iterator = productRepository.findAll();
        assertTrue(iterator.hasNext());
        iterator.next(); // Ambil satu produk

        assertFalse(iterator.hasNext(), "Iterator harus habis setelah satu produk.");
    }
    @Test
    void testCreateProduct_NullName() {
        Product product = new Product();
        product.setProductId("null-name");
        product.setProductName(null);
        product.setProductQuantity(10);

        productRepository.create(product);
        Product found = productRepository.findById("null-name");

        assertNotNull(found);
        assertNull(found.getProductName(), "Nama produk harus null jika tidak di-set.");
    }
    @Test
    void testDeleteProduct_Twice() {
        Product product = new Product();
        product.setProductId("to-delete");
        product.setProductName("Temporary Product");
        product.setProductQuantity(10);

        productRepository.create(product);
        assertTrue(productRepository.delete("to-delete"));
        assertFalse(productRepository.delete("to-delete"), "Produk yang sudah dihapus tidak bisa dihapus lagi.");
    }
    @Test
    void testUpdate_NonExistentProduct_DoesNotCreate() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("not-found");
        updatedProduct.setProductName("Should Not Exist");
        updatedProduct.setProductQuantity(99);

        Product result = productRepository.update(updatedProduct);
        assertNull(result, "Update tidak boleh membuat produk baru.");

        assertNull(productRepository.findById("not-found"), "Produk tidak boleh ditemukan setelah update gagal.");
    }
    @Test
    void testFindAllAfterDeletingAllProducts() {
        Product product = new Product();
        product.setProductId("to-delete");
        product.setProductName("Temp");
        product.setProductQuantity(1);
        productRepository.create(product);

        productRepository.delete("to-delete");

        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext(), "Iterator harus kosong setelah semua produk dihapus.");
    }

    @Test
    void testDeleteProduct_VeryLongId() {
        String longId = "a".repeat(1000);
        assertFalse(productRepository.delete(longId), "Hapus harus gagal jika ID sangat panjang.");
    }

    @Test
    void testFindById_SpecialCharacters() {
        String specialId = "#@$%";
        Product product = new Product();
        product.setProductId(specialId);
        product.setProductName("Special ID");
        product.setProductQuantity(5);

        productRepository.create(product);
        Product foundProduct = productRepository.findById(specialId);

        assertNotNull(foundProduct, "Produk dengan ID spesial harus ditemukan.");
        assertEquals(specialId, foundProduct.getProductId());
    }

    @Test
    void testUpdateProduct_EmptyRepository_DoesNotCreate() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("not-found");
        updatedProduct.setProductName("Should Not Exist");
        updatedProduct.setProductQuantity(99);

        Product result = productRepository.update(updatedProduct);
        assertNull(result, "Update tidak boleh membuat produk baru.");

        assertNull(productRepository.findById("not-found"), "Produk tidak boleh ditemukan setelah update gagal.");
    }

}