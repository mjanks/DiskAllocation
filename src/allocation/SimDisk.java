package allocation;

import java.util.ArrayList;
import java.util.HashMap;

public class SimDisk {
    HashMap allocatedList = new HashMap(); // K = index start of file, V = size of file
    HashMap freeList = new HashMap(); // K = index start of hole, V = size of hole
    HashMap directory = new HashMap(); // K = index start of file, V = fileName
    ArrayList keySet = new ArrayList();
    int totalHoles;
    int fatBlocks;
    int[] detailsArray;
    int[] temp;
    int number = 1;
    int index;
    int size;
    int segmentSize = 1;
    int block;
    int count;

    public SimDisk(int s) {
        this.size = s;
    }

    public void allocate(String fileName, int sizeOfFile) {
        if(sizeOfFile <= 0) {
            System.out.println("Size of file cannot be less than 1!");
            return;
        }
        // if enough space, if there's a large enough hole for the file to fit,
        // add it to allocatedList AND directory

        // add to allocatedList
        if(allocatedList.isEmpty()) {
            // calc FAT blocks
            sizeOfFile = calcFAT(sizeOfFile);

            allocatedList.put(0, sizeOfFile);
            directory.put(0, fileName);

            System.out.println("File " + fileName + " was added successfully");
            //System.out.println();
            //System.out.println("Allocate called. List isEmpty.");
            return;
        } else { // allocatedList NOT empty, need to check some things
            // are there any holes? how big are they?
            // check for hole
            // generate freeList
            freeList = new HashMap();
            segmentSize = 1;
            for(int i=0; i < size; i++) {
                if(allocatedList.containsKey(i)) {
                    //System.out.println(allocatedList.get(i));
                    //System.out.println("allocated list contains key!" + allocatedList);
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

            // algorithms to decide how to allocate the files

            /*
            // *********** FIRST FIT ***********
            if(firstFit(fileName, sizeOfFile))
                return;
             */

            /*
            // *********** CONTIGUOUS ***********
            if(contiguous(fileName, sizeOfFile))
                return;
             */


            // *********** INDEXED ***********
            if(indexed(fileName, sizeOfFile))
                return;


            // need to find the smallest hole that the file will fit into
            // sort freeList by hole size, first convert values to int[]
//            temp = new int[size];
//            count = 0;
//            for(Object value: freeList.values()) {
//                temp[count] = (Integer) value;
//                count++;
//            }
//            sort(temp); // sort the holes smallest to largest
//
//            // check if there's a perfect fit
//            for(int i=0; i < size; i++) {
//                if(freeList.get(i) != null) {
//                    if((Integer) freeList.get(i) == sizeOfFile){ // if perfect fit, allocate, done
//                        allocatedList.put(i, sizeOfFile); // add to allocatedList
//                        directory.put(i, fileName); // add to directory
//                        System.out.println("File " + fileName + " was added successfully");
//                        return;
//                    }
//                }
//            }
//
//            // check next largest hole, and so on
//            for(int i=0; i < temp.length; i++) {
//                if(temp[i] != 0 && temp[i] > sizeOfFile) { // skip the zeros!
//                    for(int j=0; j < size; j++) {
//                        if(freeList.get(j) != null) {
//                            if((Integer) freeList.get(j) == temp[i]) {
//                                allocatedList.put(j, sizeOfFile); // add to allocatedList
//                                directory.put(j, fileName); // add to directory
//                                System.out.println("File " + fileName + " was added successfully");
//                                return;
//                            }
//                        }
//                    }
//                }
//            }
//            // *************** END CONTIGUOUS ALLOCATION ***************

            // *************** INDEXED ALLOCATION ***************





            // *************** END INDEXED ALLOCATION ***************



        }
        System.out.println("File " + fileName + " was not added. Not enough space!");
    }

    // Function to sort array using insertion sort
    // Referenced: https://www.geeksforgeeks.org/insertion-sort/
    public void sort(int arr[])
    {
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
        // deallocate, remove from allocatedList
        for(int i=0; i < size; i++) {
            if(directory.get(i) != null)
                if(directory.get(i).equals(file)) {
                    allocatedList.remove(i);
                    directory.remove(i);
                    System.out.println("File " + file + " was deleted successfully");
                    //System.out.println();
                    return;
                }
        }
        System.out.println("File " + file + " does not exist.");
    }

    public void printDirectory() {
        keySet = new ArrayList();
        detailsArray = new int[size];
        System.out.println();
        System.out.println("DIRECTORY:");

        // Referenced: https://stackoverflow.com/questions/10462819/get-keys-from-hashmap-in-java
        for ( Object key : allocatedList.keySet() ) {
            //System.out.println( key );
            keySet.add(key);
        }

        number = 1;
        for(int i=0; i < keySet.size(); i++) {
            System.out.print(number + ". Name of file: " + directory.get(keySet.get(i)) + ", Block(s) ");
            block = (Integer) keySet.get(i);
            for(int j=0; j < (Integer) allocatedList.get(keySet.get(i)); j++) {
                System.out.print(block + " ");
                // details array
                detailsArray[block] = number;
                block++;
            }
            number++;
            System.out.println();
        }
        System.out.println();
        System.out.println("DETAILS:");
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

    public boolean firstFit(String fileName, int sizeOfFile) {
        for(int i=0; i < size; i++) {
            if(freeList.get(i) != null) {
                if((Integer) freeList.get(i) >= sizeOfFile){ // first fit
                    allocatedList.put(i, sizeOfFile);
                    directory.put(i, fileName); // add to directory
                    System.out.println("File " + fileName + " was added successfully");
                    //System.out.println();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contiguous(String fileName, int sizeOfFile) {
        // need to find the smallest hole that the file will fit into
        // sort freeList by hole size, first convert values to int[]
        temp = new int[size];
        count = 0;
        for(Object value: freeList.values()) {
            temp[count] = (Integer) value;
            count++;
        }
        sort(temp); // sort the holes smallest to largest

        // *************** CONTIGUOUS ALLOCATION ***************
        // check if there's a perfect fit
        for(int i=0; i < size; i++) {
            if(freeList.get(i) != null) {
                if((Integer) freeList.get(i) == sizeOfFile){ // if perfect fit, allocate, done
                    allocatedList.put(i, sizeOfFile); // add to allocatedList
                    directory.put(i, fileName); // add to directory
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
        // *************** END CONTIGUOUS ALLOCATION ***************
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
        System.out.println("totalHoles: " + totalHoles);
        System.out.println("sizeOfFile: " + sizeOfFile);

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
                        //sizeOfFile = 0;
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

}
