package csci201.bs;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class clickedCard extends JLabel{
	
	private ImageIcon image;
	private String path;

	public clickedCard(String path)
	{
		super();
		ImageIcon clickedCard = new ImageIcon(path);
		Image img = clickedCard.getImage();
		img = img.getScaledInstance(65, 90, Image.SCALE_SMOOTH);
		clickedCard = new ImageIcon(img);
		this.setIcon(clickedCard);
		this.image = clickedCard;
		this.path = path;
	}
	
	public void updateCard(String path)
	{
		ImageIcon clickedCard = new ImageIcon(path);
		Image img = clickedCard.getImage();
		img = img.getScaledInstance(65, 90, Image.SCALE_SMOOTH);
		clickedCard = new ImageIcon(img);
		this.setIcon(clickedCard);
		this.image = clickedCard;
		this.path = path;
	}
	
	public String getPath()
	{
		return this.path;
	}
	
	public boolean isCardBack()
	{
		if(path.matches("cardback.png"))
			return true;
		else
			return false;
	}
	public clickedCard getClickedCard(String path){
		if (this.path == path){
			return this;
		}
		else{
			return null;
		}
	}
}
