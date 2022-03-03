package dto;

import java.awt.Color;
import java.io.Serializable;

public class Daire implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer cap;
	private Color renk;

	private static Daire instance = null;

	public static Daire getInstance() {
		if (instance == null)
			instance = new Daire();
		return instance;
	}

	public Daire() {
		// TODO Auto-generated constructor stub
	}

	public Daire(Integer yariCap, Color renk) {
		this.cap = yariCap;
		this.renk = renk;
	}

	public Integer getYariCap() {
		return cap;
	}

	public void setYariCap(Integer yariCap) {
		this.cap = yariCap;
	}

	public Color getRenk() {
		return renk;
	}

	public void setRenk(Color renk) {
		this.renk = renk;
	}

	@Override
	public String toString() {
		return "Daire [cap=" + cap + ", renk=" + renk + "]";
	}

}
