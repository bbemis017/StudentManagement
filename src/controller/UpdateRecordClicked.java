package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class UpdateRecordClicked implements ActionListener{
	
	private Controller control;
	
	public UpdateRecordClicked(Controller control){
		this.control = control;
	}
	
	public String addSingleQuotes(String str){
		
		//TODO: single quotes are always ok make this available to other parts of program
			return "'" + str + "'";
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String table = control.getTable();
		int rowIndex = control.dataBaseView.table.getSelectedRow();
		if(rowIndex!=-1 && !table.equals("EnrolledClasses")){
			int firstCol = 0, secondCol = 1;
			
			JTextField col1 = new JTextField();
			col1.setText( (String) control.dataBaseView.table.getModel().getValueAt(rowIndex, 0) ); 
			String col1Name = control.dataBaseView.table.getModel().getColumnName(firstCol);
			JTextField col2 = new JTextField();
			col2.setText(  (String) control.dataBaseView.table.getModel().getValueAt(rowIndex, 1) );
			String col2Name = control.dataBaseView.table.getModel().getColumnName(secondCol);
			Object[] fields = {col1Name,col1,col2Name,col2};
			
			int result = JOptionPane.showConfirmDialog(null, fields,"Edit Record",JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.OK_OPTION){
				String where = "";
				
					where = "ID=" + (String)control.dataBaseView.table.getModel().getValueAt(rowIndex,0);
				
					//cannot update the EnrolledClasses table
				
				control.dataBaseManager.updateRecord(control.getTable(), col1Name+"="+addSingleQuotes(col1.getText()), where);
				control.dataBaseManager.updateRecord(control.getTable(), col2Name+"="+addSingleQuotes(col2.getText()), where);
			}

		}
	}

}
