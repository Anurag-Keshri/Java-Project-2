import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class TrainSchedule extends JFrame implements ActionListener {

	private final String[] stations = { "Mumbai", "Delhi", "Chennai", "Kolkata", "Bangalore", "Hyderabad", "Ahmedabad",
			"Pune", "Jaipur", "Lucknow", "Surat", "Kanpur", "Nagpur", "Visakhapatnam", "Bhopal", "Patna", "Ludhiana", "Agra",
			"Nashik", "Vadodara" };
	private final String[] trains = { "Rajdhani Express", "Shatabdi Express", "Duronto Express", "Garib Rath Express",
			"Humsafar Express", "Tejas Express", "Gatimaan Express", "Double Decker Express", "Janshatabdi Express",
			"Sampark Kranti Express", "Superfast Express", "Express", "Passenger Train", "Intercity Express",
			"Rajya Rani Express", "Antyodaya Express", "Mahamana Express", "Uday Express", "Him Darshan Express",
			"Kashi Mahakal Express" };

	private JComboBox<String> fromStation;
	private JComboBox<String> toStation;
	private JSpinner dateSpinner;
	private JButton showTrainsBtn;
	private JButton bookBtn;
	private JTable trainTable;
	String name;
	String age;
	boolean male;
	boolean female;
	public TrainSchedule(String name, String age, boolean male, boolean female, JComboBox from, JComboBox to, JSpinner spinner) {
		super("Indian Train Schedule");
		this.name = name;
		this.age = age;
		this.male = male;
		this.female = female;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Initialize UI components
		// fromStation = new JComboBox<>(stations);
		fromStation = (from);
		// toStation = new JComboBox<>(stations);
		toStation = (to);
		dateSpinner = spinner;
		dateSpinner.setValue(spinner.getValue());
		
		showTrainsBtn = new JButton("Show Trains");
		showTrainsBtn.addActionListener(this);
		bookBtn = new JButton("Book");
		bookBtn.addActionListener(this);

		// Place UI components in a panel
		JPanel inputPanel = new JPanel();
		inputPanel.add(new JLabel("From:"));
		inputPanel.add(fromStation);
		inputPanel.add(new JLabel("To:"));
		inputPanel.add(toStation);
		inputPanel.add(new JLabel("Date:"));
		inputPanel.add(dateSpinner);
		inputPanel.add(showTrainsBtn);
		inputPanel.add(bookBtn);

		// Initialize train table
		String[] columns = { "Train", "Departure Time", "Arrival Time" };
		DefaultTableModel model = new DefaultTableModel(columns, 0);
		trainTable = new JTable(model);
		trainTable.setDefaultEditor(Object.class, null);;

		// Add train table to a scroll pane
		JScrollPane scrollPane = new JScrollPane(trainTable);

		// Add input panel and scroll pane to the window
		getContentPane().add(inputPanel, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(bookBtn, BorderLayout.SOUTH);

		// Set window size and position
		setSize(600, 400);
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == showTrainsBtn) {
			// Get selected from station
			String from = (String) fromStation.getSelectedItem();

			// Get selected to station
			String to = (String) toStation.getSelectedItem();

			// Get selected date
			Date date = (Date) dateSpinner.getValue();
			// Generate a list of trains for the selected stations and date
			ArrayList<String[]> trainList = generateTrainList(from, to, date);

			// Update the train table with the new data
			DefaultTableModel model = (DefaultTableModel) trainTable.getModel();
			model.setRowCount(0);
			for (String[] train : trainList) {
				model.addRow(train);
			}
		}

		if (e.getSource() == bookBtn) {
			dispose();
			new TrainTicketGenerator(name, age, male, female, trainTable).setVisible(true);;
			
		}
	}

	private ArrayList<String[]> generateTrainList(String from, String to, Date date) {
		ArrayList<String[]> trainList = new ArrayList<>();

		// Get a random number of trains between 5 and 15
		Random rand = new Random();
		int numTrains = rand.nextInt(11) + 5;

		// Get the selected date in "dd/MM/yyyy" format
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String selectedDate = dateFormat.format(date);

		// Generate the train list
		for (int i = 0; i < numTrains; i++) {
			// Get a random train name
			int trainIndex = rand.nextInt(trains.length);
			String trainName = trains[trainIndex];

			// Get a random departure time between 00:00 and 23:59
			int hour = rand.nextInt(24);
			int minute = rand.nextInt(60);
			String departureTime = String.format("%02d:%02d", hour, minute);

			// Calculate the arrival time based on the distance between the stations and the
			// average train speed
			int distance = Math.abs(getStationIndex(from) - getStationIndex(to)) * 100;
			int avgSpeed = rand.nextInt(60) + 40; // average speed in km/h
			int travelTime = distance / avgSpeed; // travel time in hours
			int arrivalHour = (hour + travelTime) % 24;
			int arrivalMinute = minute + (travelTime % 1) * 60;
			if (arrivalMinute >= 60) {
				arrivalHour++;
				arrivalMinute -= 60;
			}
			String arrivalTime = String.format("%02d:%02d", arrivalHour, arrivalMinute);

			// Add the train to the list
			String[] train = { trainName, departureTime, arrivalTime };
			trainList.add(train);
		}
		
		return trainList;
	}

	private int getStationIndex(String stationName) {
		for (int i = 0; i < stations.length; i++) {
			if (stations[i].equals(stationName)) {
				return i;
			}
		}
		return -1;
	}
}
