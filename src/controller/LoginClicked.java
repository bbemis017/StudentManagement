package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginClicked implements ActionListener{
		private Controller control;
		
		public LoginClicked(Controller control){
			this.control = control;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
				if( control.dataBaseManager.connect(control.login.getUserName(), control.login.getPassword() ) ) {
					
					control.SuccessfulLogin();
				}else{
					control.login.loginFailed();
				}
			
			
		}
		
	
}
