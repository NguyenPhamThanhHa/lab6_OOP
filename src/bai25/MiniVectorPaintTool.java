package bai25;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// Định nghĩa các loại hình vẽ
enum ShapeType {
    LINE, RECTANGLE, OVAL
}

// Lớp đại diện cho một đối tượng hình vẽ
class MyShape {
    ShapeType type;
    Point startPoint;
    Point endPoint;
    Color color;

    public MyShape(ShapeType type, Point startPoint, Point endPoint, Color color) {
        this.type = type;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.color = color;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(startPoint.x - endPoint.x);
        int height = Math.abs(startPoint.y - endPoint.y);

        switch (type) {
            case LINE:
                g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
                break;
            case RECTANGLE:
                g2d.drawRect(x, y, width, height);
                break;
            case OVAL:
                g2d.drawOval(x, y, width, height);
                break;
        }
    }
}

public class MiniVectorPaintTool extends JFrame {
    private List<MyShape> shapes = new ArrayList<>();
    private ShapeType currentShapeType = ShapeType.LINE;
    private Color currentColor = Color.BLACK;
    private Point startPoint = null;
    private Point currentPoint = null;
    private JLabel lblStatus;

    public MiniVectorPaintTool() {
        // Thiết lập khung ứng dụng chính
        setTitle("Mini Vector Paint Tool");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Căn giữa màn hình
        setLayout(new BorderLayout());

        // --- PHÍA BẮC (NORTH): Thanh công cụ ---
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        JRadioButton rbLine = new JRadioButton("Đường thẳng", true);
        JRadioButton rbRect = new JRadioButton("Hình chữ nhật");
        JRadioButton rbOval = new JRadioButton("Hình tròn");
        
        ButtonGroup group = new ButtonGroup();
        group.add(rbLine);
        group.add(rbRect);
        group.add(rbOval);

        rbLine.addActionListener(e -> currentShapeType = ShapeType.LINE);
        rbRect.addActionListener(e -> currentShapeType = ShapeType.RECTANGLE);
        rbOval.addActionListener(e -> currentShapeType = ShapeType.OVAL);

        JButton btnColor = new JButton("Chọn màu");
        btnColor.addActionListener(e -> {
            Color chosenColor = JColorChooser.showDialog(this, "Chọn màu vẽ", currentColor);
            if (chosenColor != null) {
                currentColor = chosenColor;
            }
        });

        JButton btnClear = new JButton("Xóa toàn bộ");
        btnClear.addActionListener(e -> {
            shapes.clear();
            repaint();
        });

        toolbar.add(new JLabel("Hình vẽ:"));
        toolbar.add(rbLine);
        toolbar.add(rbRect);
        toolbar.add(rbOval);
        toolbar.add(btnColor);
        toolbar.add(btnClear);
        add(toolbar, BorderLayout.NORTH);

        // --- TRUNG TÂM (CENTER): Vùng vẽ Canvas tùy chỉnh ---
        PaintPanel canvas = new PaintPanel();
        add(canvas, BorderLayout.CENTER);

        // --- PHÍA DƯỚI (SOUTH): Thanh trạng thái hiển thị tọa độ chuột ---
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblStatus = new JLabel("Tọa độ chuột: X: 0, Y: 0");
        statusPanel.add(lblStatus);
        add(statusPanel, BorderLayout.SOUTH);
    }

    // Lớp nội bộ quản lý vùng vẽ và sự kiện chuột
    class PaintPanel extends JPanel {
        public PaintPanel() {
            setBackground(Color.WHITE);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    startPoint = e.getPoint();
                    currentPoint = startPoint;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (startPoint != null) {
                        Point endPoint = e.getPoint();
                        // Thêm hình vẽ hoàn chỉnh vào danh sách
                        shapes.add(new MyShape(currentShapeType, startPoint, endPoint, currentColor));
                        startPoint = null;
                        currentPoint = null;
                        repaint();
                    }
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    currentPoint = e.getPoint();
                    lblStatus.setText("Tọa độ chuột: X: " + currentPoint.x + ", Y: " + currentPoint.y);
                    repaint(); // Kích hoạt vẽ lại để tạo hiệu ứng xem trước (rubber-banding)
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    lblStatus.setText("Tọa độ chuột: X: " + e.getX() + ", Y: " + e.getY());
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            // Bật khử răng cưa giúp đường nét mượt mà
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(2)); // Độ dày nét vẽ

            // 1. Vẽ lại tất cả các hình đã được lưu cố định trước đó
            for (MyShape shape : shapes) {
                shape.draw(g2d);
            }

            // 2. Hiệu ứng xem trước (Rubber-banding) khi đang nhấn và kéo chuột
            if (startPoint != null && currentPoint != null) {
                g2d.setColor(currentColor);
                int x = Math.min(startPoint.x, currentPoint.x);
                int y = Math.min(startPoint.y, currentPoint.y);
                int width = Math.abs(startPoint.x - currentPoint.x);
                int height = Math.abs(startPoint.y - currentPoint.y);

                switch (currentShapeType) {
                    case LINE:
                        g2d.drawLine(startPoint.x, startPoint.y, currentPoint.x, currentPoint.y);
                        break;
                    case RECTANGLE:
                        g2d.drawRect(x, y, width, height);
                        break;
                    case OVAL:
                        g2d.drawOval(x, y, width, height);
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MiniVectorPaintTool().setVisible(true);
        });
    }
}