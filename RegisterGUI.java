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

class RegisterGUI extends JFrame {
	JLabel titleLabel, usernameLabel, passwordLabel, confirmPasswordLabel;
	JTextField usernameField;
	JPasswordField passwordField, confirmPasswordField;
	JButton registerButton;

	public RegisterGUI() {
		// set window properties
		setTitle("Register");
		setSize(500, 400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);

		// create GUI components
		titleLabel = new JLabel("Create an Account");
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
		confirmPasswordLabel = new JLabel("Confirm Password");
		confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD, 16));
		confirmPasswordLabel.setForeground(new Color(0, 128, 128));
		confirmPasswordLabel.setBounds(100, 220, 150, 30);
		confirmPasswordLabel.setHorizontalAlignment(JLabel.CENTER);
		usernameField = new JTextField(20);
		usernameField.setBounds(100, 110, 300, 30);
		passwordField = new JPasswordField(20);
		passwordField.setBounds(100, 180, 300, 30);
		confirmPasswordField = new JPasswordField(20);
		confirmPasswordField.setBounds(100, 250, 300, 30);
		registerButton = new JButton("Register");
		registerButton.setFont(new Font("Arial", Font.BOLD, 16));
		registerButton.setBackground(new Color(0, 128, 128));
		registerButton.setForeground(Color.WHITE);
		registerButton.setBounds(180, 310, 140, 30);
		registerButton.setHorizontalAlignment(JButton.CENTER);

		// set layout and add components to the window
		setLayout(null);
		add(titleLabel);
		add(usernameLabel);
		add(passwordLabel);
		add(confirmPasswordLabel);
		add(usernameField);
		add(passwordField);
		add(confirmPasswordField);
		add(registerButton);

		// center the window on the screen
		setLocationRelativeTo(null);

		// add event listener for register button
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				String confirmPassword = new String(confirmPasswordField.getPassword());

				if (!password.equals(confirmPassword)) {
					JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.");
					passwordField.setText("");
					confirmPasswordField.setText("");
					return;
				}

				// code to add new user to database goes here
				// Establish database connection
				String url = "jdbc:sqlite:users.db";

				Connection conn = null;
				try {
					conn = DriverManager.getConnection(url);
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: Could not connect to database.");
					return;
				}

				try {
					// Check if username already exists in the database
					String query = "SELECT * FROM users WHERE username = ?";
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setString(1, username);
					ResultSet rs = ps.executeQuery();

					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different username.");
						usernameField.setText("");
						passwordField.setText("");
						confirmPasswordField.setText("");
						return;
					}

					// Insert new user into the database
					query = "INSERT INTO users (username, password) VALUES (?, ?)";
					ps = conn.prepareStatement(query);
					ps.setString(1, username);
					ps.setString(2, password);
					int rowsInserted = ps.executeUpdate();

					if (rowsInserted > 0) {
						JOptionPane.showMessageDialog(null, "User registered successfully.");
						dispose(); // close the window
					} else {
						JOptionPane.showMessageDialog(null, "Error: Could not register user.");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error: Could not register user.");
				} finally {
					// Close the database connection
					try {
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}

				dispose();
			}
		});

		// show window
		setVisible(true);
	}
}
