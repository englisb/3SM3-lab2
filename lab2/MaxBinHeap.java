package lab2;

public class MaxBinHeap {
    private int keys[];
    private int size = 0;

    // Constructor for initializing the heap with a specific capacity.
    public MaxBinHeap(int n){
        // If the requested capacity is less than 10, initialize the array to hold 11 elements (10 + 1 for convenience).
        if(n < 10){
            keys = new int[11];
        }
        else{
            // Otherwise, initialize the array to hold n + 1 elements.
            keys = new int[n + 1];
        }
    }

    // Constructor for building a max heap from an existing array of integers.
    public MaxBinHeap(int[] a){
        // If the input array is empty, initialize the keys array to hold 11 elements.
        if (a.length == 0) 
            keys = new int[11];
        else{
            // If the input array is not empty, set the size and build the heap from the array.
            size = a.length;
            keys = buildHeap(a);
        }
    }
    
    // Builds a heap from an existing array of integers.
    private int[] buildHeap(int[] a){
        // Initialize a new array for the heap, with one extra space for convenience.
        int[] heap = new int[a.length + 1];
        heap[0] = 0; // The first element is not used to simplify index calculations.

        // Copy elements from the input array to the heap, starting from index 1.
        for (int i = 0; i < a.length; i++)
            heap[i + 1] = a[i];

        // Adjust the heap to ensure it maintains the max heap property.
        for (int i = size; i > 0; i--) {
            heap = percolateDown(heap, i);
        }
        
        return heap; // Return the adjusted heap.
    }

    // Returns the number of elements in the heap.
    public int getSize(){
        return size;
    }

    // Inserts a new element into the heap.
    public void insert(int x){
        // Insert the element at the end of the heap and then adjust its position.
        int xIndex = insertAtEnd(x);        
        percolateUp(keys, xIndex);        
        size++; // Increment the size of the heap.
    }
    
    // Adjusts the position of a newly inserted element upwards to maintain the max heap property.
    private int percolateUp(int[] arr, int index){
        int tempKey;
        // Continuously swap the element with its parent if it's larger than its parent.
        while(arr[index / 2] != 0 && arr[index] > arr[index / 2]){
            tempKey = arr[index / 2];
            arr[index / 2] = arr[index];
            arr[index] = tempKey;
            index = index / 2; // Move up the heap.
        }

        return index; // Return the new position of the element.
    }

    // Inserts a new element at the end of the heap.
    private int insertAtEnd(int x){
        int j;

        // If the heap is full, double its size.
        if (size == keys.length - 1) {
            int[] tempKeys = new int[keys.length * 2];      
            for (j = 1; j <= size; j++)               
                tempKeys[j] = keys[j];

            tempKeys[j] = x;                                
            keys = tempKeys;
            return j;
        }

        // If the heap is not full, insert the element at the first empty position.
        keys[size + 1] = x;                    
        return size + 1;                       
    }

    // Returns the maximum element in the heap.
    public int readMax() throws RuntimeException{
        if(size == 0){
            throw new RuntimeException("Cannot return max element, heap is empty!");
        }
        return keys[1]; // The root of the heap is the maximum element.
    }

    // Removes and returns the maximum element from the heap.
    public int deleteMax() throws RuntimeException{
        if(size == 0){
            throw new RuntimeException("Cannot delete max element, heap is empty!");
        }

        int maxKey = keys[1]; // Store the maximum element.
        keys[1] = keys[size]; // Replace the root with the last element in the heap.
        size--; // Decrease the size of the heap.
        percolateDown(keys, 1); // Adjust the heap to maintain the max heap property.

        // Reset empty positions to 0 for clarity.
        for (int i = size + 1; i < keys.length; i++)
            keys[i] = 0;

        return maxKey; // Return the maximum element.
    }

    // Adjusts the position of elements downwards to maintain the max heap property.
    private int[] percolateDown(int[] keysArr, int index) {
        int tempKey, keyOfInterest = keysArr[index], greaterChildIndex;
    
        // Continuously compare the element with its children and swap if necessary.
        while (index * 2 <= size) {
            greaterChildIndex = index * 2; // Assume the left child is the greater.
    
            // If the right child exists and is greater, update the index of the greater child.
            if (index * 2 + 1 <= size && keysArr[index * 2 + 1] > keysArr[index * 2]) {
                greaterChildIndex = index * 2 + 1;
            }
    
            // If the element is greater than both children, the heap is correctly ordered.
            if (keyOfInterest >= keysArr[greaterChildIndex]) {
                break;
            }
    
            // Swap the element with the greater child.
            tempKey = keysArr[greaterChildIndex];
            keysArr[greaterChildIndex] = keyOfInterest;
            keysArr[index] = tempKey;
            index = greaterChildIndex; // Move down the heap.
        }
    
        return keysArr; // Return the adjusted heap.
    }
    

    // Generates a string representation of the heap.
    public String toString(){
        StringBuilder keysString = new StringBuilder();

        // Append each key in the heap to the string builder.
        for (int k = 1; k <= size; k++)
            keysString.append(keys[k]).append(", ");

        // Remove the trailing comma and space if there are elements in the string.
        if (keysString.length() > 0) {
            keysString.setLength(keysString.length() - 2);
        }

        return keysString.toString();
    }

    // Static method to sort an array using the heap sort algorithm.
    public static void sortArray(int[] a) {
        MaxBinHeap heap = new MaxBinHeap(a); // Build a max heap from the array.
        
        // Repeatedly delete the maximum element and place it at the end of the array.
        for (int i = a.length - 1; i >= 0; i--) {
            a[i] = heap.deleteMax();
        }

        // Reverse the array to get it in ascending order.
        int start = 0, end = a.length - 1;
        while (start < end) {
            int temp = a[start];
            a[start] = a[end];
            a[end] = temp;
            start++;
            end--;
        }
    }     
}
