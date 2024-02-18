package lab2;

public class MaxBinHeap {

    private int keys[];
    private int size = 0;

    public MaxBinHeap(int n){
        if(n < 10){                 //initialize keys[] to have a default size 10 (+ 1 for empty first elements)
            keys = new int[10];
        }
        else{
            keys = new int[n];
        }
    }

    public MaxBinHeap(int[] a){
        if (a.length == 0) 
            keys = new int[10];
        
        else{
            keys = buildHeap(a);
        }
    }
    
    private int[] buildHeap(int[] a){
        for (int i = a.length - 1; i > 0; i--) {
            heapifySingleElem(a, i);
        }
        
        return a;
    }

    public int getSize(){
        return size;
    }

    public void insert(int x){
        int xIndex = insertAtEnd(x);
        
        percolateUp(xIndex);
        
        size++;
    }
    
    private int percolateUp(int index){
        int tempKey;
        //fix the tree by percolating up
        while(keys[index / 2] != 0 && keys[index] > keys[index / 2]){         //percolating up the key just added in case it breaks the max heap property
            tempKey = keys[index / 2];                 //swapping
            keys[index / 2] = keys[index];
            keys[index] = tempKey;
            index = index / 2;
        }

        return index;                                   //return the new index of the node percolated node
    }

    private int insertAtEnd(int x){
        int j;

        
        if (size == keys.length) {            //if the first non empty key is the final element in the array of keys, need to make a new array of keys double in length and copy elements over
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
        return keys[0];
    }

    public int deleteMax() throws RuntimeException{
        int maxKey = keys[0], origSize = size;

        if (maxKey == 0) {
            RuntimeException ex = new RuntimeException("Cannot delete max element, heap is empty!");
            throw ex;
        }

        heapifySingleElem(keys, size - 1);

        size--;

        for (int i = size; i >= origSize; i--)          //for delete max its a more convenient representation to have empty keys be 0
            keys[i] = 0;                                //but size handles the the proper end point anyways

        return maxKey;
    }

    private void heapifySingleElem(int[] keysArr, int index){
        int tempKey, keyOfIntrst = keysArr[index];
        keysArr[index] = keyOfIntrst;

        while(index * 2 + 1 <= size){
            if(keyOfIntrst > keys[index * 2] && keyOfIntrst > keys[index * 2 + 1])
                break;
            index = keys[index * 2] > keys[index * 2 + 1] ? index * 2 : index * 2 + 1;
            tempKey = keys[index];
            keys[index / 2] = tempKey;
            keys[index] = keyOfIntrst;
        }
    }

    public String toString(){
        String keysString = "";

        //iterate through each key in the list
        for (int k = 0; k <= size && keys[k] != 0; k++)
            keysString += keys[k] + ", ";

        return keysString;
    }

    public static void sortArray(int[] a){       //(int k, int[] a)???
        MaxBinHeap tempHeap = new MaxBinHeap(a);

        tempHeap.heapifySingleElem(a, a.length - 1);
    }
}
