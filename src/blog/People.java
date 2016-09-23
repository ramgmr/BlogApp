package blog;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


@Entity
public class People implements Comparable<People>
{
	@Id public String email;
	public Boolean Subscribed = false;
	
	@SuppressWarnings("unused")
	private People() 
	{
		
	}
	
	public People(User user)
	{
		email = user.getEmail();
		Subscribed = false;
	}
	
	// Organize BlogPost by Topic then by Date Posted then by Id
	@Override
	public int compareTo(People o) 
	{
		return email.compareTo(o.email);
	}

}
