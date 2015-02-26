/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.davmoslav.klijenti.GoogleMapsKlijent;
import org.foi.nwtis.davmoslav.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.davmoslav.slusaci.SlusacAplikacije;

/**
 *
 * @author Davor
 */
public class Kontroler extends HttpServlet {

    BP_Konfiguracija bpKonfig;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String zahtjev = request.getServletPath();
        String odrediste = null;
        HttpSession sesija = request.getSession();
        switch (zahtjev) {
            case "/Kontroler":
                odrediste = "/index.jsp";
                break;
            case "/PrijavaKorisnika":
                odrediste = "/login.jsp";
                break;
            case "/OdjavaKorisnika":
                sesija.invalidate();
                odrediste = "/Kontroler";
                break;
            case "/PregledMeteoPodataka":
                odrediste = "/zasticeno/pregledMeteoPodataka.jsp";
                break;
            case "/PregledZahtjevaZaServer":

                odrediste = "/zasticeno/pregledZahtjevaZaServer.jsp";
                break;
            case "/PregledKorisnickihZahtjeva":
                odrediste = "/zasticeno/pregledKorisnickihZahtjeva.jsp";
                break;
            default:
                ServletException up = new ServletException("Nepoznat zahtjev");
                throw up;
        }

        response.sendRedirect(getServletContext().getContextPath() + odrediste);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        this.bpKonfig = SlusacAplikacije.konfiguracija_baza;
        if (request.getParameter("unosNoveAdrese") != null) {
            if (request.getParameter("novaAdresa").isEmpty()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/PregledMeteoPodataka");
                dispatcher.forward(request, response);
                System.out.println("Upišite adresu!");
            } else {
                String novaAdresa = request.getParameter("novaAdresa");

                GoogleMapsKlijent gmk = new GoogleMapsKlijent();
                String latitude = gmk.getGeoLocation(novaAdresa).getLatitude();
                String longitude = gmk.getGeoLocation(novaAdresa).getLongitude();

                Connection conn = null;
                Statement stmt = null;
                try {
                    Class.forName(bpKonfig.getDriver_database());
                } catch (ClassNotFoundException ex) {
                    System.out.println("Greška kod učitavanja drivera: " + ex.getMessage());
                }
                String connUrl = bpKonfig.getServer_database() + bpKonfig.getUser_database();
                try {
                    //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                    conn = DriverManager.getConnection(connUrl, bpKonfig.getUser_username(), bpKonfig.getUser_password());
                    stmt = conn.createStatement();

                    stmt.executeUpdate("INSERT INTO davmoslav_adrese (adresa, latitude, longitude) VALUES ('"
                            + new String(novaAdresa).trim() + "','" + new String(latitude).trim() + "','" + new String(longitude).trim() + "')");

                    stmt.close();
                    conn.close();

                } catch (SQLException ex) {
                    System.out.println("Greška prilikom spajanja na bazu: " + ex.getMessage());
                }

                response.sendRedirect(request.getSession().getServletContext().getContextPath() + "/PregledMeteoPodataka");

            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
