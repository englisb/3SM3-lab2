package lab2;

public class YoungT {

    private int tab[][], finInts = 0;
    private static int inf;
    
    public YoungT(int k, int n, int infinity){
        inf = infinity < 100? 100 : infinity;

        if (k < 10)
            k = 10;

        if (n < 10)
            n = 10;

        tab = new int[k][n];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < n; j++) {
                tab[i][j] = inf;
            }
        }
    }

    public YoungT(int[][] a){
        tab = new int[a.length][a[0].length];
        int max = 0;

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if(a[i][j] > max)
                    max = a[i][j];
            }
        }

        inf = max * 10;

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                insert(a[i][j]);
                finInts++;
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
		//if the last element is less than infinity, the tab must be full
		if(tab[tab.length - 1][tab[0].length - 1] < inf)
			return true;
		return false;
    }
    
    public boolean insert(int x){
        int currRow = tab.length - 1, currCol = tab[0].length - 1, leftElem = 0, aboveElem = 0, temp;
        tab[currRow][currCol] = x;

        if (x >= inf || isFull())
			return false;

		while (currCol >= 1 && currRow >= 1) {
            //Checks if left element is larger than the current element
            if (tab[currRow][currCol] < tab[currRow][currCol - 1])
                leftElem = tab[currRow][currCol - 1];

            //Checks if above element is larger than the current element
            if (tab[currRow][currCol] < tab[currRow - 1][currCol])
                aboveElem = tab[currRow-1][currCol];

            //Swaps to the left
            if (leftElem >= aboveElem) {
                //Swaps the two elements
                temp = tab[currRow][currCol];
                tab[currRow][currCol] = tab[currRow][currCol - 1];
                tab[currRow][currCol - 1] = temp;
                currCol--;
            }

            //Swaps up
            if (aboveElem > leftElem) {
                //Swaps the two elements
                temp = tab[currRow][currCol];
                tab[currRow][currCol] = tab[currRow - 1][currCol];
                tab[currRow - 1][currCol] = temp;
                currRow--;
            }

            //If neither the left nor above element are larger than the current one
            if (leftElem == 0 && aboveElem == 0)
                break;
        }

        if(currCol == 0){
            //Checks if above element is larger than the current element
            if (tab[currRow][currCol] < tab[currRow - 1][currCol]){
                //Swaps the above element
                temp = tab[currRow][currCol];
                tab[currRow][currCol] = tab[currRow - 1][currCol];
                tab[currRow - 1][currCol] = temp;
            }
        }
        
        else if(currRow == 0){
            //Checks if left element is larger than the current element
            if (tab[currRow][currCol] < tab[currRow][currCol - 1]){
                //Swaps the left element
                temp = tab[currRow][currCol];
                tab[currRow][currCol] = tab[currRow][currCol - 1];
                tab[currRow][currCol - 1] = temp;
            }
        }

		return true;
    }

    public int readMin() throws RuntimeException{
        if (isEmpty()) {
            RuntimeException ex = new RuntimeException("tab is empty");
            throw ex;
        }

        return tab[0][0];
    }


    //TODO fiinish this
    public int deleteMin() throws RuntimeException{
        if (isEmpty()) {
            RuntimeException ex = new RuntimeException("tab is empty");
            throw ex;
        }

        int min = tab[0][0];
        int currRow = tab.length - 1;
		int currCol = tab[0].length - 1;

        //Defines the last element of the tab to be the inserted element
		tab[0][0] = tab[currRow][currCol];

		tab[currRow][currCol] = inf;

		//Calls the helper function to move the element to the correct location
		percolateDown(0,0);

		finInts--;
		return min;
	}


	//Moves the element down to its correct location in the tab
	private void percolateDown(int currRow, int currCol) {


		int temp;

		int rightElement = 0;
		int belowElement = 0;
		boolean rightSwap = false;



		//Checks if right element exists and is larger than the current element
		if (currCol < tab.length-1 && tab[currRow][currCol] > tab[currRow][currCol+1])
			rightElement = tab[currRow][currCol+1];

		//Checks if below element exists and is larger than the current element
		if (currRow < tab[0].length-1 && tab[currRow][currCol] > tab[currRow+1][currCol])
			belowElement = tab[currRow+1][currCol];

		//If neither the left nor above element are smaller than the current one
		if (rightElement == 0 && belowElement == 0)
			return;


		//Determines whether to perform a swap to the left or above (depending on which is smaller)
		if (rightElement<=belowElement)
			rightSwap = true;
		else
			rightSwap = false;

		//Swaps to the right
		if (rightSwap == true) {

			//Swaps the two elements
			temp = tab[currRow][currCol];
			tab[currRow][currCol] = tab[currRow][currCol+1];
			tab[currRow][currCol+1] = temp;

			//Recursively checks the column to the right
			percolateDown(currRow, currCol+1);
		}

		//Swaps down
		if (rightSwap == false) {

			//Swaps the two elements
			temp = tab[currRow][currCol];
			tab[currRow][currCol] = tab[currRow+1][currCol];
			tab[currRow+1][currCol] = temp;

			//Recursively checks the currRow below
			percolateDown(currRow+1, currCol);
		}

		return;
	}

    public boolean find(int x) throws RuntimeException{
        return false;
    }

    public String toString(){
        return "";
    }
}
