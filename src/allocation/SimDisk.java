package allocation;

import java.util.ArrayList;
import java.util.HashMap;

public class SimDisk {
    HashMap allocatedList = new HashMap(); // K = index start of file, V = size of file
    HashMap freeList = new HashMap(); // K = index start of free segment, V = size of free segment
    HashMap directory = new HashMap(); // K = index start of file, V = fileName
    ArrayList keySet = new ArrayList();
    int number = 1;
    int index;
    int size;
    int segmentSize = 1;
    int block;

    public SimDisk(int s) {
        this.size = s;
    }

    public void allocate(String fileName, int sizeOfFile) {
        if(sizeOfFile <= 0) {
            System.out.println("Size of file cannot be zero ore less!");
            return;
        }
        // if enough space, if there's a large enough hole for the file to fit,
        // add it to allocatedList AND directory

        // add to allocatedList
        if(allocatedList.isEmpty()) {
            allocatedList.put(0, sizeOfFile);
            directory.put(0, fileName);
            System.out.println("Allocate called. List isEmpty.");
            return;
        } else { // allocatedList NOT empty, need to check some things
            // are there any holes? how big are they?
            // check for hole

            freeList = new HashMap();
            segmentSize = 1;
            for(int i=0; i < size; i++) {
                if(allocatedList.containsKey(i)) {
                    System.out.println(allocatedList.get(i));
                    System.out.println("allocated list contains key!" + allocatedList);
                    i += (Integer) allocatedList.get(i) - 1; // jump to end of this file
                    segmentSize = 1;
                } else if (segmentSize == 1) {
                    freeList.put(i, segmentSize);
                    segmentSize++;
                    index = i;
                }else {
                    freeList.replace(index, segmentSize);
                    segmentSize++;
                }
            }
            System.out.println("FREELIST in method: " + freeList);

            // algorithm to decide where to allocate the file
            for(int i=0; i < size; i++) {
                if(freeList.get(i) != null) {
                    if((Integer) freeList.get(i) >= sizeOfFile){ // simple, if a large enough space found, add the file
                        allocatedList.put(i, sizeOfFile);
                        directory.put(i, fileName); // add to directory
                        System.out.println("Allocate called. " + fileName + " allocated to disk.");
                        return;
                    }
                }
            }
        }
        System.out.println("Allocate called. " + fileName + " was not allocated. Not enough space!");
    }

    public void deallocate(String file) {
        // deallocate, remove from allocatedList
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
        System.out.println("ALLOCATED LIST:");
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
        System.out.println("ALLOCATED LIST: " + allocatedList);
        System.out.println();
    }

    public void printDirectory() {
        System.out.println("DIRECTORY:");
        System.out.println(directory);
        keySet = new ArrayList();

        // Referenced: https://stackoverflow.com/questions/10462819/get-keys-from-hashmap-in-java
        for ( Object key : allocatedList.keySet() ) {
            //System.out.println( key );
            keySet.add(key);
        }

        //System.out.println("keySet size: " + keySet.size());
        for(int i=0; i < keySet.size(); i++) {
            System.out.print("Name of file: " + directory.get(keySet.get(i)) + " Blocks: ");
            block = (Integer) keySet.get(i);
            for(int j=0; j < (Integer) allocatedList.get(keySet.get(i)); j++) {
                System.out.print(block + " ");
                block++;
            }
            System.out.println();
        }

//        for(int i=0; i < directory.size(); i++) {
//            if(directory.get(i) != null) {
//                System.out.print(directory.get(i) + " ");
//                System.out.println("Blocks: "); // TO-DO! NEED TO IMPLEMENT!
//                for(int j=0; j < keySet.size(); j++) {
//                    System.out.println();
//                }
//            }
//        }
        System.out.println();
    }

    public void printFreeList() {
        System.out.println("FREELIST:");
        System.out.println(freeList);
        System.out.println();
    }

}
