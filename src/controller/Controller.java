package controller;

import java.awt.Window;
import java.awt.event.WindowEvent;

import javax.swing.JTable;

import database.Manager;
import view.DataBaseView;
import view.LoginView;

public class Controller {
	
	public Manager dataBaseManager;
	public LoginView login;
	public DataBaseView dataBaseView;
	
	private String table;
	
	public Controller(){
		dataBaseManager = new Manager(this);
		login = new LoginView(this);
		
		//TODO: temp shortcut
//		dataBaseView = new DataBaseView(this);
	}
	
	public void updateTable(JTable table,String tableName){
		dataBaseManager.getTable(table, tableName);
		dataBaseView.updateTableView();
		this.table = tableName;
	}
	
	public void SuccessfulLogin() {
		login.setVisible(false);
		dataBaseView = new DataBaseView(this);
		this.table = "Student";
		login.dispose();
		updateTable(dataBaseView.table,"Student");
	}
	
	
	public static void main(String[] args){
		Controller control = new Controller();
	}

	public String getTable() {
		return table;
	}




	

}
