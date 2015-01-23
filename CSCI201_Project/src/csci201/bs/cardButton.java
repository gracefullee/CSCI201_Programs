package csci201.bs;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class cardButton extends JButton{
	
	private static final long serialVersionUID = 1L;
	private String path;
	private ImageIcon image;
	private boolean clicked;
	
	public cardButton(ImageIcon ci, String path)
	{
		super(ci);
		this.path = path;
	}
	
	public ImageIcon getImage()
	{
		return this.image;
	}
	
	public void setClicked()
	{
		if(clicked)
			clicked = false;
		else
			clicked = true;
	}
	
	public String getPath()
	{
		return this.path;
	}
	
	public boolean getClicked()
	{
		return this.clicked;
	}
}
