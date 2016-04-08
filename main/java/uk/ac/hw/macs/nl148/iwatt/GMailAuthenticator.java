package uk.ac.hw.macs.nl148.iwatt;

/**
 * Created by mrnel on 07/04/2016.
 */
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


class GMailAuthenticator extends Authenticator {
    String user;
    String pw;
    public GMailAuthenticator (String username, String password)
    {
        super();
        this.user = username;
        this.pw = password;
    }
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(user, pw);
    }
}