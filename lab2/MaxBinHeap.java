package lab2;

import java.util.Arrays;

public class MaxBinHeap {

    private int keys[];
    private int size = 0;

    public MaxBinHeap(int n){
        if(n < 10){                 //initialize keys[] to have a default size 10 (+ 1 for empty first elements)
            keys = new int[11];
        }
        else{
            keys = new int[n + 1];
        }
    }

    public MaxBinHeap(int[] a){
        if (a.length == 0) 
            keys = new int[11];
        
        else{
            size = a.length;
            keys = buildHeap(a);
        }
    }
    
    private int[] buildHeap(int[] a){
        int[] heap = {0};
        heap = Arrays.copyOf(heap, a.length + 1);
        System.arraycopy(a, 0, heap, 1, a.length);

        for (int i = size; i > 0; i--) {
            heap = percolateDown(heap, i);
        }
        
        return heap;
    }

    public int getSize(){
        return size;
    }

    public void insert(int x){
        int xIndex = insertAtEnd(x);
        
        percolateUp(keys, xIndex);
        
        size++;
    }
    
    private int percolateUp(int[] arr, int index){
        int tempKey;
        //fix the tree by percolating up
        while(arr[index / 2] != 0 && arr[index] > arr[index / 2]){         //percolating up the key just added in case it breaks the max heap property
            tempKey = arr[index / 2];                 //swapping
            arr[index / 2] = arr[index];
            arr[index] = tempKey;
            index = index / 2;
        }

        return index;                                   //return the new index of the node percolated node
    }

    private int insertAtEnd(int x){
        int j;

        
        if (size == keys.length - 1) {            //if the first non empty key is the final element in the array of keys, need to make a new array of keys double in length and copy elements over
            int[] tempKeys = new int[keys.length * 2];      //allocated the temporary keys array of double size
            
            for (j = 1; j <= size; j++)               //copy all keys from the original array until the end
                tempKeys[j] = keys[j];

            tempKeys[j] = x;                                //j will be size + 1 at this point, now insert x as the final key in tempKeys 
            keys = tempKeys;
            return j;
        }

        keys[size + 1] = x;                    //insert x at the first empty element
        return size + 1;                       //return where the insertion was performed
    }

    public int readMax() throws RuntimeException{
        if(size == 0){
            RuntimeException ex = new RuntimeException("Cannot return max element, heap is empty!");
            throw ex;
        }
        return keys[1];
    }

    public int deleteMax() throws RuntimeException{
        RuntimeException ex = new RuntimeException("Cannot delete max element, heap is empty!");
        int origSize = size, maxKey;
        try {
            maxKey = keys[1];
            keys[1] = keys[size];
        } 
        catch (Exception e) {
            throw ex;
        }

        size--;
        percolateDown(keys, 1);

        for (int i = size; i >= origSize; i--)          //for delete max its a more convenient representation to have empty keys be 0
            keys[i] = 0;                                //but size handles the the proper end point anyways

        return maxKey;
    }

    private int[] percolateDown(int[] keysArr, int index) {
        int tempKey, keyOfIntrst = keysArr[index], greaterChldIndx;
    
        while (index * 2 <= size) { // Check if the left child exists (index * 2)
            greaterChldIndx = index * 2; // Assume the left child is the greater one
    
            // Check if the right child exists and if it is greater than the left child
            if (index * 2 + 1 <= size && keysArr[index * 2 + 1] > keysArr[index * 2]) {
                greaterChldIndx = index * 2 + 1; // Update greater child index to the right child
            }
    
            // Compare the greater child with the key of interest
            if (keyOfIntrst >= keysArr[greaterChldIndx]) {
                break; // Exit the loop if the key of interest is greater than both children
            }
    
            // Swap the key of interest with the greater child
            tempKey = keysArr[greaterChldIndx];
            keysArr[greaterChldIndx] = keyOfIntrst;
            keysArr[index] = tempKey;
            index = greaterChldIndx;
        }
    
        return keysArr;
    }
    

    public String toString(){
        String keysString = "";

        //iterate through each key in the list
        for (int k = 1; k <= size && keys[k] != 0; k++)
            keysString += keys[k] + ", ";

        return keysString;
    }

    public static void sortArray(int[] a) {
        // Step 1: Build a max heap from the array
        MaxBinHeap heap = new MaxBinHeap(a);
        int max;
        
        // Step 2: Repeatedly delete the maximum element from the heap
        // and place it at the correct position from the end of the array
        for (int i = a.length - 1; i >= 0; i--) {
                max = heap.deleteMax(); // Extract the maximum element from the heap
                a[i] = max; // Place the maximum element at its correct position in the array
        }

        int start = 0;
        int end = a.length - 1;
        while (start < end) {
            //swap the elements at start and end
            int temp = a[start];
            a[start] = a[end];
            a[end] = temp;
            //move the indices towards the center
            start++;
            end--;
        }
    }
        
}