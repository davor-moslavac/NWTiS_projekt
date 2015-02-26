/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.foi.nwtis.davmoslav.klijenti.GoogleMapsKlijent;
import org.foi.nwtis.davmoslav.konfiguracija.Konfiguracija;
import org.foi.nwtis.davmoslav.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.davmoslav.podaci.Location;
import org.foi.nwtis.davmoslav.slusaci.SlusacAplikacije;

/**
 *
 * @author Davor
 */
public class SocketServer extends Thread {

    Konfiguracija konfig;
    BP_Konfiguracija bpKonfig;
    int port;
    String adresaServera;
    String saljePoruku;
    String primaPoruku;
    String predmetPoruke;
    String sadrzajPoruke;
    long pocetak;
    private long trajanjeStanja;
    ServerSocket serverSocket;
    private Socket klijent;
    public Boolean radi = true;
    private String sqlNaredba;
    private int tip;
    int brojPrimljenihKorisnickih = 0;
    int brojNeispravnihKorisnickih = 0;
    int brojIzvršenihKorisnickih = 0;
    String cKey;
    String sKey;
    String korisnik;
    String odgovor;
    InputStream in = null;
    OutputStream out = null;

    public SocketServer() {
        this.bpKonfig = SlusacAplikacije.konfiguracija_baza;
        this.konfig = SlusacAplikacije.konfiguracija;
        this.port = Integer.parseInt(konfig.dajPostavku("port"));
        this.adresaServera = konfig.dajPostavku("adresaServera");
        this.saljePoruku = konfig.dajPostavku("saljePoruku");
        this.primaPoruku = konfig.dajPostavku("primaPoruku");
        this.predmetPoruke = konfig.dajPostavku("predmetPoruke");
        this.cKey = konfig.dajPostavku("cKey");
        this.sKey = konfig.dajPostavku("sKey");
    }

    @Override
    public void interrupt() {
        if (in != null) {
            try {
                in.close();
            } catch (IOException ex) {
                System.out.println(this.getName() + " Problem kod zatvaranja in: " + ex.getMessage());
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException ex) {
                System.out.println(this.getName() + " Problem kod zatvaranja out: " + ex.getMessage());
            }
        }
        if (klijent != null) {
            try {
                klijent.close();
            } catch (IOException ex) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void run() {
        pocetak = System.currentTimeMillis();
        trajanjeStanja = System.currentTimeMillis();
        System.out.println("Preuzimanje naredbi pokrenuto!");

        try {
            serverSocket = new ServerSocket(this.port);

            while (radi) {

                klijent = serverSocket.accept();

                try {
                    in = klijent.getInputStream();
                    out = klijent.getOutputStream();

                    StringBuilder komanda = new StringBuilder();
                    String input;

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                    while ((input = reader.readLine()) != null) {
                        komanda.append(input);
                    }

                    System.out.println("Primljena komanda : " + komanda);
                    
                    String sintaksa = "^USER ([a-zA-Z0-9_-]{3,15}); PASSWD ([a-z0-9]{6,18});";
                    Pattern pattern = Pattern.compile(sintaksa);
                    Matcher matcher = pattern.matcher(komanda);

                    String sintaksaADD = "^USER ([a-zA-Z0-9_-]{3,15}); PASSWD ([a-z0-9]{6,18}); ADD ([a-zA-Z0-9_-]{3,15}); NEWPASSWD ([a-z0-9]{6,18});$";
                    Pattern patternADD = Pattern.compile(sintaksaADD);
                    Matcher matcherADD = patternADD.matcher(komanda);

                    String sintaksaTEST = "^USER ([a-zA-Z0-9_-]{3,15}); PASSWD ([a-z0-9]{6,18}); TEST (.*);$";
                    Pattern patternTEST = Pattern.compile(sintaksaTEST);
                    Matcher matcherTEST = patternTEST.matcher(komanda);

                    String sintaksaADDAdresa = "^USER ([a-zA-Z0-9_-]{3,15}); PASSWD ([a-z0-9]{6,18}); ADD (.*);$";
                    Pattern patternADDAdresa = Pattern.compile(sintaksaADDAdresa);
                    Matcher matcherADDAdresa = patternADDAdresa.matcher(komanda);

                    String sintaksaGETAdresa = "^USER ([a-zA-Z0-9_-]{3,15}); PASSWD ([a-z0-9]{6,18}); GET (.*);$";
                    Pattern patternGETAdresa = Pattern.compile(sintaksaGETAdresa);
                    Matcher matcherGETAdresa = patternGETAdresa.matcher(komanda);

                    String sintaksaUSER = "^USER ([a-zA-Z0-9_-]{3,15}); GET (.*);$";
                    Pattern patternUSER = Pattern.compile(sintaksaUSER);
                    Matcher matcherUSER = patternUSER.matcher(komanda);

                    String sintaksaPAUSE = "^USER ([a-zA-Z0-9_-]{3,15}); PASSWD ([a-z0-9]{6,18}); PAUSE;*$";
                    Pattern patternPAUSE = Pattern.compile(sintaksaPAUSE);
                    Matcher matcherPAUSE = patternPAUSE.matcher(komanda);

                    String sintaksaSTART = "^USER ([a-zA-Z0-9_-]{3,15}); PASSWD ([a-z0-9]{6,18}); START;*$";
                    Pattern patternSTART = Pattern.compile(sintaksaSTART);
                    Matcher matcherSTART = patternSTART.matcher(komanda);

                    String sintaksaSTOP = "^USER ([a-zA-Z0-9_-]{3,15}); PASSWD ([a-z0-9]{6,18}); STOP;*$";
                    Pattern patternSTOP = Pattern.compile(sintaksaSTOP);
                    Matcher matcherSTOP = patternSTOP.matcher(komanda);

                    boolean statusADD = matcherADD.matches();
                    boolean statusTEST = matcherTEST.matches();
                    boolean statusPAUSE = matcherPAUSE.matches();
                    boolean statusSTART = matcherSTART.matches();
                    boolean statusSTOP = matcherSTOP.matches();
                    boolean statusADDAdresa = matcherADDAdresa.matches();
                    boolean statusGETAdresa = matcherGETAdresa.matches();
                    boolean statusUSER = matcherUSER.matches();
                    
                    if (statusADD) {
                        System.out.println("Administratorska naredba: ADD");
                        sqlNaredba = "INSERT INTO davmoslav_korisnici (id, korisnicko_ime, lozinka, tip_korisnika) VALUES (DEFAULT,'" + matcherADD.group(1) + "','" + matcherADD.group(4) + "',1)";
                        tip = 1;
                    } else if (statusPAUSE) {
                        System.out.println("Administratorska naredba: PAUSE");
                        if (PreuzimanjePodataka.pauza == false) {
                            //System.out.println("OK 10");
                            odgovor = "OK 10";
                            out.write(odgovor.getBytes());
                            out.flush();
                            klijent.shutdownOutput();
                            PreuzimanjePodataka.pauza = true;
                            System.out.println("Saljem mail");
                            long vrijemeIzvršavanja = System.currentTimeMillis() - pocetak;
                            long trajanjePrethodnogStanja = System.currentTimeMillis() - trajanjeStanja;
                            korisnik = matcherPAUSE.group(1);
                            sadrzajPoruke = "Informacija o komandama:"
                                    + "\nKomanda: PAUSE"
                                    + "\nVrijeme izvrsavanja: " + vrijemeIzvršavanja + " ms"
                                    + "\nTrajanje prethodnog stanja: " + trajanjePrethodnogStanja + " ms"
                                    + "\nBroj primljenih korisnickih komandi: " + brojPrimljenihKorisnickih
                                    + "\nBroj Neispravnih korisnickih komandi: " + brojNeispravnihKorisnickih
                                    + "\nBroj izvrsenih korisnickih komandi: " + brojIzvršenihKorisnickih;
                            saljiPoruku(adresaServera, saljePoruku, primaPoruku, predmetPoruke, sadrzajPoruke);
                            zapisiDnevnik(korisnik, "PAUSE", odgovor);
                        } else {
                            System.out.println("ERR 40");
                            korisnik = matcherPAUSE.group(1);
                            odgovor = "ERR 40";
                            out.write(odgovor.getBytes());
                            out.flush();
                            klijent.shutdownOutput();
                            zapisiDnevnik(korisnik, "PAUSE", "ERR 40");
                        }
                    } else if (statusSTART) {
                        System.out.println("Administratorska naredba: START");
                        if (PreuzimanjePodataka.pauza == true) {
                            //System.out.println("OK 10");
                            odgovor = "OK 10";
                            PreuzimanjePodataka.pauza = false;
                            out.write(odgovor.getBytes());
                            out.flush();
                            klijent.shutdownOutput();
                            System.out.println("Saljem mail");
                            long vrijemeIzvršavanja = System.currentTimeMillis() - pocetak;
                            long trajanjePrethodnogStanja = System.currentTimeMillis() - trajanjeStanja;
                            korisnik = matcherSTART.group(1);
                            sadrzajPoruke = "Informacija o komandama:"
                                    + "\nKomanda: START"
                                    + "\nVrijeme izvrsavanja: " + vrijemeIzvršavanja + " ms"
                                    + "\nTrajanje prethodnog stanja: " + trajanjePrethodnogStanja + " ms"
                                    + "\nBroj primljenih korisnickih komandi: " + brojPrimljenihKorisnickih
                                    + "\nBroj Neispravnih korisnickih komandi: " + brojNeispravnihKorisnickih
                                    + "\nBroj izvrsenih korisnickih komandi: " + brojIzvršenihKorisnickih;
                            saljiPoruku(adresaServera, saljePoruku, primaPoruku, predmetPoruke, sadrzajPoruke);
                            zapisiDnevnik(korisnik, "START", odgovor);
                        } else {
                            //System.out.println("ERR 41");
                            odgovor = "ERR 41";
                            out.write(odgovor.getBytes());
                            out.flush();
                            klijent.shutdownOutput();
                            korisnik = matcherSTART.group(1);
                            zapisiDnevnik(korisnik, "START", odgovor);
                        }
                    } else if (statusSTOP) {
                        System.out.println("Administratorska naredba: STOP");
                        if (PreuzimanjePodataka.pokretanjeDretve == true) {
                            //System.out.println("OK 10");
                            odgovor = "OK 10";
                            PreuzimanjePodataka.pokretanjeDretve = false;
                            out.write(odgovor.getBytes());
                            out.flush();
                            klijent.shutdownOutput();
                            System.out.println("Saljem mail");
                            long vrijemeIzvršavanja = System.currentTimeMillis() - pocetak;
                            long trajanjePrethodnogStanja = System.currentTimeMillis() - trajanjeStanja;
                            korisnik = matcherSTOP.group(1);
                            sadrzajPoruke = "Informacija o komandama:"
                                    + "\nKomanda: STOP"
                                    + "\nVrijeme izvrsavanja: " + vrijemeIzvršavanja + " ms"
                                    + "\nTrajanje prethodnog stanja: " + trajanjePrethodnogStanja + " ms"
                                    + "\nBroj primljenih korisnickih komandi: " + brojPrimljenihKorisnickih
                                    + "\nBroj Neispravnih korisnickih komandi: " + brojNeispravnihKorisnickih
                                    + "\nBroj izvrsenih korisnickih komandi: " + brojIzvršenihKorisnickih;
                            saljiPoruku(adresaServera, saljePoruku, primaPoruku, predmetPoruke, sadrzajPoruke);
                            zapisiDnevnik(korisnik, "STOP", odgovor);
                            radi = false;
                        } else {
                            //System.out.println("ERR 42");
                            odgovor = "ERR 42";
                            out.write(odgovor.getBytes());
                            out.flush();
                            klijent.shutdownOutput();
                            korisnik = matcherSTOP.group(1);
                            zapisiDnevnik(korisnik, "STOP", odgovor);
                        }
                    } else if (statusGETAdresa) {
                        //System.out.println("Administratorska naredba: GET adresa");
                        sqlNaredba = "SELECT vlaga, kisa, temperatura, vjetar, snijeg, latitude, longitude FROM davmoslav_meteo WHERE adresa='" + matcherGETAdresa.group(3) + "' ORDER BY vrijeme DESC";
                        tip = 3;
                        korisnik = matcherGETAdresa.group(1);
                    } else if (statusADDAdresa) {
                        //System.out.println("Administratorska naredba: ADD adresa");
                        String adresa = matcherADDAdresa.group(3);
                        GoogleMapsKlijent gmk = new GoogleMapsKlijent();
                        String latitude = gmk.getGeoLocation(adresa).getLatitude();
                        String longitude = gmk.getGeoLocation(adresa).getLongitude();
                        if (adresa != null && adresa.length() > 0) {
                            Location loc = gmk.getGeoLocation(adresa);
                            latitude = loc.getLatitude();
                            longitude = loc.getLongitude();
                        }
                        sqlNaredba = "INSERT INTO davmoslav_adrese (idadresa, adresa, latitude, longitude) VALUES (DEFAULT,'" + adresa + "','" + latitude + "','" + longitude + "')";
                        tip = 2;
                        korisnik = matcherADDAdresa.group(1);
                    } else if (statusTEST) {
                        //System.out.println("Administratorska naredba: TEST adresa");
                        sqlNaredba = "SELECT adresa FROM davmoslav_adrese WHERE adresa='" + matcherTEST.group(3) + "'";
                        tip = 4;
                        korisnik = matcherTEST.group(1);
                    } else if (statusUSER) {
                        //System.out.println("Korisnička naredba: GET adresa");
                        sqlNaredba = "SELECT vlaga, kisa, temperatura, vjetar, snijeg, latitude, longitude FROM davmoslav_meteo WHERE adresa='" + matcherUSER.group(3) + "' ORDER BY vrijeme DESC";
                        tip = 5;
                        korisnik = matcherUSER.group(1);
                        brojPrimljenihKorisnickih++;
                    } else {
                        System.out.println("Parametri nisu ispravni!");
                        brojNeispravnihKorisnickih++;
                    }
                    azurirajBazu(sqlNaredba, tip, korisnik);
                } catch (IOException ex) {
                    System.out.println("Spajanje na port nije uspjelo: " + ex.getMessage());
                }
            }

        } catch (IOException ex) {
            System.out.println("Spajanje na port nije uspjelo: " + ex.getMessage());
        }
    }

    private void zapisiDnevnik(String korisnik, String komanda, String odgovor) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(bpKonfig.getDriver_database());
        } catch (ClassNotFoundException ex) {
            System.out.println("Greška kod učitavanja drivera: " + ex.getMessage());
        }
        String connUrl = bpKonfig.getServer_database() + bpKonfig.getUser_database();
        try {
            conn = DriverManager.getConnection(connUrl, bpKonfig.getUser_username(), bpKonfig.getUser_password());
            stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO davmoslav_dnevnik (vrijeme, korisnik, komanda, odgovor) VALUES ('"
                    + (System.currentTimeMillis() / 1000L) + "','" + new String(korisnik).trim() + "','" + new String(komanda).trim() + "','" + new String(odgovor).trim() + "')");

            System.out.println("Upisani podaci!");
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("Greška prilikom spajanja na bazu: " + ex.getMessage());
        }
    }

    private String azurirajBazu(String sqlNaredba, int tip, String korisnik) throws IOException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(bpKonfig.getDriver_database());

        } catch (ClassNotFoundException ex) {
            System.out.println("Greška kod učitavanja drivera: " + ex.getMessage());
        }
        String connUrl = bpKonfig.getServer_database() + bpKonfig.getUser_database();
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            conn = DriverManager.getConnection(connUrl, bpKonfig.getUser_username(), bpKonfig.getUser_password());
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println("Spajanje na bazu greska: " + ex.getMessage());
        }
        try {

            if (tip == 1) {
                int rez = stmt.executeUpdate(sqlNaredba);
                if (rez == 1) {
                    //System.out.println("OK 10");
                    odgovor = "OK 10";
                    out.write(odgovor.getBytes());
                    out.flush();
                    klijent.shutdownOutput();
                    System.out.println("Saljem mail");
                    long vrijemeIzvršavanja = System.currentTimeMillis() - pocetak;
                    long trajanjePrethodnogStanja = System.currentTimeMillis() - trajanjeStanja;
                    sadrzajPoruke = "Informacija o komandama:"
                            + "\nKomanda: ADD"
                            + "\nVrijeme izvrsavanja: " + vrijemeIzvršavanja + " ms"
                            + "\nTrajanje prethodnog stanja: " + trajanjePrethodnogStanja + " ms"
                            + "\nBroj primljenih korisnickih komandi: " + brojPrimljenihKorisnickih
                            + "\nBroj Neispravnih korisnickih komandi: " + brojNeispravnihKorisnickih
                            + "\nBroj izvrsenih korisnickih komandi: " + brojIzvršenihKorisnickih;
                    saljiPoruku(adresaServera, saljePoruku, primaPoruku, predmetPoruke, sadrzajPoruke);
                    zapisiDnevnik(korisnik, "ADD", odgovor);
                } else {
                    //System.out.println("ERR 50");
                    odgovor = "ERR 50";
                    out.write(odgovor.getBytes());
                    out.flush();
                    klijent.shutdownOutput();
                    zapisiDnevnik(korisnik, "ADD", odgovor);
                }
            } else if (tip == 2) {
                int rez = stmt.executeUpdate(sqlNaredba);
                if (rez != 1) {
                    //System.out.println("ERR 50");
                    odgovor = "ERR 50";
                    zapisiDnevnik(korisnik, "ADD adresa", odgovor);
                } else {
                    //System.out.println("OK 10");
                    odgovor = "OK 10";
                    out.write(odgovor.getBytes());
                    out.flush();
                    klijent.shutdownOutput();
                    System.out.println("Saljem mail");
                    long vrijemeIzvršavanja = System.currentTimeMillis() - pocetak;
                    long trajanjePrethodnogStanja = System.currentTimeMillis() - trajanjeStanja;
                    sadrzajPoruke = "Informacija o komandama:"
                            + "\nKomanda: ADD adresa"
                            + "\nVrijeme izvrsavanja: " + vrijemeIzvršavanja + " ms"
                            + "\nTrajanje prethodnog stanja: " + trajanjePrethodnogStanja + " ms"
                            + "\nBroj primljenih korisnickih komandi: " + brojPrimljenihKorisnickih
                            + "\nBroj Neispravnih korisnickih komandi: " + brojNeispravnihKorisnickih
                            + "\nBroj izvrsenih korisnickih komandi: " + brojIzvršenihKorisnickih;
                    saljiPoruku(adresaServera, saljePoruku, primaPoruku, predmetPoruke, sadrzajPoruke);
                    zapisiDnevnik(korisnik, "ADD adresa", odgovor);
                }
            } else if (tip == 3) {
                rs = stmt.executeQuery(sqlNaredba);
                if (rs.next()) {
                    //System.out.println("OK 10");
                    odgovor = "OK 10: \n" + "VLAGA " + rs.getString("vlaga") + " KISA " + rs.getString("kisa") + " TEMPERATURA " + rs.getString("temperatura")
                            + " VJETAR " + rs.getString("vjetar") + " SNIJEG " + rs.getString("snijeg") + " GEOSIR " + rs.getString("LATITUDE") + " GEODUZ " + rs.getString("longitude");
                    out.write(odgovor.getBytes());
                    out.flush();
                    klijent.shutdownOutput();
                    System.out.println("VLAGA " + rs.getString("vlaga") + " KISA " + rs.getString("kisa") + " TEMPERATURA " + rs.getString("temperatura")
                            + " VJETAR " + rs.getString("vjetar") + " SNIJEG " + rs.getString("snijeg") + " GEOSIR " + rs.getString("LATITUDE") + " GEODUZ " + rs.getString("longitude"));
                    System.out.println("Saljem mail");
                    long vrijemeIzvršavanja = System.currentTimeMillis() - pocetak;
                    long trajanjePrethodnogStanja = System.currentTimeMillis() - trajanjeStanja;
                    sadrzajPoruke = "Informacija o komandama:"
                            + "\nKomanda: GET"
                            + "\nVrijeme izvrsavanja: " + vrijemeIzvršavanja + " ms"
                            + "\nTrajanje prethodnog stanja: " + trajanjePrethodnogStanja + " ms"
                            + "\nBroj primljenih korisnickih komandi: " + brojPrimljenihKorisnickih
                            + "\nBroj Neispravnih korisnickih komandi: " + brojNeispravnihKorisnickih
                            + "\nBroj izvrsenih korisnickih komandi: " + brojIzvršenihKorisnickih;
                    saljiPoruku(adresaServera, saljePoruku, primaPoruku, predmetPoruke, sadrzajPoruke);
                    zapisiDnevnik(korisnik, "GET", odgovor);
                } else {
                    //System.out.println("ERR 52");
                    odgovor = "ERR 52";
                    out.write(odgovor.getBytes());
                    out.flush();
                    klijent.shutdownOutput();
                    zapisiDnevnik(korisnik, "GET", odgovor);
                }
            } else if (tip == 4) {
                rs = stmt.executeQuery(sqlNaredba);
                if (rs.next()) {
                    //System.out.println("OK 10");
                    odgovor = "OK 10";
                    out.write(odgovor.getBytes());
                    out.flush();
                    klijent.shutdownOutput();
                    System.out.println("Saljem mail");
                    long vrijemeIzvršavanja = System.currentTimeMillis() - pocetak;
                    long trajanjePrethodnogStanja = System.currentTimeMillis() - trajanjeStanja;
                    sadrzajPoruke = "Informacija o komandama:"
                            + "\nKomanda: TEST"
                            + "\nVrijeme izvrsavanja: " + vrijemeIzvršavanja + " ms"
                            + "\nTrajanje prethodnog stanja: " + trajanjePrethodnogStanja + " ms"
                            + "\nBroj primljenih korisnickih komandi: " + brojPrimljenihKorisnickih
                            + "\nBroj Neispravnih korisnickih komandi: " + brojNeispravnihKorisnickih
                            + "\nBroj izvrsenih korisnickih komandi: " + brojIzvršenihKorisnickih;
                    saljiPoruku(adresaServera, saljePoruku, primaPoruku, predmetPoruke, sadrzajPoruke);
                    zapisiDnevnik(korisnik, "TEST", odgovor);
                } else {
                    //System.out.println("ERR 51");
                    odgovor = "ERR 51";
                    out.write(odgovor.getBytes());
                    out.flush();
                    klijent.shutdownOutput();
                    zapisiDnevnik(korisnik, "TEST", odgovor);
                }
            } else if (tip == 5) {
                rs = stmt.executeQuery(sqlNaredba);
                if (rs.next()) {
                    //System.out.println("OK 10");
                    odgovor = "OK 10\n" + "VLAGA " + rs.getString("vlaga") + " KISA " + rs.getString("kisa") + " TEMPERATURA " + rs.getString("temperatura")
                            + " VJETAR " + rs.getString("vjetar") + " SNIJEG " + rs.getString("snijeg") + " GEOSIR " + rs.getString("LATITUDE") + " GEODUZ " + rs.getString("longitude");
                    out.write(odgovor.getBytes());
                    out.flush();
                    klijent.shutdownOutput();
                    System.out.println("VLAGA " + rs.getString("vlaga") + " KISA " + rs.getString("kisa") + " TEMPERATURA " + rs.getString("temperatura")
                            + " VJETAR " + rs.getString("vjetar") + " SNIJEG " + rs.getString("snijeg") + " GEOSIR " + rs.getString("LATITUDE") + " GEODUZ " + rs.getString("longitude"));
                    brojPrimljenihKorisnickih++;
                    zapisiDnevnik(korisnik, "USER", "OK 10");
                } else {
                    //System.out.println("ERR 52");
                    odgovor = "ERR 52";
                    out.write(odgovor.getBytes());
                    out.flush();
                    klijent.shutdownOutput();
                    zapisiDnevnik(korisnik, "USER", odgovor);
                }
            }
        } catch (SQLException ex) {
            odgovor = "ERR 50"+ ex.getMessage();
            out.write(odgovor.getBytes());
            out.flush();
            klijent.shutdownOutput();
            //System.out.println("Greška u radu s bazom:" + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return odgovor;
    }

    public String saljiPoruku(String adresaServera, String saljePoruku, String primaPoruku, String predmetPoruke, String sadrzajPoruke) {
        try {
            // Create the JavaMail session
            java.util.Properties properties = System.getProperties();
            properties.put("mail.smtp.host", adresaServera);

            Session session = Session.getInstance(properties, null);

            // Construct the message
            MimeMessage message = new MimeMessage(session);

            // Set the from address
            Address fromAddress = new InternetAddress(saljePoruku);
            message.setFrom(fromAddress);

            // Parse and set the recipient addresses
            Address[] toAddresses = InternetAddress.parse(primaPoruku);
            message.setRecipients(Message.RecipientType.TO, toAddresses);

            // Set the subject and text
            message.setSubject(predmetPoruke);
            message.setContent(sadrzajPoruke, "text/plain");

            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
            return "Poruka nije isporučena, dogodila se pogreška kod slanja poruke";

        } catch (SendFailedException e) {
            e.printStackTrace();
            return "Poruka nije isporučena, dogodila se pogreška kod slanja poruke";

        } catch (MessagingException e) {
            e.printStackTrace();
            return "Poruka nije isporučena, dogodila se pogreška kod slanja poruke";
        }
        return "Email poruka isporučena";
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }
}
