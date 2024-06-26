package controller;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Random;

public class SudokuApp extends Application {

    private static final int SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    private static final int MAX_ERRORS = 3;

    private int[][] sudokuBoard;
    private int[][] solution;
    private int[][] initialBoard;
    private int errorCount;
    
    private static final double BUTTON_SIZE = 40;
    private static final double CELL_SIZE = 40;
    
    private AnchorPane anchorPane;
    
    private Label errorLabel ; 
    
    private Button reset ; 
    
    private Button startStopButton ; 
    
    private GridPane gridPane ; 
    
    @Override
    public void start(Stage primaryStage) {
    	anchorPane = new AnchorPane() ; 
    	anchorPane.setPrefHeight(1100);
    	anchorPane.setPrefWidth(600);
    	
        primaryStage.setTitle("Sudoku Game");

        // Khởi tạo bảng Sudoku và giải pháp
        initializeSudoku();

        // Tạo một GridPane
        gridPane = createSudokuGrid();
        gridPane.setPrefHeight(600);
        gridPane.setPrefWidth(600);
        gridPane.setLayoutX(10);
        gridPane.setLayoutY(10);
        gridPane.setAlignment(Pos.CENTER);
        
        errorLabel = new Label("Error : 0 ") ;
        errorLabel.setPrefSize(BUTTON_SIZE * 2, CELL_SIZE);
        errorLabel.setLayoutX(650);
        errorLabel.setLayoutY(10);
        gridPane.add(errorLabel, SIZE - 1, SIZE);
        
        reset = new Button ("RESET"); 
        reset.setLayoutX(700);
        reset.setLayoutY(50);
        reset.setPrefSize(60 ,40 );
       
        anchorPane.getChildren().addAll(gridPane, errorLabel , reset) ; 
        
        addStartStopButton(anchorPane);
        startStopButton.setLayoutX(640);
        startStopButton.setLayoutY(50);
            
        Scene scene = new Scene(anchorPane, 800 , 650 );
        
        primaryStage.centerOnScreen();
        
        primaryStage.setScene(scene);
      
        primaryStage.show();
    }
    
    private void initializeSudoku() {
        // Khởi tạo mảng giải pháp Sudoku
        solution = new int[SIZE][SIZE];
        solveSudoku(solution);

        // Khởi tạo mảng ban đầu Sudoku
        initialBoard = new int[SIZE][SIZE];
        copyArray(solution, initialBoard);

        // Xáo trộn các giá trị trong mảng ban đầu để tạo bảng Sudoku ngẫu nhiên
        shuffleSudokuBoard(initialBoard);

        // Khởi tạo mảng hiện tại của Sudoku
        sudokuBoard = new int[SIZE][SIZE];
        copyArray(initialBoard, sudokuBoard);

        // Đặt một số ô là 0 để người chơi điền
        setEmptyCells();
    }

    private void setEmptyCells() {
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                // Đặt một số ô là 0 (để người chơi điền) với xác suất 50%
                if (random.nextDouble() < 0.5) {
                    sudokuBoard[i][j] = 0;
                }
            }
        }
    }

    private void shuffleSudokuBoard(int[][] array) {  // xáo trộn các bảng 
        Random random = new Random();

        // Hoán đổi các hàng và cột con 3x3 ngẫu nhiên
        for (int i = 0; i < SIZE; i += SUBGRID_SIZE) {
            shuffleSubgridRows(array, i, random.nextInt(SUBGRID_SIZE));
            shuffleSubgridCols(array, i, random.nextInt(SUBGRID_SIZE));
        }
    }

    private void shuffleSubgridRows(int[][] array, int startRow, int targetRow) { // xáo trộn các hàng 3 * 3 
        int[] temp = array[startRow];
        array[startRow] = array[startRow + targetRow];
        array[startRow + targetRow] = temp;
    }

    private void shuffleSubgridCols(int[][] array, int startCol, int targetCol) {  // xáo trộn cột 3 * 3 
        for (int i = 0; i < SIZE; i++) {
            int temp = array[i][startCol];
            array[i][startCol] = array[i][startCol + targetCol];
            array[i][startCol + targetCol] = temp;
        }
    }

    private void copyArray(int[][] source, int[][] destination) {
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(source[i], 0, destination[i], 0, SIZE);
        }
    }

    private void solveSudoku(int[][] board) {
        // TODO: Implement your Sudoku solving algorithm here
        // This example uses a simple recursive backtracking algorithm for demonstration purposes.
        // A complete Sudoku-solving algorithm may involve more advanced techniques.

        // Placeholder: Recursive backtracking algorithm
        solveSudokuRecursive(board);
    }

    private boolean solveSudokuRecursive(int[][] board) {
        // TODO: Implement your recursive backtracking algorithm here
        // This example is a simple placeholder and doesn't fully solve the Sudoku puzzle.

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValidMove(board, row, col, num)) {
                            board[row][col] = num;

                            if (solveSudokuRecursive(board)) {
                                return true;
                            }

                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidMove(int[][] board, int row, int col, int num) {
        return !isInRow(board, row, num) && !isInColumn(board, col, num) && !isInSubgrid(board, row, col, num);
    }

    private boolean isInRow(int[][] board, int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInColumn(int[][] board, int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInSubgrid(int[][] board, int row, int col, int num) {
        int subgridStartRow = row - row % SUBGRID_SIZE;
        int subgridStartCol = col - col % SUBGRID_SIZE;

        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for (int j = 0; j < SUBGRID_SIZE; j++) {
                if (board[subgridStartRow + i][subgridStartCol + j] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    private GridPane createSudokuGrid() {
        gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // Duyệt qua từng ô của Sudoku và thêm TextField để hiển thị giá trị
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                TextField textField = new TextField();
                textField.setPrefSize(CELL_SIZE, CELL_SIZE);
                textField.setAlignment(Pos.CENTER);

                // Đặt màu sắc cho ô con 3x3
                setColorToSubgrid(textField, i, j);

                // Nếu giá trị của Sudoku khác 0, hiển thị giá trị đó
                if (sudokuBoard[i][j] != 0  ) {
                    textField.setText(String.valueOf(sudokuBoard[i][j]));
                    textField.setEditable(false);
                } else {
                    // Tạo biến cuối cùng mới để sử dụng trong lambda expression
                    final int finalI = i;
                    final int finalJ = j;

                    // Thêm sự kiện kiểm tra lỗi khi người chơi điền vào ô
                    textField.textProperty().addListener((observable, oldValue, newValue) ->
                            checkError(textField, finalI, finalJ));
                }

                // Thêm TextField vào GridPane
                textField.setPrefWidth(70);
                textField.setPrefHeight(70);
                gridPane.add(textField, j, i);
            }
        }
        return gridPane;
    }

    private void setColorToSubgrid(TextField textField, int row, int col) {
        int subgridRow = row / SUBGRID_SIZE;
        int subgridCol = col / SUBGRID_SIZE;

        // Màu sắc cho ô con 3x3
        if ((subgridRow + subgridCol) % 2 == 0) {
            textField.setStyle("-fx-control-inner-background: #d3d3d3;");
        } else {
            textField.setStyle("-fx-control-inner-background: #f0f0f0;");
        }
    }

    private void checkError(TextField textField, int row, int col) {

        if (textField.getText().isEmpty()) {
            return;
        }

        int inputValue = Integer.parseInt(textField.getText());
        if (isValidMove(sudokuBoard, row, col, inputValue)) {
            // Clear previous error style
            textField.setStyle("");
            if (initialBoard[row][col] != 0) {
                // Disable editing for cells with initial values
                textField.setEditable(false);
            }
            checkGameCompletion();
        } else {
            // Set error style for invalid move
            textField.setStyle("-fx-control-inner-background: #ffcccc;");
            errorCount++;
            updateErrorLabel(errorLabel, String.valueOf(errorCount) );
            
            if (errorCount >= MAX_ERRORS) {
                // Player loses when reaching the maximum number of errors
            	Alert alert = new Alert (AlertType.INFORMATION) ; 
            	alert.setTitle("");
            	alert.setHeaderText("");
            	alert.setContentText("Game Over - You made too many errors!");
            	alert.show();
            }
        }
    }

    private void updateErrorLabel(Label errorLabel , String errorCount) {
        errorLabel.setText("Errors: " + errorCount);
    }
    
    private void checkGameCompletion() {
        // Check if the player has filled in all cells correctly
        boolean isGameComplete = true;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (sudokuBoard[i][j] == 0 || !isValidMove(sudokuBoard, i, j, sudokuBoard[i][j])) {
                    isGameComplete = false;
                    break;
                }
            }
        }

        if (isGameComplete) {
            System.out.println("Congratulations! You've completed the Sudoku puzzle!");  
        }
    }

    private void addStartStopButton(AnchorPane anchorPane) {
        startStopButton = new Button("Start/Stop");
        startStopButton.setPrefHeight(40);
        startStopButton.setPrefWidth(60);
        anchorPane.getChildren().add(startStopButton);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
