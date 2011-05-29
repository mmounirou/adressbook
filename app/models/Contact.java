package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import play.data.validation.Email;
import play.data.validation.Phone;
import play.db.jpa.Model;

@Entity
public class Contact extends Model
{

	public String name;
	public String firstName;

	@Email
	public String mail;

	@Phone
	public String phone;

	@ManyToOne(cascade = CascadeType.PERSIST)
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
		return Contact.find("byMailAndPhone", mail, phone).first();

	}

}
