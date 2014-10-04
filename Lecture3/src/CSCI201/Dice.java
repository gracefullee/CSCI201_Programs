package CSCI201;

import java.util.Scanner;

public class Dice {
	
	private int numberOfRolls;
	private int count[];
	public static final int NUM_FACES = 6;
	
	Dice(int numberOfRolls)
	{
		this.numberOfRolls = numberOfRolls;
		this.count = new int[Dice.NUM_FACES];
	}
	
	public Dice()
	{
		numberOfRolls = 1000;
	}
	
	public void roll()
	{
		for(int i=0; i<this.numberOfRolls; i++)
		{
			int num = (int)(Math.random()*6);
			count[num]++;
		}
	}
	
	public void printStatistics()
	{
		for(int i=0; i<Dice.NUM_FACES; i++)
		{
			System.out.print("The number " + (i+1) + " was rolled " + count[i] + " times ");
			double percentage = (double)(this.count[i])/this.numberOfRolls;
			System.out.println("(" + (percentage*100) + "%).");
		}
	}
	
	private static int getNumRolls()
	{
		System.out.print("How many rolls? ");
		Scanner scan = new Scanner(System.in);
		int numRolls = scan.nextInt();
		scan.close();
		return numRolls;
	}
	
	public static void main(String [] args)
	{
		int numRolls = getNumRolls();
		Dice d = new Dice(numRolls);
		d.roll();
		d.printStatistics();
	}
}
