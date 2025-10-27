{\rtf1\ansi\ansicpg1252\cocoartf2822
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 ArialMT;\f1\froman\fcharset0 Times-Roman;\f2\fswiss\fcharset0 Helvetica;
}
{\colortbl;\red255\green255\blue255;\red0\green0\blue0;\red26\green26\blue26;\red255\green255\blue255;
}
{\*\expandedcolortbl;;\cssrgb\c0\c0\c0;\cssrgb\c13333\c13333\c13333;\cssrgb\c100000\c100000\c100000;
}
\paperw11900\paperh16840\margl1440\margr1440\vieww28600\viewh15060\viewkind0
\deftab720
\pard\pardeftab720\partightenfactor0

\f0\fs20 \cf0 \expnd0\expndtw0\kerning0
\outl0\strokewidth0 \strokec2 package passwordmanager;\
\
import javax.swing.*;\
import java.awt.*;\
import java.awt.event.*;\
import java.io.*;\
import java.util.ArrayList;\
\
// ----- Interface for File Handling -----\
interface DataStore \{\
\'a0 \'a0 void savePasswords(ArrayList<PasswordEntry> list) throws IOException;\
\'a0 \'a0 ArrayList<PasswordEntry> loadPasswords() throws IOException, ClassNotFoundException;\
\}\
\
// ----- Base Class -----\
class Entry implements Serializable \{\
\'a0 \'a0 protected String website;\
\'a0 \'a0 protected String username;\
\
\'a0 \'a0 public Entry(String website, String username) \{\
\'a0 \'a0 \'a0 \'a0 this.website = website;\
\'a0 \'a0 \'a0 \'a0 this.username = username;\
\'a0 \'a0 \}\
\
\'a0 \'a0 public String getWebsite() \{ return website; \}\
\'a0 \'a0 public String getUsername() \{ return username; \}\
\}\
\
// ----- Derived Class -----\
class PasswordEntry extends Entry \{\
\'a0 \'a0 private String password;\
\
\'a0 \'a0 public PasswordEntry(String website, String username, String password) \{\
\'a0 \'a0 \'a0 \'a0 super(website, username);\
\'a0 \'a0 \'a0 \'a0 this.password = password;\
\'a0 \'a0 \}\
\
\'a0 \'a0 public String getPassword() \{ return password; \}\
\
\'a0 \'a0 @Override\
\'a0 \'a0 public String toString() \{\
\'a0 \'a0 \'a0 \'a0 return website + " | " + username + " | " + password;\
\'a0 \'a0 \}\
\}\
\
// ----- File Handling Implementation -----\
class FileDataStore implements DataStore \{\
\'a0 \'a0 private final File file = new File("passwords.dat");\
\
\'a0 \'a0 @Override\
\'a0 \'a0 public void savePasswords(ArrayList<PasswordEntry> list) throws IOException \{\
\'a0 \'a0 \'a0 \'a0 ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));\
\'a0 \'a0 \'a0 \'a0 out.writeObject(list);\
\'a0 \'a0 \'a0 \'a0 out.close();\
\'a0 \'a0 \}\
\
\'a0 \'a0 @Override\
\'a0 \'a0 public ArrayList<PasswordEntry> loadPasswords() throws IOException, ClassNotFoundException \{\
\'a0 \'a0 \'a0 \'a0 if (!file.exists()) return new ArrayList<>();\
\'a0 \'a0 \'a0 \'a0 ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));\
\'a0 \'a0 \'a0 \'a0 ArrayList<PasswordEntry> list = (ArrayList<PasswordEntry>) in.readObject();\
\'a0 \'a0 \'a0 \'a0 in.close();\
\'a0 \'a0 \'a0 \'a0 return list;\
\'a0 \'a0 \}\
\}\
\
// ----- Main GUI -----\
public class PasswordManagerApp extends JFrame \{\
\'a0 \'a0 private ArrayList<PasswordEntry> passwords = new ArrayList<>();\
\'a0 \'a0 private DataStore store = new FileDataStore();\
\
\'a0 \'a0 private DefaultListModel<PasswordEntry> model = new DefaultListModel<>();\
\'a0 \'a0 private JList<PasswordEntry> list = new JList<>(model);\
\
\'a0 \'a0 private JTextField tfWebsite = new JTextField();\
\'a0 \'a0 private JTextField tfUsername = new JTextField();\
\'a0 \'a0 private JTextField tfPassword = new JTextField();\
\
\'a0 \'a0 public PasswordManagerApp() \{\
\'a0 \'a0 \'a0 \'a0 setTitle("Password Manager");\
\'a0 \'a0 \'a0 \'a0 setSize(500, 400);\
\'a0 \'a0 \'a0 \'a0 setDefaultCloseOperation(EXIT_ON_CLOSE);\
\'a0 \'a0 \'a0 \'a0 setLayout(new BorderLayout());\
\
\'a0 \'a0 \'a0 \'a0 // Top Input Panel\
\'a0 \'a0 \'a0 \'a0 JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));\
\'a0 \'a0 \'a0 \'a0 panel.add(new JLabel("Website/App:"));\
\'a0 \'a0 \'a0 \'a0 panel.add(tfWebsite);\
\'a0 \'a0 \'a0 \'a0 panel.add(new JLabel("Username/Email:"));\
\'a0 \'a0 \'a0 \'a0 panel.add(tfUsername);\
\'a0 \'a0 \'a0 \'a0 panel.add(new JLabel("Password:"));\
\'a0 \'a0 \'a0 \'a0 panel.add(tfPassword);\
\
\'a0 \'a0 \'a0 \'a0 JButton btnAdd = new JButton("Add Password");\
\'a0 \'a0 \'a0 \'a0 JButton btnDelete = new JButton("Delete Selected");\
\'a0 \'a0 \'a0 \'a0 panel.add(btnAdd);\
\'a0 \'a0 \'a0 \'a0 panel.add(btnDelete);\
\
\'a0 \'a0 \'a0 \'a0 add(panel, BorderLayout.NORTH);\
\'a0 \'a0 \'a0 \'a0 add(new JScrollPane(list), BorderLayout.CENTER);\
\
\'a0 \'a0 \'a0 \'a0 // Bottom Buttons\
\'a0 \'a0 \'a0 \'a0 JPanel bottom = new JPanel();\
\'a0 \'a0 \'a0 \'a0 JButton btnSave = new JButton("Save");\
\'a0 \'a0 \'a0 \'a0 JButton btnLoad = new JButton("Load");\
\'a0 \'a0 \'a0 \'a0 bottom.add(btnSave);\
\'a0 \'a0 \'a0 \'a0 bottom.add(btnLoad);\
\'a0 \'a0 \'a0 \'a0 add(bottom, BorderLayout.SOUTH);\
\
\'a0 \'a0 \'a0 \'a0 // Event: Add Password\
\'a0 \'a0 \'a0 \'a0 btnAdd.addActionListener(e -> \{\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 try \{\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 String site = tfWebsite.getText().trim();\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 String user = tfUsername.getText().trim();\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 String pass = tfPassword.getText().trim();\
\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 if (site.isEmpty() || user.isEmpty() || pass.isEmpty()) \{\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 throw new Exception("All fields are required!");\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \}\
\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 PasswordEntry entry = new PasswordEntry(site, user, pass);\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 passwords.add(entry);\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 model.addElement(entry);\
\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 tfWebsite.setText("");\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 tfUsername.setText("");\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 tfPassword.setText("");\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \} catch (Exception ex) \{\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 JOptionPane.showMessageDialog(this, ex.getMessage());\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \}\
\'a0 \'a0 \'a0 \'a0 \});\
\
\'a0 \'a0 \'a0 \'a0 // Event: Delete\
\'a0 \'a0 \'a0 \'a0 btnDelete.addActionListener(e -> \{\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 int index = list.getSelectedIndex();\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 if (index >= 0) \{\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 passwords.remove(index);\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 model.remove(index);\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \} else \{\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 JOptionPane.showMessageDialog(this, "Select an entry to delete.");\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \}\
\'a0 \'a0 \'a0 \'a0 \});\
\
\'a0 \'a0 \'a0 \'a0 // Event: Save\
\'a0 \'a0 \'a0 \'a0 btnSave.addActionListener(e -> \{\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 try \{\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 store.savePasswords(passwords);\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 JOptionPane.showMessageDialog(this, "Passwords saved successfully!");\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \} catch (IOException ex) \{\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 JOptionPane.showMessageDialog(this, "Error saving: " + ex.getMessage());\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \}\
\'a0 \'a0 \'a0 \'a0 \});\
\
\'a0 \'a0 \'a0 \'a0 // Event: Load\
\'a0 \'a0 \'a0 \'a0 btnLoad.addActionListener(e -> \{\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 try \{\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 passwords = store.loadPasswords();\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 model.clear();\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 for (PasswordEntry p : passwords) model.addElement(p);\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 JOptionPane.showMessageDialog(this, "Data loaded successfully!");\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \} catch (Exception ex) \{\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 JOptionPane.showMessageDialog(this, "Error loading: " + ex.getMessage());\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \}\
\'a0 \'a0 \'a0 \'a0 \});\
\'a0 \'a0 \}\
\
\'a0 \'a0 // ----- Login Screen -----\
\'a0 \'a0 public static void main(String[] args) \{\
\'a0 \'a0 \'a0 \'a0 SwingUtilities.invokeLater(() -> \{\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 JPasswordField pf = new JPasswordField();\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 int option = JOptionPane.showConfirmDialog(null, pf, "Enter Master Password:", JOptionPane.OK_CANCEL_OPTION);\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 if (option == JOptionPane.OK_OPTION) \{\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 String pass = new String(pf.getPassword());\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 if (pass.equals("admin123")) \{ // simple login password\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 new PasswordManagerApp().setVisible(true);\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \} else \{\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 JOptionPane.showMessageDialog(null, "Wrong password!");\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \}\
\'a0 \'a0 \'a0 \'a0 \'a0 \'a0 \}\
\'a0 \'a0 \'a0 \'a0 \});\
\'a0 \'a0 \}\
\}\
\
\
\pard\pardeftab720\partightenfactor0

\f1\fs24 \cf0 \
\
\pard\pardeftab720\partightenfactor0

\f2\fs28 \cf3 \cb4 \strokec3 \
\
}