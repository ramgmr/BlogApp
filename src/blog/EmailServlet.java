package blog;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static com.googlecode.objectify.ObjectifyService.ofy;

@SuppressWarnings("serial")
public class EmailServlet extends HttpServlet 
{
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException 
	{
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        if(user == null)
        {
        	resp.sendRedirect("/ofyblog.jsp");
        }
        List<People> people = ofy().load().type(People.class).list();
        People person = null;
        for(People p : people)
        {
        	if(p.email.compareTo(user.getEmail()) == 0)
        	{
        		person = p;
        		break;
        	}
        }
        if(person == null)
        {
        	person = new People(user);
        	person.Subscribed = !person.Subscribed;
        	ofy().save().entities(person).now();
        }
        else
        {
        	person.Subscribed = !person.Subscribed;
        	ofy().save().entities(person);
        }
	}
}
