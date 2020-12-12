package allocation;

public class Main {

	public static void main(String[] args) {
		int size = 10;
		SimDisk sd = new SimDisk(size);

		// TO-DO! PROCESS INPUT FILE!

		// testing
		sd.allocate("one.dat", 1);
		sd.printAllocatedList();
		sd.printDirectory();
		sd.allocate("two.dat", 2);
		sd.printAllocatedList();
		sd.printDirectory();
		sd.allocate("three.dat", 3);
		sd.printAllocatedList();
		sd.printDirectory();
		sd.allocate("four.dat", 4);
		sd.printAllocatedList();
		sd.printDirectory();

		sd.deallocate("one.dat");
		sd.printAllocatedList();
		sd.printDirectory();

		sd.deallocate("three.dat");
		sd.printAllocatedList();
		sd.printDirectory();

		sd.allocate("five.dat", 5);
		sd.printAllocatedList();
		sd.printDirectory();

		sd.allocate("six.dat", 2);
		sd.printAllocatedList();
		sd.printDirectory();

		sd.allocate("seven.dat", 1);
		sd.printAllocatedList();
		sd.printDirectory();

		sd.allocate("eight.dat", 1);
		sd.printAllocatedList();
		sd.printDirectory();

		sd.deallocate("four.dat");
		sd.printAllocatedList();
		sd.printDirectory();

		sd.deallocate("six.dat");
		sd.printAllocatedList();
		sd.printDirectory();

		sd.deallocate("seven.dat");
		sd.printAllocatedList();
		sd.printDirectory();

		sd.allocate("nine.dat", 5);
		sd.printAllocatedList();
		sd.printDirectory();

		sd.allocate("ten.dat", 3);
		sd.printAllocatedList();
		sd.printDirectory();

		sd.allocate("eleven.dat", 2);
		sd.printAllocatedList();
		sd.printDirectory();

		sd.allocate("twelve.dat", 1);
		sd.printAllocatedList();
		sd.printDirectory();

		sd.allocate("thirteen.dat", 0);
		sd.printAllocatedList();
		sd.printDirectory();

		sd.allocate("fourteen.dat", 0);
		sd.printAllocatedList();
		sd.printDirectory();

	}
}


