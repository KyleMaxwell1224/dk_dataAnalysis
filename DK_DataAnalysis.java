//Kyle Maxwell
//University of Pittsburgh
//Diamond Kinetics Coding Challenge
//Spring 2019

import java.io.*;
import java.util.*;

public class DK_DataAnalysis 
{ 
	private static ArrayList<ArrayList<Double>> swingData = new ArrayList<ArrayList<Double>>(); /*To fix the issue of not knowing the amount of rows, I 
	decided to transition from a 2D array to a 2D arraylist. */
	
	public static void main(String[] args) throws Exception
	{
		String swing = "latestSwing.csv"; 		
		int row = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(swing))) //Implementing the try-with-resources statement
		{
			while(br.ready() )
			{
				String[] lineOfData = br.readLine().split(",");
				swingData.add(new ArrayList<Double>());
				for (int i = 0; i < lineOfData.length; i++)
					swingData.get(row).add(Double.parseDouble(lineOfData[i]));
			row++;
			}
		}
		System.out.println(searchContinuityAboveValue( 6, 0, 1000, .043, 50)); 
		System.out.println(backSearchContinuityWithinRange( 3, 0, 1200, -1, 0, 100)); 
		System.out.println(searchContinuityAboveValueTwoSignals(0, 3, 50, 100, 80000, -.5, 4));
		System.out.println(searchMultiContinuityWithinRange(0, 5, 100, 10000, 68000, 5)); 
	//	These were used to test the data structure and implementation of the methods.
	
	}
	//The integer that will be returned is the first index where all conditions are satisfied
	public static int searchContinuityAboveValue(int data, int indexBegin, int indexEnd, double threshold, int winLength) //The data value being inputted is the column number. The row value iterates through the column.
	{	
	start: 
		for (int row = indexBegin; row <= indexEnd; row++)
		{
			if(swingData.get(row).get(data) > threshold)
			{
				if (winLength == 1)
					return row;
				
				int index = row;
				int incrementer = row + 1;
				for (int i = 0; i < winLength; i++)
				{
					if (swingData.get(incrementer).get(data) <= threshold)
						break start; //If the value isn't there, go back to starting loop					
					incrementer++;	
				}
				return index;
			}
		}
		return -1; //if no numbers were found in the parameters, -1 will return
	}
	public static int backSearchContinuityWithinRange(int data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength)
	{
	start:
		for (int row = indexEnd; row >= indexBegin; row--)
		{
			if(swingData.get(row).get(data) > thresholdLo && swingData.get(row).get(data) < thresholdHi)
			{
				if (winLength == 1)
					return row;

				int index = row;
				int incrementer = row + 1;
				for (int i = 0; i < winLength; i++)
				{
					if (swingData.get(incrementer).get(data) <= thresholdLo || swingData.get(incrementer).get(data) >= thresholdHi)
						break start; 			
					incrementer++;	
				}
				return index;
			}
		}
		return -1; 
	}
	
	public static int searchContinuityAboveValueTwoSignals(int data1, int data2, int indexBegin, int indexEnd, double threshold1, double threshold2, int winLength)
	{
	start:
		for (int row = indexBegin; row <= indexEnd; row++)
		{
			if(swingData.get(row).get(data1) > threshold1 && swingData.get(row).get(data2) > threshold2)
			{
				if (winLength == 1)
					return row;
				
				int index = row;
				int incrementer = row + 1;
				for (int i = 0; i < winLength; i++)
				{
					if (swingData.get(incrementer).get(data1) <= threshold1 || swingData.get(incrementer).get(data2) <= threshold2)
						break start; 					
					incrementer++;	
				}
				return index;
			}
		}
		return -1; 
	}
	
	public static int searchMultiContinuityWithinRange(int data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength)
	{
	start:
		for (int row = indexBegin; row <= indexEnd; row++)
		{
			if(swingData.get(row).get(data) > thresholdLo & swingData.get(row).get(data) < thresholdHi)
			{
				if (winLength == 1)
					return row;
				
				int index = row;
				int incrementer = row + 1;
				for (int i = 0; i < winLength; i++)
				{
					if (swingData.get(incrementer).get(data) <= thresholdLo || swingData.get(incrementer).get(data) >= thresholdHi)
						break start; 					
					incrementer++;	
				}
				return index;
			}
		}
		return -1; 
	}
}