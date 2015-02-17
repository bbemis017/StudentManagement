package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableSwitchClicked implements ActionListener {
	private Controller control;
	private String table;

	public TableSwitchClicked(Controller control, String table) {
		this.control = control;
		this.table = table;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		control.updateTable( table);
	}

}
