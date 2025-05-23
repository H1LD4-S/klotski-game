package controller;

import model.Direction;
import model.MapModel;
import view.game.BoxComponent;
import view.game.GameFrame;
import view.game.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private final MapModel model;
    private final int[][] initialMatrix;
    private String currentUser;
    private int currentLevel;
    private boolean initialized = false;



    public GameController(GamePanel view, MapModel model) {
        this.view = view;
        this.model = model;

        this.initialMatrix = deepCopyMatrix(model.getMatrix());
        view.setController(this);
    }

    private int[][] deepCopyMatrix(int[][] source) {
        return Arrays.stream(source)
                .map(int[]::clone)
                .toArray(int[][]::new);
    }

    public boolean isWin(){
        return model.getId(1, 3) == 4 &&
                model.getId(1, 4) == 4 &&
                model.getId(2, 3) == 4 &&
                model.getId(2, 4) == 4;
    }

    public void setCurrentUser(String user) {
        this.currentUser = user;
        if (user != null) {
            autoLoadGame();//登录后加载进度
        }
    }

    public void setCurrentLevel(int level) {
        this.currentLevel = level;
    }
    public void saveGame() {
        if (currentUser == null) {
            return; // 游客无法保存进度
        }
        String filePath = currentUser + "_level" + currentLevel + ".data";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(model.getMatrix());
            oos.writeInt(view.getSteps());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadGame() {
        if (currentUser == null) {
            return; // 游客无法加载进度
        }
        String filePath = currentUser + "_level" + currentLevel + ".data";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            int[][] savedMatrix = (int[][]) ois.readObject();
            int savedSteps = ois.readInt();
            model.setMatrix(savedMatrix);
            view.clearBoxes();
            view.initialGame();
            view.setSteps(savedSteps);
            view.revalidate();
            view.repaint();
            System.out.println("游戏进度成功从 " + filePath+ "加载");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("加载游戏进度时出错: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "加载游戏进度失败，请检查保存文件是否存在", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void autoLoadGame() {
        String filePath = currentUser + "_level" + currentLevel + ".data";
        File file = new File(filePath);

        if (!file.exists()) {
            // 如果存档不存在，初始化默认存档
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                // 使用当前模型的初始矩阵和步数0
                oos.writeObject(model.getMatrix());
                oos.writeInt(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loadGame();
        }
    }

    public void restartGame() {
        model.setMatrix(deepCopyMatrix(initialMatrix));//换成深度拷贝
        view.clearBoxes();
        view.initialGame();
        view.setSteps(0); // 重置步数
        System.out.println("已重置到关卡原始状态");
    }

    public boolean doMove(int row, int col, Direction direction) {
        AudioPlayer.playMoveSound();
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

        else if (model.getId(row,col)==2||model.getId(row,col)==5||model.getId(row,col)==6||model.getId(row,col)==8){
            int a = model.getId(row,col);
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
            if (direction.getRow()==0){
                if (model.checkInWidthSize(nextCol)){
                    if (model.getId(row,nextCol)==0){
                        model.getMatrix()[row][nextCol] = a;
                        model.getMatrix()[row][col-direction.getCol()] = 0;
                        BoxComponent box = view.getSelectedBox();
                        box.setRow(row);
                        box.setCol(nextCol);
                        box.setLocation(box.getCol() * view.getGRID_SIZE()  + 2, box.getRow() * view.getGRID_SIZE() + 2);
                        box.repaint();
                        return true;
                    }else if (model.getId(row,nextCol)==a){
                        if (model.checkInWidthSize(nextCol+direction.getCol())){
                            if (model.getId(row,nextCol+direction.getCol())==0){
                                model.getMatrix()[row][nextCol+direction.getCol()] = a;
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
                        if (model.getId(row,col+1)==a){
                            if (model.checkInWidthSize(col+2)){
                                if (model.getId(row,col+2)==a){
                                    if (model.checkInWidthSize(col+3)){
                                        if (model.getId(row,col+3)==a){
                                            if (model.getId(nextRow,col)==0&&model.getId(nextRow,col+1)==0){
                                                model.getMatrix()[row][col]=0;
                                                model.getMatrix()[row][col+1]=0;
                                                model.getMatrix()[nextRow][col]=a;
                                                model.getMatrix()[nextRow][col+1]=a;
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
                                                model.getMatrix()[nextRow][col]=a;
                                                model.getMatrix()[nextRow][col-1]=a;
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
                                            model.getMatrix()[nextRow][col]=a;
                                            model.getMatrix()[nextRow][col-1]=a;
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
                                        model.getMatrix()[nextRow][col]=a;
                                        model.getMatrix()[nextRow][col+1]=a;
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
                                    model.getMatrix()[nextRow][col]=a;
                                    model.getMatrix()[nextRow][col+1]=a;
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
                                model.getMatrix()[nextRow][col]=a;
                                model.getMatrix()[nextRow][col-1]=a;
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
                            model.getMatrix()[nextRow][col]=a;
                            model.getMatrix()[nextRow][col-1]=a;
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

    public boolean isInitialized() {
        return initialized;
    }

    public void markInitialized() {
        this.initialized = true;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    //todo: add other methods such as loadGame, saveGame...

}
