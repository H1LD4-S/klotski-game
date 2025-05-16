import model.MapModel;
import view.game.GameFrame;
import view.login.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(280, 280);
            loginFrame.setVisible(true);
            MapModel mapModel = new MapModel(new int[][]{
                    {2, 2, 5, 5, 1},
                    {4, 4, 3, 1, 0},
                    {4, 4, 3, 1, 0},
                    {6, 6, 8, 8, 1}
            });
            GameFrame gameFrame = new GameFrame(1500, 900, mapModel);
            gameFrame.setVisible(false);
            loginFrame.setGameFrame(gameFrame);
        });
    }
}
