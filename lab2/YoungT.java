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
            RuntimeException ex = new RuntimeException("Tableau is empty");
            throw ex;
        }

        return tab[0][0];
    }

    public int deleteMin() throws RuntimeException{
        RuntimeException ex = new RuntimeException("Tableau is empty");
        if (isEmpty())
            throw ex;

        int min = tab[0][0], currRow = tab.length - 1, currCol = tab[0].length - 1;

        //Defines the last element of the tab to be the inserted element
		tab[0][0] = tab[currRow][currCol];

		tab[currRow][currCol] = inf;

		//Calls the helper function to move the element to the correct location
		swaps(0,0);

		finInts--;
		return min;
	}


	//swaps the selected element further in the tableau until it satisfies the nondecreasing property
	private void swaps(int currRow, int currCol) {
		int temp, rightElem = 0, belowElem = 0;

        //checks if below element exists and is larger than the current one
        if (currRow < tab[0].length - 1 && tab[currRow][currCol] > tab[currRow + 1][currCol])
            belowElem = tab[currRow + 1][currCol];

		//checks if right element exists and is larger than the current one
		if (currCol < tab.length - 1 && tab[currRow][currCol] > tab[currRow][currCol + 1])
			rightElem = tab[currRow][currCol + 1];

		//if the current element is larger than the right and below adjacent elements, it is in the correct spot
		if (rightElem == 0 && belowElem == 0)
			return;

		//swap right
        if (belowElem <= rightElem){
            //Swaps the two elements
            temp = tab[currRow][currCol];
            tab[currRow][currCol] = tab[currRow + 1][currCol];
            tab[currRow + 1][currCol] = temp;
            //checks the next column
            swaps(currRow, currCol + 1);
        }

        //swap down
		else{
            temp = tab[currRow][currCol];
            tab[currRow][currCol] = tab[currRow][currCol + 1];
            tab[currRow][currCol + 1] = temp;
            //checks the next currRow
            swaps(currRow + 1, currCol);
        }

		return;
	}

    public boolean find(int x) throws RuntimeException{
        RuntimeException empty = new RuntimeException("Tableau is empty"), large = new RuntimeException("Element too large");
        int currRow = 0, currCol = tab[0].length - 1;


		if (x >= inf || isEmpty())
            throw large;

        if (isEmpty())
            throw empty;

        //Runs until a match is a found
        while(tab[currRow][currCol] != x) {
            
            //Checks if next probe should be below
            if (x > tab[currRow][currCol]) {
                //Prints current element then moves on to the next
                if (currRow + 1 < tab.length) {
                    if (tab[currRow][currCol] == inf)
                        System.out.print("infinity, "); 
                    else
                        System.out.print(tab[currRow][currCol] + ", "); 
                    
                    currRow++;
                }
                //If the current currRow to check is the final one, the element is not in the tableau
                else {
                    System.out.print(tab[currRow][currCol]);
                    return false;
                }
            }

            //Checks if next probe should be to the left
            if (x < tab[currRow][currCol]) {

                //Prints current element then moves on to the next
                if (currCol - 1 >= 0) {
                    if (tab[currRow][currCol] == inf)
                        System.out.print("infinity, "); 
                    else
                        System.out.print(tab[currRow][currCol] + ", ");
                    currCol--;
                }

                //If the current column to check is the final one, the element is not in the tableau
                else {
                    System.out.print(tab[currRow][currCol]);
                    return false;
                }

            }
        }

        //Prints the final element and returns true
        System.out.print(tab[currRow][currCol]);
        return true;
    }

    public String toString(){
        String str = "";

		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[0].length; j++) {
				
				//Adds each element from the tableau to the string
				if (tab[i][j] == inf)
					str += "infinity";
				else
					str += tab[i][j];

                str += ", ";
			}
		}

		return str;
    }
}
