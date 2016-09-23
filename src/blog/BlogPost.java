package blog;
import java.util.Date;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


@Entity
public class BlogPost implements Comparable<BlogPost>
{
	@Id Long id;
	public User user;
	public String Title;
	public String Content;
	public Date DatePosted;
	
	@SuppressWarnings("unused")
	private BlogPost() 
	{
		DatePosted = new Date();
	}
	
	public BlogPost(User user, String title, String content)
	{
		this.user = user;
		this.Title = title;
		this.Content = content;
		DatePosted = new Date();
	}
	
	public User GetUser()
	{
		return user;
	}
	
	public String GetContent()
	{
		return Content;
	}
	
	public Date GetDatePosted()
	{
		return DatePosted;
	}
	
	// Organize BlogPost by Topic then by Date Posted then by Id
	@Override
	public int compareTo(BlogPost o) 
	{
		if(DatePosted.after(o.DatePosted))
		{
			return -1;
		}
		else if(DatePosted.before(o.DatePosted))
		{
			return 1;
		}
		return 0;
	}

}
