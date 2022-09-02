package piano;

import util.Audio;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Piano extends JFrame implements KeyListener {
    private PlayMode mode;
    private PlayMode mode2;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu_file = new JMenu("文件");
    private JMenu menu_start = new JMenu("开始");
    private JMenu menu_help = new JMenu("帮助");
    private JMenuItem menuItem_new = new JMenuItem("新建自制简谱");
    private JMenu menu_play_in = new JMenu("播放内置简谱");
    private JMenuItem menuItem_qiFengLe = new JMenuItem("起风了");
    private JMenuItem menuItem_play_userMade = new JMenuItem("播放自制简谱");
    private JMenu menu_model = new JMenu("自动弹奏键位模式");
    private JRadioButtonMenuItem radioMenuItem_model1 = new JRadioButtonMenuItem(
            "小键盘 + 字母键盘");
    private JRadioButtonMenuItem radioMenuItem_model2 = new JRadioButtonMenuItem(
            "字母键盘");
    private ButtonGroup group_model = new ButtonGroup();// 存放键位模式的两个菜单栏单选按钮
    private JMenuItem menuItem_forward = new JMenuItem("前进10个音符");
    private JMenuItem menuItem_backward = new JMenuItem("倒退10个音符");
    private JMenuItem menuItem_suspend = new JMenuItem("暂停播放");
    private JMenuItem menuItem_stop = new JMenuItem("停止播放");
    private JMenuItem menuItem_display = new JMenuItem("显示/隐藏进度滑块");
    private JMenuItem menuItem_reset = new JMenuItem("重置进度滑块、音符组件");
    private JMenuItem menuItem_about = new JMenuItem("关于");
    private JSlider slider_progress = new JSlider();// 显示进度滑块
    private JLabel label_index = new JLabel();// 显示当前音符位置
    private JLabel label_notes = new JLabel("主奏");
    private JLabel label_accompaniments = new JLabel("伴奏");

    private JButton[] button = {new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton()};// 白键
    private JButton[] buttonblack = {new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton()};// 黑键
    private JButton[] buttonBorder = {new JButton(), new JButton(),
            new JButton(), new JButton()};// 边框
    private JButton[] button1 = {new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton()};// 第一排键位
    private JLabel[] label1_1 = {new JLabel("丶"), new JLabel("1"),
            new JLabel("2"), new JLabel("3"), new JLabel("4"), new JLabel("5"),
            new JLabel("6"), new JLabel("7"), new JLabel("1"), new JLabel("2"),
            new JLabel("3"), new JLabel("4"), new JLabel("5"), new JLabel("6"),
            new JLabel("4"), new JLabel("5"), new JLabel("6"),
            new JLabel("4"), new JLabel("5"), new JLabel("6"), new JLabel("7")};// 第一排键位音符提示
    private JLabel[] label1_2 = {new JLabel(), new JLabel("."),
            new JLabel("."), new JLabel("."), new JLabel("."), new JLabel("."),
            new JLabel("."), new JLabel("."), new JLabel(":"), new JLabel(":"),
            new JLabel(":"), new JLabel(":"), new JLabel(":"), new JLabel(":"),
            new JLabel(":"), new JLabel(":"), new JLabel(":"),
            new JLabel("."), new JLabel("."), new JLabel("."), new JLabel(".")};// 第一排键位倍音提示
    private JButton[] button2 = {new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton()};// 第二排键位
    private JLabel[] label2_1 = {new JLabel("Tab"), new JLabel("1"),
            new JLabel("2"), new JLabel("3"), new JLabel("4"), new JLabel("5"),
            new JLabel("6"), new JLabel("7"), new JLabel("1"), new JLabel("2"),
            new JLabel("3"), new JLabel("4"), new JLabel("5"), new JLabel("\\"),
            new JLabel("1"), new JLabel("2"), new JLabel("3"),
            new JLabel("7"), new JLabel("1"), new JLabel("2"), new JLabel("3")};// 第二排键位音符提示
    private JLabel[] label2_2 = {new JLabel(), new JLabel(),
            new JLabel(), new JLabel(), new JLabel(), new JLabel(),
            new JLabel(), new JLabel(), new JLabel("."), new JLabel("."),
            new JLabel("."), new JLabel("."), new JLabel("."), new JLabel(),
            new JLabel(":"), new JLabel(":"), new JLabel(":"),
            new JLabel(), new JLabel("."), new JLabel("."), new JLabel(".")};// 第二排键位倍音提示
    private JButton[] button3 = {new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton()};// 第三排键位
    private JLabel[] label3_1 = {new JLabel("Caps Look"), new JLabel("1"),
            new JLabel("2"), new JLabel("3"), new JLabel("4"), new JLabel("5"),
            new JLabel("6"), new JLabel("7"), new JLabel("1"), new JLabel("2"),
            new JLabel("3"), new JLabel("4"), new JLabel("Enter"),
            new JLabel("4"), new JLabel("5"), new JLabel("6")};// 第三排键位音符提示
    private JLabel[] label3_2 = {new JLabel(), new JLabel("."),
            new JLabel("."), new JLabel("."), new JLabel("."), new JLabel("."),
            new JLabel("."), new JLabel("."), new JLabel(), new JLabel(),
            new JLabel(), new JLabel(), new JLabel(), new JLabel(),
            new JLabel(), new JLabel()};// 第三排键位倍音提示
    private JButton[] button4 = {new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton()};// 第四排键位
    private JLabel[] label4_1 = {new JLabel("Shift"), new JLabel("1"),
            new JLabel("2"), new JLabel("3"), new JLabel("4"), new JLabel("5"),
            new JLabel("6"), new JLabel("7"), new JLabel("1"), new JLabel("2"),
            new JLabel("3"), new JLabel("Shift"), new JLabel("4"),
            new JLabel("1"), new JLabel("2"), new JLabel("3"),
            new JLabel("7")};// 第四排键位音符提示
    private JLabel[] label4_2 = {new JLabel(), new JLabel(":"),
            new JLabel(":"), new JLabel(":"), new JLabel(":"), new JLabel(":"),
            new JLabel(":"), new JLabel(":"), new JLabel("."), new JLabel("."),
            new JLabel("."), new JLabel(), new JLabel("."), new JLabel(),
            new JLabel(), new JLabel(), new JLabel("·")};// 第四排键位倍音提示
    private JButton[] button5 = {new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton(), new JButton(), new JButton(),
            new JButton(), new JButton()};// 第五排键位
    private JLabel[] label5_1 = {new JLabel("Ctrl"), new JLabel("Win"),
            new JLabel("Alt"), new JLabel("Space"), new JLabel("Alt"),
            new JLabel("Fn"), new JLabel("Menu"), new JLabel("Ctrl"),
            new JLabel("1"), new JLabel("2"), new JLabel("3"), new JLabel("5"),
            new JLabel("6")};// 第五排键位音符提示
    private JLabel[] label5_2 = {new JLabel(), new JLabel(), new JLabel(),
            new JLabel(), new JLabel(), new JLabel(), new JLabel(),
            new JLabel(), new JLabel("."), new JLabel("."), new JLabel("."),
            new JLabel("."), new JLabel(".")};// 第五排键位倍音提示

    private Boolean KEY_CODE_LOWL_1 = true;// 判断按键按下的状态是否还存在
    private Boolean KEY_CODE_LOWL_2 = true;
    private Boolean KEY_CODE_LOWL_3 = true;
    private Boolean KEY_CODE_LOWL_4 = true;
    private Boolean KEY_CODE_LOWL_5 = true;
    private Boolean KEY_CODE_LOWL_6 = true;
    private Boolean KEY_CODE_LOWL_7 = true;
    private Boolean KEY_CODE_LOW_1 = true;
    private Boolean KEY_CODE_LOW_2 = true;
    private Boolean KEY_CODE_LOW_3 = true;
    private Boolean KEY_CODE_LOW_4 = true;
    private Boolean KEY_CODE_LOW_5 = true;
    private Boolean KEY_CODE_LOW_6 = true;
    private Boolean KEY_CODE_LOW_7 = true;
    private Boolean KEY_CODE_MIDDLE_1 = true;
    private Boolean KEY_CODE_MIDDLE_2 = true;
    private Boolean KEY_CODE_MIDDLE_3 = true;
    private Boolean KEY_CODE_MIDDLE_4 = true;
    private Boolean KEY_CODE_MIDDLE_5 = true;
    private Boolean KEY_CODE_MIDDLE_6 = true;
    private Boolean KEY_CODE_MIDDLE_7 = true;
    private Boolean KEY_CODE_HIGH_1 = true;
    private Boolean KEY_CODE_HIGH_2 = true;
    private Boolean KEY_CODE_HIGH_3 = true;
    private Boolean KEY_CODE_HIGH_4 = true;
    private Boolean KEY_CODE_HIGH_5 = true;
    private Boolean KEY_CODE_HIGH_6 = true;
    private Boolean KEY_CODE_HIGH_7 = true;
    private Boolean KEY_CODE_HIGHH_1 = true;
    private Boolean KEY_CODE_HIGHH_2 = true;
    private Boolean KEY_CODE_HIGHH_3 = true;
    private Boolean KEY_CODE_HIGHH_4 = true;
    private Boolean KEY_CODE_HIGHH_5 = true;
    private Boolean KEY_CODE_HIGHH_6 = true;

    private Color color = Color.white;
    private Color colorll = new Color(0, 50, 200);
    private Color colorl = new Color(0, 100, 150);
    private Color colorm = new Color(0, 150, 100);
    private Color colorh = new Color(0, 200, 50);
    private Color colorhh = new Color(0, 255, 0);

    private NewNotes newNotes = new NewNotes();

    private File file = new File("C:\\Users\\" + System.getenv("USERNAME") + "\\Documents", "MyPiano.cfg");
    private String[] options = {"确定", "取消"};
    private String read;
    private String isShowUpdate;
    private int model;
    private boolean slider_progressIsPress = false;

    public void initMenu() {// 初始化菜单栏
        group_model.add(radioMenuItem_model1);
        group_model.add(radioMenuItem_model2);
        if (model == 1)
            radioMenuItem_model1.setSelected(true);
        else if (model == 2)
            radioMenuItem_model2.setSelected(true);

        setJMenuBar(menuBar);
        menuBar.setBackground(new Color(100, 100, 100));
        menuBar.add(menu_file);
        menuBar.add(menu_start);
        menuBar.add(menu_help);
        menu_file.add(menuItem_new);
        menu_help.add(menuItem_about);
        menu_start.add(menu_play_in);
        menu_play_in.add(menuItem_qiFengLe);
        menu_start.add(menuItem_play_userMade);
        menu_start.addSeparator();
        menu_start.add(menu_model);
        menu_model.add(radioMenuItem_model1);
        menu_model.add(radioMenuItem_model2);
        menu_start.addSeparator();
        menu_start.add(menuItem_backward);
        menu_start.add(menuItem_forward);
        menu_start.add(menuItem_suspend);
        menu_start.add(menuItem_stop);
        menu_start.addSeparator();
        menu_start.add(menuItem_display);
        menu_start.add(menuItem_reset);
        menuItem_forward.setEnabled(false);
        menuItem_forward.setAccelerator(KeyStroke.getKeyStroke("F2"));
        menuItem_backward.setEnabled(false);
        menuItem_backward.setAccelerator(KeyStroke.getKeyStroke("F1"));
        menuItem_suspend.setEnabled(false);
        menuItem_suspend.setAccelerator(KeyStroke.getKeyStroke("SPACE"));
        menuItem_stop.setEnabled(false);
        menuItem_stop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, ActionEvent.SHIFT_MASK));
        menuItem_display.setAccelerator((KeyStroke.getKeyStroke("F3")));
        menuItem_qiFengLe.addActionListener(new Action());
        menuItem_play_userMade.addActionListener(new Action());
        menuItem_play_userMade.setAccelerator(KeyStroke
                .getKeyStroke(KeyEvent.VK_SPACE, ActionEvent.CTRL_MASK));
        menuItem_forward.addActionListener(new Action());
        menuItem_backward.addActionListener(new Action());
        menuItem_suspend.addActionListener(new Action());
        menuItem_stop.addActionListener(new Action());
        menuItem_about.addActionListener(new Action());
        menuItem_new.addActionListener(new Action());
        menuItem_display.addActionListener(new Action());
        menuItem_reset.addActionListener(new Action());
        menu_file.setForeground(new Color(220, 220, 220));// 设置前景色
        menu_start.setForeground(new Color(220, 220, 220));
        menu_help.setForeground(new Color(220, 220, 220));
    }

    public void initButton() {// 初始化按键
        // 初始化黑键
        for (int i = 0, j = 14, k = 2; i < buttonblack.length; i++, j += 25, k += 2) {
            add(buttonblack[i]);
            buttonblack[i].setFocusable(false);
            buttonblack[i].setEnabled(false);
            buttonblack[i].addMouseMotionListener(new Mouse());
            buttonblack[i].setBackground(Color.BLACK);
            if (i == 2 || i == 5 || i == 7 || i == 10 || i == 12 || i == 15
                    || i == 17 || i == 20 || i == 22) {
                j += 25;
                k += 2;
            }
            buttonblack[i].setBounds(25 + j + k, 20, 20, 65);
        }
        // 初始化白键
        for (int i = 0, j = 0, k = 0; i < button.length; i++, j += 25, k += 2) {
            add(button[i]);
            button[i].setFocusable(false);
            button[i].setEnabled(false);
            button[i].addMouseMotionListener(new Mouse());
            button[i].addMouseListener(new Mouse());
            button[i].setBackground(Color.white);
            button[i].setBounds(25 + j + k, 20, 25, 100);
        }
        // 初始化边框
        for (JButton temp : buttonBorder) {
            temp.setFocusable(false);
            temp.setEnabled(false);
            temp.addMouseMotionListener(new Mouse());
            temp.setBackground(new Color(165, 42, 42));
            add(temp);
        }
        buttonBorder[0].setBounds(25, 10, 943, 10);
        buttonBorder[1].setBounds(25, 120, 943, 10);
        buttonBorder[2].setBounds(15, 10, 10, 120);
        buttonBorder[3].setBounds(968, 10, 10, 120);
        // 初始化第一排键位
        for (int i = 0, x = 0, width = 40; i < button1.length; i++, x += 40) {
            add(label1_1[i]);// 先添加的在上一层
            add(label1_2[i]);
            add(button1[i]);
            button1[i].setFocusable(false);
            button1[i].setEnabled(false);
            button1[i].addMouseMotionListener(new Mouse());
            button1[i].addMouseListener(new Mouse());
            button1[i].setBackground(Color.white);
            if (i == 13) {
                width = 80;
            }
            if (i == 14) {
                x += 80;
            }
            if (i == 17) {
                x += 40;
            }
            if (i == 0) {
                button1[i].setBackground(new Color(100, 100, 100));
                label1_1[i].setForeground(new Color(220, 220, 220));
            } else {
                button1[i].addMouseListener(new Mouse());
            }
            label1_2[i].setVerticalAlignment(JLabel.TOP);
            label1_2[i].setHorizontalAlignment(JLabel.CENTER);
            label1_1[i].setHorizontalAlignment(JLabel.CENTER);
            label1_1[i].setBounds(x + 16, 150, width, 40);
            label1_2[i].setBounds(x + 16, 150, width, 40);
            button1[i].setBounds(x + 16, 150, width, 40);
            width = 40;
        }
        // 初始化第二排键位
        for (int i = 0, x = 0, width = 40, height = 40; i < button2.length; i++, x += 40) {
            add(label2_1[i]);
            add(label2_2[i]);
            add(button2[i]);
            button2[i].setFocusable(false);
            button2[i].setEnabled(false);
            button2[i].addMouseMotionListener(new Mouse());
            button2[i].addMouseListener(new Mouse());
            button2[i].setBackground(Color.white);
            if (i == 0) {
                width = 60;
            }
            if (i == 1) {
                x += 20;
            }
            if (i == 13) {
                width = 60;
            }
            if (i == 14) {
                x += 60;
            }
            if (i == 17) {
                x += 40;
            }
            if (i == 20) {
                height = 80;
            }
            if (i == 0 || i == 13) {
                button2[i].setBackground(new Color(100, 100, 100));
                label2_1[i].setForeground(new Color(220, 220, 220));
            }
            if (i == 20) {
                label2_1[i].setFont(new Font(null, Font.BOLD, 20));
                label2_2[i].setFont(new Font(null, Font.BOLD, 20));
            }
            label2_2[i].setVerticalAlignment(JLabel.TOP);
            label2_2[i].setHorizontalAlignment(JLabel.CENTER);
            label2_1[i].setHorizontalAlignment(JLabel.CENTER);
            label2_1[i].setBounds(x + 16, 190, width, height);
            label2_2[i].setBounds(x + 16, 190, width, height);
            button2[i].setBounds(x + 16, 190, width, height);
            width = 40;
            height = 40;
        }
        // 初始化第三排键位
        for (int i = 0, x = 0, width = 40; i < button3.length; i++, x += 40) {
            add(label3_1[i]);
            add(label3_2[i]);
            add(button3[i]);
            button3[i].setFocusable(false);
            button3[i].setEnabled(false);
            button3[i].addMouseMotionListener(new Mouse());
            button3[i].addMouseListener(new Mouse());
            button3[i].setBackground(Color.white);
            if (i == 0) {
                width = 80;
            }
            if (i == 1) {
                x += 40;
            }
            if (i == 12) {
                width = 80;
            }
            if (i == 13) {
                x += 240;
            }
            if (i == 0 || i == 12) {
                button3[i].setBackground(new Color(100, 100, 100));
                label3_1[i].setForeground(new Color(220, 220, 220));
            }
            label3_2[i].setVerticalAlignment(JLabel.BOTTOM);
            label3_2[i].setHorizontalAlignment(JLabel.CENTER);
            label3_1[i].setHorizontalAlignment(JLabel.CENTER);
            label3_1[i].setBounds(x + 16, 230, width, 40);
            label3_2[i].setBounds(x + 16, 230, width, 40);
            button3[i].setBounds(x + 16, 230, width, 40);
            width = 40;
        }
        // 初始化第四排键位
        for (int i = 0, width = 40, height = 40, x = 0; i < button4.length; i++, x += 40) {
            add(label4_1[i]);
            add(label4_2[i]);
            add(button4[i]);
            button4[i].setFocusable(false);
            button4[i].setEnabled(false);
            button4[i].addMouseMotionListener(new Mouse());
            button4[i].addMouseListener(new Mouse());
            button4[i].setBackground(Color.white);
            if (i == 0) {
                width = 100;
            }
            if (i == 1) {
                x += 60;
            }
            if (i == 11) {
                width = 100;
            }
            if (i == 12) {
                x += 140;
            }
            if (i == 13) {
                x += 80;
            }
            if (i == 16) {
                height = 80;
            }
            if (i == 0 || i == 11) {
                button4[i].setBackground(new Color(100, 100, 100));
                label4_1[i].setForeground(new Color(220, 220, 220));
            }
            if (i == 16) {
                label4_1[i].setFont(new Font(null, Font.BOLD, 20));
                label4_2[i].setFont(new Font(null, Font.BOLD, 20));
            }
            label4_1[i].setHorizontalAlignment(JLabel.CENTER);
            label4_2[i].setHorizontalAlignment(JLabel.CENTER);
            label4_2[i].setVerticalAlignment(JLabel.BOTTOM);
            label4_1[i].setBounds(x + 16, 270, width, height);
            label4_2[i].setBounds(x + 16, 270, width, height);
            button4[i].setBounds(x + 16, 270, width, height);
            width = 40;
            height = 40;
        }
        // 初始化第五排键位
        for (int i = 0, x = 0, width = 54; i < button5.length; i++, x += 54) {
            add(label5_1[i]);
            add(label5_2[i]);
            add(button5[i]);
            button5[i].setFocusable(false);
            button5[i].setEnabled(false);
            button5[i].addMouseMotionListener(new Mouse());
            button5[i].addMouseListener(new Mouse());
            button5[i].setBackground(Color.white);
            if (i == 3) {
                width = 222;
            }
            if (i == 4) {
                x += 168;
            }
            if (i == 8) {
                x += 40;
            }
            if (i >= 8 && i <= 10) {
                width = 40;
            }
            if (i == 9 || i == 10) {
                x -= 14;
            }
            if (i == 11) {
                x += 26;
                width = 80;
            }
            if (i == 12) {
                width = 40;
                x += 26;
            }
            if (i >= 0 && i <= 7) {
                button5[i].setBackground(new Color(100, 100, 100));
                label5_1[i].setForeground(new Color(220, 220, 220));
            }
            label5_1[i].setHorizontalAlignment(JLabel.CENTER);
            label5_2[i].setHorizontalAlignment(JLabel.CENTER);
            label5_2[i].setVerticalAlignment(JLabel.BOTTOM);
            label5_1[i].setBounds(x + 16, 310, width, 40);
            label5_2[i].setBounds(x + 16, 310, width, 40);
            button5[i].setBounds(x + 16, 310, width, 40);
            width = 54;
        }
    }

    public void initslider_progresslider() {// 初始化滑块与标签

        label_accompaniments.setBounds(630, 205, 52, 50);
        label_accompaniments.setVisible(false);
        label_accompaniments.setFont(new Font(null, Font.BOLD, 20));
        label_accompaniments.setText("音符");
        label_accompaniments.setBorder(new TitledBorder("伴奏"));
        label_accompaniments.setHorizontalAlignment(JLabel.CENTER);
        label_accompaniments.setVerticalAlignment(JLabel.CENTER);
        label_accompaniments.setOpaque(true);
        label_accompaniments.setBackground(Color.white);
        label_accompaniments.setEnabled(false);
        add(label_accompaniments);

        label_index.setBounds(691, 205, 50, 50);
        label_index.setVisible(false);
        label_index.setFont(new Font(null, Font.BOLD, 20));
        label_index.setText("0");
        label_index.setBorder(new TitledBorder("位置"));
        label_index.setHorizontalAlignment(JLabel.CENTER);
        label_index.setVerticalAlignment(JLabel.CENTER);
        label_index.setOpaque(true);
        label_index.setBackground(Color.white);
        label_index.setEnabled(false);
        add(label_index);

        label_notes.setBounds(751, 205, 52, 50);
        label_notes.setVisible(false);
        label_notes.setFont(new Font(null, Font.BOLD, 20));
        label_notes.setText("音符");
        label_notes.setBorder(new TitledBorder("主奏"));
        label_notes.setHorizontalAlignment(JLabel.CENTER);
        label_notes.setVerticalAlignment(JLabel.CENTER);
        label_notes.setOpaque(true);// 设置为不透明，否则背景色没有效果
        label_notes.setBackground(Color.white);
        add(label_notes);

        slider_progress.setVisible(false);
        slider_progress.setBounds(621, 150, 190, 50);
        slider_progress.setBackground(Color.white);
        slider_progress.setFocusable(false);
        slider_progress.setMinimum(0);
        slider_progress.setValue(0);
        slider_progress.setPaintLabels(true);// 显示标签
        slider_progress.setPaintTicks(true);// 显示刻度
        slider_progress.addChangeListener(new Change());
        slider_progress.addMouseListener(new Mouse());
        slider_progress.setEnabled(false);
        add(slider_progress);
    }

    public void startFrame() {// 启动窗口
        // 获取配置文件信息，显示更新说明与默认选择自动弹奏键位模式
        if (file.exists())
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] byt = new byte[256];
                int len = fis.read(byt);
                read = new String(byt, 0, len);
                fis.close();
                isShowUpdate = read.substring
                        (read.indexOf("isShowUpdate = ") + "isShowUpdate = ".length(), read.indexOf(" [isShowUpdate]"));
                String model = read.substring
                        (read.indexOf("model = ") + "model = ".length(), read.indexOf(" [model]"));
                this.model = Integer.valueOf(model).intValue();
            } catch (IOException e) {
                e.printStackTrace();
            }
        setIconImage(new AddImage("/img/img.jpg").addImage());

        enableInputMethods(false);// 禁用输入法
        Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_NUM_LOCK,
                true);// 打开num键
        setTitle("MyPiano");
        setSize(1006, 430);
        setLocation(
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()
                        / 2 - this.getWidth() / 2),
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()
                        / 2 - (this.getHeight() / 2)));
        getContentPane().setBackground(Color.gray);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addKeyListener(this);
        addMouseMotionListener(new Mouse());
        addMouseListener(new Mouse());
        addWindowListener(new Win());
        requestFocus();

        initMenu();
        initButton();
        initslider_progresslider();

        setVisible(true);

        if (this.isShowUpdate.equals("1"))
            //new Tips();
            showDialog();
    }

    // 显示更新及关于界面
    public void showDialog() {
        String date = "2021-09-30";
        JOptionPane.showMessageDialog(Piano.this,
                "当前版本：" + Start.getNewVersion() + "  （" + date +"）\n\n" +
                        "更新说明:\n" +
                        "1、新增小键盘处对应倍高音按键，修改音符可视化组件相应快捷键\n" +
                        "2、新增新建自制简谱界面双击文本域打开文件的功能\n" +
                        "3、优化新建自制简谱界面字体缩放体验\n" +
                        "4、新增新建自制简谱界面读取简谱文件后自动填充音长与文件名以及保存简谱文件时自动保存音长功能\n" +
                        "5、优化配置文件\n" +
                        "6、新增鼠标拖拽即可连续弹奏功能，同时也新增了一些Bug（菜单项的功能尽量使用快捷键操作）\n" +
                        "7、新增新建自制简谱界面可利用菜单项快捷打开简谱文件所在文件夹\n" +
                        "8、新增新建自制简谱界面的编辑辅助功能\n" +
                        "9、优化音符解析模块\n" +
                        "10、新增音符数量显示\n" +
                        "注：仅自动显示一次，后续可在帮助菜单栏中再次打开\n" +
                        "\n如您发现了bug或者有更好的修改建议，敬请反馈\n" +
                        "技术支持QQ：1452682437",
                "关于此软件", JOptionPane.INFORMATION_MESSAGE);
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] byt = new byte[256];
                int len = fis.read(byt);
                read = new String(byt, 0, len);
                fis.close();

                char[] ch = read.toCharArray();
                ch[read.indexOf("isShowUpdate = ") + "isShowUpdate = ".length()] = '0';
                String read_new = new String(ch);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(read_new.getBytes());
                fos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {// 键盘按下事件
        switch (e.getKeyCode()) {
            ///////////////////////////////////////////////////////////////// 倍低音
            case KeyEvent.VK_Z:
                if (KEY_CODE_LOWL_1) {
                    KEY_CODE_LOWL_1 = false;
                    label_notes.setForeground(colorll);
                    label_notes.setText("1--");
                    new Audio("audio/ll1.mp3").start();
                    button[0].setBackground(colorll);
                    button4[1].setBackground(colorll);
                }
                break;
            case KeyEvent.VK_X:
                if (KEY_CODE_LOWL_2) {
                    KEY_CODE_LOWL_2 = false;
                    label_notes.setForeground(colorll);
                    label_notes.setText("2--");
                    new Audio("audio/ll2.mp3").start();
                    button[1].setBackground(colorll);
                    button4[2].setBackground(colorll);
                }
                break;
            case KeyEvent.VK_C:
                if (KEY_CODE_LOWL_3) {
                    KEY_CODE_LOWL_3 = false;
                    label_notes.setForeground(colorll);
                    label_notes.setText("3--");
                    new Audio("audio/ll3.mp3").start();
                    button[2].setBackground(colorll);
                    button4[3].setBackground(colorll);
                }
                break;
            case KeyEvent.VK_V:
                if (KEY_CODE_LOWL_4) {
                    KEY_CODE_LOWL_4 = false;
                    label_notes.setForeground(colorll);
                    label_notes.setText("4--");
                    new Audio("audio/ll4.mp3").start();
                    button[3].setBackground(colorll);
                    button4[4].setBackground(colorll);
                }
                break;
            case KeyEvent.VK_B:
                if (KEY_CODE_LOWL_5) {
                    KEY_CODE_LOWL_5 = false;
                    label_notes.setForeground(colorll);
                    label_notes.setText("5--");
                    new Audio("audio/ll5.mp3").start();
                    button[4].setBackground(colorll);
                    button4[5].setBackground(colorll);
                }
                break;
            case KeyEvent.VK_N:
                if (KEY_CODE_LOWL_6) {
                    KEY_CODE_LOWL_6 = false;
                    label_notes.setForeground(colorll);
                    label_notes.setText("6--");
                    new Audio("audio/ll6.mp3").start();
                    button[5].setBackground(colorll);
                    button4[6].setBackground(colorll);
                }
                break;
            case KeyEvent.VK_M:
                if (KEY_CODE_LOWL_7) {
                    KEY_CODE_LOWL_7 = false;
                    label_notes.setForeground(colorll);
                    label_notes.setText("7--");
                    new Audio("audio/ll7.mp3").start();
                    button[6].setBackground(colorll);
                    button4[7].setBackground(colorll);
                }
                break;
            ///////////////////////////////////////////////////////////////// 倍低音
            ///////////////////////////////////////////////////////////////// 低音
            case KeyEvent.VK_A:
                if (KEY_CODE_LOW_1) {
                    KEY_CODE_LOW_1 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("1-");
                    new Audio("audio/l1.mp3").start();
                    button[7].setBackground(colorl);
                    button3[1].setBackground(colorl);
                }
                break;
            case 44:
                if (KEY_CODE_LOW_1) {
                    KEY_CODE_LOW_1 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("1-");
                    new Audio("audio/l1.mp3").start();
                    button[7].setBackground(colorl);
                    button4[8].setBackground(colorl);
                }
                break;
            case KeyEvent.VK_LEFT:
                if (KEY_CODE_LOW_1) {
                    KEY_CODE_LOW_1 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("1-");
                    new Audio("audio/l1.mp3").start();
                    button[7].setBackground(colorl);
                    button5[8].setBackground(colorl);
                }
                break;

            case KeyEvent.VK_S:
                if (KEY_CODE_LOW_2) {
                    KEY_CODE_LOW_2 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("2-");
                    new Audio("audio/l2.mp3").start();
                    button[8].setBackground(colorl);
                    button3[2].setBackground(colorl);
                }
                break;
            case 46:
                if (KEY_CODE_LOW_2) {
                    KEY_CODE_LOW_2 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("2-");
                    new Audio("audio/l2.mp3").start();
                    button[8].setBackground(colorl);
                    button4[9].setBackground(colorl);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (KEY_CODE_LOW_2) {
                    KEY_CODE_LOW_2 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("2-");
                    new Audio("audio/l2.mp3").start();
                    button[8].setBackground(colorl);
                    button5[9].setBackground(colorl);
                }
                break;

            case KeyEvent.VK_D:
                if (KEY_CODE_LOW_3) {
                    KEY_CODE_LOW_3 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("3-");
                    new Audio("audio/l3.mp3").start();
                    button[9].setBackground(colorl);
                    button3[3].setBackground(colorl);
                }
                break;
            case 47:
                if (KEY_CODE_LOW_3) {
                    KEY_CODE_LOW_3 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("3-");
                    new Audio("audio/l3.mp3").start();
                    button[9].setBackground(colorl);
                    button4[10].setBackground(colorl);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (KEY_CODE_LOW_3) {
                    KEY_CODE_LOW_3 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("3-");
                    new Audio("audio/l3.mp3").start();
                    button[9].setBackground(colorl);
                    button5[10].setBackground(colorl);
                }
                break;

            case KeyEvent.VK_F:
                if (KEY_CODE_LOW_4) {
                    KEY_CODE_LOW_4 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("4-");
                    new Audio("audio/l4.mp3").start();
                    button[10].setBackground(colorl);
                    button3[4].setBackground(colorl);
                }
                break;
            case KeyEvent.VK_UP:
                if (KEY_CODE_LOW_4) {
                    KEY_CODE_LOW_4 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("4-");
                    new Audio("audio/l4.mp3").start();
                    button[10].setBackground(colorl);
                    button4[12].setBackground(colorl);
                }
                break;

            case KeyEvent.VK_G:
                if (KEY_CODE_LOW_5) {
                    KEY_CODE_LOW_5 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("5-");
                    new Audio("audio/l5.mp3").start();
                    button[11].setBackground(colorl);
                    button3[5].setBackground(colorl);
                }
                break;
            case KeyEvent.VK_NUMPAD0:
                if (KEY_CODE_LOW_5) {
                    KEY_CODE_LOW_5 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("5-");
                    new Audio("audio/l5.mp3").start();
                    button[11].setBackground(colorl);
                    button5[11].setBackground(colorl);
                }
                break;

            case KeyEvent.VK_H:
                if (KEY_CODE_LOW_6) {
                    KEY_CODE_LOW_6 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("6-");
                    new Audio("audio/l6.mp3").start();
                    button[12].setBackground(colorl);
                    button3[6].setBackground(colorl);
                }
            case 110:// 小键盘的del（点）
                if (KEY_CODE_LOW_6) {
                    KEY_CODE_LOW_6 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("6-");
                    new Audio("audio/l6.mp3").start();
                    button[12].setBackground(colorl);
                    button5[12].setBackground(colorl);
                }
                break;

            case KeyEvent.VK_J:
                if (KEY_CODE_LOW_7) {
                    KEY_CODE_LOW_7 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("7-");
                    new Audio("audio/l7.mp3").start();
                    button[13].setBackground(colorl);
                    button3[7].setBackground(colorl);
                }
                break;
            case KeyEvent.VK_ENTER:
                if (KEY_CODE_LOW_7) {
                    KEY_CODE_LOW_7 = false;
                    label_notes.setForeground(colorl);
                    label_notes.setText("7-");
                    new Audio("audio/l7.mp3").start();
                    button[13].setBackground(colorl);
                    button4[16].setBackground(colorl);
                }
                break;
            ///////////////////////////////////////////////////////////////// 低音
            ///////////////////////////////////////////////////////////////// 中音
            case KeyEvent.VK_Q:
                if (KEY_CODE_MIDDLE_1) {
                    KEY_CODE_MIDDLE_1 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("1");
                    new Audio("audio/m1.mp3").start();
                    button[14].setBackground(colorm);
                    button2[1].setBackground(colorm);
                }
                break;
            case KeyEvent.VK_NUMPAD1:
                if (KEY_CODE_MIDDLE_1) {
                    KEY_CODE_MIDDLE_1 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("1");
                    new Audio("audio/m1.mp3").start();
                    button[14].setBackground(colorm);
                    button4[13].setBackground(colorm);
                }
                break;
            case KeyEvent.VK_K:
                if (KEY_CODE_MIDDLE_1) {
                    KEY_CODE_MIDDLE_1 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("1");
                    new Audio("audio/m1.mp3").start();
                    button[14].setBackground(colorm);
                    button3[8].setBackground(colorm);
                }
                break;

            case KeyEvent.VK_W:
                if (KEY_CODE_MIDDLE_2) {
                    KEY_CODE_MIDDLE_2 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("2");
                    new Audio("audio/m2.mp3").start();
                    button[15].setBackground(colorm);
                    button2[2].setBackground(colorm);
                }
                break;
            case KeyEvent.VK_NUMPAD2:
                if (KEY_CODE_MIDDLE_2) {
                    KEY_CODE_MIDDLE_2 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("2");
                    new Audio("audio/m2.mp3").start();
                    button[15].setBackground(colorm);
                    button4[14].setBackground(colorm);
                }
                break;
            case KeyEvent.VK_L:
                if (KEY_CODE_MIDDLE_2) {
                    KEY_CODE_MIDDLE_2 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("2");
                    new Audio("audio/m2.mp3").start();
                    button[15].setBackground(colorm);
                    button3[9].setBackground(colorm);
                }
                break;

            case KeyEvent.VK_E:
                if (KEY_CODE_MIDDLE_3) {
                    KEY_CODE_MIDDLE_3 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("3");
                    new Audio("audio/m3.mp3").start();
                    button[16].setBackground(colorm);
                    button2[3].setBackground(colorm);
                }
                break;
            case KeyEvent.VK_NUMPAD3:
                if (KEY_CODE_MIDDLE_3) {
                    KEY_CODE_MIDDLE_3 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("3");
                    new Audio("audio/m3.mp3").start();
                    button[16].setBackground(colorm);
                    button4[15].setBackground(colorm);
                }
                break;
            case 59:// 分号
                if (KEY_CODE_MIDDLE_3) {
                    KEY_CODE_MIDDLE_3 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("3");
                    new Audio("audio/m3.mp3").start();
                    button[16].setBackground(colorm);
                    button3[10].setBackground(colorm);
                }
                break;

            case KeyEvent.VK_R:
                if (KEY_CODE_MIDDLE_4) {
                    KEY_CODE_MIDDLE_4 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("4");
                    new Audio("audio/m4.mp3").start();
                    button[17].setBackground(colorm);
                    button2[4].setBackground(colorm);
                }
                break;
            case KeyEvent.VK_NUMPAD4:
                if (KEY_CODE_MIDDLE_4) {
                    KEY_CODE_MIDDLE_4 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("4");
                    new Audio("audio/m4.mp3").start();
                    button[17].setBackground(colorm);
                    button3[13].setBackground(colorm);
                }
                break;
            case 222:// 引号
                if (KEY_CODE_MIDDLE_4) {
                    KEY_CODE_MIDDLE_4 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("4");
                    new Audio("audio/m4.mp3").start();
                    button[17].setBackground(colorm);
                    button3[11].setBackground(colorm);
                }
                break;

            case KeyEvent.VK_T:
                if (KEY_CODE_MIDDLE_5) {
                    KEY_CODE_MIDDLE_5 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("5");
                    new Audio("audio/m5.mp3").start();
                    button[18].setBackground(colorm);
                    button2[5].setBackground(colorm);
                }
                break;
            case KeyEvent.VK_NUMPAD5:
                if (KEY_CODE_MIDDLE_5) {
                    KEY_CODE_MIDDLE_5 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("5");
                    new Audio("audio/m5.mp3").start();
                    button[18].setBackground(colorm);
                    button3[14].setBackground(colorm);
                }
                break;

            case KeyEvent.VK_Y:
                if (KEY_CODE_MIDDLE_6) {
                    KEY_CODE_MIDDLE_6 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("6");
                    new Audio("audio/m6.mp3").start();
                    button[19].setBackground(colorm);
                    button2[6].setBackground(colorm);
                }
                break;
            case KeyEvent.VK_NUMPAD6:
                if (KEY_CODE_MIDDLE_6) {
                    KEY_CODE_MIDDLE_6 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("6");
                    new Audio("audio/m6.mp3").start();
                    button[19].setBackground(colorm);
                    button3[15].setBackground(colorm);
                }
                break;

            case KeyEvent.VK_U:
                if (KEY_CODE_MIDDLE_7) {
                    KEY_CODE_MIDDLE_7 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("7");
                    new Audio("audio/m7.mp3").start();
                    button[20].setBackground(colorm);
                    button2[7].setBackground(colorm);
                }
                break;
            case KeyEvent.VK_NUMPAD7:
                if (KEY_CODE_MIDDLE_7) {
                    KEY_CODE_MIDDLE_7 = false;
                    label_notes.setForeground(colorm);
                    label_notes.setText("7");
                    new Audio("audio/m7.mp3").start();
                    button[20].setBackground(colorm);
                    button2[17].setBackground(colorm);
                }
                break;
            ///////////////////////////////////////////////////////////////// 中音
            ///////////////////////////////////////////////////////////////// 高音
            case KeyEvent.VK_1:
                if (KEY_CODE_HIGH_1) {
                    KEY_CODE_HIGH_1 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("1+");
                    new Audio("audio/h1.mp3").start();
                    button[21].setBackground(colorh);
                    button1[1].setBackground(colorh);
                }
                break;
            case KeyEvent.VK_NUMPAD8:
                if (KEY_CODE_HIGH_1) {
                    KEY_CODE_HIGH_1 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("1+");
                    new Audio("audio/h1.mp3").start();
                    button[21].setBackground(colorh);
                    button2[18].setBackground(colorh);
                }
                break;
            case KeyEvent.VK_I:
                if (KEY_CODE_HIGH_1) {
                    KEY_CODE_HIGH_1 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("1+");
                    new Audio("audio/h1.mp3").start();
                    button[21].setBackground(colorh);
                    button2[8].setBackground(colorh);
                }
                break;

            case KeyEvent.VK_2:
                if (KEY_CODE_HIGH_2) {
                    KEY_CODE_HIGH_2 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("2+");
                    new Audio("audio/h2.mp3").start();
                    button[22].setBackground(colorh);
                    button1[2].setBackground(colorh);
                }
                break;
            case KeyEvent.VK_NUMPAD9:
                if (KEY_CODE_HIGH_2) {
                    KEY_CODE_HIGH_2 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("2+");
                    new Audio("audio/h2.mp3").start();
                    button[22].setBackground(colorh);
                    button2[19].setBackground(colorh);
                }
                break;
            case KeyEvent.VK_O:
                if (KEY_CODE_HIGH_2) {
                    KEY_CODE_HIGH_2 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("2+");
                    new Audio("audio/h2.mp3").start();
                    button[22].setBackground(colorh);
                    button2[9].setBackground(colorh);
                }
                break;

            case KeyEvent.VK_3:
                if (KEY_CODE_HIGH_3) {
                    KEY_CODE_HIGH_3 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("3+");
                    new Audio("audio/h3.mp3").start();
                    button[23].setBackground(colorh);
                    button1[3].setBackground(colorh);
                }
                break;
            case KeyEvent.VK_ADD:
                if (KEY_CODE_HIGH_3) {
                    KEY_CODE_HIGH_3 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("3+");
                    new Audio("audio/h3.mp3").start();
                    button[23].setBackground(colorh);
                    button2[20].setBackground(colorh);
                }
                break;
            case KeyEvent.VK_P:
                if (KEY_CODE_HIGH_3) {
                    KEY_CODE_HIGH_3 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("3+");
                    new Audio("audio/h3.mp3").start();
                    button[23].setBackground(colorh);
                    button2[10].setBackground(colorh);
                }
                break;

            case KeyEvent.VK_4:
                if (KEY_CODE_HIGH_4) {
                    KEY_CODE_HIGH_4 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("4+");
                    new Audio("audio/h4.mp3").start();
                    button[24].setBackground(colorh);
                    button1[4].setBackground(colorh);
                }
                break;
            case 91:
                if (KEY_CODE_HIGH_4) {
                    KEY_CODE_HIGH_4 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("4+");
                    new Audio("audio/h4.mp3").start();
                    button[24].setBackground(colorh);
                    button2[11].setBackground(colorh);
                }
                break;

            case KeyEvent.VK_NUM_LOCK:// 这个键的处理有点问题，以后再解决
                if (KEY_CODE_HIGH_4) {
                    KEY_CODE_HIGH_4 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("4+");
                    button[24].setBackground(colorh);
                    button1[17].setBackground(colorh);
                    Toolkit.getDefaultToolkit()
                            .setLockingKeyState(KeyEvent.VK_NUM_LOCK, true);
                    if (Toolkit.getDefaultToolkit()
                            .getLockingKeyState(KeyEvent.VK_NUM_LOCK)) {
                        new Audio("audio/h4.mp3").start();
                    }
                }
                break;

            case KeyEvent.VK_5:
                if (KEY_CODE_HIGH_5) {
                    KEY_CODE_HIGH_5 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("5+");
                    new Audio("audio/h5.mp3").start();
                    button[25].setBackground(colorh);
                    button1[5].setBackground(colorh);
                }
                break;
            case 93:
                if (KEY_CODE_HIGH_5) {
                    KEY_CODE_HIGH_5 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("5+");
                    new Audio("audio/h5.mp3").start();
                    button[25].setBackground(colorh);
                    button2[12].setBackground(colorh);
                }
                break;
            case KeyEvent.VK_DIVIDE:
                if (KEY_CODE_HIGH_5) {
                    KEY_CODE_HIGH_5 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("5+");
                    new Audio("audio/h5.mp3").start();
                    button[25].setBackground(colorh);
                    button1[18].setBackground(colorh);
                }
                break;

            case KeyEvent.VK_6:
                if (KEY_CODE_HIGH_6) {
                    KEY_CODE_HIGH_6 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("6+");
                    new Audio("audio/h6.mp3").start();
                    button[26].setBackground(colorh);
                    button1[6].setBackground(colorh);
                }
                break;
            case KeyEvent.VK_MULTIPLY:
                if (KEY_CODE_HIGH_6) {
                    KEY_CODE_HIGH_6 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("6+");
                    new Audio("audio/h6.mp3").start();
                    button[26].setBackground(colorh);
                    button1[19].setBackground(colorh);
                }
                break;

            case KeyEvent.VK_7:
                if (KEY_CODE_HIGH_7) {
                    KEY_CODE_HIGH_7 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("7+");
                    new Audio("audio/h7.mp3").start();
                    button[27].setBackground(colorh);
                    button1[7].setBackground(colorh);
                }
                break;
            case KeyEvent.VK_SUBTRACT:
                if (KEY_CODE_HIGH_7) {
                    KEY_CODE_HIGH_7 = false;
                    label_notes.setForeground(colorh);
                    label_notes.setText("7+");
                    new Audio("audio/h7.mp3").start();
                    button[27].setBackground(colorh);
                    button1[20].setBackground(colorh);
                }
                break;
            ///////////////////////////////////////////////////////////////// 高音
            ///////////////////////////////////////////////////////////////// 倍高音
            case KeyEvent.VK_8:
                if (KEY_CODE_HIGHH_1) {
                    KEY_CODE_HIGHH_1 = false;
                    label_notes.setForeground(colorhh);
                    label_notes.setText("1++");
                    new Audio("audio/hh1.mp3").start();
                    button[28].setBackground(colorhh);
                    button1[8].setBackground(colorhh);
                }
                break;
            case KeyEvent.VK_DELETE:
                if (KEY_CODE_HIGHH_1) {
                    KEY_CODE_HIGHH_1 = false;
                    label_notes.setForeground(colorhh);
                    label_notes.setText("1++");
                    new Audio("audio/hh1.mp3").start();
                    button[28].setBackground(colorhh);
                    button2[14].setBackground(colorhh);
                }
                break;
            case KeyEvent.VK_9:
                if (KEY_CODE_HIGHH_2) {
                    KEY_CODE_HIGHH_2 = false;
                    label_notes.setForeground(colorhh);
                    label_notes.setText("2++");
                    new Audio("audio/hh2.mp3").start();
                    button[29].setBackground(colorhh);
                    button1[9].setBackground(colorhh);
                }
                break;
            case KeyEvent.VK_END:
                if (KEY_CODE_HIGHH_2) {
                    KEY_CODE_HIGHH_2 = false;
                    label_notes.setForeground(colorhh);
                    label_notes.setText("2++");
                    new Audio("audio/hh2.mp3").start();
                    button[29].setBackground(colorhh);
                    button2[15].setBackground(colorhh);
                }
                break;
            case KeyEvent.VK_0:
                if (KEY_CODE_HIGHH_3) {
                    KEY_CODE_HIGHH_3 = false;
                    label_notes.setForeground(colorhh);
                    label_notes.setText("3++");
                    new Audio("audio/hh3.mp3").start();
                    button[30].setBackground(colorhh);
                    button1[10].setBackground(colorhh);
                }
                break;
            case KeyEvent.VK_PAGE_DOWN:
                if (KEY_CODE_HIGHH_3) {
                    KEY_CODE_HIGHH_3 = false;
                    label_notes.setForeground(colorhh);
                    label_notes.setText("3++");
                    new Audio("audio/hh3.mp3").start();
                    button[30].setBackground(colorhh);
                    button2[16].setBackground(colorhh);
                }
                break;
            case 45:// 字母键盘上方的减号
                if (KEY_CODE_HIGHH_4) {
                    KEY_CODE_HIGHH_4 = false;
                    label_notes.setForeground(colorhh);
                    label_notes.setText("4++");
                    new Audio("audio/hh4.mp3").start();
                    button[31].setBackground(colorhh);
                    button1[11].setBackground(colorhh);
                }
                break;
            case KeyEvent.VK_INSERT:
                if (KEY_CODE_HIGHH_4) {
                    KEY_CODE_HIGHH_4 = false;
                    label_notes.setForeground(colorhh);
                    label_notes.setText("4++");
                    new Audio("audio/hh4.mp3").start();
                    button[31].setBackground(colorhh);
                    button1[14].setBackground(colorhh);
                }
                break;
            case 61:// 等号
                if (KEY_CODE_HIGHH_5) {
                    KEY_CODE_HIGHH_5 = false;
                    label_notes.setForeground(colorhh);
                    label_notes.setText("5++");
                    new Audio("audio/hh5.mp3").start();
                    button[32].setBackground(colorhh);
                    button1[12].setBackground(colorhh);
                }
                break;
            case KeyEvent.VK_HOME:
                if (KEY_CODE_HIGHH_5) {
                    KEY_CODE_HIGHH_5 = false;
                    label_notes.setForeground(colorhh);
                    label_notes.setText("5++");
                    new Audio("audio/hh5.mp3").start();
                    button[32].setBackground(colorhh);
                    button1[15].setBackground(colorhh);
                }
                break;
            case 8:// 退格键
                if (KEY_CODE_HIGHH_6) {
                    KEY_CODE_HIGHH_6 = false;
                    label_notes.setForeground(colorhh);
                    label_notes.setText("6++");
                    new Audio("audio/hh6.mp3").start();
                    button[33].setBackground(colorhh);
                    button1[13].setBackground(colorhh);
                }
                break;
            case KeyEvent.VK_PAGE_UP:
                if (KEY_CODE_HIGHH_6) {
                    KEY_CODE_HIGHH_6 = false;
                    label_notes.setForeground(colorhh);
                    label_notes.setText("6++");
                    new Audio("audio/hh6.mp3").start();
                    button[33].setBackground(colorhh);
                    button1[16].setBackground(colorhh);
                }
                break;
            ///////////////////////////////////////////////////////////////// 倍高音
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {// 按键释放事件
        switch (e.getKeyCode()) {
            ///////////////////////////////////////////////////////////////// 倍低音
            case KeyEvent.VK_Z:
                KEY_CODE_LOWL_1 = true;
                label_notes.setText("");
                button[0].setBackground(color);
                button4[1].setBackground(color);
                break;
            case KeyEvent.VK_X:
                KEY_CODE_LOWL_2 = true;
                label_notes.setText("");
                button[1].setBackground(color);
                button4[2].setBackground(color);
                break;
            case KeyEvent.VK_C:
                KEY_CODE_LOWL_3 = true;
                label_notes.setText("");
                button[2].setBackground(color);
                button4[3].setBackground(color);
                break;
            case KeyEvent.VK_V:
                KEY_CODE_LOWL_4 = true;
                label_notes.setText("");
                button[3].setBackground(color);
                button4[4].setBackground(color);
                break;
            case KeyEvent.VK_B:
                KEY_CODE_LOWL_5 = true;
                label_notes.setText("");
                button[4].setBackground(color);
                button4[5].setBackground(color);
                break;
            case KeyEvent.VK_N:
                KEY_CODE_LOWL_6 = true;
                label_notes.setText("");
                button[5].setBackground(color);
                button4[6].setBackground(color);
                break;
            case KeyEvent.VK_M:
                KEY_CODE_LOWL_7 = true;
                label_notes.setText("");
                button[6].setBackground(color);
                button4[7].setBackground(color);
                break;
            ///////////////////////////////////////////////////////////////// 倍低音
            ///////////////////////////////////////////////////////////////// 低音
            case KeyEvent.VK_A:
                KEY_CODE_LOW_1 = true;
                label_notes.setText("");
                button[7].setBackground(color);
                button3[1].setBackground(color);
                break;
            case KeyEvent.VK_LEFT:
                KEY_CODE_LOW_1 = true;
                label_notes.setText("");
                button[7].setBackground(color);
                button5[8].setBackground(color);
                break;
            case 44:
                KEY_CODE_LOW_1 = true;
                label_notes.setText("");
                button[7].setBackground(color);
                button4[8].setBackground(color);
                break;
            case KeyEvent.VK_S:
                KEY_CODE_LOW_2 = true;
                label_notes.setText("");
                button[8].setBackground(color);
                button3[2].setBackground(color);
                break;
            case KeyEvent.VK_DOWN:
                KEY_CODE_LOW_2 = true;
                label_notes.setText("");
                button[8].setBackground(color);
                button5[9].setBackground(color);
                break;
            case 46:
                KEY_CODE_LOW_2 = true;
                label_notes.setText("");
                button[8].setBackground(color);
                button4[9].setBackground(color);
                break;
            case KeyEvent.VK_D:
                KEY_CODE_LOW_3 = true;
                label_notes.setText("");
                button[9].setBackground(color);
                button3[3].setBackground(color);
                break;
            case KeyEvent.VK_RIGHT:
                KEY_CODE_LOW_3 = true;
                label_notes.setText("");
                button[9].setBackground(color);
                button5[10].setBackground(color);
                break;
            case 47:
                KEY_CODE_LOW_3 = true;
                label_notes.setText("");
                button[9].setBackground(color);
                button4[10].setBackground(color);
                break;
            case KeyEvent.VK_F:
                KEY_CODE_LOW_4 = true;
                label_notes.setText("");
                button[10].setBackground(color);
                button3[4].setBackground(color);
                break;
            case KeyEvent.VK_UP:
                KEY_CODE_LOW_4 = true;
                label_notes.setText("");
                button[10].setBackground(color);
                button4[12].setBackground(color);
                break;
            case KeyEvent.VK_G:
                KEY_CODE_LOW_5 = true;
                label_notes.setText("");
                button[11].setBackground(color);
                button3[5].setBackground(color);
                break;
            case KeyEvent.VK_NUMPAD0:
                KEY_CODE_LOW_5 = true;
                label_notes.setText("");
                button[11].setBackground(color);
                button5[11].setBackground(color);
                break;
            case KeyEvent.VK_H:
                KEY_CODE_LOW_6 = true;
                label_notes.setText("");
                button[12].setBackground(color);
                button3[6].setBackground(color);
                break;
            case 110:
                KEY_CODE_LOW_6 = true;
                label_notes.setText("");
                button[12].setBackground(color);
                button5[12].setBackground(color);
                break;
            case KeyEvent.VK_J:
                KEY_CODE_LOW_7 = true;
                label_notes.setText("");
                button[13].setBackground(color);
                button3[7].setBackground(color);
                break;
            case KeyEvent.VK_ENTER:
                KEY_CODE_LOW_7 = true;
                label_notes.setText("");
                button[13].setBackground(color);
                button4[16].setBackground(color);
                break;
            ///////////////////////////////////////////////////////////////// 低音
            ///////////////////////////////////////////////////////////////// 中音
            case KeyEvent.VK_Q:
                KEY_CODE_MIDDLE_1 = true;
                label_notes.setText("");
                button[14].setBackground(color);
                button2[1].setBackground(color);
                break;
            case KeyEvent.VK_NUMPAD1:
                KEY_CODE_MIDDLE_1 = true;
                label_notes.setText("");
                button[14].setBackground(color);
                button4[13].setBackground(color);
                break;
            case KeyEvent.VK_K:
                KEY_CODE_MIDDLE_1 = true;
                label_notes.setText("");
                button[14].setBackground(color);
                button3[8].setBackground(color);
                break;
            case KeyEvent.VK_W:
                KEY_CODE_MIDDLE_2 = true;
                label_notes.setText("");
                button[15].setBackground(color);
                button2[2].setBackground(color);
                break;
            case KeyEvent.VK_NUMPAD2:
                KEY_CODE_MIDDLE_2 = true;
                label_notes.setText("");
                button[15].setBackground(color);
                button4[14].setBackground(color);
                break;
            case KeyEvent.VK_L:
                KEY_CODE_MIDDLE_2 = true;
                label_notes.setText("");
                button[15].setBackground(color);
                button3[9].setBackground(color);
                break;
            case KeyEvent.VK_E:
                KEY_CODE_MIDDLE_3 = true;
                label_notes.setText("");
                button[16].setBackground(color);
                button2[3].setBackground(color);
                break;
            case KeyEvent.VK_NUMPAD3:
                KEY_CODE_MIDDLE_3 = true;
                label_notes.setText("");
                button[16].setBackground(color);
                button4[15].setBackground(color);
                break;
            case 59:
                KEY_CODE_MIDDLE_3 = true;
                label_notes.setText("");
                button[16].setBackground(color);
                button3[10].setBackground(color);
                break;
            case KeyEvent.VK_R:
                KEY_CODE_MIDDLE_4 = true;
                label_notes.setText("");
                button[17].setBackground(color);
                button2[4].setBackground(color);
                break;
            case KeyEvent.VK_NUMPAD4:
                KEY_CODE_MIDDLE_4 = true;
                label_notes.setText("");
                button[17].setBackground(color);
                button3[13].setBackground(color);
                break;
            case 222:
                KEY_CODE_MIDDLE_4 = true;
                label_notes.setText("");
                button[17].setBackground(color);
                button3[11].setBackground(color);
                break;
            case KeyEvent.VK_T:
                KEY_CODE_MIDDLE_5 = true;
                label_notes.setText("");
                button[18].setBackground(color);
                button2[5].setBackground(color);
                break;
            case KeyEvent.VK_NUMPAD5:
                KEY_CODE_MIDDLE_5 = true;
                label_notes.setText("");
                button[18].setBackground(color);
                button3[14].setBackground(color);
                break;
            case KeyEvent.VK_Y:
                KEY_CODE_MIDDLE_6 = true;
                label_notes.setText("");
                button[19].setBackground(color);
                button2[6].setBackground(color);
                break;
            case KeyEvent.VK_NUMPAD6:
                KEY_CODE_MIDDLE_6 = true;
                label_notes.setText("");
                button[19].setBackground(color);
                button3[15].setBackground(color);
                break;
            case KeyEvent.VK_U:
                KEY_CODE_MIDDLE_7 = true;
                label_notes.setText("");
                button[20].setBackground(color);
                button2[7].setBackground(color);
                break;
            case KeyEvent.VK_NUMPAD7:
                KEY_CODE_MIDDLE_7 = true;
                label_notes.setText("");
                button[20].setBackground(color);
                button2[17].setBackground(color);
                break;
            ///////////////////////////////////////////////////////////////// 中音
            ///////////////////////////////////////////////////////////////// 高音
            case KeyEvent.VK_1:
                KEY_CODE_HIGH_1 = true;
                label_notes.setText("");
                button[21].setBackground(color);
                button1[1].setBackground(color);
                break;
            case KeyEvent.VK_NUMPAD8:
                KEY_CODE_HIGH_1 = true;
                label_notes.setText("");
                button[21].setBackground(color);
                button2[18].setBackground(color);
                break;
            case KeyEvent.VK_I:
                KEY_CODE_HIGH_1 = true;
                label_notes.setText("");
                button[21].setBackground(color);
                button2[8].setBackground(color);
                break;
            case KeyEvent.VK_2:
                KEY_CODE_HIGH_2 = true;
                label_notes.setText("");
                button[22].setBackground(color);
                button1[2].setBackground(color);
                break;
            case KeyEvent.VK_NUMPAD9:
                KEY_CODE_HIGH_2 = true;
                label_notes.setText("");
                button[22].setBackground(color);
                button2[19].setBackground(color);
                break;
            case KeyEvent.VK_O:
                KEY_CODE_HIGH_2 = true;
                label_notes.setText("");
                button[22].setBackground(color);
                button2[9].setBackground(color);
                break;
            case KeyEvent.VK_3:
                KEY_CODE_HIGH_3 = true;
                label_notes.setText("");
                button[23].setBackground(color);
                button1[3].setBackground(color);
                break;
            case KeyEvent.VK_ADD:
                KEY_CODE_HIGH_3 = true;
                label_notes.setText("");
                button[23].setBackground(color);
                button2[20].setBackground(color);
                break;
            case KeyEvent.VK_P:
                KEY_CODE_HIGH_3 = true;
                label_notes.setText("");
                button[23].setBackground(color);
                button2[10].setBackground(color);
                break;
            case KeyEvent.VK_4:
                KEY_CODE_HIGH_4 = true;
                label_notes.setText("");
                button[24].setBackground(color);
                button1[4].setBackground(color);
                break;
            case 91:
                KEY_CODE_HIGH_4 = true;
                label_notes.setText("");
                button[24].setBackground(color);
                button2[11].setBackground(color);
                break;
            case KeyEvent.VK_NUM_LOCK:
                KEY_CODE_HIGH_4 = true;
                label_notes.setText("");
                Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_NUM_LOCK,
                        true);
                if (Toolkit.getDefaultToolkit()
                        .getLockingKeyState(KeyEvent.VK_NUM_LOCK)) {
                    button[24].setBackground(color);
                    button1[17].setBackground(color);
                }
                break;
            case KeyEvent.VK_5:
                KEY_CODE_HIGH_5 = true;
                label_notes.setText("");
                button[25].setBackground(color);
                button1[5].setBackground(color);
                break;
            case KeyEvent.VK_DIVIDE:
                KEY_CODE_HIGH_5 = true;
                label_notes.setText("");
                button[25].setBackground(color);
                button1[18].setBackground(color);
                break;
            case 93:
                KEY_CODE_HIGH_5 = true;
                label_notes.setText("");
                button[25].setBackground(color);
                button2[12].setBackground(color);
                break;
            case KeyEvent.VK_6:
                KEY_CODE_HIGH_6 = true;
                label_notes.setText("");
                button[26].setBackground(color);
                button1[6].setBackground(color);
                break;
            case KeyEvent.VK_MULTIPLY:
                KEY_CODE_HIGH_6 = true;
                label_notes.setText("");
                button[26].setBackground(color);
                button1[19].setBackground(color);
                break;
            case KeyEvent.VK_7:
                KEY_CODE_HIGH_7 = true;
                label_notes.setText("");
                button[27].setBackground(color);
                button1[7].setBackground(color);
                break;
            case KeyEvent.VK_SUBTRACT:
                KEY_CODE_HIGH_7 = true;
                label_notes.setText("");
                button[27].setBackground(color);
                button1[20].setBackground(color);
                break;
            ///////////////////////////////////////////////////////////////// 高音
            ///////////////////////////////////////////////////////////////// 倍高音
            case KeyEvent.VK_8:
                KEY_CODE_HIGHH_1 = true;
                label_notes.setText("");
                button[28].setBackground(color);
                button1[8].setBackground(color);
                break;
            case KeyEvent.VK_DELETE:
                KEY_CODE_HIGHH_1 = true;
                label_notes.setText("");
                button[28].setBackground(color);
                button2[14].setBackground(color);
                break;
            case KeyEvent.VK_9:
                KEY_CODE_HIGHH_2 = true;
                label_notes.setText("");
                button[29].setBackground(color);
                button1[9].setBackground(color);
                break;
            case KeyEvent.VK_END:
                KEY_CODE_HIGHH_2 = true;
                label_notes.setText("");
                button[29].setBackground(color);
                button2[15].setBackground(color);
                break;
            case KeyEvent.VK_0:
                KEY_CODE_HIGHH_3 = true;
                label_notes.setText("");
                button[30].setBackground(color);
                button1[10].setBackground(color);
                break;
            case KeyEvent.VK_PAGE_DOWN:
                KEY_CODE_HIGHH_3 = true;
                label_notes.setText("");
                button[30].setBackground(color);
                button2[16].setBackground(color);
                break;
            case 45:
                KEY_CODE_HIGHH_4 = true;
                label_notes.setText("");
                button[31].setBackground(color);
                button1[11].setBackground(color);
                break;
            case KeyEvent.VK_INSERT:
                KEY_CODE_HIGHH_4 = true;
                label_notes.setText("");
                button[31].setBackground(color);
                button1[14].setBackground(color);
                break;
            case 61:
                KEY_CODE_HIGHH_5 = true;
                label_notes.setText("");
                button[32].setBackground(color);
                button1[12].setBackground(color);
                break;
            case KeyEvent.VK_HOME:
                KEY_CODE_HIGHH_5 = true;
                label_notes.setText("");
                button[32].setBackground(color);
                button1[15].setBackground(color);
                break;
            case 8:
                KEY_CODE_HIGHH_6 = true;
                label_notes.setText("");
                button[33].setBackground(color);
                button1[13].setBackground(color);
                break;
            case KeyEvent.VK_PAGE_UP:
                KEY_CODE_HIGHH_6 = true;
                label_notes.setText("");
                button[33].setBackground(color);
                button1[16].setBackground(color);
                break;
            ///////////////////////////////////////////////////////////////// 倍高音
        }
        repaint();
    }

    class Change implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == slider_progress
                    && Piano.this.slider_progressIsPress) {
                mode.setIndex(slider_progress.getValue());
                mode2.setIndex(slider_progress.getValue());
            }
        }

    }

    class Action extends JFrame implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {// 菜单栏/按钮点击事件
            if (e.getSource() == menuItem_qiFengLe) {
                try {
                    mode.setStop();// 播放前先停止之前的线程，以免多首曲子同时播放出现混响
                    mode2.setStop();
                } catch (Exception e1) {
                    // TODO: handle exception
                }
                menuItem_reset.setEnabled(false);
                label_index.setEnabled(true);
                label_accompaniments.setEnabled(true);
                // 起风了
                slider_progress
                        .setMaximum(Notes.notes_QiFengLe.split(" ").length);
                label_index.setBorder(new TitledBorder(String
                        .valueOf(Notes.notes_QiFengLe.split(" ").length)));
                slider_progress.setLabelTable(null);// 很重要
                // 要先设置为null再从新设置刻度值，不然不会正常更新
                slider_progress.setMajorTickSpacing(
                        (Notes.notes_QiFengLe.split(" ").length / 7) == 0
                                ? 1
                                : (Notes.notes_QiFengLe.split(" ").length
                                / 7));
                mode = new PlayMode();
                mode.setName("主奏线程");
                mode2 = new PlayMode();
                mode2.setName("伴奏线程");
                mode.setNotes(Notes.notes_QiFengLe, 180);
                mode2.setNotes(Notes.accompaniments_QiFengLe, 180);

                mode.start();
                mode2.start();
                slider_progress.setEnabled(true);
                menuItem_suspend.setEnabled(true);
                menuItem_stop.setEnabled(true);
                menuItem_forward.setEnabled(true);
                menuItem_backward.setEnabled(true);
            } else if (e.getSource() == menuItem_play_userMade) {// 播放自制简谱
                if ((newNotes.getNotes() == null
                        && newNotes.getAccompaniments() == null)
                        || (newNotes.getNotes().equals("")
                        && newNotes.getAccompaniments().equals(""))) {
                    JOptionPane.showMessageDialog(this, "自制简谱为空，请先新建自制简谱",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    mode.setStop();// 播放前先停止之前的线程，以免多首曲子同时播放出现混响
                    mode2.setStop();
                } catch (Exception e1) {
                    // TODO: handle exception
                }
                menuItem_reset.setEnabled(false);
                label_index.setEnabled(true);
                label_accompaniments.setEnabled(true);
                if (!newNotes.getNotes().equals("")) {
                    slider_progress
                            .setMaximum(newNotes.getNotes().split(" ").length);
                    label_index.setBorder(new TitledBorder(String
                            .valueOf(newNotes.getNotes().split(" ").length)));
                    slider_progress.setLabelTable(null);
                    slider_progress.setMajorTickSpacing(
                            (newNotes.getNotes().split(" ").length / 7) == 0 ? 1
                                    : (newNotes.getNotes().split(" ").length
                                    / 7));// 如果音符不足7个，也就是结果为0，那就设置间隔为1
                } else if (newNotes.getNotes().equals("")) {
                    slider_progress.setMaximum(
                            newNotes.getAccompaniments().split(" ").length);
                    label_index.setBorder(new TitledBorder(String.valueOf(
                            newNotes.getAccompaniments().split(" ").length)));
                    slider_progress.setLabelTable(null);
                    slider_progress.setMajorTickSpacing(
                            (newNotes.getAccompaniments().split(" ").length
                                    / 7) == 0 ? 1
                                    : (newNotes.getAccompaniments()
                                    .split(" ").length / 7));
                }
                mode = new PlayMode();
                mode.setName("主奏线程");
                mode2 = new PlayMode();
                mode2.setName("伴奏线程");
                mode.setNotes(newNotes.getNotes(), newNotes.getTimes());
                mode2.setNotes(newNotes.getAccompaniments(),
                        newNotes.getTimes());
                mode.start();
                mode2.start();
                slider_progress.setEnabled(true);
                menuItem_suspend.setEnabled(true);
                menuItem_stop.setEnabled(true);
                menuItem_forward.setEnabled(true);
                menuItem_backward.setEnabled(true);
            } else if (e.getSource() == menuItem_suspend) {// 暂停/继续
                if (e.getActionCommand().equals("暂停播放")) {
                    try {
                        mode.setSuspend(true);
                        mode2.setSuspend(true);
                        menuItem_suspend.setText("继续播放");
                    } catch (Exception e1) {
                    }
                } else if (e.getActionCommand().equals("继续播放")) {
                    try {
                        mode.setSuspend(false);
                        mode2.setSuspend(false);
                        menuItem_suspend.setText("暂停播放");
                    } catch (Exception e1) {
                    }
                }

            } else if (e.getSource() == menuItem_forward) {// 前进10个音符
                try {
                    mode.setForward();
                    mode2.setForward();
                } catch (Exception e1) {
                }
            } else if (e.getSource() == menuItem_backward) {// 倒退10个音符
                try {
                    mode.setBackward();
                    mode2.setBackward();
                } catch (Exception e1) {
                }
            } else if (e.getSource() == menuItem_stop) {// 停止
                try {
                    mode.setStop();
                    mode2.setStop();
                    menuItem_suspend.setText("暂停播放");
                    menuItem_suspend.setEnabled(false);
                    menuItem_stop.setEnabled(false);
                    menuItem_forward.setEnabled(false);
                    menuItem_backward.setEnabled(false);
                    slider_progress.setEnabled(false);
                } catch (Exception e1) {
                }
            } else if (e.getSource() == menuItem_display) {
                if (!slider_progress.isVisible()) {
                    button1[14].setVisible(false);
                    button1[15].setVisible(false);
                    button1[16].setVisible(false);
                    button2[14].setVisible(false);
                    button2[15].setVisible(false);
                    button2[16].setVisible(false);
                    label1_1[14].setVisible(false);
                    label1_1[15].setVisible(false);
                    label1_1[16].setVisible(false);
                    label1_2[14].setVisible(false);
                    label1_2[15].setVisible(false);
                    label1_2[16].setVisible(false);
                    label2_1[14].setVisible(false);
                    label2_1[15].setVisible(false);
                    label2_1[16].setVisible(false);
                    label2_2[14].setVisible(false);
                    label2_2[15].setVisible(false);
                    label2_2[16].setVisible(false);
                    slider_progress.setVisible(true);
                    label_index.setVisible(true);
                    label_notes.setVisible(true);
                    label_accompaniments.setVisible(true);
                } else if (slider_progress.isVisible()) {
                    button1[14].setVisible(true);
                    button1[15].setVisible(true);
                    button1[16].setVisible(true);
                    button2[14].setVisible(true);
                    button2[15].setVisible(true);
                    button2[16].setVisible(true);
                    label1_1[14].setVisible(true);
                    label1_1[15].setVisible(true);
                    label1_1[16].setVisible(true);
                    label1_2[14].setVisible(true);
                    label1_2[15].setVisible(true);
                    label1_2[16].setVisible(true);
                    label2_1[14].setVisible(true);
                    label2_1[15].setVisible(true);
                    label2_1[16].setVisible(true);
                    label2_2[14].setVisible(true);
                    label2_2[15].setVisible(true);
                    label2_2[16].setVisible(true);
                    slider_progress.setVisible(false);
                    label_index.setVisible(false);
                    label_notes.setVisible(false);
                    label_accompaniments.setVisible(false);
                }
            } else if (e.getSource() == menuItem_reset) {// 重置音符可视化组件
                slider_progress.setLabelTable(null);
                slider_progress.setValue(0);
                label_notes.setForeground(Color.black);
                label_notes.setText("音符");
                label_index.setText("0");
                label_index.setBorder(new TitledBorder("位置"));
                label_accompaniments.setForeground(Color.black);
                label_accompaniments.setText("音符");

            } else if (e.getSource() == menuItem_about) {
                showDialog();
            } else if (e.getSource() == menuItem_new) {// 新建自制简谱
                if (!newNotes.isShowing())
                    newNotes.initFrame();
                else {
                    newNotes.setState(JFrame.NORMAL);
                }
            }
        }
    }

    class Mouse extends MouseAdapter {

        // 鼠标拖拽
        @Override
        public void mouseDragged(MouseEvent e) {
            repaint();
        }

        // 鼠标移动
        @Override
        public void mouseMoved(MouseEvent e) {
        }

        // 鼠标按下
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getComponent() == slider_progress) {
                Piano.this.slider_progressIsPress = true;
            }

            // 白键
            if (e.getComponent() == button[0]) {
                label_notes.setForeground(colorll);
                label_notes.setText("1--");
                new Audio("audio/ll1.mp3").start();
                e.getComponent().setBackground(colorll);
            } else if (e.getComponent() == button[1]) {
                label_notes.setForeground(colorll);
                label_notes.setText("2--");
                new Audio("audio/ll2.mp3").start();
                e.getComponent().setBackground(colorll);
            } else if (e.getComponent() == button[2]) {
                label_notes.setForeground(colorll);
                label_notes.setText("3--");
                new Audio("audio/ll3.mp3").start();
                e.getComponent().setBackground(colorll);
            } else if (e.getComponent() == button[3]) {
                label_notes.setForeground(colorll);
                label_notes.setText("4--");
                new Audio("audio/ll4.mp3").start();
                e.getComponent().setBackground(colorll);
            } else if (e.getComponent() == button[4]) {
                label_notes.setForeground(colorll);
                label_notes.setText("5--");
                new Audio("audio/ll5.mp3").start();
                e.getComponent().setBackground(colorll);
            } else if (e.getComponent() == button[5]) {
                label_notes.setForeground(colorll);
                label_notes.setText("6--");
                new Audio("audio/ll6.mp3").start();
                e.getComponent().setBackground(colorll);
            } else if (e.getComponent() == button[6]) {
                label_notes.setForeground(colorll);
                label_notes.setText("7--");
                new Audio("audio/ll7.mp3").start();
                e.getComponent().setBackground(colorll);
            } else if (e.getComponent() == button[7]) {
                label_notes.setForeground(colorl);
                label_notes.setText("1-");
                new Audio("audio/l1.mp3").start();
                e.getComponent().setBackground(colorl);
            } else if (e.getComponent() == button[8]) {
                label_notes.setForeground(colorl);
                label_notes.setText("2-");
                new Audio("audio/l2.mp3").start();
                e.getComponent().setBackground(colorl);
            } else if (e.getComponent() == button[9]) {
                label_notes.setForeground(colorl);
                label_notes.setText("3-");
                new Audio("audio/l3.mp3").start();
                e.getComponent().setBackground(colorl);
            } else if (e.getComponent() == button[10]) {
                label_notes.setForeground(colorl);
                label_notes.setText("4-");
                new Audio("audio/l4.mp3").start();
                e.getComponent().setBackground(colorl);
            } else if (e.getComponent() == button[11]) {
                label_notes.setForeground(colorl);
                label_notes.setText("5-");
                new Audio("audio/l5.mp3").start();
                e.getComponent().setBackground(colorl);
            } else if (e.getComponent() == button[12]) {
                label_notes.setForeground(colorl);
                label_notes.setText("6-");
                new Audio("audio/l6.mp3").start();
                e.getComponent().setBackground(colorl);
            } else if (e.getComponent() == button[13]) {
                label_notes.setForeground(colorl);
                label_notes.setText("7-");
                new Audio("audio/l7.mp3").start();
                e.getComponent().setBackground(colorl);
            } else if (e.getComponent() == button[14]) {
                label_notes.setForeground(colorm);
                label_notes.setText("1");
                new Audio("audio/m1.mp3").start();
                e.getComponent().setBackground(colorm);
            } else if (e.getComponent() == button[15]) {
                label_notes.setForeground(colorm);
                label_notes.setText("2");
                new Audio("audio/m2.mp3").start();
                e.getComponent().setBackground(colorm);
            } else if (e.getComponent() == button[16]) {
                label_notes.setForeground(colorm);
                label_notes.setText("3");
                new Audio("audio/m3.mp3").start();
                e.getComponent().setBackground(colorm);
            } else if (e.getComponent() == button[17]) {
                label_notes.setForeground(colorm);
                label_notes.setText("4");
                new Audio("audio/m4.mp3").start();
                e.getComponent().setBackground(colorm);
            } else if (e.getComponent() == button[18]) {
                label_notes.setForeground(colorm);
                label_notes.setText("5");
                new Audio("audio/m5.mp3").start();
                e.getComponent().setBackground(colorm);
            } else if (e.getComponent() == button[19]) {
                label_notes.setForeground(colorm);
                label_notes.setText("6");
                new Audio("audio/m6.mp3").start();
                e.getComponent().setBackground(colorm);
            } else if (e.getComponent() == button[20]) {
                label_notes.setForeground(colorm);
                label_notes.setText("7");
                new Audio("audio/m7.mp3").start();
                e.getComponent().setBackground(colorm);
            } else if (e.getComponent() == button[21]) {
                label_notes.setForeground(colorh);
                label_notes.setText("1+");
                new Audio("audio/h1.mp3").start();
                e.getComponent().setBackground(colorh);
            } else if (e.getComponent() == button[22]) {
                label_notes.setForeground(colorh);
                label_notes.setText("2+");
                new Audio("audio/h2.mp3").start();
                e.getComponent().setBackground(colorh);
            } else if (e.getComponent() == button[23]) {
                label_notes.setForeground(colorh);
                label_notes.setText("3+");
                new Audio("audio/h3.mp3").start();
                e.getComponent().setBackground(colorh);
            } else if (e.getComponent() == button[24]) {
                label_notes.setForeground(colorh);
                label_notes.setText("4+");
                new Audio("audio/h4.mp3").start();
                e.getComponent().setBackground(colorh);
            } else if (e.getComponent() == button[25]) {
                label_notes.setForeground(colorh);
                label_notes.setText("5+");
                new Audio("audio/h5.mp3").start();
                e.getComponent().setBackground(colorh);
            } else if (e.getComponent() == button[26]) {
                label_notes.setForeground(colorh);
                label_notes.setText("6+");
                new Audio("audio/h6.mp3").start();
                e.getComponent().setBackground(colorh);
            } else if (e.getComponent() == button[27]) {
                label_notes.setForeground(colorh);
                label_notes.setText("7+");
                new Audio("audio/h7.mp3").start();
                e.getComponent().setBackground(colorh);
            } else if (e.getComponent() == button[28]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("1++");
                new Audio("audio/hh1.mp3").start();
                e.getComponent().setBackground(colorhh);
            } else if (e.getComponent() == button[29]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("2++");
                new Audio("audio/hh2.mp3").start();
                e.getComponent().setBackground(colorhh);
            } else if (e.getComponent() == button[30]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("3++");
                new Audio("audio/hh3.mp3").start();
                e.getComponent().setBackground(colorhh);
            } else if (e.getComponent() == button[31]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("4++");
                new Audio("audio/hh4.mp3").start();
                e.getComponent().setBackground(colorhh);
            } else if (e.getComponent() == button[32]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("5++");
                new Audio("audio/hh5.mp3").start();
                e.getComponent().setBackground(colorhh);
            } else if (e.getComponent() == button[33]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("6++");
                new Audio("audio/hh6.mp3").start();
                e.getComponent().setBackground(colorhh);
            } else if (e.getComponent() == button[34]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("7++");
                new Audio("audio/hh7.mp3").start();
                e.getComponent().setBackground(colorhh);
            }
            // 第一排键位
            else if (e.getComponent() == button1[1]) {
                label_notes.setForeground(colorh);
                label_notes.setText("1+");
                new Audio("audio/h1.mp3").start();
                e.getComponent().setBackground(colorh);
                button[21].setBackground(colorh);
            } else if (e.getComponent() == button1[2]) {
                label_notes.setForeground(colorh);
                label_notes.setText("2+");
                new Audio("audio/h2.mp3").start();
                e.getComponent().setBackground(colorh);
                button[22].setBackground(colorh);
            } else if (e.getComponent() == button1[3]) {
                label_notes.setForeground(colorh);
                label_notes.setText("3+");
                new Audio("audio/h3.mp3").start();
                e.getComponent().setBackground(colorh);
                button[23].setBackground(colorh);
            } else if (e.getComponent() == button1[4]) {
                label_notes.setForeground(colorh);
                label_notes.setText("4+");
                new Audio("audio/h4.mp3").start();
                e.getComponent().setBackground(colorh);
                button[24].setBackground(colorh);
            } else if (e.getComponent() == button1[5]) {
                label_notes.setForeground(colorh);
                label_notes.setText("5+");
                new Audio("audio/h5.mp3").start();
                e.getComponent().setBackground(colorh);
                button[25].setBackground(colorh);
            } else if (e.getComponent() == button1[6]) {
                label_notes.setForeground(colorh);
                label_notes.setText("6+");
                new Audio("audio/h6.mp3").start();
                e.getComponent().setBackground(colorh);
                button[26].setBackground(colorh);
            } else if (e.getComponent() == button1[7]) {
                label_notes.setForeground(colorh);
                label_notes.setText("7+");
                new Audio("audio/h7.mp3").start();
                e.getComponent().setBackground(colorh);
                button[27].setBackground(colorh);
            } else if (e.getComponent() == button1[8]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("1++");
                new Audio("audio/hh1.mp3").start();
                e.getComponent().setBackground(colorhh);
                button[28].setBackground(colorhh);
            } else if (e.getComponent() == button1[9]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("2++");
                new Audio("audio/hh2.mp3").start();
                e.getComponent().setBackground(colorhh);
                button[29].setBackground(colorhh);
            } else if (e.getComponent() == button1[10]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("3++");
                new Audio("audio/hh3.mp3").start();
                e.getComponent().setBackground(colorhh);
                button[30].setBackground(colorhh);
            } else if (e.getComponent() == button1[11]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("4++");
                new Audio("audio/hh4.mp3").start();
                e.getComponent().setBackground(colorhh);
                button[31].setBackground(colorhh);
            } else if (e.getComponent() == button1[12]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("5++");
                new Audio("audio/hh5.mp3").start();
                e.getComponent().setBackground(colorhh);
                button[32].setBackground(colorhh);
            } else if (e.getComponent() == button1[13]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("6++");
                new Audio("audio/hh6.mp3").start();
                e.getComponent().setBackground(colorhh);
                button[33].setBackground(colorhh);
            } else if (e.getComponent() == button1[14]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("4++");
                new Audio("audio/hh4.mp3").start();
                e.getComponent().setBackground(colorhh);
                button[31].setBackground(colorhh);
            } else if (e.getComponent() == button1[15]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("5++");
                new Audio("audio/hh5.mp3").start();
                e.getComponent().setBackground(colorhh);
                button[32].setBackground(colorhh);
            } else if (e.getComponent() == button1[16]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("6++");
                new Audio("audio/hh6.mp3").start();
                e.getComponent().setBackground(colorhh);
                button[33].setBackground(colorhh);
            } else if (e.getComponent() == button1[17]) {
                label_notes.setForeground(colorh);
                label_notes.setText("4+");
                new Audio("audio/h4.mp3").start();
                e.getComponent().setBackground(colorh);
                button[24].setBackground(colorh);
            } else if (e.getComponent() == button1[18]) {
                label_notes.setForeground(colorh);
                label_notes.setText("5+");
                new Audio("audio/h5.mp3").start();
                e.getComponent().setBackground(colorh);
                button[25].setBackground(colorh);
            } else if (e.getComponent() == button1[19]) {
                label_notes.setForeground(colorh);
                label_notes.setText("6+");
                new Audio("audio/h6.mp3").start();
                e.getComponent().setBackground(colorh);
                button[26].setBackground(colorh);
            } else if (e.getComponent() == button1[20]) {
                label_notes.setForeground(colorh);
                label_notes.setText("7+");
                new Audio("audio/h7.mp3").start();
                e.getComponent().setBackground(colorh);
                button[27].setBackground(colorh);
            }
            // 第二排键位
            else if (e.getComponent() == button2[1]) {
                label_notes.setForeground(colorm);
                label_notes.setText("1");
                new Audio("audio/m1.mp3").start();
                e.getComponent().setBackground(colorm);
                button[14].setBackground(colorm);
            } else if (e.getComponent() == button2[2]) {
                label_notes.setForeground(colorm);
                label_notes.setText("2");
                new Audio("audio/m2.mp3").start();
                e.getComponent().setBackground(colorm);
                button[15].setBackground(colorm);
            } else if (e.getComponent() == button2[3]) {
                label_notes.setForeground(colorm);
                label_notes.setText("3");
                new Audio("audio/m3.mp3").start();
                e.getComponent().setBackground(colorm);
                button[16].setBackground(colorm);
            } else if (e.getComponent() == button2[4]) {
                label_notes.setForeground(colorm);
                label_notes.setText("4");
                new Audio("audio/m4.mp3").start();
                e.getComponent().setBackground(colorm);
                button[17].setBackground(colorm);
            } else if (e.getComponent() == button2[5]) {
                label_notes.setForeground(colorm);
                label_notes.setText("5");
                new Audio("audio/m5.mp3").start();
                e.getComponent().setBackground(colorm);
                button[18].setBackground(colorm);
            } else if (e.getComponent() == button2[6]) {
                label_notes.setForeground(colorm);
                label_notes.setText("6");
                new Audio("audio/m6.mp3").start();
                e.getComponent().setBackground(colorm);
                button[19].setBackground(colorm);
            } else if (e.getComponent() == button2[7]) {
                label_notes.setForeground(colorm);
                label_notes.setText("7");
                new Audio("audio/m7.mp3").start();
                e.getComponent().setBackground(colorm);
                button[20].setBackground(colorm);
            } else if (e.getComponent() == button2[8]) {
                label_notes.setForeground(colorh);
                label_notes.setText("1+");
                new Audio("audio/h1.mp3").start();
                e.getComponent().setBackground(colorh);
                button[21].setBackground(colorh);
            } else if (e.getComponent() == button2[9]) {
                label_notes.setForeground(colorh);
                label_notes.setText("2+");
                new Audio("audio/h2.mp3").start();
                e.getComponent().setBackground(colorh);
                button[22].setBackground(colorh);
            } else if (e.getComponent() == button2[10]) {
                label_notes.setForeground(colorh);
                label_notes.setText("3+");
                new Audio("audio/h3.mp3").start();
                e.getComponent().setBackground(colorh);
                button[23].setBackground(colorh);
            } else if (e.getComponent() == button2[11]) {
                label_notes.setForeground(colorh);
                label_notes.setText("4+");
                new Audio("audio/h4.mp3").start();
                e.getComponent().setBackground(colorh);
                button[24].setBackground(colorh);
            } else if (e.getComponent() == button2[12]) {
                label_notes.setForeground(colorh);
                label_notes.setText("5+");
                new Audio("audio/h5.mp3").start();
                e.getComponent().setBackground(colorh);
                button[25].setBackground(colorh);
            } else if (e.getComponent() == button2[14]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("1++");
                new Audio("audio/hh1.mp3").start();
                e.getComponent().setBackground(colorhh);
                button[28].setBackground(colorhh);
            } else if (e.getComponent() == button2[15]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("2++");
                new Audio("audio/hh2.mp3").start();
                e.getComponent().setBackground(colorhh);
                button[29].setBackground(colorhh);
            } else if (e.getComponent() == button2[16]) {
                label_notes.setForeground(colorhh);
                label_notes.setText("3++");
                new Audio("audio/hh3.mp3").start();
                e.getComponent().setBackground(colorhh);
                button[30].setBackground(colorhh);
            } else if (e.getComponent() == button2[17]) {
                label_notes.setForeground(colorm);
                label_notes.setText("7");
                new Audio("audio/m7.mp3").start();
                e.getComponent().setBackground(colorm);
                button[20].setBackground(colorm);
            } else if (e.getComponent() == button2[18]) {
                label_notes.setForeground(colorh);
                label_notes.setText("1+");
                new Audio("audio/h1.mp3").start();
                e.getComponent().setBackground(colorh);
                button[21].setBackground(colorh);
            } else if (e.getComponent() == button2[19]) {
                label_notes.setForeground(colorh);
                label_notes.setText("2+");
                new Audio("audio/h2.mp3").start();
                e.getComponent().setBackground(colorh);
                button[22].setBackground(colorh);
            } else if (e.getComponent() == button2[20]) {
                label_notes.setForeground(colorh);
                label_notes.setText("3+");
                new Audio("audio/h3.mp3").start();
                e.getComponent().setBackground(colorh);
                button[23].setBackground(colorh);
            }
            // 第三排键位
            else if (e.getComponent() == button3[1]) {
                label_notes.setForeground(colorl);
                label_notes.setText("1-");
                new Audio("audio/l1.mp3").start();
                e.getComponent().setBackground(colorl);
                button[7].setBackground(colorl);
            } else if (e.getComponent() == button3[2]) {
                label_notes.setForeground(colorl);
                label_notes.setText("2-");
                new Audio("audio/l2.mp3").start();
                e.getComponent().setBackground(colorl);
                button[8].setBackground(colorl);
            } else if (e.getComponent() == button3[3]) {
                label_notes.setForeground(colorl);
                label_notes.setText("3-");
                new Audio("audio/l3.mp3").start();
                e.getComponent().setBackground(colorl);
                button[9].setBackground(colorl);
            } else if (e.getComponent() == button3[4]) {
                label_notes.setForeground(colorl);
                label_notes.setText("4-");
                new Audio("audio/l4.mp3").start();
                e.getComponent().setBackground(colorl);
                button[10].setBackground(colorl);
            } else if (e.getComponent() == button3[5]) {
                label_notes.setForeground(colorl);
                label_notes.setText("5-");
                new Audio("audio/l5.mp3").start();
                e.getComponent().setBackground(colorl);
                button[11].setBackground(colorl);
            } else if (e.getComponent() == button3[6]) {
                label_notes.setForeground(colorl);
                label_notes.setText("6-");
                new Audio("audio/l6.mp3").start();
                e.getComponent().setBackground(colorl);
                button[12].setBackground(colorl);
            } else if (e.getComponent() == button3[7]) {
                label_notes.setForeground(colorl);
                label_notes.setText("7-");
                new Audio("audio/l7.mp3").start();
                e.getComponent().setBackground(colorl);
                button[13].setBackground(colorl);
            } else if (e.getComponent() == button3[8]) {
                label_notes.setForeground(colorm);
                label_notes.setText("1");
                new Audio("audio/m1.mp3").start();
                e.getComponent().setBackground(colorm);
                button[14].setBackground(colorm);
            } else if (e.getComponent() == button3[9]) {
                label_notes.setForeground(colorm);
                label_notes.setText("2");
                new Audio("audio/m2.mp3").start();
                e.getComponent().setBackground(colorm);
                button[15].setBackground(colorm);
            } else if (e.getComponent() == button3[10]) {
                label_notes.setForeground(colorm);
                label_notes.setText("3");
                new Audio("audio/m3.mp3").start();
                e.getComponent().setBackground(colorm);
                button[16].setBackground(colorm);
            } else if (e.getComponent() == button3[11]) {
                label_notes.setForeground(colorm);
                label_notes.setText("4");
                new Audio("audio/m4.mp3").start();
                e.getComponent().setBackground(colorm);
                button[17].setBackground(colorm);
            } else if (e.getComponent() == button3[13]) {
                label_notes.setForeground(colorm);
                label_notes.setText("4");
                new Audio("audio/m4.mp3").start();
                e.getComponent().setBackground(colorm);
                button[17].setBackground(colorm);
            } else if (e.getComponent() == button3[14]) {
                label_notes.setForeground(colorm);
                label_notes.setText("5");
                new Audio("audio/m5.mp3").start();
                e.getComponent().setBackground(colorm);
                button[18].setBackground(colorm);
            } else if (e.getComponent() == button3[15]) {
                label_notes.setForeground(colorm);
                label_notes.setText("6");
                new Audio("audio/m6.mp3").start();
                e.getComponent().setBackground(colorm);
                button[19].setBackground(colorm);
            }
            // 第四排键位
            else if (e.getComponent() == button4[1]) {
                label_notes.setForeground(colorll);
                label_notes.setText("1--");
                new Audio("audio/ll1.mp3").start();
                e.getComponent().setBackground(colorll);
                button[0].setBackground(colorll);
            } else if (e.getComponent() == button4[2]) {
                label_notes.setForeground(colorll);
                label_notes.setText("2--");
                new Audio("audio/ll2.mp3").start();
                e.getComponent().setBackground(colorll);
                button[1].setBackground(colorll);
            } else if (e.getComponent() == button4[3]) {
                label_notes.setForeground(colorll);
                label_notes.setText("3--");
                new Audio("audio/ll3.mp3").start();
                e.getComponent().setBackground(colorll);
                button[2].setBackground(colorll);
            } else if (e.getComponent() == button4[4]) {
                label_notes.setForeground(colorll);
                label_notes.setText("4--");
                new Audio("audio/ll4.mp3").start();
                e.getComponent().setBackground(colorll);
                button[3].setBackground(colorll);
            } else if (e.getComponent() == button4[5]) {
                label_notes.setForeground(colorll);
                label_notes.setText("5--");
                new Audio("audio/ll5.mp3").start();
                e.getComponent().setBackground(colorll);
                button[4].setBackground(colorll);
            } else if (e.getComponent() == button4[6]) {
                label_notes.setForeground(colorll);
                label_notes.setText("6--");
                new Audio("audio/ll6.mp3").start();
                e.getComponent().setBackground(colorll);
                button[5].setBackground(colorll);
            } else if (e.getComponent() == button4[7]) {
                label_notes.setForeground(colorll);
                label_notes.setText("7--");
                new Audio("audio/ll7.mp3").start();
                e.getComponent().setBackground(colorll);
                button[6].setBackground(colorll);
            } else if (e.getComponent() == button4[8]) {
                label_notes.setForeground(colorl);
                label_notes.setText("1-");
                new Audio("audio/l1.mp3").start();
                e.getComponent().setBackground(colorl);
                button[7].setBackground(colorl);
            } else if (e.getComponent() == button4[9]) {
                label_notes.setForeground(colorl);
                label_notes.setText("2-");
                new Audio("audio/l2.mp3").start();
                e.getComponent().setBackground(colorl);
                button[8].setBackground(colorl);
            } else if (e.getComponent() == button4[10]) {
                label_notes.setForeground(colorl);
                label_notes.setText("3-");
                new Audio("audio/l3.mp3").start();
                e.getComponent().setBackground(colorl);
                button[9].setBackground(colorl);
            } else if (e.getComponent() == button4[12]) {
                label_notes.setForeground(colorl);
                label_notes.setText("4-");
                new Audio("audio/l4.mp3").start();
                e.getComponent().setBackground(colorl);
                button[10].setBackground(colorl);
            } else if (e.getComponent() == button4[13]) {
                label_notes.setForeground(colorm);
                label_notes.setText("1");
                new Audio("audio/m1.mp3").start();
                e.getComponent().setBackground(colorm);
                button[14].setBackground(colorm);
            } else if (e.getComponent() == button4[14]) {
                label_notes.setForeground(colorm);
                label_notes.setText("2");
                new Audio("audio/m2.mp3").start();
                e.getComponent().setBackground(colorm);
                button[15].setBackground(colorm);
            } else if (e.getComponent() == button4[15]) {
                label_notes.setForeground(colorm);
                label_notes.setText("3");
                new Audio("audio/m3.mp3").start();
                e.getComponent().setBackground(colorm);
                button[16].setBackground(colorm);
            } else if (e.getComponent() == button4[16]) {
                label_notes.setForeground(colorl);
                label_notes.setText("7-");
                new Audio("audio/l7.mp3").start();
                e.getComponent().setBackground(colorl);
                button[13].setBackground(colorl);
            }
            // 第五排键位
            else if (e.getComponent() == button5[8]) {
                label_notes.setForeground(colorl);
                label_notes.setText("1-");
                new Audio("audio/l1.mp3").start();
                e.getComponent().setBackground(colorl);
                button[7].setBackground(colorl);
            } else if (e.getComponent() == button5[9]) {
                label_notes.setForeground(colorl);
                label_notes.setText("2-");
                new Audio("audio/l2.mp3").start();
                e.getComponent().setBackground(colorl);
                button[8].setBackground(colorl);
            } else if (e.getComponent() == button5[10]) {
                label_notes.setForeground(colorl);
                label_notes.setText("3-");
                new Audio("audio/l3.mp3").start();
                e.getComponent().setBackground(colorl);
                button[9].setBackground(colorl);
            } else if (e.getComponent() == button5[11]) {
                label_notes.setForeground(colorl);
                label_notes.setText("5-");
                new Audio("audio/l5.mp3").start();
                e.getComponent().setBackground(colorl);
                button[11].setBackground(colorl);
            } else if (e.getComponent() == button5[12]) {
                label_notes.setForeground(colorl);
                label_notes.setText("6-");
                new Audio("audio/l6.mp3").start();
                e.getComponent().setBackground(colorl);
                button[12].setBackground(colorl);
            }
            repaint();
        }

        // 鼠标点击
        @Override
        public void mouseClicked(MouseEvent e) {
            repaint();
        }

        // 鼠标释放
        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getComponent() == slider_progress) {
                Piano.this.slider_progressIsPress = false;
            }

            if (e.getComponent() != button1[0]
                    && e.getComponent() != button2[0]
                    && e.getComponent() != button2[13]
                    && e.getComponent() != button3[0]
                    && e.getComponent() != button3[12]
                    && e.getComponent() != button4[0]
                    && e.getComponent() != button4[11]
                    && e.getComponent() != button5[0]
                    && e.getComponent() != button5[1]
                    && e.getComponent() != button5[2]
                    && e.getComponent() != button5[3]
                    && e.getComponent() != button5[4]
                    && e.getComponent() != button5[5]
                    && e.getComponent() != button5[6]
                    && e.getComponent() != button5[7]
                    && e.getComponent() != slider_progress) {
                e.getComponent().setBackground(color);
            }
            for (JButton temp : button) {
                if (e.getComponent() == temp) {
                    label_notes.setText("");
                }
            }
            // 第一排键位
            if (e.getComponent() == button1[1]) {
                label_notes.setText("");
                button[21].setBackground(color);
            }
            if (e.getComponent() == button1[2]) {
                label_notes.setText("");
                button[22].setBackground(color);
            }
            if (e.getComponent() == button1[3]) {
                label_notes.setText("");
                button[23].setBackground(color);
            }
            if (e.getComponent() == button1[4]) {
                label_notes.setText("");
                button[24].setBackground(color);
            }
            if (e.getComponent() == button1[5]) {
                label_notes.setText("");
                button[25].setBackground(color);
            }
            if (e.getComponent() == button1[6]) {
                label_notes.setText("");
                button[26].setBackground(color);
            }
            if (e.getComponent() == button1[7]) {
                label_notes.setText("");
                button[27].setBackground(color);
            }
            if (e.getComponent() == button1[8]) {
                label_notes.setText("");
                button[28].setBackground(color);
            }
            if (e.getComponent() == button1[9]) {
                label_notes.setText("");
                button[29].setBackground(color);
            }
            if (e.getComponent() == button1[10]) {
                label_notes.setText("");
                button[30].setBackground(color);
            }
            if (e.getComponent() == button1[11]) {
                label_notes.setText("");
                button[31].setBackground(color);
            }
            if (e.getComponent() == button1[12]) {
                label_notes.setText("");
                button[32].setBackground(color);
            }
            if (e.getComponent() == button1[13]) {
                label_notes.setText("");
                button[33].setBackground(color);
            }
            if (e.getComponent() == button1[14]) {
                label_notes.setText("");
                button[31].setBackground(color);
            }
            if (e.getComponent() == button1[15]) {
                label_notes.setText("");
                button[32].setBackground(color);
            }
            if (e.getComponent() == button1[16]) {
                label_notes.setText("");
                button[33].setBackground(color);
            }
            if (e.getComponent() == button1[17]) {
                label_notes.setText("");
                button[24].setBackground(color);
            }
            if (e.getComponent() == button1[18]) {
                label_notes.setText("");
                button[25].setBackground(color);
            }
            if (e.getComponent() == button1[19]) {
                label_notes.setText("");
                button[26].setBackground(color);
            }
            if (e.getComponent() == button1[20]) {
                label_notes.setText("");
                button[27].setBackground(color);
            }
            // 第二排键位
            if (e.getComponent() == button2[1]) {
                label_notes.setText("");
                button[14].setBackground(color);
            }
            if (e.getComponent() == button2[2]) {
                label_notes.setText("");
                button[15].setBackground(color);
            }
            if (e.getComponent() == button2[3]) {
                label_notes.setText("");
                button[16].setBackground(color);
            }
            if (e.getComponent() == button2[4]) {
                label_notes.setText("");
                button[17].setBackground(color);
            }
            if (e.getComponent() == button2[5]) {
                label_notes.setText("");
                button[18].setBackground(color);
            }
            if (e.getComponent() == button2[6]) {
                label_notes.setText("");
                button[19].setBackground(color);
            }
            if (e.getComponent() == button2[7]) {
                label_notes.setText("");
                button[20].setBackground(color);
            }
            if (e.getComponent() == button2[8]) {
                label_notes.setText("");
                button[21].setBackground(color);
            }
            if (e.getComponent() == button2[9]) {
                label_notes.setText("");
                button[22].setBackground(color);
            }
            if (e.getComponent() == button2[10]) {
                label_notes.setText("");
                button[23].setBackground(color);
            }
            if (e.getComponent() == button2[11]) {
                label_notes.setText("");
                button[24].setBackground(color);
            }
            if (e.getComponent() == button2[12]) {
                label_notes.setText("");
                button[25].setBackground(color);
            }
            if (e.getComponent() == button2[14]) {
                label_notes.setText("");
                button[28].setBackground(color);
            }
            if (e.getComponent() == button2[15]) {
                label_notes.setText("");
                button[29].setBackground(color);
            }
            if (e.getComponent() == button2[16]) {
                label_notes.setText("");
                button[30].setBackground(color);
            }
            if (e.getComponent() == button2[17]) {
                label_notes.setText("");
                button[20].setBackground(color);
            }
            if (e.getComponent() == button2[18]) {
                label_notes.setText("");
                button[21].setBackground(color);
            }
            if (e.getComponent() == button2[19]) {
                label_notes.setText("");
                button[22].setBackground(color);
            }
            if (e.getComponent() == button2[20]) {
                label_notes.setText("");
                button[23].setBackground(color);
            }
            // 第三排键位
            if (e.getComponent() == button3[1]) {
                label_notes.setText("");
                button[7].setBackground(color);
            }
            if (e.getComponent() == button3[2]) {
                label_notes.setText("");
                button[8].setBackground(color);
            }
            if (e.getComponent() == button3[3]) {
                label_notes.setText("");
                button[9].setBackground(color);
            }
            if (e.getComponent() == button3[4]) {
                label_notes.setText("");
                button[10].setBackground(color);
            }
            if (e.getComponent() == button3[5]) {
                label_notes.setText("");
                button[11].setBackground(color);
            }
            if (e.getComponent() == button3[6]) {
                label_notes.setText("");
                button[12].setBackground(color);
            }
            if (e.getComponent() == button3[7]) {
                label_notes.setText("");
                button[13].setBackground(color);
            }
            if (e.getComponent() == button3[8]) {
                label_notes.setText("");
                button[14].setBackground(color);
            }
            if (e.getComponent() == button3[9]) {
                label_notes.setText("");
                button[15].setBackground(color);
            }
            if (e.getComponent() == button3[10]) {
                label_notes.setText("");
                button[16].setBackground(color);
            }
            if (e.getComponent() == button3[11]) {
                label_notes.setText("");
                button[17].setBackground(color);
            }
            if (e.getComponent() == button3[13]) {
                label_notes.setText("");
                button[17].setBackground(color);
            }
            if (e.getComponent() == button3[14]) {
                label_notes.setText("");
                button[18].setBackground(color);
            }
            if (e.getComponent() == button3[15]) {
                label_notes.setText("");
                button[19].setBackground(color);
            }
            // 第四排键位
            if (e.getComponent() == button4[1]) {
                label_notes.setText("");
                button[0].setBackground(color);
            }
            if (e.getComponent() == button4[2]) {
                label_notes.setText("");
                button[1].setBackground(color);
            }
            if (e.getComponent() == button4[3]) {
                label_notes.setText("");
                button[2].setBackground(color);
            }
            if (e.getComponent() == button4[4]) {
                label_notes.setText("");
                button[3].setBackground(color);
            }
            if (e.getComponent() == button4[5]) {
                label_notes.setText("");
                button[4].setBackground(color);
            }
            if (e.getComponent() == button4[6]) {
                label_notes.setText("");
                button[5].setBackground(color);
            }
            if (e.getComponent() == button4[7]) {
                label_notes.setText("");
                button[6].setBackground(color);
            }
            if (e.getComponent() == button4[8]) {
                label_notes.setText("");
                button[7].setBackground(color);
            }
            if (e.getComponent() == button4[9]) {
                label_notes.setText("");
                button[8].setBackground(color);
            }
            if (e.getComponent() == button4[10]) {
                label_notes.setText("");
                button[9].setBackground(color);
            }
            if (e.getComponent() == button4[12]) {
                label_notes.setText("");
                button[10].setBackground(color);
            }
            if (e.getComponent() == button4[13]) {
                label_notes.setText("");
                button[14].setBackground(color);
            }
            if (e.getComponent() == button4[14]) {
                label_notes.setText("");
                button[15].setBackground(color);
            }
            if (e.getComponent() == button4[15]) {
                label_notes.setText("");
                button[16].setBackground(color);
            }
            if (e.getComponent() == button4[16]) {
                label_notes.setText("");
                button[13].setBackground(color);
            }
            // 第五排键位
            if (e.getComponent() == button5[8]) {
                label_notes.setText("");
                button[7].setBackground(color);
            }
            if (e.getComponent() == button5[9]) {
                label_notes.setText("");
                button[8].setBackground(color);
            }
            if (e.getComponent() == button5[10]) {
                label_notes.setText("");
                button[9].setBackground(color);
            }
            if (e.getComponent() == button5[11]) {
                label_notes.setText("");
                button[11].setBackground(color);
            }
            if (e.getComponent() == button5[12]) {
                label_notes.setText("");
                button[12].setBackground(color);
            }
            repaint();

        }

        // 鼠标进入组件
        @Override
        public void mouseEntered(MouseEvent e) {
            // 判断鼠标是否被按下
            if (e.getModifiers() == MouseEvent.BUTTON1_MASK ||
                    e.getModifiers() == MouseEvent.BUTTON2_MASK ||
                    e.getModifiers() == MouseEvent.BUTTON3_MASK)
                mousePressed(e);
            else {
                if (e.getComponent() != button1[0]
                        && e.getComponent() != button2[0]
                        && e.getComponent() != button2[13]
                        && e.getComponent() != button3[0]
                        && e.getComponent() != button3[12]
                        && e.getComponent() != button4[0]
                        && e.getComponent() != button4[11]
                        && e.getComponent() != button5[0]
                        && e.getComponent() != button5[1]
                        && e.getComponent() != button5[2]
                        && e.getComponent() != button5[3]
                        && e.getComponent() != button5[4]
                        && e.getComponent() != button5[5]
                        && e.getComponent() != button5[6]
                        && e.getComponent() != button5[7]
                        && e.getComponent() != slider_progress) {
                    e.getComponent().setBackground(new Color(220, 220, 220));
                }
            }
            repaint();
        }

        // 鼠标退出组件
        @Override
        public void mouseExited(MouseEvent e) {
            mouseReleased(e);
            repaint();

        }

    }

    class Win extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {// 关闭程序弹出对话框
            if (file.exists())
                try {
                    FileInputStream fis = new FileInputStream(file);
                    byte[] byt = new byte[256];
                    int len = fis.read(byt);
                    read = new String(byt, 0, len);
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            if (newNotes.getIsSave()) {
                int index = JOptionPane.showOptionDialog(Piano.this,
                        "检测到您自制简谱的最后一次更改没有保存\n                 是否执意退出？", "警告",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
                if (index == 0) {
                    try {
                        char[] ch = read.toCharArray();
                        if (radioMenuItem_model1.isSelected()) {
                            ch[read.indexOf("model = ") + "model = ".length()] = '1';
                        } else if (radioMenuItem_model2.isSelected()) {
                            ch[read.indexOf("model = ") + "model = ".length()] = '2';
                        }
                        String read_new = new String(ch);
                        if (newNotes.getTextSize() != 0)
                            read_new = read.replaceAll(read_new.substring
                                                    (read.indexOf("size = ") + "size = ".length(),
                                                    read.indexOf("\n", read.indexOf("size = ") + "size = ".length())),
                                    String.valueOf(newNotes.getTextSize()));
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(read_new.getBytes());
                        fos.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.exit(0);
                }
            } else {
                try {
                    char[] ch = read.toCharArray();
                    if (radioMenuItem_model1.isSelected()) {
                        ch[read.indexOf("model = ") + "model = "
                                .length()] = '1';
                    } else if (radioMenuItem_model2.isSelected()) {
                        ch[read.indexOf("model = ") + "model = "
                                .length()] = '2';
                    }
                    String read_new = new String(ch);
                    if (newNotes.getTextSize() != 0)
                        read_new = read.replaceAll(read_new
                                        .substring(Piano.this
                                                .read.indexOf("size = ") + "size = "
                                                .length(), Piano.this
                                                .read.indexOf("\n", Piano.this
                                                        .read.indexOf("size = ") + "size = "
                                                        .length())),
                                String.valueOf(newNotes.getTextSize()));
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(read_new.getBytes());
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            }

        }

    }

    public class PlayMode extends Thread {// 自动弹奏类
        private String[] notes;
        private volatile boolean isStop = false;
        private volatile boolean isSuspend = false;
        private volatile boolean isForward = false;
        private volatile boolean isBackward = false;
        private int times;
        private volatile int index_now;// 当前音符的位置

        private Color color = Color.white;
        private Color colorll = new Color(0, 50, 200);
        private Color colorl = new Color(0, 100, 150);
        private Color colorm = new Color(0, 150, 100);
        private Color colorh = new Color(0, 200, 50);
        private Color colorhh = new Color(0, 255, 0);

        public void setNotes(String notes, int times) {
            this.notes = notes.split(" ");
            this.times = times;
        }


        public void setStop() {
            isStop = true;
        }

        public void setSuspend(boolean isSuspend) {
            this.isSuspend = isSuspend;
        }

        public void setForward() {
            this.isForward = true;
        }

        public void setBackward() {
            this.isBackward = true;
        }

        public void setIndex(int index_now) {
            this.index_now = index_now;
        }

        @Override
        public void run() {
            this.index_now = 0;
            int times = this.times;
            isStop = false;
            try {
                new Audio("audio/test.mp3").start();
                sleep(1000);
                for (int i = 0; i < notes.length; i++, this.index_now++) {
                    if (i != (this.index_now)) {
                        if (this.index_now >= notes.length) {
                            this.index_now = notes.length - 1;
                        }
                        i = this.index_now;
                    }
                    if (isSuspend) {// 暂停
                        while (isSuspend) {
                            if (isForward) {
                                i += 10;
                                this.index_now += 10;
                                if (i >= notes.length - 1) {
                                    i = notes.length - 1;
                                    this.index_now = notes.length - 1;
                                }
                                isForward = false;
                            }
                            if (isBackward) {
                                i -= 10;
                                this.index_now -= 10;
                                if (i <= 0) {
                                    i = 0;
                                    this.index_now = 0;
                                }
                                isBackward = false;
                            }
                            if (isStop) {
                                slider_progress.setValue(notes.length);
                                label_index
                                        .setText(String.valueOf(notes.length));
                                label_index.setEnabled(false);
                                label_accompaniments.setEnabled(false);
                                menuItem_reset.setEnabled(true);
                                break;
                            }
                            if (i != (this.index_now)) {
                                if (this.index_now >= notes.length) {
                                    this.index_now = notes.length - 1;
                                }
                                i = this.index_now;
                            }
                            slider_progress.setValue(i + 1);
                            label_index.setText(String.valueOf(i + 1));
                        }
                    }
                    if (isForward) {// 前进
                        i += 10;
                        this.index_now += 10;
                        if (i >= notes.length - 1) {
                            i = notes.length - 1;
                            this.index_now = notes.length - 1;
                        }
                        isForward = false;
                    }
                    if (isBackward) {// 倒退
                        i -= 10;
                        this.index_now -= 10;
                        if (i <= 0) {
                            i = 0;
                            this.index_now = 0;
                        }
                        isBackward = false;
                    }
                    if (isStop) {// 停止
                        slider_progress.setValue(notes.length);
                        label_index.setText(String.valueOf(notes.length));
                        label_index.setEnabled(false);
                        label_accompaniments.setEnabled(false);
                        menuItem_reset.setEnabled(true);
                        break;
                    }
                    slider_progress.setValue(i + 1);
                    label_index.setText(String.valueOf(i + 1));
                    if (getName().equals("主奏线程")) {
                        if (notes[i].equals("1--")
                                || notes[i].equals("2--")
                                || notes[i].equals("3--")
                                || notes[i].equals("4--")
                                || notes[i].equals("5--")
                                || notes[i].equals("6--")
                                || notes[i].equals("7--")) {
                            label_notes.setForeground(colorll);
                        } else if (notes[i].equals("1-")
                                || notes[i].equals("2-")
                                || notes[i].equals("3-")
                                || notes[i].equals("4-")
                                || notes[i].equals("5-")
                                || notes[i].equals("6-")
                                || notes[i].equals("7-")) {
                            label_notes.setForeground(colorl);
                        } else if (notes[i].equals("1") || notes[i].equals("2")
                                || notes[i].equals("3") || notes[i].equals("4")
                                || notes[i].equals("5") || notes[i].equals("6")
                                || notes[i].equals("7")) {
                            label_notes.setForeground(colorm);
                        } else if (notes[i].equals("1+")
                                || notes[i].equals("2+")
                                || notes[i].equals("3+")
                                || notes[i].equals("4+")
                                || notes[i].equals("5+")
                                || notes[i].equals("6+")
                                || notes[i].equals("7+")) {
                            label_notes.setForeground(colorh);
                        } else if (notes[i].equals("1++")
                                || notes[i].equals("2++")
                                || notes[i].equals("3++")
                                || notes[i].equals("4++")
                                || notes[i].equals("5++")
                                || notes[i].equals("6++")
                                || notes[i].equals("7++")) {
                            label_notes.setForeground(colorhh);
                        }
                        label_notes.setText(notes[i]);
                    } else if (getName().equals("伴奏线程")) {
                        if (notes[i].equals("1--") || notes[i].equals("2--")
                                || notes[i].equals("3--")
                                || notes[i].equals("4--")
                                || notes[i].equals("5--")
                                || notes[i].equals("6--")
                                || notes[i].equals("7--")) {
                            label_accompaniments.setForeground(colorll);
                        } else if (notes[i].equals("1-")
                                || notes[i].equals("2-")
                                || notes[i].equals("3-")
                                || notes[i].equals("4-")
                                || notes[i].equals("5-")
                                || notes[i].equals("6-")
                                || notes[i].equals("7-")) {
                            label_accompaniments.setForeground(colorl);
                        } else if (notes[i].equals("1") || notes[i].equals("2")
                                || notes[i].equals("3") || notes[i].equals("4")
                                || notes[i].equals("5") || notes[i].equals("6")
                                || notes[i].equals("7")) {
                            label_accompaniments.setForeground(colorm);
                        } else if (notes[i].equals("1+")
                                || notes[i].equals("2+")
                                || notes[i].equals("3+")
                                || notes[i].equals("4+")
                                || notes[i].equals("5+")
                                || notes[i].equals("6+")
                                || notes[i].equals("7+")) {
                            label_accompaniments.setForeground(colorh);
                        } else if (notes[i].equals("1++")
                                || notes[i].equals("2++")
                                || notes[i].equals("3++")
                                || notes[i].equals("4++")
                                || notes[i].equals("5++")
                                || notes[i].equals("6++")
                                || notes[i].equals("7++")) {
                            label_accompaniments.setForeground(colorhh);
                        }
                        label_accompaniments.setText(notes[i]);
                    }
                    if (radioMenuItem_model1.isSelected()) {
                        if (this.getName().equals("主奏线程")) {
                            switch (notes[i]) {
                                case "1--":
                                    new Audio("audio/ll1.mp3").start();
                                    button[0].setBackground(colorll);
                                    button4[1].setBackground(colorll);
                                    repaint();
                                    sleep(times / 2);
                                    button[0].setBackground(color);
                                    button4[1].setBackground(color);
                                    break;
                                case "2--":
                                    new Audio("audio/ll2.mp3").start();
                                    button[1].setBackground(colorll);
                                    button4[2].setBackground(colorll);
                                    repaint();
                                    sleep(times / 2);
                                    button[1].setBackground(color);
                                    button4[2].setBackground(color);
                                    break;
                                case "3--":
                                    new Audio("audio/ll3.mp3").start();
                                    button[2].setBackground(colorll);
                                    button4[3].setBackground(colorll);
                                    repaint();
                                    sleep(times / 2);
                                    button[2].setBackground(color);
                                    button4[3].setBackground(color);
                                    break;
                                case "4--":
                                    new Audio("audio/ll4.mp3").start();
                                    button[3].setBackground(colorll);
                                    button4[4].setBackground(colorll);
                                    repaint();
                                    sleep(times / 2);
                                    button[3].setBackground(color);
                                    button4[4].setBackground(color);
                                    break;
                                case "5--":
                                    new Audio("audio/ll5.mp3").start();
                                    button[4].setBackground(colorll);
                                    button4[5].setBackground(colorll);
                                    repaint();
                                    sleep(times / 2);
                                    button[4].setBackground(color);
                                    button4[5].setBackground(color);
                                    break;
                                case "6--":
                                    new Audio("audio/ll6.mp3").start();
                                    button[5].setBackground(colorll);
                                    button4[6].setBackground(colorll);
                                    repaint();
                                    sleep(times / 2);
                                    button[5].setBackground(color);
                                    button4[6].setBackground(color);
                                    break;
                                case "7--":
                                    new Audio("audio/ll7.mp3").start();
                                    button[6].setBackground(colorll);
                                    button4[7].setBackground(colorll);
                                    repaint();
                                    sleep(times / 2);
                                    button[6].setBackground(color);
                                    button4[7].setBackground(color);
                                    break;
                                case "1-":
                                    new Audio("audio/l1.mp3").start();
                                    button[7].setBackground(colorl);
                                    button5[8].setBackground(colorl);
                                    repaint();
                                    sleep(times / 2);
                                    button[7].setBackground(color);
                                    button5[8].setBackground(color);
                                    break;
                                case "2-":
                                    new Audio("audio/l2.mp3").start();
                                    button[8].setBackground(colorl);
                                    button5[9].setBackground(colorl);
                                    repaint();
                                    sleep(times / 2);
                                    button[8].setBackground(color);
                                    button5[9].setBackground(color);
                                    break;
                                case "3-":
                                    new Audio("audio/l3.mp3").start();
                                    button[9].setBackground(colorl);
                                    button5[10].setBackground(colorl);
                                    repaint();
                                    sleep(times / 2);
                                    button[9].setBackground(color);
                                    button5[10].setBackground(color);
                                    break;
                                case "4-":
                                    new Audio("audio/l4.mp3").start();
                                    button[10].setBackground(colorl);
                                    button4[12].setBackground(colorl);
                                    repaint();
                                    sleep(times / 2);
                                    button[10].setBackground(color);
                                    button4[12].setBackground(color);
                                    break;
                                case "5-":
                                    new Audio("audio/l5.mp3").start();
                                    button[11].setBackground(colorl);
                                    button5[11].setBackground(colorl);
                                    repaint();
                                    sleep(times / 2);
                                    button[11].setBackground(color);
                                    button5[11].setBackground(color);
                                    break;
                                case "6-":
                                    new Audio("audio/l6.mp3").start();
                                    button[12].setBackground(colorl);
                                    button5[12].setBackground(colorl);
                                    repaint();
                                    sleep(times / 2);
                                    button[12].setBackground(color);
                                    button5[12].setBackground(color);
                                    break;
                                case "7-":
                                    new Audio("audio/l7.mp3").start();
                                    button[13].setBackground(colorl);
                                    button4[16].setBackground(colorl);
                                    repaint();
                                    sleep(times / 2);
                                    button[13].setBackground(color);
                                    button4[16].setBackground(color);
                                    break;
                                case "1":
                                    new Audio("audio/m1.mp3").start();
                                    button[14].setBackground(colorm);
                                    button4[13].setBackground(colorm);
                                    repaint();
                                    sleep(times / 2);
                                    button[14].setBackground(color);
                                    button4[13].setBackground(color);
                                    break;
                                case "2":
                                    new Audio("audio/m2.mp3").start();
                                    button[15].setBackground(colorm);
                                    button4[14].setBackground(colorm);
                                    repaint();
                                    sleep(times / 2);
                                    button[15].setBackground(color);
                                    button4[14].setBackground(color);
                                    break;
                                case "3":
                                    new Audio("audio/m3.mp3").start();
                                    button[16].setBackground(colorm);
                                    button4[15].setBackground(colorm);
                                    repaint();
                                    sleep(times / 2);
                                    button[16].setBackground(color);
                                    button4[15].setBackground(color);
                                    break;
                                case "4":
                                    new Audio("audio/m4.mp3").start();
                                    button[17].setBackground(colorm);
                                    button3[13].setBackground(colorm);
                                    repaint();
                                    sleep(times / 2);
                                    button[17].setBackground(color);
                                    button3[13].setBackground(color);
                                    break;
                                case "5":
                                    new Audio("audio/m5.mp3").start();
                                    button[18].setBackground(colorm);
                                    button3[14].setBackground(colorm);
                                    repaint();
                                    sleep(times / 2);
                                    button[18].setBackground(color);
                                    button3[14].setBackground(color);
                                    break;
                                case "6":
                                    new Audio("audio/m6.mp3").start();
                                    button[19].setBackground(colorm);
                                    button3[15].setBackground(colorm);
                                    repaint();
                                    sleep(times / 2);
                                    button[19].setBackground(color);
                                    button3[15].setBackground(color);
                                    break;
                                case "7":
                                    new Audio("audio/m7.mp3").start();
                                    button[20].setBackground(colorm);
                                    button2[17].setBackground(colorm);
                                    repaint();
                                    sleep(times / 2);
                                    button[20].setBackground(color);
                                    button2[17].setBackground(color);
                                    break;
                                case "1+":
                                    new Audio("audio/h1.mp3").start();
                                    button[21].setBackground(colorh);
                                    button2[18].setBackground(colorh);
                                    repaint();
                                    sleep(times / 2);
                                    button[21].setBackground(color);
                                    button2[18].setBackground(color);
                                    break;
                                case "2+":
                                    new Audio("audio/h2.mp3").start();
                                    button[22].setBackground(colorh);
                                    button2[19].setBackground(colorh);
                                    repaint();
                                    sleep(times / 2);
                                    button[22].setBackground(color);
                                    button2[19].setBackground(color);
                                    break;
                                case "3+":
                                    new Audio("audio/h3.mp3").start();
                                    button[23].setBackground(colorh);
                                    button2[20].setBackground(colorh);
                                    repaint();
                                    sleep(times / 2);
                                    button[23].setBackground(color);
                                    button2[20].setBackground(color);
                                    break;
                                case "4+":
                                    new Audio("audio/h4.mp3").start();
                                    button[24].setBackground(colorh);
                                    button1[17].setBackground(colorh);
                                    repaint();
                                    sleep(times / 2);
                                    button[24].setBackground(color);
                                    button1[17].setBackground(color);
                                    break;
                                case "5+":
                                    new Audio("audio/h5.mp3").start();
                                    button[25].setBackground(colorh);
                                    button1[18].setBackground(colorh);
                                    repaint();
                                    sleep(times / 2);
                                    button[25].setBackground(color);
                                    button1[18].setBackground(color);
                                    break;
                                case "6+":
                                    new Audio("audio/h6.mp3").start();
                                    button[26].setBackground(colorh);
                                    button1[19].setBackground(colorh);
                                    repaint();
                                    sleep(times / 2);
                                    button[26].setBackground(color);
                                    button1[19].setBackground(color);
                                    break;
                                case "7+":
                                    new Audio("audio/h7.mp3").start();
                                    button[27].setBackground(colorh);
                                    button1[20].setBackground(colorh);
                                    repaint();
                                    sleep(times / 2);
                                    button[27].setBackground(color);
                                    button1[20].setBackground(color);
                                    break;
                                case "1++":
                                    new Audio("audio/hh1.mp3").start();
                                    button[28].setBackground(colorhh);
                                    button2[14].setBackground(colorhh);
                                    repaint();
                                    sleep(times / 2);
                                    button[28].setBackground(color);
                                    button2[14].setBackground(color);
                                    break;
                                case "2++":
                                    new Audio("audio/hh2.mp3").start();
                                    button[29].setBackground(colorhh);
                                    button2[15].setBackground(colorhh);
                                    repaint();
                                    sleep(times / 2);
                                    button[29].setBackground(color);
                                    button2[15].setBackground(color);
                                    break;
                                case "3++":
                                    new Audio("audio/hh3.mp3").start();
                                    button[30].setBackground(colorhh);
                                    button2[16].setBackground(colorhh);
                                    repaint();
                                    sleep(times / 2);
                                    button[30].setBackground(color);
                                    button2[16].setBackground(color);
                                    break;
                                case "4++":
                                    new Audio("audio/hh4.mp3").start();
                                    button[31].setBackground(colorhh);
                                    button1[14].setBackground(colorhh);
                                    repaint();
                                    sleep(times / 2);
                                    button[31].setBackground(color);
                                    button1[14].setBackground(color);
                                    break;
                                case "5++":
                                    new Audio("audio/hh5.mp3").start();
                                    button[32].setBackground(colorhh);
                                    button1[15].setBackground(colorhh);
                                    repaint();
                                    sleep(times / 2);
                                    button[32].setBackground(color);
                                    button1[15].setBackground(color);
                                    break;
                                case "6++":
                                    new Audio("audio/hh6.mp3").start();
                                    button[33].setBackground(colorhh);
                                    button1[16].setBackground(colorhh);
                                    repaint();
                                    sleep(times / 2);
                                    button[33].setBackground(color);
                                    button1[16].setBackground(color);
                                    break;
                                case "7++":
                                    new Audio("audio/hh7.mp3").start();
                                    button[34].setBackground(colorhh);
                                    repaint();
                                    sleep(times / 2);
                                    button[34].setBackground(color);
                                    break;
                                case "0":
                                    sleep(times / 2);
                                    break;
                                default:
                                    continue;
                            }
                        } else if (this.getName().equals("伴奏线程")) {
                            switch (notes[i]) {
                                case "1--":
                                    new Audio("audio/ll1.mp3").start();
                                    button[0].setBackground(colorll);
                                    button4[1].setBackground(colorll);
                                    repaint();
                                    sleep(times / 2);
                                    button[0].setBackground(color);
                                    button4[1].setBackground(color);
                                    break;
                                case "2--":
                                    new Audio("audio/ll2.mp3").start();
                                    button[1].setBackground(colorll);
                                    button4[2].setBackground(colorll);
                                    repaint();
                                    sleep(times / 2);
                                    button[1].setBackground(color);
                                    button4[2].setBackground(color);
                                    break;
                                case "3--":
                                    new Audio("audio/ll3.mp3").start();
                                    button[2].setBackground(colorll);
                                    button4[3].setBackground(colorll);
                                    repaint();
                                    sleep(times / 2);
                                    button[2].setBackground(color);
                                    button4[3].setBackground(color);
                                    break;
                                case "4--":
                                    new Audio("audio/ll4.mp3").start();
                                    button[3].setBackground(colorll);
                                    button4[4].setBackground(colorll);
                                    repaint();
                                    sleep(times / 2);
                                    button[3].setBackground(color);
                                    button4[4].setBackground(color);
                                    break;
                                case "5--":
                                    new Audio("audio/ll5.mp3").start();
                                    button[4].setBackground(colorll);
                                    button4[5].setBackground(colorll);
                                    repaint();
                                    sleep(times / 2);
                                    button[4].setBackground(color);
                                    button4[5].setBackground(color);
                                    break;
                                case "6--":
                                    new Audio("audio/ll6.mp3").start();
                                    button[5].setBackground(colorll);
                                    button4[6].setBackground(colorll);
                                    repaint();
                                    sleep(times / 2);
                                    button[5].setBackground(color);
                                    button4[6].setBackground(color);
                                    break;
                                case "7--":
                                    new Audio("audio/ll7.mp3").start();
                                    button[6].setBackground(colorll);
                                    button4[7].setBackground(colorll);
                                    repaint();
                                    sleep(times / 2);
                                    button[6].setBackground(color);
                                    button4[7].setBackground(color);
                                    break;
                                case "1-":
                                    new Audio("audio/l1.mp3").start();
                                    button[7].setBackground(colorl);
                                    button3[1].setBackground(colorl);
                                    repaint();
                                    sleep(times / 2);
                                    button[7].setBackground(color);
                                    button3[1].setBackground(color);
                                    break;
                                case "2-":
                                    new Audio("audio/l2.mp3").start();
                                    button[8].setBackground(colorl);
                                    button3[2].setBackground(colorl);
                                    repaint();
                                    sleep(times / 2);
                                    button[8].setBackground(color);
                                    button3[2].setBackground(color);
                                    break;
                                case "3-":
                                    new Audio("audio/l3.mp3").start();
                                    button[9].setBackground(colorl);
                                    button3[3].setBackground(colorl);
                                    repaint();
                                    sleep(times / 2);
                                    button[9].setBackground(color);
                                    button3[3].setBackground(color);
                                    break;
                                case "4-":
                                    new Audio("audio/l4.mp3").start();
                                    button[10].setBackground(colorl);
                                    button3[4].setBackground(colorl);
                                    repaint();
                                    sleep(times / 2);
                                    button[10].setBackground(color);
                                    button3[4].setBackground(color);
                                    break;
                                case "5-":
                                    new Audio("audio/l5.mp3").start();
                                    button[11].setBackground(colorl);
                                    button3[5].setBackground(colorl);
                                    repaint();
                                    sleep(times / 2);
                                    button[11].setBackground(color);
                                    button3[5].setBackground(color);
                                    break;
                                case "6-":
                                    new Audio("audio/l6.mp3").start();
                                    button[12].setBackground(colorl);
                                    button3[6].setBackground(colorl);
                                    repaint();
                                    sleep(times / 2);
                                    button[12].setBackground(color);
                                    button3[6].setBackground(color);
                                    break;
                                case "7-":
                                    new Audio("audio/l7.mp3").start();
                                    button[13].setBackground(colorl);
                                    button3[7].setBackground(colorl);
                                    repaint();
                                    sleep(times / 2);
                                    button[13].setBackground(color);
                                    button3[7].setBackground(color);
                                    break;
                                case "1":
                                    new Audio("audio/m1.mp3").start();
                                    button[14].setBackground(colorm);
                                    button2[1].setBackground(colorm);
                                    repaint();
                                    sleep(times / 2);
                                    button[14].setBackground(color);
                                    button2[1].setBackground(color);
                                    break;
                                case "2":
                                    new Audio("audio/m2.mp3").start();
                                    button[15].setBackground(colorm);
                                    button2[2].setBackground(colorm);
                                    repaint();
                                    sleep(times / 2);
                                    button[15].setBackground(color);
                                    button2[2].setBackground(color);
                                    break;
                                case "3":
                                    new Audio("audio/m3.mp3").start();
                                    button[16].setBackground(colorm);
                                    button2[3].setBackground(colorm);
                                    repaint();
                                    sleep(times / 2);
                                    button[16].setBackground(color);
                                    button2[3].setBackground(color);
                                    break;
                                case "4":
                                    new Audio("audio/m4.mp3").start();
                                    button[17].setBackground(colorm);
                                    button2[4].setBackground(colorm);
                                    repaint();
                                    sleep(times / 2);
                                    button[17].setBackground(color);
                                    button2[4].setBackground(color);
                                    break;
                                case "5":
                                    new Audio("audio/m5.mp3").start();
                                    button[18].setBackground(colorm);
                                    button2[5].setBackground(colorm);
                                    repaint();
                                    sleep(times / 2);
                                    button[18].setBackground(color);
                                    button2[5].setBackground(color);
                                    break;
                                case "6":
                                    new Audio("audio/m6.mp3").start();
                                    button[19].setBackground(colorm);
                                    button2[6].setBackground(colorm);
                                    repaint();
                                    sleep(times / 2);
                                    button[19].setBackground(color);
                                    button2[6].setBackground(color);
                                    break;
                                case "7":
                                    new Audio("audio/m7.mp3").start();
                                    button[20].setBackground(colorm);
                                    button2[7].setBackground(colorm);
                                    repaint();
                                    sleep(times / 2);
                                    button[20].setBackground(color);
                                    button2[7].setBackground(color);
                                    break;
                                case "1+":
                                    new Audio("audio/h1.mp3").start();
                                    button[21].setBackground(colorh);
                                    button1[1].setBackground(colorh);
                                    repaint();
                                    sleep(times / 2);
                                    button[21].setBackground(color);
                                    button1[1].setBackground(color);
                                    break;
                                case "2+":
                                    new Audio("audio/h2.mp3").start();
                                    button[22].setBackground(colorh);
                                    button1[2].setBackground(colorh);
                                    repaint();
                                    sleep(times / 2);
                                    button[22].setBackground(color);
                                    button1[2].setBackground(color);
                                    break;
                                case "3+":
                                    new Audio("audio/h3.mp3").start();
                                    button[23].setBackground(colorh);
                                    button1[3].setBackground(colorh);
                                    repaint();
                                    sleep(times / 2);
                                    button[23].setBackground(color);
                                    button1[3].setBackground(color);
                                    break;
                                case "4+":
                                    new Audio("audio/h4.mp3").start();
                                    button[24].setBackground(colorh);
                                    button1[4].setBackground(colorh);
                                    repaint();
                                    sleep(times / 2);
                                    button[24].setBackground(color);
                                    button1[4].setBackground(color);
                                    break;
                                case "5+":
                                    new Audio("audio/h5.mp3").start();
                                    button[25].setBackground(colorh);
                                    button1[5].setBackground(colorh);
                                    repaint();
                                    sleep(times / 2);
                                    button[25].setBackground(color);
                                    button1[5].setBackground(color);
                                    break;
                                case "6+":
                                    new Audio("audio/h6.mp3").start();
                                    button[26].setBackground(colorh);
                                    button1[6].setBackground(colorh);
                                    repaint();
                                    sleep(times / 2);
                                    button[26].setBackground(color);
                                    button1[6].setBackground(color);
                                    break;
                                case "7+":
                                    new Audio("audio/h7.mp3").start();
                                    button[27].setBackground(colorh);
                                    button1[7].setBackground(colorh);
                                    repaint();
                                    sleep(times / 2);
                                    button[27].setBackground(color);
                                    button1[7].setBackground(color);
                                    break;
                                case "1++":
                                    new Audio("audio/hh1.mp3").start();
                                    button[28].setBackground(colorhh);
                                    button1[8].setBackground(colorhh);
                                    repaint();
                                    sleep(times / 2);
                                    button[28].setBackground(color);
                                    button1[8].setBackground(color);
                                    break;
                                case "2++":
                                    new Audio("audio/hh2.mp3").start();
                                    button[29].setBackground(colorhh);
                                    button1[9].setBackground(colorhh);
                                    repaint();
                                    sleep(times / 2);
                                    button[29].setBackground(color);
                                    button1[9].setBackground(color);
                                    break;
                                case "3++":
                                    new Audio("audio/hh3.mp3").start();
                                    button[30].setBackground(colorhh);
                                    button1[10].setBackground(colorhh);
                                    repaint();
                                    sleep(times / 2);
                                    button[30].setBackground(color);
                                    button1[10].setBackground(color);
                                    break;
                                case "4++":
                                    new Audio("audio/hh4.mp3").start();
                                    button[31].setBackground(colorhh);
                                    button1[11].setBackground(colorhh);
                                    repaint();
                                    sleep(times / 2);
                                    button[31].setBackground(color);
                                    button1[11].setBackground(color);
                                    break;
                                case "5++":
                                    new Audio("audio/hh5.mp3").start();
                                    button[32].setBackground(colorhh);
                                    button1[12].setBackground(colorhh);
                                    repaint();
                                    sleep(times / 2);
                                    button[32].setBackground(color);
                                    button1[12].setBackground(color);
                                    break;
                                case "6++":
                                    new Audio("audio/hh6.mp3").start();
                                    button[33].setBackground(colorhh);
                                    button1[13].setBackground(colorhh);
                                    repaint();
                                    sleep(times / 2);
                                    button[33].setBackground(color);
                                    button1[13].setBackground(color);
                                    break;
                                case "7++":
                                    new Audio("audio/hh7.mp3").start();
                                    button[34].setBackground(colorhh);
                                    repaint();
                                    sleep(times / 2);
                                    button[34].setBackground(color);
                                    break;
                                case "0":
                                    sleep(times / 2);
                                    break;
                                default:
                                    continue;
                            }
                        }
                    } else if (radioMenuItem_model2.isSelected()) {
                        switch (notes[i]) {
                            case "1--":
                                new Audio("audio/ll1.mp3").start();
                                button[0].setBackground(colorll);
                                button4[1].setBackground(colorll);
                                repaint();
                                sleep(times / 2);
                                button[0].setBackground(color);
                                button4[1].setBackground(color);
                                break;
                            case "2--":
                                new Audio("audio/ll2.mp3").start();
                                button[1].setBackground(colorll);
                                button4[2].setBackground(colorll);
                                repaint();
                                sleep(times / 2);
                                button[1].setBackground(color);
                                button4[2].setBackground(color);
                                break;
                            case "3--":
                                new Audio("audio/ll3.mp3").start();
                                button[2].setBackground(colorll);
                                button4[3].setBackground(colorll);
                                repaint();
                                sleep(times / 2);
                                button[2].setBackground(color);
                                button4[3].setBackground(color);
                                break;
                            case "4--":
                                new Audio("audio/ll4.mp3").start();
                                button[3].setBackground(colorll);
                                button4[4].setBackground(colorll);
                                repaint();
                                sleep(times / 2);
                                button[3].setBackground(color);
                                button4[4].setBackground(color);
                                break;
                            case "5--":
                                new Audio("audio/ll5.mp3").start();
                                button[4].setBackground(colorll);
                                button4[5].setBackground(colorll);
                                repaint();
                                sleep(times / 2);
                                button[4].setBackground(color);
                                button4[5].setBackground(color);
                                break;
                            case "6--":
                                new Audio("audio/ll6.mp3").start();
                                button[5].setBackground(colorll);
                                button4[6].setBackground(colorll);
                                repaint();
                                sleep(times / 2);
                                button[5].setBackground(color);
                                button4[6].setBackground(color);
                                break;
                            case "7--":
                                new Audio("audio/ll7.mp3").start();
                                button[6].setBackground(colorll);
                                button4[7].setBackground(colorll);
                                repaint();
                                sleep(times / 2);
                                button[6].setBackground(color);
                                button4[7].setBackground(color);
                                break;
                            case "1-":
                                new Audio("audio/l1.mp3").start();
                                button[7].setBackground(colorl);
                                button3[1].setBackground(colorl);
                                repaint();
                                sleep(times / 2);
                                button[7].setBackground(color);
                                button3[1].setBackground(color);
                                break;
                            case "2-":
                                new Audio("audio/l2.mp3").start();
                                button[8].setBackground(colorl);
                                button3[2].setBackground(colorl);
                                repaint();
                                sleep(times / 2);
                                button[8].setBackground(color);
                                button3[2].setBackground(color);
                                break;
                            case "3-":
                                new Audio("audio/l3.mp3").start();
                                button[9].setBackground(colorl);
                                button3[3].setBackground(colorl);
                                repaint();
                                sleep(times / 2);
                                button[9].setBackground(color);
                                button3[3].setBackground(color);
                                break;
                            case "4-":
                                new Audio("audio/l4.mp3").start();
                                button[10].setBackground(colorl);
                                button3[4].setBackground(colorl);
                                repaint();
                                sleep(times / 2);
                                button[10].setBackground(color);
                                button3[4].setBackground(color);
                                break;
                            case "5-":
                                new Audio("audio/l5.mp3").start();
                                button[11].setBackground(colorl);
                                button3[5].setBackground(colorl);
                                repaint();
                                sleep(times / 2);
                                button[11].setBackground(color);
                                button3[5].setBackground(color);
                                break;
                            case "6-":
                                new Audio("audio/l6.mp3").start();
                                button[12].setBackground(colorl);
                                button3[6].setBackground(colorl);
                                repaint();
                                sleep(times / 2);
                                button[12].setBackground(color);
                                button3[6].setBackground(color);
                                break;
                            case "7-":
                                new Audio("audio/l7.mp3").start();
                                button[13].setBackground(colorl);
                                button3[7].setBackground(colorl);
                                repaint();
                                sleep(times / 2);
                                button[13].setBackground(color);
                                button3[7].setBackground(color);
                                break;
                            case "1":
                                new Audio("audio/m1.mp3").start();
                                button[14].setBackground(colorm);
                                button2[1].setBackground(colorm);
                                repaint();
                                sleep(times / 2);
                                button[14].setBackground(color);
                                button2[1].setBackground(color);
                                break;
                            case "2":
                                new Audio("audio/m2.mp3").start();
                                button[15].setBackground(colorm);
                                button2[2].setBackground(colorm);
                                repaint();
                                sleep(times / 2);
                                button[15].setBackground(color);
                                button2[2].setBackground(color);
                                break;
                            case "3":
                                new Audio("audio/m3.mp3").start();
                                button[16].setBackground(colorm);
                                button2[3].setBackground(colorm);
                                repaint();
                                sleep(times / 2);
                                button[16].setBackground(color);
                                button2[3].setBackground(color);
                                break;
                            case "4":
                                new Audio("audio/m4.mp3").start();
                                button[17].setBackground(colorm);
                                button2[4].setBackground(colorm);
                                repaint();
                                sleep(times / 2);
                                button[17].setBackground(color);
                                button2[4].setBackground(color);
                                break;
                            case "5":
                                new Audio("audio/m5.mp3").start();
                                button[18].setBackground(colorm);
                                button2[5].setBackground(colorm);
                                repaint();
                                sleep(times / 2);
                                button[18].setBackground(color);
                                button2[5].setBackground(color);
                                break;
                            case "6":
                                new Audio("audio/m6.mp3").start();
                                button[19].setBackground(colorm);
                                button2[6].setBackground(colorm);
                                repaint();
                                sleep(times / 2);
                                button[19].setBackground(color);
                                button2[6].setBackground(color);
                                break;
                            case "7":
                                new Audio("audio/m7.mp3").start();
                                button[20].setBackground(colorm);
                                button2[7].setBackground(colorm);
                                repaint();
                                sleep(times / 2);
                                button[20].setBackground(color);
                                button2[7].setBackground(color);
                                break;
                            case "1+":
                                new Audio("audio/h1.mp3").start();
                                button[21].setBackground(colorh);
                                button1[1].setBackground(colorh);
                                repaint();
                                sleep(times / 2);
                                button[21].setBackground(color);
                                button1[1].setBackground(color);
                                break;
                            case "2+":
                                new Audio("audio/h2.mp3").start();
                                button[22].setBackground(colorh);
                                button1[2].setBackground(colorh);
                                repaint();
                                sleep(times / 2);
                                button[22].setBackground(color);
                                button1[2].setBackground(color);
                                break;
                            case "3+":
                                new Audio("audio/h3.mp3").start();
                                button[23].setBackground(colorh);
                                button1[3].setBackground(colorh);
                                repaint();
                                sleep(times / 2);
                                button[23].setBackground(color);
                                button1[3].setBackground(color);
                                break;
                            case "4+":
                                new Audio("audio/h4.mp3").start();
                                button[24].setBackground(colorh);
                                button1[4].setBackground(colorh);
                                repaint();
                                sleep(times / 2);
                                button[24].setBackground(color);
                                button1[4].setBackground(color);
                                break;
                            case "5+":
                                new Audio("audio/h5.mp3").start();
                                button[25].setBackground(colorh);
                                button1[5].setBackground(colorh);
                                repaint();
                                sleep(times / 2);
                                button[25].setBackground(color);
                                button1[5].setBackground(color);
                                break;
                            case "6+":
                                new Audio("audio/h6.mp3").start();
                                button[26].setBackground(colorh);
                                button1[6].setBackground(colorh);
                                repaint();
                                sleep(times / 2);
                                button[26].setBackground(color);
                                button1[6].setBackground(color);
                                break;
                            case "7+":
                                new Audio("audio/h7.mp3").start();
                                button[27].setBackground(colorh);
                                button1[7].setBackground(colorh);
                                repaint();
                                sleep(times / 2);
                                button[27].setBackground(color);
                                button1[7].setBackground(color);
                                break;
                            case "1++":
                                new Audio("audio/hh1.mp3").start();
                                button[28].setBackground(colorhh);
                                button1[8].setBackground(colorhh);
                                repaint();
                                sleep(times / 2);
                                button[28].setBackground(color);
                                button1[8].setBackground(color);
                                break;
                            case "2++":
                                new Audio("audio/hh2.mp3").start();
                                button[29].setBackground(colorhh);
                                button1[9].setBackground(colorhh);
                                repaint();
                                sleep(times / 2);
                                button[29].setBackground(color);
                                button1[9].setBackground(color);
                                break;
                            case "3++":
                                new Audio("audio/hh3.mp3").start();
                                button[30].setBackground(colorhh);
                                button1[10].setBackground(colorhh);
                                repaint();
                                sleep(times / 2);
                                button[30].setBackground(color);
                                button1[10].setBackground(color);
                                break;
                            case "4++":
                                new Audio("audio/hh4.mp3").start();
                                button[31].setBackground(colorhh);
                                button1[11].setBackground(colorhh);
                                repaint();
                                sleep(times / 2);
                                button[31].setBackground(color);
                                button1[11].setBackground(color);
                                break;
                            case "5++":
                                new Audio("audio/hh5.mp3").start();
                                button[32].setBackground(colorhh);
                                button1[12].setBackground(colorhh);
                                repaint();
                                sleep(times / 2);
                                button[32].setBackground(color);
                                button1[12].setBackground(color);
                                break;
                            case "6++":
                                new Audio("audio/hh6.mp3").start();
                                button[33].setBackground(colorhh);
                                button1[13].setBackground(colorhh);
                                repaint();
                                sleep(times / 2);
                                button[33].setBackground(color);
                                button1[13].setBackground(color);
                                break;
                            case "7++":
                                new Audio("audio/hh7.mp3").start();
                                button[34].setBackground(colorhh);
                                repaint();
                                sleep(times / 2);
                                button[34].setBackground(color);
                                break;
                            case "0":
                                sleep(times / 2);
                                break;
                            default:
                                continue;
                        }
                    }
                    if (i == notes.length - 1) {
                        menuItem_suspend.setText("暂停播放");
                        menuItem_suspend.setEnabled(false);
                        menuItem_stop.setEnabled(false);
                        menuItem_forward.setEnabled(false);
                        menuItem_backward.setEnabled(false);
                        label_index.setEnabled(false);
                        label_accompaniments.setEnabled(false);
                        slider_progress.setEnabled(false);
                        menuItem_reset.setEnabled(true);
                    }
                    repaint();
                    sleep(times / 2);
                    times = this.times;// 延时减半之后恢复正常延时
                }
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

}
