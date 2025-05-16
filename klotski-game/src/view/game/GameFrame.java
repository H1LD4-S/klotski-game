package view.game;

import controller.GameController;
import model.MapModel;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private String currentUser;
    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;


    private JLabel stepLabel;
    private GamePanel gamePanel;

    public GameFrame(int width, int height, MapModel mapModel) {
        this.setTitle("2025 CS109 Project Demo");
        this.setLayout(null);
        this.setSize(width, height);
        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel);

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 80, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanel.getWidth() + 80, 210), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "Start", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 70), 180, 50);
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
