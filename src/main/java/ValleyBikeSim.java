import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.sun.tools.javac.code.TypeMetadata.Entry;

public class ValleyBikeSim {
	
	/**
	 * Fields related to stations.
	 */
	public static List<Station> stationsList;
	public static TreeMap<Integer, Station> stationsMap;
	public static List<String[]> allStationEntries;
	
	/**
	 * Fields related to rides.
	 */
	public static List<Ride> ridesList;
	public static List<String[]> allRidesEntries;
	
	/**
	 * the new station instance that gets instantiated every time the addStation() function is run
	 * and the list of new Stations that needs to be instantiated and saved into the CSV file every 
	 * time the saveStationList() function is called. 
	 */
	public static Station newStation;
	public static List<Station> newStationsList = new ArrayList<>();
	
	
	
	
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
		
		} 
		catch(Exception e) {
			System.out.println(e.getMessage());
		}	
	}
	
	
	
	public static void resolveRideData(String ridesFileName) {
		String rideData = "/Users/nukhbahmajid/Desktop/Smith_College/Junior_Year/CSC223/CSC223-ValleyBikeSim/data-files/" + ridesFileName;
		
		try {
			CSVReader rideDataReader = new CSVReader(new FileReader(rideData));
			ridesList = new ArrayList<>();
			
			allRidesEntries = rideDataReader.readAll();
			System.out.println("");
			
			int counter = 0;
			for(String[] array : allRidesEntries) {
				if(counter == 0) {
					
				} else {
					ridesList.add(new Ride(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
							Integer.parseInt(array[2]), toDate(array[3]), toDate(array[4])));
				}
				counter++;
	
			} 
			int totalDuration = 0;
			for(Ride ride : ridesList) {
				totalDuration += ride.getRideDuration();
			}
			int averageDuration = totalDuration / ridesList.size();
			System.out.println("The ride list contains " + ridesList.size() + " rides and the average ride time is " + averageDuration + " minutes.\n");
			
			
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
	
	/**
	 * Prints the main menu for the Valley Bike Simulator to the console.
	 */
	public static void printMainMenu() {
		System.out.println("Please choose from one of the following menu options:\n"
				+ "0. Quit Program.\n1. View station list.\n2. Add station list.\n3. Save station list.\n"
				+ "4. Record ride.\n5. Resolve ride data.\n6. Equalize stations.");
	} 
	
	/**
	 * When the user prompts, print the list of stations.
	 */
	public static void printStationList() {
		System.out.println("\nID\tBikes\tPedelacs AvDocs\tMainReq\t  Cap\tKiosk\tName - Address\n");
		for(Map.Entry<Integer, Station> entry: stationsMap.entrySet()) {
			System.out.println(entry.getKey() + "\t" + entry.getValue().getBikes() + "\t" + entry.getValue().getPedelacs()
			+ "\t  " + entry.getValue().getAvailableDocks() + "\t" + entry.getValue().getMaintainenceReq() + "\t  " + entry.getValue().getCapacity()
			+ "\t" + entry.getValue().getHasKiosk() + "\t" + entry.getValue().getName() + " - " + entry.getValue().getAddress());
		}
		System.out.println("");
	
	}
	
	
	/**
	 * Create a station as prompted by the user.
	 */
	public static void addStation() {
		newStation = new Station(0, null, 0, 0, 0, 0, 0, false, null);
		System.out.println("\nYou are about to add a new station. Please specify the following details for the new station:\n");
		while(true) {
			System.out.println("Station ID (00-99):");
			Scanner userInput = new Scanner(System.in);
			String inputID = userInput.nextLine();
			try {
				int parsedID = Integer.parseInt(inputID);
				if(!stationsMap.containsKey(parsedID)) {
					if(parsedID < 00 | parsedID > 99) {
						System.out.println("\nInvalid input ID: out of range (00-99). Please start over.");
						continue;
					} else {
						newStation.setID(parsedID);
					}
				}
				else {
					System.out.println("Invalid ID number: A station with that ID already exists. Please enter a valid ID.");
					continue;
				}
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Please start over.");
			}
			
			System.out.println("Station Name: ");
			String inputName = userInput.nextLine();
			newStation.setName(inputName);
			//System.out.println("The new station ID is: " + newStation.getID() + " and the name is " + newStation.getName());
			
			
			
			System.out.println("Specify the capacity for the new station (range: 0-20):");
			String inputCapacity = userInput.nextLine();
			try {
				int capacityParsed = Integer.parseInt(inputCapacity);
				//System.out.println("The capacity is: " + capacityParsed);
				if(capacityParsed > 20 | capacityParsed < 0) {
					System.out.println("Invalid capacity specified. Please start over.");
					continue;
				} else {
					newStation.setCapacity(capacityParsed);
				}
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Please start over.");
			}
			
			
			
			
			System.out.println("Enter the number of total bikes at this station (range: 0-" + newStation.getCapacity() + "): ");
			String inputBikes = userInput.nextLine();
			try {
				int bikesParsed = Integer.parseInt(inputBikes);
				//System.out.println("The bikes are: " + bikesParsed);
				if(bikesParsed > newStation.getCapacity()) {
					System.out.println("The number of bikes specified exceeds the capacity of the station. Please start over.\n");
					continue;
				} else if(bikesParsed < 0) {
					System.out.println("Invalid number of bikes entered. Please start over.\n");
					continue;
				} else {
					newStation.setBikes(bikesParsed);
				}
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Please start over.");
			}
			
			
			System.out.println("Enter the number of pedelacs (range: 0-" + (newStation.getCapacity() - newStation.getBikes()) +"): ");
			String inputPedelacs = userInput.nextLine();
			try {
				int pedelacsParsed = Integer.parseInt(inputPedelacs);
				if(pedelacsParsed > newStation.getCapacity() - newStation.getBikes()) {
					System.out.println("The number of pedelacs specified exceeds the available docks. Please start over.\n");
					continue;
				} else if(pedelacsParsed < 0) {
					System.out.println("Invalid number of pedelacs entered. Please start over.\n");
					continue;
				} else {
					newStation.setPedelacs(pedelacsParsed);
				}
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Please start over.");
			}
			
			newStation.setAvailableDocks(newStation.getCapacity() - (newStation.getBikes() + newStation.getPedelacs()));
			
			
			
			System.out.println("\nDoes the station have a kiosk?");
			String inputKiosk = userInput.nextLine();
			if(inputKiosk.equals("Y") || inputKiosk.equals("yes") || inputKiosk.equals("Yes") || inputKiosk.equals("YES")) {
				newStation.setHasKiosk(true);
			} else if(inputKiosk.equals("N") || inputKiosk.equals("no") || inputKiosk.equals("No") || inputKiosk.equals("NO")) {
				newStation.setHasKiosk(false);
			} else {
				System.out.println("\nInvalid input. Please start over.");
				continue;
			}
			
			
			System.out.println("Lastly, please enter the address for the new station:\n");
			String inputAddressString = userInput.nextLine();
			newStation.setAddress(inputAddressString);
			
			/*
			 * Assuming a new station doesn't require maintenance requests and hence setting them to 0.
			 */
			
			
			/*
			 * Printing all the specifications of the station designed by the user:
			 */
			System.out.println("This is the new station you will be adding to the list:\n" + "\nID: " + newStation.getID() + "\nName: " + 
			newStation.getName() + "\nCapacity: " + newStation.getCapacity() + 
				"\nNumber of Bikes: " + newStation.getBikes() + "\nNumber of Pedelacs: " + newStation.getPedelacs() + "\nNumber of Available Docks: " + 
			newStation.getAvailableDocks() + "\nNumber of Maintenance Requests: " + newStation.getMaintainenceReq() + "\nHas a kiosk: " + 
				newStation.getHasKiosk() + "\nAddress: " + newStation.getAddress() + "\n");
			
			/*
			 * Save this newly created station to the new stations list.
			 */
			newStationsList.add(newStation);
			
			break;	
		}
		return;	
	}
	
	public static void saveStation() {
		
	}
	
	public static void recordRide() {
		
	}
	
	public static void equalizeStations() {
		
	}
	
	
	
	/**
	 * Interpret the boolean given as argument and return a number.
	 * @param b - a boolean argument to be interpreted as a number
	 * @return a number: 0 if boolean is false or 1 if boolean is true
	 */
	public static int boolToInt(boolean b) {
		if(b) {
			return 1;
		} else {
			return 0;
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println("Welcome to the ValleyBike Simulator.");
		readData();
		Scanner userInput = new Scanner(System.in);
		try {
			while(true) {
				printMainMenu();
				System.out.println("\nPlease enter a number (0-6): ");
				String input = userInput.nextLine();

				if(input.compareTo("0") == 0) {
					System.out.println("\nThank you for using Valley Bike Simulator!");
					break;
				} else if(input.compareTo("1") == 0) {
					printStationList();	
				} else if(input.equals("2")) {
					addStation();
				} else if(input.equals("3")) {
					saveStation();
				} else if(input.equals("4")) {
					recordRide();
				} else if(input.equals("5")) {
					System.out.println("\nEnter the file name (including extension) of the file located in data-files:");
					String rideFile = userInput.nextLine();
					resolveRideData(rideFile);
				} else if(input.equals("6")) {
					equalizeStations();
				} else {
					System.out.println("\nInvalid input, please select a number within the 0-6 range.\n");
				}
			}
		} catch(Exception e){
			
		}
		
	}
	

}
