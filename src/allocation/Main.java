package allocation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int totBlock;
		String lineOfData;
		String command = "";

		// Process input and run simulations
		File file = new File("disk.dat");
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException: " + e);
		}
		System.out.println("************ START CONTIGUOUS ALLOCATION ************");
		totBlock = Integer.parseInt(scan.next());
		SimDisk sd = new SimDisk(totBlock);
		System.out.println("totBlock = " + totBlock);
		//SimDisk sd2 = new SimDisk(totBlock);

		while(scan.hasNextLine()){
			lineOfData = scan.nextLine();
			String[] tokens = lineOfData.split("\"");
			for(int i=0; i < tokens.length; i++) {
				tokens[i] = tokens[i].trim();
			}
			command = tokens[0];
			switch(command) {
				case "add":
					sd.contiguousAllocation(tokens[1], Integer.parseInt(tokens[2]));
					break;
				case "del":
					sd.deallocate(tokens[1]);
					break;
				case "print":
					sd.printDirectory();
					break;
				case "read":
					System.out.println("NOT YET IMPLEMENTED");
					break;
			}
		}
		scan.close();
		System.out.println("************ CONTIGUOUS ALLOCATION STATS ************");
		sd.printStats();
		System.out.println();
		System.out.println("************ END OF CONTIGUOUS ALLOCATION ************");

		file = new File("disk.dat");
		scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException: " + e);
		}
		System.out.println("************ START INDEXED ALLOCATION ************");
		totBlock = Integer.parseInt(scan.next());
		SimDisk sd2 = new SimDisk(totBlock);
		System.out.println("totBlock = " + totBlock);

		while(scan.hasNextLine()){
			lineOfData = scan.nextLine();
			String[] tokens = lineOfData.split("\"");
			for(int i=0; i < tokens.length; i++) {
				tokens[i] = tokens[i].trim();
			}
			command = tokens[0];
			switch(command) {
				case "add":
					sd2.indexedAllocation(tokens[1], Integer.parseInt(tokens[2]));
					break;
				case "del":
					sd2.deallocate(tokens[1]);
					break;
				case "print":
					sd2.printDirectory();
					break;
				case "read":
					System.out.println("NOT YET IMPLEMENTED");
					break;
			}
		}
		scan.close();
		System.out.println("************ INDEXED ALLOCATION STATS ************");
		sd.printStats();
		System.out.println();
		System.out.println("************ END OF INDEXED ALLOCATION ************");

		// testing

//		System.out.println("************ START CONTIGUOUS ALLOCATION ************");
//		System.out.println("totBlock = " + totBlock);
//		sd.contiguousAllocation("bob.txt", 5);
//		sd.contiguousAllocation("second hand.txt", 8);
//		sd.contiguousAllocation("third", 7);
//		sd.printDirectory();
//		// read "bob.txt"
//		// read "second hand.txt"
//		sd.deallocate("bob.txt");
//		sd.printDirectory();
//		sd.contiguousAllocation("fourth in line.dat", 1);
//		sd.contiguousAllocation("fifth is big", 8);
//		// read "fourth in line.dat"
//		// read "fifth is big"
//		sd.printDirectory();
//		System.out.println("************ CONTIGUOUS ALLOCATION STATS ************");
//		sd.printStats();
//		System.out.println();
//		System.out.println("************ END OF CONTIGUOUS ALLOCATION ************");
//
//		System.out.println("************ START INDEXED ALLOCATION ************");
//		System.out.println("totBlock = " + totBlock);
//		sd2.indexedAllocation("bob.txt", 5);
//		sd2.indexedAllocation("second hand.txt", 8);
//		sd2.indexedAllocation("third", 7);
//		sd2.printDirectory();
//		// read "bob.txt"
//		// read "second hand.txt"
//		sd2.deallocate("bob.txt");
//		sd2.printDirectory();
//		sd2.indexedAllocation("fourth in line.dat", 1);
//		sd2.indexedAllocation("fifth is big", 8);
//		sd2.indexedAllocation("too big.dat", 1);
//		// read "fourth in line.dat"
//		// read "fifth is big"
//		sd2.printDirectory();
//		System.out.println("************ INDEXED ALLOCATION STATS ************");
//		sd2.printStats();
//		System.out.println();
//		System.out.println("************ END OF INDEXED ALLOCATION ************");
	}
}


