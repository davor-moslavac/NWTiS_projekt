/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.davmoslav.web.zrna;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.AuthenticationFailedException;
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
import org.foi.nwtis.davmoslav.web.kontrole.Poruka;
import org.foi.nwtis.davmoslav.web.kontrole.PrivitakPoruke;

/**
 * 
 * @author Davor
 */
@ManagedBean
@SessionScoped
public class PregledSvihPoruka {

    EmailPovezivanje ep = null;
    private String email_posluzitelj = "";
    private String korisnicko_ime = "";
    private String lozinka = "";
    private List<Poruka> poruke = new ArrayList<Poruka>();
    private Poruka odabranaPoruka;
    private String porukaID;
    private List<String> mapeKorisnika = new ArrayList<>();
    private String odabranaMapa = "inbox";


    public PregledSvihPoruka() {
    }

    public String pregledPoruke() {
        return "OK";
    }

    public String getPorukaID() {

        return porukaID;
    }

    public void setPorukaID(String porukaID) {
        for (Poruka p : poruke) {
            if (p.getId().equals(porukaID)) {
                this.odabranaPoruka = p;
                break;
            }
        }
        this.porukaID = porukaID;
    }

    /**
     * Dohvaca mape korisnika
     * @return
     * @throws MessagingException 
     */
    public List<String> getMapeKorisnika() throws MessagingException {
        ep = (EmailPovezivanje) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("emailPovezivanje");
        korisnicko_ime = ep.getKorisnicko_ime();
        lozinka = ep.getKorisnicka_lozinka();
        email_posluzitelj = ep.getEmail_posluzitelj();
        Session session = null;
        Store store = null;
        Folder folder = null;
        try {
            session = Session.getDefaultInstance(System.getProperties(), null);
            store = session.getStore("imap");
            store.connect(email_posluzitelj, korisnicko_ime, lozinka);
            folder = store.getDefaultFolder();
            mapeKorisnika.clear();
            for (Folder f : folder.list()) {
                mapeKorisnika.add(f.getName());
            }

        } catch (MessagingException ex) {
            Logger.getLogger(PregledSvihPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mapeKorisnika;
    }

    public void setMapeKorisnika(List<String> mapeKorisnika) {
        this.mapeKorisnika = mapeKorisnika;
    }

    public String getOdabranaMapa() {
        if (odabranaMapa == null) {
            odabranaMapa = "inbox";
        }
        return odabranaMapa;
    }

    public void setOdabranaMapa(String odabranaMapa) {
        this.odabranaMapa = odabranaMapa;
    }

    public String odaberiMapu() {
        return "promjenaMape";
    }

    public List<Poruka> getPoruke() {
        ep = (EmailPovezivanje) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("emailPovezivanje");
        korisnicko_ime = ep.getKorisnicko_ime();
        lozinka = ep.getKorisnicka_lozinka();
        email_posluzitelj = ep.getEmail_posluzitelj();
        dohvatiPoruke();
        return poruke;
    }

    public void setPoruke(List<Poruka> poruke) {
        this.poruke = poruke;
    }

    public Poruka getOdabranaPoruka() {
        return odabranaPoruka;
    }

    public void setOdabranaPoruka(Poruka odabranaPoruka) {
        this.odabranaPoruka = odabranaPoruka;
    }

    private void printData(String data) {
        System.out.println(data);
    }

    /**
     * Metoda za dohvacanje poruka
     */
    private void dohvatiPoruke() {
        poruke.clear();
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

        try {
            printData("--------------processing mails started-----------------");
            session = Session.getDefaultInstance(System.getProperties(), null);

            printData("getting the session for accessing email.");
            store = session.getStore("imap");

            store.connect(email_posluzitelj, korisnicko_ime, lozinka);
            printData("Connection established with IMAP server.");
            // Get a handle on the default folder
            folder = store.getDefaultFolder();

            printData("Getting the Inbox folder.");

            // Retrieve the "Inbox"
            folder = folder.getFolder(odabranaMapa);

            //Reading the Email Index in Read / Write Mode
            folder.open(Folder.READ_ONLY);

            // Retrieve the messages
            messages = folder.getMessages();

            // Loop over all of the messages
            for (int messageNumber = 0; messageNumber < messages.length; messageNumber++) {

                // Retrieve the next message to be read
                message = messages[messageNumber];

                // Retrieve the message content
                messagecontentObject = message.getContent();

                List<PrivitakPoruke> pp = new ArrayList<PrivitakPoruke>();

                String sadrzaj = "";

                // Determine email type
                if (messagecontentObject instanceof Multipart) {
                    printData("Found Email with Attachment");
                    sender = ((InternetAddress) message.getFrom()[0]).getPersonal();

                    // If the "personal" information has no entry, check the address for the sender information
                    printData("If the personal information has no entry, check the address for the sender information.");

                    if (sender == null) {
                        sender = ((InternetAddress) message.getFrom()[0]).getAddress();
                        printData("sender in NULL. Printing Address:" + sender);
                    }
                    printData("Sender -." + sender);

                    // Get the subject information
                    subject = message.getSubject();

                    printData("subject=" + subject);

                    // Retrieve the Multipart object from the message
                    multipart = (Multipart) message.getContent();

                    printData("Retrieve the Multipart object from the message");

                    // Loop over the parts of the email
                    for (int i = 0; i < multipart.getCount(); i++) {
                        // Retrieve the next part
                        part = multipart.getBodyPart(i);

                        // Get the content type
                        contentType = part.getContentType();

                        // Display the content type
                        printData("Content: " + contentType);
                        String fileName = "";
                        if (contentType.startsWith("TEXT/PLAIN")) {
                            printData("---------reading content type text/plain  mail -------------");
                            sadrzaj = part.getContent().toString();
                            //System.out.println("Sadržaj je : " + sadrzaj);
                        } else {
                            // Retrieve the file name
                            fileName = part.getFileName();
                            printData("retrive the fileName=" + fileName);
                        }

                        PrivitakPoruke privitak = new PrivitakPoruke(i, contentType, part.getSize(), fileName);
                        pp.add(privitak);
                    }
                } else {
                    printData("Found Mail Without Attachment");
                    sender = ((InternetAddress) message.getFrom()[0]).getPersonal();

                    // If the "personal" information has no entry, check the address for the sender information
                    printData("If the personal information has no entry, check the address for the sender information.");

                    if (sender == null) {
                        sender = ((InternetAddress) message.getFrom()[0]).getAddress();
                        printData("sender in NULL. Printing Address:" + sender);
                    }

                    // Get the subject information
                    sadrzaj = (String) message.getContent();
                    subject = message.getSubject();
                    printData("subject=" + subject);
                }

                String messID = "";
                String[] zaglavlje = message.getHeader("Message-ID");
                if (zaglavlje != null && zaglavlje.length > 0) {
                    messID = zaglavlje[0];
                }
                Poruka p = new Poruka(messID, message.getSentDate(), sender, subject, contentType, sadrzaj, message.getSize(), pp.size(), message.getFlags(), pp, true, true);
                poruke.add(p);
            }

            // Close the folder
            folder.close(true);

            // Close the message store
            store.close();
        } catch (AuthenticationFailedException e) {
            printData("Not able to process the mail reading.");
            e.printStackTrace();
        } catch (FolderClosedException e) {
            printData("Not able to process the mail reading.");
            e.printStackTrace();
        } catch (FolderNotFoundException e) {
            printData("Not able to process the mail reading.");
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            printData("Not able to process the mail reading.");
            e.printStackTrace();
        } catch (ReadOnlyFolderException e) {
            printData("Not able to process the mail reading.");
            e.printStackTrace();
        } catch (StoreClosedException e) {
            printData("Not able to process the mail reading.");
            e.printStackTrace();
        } catch (Exception e) {
            printData("Not able to process the mail reading.");
            e.printStackTrace();
        }
    }
}
