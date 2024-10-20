package cleancode.minesweeper.tobe.minesweeper.io.sign;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;

public interface CellSignProvidable {

    String provide(CellSnapshot cellSnapshot);

    boolean supports(CellSnapshot cellSnapshot);
}
