import java.util.Scanner;
import java.sql.*;
import java.util.Random;

public class Program {
    static Connection con;

    public static boolean login(Scanner input) {
        System.out.println("\n===LOGIN SPFC MATCH===");
        System.out.print("Username: ");
        String username = input.next();
        System.out.print("Password: ");
        String password = input.next();

        //username dan password nya
        return username.equals("hattaasrirahman") && password.equals("LulusAAL73TH2024");
    }

    public static String generateCaptcha() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captcha = new StringBuilder();
        Random rnd = new Random();

        // Generate random characters (e.g., length = 6)
        int length = 6;
        for (int i = 0; i < length; i++) {
            captcha.append(characters.charAt(rnd.nextInt(characters.length())));
        }

        return captcha.toString();
    }

    public static void showCaptcha(String captcha) {
        System.out.println("\n===CAPTCHA===");
        System.out.println("Masukkan CAPTCHA ini: " + captcha);
    }

    public static boolean verifyCaptcha(Scanner input, String captcha) {
        System.out.print("Jawaban CAPTCHA: ");
        String jawaban = input.next();

        return jawaban.equals(captcha);
    }

    public static void main(String[] args) throws Exception {
        try (Scanner input = new Scanner(System.in)) {
            String inputanPengguna;
            boolean isLanjutkan = true;
            String link = "jdbc:mysql://localhost:3306/TiketBola";

            try {
                if (login(input)) {
                    System.out.println("\nLOGIN BERHASIL");

                    while (isLanjutkan) {
                        String captcha = generateCaptcha();
                        showCaptcha(captcha);

                        if (verifyCaptcha(input, captcha)) {
                            Tagihan tagihan = new Tagihan();
                            Class.forName("com.mysql.jdbc.Driver");
                            con = DriverManager.getConnection(link, "root", "");
                            System.out.println("\nKONEKSI KE DATABASE SUDAH BERHASIL");

                            String sapa = "Halo";
                            String ubahkalimat = sapa.replace("Halo", "\nSelamat pagi :)\n");
                            System.out.println(ubahkalimat);

                            tagihan.timedate();
                            System.out.println("===PROGRAM Penjualan Tiket Bola===");
                            System.out.println("Pilihan Menu");
                            System.out.println("1. Lihat Data Pembeli");
                            System.out.println("2. Tambah Data Pembeli");
                            System.out.println("3. Ubah Data Pembeli");
                            System.out.println("4. Hapus Data Pembeli");

                            System.out.print("\nSILAHKAN PILIH MENU (1-4): ");
                            inputanPengguna = input.next();

                            switch (inputanPengguna) {
                                case "1":
                                    tagihan.tampil();
                                    break;
                                case "2":
                                    tagihan.tambah();
                                    break;
                                case "3":
                                    tagihan.ubah();
                                    break;
                                case "4":
                                    tagihan.hapus();
                                    break;
                                default:
                                    System.err.println("\nMOHON INPUT DENGAN BENAR!(1-4)");
                            }

                            System.out.print("\nMasih Ingin Menggunakan Program? [y/t] ");
                            inputanPengguna = input.next();
                            isLanjutkan = inputanPengguna.equalsIgnoreCase("y");
                        } else {
                            System.err.println("\nJawaban CAPTCHA salah, silakan coba lagi.");
                        }
                    }
                } else {
                    System.err.println("\nLogin gagal. Username atau password salah.");
                }

                System.out.println("PROGRAM SELESAI");
            } catch (ClassNotFoundException ex) {
                System.err.println("DRIVER ERROR");
                System.exit(0);
            } catch (Exception e) {
                System.err.println("KONEKSI KE DATABASE GAGAL" + e.getMessage());
            }
        }
    }
}
