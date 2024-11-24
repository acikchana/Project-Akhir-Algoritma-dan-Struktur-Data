package PengaturJadwal;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TaskManagerGUI extends JFrame {
    private TaskList taskList;
    private JTextField judulField, deadlineField, estimasiField;
    private JComboBox<String> prioritasCombo;
    private JTable taskTable;
    private DefaultTableModel tableModel;
    
    public TaskManagerGUI() {
        taskList = new TaskList();
        
        // Setup frame
        setTitle("Task Manager");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main container dengan padding
        JPanel mainContainer = new JPanel(new BorderLayout(10, 10));
        mainContainer.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(mainContainer);
        
        // Panel input dengan styling
        JPanel inputPanel = createInputPanel();
        
        // Panel kontrol (sorting dan filtering)
        JPanel controlPanel = createControlPanel();
        
        // Panel tabel
        JPanel tablePanel = createTablePanel();
        
        // Layout utama
        mainContainer.add(inputPanel, BorderLayout.NORTH);
        mainContainer.add(controlPanel, BorderLayout.CENTER);
        mainContainer.add(tablePanel, BorderLayout.SOUTH);
    }
    
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Input Task"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Judul
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Judul:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.weightx = 1.0;
        judulField = new JTextField(20);
        inputPanel.add(judulField, gbc);
        
        // Deadline
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.0;
        inputPanel.add(new JLabel("Deadline (YYYY-MM-DD):"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.weightx = 1.0;
        deadlineField = new JTextField(20);
        inputPanel.add(deadlineField, gbc);
        
        // Prioritas
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0.0;
        inputPanel.add(new JLabel("Prioritas:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        String[] prioritasOptions = {"Rendah", "Sedang", "Tinggi"};
        prioritasCombo = new JComboBox<>(prioritasOptions);
        inputPanel.add(prioritasCombo, gbc);
        
        // Estimasi Waktu
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Estimasi Waktu (jam):"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 3;
        estimasiField = new JTextField(20);
        inputPanel.add(estimasiField, gbc);
        
        // Tombol Tambah
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton tambahButton = new JButton("Tambah Task");
        tambahButton.setPreferredSize(new Dimension(150, 30));
        inputPanel.add(tambahButton, gbc);
        
        // Event listener untuk tombol tambah
        tambahButton.addActionListener(e -> tambahTask());
        
        return inputPanel;
    }
    
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Pengaturan Sorting"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Panel untuk sorting berdasarkan Deadline
        JPanel deadlinePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton sortDeadlineAscButton = new JButton("↑ Deadline (Terdekat)");
        JButton sortDeadlineDescButton = new JButton("↓ Deadline (Terjauh)");
        deadlinePanel.add(sortDeadlineAscButton);
        deadlinePanel.add(sortDeadlineDescButton);
        
        gbc.gridx = 0; gbc.gridy = 0;
        controlPanel.add(deadlinePanel, gbc);
        
        // Panel untuk sorting berdasarkan Prioritas
        JPanel prioritasPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton sortPrioritasHighButton = new JButton("↑ Prioritas (Tinggi-Rendah)");
        JButton sortPrioritasLowButton = new JButton("↓ Prioritas (Rendah-Tinggi)");
        prioritasPanel.add(sortPrioritasHighButton);
        prioritasPanel.add(sortPrioritasLowButton);
        
        gbc.gridx = 0; gbc.gridy = 1;
        controlPanel.add(prioritasPanel, gbc);
        
        // Event listeners
        sortDeadlineAscButton.addActionListener(e -> {
            taskList.bubbleSortByDeadline(true);
            updateTable();
        });
        
        sortDeadlineDescButton.addActionListener(e -> {
            taskList.bubbleSortByDeadline(false);
            updateTable();
        });
        
        sortPrioritasHighButton.addActionListener(e -> {
            taskList.selectionSortByPrioritas(true);
            updateTable();
        });
        
        sortPrioritasLowButton.addActionListener(e -> {
            taskList.selectionSortByPrioritas(false);
            updateTable();
        });
        
        return controlPanel;
    }
    
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Daftar Task"));
        
        String[] columns = {"Judul", "Deadline", "Prioritas", "Estimasi Waktu"};
        tableModel = new DefaultTableModel(columns, 0);
        taskTable = new JTable(tableModel);
        
        // Styling tabel
        taskTable.setFillsViewportHeight(true);
        taskTable.setShowGrid(true);
        taskTable.setGridColor(Color.LIGHT_GRAY);
        
        // Scroll pane untuk tabel
        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    private void tambahTask() {
        try {
            String judul = judulField.getText();
            String deadline = deadlineField.getText();
            int prioritas = prioritasCombo.getSelectedIndex() + 1;
            int estimasi = Integer.parseInt(estimasiField.getText());
            
            if (judul.isEmpty() || deadline.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Semua field harus diisi!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Task newTask = new Task(judul, deadline, prioritas, estimasi);
            taskList.tambahTask(newTask);
            updateTable();
            
            // Clear fields
            judulField.setText("");
            deadlineField.setText("");
            prioritasCombo.setSelectedIndex(0);
            estimasiField.setText("");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Masukkan angka valid untuk estimasi waktu!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateTable() {
        tableModel.setRowCount(0);
        Task[] tasks = taskList.getTaskArray();
        for (Task task : tasks) {
            String prioritasStr = "";
            switch(task.getPrioritas()) {
                case 1: prioritasStr = "Rendah"; break;
                case 2: prioritasStr = "Sedang"; break;
                case 3: prioritasStr = "Tinggi"; break;
            }
            
            Object[] row = {
                task.getJudul(),
                task.getDeadline(),
                prioritasStr,
                task.getEstimasiWaktu() + " jam"
            };
            tableModel.addRow(row);
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            TaskManagerGUI gui = new TaskManagerGUI();
            gui.setVisible(true);
        });
    }
}