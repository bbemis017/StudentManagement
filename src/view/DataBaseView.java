package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
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


@SuppressWarnings("serial")
public class DataBaseView extends JFrame{
	
	public static final int START_WIDTH=800, START_HEIGHT=700;
	
	private Controller control;
	public JTable table;
	
	private JPanel panel,tableSelection,editPanel;
	private JScrollPane scrollPane;
	private JButton studentButton, classButton, enrolledButton, addButton, updateButton, deleteButton;
	
	public DataBaseView(final Controller control){
		super("Database School");
		setSize(START_WIDTH,START_HEIGHT);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		this.control = control;
		
		//calls control.exit() when user closes window
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				control.exit();
				System.exit(0);
				
			}
		});
		
		createPanel();
		add(panel);
		
		setVisible(true);
	}
	
	/**
	 * Creates Gui
	 */
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
	
	/**
	 * Updates Gui of table
	 */
	public void updateTableView(Vector<Object> data, Vector<Object> ColNames){
		JTable temp = new JTable(data,ColNames);
		table.setModel( temp.getModel() );
		table.repaint();
	}
	

	
	/**
	 * Creates table
	 */
	private void createTable(){
		
		//temporary values
		String[] columnNames = {" "," "};
		String[][] data = { {" "},{" "} };
		
		//create table
		table = new JTable(data,columnNames);
		JTextField tf = new JTextField();
		tf.setEditable(false);
		DefaultCellEditor editor = new DefaultCellEditor(tf);
		table.setDefaultEditor(Object.class, editor);
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		

	}

}
