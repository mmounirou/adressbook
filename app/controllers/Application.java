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

	public static void edit(long id)
	{
		Contact contact = Contact.findById(id);
		render(contact);
	}

	public static void updateContact(long id, String name, String firstName, String mail, String phone)
	{
		Contact contact = Contact.findById(id);
		contact.name = name;
		contact.firstName = firstName;
		contact.mail = mail;
		contact.phone = phone;
		contact.save();
		index();
	}

	public static void addContact(String name, String firstName, String mail, String phone)
	{
		Contact contact = new Contact(name, firstName, mail, phone);
		contact.create();

		// Refresh the interface
		index();
	}

}