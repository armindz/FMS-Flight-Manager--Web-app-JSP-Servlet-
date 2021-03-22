package management;

import java.util.ArrayList;

import models.User;

import database.UserDatabase;

public class UserManagementSystem {

	UserDatabase userdb = new UserDatabase();

	public void createUser(String username, String password) {
		
		if (isUserDataUnique(username)) {
		User user = new User (userdb.generateUserID(), username, password);
		userdb.storeToDatabase(user);
		}
	}

	public void deleteUser(String username, String password) {
		
		userdb.deleteContentFromDatabase(getUserIdFromUsersData(username,password));
	}

	public void changeUsersPassword(String username, String password) {
		
		userdb.updateDatabaseContent(getUserIdFromUsersData(username, password), username, password);
	}

	private boolean isUserDataUnique(String username) {
		ArrayList<User> fetchDataToList = (ArrayList <User>) userdb.fetchDatabaseContent();
		for (int i = 0; i < fetchDataToList.size(); i++) {
			if (fetchDataToList.get(i).getUsername().equals(username)) {
				return false;
			}
		}

		return true;

	}

	public boolean isLoginValid(String username, String password) {

		ArrayList<User> fetchDataToList = (ArrayList<User>) userdb.fetchDatabaseContent();
		for (int i = 0; i < fetchDataToList.size(); i++) {
			if (fetchDataToList.get(i).getUsername().equals(username)
					&& fetchDataToList.get(i).getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<User> getUserList() {

		ArrayList<User> fetchDataToList = (ArrayList<User>) userdb.fetchDatabaseContent();
		return fetchDataToList;
	}
	
	private int getUserIdFromUsersData (String username, String password) {
		
		ArrayList<User> fetchDataToList = (ArrayList<User>) userdb.fetchDatabaseContent();
		int userID = 0;
		for (int i = 0; i < fetchDataToList.size(); i++) {
			if (fetchDataToList.get(i).getUsername().equals(username)
					&& fetchDataToList.get(i).getPassword().equals(password)) {
				userID = fetchDataToList.get(i).getUserID();
				
			}
			
			}
		
		return userID;
	}
}
