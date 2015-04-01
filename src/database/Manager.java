package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Vector;



import controller.Controller;

public class Manager {
	
	public static final String SQL_SERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String URL = "jdbc:sqlserver://localhost;databaseName=School";
	public static final String STUDENT="Student", CLASS="Class", ENROLLED_CLASSES="EnrolledClasses";
	
	public static final String INNER_JOIN_QUERY = "SELECT EnrolledClasses.StudentID, Student.Name, Class.ID,Class.Title FROM Student INNER JOIN EnrolledClasses " +
			"ON Student.ID=EnrolledClasses.StudentID INNER JOIN Class ON Class.ID=EnrolledClasses.ClassID";
	
	private Connection connection;
	private Controller control;
	
	public Manager(Controller control){
		this.control = control;
	}
	/**
	 * Connects to the database
	 * 
	 * @param username - String user of database
	 * @param password - String password of user to database
	 * @return boolean true if connection was successful  false if connection was not
	 */
	public boolean connect(String username, String password){
		try{
			Class.forName(SQL_SERVER_DRIVER);
			connection = DriverManager.getConnection(URL,username,password);
			return true;
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			return false;
		}
		
		return false;
	}
	
	/**
	 * Sends query to server and returns ResultSet
	 * 
	 * @param query - String query to be sent
	 * @return ResultSet- returned from database
	 */
	public ResultSet getResult(String query){
		Statement statement;
		ResultSet result = null;
		try {
			//sends query to database
			statement = connection.createStatement();
			result = statement.executeQuery(query);
		} catch (SQLException e) {
			//executeQuery will throw SQLException if query is not possible
			//so notify user of invalid Entry
			control.invalidData();
		}
		return result;
	}
	
	/**
	 * sends sql command to database
	 * @param sql - String sql command to send to database
	 */
	public void Update(String sql){
		Statement statement;
		try{
			statement = connection.createStatement();
			statement.executeUpdate(sql);
		}catch(SQLException e){
			//notifies user of invalid Entry
			control.invalidData();
		}
	}
	
	/**
	 * Adds new record to the table and notifies controller to update GUI
	 * @param col1 - first column to update
	 * @param col2 - second column to update
	 * @param tableName - Name of table corresponding to database
	 */
	public void addRecord(String col1, String col2,String tableName){
		String query = "INSERT INTO " + tableName + " values (" + addSingleQuotes(col1) + "," + addSingleQuotes(col2) + ")";
		Update(query);
		control.updateTable( control.getTableName());
	}
	
	/**
	 * Deletes a record from the database
	 * @param id - what key value to identify the record with(primary key)
	 * @param col - what column to look in to find id
	 * @param tableName - Name of table corresponding to database
	 */
	public void deleteRecord(String id,String col,String tableName){
		String sql = "DELETE FROM " + tableName + " WHERE " + col + "=" + id;
		Update(sql);
		control.updateTable(control.getTableName());
	}
	
	/**
	 * deletes a record that requires a composite key from the database
	 * @param idName1 - String Name of first Id column
	 * @param id1 - String id value of record
	 * @param idName2 -String  Name of second Id column
	 * @param id2 - String id value of record
	 * @param tableName - String name of table corresponding to database
	 */
	public void deleteCompositeRecord(String idName1,String id1,String idName2,String id2, String tableName){
		String sql = "DELETE FROM " + tableName + " WHERE " + idName1 +"=" + id1 + " AND " + idName2 + "=" + id2 + ";";
		Update(sql);
		control.updateTable(tableName);
	}
	
	/**
	 * Update a record from the database
	 * @param tableName - Name of table corresponding to database
	 * @param set - what to set... in form of ='5'
	 * @param where - how to identify the row columnName='value' (primary key)
	 */
	public void updateRecord(String tableName,String set, String where){
		String sql = "UPDATE " + tableName + " SET " + set + " WHERE " + where;
		System.out.println(sql);
		Update(sql);
		control.updateTable( control.getTableName());
	}
	
	
	/**
	 * Finds the highest Integer + 1 in column ID
	 * @param tableName
	 * @return - int next highest in column unless it is empty then returns 1
	 */
	public int findNextIdInt(String tableName){
		ResultSet result = getResult("SELECT MAX(ID) FROM " + tableName);  //tells database to return Max value in ID
		try {
			result.next();	//skips first index
			String num = result.getString(1);
			if(num==null)
				return 1;
			return Integer.parseInt( num ) + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
		
		
	}
	
	/**
	 * Retrieves the designated table from the database
	 * 
	 * @param tableName - String name of table corresponding to database
	 * @return -Vector<Object> contains a vector<Object> of table data and a vector<Object>
	 * of Column names
	 */
	public Vector<Object> getTable(String tableName){
		
		String query;
		if(tableName.equals(Manager.ENROLLED_CLASSES))					//if table selected is EnrolledStudent do inner_join_query
			query = Manager.INNER_JOIN_QUERY;
		else
			query = "SELECT * FROM " + tableName;
		
		ResultSet result = getResult(query);
		try {
			//find the column names and store them in colNames
			int numCols = result.getMetaData().getColumnCount();
			Vector<String> colName = new Vector<String>(numCols);
			for( int i = 1; i <= numCols; i++)
				colName.add( result.getMetaData().getColumnName(i) );
			
			//get data from records
			Vector<Object> data = new Vector<Object>();
			while(result.next()){
				
				//store data from cells in records
				Vector<String> record = new Vector<String>(numCols);
				for(int i =1; i <= numCols;i++){
					record.add(result.getString(i));
					
				}
				
				data.add(record);
			}
			
			//stores data and colNames in one vector to be returned together
			 Vector<Object> dataAndCol = new Vector<Object>(2);
			 dataAndCol.add(data);
			 dataAndCol.add(colName);
			 
			 return dataAndCol;
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return null;//TODO: account for when this function returns null values
		
	}
	
	
	/**
	 * Surrounds str with single quotes and returns new String
	 * 'str'
	 * @param str
	 * @return - String str with single quotes around it
	 */
	public static String addSingleQuotes(String str){   return "'" + str + "'";  }
	
	/**
	 * closes connection to database
	 */
	public void closeConnection(){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
