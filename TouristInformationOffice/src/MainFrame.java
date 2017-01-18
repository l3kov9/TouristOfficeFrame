import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainFrame extends JFrame{

	private int selectedType = 0; // 0-no type,1-Tourist,2-Hotel,3-Ticket
	private JTable tableTourist = new JTable();
	private JTable tableHotel=new JTable();
	private JTable tableTicket=new JTable();
	private JScrollPane scrollerTourist = new JScrollPane(tableTourist);
	private JScrollPane scrollerHotel=new JScrollPane(tableHotel);
	private JScrollPane scrollerTicket=new JScrollPane(tableTicket);

	private JPanel headerPanel = new JPanel();
	private JPanel midPanel = new JPanel();
	private JPanel footerPanel = new JPanel();
	private JPanel secondFooterPanel=new JPanel();

	private JLabel textLabel = new JLabel("     Input string");
	private JLabel intLabel = new JLabel("     Input int");
	private JLabel touristLabel=new JLabel("     Choose object");
	private JLabel objectTypeLabel = new JLabel("     Choose action");
	private JLabel touristIDLabel=new JLabel("");

	private JTextField touristTextField=new JTextField();
	private JTextField textField = new JTextField();
	private JTextField intField = new JTextField();
	private String[] comboValues = {"","Tourist","Hotel","Ticket"};
	private JComboBox<String> typeCombo = new JComboBox<>(comboValues);
	private JComboBox<String> touristCombo=new JComboBox<>();

	private JButton addButton = new JButton("Add");
	private JButton refreshButton = new JButton("Refresh");
	private JButton deleteButton=new JButton("Delete");
	private JButton clearAllTablesButton=new JButton("Clear all");


	public MainFrame(){
		init();
		this.pack();
	}


	private void init(){
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(500, 600);
		this.setLayout(new GridLayout(4, 1));

		//add to Frame
		this.add(headerPanel);
		this.add(midPanel);
		this.add(footerPanel);
		this.add(secondFooterPanel);
		this.setTitle("Tourist Information Office");

		//headerPanel
		headerPanel.setBackground(Color.lightGray);
		headerPanel.setLayout(new GridLayout(5, 2));
		headerPanel.add(textLabel);
		headerPanel.add(textField);
		headerPanel.add(intLabel);
		headerPanel.add(intField);
		headerPanel.add(touristIDLabel);
		touristIDLabel.setVisible(false);
		headerPanel.add(touristTextField);
		touristTextField.setVisible(false);
		headerPanel.add(touristLabel);
		headerPanel.add(touristCombo);
		headerPanel.add(objectTypeLabel);
		headerPanel.add(typeCombo);
		typeCombo.addActionListener(new ChangeLabel());

		//midPanel
		midPanel.setBackground(Color.lightGray);
		midPanel.add(addButton);
		addButton.setPreferredSize(new Dimension(110,25));
		midPanel.add(deleteButton);
		deleteButton.setPreferredSize(new Dimension(110,25));
		midPanel.add(refreshButton);
		refreshButton.setPreferredSize(new Dimension(90,25));
		midPanel.add(clearAllTablesButton);
		clearAllTablesButton.setPreferredSize(new Dimension(90,25));

		deleteButton.addActionListener(new DeleteAction());
		addButton.addActionListener(new AddAction());
		refreshButton.addActionListener(new RefreshAction());
		clearAllTablesButton.addActionListener(new ClearAllTablesAction());

		//footerPanel
		footerPanel.setBackground(Color.lightGray);
		scrollerTourist.setPreferredSize(new Dimension(430,130));
		footerPanel.add(scrollerTourist);

		//secondFooterPanel
		secondFooterPanel.setBackground(Color.lightGray);
		scrollerHotel.setPreferredSize(new Dimension(215,130));
		secondFooterPanel.add(scrollerHotel);
		scrollerTicket.setPreferredSize(new Dimension(215,130));
		secondFooterPanel.add(scrollerTicket);

		tableTourist.setModel(getAllTourist());
		tableHotel.setModel(getAllHotel());
		tableTicket.setModel(getAllTicket());
	}// end init()


	private void filltouristCombo(){
		Connection conn=DBCommunication.myConnection();
		PreparedStatement state=null;
		String sql="select TouristID, Name from Tourist";
		ResultSet result=null;
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			touristCombo.removeAllItems();
			while (result.next()){
				String id=result.getString("TouristID");
				String model=result.getString("Name");
				String comboString = id + ". " + model;
				touristCombo.addItem(comboString);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			try {
				assert result != null;
				result.close();
				assert state != null;
				state.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}//end Button TouristCombo

	private void fillHotelCombo(){
		Connection conn=DBCommunication.myConnection();
		PreparedStatement state=null;
		String sql="select TouristID, Hotel from Hotel";
		ResultSet result=null;
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			touristCombo.removeAllItems();
			while (result.next()){
				String id=result.getString("TouristID");
				String model=result.getString("Hotel");
				String comboString = id + ". " + model;
				touristCombo.addItem(comboString);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			try {
				assert result != null;
				result.close();
				assert state != null;
				state.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}//end Button Hotel Combo

	private void fillTicketCombo(){
		Connection conn=DBCommunication.myConnection();
		PreparedStatement state=null;
		String sql="select TouristID, Ticket from Ticket";
		ResultSet result=null;
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			touristCombo.removeAllItems();
			while (result.next()){
				String id=result.getString("TouristID");
				String model=result.getString("Ticket");
				String comboString = id + ". " + model;
				touristCombo.addItem(comboString);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			try {
				assert result != null;
				result.close();
				assert state != null;
				state.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}//end Button Ticket Combo


	public MyModel getAllTicket(){
		Connection conn = DBCommunication.myConnection();
		ResultSet result = null;
		MyModel model = null;
		PreparedStatement state = null;
		try {
			state = conn.prepareStatement("select * from Ticket");
			result = state.executeQuery();
			model = new MyModel(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}// end allTickets

	public MyModel getAllHotel(){
		Connection conn = DBCommunication.myConnection();
		ResultSet result = null;
		MyModel model = null;
		PreparedStatement state = null;
		try {
			state = conn.prepareStatement("select * from Hotel");
			result = state.executeQuery();
			model = new MyModel(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}// end allHotels

	public MyModel getAllTourist(){
		Connection conn = DBCommunication.myConnection();
		ResultSet result = null;
		MyModel model = null;
		PreparedStatement state = null;
		try {
			state = conn.prepareStatement("select * from Tourist");
			result = state.executeQuery();
			model = new MyModel(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}// end allTourists



	public String getTouristNameFromCombo(){
		String selection = (String)touristCombo.getSelectedItem();
		int index=selection.indexOf('.');
		String model=selection.substring(index+2);
		filltouristCombo();
		return model;
	}//end getTourist Name

	public int getTouristIDFromCombo(){
		String selection = (String)touristCombo.getSelectedItem();
		int index=selection.indexOf('.');
		String model=selection.substring(0,index);
		//filltouristCombo();
		return Integer.parseInt(model);
	}// end getTourist ID



	class DeleteAction implements ActionListener{
		public void actionPerformed(ActionEvent e){

			switch(selectedType) {

				case 1:
					try {
						Connection conn = DBCommunication.myConnection();
						PreparedStatement state;
						String sql = "delete from Hotel where TouristID=?";
						state = conn.prepareStatement(sql);
						state.setInt(1, getTouristIDFromCombo());
						state.execute();
						tableHotel.setModel(getAllHotel());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					try {
						Connection conn = DBCommunication.myConnection();
						PreparedStatement state = null;
						String sql = "delete from Ticket WHERE TouristID=?";
						state = conn.prepareStatement(sql);
						state.setInt(1, getTouristIDFromCombo());
						state.execute();
						tableTicket.setModel(getAllTicket());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					try {
						Connection conn = DBCommunication.myConnection();
						PreparedStatement state = null;
						String sql = "delete from Tourist where TouristID =?";
						state = conn.prepareStatement(sql);
						state.setInt(1, getTouristIDFromCombo());
						state.execute();
						tableTourist.setModel(getAllTourist());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					tableTourist.setModel(getAllTourist());
					tableHotel.setModel(getAllHotel());
					tableTicket.setModel(getAllTicket());
					filltouristCombo();
					break;

				case 2:
					try {
						Connection conn = DBCommunication.myConnection();
						PreparedStatement state;
						String sql = "delete from Hotel where TouristID=?";
						state = conn.prepareStatement(sql);
						state.setInt(1, getTouristIDFromCombo());
						state.execute();
						tableHotel.setModel(getAllHotel());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					tableTourist.setModel(getAllTourist());
					tableHotel.setModel(getAllHotel());
					tableTicket.setModel(getAllTicket());
					fillHotelCombo();
					break;
				case 3:
					try {
						Connection conn = DBCommunication.myConnection();
						PreparedStatement state = null;
						String sql = "delete from Ticket WHERE TouristID=?";
						state = conn.prepareStatement(sql);
						state.setInt(1, getTouristIDFromCombo());
						state.execute();
						tableTicket.setModel(getAllTicket());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					tableTourist.setModel(getAllTourist());
					tableHotel.setModel(getAllHotel());
					tableTicket.setModel(getAllTicket());
					fillTicketCombo();
					break;
			}

		}//end method
	}// end DeleteAction

	class RefreshAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			tableTourist.setModel(getAllTourist());
			tableHotel.setModel(getAllHotel());
			tableTicket.setModel(getAllTicket());

		}// end method

	}// end RefreshAction

	class ClearAllTablesAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			Connection conn = DBCommunication.myConnection();
			PreparedStatement state = null;
			String sql = "delete from Hotel where TouristID>0";
			try {
				state = conn.prepareStatement(sql);
				state.execute();
			} catch (SQLException ea) {
				ea.printStackTrace();
			}finally {
				try{
					state.close();
					conn.close();
					clearFields();
				}
				catch(SQLException ea){
					ea.printStackTrace();
				}
			}
			conn = DBCommunication.myConnection();
			state = null;
			sql = "delete from Ticket where TouristID>0";
			try {
				state = conn.prepareStatement(sql);
				state.execute();
			} catch (SQLException ea) {
				ea.printStackTrace();
			}finally {
				try{
					state.close();
					conn.close();
					clearFields();
				}
				catch(SQLException ea){
					ea.printStackTrace();
				}
			}
			conn = DBCommunication.myConnection();
			state = null;
			sql = "delete from Tourist where TouristID>0";
			try {
				state = conn.prepareStatement(sql);
				state.execute();
			} catch (SQLException ea) {
				ea.printStackTrace();
			}finally {
				try{
					state.close();
					conn.close();
					clearFields();
				}
				catch(SQLException ea){
					ea.printStackTrace();
				}
			}
			filltouristCombo();
			tableTourist.setModel(getAllTourist());
			tableHotel.setModel(getAllHotel());
			tableTicket.setModel(getAllTicket());
			clearFields();

		}
	}//end ClearAllTables

	class ChangeLabel implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String selectedItem = (String)typeCombo.getSelectedItem();
			//	String[] comboValues = {"","Tourist","Hotel","Ticket"};
			switch(selectedItem){
				case "Tourist" : intLabel.setText("     Age");
					textLabel.setText("     Full Name");
					addButton.setText("Add tourist");
					deleteButton.setText("Delete tourist");
					touristLabel.setText("     Tourist ID");
					touristIDLabel.setVisible(false);
					touristTextField.setVisible(false);
					filltouristCombo();
					selectedType = 1;
					break;
				case "Hotel": intLabel.setText("     Price per night");
					textLabel.setText("     Hotel name");
					addButton.setText("Add hotel");
					deleteButton.setText("Delete hotel");
					touristIDLabel.setVisible(true);
					touristIDLabel.setText("     Input tourist ID");
					touristTextField.setVisible(true);
					touristLabel.setText("     Hotel ID");
					fillHotelCombo();
					selectedType = 2;
					break;
				case "Ticket": intLabel.setText("     Ticket price");
					textLabel.setText("     Destination");
					addButton.setText("Add ticket");
					deleteButton.setText("Delete ticket");
					touristIDLabel.setVisible(true);
					touristIDLabel.setText("     Input tourist ID");
					touristTextField.setVisible(true);
					touristLabel.setText("     Ticket ID");
					fillTicketCombo();
					selectedType = 3;
					break;
				case "": intLabel.setText("     Input int");
					textLabel.setText("     Input string");
					addButton.setText("Add");
					deleteButton.setText("Delete");
					touristCombo.removeAllItems();
					touristIDLabel.setVisible(false);
					touristTextField.setVisible(false);
					touristLabel.setText("     Choose object");
					selectedType = 0;
					break;
			}// end switch
		}// end method

	}// end ChangeLabel

	class AddAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {

			String inputString = textField.getText();
			int inputInt = Integer.parseInt(intField.getText());
			switch(selectedType){
				case 1  : Connection conn = DBCommunication.myConnection();
					PreparedStatement state = null;
					String sql = "insert into Tourist values(null,?,?)";
					try {
						state = conn.prepareStatement(sql);
						state.setString(1, inputString);
						state.setInt(2,  inputInt);
						state.execute();
					} catch (SQLException e) {
						e.printStackTrace();
					}finally {
						try{
							state.close();
							conn.close();
							clearFields();
							filltouristCombo();
							break;
						}
						catch(SQLException e){
							e.printStackTrace();
						}
					}

					clearFields();
					filltouristCombo();
					break;
				case 2 : conn = DBCommunication.myConnection();
					state = null;
					sql = "insert into Hotel values(?,?,?)";
					int inputID=Integer.parseInt(touristTextField.getText());

					try {
						state = conn.prepareStatement(sql);
						state.setInt(1,inputID);
						state.setString(2, inputString);
						state.setInt(3,  inputInt);
						state.execute();

					} catch (SQLException e) {
						e.printStackTrace();
					}finally {
						try{
							state.close();
							conn.close();
							clearFields();
							fillHotelCombo();
							break;
						}
						catch(SQLException e){
							e.printStackTrace();
						}
					}

					clearFields();
					fillHotelCombo();
					break;
				case 3 : conn = DBCommunication.myConnection();
					state = null;
					sql = "insert into Ticket values(?,?,?)";
					int inputIDticket=Integer.parseInt(touristTextField.getText());

					try {
						state = conn.prepareStatement(sql);
						state.setInt(1,inputIDticket);
						state.setString(2, inputString);
						state.setInt(3,  inputInt);
						state.execute();
					} catch (SQLException e) {
						e.printStackTrace();
					}finally {
						try{
							state.close();
							conn.close();
							clearFields();
							fillTicketCombo();
							break;
						}
						catch(SQLException e){
							e.printStackTrace();
						}
					}

					clearFields();
					fillTicketCombo();
					break;
				case 0 : new JOptionPane().showMessageDialog(null, "Choose action","No type",JOptionPane.ERROR_MESSAGE);
					break;
			}
			tableTourist.setModel(getAllTourist());
			tableHotel.setModel(getAllHotel());
			tableTicket.setModel(getAllTicket());
		}// end method

	}// end AddAction

	private void clearFields(){
		intField.setText("");
		textField.setText("");
		touristTextField.setText("");
	}//end clearFields

}// end class
