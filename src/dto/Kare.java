package dto;

import java.awt.Color;
import java.io.Serializable;

public class Kare implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer kenar;
	private Color renk;

	private static Kare instance = null;

	public static Kare getInstance() {
		if (instance == null)
			instance = new Kare();
		return instance;
	}

	public Kare() {
		// TODO Auto-generated constructor stub
	}

	public Kare(Integer kenar, Color renk) {
		super();
		this.kenar = kenar;
		this.renk = renk;
	}

	public Integer getKenar() {
		return kenar;
	}

	public void setKenar(Integer kenar) {
		this.kenar = kenar;
	}

	public Color getRenk() {
		return renk;
	}

	public void setRenk(Color renk) {
		this.renk = renk;
	}

	@Override
	public String toString() {
		return "Kare [kenar=" + kenar + ", renk=" + renk + "]";
	}

}
