package send_bot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Main
{
    private static String combine(String args[])
    {
        StringBuilder bd = new StringBuilder();
        for (int n = 2; n < args.length; ++n)
            bd.append(args[n]).append(' ');
        
        return bd.toString();
    }
    public static void main(String[] args) throws MessagingException
    {
        
        if (args.length < 3)
        {
            System.out.println("Usage:  <speaker> <path to email adresses> <content>");
            for (String arg: args)
                System.out.println("arg: " + arg);
        }
        else
        {
            String[] receivers = getReceivers(args[1]);
            if (receivers == null)
                return ;
            sendEmail("New msg from: " + args[0],
                      combine(args),
                      "mail.tripadvisor.com",
                      "mail.smtp.host",
                      receivers);
        }
        //System.out.println("sending mail");
        //sendEmail("noone", "fake IRC log", "mail.tripadvisor.com", "mail.smtp.host", new String[]{"vthaonguyen@tripadvisor.com"});
    }

    public static String sendEmail(String subject, String content, String mailServer, String host, String users[]) throws MessagingException
    {
        Properties props = System.getProperties();
        props.put(host, mailServer);
        Session session = Session.getInstance(props, null);
        
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("TheAwesomeBot@tripadvisor.com"));
        
        Address toAddr[] = new Address[users.length];
        for (int n = 0; n < toAddr.length; ++n)
        {
            toAddr[n] = new InternetAddress(users[n]);
        }
        //Address toAddress = new InternetAddress(users);
        //msg.setRecipient(Message.RecipientType.TO, toAddress);
        msg.setRecipients(Message.RecipientType.TO, toAddr);
        msg.setSubject(subject);
        msg.setContent(content, "text/html");
        
        Transport.send(msg);
        
        return "SENT!";
    }
    
    
    private static String[] getReceivers(String path)
    {
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(path));
            Set<String> st = new HashSet<String>();
            String line;
            while((line = br.readLine()) != null)
            {
                st.add(line);
            }
            System.out.println("receiver: " + st);
            return st.toArray(new String[st.size()]);
        }
        catch (FileNotFoundException ex)
        {
            return null;
        }
        catch (IOException ex)
        {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        finally
        {
            try
            {
                br.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(send_bot.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
}