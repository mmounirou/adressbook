package utils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.Link;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.Organization;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.util.ServiceException;

public class GMailUtils
{

	public static class Person
	{
		private String familyName;
		private String name;
		private String phoneNumber;
		private String compagny;
		private String email;

		public Person(String familyName, String name, String compagny, String phoneNumber, String email)
		{
			this.familyName = familyName;
			this.name = name;
			this.phoneNumber = phoneNumber;
			this.compagny = compagny;
			this.email = email;
		}

		public String getFirstName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getPhoneNumber()
		{
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber)
		{
			this.phoneNumber = phoneNumber;
		}

		public String getCompagny()
		{
			return compagny;
		}

		public void setCompagny(String compagny)
		{
			this.compagny = compagny;
		}

		public String getEmail()
		{
			return email;
		}

		public void setEmail(String email)
		{
			this.email = email;
		}

		public String getFamilyName()
		{

			return familyName;
		}

		public void setFamilyName(String familyName)
		{
			this.familyName = familyName;
		}

		@Override
		public String toString()
		{
			return "Person{" + "familyName='" + familyName + '\'' + ", name='" + name + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", compagny='" + compagny + '\''
					+ ", email='" + email + '\'' + "}";
		}

	}

	public static List<Person> getContactFromGoogle(String usermail, String password) throws ServiceException, IOException
	{

		ContactsService myService = new ContactsService("AdressBook");
		myService.setUserCredentials(usermail, password);

		List<Person> persons = new ArrayList<Person>();

		// Request the feed
		URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");

		do
		{
			ContactFeed resultFeed = myService.getFeed(feedUrl, ContactFeed.class);

			for (int i = 0; i < resultFeed.getEntries().size(); i++)
			{
				ContactEntry entry = resultFeed.getEntries().get(i);
				String strName = "";
				String strFamilyName = "";
				String phoneNumber = "";
				String mail = "";
				String compagny = "";

				if (entry.hasName())
				{
					Name name = entry.getName();

					if (name.hasGivenName())
					{
						strName = name.getGivenName().getValue();

					}

					if (name.hasFamilyName())
					{
						strFamilyName = name.getFamilyName().getValue();

					}
				}

				for (Email email : entry.getEmailAddresses())
				{
					mail = email.getAddress();
				}

				for (Organization organization : entry.getOrganizations())
				{
					compagny = organization.getOrgName().getValue();

				}

				for (PhoneNumber number : entry.getPhoneNumbers())
				{
					phoneNumber = number.getPhoneNumber();
				}

				persons.add(new Person(strFamilyName, strName, compagny, phoneNumber, mail));

			}

			feedUrl = null;
			Link link = resultFeed.getNextLink();
			if (link != null)
			{

				feedUrl = new URL(link.getHref());

			}
		} while (feedUrl != null);
		return persons;
	}
}
