package view.game;

import controller.GameController;
import model.Direction;
import model.MapModel;
import view.FrameUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * It is the subclass of ListenerPanel, so that it should implement those four methods: do move left, up, down ,right.
 * The class contains a grids, which is the corresponding GUI view of the matrix variable in MapMatrix.
 */
public class GamePanel extends ListenerPanel {
    private List<BoxComponent> boxes;
    private MapModel model;
    private GameController controller;
    private JLabel stepLabel;
    private int steps;
    private final int GRID_SIZE = 200;
    private BoxComponent selectedBox;
    private static final Map<Integer, Image> IMAGE_CACHE = new HashMap<>();
    private JLabel winLabel;

    public int getSteps() {
        return steps;
    }
    public void setSteps(int steps) {
        this.steps = steps;
        if (stepLabel != null) {
            stepLabel.setText("Step: " + steps);
        }
    }
    public void clearBoxes() {
        // 实现清空方块的逻辑，例如移除所有组件
        this.removeAll();
        this.revalidate();
        this.repaint();
    }


    public GamePanel(MapModel model) {
        boxes = new ArrayList<>();
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setSize(model.getWidth() * GRID_SIZE + 4, model.getHeight() * GRID_SIZE + 4);
        this.model = model;
        this.selectedBox = null;
        initialGame();
    }

    /*
                        {1, 2, 2, 1, 1},
                        {3, 4, 4, 2, 2},
                        {3, 4, 4, 1, 0},
                        {1, 2, 2, 1, 0},
                        {1, 1, 1, 1, 1}
     */
    public void initialGame() {
        this.steps = 0;
        //copy a map
        int[][] map = new int[model.getHeight()][model.getWidth()];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = model.getId(i, j);
            }
        }
        for (Component a : this.getComponents()) {
            if (a instanceof BoxComponent) {
                this.remove(a);
            }
        }
        boxes.clear();
        //build Component
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int id = map[i][j];
                BoxComponent box = null;
                Image image = loadImageForId(id);
                if (image!=null){
                    box = new BoxComponent(image, i, j);
                    if (map[i][j] == 1) {
                        box = new BoxComponent(image, i, j);
                        box.setSize(GRID_SIZE, GRID_SIZE);
                        map[i][j] = 7;
                    } else if (map[i][j] == 2) {
                        box = new BoxComponent(image, i, j);
                        box.setSize(GRID_SIZE * 2, GRID_SIZE);
                        map[i][j] = 7;
                        map[i][j + 1] = 7;
                    } else if (map[i][j] == 3) {
                        box = new BoxComponent(image, i, j);
                        box.setSize(GRID_SIZE, GRID_SIZE * 2);
                        map[i][j] = 7;
                        map[i + 1][j] = 7;
                    } else if (map[i][j] == 4) {
                        box = new BoxComponent(image, i, j);
                        box.setSize(GRID_SIZE * 2, GRID_SIZE * 2);
                        map[i][j] = 7;
                        map[i + 1][j] = 7;
                        map[i][j + 1] = 7;
                        map[i + 1][j + 1] = 7;
                    }else if (map[i][j] == 5) {
                        box = new BoxComponent(image, i, j);
                        box.setSize(GRID_SIZE * 2, GRID_SIZE);
                        map[i][j] = 7;
                        map[i][j + 1] = 7;
                    }else if (map[i][j] == 6) {
                        box = new BoxComponent(image, i, j);
                        box.setSize(GRID_SIZE * 2, GRID_SIZE);
                        map[i][j] = 7;
                        map[i][j + 1] = 7;
                    }else if (map[i][j] == 8) {
                        box = new BoxComponent(image, i, j);
                        box.setSize(GRID_SIZE * 2, GRID_SIZE);
                        map[i][j] = 7;
                        map[i][j + 1] = 7;
                    }
                }
                if (box != null) {
                    box.setLocation(j * GRID_SIZE + 2, i * GRID_SIZE + 2);
                    boxes.add(box);
                    this.add(box);
                }
            }
        }
        selectedBox = null;
        steps = 0;
        if (stepLabel != null) {
            stepLabel.setText("Step: 0");
        }

        this.revalidate();
        this.repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
        this.setBorder(border);
    }

    private Image loadImageForId(int id) {
        if (IMAGE_CACHE.containsKey(id)) {
            return IMAGE_CACHE.get(id);
        }
        try {
            // 根据 ID 加载对应图片（路径需与资源目录结构匹配）
            String imagePath = "/images/block_" + id + ".png";
            Image image = new ImageIcon(getClass().getResource(imagePath)).getImage();
            IMAGE_CACHE.put(id, image);
            return image;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void doMouseClick(Point point) {
        Component component = this.getComponentAt(point);
        if (component instanceof BoxComponent clickedComponent) {
            if (selectedBox == null) {
                selectedBox = clickedComponent;
                selectedBox.setSelected(true);
            } else if (selectedBox != clickedComponent) {
                selectedBox.setSelected(false);
                clickedComponent.setSelected(true);
                selectedBox = clickedComponent;
            } else {
                clickedComponent.setSelected(false);
                selectedBox = null;
            }
        }
    }



    @Override
    public void doMoveRight() {
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.RIGHT)) {
                System.out.println("Click VK_RIGHT");
                afterMove();
            }
        }
    }

    @Override
    public void doMoveLeft() {
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.LEFT)) {
                System.out.println("Click VK_LEFT");
                afterMove();
            }
        }
    }

    @Override
    public void doMoveUp() {
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.UP)) {
                System.out.println("Click VK_Up");
                afterMove();
            }
        }
    }

    @Override
    public void doMoveDown() {
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.DOWN)) {
                System.out.println("Click VK_DOWN");
                afterMove();
            }
        }
    }

    public void afterMove() {
        this.steps++;
        this.stepLabel.setText(String.format("Step: %d", this.steps));
        repaint();  // 重绘界面
        checkWin(); // 检查是否胜利

    }
    private void checkWin() {
        if (controller.isWin()) {  // 假设 controller 有一个 isWin() 方法
            showWinMessage();      // 显示胜利提示
            disableControls();      // 禁用后续操作
        }
    }

    private void showWinMessage() {
        JOptionPane.showMessageDialog(
                this,
                "恭喜！曹操已经逃出华容道！",
                "游戏胜利",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    private void disableControls() {
        selectedBox = null;  // 清空当前选中方块
        // 如果有其他控件（如重玩按钮），可以在这里处理
    }

    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }


    public void setController(GameController controller) {
        this.controller = controller;
    }

    public BoxComponent getSelectedBox() {
        return selectedBox;
    }

    public int getGRID_SIZE() {
        return GRID_SIZE;
    }


}
