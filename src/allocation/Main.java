package allocation;

public class Main {

	public static void main(String[] args) {
		int size = 30;
		SimDisk sd = new SimDisk(size);

		// TO-DO! PROCESS INPUT FILE!

		// testing

		System.out.println("totBlock = " + size);
		sd.allocate("bob.txt", 5);
		sd.allocate("second hand.txt", 8);
		sd.allocate("third", 7);
		sd.printDirectory();
		// read "bob.txt"
		// read "second hand.txt"
		sd.deallocate("bob.txt");
		sd.printDirectory();
		sd.allocate("fourth in line.dat", 1);
		sd.allocate("fifth is big", 8);
		// read "fourth in line.dat"
		// read "fifth is big"
		sd.printDirectory();



	}
}


