package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import custom.CustomPanel;
import custom.DaireOlustur;
import custom.DikdortgenOlustur;
import custom.EnumUtil;
import custom.KareOlustur;
import custom.SekilFactory;
import dto.Daire;
import dto.Dikdortgen;
import dto.Kare;
import dto.User;

public class Server extends JFrame {

	private JPanel contentPane;
	private JDialog d;

	private CustomPanel mp;
	private ArrayList<DaireOlustur> dList;
	private ArrayList<KareOlustur> kList;
	private ArrayList<DikdortgenOlustur> dikList;
	private Map<String, Object> uList = new HashMap<>();
	private Daire daire;
	private Kare kare;
	private Dikdortgen dikdortgen;

	static ServerSocket server;
	static Socket con;

	public Server(int port) {
		try {
			init(port);
			server = new ServerSocket(port);
			new AcceptThread().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(int port) {
		setTitle("SERVER STARTED ON: " + port);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JButton btnNewButton = new JButton("Ekle");
		contentPane.add(btnNewButton, BorderLayout.SOUTH);

		dList = new ArrayList<>();
		kList = new ArrayList<>();
		dikList = new ArrayList<>();
		mp = new CustomPanel();
		mp.setDaireList(dList);
		mp.setKareList(kList);
		mp.setDikList(dikList);
		getContentPane().add(mp, "Center");

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createModel();
			}
		});

	}

	public void createModel() {
		JFrame f = new JFrame();
		d = new JDialog(f, "Þekil Ekleme Formu", true);
		d.setLayout(null);

		JLabel lblNewLabel = new JLabel("\u015Eekil");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 5, 95, 25);
		d.add(lblNewLabel);

		JLabel kisaKenarLabel = new JLabel("1");
		kisaKenarLabel.setToolTipText("");
		kisaKenarLabel.setVisible(false);
		kisaKenarLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		kisaKenarLabel.setBounds(10, 80, 95, 25);
		d.add(kisaKenarLabel);

		JLabel lblRenk = new JLabel("Renk");
		lblRenk.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRenk.setBounds(10, 40, 95, 25);
		d.add(lblRenk);

		JComboBox renkCombo = new JComboBox(
				new Object[] { "", "Siyah", "Kýrmýzý", "Pembe", "Turuncu", "Sarý", "Mavi", "Gri" });
		renkCombo.setBounds(95, 40, 176, 25);
		d.add(renkCombo);

		JTextField kisaKenarText = new JTextField();
		kisaKenarText.setBounds(95, 80, 176, 25);
		kisaKenarText.setVisible(false);
		kisaKenarText.setColumns(10);
		d.add(kisaKenarText);

		JLabel uzunKenarLabel = new JLabel("2");
		uzunKenarLabel.setToolTipText("");
		uzunKenarLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		uzunKenarLabel.setBounds(10, 120, 95, 25);
		uzunKenarLabel.setVisible(false);
		d.add(uzunKenarLabel);

		JTextField uzunKenarText = new JTextField();
		uzunKenarText.setColumns(10);
		uzunKenarText.setBounds(95, 120, 176, 25);
		uzunKenarText.setVisible(false);
		d.add(uzunKenarText);

		JComboBox sekilCombo = new JComboBox(new Object[] { "", "Kare", "Dikdörtgen", "Daire" });
		sekilCombo.setBounds(95, 5, 176, 25);
		sekilCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sekilCombo.getSelectedIndex() == 1) {
					kisaKenarLabel.setVisible(true);
					uzunKenarLabel.setVisible(false);
					kisaKenarText.setVisible(true);
					uzunKenarText.setVisible(false);
					kisaKenarLabel.setText("Kenar");
					uzunKenarText.setText(null);
					kisaKenarText.setText(null);
				} else if (sekilCombo.getSelectedIndex() == 2) {
					kisaKenarLabel.setVisible(true);
					uzunKenarLabel.setVisible(true);
					kisaKenarText.setVisible(true);
					uzunKenarText.setVisible(true);
					kisaKenarLabel.setText("Kýsa Kenar");
					uzunKenarLabel.setText("Uzun Kenar");
					uzunKenarText.setText(null);
					kisaKenarText.setText(null);
				} else if (sekilCombo.getSelectedIndex() == 3) {
					kisaKenarLabel.setVisible(true);
					kisaKenarText.setVisible(true);
					kisaKenarLabel.setText("Yarý Çap");
					uzunKenarLabel.setVisible(false);
					uzunKenarText.setVisible(false);
					uzunKenarText.setText(null);
					kisaKenarText.setText(null);
				} else {
					uzunKenarLabel.setVisible(false);
					kisaKenarLabel.setVisible(false);
					kisaKenarText.setVisible(false);
					uzunKenarText.setVisible(false);
					uzunKenarText.setText(null);
					kisaKenarText.setText(null);
				}

			}
		});
		d.add(sekilCombo);

		JButton b = new JButton("EKLE");
		b.setBounds(95, 150, 100, 25);
		b.setForeground(Color.BLUE);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (sekilCombo.getSelectedIndex() == 0)
					JOptionPane.showMessageDialog(null, "Þekil Seçin!");
				else {
					if (sekilCombo.getSelectedIndex() == 1) {
						kare = new Kare(Integer.valueOf(kisaKenarText.getText()),
								getRenk(renkCombo.getSelectedIndex()));
						draw(EnumUtil.KARE);

					} else if (sekilCombo.getSelectedIndex() == 2) {
						dikdortgen = new Dikdortgen(Integer.valueOf(kisaKenarText.getText()),
								Integer.valueOf(uzunKenarText.getText()), getRenk(renkCombo.getSelectedIndex()));
						draw(EnumUtil.DIKDORTGEN);

					} else if (sekilCombo.getSelectedIndex() == 3) {
						daire = new Daire(Integer.valueOf(kisaKenarText.getText()),
								getRenk(renkCombo.getSelectedIndex()));
						draw(EnumUtil.DAIRE);
					}
				}
			}
		});
		d.setBounds(500, 100, 300, 230);
		d.add(b);
		d.setVisible(true);
	}

	public void draw(String type) {
		if (type.endsWith(EnumUtil.DAIRE)) {
			SekilFactory factory = new SekilFactory();
			DaireOlustur sekil = (DaireOlustur) factory.getSekil(type);
			sekil.setDaire(daire);
			sekil.sekilOlustur(mp, con);
			dList.add(sekil);
			sekil.start();
		}

		if (type.endsWith(EnumUtil.KARE)) {
			SekilFactory factory = new SekilFactory();
			KareOlustur sekil = (KareOlustur) factory.getSekil(type);
			sekil.setKare(kare);
			sekil.sekilOlustur(mp, con);
			kList.add(sekil);
			sekil.start();
		}

		if (type.endsWith(EnumUtil.DIKDORTGEN)) {
			SekilFactory factory = new SekilFactory();
			DikdortgenOlustur sekil = (DikdortgenOlustur) factory.getSekil(type);
			sekil.setDikdortgen(dikdortgen);
			sekil.sekilOlustur(mp, con);
			dikList.add(sekil);
			sekil.start();
		}

	}

	public Color getRenk(int index) {
		if (index == 1)
			return Color.BLACK;
		else if (index == 2)
			return Color.RED;
		if (index == 3)
			return Color.PINK;
		if (index == 4)
			return Color.ORANGE;
		if (index == 5)
			return Color.YELLOW;
		if (index == 6)
			return Color.BLUE;
		if (index == 7)
			return Color.GRAY;

		return Color.BLACK;
	}

	// Multiple User Thread
	class AcceptThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					con = server.accept();
					InputStream inputStream = con.getInputStream();
					OutputStream outputStream = con.getOutputStream();

					ObjectInputStream is = new ObjectInputStream(inputStream);
					User dto = (User) is.readObject();

					if (uList.containsKey(dto.getUserName())) {
						DataOutputStream data = new DataOutputStream(outputStream);
						data.writeUTF(EnumUtil.OK);
					} else {
						uList.put(dto.getUserName(), con);
						System.out.println(dto.getUserName() + " connected");

						DataOutputStream output = new DataOutputStream(outputStream);
						output.writeUTF("");
						new ReadThread(dto.getUserName()).start();
						new PropThread().start();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	class ReadThread extends Thread {
		String user;

		public ReadThread(String data) {
			this.user = data;
		}

		@Override
		public void run() {

			while (!uList.isEmpty()) {
				try {
					if (con != null) {
						InputStream inputStream = con.getInputStream();
						String input = new DataInputStream(inputStream).readUTF();

						if (input.equals(EnumUtil.DISCONNECT)) {
							uList.remove(user);
							System.err.println(user + " disconnect\n");
							new PropThread().start();
							Set<String> k = uList.keySet();
							Iterator<String> iterator = k.iterator();

							while (iterator.hasNext()) {
								String key = iterator.next();

								if (!key.equalsIgnoreCase(user)) {
									try {
										new DataOutputStream(((Socket) uList.get(key)).getOutputStream()).writeUTF(key);
									} catch (Exception e) {
										uList.remove(key);
										System.out.println(user + " disconnect\n");
										new PropThread().start();
									}
								}

							}
						}
					}

				} catch (Exception e) {
					reConnect();
					e.printStackTrace();
				}
			}
		}
	}

	class PropThread extends Thread {
		@Override
		public void run() {
			try {
				String ids = "";
				Set<String> k = uList.keySet();
				Iterator<String> iterator = k.iterator();

				while (iterator.hasNext()) {
					String key = iterator.next();
					ids = ids + key + ",";

					if (ids.length() != 0)
						ids = ids.substring(0, ids.length() - 1);

					iterator = k.iterator();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void reConnect() {
		try {
			con = null;
			System.gc();
			con = server.accept();
			System.out.println("Connection established...");
		} catch (Exception e) {
			System.out.println("ReConnect not successful " + e.getMessage());
		}
	}
}
