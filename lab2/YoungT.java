package lab2;

public class YoungT {

    private int tab[][], finInts = 0;
    private static int inf;
    
    public YoungT(int k, int n, int infinity){
        inf = infinity < 100 ? 100 : infinity;

        if (k < 10) k = 10;
        if (n < 10) n = 10;

        // Initialize 'tab', the 2D array representing the tableau, with the specified dimensions k x n.
        tab = new int[k][n];
        // Fill each cell of 'tab' with the 'inf' value. This loop iterates over each row (i) and column (j)
        // and assigns the "infinity" value to each cell. This initialization step effectively sets up the tableau
        // in a state where all elements are considered to be at the maximum value (infinity), ready for subsequent insertions
        // of actual values, which will be lesser than infinity and thus can be sorted appropriately within the tableau.
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < n; j++) {
                tab[i][j] = inf;
            }
        }
    }


    public YoungT(int[][] a){
        tab = new int[a.length][a[0].length];

        // Variable 'max' is used to find the maximum value in the input array 'a'.
        // This is necessary to define an "infinity" value that is greater than any existing value in 'a'.
        int max = 0;

        // Iterate over each element in the input array 'a' to find the maximum value.
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if(a[i][j] > max)
                    max = a[i][j]; // Update 'max' if a larger value is found.
            }
        }

        inf = max * 10;

        // Initialize every cell in 'tab' to 'inf'. This step prepares the tableau 
        // by setting all positions to infinity, making it ready for the insertion of actual values.
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                tab[i][j] = inf;
            }
        }

        // Insert each element from the input array 'a' into the Young tableau.
        // The insert method is called for each element, which places it in the correct position
        // within the tableau to maintain the sorted properties of the Young tableau.
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                insert(a[i][j]);
            }
        }
    }


    public int getNumElem(){
        return finInts;
    }

    public int getInfinity(){
        return inf;
    }

    public boolean isEmpty(){
        if (finInts == 0)
            return true;

        return false;
    }

    public boolean isFull(){
		//if the last element is less than infinity, the tablea is full
		if(tab[tab.length - 1][tab[0].length - 1] < inf)
			return true;
		return false;
    }

    public boolean insert(int x) {
        if (x >= inf || isFull())
            return false;
        
        // Start the insertion process from the bottom-right corner of the tableau.
        // This is the logical place to start since we want to maintain the sorted property of the tableau
        // while inserting a new value.
        int i = tab.length - 1, j = tab[0].length - 1;
        int maxI, maxJ, temp;
        int leftElem, aboveElem;
        
        // Temporarily place 'x' at the bottom-right corner of the tableau.
        tab[i][j] = x;
    
        // Begin a loop to percolate 'x' up to its correct position in the tableau.
        // This ensures that the tableau remains sorted according to its properties.
        while (true) {
            // Initialize variables to keep track of the current element's position
            // and the values of its left and above neighbors.
            maxI = i; maxJ = j;
            leftElem = 0; aboveElem = 0;
            
            // Check if the left neighbor exists and is greater than the current value.
            // If so, update 'maxI' to move 'x' towards this position.
            if (i > 0 && tab[i - 1][j] > tab[maxI][maxJ]) {
                maxI = i - 1;
                leftElem = tab[i - 1][j];
            }
            // Similarly, check if the above neighbor exists and is greater than the current value.
            // If so, update 'maxJ' to consider moving 'x' upwards.
            if (j > 0 && tab[i][j - 1] > tab[maxI][maxJ]) {
                maxJ = j - 1;
                aboveElem = tab[i][j - 1];
            }
            
            // If the current position is already the maximum among its neighbors,
            // then 'x' is in the correct position, and the loop can break.
            if (maxI == i && maxJ == j) {
                break;
            }
            
            // Determine the direction to swap based on whether the left or above neighbor is greater.
            maxI = leftElem >= aboveElem ? maxI : i;
            maxJ = aboveElem >= leftElem ? maxJ : j;
    
            // Swap the current element with the chosen neighbor to move 'x' towards its correct position.
            temp = tab[i][j];
            tab[i][j] = tab[maxI][maxJ];
            tab[maxI][maxJ] = temp;
            
            // Update the indices to continue the process from the new position.
            i = maxI;
            j = maxJ;
        }
        
        // Once 'x' is inserted, increment the count of finite elements in the tableau.
        finInts++;
        return true; // Return true to indicate successful insertion.
    }
    

    public int readMin() throws RuntimeException{
        if (isEmpty()) {
            RuntimeException ex = new RuntimeException("Tableau is empty");
            throw ex;
        }

        return tab[0][0];
    }

    public int deleteMin() throws RuntimeException{
        // Throws an exception if the tableau is empty, indicating there's nothing to delete.
        if (isEmpty())
            throw new RuntimeException("Tableau is empty");
    
        // Retrieves the minimum value from the tableau, which is always at tab[0][0] due to the sorted nature of the tableau.
        int min = tab[0][0], currRow = tab.length - 1, currCol = tab[0].length - 1;
    
        // Moves the bottom-rightmost element (potentially the "largest" or placeholder infinity value) to the top-left,
        // effectively removing the minimum element but breaking the tableau's sorted property.
        tab[0][0] = tab[currRow][currCol];
    
        // Resets the bottom-rightmost element to infinity to maintain the integrity of the tableau's boundaries.
        tab[currRow][currCol] = inf;
    
        // Calls `percolateDown` from the top-left corner to restore the tableau's sorted properties
        // by appropriately repositioning the new top-left element.
        percolateDown(0,0);
    
        // Decreases the count of finite elements in the tableau, reflecting the deletion.
        finInts--;
        return min; // Returns the value of the removed minimum element.
    }
    

	private void percolateDown(int currRow, int currCol) {
        // Temporary variable for swapping elements and variables to hold the potential elements to compare.
        int temp, rightElem = 0, belowElem = 0;
    
        // Checks if there's a below element that is smaller than the current one and assigns it to `belowElem` if true.
        if (currRow < tab.length - 1 && tab[currRow][currCol] > tab[currRow + 1][currCol])
            belowElem = tab[currRow + 1][currCol];
    
        // Similarly, checks for a right element smaller than the current one and assigns it to `rightElem`.
        if (currCol < tab[0].length - 1 && tab[currRow][currCol] > tab[currRow][currCol + 1])
            rightElem = tab[currRow][currCol + 1];
    
        // If there are no smaller elements to the right or below, the current position is correct, and the method returns.
        if (rightElem == 0 && belowElem == 0)
            return;
    
        // Depending on which direction has the smaller element, swaps and calls `percolateDown` recursively
        // to move the current element down/right in the tableau, thus maintaining the sorted property.
        if (rightElem == 0 || belowElem <= rightElem){
            temp = tab[currRow][currCol];
            tab[currRow][currCol] = tab[currRow + 1][currCol];
            tab[currRow + 1][currCol] = temp;
            percolateDown(currRow + 1, currCol);
        } else if(belowElem == 0 || rightElem <= belowElem){
            temp = tab[currRow][currCol];
            tab[currRow][currCol] = tab[currRow][currCol + 1];
            tab[currRow][currCol + 1] = temp;
            percolateDown(currRow, currCol + 1);
        }
    }
    

    public boolean find(int x) throws RuntimeException{
        // Predefined exceptions for common error conditions.
        RuntimeException empty = new RuntimeException("Tableau is empty"), large = new RuntimeException("Element too large");
        // Start the search from the top-right corner of the tableau.
        int currRow = 0, currCol = tab[0].length - 1;
    
        // If the searched element is greater than or equal to the predefined 'infinity' (inf), it's considered too large to be in the tableau.
        if (x >= inf)
            throw large;
    
        // If the tableau is empty (a precondition checked by the isEmpty method), throw an exception.
        if (isEmpty())
            throw empty;
    
        // The search process begins, moving either down or left depending on the comparison between 'x' and the current element.
        while(tab[currRow][currCol] != x) {
            // If 'x' is greater than the current element, move down to the next row to find a larger element.
            if (x > tab[currRow][currCol]) {
                if (currRow + 1 < tab.length) {
                    // Optional: Print the current element before moving on (useful for debugging or logging).
                    System.out.print(tab[currRow][currCol] == inf ? "inf, " : tab[currRow][currCol] + ", ");
                    currRow++;
                } else {
                    // If moving down is not possible, print the current element and return false indicating 'x' is not found.
                    System.out.print(tab[currRow][currCol]);
                    return false;
                }
            }
    
            // If 'x' is less than the current element, move left to the previous column to find a smaller element.
            if (x < tab[currRow][currCol]) {
                if (currCol - 1 >= 0) {
                    // Optional: Print the current element before moving on.
                    System.out.print(tab[currRow][currCol] == inf ? "inf, " : tab[currRow][currCol] + ", ");
                    currCol--;
                } else {
                    // If moving left is not possible, print the current element and return false indicating 'x' is not found.
                    System.out.print(tab[currRow][currCol]);
                    return false;
                }
            }
        }
    
        // If the loop exits because the current element matches 'x', print the element and return true.
        System.out.print(tab[currRow][currCol]);
        return true;
    }
    

    public String toString(){
        String str = "";

		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				
				if (tab[i][j] == inf)
					str += "inf";
				else
					str += tab[i][j];

                str += ", ";
			}
		}

        str = str.substring(0, str.length() - 2);
		return str;
    }
}
