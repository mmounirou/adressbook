package controllers;

import java.io.IOException;
import java.util.List;

import models.Contact;
import models.ContactGroup;
import play.mvc.Controller;
import play.mvc.With;

import com.google.gson.Gson;

@Check("admin")
@With(Secure.class)
public class Utils extends Controller
{

	public static void exportContacts()
	{
		List<ContactGroup> groups = ContactGroup.findAll();
		List<Contact> contacts = Contact.findAll();
		renderJSON(contacts);
		if (request.format.equals("json"))
			renderJSON(contacts);

		try
		{
			response.out.write(new Gson().toJson(contacts).getBytes());
			response.out.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
