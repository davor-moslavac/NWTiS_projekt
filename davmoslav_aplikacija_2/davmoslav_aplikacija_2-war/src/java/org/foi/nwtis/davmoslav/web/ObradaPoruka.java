/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav.web;

import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.mail.AuthenticationFailedException;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.ReadOnlyFolderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.StoreClosedException;
import javax.mail.internet.InternetAddress;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import org.foi.nwtis.davmoslav.konfiguracije.Konfiguracija;
import org.foi.nwtis.davmoslav.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.davmoslav.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.davmoslav.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.davmoslav.web.kontrole.JMSPoruka;
import org.foi.nwtis.davmoslav.web.kontrole.JMSSender;

/**
 * Klasa se koristi za periodično provjeravanje e-mail poruka i razvrstavanje
 * tih poruka u određene mape
 *
 * @author Tomashevski
 */
public class ObradaPoruka extends Thread {

    public BP_Konfiguracija bpKonfig;
    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss.zzz");
    private String emailPosluzitelj;
    private String emailKorisnik;
    private String emailLozinka;
    private String emailPredmet;
    private String mapaIspravnePoruke;
    private String mapaNeispravnePoruke;
    private String mapaOstalePoruke;
    private long interval;
    private Date vrijemeZaMsPoc;
    private Date vrijemeZaMsKraj;
    private String vrijemePocetka;
    private String vrijemeKraja;
    private int brojProcitanihPoruka;
    private int brojIspravnihPoruka;
    private int brojOstalihPoruka;
    private JMSSender jmsSender;
    ServletContext kontekst;
    private String nazivFoldera;
    private boolean radi;

    /**
     * Koristi se za ispis podataka na konzoli
     *
     * @param data String koji želimo ispisati
     */
    private void printData(String data) {
        System.out.println(data);
    }

    public ObradaPoruka(ServletContext kontekst) {
        this.kontekst = kontekst;
        nazivFoldera = kontekst.getInitParameter("konfiguracijskaDatoteka");
        String path = kontekst.getRealPath("WEB-INF");

        Konfiguracija konf = null;
        try {
            konf = KonfiguracijaApstraktna.preuzmiKonfiguraciju(path + File.separator + nazivFoldera);
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
        interval = Integer.parseInt(konf.dajPostavku("interval"));
        emailPosluzitelj = konf.dajPostavku("email_posluzitelj");
        emailKorisnik = konf.dajPostavku("korisnicko_ime");
        emailLozinka = konf.dajPostavku("korisnicka_lozinka");
        emailPredmet = konf.dajPostavku("predmet");
        mapaIspravnePoruke = konf.dajPostavku("ispravnoFolder");
        mapaNeispravnePoruke = konf.dajPostavku("neispravnoFolder");
        mapaOstalePoruke = konf.dajPostavku("ostaloFolder");
    }

    /**
     * Metoda kreira folder koji je određen konfiguracijom
     *
     * @param store prosljeđuje se store na kojem želimo kreirati folder
     * @param mapa prosljeđuje se ime foldera kojeg želimo kreirati
     * @return vraća true ako je uspješno izvršeno kreiranje folder, odnosno
     * false ako nije
     */
    public boolean kreirajMapuNaStoreu(Store store, String mapa) {
        boolean kreiran = false;
        try {
            Folder parent = store.getDefaultFolder();
            Folder novi = parent.getFolder(mapa);
            if (!novi.exists()) {
                kreiran = novi.create(Folder.HOLDS_MESSAGES);
            }
        } catch (MessagingException ex) {
            printData("Greska pri kreiranju mape!");
        }
        return kreiran;
    }

    /**
     * Metoda se koristi za prebacivanje e-mail poruke iz jednog foldera u drugi
     *
     * @param store store koji se koristi za rad
     * @param folder mapa u kojoj je trenutno poruka
     * @param msg poruka koju želimo prebaciti
     * @param mapa mapa u koju želimo prebaciti poruku
     */
    public void moveMail(Store store, Folder folder, Message msg, String mapa) {
        //povecaj brojeve

        if (mapa.equals(mapaIspravnePoruke)) {
            brojIspravnihPoruka = brojIspravnihPoruka + 1;
        } else if (mapa.equals(mapaOstalePoruke)) {
            brojOstalihPoruka = brojOstalihPoruka + 1;
        }

        Folder destinationFolder;
        kreirajMapuNaStoreu(store, mapa);
        try {
            Message[] msgs = {msg};
            if (msgs != null) {
                destinationFolder = store.getFolder(mapa);
                destinationFolder.create(Folder.HOLDS_MESSAGES);

                if (!destinationFolder.exists()) {
                    System.out.println("Ne postoji folder kreiram ga");
                    destinationFolder.create(Folder.HOLDS_MESSAGES);
                    System.out.println("kreirano");
                }
                destinationFolder.open(Folder.READ_WRITE);

                folder.copyMessages(msgs, destinationFolder);

                destinationFolder.close(true);
            }

        } catch (MessagingException ex) {
            Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metoda se koristi za procesiranje e-mailova, odnosno provjeru predmeta
     * poruke, tipa poruke i slično, na temelju kojih prebacuje poruku u
     * određenu mapu, uz to šalje JMS poruku o email statistici na red JMS
     * poruka
     */
    public void processMail() {
        Session session = null;
        Store store = null;
        Folder folder = null;
        Message message = null;
        Message[] messages = null;
        Object messagecontentObject = null;
        String sender = null;
        String subject = null;
        Multipart multipart = null;
        Part part = null;
        String contentType = null;

        brojProcitanihPoruka = 0;
        brojIspravnihPoruka = 0;
        brojOstalihPoruka = 0;

        try {
            //System.out.println("POCETAK OBRADE");
            //vrijeme pocetka
            vrijemePocetka = format.format(new Date());

            //System.out.println("Vrijeme pocetka: " + vrijemePocetka);
            printData("--------------processing mails started-----------------");
            session = Session.getDefaultInstance(System.getProperties(), null);

            //printData("getting the session for accessing email.");
            store = session.getStore("imap");

            store.connect(emailPosluzitelj, emailKorisnik, emailLozinka);
            //printData("Connection established with IMAP server.");

            // Get a handle on the default folder
            folder = store.getDefaultFolder();

            //printData("Getting the Inbox folder.");
            // Retrieve the "Inbox"
            folder = folder.getFolder("inbox");

            //Reading the Email Index in Read / Write Mode
            folder.open(Folder.READ_WRITE);

            // Retrieve the messages
            messages = folder.getMessages();

            // Loop over all of the messages
            for (int messageNumber = 0; messageNumber < messages.length; messageNumber++) {
                brojProcitanihPoruka = brojProcitanihPoruka + 1;

                // Retrieve the next message to be read
                message = messages[messageNumber];
                boolean tip_poruke = true;
                boolean ideUIspravne = false;
                boolean ideUOstale = false;
                // Retrieve the message content
                messagecontentObject = message.getContent();

                // Determine email type
                if (messagecontentObject instanceof Multipart) {
                    //printData("Found Email with Attachment");
                    sender = ((InternetAddress) message.getFrom()[0]).getPersonal();

                    // If the "personal" information has no entry, check the address for the sender information
                    //printData("If the personal information has no entry, check the address for the sender information.");
                    if (sender == null) {
                        sender = ((InternetAddress) message.getFrom()[0]).getAddress();
                        //printData("sender in NULL. Printing Address:" + sender);
                    }
                    //printData("Sender -." + sender);

                    // Get the subject information
                    subject = message.getSubject();

                    //printData("subject=" + subject);
                    //ako subject zapocinje sa kljucnom rijeci obradi ga
                    if (subject.equals(emailPredmet)) {
                        //ispitaj sadrzaj poruke
                        multipart = (Multipart) message.getContent();
                        //printData("Retrieve the Multipart object from the message");
                        // Loop over the parts of the email
                        for (int i = 0; i < multipart.getCount(); i++) {
                            // Retrieve the next part
                            part = multipart.getBodyPart(i);
                            // Get the content type
                            contentType = part.getContentType();
                            if (!part.isMimeType(contentType)) {
                                //ova poruka nije MIME
                                tip_poruke = false;
                            }
                            // Display the content type
                            //printData("Content: " + contentType);
                        }//for petlja ne dirati

                        if (tip_poruke) {
                            //System.out.println("MIME JE");
                            //je mime
                            // Loop over the parts of the email
                            for (int i = 0; i < multipart.getCount(); i++) {
                                part = multipart.getBodyPart(i);
                                contentType = part.getContentType();
                                if (contentType.startsWith("TEXT/PLAIN")) {
                                    //System.out.println("IMA TEXT PLAIN");
                                    ideUIspravne = true;
                                }
                            }
                            if (!ideUIspravne) {
                                ideUOstale = true;
                            }
                        } else {
                            ideUOstale = true;
                        }
                    } else {
                        ideUOstale = true;
                    }

                } else {
                    //printData("Found Mail Without Attachment");

                    subject = message.getSubject();
                    if (subject.equals(emailPredmet)) {
                        if (message.getContentType().startsWith("TEXT/PLAIN")) {
                            ideUIspravne = true;
                        } else {
                            ideUOstale = true;
                        }
                    } else {
                        ideUOstale = true;
                    }

                    // If the "personal" information has no entry, check the address for the sender information
                    //printData("If the personal information has no entry, check the address for the sender information.");
                    if (sender == null) {
                        sender = ((InternetAddress) message.getFrom()[0]).getAddress();
                        printData("sender in NULL. Printing Address:" + sender);
                    }

                }
                if (ideUOstale) {
                    System.out.println("Saljem mail u ostale poruke!");
                    moveMail(store, folder, message, mapaOstalePoruke);
                    subject = message.getSubject();
                    message.setFlag(Flags.Flag.DELETED, true);
                } else if (ideUIspravne) {
                    System.out.println("Saljem mail u ispravne poruke!");
                    moveMail(store, folder, message, mapaIspravnePoruke);
                    subject = message.getSubject();
                    message.setFlag(Flags.Flag.DELETED, true);
                }

            }
            // Close the folder
            vrijemeKraja = format.format(new Date());

            //System.out.println("Vrijeme kraja: " + vrijemeKraja);
            folder.close(true);
            // Close the message store
            store.close();

            JMSPoruka poruka = new JMSPoruka(vrijemePocetka, vrijemeKraja, brojProcitanihPoruka, brojIspravnihPoruka, brojOstalihPoruka);
            jmsSender = new JMSSender();
            jmsSender.sendJMSMessageToNWTiS_davmoslav_1(poruka);
            System.out.println("******POSLANA PORUKA****");

        } catch (AuthenticationFailedException e) {
            printData("Not able to process the mail reading. Autentikacijska greska!");
        } catch (FolderClosedException e) {
            printData("Not able to process the mail reading. Zatvoren folder");
        } catch (FolderNotFoundException e) {
            printData("Not able to process the mail reading. Nije pronaden folder");
        } catch (NoSuchProviderException e) {
            printData("Not able to process the mail reading. Nema providera");
        } catch (ReadOnlyFolderException e) {
            printData("Not able to process the mail reading. Folder je read only");
        } catch (StoreClosedException e) {
            printData("Not able to process the mail reading. Store je zatvoren");
        } catch (MessagingException ex) {
            Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(ObradaPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metoda za gašenje aplikacije i pozivanje ispisa statistike
     */
    public void ugasi() {
        radi = false;
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        System.out.println("USAO JE U DRETVU");
        while (true) {
            vrijemeZaMsPoc = new Date();
            processMail();
            vrijemeZaMsKraj = new Date();
            try {
                //TODO izracunati stvarno vrijeme spavanja kao u zadaci 1
                long spavanje = vrijemeZaMsKraj.getTime() - vrijemeZaMsPoc.getTime();
                sleep((interval * 1000) - spavanje);
            } catch (InterruptedException ex) {
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    public BP_Konfiguracija getBpKonfig() {
        return bpKonfig;
    }

    public void setBpKonfig(BP_Konfiguracija bpKonfig) {
        this.bpKonfig = bpKonfig;
    }
}
