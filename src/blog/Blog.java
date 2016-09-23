package blog;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

@Entity
@Cache
public class Blog implements Comparable<Blog>
{
	@Id public String BlogName;
	@Load List<Ref<BlogPost>> Posts = new ArrayList<Ref<BlogPost>>();
	
	public String GetBlogName()
	{
		return BlogName;
	}
	
	public List<Ref<BlogPost>> GetTopics()
	{
		return Posts;
	}
	
	public Boolean InsertTopic(BlogPost post)
	{
		Posts.add(Ref.create(Key.create(BlogPost.class, post.id)));
		return true;
	}
	
	@Override
	public int compareTo(Blog o) 
	{
		return this.BlogName.compareTo(o.BlogName);
	}
}
