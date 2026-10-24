package bai24;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TodoListManagerGUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField txtTask;
    private JComboBox<String> cbPriority;
    private JButton btnAdd, btnComplete, btnDelete, btnStats;

    public TodoListManagerGUI() {
        // Thiết lập khung giao diện chính
        setTitle("Quản lý Công việc Cá nhân");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Căn giữa màn hình
        setLayout(new BorderLayout());

        // 1. Panel phía Bắc - Form nhập liệu
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        inputPanel.add(new JLabel("Tên công việc:"));
        txtTask = new JTextField(15);
        inputPanel.add(txtTask);

        inputPanel.add(new JLabel("Mức độ ưu tiên:"));
        cbPriority = new JComboBox<>(new String[]{"Cao", "Trung bình", "Thấp"});
        inputPanel.add(cbPriority);

        btnAdd = new JButton("Thêm công việc");
        inputPanel.add(btnAdd);

        add(inputPanel, BorderLayout.NORTH);

        // 2. Panel ở Giữa - JTable
        String[] columnNames = {"STT", "Tên công việc", "Độ ưu tiên", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 3. Panel phía Nam - Các nút chức năng
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnComplete = new JButton("Đánh dấu Hoàn thành");
        btnDelete = new JButton("Xóa công việc đã chọn");
        btnStats = new JButton("Thống kê");

        actionPanel.add(btnComplete);
        actionPanel.add(btnDelete);
        actionPanel.add(btnStats);
        add(actionPanel, BorderLayout.SOUTH);

        // --- XỬ LÝ SỰ KIỆN ---
        btnAdd.addActionListener(e -> addTask());
        btnComplete.addActionListener(e -> markCompleted());
        btnDelete.addActionListener(e -> deleteTask());
        btnStats.addActionListener(e -> showStatistics());
    }

    // Xử lý sự kiện thêm công việc mới
    private void addTask() {
        String taskName = txtTask.getText().trim();
        if (taskName.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Tên công việc không được để trống!", 
                "Lỗi nhập liệu", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String priority = (String) cbPriority.getSelectedItem();
        int stt = tableModel.getRowCount() + 1;

        // Thêm dòng mới vào bảng với trạng thái mặc định là 'Chưa hoàn thành'
        tableModel.addRow(new Object[]{stt, taskName, priority, "Chưa hoàn thành"});
        
        // Xóa trắng ô nhập liệu và focus lại
        txtTask.setText("");
        txtTask.requestFocus();
    }

    // Xử lý sự kiện 
    private void markCompleted() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn một công việc trên bảng để đánh dấu hoàn thành!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cập nhật giá trị cột Trạng thái 
        tableModel.setValueAt("Đã hoàn thành", selectedRow, 3);
    }

    // Xử lý sự kiện xóa công việc
    private void deleteTask() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn một công việc trên bảng để xóa!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Hộp thoại hỏi xác nhận trước khi xóa
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa công việc này không?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION);

            // Cập nhật lại số thứ tự (STT) sau khi xóa
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            updateRowNumbers(); 
        }
    }

    // Hàm phụ trợ cập nhật lại cột STT sau khi xóa dòng
    private void updateRowNumbers() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(i + 1, i, 0);
        }
    }

    // Xử lý sự kiện thống kê
    private void showStatistics() {
        int total = tableModel.getRowCount();
        int completed = 0;
        int uncompleted = 0;

        for (int i = 0; i < total; i++) {
            String status = (String) tableModel.getValueAt(i, 3);
            if ("Đã hoàn thành".equals(status)) {
                completed++;
            } else {
                uncompleted++;
            }
        }

        String statsMessage = String.format(
            " BẢNG THỐNG KÊ CÔNG VIỆC\n\n" +
            "• Tổng số công việc: %d\n" +
            "• Đã hoàn thành: %d\n" +
            "• Chưa hoàn thành: %d", 
            total, completed, uncompleted
        );

        JOptionPane.showMessageDialog(this, 
            statsMessage, 
            "Thống kê công việc", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        // Khởi chạy ứng dụng trên luồng Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new TodoListManagerGUI().setVisible(true);
        });
    }
}
