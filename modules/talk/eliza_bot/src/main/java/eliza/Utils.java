package eliza;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Utils
{
    private static final String DIGITS = "0123456789";
    private static final String WEATHER = "http://api.wunderground.com/api/de13f2e6a2c425f6/conditions/q/MA/Newton.json";
    
    public static void main(String[] args) throws MessagingException
    {
        System.out.println("sending mail");
        sendEmail("noone", "fake IRC log", "mail.tripadvisor.com", "mail.smtp.host", "vthaonguyen@tripadvisor.com");
    }

    public static String sendEmail(String sender, String content, String mailServer, String host, String username) throws MessagingException
    {
        Properties props = System.getProperties();
        props.put(host, mailServer);
        Session session = Session.getInstance(props, null);
        
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("TheAwesomeBot@tripadvisor.com"));
        Address toAddress = new InternetAddress(username);
        msg.setRecipient(Message.RecipientType.TO, toAddress);
        msg.setSubject("TEst");
        msg.setContent(content, "text/html");
        
        Transport.send(msg);
        System.out.println("msg sent");
        
        return "SENT";
    }
    
    public static String getNextShuttleTime(String name)
    {
        throw new UnsupportedOperationException();
    }

    public static int getDis(String str, String text)
    {
        if (str.equals(text))
            return 0;
        else if (str.length() == 0)
            return text.length();
        else if (text.length() == 0)
            return str.length();

        int strArrDis[] = new int[text.length() + 1];
        int textArrDis[] = new int[text.length() + 1];

        for (int i = 0; i < strArrDis.length; i++)
            strArrDis[i] = i;

        for (int i = 0; i < str.length(); i++)
        {
            textArrDis[0] = i + 1;
            for (int j = 0; j < text.length(); j++)
            {
                textArrDis[j + 1] = min(textArrDis[j] + 1, 
                                        strArrDis[j + 1] + 1,
                                        strArrDis[j] + (str.charAt(i) == text.charAt(j) ? 0 : 1));
            }
            System.arraycopy(textArrDis, 0, strArrDis, 0, strArrDis.length);
        }

        return textArrDis[text.length()];
    }

    public static int min(int n1, int n2, int n3)
    {
        return n1 <= n2
                    ? (n1 < n3 ? n1 : n3)
                    : (n2 < n3 ? n2 : n3);
    }

    public static String getCurrentTime()
    {
        return new DateTime().toString("HH:mm:ss");
    }
    
    public static String getCurrentDate()
    {
        return new DateTime().toString("dd/MM/YYYY");
    }
    
    public static String getCurrentWeather()
    {
        try
        {
            // TODO: cached the file as only 10 requests can be made a minute 
            //       (and 500 a day)
            URL json = new URL(WEATHER);

            JSONObject jsonObj = (JSONObject) JSONValue.parse(new InputStreamReader(json.openStream()));
            JSONObject currentObs = (JSONObject) jsonObj.get("current_observation");
            
            String temp = currentObs.get("temperature_string").toString();
            String feelsLike = currentObs.get("feelslike_string").toString();
            String w = currentObs.get("weather").toString();
            String loc = ((JSONObject) currentObs.get("display_location")).get("full").toString();

            return temp + " [feels like " + feelsLike + "], " + w + " in " + loc;
        }
        catch (MalformedURLException ex)
        {
            return "<UNKNOWN>";
        }
        catch (IOException e)
        {
            return "<UNKNOWN>";
        }
    }

    public static int count(String str, char c)
    {
        int count = 0;
        for (int i = 0; i < str.length(); i++)
            if (str.charAt(i) == c)
                count++;
        return count;
    }
    
    public static int countFirstDigits(String str)
    {
        int count = 0;
        for (int i = 0; i < str.length(); i++)
        {
            if (DIGITS.indexOf(str.charAt(i)) == -1)
                return count;
            count++;
        }
        return count;
    }
    
    /**
     * Find the number of characters that are equal to * or #
     * or that literally match the pattern
     */
    public static int countMatch(String str, String pat)
    {
        int strIndex = 0; 
        int patIndex = 0;
        int count = 0;
        while (strIndex < str.length() && patIndex < pat.length())
        {
            char p = pat.charAt(patIndex);
            if (p == '*' || p == '#')
                return count;
            if (str.charAt(strIndex) != p)
                return -1;
            strIndex++;
            patIndex++;
            count++;
        }
        return count;
    }

    public static int countMatchInPattern(String str, String pat)
    {
        int count = 0;
        for (int i = 0; i < str.length(); i++)
        {
            if (countMatch(str.substring(i), pat) >= 0)
                return count;
            count++;
        }
        return -1;
    }

    /**
     * - Match the string against the pattern
     * - put into `matches` array all substrings that match * and/or #
     */
    private static boolean doMatch(String str, String pat, String matches[])
    {
        int strIndex = 0;
        int iMatch = 0;
        int patIndex = 0;
        while (patIndex < pat.length() && iMatch < matches.length)
        {
            char p = pat.charAt(patIndex);
            if (p == '*')
            {
                int n;
                if (patIndex + 1 == pat.length())
                {
                    //  * is the last thing in pat
                    //  n is remaining string length
                    n = str.length() - strIndex;
                }
                else
                {
                    //  * is not last in pat
                    //  find using remaining pat
                    n = countMatchInPattern(str.substring(strIndex), pat.substring(patIndex + 1));
                }
                if (n < 0)
                    return false;
                matches[iMatch++] = str.substring(strIndex, strIndex + n);
                strIndex += n;
                patIndex++;
            }
            else if (p == '#')
            {
                int n = countFirstDigits(str.substring(strIndex));
                matches[iMatch++] = str.substring(strIndex, strIndex + n);
                strIndex += n;
                patIndex++;
            }
            else
            {
                int n = countMatch(str.substring(strIndex), pat.substring(patIndex));
                if (n <= 0)
                    return false;
                strIndex += n;
                patIndex += n;
            }
        }
        if (strIndex >= str.length() && patIndex >= pat.length())
            return true;
        return false;
    }

    public static boolean extractIfMatched(String str, String pat, String matches[])
    {
        return doMatch(str, pat, matches);
    }

    public static String translate(String str, String src, String target)
    {
        if (src.length() != target.length())
        {
            throw new IllegalArgumentException("SRC and TARGET have to have the same length");
        }

        for (int i = 0; i < src.length(); i++)
        {
            str = str.replace(src.charAt(i), target.charAt(i));
        }
        return str;
    }

    public static String compress(String s)
    {
        StringBuilder retVal = new StringBuilder();
        if (s.length() == 0)
            return s;
        char c = s.charAt(0);
        for (int i = 1; i < s.length(); i++)
        {
            if (c == ' '
                && ((s.charAt(i) == ' ')
                    || (s.charAt(i) == ',')
                    || (s.charAt(i) == '.')))
            {
                // no -op
            }
            else if (c != ' ' && s.charAt(i) == '?')
            {
                retVal.append(c).append(' ');
            }
            else
            {
                retVal.append(c);
            }
            c = s.charAt(i);
        }
        retVal.append(c);
        return retVal.toString();
    }

    public static String trimLeading(String str)
    {
        for (int i = 0; i < str.length(); i++)
        {
            if (!Character.isWhitespace(str.charAt(i)))
                return str.substring(i);
        }
        return "";
    }

    public static String pad(String s)
    {
        if (s.length() == 0)
            return " ";
        char first = s.charAt(0);
        char last = s.charAt(s.length() - 1);
        if (first == ' ' && last == ' ')
            return s;
        if (first == ' ' && last != ' ')
            return s + " ";
        if (first != ' ' && last == ' ')
            return " " + s;
        if (first != ' ' && last != ' ')
            return " " + s + " ";

        // TODO: unreachable
        return s;
    }
}