package lab2;

public class MaxBinHeap {

    private int keys[], size;

    public MaxBinHeap(int n){
        if(n < 10){                 //initialize keys[] to have a default size 10 (+ 1 for empty first elements)
            size = 10;
            keys = new int[11];
        }
        else{
            keys = new int[n + 1];
            size = n;
        }
        
        for (int i = 0; i < keys.length; i++)
            keys[i] = -1;
    }

    public MaxBinHeap(int[] a){
        if (a.length == 0) {
            size = 10;
            keys = new int[size + 1];
        }
        else{
            size = a.length;
            keys = new int[size + 1];
            for (int i : a) {
                insert(i);
            }
        }
    }
    

    public int getSize(){
        return size;
    }

    public void insert(int x){
        int xIndex = insertAtEnd(x);
        int tempKey;

        //fix the tree by percolating up
        while(keys[xIndex] > keys[xIndex / 2]){         //edge case for when x is percolated to the root is covered b/c keys[0] == -1
            tempKey = keys[xIndex / 2];                 //swapping
            keys[xIndex / 2] = keys[xIndex];
            keys[xIndex] = tempKey;
            xIndex = xIndex / 2;
        }

        size++;
    }

    private int insertAtEnd(int x){
        int i, j;

        for (i = keys.length - 1; i > 0; i--) {                 //traversing the keys backwards until a non empty (non -1) element is found
            if(keys[i] != -1){
                if (i == keys.length - 1) {            //if the first non empty key is the final element in the array of keys, need to make a new array of keys double in length and copy elements over
                    int[] tempKeys = new int[keys.length * 2];      //allocated the temporary keys array of double size
                    tempKeys[0] = -1;                               //set the offset index as -1
                    
                    for (j = 1; j < keys.length; j++)               //copy all keys from the original array until the end
                        tempKeys[j] = keys[j];

                    tempKeys[j] = x;                                //will be keys.length + 1 at this point, now insert x as the final key in tempKeys 

                    for (j++; j < tempKeys.length ; j++)            //populate the remaining elements in tempKeys as empty (-1)
                        tempKeys[j] = -1;
                    
                    keys = tempKeys;
                    return j;
                }

                keys[i + 1] = x;                        //otherwise, insert x at the first empty element
                break;
            }
        }

        return i;
    }

    public int readMax() throws RuntimeException{
        if(keys[1] == -1){
            RuntimeException ex = new RuntimeException("Cannot return max element, heap is empty!");
            throw ex;
        }
        return keys[1];
    }

    public int deleteMax() throws RuntimeException{
        int i, minKey, currIndex = 1, tempKey = 0, maxIndex = 1;

        for (i = keys.length - 1; i == -1; i--){

        }

        minKey = keys[i + 1];
        keys[maxIndex] = minKey;

        while(currIndex * 2 != -1 && currIndex * 2 + 1 != -1){
            if(minKey > keys[currIndex * 2] || minKey > keys[currIndex * 2 + 1])
                break;
            currIndex = keys[currIndex * 2] > keys[currIndex * 2 + 1] ? currIndex * 2 : currIndex * 2 + 1;
            tempKey = keys[currIndex];
            keys[currIndex / 2] = tempKey;
            keys[currIndex] = minKey;
        }


        return 0;
    }

    public String toString(){
        String keysString = "";

        //iterate through each key in the list
        for (int k = 1; keys[k] != -1 && k < keys.length; k++)
            keysString += keys[k] + ", ";

        return keysString;
    }

    public static void sortArray(int k, int[] a){
        
    }
}
