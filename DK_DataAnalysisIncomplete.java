//Kyle Maxwell
//University of Pittsburgh
//Diamond Kinetics Coding Challenge
//Spring 2019

import java.io.*;
import java.util.*;

public class DK_DataAnalysisIncomplete
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
		searchContinuityAboveValue( 6, 0, 1000, -.38, 13); 
		backSearchContinuityWithinRange( 3, 0, 1200, -1, 0, 100); 
		searchContinuityAboveValueTwoSignals(0, 3, 50, 100, 80000, -.5, 1);
		searchMultiContinuityWithinRange(2, 5, 1000, 0, 1, 12); 
	//	These were used to test the data structure and implementation of the methods.
	
	}
	//The integer that will be printed is the first index where all conditions are satisfied
	public static void searchContinuityAboveValue(int data, int indexBegin, int indexEnd, double threshold, int winLength) //The data value being inputted is the column number. The row value iterates through the column.
	{	
		boolean found = true;
		int indexFound = 0;
		String foundContinuity = "";
		for (int row = indexBegin; row <= indexEnd; row++)
		{
			if(swingData.get(row).get(data) > threshold)
			{
				if (winLength == 1)
				{
					System.out.println("Continuity was found at: " + row);
					return;
				}
		
				for (int i = row+1; i < (row+winLength); i++) //Start at row + 1 because we know row is within values
				{
					if (swingData.get(i).get(data) <= threshold || i > indexEnd)
					{
						found = false;
						break;
					}
				}
				if (found = true)
				{
					indexFound = row;
					break;
				}
			}
		}
			if (indexFound > 0)
			{
				foundContinuity = indexFound + "-" + (indexFound+winLength-1);
				System.out.println("Continuity was found at: " + foundContinuity); //Print out the solution if found
				return; //Return, we found what we needed
			}
			else
			{
				System.out.println("No continuity was found in this range");
				return; //Return, nothing was found
		}
	}
	public static void backSearchContinuityWithinRange(int data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength)
	{
		boolean found = true;
		String foundContinuity = "";
		for (int row = indexEnd; row >= indexBegin; row--)
		{
			if(swingData.get(row).get(data) > thresholdLo && swingData.get(row).get(data) < thresholdHi)
			{
				if (winLength == 1)
				{
					System.out.println("Continuity was found at: " + row);
					return;
				}
				for (int i = row-1; i > (row-winLength); i--)
				{
					if (swingData.get(i).get(data) <= thresholdLo || swingData.get(i).get(data) >= thresholdHi || i < indexBegin)
					{
						found = false;
						break;			
					}
				}
			if (found = true)
			{
				foundContinuity += row + "-" + (row-winLength +1);
				System.out.println("Continuity was found at: " + foundContinuity);
				return;
			}
			else
				System.out.println("No continuity was found");
			}
		}
	}
	
	public static void searchContinuityAboveValueTwoSignals(int data1, int data2, int indexBegin, int indexEnd, double threshold1, double threshold2, int winLength)
	{
		String foundContinuity = "";
		boolean found = true;
		for (int row = indexBegin; row <= indexEnd; row++)
		{
			if(swingData.get(row).get(data1) > threshold1 && swingData.get(row).get(data2) > threshold2)
			{
				if (winLength == 1)
				{
					System.out.println("Continuity was found at: " + row);
					return;
				}
				for (int i = row+1; i < (row+winLength); i++)
				{
					if (swingData.get(i).get(data1) <= threshold1 || swingData.get(i).get(data2) <= threshold2 || i >= indexEnd) //if i goes past the indexEnd, we stop because this isn't allowed.
					{
						found = false;
						break; 			
					}						
				}
			if (found == true)
			{
				foundContinuity = row + "-" + (row+winLength-1);
				System.out.println("Continuty was found at: " + foundContinuity); //Print out the solution if found
				return; //Return, we found what we needed
			}
			else
			{
				System.out.println("No continuity was found in this range");
				return; //Return, nothing was found
			}
			}
		}
	}
	
	public static void searchMultiContinuityWithinRange(int data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength)
	{
		String foundIndicies = "";
		for (int row = indexBegin; row <= indexEnd; row++)
		{
			boolean validData = true;
			if(swingData.get(row).get(data) > thresholdLo & swingData.get(row).get(data) < thresholdHi) //If data is in value...
			{
				if (winLength == 1)
					foundIndicies += row + " ";
				int index = row;
				for (int i = index+1; i < index+winLength; i++)
				{
					if (swingData.get(i).get(data) <= thresholdLo || swingData.get(i).get(data) >= thresholdHi)
					{
						validData = false; //The data is not valid, so we will not add it
						break;	
					}
				}								
				row = row + winLength - 1; //Avoid checking numbers within the continuity twice, since we know there is a continuity.
				if (validData == true) //If the number is valid...
					if (winLength > 1) //and the winLength is greater than 1...
						foundIndicies += index + "-" + (index+winLength-1) + " "; //Formatting to show where the continuity exists
			}
		}
		if (foundIndicies == "")
			System.out.println("No continuities were found");
		else
			System.out.println("Continuities were found at: " + foundIndicies);
	}
}