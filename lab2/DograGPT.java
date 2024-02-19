
public class YoungT {

	private int [][] tableau;
	private int numIntegers;
	private int infinity = 100;


	//Constructs an empty tableau from a given size
	public YoungT(int k, int n, int infinity) {

		//Makes the minimum size of YoungT 10x10
		if (k<10)
			k = 10;
		if (n<10)
			n = 10;

		//Resets the infinity variable if it is too small
		if (infinity<100)
			infinity = 100;


		tableau = new int [k][n];

		//Nested loop to set every element to infinity
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < n; j++) {
				tableau[i][j] = infinity;
			}
		}



	}


	//Constructs an empty tableau from a given 2D array
	public YoungT(int[][] a) {


		//Initializes the number of integers to be zero at first
		numIntegers = 0;

		//Gets number of rows in the 2D array
		int rowSize = a.length;

		//Gets number of columns in the 2D array
		int columnSize = a[0].length;

		int maxValue = 0;

		//Iterate through the 2D array to find the maximum value
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < columnSize; j++) {
				if (a[i][j]>maxValue)
					maxValue =  a[i][j];
			}
		}

		//Set infinity based on the maximum value
		infinity = maxValue*10;


		tableau = new int [rowSize][columnSize];


		//Nested loop to set every element to infinity
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < columnSize; j++) {
				tableau[i][j] = infinity;
			}
		}

		boolean numIncrease = false;

		//Iterates through the 2D array
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < columnSize; j++) {

				//Adds each element from the array to the tableau
				numIncrease = insert(a[i][j]);

				if (numIncrease == true)
					numIntegers++;


			}
		}




	}



	//Returns the number of integers in the tableau
	public int getNumElem() {

		return numIntegers;


	}


	//Returns the value that represents infinity
	public int getInfinity() {

		return infinity;


	}



	//Checks if the tableau is empty
	public boolean isEmpty() {

		//If the first element is infinity, the tableau must be empty
		if (tableau[0][0] == infinity)
			return true;
		//Otherwise, the tableau is not empty
		else
			return false;

	}

	//Checks if the tableau is full
	public boolean isFull() {

		//Initializes the length of the row and column
		int rowSize = tableau.length;
		int columnSize = tableau[0].length;

		//If the last element is less than infinity, the tableau must be full
		if (tableau[rowSize-1][columnSize-1] < infinity)
			return true;
		//Otherwise, the tableau is not full
		else
			return false;

		/*
		//Checks if the number of integers is equal to the size of the tableau
		if (numIntegers == rowSize*columnSize)
			return true;
		else
			return false;
		 */





	}


	//Inserts the element x into the tableau and performs the necessary swaps
	public boolean insert(int x) {


		int row = tableau.length-1;
		int col = tableau[0].length-1;

		//Does not insert if x is larger or equal to infinity or if the tableau is full
		if (x >= infinity || isFull())
			return false;


		else {

			//Defines the last element of the tableau to be the inserted element
			tableau[row][col] = x;

			//Calls the helper function to move the element to the correct location
			insertHelper(row,col);

			return true;

		}





	}


	//Helper function to recursively insert the element in the correct spot of the tableau
	private void insertHelper (int row, int col) {


		int temp;

		int leftElement = 0;
		int aboveElement = 0;
		boolean leftSwap = false;



		//Checks if left element exists and is larger than the current element
		if (col > 0 && tableau[row][col] < tableau[row][col-1])
			leftElement = tableau[row][col-1];

		//Checks if above element exists and is larger than the current element
		if (row > 0 && tableau[row][col] < tableau[row-1][col])
			aboveElement = tableau[row-1][col];


		//If neither the left nor above element are larger than the current one
		if (leftElement == 0 && aboveElement == 0)
			return;


		//Determines whether to perform a swap to the left or above (depending on which is larger)
		if (leftElement>=aboveElement)
			leftSwap = true;
		else
			leftSwap = false;




		//Swaps to the left
		if (leftSwap == true) {

			//Swaps the two elements
			temp = tableau[row][col];
			tableau[row][col] = tableau[row][col-1];
			tableau[row][col-1] = temp;

			//Recursively checks the column to the left
			insertHelper(row, col-1);
		}

		//Swaps up
		if (leftSwap == false) {

			//Swaps the two elements
			temp = tableau[row][col];
			tableau[row][col] = tableau[row-1][col];
			tableau[row-1][col] = temp;

			//Recursively checks the row above
			insertHelper(row-1, col);
		}

		return;
	}



	//Returns the minimum value in the tableau
	public int readMin() throws RuntimeException{

		//Throws an exception if the tableau is empty
		if (numIntegers == 0)
			throw new RuntimeException("Tableau is empty!");


		return tableau[0][0];


	}


	//Deletes the smallest element from the tableau
	public int deleteMin() throws RuntimeException{

		//Throws an exception if the tableau is empty
		if (isEmpty())
			throw new RuntimeException("Tableau is empty!");

		int row = tableau.length-1;
		int col = tableau[0].length-1;

		int smallest = tableau[0][0];



		//Defines the last element of the tableau to be the inserted element
		tableau[0][0] = tableau[row][col];

		tableau[row][col] = infinity;

		//Calls the helper function to move the element to the correct location
		percolateDown(0,0);

		numIntegers--;
		return smallest;

	}


	//Moves the element down to its correct location in the tableau
	private void percolateDown(int row, int col) {


		int temp;

		int rightElement = 0;
		int belowElement = 0;
		boolean rightSwap = false;



		//Checks if right element exists and is larger than the current element
		if (col < tableau.length-1 && tableau[row][col] > tableau[row][col+1])
			rightElement = tableau[row][col+1];

		//Checks if below element exists and is larger than the current element
		if (row < tableau[0].length-1 && tableau[row][col] > tableau[row+1][col])
			belowElement = tableau[row+1][col];

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
			temp = tableau[row][col];
			tableau[row][col] = tableau[row][col+1];
			tableau[row][col+1] = temp;

			//Recursively checks the column to the right
			percolateDown(row, col+1);
		}

		//Swaps down
		if (rightSwap == false) {

			//Swaps the two elements
			temp = tableau[row][col];
			tableau[row][col] = tableau[row+1][col];
			tableau[row+1][col] = temp;

			//Recursively checks the row below
			percolateDown(row+1, col);
		}

		return;




	}



	//Finds if a given an element is in the tableau
	public boolean find(int x) throws RuntimeException{

		//Does not try to find x if it is larger or equal to infinity or if the tableau is full
		if (x >= infinity || isEmpty())
			throw new RuntimeException("Tableau is empty or element is too large!");


		int row = 0;
		int col = tableau[0].length-1;


		//Runs until a match is a found
		while (tableau[row][col] != x) {

			//Checks if next probe should be to the left
			if (x<tableau[row][col]) {

				//Prints current element then moves on to the next
				if (col-1>=0) {
					System.out.print(tableau[row][col] + ", ");
					col--;
				}

				//If the current column to check is the final one, the element is not in the tableau
				else {
					System.out.print(tableau[row][col]);
					return false;
				}

			}


			//Checks if next probe should be below
			if (x>tableau[row][col]) {

				//Prints current element then moves on to the next
				if (row+1<tableau.length) {
					System.out.print(tableau[row][col] + ", ");
					row++;
				}

				//If the current row to check is the final one, the element is not in the tableau
				else {
					System.out.print(tableau[row][col]);
					return false;
				}


			}


		}


		//Prints the final element and returns true
		System.out.print(tableau[row][col]);
		return true;


	}


	//Returns a string representation of the tableau
	public String toString() {

		String finalString = "";


		//Gets number of rows in the tableau
		int rowSize = tableau.length;

		//Gets number of columns in the tableau
		int columnSize = tableau[0].length;

		//Iterates through the tableau
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < columnSize; j++) {

				/*
				//Adds each element from the tableau to the string
				if (j+1<tableau[0].length)
					finalString += tableau[i][j] + ", ";
				else
					finalString += tableau[i][j];

				 */
				
				//Adds each element from the tableau to the string
				if (i == rowSize-1 && j == columnSize-1)
					finalString += tableau[i][j];
				else
					finalString += tableau[i][j] + ", ";


			}

			//Adds a line where necessary
			//finalString += "\n";
		}


		return finalString;

	}



}
