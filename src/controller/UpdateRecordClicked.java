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
	
	private String addSingleQuotes(String str){
		
		//TODO: single quotes are always ok make this available to other parts of program
			return "'" + str + "'";
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String table = control.getTable();
		int rowIndex = control.dataBaseView.table.getSelectedRow();
		if(rowIndex!=-1){
			JTextField col1 = new JTextField();
			col1.setText( (String) control.dataBaseView.table.getModel().getValueAt(rowIndex, 0) );
			JTextField col2 = new JTextField();
			col2.setText( (String) control.dataBaseView.table.getModel().getValueAt(rowIndex, 1) );
			String set1 = control.dataBaseView.table.getColumnName(0);
			String set2 = control.dataBaseView.table.getColumnName(1);
			Object[] fields = {set1,col1,set2, col2};
			int result = JOptionPane.showConfirmDialog(null, fields,"Edit Record",JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.OK_OPTION){
				String where = "";
				if(table.equals("Class") || table.equals("Student")){
					where = "ID=" + (String)control.dataBaseView.table.getModel().getValueAt(rowIndex, 0);
				}else{//find row then edit
					
						
					
				}
				
				
				control.dataBaseManager.updateRecord(control.getTable(),set1+"="+addSingleQuotes(col1.getText()),where);
				control.dataBaseManager.updateRecord(control.getTable(),set2+"="+addSingleQuotes(col2.getText()),where);
			}
		}
	}

}
