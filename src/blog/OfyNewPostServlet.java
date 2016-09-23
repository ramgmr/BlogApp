package blog;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import blog.BlogPost;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static com.googlecode.objectify.ObjectifyService.ofy;

@SuppressWarnings("serial")
public class OfyNewPostServlet extends HttpServlet
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

        // We have one entity group per Guestbook with all Greetings residing
        // in the same entity group as the Guestbook to which they belong.
        // This lets us run a transactional ancestor query to retrieve all
        // Greetings for a given Guestbook.  However, the write rate to each
        // Guestbook should be limited to ~1/second.
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        if(title == null || title.length() == 0 || content == null || content.length() == 0)
        {
        	resp.sendRedirect("/ofyblog.jsp");
        }
        BlogPost post = new BlogPost(user, title, content);
        ofy().save().entities(post).now();
        resp.sendRedirect("/ofyblog.jsp");
	}
}
