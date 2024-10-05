package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.GameLevel;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {

    private final Cell[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel) {
        int colSize = gameLevel.getColSize();
        int rowSize = gameLevel.getRowSize();
        board = new Cell[rowSize][colSize];

        landMineCount = gameLevel.getLandMineCount();;
    }

    public void initializeGame() {
        int rowSize = getRowSize();
        int colSize = getColSize();
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                board[row][col] = Cell.create();
            }
        }

        for (int i = 0; i < landMineCount; i++) {
            int landMineCol = new Random().nextInt(colSize);
            int landMindRow = new Random().nextInt(rowSize);
            Cell cell = findCell(landMindRow, landMineCol);
            cell.turnOnLandMine();
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if (isLandMineCell(row, col)) {
                    continue;
                }
                int count = countNearbyLandMines(row, col);
                Cell cell = findCell(row, col);
                cell.updateNearbyLandMineCount(count);
            }
        }
    }

    public boolean isAllCellChecked() {
        return Arrays.stream(board) // Stream<String[]>
                .flatMap(Arrays::stream) // Stream<String>
                .allMatch(Cell::isChecked);
    }

    public String getSign(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        return cell.getSign();
    }

    public void flag(int rowIndex, int ColIndex) {
        Cell cell = findCell(rowIndex, ColIndex);
        cell.flag();
    }

    public void open(int selectedRowIndex, int selectedColIndex) {
        Cell cell = findCell(selectedRowIndex, selectedColIndex);
        cell.open();
    }

    public boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        Cell cell = findCell(selectedRowIndex, selectedColIndex);
        return cell.isLandMine();
    }

    private Cell findCell(int rowIndex, int colIndex) {
        return board[rowIndex][colIndex];
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    private int countNearbyLandMines(int row, int low) {
        int rowSize = board.length;
        int colSize = board[0].length;
        int count = 0;
        if (row - 1 >= 0 && low - 1 >= 0 && isLandMineCell(row - 1, low - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, low)) {
            count++;
        }
        if (row - 1 >= 0 && low + 1 < colSize && isLandMineCell(row - 1, low + 1)) {
            count++;
        }
        if (low - 1 >= 0 && isLandMineCell(row, low - 1)) {
            count++;
        }
        if (low + 1 < colSize && isLandMineCell(row, low + 1)) {
            count++;
        }
        if (row + 1 < rowSize && low - 1 >= 0 && isLandMineCell(row + 1, low - 1)) {
            count++;
        }
        if (row + 1 < rowSize && isLandMineCell(row + 1, low)) {
            count++;
        }
        if (row + 1 < rowSize && low + 1 < colSize && isLandMineCell(row + 1, low + 1)) {
            count++;
        }
        return count;
    }

    public void openSurroundedCells(int row, int col) {
        if (row < 0 || row >= getRowSize() || col < 0 || col >= getColSize()) {
            return;
        }
        if (isOpenedCell(row, col)) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }

        open(row, col);

        if (doesCellHaveLandMineCount(row, col)) {
            return;
        }

        openSurroundedCells(row - 1, col - 1);
        openSurroundedCells(row - 1, col);
        openSurroundedCells(row - 1, col + 1);
        openSurroundedCells(row, col - 1);
        openSurroundedCells(row, col + 1);
        openSurroundedCells(row + 1, col - 1);
        openSurroundedCells(row + 1, col);
        openSurroundedCells(row + 1, col + 1);
    }

    private boolean doesCellHaveLandMineCount(int row, int col) {
        return findCell(row, col).hasLandMineCount();
    }

    private boolean isOpenedCell(int row, int col) {
        return findCell(row, col).isOpened();
    }
}
