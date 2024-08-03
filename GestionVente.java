package monPackage;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;


public class GestionVente {

	private JFrame frame;
	private JTextField textFieldProduit;
	private JTextField textFieldVendeur;
	private JTextField textFieldClient;
	private JTextField textFieldPrix;
	private JTextField textFieldTotal;
	private JTable table;
	private JSpinner spinnerQuantite = new JSpinner();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionVente window = new GestionVente();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	/**
	 * Create the application.
	 */
	public GestionVente() {
		initialize();
		Table();
	}
	
	public void Connexion() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/GESTIONPHARMACIE", "root", "djabar00");
		} catch (Exception e) {
            System.out.println("Connection failed!");
            e.printStackTrace();		}
	}
	
	private void Table() {
		try {
			Connexion();
			String[] entete = {"Code", "Produit", "Client", "Vendeur", "Prix", "Quantité", "Total"};
			String[] ligne = new String[8];
			
			DefaultTableModel tableModel = new DefaultTableModel(null, entete);
			
			Statement st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM VENTE");
			while(rs.next()){
				ligne[0] = rs.getString("CODE_VENTE");
				ligne[1] = rs.getString("NOM_PRODUIT");
				ligne[2] = rs.getString("NOM_CLIENT");
				ligne[3] = rs.getString("NOM_VENDEUR");
				ligne[4] = rs.getString("PRIX_VENTE");
				ligne[5] = rs.getString("QUANTITE");
				ligne[6] = rs.getString("TOTAL");
				tableModel.addRow(ligne);
			}			
			table.setModel(tableModel);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 775, 660);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel.setBackground(SystemColor.desktop);
		panel.setBounds(12, 26, 751, 113);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblGestionVentePharmacie = new JLabel("Gestion Vente Pharmacie");
		lblGestionVentePharmacie.setFont(new Font("Dialog", Font.BOLD, 45));
		lblGestionVentePharmacie.setForeground(Color.WHITE);
		lblGestionVentePharmacie.setHorizontalAlignment(SwingConstants.CENTER);
		lblGestionVentePharmacie.setBounds(12, 12, 727, 89);
		panel.add(lblGestionVentePharmacie);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_1.setBackground(SystemColor.desktop);
		panel_1.setBounds(12, 167, 751, 266);
		frame.getContentPane().add(panel_1);
		
		JLabel lblNom = new JLabel("Produit");
		lblNom.setFont(new Font("Dialog", Font.BOLD, 17));
		lblNom.setForeground(Color.WHITE);
		lblNom.setBounds(12, 22, 80, 20);
		panel_1.add(lblNom);
		
		JLabel lblVendeur = new JLabel("Vendeur");
		lblVendeur.setForeground(Color.WHITE);
		lblVendeur.setFont(new Font("Dialog", Font.BOLD, 17));
		lblVendeur.setBounds(12, 82, 80, 20);
		panel_1.add(lblVendeur);
		
		JLabel lblClient = new JLabel("Client");
		lblClient.setForeground(Color.WHITE);
		lblClient.setFont(new Font("Dialog", Font.BOLD, 17));
		lblClient.setBounds(12, 142, 80, 20);
		panel_1.add(lblClient);
		
		JLabel lblPrix = new JLabel("Prix");
		lblPrix.setForeground(Color.WHITE);
		lblPrix.setFont(new Font("Dialog", Font.BOLD, 17));
		lblPrix.setBounds(421, 22, 80, 20);
		panel_1.add(lblPrix);
		
		JLabel lblQuantit = new JLabel("Quantité");
		lblQuantit.setForeground(Color.WHITE);
		lblQuantit.setFont(new Font("Dialog", Font.BOLD, 17));
		lblQuantit.setBounds(421, 86, 80, 20);
		panel_1.add(lblQuantit);
		
		JLabel lblTotal = new JLabel("Total");
		lblTotal.setForeground(Color.WHITE);
		lblTotal.setFont(new Font("Dialog", Font.BOLD, 17));
		lblTotal.setBounds(421, 146, 80, 20);
		panel_1.add(lblTotal);
		
		JButton btnEnregistrer = new JButton("Enregistrer");
		btnEnregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connexion();
				if (textFieldProduit.getText().trim().isEmpty() || textFieldClient.getText().trim().isEmpty() || textFieldVendeur.getText().trim().isEmpty() || textFieldPrix.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Veuillez entrez toutes les informations");
				}
				else {
					try {
						pst = con.prepareStatement("INSERT INTO VENTE (NOM_PRODUIT, NOM_CLIENT, NOM_VENDEUR, PRIX_VENTE, QUANTITE, TOTAL) VALUES (?, ?, ?, ?, ?, ?)");
						pst.setString(1, textFieldProduit.getText());
						pst.setString(2, textFieldClient.getText());
						pst.setString(3, textFieldVendeur.getText());
						pst.setString(4, textFieldPrix.getText());
						pst.setString(5, spinnerQuantite.getValue().toString());
						pst.setString(6, textFieldTotal.getText());
						pst.executeUpdate();
						con.close();
						JOptionPane.showMessageDialog(null, "Vente ENREGISTRÉE");
						textFieldClient.setText("");
						textFieldPrix.setText("");
						textFieldTotal.setText("");
						textFieldProduit.setText("");
						textFieldVendeur.setText("");
						Table();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnEnregistrer.setBounds(519, 200, 206, 38);
		panel_1.add(btnEnregistrer);
		
		JButton btnRinitialiser = new JButton("Réinitialiser");
		btnRinitialiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldClient.setText("");
				textFieldPrix.setText("");
				textFieldTotal.setText("");
				textFieldProduit.setText("");
				textFieldVendeur.setText("");
			}
		});
		btnRinitialiser.setBounds(115, 200, 206, 38);
		panel_1.add(btnRinitialiser);
		
		textFieldProduit = new JTextField();
		textFieldProduit.setFont(new Font("Dialog", Font.BOLD, 12));
		textFieldProduit.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProduit.setBounds(115, 20, 206, 27);
		panel_1.add(textFieldProduit);
		textFieldProduit.setColumns(10);
		
		textFieldVendeur = new JTextField();
		textFieldVendeur.setFont(new Font("Dialog", Font.BOLD, 12));
		textFieldVendeur.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldVendeur.setColumns(10);
		textFieldVendeur.setBounds(115, 84, 206, 27);
		panel_1.add(textFieldVendeur);
		
		textFieldClient = new JTextField();
		textFieldClient.setFont(new Font("Dialog", Font.BOLD, 12));
		textFieldClient.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldClient.setColumns(10);
		textFieldClient.setBounds(115, 140, 206, 27);
		panel_1.add(textFieldClient);
		
		textFieldPrix = new JTextField();
		textFieldPrix.setFont(new Font("Dialog", Font.BOLD, 12));
		textFieldPrix.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldPrix.setColumns(10);
		textFieldPrix.setBounds(519, 20, 206, 27);
		panel_1.add(textFieldPrix);
		
		textFieldTotal = new JTextField();
		textFieldTotal.setFont(new Font("Dialog", Font.BOLD, 12));
		textFieldTotal.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldTotal.setEditable(false);
		textFieldTotal.setColumns(10);
		textFieldTotal.setBounds(519, 144, 206, 27);
		panel_1.add(textFieldTotal);
		
		spinnerQuantite.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				float prix = Float.parseFloat(textFieldPrix.getText());
				int quantite = Integer.parseInt(spinnerQuantite.getValue().toString());
				float total = prix * quantite;
				textFieldTotal.setText(String.valueOf(total));
			}
		});
		spinnerQuantite.setBounds(519, 80, 206, 27);
		panel_1.add(spinnerQuantite);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBounds(12, 447, 751, 201);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 751, 201);
		panel_2.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Dialog", Font.BOLD, 12));
		table.setEnabled(false);
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
	}
}
