package custom;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import dto.Kare;

public class KareOlustur extends Thread implements Sekil {

	private Integer xKord;
	private Integer yKord;
	private Integer dikey;
	private CustomPanel panel;
	private Kare kare;
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
				Thread.sleep(12);
				try {
					if (socket != null) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("x_kord", getxKord());
						map.put("y_kord", getyKord());
						map.put("kenar", getKare().getKenar());

						DataOutputStream output = new DataOutputStream(socket.getOutputStream());
						output.writeUTF(EnumUtil.KARE + " :" + map.toString());
					}
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			} catch (Exception e) {
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

	public void setKare(Kare kare) {
		this.kare = kare;
	}

	public Kare getKare() {
		return kare;
	}

}
