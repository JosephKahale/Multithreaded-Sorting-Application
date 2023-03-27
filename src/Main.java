//Joseph Kahale
//CS4310.02
//Professor Damavandi
//March 25, 2023

//Project 1 - Task 1

//Please enter the input file name:
//data
//Original List: [7, 12, 19, 3, 18, 4, 2, 6, 15, 8]
//Sorted List: [2, 3, 4, 6, 7, 8, 12, 15, 18, 19]

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

//This class assigns a thread number, and the tasks for the following threads, which are sorting and merging.
class SortThread extends Thread {
    public int threadNumber;
    public SortThread (int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        //Sorting Thread 1
        if(threadNumber == 0){
            Main.arr1 = sortingArr(0,Main.originalList.length / 2);
        }
        //Sorting Thread 2
        else if(threadNumber == 1){
            Main.arr2 = sortingArr(Main.originalList.length / 2,Main.originalList.length);
        }
        //Merging Thread 3, which merges and sorts a final time
        else if(threadNumber == 2){
            mergeArr();
        }
    }

    //This function sorts an array
    public Integer[] sortingArr(int start, int end) {
        Integer[] arr = Arrays.copyOfRange(Main.originalList, start, end );
        Arrays.sort(arr);

        return arr;
    }

    //This function merges both smaller arrays
    public void mergeArr() {
        int arr1L = Main.arr1.length;
        int arr2L = Main.arr2.length;
        int[] arr3 = new int[Main.originalList.length];
        int temparr[] = new int[Main.arr1.length];
        for(int i = 0; i < Main.arr1.length; i++){
            temparr[i] = Main.arr1[i];
        }
        int temparrTwo[] = new int[Main.arr1.length];
        for(int i = 0; i < Main.arr2.length; i++){
            temparrTwo[i] = Main.arr2[i];
        }


        System.arraycopy(temparr, 0, arr3, 0, arr1L);
        System.arraycopy(temparrTwo, 0, arr3, arr1L, arr2L);

        Arrays.sort(arr3);
        Main.sortedList = arr3;
    }
}

public class Main{
    //sorting arrays
    public static Integer[] arr1;
    public static Integer[] arr2;
    //original array
    public static Integer[] originalList;
    //final sorted array
    public static int[] sortedList;

    //Reads from file and initializes arrays
    public static Integer[] readFromFile(String fileName) throws FileNotFoundException {

        File one = new File(fileName + ".txt");
        Scanner myReader = new Scanner(one);
            String[] newLine = myReader.nextLine().trim().split(" ");
            Integer[] temp = new Integer[newLine.length];
            for(int i = 0; i < newLine.length; i++){
                temp[i] = Integer.parseInt(newLine[i]);
            }
            originalList = new Integer[temp.length];
            sortedList = new int[temp.length];
            arr1 = new Integer[temp.length /2];
            arr2 = new Integer[temp.length /2];
        return temp;
    }


    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        originalList = new Integer[0];
        //Takes input for file name
        System.out.println("Please enter the input file name: ");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        originalList = readFromFile(s);

        System.out.println("Original List: " + Arrays.toString(originalList));
        SortThread[] threads = new SortThread[3];

        for(int i = 0; i < threads.length; i++){
            threads[i] = new SortThread(i);
            threads[i].start();
            threads[i].join();
        }

        System.out.println("Sorted List: " + Arrays.toString(sortedList));
    }
}