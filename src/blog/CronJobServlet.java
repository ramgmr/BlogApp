package blog;

//[START simple_includes]
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//[END simple_includes]

//[START multipart_includes]
import java.io.UnsupportedEncodingException;

//[END multipart_includes]
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CronJobServlet extends HttpServlet 
{
	private static final Logger _logger = Logger.getLogger(CronJobServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException 
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try 
		{
			_logger.info("Cron Job has been executed");
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("noreply@blogapp-144400.appspot.com", "noreply"));
			msg.addRecipient(Message.RecipientType.TO,
			new InternetAddress("ryan.maphet@utexas.edu", "Ryan Maphet"));
			msg.setSubject("Cron test job");
			msg.setText("Cron test jobs body");
			Transport.send(msg);
		} 
		catch (AddressException e) 
		{
		    _logger.info("Address Exception caught.\n" + e.getMessage());
		} 
		catch (MessagingException e) 
		{
			_logger.info("Messaging Exception caught.\n" + e.getMessage());
		} 
		catch (UnsupportedEncodingException e) 
		{
			_logger.info("Unsupported Encoding Exception caught.\n" + e.getMessage());
		}
		catch (Exception ex) 
		{
			_logger.info("Unknown Exception caught.\n" + ex.getMessage());
		}
		
}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException 
	{
		doGet(req, resp);
	}
}