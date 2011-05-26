package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Contact extends Model
{
	public String name;
	public String firstName;
	public String mail;
	public String phone;

	public Contact()
	{
		// TODO Auto-generated constructor stub
	}

	public Contact(String name, String firstName, String mail, String phone)
	{
		super();
		this.name = name;
		this.firstName = firstName;
		this.mail = mail;
		this.phone = phone;
	}

}
