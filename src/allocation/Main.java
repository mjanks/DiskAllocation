package allocation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int totBlock;
		String lineOfData;
		String command = "";

		// Contiguous Allocation
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
					sd.read(tokens[1]);
					break;
			}
		}
		scan.close();
		System.out.println("************ CONTIGUOUS ALLOCATION STATS ************");
		sd.printStats();
		System.out.println();
		System.out.println("************ END OF CONTIGUOUS ALLOCATION ************");

		// Indexed Allocation
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
					sd2.read(tokens[1]);
					break;
			}
		}
		scan.close();
		System.out.println("************ INDEXED ALLOCATION STATS ************");
		sd2.printStats();
		System.out.println();
		System.out.println("************ END OF INDEXED ALLOCATION ************");
	}
}


