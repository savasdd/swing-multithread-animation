package custom;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class CustomPanel extends JPanel {

	private ArrayList<DaireOlustur> daireList;
	private ArrayList<KareOlustur> kareList;
	private ArrayList<DikdortgenOlustur> dikList;

	public CustomPanel() {
		// TODO Auto-generated constructor stub
	}

	public void paint(Graphics g) {
		super.paint(g);

		if (getDaireList().size() > 0)
			for (DaireOlustur b : getDaireList()) {
				int r = b.getDaire() != null ? b.getDaire().getYariCap() : 0;
				g.setColor(b.getDaire() != null ? b.getDaire().getRenk() : Color.BLACK);
				g.fillOval(b.getxKord(), b.getyKord(), r, r);
			}

		if (getKareList().size() > 0)
			for (KareOlustur b : getKareList()) {
				int k = b.getKare() != null ? b.getKare().getKenar() : 0;
				g.setColor(b.getKare() != null ? b.getKare().getRenk() : Color.GRAY);
				g.fillRect(b.getxKord(), b.getyKord(), k, k);
			}

		if (getDikList().size() > 0)
			for (DikdortgenOlustur b : getDikList()) {
				int k = b.getDikdortgen() != null ? b.getDikdortgen().getKisa() : 0;
				int u = b.getDikdortgen() != null ? b.getDikdortgen().getUzun() : 0;
				g.setColor(b.getDikdortgen() != null ? b.getDikdortgen().getRenk() : Color.BLUE);
				g.fillRect(b.getxKord(), b.getyKord(), u, k);
			}
	}

	public void setDaireList(ArrayList<DaireOlustur> daireList) {
		this.daireList = daireList;
	}

	public ArrayList<DaireOlustur> getDaireList() {
		return daireList;
	}

	public void setKareList(ArrayList<KareOlustur> kareList) {
		this.kareList = kareList;
	}

	public ArrayList<KareOlustur> getKareList() {
		return kareList;
	}

	public void setDikList(ArrayList<DikdortgenOlustur> dikList) {
		this.dikList = dikList;
	}

	public ArrayList<DikdortgenOlustur> getDikList() {
		return dikList;
	}

}
