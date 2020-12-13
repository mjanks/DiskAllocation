package allocation;

/* Created by Michael Janks
12/13/20
COSC 423
DISK ALLOCATION PROJECT EXTRA CREDIT
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SimDisk {
    HashMap allocatedList = new HashMap(); // K = index start of file, V = size of file
    HashMap freeList = new HashMap(); // K = index start of hole, V = size of hole
    HashMap directory = new HashMap(); // K = index start of file, V = fileName
    HashMap adjustedDirectory = new HashMap<String, String>(); // used for printing
    HashMap fileNumbers = new HashMap(); // K = fileName, V = number
    ArrayList keySet = new ArrayList();
    ArrayList matched = new ArrayList();
    boolean flag = false;
    int[] detailsArray;
    int[] temp;
    int totalHoles;
    int fatBlocks;
    int number = 1;
    int index;
    int size;
    int segmentSize = 1;
    int block;
    int count;
    int notAllocated = 0;
    int numMoves;
    int totMoves = 0;

    // TO-DO! IMPLEMENT READ FUNCTION

    public SimDisk(int s) {
        this.size = s;
    }

    public HashMap generateFreeList() {
        freeList = new HashMap();
        segmentSize = 1;
        for(int i=0; i < size; i++) {
            if(allocatedList.containsKey(i)) {
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
        return freeList;
    }

    public void contiguousAllocation(String fileName, int sizeOfFile) {
        if(sizeOfFile <= 0) {
            System.out.println("Size of file cannot be less than 1!");
            return;
        }
        // add to allocatedList
        if(allocatedList.isEmpty()) {
            allocatedList.put(0, sizeOfFile);
            directory.put(0, fileName);
            System.out.println("File " + fileName + " was added successfully");
            return;
        } else {
            // generate freeList
            freeList = generateFreeList();
            // *********** CONTIGUOUS ***********
            if(contiguous(fileName, sizeOfFile))
                return;
            /*
            // *********** FIRST FIT ***********
            if(firstFit(fileName, sizeOfFile))
                return;
             */
        }
        notAllocated++;
        System.out.println("File " + fileName + " was not added. Not enough space!");
    }

    public void indexedAllocation(String fileName, int sizeOfFile) {
        if(sizeOfFile <= 0) {
            System.out.println("Size of file cannot be less than 1!");
            return;
        }

        // add to allocatedList
        if(allocatedList.isEmpty()) {
            // calc FAT blocks
            sizeOfFile = calcFAT(sizeOfFile);
            allocatedList.put(0, sizeOfFile);
            directory.put(0, fileName);
            System.out.println("File " + fileName + " was added successfully");
            return;
        } else {
            // generate freeList
            freeList = generateFreeList();
            // *********** INDEXED ***********
            if(indexed(fileName, sizeOfFile))
                return;
            /*
            // *********** FIRST FIT ***********
            if(firstFit(fileName, sizeOfFile))
                return;
             */
        }
        notAllocated++;
        System.out.println("File " + fileName + " was not added. Not enough space!");
    }

    public void sort(int arr[]) {
        // Function to sort array using insertion sort
        // Referenced: https://www.geeksforgeeks.org/insertion-sort/
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;
            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    public void deallocate(String file) {
        boolean flag = false;
        for(int i=0; i < size; i++) {
            if(directory.get(i) != null)
                if(directory.get(i).equals(file)) {
                    allocatedList.remove(i);
                    directory.remove(i);
                    flag = true;
                    //System.out.println("File " + file + " was deleted successfully");
                    //return;
                }
        }
        if(flag) {
            System.out.println("File " + file + " was deleted successfully");
            return;
        } else {
            System.out.println("File " + file + " does not exist.");
        }
    }

    public void deallocateIndexed(String file) {

    }

    public void createAdjustedDirectory() {
        keySet = new ArrayList();
        adjustedDirectory = new HashMap();
        matched = new ArrayList();
        String blocks = "";

        // Referenced: https://stackoverflow.com/questions/10462819/get-keys-from-hashmap-in-java
        for ( Object key : allocatedList.keySet() ) {
            //System.out.println( key );
            keySet.add(key);
        }

        blocks = "";
        number = 1;
        for(int i=0; i < keySet.size(); i++) {
            blocks = "";
            block = (Integer) keySet.get(i);
            for(int j=0; j < (Integer) allocatedList.get(keySet.get(i)); j++) {
                blocks += block + " ";
                block++;
            }
            flag = false;
            for(int m=0; m < matched.size(); m++) {
                if(directory.get(keySet.get(i)).equals(matched.get(m))) {
                    flag = true;
                }
            }
            if(flag == false) {
                adjustedDirectory.put(directory.get(keySet.get(i)), blocks);
                blocks = "";
            }
            if(flag == false){
                for(int k=(i+1); k < directory.size(); k++) {
                    if(directory.get(keySet.get(i)).equals(directory.get(keySet.get(k)))) {
                        block = (Integer) keySet.get(k);
                        matched.add(directory.get(keySet.get(i)));
                        for(int l=0; l < (Integer) allocatedList.get(keySet.get(k)); l++) {
                            blocks += block + " ";
                            block++;
                        }
                    }
                }
            }
            if(!blocks.equals("")) {
                blocks += adjustedDirectory.get(directory.get(keySet.get(i)));
                adjustedDirectory.replace(directory.get(keySet.get(i)), blocks);
            }
        }
    }

    public void createFileNumbers() {
        number = 1;
        fileNumbers = new HashMap();
        keySet = new ArrayList();
        for ( Object key : adjustedDirectory.keySet() ) {
            keySet.add(key);
        }
        for(int i=0; i < adjustedDirectory.size(); i++) {
            fileNumbers.put(keySet.get(i), number);
            number++;
        }
    }

    public void createDetailsArray() {
        detailsArray = new int[size];
        number = 1;
        // need to parse the string from the adjustedDirectory
        // Referenced: https://www.javainterviewpoint.com/iterate-through-hashmap/
        Iterator keySetIterator = adjustedDirectory.keySet().iterator();
        while (keySetIterator.hasNext())
        {
            String key = (String) keySetIterator.next();
            String str = (String) adjustedDirectory.get(key);
            String[] tokens = str.split(" ");
            for(int i=0; i < tokens.length; i++) {
                detailsArray[Integer.parseInt(tokens[i])] = number;
            }
            number++;
        }
    }

    public void printDirectory() {
        keySet = new ArrayList();
        //detailsArray = new int[size];
        adjustedDirectory = new HashMap();
        matched = new ArrayList();
        System.out.println();
        System.out.println("============== Current Drive Contents =================");
        System.out.println();
        System.out.println("DIRECTORY:");
        createAdjustedDirectory();

        // print adjusted directory
        number = 1;
        keySet = new ArrayList();
        for ( Object key : adjustedDirectory.keySet() ) {
            keySet.add(key);
        }
        for(int i=0; i < adjustedDirectory.size(); i++) {
            System.out.print(number + ". " + keySet.get(i) + ", Block(s) ");
            System.out.print(adjustedDirectory.get(keySet.get(i)));
            System.out.println();
            number++;
        }
        System.out.println();

        System.out.println("DETAILS:");
        createDetailsArray();

        // print the details array
        for(int i=0; i < detailsArray.length; i++) {
            if((i % 10) == 0 && i != 0)
                System.out.println();
            if(detailsArray[i] == 0)
                System.out.print("* ");
            else
                System.out.print(detailsArray[i] + " ");
        }
        System.out.println();
        System.out.println();
    }

    public void printStats() {
        System.out.println();
        System.out.println("During this simulation,");
        System.out.println("Total head moves = " + totMoves);
        System.out.println("Total number of files not " +
                "allocated due to insufficient space = " + notAllocated);
    }

    public void read(String fileName) {
        numMoves = 0;
        createAdjustedDirectory();
        createFileNumbers();
        createDetailsArray();

        // calculate numMoves
        for(int i=0; i < detailsArray.length; i++) {

            if(detailsArray[i] == (Integer) fileNumbers.get(fileName)) {
                numMoves++;
            }
            while(detailsArray[i] == (Integer) fileNumbers.get(fileName)) {
                if(i == detailsArray.length-1) {
                    totMoves += numMoves;
                    System.out.println("File " + fileName + " was read successfully with " + numMoves + " head move(s)");
                    return;
                }
                i++;
            }
        }
        totMoves += numMoves;
        System.out.println("File " + fileName + " was read successfully with " + numMoves + " head move(s)");
    }

    public boolean contiguous(String fileName, int sizeOfFile) {
        temp = new int[size];
        count = 0;
        for(Object value: freeList.values()) {
            temp[count] = (Integer) value;
            count++;
        }
        sort(temp); // sort the holes smallest to largest
        // check if there's a perfect fit
        for(int i=0; i < size; i++) {
            if(freeList.get(i) != null) {
                if((Integer) freeList.get(i) == sizeOfFile){ // if perfect fit, allocate, done
                    allocatedList.put(i, sizeOfFile);
                    directory.put(i, fileName);
                    System.out.println("File " + fileName + " was added successfully");
                    return true;
                }
            }
        }

        // check next largest hole, and so on
        for(int i=0; i < temp.length; i++) {
            if(temp[i] != 0 && temp[i] > sizeOfFile) { // skip the zeros!
                for(int j=0; j < size; j++) {
                    if(freeList.get(j) != null) {
                        if((Integer) freeList.get(j) == temp[i]) {
                            allocatedList.put(j, sizeOfFile); // add to allocatedList
                            directory.put(j, fileName); // add to directory
                            System.out.println("File " + fileName + " was added successfully");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean indexed(String fileName, int sizeOfFile) {
        // find total number of holes
        totalHoles = 0;
        for(int i=0;i < size; i++) {
            if(freeList.get(i) != null) {
                totalHoles += (Integer) freeList.get(i);
            }
        }

        // calc FAT blocks
        sizeOfFile = calcFAT(sizeOfFile);

        // if sizeOfFile <= totHoles, able to allocate
        if(sizeOfFile <= totalHoles) {
            for (int i = 0; i < size; i++) {
                if (freeList.get(i) != null) {
                    if ((Integer) freeList.get(i) < sizeOfFile) {
                        allocatedList.put(i, freeList.get(i));
                        directory.put(i, fileName);
                        sizeOfFile = sizeOfFile - (Integer) freeList.get(i);
                    } else {
                        allocatedList.put(i, sizeOfFile);
                        directory.put(i, fileName);
                        System.out.println("File " + fileName + " was added successfully");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int calcFAT(int sizeOfFile) {
        fatBlocks = 0;
        for(int i=0; i < sizeOfFile; i++) {
            if((i % 7) == 0) {
                fatBlocks++;
            }
        }
        return sizeOfFile + fatBlocks;
    }

    // method not used in final simulation but helped as a jumping off point
    public boolean firstFit(String fileName, int sizeOfFile) {
        for(int i=0; i < size; i++) {
            if(freeList.get(i) != null) {
                if((Integer) freeList.get(i) >= sizeOfFile){
                    allocatedList.put(i, sizeOfFile);
                    directory.put(i, fileName); // add to directory
                    System.out.println("File " + fileName + " was added successfully");
                    return true;
                }
            }
        }
        return false;
    }
}