package models;

import javax.persistence.Entity;

import play.db.jpa.Model;
import play.mvc.With;
import controllers.Secure;

@With(Secure.class)
@Entity
public class User extends Model
{
	public String name;
	public String firstname;
	public String login;
	public String password;
	public boolean isAdmin;

	public User(String name, String firstname, String login, String password)
	{
		this.name = name;
		this.firstname = firstname;
		this.login = login;
		this.password = password;
	}

	public User(String name, String firstname, String login, String password, boolean isAdmin)
	{
		this(name, firstname, login, password);
		this.isAdmin = isAdmin;
	}

	public static User connect(String login, String password)
	{

		return find("byLoginAndPassword", login, password).first();
	}

	public static User byLogin(String login)
	{
		return find("byLogin", login).first();
	}

}
