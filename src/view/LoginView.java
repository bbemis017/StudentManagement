package view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.Controller;
import controller.LoginClicked;

@SuppressWarnings("serial")
public class LoginView extends JFrame{
	
	private final int WIDTH = 400, HEIGHT = 150;
	
	private JPanel panel;
	private JButton loginButton;
	private JPasswordField passwordField;
	private JTextField userField;
	private JLabel failedLogin;
	
	public LoginView(Controller control){
		super("DataBase Login");
		setSize(WIDTH,HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		//TODO: appears in middle of screen
		
		//create screen
		createPanel();
		
		
		loginButton.addActionListener(new LoginClicked(control));
		
		add(panel);
		setVisible(true);
		
	}
	
	/**
	 * Creates Gui for login screen
	 */
	private void createPanel(){
		panel = new JPanel();
		panel.setLayout(null);
		
		JLabel userLabel = new JLabel("UserName: ");
		userLabel.setBounds(20, 10, 80, 25);
		panel.add(userLabel);
		
		userField = new JTextField(20);
		userField.setBounds(WIDTH/2 - 80,10,160,25);
		panel.add(userField);
		
		JLabel passwordLabel = new JLabel("Password: ");
		passwordLabel.setBounds(20,40,80,25);
		panel.add(passwordLabel);
		
		passwordField = new JPasswordField(20);
		passwordField.setBounds(WIDTH/2 - 80,40,160,25);
		panel.add(passwordField);
		
		loginButton = new JButton("Login");
		loginButton.setBounds(WIDTH/2-40,70,80,25);
		panel.add(loginButton);
		
		failedLogin =  new JLabel("Failed to login");
		failedLogin.setBounds(WIDTH/2-40,100,80,25);
		failedLogin.setForeground(Color.RED);
		failedLogin.setVisible(false);
		panel.add(failedLogin);
		
		
		
	}
	
	/**
	 * notifies user that the program failed to login to database
	 */
	public void loginFailed(){
		failedLogin.setVisible(true);
	}
	
	/**
	 * 
	 * @return - String text in username box
	 */
	public String getUserName(){ return userField.getText(); }
	/**
	 * 
	 * @return - String text in password box
	 */
	public String getPassword(){ return new String( passwordField.getPassword() ); }
	
	
	

}

