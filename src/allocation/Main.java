package allocation;

public class Main {

	public static void main(String[] args) {
		int size = 10;
		SimDisk sd = new SimDisk(size);

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

//		sd.allocate("mike.txt", 3);
//		sd.printAllocatedList();
//		sd.printDirectory();
//		sd.deallocate("text.dat");
//		sd.printAllocatedList();
//		sd.printDirectory();
//		sd.allocate("new.dat", 2);
//		sd.printAllocatedList();
//		sd.printDirectory();
//		sd.allocate("sixth.dat", 6);
//		sd.printAllocatedList();
//		sd.printDirectory();
//		sd.deallocate("taylor.txt");
//		sd.printAllocatedList();
//		sd.printDirectory();
//		sd.allocate("100.dat", 2);
//		sd.printAllocatedList();
//		sd.printDirectory();

	}
}


