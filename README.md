### **Aryan Primasatya Putra Hidayat - 2206081181**  

<details>
<summary>Modul 1</summary>

## Refleksi 1
Dalam latihan pertama, saya mengimplementasikan fitur edit dan hapus produk. Pada fitur edit, saat pengguna mengklik tombol Edit, sebuah formulir akan ditampilkan dengan detail produk yang sudah ada menggunakan Thymeleaf. Setelah pengguna mengirimkan formulir, produk akan diperbarui sesuai perubahan yang dilakukan. Untuk fitur hapus, saat pengguna mengklik tombol Hapus, `ProductController.java` akan memanggil layanan yang bertanggung jawab untuk menghapus produk.

### Prinsip Clean Code yang Diterapkan:
1. **Single Responsibility Principle (SRP)**: `ProductController` hanya menangani permintaan HTTP, sementara logika bisnis diserahkan kepada `ProductService`, memastikan pemisahan tanggung jawab.
2. **Penamaan yang Jelas**: Variabel dan metode diberi nama yang deskriptif agar lebih mudah dipahami.
3. **Menghindari Duplikasi Kode**: Logika bisnis dikelola di service layer untuk menghindari pengulangan kode dalam controller.
4. **Format Konsisten**: Struktur kode rapi dengan indentasi dan spasi yang sesuai untuk meningkatkan keterbacaan.

### Praktik Keamanan dalam Kode:
1. **Validasi Input**: Pembaruan dan penghapusan produk hanya diproses jika `productId` yang valid diberikan.
2. **Mencegah Null Pointer Exception**: Dilakukan pengecekan null sebelum mengakses properti objek.
3. **Encapsulation**: Properti produk dibuat private, dan aksesnya dikendalikan melalui getter dan setter.
4. **Menghindari Nilai yang Ditetapkan Secara Hardcoded**: UUID dibuat secara dinamis untuk memastikan keunikan data.

### Area yang Perlu Ditingkatkan:
1. **Peningkatan Dependency Injection**: Menggunakan constructor injection daripada field injection dalam `ProductController` untuk meningkatkan testabilitas.
2. **Keamanan Akses Bersamaan di Repository**: Jika `ProductRepository` digunakan dalam lingkungan multithreading, diperlukan mekanisme sinkronisasi.
3. **Penanganan Error yang Lebih Baik**: Menyediakan pesan error yang lebih informatif saat terjadi kesalahan saat memperbarui atau menghapus produk.
4. **Penerapan Logging**: Menambahkan logging di dalam layanan untuk melacak perubahan pada produk.

Dengan perbaikan ini, kode akan lebih mudah dikelola, diperluas, dan lebih aman.

## Refleksi 2
### Pengujian Unit dan Cakupan Kode:
Setelah menulis unit test, saya lebih yakin bahwa fitur yang telah diimplementasikan bekerja dengan benar. Pengujian unit memastikan bahwa setiap komponen kode berfungsi sebagaimana mestinya. Namun, jumlah unit test yang ideal tergantung pada kompleksitas logika yang diuji. Sebaiknya, pengujian mencakup semua kemungkinan jalur eksekusi, termasuk skenario positif dan negatif.

Untuk memastikan cakupan pengujian yang memadai, kita dapat menggunakan metrik cakupan kode yang mengukur persentase kode yang dijalankan selama pengujian. Namun, cakupan kode 100% tidak selalu berarti perangkat lunak bebas dari bug, karena masih bisa ada skenario tepi dan kesalahan logis yang tidak terdeteksi oleh pengujian unit saja. Oleh karena itu, pengujian fungsional dan integrasi juga sangat penting.

### Masalah Clean Code dalam Pengujian Fungsional
Dalam `CreateProductFunctionalTest.java`, jika ada suite pengujian fungsional baru yang ditambahkan dengan prosedur setup yang sama dan variabel instance yang berulang, maka dapat menyebabkan duplikasi kode. Hal ini dapat berdampak negatif terhadap keterbacaan dan pemeliharaan kode.

### Masalah dan Perbaikan yang Dapat Dilakukan:
1. **Duplikasi Kode**: Pengulangan kode setup dalam beberapa kelas pengujian membuat pemeliharaan lebih sulit.
   - **Solusi**: Memindahkan logika setup umum ke dalam kelas dasar yang dapat diperluas oleh kelas pengujian lainnya.
2. **Pelanggaran Prinsip DRY (Don't Repeat Yourself)**: Penulisan logika pengujian yang berulang meningkatkan risiko inkonsistensi.
   - **Solusi**: Menggunakan kelas utilitas pengujian atau pengujian parameterized jika memungkinkan.
3. **Keterbacaan dan Organisasi Pengujian**: Logika pengujian yang tersebar di berbagai tempat dapat mengurangi keterbacaan kode.
   - **Solusi**: Mengelompokkan pengujian secara logis dan mengikuti konvensi penamaan yang jelas untuk menjelaskan tujuan pengujian.

Dengan menerapkan perbaikan ini, kode pengujian akan lebih bersih, mudah dikelola, dan lebih dapat diperluas.
</details>

<details>
<summary>Modul 2</summary>

## Refleksi 2
1. **Masalah Kualitas Kode yang Diperbaiki dan Strategi Perbaikannya:**
    - **Masalah**: Beberapa impor yang tidak digunakan seperti `import java.util.UUID;` dan `import org.springframework.ui.Model;` ditemukan di `ProductControllerTest.java`.
    - **Strategi**: Menghapus impor yang tidak digunakan untuk menjaga kode tetap bersih, mudah dibaca, dan efisien.

2. **Evaluasi Implementasi CI/CD:**
    - Workflow CI/CD dalam proyek ini telah mengotomatiskan proses build, menjalankan unit test, serta melakukan analisis kualitas dan keamanan kode setiap kali ada perubahan kode.
    - Deployment otomatis dilakukan dengan membangun image Docker dan mengunggahnya ke Koyeb setiap ada perubahan pada branch utama.
    - Dengan adanya pemeriksaan terjadwal dan mekanisme perlindungan branch, reliabilitas dan keamanan pipeline semakin ditingkatkan.
    - Namun, pipeline ini masih dapat diperbaiki dengan menambahkan pengujian integrasi dan mendukung deployment ke beberapa lingkungan berbeda.
</details>

<details>
<summary>Modul 3</summary>

1. **Penerapan Prinsip SOLID:**
   - **Single Responsibility Principle (SRP)**: `CarController` dan `ProductController` dipisahkan ke dalam file yang berbeda untuk menjaga fokusnya masing-masing.
   - **Open-Closed Principle (OCP)**: `CarService` dan `ProductService` menggunakan antarmuka sehingga fitur baru dapat ditambahkan tanpa mengubah kode yang sudah ada.
   - **Dependency Inversion Principle (DIP)**: Controller tidak bergantung pada implementasi layanan tertentu, tetapi pada antarmuka, sehingga lebih mudah diuji dan diperbarui.

2. **Pentingnya Penerapan Prinsip Ini:**
   - **SRP**: Memisahkan tanggung jawab memastikan bahwa perubahan pada satu fitur tidak merusak fitur lain.
   - **OCP**: Sistem lebih fleksibel dan lebih sedikit perubahan kode yang tidak perlu.
   - **DIP**: Mempermudah pengujian dengan menggunakan mock data dibandingkan harus berinteraksi langsung dengan database.

3. **Dampak Jika Tidak Diterapkan:**
   - Pelanggaran **SRP** dapat menyebabkan perubahan pada satu bagian kode mempengaruhi bagian lain secara tidak terduga.
   - Tanpa **OCP**, setiap fitur baru mungkin memerlukan modifikasi kode lama, meningkatkan risiko bug.
   - Tanpa **DIP**, pengujian akan sulit dilakukan karena dependensi yang kuat pada implementasi tertentu.
</details>

<details>
<summary>Modul 4</summary>

## Refleksi 4
1. **Efektivitas TDD dalam Menjamin Keandalan Kode:**
   - Mengikuti pendekatan **Test-Driven Development (TDD)** membantu menyusun proses implementasi dengan lebih terstruktur.
   - Pengujian ditulis sebelum implementasi, memastikan bahwa setiap perubahan memiliki kasus uji yang jelas.
   - Tantangan terbesar adalah mendefinisikan kasus uji sebelum memahami semua edge case, yang kadang mengharuskan refactoring ulang.
   - Perbaikan yang dapat dilakukan:
     - Menyusun deskripsi pengujian dengan lebih jelas.
     - Memperbaiki asersi agar lebih eksplisit.
     - Menggunakan pengujian parameterized untuk skenario berulang.

2. **Evaluasi Terhadap Prinsip F.I.R.S.T. dalam Pengujian Unit:**
   - **Fast**: Pengujian cepat karena menggunakan mock dan tidak bergantung pada database.
   - **Independent**: Setiap pengujian tidak bergantung pada status pengujian lain.
   - **Repeatable**: Hasil pengujian konsisten di setiap kali eksekusi.
   - **Self-Validating**: Hasil pengujian secara otomatis menentukan keberhasilan atau kegagalan.
   - **Timely**: Beberapa pengujian masih ditulis setelah implementasi, perlu peningkatan dalam penerapan siklus **Red-Green-Refactor**.
</details>

