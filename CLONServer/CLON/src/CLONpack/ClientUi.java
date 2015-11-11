package CLONpack;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ClientUi extends JFrame implements ActionListener, ItemListener {
	private Container con = this.getContentPane();
	private Dimension dm = Toolkit.getDefaultToolkit().getScreenSize(); // 스크린
	private String ip = null;
	private String port = null;// 사이즈를
								// 얻어옴
	private BorderLayout bd = new BorderLayout();
	//
	//
	private JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); // 화면을

	// 두영역으로
	// 나눔

	private JMenuBar menubar = new JMenuBar();
	private JMenu menu = new JMenu("설정");
	private JCheckBoxMenuItem item = new JCheckBoxMenuItem("서버IP 자동접속");
	public static JCheckBoxMenuItem item2 = new JCheckBoxMenuItem("App 접속가능");
	public static JList jlist = new JList(); // 회원 목록이 나올 리스트
	private JPanel jp = new JPanel();
	private JPanel memberList = new JPanel();
	private JPanel jpgroup = new JPanel();
	private JPanel single = new JPanel(); //
	//
	public static JTextArea content = new JTextArea(10, 65); // 채팅 내용이 입력될
																// 텍스트area
	public static JTextField singleAlram = new JTextField(); // 서버가 보여주는 알람
	private JTextField inputText = new JTextField(10); // 채팅 전송할 내용을 입력할 텍스트 필드
	private JButton sendBtn = new JButton("전송"); // 채팅 내용 전송 버튼
	public static JLabel memberc = new JLabel(" CLON 접속회원수 : 0"); // 접속 회원 수

	//
	private int x, y = 0; // 화면 초기 위치 x,y
	private int result = 0; // 강퇴, 차단 기능시 사용 변수

	private ClientTh th;
	private LoginSocket c;

	private JPopupMenu Jpop = new JPopupMenu();
	private JMenuItem itempop = new JMenuItem("귓속말보내기");
	private JMenuItem itempop2 = new JMenuItem("쪽지 보내기");
	public static String listValue;
	static boolean flag = true;

	private JDialog jdsend;
	private JPanel jp01;
	private JPanel jp02;
	private JLabel jp01_jl1;
	private JLabel jp01_jl2;
	private JLabel jp02_jl;
	private JTextArea jp02_ja;
	private JButton jbsend;

	public ClientUi(final ClientTh th) {
		this.th = th;
		// 회원 리스트를 나타내는 UI
		jp.setLayout(new BorderLayout());
		jp.add(inputText, "Center");
		jp.add(sendBtn, "East");
		sendBtn.addActionListener(this);
		this.c = th.getLogin();
		this.ip = c.getip();
		this.port = c.getPort();
		this.memberList.setLayout(new BorderLayout());
		memberList.add(memberc, "North");
		memberList.add(new JScrollPane(jlist), "Center");
		// End

		// 챗팅 창 UI
		content.setEditable(false);
		singleAlram.setSize(100, 100);
		content.setBorder(new EmptyBorder(0, 0, 0, 0));
		single.setLayout(new BorderLayout());
		singleAlram.setEditable(false);
		single.add(singleAlram, "North");
		single.add(new JScrollPane(content), "Center");
		jlist.setListData(new Vector());
		jlist.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					listValue = (String) jlist.getSelectedValue();
				} else {
					jlist.addMouseListener(new MouseAdapter() {

						@Override
						public void mouseClicked(MouseEvent e) {
							if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
								if (jlist.locationToIndex(e.getPoint()) == jlist
										.getSelectedIndex()) {
									int count = jlist.getModel().getSize();
									int cal = count * 18;
									if (e.getY() <= cal) {
										Jpop.show(jlist, e.getX(), e.getY());
									}
								}
							}
							super.mouseClicked(e);
						}

					});
				}
			}
		});

		jsp.add(single);
		jsp.add(memberList);
		// End

		// 최상위 컨테이너에 UI 설정
		con.setLayout(bd);
		con.add(jsp, "Center");
		con.add(jp, "South");
		// End

		this.setTitle("Client");
		this.setSize(1000, 700);
		x = (int) ((dm.getWidth() / 2) - (this.getWidth() / 2));
		y = (int) ((dm.getHeight() / 2) - (this.getHeight() / 2));
		settingmenu();
		item.addItemListener(this);
		item2.addItemListener(this);
		this.setLocation(new Point(x, y));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		inputText.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				String str2 = inputText.getText().toString();
				String split[];
				split = str2.split("/");

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!inputText.getText().toString().equals("")) {
						if (split[0].equals("@w")) {
							th.write("@w/" + LoginSocket.name + "/"
									+ split[1].toString() + "/"
									+ split[2].toString());
							content.append("<" + LoginSocket.name + ">:(귓속말 → "
									+ split[1].toString() + ") "
									+ split[2].toString() + "\n");
							inputText.setText("@w/" + listValue + "/");
						} else {
							th.write("<" + LoginSocket.name + ">:"
									+ inputText.getText().toString());
							inputText.setText("");

						}

					}
				}
			}
		});

	}

	public void settingmenu() {
		itempop.addActionListener(this);
		itempop2.addActionListener(this);
		Jpop.add(itempop);
		Jpop.add(itempop2);
		menu.add(item);
		menu.add(item2);
		menubar.add(menu);
		this.setJMenuBar(menubar);
		if (BeginSettingFile.selected || BeginSettingFile.savecheck) {
			item.setSelected(true);
		} else {
			item.setSelected(false);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		String str2 = inputText.getText().toString();
		String split[];
		split = str2.split("/");
		if (str.equals("전송")) {
			if (!inputText.getText().toString().equals("")) {
				if (split[0].equals("@w")) {
					th.write("@w/" + LoginSocket.name + "/"
							+ split[1].toString() + "/" + split[2].toString());
					content.append("<" + LoginSocket.name + ">:(귓속말 → "
							+ split[1].toString() + ") " + split[2].toString()
							+ "\n");
					inputText.setText("@w/" + listValue + "/");
				} else {
					th.write("<" + LoginSocket.name + ">:"
							+ inputText.getText().toString());
					inputText.setText("");
				}

			}
		} else if (e.getSource() == itempop) {
			inputText.setText("@w/" + listValue + "/");
			inputText.requestFocus();
		} else if (e.getSource() == itempop2) {
			if (flag) {
				createDialg();
				flag = false;
			} else {
				jp02_ja.requestFocus();
			}
		}

	}

	public void createDialg() {
		jdsend = new JDialog(this);
		jdsend.setTitle("쪽지보내기");
		jp01 = new JPanel();
		jp02 = new JPanel();
		jp01_jl1 = new JLabel("받는 사람 : ", JLabel.RIGHT);
		jp01_jl2 = new JLabel(listValue, JLabel.CENTER);
		jp02_jl = new JLabel("내용 : ");
		jp02_ja = new JTextArea();
		jbsend = new JButton("보내기");
		jdsend.setLayout(new BorderLayout());
		jdsend.setSize(200, 200);
		jp01.setLayout(new FlowLayout());
		jp02.setLayout(new BorderLayout());

		jp01.add(jp01_jl1);
		jp01.add(jp01_jl2);
		jp02.add(jp02_jl, "West");
		jp02.add(new JScrollPane(jp02_ja), "Center");
		jdsend.add(jp01, "North");
		jdsend.add(jp02, "Center");
		jdsend.add(jbsend, "South");
		x = (int) ((dm.getWidth() / 2) - (jdsend.getWidth() / 2));
		y = (int) ((dm.getHeight() / 2) - (jdsend.getHeight() / 2));
		jdsend.setLocation(new Point(x, y));
		jbsend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String str = jp02_ja.getText();
				String str2 = null;
				String split2[];
				split2 = str.split("\n");
				if (split2.length >= 1) {
					str2 = split2[0] + "@";
					for (int i = 1; i < split2.length; i++) {
						str2 += split2[i] + "@";
					}
					th.write("letter/" + LoginSocket.name + "/" + listValue
							+ "/" + str2);
				}
				jdsend.dispose();
				flag = true;
			}
		});
		jdsend.setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == item) {
			if (item.getState()) {
				BeginSettingFile
						.writeData((this.ip + "/" + this.port + "/true/o\n"));
			} else {
				BeginSettingFile
						.writeData((this.ip + "/" + this.port + "/false/o\n"));

			}
		} else if (e.getSource() == item2) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				th.write("appcheck/o");
				item2.setSelected(true);
			} else if (e.getStateChange() == ItemEvent.DESELECTED) {
				th.write("appcheck/x");
				item2.setSelected(false);
			}
		}
	}

}
