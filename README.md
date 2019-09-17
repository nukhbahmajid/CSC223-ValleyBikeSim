# CSC223-ValleyBikeSim

CSC223 Intro to Software Engineering 
Assignment 1 - Programming (Due: Sep 17 @10am)
Group Members:
Nukhbah Majid
Gin Chen
Ratidzo Vushe
Wenyue (Suzie) Xi

We designed the ValleyBike Project to fulfill functionalities further explained below:

1. Quit Program.
Allows the user to quit the program by processing the user input and quitting the program if user inputs "0" after menu is displayed. 

2. View station list.
Information displayed is sorted by ID (by the help of a TreeMap) and any new station data added is shown as well, call to function **printStationList()**.
All information is converted back to strings so that it can be appended to the CSV

3. Add station.
Allows the user to enter new Station Data into the system. We set various input parameters to ensure that entered data is within the right ranges by handling all edge cases that could throw exceptions and are considered invalid inputs. A new Station object is made with user prompted data. Call to function **addStation()**
This data is stored and can be seen when the user choses the option to 'View Station List'.


4. Save station list.
We used a CSV Writer to implement saving of the station. This overides current data and gives the user the option to save any entered information. This entails that all updated vehicle data after recorded rides as well as new Stations are saved to the CSV file. Call to function **saveStationList()**.


5. Record ride.
We record a ride that a user has currently taken by requiring information such as the start station ID, transportation, and destination ID from the user. Simulator throws exceptions when the user doesn't enter appropriate input. Call to function **recordRide()**.

6. Resolve ride data.
Manages to read the ride data, record all the rides in the ridesList and then calculates the average ride duration and displays the message informing the user how many rides there were and what the average duration was for the whole ride data. Call to function **resolveRideData()**.
 
7. Equalize stations.
Equalize the station based on the percentages of bike/pedelecs per dock capacity. If there're extra bikes/pedelecs left after the equalization, we assign them randomly to some stations. Call to function **equalizeStations()**

# Summary: 

For this ValleyBike Project, we designed to fulfill several functionalities, including quit program, view station list, add station, save station list, record ride, resolve ride data, and equalize stations. The class Ride is designed to record various features of each ride, and the Station class record various qualities of each station, such as number of pedelacs, capacity, address, etc. The ride and station data were read with the help of **readData()** and were efficiently stored as lists of objects Ride and Station repectively. This was necessary so that each row of a particular data file - whether station data or ride data - could be stored as a single entity but the attributes needed to be of different data types (e.g. number of bikes was supoosed to be saved as an integer, kiosk as a boolean, address as a String etc.) and hence forming specific Ride/Station objects for each entry of the data file was deemed efficient and feasible.  
 
In the class ValleyBikeSim, the program first reads data from station-data.csv, and stores the information in a TreeMap, because keys of a TreeMap (station IDs) are sorted and we can get access to every station (TreeMap values) using keys. Thus, it'll be easier for us to print out a sorted station list using a TreeMap. Then, the program reads and stores the ride data in a TreeMap. The idea is the same as storing the station data in a TreeMap since we can use user IDs as keys and the corresponding ride data will be the value. This program executes addStation by promoting users to answer a series of questions related to the requirement of added station and will remind users to enter the correct and valid data if exceptions appear. In the saveStation section, we overwrite the station-data csv file with the new station list every time the user chooses to save station. This program executes the resolveRideData by reading in the ride data, recording the number of its users by counting the row number, and getting the average time duration by calculating the average of all valid rides' duration time. In the end, this program equalizes station by calculating the percentage of bike/pedelecs per doc capacity and assigning the right amount of bikes/pedelecs to each station according to that percentage. If there're extra bikes/pedelecs (since we round down the ratio), we assign them randomly to some stations.


# Citations:
1. the method SaveStationList() was implemented with help from : https://stackoverflow.com/questions/42418327/how-append-a-row-line-to-an-existing-csv-file-using-opencsv-in-java

2. Documentation referred to in order to use the OpenCSV API and thereby use CSVReader and CSVWriter: http://opencsv.sourceforge.net/
