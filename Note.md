# DOCKER

To set up a Docker Compose configuration for a Java Spring Boot application, you’ll need to create the necessary Dockerfiles and a `docker-compose.yml` file to manage your application and its dependencies (e.g., a database). Below is a general example of how to do this:

### 1. **Create a Dockerfile for Spring Boot**

You need a `Dockerfile` to build an image for your Spring Boot application. Here’s an example:

```dockerfile
# Use a base image with JDK installed
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file to the container
COPY target/your-application.jar /app/your-application.jar

# Expose the port the app will run on
EXPOSE 8080

# Command to run the app
CMD ["java", "-jar", "your-application.jar"]
```

### 2. **Build Your Spring Boot Application**

Before creating the Docker image, make sure to build your Spring Boot application. You can do this using Maven or Gradle.

For Maven:
```bash
mvn clean package
```

For Gradle:
```bash
./gradlew build
```

Ensure that the generated JAR file (`your-application.jar`) is in the `target/` (or `build/libs/` for Gradle) directory.

### 3. **Create a `docker-compose.yml` File**

Now, create a `docker-compose.yml` file to define the services that your application needs. This includes your Spring Boot application and any other services like a database.

Here’s an example with a Spring Boot application and a MySQL database:

```yaml
version: '3.8'

services:
  springboot-app:
    build: .
    container_name: springboot-app
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/mydb
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: password

  db:
    image: mysql:8.0
    container_name: mysql-db
    environment:
      MYSQL_ROOT_PASSWORD: password
      MYSQL_DATABASE: mydb
    ports:
      - "3306:3306"
```

### Explanation:
- **`springboot-app`**: This is the service for your Spring Boot application. It uses the Dockerfile in the current directory to build the image. It also maps port 8080 on your host to port 8080 in the container, making your application accessible.
- **`db`**: This is the MySQL database service. It uses the official MySQL Docker image and sets the necessary environment variables for database credentials.
- **`depends_on`**: Ensures that the `db` service starts before the Spring Boot application.

### 4. **Build and Run the Docker Compose Services**

Once you have your `Dockerfile` and `docker-compose.yml` in place, you can use Docker Compose to build and start the services.

Run the following command to build and start the services:

```bash
docker-compose up --build
```

### 5. **Accessing the Application**

Once the containers are up and running:
- Your Spring Boot application should be accessible at [http://localhost:8080](http://localhost:8080).
- MySQL is running on port 3306 on the host machine (if you want to connect to it externally).

### 6. **Stopping the Services**

To stop the services, run:

```bash
docker-compose down
```

### Conclusion

This setup provides a way to run your Spring Boot application with Docker Compose, which can be extended further to include more services like Redis, RabbitMQ, etc. Just make sure to adjust the configuration (e.g., environment variables) according to your application's requirements.

## Berikut penjelasan lebih detail mengenai **command** dan **perintah** yang biasa digunakan pada file `docker-compose.yml` untuk mengelola kontainer Docker menggunakan Docker Compose:

### 1. **`version`**
```yaml
version: '3.8'
```
- **Tujuan**: Menentukan versi dari format `docker-compose.yml` yang digunakan.
- **Penjelasan**: Di sini, kita menggunakan versi `3.8` dari Docker Compose. Versi ini mencakup fitur-fitur terbaru dan kompatibilitas dengan Docker yang lebih baru.

### 2. **`services`**
```yaml
services:
  springboot-app:
    ...
  db:
    ...
```
- **Tujuan**: Menentukan berbagai layanan (services) yang dijalankan di dalam Docker Compose.
- **Penjelasan**: Setiap layanan mewakili sebuah kontainer yang akan dijalankan oleh Docker Compose. Dalam contoh ini, ada dua layanan:
    - `springboot-app`: Layanan untuk aplikasi Spring Boot.
    - `db`: Layanan untuk database MySQL.

### 3. **`build`**
```yaml
build: .
```
- **Tujuan**: Menentukan Dockerfile yang digunakan untuk membangun image untuk layanan tersebut.
- **Penjelasan**: `.` menunjukkan bahwa Dockerfile berada di direktori yang sama dengan file `docker-compose.yml`. Docker Compose akan menjalankan perintah `docker build` pada direktori ini untuk membangun image aplikasi Spring Boot.

### 4. **`image`**
```yaml
image: mysql:8.0
```
- **Tujuan**: Menentukan image yang digunakan untuk layanan tertentu.
- **Penjelasan**: Di bagian `db`, kita menggunakan image resmi MySQL versi 8.0. Docker akan menarik image tersebut dari Docker Hub jika belum ada pada mesin lokal.

### 5. **`container_name`**
```yaml
container_name: springboot-app
```
- **Tujuan**: Menentukan nama khusus untuk kontainer.
- **Penjelasan**: Ini memberi nama pada kontainer yang dijalankan, membuatnya lebih mudah dikenali daripada menggunakan nama acak yang dihasilkan Docker.

### 6. **`ports`**
```yaml
ports:
  - "8080:8080"
```
- **Tujuan**: Menentukan pemetaan port antara kontainer dan host.
- **Penjelasan**:
    - `8080:8080` berarti port 8080 pada mesin host akan dipetakan ke port 8080 dalam kontainer.
    - Ini memungkinkan aplikasi Spring Boot yang berjalan di dalam kontainer dapat diakses melalui `http://localhost:8080` pada mesin host.

### 7. **`depends_on`**
```yaml
depends_on:
  - db
```
- **Tujuan**: Menentukan ketergantungan layanan lainnya.
- **Penjelasan**: Dengan menggunakan `depends_on`, kita memberi tahu Docker Compose bahwa layanan `springboot-app` bergantung pada layanan `db`. Ini memastikan bahwa kontainer `db` (MySQL) akan dimulai terlebih dahulu sebelum kontainer `springboot-app` dijalankan.

### 8. **`environment`**
```yaml
environment:
  SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/mydb
  SPRING_DATASOURCE_USERNAME: root
  SPRING_DATASOURCE_PASSWORD: password
```
- **Tujuan**: Menetapkan variabel lingkungan untuk kontainer.
- **Penjelasan**:
    - `SPRING_DATASOURCE_URL` adalah URL untuk menghubungkan Spring Boot ke database MySQL.
    - Variabel `SPRING_DATASOURCE_USERNAME` dan `SPRING_DATASOURCE_PASSWORD` digunakan oleh aplikasi Spring Boot untuk autentikasi ke database.
    - Di sini, kita menyebutkan `db` sebagai nama layanan MySQL (dalam file Docker Compose ini), yang akan digantikan dengan alamat IP kontainer MySQL di dalam jaringan Docker internal.

### 9. **`networks` (opsional)**
```yaml
networks:
  default:
    driver: bridge
```
- **Tujuan**: Menentukan jaringan Docker untuk menghubungkan kontainer.
- **Penjelasan**:
    - Defaultnya, Docker Compose membuat jaringan internal dengan driver `bridge` untuk menghubungkan kontainer-kontainer yang ada dalam file `docker-compose.yml`.
    - Ini memungkinkan kontainer saling berkomunikasi menggunakan nama layanan (misalnya, `db` untuk MySQL).

### 10. **`volumes` (opsional)**
```yaml
volumes:
  db-data:
```
- **Tujuan**: Menentukan volume untuk menyimpan data yang bersifat persisten di luar kontainer.
- **Penjelasan**:
    - Dengan volume, data yang disimpan dalam kontainer tidak akan hilang ketika kontainer dihentikan atau dihapus.
    - Misalnya, kita bisa menyimpan data database MySQL di volume agar tetap bertahan meski kontainer MySQL dihentikan.

---

### **Perintah untuk Menjalankan Docker Compose**

Setelah file `docker-compose.yml` selesai, berikut adalah beberapa perintah utama yang digunakan untuk menjalankan dan mengelola layanan-layanan tersebut:

1. **Membangun dan Menjalankan Layanan:**
   ```bash
   docker-compose up --build
   ```
    - **`up`**: Memulai semua layanan yang ditentukan dalam file `docker-compose.yml`.
    - **`--build`**: Memastikan bahwa Docker Compose akan membangun ulang image jika ada perubahan pada Dockerfile atau konteks aplikasi.

2. **Menjalankan Layanan di Latar Belakang (detached mode):**
   ```bash
   docker-compose up -d
   ```
    - **`-d`**: Menjalankan layanan di latar belakang (detached mode), memungkinkan terminal untuk digunakan kembali tanpa harus menunggu proses kontainer selesai.

3. **Melihat Log dari Layanan:**
   ```bash
   docker-compose logs
   ```
    - Menampilkan log dari semua kontainer yang berjalan. Anda bisa menggunakan `docker-compose logs [service-name]` untuk melihat log dari layanan tertentu.

4. **Menghentikan Semua Layanan:**
   ```bash
   docker-compose down
   ```
    - Menghentikan dan menghapus semua kontainer yang dijalankan oleh Docker Compose, serta jaringan dan volume yang terkait (jika tidak menggunakan volume persisten).

5. **Menjalankan Layanan Tanpa Membangun Ulang:**
   ```bash
   docker-compose up
   ```
    - Menjalankan kontainer tanpa membangun ulang image, jika Anda yakin image sudah terbangun sebelumnya.

---

### Kesimpulan

- **`docker-compose.yml`** adalah file konfigurasi untuk mengelola beberapa layanan yang terkait (seperti aplikasi dan database) dalam sebuah proyek.
- Anda dapat mengonfigurasi berbagai aspek layanan, termasuk cara membangun image, memetakan port, menetapkan variabel lingkungan, dan mendefinisikan ketergantungan antar layanan.
- Docker Compose memungkinkan Anda untuk dengan mudah membangun dan mengelola aplikasi multi-layanan dalam kontainer, mempermudah pengembangan dan deployment aplikasi berbasis Docker.


## Berikut adalah contoh struktur **Docker Compose** dan **Dockerfile** yang digunakan untuk sebuah aplikasi **Spring Boot Microservices** yang terdiri dari beberapa layanan (misalnya, `service-a` dan `service-b`). Setiap layanan memiliki Dockerfile sendiri-sendiri, dan file `docker-compose.yml` akan ada di root directory untuk mengelola semua layanan tersebut.

### 1. **Struktur Proyek**
```
my-microservices/
│
├── docker-compose.yml
│
├── service-a/
│   ├── Dockerfile
│   ├── target/
│   │   └── service-a.jar
│   └── src/
│
├── service-b/
│   ├── Dockerfile
│   ├── target/
│   │   └── service-b.jar
│   └── src/
│
└── README.md
```

### 2. **Dockerfile untuk Setiap Layanan**

#### Dockerfile untuk `service-a` (`service-a/Dockerfile`)
```dockerfile
# Gunakan image base dengan JDK yang sesuai
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Salin file JAR aplikasi dari target ke kontainer
COPY target/service-a.jar /app/service-a.jar

# Expose port 8080
EXPOSE 8080

# Perintah untuk menjalankan aplikasi
CMD ["java", "-jar", "service-a.jar"]
```

#### Dockerfile untuk `service-b` (`service-b/Dockerfile`)
```dockerfile
# Gunakan image base dengan JDK yang sesuai
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Salin file JAR aplikasi dari target ke kontainer
COPY target/service-b.jar /app/service-b.jar

# Expose port 8081
EXPOSE 8081

# Perintah untuk menjalankan aplikasi
CMD ["java", "-jar", "service-b.jar"]
```

### 3. **docker-compose.yml**
Di file `docker-compose.yml` yang ada di root directory, kita akan mengonfigurasi semua layanan yang akan dijalankan, serta jaringan dan volume yang diperlukan. Misalnya, kita juga akan menggunakan **MySQL** sebagai database untuk layanan tertentu.

```yaml
version: '3.8'

services:
  service-a:
    build: ./service-a
    container_name: service-a
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/mydb
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: password
    networks:
      - backend

  service-b:
    build: ./service-b
    container_name: service-b
    ports:
      - "8081:8081"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/mydb
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: password
    networks:
      - backend

  db:
    image: mysql:8.0
    container_name: mysql-db
    environment:
      MYSQL_ROOT_PASSWORD: password
      MYSQL_DATABASE: mydb
    ports:
      - "3306:3306"
    networks:
      - backend
    volumes:
      - db-data:/var/lib/mysql

networks:
  backend:
    driver: bridge

volumes:
  db-data:
```

### Penjelasan:

- **`version`**: Menentukan versi dari Docker Compose yang digunakan, di sini menggunakan versi `3.8`.
- **`services`**: Mendefinisikan semua layanan yang akan dijalankan dalam aplikasi microservices.
    - **`service-a`** dan **`service-b`** masing-masing adalah layanan untuk dua aplikasi Spring Boot microservices.
        - **`build`**: Menentukan path direktori tempat Dockerfile berada. Dalam hal ini, masing-masing `service-a` dan `service-b` memiliki Dockerfile mereka masing-masing.
        - **`container_name`**: Nama kontainer yang akan digunakan untuk aplikasi tersebut.
        - **`ports`**: Pemetaaan port dari kontainer ke host, misalnya port 8080 di kontainer `service-a` dipetakan ke port 8080 di host.
        - **`depends_on`**: Mengatakan bahwa kedua layanan tersebut bergantung pada layanan `db`, yaitu MySQL.
        - **`environment`**: Menentukan variabel lingkungan, seperti URL database, username, dan password untuk Spring Boot.
        - **`networks`**: Layanan ini akan terhubung ke jaringan `backend` untuk komunikasi antar layanan.
    - **`db`**: Layanan database menggunakan MySQL, di mana kita mengonfigurasi password root dan nama database.
        - **`volumes`**: Data database disimpan di volume persisten bernama `db-data` untuk memastikan data tidak hilang saat kontainer dihentikan atau dihapus.
        - **`ports`**: Port 3306 pada kontainer dipetakan ke port 3306 pada host.
- **`networks`**: Mendefinisikan jaringan internal bernama `backend` yang digunakan oleh semua layanan untuk saling berkomunikasi.
- **`volumes`**: Volume persisten untuk menyimpan data database MySQL.

### 4. **Langkah-Langkah untuk Menjalankan**

#### 1. **Membangun dan Menjalankan Docker Compose**
Untuk membangun dan menjalankan semua layanan (microservices dan database), jalankan perintah berikut dari root direktori proyek:

```bash
docker-compose up --build
```

- Perintah ini akan membangun image untuk setiap layanan berdasarkan Dockerfile yang ada di setiap direktori (`service-a` dan `service-b`).
- Layanan-layanan akan mulai dijalankan di latar belakang.
- Jika Anda ingin menjalankan layanan di latar belakang (detached mode), tambahkan flag `-d`:
  ```bash
  docker-compose up -d --build
  ```

#### 2. **Memeriksa Layanan yang Berjalan**
Setelah kontainer dijalankan, Anda dapat memeriksa status kontainer yang berjalan dengan perintah:

```bash
docker-compose ps
```

#### 3. **Melihat Log Layanan**
Untuk melihat log dari salah satu layanan (misalnya `service-a`), gunakan perintah:

```bash
docker-compose logs service-a
```

Untuk melihat log dari seluruh layanan, cukup jalankan:

```bash
docker-compose logs
```

#### 4. **Menjaga Layanan Agar Tetap Berjalan di Latar Belakang**
Jika Anda menjalankan Docker Compose dengan flag `-d`, Anda dapat menghentikan layanan dengan perintah:

```bash
docker-compose down
```

Ini akan menghentikan semua layanan dan menghapus kontainer, jaringan, dan volume (kecuali volume yang persisten).

### Kesimpulan

Dengan konfigurasi ini, Anda memiliki:
- Aplikasi **Spring Boot Microservices** yang masing-masing dijalankan di kontainer Docker terpisah.
- **MySQL** sebagai database bersama yang diakses oleh kedua layanan.
- Semua layanan saling terhubung melalui jaringan internal yang dikelola oleh Docker Compose.

Pendekatan ini memudahkan pengelolaan aplikasi berbasis mikroservis dalam lingkungan pengembangan atau produksi yang terisolasi dan dapat dipindahkan.