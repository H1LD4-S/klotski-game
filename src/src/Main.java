import model.MapModel;
import view.game.GameFrame;
import view.login.LoginFrame;
import view.login.MenuFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(700, 400);
            loginFrame.setVisible(true);





            //GameFrame menuFrame = new GameFrame(1200,700,game1);
            //menuFrame.setVisible(false);
            //loginFrame.setGameFrame(menuFrame);



             MenuFrame menuFrame = new MenuFrame(500,500);
             menuFrame.setVisible(false);
             loginFrame.setMenuFrame(menuFrame);

        });
    }
}
