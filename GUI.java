import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.Stack;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class GUI implements ActionListener {

    /*
     * JFrame works like the main Windoow where components like lables, buttons
     * ,textfields are added to create a GUI
     */
    /* Text Area */
    JFrame window;
    JTextArea textArea; // Multi-line area that displays plain text
    JScrollPane scrollPane; // Add Scrollbar when we enter beyound text area
    boolean wordWrapOn = false;

    /* Top Menu Bar */
    JMenuBar menuBar; // Add Menu bar of Notepad(File, Edit , Format , Colour etc)
    JMenu menuFile, menuEdit, menuFormat, menuColour; // Menu icons

    /* File Menu */
    JMenuItem iNew, iNewWindow, iOpen, iSave, iSaveAs, iExit; // Menu Items

    /* Format Menu */
    JMenuItem iWrap, iFontArial, iFontCSMS, iFontTNR, iFontSize8, iFontSize12, iFontSize16, iFontSize20, iFontSize24,
            iFontSize28;
    JMenu menuFont, menuFontSize;

    /* Edit Menu */
    JMenuItem iCopy, iCopy1, iCut1, iCut, iPaste1, iPaste, iDelete, iTD, iAllSel, iUndo, iRedo;
    Stack<String> stack = new Stack<>();
    String str = "";
    int i = 0, pos = 0;
    GregorianCalendar gcalendar;

    File fun = new File(this);
    Format format = new Format(this);
    Edit edit = new Edit(this);
    UndoManager manager = new UndoManager();

    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        createWindow();
        createTextArea();
        createMenuBar();
        createFileMenu();
        createEdit();
        createFormatMenu();
        // createColourMenu();

        format.selectedFont = "Arial";
        format.createFont(16);
        format.wordWrap();
        // col.changeColour("White");
        window.setVisible(true); // We can see window
    }

    public void createWindow() {
        window = new JFrame("Notepad");
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // we need to close frame properly
        ImageIcon icon = new ImageIcon("symbol.jpg");
        window.setIconImage(icon.getImage());
    }

    public void createTextArea() {
        textArea = new JTextArea();
        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                manager.addEdit(e.getEdit());
            }
        });
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove borders of Scroll Bar
        window.add(scrollPane);

    }

    public void createMenuBar() {
        menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

        menuFile = new JMenu("File");
        menuBar.add(menuFile);

        menuEdit = new JMenu("Edit");
        menuBar.add(menuEdit);

        menuFormat = new JMenu("Format");
        menuBar.add(menuFormat);

        // menuColour = new JMenu("Background Colour");
        // menuBar.add(menuColour);
    }

    public void createFileMenu() {
        iNew = new JMenuItem("New");
        iNew.addActionListener(this); // we're implementing Action Listner to this GUI class
        iNew.setActionCommand("New"); // set string value to trigger the ActionListener on this item
        menuFile.add(iNew);

        iNewWindow = new JMenuItem("New Window");
        iNewWindow.addActionListener(this);
        iNewWindow.setActionCommand("New");
        menuFile.add(iNewWindow);

        iOpen = new JMenuItem("Open");
        iOpen.addActionListener(this);
        iOpen.setActionCommand("Open");
        menuFile.add(iOpen);

        iSave = new JMenuItem("Save");
        iSave.addActionListener(this);
        iSave.setActionCommand("Save");
        menuFile.add(iSave);

        iSaveAs = new JMenuItem("Save As");
        iSaveAs.addActionListener(this);
        iSaveAs.setActionCommand("SaveAs");
        menuFile.add(iSaveAs);

        iExit = new JMenuItem("Exit");
        iExit.addActionListener(this);
        iExit.setActionCommand("Exit");
        menuFile.add(iExit);
    }

    public void createEdit() {

        iUndo = new JMenuItem("Undo");
        iUndo.addActionListener(this);
        iUndo.setActionCommand("Undo");
        menuEdit.add(iUndo);

        iRedo = new JMenuItem("Redo");
        iRedo.addActionListener(this);
        iRedo.setActionCommand("Redo");
        menuEdit.add(iRedo);

        iCopy1 = new JMenuItem("Copy (Ctrl + C)");
        iCopy1.addActionListener(this);
        iCopy1.setActionCommand("Copy (Ctrl + C)");
        menuEdit.add(iCopy1);

        iCut1 = new JMenuItem("Cut (Ctrl + X)");
        iCut1.addActionListener(this);
        iCut1.setActionCommand("Cut (Ctrl + X)");
        menuEdit.add(iCut1);

        iPaste1 = new JMenuItem("Paste (Ctrl + V)");
        iPaste1.addActionListener(this);
        iPaste1.setActionCommand("Paste (Ctrl + V)");
        menuEdit.add(iPaste1);

        iTD = new JMenuItem("Time & Date");
        iTD.addActionListener(this);
        iTD.setActionCommand("Time & Date");
        menuEdit.add(iTD);
    }

    public void createFormatMenu() {

        iWrap = new JMenuItem("Word Wrap : OFF");
        iWrap.addActionListener(this);
        iWrap.setActionCommand("Word Wrap");
        menuFormat.add(iWrap);

        menuFont = new JMenu("Font");
        menuFormat.add(menuFont);

        iFontArial = new JMenuItem("Arial");
        iFontArial.addActionListener(this);
        iFontArial.setActionCommand("Arial");
        menuFont.add(iFontArial);

        iFontCSMS = new JMenuItem("Comic Sans MS");
        iFontCSMS.addActionListener(this);
        iFontCSMS.setActionCommand("Comic Sans MS");
        menuFont.add(iFontCSMS);

        iFontTNR = new JMenuItem("Times New Roman");
        iFontTNR.addActionListener(this);
        iFontTNR.setActionCommand("Times New Roman");
        menuFont.add(iFontTNR);

        menuFontSize = new JMenu("Font Size");
        menuFormat.add(menuFontSize);

        iFontSize8 = new JMenuItem("8");
        iFontSize8.addActionListener(this);
        iFontSize8.setActionCommand("size8");
        menuFontSize.add(iFontSize8);

        iFontSize12 = new JMenuItem("12");
        iFontSize12.addActionListener(this);
        iFontSize12.setActionCommand("size12");
        menuFontSize.add(iFontSize12);

        iFontSize16 = new JMenuItem("16");
        iFontSize16.addActionListener(this);
        iFontSize16.setActionCommand("size16");
        menuFontSize.add(iFontSize16);

        iFontSize20 = new JMenuItem("20");
        iFontSize20.addActionListener(this);
        iFontSize20.setActionCommand("size20");
        menuFontSize.add(iFontSize20);

        iFontSize24 = new JMenuItem("24");
        iFontSize24.addActionListener(this);
        iFontSize24.setActionCommand("size24");
        menuFontSize.add(iFontSize24);

        iFontSize28 = new JMenuItem("28");
        iFontSize28.addActionListener(this);
        iFontSize28.setActionCommand("size28");
        menuFontSize.add(iFontSize28);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (e.getSource() == iNewWindow) {
            // window.dispose();
            fun.newWindow();
        }

        switch (command) {
            case "New":
                fun.newFile();
                break;
            case "Open":
                fun.open();
                break;
            case "SaveAs":
                fun.saveAs();
                break;
            case "Save":
                fun.save();
                break;
            case "Exit":
                fun.exit();
                break;
            case "Word Wrap":
                format.wordWrap();
                break;
            case "Arial":
                format.setFont(command);
                break;
            case "Comic Sans MS":
                format.setFont(command);
                break;
            case "Times New Roman":
                format.setFont(command);
                break;
            case "size8":
                format.createFont(8);
                break;
            case "size12":
                format.createFont(12);
                break;
            case "size16":
                format.createFont(16);
                break;
            case "size20":
                format.createFont(20);
                break;
            case "size24":
                format.createFont(24);
                break;
            case "size28":
                format.createFont(28);
                break;
            case "Undo":
                edit.editText(command);
                break;
            case "Redo":
                edit.editText(command);
                break;
            case "Copy (Ctrl + C)":
                edit.editText(command);
                break;
            case "Cut (Ctrl + X)":
                edit.editText(command);
                break;
            case "Paste (Ctrl + V)":
                edit.editText(command);
                break;
            case "Time & Date":
                edit.editText(command);
                break;
        }
    }
}
