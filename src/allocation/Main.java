package allocation;

public class Main {

	public static void main(String[] args) {
		int size = 30;
		SimDisk sd = new SimDisk(size);
		SimDisk sd2 = new SimDisk(size);

		// TO-DO! PROCESS INPUT FILE "disk.dat"

		// testing
		System.out.println("************ START CONTIGUOUS ALLOCATION ************");
		System.out.println("totBlock = " + size);
		sd.contiguousAllocation("bob.txt", 5);
		sd.contiguousAllocation("second hand.txt", 8);
		sd.contiguousAllocation("third", 7);
		sd.printDirectory();
		// read "bob.txt"
		// read "second hand.txt"
		sd.deallocate("bob.txt");
		sd.printDirectory();
		sd.contiguousAllocation("fourth in line.dat", 1);
		sd.contiguousAllocation("fifth is big", 8);
		// read "fourth in line.dat"
		// read "fifth is big"
		sd.printDirectory();
		System.out.println("************ END OF CONTIGUOUS ALLOCATION ************");

		System.out.println();
		System.out.println("************ START INDEXED ALLOCATION ************");
		System.out.println("totBlock = " + size);
		sd2.indexedAllocation("bob.txt", 5);
		sd2.indexedAllocation("second hand.txt", 8);
		sd2.indexedAllocation("third", 7);
		sd2.printDirectory();
		// read "bob.txt"
		// read "second hand.txt"
		sd2.deallocate("bob.txt");
		sd2.printDirectory();
		sd2.indexedAllocation("fourth in line.dat", 1);
		sd2.indexedAllocation("fifth is big", 8);
		// read "fourth in line.dat"
		// read "fifth is big"
		sd2.printDirectory();
		System.out.println("************ END OF INDEXED ALLOCATION ************");
	}
}


