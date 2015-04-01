package controller;


import java.util.Vector;

import javax.swing.JOptionPane;


import database.Manager;
import view.DataBaseView;
import view.LoginView;

public class Controller {
	
	public Manager dataBaseManager;
	public LoginView login;
	public DataBaseView dataBaseView;
	
	private String tableName;
	
	/**
	 * Starts creates a new Manager for dataBase interactions and a login screen
	 */
	public Controller(){
		dataBaseManager = new Manager(this);
		login = new LoginView(this);
	}
	
	/**
	 * @param String- tableName - name of table in database to display on screen
	 * Gets data from the database and updates GUI
	 */
	@SuppressWarnings("unchecked")
	public void updateTable(String tableName){
		Vector<Object> dataAndCol = dataBaseManager.getTable(tableName);
		if( dataAndCol != null){
			dataBaseView.updateTableView( (Vector<Object>)dataAndCol.get(0) , (Vector<Object>) dataAndCol.get(1) );
			this.tableName = tableName;
		}
	}
	
	/**
	 * closing Login screen and starts the DataBaseView
	 */
	public void SuccessfulLogin() {
		login.setVisible(false);
		dataBaseView = new DataBaseView(this);
		this.tableName = "Student";
		login.dispose();
		updateTable("Student");
	}
	
	/**
	 * 
	 * @return - String Name of table currently being displayed
	 */
	public String getTableName() { return tableName; }
	
	/**
	 * Displays dialog for when the user has sent invalid data to the database
	 */
	public void invalidData(){
		Object[] field ={"Invalid"};
		JOptionPane.showConfirmDialog(null, field,"Invalid Data entry",JOptionPane.OK_OPTION);
	}
	
	/**
	 * Closes connection with database when application closes
	 */
	public void exit(){
		dataBaseManager.closeConnection();
	}


	public static void main(String[] args){
		 new Controller();
	}


	

}
