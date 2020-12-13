package allocation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String inputFile = "disk.dat"; // ** INPUT FILE TO RUN SIMULATIONS ON **
		boolean proceed = true;
		int totBlock = 0;
		String lineOfData;
		String command = "";

		// Contiguous Allocation
		File file = new File(inputFile);
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException: " + e);
			proceed = false;
		}
		try {
			if(proceed)
				totBlock = Integer.parseInt(scan.next());
		} catch (NumberFormatException e) {
			System.out.println("NumberFormatException: " + e);
			proceed = false;
		}
		if(proceed) {
			System.out.println("************ START CONTIGUOUS ALLOCATION ************");
			SimDisk sd = new SimDisk(totBlock);
			System.out.println("totBlock = " + totBlock);
			while(scan.hasNextLine()){
				lineOfData = scan.nextLine();
				String[] tokens = lineOfData.split("\"");
				for(int i=0; i < tokens.length; i++) {
					tokens[i] = tokens[i].trim();
				}
				command = tokens[0];
				if(command.equals("add") || command.equals("del") || command.equals("print")
						|| command.equals("read")) {
					switch(command) {
						case "add":
							try {
								sd.contiguousAllocation(tokens[1], Integer.parseInt(tokens[2]));
							} catch(NumberFormatException e) {
								System.out.println("NumberFormatException: " + e);
							}
							break;
						case "del":
							sd.deallocate(tokens[1]);
							break;
						case "print":
							sd.printDirectory();
							break;
						case "read":
							sd.read(tokens[1]);
							break;
					}
				} else {
					System.out.println("Invalid command. Check input file.");
				}

			}
			scan.close();
			System.out.println("************ CONTIGUOUS ALLOCATION STATS ************");
			sd.printStats();
			System.out.println();
			System.out.println("************ END OF CONTIGUOUS ALLOCATION ************");

			// Indexed Allocation
			file = new File(inputFile);
			scan = null;
			try {
				scan = new Scanner(file);
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException: " + e);
			}
			try {
				totBlock = Integer.parseInt(scan.next());
			} catch (NumberFormatException e) {
				System.out.println("NumberFormatException: " + e);
			}
			System.out.println("************ START INDEXED ALLOCATION ************");
			SimDisk sd2 = new SimDisk(totBlock);
			System.out.println("totBlock = " + totBlock);
			while(scan.hasNextLine()){
				lineOfData = scan.nextLine();
				String[] tokens = lineOfData.split("\"");
				for(int i=0; i < tokens.length; i++) {
					tokens[i] = tokens[i].trim();
				}
				command = tokens[0];
				if(command.equals("add") || command.equals("del") || command.equals("print")
						|| command.equals("read")) {
					switch(command) {
						case "add":
							try {
								sd2.contiguousAllocation(tokens[1], Integer.parseInt(tokens[2]));
							} catch(NumberFormatException e) {
								System.out.println("NumberFormatException: " + e);
							}
							break;
						case "del":
							sd2.deallocate(tokens[1]);
							break;
						case "print":
							sd2.printDirectory();
							break;
						case "read":
							sd2.read(tokens[1]);
							break;
					}
				} else {
					System.out.println("Invalid command. Check input file.");
				}
			}
			scan.close();
			System.out.println("************ INDEXED ALLOCATION STATS ************");
			sd2.printStats();
			System.out.println();
			System.out.println("************ END OF INDEXED ALLOCATION ************");
		}



	}
}


