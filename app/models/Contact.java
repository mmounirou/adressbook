package models;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

import play.db.jpa.Model;

@Entity
public class Contact extends Model
{
	public String name;
	public String firstName;
	public String mail;
	public String phone;
	@ManyToOne
	public ContactGroup cgroup;
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
		this.cgroup = ContactGroup.getOrCreate(group);

	}

	public static Contact byPhoneAndMail(String phone, String mail)
	{
		return Contact.find("byMailAndPhone", mail,phone).first();
		
	}

}
