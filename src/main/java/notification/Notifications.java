package notification;

public enum Notifications implements Notification {

	INFORMATION("resources/info.png", "#2C54AB"),
	NOTICE("resources/notice.png", "#8D9695"),
	SUCCESS("resources/success.png", "#009961"),
	WARNING("resources/warning.png", "#E23E0A"),
	ERROR("resources/error.png", "#CC0033");

	private final String urlResource;
	private final String paintHex;

	Notifications(String urlResource, String paintHex) {
		this.urlResource = urlResource;
		this.paintHex = paintHex;
	}

	@Override
	public String getURLResource() {
		return urlResource;
	}

	@Override
	public String getPaintHex() {
		return paintHex;
	}

}
