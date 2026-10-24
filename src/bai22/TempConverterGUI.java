package bai22;

import javax.swing.*;
import java.awt.*;

public class TempConverterGUI extends JFrame {
    public TempConverterGUI() {
        setTitle("Chuyển đổi nhiệt độ");
        setSize(300, 130);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER,10,15));

        JTextField txtCelsius = new JTextField(8);
        JButton btnConvert = new JButton("Chuyển °C °F");
       JLabel lblResult=new JLabel("kết quả: -- °F");
        lblResult.setFont(new Font("SansSerif", Font.BOLD, 13));

        // Thêm các thành phần vào giao diện
        add(txtCelsius);
        add(btnConvert);
        add(lblResult);
        
        // Xử lý sự kiện
        btnConvert.addActionListener(e -> {
            try {
                double c = Double.parseDouble(txtCelsius.getText().trim());
                double f = c * 1.8 + 32;
                lblResult.setText(String.format("Kết quả: %.1f °F", f));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng nhập một số hợp lệ!", 
                    "Lỗi định dạng", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TempConverterGUI().setVisible(true));
    new TempConverterGUI().setVisible(true);
    }
}
