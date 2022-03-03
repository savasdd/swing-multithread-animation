
package client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import custom.EnumUtil;
import dto.User;

public class ClientStart extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	private JTextField userip;
	private JTextField userport;

	private static Socket con;
	DataInputStream input;
	DataOutputStream output;
	String userId;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientStart frame = new ClientStart();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ClientStart() {

		setTitle("Client Connect");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 266);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		username = new JTextField();
		username.setBounds(97, 51, 188, 28);
		contentPane.add(username);
		username.setColumns(10);

		JLabel lblNewLabel = new JLabel("Kullan\u0131c\u0131 Ad\u0131:");
		lblNewLabel.setBounds(10, 51, 77, 28);
		contentPane.add(lblNewLabel);

		JButton btnConnect = new JButton("Ba\u011Flan");
		btnConnect.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnConnect.setForeground(new Color(0, 0, 255));
		btnConnect.setBounds(97, 183, 85, 28);
		contentPane.add(btnConnect);

		JLabel lblNewLabel_1 = new JLabel("Kullan\u0131c\u0131 Giri\u015F Formu");
		lblNewLabel_1.setBackground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 10, 357, 31);
		contentPane.add(lblNewLabel_1);

		userip = new JTextField();
		userip.setColumns(10);
		userip.setBounds(97, 92, 188, 28);
		contentPane.add(userip);

		JLabel lblIp = new JLabel("Host IP:");
		lblIp.setBounds(10, 92, 77, 28);
		contentPane.add(lblIp);

		userport = new JTextField();
		userport.setColumns(10);
		userport.setBounds(97, 133, 188, 28);
		contentPane.add(userport);

		JLabel lblServerPort = new JLabel("Port:");
		lblServerPort.setBounds(10, 133, 77, 28);
		contentPane.add(lblServerPort);

		// **
		userip.setText("127.0.0.1");

		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (userport.getText().equals(""))
						JOptionPane.showMessageDialog(null, "Sunucu Portu Girin!");
					else {

						String user = username.getText();
						String ip = userip.getText();
						String port = userport.getText();
						userId = user;

						User dto = User.getInstance();
						dto.setUserName(user);
						dto.setServerIp(ip);
						dto.setServerPort(port);

						con = new Socket(ip, Integer.valueOf(port));
						OutputStream outputStream = con.getOutputStream();
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
						objectOutputStream.writeObject(dto);

						String data = new DataInputStream(con.getInputStream()).readUTF();
						input = new DataInputStream(con.getInputStream());
						output = new DataOutputStream(con.getOutputStream());

						if (data.equals(EnumUtil.OK))
							System.err.println("User Conneccted!");
						else {

							Client client = new Client(user, con);
							client.setVisible(true);
							dispose();
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
	}

}
