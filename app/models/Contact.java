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
}
