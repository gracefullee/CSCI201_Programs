package csci201.bs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class GameGUI extends JFrame {
	
	private JPanel mainPanel, gamePanel, chatPanel,  clickedCardsPanel, playPanel, deckPanel, chatFieldPanel, playersPanel, imagePanel;
	DefaultListModel<String> chatDLM;
	private JList<String> chatJList;
	protected Player player;
	private JScrollPane chatJSP, imageSP;
	private int numPlayers;
	private JLabel cardBack,  middleDeck;
	JLabel myCharacter;
	JLabel [] players;
	private clickedCard[] turnCards = new clickedCard[4];
	private JButton bsButton, submitButton, chatButton;
	private ArrayList<cardButton> cardImages = new ArrayList<cardButton>();
	private JTextField statusBar, chatField;
	JTextField rankField;
	private List<cardButton> list = new ArrayList<cardButton>();
	
	public GameGUI(final Player player, int numPlayers)
	{
		super("BS");
		setSize(908, 650);
		setLocation(200, 80);
		
		this.player = player;
		this.numPlayers = numPlayers;
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		
		gamePanel = new JPanel();
		gamePanel.setLayout(new BoxLayout(gamePanel,BoxLayout.Y_AXIS));
		gamePanel.setBackground(Color.WHITE);
		gamePanel.setPreferredSize(new Dimension(600, 600));
		
			//Display other players
			playersPanel = new JPanel();
			playersPanel.setBackground(Color.WHITE);
			playersPanel.setPreferredSize(new Dimension(600 ,160));

			players = new JLabel[numPlayers];
			for(int i = 0; i < numPlayers; i++) {
				if(i != player.getID()) {
					players[i] = new JLabel("Player " + i, SwingConstants.CENTER);
					players[i].setHorizontalTextPosition(SwingConstants.CENTER);
					players[i].setVerticalTextPosition(SwingConstants.BOTTOM);
					players[i].setIconTextGap(5);
					players[i].setBorder(new EmptyBorder(0,5,0,5));
					playersPanel.add(players[i]);
				}
			}
			
			//Display BS Button & Middle Deck
			deckPanel = new JPanel();
			deckPanel.setLayout(new FlowLayout());
			deckPanel.setBackground(Color.WHITE);
			deckPanel.setPreferredSize(new Dimension(600, 160));
			deckPanel.setLayout(new GridLayout(1, 5));
			
			JPanel buttonPanel = new JPanel();
			ImageIcon bs = new ImageIcon("bs.png");
			Image img3 = bs.getImage();
			img3 = img3.getScaledInstance(130, 130, Image.SCALE_SMOOTH);
			bs = new ImageIcon(img3);
			bsButton = new JButton(bs);
			bsButton.setBorderPainted(false);
			bsButton.setContentAreaFilled(false);
			bsButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae)
				{
					player.checkBS();
				}
			});
			buttonPanel.add(bsButton);
			buttonPanel.setBackground(Color.WHITE);
			deckPanel.add(buttonPanel);
			
			ImageIcon cardback = new ImageIcon("cardback.png");
			Image img = cardback.getImage();
			img = img.getScaledInstance(100, 140, Image.SCALE_SMOOTH);
			cardback = new ImageIcon(img);
			cardBack = new JLabel();
			cardBack.setIcon(cardback);
			cardBack.setBorder(new EmptyBorder(0,25,0,25));
			middleDeck = new JLabel("0 cards in the pile");
			middleDeck.setBorder(new EmptyBorder(0,25,0,25));
			ImageIcon mc = new ImageIcon(player.avatarPath);
			Image mci = mc.getImage();
			mci = mci.getScaledInstance(96, 120, Image.SCALE_SMOOTH);
			mc = new ImageIcon(mci);
			myCharacter = new JLabel(mc);
			deckPanel.add(cardBack);
			deckPanel.add(middleDeck);
			deckPanel.add(myCharacter);
			
			//Display player cards and JComboBox (ScrollPane)
			playPanel = new JPanel();
			playPanel.setLayout(new BoxLayout(playPanel,BoxLayout.Y_AXIS));
			Dimension playSize = new Dimension(600,230);
			playPanel.setBackground(Color.WHITE);
			playPanel.setPreferredSize(playSize);
			playPanel.setMinimumSize(playSize);
			imagePanel = new JPanel();
			imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			imageSP = new JScrollPane(imagePanel);
			imageSP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			imageSP.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			Dimension imageSize = new Dimension(590,130);
			imageSP.setPreferredSize(imageSize);
			imageSP.setMinimumSize(imageSize);
			imageSP.setMaximumSize(imageSize);
			
			clickedCardsPanel = new JPanel();
			clickedCardsPanel.setBackground(Color.WHITE);
			clickedCardsPanel.setLayout(new FlowLayout());
			Dimension ccSize = new Dimension(590,100);
			clickedCardsPanel.setPreferredSize(ccSize);
			clickedCardsPanel.setMinimumSize(ccSize);
			clickedCardsPanel.setMaximumSize(ccSize);
			for(int i=0; i<4; i++)
			{
				String path = "cardback.png";
				ImageIcon clickedCard = new ImageIcon(path);
				img = clickedCard.getImage();
				img = img.getScaledInstance(65, 90, Image.SCALE_SMOOTH);
				clickedCard = new ImageIcon(img);
				turnCards[i] = new clickedCard(path);
				clickedCardsPanel.add(turnCards[i]);
			}
			ImageIcon go = new ImageIcon("go.png");
			Image goImg = go.getImage();
			goImg = goImg.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
			go = new ImageIcon(goImg);
			submitButton = new JButton(go);
			submitButton.setBorderPainted(false);
			submitButton.setContentAreaFilled(false);
			submitButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae)
				{
					ArrayList<Card> playerCards = player.getDeck();
						ArrayList<Card> selectedCards = new ArrayList<Card>();
						for (int j = 0; j < 4; j++)
						{
							clickedCard cc = (clickedCard) clickedCardsPanel.getComponent(j);
							if(!cc.isCardBack())
							{
								String cardVal = cc.getPath();
								cardVal = cardVal.replace("cards/", "");
								String[] cardVal2 = cardVal.split("_");
								int rank = 0;
								if (cardVal2[0].charAt(0) == 'A')
								{
									rank = 0;
								}
								else if (cardVal2[0].charAt(0) == 'J')
								{
									rank = 10;
								}
								else if (cardVal2[0].charAt(0) == 'Q')
								{
									rank = 11;
								}
								else if (cardVal2[0].charAt(0) == 'K')
								{
									rank = 12;
								}
								else 
								{
									String rankString = cardVal2[0];
									rank = Integer.parseInt(rankString) - 1;
								}
								Card card = new Card((cardVal2[1].charAt(0)), rank);
								selectedCards.add(card);
								for (int i = 0; i < playerCards.size(); i++)
								{
									if (playerCards.get(i).cardIsEqual(card))
									{
										playerCards.remove(i);
										cardImages.remove(i);	
									}
								}
								for (int i = 0; i < list.size(); i++){
									if (list.get(i).getPath() == cc.getPath()){
										cardButton b = list.remove(i);
										imagePanel.remove(b);
										imagePanel.invalidate();		
									}
								}
								for(int i=0; i<4; i++)
								{
									cc.updateCard("cardback.png");		
								}
								revalidate();
								repaint();		
								player.updateDeck(playerCards);
							}
						}
						player.submitCards(selectedCards);

					player.endTurn();
				}
			});
			clickedCardsPanel.add(submitButton);
			playPanel.add(clickedCardsPanel);
			playPanel.add(imageSP);
			
		statusBar = new JTextField("Waiting for Other Players...", 300);
		statusBar.setEditable(false);
		
		rankField = new JTextField("Play: Ace", 300);
		rankField.setEditable(false);
		
		gamePanel.add(playersPanel);
		gamePanel.add(deckPanel);
		gamePanel.add(statusBar);
		gamePanel.add(playPanel);
		gamePanel.add(rankField);
		
		chatPanel = new JPanel();
		chatPanel.setBackground(Color.WHITE);
		chatPanel.setPreferredSize(new Dimension(300, 600));
		chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
		
		JLabel chatLabel = new JLabel("PUBLIC CHAT");
		chatLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		chatDLM = new DefaultListModel<String>();
		chatJList = new JList<String>(chatDLM);
		chatJSP = new JScrollPane(chatJList);
		chatJSP.setPreferredSize(new Dimension(300, 540));
		
		chatFieldPanel = new JPanel();
		chatFieldPanel.setLayout(new FlowLayout());
		chatField = new JTextField(16);
		chatButton = new JButton("Enter");
		chatButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				player.chat(chatField.getText());
				chatField.setText("");
			}
		});
		chatFieldPanel.add(chatField);
		chatFieldPanel.add(chatButton);
		
		chatPanel.add(chatLabel);
		chatPanel.add(chatJSP);
		chatPanel.add(chatFieldPanel);
		
		gbc.gridx=0;
		gbc.gridy=0;
		mainPanel.add(gamePanel, gbc);
		gbc.gridx=1;
		mainPanel.add(chatPanel, gbc);
		
		add(mainPanel);
		pack();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void enableCardButtons(boolean en) 
	{
		submitButton.setEnabled(en);
		for(int i = 0; i < list.size(); i++)
				list.get(i).setEnabled(en);
	}
	
	public void addCard(Card newCard)
	{
		String path = "cards/" + newCard.toString() + ".png";
		ImageIcon ci = new ImageIcon(path);
		Image img2 = ci.getImage();
		img2 = img2.getScaledInstance(71, 100, Image.SCALE_SMOOTH);
		ci = new ImageIcon(img2);
		
		final cardButton newButton = new cardButton(ci, path);
		newButton.setBorder(new EmptyBorder(0,0,0,0));
		newButton.setContentAreaFilled(false);
		newButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				ImageIcon image = newButton.getImage();
				String path = newButton.getPath();
				if(!newButton.getClicked())
				{
					boolean result = false;
					for(int i=0; i<4; i++)
					{
						clickedCard cc = (clickedCard) clickedCardsPanel.getComponent(i);
						if(cc.isCardBack())
						{
							cc.updateCard(path);
							result = true;
							newButton.setClicked();
							repaint();
							break;
						}
					}
					if(!result)
					{
						JOptionPane.showMessageDialog(GameGUI.this, 
								"Only maximum of 4 cards can be selected for each move.", 
								"Cards", 
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else
				{
					for(int i=0; i<4; i++)
					{
						clickedCard cc = (clickedCard) clickedCardsPanel.getComponent(i);
						if(cc.getPath().matches(path))
						{
							cc.updateCard("cardback.png");
							newButton.setClicked();
							repaint();
							break;
						}
					}
				}
			}
		});
		cardImages.add(newButton);
		imagePanel.add(newButton);
		list.add(newButton);
		imagePanel.invalidate();
	}
	
	public void updateCards() 
	{
		cardImages.clear();
		imagePanel.removeAll();
		list.clear();
		
		for(Card card: player.getDeck())
			addCard(card);
		
		revalidate();
		repaint();
	}
	
	public void updateDeckSize(int size)
	{
		middleDeck.setText(size + " cards in the pile");
	}
	
	public void updatePlayersDeckSize(int [] size)
	{
		for(int i=0; i<numPlayers; i++)
		{
			if(i != player.getID())
				players[i].setText("Player " + i + " - " + size[i] + " Cards Left");
		}
	}
	
	public void updateStatus(String s)
	{
		statusBar.setText(s);
	}
}
