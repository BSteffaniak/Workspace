package net.foxycorndog.gitfoxy.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AuthenticationDialog implements Dialog, ActionListener
{
	private boolean		submitted;
	
	private	JLabel		usernameLabel, passwordLabel;
	
	private	JTextField	usernameField, passwordField;
	
	private	JButton		submitButton;
	
	private	JDialog		dialog;
	
	public String[] open()
	{
		dialog = new JDialog();
		dialog.setSize(150, 160);
		dialog.setModal(true);
		dialog.setLocationRelativeTo(null);
		dialog.setResizable(false);
		dialog.setLayout(null);
		
		usernameLabel = new JLabel();
		usernameLabel.setText("Username:");
		usernameLabel.setSize(120, 20);
		usernameLabel.setLocation(10, 5);
		dialog.add(usernameLabel);
		
		usernameField = new JTextField();
		usernameField.setSize(120, 20);
		usernameField.setLocation(usernameLabel.getX(), usernameLabel.getY() + usernameLabel.getHeight());
		dialog.add(usernameField);
		
		passwordLabel = new JLabel();
		passwordLabel.setText("Password:");
		passwordLabel.setSize(120, 20);
		passwordLabel.setLocation(10, usernameField.getY() + usernameField.getHeight() + 10);
		dialog.add(passwordLabel);
		
		passwordField = new JTextField();
		passwordField.setSize(120, 20);
		passwordField.setLocation(passwordLabel.getX(), passwordLabel.getY() + passwordLabel.getHeight());
		dialog.add(passwordField);
		
		submitButton = new JButton();
		submitButton.setText("Submit");
		submitButton.setSize(100, 20);
		submitButton.setLocation(passwordLabel.getX() + passwordLabel.getWidth() - submitButton.getWidth(), passwordField.getY() + passwordField.getHeight() + 10);
		submitButton.addActionListener(this);
		dialog.add(submitButton);
		
		dialog.setVisible(true);
		
		if (submitted)
		{
			return new String[] { usernameField.getText(), passwordField.getText() };
		}
		else
		{
			return null;
		}
	}
	
	public void actionPerformed(ActionEvent event)
	{
		Object source = event.getSource();
		
		if (source == submitButton)
		{
			submitted = true;
			
			dialog.dispose();
		}
	}
}