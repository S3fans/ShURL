import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Contact_Book extends JFrame {
    private final JTable table;
    private final DefaultTableModel tModel;
    private final List<List<String>> contactPhoneNumbers;

    public Contact_Book() {
        super("Address Book");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(400, 300));

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton editButton = new JButton("Edit");

        tModel = new DefaultTableModel();
        tModel.addColumn("Name");
        tModel.addColumn("Contact datas");
        table = new JTable(tModel);
        contactPhoneNumbers = new ArrayList<>();

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        addButton.addActionListener(e -> {
            String newContactName = JOptionPane.showInputDialog(this, "Enter the name:");
            if (newContactName != null && !newContactName.isEmpty()) {
                List<String> phoneNumbers = new ArrayList<>();
                boolean continueAddingNumbers = true;
                while (continueAddingNumbers) {
                    String newPhoneNumber = JOptionPane.showInputDialog(this, "Enter contact datas " + newContactName + ":");
                    if (newPhoneNumber != null && !newPhoneNumber.isEmpty()) {
                        phoneNumbers.add(newPhoneNumber);
                    } else {
                        continueAddingNumbers = false;
                    }
                }
                contactPhoneNumbers.add(phoneNumbers);

                Vector<String> row = new Vector<>();
                row.add(newContactName);
                row.add(String.join(", ", phoneNumbers));
                tModel.addRow(row);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tModel.removeRow(selectedRow);
                contactPhoneNumbers.remove(selectedRow);
            }
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String currentName = (String) tModel.getValueAt(selectedRow, 0);
                List<String> currentPhoneNumbers = contactPhoneNumbers.get(selectedRow);

                String editedName = JOptionPane.showInputDialog(this, "Change name:", currentName);
                if (editedName != null && !editedName.isEmpty()) {
                    List<String> editedPhoneNumbers = new ArrayList<>(currentPhoneNumbers);

                    boolean continueEditing = true;
                    while (continueEditing) {
                        StringBuilder message = new StringBuilder("Contact datas:\n");
                        for (int i = 0; i < editedPhoneNumbers.size(); i++) {
                            message.append(i + 1).append(". ").append(editedPhoneNumbers.get(i)).append("\n");
                        }
                        String choice = JOptionPane.showInputDialog(this, message.toString() + "\n" + "Choose an option:" + "\n" + "(A)dd contact data" + "\n" + "(D)elete contact data" + "\n" + "(S)ave changes");
                        switch (choice.toUpperCase()) {
                            case "A" -> {
                                String addedPhoneNumber = JOptionPane.showInputDialog(this, "Enter contact data:");
                                if (addedPhoneNumber != null && !addedPhoneNumber.isEmpty()) {
                                    editedPhoneNumbers.add(addedPhoneNumber);
                                }
                            }
                            case "D" -> {
                                int indexForDeletion = Integer.parseInt(JOptionPane.showInputDialog(this, "Select contact data to delete:")) - 1;
                                if (indexForDeletion >= 0 && indexForDeletion < editedPhoneNumbers.size()) {
                                    editedPhoneNumbers.remove(indexForDeletion);
                                }
                            }
                            case "S" -> continueEditing = false;
                            default -> JOptionPane.showMessageDialog(this, "Invalid option. Please try again.");
                        }
                    }

                    if (!editedPhoneNumbers.isEmpty()) {
                        Vector<String> row = new Vector<>(2);
                        row.add(editedName);
                        row.add(String.join(", ", editedPhoneNumbers));
                        tModel.removeRow(selectedRow);
                        tModel.addRow(row);
                        contactPhoneNumbers.set(selectedRow, editedPhoneNumbers);
                    }
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Contact_Book::new);
    }
}