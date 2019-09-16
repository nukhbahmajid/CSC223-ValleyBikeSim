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
	
	public static List<Station> stationWithAvailableDocks;
	public static List<Integer> stationId;
	
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
		System.out.println("\nID\tBikes\tPedelecs AvDocs\tMainReq\t  Cap\tKiosk\tName - Address\n");
		for(Map.Entry<Integer, Station> entry: stationsMap.entrySet()) {
			System.out.println(entry.getKey() + "\t" + entry.getValue().getBikes() + "\t" + entry.getValue().getPedelecs()
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
			
			
			
			
			System.out.println("Specify the capacity for the new station (range: 0-20):");
			String inputCapacity = userInput.nextLine();
			try {
				int capacityParsed = Integer.parseInt(inputCapacity);
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
			
			
			System.out.println("Enter the number of pedelecs (range: 0-" + (newStation.getCapacity() - newStation.getBikes()) +"): ");
			String inputPedelecs = userInput.nextLine();
			try {
				int pedelecsParsed = Integer.parseInt(inputPedelecs);
				if(pedelecsParsed > newStation.getCapacity() - newStation.getBikes()) {
					System.out.println("The number of pedelecs specified exceeds the available docks. Please start over.\n");
					continue;
				} else if(pedelecsParsed < 0) {
					System.out.println("Invalid number of pedelecs entered. Please start over.\n");
					continue;
				} else {
					newStation.setPedelecs(pedelecsParsed);
				}
			} catch(NumberFormatException e) {
				System.out.println("Invalid input. Please start over.");
			}
			
			newStation.setAvailableDocks(newStation.getCapacity() - (newStation.getBikes() + newStation.getPedelecs()));
			
			
			
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
				"\nNumber of Bikes: " + newStation.getBikes() + "\nNumber of Pedelecs: " + newStation.getPedelecs() + "\nNumber of Available Docks: " + 
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
	
	public static List<Integer> stationID() {
		stationId = new ArrayList<>();
		
		for (Station station : stationsList) {
			stationId.add(station.getID());
		}
		
		return stationId;
	}
	
	public static List<Station> availableStation() {
		stationWithAvailableDocks = new ArrayList<>();
		
		for (Station station : stationsList) {
			if (station.getAvailableDocks() > 0) {
				stationWithAvailableDocks.add(station);
			}
		}
		
		return stationWithAvailableDocks;
	}
	
	public static void recordRide() {
		Scanner input = new Scanner(System.in);
		boolean error = true;
		int start = 0;
		Station startStation = new Station(0, null, 0, 0, 0, 0, 0, false, null);
		String transportation = null;
		int end = 0;
		Station endStation = new Station(0, null, 0, 0, 0, 0, 0, false, null);
		stationId = stationID();
		
		//what's the start station
		while (error) {
			System.out.println("Which station did you start from (station ID)? ");
			try {
				start = Integer.parseInt(input.nextLine());
				error = false;
			}
			catch (NumberFormatException nfe) {
				System.out.println("Please enter a valid integer ID.");
			}
		
		}
		
		while (! stationId.contains(start)) {
			try {
				System.out.println("Please enter an existing station ID: ");
				start = Integer.parseInt(input.nextLine());
			}
			catch (NumberFormatException nfe) {
				System.out.println(nfe.getMessage());
			}
		}
		
		startStation = stationsMap.get(start);
		
		//what's the transportation
		error = true;
		while (error) {
			System.out.println("Which transportation did you use (bike or pedelec)? ");
			transportation = input.nextLine();
			
			if (transportation.toLowerCase().equals("bike")) {
				if (startStation.getBikes() <= 0) {
					System.out.println("There's no bike at the start station. Please start over and enter the correct start station ID and the transportation.");
					while (error) {
						System.out.println("Which station did you start from (station ID)? ");
						try {
							start = Integer.parseInt(input.nextLine());
							error = false;
						}
						catch (NumberFormatException nfe) {
							System.out.println("Please enter a valid integer ID.");
						}
					
					}
					
					while (! stationId.contains(start)) {
						try {
							System.out.println("Please enter an existing station ID: ");
							start = Integer.parseInt(input.nextLine());
						}
						catch (NumberFormatException nfe) {
							System.out.println(nfe.getMessage());
						}
					}
					
					startStation = stationsMap.get(start);
					error = true;
					continue;
				}
				startStation.setBikes(startStation.getBikes()-1);
				startStation.setAvailableDocks(startStation.getAvailableDocks()+1);
				error = false;
			}
			
			else if (transportation.toLowerCase().equals("pedelec")) {
				if (startStation.getPedelecs() <= 0) {
					System.out.println("There's no pedelec at the start station. Please start over and enter the correct start station ID and the transportation you used.");
					while (error) {
						System.out.println("Which station did you start from (station ID)? ");
						try {
							start = Integer.parseInt(input.nextLine());
							error = false;
						}
						catch (NumberFormatException nfe) {
							System.out.println("Please enter a valid integer ID.");
						}
					
					}
					
					while (! stationId.contains(start)) {
						try {
							System.out.println("Please enter an existing station ID: ");
							start = Integer.parseInt(input.nextLine());
						}
						catch (NumberFormatException nfe) {
							System.out.println(nfe.getMessage());
						}
					}
					
					startStation = stationsMap.get(start);
					error = true;
					continue;
				}
				startStation.setPedelecs(startStation.getPedelecs() - 1);
				startStation.setAvailableDocks(startStation.getAvailableDocks()+1);
				error = false;
				
			}
			
			else {
				System.out.println("Please enter either 'bike' or 'pedelec'.");
			}
		}
		
		//what's the destination
		error = true;
		while (error) {
			System.out.println("Where's your destination (station ID)? ");
			try {
				end = Integer.parseInt(input.nextLine());
				error = false;
			}
			catch (NumberFormatException nfe) {
				System.out.println("Please enter a valid integer ID.");
			}
			
		}
		
		while (! stationId.contains(end)) {
			try {
				System.out.println("Please enter an existing station ID: ");
				end = Integer.parseInt(input.nextLine());
			}
			catch (NumberFormatException nfe) {
				System.out.println(nfe.getMessage());
			}
		}
		
		endStation = stationsMap.get(end);
		
		while (endStation.getAvailableDocks() <= 0) {
			System.out.println("Sorry, there's no available dock for you to return.");
			System.out.println("\n" + "Here's a list of stations that have available docks: ");
			System.out.println("ID" + "\t" + "Bikes" + "\t" + "Pedelecs" + "\t" + "AvDocs"
	    			+ "\t" + "MainReq" + "\t" + "Cap" + "\t" + "Kiosk" + "\t" + "Name - Address");
			
			stationWithAvailableDocks = availableStation();
			for (Station station : stationWithAvailableDocks) {
				station.printStation();
			}
			
			error = true;
			while (error) {
				System.out.println("Please choose an available station to return (station ID): ");
				try {
					end = Integer.parseInt(input.nextLine());
					error = false;
				}
				catch (NumberFormatException nfe) {
					System.out.println("Please enter a valid integer ID.");
				}
			}
			
			while (!stationId.contains(end)) {
				try {
					System.out.println("Please enter an existing station ID: ");
					end = Integer.parseInt(input.nextLine());
				}
				catch (NumberFormatException nfe) {
					System.out.println(nfe.getMessage());
				}
			}
			
			endStation = stationsMap.get(end);
		}
		
		System.out.println("You've recorded your ride successfully!\n");
		if (transportation.toLowerCase().equals("bike")) {
			endStation.setBikes(endStation.getBikes()+1);
		} else {
			endStation.setPedelecs(endStation.getPedelecs()+1);
		}
		endStation.setAvailableDocks(endStation.getAvailableDocks()-1);

	}

	
	public static void equalizeStations() {	
        int totalBikes = 0;
		for (Station station : stationsList) {
			totalBikes += station.getBikes();
		}
		
        int totalPedelecs = 0;
		for (Station station : stationsList) {
			totalPedelecs += station.getPedelecs();
		}
		
        int totalCapacity = 0;		
		for (Station station : stationsList) {
			totalCapacity += station.getCapacity();
		}
		
		for (Station station : stationsList) {
			int bikes = station.getBikes();
			int capacity = station.getCapacity();
			bikes = Math.round(capacity * totalBikes / totalCapacity);
			station.setBikes(bikes);
		}
		
		for (Station station : stationsList) {
			int pedelecs = station.getPedelecs();
			int capacity = station.getCapacity();
			pedelecs = Math.round(capacity * totalPedelecs / totalCapacity);
			station.setPedelecs(pedelecs);
		}
		
		//what if after equalizing, the number of bikes isn't the same
		int nowTotalBikes = 0;
		for (Station station : stationsList) {
			nowTotalBikes += station.getBikes();
		}
		
		if (nowTotalBikes != totalBikes) {
			int difference = totalBikes - nowTotalBikes;
			for (int i = 0; i < difference; i++) {
				int bikes = stationsList.get(i).getBikes() + 1;
				stationsList.get(i).setBikes(bikes);
			}
		}
		
		//what if the number of pedelecs isn't the same
		int nowTotalPedelecs = 0;
		for (Station station : stationsList) {
			nowTotalPedelecs += station.getPedelecs();
		}
		
		if (nowTotalPedelecs != totalPedelecs) {
			int difference = totalPedelecs - nowTotalPedelecs;
			for (int i = 0; i < difference; i++) {
				int pedelecs = stationsList.get(i).getPedelecs() + 1;
				stationsList.get(i).setPedelecs(pedelecs);
			}
		}
		
		for (Station station : stationsList) {
			int aDocs = station.getAvailableDocks();
			aDocs = station.getCapacity() - station.getBikes() - station.getPedelecs();
			station.setAvailableDocks(aDocs);
		}
		
		System.out.println("\n" + "Equalization completed! Choose 'View station list' in menu to view the station.\n");
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
