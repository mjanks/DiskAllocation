package allocation;

import java.util.ArrayList;
import java.util.HashMap;

public class SimDisk {
    HashMap allocatedList = new HashMap(); // K = index start of file, V = size of file
    HashMap freeList = new HashMap(); // K = index start of free segment, V = size of free segment
    HashMap directory = new HashMap(); // K = index start of file, V = fileName
    int number = 1;
    int index;
    int size;
    int count = 1;

    public SimDisk(int s) {
        this.size = s;
    }

    public void allocate(String fileName, int sizeOfFile) {
        // if enough space, if there's a large enough hole for the file to fit,
        // add it to allocatedList AND directory

        // add to allocatedList
        if(allocatedList.isEmpty()) {
            allocatedList.put(0, sizeOfFile);
            // add to directory
            directory.put(0, fileName);
        } else { // allocatedList NOT empty, need to check some things
            // are there any holes? how big are they?
            // check for hole

            freeList = new HashMap();
            count = 1;
            for(int i=0; i < size+1; i++) {
                if(allocatedList.containsKey(i)) {
                    i += (Integer) allocatedList.get(i) - 1; // jump to end of this file
                    count = 1;
                } else if(count == 1){
                    freeList.put(i, count);
                    index = i;
                    count++;
                } else {
                    freeList.put(index, count++);
                }
            }
            System.out.println(freeList);

            // algorithm to decide where to allocate the file
            for(int i=0; i < size; i++) {
                if(freeList.get(i) != null) {
                    if((Integer) freeList.get(i)  > sizeOfFile){ // simple, if a large enough space found, add the file
                        allocatedList.put(i, sizeOfFile);
                        directory.put(i, fileName); // add to directory
                        return;
                    }
                }
            }





        }
    }

    public void deallocate(String file) {
        // deallocate, remove from allocatedList
        System.out.println("*******************************************");
        System.out.println(allocatedList);
        System.out.println(directory);
        for(int i=0; i < size; i++) {
            if(directory.get(i) != null)
                if(directory.get(i).equals(file)) {
                    allocatedList.remove(i);
                    directory.remove(i);
                }
        }

    }

    public void printAllocatedList() {
        number = 1;
        for(int i=0; i < size; i++) {
            if(allocatedList.containsKey(i)) {
                for(int j=0; j < (Integer) allocatedList.get(i); j++) {
                    System.out.print(number + " ");
                }
                i += (Integer) allocatedList.get(i) - 1;
                number++;
            } else {
                System.out.print("* ");
            }
        }
        System.out.println();
    }

    public void printDirectory() {
        System.out.println("DIRECTORY:");
        System.out.println(directory);
        for(int i=0; i < size; i++) {
            if(directory.get(i) != null) {
                System.out.print(directory.get(i) + " ");
                System.out.println("Blocks: "); // TO-DO! NEED TO IMPLEMENT!
            }
        }
    }



}
