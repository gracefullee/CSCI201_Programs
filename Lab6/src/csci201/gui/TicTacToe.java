package csci201.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToe extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	private String status = "Player 1's Turn";
	int playCount = 1;
	
	private ArrayList<Integer> player1 = new ArrayList<Integer>();
	private ArrayList<Integer> player2 = new ArrayList<Integer>();
	private JButton [] board = new JButton [9];
	private JLabel statusBar = new JLabel("" + status);
	
	public TicTacToe()
	{
		super("Tic Tac Toe");
		setSize(500,500);
		setLocation(400,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		
		statusBar.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(statusBar);
		
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(3,3));
		for(int i=0; i<9; i++){
			board[i] = new JButton("");
			JButton boardButton = board[i];
			boardButton.addActionListener(new ActionListener(){		
				public void actionPerformed(ActionEvent ae)
				{
					if(playCount%2==1){
						boardButton.setText("X");
						for(int j=0; j<9; j++){
							if(boardButton==board[j])
								player1.add(j);
						}
					}
					else{
						boardButton.setText("O");
						for(int i=0; i<9; i++){
							if(boardButton==board[i])
								player2.add(i);
						}
					}
					
					playCount++;
					
					if(playCount%2==1)
						statusBar.setText("Player 1's Turn");
					else{
						statusBar.setText("Player 2's Turn");
					}
					boardButton.setEnabled(false);
					
					//horizontal cases
					if(player1.contains(0) && player1.contains(1) && player1.contains(2))	
					{
						for(int j=0; j<9; j++)
							board[j].setEnabled(false);
						statusBar.setText("Player 1 Won!");
					}
					else if(player1.contains(3) && player1.contains(4) && player1.contains(5))
					{
						for(int j=0; j<9; j++)
							board[j].setEnabled(false);
						statusBar.setText("Player 1 Won!");
					}					
					else if(player1.contains(6) && player1.contains(7) && player1.contains(8))
					{
						for(int j=0; j<9; j++)
							board[j].setEnabled(false);
						statusBar.setText("Player 1 Won!");
					}
					else if(player2.contains(0) && player2.contains(1) && player2.contains(2))
					{
						for(int j=0; j<9; j++)
							board[j].setEnabled(false);
						statusBar.setText("Player 2 Won!");
					}
					else if(player2.contains(3) && player2.contains(4) && player2.contains(5))
					{
						for(int j=0; j<9; j++)
							board[j].setEnabled(false);
						statusBar.setText("Player 2 Won!");
					}
					else if(player2.contains(6) && player2.contains(7) && player2.contains(8))
					{
						for(int j=0; j<9; j++)
							board[j].setEnabled(false);
						statusBar.setText("Player 2 Won!");
					}
					//vertical cases
					else if(player1.contains(0) && player1.contains(3) && player1.contains(6))
					{
						for(int j=0; j<9; j++)
							board[j].setEnabled(false);
						statusBar.setText("Player 1 Won!");
					}
					else if(player1.contains(1) && player1.contains(4) && player1.contains(7))
					{
						for(int j=0; j<9; j++)
							board[j].setEnabled(false);
						statusBar.setText("Player 1 Won!");
					}
					else if(player1.contains(2) && player1.contains(5) && player1.contains(8))
					{
						for(int j=0; j<9; j++)
							board[j].setEnabled(false);
						statusBar.setText("Player 1 Won!");
					}
					else if(player2.contains(0) && player2.contains(3) && player2.contains(6))
					{
						for(int j=0; j<9; j++)
							board[j].setEnabled(false);
						statusBar.setText("Player 2 Won!");
					}
					else if(player2.contains(1) && player2.contains(4) && player2.contains(7))
					{
						for(int j=0; j<9; j++)
							board[j].setEnabled(false);
						statusBar.setText("Player 2 Won!");
					}
					else if(player2.contains(2) && player2.contains(5) && player2.contains(8))
					{
						for(int j=0; j<9; j++)
							board[j].setEnabled(false);
						statusBar.setText("Player 2 Won!");
					}
					//diagonal cases
					else if(player1.contains(0) && player1.contains(4) && player1.contains(8))
					{
						for(int j=0; j<9; j++)
							board[j].setEnabled(false);
						statusBar.setText("Player 1 Won!");
					}
					else if(player1.contains(2) && player1.contains(4) && player1.contains(6))
					{
						for(int j=0; j<9; j++)
							board[j].setEnabled(false);
						statusBar.setText("Player 1 Won!");
					}
					else if(player2.contains(0) && player2.contains(4) && player2.contains(8))
					{
						for(int j=0; j<9; j++)
							board[j].setEnabled(false);
						statusBar.setText("Player 2 Won!");
					}
					else if(player2.contains(2) && player2.contains(4) && player2.contains(6))
					{
						for(int j=0; j<9; j++)
							board[j].setEnabled(false);
						statusBar.setText("Player 2 Won!");
					}
					
					if(playCount==10 && (!statusBar.getText().matches("Player 2 Won!") && !statusBar.getText().matches("Player 1 Won!"))){
						statusBar.setText("No Winner! Please Try Again");
						for(int j=0; j<9; j++){
							board[j].setEnabled(false);
						}
					}
				
				}
			});
				
			gridPanel.add(boardButton);
			
		}
		mainPanel.add(gridPanel);
		
		JButton restartButton = new JButton("Restart Game");
		restartButton.setAlignmentX(CENTER_ALIGNMENT);
		restartButton.addActionListener(new ActionListener(){		
			public void actionPerformed(ActionEvent ae)
			{
				playCount = 1;
				for(int i=0; i<9; i++){
					board[i].setEnabled(true);
					board[i].setText("");
				}
				statusBar.setText("Player 1's Turn");
				player1.clear();
				player2.clear();
			}
		});
		mainPanel.add(restartButton);
		
		add(mainPanel);
		
		setVisible(true);
	}
	
	public static void main(String [] args)
	{
		new TicTacToe();
	}
}
