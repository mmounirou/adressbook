package controllers;

import java.io.IOException;
import java.util.List;

import models.Contact;
import models.User;

import org.apache.commons.lang.StringUtils;

import play.modules.paginate.ModelPaginator;
import play.modules.paginate.locator.JPAIndexedRecordLocator;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
import utils.GMailUtils;
import utils.GMailUtils.Person;

import com.google.gdata.util.ServiceException;

import controllers.Secure.Security;

@With(Secure.class)
public class Application extends Controller
{

	private static final int MAX_CONTACT_PER_PAGE = 5;

	@Before
	static void setConnectedUser()
	{
		if (Security.isConnected())
		{
			User user = User.byLogin(Security.connected());
			renderArgs.put("user", user);
		}
	}

	public static void index(String filter)
	{
		String strFilter = "";
		JPAIndexedRecordLocator locator = new JPAIndexedRecordLocator(Contact.class);
		if (!StringUtils.isEmpty(filter))
		{
			strFilter = "upper(cgroup) like upper(?) or upper(name) like upper(?) or upper(firstName) like upper(?) or upper(mail) like upper(?) or upper(phone) like upper(?)";
			String extFilter = "%" + filter + "%";
			locator = new JPAIndexedRecordLocator(Contact.class, strFilter,strFilter, extFilter, extFilter, extFilter, extFilter);
		} else
		{
			filter = "";
		}

		ModelPaginator paginator = new ModelPaginator(locator);
		paginator.setPageSize(MAX_CONTACT_PER_PAGE);
		paginator.setPagesDisplayed(3);
		render(paginator, filter);
	}

	@Check("admin")
	public static void edit(long id)
	{
		Contact contact = Contact.findById(id);
		render(contact);
	}

	public static void updateContact(long id, String cgroup, String name, String firstName, String mail, String phone)
	{
		Contact contact = Contact.findById(id);
		contact.name = name;
		contact.firstName = firstName;
		contact.mail = mail;
		contact.phone = phone;
		contact.cgroup = cgroup;
		contact.save();
		index("");
	}

	public static void addContact(String cgroup, String name, String firstName, String mail, String phone)
	{
		Contact contact = new Contact(name, firstName, cgroup, mail, phone);
		contact.create();

		// Refresh the interface
		index("");
	}

	public static void settings()
	{
		render();
	}

	@Check("admin")
	public static void loadFromGmail(String gmail, String password)
	{
		try
		{
			List<Person> contactFromGoogle = GMailUtils.getContactFromGoogle(StringUtils.defaultIfEmpty(gmail, ""), StringUtils.defaultIfEmpty(password, ""));
			int newContacts = 0;
			int updatedContacts = 0;
			for (Person person : contactFromGoogle)
			{
				Contact contact = Contact.byPhoneAndMail(person.getPhoneNumber(), person.getEmail());
				if (contact == null)
				{
					contact = new Contact(person.getFamilyName(), person.getFirstName(), person.getCompagny(), person.getEmail(), person.getPhoneNumber());
					contact.create();
					newContacts++;
				} else
				{
					boolean modified = false;
					if (!StringUtils.isEmpty(person.getFamilyName()) && StringUtils.equalsIgnoreCase(contact.name, person.getFamilyName()))
					{
						contact.name = person.getFamilyName();
						modified = true;
					}
					if (!StringUtils.isEmpty(person.getFirstName()) && StringUtils.equalsIgnoreCase(contact.firstName, person.getFirstName()))
					{
						contact.firstName = person.getFirstName();
						modified = true;
					}
					if (!StringUtils.isEmpty(person.getCompagny()) && StringUtils.equalsIgnoreCase(contact.cgroup, person.getCompagny()))
					{
						contact.name = person.getCompagny();
						modified = true;
					}
					if (!StringUtils.isEmpty(person.getEmail()) && StringUtils.equalsIgnoreCase(contact.mail, person.getEmail()))
					{
						contact.mail = person.getEmail();
						modified = true;
					}
					if (!StringUtils.isEmpty(person.getPhoneNumber()) && StringUtils.equalsIgnoreCase(contact.phone, person.getPhoneNumber()))
					{
						contact.phone = person.getPhoneNumber();
						modified = true;
					}

					if (modified)
					{
						contact.save();
						updatedContacts++;
					}
				}
			}
			flash.success(String.format("Your GMail's contacts are now loaded in adressbook: %d new %d updated", newContacts, updatedContacts));
		} catch (ServiceException e)
		{
			flash.error(e.getMessage());
		} catch (IOException e)
		{
			flash.error(e.getMessage());
		}
		settings();
	}
}