package com.odde.massivemailer.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.service.ContactService;

public class SqliteContact implements ContactService {
	private String selectMailSql = "SELECT id, name FROM mail";
	private String dbName = "jdbc:sqlite:oddemail.db";
	private List<ContactPerson> contactList;
	private Statement statement;
	private Connection connection;

	public void connectDB(String url) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection(url);
	}

	/* (non-Javadoc)
	 * @see com.odde.massivemailer.service.ContactService#getContactList()
	 */
	@Override
	public List<ContactPerson> getContactList() {
		ResultSet resultSet = null;
		try {
			openConnection();
			createIfNotExistTable();
			
			resultSet = statement.executeQuery(this.selectMailSql);
			populateContactList(resultSet);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return contactList;
	}

	private void createIfNotExistTable() throws SQLException {
		statement.executeUpdate(
				"CREATE TABLE IF NOT EXISTS mail (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, name VARCHAR(50) NOT NULL)");		
	}

	/* (non-Javadoc)
	 * @see com.odde.massivemailer.service.ContactService#addNewContact(java.lang.String)
	 */
	@Override
	public int addNewContact(String name) {
		int rowAffected = 0;
		try {
			openConnection();
			rowAffected = saveContactToDatabase(name);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return rowAffected;
	}

	private int saveContactToDatabase(String name) throws SQLException {
		int rowAffected = 0;
		if(!contactExisted(name))
			rowAffected = statement.executeUpdate("INSERT INTO mail(name) VALUES ('"+ name +"')");
		return rowAffected;
	}

	private boolean contactExisted(String name) throws SQLException {
		ResultSet resultSet = statement.executeQuery("SELECT name FROM mail WHERE name='" + name + "'");
		if(resultSet.next()) {			
			return true;
		}
		return false;
	}
	
	private void populateContactList(ResultSet resultSet) throws SQLException{
		contactList = new ArrayList<ContactPerson>();
		while (resultSet.next()) {
			ContactPerson contact = new ContactPerson();
			contact.setId(resultSet.getInt("id"));
			contact.setName(resultSet.getString("name"));
			contactList.add(contact);
		}
	}
	
	private Statement openConnection() throws ClassNotFoundException, SQLException {
		this.connectDB(dbName);
		statement = getStatement();
		return statement;
	}
	
	private void closeConnection() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Statement getStatement() throws SQLException {
		statement = connection.createStatement();
		return statement;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
}