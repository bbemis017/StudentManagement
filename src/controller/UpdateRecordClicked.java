package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import database.Manager;

public class UpdateRecordClicked implements ActionListener{
	
	private Controller control;
	
	public UpdateRecordClicked(Controller control){
		this.control = control;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String table = control.getTableName();
		int rowIndex = control.dataBaseView.table.getSelectedRow();
		
		//cannot edit a record from EnrolledClasses can only add and delete
		if(rowIndex!=-1 && !table.equals("EnrolledClasses")){
			int firstCol = 0, secondCol = 1;
			
			JTextField col1 = new JTextField();
			JTextField col2 = new JTextField();
			
			//set text in text fields to current values in table
			col1.setText( (String) control.dataBaseView.table.getModel().getValueAt(rowIndex, firstCol) );
			col2.setText(  (String) control.dataBaseView.table.getModel().getValueAt(rowIndex, secondCol) );
			
			String col1Name = control.dataBaseView.table.getModel().getColumnName(firstCol);
			String col2Name = control.dataBaseView.table.getModel().getColumnName(secondCol);
			
			Object[] fields = {col1Name,col1,col2Name,col2};
			int result = JOptionPane.showConfirmDialog(null, fields,"Edit Record",JOptionPane.OK_CANCEL_OPTION);
			
			if(result == JOptionPane.OK_OPTION){
				
				//finds ID of record going to be updated
				String where = "ID=" + (String)control.dataBaseView.table.getModel().getValueAt(rowIndex,0);
				
				//updates both values entered
				control.dataBaseManager.updateRecord(control.getTableName(), col1Name+"="+Manager.addSingleQuotes(col1.getText()), where);
				control.dataBaseManager.updateRecord(control.getTableName(), col2Name+"="+Manager.addSingleQuotes(col2.getText()), where);
			}

		}
	}

}
