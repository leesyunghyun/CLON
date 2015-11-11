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

	private JTextField id; // ���̵� �Է��ʵ�
	private JTextField pw; // �н����� �Է��ʵ�
	private JButton lg; // �α��� ��ư
	private JButton mem; // ȸ������ ��ư
	private JButton find; // ȸ��ã�� ��ư
	private JPanel jp1; // �α����Է� �г�(���̵�)
	private JPanel jp2; // ��ư �г�
	private JPanel jp3; // ��й�ȣ�Է� �г�(��й�ȣ)
	private JPanel jp4; // �α�����ü �г�
	private JDialog jd;
	private Dimension dm = Toolkit.getDefaultToolkit().getScreenSize(); // ȭ��
	private LoginSocket login; // �������
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
			jd = new JDialog(); // ���̾�α׻���
			jd.setLayout(new BorderLayout()); // ���̾ƿ�

			// �г�����
			jp1 = new JPanel(new FlowLayout()); // �α����Է��г�(���̵�)
			jp2 = new JPanel(new GridLayout(1, 3)); // ��ư �г� 3��
			jp3 = new JPanel(new FlowLayout()); // �α����Է��г�(��й�ȣ)
			jp4 = new JPanel(new BorderLayout()); // ��ü�� ���� �г�
			//

			// �ؽ�Ʈ�ʵ� �� ��ư ����
			id = new JTextField(10); // id �Է�â
			pw = new JTextField(10); // pw �Է�â
			lg = new JButton("�α���"); // �α��ι�ư
			mem = new JButton("ȸ������"); // ȸ�����Թ�ư
			find = new JButton("ID/PW ã��"); // ����ã�� ��ư

			// �� �гο� ��ư ���� ����
			jp1.add(new JLabel(" ���� �� �� : ")); // �α����г�(���̵�)�� ���̵� ����
			jp1.add(id);

			jp2.add(lg);
			jp2.add(mem); // ��ư�гο� ��ư3�� ����
			jp2.add(find);

			jp3.add(new JLabel("�� �� �� ȣ : ")); // �α����г�(��й�ȣ)�� ��й�ȣ ����
			jp3.add(pw);

			jp4.add(jp1, "North"); // ��ü �гο� �α��� �гΰ� ��й�ȣ �г� ����
			jp4.add(jp3, "Center");

			//
			jd.add(jp4, "Center"); // ���̾�α׿� ��ü�гΰ� ��ư�г� ����
			jd.add(jp2, "South");

			// ���̾�α� ����
			jd.setTitle("�α���");// �α��� â�� Ÿ��Ʋ
			jd.setSize(297, 130); // ũ��

			int x = 0, y = 0; // ȭ�� �ʱⰪ
			// ȭ�鼳�����ֱ�
			x = (int) ((dm.getWidth() / 2) - (jd.getWidth() / 2));
			y = (int) ((dm.getHeight() / 2) - (jd.getHeight() / 2));
			//

			jd.setLocation(new Point(x, y)); // ȭ����ġ�� ����� �̵���Ŵ
			jd.setResizable(false); // ũ�⺯��Ұ���
			// ����

			//
			// jd.setModal(true); // ����â�� �����Ű�� ���� ���

			// �̺�Ʈó��
			//
			jd.addWindowListener(new WindowAdapter() { // �����̺�Ʈ

				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
					super.windowClosing(e);
				}

			});

			// ��ư�̺�Ʈ
			lg.addActionListener(new ActionListener() { // �α��� �̺�Ʈ

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
										"������ ����Ǿ����ϴ�.���α׷��� �ٽ� ��������ּ���.",
										"������������", JOptionPane.CLOSED_OPTION,
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
										"ȸ�������� �߸��Ǿ��ų� ȸ�����Խ� ��ϵ�IP�� �ƴմϴ�.)",
										"���", JOptionPane.CLOSED_OPTION,
										JOptionPane.WARNING_MESSAGE);
							} else if (LoginSocket.name.equals("ban")) {
								JOptionPane.showConfirmDialog(jd,
										"���� IP�� ���ܵ� ���·� ���� �̿��� �Ұ����մϴ�. ",
										"����", JOptionPane.CLOSED_OPTION,
										JOptionPane.ERROR_MESSAGE);
							} else {
								JOptionPane.showConfirmDialog(jd,
										"���� �������Դϴ�.\n�ߺ������� �Ұ����մϴ�. ", "���",
										JOptionPane.CLOSED_OPTION,
										JOptionPane.WARNING_MESSAGE);
							}
						}
					} else {
						JOptionPane.showConfirmDialog(jd, "��� �Է����ּ���. ", "���",
								JOptionPane.CLOSED_OPTION,
								JOptionPane.WARNING_MESSAGE);

					}
				}

			});

			mem.addActionListener(new ActionListener() { // ȸ������ �̺�Ʈ

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
											"������ ����Ǿ����ϴ�.���α׷��� �ٽ� ��������ּ���.",
											"������������",
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
													"ȸ���� ��ϵ� IP�� �ƴմϴ�.\n(ȸ�����Դ���� ip�θ� ���Ӱ����մϴ�.)",
													"���",
													JOptionPane.CLOSED_OPTION,
													JOptionPane.WARNING_MESSAGE);
								} else {
									JOptionPane.showConfirmDialog(jd,
											"���̵�� ��й�ȣ�� �ٽ� Ȯ�����ּ��� ", "���",
											JOptionPane.CLOSED_OPTION,
											JOptionPane.WARNING_MESSAGE);
								}
							}
						} else {
							JOptionPane.showConfirmDialog(jd, "��� �Է����ּ���. ",
									"���", JOptionPane.CLOSED_OPTION,
									JOptionPane.WARNING_MESSAGE);

						}
					}

				}
			});
			find.addActionListener(new ActionListener() { // ȸ������ã�� �̺�Ʈ

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					FindipUI fip = new FindipUI(login);
				}
			});
			jd.setVisible(true);
		} else {
			JOptionPane.showConfirmDialog(jd, "�߸��� IP �Ǵ� PORT �Դϴ�.", "����",
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
			jd = new JDialog(); // ���̾�α׻���
			jd.setLayout(new BorderLayout()); // ���̾ƿ�

			// �г�����
			jp1 = new JPanel(new FlowLayout()); // �α����Է��г�(���̵�)
			jp2 = new JPanel(new GridLayout(1, 3)); // ��ư �г� 3��
			jp3 = new JPanel(new FlowLayout()); // �α����Է��г�(��й�ȣ)
			jp4 = new JPanel(new BorderLayout()); // ��ü�� ���� �г�
			//

			// �ؽ�Ʈ�ʵ� �� ��ư ����
			id = new JTextField(10); // id �Է�â
			pw = new JTextField(10); // pw �Է�â
			lg = new JButton("�α���"); // �α��ι�ư
			mem = new JButton("ȸ������"); // ȸ�����Թ�ư
			find = new JButton("ID/PW ã��"); // ����ã�� ��ư

			// �� �гο� ��ư ���� ����
			jp1.add(new JLabel(" ���� �� �� : ")); // �α����г�(���̵�)�� ���̵� ����
			jp1.add(id);

			jp2.add(lg);
			jp2.add(mem); // ��ư�гο� ��ư3�� ����
			jp2.add(find);

			jp3.add(new JLabel("�� �� �� ȣ : ")); // �α����г�(��й�ȣ)�� ��й�ȣ ����
			jp3.add(pw);

			jp4.add(jp1, "North"); // ��ü �гο� �α��� �гΰ� ��й�ȣ �г� ����
			jp4.add(jp3, "Center");

			//
			jd.add(jp4, "Center"); // ���̾�α׿� ��ü�гΰ� ��ư�г� ����
			jd.add(jp2, "South");

			// ���̾�α� ����
			jd.setTitle("�α���");// �α��� â�� Ÿ��Ʋ
			jd.setSize(297, 130); // ũ��

			int x = 0, y = 0; // ȭ�� �ʱⰪ
			// ȭ�鼳�����ֱ�
			x = (int) ((dm.getWidth() / 2) - (jd.getWidth() / 2));
			y = (int) ((dm.getHeight() / 2) - (jd.getHeight() / 2));
			//

			jd.setLocation(new Point(x, y)); // ȭ����ġ�� ����� �̵���Ŵ
			jd.setResizable(false); // ũ�⺯��Ұ���
			// ����

			//
			// jd.setModal(true); // ����â�� �����Ű�� ���� ���

			// �̺�Ʈó��
			//
			jd.addWindowListener(new WindowAdapter() { // �����̺�Ʈ

				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
					super.windowClosing(e);
				}

			});

			// ��ư�̺�Ʈ
			lg.addActionListener(new ActionListener() { // �α��� �̺�Ʈ

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
										"������ ����Ǿ����ϴ�.���α׷��� �ٽ� ��������ּ���.",
										"������������", JOptionPane.CLOSED_OPTION,
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
										"ȸ���� �ƴϰų� ȸ�����Խ� ��ϵ� IP�� �ƴմϴ�.)", "���",
										JOptionPane.CLOSED_OPTION,
										JOptionPane.WARNING_MESSAGE);
							} else if (LoginSocket.name.equals("ban")) {
								JOptionPane.showConfirmDialog(jd,
										"���� IP�� ���ܵ� ���·� ���� �̿��� �Ұ����մϴ�. ",
										"����", JOptionPane.CLOSED_OPTION,
										JOptionPane.ERROR_MESSAGE);
							} else {
								JOptionPane.showConfirmDialog(jd,
										"���� �������Դϴ�.\n�ߺ������� �Ұ����մϴ�. ", "���",
										JOptionPane.CLOSED_OPTION,
										JOptionPane.WARNING_MESSAGE);
							}
						}
					} else {
						JOptionPane.showConfirmDialog(jd, "��� �Է����ּ���. ", "���",
								JOptionPane.CLOSED_OPTION,
								JOptionPane.WARNING_MESSAGE);

					}
				}

			});

			mem.addActionListener(new ActionListener() { // ȸ������ �̺�Ʈ

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
											"������ ����Ǿ����ϴ�.���α׷��� �ٽ� ��������ּ���.",
											"������������",
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
													"ȸ���� ��ϵ� IP�� �ƴմϴ�.\n(ȸ�����Դ���� ip�θ� ���Ӱ����մϴ�.)",
													"���",
													JOptionPane.CLOSED_OPTION,
													JOptionPane.WARNING_MESSAGE);
								} else {
									JOptionPane.showConfirmDialog(jd,
											"���̵�� ��й�ȣ�� �ٽ� Ȯ�����ּ��� ", "���",
											JOptionPane.CLOSED_OPTION,
											JOptionPane.WARNING_MESSAGE);
								}
							}
						} else {
							JOptionPane.showConfirmDialog(jd, "��� �Է����ּ���. ",
									"���", JOptionPane.CLOSED_OPTION,
									JOptionPane.WARNING_MESSAGE);

						}
					}

				}
			});
			find.addActionListener(new ActionListener() { // ȸ������ã�� �̺�Ʈ

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					FindipUI fip = new FindipUI(login);
				}
			});
			jd.setVisible(true);
		} else {
			JOptionPane.showConfirmDialog(jd, "�߸��� IP �Ǵ� PORT �Դϴ�.", "����",
					JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			BeginSettingFile.writeData(this.ip + "/" + this.port + "/"
					+ "false/x\n");
		}

	}
}
