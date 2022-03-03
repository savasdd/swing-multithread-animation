
package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import custom.EnumUtil;

public class Client extends JFrame {

	private JPanel contentPane;
	private DefaultListModel model;
	private JTextArea msgBox;
	private JScrollPane scrollPane;

	private String userId;
	private static Socket con;
	DataInputStream input;
	DataOutputStream output;

	public Client(String user, Socket socket) {
		try {
			init(user);
			userId = user;
			model = new DefaultListModel();
			con = socket;

			input = new DataInputStream(con.getInputStream());
			output = new DataOutputStream(con.getOutputStream());
			new Read().start();

		} catch (Exception e) {
			e.printStackTrace();
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					if (output != null)
						output.writeUTF(EnumUtil.DISCONNECT);
					dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	class Read extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					String data = input.readUTF();
					if (data.contains(":;.,/=")) {
						data = data.substring(6);
						model.clear();
						StringTokenizer token = new StringTokenizer(data, ",");

						while (token.hasMoreTokens()) {
							String user = token.nextToken();
							if (!userId.equalsIgnoreCase(user))
								model.addElement(user);
						}

					} else {
						// data akýþý
						msgBox.append("" + data + "\n");
					}

				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}

		}

	}

	public void init(String user) {
		setTitle(user);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setBackground(UIManager.getColor("MenuBar.highlight"));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 22, 616, 331);
		contentPane.add(scrollPane);

		msgBox = new JTextArea();
		scrollPane.setViewportView(msgBox);
		msgBox.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
		msgBox.setForeground(new Color(255, 255, 255));
		msgBox.setBackground(new Color(0, 128, 128));
	}

}
