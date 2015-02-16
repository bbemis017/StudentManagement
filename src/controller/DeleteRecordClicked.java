package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class DeleteRecordClicked implements ActionListener {

	private Controller control;

	public DeleteRecordClicked(Controller control) {
		this.control = control;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String table = control.getTable();
		int rowIndex = control.dataBaseView.table.getSelectedRow();
		if (rowIndex != -1) {
			Object[] text = {"Are you sure you want to delete this record here and everywhere else it is referenced?"};
			int result = JOptionPane.showConfirmDialog(null,text,"Are you Sure?",JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				String id = (String) control.dataBaseView.table.getModel()
						.getValueAt(rowIndex, 0);
				if (table.equals("EnrolledClasses"))
					control.dataBaseManager
							.deleteRecord(id, "StudentID", table);
				else {

					String col = "StudentID";
					if (table.equals("Class"))
						col = "ClassID";
					control.dataBaseManager.deleteRecord(id, col,
							"EnrolledClasses");
					control.dataBaseManager.deleteRecord(id, "ID", table);
				}
			}

		}
	}

}
