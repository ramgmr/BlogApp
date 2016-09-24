package blog;

//[START simple_includes]
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//[END simple_includes]

//[START multipart_includes]
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import javax.activation.DataHandler;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
//[END multipart_includes]

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static com.googlecode.objectify.ObjectifyService.ofy;

@SuppressWarnings("serial")
public class CronServlet extends HttpServlet
{
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);
	    try
	    {
	    	Message msg = new MimeMessage(session);
	    	msg.setFrom(new InternetAddress("noreply@blogapp-144400.appspot.com", "Harrison Ford"));
	    	msg.setRecipient(Message.RecipientType.TO, new InternetAddress("ramgmr@gmail.com", "Ryan Maphet"));
	    	msg.setSubject("Daily update");
	    	msg.setText("Test cron email");
	    	Transport.send(msg);
	    }
	    catch (AddressException e)
	    {
	        // ...
	    } 
	    catch (MessagingException e) 
	    {
	        // ...
	    } 
	    catch (UnsupportedEncodingException e) 
	    {
	        // ...
	    }
	    
	}
	
	
}

