import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Calendar;

public class LoginGUI extends JFrame {
	JLabel titleLabel, usernameLabel, passwordLabel;
	JTextField usernameField;
	JPasswordField passwordField;
	JButton loginButton, registerButton;
	private Connection connection;
	private Statement statement;

	public LoginGUI() {
		// set window properties
		setTitle("Login");
		setSize(500, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		// create GUI components
		titleLabel = new JLabel("Login");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setForeground(new Color(0, 128, 128));
		titleLabel.setBounds(100, 20, 300, 30);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
		usernameLabel.setForeground(new Color(0, 128, 128));
		usernameLabel.setBounds(100, 80, 100, 30);
		usernameLabel.setHorizontalAlignment(JLabel.CENTER);
		passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
		passwordLabel.setForeground(new Color(0, 128, 128));
		passwordLabel.setBounds(100, 150, 100, 30);
		passwordLabel.setHorizontalAlignment(JLabel.CENTER);
		usernameField = new JTextField(20);
		usernameField.setBounds(100, 110, 300, 30);
		passwordField = new JPasswordField(20);
		passwordField.setBounds(100, 180, 300, 30);
		loginButton = new JButton("Login");
		loginButton.setFont(new Font("Arial", Font.BOLD, 16));
		loginButton.setBackground(new Color(0, 128, 128));
		loginButton.setForeground(Color.WHITE);
		loginButton.setBounds(100, 250, 140, 30);
		loginButton.setHorizontalAlignment(JButton.CENTER);
		registerButton = new JButton("Register");
		registerButton.setFont(new Font("Arial", Font.BOLD, 16));
		registerButton.setBackground(new Color(0, 128, 128));
		registerButton.setForeground(Color.WHITE);
		registerButton.setBounds(260, 250, 140, 30);
		registerButton.setHorizontalAlignment(JButton.CENTER);

		// set layout and add components to the window
		setLayout(null);
		add(titleLabel);
		add(usernameLabel);
		add(passwordLabel);
		add(usernameField);
		add(passwordField);
		add(loginButton);
		add(registerButton);

		// center the window on the screen
		setLocationRelativeTo(null);

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validateUser(usernameField.getText(), passwordField);
			}
		});
		// add event listener for register button
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RegisterGUI();
			}
		});

		// show window
		setVisible(true);
	}

	public boolean validateUser(String username, JPasswordField passwordField) {
		try {
			// Database Connection
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:users.db");
			statement = connection.createStatement();

			// Query to check if the entered username and password match
			char[] passwordChars = passwordField.getPassword();
			String password = new String(passwordChars);
			String query = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'";
			ResultSet resultSet = statement.executeQuery(query);

			// Check if the query returned a record
			if (resultSet.next()) {
				// Login successful
				dispose();
				TrainForm frame = new TrainForm();
				frame.setVisible(true);
				System.out.println("success");
				return true;
			} else {
				// Login failed
				System.out.println("failed");
				JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new LoginGUI();
	}
}
