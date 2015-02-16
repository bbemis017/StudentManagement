package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javafx.scene.control.CheckBox;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controller.AddRecordClicked;
import controller.Controller;
import controller.DeleteRecordClicked;
import controller.TableSwitchClicked;
import controller.UpdateRecordClicked;

public class DataBaseView extends JFrame{
	
	private Controller control;
	public JTable table;
	
	private JPanel panel,tableSelection,editPanel;
	private JScrollPane scrollPane;
	private JButton studentButton, classButton, enrolledButton, addButton, updateButton, deleteButton;
	
	public DataBaseView(Controller control){
		super("Database School");
		setSize(800,700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.control = control;
		
		createPanel();
		add(panel);
		
		setVisible(true);
	}

	private void createPanel() {
		
		panel = new JPanel();
		
		//creates table selection panel and puts it on the left side
		panel.setLayout( new BorderLayout(10, 10) );
		tableSelection = new JPanel();
		tableSelection.setLayout( new BoxLayout(tableSelection, BoxLayout.PAGE_AXIS));
		studentButton = new JButton("Student");
		studentButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, studentButton.getMinimumSize().height));
		studentButton.addActionListener(new TableSwitchClicked(control, "Student"));
		tableSelection.add(studentButton);
		classButton = new JButton("Class");
		classButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, classButton.getMinimumSize().height));
		classButton.addActionListener(new TableSwitchClicked(control,"Class"));
		tableSelection.add(classButton);
		
		enrolledButton = new JButton("EnrolledClasses");
		enrolledButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, enrolledButton.getMinimumSize().height));
		enrolledButton.addActionListener(new TableSwitchClicked(control,"EnrolledClasses"));
		tableSelection.add(enrolledButton);
		
		panel.add(tableSelection,BorderLayout.WEST);
		
		//creates table for data in center
		createTable();
		panel.add( scrollPane, BorderLayout.CENTER);
		
		//creates modification panel on right side
		editPanel = new JPanel();
		editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.PAGE_AXIS) );
		addButton = new JButton("Add Record");
		addButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, addButton.getMinimumSize().height));
		addButton.addActionListener(new AddRecordClicked(control));
		editPanel.add(addButton);
		updateButton = new JButton("Update table");
		updateButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, updateButton.getMinimumSize().height));
		updateButton.addActionListener(new UpdateRecordClicked(control) );
		editPanel.add(updateButton);
		deleteButton = new JButton("Delete Record");
		deleteButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, deleteButton.getMinimumSize().height));
		deleteButton.addActionListener( new DeleteRecordClicked(control) );
		editPanel.add(deleteButton);
		
		panel.add(editPanel,BorderLayout.EAST);
		
	}
	
	public void updateTableView(){
		table.repaint();
		table.setVisible(true);
		scrollPane.setVisible(true);
		panel.setVisible(true);
		setVisible(true);
		
	}
	
	private void createTable(){
		
		
		String[] columnNames = {"one","two"};
		String[][] data = { {"1","name"},{"2","name"} };
		
		table = new JTable(data,columnNames);
		JTextField tf = new JTextField();
		tf.setEditable(false);
		DefaultCellEditor editor = new DefaultCellEditor(tf);
		table.setDefaultEditor(Object.class, editor);
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

	}

}
