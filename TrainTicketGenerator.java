import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TrainTicketGenerator extends JFrame {
	JTable trainTable;

	public TrainTicketGenerator(String name, String age, boolean male, boolean female, JTable trainTable) {
		this.trainTable = trainTable;
		setTitle("Train Ticket");
		setSize(500, 250);
		setLocationRelativeTo(null);

		String gender = male ? "Male" : "Female";

		String[] columnNames = { "Field", "Value" };
		Object[][] data = {
				{ "Name", name },
				{ "Age", age },
				{ "Gender", gender },
				{ "Train Name", getTrainName() },
				{ "Date", getDate() },
				{ "Arrival Time", getArrivalTime() },
				{ "Departure Time", getDepartureTime() },
				{ "Class", getRandomClass() },
				{ "Fare", getRandomFare() }
		};

		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		JTable table = new JTable(model);
		table.setEnabled(false);

		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(panel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private String getUsername() {
		// return "User" + (int) (Math.random() * 100);
		return trainTable.getValueAt(trainTable.getSelectedRow(), 0).toString();
	}

	private String getTrainName() {
		String[] trainNames = { "Shatabdi Express", "Rajdhani Express", "Duronto Express", "Garib Rath Express" };
		return trainTable.getValueAt(trainTable.getSelectedRow(), 0).toString();
	}

	private String getDate() {
		return (int) (Math.random() * 31) + "/" + (int) (Math.random() * 12) + "/2023";
	}

	private String getArrivalTime() {
		return trainTable.getValueAt(trainTable.getSelectedRow(), 1).toString();
	}

	private String getDepartureTime() {
		return trainTable.getValueAt(trainTable.getSelectedRow(), 2).toString();
	}

	private String getRandomClass() {
		String[] classes = { "AC First Class", "AC 2 Tier", "AC 3 Tier", "Sleeper Class" };
		return classes[(int) (Math.random() * classes.length)];
	}

	private String getRandomFare() {
		return "Rs. " + (int) (Math.random() * 1000);
	}
}
