package CLONSerpack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ServerUi extends JFrame implements ActionListener {
	private Connection con1;
	private Statement stmt;
	private Container con = this.getContentPane();
	private Dimension dm = Toolkit.getDefaultToolkit().getScreenSize(); // ��ũ��
	// �����
	// ����
	private Database dbdb;
	private ResultSet rs = null;
	private BorderLayout bd = new BorderLayout();
	private Socket c;
	private Vector v;
	private Vector v2;
	private String split[];
	//
	//
	private JDialog jdsend;
	private JPanel jp01;
	private JPanel jp02;
	private JLabel jp01_jl1;
	private JLabel jp01_jl2;
	private JLabel jp02_jl;
	private JTextArea jp02_ja;
	private JButton jbsend;

	private JDialog secretjd = new JDialog(this);
	private JPanel secretjp = new JPanel();
	private JButton secretjb = new JButton("�����ϱ�");
	public static JTextArea secretja = new JTextArea();
	private JScrollPane secretjsp = new JScrollPane(secretja);
	
	
	
	// ����ȭ�� �����ͺ��̽� �����
	Object[] column;
	Object[][] memberinfo;
	JDialog settingjd;
	JButton settingjb;
	JTable settingjt;
	JScrollPane settingjs;
	JPanel settingjp;

	// �÷���Ű
	private boolean f8 = false;
	private boolean f5;
	private boolean f9 = false;
	private boolean f10 = false;

	private JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); // ȭ����
	// �ο�������
	// ����
	public static JList jlist = new JList(); // ȸ�� ����� ���� ����Ʈ
	private JPanel jp = new JPanel();
	private JPanel memberList = new JPanel();
	private JPanel jpgroup = new JPanel();
	private JPanel single = new JPanel();
	private JPanel setP = new JPanel();
	private JPanel setB = new JPanel();

	private JPopupMenu jpm;
	private JMenuItem jpmit1;
	private JPanel jpmjp;
	private JMenuItem jpmit2;
	// private JMenuItem jpmit3;

	//
	//

	private JMenu jmu;
	private JMenuBar jmubar;
	private JMenuItem jmuit;
	private JMenuItem jmuit2;
	//
	//
	public static JTextArea content = new JTextArea(10, 65); // ä�� ������ �Էµ�
	// �ؽ�Ʈarea
	private JTextField singleAlram = new JTextField(); // ������ �����ִ� �˶�

	public static String notice = "�� �� �� �� : ";

	private JTextField inputText = new JTextField(10); // ä�� ������ ������ �Է��� �ؽ�Ʈ �ʵ�
	private JButton sendBtn = new JButton("����"); // ä�� ���� ���� ��ư
	private JButton banBtn = new JButton("����"); // ���� ��ư
	private JButton exitBtn = new JButton("����"); // ���� ��ư
	private JButton settingBtn = new JButton("�˸�");
	private JButton resetBtn = new JButton("����");
	public static JLabel memberc = new JLabel(" CLON ����ȸ���� : 0"); // ���� ȸ�� ��
	public String selection = null;
	//
	//
	private int x, y = 0; // ȭ�� �ʱ� ��ġ x,y
	private int result = 0; // ����, ���� ��ɽ� ��� ����

	public void CreateJPopupmenu() {
		jpm = new JPopupMenu("����");
		jpmit1 = new JMenuItem("�ӼӸ�������");
		jpmit2 = new JMenuItem("����������");
		// jpmit1 = new JMenuItem("�ӼӸ�");
		jpm.add(jpmit1);
		jpm.add(jpmit2);

		jpmit1.addActionListener(this);
		jpmit2.addActionListener(this);
	}

	public void Createmenu() {
		jmubar = new JMenuBar(); // �޴��ٸ� ����
		jmu = new JMenu("����"); // '����'�̶�� �޴��� ����
		jmubar.add(jmu); // �޴��ٿ� '����'�̶�� �޴��� �߰�
		jmuit = new JMenuItem("ȸ������"); // 'ȸ������'��� �޴������� ����
		jmuit2 = new JMenuItem("�д��ϱ�");
		jmu.add(jmuit); // '����' �޴� �ȿ� 'ȸ������'��� �޴������� �߰�
		jmu.add(jmuit2);
		jmubar.setBorder(BorderFactory.createLineBorder(Color.gray)); // �޴��� ����
		// ����

		jmuit.addActionListener(this); // 'ȸ������' �޴� ������ ���ý� �߻��ϴ� �̺�Ʈ ����
		jmuit2.addActionListener(this);
		
		setJMenuBar(jmubar);
	}

	public ServerUi() {
		dbdb = new Database();
		// ȸ�� ����Ʈ�� ��Ÿ���� UI
		jp.setLayout(new BorderLayout());
		jp.add(inputText, "Center");
		jp.add(sendBtn, "East");

		exitBtn.addActionListener(this);
		banBtn.addActionListener(this);
		sendBtn.addActionListener(this);
		resetBtn.addActionListener(this);
		settingBtn.addActionListener(this);

		jpgroup.add(exitBtn);
		jpgroup.add(banBtn);

		memberList.setLayout(new BorderLayout());
		memberList.add(memberc, "North");
		jlist.setListData(new Vector());
		jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		memberList.add(new JScrollPane(jlist), "Center");
		memberList.add(jpgroup, "South");
		// End

		// ê�� â UI
		content.setEditable(false);
		singleAlram.setEditable(false);
		singleAlram.setBorder(new EmptyBorder(0, 0, 0, 0));
		content.setBorder(new EmptyBorder(0, 0, 0, 0));
		single.setLayout(new BorderLayout());
		setP.setLayout(new BorderLayout());
		setP.add(singleAlram, "Center");
		setB.add(settingBtn);
		setB.add(resetBtn);
		setP.add(setB, "East");
		single.add(setP, "North");
		single.add(new JScrollPane(content), "Center");

		jsp.add(single);
		jsp.add(memberList);
		// End

		// �ֻ��� �����̳ʿ� UI ����
		con.setLayout(bd);
		con.add(jsp, "Center");
		con.add(jp, "South");
		// End

		this.setTitle("Server");
		this.setSize(1000, 700);
		x = (int) ((dm.getWidth() / 2) - (this.getWidth() / 2));
		y = (int) ((dm.getHeight() / 2) - (this.getHeight() / 2));
		this.setLocation(new Point(x, y));
		Createmenu();
		CreateJPopupmenu();

		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				for (int i = 0; i < v.size(); i++) {
					ServerInfo info = (ServerInfo) v.get(i);
					info.serwrite("###  ������ ����ġ�ʰ� ����Ǿ����ϴ�.  ###");
				}
				content.append("###  ������ ����ġ�ʰ� ����Ǿ����ϴ�.  ###");
				Database dbdb = new Database();
				dbdb.setalloff();
				System.exit(0);
				super.windowClosing(e);
			}

		});
		this.setResizable(false);
		this.setVisible(true);

		jlist.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()) {
					selection = (String) jlist.getSelectedValue();

				} else {
					jlist.addMouseListener(new MouseAdapter() {

						@Override
						public void mousePressed(MouseEvent e) {
							if (e.getModifiers() == e.BUTTON3_MASK) {
								if (jlist.locationToIndex(e.getPoint()) == jlist
										.getSelectedIndex()) {

									int count = jlist.getModel().getSize();
									int cal = count * 18;
									if (e.getY() <= cal) {
										jpm.show(jlist, e.getX(), e.getY());
									}
								}
							}
							super.mousePressed(e);
						}

					});

				}

			}
		});

		singleAlram.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_F5 && f5 == true) {
					singleAlram.setEditable(false);
					f5 = false;
					inputText.requestFocus();
					if (!singleAlram.getText().toString().equals("")) {
						notice = "�� �� �� �� : "
								+ singleAlram.getText().toString();
						for (int i = 0; i < v.size(); i++) {
							ServerInfo info = (ServerInfo) v.get(i);
							info.serwrite("alram" + "/" + notice);
						}
					}
				}
			}
		});

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
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!inputText.getText().equals("")) {
						String str2 = inputText.getText().toString();
						split = str2.split("/");
						if (split[0].equals("@w")) {
							for (int i = 0; i < v.size(); i++) {
								ServerInfo info = (ServerInfo) v.get(i);
								if (info.getname().equals(split[1].toString())) {
									info.serwrite("������ �ӼӸ� : " + split[2]);
									content.append(split[1] + "�Կ��� ���� �� : "
											+ split[2] + "\n");
									inputText.setText(split[0] + "/" + split[1]
											+ "/");
									return;
								}
							}
						}
						if (inputText.getText().toString().equals("/û��")) {
							content.setText("ȭ���� û�ҵǾ����ϴ�.\n");
							inputText.setText("");
							return;
						}
						for (int i = 0; i < v.size(); i++) {
							ServerInfo info = (ServerInfo) v.get(i);
							info.serwrite("##### << ���� : "
									+ inputText.getText().toString()
									+ " >> #####");
						}
						content.append("�� : " + inputText.getText().toString()
								+ "\n");
						inputText.setText("");
					}
				} else if (e.getKeyCode() == KeyEvent.VK_F5 && f5 == false) {
					singleAlram.setEditable(true);
					f5 = true;
					singleAlram.requestFocus();
				}

			}
		});

	}

	public void setinfo(Vector v, Socket c, Vector v2) {
		this.v = v;
		this.c = c;
		this.v2 = v2;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();

		if (str.equals("����")) {
			if (!inputText.getText().equals("")) {
				String str2 = inputText.getText().toString();
				split = str2.split("/");
				if (split[0].equals("@w")) {
					for (int i = 0; i < v.size(); i++) {
						ServerInfo info = (ServerInfo) v.get(i);
						if (info.getname().equals(split[1].toString())) {
							info.serwrite("������ �ӼӸ� : " + split[2]);
							content.append(split[1] + "�Կ��� ���� �� : " + split[2]
									+ "\n");
							inputText.setText(split[0] + "/" + split[1] + "/");
							return;
						}
					}
				}
				if (inputText.getText().toString().equals("/û��")) {
					content.setText("ȭ���� û�ҵǾ����ϴ�.\n");
					inputText.setText("");
					return;
				}
				for (int i = 0; i < v.size(); i++) {
					ServerInfo info = (ServerInfo) v.get(i);
					info.serwrite("##### << ���� : "
							+ inputText.getText().toString() + " >> #####");

				}

				content.append("�� : " + inputText.getText().toString() + "\n");
				inputText.setText("");
			}
		} else if (str.equals("����")) {
			result = JOptionPane.showConfirmDialog(null, selection
					+ "���� �����Ͻðڽ��ϱ�?", "���ܱ��", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (result == 0 && !(selection.equals(null))) {
				for (int i = 0; i < v.size(); i++) {
					ServerInfo info = (ServerInfo) v.get(i);
					if (selection.equals(info.getname().toString())) {
						Database data = info.getdata();
						data.ban(info.getid());

						content.append("## " + info.getname()
								+ " ## ���� IP�� ���� ���ϼ̽��ϴ�.\n");
						for (int i1 = 0; i1 < v.size(); i1++) {
							ServerInfo info2 = (ServerInfo) v.get(i1);
							info2.writed("## " + info.getname()
									+ " ## ���� IP�� ���� ���ϼ̽��ϴ�.");
						}

						Socket c = info.getc();
						try {
							c.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						break;
					}

				}

			}

		} else if (str.equals("����")) {
			result = JOptionPane.showConfirmDialog(null, selection
					+ "���� �����Ͻðڽ��ϱ�?", "������", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (result == 0 && !(selection.equals(null))) {

				for (int i = 0; i < v.size(); i++) {
					ServerInfo info = (ServerInfo) v.get(i);
					if (selection.equals(info.getname().toString())) {
						content.append("## " + info.getname()
								+ " ## ���� ���� ���� ���ϼ̽��ϴ�.\n");
						for (int i1 = 0; i1 < v.size(); i1++) {
							ServerInfo info2 = (ServerInfo) v.get(i1);
							info2.writed("## " + info.getname()
									+ " ## ���� ���� ���� ���ϼ̽��ϴ�.");

						}
						Socket c = info.getc();
						try {
							c.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					}

				}
			}
		} else if (str.equals("����")) {
			singleAlram.setEditable(true);
			f5 = true;
		} else if (str.equals("�˸�")) {
			singleAlram.setEditable(false);
			f5 = false;

			if (!singleAlram.getText().toString().equals("")) {
				notice = "�� �� �� �� : " + singleAlram.getText().toString();
				for (int i = 0; i < v.size(); i++) {
					ServerInfo info = (ServerInfo) v.get(i);
					info.serwrite("alram" + "/" + notice);
				}
			}

		} else if (str.equals("����")) {

			con1 = dbdb.getcon();
			try {
				stmt = con1.createStatement();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				settingjd.dispose();
			}

			String str2 = "Select * from CLON_member;";
			try {
				rs = stmt.executeQuery(str2);
			} catch (SQLException e1) {
			}
			if (settingjt.getCellEditor() != null) {
				settingjt.getCellEditor().stopCellEditing();
			} else {

			}

			for (int i = 0; i < dbdb.getcount() + 1; i++) {
				String str3 = "Update CLON_member set pwd = '"
						+ settingjt.getValueAt(i, 1) + "', nicname = '"
						+ settingjt.getValueAt(i, 2) + "', ip = '"
						+ settingjt.getValueAt(i, 3) + "', onoff = '"
						+ settingjt.getValueAt(i, 4) + "', ban = '"
						+ settingjt.getValueAt(i, 5) + "', appcheck = '"
						+ settingjt.getValueAt(i, 6) + "' where id = '"
						+ settingjt.getValueAt(i, 0) + "';";

				try {
					stmt.executeUpdate(str3);
					f8 = false;
					settingjd.dispose();
				} catch (SQLException e1) {
					settingjd.dispose();
				}

			}

		} else if (e.getSource() == jmuit && f8 == false) {
			f8 = true;
			int x = 0, y = 0;
			column = new Object[] { "ID", "Password", "Nicname", "IP",
					"Connect", "Ban", "appcheck" };

			con1 = dbdb.getcon();
			try {
				stmt = con1.createStatement();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				settingjd.dispose();
			}

			String str2 = "Select * from CLON_member;";
			try {
				rs = stmt.executeQuery(str2);
			} catch (SQLException e1) {
				settingjd.dispose();
			}

			memberinfo = new Object[dbdb.getcount() + 1][7];

			try {
				rs.first();
				memberinfo[0][0] = rs.getString("id");
				memberinfo[0][1] = rs.getString("pwd");
				memberinfo[0][2] = rs.getString("nicname");
				memberinfo[0][3] = rs.getString("ip");
				memberinfo[0][4] = rs.getString("onoff");
				memberinfo[0][5] = rs.getString("ban");
				memberinfo[0][6] = rs.getString("appcheck");
				rs.next();
				for (int i = 1; i < dbdb.getcount() + 1; i++) {
					int j = 0;
					memberinfo[i][j] = rs.getString("id");
					j++;
					memberinfo[i][j] = rs.getString("pwd");
					j++;
					memberinfo[i][j] = rs.getString("nicname");
					j++;
					memberinfo[i][j] = rs.getString("ip");
					j++;
					memberinfo[i][j] = rs.getString("onoff");
					j++;
					memberinfo[i][j] = rs.getString("ban");
					j++;
					memberinfo[i][j] = rs.getString("appcheck");
					rs.next();
				}
			} catch (SQLException e1) {

			}
			settingjd = new JDialog(this);
			settingjb = new JButton("����");
			settingjd.setLayout(new BorderLayout());
			settingjt = new JTable(memberinfo, column);
			settingjt.setRowSelectionAllowed(false);
			settingjt.setCellSelectionEnabled(true);
			settingjt.setEnabled(true);
			settingjt.setRowHeight(25);
			settingjd.setSize(700, 300);
			x = (int) ((dm.getWidth() / 2) - (settingjd.getWidth() / 2));
			y = (int) ((dm.getHeight() / 2) - (settingjd.getHeight() / 2));
			settingjd.setLocation(new Point(x, y));
			settingjs = new JScrollPane(settingjt);
			settingjp = new JPanel();
			settingjd.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					// TODO Auto-generated method stub
					f8 = false;
					super.windowClosing(e);
				}

			});
			settingjb.addActionListener(this);
			settingjp.add(settingjb);
			settingjd.setVisible(true);
			settingjd.add(settingjs, "Center");
			settingjd.add(settingjp, "South");

		} else if (e.getSource() == jpmit1) {
			inputText.setText("@w/" + selection + "/");
			inputText.requestFocus();
		} else if (e.getSource() == jpmit2 && f9 == false) {
			f9 = true;
			int x, y;
			jdsend = new JDialog(this);
			jp01 = new JPanel();
			jp02 = new JPanel();
			jp01_jl1 = new JLabel("�޴� ��� : ", JLabel.RIGHT);
			jp01_jl2 = new JLabel(selection, JLabel.CENTER);
			jp02_jl = new JLabel("���� : ");
			jp02_ja = new JTextArea();
			jbsend = new JButton("������");

			jdsend.setLayout(new BorderLayout());
			jdsend.setSize(200, 200);
			jp01.setLayout(new FlowLayout());
			jp02.setLayout(new BorderLayout());
			jdsend.setVisible(true);
			jp01.add(jp01_jl1);
			jp01.add(jp01_jl2);
			jp02.add(jp02_jl, "West");
			jp02.add(jp02_ja, "Center");

			jdsend.add(jp01, "North");
			jdsend.add(jp02, "Center");
			jdsend.add(jbsend, "South");
			x = (int) ((dm.getWidth() / 2) - (jdsend.getWidth() / 2));
			y = (int) ((dm.getHeight() / 2) - (jdsend.getHeight() / 2));
			jdsend.setLocation(new Point(x, y));

			jbsend.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO �ڵ� ������ �޼ҵ� ����
					f9 = false;
					String str3;
					String str4 = "letter/Server/" + selection + "/";
					String split2[];
					str3 = jp02_ja.getText().toString();
					split2 = str3.split("\n");
					if (!jp02_ja.getText().toString().equals("")) {
						if (split2.length >= 1) {
							for (int i = 0; i < v.size(); i++) {
								ServerInfo info = (ServerInfo) v.get(i);
								if (info.getname().equals(selection)) {
									for (int j = 0; j < split2.length; j++) {
										str4 += split2[j] + "@";
									}
									info.serwrite(str4);
								}

							}
						}
					}
					jdsend.dispose();
				}
			});

			jdsend.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					// TODO Auto-generated method stub
					f9 = false;
					super.windowClosing(e);
				}

			});

		} else if (e.getSource() == jpmit2 && f9 == true) {
			jp02_ja.requestFocus();
		} else if (e.getSource() == jmuit2 && f10 == false) {
			f10 = true;
			int x, y;
			
			secretjd.setSize(400, 400);
			secretjd.setLayout(new BorderLayout());
			secretjp.setLayout(new GridLayout(1, 1));
			secretja.setEditable(false);
			secretjp.add(secretjsp);
			secretjd.add(secretjp, "Center");
			secretjd.add(secretjb,"South");

			x = (int) ((dm.getWidth() / 2) - (secretjd.getWidth() / 2));
			y = (int) ((dm.getHeight() / 2) - (secretjd.getHeight() / 2));
			secretjd.setLocation(new Point(x, y));

			secretjd.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					// TODO Auto-generated method stub
					f10 = false;
					secretjd.dispose();
					super.windowClosing(e);
				}

			});
			
			secretjd.setVisible(true);

		}
	}

}
