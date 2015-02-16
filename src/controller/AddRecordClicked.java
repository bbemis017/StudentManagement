package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddRecordClicked implements ActionListener {
	private Controller control;

	public AddRecordClicked(Controller control) {
		this.control = control;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (control.getTable().equals("EnrolledClasses")) {
			JTextField col1 = new JTextField();
			JTextField col2 = new JTextField();
			Object[] fields = {"Column 1" ,col1,"Column 2",col2};
			int result = JOptionPane.showConfirmDialog(null, fields,"Enter in data", JOptionPane.OK_CANCEL_OPTION);
			if( result == JOptionPane.OK_OPTION)
				control.dataBaseManager.addRecord(col1.getText(), col2.getText(), control.getTable());
			
		} else {
			JTextField column2 = new JTextField();
			Object[] fields = { "Column 2", column2 };
			int result = JOptionPane.showConfirmDialog(null, fields,"Enter in Column 2", JOptionPane.OK_CANCEL_OPTION);

			int newID = control.dataBaseManager.findNextIdInt(control.getTable());

			if (result == JOptionPane.OK_OPTION)
				control.dataBaseManager.addRecord("" + newID,column2.getText(), control.getTable());
		}
	}

}