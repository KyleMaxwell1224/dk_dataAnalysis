//Kyle Maxwell
//University of Pittsburgh
//Diamond Kinetics Coding Challenge
//Spring 2019

import java.io.*;
import java.util.*;

public class DK_DataAnalysis
{
	private static int rowsOfData = 1276; //These variables are used in every operation, so I made them static to avoid adding to every parameter
	private static int colsOfData = 7;
	private static double[][] swingData = new double[rowsOfData][colsOfData]; //I feel that a 2D array allows for an efficient run-time as well as an easy way to traverse data with indicies
	public static void main(String[] args) throws Exception
	{
		String swing = "latestSwing.csv"; 
		BufferedReader br = new BufferedReader(new FileReader(swing));
		
		int count = 0;
		
		while(count <= rowsOfData) //Reads data into swingData 
		{

			for(int row = 0; row < swingData.length; row++)
			{		
				String[] lineOfData = br.readLine().split(",");	
				for(int col = 0; col <swingData[row].length; col++)
				{
					double number = Double.parseDouble(lineOfData[col]);
					swingData[row][col] = number;
					count++;
				}
			}
		}
		br.close();
	/*	System.out.println(Arrays.toString(searchContinuityAboveValue( 0, 0, 1277, -1, 1)));
		System.out.println(Arrays.toString(backSearchContinuityWithinRange( 0, 200, 0, 400, 20000, 5)));
		System.out.println(Arrays.toString(searchContinuityAboveValueTwoSignals(0, 3, 50, 100, 80000, -.5, 4)));
		System.out.println(Arrays.toString(searchMultiContinuityWithinRange(0, 5, 100, 10000, 68000, 5)));
		These were used to test the data structure and implementation of the methods.
	*/
	}
	// For the following methods, the return type is an array of integers, which represent the index or indicies of data pieces that satisfy the criteria
	public static int[] searchContinuityAboveValue(int data, int indexBegin, int indexEnd, double threshold, int winLength) //The data value being inputted is the column number. The row value iterates through the column.
	{	
		int[] indicies = new int[winLength];
		int count = 0;
		
		for (int row = indexBegin; row <= indexEnd; row++)
		{
			if(swingData[row][data] > threshold)
			{
				indicies[count] = row+1; //I add +1 to the row to correspond more accurately with the CSV file. Without the +1, it corresponds to the 2D array I made.
				count++;
				
				if (count == winLength) //To avoid going out of bounds, I have a test put in here to break if the count equals the array length
					break;
			}
		}
		return indicies; //if no numbers were found in the parameters, indicies will return as all 0's
	}
	public static int[] backSearchContinuityWithinRange(int data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength)
	{
		int[] indicies = new int[winLength];
		int count = 0;
		
		for (int row = indexBegin; row >= indexEnd; row--)
		{
			if (swingData[row][data] < thresholdHi && swingData[row][data] > thresholdLo)
			{
				indicies[count] = (row+1);
				count++;
				
				if (count == winLength)
					break;
			}
		}
		return indicies;
	}
	public static int[] searchContinuityAboveValueTwoSignals(int data1, int data2, int indexBegin, int indexEnd, double threshold1, double threshold2, int winLength)
	{
		int[] indicies = new int[winLength];
		int count = 0;
		for (int row = indexBegin; row <= indexEnd; row++)
		{
			if (swingData[row][data1] > threshold1 && swingData[row][data2] > threshold2)
			{
				indicies[count] = row+1;
				count++;
				
				if (count == winLength)
					break;
			}
		}
		return indicies;
	}
	public static int[] searchMultiContinuityWithinRange(int data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength)
	{
		int[] indicies = new int[winLength+1];
		int count = 0;
		int row = indexBegin;
			while(count <= winLength && row <= indexEnd)
			{
				if (swingData[row][data] > thresholdLo && swingData[row][data] < thresholdHi)
				{
					indicies[count] = row+1;
					count++;
				}
				row++;
			}
			return indicies;
	}	
}