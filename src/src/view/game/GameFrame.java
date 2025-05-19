package view.game;

import controller.GameController;
import model.MapModel;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;
    private String currentUser;

    private JLabel stepLabel;
    private GamePanel gamePanel;



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

        this.restartBtn = FrameUtil.createButton(this, "重新开始", new Point(gamePanel.getWidth() + 180, 620), 100, 50);
        this.loadBtn = FrameUtil.createButton(this, "载入", new Point(gamePanel.getWidth() + 180, 710), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "开始游戏", new Font("serif", Font.ITALIC, 35), new Point(gamePanel.getWidth() + 180, 470), 180, 50);
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

}
