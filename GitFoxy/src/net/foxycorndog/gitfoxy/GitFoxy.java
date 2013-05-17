package net.foxycorndog.gitfoxy;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.PullRequestMarker;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubRequest;
import org.eclipse.egit.github.core.client.GitHubResponse;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;

import net.foxycorndog.gitfoxy.command.Command;
import net.foxycorndog.gitfoxy.dialog.AuthenticationDialog;
import net.foxycorndog.gitfoxy.dialog.CommitMessageDialog;
import net.foxycorndog.gitfoxy.event.CommandEvent;
import net.foxycorndog.gitfoxy.event.CommandListener;
import net.foxycorndog.gitfoxy.util.ConfigReader;
import net.foxycorndog.jfoxyutil.Queue;

public class GitFoxy implements ActionListener, CommandListener
{
	private	String			configLocation;
	private	String			username, password;
	
	private	ImageIcon		destroyImage, destroyLightImage;
	
	private File			configFile;
	
	private	JLabel			directoryLabel;
	
	private JButton			pullButton, addButton, commitButton, pushButton, directoryBrowseButton, submitCommandButton, destroyButton;
	
	private JTextField		commandField;
	
	private JComboBox		directoryBox;
	
	private	JTextArea		outputArea;
	
	private JFileChooser	gitChooser, directoryChooser;
	
	private JFrame			frame;
	
	private	ArrayList<Command>		commands;
	
	private	HashMap<String, String>	configData;
	
	public GitFoxy()
	{
		//Basic authentication
		GitHubClient client = new GitHubClient();
		client.setCredentials("FoxyCorndog", "");
		GitHubRequest request = new GitHubRequest();
		request.setUri("/users/FoxyCorndog");
		
		try
		{
			GitHubResponse response = client.get(request);
			System.out.println(client.getUser());
			
			System.out.println(response.getBody());
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		
		commands = new ArrayList<Command>();
		
		configLocation = "gitfoxy.config";
		
		configFile     = new File(configLocation);
		
		if (!configFile.exists())
		{
			try
			{
				configFile.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		configData = new HashMap<String, String>();
		
		ConfigReader.read("gitfoxy.config", configData);
		
		if (!configData.containsKey("git.location") || new File(configData.get("git.location")).exists() == false)
		{
			setConfigDataValue("git.location", findGitLocation());
		}
		
		String pass = "";
		
		try
		{
			pass = new BufferedReader(new FileReader(new File("pass.txt"))).readLine();
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		
		RepositoryService service = new RepositoryService();
		service.getClient().setCredentials("FoxyCorndogTest", pass);
		
		PullRequestService pService = new PullRequestService();
		pService.getClient().setCredentials("FoxyCorndogTest", pass);
		
		RepositoryId repo = new RepositoryId("FoxyCorndog", "ArrowWorkspace");
		PullRequest request = new PullRequest();

		try
		{
			List<Repository> repos = service.getRepositories();
			repos.addAll(service.getOrgRepositories("FoxyCorndogOrganization"));
			
			System.out.println(repos.size());
			
			Repository wsp = service.getRepository(repo);
			
			System.out.println(wsp.getCreatedAt());
			
//			User user = null;
//			
//			UserService uService = new UserService();
//			user = uService.getUser("FoxyCorndog");
//			
//			System.out.println(user.getLogin());
			
			System.out.println(wsp);
			
			System.out.println(pService.createPullRequest(wsp, 0, "master", "master"));
			
//			PullRequestMarker p = new PullRequestMarker();
//			p.setRepo(wsp);
//			p.setUser(user);
//			request.setBase(p);
//			request.setHead(p);
//			
			
//			System.out.println(request.getState());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("done");
	}
	
	public void open()
	{
		frame = new JFrame();
		frame.setTitle("GitFoxy");
		frame.setSize(800, 500);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		pullButton = new JButton();
		pullButton.setText("Git pull");
		pullButton.setSize(100, 20);
		pullButton.setLocation(50, 80);
		pullButton.setVisible(true);
		pullButton.addActionListener(this);
		frame.add(pullButton);
		
		addButton = new JButton();
		addButton.setText("Git add");
		addButton.setSize(100, 20);
		addButton.setLocation(170, 80);
		addButton.setVisible(true);
		addButton.addActionListener(this);
		frame.add(addButton);
		
		commitButton = new JButton();
		commitButton.setText("Git commit");
		commitButton.setSize(100, 20);
		commitButton.setLocation(290, 80);
		commitButton.setVisible(true);
		commitButton.addActionListener(this);
		frame.add(commitButton);
		
		pushButton = new JButton();
		pushButton.setText("Git push");
		pushButton.setSize(100, 20);
		pushButton.setLocation(410, 80);
		pushButton.setVisible(true);
		pushButton.addActionListener(this);
		frame.add(pushButton);
		
		directoryLabel = new JLabel();
		directoryLabel.setText("Directory:");
		directoryLabel.setSize(100, 20);
		directoryLabel.setLocation(30, 10);
		frame.add(directoryLabel);
		
		directoryBox = new JComboBox();
		directoryBox.setSize(600, 20);
		directoryBox.setLocation(directoryLabel.getX(), directoryLabel.getY() + directoryLabel.getHeight() + 5);
		directoryBox.setEditable(true);
		frame.add(directoryBox);
		
		if (configData.containsKey("recent.directories"))
		{
			String recentDirectories[] = configData.get("recent.directories").split(";");
			
			for (String dir : recentDirectories)
			{
				directoryBox.addItem(dir);
			}
		}
		
		directoryBrowseButton = new JButton();
		directoryBrowseButton.setText("Browse");
		directoryBrowseButton.setSize(100, 20);
		directoryBrowseButton.setLocation(directoryBox.getX() + directoryBox.getWidth() + 10, directoryBox.getY());
		directoryBrowseButton.addActionListener(this);
		frame.add(directoryBrowseButton);
		
		outputArea = new JTextArea();
		outputArea.setLocation(directoryBox.getX(), 110);
		outputArea.setSize(frame.getWidth() - directoryBox.getX() * 2, frame.getHeight() - outputArea.getY() - 80);
		outputArea.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(outputArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setSize(outputArea.getSize());
		scroll.setLocation(outputArea.getLocation());
		frame.add(scroll);
		
		commandField = new JTextField();
		commandField.setSize(600, 20);
		commandField.setLocation(outputArea.getX(), outputArea.getY() + outputArea.getHeight() + 10);
		commandField.addActionListener(this);
		frame.add(commandField);
		
		submitCommandButton = new JButton();
		submitCommandButton.setText("Submit");
		submitCommandButton.setSize(100, 20);
		submitCommandButton.setLocation(commandField.getX() + commandField.getWidth() + 10, commandField.getY());
		submitCommandButton.addActionListener(this);
		frame.add(submitCommandButton);
		
		destroyImage      = new ImageIcon("res/images/destroy.png");
		destroyLightImage = new ImageIcon("res/images/destroylight.png");
		
		destroyButton = new JButton();
		destroyButton.setSize(16, 16);
		destroyButton.setIcon(destroyImage);
		destroyButton.setEnabled(false);
		destroyButton.setLocation(outputArea.getX() + outputArea.getWidth() - destroyButton.getWidth(), pullButton.getY());
		destroyButton.addActionListener(this);
		frame.add(destroyButton);
	    
		frame.setVisible(true);
	}
	
	/**
	 * Method that appends the given String to the value of the key given.
	 * 
	 * @param key The key to append to.
	 * @param value	The value to append.
	 */
	public void appendConfigDataValue(String key, String value)
	{
		String prev = "";
		
		if (configData.containsKey(key))
		{
			prev = configData.get(key);
		}
		
		setConfigDataValue(key, prev + value);
	}
	
	/**
	 * Set a CONFIG_DATA value in the HashMap and the arrow.config file.
	 * If the key is not already in the file, it will add it to the end.
	 * 
	 * @param key The key of the property to set.
	 * @param value The value of the property to set.
	 */
	public void setConfigDataValue(String key, String value)
	{
		boolean added = false;
		
		configData.put(key, value);
		
		try
		{
			PrintWriter p = new PrintWriter(new FileWriter(configLocation));
			
			String keys[] = configData.keySet().toArray(new String[0]);
			
			for (int i = 0; i < configData.size(); i ++)
			{
				String lineKey   = null;
				String lineValue = null;
				
				lineKey          = keys[i];
				
				if (lineKey.equals(key))
				{
					lineValue = configData.get(lineKey);
				}
				else
				{
					lineValue = configData.get(lineKey);
				}
				
				p.print(lineKey + "=" + lineValue + (i == configData.size() - 1 ? "" : "\r\n"));
			}
		
			p.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private String findGitLocation()
	{
		if (gitChooser == null)
		{
			gitChooser = new JFileChooser();
			gitChooser.setSize(400, 300);
			gitChooser.setVisible(true);
			gitChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			gitChooser.setAcceptAllFileFilterUsed(false);
		}
		
		gitChooser.showOpenDialog(frame);
		
		String loc = null;
		
		if (gitChooser.getSelectedFile() != null)
		{
			loc = gitChooser.getSelectedFile().getAbsolutePath();
		}
		
		return loc;
	}
	
	public void actionPerformed(ActionEvent event)
	{
		Object source = event.getSource();

		if (source == pullButton)
		{
			Command command = new Command(new String[] { configData.get("git.location") + "/bin/git", "pull", "origin", "master" }, directoryBox.getSelectedItem().toString());
			command.addCommandListener(this);
			command.execute();
		}
		else if (source == addButton)
		{
			Command command = new Command(new String[] { configData.get("git.location") + "/bin/git", "add", "." }, directoryBox.getSelectedItem().toString());
			command.addCommandListener(this);
			command.execute();
		}
		else if (source == commitButton)
		{
			CommitMessageDialog dialog = new CommitMessageDialog();

			String message = dialog.open();

			if (message != null)
			{
				Command command = new Command(new String[] { configData.get("git.location") + "/bin/git", "commit", "-m", '"' + message + '"' }, directoryBox.getSelectedItem().toString());
				command.addCommandListener(this);
				command.execute();
			}
		}
		else if (source == pushButton)
		{
			AuthenticationDialog dialog = new AuthenticationDialog();

			String values[] = dialog.open();

			if (values != null)
			{
				username = values[0];
				password = values[1];

				Command command = new Command(new String[] { configData.get("git.location") + "/bin/git", "push", "origin", "master" }, directoryBox.getSelectedItem().toString());
				command.addCommandListener(this);
				command.execute();
			}
		}
		else if (source == directoryBrowseButton)
		{
			if (directoryChooser == null)
			{
				directoryChooser = new JFileChooser();
				directoryChooser.setSize(400, 300);
				directoryChooser.setVisible(true);
				directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				directoryChooser.setAcceptAllFileFilterUsed(false);
			}

			directoryChooser.showOpenDialog(frame);

			if (directoryChooser.getSelectedFile() != null)
			{
				directoryBox.addItem(directoryChooser.getSelectedFile().getAbsolutePath());
				directoryBox.setSelectedItem(directoryChooser.getSelectedFile().getAbsolutePath());
			}
		}
		else if (source == submitCommandButton || source == commandField)
		{
			String commands[] = commandField.getText().split(" ");

			commandField.setText("");

			if (commands[0].startsWith("git"))
			{
				commands[0] = '"' + configData.get("git.location") + "/bin/git\"";

				Command command = new Command(commands, directoryBox.getSelectedItem().toString());
				command.addCommandListener(this);
				command.execute();
			}
			else
			{
				Command command = new Command(commands, directoryBox.getSelectedItem().toString());
				command.addCommandListener(this);
				command.execute();
			}
		}
		else if (source == destroyButton)
		{
			Command c = commands.get(0);
			c.destroy();
			commands.remove(0);
			
			if (commands.size() <= 0)
			{
				destroyButton.setEnabled(false);
			}
		}

		if (source == pullButton || source == submitCommandButton || source == commandField || source == pushButton || source == addButton || source == commitButton)
		{
			String dir = directoryBox.getSelectedItem().toString();

			if (new File(dir).isDirectory())
			{
				if (!configData.containsKey("recent.directories") || !configData.get("recent.directories").contains(dir + ";"))
				{
					appendConfigDataValue("recent.directories", dir + ";");
				}
			}
		}
	}

	public void onResultReceived(CommandEvent event)
	{
		outputArea.setText(event.getOutput());
		
		commands.remove(event.getCommand());
		
		if (commands.size() <= 0)
		{
			destroyButton.setEnabled(false);
		}
	}

	public void onOutputReceived(CommandEvent event)
	{
		outputArea.append(event.getOutput() + "\n");
	}

	public void onErrorReceived(CommandEvent event)
	{
		outputArea.append(event.getOutput() + "\n");
	}

	public void onCommandStarted(CommandEvent event)
	{
		Command command = event.getCommand();
		
		commands.add(command);
		
		destroyButton.setEnabled(true);
		
		PrintWriter writer = command.getWriter();

		System.out.println(username + ", " + password);
		writer.print(username);
		writer.print("\n");
		writer.flush();

		writer.print(password);
		writer.print("\n");
		writer.flush();

//		System.out.println(writer.);
//		writer.close();

		username = null;
		password = null;
	}
	
	public static void main(String args[])
	{
		GitFoxy program = new GitFoxy();
		
//		program.open();
	}
}
