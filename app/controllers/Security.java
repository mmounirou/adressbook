package controllers;

import models.User;

public class Security extends controllers.Secure.Security
{
	static boolean authenticate(String username, String password)
	{
		return User.connect(username, password) != null;
	}

	static boolean check(String profile)
	{
		User user = User.byLogin(connected());
		return (user == null) ? false : user.isAdmin;
	}
}
