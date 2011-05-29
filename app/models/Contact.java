package models;

import javax.persistence.Entity;
import javax.persistence.Lob;

import play.db.jpa.Model;

@Entity
public class Contact extends Model
{
	public String name;
	public String firstName;
	public String mail;
	public String phone;
	public String cgroup;
	@Lob
	public String notes = "";

	public Contact()
	{
		// TODO Auto-generated constructor stub
	}

	public Contact(String name, String firstName, String group, String mail, String phone)
	{
		super();
		this.name = name;
		this.firstName = firstName;
		this.mail = mail;
		this.phone = phone;
		this.cgroup = group;

	}

	public static Contact byPhoneAndMail(String phone, String mail)
	{
		return Contact.find("byMailAndPhone", mail,phone).first();
		
	}

}
