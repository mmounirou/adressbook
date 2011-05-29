package controllers;

import models.Contact;
import models.User;

import org.apache.commons.lang.StringUtils;

import play.modules.paginate.ModelPaginator;
import play.modules.paginate.locator.JPAIndexedRecordLocator;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
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
			strFilter = "upper(name) like upper(?) or upper(firstName) like upper(?) or upper(mail) like upper(?) or upper(phone) like upper(?)";
			String extFilter = "%" + filter + "%";
			locator = new JPAIndexedRecordLocator(Contact.class, strFilter, extFilter, extFilter, extFilter, extFilter);
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

	public static void updateContact(long id, String name, String firstName, String mail, String phone)
	{
		Contact contact = Contact.findById(id);
		contact.name = name;
		contact.firstName = firstName;
		contact.mail = mail;
		contact.phone = phone;
		contact.save();
		index("");
	}

	public static void addContact(String name, String firstName, String mail, String phone)
	{
		Contact contact = new Contact(name, firstName, mail, phone);
		contact.create();

		// Refresh the interface
		index("");
	}

}