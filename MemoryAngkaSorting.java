// Neyla Mujahidah Villah
// 235150700111008
// Algoritma dan Struktur Data

import javax.swing.*; // Untuk komponen GUI seperti JFrame, JLabel, JButton
import java.awt.*; // Untuk tampilan GUI seperti tata letak
import java.util.Arrays; // Untuk operasi array
import java.util.Random; // Untuk menghasilkan angka acak
import java.util.Timer; // Untuk mengatur waktu.
import java.util.TimerTask; // Untuk menjalankan timer.

// Deklarasi variabel
public class MemoryAngkaSorting {
    private static JFrame frame; // Frame utama 
    private static JPanel panelCards; // Panel untuk menampung semua halaman
    private static JPanel panelUtama, panelSalah; // Halaman utama dan halaman ketika salah
    private static JLabel labelLevel, labelInstruksi, labelAngka; // Untuk menampilkan level, instruksi, dan angka acak
    private static JTextField inputField; // Untuk kolom input 
    private static JButton buttonAscending, buttonDescending, submitButton, cobaLagiButton; // Tombol untuk memilih urutan dan navigasi
    private static Random random = new Random(); // Objek untuk menghasilkan angka acak
    private static int level = 1; // Level permainan, dimulai dari level 1
    private static int panjangUrutan = 2; // Panjang angka pada level 1
    private static int[] urutanAsli; // Array untuk angka acak
    private static int[] urutanTarget; // Array untuk urutan angka yang benar
    private static boolean ascendingOrder; // Mengecek urutan adalah ascending atau descending 

    public static void main(String[] args) {
        // Menginisialisasi tampilan GUI
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Memory Game"); // Frame utama untuk aplikasi dengan judul Memory Game
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Mengatur properti saat jendela ditutup
            frame.setSize(500, 300); // Mengatur ukuran frame utama

            panelCards = new JPanel(new CardLayout()); // Mengatur tata letak saat pergantian panel (halaman)

            // Membuat halaman utama
            panelUtama = new JPanel(); // Objek untuk menyimpan komponen GUI seperti tombol, label, kolom input
            panelUtama.setLayout(new BoxLayout(panelUtama, BoxLayout.Y_AXIS)); // Mengatur layout tampilan secara vertikal (atas ke bawah)
            panelUtama.setAlignmentX(Component.CENTER_ALIGNMENT); // Mengatur elemen berada di tengah

            labelLevel = new JLabel("Selamat Datang di Game", SwingConstants.CENTER); // JLabel untuk menampilkan tampilan teks
            labelLevel.setAlignmentX(Component.CENTER_ALIGNMENT); // Mengatur elemen berada di tengah
            panelUtama.add(Box.createVerticalStrut(10)); // Margin
            panelUtama.add(labelLevel); // Menambahkan tampilan level

            labelInstruksi = new JLabel("Pilih jenis pengurutan di bawah ini!", SwingConstants.CENTER); // JLabel untuk menampilkan tampilan teks
            labelInstruksi.setAlignmentX(Component.CENTER_ALIGNMENT); // Mengatur elemen berada di tengah
            panelUtama.add(Box.createVerticalStrut(10)); // Margin
            panelUtama.add(labelInstruksi); // Menambahkan teks instruksi

            labelAngka = new JLabel("", SwingConstants.CENTER); // Objek untuk menampilkan angka acak
            labelAngka.setAlignmentX(Component.CENTER_ALIGNMENT); // Mengatur elemen berada di tengah
            labelAngka.setVisible(false); // Menyembunyikan angka sebelum permainan dimulai
            panelUtama.add(Box.createVerticalStrut(20)); // Margin
            panelUtama.add(labelAngka); // Menampilkan angka acak

            JPanel panelTombol = new JPanel(new FlowLayout()); // Untuk menata elemen tombol
            buttonAscending = new JButton("Ascending"); // Membuat tombol ascending
            buttonDescending = new JButton("Descending"); // Membuat tombol ascending
            panelTombol.add(buttonAscending); // Menampilkan tombol ascending
            panelTombol.add(buttonDescending); // Menampilkan tombol descending
            panelUtama.add(Box.createVerticalStrut(10)); // Margin
            panelUtama.add(panelTombol); // Menambahkan tombol ke halaman utama

            JPanel panelInput = new JPanel(new FlowLayout()); // Panel untuk kolom input dan tombol submit
            inputField = new JTextField(20); // Membuat kolom input dengan panjang karakter 20
            submitButton = new JButton("Submit"); // Membuat tombol submit
            panelInput.add(inputField); // Menampilkan kolom input
            panelInput.add(submitButton); // Menampilkan tombol submit
            panelUtama.add(Box.createVerticalStrut(10)); // Margin
            panelUtama.add(panelInput); // Menambahkan kolom input ke halaman utama

            // Membuat halaman untuk jawaban salah
            panelSalah = new JPanel(); 
            panelSalah.setLayout(new BoxLayout(panelSalah, BoxLayout.Y_AXIS)); // Mengatur layout secara vertikal
            JLabel labelSalah = new JLabel("", SwingConstants.CENTER); // Untuk menampilkan pesan kesalahan
            labelSalah.setAlignmentX(Component.CENTER_ALIGNMENT); // Mengatur elemen berada di tengah
            panelSalah.add(Box.createVerticalStrut(20)); // Margin
            panelSalah.add(labelSalah); // Menambahkan label ke halaman salah

            cobaLagiButton = new JButton("Coba Lagi"); // Membuat tombol 'coba lagi'
            cobaLagiButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Mengatur elemen berada di tengah
            panelSalah.add(Box.createVerticalStrut(20)); // Margin
            panelSalah.add(cobaLagiButton); // Menampilkan tombol "Coba Lagi"

            // Menambahkan halaman ke panelCards
            panelCards.add(panelUtama, "main"); // Halaman utama diberi nama "main"
            panelCards.add(panelSalah, "wrong"); // Halaman jawaban salah diberi nama "wrong"

            frame.add(panelCards); // Menambahkan panelCards ke frame
            frame.setVisible(true); // Menampilkan frame

            labelAngka.setVisible(false); // Menyembunyikan angka saat pertama kali aplikasi berjalan
            panelInput.setVisible(false); // Menyembunyikan kolom input saat pertama kali aplikasi berjalan

            // Menambahkan action listener untuk tombol
            buttonAscending.addActionListener(e -> mulaiGame(true, panelInput)); // Jika tombol ascending diklik
            buttonDescending.addActionListener(e -> mulaiGame(false, panelInput)); // Jika tombol descending diklik
            submitButton.addActionListener(e -> evaluasiJawaban(panelInput, labelSalah)); // Jika tombol submit diklik
            cobaLagiButton.addActionListener(e -> resetGame(panelInput)); // Jika tombol coba lagi diklik
        });
    }

    // Fungsi untuk memulai game berdasarkan urutan yang dipilih
    private static void mulaiGame(boolean ascending, JPanel panelInput) {
        ascendingOrder = ascending; // Menyimpan jenis urutan yang dipilih
        labelLevel.setText("Level " + level); // Menampilkan level game
        labelInstruksi.setText(""); // Menghapus instruksi ktika sudah memilih urutan
        buttonAscending.setVisible(false); // Menyembunyikan tombol urutan setelah dipilih
        buttonDescending.setVisible(false); // Menyembunyikan tombol urutan setelah dipilih

        urutanAsli = new int[panjangUrutan]; // Array untuk menyimpan angka acak
        StringBuilder angkaTampil = new StringBuilder(); // Untuk menampilkan angka acak
        for (int i = 0; i < panjangUrutan; i++) {
            urutanAsli[i] = random.nextInt(9) + 1; // Angka acak antara 1 sampai 9
            angkaTampil.append(urutanAsli[i]).append(" "); // Menambahkan angka ke string
        }
        labelAngka.setText(angkaTampil.toString().trim()); // Objek yang berisi angka acak
        labelAngka.setVisible(true); // Menampilkan angka acak
        panelInput.setVisible(true); // Menampilkan kolom input dan tombol submit

        urutanTarget = ascending ? bubbleSort(urutanAsli.clone()) : bubbleSortDesc(urutanAsli.clone()); // Meengecek urutan yang dipilih

        // Mengatur timer
        Timer timer = new Timer(); // Membuat objek timer
        timer.schedule(new TimerTask() { // Mengatur jadwal timer dijalankan
            @Override
            public void run() {
                labelAngka.setText(""); // Menghilangkan angka setelah 3 detik
                labelInstruksi.setText("Masukkan urutan yang benar:");
            }
        }, 3000); // Mengatur timer selama 3 detik
    }

    // Mengecek inputan pemain
    private static void evaluasiJawaban(JPanel panelInput, JLabel labelSalah) {
        String[] input = inputField.getText().split(" "); // Mengambil input pemain (dan membaca spasi yang diinputkan)
        int[] urutanPengguna = new int[input.length]; // Array untuk menyimpan urutan pemain
        try {
            for (int i = 0; i < input.length; i++) {
                urutanPengguna[i] = Integer.parseInt(input[i]); // Konversi input dari String ke int
            }
            boolean benar = Arrays.equals(urutanPengguna, urutanTarget); // Mengecek urutan inputan 

            if (benar) { // Jika benar
                level++; // Naik ke level berikutnya
                panjangUrutan++; // Menambah panjang urutan angka
                labelInstruksi.setText("Benar! Level Selanjutnya ");
                inputField.setText(""); // Mengosongkan kolom input

                // Mengatur timer sebelum lanjut ke level berikutnya
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mulaiGame(ascendingOrder, panelInput); // Memulai game untuk level berikutnya
                    }
                }, 1000); // Mengatur timer selama 1 detik
            } else { // Jika salah
                labelSalah.setText("Salah! Jawaban yang benar: " + Arrays.toString(urutanTarget)); // Menampilkan jawaban yang benar
                CardLayout cl = (CardLayout) panelCards.getLayout(); 
                cl.show(panelCards, "wrong"); // Berpindah ke halaman jawaban salah
            }
        } catch (NumberFormatException ex) { // Jika input tidak valid
            JOptionPane.showMessageDialog(frame, "Input tidak valid! Gunakan Spasi!");
        }
    }

    // Mereset game saat pemain kalah
    private static void resetGame(JPanel panelInput) {
        level = 1; // Mengembalikan level ke 1
        panjangUrutan = 2; // Mengembalikan panjang urutan 
        labelLevel.setText("Selamat Datang di Game"); 
        labelInstruksi.setText("Pilih jenis pengurutan di bawah ini!"); 
        buttonAscending.setVisible(true); // Menampilkan tombol pilihan urutan
        buttonDescending.setVisible(true); // Menampilkan tombol pilihan urutan
        labelAngka.setVisible(false); // Menyembunyikan angka
        inputField.setText(""); // Mengosongkan kolom input
        panelInput.setVisible(false); // Menyembunyikan panel input

        CardLayout cl = (CardLayout) panelCards.getLayout(); 
        cl.show(panelCards, "main"); // Berpindah ke halaman utama
    }

    // Fungsi untuk mengurutkan array secara ascending 
    private static int[] bubbleSort(int[] arr) {
        int[] sortedArr = arr.clone(); // Membuat salinan array 
        int n = sortedArr.length; // Menyimpan panjang array ke dalam variabel n
    
        // Loop untuk mengontrol jumlah iterasi pada pengurutan
        for (int i = 0; i < n - 1; i++) { 
            // Loop untuk membandingkan elemen dalam array
            for (int j = 0; j < n - i - 1; j++) { 
                // Jika elemen di indeks j lebih besar dari elemen di indeks j+1, maka tukar posisinya
                if (sortedArr[j] > sortedArr[j + 1]) { 
                    int temp = sortedArr[j]; // Menyimpan elemen dalam variabel sementara
                    sortedArr[j] = sortedArr[j + 1]; // Tukar elemen dengan elemen berikutnya
                    sortedArr[j + 1] = temp; // Pindahkan nilai dari variabel sementara ke posisi berikutnya
                }
            }
        }
        return sortedArr; // Mengembalikan array yang sudah diurutkan secara ascending
    }
    
    // Fungsi untuk mengurutkan array secara descending 
    private static int[] bubbleSortDesc(int[] arr) {
        int[] sortedArr = arr.clone(); // Membuat salinan array
        int n = sortedArr.length; // Menyimpan panjang array ke dalam variabel n
    
        // Loop untuk mengontrol jumlah iterasi pada pengurutan
        for (int i = 0; i < n - 1; i++) {
            // Loop untuk membandingkan elemen dalam array
            for (int j = 0; j < n - i - 1; j++) {
                // Jika elemen di indeks j lebih kecil dari elemen di indeks j+1, maka tukar posisinya
                if (sortedArr[j] < sortedArr[j + 1]) { 
                    int temp = sortedArr[j]; // Simpan elemen dalam variabel sementara
                    sortedArr[j] = sortedArr[j + 1]; // Tukar elemen dengan elemen berikutnya
                    sortedArr[j + 1] = temp; // Pindahkan nilai dari variabel sementara ke posisi berikutnya
                }
            }
        }
        return sortedArr; // Mengembalikan array yang sudah diurutkan secara descending (terurut turun)
    }    
}
