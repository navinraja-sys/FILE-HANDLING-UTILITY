import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextFileEditor extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JButton openButton, saveButton, saveAsButton, clearButton;
    private File currentFile = null;

    public TextFileEditor() {
        // Frame setup
        setTitle("Text File Editor");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Text area with scroll pane
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Buttons
        openButton = new JButton("Open");
        saveButton = new JButton("Save");
        saveAsButton = new JButton("Save As");
        clearButton = new JButton("Clear");

        openButton.addActionListener(this);
        saveButton.addActionListener(this);
        saveAsButton.addActionListener(this);
        clearButton.addActionListener(this);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(saveAsButton);
        buttonPanel.add(clearButton);

        // Layout
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Handle button events
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            openFile();
        } else if (e.getSource() == saveButton) {
            saveFile(false);  // Save to current file
        } else if (e.getSource() == saveAsButton) {
            saveFile(true);   // Save As
        } else if (e.getSource() == clearButton) {
            textArea.setText("");
        }
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                textArea.read(reader, null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage());
            }
        }
    }

    private void saveFile(boolean saveAs) {
        if (currentFile == null || saveAs) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                currentFile = fileChooser.getSelectedFile();
            } else {
                return; // User canceled
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
            textArea.write(writer);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TextFileEditor());
    }
}
