package view.login;

import view.FrameUtil;
import view.game.GameFrame;
import model.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginFrame extends JFrame {
    private JTextField username;
    private JTextField password;
    private JButton submitBtn;
    private JButton resetBtn;
    private GameFrame gameFrame;
    private JButton guestloginBtn;
    private JButton registerBtn;//添加注册和登录按钮
    private UserManager userManager;


    public LoginFrame(int width, int height) {
        this.setTitle("Login Frame");
        this.setLayout(null);
        this.setSize(width, height);
        this.userManager = new UserManager();
        JLabel userLabel = FrameUtil.createJLabel(this, new Point(50, 20), 70, 40, "username:");
        JLabel passLabel = FrameUtil.createJLabel(this, new Point(50, 80), 70, 40, "password:");
        username = FrameUtil.createJTextField(this, new Point(120, 20), 120, 40);
        password = FrameUtil.createJTextField(this, new Point(120, 80), 120, 40);

        submitBtn = FrameUtil.createButton(this, "Confirm", new Point(40, 140), 100, 40);
        resetBtn = FrameUtil.createButton(this, "Reset", new Point(160, 140), 100, 40);
        guestloginBtn = FrameUtil.createButton(this, "Guest Login", new Point(40, 190), 100, 40);
        registerBtn = FrameUtil.createButton(this, "Register", new Point(160, 190), 100, 40);
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameInput = username.getText();
                String passwordInput = password.getText();
                if (userManager.login(usernameInput, passwordInput)) {
                    if (gameFrame != null) {
                        gameFrame.setVisible(true);
                        gameFrame.setUser(usernameInput); // 可添加方法记录当前用户
                        setVisible(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "用户名或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username.setText("");
                password.setText("");
            }
        });
        guestloginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameFrame != null) {
                    gameFrame.setVisible(true);
                    gameFrame.setUser(null); // 游客登录，用户名为 null
                    setVisible(false);
                }
            }
        });

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameInput = username.getText();
                String passwordInput = password.getText();
                if (userManager.register(usernameInput, passwordInput)) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "注册成功", "注册", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "用户名已存在", "注册失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }
}

