package game.shared;

import java.io.Serializable;

public class Entry implements Serializable{
	public String text;

	public Entry(String text){
		this.setText(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
