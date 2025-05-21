package view.login;

import model.UserManager;
import view.FrameUtil;
import view.game.GameFrame;

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
    private MenuFrame menuFrame;


    public LoginFrame(int width, int height) {
        this.setTitle("华容道");
        this.setLayout(null);
        this.setSize(width, height);
        this.userManager = new UserManager();

        // 创建带背景的面板，并设置为窗口的内容面板
        BackgroundPanel backgroundPanel = new BackgroundPanel("/images/login.png"); // 替换为你的图片路径
        backgroundPanel.setLayout(null); // 使用绝对布局
        this.setContentPane(backgroundPanel); // 替换默认的ContentPane



        JLabel userLabel = FrameUtil.createJLabel(this, new Point(90, 70), 120, 48, "用户名:");
        JLabel passLabel = FrameUtil.createJLabel(this, new Point(90, 120), 120, 48, "密码:");
        userLabel.setForeground(Color.WHITE);
        passLabel.setForeground(Color.WHITE);
        username = FrameUtil.createJTextField(this, new Point(150, 70), 120, 40);
        password = FrameUtil.createJTextField(this, new Point(150, 120), 120, 40);

        submitBtn = FrameUtil.createButton(this, "登录", new Point(60, 200), 130, 40);
        resetBtn = FrameUtil.createButton(this, "清空", new Point(220, 200), 130, 40);
        guestloginBtn = FrameUtil.createButton(this, "以游客身份游戏", new Point(60, 270), 130, 40);
        registerBtn = FrameUtil.createButton(this, "注册", new Point(220, 270), 130, 40);

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameInput = username.getText();
                String passwordInput = password.getText();
                if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this,"输入不能为空","登陆失败",JOptionPane.ERROR_MESSAGE); // 提示输入不能为空
                    return;
                }
                if (userManager.login(usernameInput, passwordInput)) {
                    if (menuFrame != null) {
                        menuFrame.setCurrentUser(usernameInput);
                        menuFrame.setVisible(true);
                        setVisible(false);//这里原来忘了把用户名传过去，导致无法保存游戏进度！！！
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
                }else {
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

    public void setMenuFrame(MenuFrame menuFrame){
        this.menuFrame = menuFrame;
    }

}
