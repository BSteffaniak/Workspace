package net.foxycorndog.gitfoxy.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CommitMessageDialog implements Dialog, ActionListener
{
	private boolean		submitted;
	
	private	JLabel		instructionLabel;
	
	private	JButton		submitButton;
	
	private	JTextArea	messageArea;
	
	private	JDialog		dialog;
	
	public String open()
	{
		dialog = new JDialog();
		dialog.setSize(500, 300);
		dialog.setModal(true);
		dialog.setLocationRelativeTo(null);
		dialog.setResizable(false);
		dialog.setLayout(null);
		
		instructionLabel = new JLabel();
		instructionLabel.setText("Commit message:");
		instructionLabel.setSize(120, 20);
		instructionLabel.setLocation(10, 5);
		dialog.add(instructionLabel);
		
		messageArea = new JTextArea();
		messageArea.setSize(dialog.getWidth() - instructionLabel.getX() * 2, dialog.getHeight() - instructionLabel.getY() - instructionLabel.getHeight() - 80);
		messageArea.setLocation(instructionLabel.getX(), instructionLabel.getY() + instructionLabel.getHeight() + 5);
		messageArea.setWrapStyleWord(true);
		messageArea.setLineWrap(true);
		
		JScrollPane scroll = new JScrollPane(messageArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setSize(messageArea.getSize());
		scroll.setLocation(messageArea.getLocation());
		dialog.add(scroll);
		
		submitButton = new JButton();
		submitButton.setText("Submit");
		submitButton.setSize(100, 20);
		submitButton.setLocation(messageArea.getX() + messageArea.getWidth() - submitButton.getWidth(), messageArea.getY() + messageArea.getHeight() + 10);
		submitButton.addActionListener(this);
		dialog.add(submitButton);
		
		dialog.setVisible(true);
		
		if (submitted)
		{
			return messageArea.getText();
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