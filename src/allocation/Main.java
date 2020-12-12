package allocation;

public class Main {

	public static void main(String[] args) {
		int size = 30;
		SimDisk sd = new SimDisk(size);

		// TO-DO! PROCESS INPUT FILE!

		// testing

		System.out.println("totBlock = " + size);
		sd.allocate("a.dat", 5);
		sd.printDirectory();
		//sd.printAllocatedList();

		sd.allocate("b.dat", 7);
		sd.printDirectory();
		//sd.printAllocatedList();

		sd.deallocate("a.dat");
		sd.printDirectory();
		//sd.printAllocatedList();

		sd.allocate("c.dat", 2);
		sd.printDirectory();
		//sd.printAllocatedList();

		sd.allocate("z.dat", 4);
		sd.printDirectory();
		//sd.printAllocatedList();

		sd.allocate("y.dat", 3);
		sd.printDirectory();
		//sd.printAllocatedList();

		sd.allocate("x.dat", 2);
		sd.printDirectory();

		sd.allocate("p.dat", 2);
		sd.printDirectory();
		sd.allocate("q.dat", 2);
		sd.printDirectory();
		sd.deallocate("z.dat");
		sd.printDirectory();
		sd.deallocate("p.dat");
		sd.printDirectory();
		sd.allocate("r.dat", 2);
		sd.printDirectory();

		//sd.printAllocatedList();
//
//		sd.allocate("d", 5);
//		sd.printDirectory();
//		sd.printAllocatedList();

	}
}


