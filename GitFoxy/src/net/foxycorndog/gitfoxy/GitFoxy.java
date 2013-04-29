package net.foxycorndog.gitfoxy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import net.foxycorndog.gitfoxy.command.Command;

public class GitFoxy implements ActionListener
{
	private String			gitLocation;
	
	private File			config;
	
	private JButton			locateGitButton, pullButton;
	
	private JFileChooser	fileLocator;
	
	private JFrame			frame;
	
	public GitFoxy()
	{
		config = new File("gitfoxy.config");
		
		if (!config.exists())
		{
			try
			{
				config.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		checkGitLocation();
	}
	
	public void open()
	{
		frame = new JFrame();
		frame.setTitle("GitFoxy");
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		locateGitButton = new JButton("Locate Git");
		locateGitButton.setSize(180, 20);
		locateGitButton.setLocation(50, 50);
		locateGitButton.setVisible(true);
		locateGitButton.addActionListener(this);
		frame.add(locateGitButton);
		
		pullButton = new JButton("Git pull");
		pullButton.setSize(100, 20);
		pullButton.setLocation(50, 80);
		pullButton.setVisible(true);
		pullButton.addActionListener(this);
		frame.add(pullButton);
		
		frame.setVisible(true);
	}
	
	private void checkGitLocation()
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(config));
			
			String line = null;
			
			try
			{
				line = br.readLine();
			
				if (line == null || line.equals(""))
				{
					
				}
				else
				{
					gitLocation = line;
				}
				
				br.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	private String findGitLocation()
	{
		fileLocator = new JFileChooser();
		fileLocator.setSize(400, 300);
		fileLocator.setVisible(true);
		fileLocator.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileLocator.setAcceptAllFileFilterUsed(false);
		fileLocator.showOpenDialog(frame);
		
		String loc = null;
		
		if (fileLocator.getSelectedFile() != null)
		{
			loc = fileLocator.getSelectedFile().getAbsolutePath();
		}
		
		return loc;
	}
	
	public static void main(String args[])
	{
		GitFoxy program = new GitFoxy();
		
		program.open();
	}

	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() == locateGitButton)
		{
			gitLocation = findGitLocation();
			
			PrintWriter pw  = null;
			
			try
			{
				pw = new PrintWriter(new File("gitfoxy.config"));
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			
			pw.println(gitLocation);
			
			pw.close();
		}
		else if (event.getSource() == pullButton)
		{
			System.out.println(new File(gitLocation + "/bin/git.exe").exists());
			Command.execute(new String[] { "mkdir", "test" }, gitLocation + "/bin/");
		}
	}
}