import java.util.Arrays;

public class Evaluate {

    private char[][] gameBoard;
    private int tilesNeeded;
    private int boardRows, boardColumns;


    public Evaluate (int boardRows, int boardColumns, int tilesNeeded, int maxLevels){

        this.boardColumns = boardColumns;
        this.boardRows = boardRows;
        this.tilesNeeded = tilesNeeded;

        // Initializes game board with size boardRows and boardColumns
        this.gameBoard = new char[boardRows][boardColumns];

        // Sets all values in the game board to 'g' to indicate an empty game board
        for (int i = 0; i< boardRows; i++){
            for (int j =0; j<boardColumns; j++){
                this.gameBoard[i][j] = 'g';
            }
        }
    }

    private String gameBoardToString(){

        //Initialize a data structure to hold the concatenated chars from the array
        StringBuilder config = new StringBuilder();

        //length attribute gets the number of rows in a 2D array. length attribute on a row gets the number of columns in a
        // 2D array. We iterate through all the rows and columns and join all the characters in the array into a single
        // String config

        for (int i = 0; i < this.boardRows; i++){
            for (int j =0; j < this.boardColumns; j++){
                config.append(this.gameBoard[i][j]);
            }
        }

        // Returns String object representation of StringBuilder object
        return config.toString();

    }

    public Dictionary createDictionary(){
        // Creates dict of size 9973 each time. 9973 is a prime number
        Dictionary dict = new Dictionary(9973);
        return dict;
    }

    public Data repeatedConfig(Dictionary dict){
        // Gets String representation of gameBoard
        String config = gameBoardToString();

        // Returns Data object for key config if found. Otherwise, returns null
        return dict.get(config);
    }

    public void insertConfig(Dictionary dict, int score, int level) {
        // Gets String representation of gameBoard
        String config = gameBoardToString();

        // Creates new Data object and inserts it into the hashtable
        Data new_record = new Data(config, score, level);
        try {
            dict.put(new_record);
        } catch (DuplicatedKeyException e) {
            System.out.println(e.getMessage());
        }

    }

    public void storePlay(int row, int col, char symbol){
        //Stores char symbol in gameBoard
        this.gameBoard[row][col] = symbol;
    }

    public boolean squareIsEmpty (int row, int col){
        // Returns true if square is empty, false otherwise
        return this.gameBoard[row][col] == 'g';
    }

    public boolean tileOfComputer (int row, int col){
        // Returns true if computer tile, false otherwise
        return this.gameBoard[row][col] == 'o';
    }

    public boolean tileOfHuman (int row, int col){
        // Returns true if human tile, false otherwise
        return this.gameBoard[row][col] == 'o';
    }

    private boolean winsByRow(char symbol){

        // Function calculates max number of consecutive char symbol in a row

        // Stores the maximum count of consecutive occurrences of char symbol
        int max_row_count =0;

        // Iterates through rows of gameboard
        for (int i=0; i<this.boardRows; i++){

            // Reset the row counter everytime we go to a new row
            int row_count = 0;

            // Iterates through columns of gameboard (values in the rows)
            for (int j=0; j<this.boardColumns; j++){

                // We compare all the values in a row to the char symbol. If the element in the row is the same
                // as char symbol we increment the counter by 1 and update the max_row_count if possible.
                if (symbol == this.gameBoard[i][j]){

                    row_count+=1;

                    if (row_count > max_row_count){
                        max_row_count = row_count;
                    }
                }
                // If the value is not the same as char symbol, then we reset the row_count to 0
                else {
                    row_count = 0;
                }
            }
        }

        // If the max_row_count is greater than or equal to tilesNeeded, then we have sufficient tiles in a row
        // for char symbol to win so we return true. Otherwise, we return false.
        if (max_row_count >= this.tilesNeeded){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean winsByCol(char symbol) {
        // Function calculates max number of consecutive char symbol in a column

        // Stores the maximum count of consecutive occurrences of char symbol
        int max_col_count = 0;

        // Iterates through columns of gameboard
        for (int j = 0; j < this.boardColumns; j++) {

            // Reset the col counter everytime we go to a new col
            int col_count = 0;

            // Iterates through rows of gameboard (values in the cols)
            for (int i = 0; i < this.boardRows; i++) {

                // We compare all the values in a coloumn to the char symbol. If the element in the col is the same
                // as char symbol we increment the counter by 1 and update the max_col_count if possible.
                if (symbol == this.gameBoard[i][j]){

                    col_count+=1;

                    if (col_count > max_col_count){
                        max_col_count = col_count;
                    }
                }
                // If the value is not the same as char symbol, then we reset the row_count to 0
                else {
                    col_count = 0;
                }
            }
        }

        // If the max_col_count is greater than or equal to tilesNeeded, then we have sufficient tiles in a col
        // for char symbol to win so we return true. Otherwise, we return false.

        if (max_col_count >= this.tilesNeeded){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean winsByDiagonalTopRightToBottomLeft(char symbol){

        // Function calculates max number of consecutive char symbol in a diagonal from TOP RIGHT to BOTTOM LEFT

        // Stores the maximum count of consecutive occurrences of char symbol
        int max_diagonal_count = 0;

        // Iterates through diagonals from top right to bottom left
        for(int k = 0 ; k <= this.boardColumns - 1  + this.boardRows - 1; k++) {

            // Reset counter for each new diagonal
            int count = 0;

            // Iterates through values in the diagonal. The sum of their indices is equal to k (the index
            // of the column) so we can easily calculate i by iterating through j (up to and equal to k) and subtracting
            // j from k to get i.
            for(int j = 0 ; j <= k ; j++) {
                int i = k - j;

                // If i surpasses the number of available rows or j surpasses the number of available columns, we
                // break out the inner for loop. This happens when the num of rows and cols in the array are different
                if (i >= this.boardRows || j >= this.boardColumns){
                    continue;
                }
                // Check if char symbol is equal to the element in the diagonal. If so, we increment count by 1 and update
                // max_diagonal_count if possible.
                if (symbol == this.gameBoard[i][j]){
                    count+=1;
                    if (count>max_diagonal_count){
                        max_diagonal_count = count;
                    }
                }
                // Otherwise, we reset the counter to 0
                else {
                    count =0;
                }
            }
        }

        // If the max_diagonal_count is greater than or equal to tilesNeeded, then we have sufficient tiles in a diagonal
        // for char symbol to win so we return true. Otherwise, we return false.
        if (max_diagonal_count >= this.tilesNeeded){
            return true;
        }
        else {
            return false;
        }

    }

    private boolean winsByDiagonalTopLeftToBottomRight(char symbol){

        // Function calculates max number of consecutive char symbol in a diagonal from TOP LEFT to BOTTOM RIGHT

        // Stores the maximum count of consecutive occurrences of char symbol
        int max_diagonal_count = 0;

        // Deals with the diagonals below the midpoint in the array. Iterates through diagonals from last row to the
        // first row.
        for (int i = this.boardRows - 1; i > 0; i--) {

            // reset counter at each new diagonal
            int count = 0;

            // Iterates through the diagonal with origin (top left point) at first element of row i
            for (int j = 0, x = i; x < this.boardRows; j++, x++) {

                // If j surpasses the number of available cols we break out the inner for loop. This happens when the
                // num of rows and cols in the array are different
                if (j>=this.boardColumns){
                    break;
                }
                // If char symbol is the same as the element in the diagonal, we increment counter by 1 and update
                // max_diagonal_count if possible.
                if(symbol == this.gameBoard[x][j]){
                    count+=1;

                    if (count>max_diagonal_count){
                        max_diagonal_count=count;
                    }
                }
                //Otherwise, we reset the counter to 0
                else {
                    count = 0;
                }
            }
        }

        // Deals with the diagonals at and after the midpoint in the array. Iterates through diagonals from first column to the
        // last column
        for (int i = 0; i < this.boardColumns; i++) {

            // Resets counter for each new diagonal
            int count = 0;

            // Iterates through diagonal values with origin (top left point) at first element of col i
            for (int j = 0, y = i; y < this.boardColumns; j++, y++) {

                // If j surpasses the number of available cols we break out the inner for loop. This happens when the
                // num of rows and cols in the array are different
                if (j>=this.boardRows){
                    break;
                }

                // If char symbol is the same as the element in the diagonal, we increment counter by 1 and update
                // max_diagonal_count if possible.
                if(symbol == this.gameBoard[j][y]){
                    count+=1;

                    if (count>max_diagonal_count){
                        max_diagonal_count=count;
                    }
                }
                //Otherwise, we reset the counter to 0
                else {
                    count = 0;
                }
            }
        }

        // If the max_diagonal_count is greater than or equal to tilesNeeded, then we have sufficient tiles in a diagonal
        // for char symbol to win so we return true. Otherwise, we return false.
        if (max_diagonal_count >= this.tilesNeeded){
            return true;
        }
        else {
            return false;
        }

    }

    public boolean wins (char symbol){

        // If either of these conditions is true then it returns true, Otherwise, returns false.
        if (winsByRow(symbol) || winsByCol(symbol) || winsByDiagonalTopRightToBottomLeft(symbol) || winsByDiagonalTopLeftToBottomRight(symbol)){
            return true;
        }
        else {
            return false;
        }

    }

    public boolean isDraw(){
        // Gets String representation of gameBoard
        String config = gameBoardToString();

        // Checks to see if there any more empty squares in the gameBoard. Returns -1 if 'g' not found in String config,
        // meaning there are no more empty squares in the gameBoard
        int val = config.indexOf('g');

        // Return true if no more empty squares. Otherwise, false.
        if (val == -1){
            return true;
        }
        else {
            return false;
        }
    }

    public int evalBoard(){
        // Return 3 if computer wins
        if (wins('o')){
            return 3;
        }
        // Return 0 if human wins
        else if (wins('b')){
            return 0;
        }
        //Return 2 if game is draw
        else if (isDraw()){
            return 2;
        }
        // Game is not a draw and no player has won. Game is still undecided.
        else {
            return 1;
        }
    }
}
