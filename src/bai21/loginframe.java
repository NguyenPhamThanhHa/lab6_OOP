package bai21;
import javax.swing.*;
import java.awt.*;

public class loginframe extends JFrame {
public loginframe(){
    setTitle("Đăng nhập");
    setSize(350, 180);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new GridLayout(3,2,10,10));

    //khai báo các ô nhập liệu
    JTextField txtUsername = new JTextField();
    JPasswordField txtPassword = new JPasswordField();
    JButton btnLogin = new JButton("Đăng nhập");
    JButton btnExit = new JButton("Thoát");

    add(new JLabel("Tên đăng nhập:")); add(txtUsername);
    add(new JLabel("Mật khẩu:")); add(txtPassword);
    add(btnLogin); add(btnExit);

    //xử lý sự kiện
    btnLogin.addActionListener(e -> {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        if(username.equals("admin") && password.equals("123456")){
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
        } else
             {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng!");
        }
    });
btnExit.addActionListener(e -> 
        System.exit(0));
    }
    
    public static void main(String[] args) {
SwingUtilities.invokeLater(() -> new loginframe().setVisible(true));
    }
}

