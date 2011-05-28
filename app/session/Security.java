package session;

import models.User;

public class Security extends controllers.Secure.Security
{
	public static boolean authenticate(String username, String password)
	{
		return User.connect(username, password) != null;
	}

	public static boolean check(String profile)
	{
		User user = User.byLogin(Security.connected());
		return (user == null) ? false : user.isAdmin;
	}
}
