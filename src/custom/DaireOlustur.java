package custom;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import dto.Daire;

public class DaireOlustur extends Thread implements Sekil {
	private Integer xKord;
	private Integer yKord;
	private Integer dikey;
	private CustomPanel panel;
	private Daire daire;
	private Socket socket;

	@Override
	public void sekilOlustur(CustomPanel pnl, Socket con) {
		panel = pnl;
		socket = con;
		setxKord((int) (Math.random() * panel.getWidth()));
		setyKord((int) (Math.random() * panel.getHeight()));
		setDikey(1);
	}

	public void run() {
		while (true) {
			if (getyKord() <= 0)
				setDikey(1);
			if (getyKord() + 30 > panel.getHeight())
				setDikey(-1);

			setyKord(getyKord() + getDikey());
			panel.repaint();
			try {
				Thread.sleep(14);
				try {
					if (socket != null) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("x_kord", getxKord());
						map.put("y_kord", getyKord());
						map.put("r_cap", getDaire().getYariCap());

						DataOutputStream output = new DataOutputStream(socket.getOutputStream());
						output.writeUTF(EnumUtil.DAIRE + " :" + map.toString());
						// output.flush();
					}
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setxKord(Integer xKord) {
		this.xKord = xKord;
	}

	public Integer getxKord() {
		return xKord;
	}

	public void setyKord(Integer yKord) {
		this.yKord = yKord;
	}

	public Integer getyKord() {
		return yKord;
	}

	public void setDikey(Integer dikey) {
		this.dikey = dikey;
	}

	public Integer getDikey() {
		return dikey;
	}

	public void setDaire(Daire daire) {
		this.daire = daire;
	}

	public Daire getDaire() {
		return daire;
	}

}
