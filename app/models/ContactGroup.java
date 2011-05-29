package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class ContactGroup extends Model
{
	public String name;

	public ContactGroup(String group)
	{
		this.name = group;
	}

	public ContactGroup()
	{
		// TODO Auto-generated constructor stub
	}

	public static ContactGroup getOrCreate(String group)
	{
		ContactGroup contactGroup = find("byName", group).first();
		if (contactGroup == null)
		{
			contactGroup = new ContactGroup(group);
			contactGroup.create();
		}
		return contactGroup;
	}

}
