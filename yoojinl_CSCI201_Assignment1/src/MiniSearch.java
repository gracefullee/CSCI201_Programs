import java.util.Scanner;


public class MiniSearch
{
	public static boolean isAdmin(String input)
	{
		char adm = input.charAt(0);
		if(adm=='A' && input.length()==1)
			return true;
		return false;
	}
	
	public static void adminGrid(String [][] grid,int row, int col)
	{
		System.out.println("Here's the grid, just for you admin!");
		for(int i=0; i<row; i++)
		{
			for(int j=0; j<col; j++)
				System.out.print(grid[i][j] + " ");
			System.out.print('\n');
		}
	}
	
	public String [][] makeGrid(int row, int col, String [] puzzle)
	{
		String grid[][] = new String[row][col];
		int k = 0;
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				grid[i][j] = puzzle[k];
				k++;
			}
		}
		return grid;
	}
	
	public String findString(boolean adminTrue, int row, int col, String [][] grid)
	{
		StringBuilder solStr = new StringBuilder();
		int r = 0; int c = 0;
		solStr.append(grid[r][c]);
		if(adminTrue==true){
			System.out.println("Here is the step by step, just for you admin!");
			System.out.println("R:"+ r + " C:" + c + " L:" + grid[r][c]);
		}
		if(row > col)
		{
			boolean toRight = true;
			while(r<row-1){
				if(c==col-1)
					toRight = false;
				if(c==0)
					toRight = true;
				if(toRight==true){
					r++; c++;
					solStr.append(grid[r][c]);
					if(adminTrue==true)
						System.out.println("R:"+ r + " C:" + c + " L:" + grid[r][c]);
				}
				else{
					r++; c--;
					solStr.append(grid[r][c]);
					if(adminTrue==true)
						System.out.println("R:"+ r + " C:" + c + " L:" + grid[r][c]);
				}
			}
			String finStr = solStr.toString();
			return finStr;
		}
		
		else if(row < col)
		{
			boolean toDown = true;
			while(c<col-1){
				if(r==row-1)
					toDown = false;
				if(r==0)
					toDown = true;
				if(toDown==true){
					r++; c++;
					solStr.append(grid[r][c]);
					if(adminTrue==true)
						System.out.println("R:"+ r + " C:" + c + " L:" + grid[r][c]);
				}
				else{
					r--; c++;
					solStr.append(grid[r][c]);
					if(adminTrue==true)
						System.out.println("R:"+ r + " C:" + c + " L:" + grid[r][c]);
				}
			}
			String finStr = solStr.toString();
			return finStr;
		}
		
		else
		{
			while(r<row-1){
				r++; c++;
				solStr.append(grid[r][c]);
				if(adminTrue==true)
					System.out.println("R:"+ r + " C:" + c + " L:" + grid[r][c]);
			}
			String finStr = solStr.toString();
			System.out.println("Result: " + finStr);
			if(adminTrue==true)
				System.out.println("Thanks for using this program!");
			return finStr;
		}
	}
	
	public static void main(String [] args)
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to the Maze Game!");
		
		MiniSearch search = new MiniSearch();
		
		System.out.println("Are you an admin?");
		String admin = scan.nextLine();
		boolean adminTrue = isAdmin(admin);
		char adm = admin.charAt(0);
		if(adm=='A'&&admin.length()==1)
			adminTrue = true;

		int row, col;
		System.out.println("How many rows are in the grid?");
		while(!scan.hasNextInt()){
			System.out.println("Error: Please enter a number. Please try again.");
			scan.nextLine();
		}
		row = scan.nextInt();
		
		scan.nextLine();
		System.out.println("How many columns are in the grid?");		
		while(!scan.hasNextInt()){
			System.out.println("Error: Please enter a number. Please try again.");
			scan.nextLine();
		}
		col = scan.nextInt();
		
		scan.nextLine();
		
		int gridSize = col*row;
		System.out.println("Enter " + gridSize + " letters separated by spaces.");		
		String puzzle = scan.nextLine();
		String [] puzzleArray = puzzle.split(" ");
		for(int i=0; i<puzzleArray.length; i++){
			while(puzzleArray[i].length()!=1 || puzzleArray.length!=gridSize){
				System.out.println("Error: that is not " + gridSize + " letters. Please try again!");
				puzzle = scan.nextLine();
				puzzleArray = puzzle.split(" ");
			}
		}	
		
		String grid[][] = search.makeGrid(row,col,puzzleArray);
		
		if(adminTrue==true)
			adminGrid(grid,row,col);
		
		String solution = search.findString(adminTrue, row, col, grid);
		
		System.out.println("Result: " + solution);
		System.out.println("Thanks for using this program!");
		
		scan.close();
	}
}
