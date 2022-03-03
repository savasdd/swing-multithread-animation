package custom;

public class SekilFactory {

	public Sekil getSekil(String type) {

		if (type != null && type.equalsIgnoreCase(EnumUtil.DAIRE)) {
			return new DaireOlustur();

		} else if (type != null && type.equalsIgnoreCase(EnumUtil.DIKDORTGEN)) {
			return new DikdortgenOlustur();

		} else if (type != null && type.equalsIgnoreCase(EnumUtil.KARE)) {
			return new KareOlustur();
		}

		return null;
	}

}
