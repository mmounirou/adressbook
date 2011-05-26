package controllers;

import java.util.List;

import models.Contact;
import play.mvc.Controller;

public class Application extends Controller
{

	public static void index()
	{

		List<Contact> contacts = Contact.all().fetch();
		render(contacts);
	}

}