import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class TicTacToe {
	
	private static int ticTacToeSize = 3;
	private ArrayList<String> instructions = new ArrayList<String>();
	private char [][] ticTacToeGrid = new char [3][3];
	private int result = 0;
	private static String error = null;
	
	public TicTacToe()
	{
		for(int i=0; i<ticTacToeSize; i++)
		{
			for(int j=0; j<ticTacToeSize; j++)
			{
				ticTacToeGrid[i][j] = '_';
			}
		}
	}
	
	public void read(String inputFilename, Scanner scan)
	{
		try{
			FileReader fr = new FileReader(inputFilename);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while(line!=null)
			{
				instructions.add(line);
				line = br.readLine();
			}
			br.close();
			fr.close();
		}	catch (FileNotFoundException fnfe) {
			error = "Input DNE";
			return;
		}	catch (IOException ioe) {
			System.out.println("IOException occurred: " + ioe.getMessage());
		}
	}
	
	public void play()
	{	
		ArrayList<Integer> player1 = new ArrayList<Integer>();
		ArrayList<Integer> player2 = new ArrayList<Integer>();
		for(int i=0; i<instructions.size(); i++)
		{
			int row, col;
			String [] line = instructions.get(i).split(", ");
			row = Integer.valueOf(line[0]);
			col = Integer.valueOf(line[1]);
			try{
				if(ticTacToeGrid[row][col]=='_'){				
					if(i%2==0){
						ticTacToeGrid[row][col] = 'X';
						player1.add((row*3)+col);
					}
					if(i%2==1){
						ticTacToeGrid[row][col] = 'O';
						player2.add((row*3)+col);
					}
				}
				else if(ticTacToeGrid[row][col]=='X' || ticTacToeGrid[row][col]=='O')
					error = "Repeated Move";
			}	catch(ArrayIndexOutOfBoundsException aibe){
				error = "Out of Bounds";
				return;
			}
			//horizontal cases
			if(player1.contains(0) && player1.contains(1) && player1.contains(2))
				result = 1;
			else if(player1.contains(3) && player1.contains(4) && player1.contains(5))
				result = 1;
			else if(player1.contains(6) && player1.contains(7) && player1.contains(8))
				result = 1;
			else if(player2.contains(0) && player2.contains(1) && player2.contains(2))
				result = 2;
			else if(player2.contains(3) && player2.contains(4) && player2.contains(5))
				result = 2;
			else if(player2.contains(6) && player2.contains(7) && player2.contains(8))
				result = 2;
			//vertical cases
			else if(player1.contains(0) && player1.contains(3) && player1.contains(6))
				result = 1;
			else if(player1.contains(1) && player1.contains(4) && player1.contains(7))
				result = 1;
			else if(player1.contains(2) && player1.contains(5) && player1.contains(8))
				result = 1;
			else if(player2.contains(0) && player2.contains(3) && player2.contains(6))
				result = 2;
			else if(player2.contains(1) && player2.contains(4) && player2.contains(7))
				result = 2;
			else if(player2.contains(2) && player2.contains(5) && player2.contains(8))
				result = 2;
			//diagonal cases
			else if(player1.contains(0) && player1.contains(4) && player1.contains(8))
				result = 1;
			else if(player1.contains(2) && player1.contains(4) && player1.contains(6))
				result = 1;
			else if(player2.contains(0) && player2.contains(4) && player2.contains(8))
				result = 2;
			else if(player2.contains(2) && player2.contains(4) && player2.contains(6))
				result = 2;
			
			if(result==1||result==2){
				return;
			}
		}
	}
	
	public void print(String outputFilename)
	{
		try{
			FileWriter fw = new FileWriter(outputFilename);
			PrintWriter pw = new PrintWriter(fw);
			
			for(int i=0; i<ticTacToeSize; i++)
			{
				for(int j=0; j<ticTacToeSize; j++)
				{
					pw.print(ticTacToeGrid[i][j]+" ");
				}
				pw.print('\n');
			}
			if(error==null && (result==1 || result==2))
				pw.println("Player " + result);
			else if(error==null && result==0)
				pw.println("Neither");
			else if(error!=null)
				pw.println(error);
			
			pw.close();
			fw.close();
		}	catch (IOException ioe) {
			System.out.println("IOException occurred: " + ioe.getMessage());
		}
	}
	
	public static void main(String [] args)
	{
		Scanner scan = new Scanner(System.in);
		System.out.print("Please enter the name of input file: ");
		String inputFilename = scan.nextLine();
		TicTacToe trial = new TicTacToe();
		trial.read(inputFilename, scan);
		trial.play();
	
		System.out.print("Please enter the name of output file: ");
		String outputFilename = scan.nextLine();
		trial.print(outputFilename);
		scan.close();
	}
}
