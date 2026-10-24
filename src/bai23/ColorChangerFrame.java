package bai23;
import javax.swing.*;
import java.awt.*;

public class ColorChangerFrame extends JFrame {
    
    private Color circleColor = Color.RED;

    public ColorChangerFrame() {
        // Cấu hình cửa sổ chính 
        setTitle("Đổi màu Hình vẽ Tương tác");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- PHÍA BẮC (NORTH) ----
        JPanel topPanel = new JPanel();
        JLabel lblSelect = new JLabel("Chọn màu hình tròn:");
        String[] colorOptions = {"Đỏ", "Xanh lá", "Xanh dương"};
        JComboBox<String> cbColor = new JComboBox<>(colorOptions);
        
        topPanel.add(lblSelect);
        topPanel.add(cbColor);
        add(topPanel, BorderLayout.NORTH);

        // --- TRUNG TÂM (CENTER) ----
        JPanel drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Bật khử răng cưa 
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Thiết lập màu vẽ
                g2d.setColor(circleColor);
                
                // Tính toán tọa độ
                int diameter = 120;
                int x = (getWidth() - diameter) / 2;
                int y = (getHeight() - diameter) / 2;
                
            
                g2d.fillOval(x, y, diameter, diameter);
            }
        };
        add(drawPanel, BorderLayout.CENTER);

        // --- XỬ LÝ SỰ KIỆN JCOMBOBOX ---
        cbColor.addActionListener(e -> {
            String selected = (String) cbColor.getSelectedItem();
            if ("Đỏ".equals(selected)) 
                {
                circleColor = Color.RED;
            } 

            else if ("Xanh lá".equals(selected))
                 {
                circleColor = new Color(0, 153, 76);
            }

             else if ("Xanh dương".equals(selected)) 
                {
                circleColor = Color.BLUE;
            }
            
            drawPanel.repaint();
        });
    }

    public static void main(String[] args) {
    
        SwingUtilities.invokeLater(() ->
            new ColorChangerFrame().setVisible(true));
        };
    }
