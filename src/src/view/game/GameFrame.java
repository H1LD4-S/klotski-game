package view.game;

import controller.GameController;
import model.MapModel;
import view.FrameUtil;
import view.login.BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;
    private String currentUser;

    private JLabel stepLabel;
    private GamePanel gamePanel;
    private int currentLevel;



    public GameFrame(int width, int height, MapModel mapModel) {
        this.setTitle("华容道");
        this.setLayout(null);
        this.setSize(width, height);
        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(30, 30);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel);
        // 创建带背景的面板，并设置为窗口的内容面板
        GameBackgroundPanel backgroundPanel = new GameBackgroundPanel("/images/game.png"); // 替换为你的图片路径
        backgroundPanel.setLayout(null); // 使用绝对布局
        this.setContentPane(backgroundPanel); // 替换默认的ContentPane




        this.restartBtn = FrameUtil.createButton(this, "重新开始", new Point(gamePanel.getWidth() + 180, 450), 100, 50);
        this.loadBtn = FrameUtil.createButton(this, "载入", new Point(gamePanel.getWidth() + 180, 550), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "开始游戏", new Font("serif", Font.ITALIC, 35), new Point(gamePanel.getWidth() + 180, 300), 180, 50);
        stepLabel.setForeground(Color.WHITE);
        backgroundPanel.add(restartBtn);
        backgroundPanel.add(loadBtn);
        backgroundPanel.add(stepLabel);
        backgroundPanel.add(gamePanel);




        gamePanel.setStepLabel(stepLabel);

        this.loadBtn.addActionListener(e -> {
            if (currentUser != null) {
                controller.saveGame();
                JOptionPane.showMessageDialog(this, "游戏进度保存成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "游客用户无法保存游戏进度", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
            gamePanel.requestFocusInWindow();
        });

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });
        //todo: add other button here
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                if (!controller.isInitialized()) {
                    String filePath = controller.getCurrentUser() + "_level" + controller.getCurrentLevel() + ".data";
                    File saveFile = new File(filePath);

                    if (saveFile.exists()) {
                        controller.loadGame();
                    } else {
                        controller.restartGame();
                    }
                    controller.markInitialized();
                }// 由于打开第二关和第三关都会和第一关一样，而点击重启会正常，因此首次激活窗口时强制初始化
                //woc但是存档功能又没了！！啊啊啊啊啊啊啊啊
                //所以！应该写成只有在没有存档时才会初始化！！
                //已修改，欧耶
                //不行，又失败了woc，/(ㄒoㄒ)/
                //试试“强制深拷贝”好了
                //不对，没有用，根本不是这里的问题
            }
        });
    }
    public void setUser(String user) {
        this.currentUser = user;
        if (user == null) {
            loadBtn.setEnabled(false);
        } else {
            loadBtn.setEnabled(true);
        }
        controller.setCurrentUser(user);
    }
    public void setCurrentLevel(int level){
        this.currentLevel=level;
        controller.setCurrentLevel(level);//原来问题在这里！！！！！终于找到了哈哈哈哈哈哈哈哈哈哈
    }


}

