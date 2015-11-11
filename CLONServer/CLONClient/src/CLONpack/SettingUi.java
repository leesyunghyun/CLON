package CLONpack;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingUi extends JDialog implements ActionListener {
	public static JTextField ip; // ���̵� �Է��ʵ�
	public static JTextField port; // �н����� �Է��ʵ�
	private JButton logincon; // ä�����ӹ�ư
	private JButton exit; // �ݱ� ��ư
	private JPanel jp1; // IP�Է� �г�(���̵�)
	private JPanel jp2; // ��ư �г�
	private JPanel jp3; // PORT�Է� �г�
	private JPanel jp4; // �α�����ü �г�
	private JPanel jp5; // üũ �ڽ�
	private JCheckBox ch;
	private Dimension dm = Toolkit.getDefaultToolkit().getScreenSize(); // ȭ��
	private BeginSettingFile bg;

	public SettingUi(BeginSettingFile bg) {
		this.bg = bg;
		this.setLayout(new BorderLayout()); // ���̾ƿ�
		// �г�����
		jp1 = new JPanel(new FlowLayout()); // ����IP�Է� �г�
		jp2 = new JPanel(new GridLayout(1, 3)); // ��ư �г� 3��
		jp3 = new JPanel(new FlowLayout()); // ���� PORT �Է��г�
		jp4 = new JPanel(new BorderLayout()); // ��ü�� ���� �г�
		jp5 = new JPanel(new FlowLayout()); // �ڵ����� üũ�ڽ�
		//

		// �ؽ�Ʈ�ʵ� �� ��ư ����
		ip = new JTextField(10); // ip �Է�â
		port = new JTextField(10); // port �Է�â
		logincon = new JButton("�����Ϸ�"); // �α��ο����ư
		exit = new JButton("�ݱ�"); // �ݱ��ư
		ch = new JCheckBox("�ڵ� ����"); // üũ�ڽ�
		//
		// üũ�ڽ� �̺�Ʈ
		ch.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					BeginSettingFile.savecheck = true;
				} else {
					BeginSettingFile.savecheck = false;
				}

			}
		});

		//
		// üũ�ڽ� ����
		jp5.add(ch);
		//

		// �̺�Ʈ ��ư
		logincon.addActionListener(this);
		exit.addActionListener(this);
		//

		// �� �гο� ��ư ���� ����
		jp1.add(new JLabel(" ServerIp : ")); // ����ip
		jp1.add(ip);

		jp2.add(logincon);
		jp2.add(exit); // ��ư�гο� ��ư 2��

		jp3.add(new JLabel(" ClonPort : ")); // ���� port
		jp3.add(port);

		jp4.add(jp1, "North"); //
		jp4.add(jp3, "Center");
		jp4.add(jp5, "South");

		//
		this.add(jp4, "Center");
		this.add(jp2, "South");

		// ���̾�α� ����
		this.setTitle("�ʱ� ����");// ������ Ÿ��Ʋ
		this.setSize(230, 155); // ũ��
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
				super.windowClosing(e);
			}
		});
		int x = 0, y = 0; // ȭ�� �ʱⰪ
		// ȭ�鼳�����ֱ�
		x = (int) ((dm.getWidth() / 2) - (this.getWidth() / 2));
		y = (int) ((dm.getHeight() / 2) - (this.getHeight() / 2));
		//

		this.setLocation(new Point(x, y)); // ȭ����ġ�� ����� �̵���Ŵ
		this.setResizable(false); // ũ�⺯��Ұ���
		this.setVisible(true); // ����

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "�����Ϸ�") {
			bg.writeData(ip.getText().toString().trim() + "/"
					+ port.getText().toString().trim() + "/"
					+ String.valueOf(BeginSettingFile.savecheck) + "/o\n");
			new LoginUI(ip.getText().toString().trim(), port.getText()
					.toString().trim(), this);
		} else {
			System.exit(0);
		}

	}

}
