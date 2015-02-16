package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import controller.Controller;

public class Manager {
	
	private Connection connection;
	private Controller control;
	
	public Manager(Controller control){
		this.control = control;
	}

	
	public boolean connect(String username, String password){
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=School",username,password);
			
			return true;
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			return false;
		}
		System.err.println("Connection Error not Accounted For");
		return false;
	}
	
	public ResultSet getResult(String query){
		Statement statement;
		ResultSet result = null;
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			//TODO: what if connection is broken
		}
		return result;
	}
	
	public void Update(String sql){
		Statement statement;
		try{
			statement = connection.createStatement();
			statement.executeUpdate(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void addRecord(String col1, String col2,String table){
		// puts together a query .. if col2 is a String it will be surrounded by '' otherwise it assumes that they are int
		String query = "INSERT INTO " + table + " values (" + col1 + "," + (col2 instanceof String ? ("'"+col2+"'") : col2) + ")";//TODO:
		Update(query);
		control.updateTable(control.dataBaseView.table, control.getTable());
	}
	
	public void deleteRecord(String id,String col,String table){
		String sql = "DELETE FROM " + table + " WHERE " + col + "=" + id;
		System.out.println(sql);
		Update(sql);
		control.updateTable(control.dataBaseView.table, control.getTable());
	}
	
	public void updateRecord(String table,String set, String where){
		String sql = "UPDATE " + table + " SET " + set + " WHERE " + where;
		System.out.println(sql);
		Update(sql);
		control.updateTable(control.dataBaseView.table, control.getTable());
	}
	
	
	
	public int findNextIdInt(String table){
		ResultSet result = getResult("SELECT MAX(ID) FROM " + table);
		try {
			result.next();
			String num = result.getString(1);
			if(num==null)
				return 1;
			return Integer.parseInt( num ) + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
		
	}
	
	//this works
	public JTable getTable(JTable table, String tableName){
		
		ResultSet result = getResult("SELECT * FROM " + tableName);
		try {
			
			int numCols = result.getMetaData().getColumnCount();
			Vector<String> colName = new Vector<String>(numCols);
			for( int i = 1; i <= numCols; i++)
				colName.add( result.getMetaData().getColumnName(i) );
			
			Vector<Object> data = new Vector<Object>();
			while(result.next()){
				
				Vector<String> record = new Vector<String>(numCols);
				for(int i =1; i <= numCols;i++){
					record.add(result.getString(i));
					
				}
				
				data.add(record);
			}
			
			
			 JTable temp = new JTable(data,colName);
			 table.setModel( temp.getModel() );
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return table;
		
	}

}
