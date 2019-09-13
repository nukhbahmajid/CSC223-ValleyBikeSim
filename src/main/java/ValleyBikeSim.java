import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.*;
import com.opencsv.CSVReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.sun.tools.javac.code.TypeMetadata.Entry;

public class ValleyBikeSim {
	
	/*
	 * Fields related to stations.
	 */
	public static List<Station> stationsList;
	public static TreeMap<Integer, Station> stationsMap;
	public static List<String[]> allStationEntries;
	
	/*
	 * Fields related to rides.
	 */
	public static List<Ride> ridesList;
	//public static List<Rides> ridesList2;
	public static TreeMap<Integer, Ride> ridesMap;
	//public static TreeMap<Integer, Rides> ridesMap2;
	public static List<String[]> allRidesEntries;
	//public static List<String[]> allridesEntries2;
	
	/**
	 * Read in all the data files and store them in appropriate data structures
	 */
	public static void readData() {
		try {
			String stationData = "/Users/nukhbahmajid/Desktop/Smith_College/Junior_Year/CSC223/CSC223-ValleyBikeSim/data-files/station-data.csv";

			
			CSVReader stationDataReader = new CSVReader(new FileReader(stationData));
			
			
			stationsList = new ArrayList<>();
			stationsMap = new TreeMap<>();
			
			// how to read the CSV data row wise:
			allStationEntries = stationDataReader.readAll();
			System.out.println("");
			int counter = 0;
			for(String[] array : allStationEntries) {
				if(counter == 0) {
					
				} else {
					stationsList.add(new Station((Integer.parseInt(array[0])), array[1], Integer.parseInt(array[2]), Integer.parseInt(array[3]), Integer.parseInt(array[4]),
						Integer.parseInt(array[5]), Integer.parseInt(array[6]), toBool(array[7]), array[8]));
					
				}
				counter++;
				
			}
		
			for(Station station : stationsList) {
				stationsMap.put(station.getID(), station);
			}
			
			
			System.out.println("Should have sorted data by IDs below: \n");
			
			for(Map.Entry<Integer, Station> entry: stationsMap.entrySet()) {
				System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue().getName());
			}
			
			
		
		} 
		
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void processRideData(String ridesFileName) {
		String rideData = "/Users/nukhbahmajid/Desktop/Smith_College/Junior_Year/CSC223/CSC223-ValleyBikeSim/data-files/" + ridesFileName;
		
		try {
			CSVReader rideDataReader = new CSVReader(new FileReader(rideData));
			
			ridesList = new ArrayList<>();
			ridesMap = new TreeMap<>();
			
			allRidesEntries = rideDataReader.readAll();
			System.out.println("");
			int counter = 0;
			for(String[] array : allRidesEntries) {
				if(counter == 0) {
					
				} else {
					ridesList.add(new Ride(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]), toDate(array[3]), toDate(array[4])));
				}
	
			}
			
			
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Convert a string to Date java object.
	 * @throws ParseException 
	 */
	private static Date toDate(String s) throws ParseException {
		Date dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
		return dateTime;
	}
	
	
	/**
	 * Helper function to pass the String values of "0" and "1" as arguments
	 * and return boolean values of true and false respectively. 
	 */
	private static boolean toBool(String s) {
		if(s.equals("0")) {
			return false;
		} else {
			return true;
		}
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("Welcome to CSC223.\n");
		readData();
		
		
		
	}//endMain
	

}
