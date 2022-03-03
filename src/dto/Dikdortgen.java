package dto;

import java.awt.Color;
import java.io.Serializable;

public class Dikdortgen implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer kisa;
	private Integer uzun;
	private Color renk;

	private static Dikdortgen instance = null;

	public static Dikdortgen getInstance() {
		if (instance == null)
			instance = new Dikdortgen();
		return instance;
	}

	public Dikdortgen() {
		// TODO Auto-generated constructor stub
	}

	public Dikdortgen(Integer kisaKenar, Integer uzunKenar, Color renk) {
		super();
		this.kisa = kisaKenar;
		this.uzun = uzunKenar;
		this.renk = renk;
	}

	public Integer getKisa() {
		return kisa;
	}

	public void setKisa(Integer kisa) {
		this.kisa = kisa;
	}

	public Integer getUzun() {
		return uzun;
	}

	public void setUzun(Integer uzun) {
		this.uzun = uzun;
	}

	public Color getRenk() {
		return renk;
	}

	public void setRenk(Color renk) {
		this.renk = renk;
	}

	@Override
	public String toString() {
		return "Dikdortgen [kisa=" + kisa + ", uzun=" + uzun + ", renk=" + renk + "]";
	}

}
