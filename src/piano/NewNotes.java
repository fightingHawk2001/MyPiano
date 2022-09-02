package piano;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

public class NewNotes extends JFrame implements ActionListener {
    private JFrame frame = new JFrame("编辑辅助器");

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu("文件");
    private JMenu menuEdit = new JMenu("编辑");
    private JMenu menuWindow = new JMenu("窗口");
    private JMenu menuHelp = new JMenu("帮助");
    private JMenuItem menuItemReadNotes = new JMenuItem("读取主奏");
    private JMenuItem menuItemReadAccompaniments = new JMenuItem("读取伴奏");
    private JMenuItem menuItemSave = new JMenuItem("保存");
    private JMenuItem menuItemShow = new JMenuItem("在资源管理器中打开");
    private JMenuItem menuItemUndo = new JMenuItem("撤销");
    private JMenuItem menuItemRedo = new JMenuItem("恢复");
    private JMenuItem menuItemZoomIn = new JMenuItem("放大");
    private JMenuItem menuItemZoomOut = new JMenuItem("缩小");
    private JMenuItem menuItemAssist = new JMenuItem("编辑辅助");
    private JMenuItem menuItemHelp = new JMenuItem("使用说明");
    private JTextArea jta1 = new JTextArea();
    private JTextArea jta2 = new JTextArea();
    private JTextField jtf1 = new JTextField();
    private JTextField jtf2 = new JTextField();
    private JButton buttonEnter = new JButton("确定");
    private JScrollPane jsp1 = new JScrollPane(jta1);
    private JScrollPane jsp2 = new JScrollPane(jta2);
    private JFileChooser chooser = new JFileChooser("notes");
    private FileNameExtensionFilter filter1 = new FileNameExtensionFilter(
            "*.notes", "notes");
    private FileNameExtensionFilter filter2 = new FileNameExtensionFilter(
            "*.accompaniments", "accompaniments");
    private UndoManager manager = new UndoManager();// 为文本添加撤销、恢复的功能所需的类
    private Mouse mouse = new Mouse();
    private TextListener textListener = new TextListener();

    // 辅助编辑界面按钮
    private JButton[] buttonhhs = {new JButton("1++"), new JButton("2++"), new JButton("3++"),
            new JButton("4++"), new JButton("5++"), new JButton("6++"), new JButton("7++")};
    private JButton[] buttonhs = {new JButton("1+"), new JButton("2+"), new JButton("3+"),
            new JButton("4+"), new JButton("5+"), new JButton("6+"), new JButton("7+")};
    private JButton[] buttonms = {new JButton("1"), new JButton("2"), new JButton("3"),
            new JButton("4"), new JButton("5"), new JButton("6"), new JButton("7")};
    private JButton[] buttonls = {new JButton("1-"), new JButton("2-"), new JButton("3-"),
            new JButton("4-"), new JButton("5-"), new JButton("6-"), new JButton("7-")};
    private JButton[] buttonlls = {new JButton("1--"), new JButton("2--"), new JButton("3--"),
            new JButton("4--"), new JButton("5--"), new JButton("6--"), new JButton("7--")};
    private JButton buttonNewline = new JButton("换行");
    private JButton buttonDelay = new JButton("0");
    private JButton buttonDelete = new JButton("删除");
    private JRadioButton rdBtn_notes = new JRadioButton("主奏音符");
    private JRadioButton rdBtn_accompaniments = new JRadioButton("伴奏音符");
    private ButtonGroup buttonGroup = new ButtonGroup();

    private String notes;
    private String accompaniments;
    private int times;
    private String isShowHelp;
    private boolean isSave = false;
    private boolean isRead = false;
    private String jta1_old = "";
    private String jta2_old = "";
    private int size;

    public String getNotes() {
        return this.notes;
    }

    public String getAccompaniments() {
        return this.accompaniments;
    }

    public int getTimes() {
        return this.times;
    }

    public boolean getIsSave() {
        return this.isSave;
    }

    public int getTextSize() {
        return this.size;
    }

    private void initTextArea() {
        String[] notes = {
                "0",
                "1--", "2--", "3--", "4--", "5--", "6--", "7--",
                "1-", "2-", "3-", "4-", "5-", "6-", "7-",
                "1", "2", "3", "4", "5", "6", "7",
                "1+", "2+", "3+", "4+", "5+", "6+", "7+",
                "1++", "2++", "3++", "4++", "5++", "6++", "7++",
        };
        String[] strings = jta1.getText().split(" ");
        int num = 0;
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < notes.length; j++) {
                if (strings[i].equals(notes[j])) {
                    num++;
                }
            }
        }
        jsp1.setBorder(new TitledBorder("主奏音符（" + num + "）"));
        strings = jta2.getText().split(" ");
        num = 0;
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < notes.length; j++) {
                if (strings[i].equals(notes[j])) {
                    num++;
                }
            }
        }
        jsp2.setBorder(new TitledBorder("主奏音符（" + num + "）"));

        // 添加鼠标点击事件
        jta1.addMouseListener(mouse);
        jta2.addMouseListener(mouse);
        jta1.getDocument().addUndoableEditListener(manager);// 添加文本侦听器
        jta1.getDocument().addDocumentListener(textListener);
        jta1.setFont(new Font(null, Font.BOLD, this.size));
        jta2.getDocument().addUndoableEditListener(manager);// 添加文本侦听器
        jta2.getDocument().addDocumentListener(textListener);
        jta2.setFont(new Font(null, Font.BOLD, this.size));

        jsp1.setPreferredSize(new Dimension(this.getWidth() - 25, 175));
        jsp2.setPreferredSize(new Dimension(this.getWidth() - 25, 175));

        jtf1.setBorder(new TitledBorder("音长(ms)"));
        jtf1.setPreferredSize(new Dimension(100, 50));
        jtf2.setBorder(new TitledBorder("文件名"));
        jtf2.setPreferredSize(new Dimension(100, 50));

        add(jsp1);
        add(jsp2);
        add(jtf1);
        add(jtf2);
        //添加按钮
        buttonEnter.setPreferredSize(new Dimension(100, 50));
        buttonEnter.addActionListener(this);
        buttonEnter.setFont(new Font("", 0, 20));
        add(buttonEnter);
    }

    private void initMenu() {
        setJMenuBar(menuBar);
        menuBar.add(menuFile);
        menuFile.add(menuItemReadNotes);
        menuFile.add(menuItemReadAccompaniments);
        menuFile.addSeparator();
        menuFile.add(menuItemSave);
        menuFile.addSeparator();
        menuFile.add(menuItemShow);
        menuItemReadNotes.addActionListener(this);
        menuItemReadNotes.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        menuItemReadAccompaniments.addActionListener(this);
        menuItemReadAccompaniments.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_DOWN_MASK));
        menuItemSave.addActionListener(this);
        menuItemSave.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        menuItemShow.addActionListener(this);
        menuItemShow.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));

        menuBar.add(menuEdit);
        menuEdit.add(menuItemUndo);
        menuEdit.add(menuItemRedo);
        menuEdit.addSeparator();
        menuEdit.add(menuItemZoomIn);
        menuEdit.add(menuItemZoomOut);
        menuItemUndo.addActionListener(this);
        menuItemUndo.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        menuItemRedo.addActionListener(this);
        menuItemRedo.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
        menuItemZoomIn.addActionListener(this);
        menuItemZoomIn.setAccelerator(
                KeyStroke.getKeyStroke(61, InputEvent.CTRL_DOWN_MASK));
        menuItemZoomOut.addActionListener(this);
        menuItemZoomOut.setAccelerator(
                KeyStroke.getKeyStroke(45, InputEvent.CTRL_DOWN_MASK));

        menuBar.add(menuWindow);
        menuWindow.add(menuItemAssist);
        menuItemAssist.addActionListener(this);

        menuBar.add(menuHelp);
        menuHelp.add(menuItemHelp);
        menuItemHelp.addActionListener(this);
    }

    public void initFrame() {
        setIconImage(new AddImage("/img/img.jpg").addImage());


        File file = new File("C:\\Users\\" + System.getenv("USERNAME") + "\\Documents", "MyPiano.cfg");
        if (file.exists())
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] byt = new byte[128];
                int len = fis.read(byt);
                String read = new String(byt, 0, len);
                fis.close();
                this.isShowHelp = read.substring(read
                        .indexOf("isShowHelp = ") + "isShowHelp = "
                        .length(), read
                        .indexOf(" [isShowHelp]"));
                if (!this.isRead) {
                    this.size = Integer.parseInt(read.substring(read
                            .indexOf("size = ") + "size = ".length(), read
                            .indexOf("\n", read.indexOf("size = ") + "size = "
                                    .length())));
                    this.isRead = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        file = new File("notes");
        if (!file.exists()) {
            file.mkdir();
        }

        setSize(700, 500);
        setLocation(
                (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - this.getWidth() / 2,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - this.getHeight() / 2);
        setTitle("新建自制简谱");
        setLayout(new FlowLayout());
        setResizable(false);
        addWindowListener(new Win());
        // setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        // 设置此窗口打开时原窗口不能进行操作

        initMenu();
        initTextArea();

        setVisible(true);
        if (isShowHelp.equals("1"))
            showHelpDialog();
    }

    // 显示使用说明窗口
    private void showHelpDialog() {
        JOptionPane.showMessageDialog(this,
                "                                                                                             使用自动弹奏功能前请仔细阅读此说明\n"
                        + "1、主奏与伴奏中支持输入的35个音符：\n"
                        + "    “1--” ~ “7--”， “1-” ~ “7-”， “1” ~ “7”， “1+” ~ “7+”， “1++” ~ “7++”\n"
                        + "    分别代表倍低音、低音、中音、高音、倍高音一共35个音符\n"
                        + "    （数字的后缀分别是减号（不是下划线）与加号）\n"
                        + "    （输入的时候没有引号，eg：1 5 4- 7-- 3++ 6+ 3--）\n"
                        + "    （由于键盘上并没有倍高音7的键位，所以在使用自动弹奏中有倍高音7时，钢琴对应键位会有变化，而键盘键位无变化）\n"
                        + "    （因为没有黑键的音源以及键盘上没有合适的黑键的键位，于是就取消了黑键的功能）\n"
                        + "2、分别在主奏与伴奏中输入需要自动弹奏的音符，每个音符之间用空格隔开（任意多个空格，每两个空格之间为一个音符）\n"
                        + "    （输入字符“0”，则会使音长额外延长一倍）\n"
                        + "    （输入除了上面35个音符以及“0”以外的任意字符不会对弹奏起任何作用）\n"
                        + "    （如果需要换行填写，则需在上一行的末尾以及下一行的开头都加上空格）\n"
                        + "3、如果觉得上述输入方式太过于繁琐，可以点击”窗口“菜单栏中的”编辑辅助“菜单项，在弹出的编辑辅助器中先选择左下角的”主奏”/“伴奏”，\n"
                        + "    然后就可以直接通过鼠标点击相应的按钮来录入对应的音符\n"
                        + "    （删除按钮一次性删除一个音符，而不是一个字符）\n"
                        + "4、音长里输入每两个音符之间的间隔时长，单位是毫秒（ms）\n"
                        + "    （建议输入整片谱子的最短的间隔，其余更长的的间隔可以利用延时加倍解决）\n"
                        + "5、如果想保存当前录入的谱子，就请输入合法的文件名，然后点击保存菜单项，等待出现保存成功的提示消息\n"
                        + "    （主奏会保存为[输入的文件名]+[音长]+“.notes”）\n"
                        + "    （伴奏会保存为[输入的文件名]+[音长]+“.accompaniments”）\n"
                        + "6、当成功保存了谱子之后，可以点击读取主奏/伴奏菜单项分别读取主奏与伴奏（或者双击文本域）\n"
                        + "7、当主奏、伴奏、音长都已经准备就绪时，就可以点击确认按钮返回到钢琴窗口中\n"
                        + "    然后点击“控制”菜单栏中的“播放自制简谱”便可自动弹奏\n"
                        + "    （主奏与伴奏可以只输入其一，并且在自动弹奏时也可以进行手动弹奏，利用这一点可以实现自动弹伴奏，手动弹主奏）\n"
                        + "注：此功能仅供娱乐，为了开放编写自动弹奏功能，于是自由度就不高，停顿缓急也许就不会很方便，快乐就完事了\n"
                        + "    有时也许会出现卡顿、主奏伴奏不同步等bug，目前我也没有办法解决，重新播放或者重启软件即可\n"
                        + "    （仅自动显示一次，后续可在帮助菜单栏中再次打开）",
                "自动弹奏使用说明", JOptionPane.INFORMATION_MESSAGE);
        File file = new File("C:\\Users\\" + System.getenv("USERNAME") + "\\Documents", "MyPiano.cfg");
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] byt = new byte[256];
                int len = fis.read(byt);
                String read = new String(byt, 0, len);
                fis.close();

                char[] ch = read.toCharArray();
                ch[read.indexOf("isShowHelp = ") + "isShowHelp = ".length()] = '0';
                String read_new = new String(ch);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(read_new.getBytes());
                fos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    // 显示辅助编辑窗口
    private void showAssistFrame() {
        frame.setSize(600, 330);
        frame.setIconImage(new AddImage("/img/img.jpg").addImage());
        frame.setLocation(
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - frame.getWidth() / 2),
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (frame.getHeight() / 2)));
        frame.setLayout(new FlowLayout());

        initAssistButton();

        frame.setResizable(false);
        frame.setFocusable(false);
        frame.setVisible(true);
        frame.addWindowListener(new Win());
    }

    private void initAssistButton() {
        for (JButton buttonhh : buttonhhs) {
            buttonhh.setFont(new Font("", 0, 20));
            buttonhh.setPreferredSize(new Dimension(75, 40));
            buttonhh.setFocusable(false);
            buttonhh.addActionListener(this);
            frame.add(buttonhh);
        }
        for (JButton buttonh : buttonhs) {
            buttonh.setFont(new Font("", 0, 20));
            buttonh.setPreferredSize(new Dimension(75, 40));
            buttonh.setFocusable(false);
            buttonh.addActionListener(this);
            frame.add(buttonh);
        }
        for (JButton buttonm : buttonms) {
            buttonm.setFont(new Font("", 0, 20));
            buttonm.setPreferredSize(new Dimension(75, 40));
            buttonm.setFocusable(false);
            buttonm.addActionListener(this);
            frame.add(buttonm);
        }
        for (JButton buttonl : buttonls) {
            buttonl.setFont(new Font("", 0, 20));
            buttonl.setPreferredSize(new Dimension(75, 40));
            buttonl.setFocusable(false);
            buttonl.addActionListener(this);
            frame.add(buttonl);
        }
        for (JButton buttonll : buttonlls) {
            buttonll.setFont(new Font("", 0, 20));
            buttonll.setPreferredSize(new Dimension(75, 40));
            buttonll.setFocusable(false);
            buttonll.addActionListener(this);
            frame.add(buttonll);
        }
        buttonGroup.add(rdBtn_notes);
        buttonGroup.add(rdBtn_accompaniments);

        rdBtn_notes.setFont(new Font("", 0, 20));
        rdBtn_notes.setPreferredSize(new Dimension(125, 40));
        rdBtn_notes.setFocusable(false);
        rdBtn_notes.setSelected(true);
        frame.add(rdBtn_notes);

        rdBtn_accompaniments.setFont(new Font("", 0, 20));
        rdBtn_accompaniments.setPreferredSize(new Dimension(185, 40));
        rdBtn_accompaniments.setFocusable(false);
        frame.add(rdBtn_accompaniments);

        buttonDelay.setFont(new Font("", 0, 20));
        buttonDelay.setPreferredSize(new Dimension(75, 40));
        buttonDelay.setFocusable(false);
        buttonDelay.addActionListener(this);
        frame.add(buttonDelay);

        buttonNewline.setFont(new Font("", 0, 20));
        buttonNewline.setPreferredSize(new Dimension(75, 40));
        buttonNewline.setFocusable(false);
        buttonNewline.addActionListener(this);
        frame.add(buttonNewline);

        buttonDelete.setFont(new Font("", 0, 20));
        buttonDelete.setPreferredSize(new Dimension(75, 40));
        buttonDelete.setFocusable(false);
        buttonDelete.addActionListener(this);
        frame.add(buttonDelete);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 保存简谱文件
        if (e.getSource() == menuItemSave) {
            if ((!jtf2.getText().equals("")) && ((!jta1.getText().equals(""))
                    || (!jta2.getText().equals("")))) {
                File fileNotes = new File("notes/" + jtf2.getText() + "_" + jtf1.getText() + ".notes");
                File fileAccompaniments = new File(
                        "notes/" + jtf2.getText() + "_" + jtf1.getText() + ".accompaniments");
                try {
                    if ((!fileNotes.exists())
                            && (!fileAccompaniments.exists())) {
                        fileNotes.createNewFile();
                        fileAccompaniments.createNewFile();
                        FileOutputStream fos = new FileOutputStream(fileNotes);
                        fos.write(jta1.getText().getBytes());
                        fos.close();
                        fos = new FileOutputStream(fileAccompaniments);
                        fos.write(jta2.getText().getBytes());
                        fos.close();
                        JOptionPane.showMessageDialog(this, "文件保存成功");
                        jta1_old = jta1.getText();
                        jta2_old = jta2.getText();
                    } else if (fileNotes.exists()
                            && fileAccompaniments.exists()) {
                        int value = JOptionPane.showConfirmDialog(this,
                                "此文件名已存在，是否替换", "警告",
                                JOptionPane.WARNING_MESSAGE,
                                JOptionPane.WARNING_MESSAGE);
                        if (value == 0) {
                            FileOutputStream fos = new FileOutputStream(fileNotes);
                            fos.write(jta1.getText().getBytes());
                            fos.close();
                            fos = new FileOutputStream(fileAccompaniments);
                            fos.write(jta2.getText().getBytes());
                            fos.close();
                            JOptionPane.showMessageDialog(this, "文件保存成功");
                            jta1_old = jta1.getText();
                            jta2_old = jta2.getText();
                        } else if (value == 1) {
                            setState(NORMAL);
                        }
                    }
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(this, "找不到“notes”文件夹或者文件格式不对",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (jtf2.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "请输入文件名", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if (jta1.getText().equals("") && jta2.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "请至少输入主奏与伴奏中的一个", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == menuItemReadNotes) {// 读取主奏
            chooser.setFileFilter(filter1);
            chooser.setDialogTitle("读取主奏");
            chooser.setSelectedFile(new File("*.notes"));
            int value = chooser.showOpenDialog(this);
            if (value == JFileChooser.APPROVE_OPTION) {
                String name = chooser.getSelectedFile().getName();
                String times = name.substring(name.indexOf("_") + 1, name.indexOf("."));
                String nameOnly = name.substring(0, name.indexOf("_"));
                File fileNotes = chooser.getSelectedFile();
                try {
                    FileInputStream fis = new FileInputStream(fileNotes);
                    byte[] byt = new byte[4096];
                    int len = fis.read(byt);
                    String notes = new String(byt, 0, len);
                    jta1.setText(notes);
                    jtf1.setText(times);
                    jtf2.setText(nameOnly);
                    fis.close();
                    jta1_old = jta1.getText();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        } else if (e.getSource() == menuItemReadAccompaniments) {// 读取伴奏
            chooser.setFileFilter(filter2);
            chooser.setDialogTitle("读取伴奏");
            chooser.setSelectedFile(new File("*.accompaniments"));
            int value = chooser.showOpenDialog(this);
            if (value == JFileChooser.APPROVE_OPTION) {
                String name = chooser.getSelectedFile().getName();
                String times = name.substring(name.indexOf("_") + 1, name.indexOf("."));
                String nameOnly = name.substring(0, name.indexOf("_"));
                File fileAccompaniments = chooser.getSelectedFile();
                try {
                    FileInputStream fis = new FileInputStream(fileAccompaniments);
                    byte[] byt = new byte[4096];
                    int len = fis.read(byt);
                    String accompaniments = new String(byt, 0, len);
                    jta2.setText(accompaniments);
                    jtf1.setText(times);
                    jtf2.setText(nameOnly);
                    fis.close();
                    jta2_old = jta2.getText();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        } else if (e.getSource() == menuItemShow) {
            try {
                Desktop.getDesktop().open(new File("notes"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == menuItemUndo && manager.canUndo()) {// 撤销
            manager.undo();
        } else if (e.getSource() == menuItemRedo && manager.canRedo()) {// 恢复
            manager.redo();
        } else if (e.getSource() == menuItemZoomIn) {// 放大
            this.size += 2;
            if (this.size >= 40)
                this.size = 40;
            jta1.setFont(new Font(null, Font.BOLD, this.size));
            jta2.setFont(new Font(null, Font.BOLD, this.size));
        } else if (e.getSource() == menuItemZoomOut) {// 缩小
            this.size -= 2;
            if (this.size <= 12)
                this.size = 12;
            jta1.setFont(new Font(null, Font.BOLD, this.size));
            jta2.setFont(new Font(null, Font.BOLD, this.size));
        } else if (e.getSource() == menuItemHelp) {
            showHelpDialog();
        } else if (e.getSource() == menuItemAssist) {
            if (frame.isShowing())
                frame.setState(JFrame.NORMAL);
            else if (!frame.isShowing())
                showAssistFrame();
        } else if (e.getSource() == buttonEnter) {
            String notes = jta1.getText();
            String accompaniments = jta2.getText();
            try {
                this.notes = notes;
                this.accompaniments = accompaniments;
                int times = Integer.parseInt(jtf1.getText());
                this.times = times;
                buttonEnter.removeActionListener(this);
                menuItemReadNotes.removeActionListener(this);
                menuItemReadAccompaniments.removeActionListener(this);
                menuItemSave.removeActionListener(this);
                menuItemUndo.removeActionListener(this);
                menuItemRedo.removeActionListener(this);
                menuItemHelp.removeActionListener(this);
                jta1.getDocument().removeUndoableEditListener(manager);
                jta2.getDocument().removeUndoableEditListener(manager);
                jta1.removeMouseListener(mouse);
                jta2.removeMouseListener(mouse);
                jta1.getDocument().removeDocumentListener(textListener);
                jta2.getDocument().removeDocumentListener(textListener);
                menuBar.removeAll();
                menuFile.removeAll();
                menuEdit.removeAll();
                menuHelp.removeAll();
                setVisible(false);

                frame.dispose();
                for (JButton buttonhh : buttonhhs) {
                    buttonhh.removeActionListener(NewNotes.this);
                }
                for (JButton buttonh : buttonhs) {
                    buttonh.removeActionListener(NewNotes.this);
                }
                for (JButton buttonm : buttonms) {
                    buttonm.removeActionListener(NewNotes.this);
                }
                for (JButton buttonl : buttonls) {
                    buttonl.removeActionListener(NewNotes.this);
                }
                for (JButton buttonll : buttonlls) {
                    buttonll.removeActionListener(NewNotes.this);
                }
                buttonDelay.removeActionListener(NewNotes.this);
                buttonNewline.removeActionListener(NewNotes.this);
                buttonDelete.removeActionListener(NewNotes.this);
                if ((!jta1_old.equals(jta1.getText()))
                        || (!jta2_old.equals(jta2.getText()))) {
                    this.isSave = true;
                } else {
                    this.isSave = false;
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, "请输入音长或者音长格式不对（只能输入数字）",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        // 通过主奏与伴奏音符单选按钮来判断点击的按钮输入到哪一个文本域中
        if (rdBtn_notes.isSelected()) {
            for (JButton buttonhh : buttonhhs) {
                if (e.getSource() == buttonhh) {
                    jta1.setText(jta1.getText() + " " + buttonhh.getText());
                }
            }
            for (JButton buttonh : buttonhs) {
                if (e.getSource() == buttonh) {
                    jta1.setText(jta1.getText() + " " + buttonh.getText());
                }
            }
            for (JButton buttonm : buttonms) {
                if (e.getSource() == buttonm) {
                    jta1.setText(jta1.getText() + " " + buttonm.getText());
                }
            }
            for (JButton buttonl : buttonls) {
                if (e.getSource() == buttonl) {
                    jta1.setText(jta1.getText() + " " + buttonl.getText());
                }
            }
            for (JButton buttonll : buttonlls) {
                if (e.getSource() == buttonll) {
                    jta1.setText(jta1.getText() + " " + buttonll.getText());
                }
            }
            if (e.getSource() == buttonDelay) {
                jta1.setText(jta1.getText() + " 0");
            } else if (e.getSource() == buttonNewline) {
                jta1.setText(jta1.getText() + " \n");
            } else if (e.getSource() == buttonDelete) {
                jta1.setText(jta1.getText().substring(0, jta1.getText().lastIndexOf(" ")));
            }
            jta1.requestFocus();
        }
        // 伴奏
        else if (rdBtn_accompaniments.isSelected()) {
            for (JButton buttonhh : buttonhhs) {
                if (e.getSource() == buttonhh) {
                    jta2.setText(jta2.getText() + " " + buttonhh.getText());
                }
            }
            for (JButton buttonh : buttonhs) {
                if (e.getSource() == buttonh) {
                    jta2.setText(jta2.getText() + " " + buttonh.getText());
                }
            }
            for (JButton buttonm : buttonms) {
                if (e.getSource() == buttonm) {
                    jta2.setText(jta2.getText() + " " + buttonm.getText());
                }
            }
            for (JButton buttonl : buttonls) {
                if (e.getSource() == buttonl) {
                    jta2.setText(jta2.getText() + " " + buttonl.getText());
                }
            }
            for (JButton buttonll : buttonlls) {
                if (e.getSource() == buttonll) {
                    jta2.setText(jta2.getText() + " " + buttonll.getText());
                }
            }
            if (e.getSource() == buttonDelay) {
                jta2.setText(jta2.getText() + " 0");
            } else if (e.getSource() == buttonNewline) {
                jta2.setText(jta2.getText() + " \n");
            } else if (e.getSource() == buttonDelete) {
                jta2.setText(jta2.getText().substring(0, jta2.getText().lastIndexOf(" ")));
            }
            jta2.requestFocus();
        }
    }

    class Mouse extends MouseAdapter {
        // 鼠标双击事件
        @Override
        public void mouseClicked(MouseEvent e) {
            // 双击打开主奏文件
            if (e.getClickCount() == 2 && e.getSource().equals(jta1)) {
                chooser.setFileFilter(filter1);
                chooser.setDialogTitle("读取主奏");
                chooser.setSelectedFile(new File("*.notes"));
                int value = chooser.showOpenDialog(NewNotes.this);
                if (value == JFileChooser.APPROVE_OPTION) {
                    String name = chooser.getSelectedFile().getName();
                    String times = name.substring(name.indexOf("_") + 1, name.indexOf("."));
                    String nameOnly = name.substring(0, name.indexOf("_"));
                    File fileNotes = chooser.getSelectedFile();
                    try {
                        FileInputStream fis = new FileInputStream(fileNotes);
                        byte[] byt = new byte[4096];
                        int len = fis.read(byt);
                        String notes = new String(byt, 0, len);
                        jta1.setText(notes);
                        jtf1.setText(times);
                        jtf2.setText(nameOnly);
                        fis.close();
                        jta1_old = jta1.getText();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
            // 双击打开伴奏文件
            if (e.getClickCount() == 2 && e.getSource().equals(jta2)) {
                chooser.setFileFilter(filter2);
                chooser.setDialogTitle("读取伴奏");
                chooser.setSelectedFile(new File("*.accompaniments"));
                int value = chooser.showOpenDialog(NewNotes.this);
                if (value == JFileChooser.APPROVE_OPTION) {
                    String name = chooser.getSelectedFile().getName();
                    String times = name.substring(name.indexOf("_") + 1, name.indexOf("."));
                    String nameOnly = name.substring(0, name.indexOf("_"));
                    File fileAccompaniments = chooser.getSelectedFile();
                    try {
                        FileInputStream fis = new FileInputStream(fileAccompaniments);
                        byte[] byt = new byte[4096];
                        int len = fis.read(byt);
                        String accompaniments = new String(byt, 0, len);
                        jta2.setText(accompaniments);
                        jtf1.setText(times);
                        jtf2.setText(nameOnly);
                        fis.close();
                        jta2_old = jta2.getText();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    class Win extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {// 关闭窗口监听事件
            if (e.getSource().equals(NewNotes.this)) {
                // 移除监听器，避免再次打开此窗口时重复添加监听器
                if ((!jta1_old.equals(jta1.getText()))
                        || (!jta2_old.equals(jta2.getText()))) {
                    NewNotes.this.isSave = true;
                } else {
                    NewNotes.this.isSave = false;
                }
                buttonEnter.removeActionListener(NewNotes.this);
                menuItemReadNotes.removeActionListener(NewNotes.this);
                menuItemReadAccompaniments.removeActionListener(NewNotes.this);
                menuItemSave.removeActionListener(NewNotes.this);
                menuItemUndo.removeActionListener(NewNotes.this);
                menuItemRedo.removeActionListener(NewNotes.this);
                menuItemHelp.removeActionListener(NewNotes.this);
                jta1.getDocument().removeUndoableEditListener(manager);
                jta2.getDocument().removeUndoableEditListener(manager);
                jta1.removeMouseListener(mouse);
                jta2.removeMouseListener(mouse);
                jta1.getDocument().removeDocumentListener(textListener);
                jta2.getDocument().removeDocumentListener(textListener);
                menuBar.removeAll();
                menuFile.removeAll();
                menuEdit.removeAll();
                menuWindow.removeAll();
                menuHelp.removeAll();
                NewNotes.this.removeWindowListener(this);
                frame.dispose();

                for (JButton buttonhh : buttonhhs) {
                    buttonhh.removeActionListener(NewNotes.this);
                }
                for (JButton buttonh : buttonhs) {
                    buttonh.removeActionListener(NewNotes.this);
                }
                for (JButton buttonm : buttonms) {
                    buttonm.removeActionListener(NewNotes.this);
                }
                for (JButton buttonl : buttonls) {
                    buttonl.removeActionListener(NewNotes.this);
                }
                for (JButton buttonll : buttonlls) {
                    buttonll.removeActionListener(NewNotes.this);
                }
                buttonDelay.removeActionListener(NewNotes.this);
                buttonNewline.removeActionListener(NewNotes.this);
                buttonDelete.removeActionListener(NewNotes.this);
                frame.removeWindowListener(this);
            } else if (e.getSource().equals(frame)) {
                for (JButton buttonhh : buttonhhs) {
                    buttonhh.removeActionListener(NewNotes.this);
                }
                for (JButton buttonh : buttonhs) {
                    buttonh.removeActionListener(NewNotes.this);
                }
                for (JButton buttonm : buttonms) {
                    buttonm.removeActionListener(NewNotes.this);
                }
                for (JButton buttonl : buttonls) {
                    buttonl.removeActionListener(NewNotes.this);
                }
                for (JButton buttonll : buttonlls) {
                    buttonll.removeActionListener(NewNotes.this);
                }
                buttonDelay.removeActionListener(NewNotes.this);
                buttonNewline.removeActionListener(NewNotes.this);
                buttonDelete.removeActionListener(NewNotes.this);
                frame.removeWindowListener(this);
            }
        }

    }

    private class TextListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            changedUpdate(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            changedUpdate(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            String[] notes = {
                    "0",
                    "1--", "2--", "3--", "4--", "5--", "6--", "7--",
                    "1-", "2-", "3-", "4-", "5-", "6-", "7-",
                    "1", "2", "3", "4", "5", "6", "7",
                    "1+", "2+", "3+", "4+", "5+", "6+", "7+",
                    "1++", "2++", "3++", "4++", "5++", "6++", "7++",
            };
            if (e.getDocument().equals(jta1.getDocument())) {
                String[] strings = jta1.getText().split(" ");
                int num = 0;
                for (int i = 0; i < strings.length; i++) {
                    for (int j = 0; j < notes.length; j++) {
                        if (strings[i].equals(notes[j])) {
                            num++;
                        }
                    }
                }
                jsp1.setBorder(new TitledBorder("主奏音符（" + num + "）"));
            } else if (e.getDocument().equals(jta2.getDocument())) {
                String[] strings = jta2.getText().split(" ");
                int num = 0;
                for (int i = 0; i < strings.length; i++) {
                    for (int j = 0; j < notes.length; j++) {
                        if (strings[i].equals(notes[j])) {
                            num++;
                        }
                    }
                }
                jsp2.setBorder(new TitledBorder("主奏音符（" + num + "）"));
            }
        }
    }

}
