package view.login;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            // 从资源文件加载图片
            URL resource = getClass().getResource(imagePath);
            if (resource != null) {
                backgroundImage = new ImageIcon(resource).getImage();
            } else {
                System.err.println("图片未找到: " + imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // 绘制背景图片（根据面板大小拉伸）
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
