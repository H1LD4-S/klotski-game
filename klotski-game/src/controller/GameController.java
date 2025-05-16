package controller;

import model.Direction;
import model.MapModel;
import view.game.BoxComponent;
import view.game.GamePanel;

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private final MapModel model;

    public GameController(GamePanel view, MapModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
    }

    public void restartGame() {
        System.out.println("Do restart game here");
    }

    public boolean doMove(int row, int col, Direction direction){
        if (model.getId(row, col) == 1) {
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
            if (model.checkInHeightSize(nextRow) && model.checkInWidthSize(nextCol)) {
                if (model.getId(nextRow, nextCol) == 0) {
                    model.getMatrix()[row][col] = 0;
                    model.getMatrix()[nextRow][nextCol] = 1;
                    BoxComponent box = view.getSelectedBox();
                    box.setRow(nextRow);
                    box.setCol(nextCol);
                    box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                    box.repaint();
                    return true;
                }
            }else {
                return false;
            }
        }

        else if (model.getId(row,col)==2){
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
            if (direction.getRow()==0){
                if (model.checkInWidthSize(nextCol)){
                    if (model.getId(row,nextCol)==0){
                        model.getMatrix()[row][nextCol] = 2;
                        model.getMatrix()[row][col-direction.getCol()] = 0;
                        BoxComponent box = view.getSelectedBox();
                        box.setRow(row);
                        box.setCol(nextCol);
                        box.setLocation(box.getCol() * view.getGRID_SIZE()  + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        box.repaint();
                        return true;
                    }else if (model.getId(row,nextCol)==2){
                        if (model.checkInWidthSize(nextCol+direction.getCol())){
                            if (model.getId(row,nextCol+direction.getCol())==0){
                                model.getMatrix()[row][nextCol+direction.getCol()] = 2;
                                model.getMatrix()[row][col] = 0;
                                BoxComponent box = view.getSelectedBox();
                                box.setRow(row);
                                box.setCol(nextCol);
                                box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                box.repaint();
                                return true;
                            }
                        }
                    }
                }
            } else {
                if (model.checkInHeightSize(nextRow)){
                    if (model.checkInWidthSize(col+1)){
                        if (model.getId(row,col+1)==2){
                            if (model.checkInWidthSize(col+2)){
                                if (model.getId(row,col+2)==2){
                                    if (model.checkInWidthSize(col+3)){
                                        if (model.getId(row,col+3)==2){
                                            if (model.getId(nextRow,col)==0&&model.getId(nextRow,col+1)==0){
                                                model.getMatrix()[row][col]=0;
                                                model.getMatrix()[row][col+1]=0;
                                                model.getMatrix()[nextRow][col]=2;
                                                model.getMatrix()[nextRow][col+1]=2;
                                                BoxComponent box = view.getSelectedBox();
                                                box.setRow(nextRow);
                                                box.setCol(col);
                                                box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                                box.repaint();
                                                return true;
                                            }
                                        }else {
                                            if (model.getId(nextRow,col)==0&&model.getId(nextRow,col-1)==0){
                                                model.getMatrix()[row][col]=0;
                                                model.getMatrix()[row][col-1]=0;
                                                model.getMatrix()[nextRow][col]=2;
                                                model.getMatrix()[nextRow][col-1]=2;
                                                BoxComponent box = view.getSelectedBox();
                                                box.setRow(nextRow);
                                                box.setCol(col);
                                                box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                                box.repaint();
                                                return true;
                                            }
                                        }
                                    }else {
                                        if (model.getId(nextRow,col)==0&&model.getId(nextRow,col-1)==0){
                                            model.getMatrix()[row][col]=0;
                                            model.getMatrix()[row][col-1]=0;
                                            model.getMatrix()[nextRow][col]=2;
                                            model.getMatrix()[nextRow][col-1]=2;
                                            BoxComponent box = view.getSelectedBox();
                                            box.setRow(nextRow);
                                            box.setCol(col);
                                            box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                            box.repaint();
                                            return true;
                                        }
                                    }
                                }else {
                                    if (model.getId(nextRow,col)==0&&model.getId(nextRow,col+1)==0){
                                        model.getMatrix()[row][col]=0;
                                        model.getMatrix()[row][col+1]=0;
                                        model.getMatrix()[nextRow][col]=2;
                                        model.getMatrix()[nextRow][col+1]=2;
                                        BoxComponent box = view.getSelectedBox();
                                        box.setRow(nextRow);
                                        box.setCol(col);
                                        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                        box.repaint();
                                        return true;
                                    }
                                }
                            }else {
                                if (model.getId(nextRow,col)==0&&model.getId(nextRow,col+1)==0){
                                    model.getMatrix()[row][col]=0;
                                    model.getMatrix()[row][col+1]=0;
                                    model.getMatrix()[nextRow][col]=2;
                                    model.getMatrix()[nextRow][col+1]=2;
                                    BoxComponent box = view.getSelectedBox();
                                    box.setRow(nextRow);
                                    box.setCol(col);
                                    box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                    box.repaint();
                                    return true;
                                }
                            }
                        }else {
                            if (model.getId(nextRow,col)==0&&model.getId(nextRow,col-1)==0){
                                model.getMatrix()[row][col]=0;
                                model.getMatrix()[row][col-1]=0;
                                model.getMatrix()[nextRow][col]=2;
                                model.getMatrix()[nextRow][col-1]=2;
                                BoxComponent box = view.getSelectedBox();
                                box.setRow(nextRow);
                                box.setCol(col);
                                box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                box.repaint();
                                return true;
                            }
                        }

                    }else {
                        if (model.getId(nextRow,col)==0&&model.getId(nextRow,col-1)==0){
                            model.getMatrix()[row][col]=0;
                            model.getMatrix()[row][col-1]=0;
                            model.getMatrix()[nextRow][col]=2;
                            model.getMatrix()[nextRow][col-1]=2;
                            BoxComponent box = view.getSelectedBox();
                            box.setRow(nextRow);
                            box.setCol(col);
                            box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                            box.repaint();
                            return true;
                        }
                    }
                }

            }

        }
        //关羽
        else if (model.getId(row,col)==3){
            int nextCol = col + direction.getCol();
            int nextRow = row + direction.getRow();
            if (direction.getCol()==0){
                if (model.checkInHeightSize(nextRow)){
                    if (model.getId(nextRow,col)==0){
                        model.getMatrix()[nextRow][col] = 3;
                        model.getMatrix()[row-direction.getRow()][col] = 0;
                        BoxComponent box = view.getSelectedBox();
                        box.setRow(nextRow);
                        box.setCol(col);
                        box.setLocation(box.getCol() * view.getGRID_SIZE()  + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        box.repaint();
                        return true;
                    }else if (model.getId(nextRow,col)==3){
                        if (model.checkInHeightSize(nextRow+direction.getRow())){
                            if (model.getId(nextRow+direction.getRow(),col)==0){
                                model.getMatrix()[nextRow+direction.getRow()][col] = 3;
                                model.getMatrix()[row][col] = 0;
                                BoxComponent box = view.getSelectedBox();
                                box.setRow(nextRow);
                                box.setCol(col);
                                box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                box.repaint();
                                return true;
                            }
                        }
                    }
                }
            }else {
                if (model.checkInWidthSize(nextCol)){
                    if (model.checkInHeightSize(row+1)){
                        if (model.getId(row+1,col)==3){
                            if (model.checkInHeightSize(row+2)){
                                if (model.getId(row+2,col)==3){
                                    if (model.checkInHeightSize(row+3)){
                                        if (model.getId(row+3,col)==3){
                                            if (model.getId(row,nextCol)==0&&model.getId(row+1,nextCol)==0){
                                                model.getMatrix()[row][col]=0;
                                                model.getMatrix()[row+1][col]=0;
                                                model.getMatrix()[row][nextCol]=3;
                                                model.getMatrix()[row+1][nextCol]=3;
                                                BoxComponent box = view.getSelectedBox();
                                                box.setRow(row);
                                                box.setCol(nextCol);
                                                box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                                box.repaint();
                                                return true;
                                            }
                                        }else {
                                            if (model.getId(row,nextCol)==0&&model.getId(row-1,nextCol)==0){
                                                model.getMatrix()[row][col]=0;
                                                model.getMatrix()[row-1][col]=0;
                                                model.getMatrix()[row][nextCol]=3;
                                                model.getMatrix()[row-1][nextCol]=3;
                                                BoxComponent box = view.getSelectedBox();
                                                box.setRow(row);
                                                box.setCol(nextCol);
                                                box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                                box.repaint();
                                                return true;
                                            }
                                        }
                                    }else {
                                        if (model.getId(row,nextCol)==0&&model.getId(row-1,nextCol)==0){
                                            model.getMatrix()[row][col]=0;
                                            model.getMatrix()[row-1][col]=0;
                                            model.getMatrix()[row][nextCol]=3;
                                            model.getMatrix()[row-1][nextCol]=3;
                                            BoxComponent box = view.getSelectedBox();
                                            box.setRow(row);
                                            box.setCol(nextCol);
                                            box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                            box.repaint();
                                            return true;
                                        }
                                    }
                                }else {
                                    if (model.getId(row,nextCol)==0&&model.getId(row+1,nextCol)==0){
                                        model.getMatrix()[row][col]=0;
                                        model.getMatrix()[row+1][col]=0;
                                        model.getMatrix()[row][nextCol]=3;
                                        model.getMatrix()[row+1][nextCol]=3;
                                        BoxComponent box = view.getSelectedBox();
                                        box.setRow(row);
                                        box.setCol(nextCol);
                                        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                        box.repaint();
                                        return true;
                                    }
                                }
                            }else {
                                if (model.getId(row,nextCol)==0&&model.getId(row+1,nextCol)==0){
                                    model.getMatrix()[row][col]=0;
                                    model.getMatrix()[row+1][col]=0;
                                    model.getMatrix()[row][nextCol]=3;
                                    model.getMatrix()[row+1][nextCol]=3;
                                    BoxComponent box = view.getSelectedBox();
                                    box.setRow(row);
                                    box.setCol(nextCol);
                                    box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                    box.repaint();
                                    return true;
                                }
                            }
                        }else {
                            if (model.getId(row,nextCol)==0&&model.getId(row-1,nextCol)==0){
                                model.getMatrix()[row][col]=0;
                                model.getMatrix()[row-1][col]=0;
                                model.getMatrix()[row][nextCol]=3;
                                model.getMatrix()[row-1][nextCol]=3;
                                BoxComponent box = view.getSelectedBox();
                                box.setRow(row);
                                box.setCol(nextCol);
                                box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                box.repaint();
                                return true;
                            }
                        }

                    }else {
                        if (model.getId(row,nextCol)==0&&model.getId(row-1,nextCol)==0){
                            model.getMatrix()[row][col]=0;
                            model.getMatrix()[row-1][col]=0;
                            model.getMatrix()[row][nextCol]=3;
                            model.getMatrix()[row-1][nextCol]=3;
                            BoxComponent box = view.getSelectedBox();
                            box.setRow(row);
                            box.setCol(nextCol);
                            box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                            box.repaint();
                            return true;
                        }
                    }
                }
            }
        }
        //曹操
        else if (model.getId(row,col)==4) {
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
            if (direction.getCol()==0){
                if (model.checkInHeightSize(nextRow)) {
                    if (model.getId(nextRow,col)==4){
                       if (model.checkInHeightSize(nextRow+1)){
                           if (model.getId(nextRow+direction.getRow(),col)==0){
                               if (model.checkInWidthSize(col+1)) {
                                   if (model.getId(row,col+1)==4){
                                       if (model.getId(nextRow+direction.getRow(),col+1)==0){
                                           model.getMatrix()[row][col]=0;
                                           model.getMatrix()[row][col+1]=0;
                                           model.getMatrix()[nextRow][col]=4;
                                           model.getMatrix()[nextRow][col+1]=4;
                                           model.getMatrix()[nextRow+direction.getRow()][col]=4;
                                           model.getMatrix()[nextRow+direction.getRow()][col+1]=4;
                                           BoxComponent box = view.getSelectedBox();
                                           box.setRow(nextRow);
                                           box.setCol(col);
                                           box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                           box.repaint();
                                           return true;
                                       }else return false;
                                   }else if (model.getId(nextRow+direction.getRow(),col-1)==0){
                                       model.getMatrix()[row][col]=0;
                                       model.getMatrix()[row][col-1]=0;
                                       model.getMatrix()[nextRow][col]=4;
                                       model.getMatrix()[nextRow][col-1]=4;
                                       model.getMatrix()[nextRow+direction.getRow()][col]=4;
                                       model.getMatrix()[nextRow+direction.getRow()][col-1]=4;
                                       BoxComponent box = view.getSelectedBox();
                                       box.setRow(nextRow);
                                       box.setCol(col);
                                       box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                       box.repaint();
                                       return true;
                                   }else return false;
                               }else if (model.getId(nextRow+direction.getRow(),col-1)==0){
                                   model.getMatrix()[row][col]=0;
                                   model.getMatrix()[row][col-1]=0;
                                   model.getMatrix()[nextRow][col]=4;
                                   model.getMatrix()[nextRow][col-1]=4;
                                   model.getMatrix()[nextRow+direction.getRow()][col]=4;
                                   model.getMatrix()[nextRow+direction.getRow()][col-1]=4;
                                   BoxComponent box = view.getSelectedBox();
                                   box.setRow(nextRow);
                                   box.setCol(col);
                                   box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                   box.repaint();
                                   return true;
                               }else return false;
                           }else return false;
                       }else return false;
                    }else if (model.getId(nextRow,col)==0){
                        if (model.checkInWidthSize(col+1)){
                            if (model.getId(row,col+1)==4){
                                if (model.getId(nextRow,col+1)==0){
                                    model.getMatrix()[row-direction.getRow()][col]=0;
                                    model.getMatrix()[row-direction.getRow()][col+1]=0;
                                    model.getMatrix()[row][col]=4;
                                    model.getMatrix()[row][col+1]=4;
                                    model.getMatrix()[nextRow][col]=4;
                                    model.getMatrix()[nextRow][col+1]=4;
                                    BoxComponent box = view.getSelectedBox();
                                    box.setRow(nextRow);
                                    box.setCol(col);
                                    box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                    box.repaint();
                                    return true;
                                }else return false;
                            }else if (model.getId(nextRow,col-1)==0){
                                model.getMatrix()[row-direction.getRow()][col]=0;
                                model.getMatrix()[row-direction.getRow()][col-1]=0;
                                model.getMatrix()[row][col]=4;
                                model.getMatrix()[row][col-1]=4;
                                model.getMatrix()[nextRow][col]=4;
                                model.getMatrix()[nextRow][col-1]=4;
                                BoxComponent box = view.getSelectedBox();
                                box.setRow(nextRow);
                                box.setCol(col);
                                box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                box.repaint();
                                return true;
                            }else return false;
                        }else if (model.getId(nextRow,col-1)==0){
                            model.getMatrix()[row-direction.getRow()][col]=0;
                            model.getMatrix()[row-direction.getRow()][col-1]=0;
                            model.getMatrix()[row][col]=4;
                            model.getMatrix()[row][col-1]=4;
                            model.getMatrix()[nextRow][col]=4;
                            model.getMatrix()[nextRow][col-1]=4;
                            BoxComponent box = view.getSelectedBox();
                            box.setRow(nextRow);
                            box.setCol(col);
                            box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                            box.repaint();
                            return true;
                        }else return false;
                    }
                }
            }else if (direction.getRow()==0){
                if (model.checkInWidthSize(nextCol)) {
                    if (model.getId(row,nextCol)==4){
                        if (model.checkInWidthSize(nextCol+direction.getCol())){
                            if (model.getId(row,nextCol+direction.getCol())==0){
                                if (model.checkInWidthSize(nextCol)) {
                                    if (model.getId(row,nextCol)==4){
                                        if (model.getId(row+1,nextCol+direction.getCol())==0){
                                            model.getMatrix()[row][col]=0;
                                            model.getMatrix()[row+1][col]=0;
                                            model.getMatrix()[row][nextCol]=4;
                                            model.getMatrix()[row+1][nextCol]=4;
                                            model.getMatrix()[row][nextCol+direction.getCol()]=4;
                                            model.getMatrix()[row+1][nextCol+direction.getCol()]=4;
                                            BoxComponent box = view.getSelectedBox();
                                            box.setRow(row);
                                            box.setCol(nextCol);
                                            box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                            box.repaint();
                                            return true;
                                        }else return false;
                                    }else if (model.getId(row-1,nextCol+direction.getCol())==0){
                                        model.getMatrix()[row][col]=0;
                                        model.getMatrix()[row-1][col]=0;
                                        model.getMatrix()[row][nextCol]=4;
                                        model.getMatrix()[row-1][nextCol]=4;
                                        model.getMatrix()[row][nextCol+direction.getCol()]=4;
                                        model.getMatrix()[row-1][nextCol+direction.getCol()]=4;
                                        BoxComponent box = view.getSelectedBox();
                                        box.setRow(row);
                                        box.setCol(nextCol);
                                        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                        box.repaint();
                                        return true;
                                    }else return false;
                                }else if (model.getId(row-1,nextCol+direction.getCol())==0){
                                    model.getMatrix()[row][col]=0;
                                    model.getMatrix()[row-1][col]=0;
                                    model.getMatrix()[row][nextCol]=4;
                                    model.getMatrix()[row-1][nextCol]=4;
                                    model.getMatrix()[row][nextCol+direction.getCol()]=4;
                                    model.getMatrix()[row-1][nextCol+direction.getCol()]=4;
                                    BoxComponent box = view.getSelectedBox();
                                    box.setRow(row);
                                    box.setCol(nextCol);
                                    box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                    box.repaint();
                                    return true;
                                }else return false;
                            }else return false;
                        }else return false;
                    }else if (model.getId(row,nextCol)==0){
                        if (model.checkInWidthSize(col+1)){
                            if (model.getId(row,col+1)==4){
                                if (model.getId(row+1, nextCol)==0){
                                    model.getMatrix()[row][col-direction.getCol()]=0;
                                    model.getMatrix()[row+1][col-direction.getCol()]=0;
                                    model.getMatrix()[row][col]=4;
                                    model.getMatrix()[row+1][col]=4;
                                    model.getMatrix()[row][nextCol]=4;
                                    model.getMatrix()[row+1][nextCol]=4;
                                    BoxComponent box = view.getSelectedBox();
                                    box.setRow(row);
                                    box.setCol(nextCol);
                                    box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                    box.repaint();
                                    return true;
                                }else return false;
                            }else if (model.getId(row-1,nextCol)==0){
                                model.getMatrix()[row][col-direction.getCol()]=0;
                                model.getMatrix()[row-1][col-direction.getCol()]=0;
                                model.getMatrix()[row][col]=4;
                                model.getMatrix()[row-1][col]=4;
                                model.getMatrix()[row][nextCol]=4;
                                model.getMatrix()[row-1][nextCol]=4;
                                BoxComponent box = view.getSelectedBox();
                                box.setRow(row);
                                box.setCol(nextCol);
                                box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                                box.repaint();
                                return true;
                            }else return false;
                        }else if (model.getId(row-1,nextCol)==0){
                            model.getMatrix()[row][col-direction.getCol()]=0;
                            model.getMatrix()[row-1][col-direction.getCol()]=0;
                            model.getMatrix()[row][col]=4;
                            model.getMatrix()[row-1][col]=4;
                            model.getMatrix()[row][nextRow]=4;
                            model.getMatrix()[row-1][nextCol]=4;
                            BoxComponent box = view.getSelectedBox();
                            box.setRow(row);
                            box.setCol(nextCol);
                            box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                            box.repaint();
                            return true;
                        }else return false;
                    }
                }
            }
        }
        return false;

    }
    //todo: add other methods such as loadGame, saveGame...

}
