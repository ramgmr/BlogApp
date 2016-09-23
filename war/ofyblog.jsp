<%-- //[START all]--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%-- //[Start Imports] --%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="blog.Blog" %>
<%@ page import="blog.BlogPost" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- //[End Imports] --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>SAB</title>
	<link type="text/css" rel="stylesheet" href="/style/css/bootstrap.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	
</head>
<body>
<%
    String BlogName = request.getParameter("BlogName");
    if (BlogName == null) 
    {
        BlogName = "default";
    }
    pageContext.setAttribute("BlogName", BlogName);
%>
<!--  [START Navbar] -->
<div class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Super Awesome Blog</a>
    </div>
	
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-left">
	    <li class="active"><a href="#">Home <span class="badge">42</span></a></li>
	    <li><a href="#">Profile <span class="badge"></span></a></li>
	    <li><a href="#">Messages <span class="badge">3</span></a></li>
	  </ul>
      <ul class="nav navbar-nav navbar-right">
      		<li>
        	<%
        		UserService userService = UserServiceFactory.getUserService();
            	User user = userService.getCurrentUser();
			    if (user != null) 
			    {
			      	pageContext.setAttribute("user", user);
					%>
					<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>
					<%
			    }
			    else 
			    {
					%>
					<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">sign in</a>
					<%
				}
			%>			    
			</li>
      </ul>         
    </div>
  </div>
</div>
<!-- [END Navbar] -->



<div class="container">
	<%
		if(user != null)
		{
			%>
			<div>
				<a id="btnnewpost" class="btn btn-default btn-lg btn-block">New Post</a>
			</div>
			<%
		}
	%>	
	<!-- [START Printing Post] -->
	<div class="jumbotron">
	  <div class="">
	    <h1>Recent Post</h1>
	  </div>
	<%
		ObjectifyService.register(BlogPost.class);		
		List<BlogPost> Posts = ObjectifyService.ofy().load().type(BlogPost.class).list();
		Collections.sort(Posts);
		if (Posts.isEmpty()) 
		{
			%>
	        <div class="alert alert-dismissible alert-danger">
	  		<button type="button" class="close" data-dismiss="alert">&times;</button>
	  		<strong>Oh snap!</strong> The blog is empty. You should login and post something!
			</div>
			<%
	    } 
	    else 
	    {
	    	int i = 0;
			for (BlogPost g : Posts) 
	       	{
				pageContext.setAttribute("post_content", g.Content);
				pageContext.setAttribute("post_user", g.user);
				pageContext.setAttribute("post_date", g.DatePosted);
				%>
				<blockquote>
				  <p>${fn:escapeXml(post_content)}</p>
				  <small><cite title="Source Title">${fn:escapeXml(post_user.nickname)}</cite>  ${fn:escapeXml(post_date)}</small>
				</blockquote>
				<%
	        }
	    }
	%>
	</div>
<!-- [END Printing Post] -->
	<%
		if(user != null)
		{
			%>
			<div>
				<a id="btnnewpost2" class="btn btn-default btn-lg btn-block">New Post</a>
			</div>
			<%
		}
	%>
	<!-- [START New Post] -->
  <div class="modal fade" id="newpost" >
	<div class="modal-dialog">
	  <div class="modal-content">
	    <div class="modal-header">
	      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	      <h4 class="modal-title">New Post</h4>
	    </div>
	    <form action="/ofynewpost" method="post">
	    <div class="modal-body">
	      <div>
      		<textarea name="content" rows="5" cols="60"></textarea>
          </div>
      	  <input type="hidden" name="blogname" value="${fn:escapeXml(BlogName)}"/>
          
	    </div>
	    <div class="modal-footer">
	      <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      <button type="submit" class="btn btn-primary">Save</button>
	    </div>
	    </form>
	  </div>
	</div>
  </div>
  <!-- [END New Post] -->
  </div>
	
  <script>
	$(document).ready(function(){
	    $("#btnnewpost").click(function(){
	        $("#newpost").modal();
	    });
	    
	    $("#btnnewpost2").click(function(){
	        $("#newpost").modal();
	    });
    });
  </script>
</body>
</html>