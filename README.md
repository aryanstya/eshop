### **Aryan Primasatya Putra Hidayat - 2206081181**  

<details>  
<summary>Modul 1</summary>  

## **Refleksi 1**  
Dalam **Latihan 1**, saya mengimplementasikan fitur **edit dan hapus produk**. Untuk fitur edit, ketika pengguna mengklik tombol "Edit", sebuah formulir akan ditampilkan berisi detail produk saat ini menggunakan Thymeleaf. Setelah pengguna mengirimkan formulir, produk akan diperbarui. Sementara itu, untuk fitur hapus, ketika pengguna menekan tombol "Hapus", `ProductController.java` akan memanggil layanan yang bertanggung jawab untuk menghapus produk.  

### **Prinsip Clean Code yang Diterapkan:**  
1. **Single Responsibility Principle (SRP)**: `ProductController` hanya menangani permintaan HTTP dan meneruskan logika bisnis ke `ProductService`, menjaga pemisahan tugas.  
2. **Penamaan yang Deskriptif**: Variabel dan metode menggunakan nama yang jelas serta mudah dipahami.  
3. **Menghindari Duplikasi Kode**: Penggunaan service layer untuk menghindari pengulangan logika dalam controller.  
4. **Format Kode yang Konsisten**: Indentasi dan spasi dijaga agar kode tetap rapi dan mudah dibaca.  

### **Praktik Keamanan yang Diterapkan:**  
1. **Validasi Input**: Pembaruan dan penghapusan produk hanya dilakukan jika `productId` yang diberikan valid.  
2. **Mencegah Null Pointer Exception**: Menggunakan pemeriksaan null secara aman sebelum mengakses properti objek.  
3. **Enkapsulasi**: Properti produk bersifat privat dan hanya dapat diakses melalui getter dan setter.  
4. **Menghindari Nilai Hardcoded**: UUID dihasilkan secara dinamis untuk menghindari penggunaan nilai tetap.  

### **Area yang Perlu Ditingkatkan:**  
1. **Peningkatan Dependency Injection**: Menggunakan constructor injection daripada field injection di `ProductController` untuk meningkatkan keterujian kode.  
2. **Keamanan Akses Bersamaan di Repository**: Jika `ProductRepository` digunakan secara bersamaan, perlu disinkronkan untuk menghindari race condition.  
3. **Peningkatan Penanganan Kesalahan**: Memberikan pesan kesalahan yang lebih informatif saat memperbarui atau menghapus produk yang tidak ada.  
4. **Penerapan Logging**: Menambahkan mekanisme logging di dalam service untuk melacak perubahan pada produk.  

Dengan menerapkan perbaikan ini, kode akan lebih mudah dipelihara, dikembangkan, dan lebih aman.  

## **Refleksi 2**  
### **Pengujian Unit dan Cakupan Kode**  
Setelah menulis pengujian unit, saya lebih yakin bahwa fitur yang diimplementasikan bekerja sesuai harapan. Pengujian unit membantu memastikan bahwa setiap komponen kode berfungsi dengan benar. Namun, jumlah pengujian yang diperlukan dalam suatu kelas tergantung pada kompleksitas logikanya. Praktik yang baik adalah mencakup semua kemungkinan jalur, termasuk skenario positif dan negatif.  

Untuk memastikan cakupan pengujian yang cukup, kita bisa menggunakan metrik code coverage yang mengukur persentase kode yang dieksekusi selama pengujian. Namun, cakupan 100% tidak menjamin bahwa perangkat lunak bebas dari bug—hanya menunjukkan bahwa semua baris kode telah dieksekusi setidaknya sekali. Oleh karena itu, pengujian fungsional dan integrasi juga sangat penting.  

### **Masalah Clean Code dalam Pengujian Fungsional**  
Pada file `CreateProductFunctionalTest.java`, jika kita menambahkan suite pengujian fungsional baru dengan prosedur setup dan variabel instance yang sama, ini bisa menyebabkan duplikasi kode, yang berdampak negatif pada pemeliharaan dan keterbacaan kode.  

### **Potensi Masalah dan Solusinya**  
1. **Duplikasi Kode**: Pengulangan setup dalam beberapa kelas pengujian menyebabkan tantangan pemeliharaan.  
   - **Solusi**: Ekstraksi logika setup umum ke dalam kelas dasar yang bisa diperluas oleh kelas pengujian lainnya.  

2. **Pelanggaran Prinsip DRY (Don't Repeat Yourself)**: Redundansi dalam logika pengujian meningkatkan risiko inkonsistensi.  
   - **Solusi**: Menggunakan kelas utilitas pengujian atau parameterized tests jika memungkinkan.  

3. **Keterbacaan & Organisasi Pengujian**: Logika pengujian yang serupa di berbagai tempat dapat mengurangi keterbacaan.  
   - **Solusi**: Mengelompokkan pengujian secara logis dan mengikuti konvensi penamaan yang jelas.  

Dengan merestrukturisasi suite pengujian fungsional sesuai prinsip ini, kode pengujian akan lebih bersih, mudah dipelihara, dan lebih scalable.  

</details>  

<details>  
<summary>Modul 2</summary>  

## **Refleksi 2**  
1. **Masalah Kualitas Kode yang Diperbaiki dan Strategi Perbaikannya**  
   - **Masalah**: Import `"import java.util.UUID;"` dan `"import org.springframework.ui.Model;"` di `ProductControllerTest.java` tidak digunakan.  
   - **Strategi**: Menghapus import yang tidak digunakan untuk menjaga kode tetap bersih, mudah dibaca, dan efisien.  

2. **Evaluasi CI/CD**  
   - Workflow CI sudah otomatis membangun proyek, menjalankan pengujian unit, dan melakukan analisis kualitas serta keamanan kode setiap kali ada push atau pull request.  
   - Workflow deployment secara otomatis membuat image Docker dan menerapkannya ke Koyeb ketika ada push ke branch utama.  
   - Mekanisme pemeriksaan terjadwal dan perlindungan branch meningkatkan keandalan serta keamanan proses integrasi dan deployment.  
   - **Potensi peningkatan**: Menambahkan pengujian integrasi dan deployment multi-lingkungan untuk memperkuat pipeline CI/CD.  

</details>  

<details>  
<summary>Modul 3</summary>  

1. **Prinsip Clean Code yang Diterapkan**  
   - **Single Responsibility Principle (SRP)**: `CarController` dan `ProductController` dipisahkan agar masing-masing hanya menangani tugasnya sendiri.  
   - **Open-Closed Principle (OCP)**: Menggunakan antarmuka (`CarService` dan `ProductService`) agar fitur baru bisa ditambahkan tanpa mengubah kode yang ada.  
   - **Dependency Inversion Principle (DIP)**: Controller bergantung pada antarmuka, bukan implementasi spesifik, sehingga lebih fleksibel untuk pengujian dan pembaruan.  

2. **Pentingnya Prinsip Ini**  
   - **SRP**: Memudahkan pengelolaan proyek, sehingga perubahan pada fitur mobil tidak memengaruhi fitur produk.  
   - **OCP**: Memungkinkan pengembangan tanpa mengganggu kode yang sudah berfungsi, mengurangi risiko bug.  
   - **DIP**: Mempermudah pengujian karena bisa menggunakan mock data tanpa bergantung pada database asli.  

3. **Konsekuensi Jika Tidak Diterapkan**  
   - Tanpa **SRP**, kode menjadi berantakan karena fitur mobil dan produk bercampur dalam satu controller.  
   - Tanpa **OCP**, setiap penambahan fitur memerlukan perubahan pada kode lama, meningkatkan kemungkinan error.  
   - Tanpa **DIP**, pengujian menjadi sulit karena controller akan terikat pada implementasi spesifik layanan.  

</details>  

<details>  
<summary>Modul 4</summary>  

## **Refleksi 4**  

1. **Efektivitas TDD dalam Pengembangan Kode yang Andal**  
   - Mengikuti alur **TDD** membantu dalam menyusun proses implementasi secara lebih sistematis. Dengan memulai dari pengujian, saya dapat fokus pada perilaku yang diharapkan sebelum menulis kode sebenarnya.  
   - Namun, tantangannya adalah mendefinisikan pengujian sebelum memahami semua edge case, yang kadang menyebabkan perlu adanya refaktor pada pengujian.  
   - **Peningkatan yang bisa dilakukan**:  
     - Memperjelas deskripsi pengujian untuk mendokumentasikan tujuan dengan lebih baik.  
     - Menyempurnakan assertion agar pesan kegagalan lebih jelas.  
     - Menggunakan parameterized tests untuk skenario yang berulang.  

2. **Evaluasi Pengujian Berdasarkan Prinsip F.I.R.S.T.**  

| Prinsip  | Evaluasi |  
|------------|------------|  
| **Fast** | Pengujian berjalan cepat karena menggunakan operasi dalam memori dan mock, tanpa ketergantungan berat. |  
| **Independent** | Setiap pengujian berdiri sendiri dan tidak bergantung pada status eksternal. |  
| **Repeatable** | Pengujian memberikan hasil yang konsisten setiap kali dijalankan. |  
| **Self-Validating** | Setiap pengujian memiliki assertion yang jelas untuk menentukan keberhasilan/kegagalan secara otomatis. |  
| **Timely** | Beberapa pengujian ditulis setelah implementasi, bukan sebelum. Perlu lebih disiplin dalam mengikuti siklus **Red-Green-Refactor**. |  

### **Langkah Perbaikan Selanjutnya**  
- Menambah cakupan pengujian edge case.  
- Menambahkan pengujian integrasi.  
- Memperbaiki struktur pengujian agar lebih terorganisir.  

</details>
