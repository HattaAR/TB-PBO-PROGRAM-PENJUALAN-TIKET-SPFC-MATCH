import java.util.Scanner;
import java.util.InputMismatchException;
import java.sql.*;
import java.util.HashMap;

//class DataPelanggan merupakan implementasi dari class pelanggan
public class DataPelanggan implements Pelanggan {
    // deklarasi variabel
    static Connection con;
    String url = "jdbc:mysql://localhost:3306/TiketBola";
    int noKtp;
    String nama;
    String jenistiket;
    int biaya;
    int noTiket;
    double totalBiaya;
    int bayar;
    double kembalian;

    // constructor kosong
    public DataPelanggan() {

    }

    // membuat objek baru dengan nama input
    Scanner input = new Scanner(System.in);

    // override method dari interface
    // method tampil dari class pelanggan
    @Override
    public void tampil() throws SQLException {

    }

    // method tambah dari class pelanggan
    @Override
    public void tambah() throws SQLException {
        System.out.println("==INPUT DATA");
        // exception
        try {
            // menginputkan nama pelanggan
            System.out.println("Inputkan Nama : ");
            this.nama = input.nextLine();

            // no pelanggan
            int fail = 0;
            // berikut adalah block program untuk exception
            do {
                try {
                    System.out.println("Inputkan no Ktp : ");
                    this.noKtp = input.nextInt();// inputan user
                    input.nextLine();
                    if (this.noKtp < 1) {// jika inputan < 1, maka akan muncul pesan dibawah
                        System.out.println("Mohon Inputkan angka dengan baik dan benar");
                    }
                } catch (Exception e) {
                    fail += 1;
                    if (fail > 2) {// jika fail > 2, maka akan muncul pesan berikut
                        System.out.println("Mohon perhatikan lagi inputan");
                    } else {// kemudian else akan muncul pesan dibawah
                        System.out.println("Mohon inputkan angka");
                    }
                    input.nextLine();
                }
            } while (this.noKtp < 1); // perulangan akan terus dilakukan selama noPelanggan < 1

            

            // untuk jenis Tiket
            // COLLECTION HASHMAP
            HashMap<String, String> daftartiket  = new HashMap<String, String>();// membuat objek baru dg nama jenis
                                                                                // tiket
            // terdapat 5 jenis tiket  spt berikut
            daftartiket.put("1", "Selatan");
            daftartiket.put("2", "Utara");
            daftartiket.put("3", "Timur");
            daftartiket.put("4", "VIP");
            daftartiket.put("5", "VVIP");
            System.out.println(jenistiket);
            // exception
            do {
                try {
                    System.out.println("Inputkan Jenis tiket (1.selatan/2.utara/3.timur/4.vip/5.vvip) : ");
                    this.noTiket = input.nextInt();// inputan user
                    input.nextLine();
                    if (this.noTiket < 1 || this.noTiket > 5)// jika inputan < 1 dan > 5 maka akan
                                                             // muncul pesan spt dibawah
                    {
                        System.out.println("Inputan harus angka 1-5");
                    }
                } catch (Exception e) {
                    fail += 1;
                    if (fail > 2) {
                        System.out.println("Mohon perhatikan lagi inputan");
                    } else {
                        System.out.println("Mohon Inputkan inputan berupa angka!");
                    }
                    input.nextLine();
                }
            } while (this.noTiket < 1 || this.noTiket > 5); // perulangan akan terus dilakukan
                                                            // jika inputan bernilai <1 dan > 5
            if (this.noTiket == 1) {// jika yang diinputkan angka 1, maka jenis tiket Selatan
                this.jenistiket = "Selatan";
            } else if (this.noTiket == 2) {
                this.jenistiket = "Utara";
            } else if (noTiket == 3) {
                this.jenistiket = "Timur";
            } else if (noTiket == 4) {
                this.jenistiket = "VIP";
            } else if (noTiket == 5) {
                this.jenistiket = "VVIP";
            }

            // untuk biaya
            // percabangan switch dg beberapa case
            switch (jenistiket) {
                case "Selatan":
                    this.biaya = 20000;
                    System.out.println("Tagihan : " + this.biaya);
                    break;
                case "Utara":
                    this.biaya = 20000;
                    System.out.println("Tagihan : " + this.biaya);
                    break;
                case "Timur":
                    this.biaya = 30000;
                    System.out.println("Tagihan : " + this.biaya);
                    break;
                case "VIP":
                    this.biaya = 100000;
                    System.out.println("Tagihan : " + this.biaya);
                    break;
                case "VVIP":
                    this.biaya = 150000;
                    System.out.println("Tagihan : " + this.biaya);
                    break;
            }
            
            // untuk bayar
            // exception
            do {
                try {
                    System.out.println("Inputkan Bayar : ");
                    bayar = input.nextInt();// inputan user
                    input.nextLine();
                    if (bayar < totalBiaya) {
                        System.out.println("Mohon Inputkan angka dengan baik dan benar");
                    }
                } catch (Exception e) {
                    fail += 1;
                    if (fail > 2) {
                        System.out.println("Mohon perhatikan lagi inputan");
                    } else {
                        System.out.println("Mohon inputkan angka");
                    }
                    input.nextLine();
                }
            } while (bayar < biaya); // perulangan akan terus dilakukan jika bayar<totalBiaya

            // kembalian
            // proses matematika untuk kembalian
            this.kembalian = this.bayar - biaya;
            System.out.println("Kembalian = " + this.kembalian);

            // berikut adalah block program untuk koneksi database
            String sql = "INSERT INTO tiket_bola (nama,biaya,noTiket,noKtp,jenis_tiket, bayar, kembalian) VALUES ('"
                    + nama + "','" + biaya + "','"+ noTiket +"','" + noKtp
                    + "','" + jenistiket + "','" + bayar + "','" + kembalian + "')";
            con = DriverManager.getConnection(url, "root", "");
            Statement statement = con.createStatement();
            statement.execute(sql);
            System.out.println("DATA BERHASIL DIINPUT!");
        } catch (SQLException e) {
            System.err.println("DATA GAGAL DIINPUT");
            System.err.println(e.getMessage());

        } catch (InputMismatchException e) {
            System.err.println("Inputan harus berupa angka");

        }
    }

    // method ubah dari class pelanggan     
    @Override
    public void ubah() throws SQLException {

    }

    // method hapus dari class pelanggan
    @Override
    public void hapus() throws SQLException {

    }

    // method cari dari class pelanggan
    @Override
    public void cari() throws SQLException {

    }

}