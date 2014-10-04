package CSCI201.filePackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileCopier
{
	private String inputFilename;
	private String[] outputFilenames;
	
	public FileCopier(String inputFilename)
	{
		this.inputFilename = inputFilename;
	}
	
	private void promptForOutputFiles(Scanner scan)
	{
		System.out.println("How many copies of the file would you like?");
		int numFiles = scan.nextInt();
		scan.nextLine();
		while(numFiles<1)
		{
			System.out.println("Please enter a positive number: ");
			numFiles = scan.nextInt();
			scan.nextLine();
		}
		this.outputFilenames = new String[numFiles];
		for(int i=0; i<numFiles; i++)
		{
			System.out.print("Enter the name of file #" + (1+i)+ ": ");
			this.outputFilenames[i] = scan.nextLine();
		}
	}
	
	public void copyFile()
	{
		try{
			FileReader fr = new FileReader(this.inputFilename);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw[] = new FileWriter[this.outputFilenames.length];
			PrintWriter pw[] = new PrintWriter[this.outputFilenames.length];
			for(int i=0; i < this.outputFilenames.length; i++)
			{
				fw[i] = new FileWriter(this.outputFilenames[i]);
				pw[i] = new PrintWriter(fw[i]);
			}
			String line = br.readLine();
			while(line != null)
			{
				for(int i=0; i< pw.length; i++)
				{
					pw[i].println(line);
				}
				line = br.readLine();
			}
			
			br.close();
			fr.close();
			for(int i=0; i<pw.length; i++)
			{
				pw[i].flush();
				pw[i].close();
				fw[i].close();
			}
		}	catch (FileNotFoundException fnfe) {
			System.out.println("Input file could not be found: " + fnfe.getMessage());
			return;
		}	catch (IOException ioe) {
			System.out.println("IOException occurred: " + ioe.getMessage());
		}	finally {
			System.out.println("Inside finally block");
		}
		
	}
	
	public static void main(String [] args)
	{
		System.out.println("What file would you like to copy?");
		Scanner scan = new Scanner(System.in);
		String inputFilename = scan.nextLine();
		FileCopier fc = new FileCopier(inputFilename);
		fc.promptForOutputFiles(scan);
		fc.copyFile();
		scan.close();
	}
}
