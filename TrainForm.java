import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Calendar;

public class TrainForm extends JFrame implements ActionListener {

	JLabel titleLbl, fromLbl, toLbl, trainLbl, dateLbl, classLbl, nameLabel, ageLbl, genderLbl;
	JTextField nameTxt, ageTxt;
	JComboBox<String> fromCB, toCB, trainCB, classCB;
	JRadioButton maleRB, femaleRB;
	ButtonGroup genderGroup;
	JButton searchBtn, bookBtn, resetBtn;
	JSpinner dateSpinner;

	public TrainForm() {
		setTitle("Indian Railways Reservation System");
		setSize(700, 500);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Labels
		labels();

		// Text Fields
		text_fields();

		// Combo Boxes
		combo_boxes();

		// Spinner
		spinner();

		// Buttons
		buttons();

		// setBackgroundImage("background.jpg");

		// Add Components to Frame
		add_components();
	}

	public void labels() {
		titleLbl = new JLabel("Indian Railways Reservation System");
		titleLbl.setBounds(175, 20, 400, 30);
		titleLbl.setFont(new Font("Arial", Font.BOLD, 20));
		fromLbl = new JLabel("From");
		fromLbl.setBounds(50, 80, 100, 30);
		toLbl = new JLabel("To");
		toLbl.setBounds(350, 80, 100, 30);
		trainLbl = new JLabel("Train");
		trainLbl.setBounds(50, 140, 100, 30);
		dateLbl = new JLabel("Date");
		dateLbl.setBounds(350, 140, 100, 30);
		classLbl = new JLabel("Class");
		classLbl.setBounds(50, 140, 100, 30);
		nameLabel = new JLabel("Name");
		nameLabel.setBounds(50, 200, 100, 30);
		ageLbl = new JLabel("Age");
		ageLbl.setBounds(350, 200, 100, 30);
		genderLbl = new JLabel("Gender");
		genderLbl.setBounds(175, 300, 100, 30);
	}

	public void text_fields() {
		nameTxt = new JTextField();
		nameTxt.setBounds(100, 200, 200, 30);
		ageTxt = new JTextField();
		ageTxt.setBounds(400, 200, 200, 30);
	}

	public void combo_boxes() {
		String[] cities = { "Mumbai", "Delhi", "Chennai", "Kolkata", "Bengaluru", "Hyderabad", "Ahmedabad", "Pune" };
		fromCB = new JComboBox<>(cities);
		fromCB.setBounds(100, 80, 200, 30);
		toCB = new JComboBox<>(cities);
		toCB.setBounds(400, 80, 200, 30);
		// String[] trains = { "Rajdhani Express", "Duronto Express", "Shatabdi Express", "Garib Rath Express",
		// 		"Jan Shatabdi Express" };
		String[] trains = { "Rajdhani Express", "Shatabdi Express", "Duronto Express", "Garib Rath Express",
		"Humsafar Express", "Tejas Express", "Gatimaan Express", "Double Decker Express", "Janshatabdi Express",
		"Sampark Kranti Express", "Superfast Express", "Express", "Passenger Train", "Intercity Express",
		"Rajya Rani Express", "Antyodaya Express", "Mahamana Express", "Uday Express", "Him Darshan Express",
		"Kashi Mahakal Express" };
		trainCB = new JComboBox<>(trains);
		trainCB.setBounds(100, 140, 200, 30);
		String[] classes = { "AC First Class (1A)", "AC Two Tier (2A)", "AC Three Tier (3A)", "Sleeper Class (SL)",
				"Second Sitting (2S)" };
		classCB = new JComboBox<>(classes);
		classCB.setBounds(100, 140, 200, 30);
		// Radio Buttons
		maleRB = new JRadioButton("Male");
		maleRB.setBounds(250, 300, 100, 30);
		femaleRB = new JRadioButton("Female");
		femaleRB.setBounds(350, 300, 100, 30);
		genderGroup = new ButtonGroup();
		genderGroup.add(maleRB);
		genderGroup.add(femaleRB);
	}

	void spinner() {
		SpinnerDateModel dateModel = new SpinnerDateModel();
		dateSpinner = new JSpinner(dateModel);
		dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy"));
		dateSpinner.setBounds(400, 140, 200, 30);
	}

	public void buttons() {
		searchBtn = new JButton("Search Trains");
		searchBtn.setBounds(150, 375, 150, 30);
		searchBtn.addActionListener(this);
		bookBtn = new JButton("Book Ticket");
		// bookBtn.setBounds(250, 375, 150, 30);
		resetBtn = new JButton("Reset");
		resetBtn.setBounds(350, 375, 150, 30);
	}

	public void add_components() {
		add(titleLbl);
		add(fromLbl);
		add(toLbl);
		// add(trainLbl);
		add(dateLbl);
		add(classLbl);
		add(nameLabel);
		add(ageLbl);
		add(genderLbl);
		add(nameTxt);
		add(ageTxt);
		add(fromCB);
		add(toCB);
		// add(trainCB);
		add(classCB);
		add(maleRB);
		add(femaleRB);
		add(dateSpinner);
		add(searchBtn);
		add(bookBtn);
		add(resetBtn);
	}

	public void actionPerformed(ActionEvent ae) {
		// code for actions performed by buttons goes here
		if (ae.getSource() == searchBtn) {
			if (nameTxt.getText().isEmpty()) {
				System.out.println("Enter name");
				return;
			}
			if (ageTxt.getText().isEmpty()) {
				System.out.println("Enter age");
				return;
			}
			if (maleRB.isSelected() == false && femaleRB.isSelected() == false) {
				System.out.println("Choose gender");
				return;
			}

			System.out.println("search_button");
			dispose();
			new TrainSchedule(nameTxt.getText(), ageTxt.getText(), maleRB.isSelected(), femaleRB.isSelected(), fromCB, toCB, dateSpinner).setVisible(true);;
		}
	}

	public static void main(String[] args) {
		TrainForm frame = new TrainForm();
		frame.setVisible(true);
	}
}
