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
    private JFrame frame = new JFrame("�༭������");

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu("�ļ�");
    private JMenu menuEdit = new JMenu("�༭");
    private JMenu menuWindow = new JMenu("����");
    private JMenu menuHelp = new JMenu("����");
    private JMenuItem menuItemReadNotes = new JMenuItem("��ȡ����");
    private JMenuItem menuItemReadAccompaniments = new JMenuItem("��ȡ����");
    private JMenuItem menuItemSave = new JMenuItem("����");
    private JMenuItem menuItemShow = new JMenuItem("����Դ�������д�");
    private JMenuItem menuItemUndo = new JMenuItem("����");
    private JMenuItem menuItemRedo = new JMenuItem("�ָ�");
    private JMenuItem menuItemZoomIn = new JMenuItem("�Ŵ�");
    private JMenuItem menuItemZoomOut = new JMenuItem("��С");
    private JMenuItem menuItemAssist = new JMenuItem("�༭����");
    private JMenuItem menuItemHelp = new JMenuItem("ʹ��˵��");
    private JTextArea jta1 = new JTextArea();
    private JTextArea jta2 = new JTextArea();
    private JTextField jtf1 = new JTextField();
    private JTextField jtf2 = new JTextField();
    private JButton buttonEnter = new JButton("ȷ��");
    private JScrollPane jsp1 = new JScrollPane(jta1);
    private JScrollPane jsp2 = new JScrollPane(jta2);
    private JFileChooser chooser = new JFileChooser("notes");
    private FileNameExtensionFilter filter1 = new FileNameExtensionFilter(
            "*.notes", "notes");
    private FileNameExtensionFilter filter2 = new FileNameExtensionFilter(
            "*.accompaniments", "accompaniments");
    private UndoManager manager = new UndoManager();// Ϊ�ı���ӳ������ָ��Ĺ����������
    private Mouse mouse = new Mouse();
    private TextListener textListener = new TextListener();

    // �����༭���水ť
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
    private JButton buttonNewline = new JButton("����");
    private JButton buttonDelay = new JButton("0");
    private JButton buttonDelete = new JButton("ɾ��");
    private JRadioButton rdBtn_notes = new JRadioButton("��������");
    private JRadioButton rdBtn_accompaniments = new JRadioButton("��������");
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
        jsp1.setBorder(new TitledBorder("����������" + num + "��"));
        strings = jta2.getText().split(" ");
        num = 0;
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < notes.length; j++) {
                if (strings[i].equals(notes[j])) {
                    num++;
                }
            }
        }
        jsp2.setBorder(new TitledBorder("����������" + num + "��"));

        // ���������¼�
        jta1.addMouseListener(mouse);
        jta2.addMouseListener(mouse);
        jta1.getDocument().addUndoableEditListener(manager);// ����ı�������
        jta1.getDocument().addDocumentListener(textListener);
        jta1.setFont(new Font(null, Font.BOLD, this.size));
        jta2.getDocument().addUndoableEditListener(manager);// ����ı�������
        jta2.getDocument().addDocumentListener(textListener);
        jta2.setFont(new Font(null, Font.BOLD, this.size));

        jsp1.setPreferredSize(new Dimension(this.getWidth() - 25, 175));
        jsp2.setPreferredSize(new Dimension(this.getWidth() - 25, 175));

        jtf1.setBorder(new TitledBorder("����(ms)"));
        jtf1.setPreferredSize(new Dimension(100, 50));
        jtf2.setBorder(new TitledBorder("�ļ���"));
        jtf2.setPreferredSize(new Dimension(100, 50));

        add(jsp1);
        add(jsp2);
        add(jtf1);
        add(jtf2);
        //��Ӱ�ť
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
        setTitle("�½����Ƽ���");
        setLayout(new FlowLayout());
        setResizable(false);
        addWindowListener(new Win());
        // setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        // ���ô˴��ڴ�ʱԭ���ڲ��ܽ��в���

        initMenu();
        initTextArea();

        setVisible(true);
        if (isShowHelp.equals("1"))
            showHelpDialog();
    }

    // ��ʾʹ��˵������
    private void showHelpDialog() {
        JOptionPane.showMessageDialog(this,
                "                                                                                             ʹ���Զ����๦��ǰ����ϸ�Ķ���˵��\n"
                        + "1�������������֧�������35��������\n"
                        + "    ��1--�� ~ ��7--���� ��1-�� ~ ��7-���� ��1�� ~ ��7���� ��1+�� ~ ��7+���� ��1++�� ~ ��7++��\n"
                        + "    �ֱ����������������������������������һ��35������\n"
                        + "    �����ֵĺ�׺�ֱ��Ǽ��ţ������»��ߣ���Ӻţ�\n"
                        + "    �������ʱ��û�����ţ�eg��1 5 4- 7-- 3++ 6+ 3--��\n"
                        + "    �����ڼ����ϲ�û�б�����7�ļ�λ��������ʹ���Զ��������б�����7ʱ�����ٶ�Ӧ��λ���б仯�������̼�λ�ޱ仯��\n"
                        + "    ����Ϊû�кڼ�����Դ�Լ�������û�к��ʵĺڼ��ļ�λ�����Ǿ�ȡ���˺ڼ��Ĺ��ܣ�\n"
                        + "2���ֱ��������������������Ҫ�Զ������������ÿ������֮���ÿո�������������ո�ÿ�����ո�֮��Ϊһ��������\n"
                        + "    �������ַ���0�������ʹ���������ӳ�һ����\n"
                        + "    �������������35�������Լ���0������������ַ�����Ե������κ����ã�\n"
                        + "    �������Ҫ������д����������һ�е�ĩβ�Լ���һ�еĿ�ͷ�����Ͽո�\n"
                        + "3����������������뷽ʽ̫���ڷ��������Ե�������ڡ��˵����еġ��༭�������˵���ڵ����ı༭����������ѡ�����½ǵġ����ࡱ/�����ࡱ��\n"
                        + "    Ȼ��Ϳ���ֱ��ͨ���������Ӧ�İ�ť��¼���Ӧ������\n"
                        + "    ��ɾ����ťһ����ɾ��һ��������������һ���ַ���\n"
                        + "4������������ÿ��������֮��ļ��ʱ������λ�Ǻ��루ms��\n"
                        + "    ������������Ƭ���ӵ���̵ļ������������ĵļ������������ʱ�ӱ������\n"
                        + "5������뱣�浱ǰ¼������ӣ���������Ϸ����ļ�����Ȼ��������˵���ȴ����ֱ���ɹ�����ʾ��Ϣ\n"
                        + "    ������ᱣ��Ϊ[������ļ���]+[����]+��.notes����\n"
                        + "    ������ᱣ��Ϊ[������ļ���]+[����]+��.accompaniments����\n"
                        + "6�����ɹ�����������֮�󣬿��Ե����ȡ����/����˵���ֱ��ȡ��������ࣨ����˫���ı���\n"
                        + "7�������ࡢ���ࡢ�������Ѿ�׼������ʱ���Ϳ��Ե��ȷ�ϰ�ť���ص����ٴ�����\n"
                        + "    Ȼ���������ơ��˵����еġ��������Ƽ��ס�����Զ�����\n"
                        + "    ��������������ֻ������һ���������Զ�����ʱҲ���Խ����ֶ����࣬������һ�����ʵ���Զ������࣬�ֶ������ࣩ\n"
                        + "ע���˹��ܽ������֣�Ϊ�˿��ű�д�Զ����๦�ܣ��������ɶȾͲ��ߣ�ͣ�ٻ���Ҳ��Ͳ���ܷ��㣬���־�������\n"
                        + "    ��ʱҲ�����ֿ��١�������಻ͬ����bug��Ŀǰ��Ҳû�а취��������²��Ż��������������\n"
                        + "    �����Զ���ʾһ�Σ��������ڰ����˵������ٴδ򿪣�",
                "�Զ�����ʹ��˵��", JOptionPane.INFORMATION_MESSAGE);
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

    // ��ʾ�����༭����
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
        // ��������ļ�
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
                        JOptionPane.showMessageDialog(this, "�ļ�����ɹ�");
                        jta1_old = jta1.getText();
                        jta2_old = jta2.getText();
                    } else if (fileNotes.exists()
                            && fileAccompaniments.exists()) {
                        int value = JOptionPane.showConfirmDialog(this,
                                "���ļ����Ѵ��ڣ��Ƿ��滻", "����",
                                JOptionPane.WARNING_MESSAGE,
                                JOptionPane.WARNING_MESSAGE);
                        if (value == 0) {
                            FileOutputStream fos = new FileOutputStream(fileNotes);
                            fos.write(jta1.getText().getBytes());
                            fos.close();
                            fos = new FileOutputStream(fileAccompaniments);
                            fos.write(jta2.getText().getBytes());
                            fos.close();
                            JOptionPane.showMessageDialog(this, "�ļ�����ɹ�");
                            jta1_old = jta1.getText();
                            jta2_old = jta2.getText();
                        } else if (value == 1) {
                            setState(NORMAL);
                        }
                    }
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(this, "�Ҳ�����notes���ļ��л����ļ���ʽ����",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (jtf2.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "�������ļ���", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if (jta1.getText().equals("") && jta2.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "��������������������е�һ��", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == menuItemReadNotes) {// ��ȡ����
            chooser.setFileFilter(filter1);
            chooser.setDialogTitle("��ȡ����");
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
        } else if (e.getSource() == menuItemReadAccompaniments) {// ��ȡ����
            chooser.setFileFilter(filter2);
            chooser.setDialogTitle("��ȡ����");
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
        } else if (e.getSource() == menuItemUndo && manager.canUndo()) {// ����
            manager.undo();
        } else if (e.getSource() == menuItemRedo && manager.canRedo()) {// �ָ�
            manager.redo();
        } else if (e.getSource() == menuItemZoomIn) {// �Ŵ�
            this.size += 2;
            if (this.size >= 40)
                this.size = 40;
            jta1.setFont(new Font(null, Font.BOLD, this.size));
            jta2.setFont(new Font(null, Font.BOLD, this.size));
        } else if (e.getSource() == menuItemZoomOut) {// ��С
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
                JOptionPane.showMessageDialog(this, "��������������������ʽ���ԣ�ֻ���������֣�",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        // ͨ�����������������ѡ��ť���жϵ���İ�ť���뵽��һ���ı�����
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
        // ����
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
        // ���˫���¼�
        @Override
        public void mouseClicked(MouseEvent e) {
            // ˫���������ļ�
            if (e.getClickCount() == 2 && e.getSource().equals(jta1)) {
                chooser.setFileFilter(filter1);
                chooser.setDialogTitle("��ȡ����");
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
            // ˫���򿪰����ļ�
            if (e.getClickCount() == 2 && e.getSource().equals(jta2)) {
                chooser.setFileFilter(filter2);
                chooser.setDialogTitle("��ȡ����");
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
        public void windowClosing(WindowEvent e) {// �رմ��ڼ����¼�
            if (e.getSource().equals(NewNotes.this)) {
                // �Ƴ��������������ٴδ򿪴˴���ʱ�ظ���Ӽ�����
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
                jsp1.setBorder(new TitledBorder("����������" + num + "��"));
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
                jsp2.setBorder(new TitledBorder("����������" + num + "��"));
            }
        }
    }

}
