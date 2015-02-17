package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import database.Manager;

public class AddRecordClicked implements ActionListener {
	private Controller control;

	public AddRecordClicked(Controller control) {
		this.control = control;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (control.getTableName().equals(Manager.ENROLLED_CLASSES)) {	//if EnrolledClasses table is selected then user can enter two fields				
			
			JTextField col1 = new JTextField();
			JTextField col2 = new JTextField();
			
			// gets the name of the columns that the user can enter data into
			String col1Name = control.dataBaseView.table.getModel().getColumnName(0);
			String col2Name = control.dataBaseView.table.getModel().getColumnName(2);
			
			//creates JOptionPane with 2 labels and 2 textfields
			Object[] fields = {col1Name ,col1,col2Name,col2};
			int result = JOptionPane.showConfirmDialog(null, fields,"Enter in data", JOptionPane.OK_CANCEL_OPTION);
			
			if( result == JOptionPane.OK_OPTION)
				control.dataBaseManager.addRecord(col1.getText(), col2.getText(), control.getTableName());
			
			
		} else {	//if table is not EnrolledClasses	
			
			//creates JOptionPane
			JTextField column2 = new JTextField();
			Object[] fields = { "Column 2", column2 };
			int result = JOptionPane.showConfirmDialog(null, fields,"Enter in Column 2", JOptionPane.OK_CANCEL_OPTION);
			
			//creates a unique id for new record
			int newID = control.dataBaseManager.findNextIdInt(control.getTableName());
			
			
			if (result == JOptionPane.OK_OPTION)
				control.dataBaseManager.addRecord("" + newID,column2.getText(), control.getTableName());
		}
	}

}
