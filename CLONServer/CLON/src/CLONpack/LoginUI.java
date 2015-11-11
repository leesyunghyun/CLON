package CLONpack;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginUI {

	private JTextField id; // 아이디 입력필드
	private JTextField pw; // 패스워드 입력필드
	private JButton lg; // 로그인 버튼
	private JButton mem; // 회원가입 버튼
	private JButton find; // 회원찾기 버튼
	private JPanel jp1; // 로그인입력 패널(아이디)
	private JPanel jp2; // 버튼 패널
	private JPanel jp3; // 비밀번호입력 패널(비밀번호)
	private JPanel jp4; // 로그인전체 패널
	private JDialog jd;
	private Dimension dm = Toolkit.getDefaultToolkit().getScreenSize(); // 화면
	private LoginSocket login; // 가운데설정
	private String ip;
	private String port;
	private SettingUi ui;

	public LoginUI(String ip, String port, SettingUi ui) {
		this.ip = ip;
		this.port = port;
		this.ui = ui;
		login = new LoginSocket(this.ip, this.port, this);
		if (LoginSocket.checked) {
			ui.dispose();
			jd = new JDialog(); // 다이얼로그생성
			jd.setLayout(new BorderLayout()); // 레이아웃

			// 패널정의
			jp1 = new JPanel(new FlowLayout()); // 로그인입력패널(아이디)
			jp2 = new JPanel(new GridLayout(1, 3)); // 버튼 패널 3개
			jp3 = new JPanel(new FlowLayout()); // 로그인입력패널(비밀번호)
			jp4 = new JPanel(new BorderLayout()); // 전체를 담을 패널
			//

			// 텍스트필드 및 버튼 정의
			id = new JTextField(10); // id 입력창
			pw = new JTextField(10); // pw 입력창
			lg = new JButton("로그인"); // 로그인버튼
			mem = new JButton("회원가입"); // 회원가입버튼
			find = new JButton("ID/PW 찾기"); // 정보찾기 버튼

			// 각 패널에 버튼 등을 삽입
			jp1.add(new JLabel(" 　아 이 디 : ")); // 로그인패널(아이디)에 아이디 삽입
			jp1.add(id);

			jp2.add(lg);
			jp2.add(mem); // 버튼패널에 버튼3개 삽입
			jp2.add(find);

			jp3.add(new JLabel("비 밀 번 호 : ")); // 로그인패널(비밀번호)에 비밀번호 삽입
			jp3.add(pw);

			jp4.add(jp1, "North"); // 전체 패널에 로그인 패널과 비밀번호 패널 삽입
			jp4.add(jp3, "Center");

			//
			jd.add(jp4, "Center"); // 다이얼로그에 전체패널과 버튼패널 삽입
			jd.add(jp2, "South");

			// 다이얼로그 셋팅
			jd.setTitle("로그인");// 로그인 창의 타이틀
			jd.setSize(297, 130); // 크기

			int x = 0, y = 0; // 화면 초기값
			// 화면설정해주기
			x = (int) ((dm.getWidth() / 2) - (jd.getWidth() / 2));
			y = (int) ((dm.getHeight() / 2) - (jd.getHeight() / 2));
			//

			jd.setLocation(new Point(x, y)); // 화면위치를 가운데로 이동시킴
			jd.setResizable(false); // 크기변경불가능
			// 투명도

			//
			// jd.setModal(true); // 다음창을 실행시키기 위해 대기

			// 이벤트처리
			//
			jd.addWindowListener(new WindowAdapter() { // 종료이벤트

				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
					super.windowClosing(e);
				}

			});

			// 버튼이벤트
			lg.addActionListener(new ActionListener() { // 로그인 이벤트

				@Override
				public void actionPerformed(ActionEvent arg0) {

					if (!(id.getText().equals("") || pw.getText().toString()
							.equals(""))) {
						login.setting(id.getText().toString(), pw.getText()
								.toString());
						boolean result = login.connect();
						if (result) {
							jd.dispose();
							ClientTh th = new ClientTh(login);
							new ClientUi(th);
							th.start();

						} else {
							if (LoginSocket.name.equals("Serverexit")) {
								JOptionPane.showConfirmDialog(jd,
										"서버가 종료되었습니다.프로그램을 다시 실행시켜주세요.",
										"서버접속종료", JOptionPane.CLOSED_OPTION,
										JOptionPane.ERROR_MESSAGE);
								try {
									Thread.sleep(1000);
									System.exit(0);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else if (LoginSocket.name.equals("false")) {
								JOptionPane.showConfirmDialog(jd,
										"회원정보가 잘못되었거나 회원가입시 등록된IP가 아닙니다.)",
										"경고", JOptionPane.CLOSED_OPTION,
										JOptionPane.WARNING_MESSAGE);
							} else if (LoginSocket.name.equals("ban")) {
								JOptionPane.showConfirmDialog(jd,
										"현재 IP가 차단된 상태로 서비스 이용이 불가능합니다. ",
										"차단", JOptionPane.CLOSED_OPTION,
										JOptionPane.ERROR_MESSAGE);
							} else {
								JOptionPane.showConfirmDialog(jd,
										"현재 접속중입니다.\n중복접속은 불가능합니다. ", "경고",
										JOptionPane.CLOSED_OPTION,
										JOptionPane.WARNING_MESSAGE);
							}
						}
					} else {
						JOptionPane.showConfirmDialog(jd, "모두 입력해주세요. ", "경고",
								JOptionPane.CLOSED_OPTION,
								JOptionPane.WARNING_MESSAGE);

					}
				}

			});

			mem.addActionListener(new ActionListener() { // 회원가입 이벤트

				@Override
				public void actionPerformed(ActionEvent arg0) {

					new NewmemUI(login);
				}
			});
			pw.addKeyListener(new KeyListener() {

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
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						if (!(id.getText().equals("") || pw.getText()
								.toString().equals(""))) {
							login.setting(id.getText().toString(), pw.getText()
									.toString());
							boolean result = login.connect();
							if (result) {
								jd.dispose();
								ClientTh th = new ClientTh(login);
								new ClientUi(th);
								th.start();

							} else {
								if (LoginSocket.name.equals("Serverexit")) {
									JOptionPane.showConfirmDialog(jd,
											"서버가 종료되었습니다.프로그램을 다시 실행시켜주세요.",
											"서버접속종료",
											JOptionPane.CLOSED_OPTION,
											JOptionPane.ERROR_MESSAGE);
									try {
										Thread.sleep(1000);
										System.exit(0);
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								} else if (LoginSocket.name.equals("false")) {
									JOptionPane
											.showConfirmDialog(
													jd,
													"회원에 등록된 IP가 아닙니다.\n(회원가입당시의 ip로만 접속가능합니다.)",
													"경고",
													JOptionPane.CLOSED_OPTION,
													JOptionPane.WARNING_MESSAGE);
								} else {
									JOptionPane.showConfirmDialog(jd,
											"아이디와 비밀번호를 다시 확인해주세요 ", "경고",
											JOptionPane.CLOSED_OPTION,
											JOptionPane.WARNING_MESSAGE);
								}
							}
						} else {
							JOptionPane.showConfirmDialog(jd, "모두 입력해주세요. ",
									"경고", JOptionPane.CLOSED_OPTION,
									JOptionPane.WARNING_MESSAGE);

						}
					}

				}
			});
			find.addActionListener(new ActionListener() { // 회원정보찾기 이벤트

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					FindipUI fip = new FindipUI(login);
				}
			});
			jd.setVisible(true);
		} else {
			JOptionPane.showConfirmDialog(jd, "잘못된 IP 또는 PORT 입니다.", "에러",
					JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			BeginSettingFile.writeData(this.ip + "/" + this.port + "/"
					+ "false/x\n");
		}

	}

	public LoginUI(String ip, String port) {
		this.ip = ip;
		this.port = port;
		login = new LoginSocket(this.ip, this.port, this);
		if (LoginSocket.checked) {
			jd = new JDialog(); // 다이얼로그생성
			jd.setLayout(new BorderLayout()); // 레이아웃

			// 패널정의
			jp1 = new JPanel(new FlowLayout()); // 로그인입력패널(아이디)
			jp2 = new JPanel(new GridLayout(1, 3)); // 버튼 패널 3개
			jp3 = new JPanel(new FlowLayout()); // 로그인입력패널(비밀번호)
			jp4 = new JPanel(new BorderLayout()); // 전체를 담을 패널
			//

			// 텍스트필드 및 버튼 정의
			id = new JTextField(10); // id 입력창
			pw = new JTextField(10); // pw 입력창
			lg = new JButton("로그인"); // 로그인버튼
			mem = new JButton("회원가입"); // 회원가입버튼
			find = new JButton("ID/PW 찾기"); // 정보찾기 버튼

			// 각 패널에 버튼 등을 삽입
			jp1.add(new JLabel(" 　아 이 디 : ")); // 로그인패널(아이디)에 아이디 삽입
			jp1.add(id);

			jp2.add(lg);
			jp2.add(mem); // 버튼패널에 버튼3개 삽입
			jp2.add(find);

			jp3.add(new JLabel("비 밀 번 호 : ")); // 로그인패널(비밀번호)에 비밀번호 삽입
			jp3.add(pw);

			jp4.add(jp1, "North"); // 전체 패널에 로그인 패널과 비밀번호 패널 삽입
			jp4.add(jp3, "Center");

			//
			jd.add(jp4, "Center"); // 다이얼로그에 전체패널과 버튼패널 삽입
			jd.add(jp2, "South");

			// 다이얼로그 셋팅
			jd.setTitle("로그인");// 로그인 창의 타이틀
			jd.setSize(297, 130); // 크기

			int x = 0, y = 0; // 화면 초기값
			// 화면설정해주기
			x = (int) ((dm.getWidth() / 2) - (jd.getWidth() / 2));
			y = (int) ((dm.getHeight() / 2) - (jd.getHeight() / 2));
			//

			jd.setLocation(new Point(x, y)); // 화면위치를 가운데로 이동시킴
			jd.setResizable(false); // 크기변경불가능
			// 투명도

			//
			// jd.setModal(true); // 다음창을 실행시키기 위해 대기

			// 이벤트처리
			//
			jd.addWindowListener(new WindowAdapter() { // 종료이벤트

				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
					super.windowClosing(e);
				}

			});

			// 버튼이벤트
			lg.addActionListener(new ActionListener() { // 로그인 이벤트

				@Override
				public void actionPerformed(ActionEvent arg0) {

					if (!(id.getText().equals("") || pw.getText().toString()
							.equals(""))) {
						login.setting(id.getText().toString(), pw.getText()
								.toString());
						boolean result = login.connect();
						if (result) {
							jd.dispose();
							ClientTh th = new ClientTh(login);
							new ClientUi(th);
							th.start();

						} else {
							if (LoginSocket.name.equals("Serverexit")) {
								JOptionPane.showConfirmDialog(jd,
										"서버가 종료되었습니다.프로그램을 다시 실행시켜주세요.",
										"서버접속종료", JOptionPane.CLOSED_OPTION,
										JOptionPane.ERROR_MESSAGE);
								try {
									Thread.sleep(1000);
									System.exit(0);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else if (LoginSocket.name.equals("false")) {
								JOptionPane.showConfirmDialog(jd,
										"회원이 아니거나 회원가입시 등록된 IP가 아닙니다.)", "경고",
										JOptionPane.CLOSED_OPTION,
										JOptionPane.WARNING_MESSAGE);
							} else if (LoginSocket.name.equals("ban")) {
								JOptionPane.showConfirmDialog(jd,
										"현재 IP가 차단된 상태로 서비스 이용이 불가능합니다. ",
										"차단", JOptionPane.CLOSED_OPTION,
										JOptionPane.ERROR_MESSAGE);
							} else {
								JOptionPane.showConfirmDialog(jd,
										"현재 접속중입니다.\n중복접속은 불가능합니다. ", "경고",
										JOptionPane.CLOSED_OPTION,
										JOptionPane.WARNING_MESSAGE);
							}
						}
					} else {
						JOptionPane.showConfirmDialog(jd, "모두 입력해주세요. ", "경고",
								JOptionPane.CLOSED_OPTION,
								JOptionPane.WARNING_MESSAGE);

					}
				}

			});

			mem.addActionListener(new ActionListener() { // 회원가입 이벤트

				@Override
				public void actionPerformed(ActionEvent arg0) {

					new NewmemUI(login);
				}
			});
			pw.addKeyListener(new KeyListener() {

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
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						if (!(id.getText().equals("") || pw.getText()
								.toString().equals(""))) {
							login.setting(id.getText().toString(), pw.getText()
									.toString());
							boolean result = login.connect();
							if (result) {
								jd.dispose();
								ClientTh th = new ClientTh(login);
								new ClientUi(th);
								th.start();

							} else {
								if (LoginSocket.name.equals("Serverexit")) {
									JOptionPane.showConfirmDialog(jd,
											"서버가 종료되었습니다.프로그램을 다시 실행시켜주세요.",
											"서버접속종료",
											JOptionPane.CLOSED_OPTION,
											JOptionPane.ERROR_MESSAGE);
									try {
										Thread.sleep(1000);
										System.exit(0);
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								} else if (LoginSocket.name.equals("false")) {
									JOptionPane
											.showConfirmDialog(
													jd,
													"회원에 등록된 IP가 아닙니다.\n(회원가입당시의 ip로만 접속가능합니다.)",
													"경고",
													JOptionPane.CLOSED_OPTION,
													JOptionPane.WARNING_MESSAGE);
								} else {
									JOptionPane.showConfirmDialog(jd,
											"아이디와 비밀번호를 다시 확인해주세요 ", "경고",
											JOptionPane.CLOSED_OPTION,
											JOptionPane.WARNING_MESSAGE);
								}
							}
						} else {
							JOptionPane.showConfirmDialog(jd, "모두 입력해주세요. ",
									"경고", JOptionPane.CLOSED_OPTION,
									JOptionPane.WARNING_MESSAGE);

						}
					}

				}
			});
			find.addActionListener(new ActionListener() { // 회원정보찾기 이벤트

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					FindipUI fip = new FindipUI(login);
				}
			});
			jd.setVisible(true);
		} else {
			JOptionPane.showConfirmDialog(jd, "잘못된 IP 또는 PORT 입니다.", "에러",
					JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			BeginSettingFile.writeData(this.ip + "/" + this.port + "/"
					+ "false/x\n");
		}

	}
}
