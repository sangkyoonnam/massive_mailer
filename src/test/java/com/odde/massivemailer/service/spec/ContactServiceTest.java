package com.odde.massivemailer.service.spec;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.impl.SqliteContact;

public class ContactServiceTest {

	private SqliteContact service = new SqliteContact();
	private Connection connection;
	private Statement stmt;
	private ResultSet resultSet;
	private List<ContactPerson> contactList;

	@Before
	public void setupConnection() throws ClassNotFoundException, SQLException {
		service.connectDB("jdbc:sqlite:oddemail.db");
		connection = service.getConnection();
		stmt = service.getStatement();
		stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS mail (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, name VARCHAR(50) NOT NULL)");
		stmt.executeUpdate("DELETE FROM mail");
		stmt.executeUpdate("INSERT INTO mail(name) VALUES ('name 1')");
	}

	@Test
	public void checkContactReturned() throws SQLException, ClassNotFoundException {
		contactList = service.getContactList();
		for(ContactPerson contact : contactList) {
			assertEquals("name 1", contact.getName());
		}
	}
	
	@Test
	public void addNewContactTest() throws SQLException {
		int rowAffected = service.addNewContact("AddNewContact");
		assertEquals(1, rowAffected);
	}
	
	@Test
	public void addNewContactFailedTest() throws SQLException {
		int rowAffected = service.addNewContact("name 1");
		assertEquals(0, rowAffected);
	}
	
	@After
	public void resetConnection() throws SQLException {
		stmt.close();
		connection.close();
	}
}