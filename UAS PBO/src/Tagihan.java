
import java.util.Scanner;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tagihan extends DataPelanggan {
    // block program untuk koneksi ke database
    static Connection con;
    String url = "jdbc:mysql://localhost:3306/TiketBola";
    Scanner input = new Scanner(System.in);

    // berikut adalah block program untuk method date
    public void timedate() {
        LocalDateTime waktu = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy || HH:mm:ss");
        String formattedDate = waktu.format(myFormatObj);
        System.out.println("Waktu : " + formattedDate);
    }

    // database
    // method tampil yg di override dri class pelanggan
    @Override
    public void tampil() throws SQLException {
        System.out.println("\n==TAMPILAN INFO PELANGGAN SPFC MATCH==");
        // block program untuk koneksi ke database
        String sql = "SELECT * FROM tiket_bola";
        con = DriverManager.getConnection(url, "root", "");
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery(sql);

        while (result.next()) {// dilakukan perulangan while untuk method tampil
            System.out.print("\nNomor Tiket: ");
            System.out.print(result.getString("noTiket"));
            System.out.print("\nNama Pelanggan\t : ");
            System.out.print(result.getString("nama"));
            System.out.print("\nNomor Ktp: ");
            System.out.print(result.getString("noKtp"));
            System.out.print("\nJenis Tiket: ");
            System.out.print(result.getString("jenis_Tiket"));
            System.out.print("\nBiaya\t\t : ");
            System.out.print(result.getInt("biaya"));
            System.out.print("\nBayar\t\t : ");
            System.out.print(result.getInt("bayar"));
            System.out.print("\nKembalian\t : ");
            System.out.print(result.getDouble("kembalian"));
            System.out.print("\n");
        }
    }

    // database
    // method ubah yang di override dri class pelanggan
    @Override
    public void ubah() throws SQLException {
        System.out.println("==UPDATE DATA PELANGGAN==");
        try {
            tampil();// method tampil utk menampilkan data pelanggan
            System.out.print("\nMasukkan Nomor ktp pelanggan yang akan di ubah : ");
            Integer noKtp = Integer.parseInt(input.nextLine());

            String sql = "SELECT * FROM tiket_bola WHERE noKtp = " + noKtp;
            con = DriverManager.getConnection(url, "root", "");
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // percabangan if
            if (result.next()) {

                System.out.print("Nama baru [" + result.getString("nama") + "]\t: ");
                String nama = input.nextLine();

                sql = "UPDATE tiket_bola SET nama='" + nama + "' WHERE noKtp='" + noKtp + "'";
                // System.out.println(sql);

                if (statement.executeUpdate(sql) > 0) {
                    System.out.println("Berhasil memperbaharui data Pelanggan (" + noKtp + ")");
                }
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mengedit data");// akan muncul pesan jika terjadi exception
            System.err.println(e.getMessage());
        }

    }

    // database
    // method hapus dioverride dri class pelanggan
    @Override
    public void hapus() throws SQLException {
        System.out.println("==HAPUS DATA PELANGGAN==");

        try {
            tampil();
            System.out.print("\nInput No Ktp : ");
            String keyword = input.nextLine();

            String sql = "DELETE FROM tiket_bola WHERE noKtp LIKE '%"+keyword+"%'" ;
            con = DriverManager.getConnection(url, "root", "");
            Statement statement = con.createStatement();
            // ResultSet result = statement.executeQuery(sql);

            if (statement.executeUpdate(sql) > 0) {
                System.out.println("Data Pelanggan Sudah Dihapus");
            }
        }
        // muncul pesan jika terjadi SQLexception
        catch (SQLException e) {
            System.out.println("Penghapusan Data Gagal");
        }
        // muncul pesan jika terjadi exception
        catch (Exception e) {
            System.out.println("Input data yang benar!");
        }
    }

}
