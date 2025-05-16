package model;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
//这个类是主要功能，用来管理用户，包括注册，登录，保存用户等功能；我使用了一个临时名单users，而最终名单是保存在users.data文件中的。
public class UserManager {
    private static final String USER_INFORMATION = "users.data";//用户数据库
    private List<User> users;//临时名单

    public UserManager() {
        users = loadUsers();
    }

    public boolean register(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; //不能用户名重复
            }
        }
        users.add(new User(username, password));
        saveUsers();
        return true;
    }//注册，将新用户加入名单，保存名单

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }//登录验证

    private List<User> loadUsers() {
        List<User> userList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_INFORMATION))) {
            userList = (List<User>) ois.readObject();//强转换为list
        } catch (IOException | ClassNotFoundException e) {
            // 不存在，使用空的
        }
        return userList;
    }//加载一份用户名单，不存在就新建一个空的名单

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_INFORMATION))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//保存名单
}
