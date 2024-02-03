package lab2;

public class PartialTestMaxBinHeap {

    public static void main(String[] args) {
        // Data for testing
        int[] arr1 = {1, 5, 4, 3, 20, 18, 16, 14, 10, 12};
        int[] arr2 = {5, 4, 3, 11, 35, 56};

        // Testing MaxBinHeap class
        System.out.println("---------------------**TEST BEGINS**--------------------------------");
        System.out.println("\n--------------Testing MaxBinHeap class--------------------------------");

        // Test 1: Testing MaxBinHeap constructor with an array
        System.out.println("\n--------Test 1------------");
        System.out.println("Testing MaxBinHeap constructor with arr1:");
        MaxBinHeap heap1 = new MaxBinHeap(arr1);
        System.out.println("Heap: " + heap1);
        System.out.println("Expected: 20, 14, 18, 10, 12, 5, 16, 3, 4, 1");

        // Test 2: Testing insert
        System.out.println("\n--------Test 2------------");
        System.out.println("Testing insert method by adding 22 to the heap:");
        heap1.insert(22);
        System.out.println("Heap after insertion: " + heap1);
        System.out.println("Expected: 22, 20, 18, 10, 14, 4, 16, 3, 1, 5, 12");

        // Test 3: Testing deleteMax
        System.out.println("\n--------Test 3------------");
        int max = heap1.deleteMax();
        System.out.println("Deleted max element: " + max);
        System.out.println("Heap after deleting max: " + heap1);
        System.out.println("Expected: 20, 14, 18, 10, 12, 5, 16, 3, 4, 1");

        // Test 4: Testing sortArray
        System.out.println("\n--------Test 4------------");
        System.out.println("Testing sortArray with arr2:");
        MaxBinHeap.sortArray(arr2);
        System.out.print("Sorted arr2: ");
        for (int n : arr2) System.out.print(n + " ");
        System.out.println("\nExpected: 56 35 11 5 4 3");
        
     // Test 5: Testing getSize()
        System.out.println("\n--------Test 5------------");
        System.out.println("Testing getSize method:");
        System.out.println("Current heap size: " + heap1.getSize());
        System.out.println("Expected heap size: 10");

        // Test 6: Testing readMax()
        System.out.println("\n--------Test 6------------");
        System.out.println("Testing readMax method:");
        try {
            System.out.println("Current max element: " + heap1.readMax());
            System.out.println("Expected max element: 20");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        // Test 7: Testing toString()
        System.out.println("\n--------Test 7------------");
        System.out.println("Testing toString method:");
        System.out.println("Heap representation: " + heap1);
        System.out.println("Expected representation: 20, 14, 18, 10, 12, 4, 16, 3, 1, 5");


        System.out.println("\n-----------------------------Testing Done for MaxBinHeap!-------------------------------------");
    }
}