package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import database.Manager;

public class DeleteRecordClicked implements ActionListener {

	private Controller control;

	public DeleteRecordClicked(Controller control) {
		this.control = control;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String table = control.getTableName();
		int rowIndex = control.dataBaseView.table.getSelectedRow();
		
		//as long as they have selected a row
		if (rowIndex != -1) {
			
			//display JOptionPane
			Object[] text = {"Are you sure you want to delete this record here and everywhere else it is referenced?"};
			int result = JOptionPane.showConfirmDialog(null,text,"Are you Sure?",JOptionPane.YES_NO_OPTION);
			
			if (result == JOptionPane.YES_OPTION) {
				//get ID in first column
				String id = (String) control.dataBaseView.table.getModel().getValueAt(rowIndex, 0);
				
				if (table.equals(Manager.ENROLLED_CLASSES)){
					String id2 = (String) control.dataBaseView.table.getModel().getValueAt(rowIndex, 2);
					control.dataBaseManager.deleteCompositeRecord("StudentID", id, "ClassID", id2, Manager.ENROLLED_CLASSES);
				}
				else {
				
					control.dataBaseManager.deleteRecord(id, "ID", table);
				}
				
			}
			

		}
		
	}

}
