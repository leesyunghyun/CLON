package CLONpack;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class FindipUI extends JDialog {
	private LoginSocket login;
	Dimension dm = Toolkit.getDefaultToolkit().getScreenSize(); // ȭ����ġ �߾�����

	private String ip;
	private Socket c; // �����ϱ� ���� �۾�

	public FindipUI(LoginSocket login) {
		this.login = login;
		int x, y; // ȭ����ġ�� �߾����� �����ϱ� ���� ����
		x = (int) ((dm.getWidth() / 2) - (this.getWidth() / 2));
		y = (int) ((dm.getHeight() / 2) - (this.getHeight() / 2));

		findid fiid = new findid(login); // idã�� �г�
		findpw fipw = new findpw(login); // ��й�ȣ ã�� �г�

		JTabbedPane jtp = new JTabbedPane(); // �гε��� ���� ��

		jtp.add("IDã��", fiid); // �ǿ� id ã�� �г��� ����
		jtp.add("PWã��", fipw); // �ǿ� ��й�ȣ ã�� �г��� ����

		this.getContentPane().add(jtp); // ���̾�α׿� ���� ���� ����
		this.setSize(250, 140); // ũ�⼳��

		this.setLocation(new Point(x, y)); // ȭ����ġ����
		this.setTitle("ID/PW ã��"); // Ÿ��Ʋ����
		this.setResizable(false);
		this.setVisible(true); // ���� ����
	}

}

// ���̵�ã��� �����Ǹ� ���ؼ� ã�� �� ����
class findid extends JPanel implements ActionListener {
	private JButton find; // ã���ư
	private JTextField jip; // �������Է�â
	private JLabel jl; // �����Ƕ�
	private LoginSocket login;
	private String ip;
	private Socket c;
	private BufferedWriter bw;
	private BufferedReader br;
	private boolean result;
	private String message;
	private String split[];

	public findid(LoginSocket login) {
		this.login = login;
		this.br = this.login.getBr();
		this.bw = this.login.getBw();
		c = login.getc();

		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER)); // �г���
																	// �÷ο췹�̾ƿ�����
																	// ����

		find = new JButton("ã��"); // ã�� ��ư
		jip = new JTextField(15); // �������Է�â
		jl = new JLabel("������ : ", JLabel.CENTER); // ������ ��

		jp.add(jl); // �гο� ���̵���� ����
		jp.add(jip); // �гο� �������Է�â�� ����

		find.addActionListener(this); // ã���ư�׼��̺�Ʈ

		this.setLayout(new BorderLayout()); // ���� �г��� �������̾ƿ����� ����
		this.add(find, "South"); // ã���ư�� ���� �г� ���ʿ� ����
		this.add(jp, "Center"); // jp�г��� ���� �г� �߾ӿ� ����
		jip.setText(ip);
		jip.setEnabled(false);
	}

	// ��ư �׼��̺�Ʈ
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand() == "ã��") { // ã�� ��ư�� ���� �׼�
			if (!jip.getText().equals("")) {
				try {
					bw.write("findid/" + ip + "\n");
					bw.flush();
					message = br.readLine();
					split = message.split("/");
				} catch (IOException e1) {

					e1.printStackTrace();
				}

				if (split[0].equals("true")) {
					JOptionPane.showConfirmDialog(this, "ã���ô� ���̵��  "
							+ split[1], "ID ã��", JOptionPane.CLOSED_OPTION,
							JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showConfirmDialog(this,
							"ȸ�����Խ� ���� ID�� �ƴմϴ�.  ", "���̵� ã��ERROR",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.WARNING_MESSAGE);
				}

			}
		}
	}
}

// ///////////////

// ��й�ȣ ã��� ���̵�� �������ʿ�
class findpw extends JPanel implements ActionListener {
	private JButton find; // ã���ư ����
	private JTextField jip, jid; // ���̵�� �������Է�â
	private JLabel jl; // �����Ƕ�
	private JLabel jll; // ���̵��
	private LoginSocket login;
	private String ip;
	private Socket c;
	private BufferedWriter bw;
	private BufferedReader br;
	private boolean result;
	private String message;
	private String split[];

	public findpw(LoginSocket login) {
		this.login = login;
		this.br = this.login.getBr();
		this.bw = this.login.getBw();
		c = login.getc();
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JPanel jp = new JPanel(new GridLayout(2, 2)); // jp�г��� �׸��� ���̾ƿ����� ����

		find = new JButton("ã��"); // ã���ư
		jip = new JTextField(10); // ip�Է�â
		jid = new JTextField(10); // id�Է�â
		jl = new JLabel("������ : ", JLabel.CENTER); // �����Ƕ�
		jll = new JLabel("���̵� : ", JLabel.CENTER); // ���̵��

		jp.add(jl); // jp�гο� �����Ƕ��� ����
		jp.add(jip); // jp�гο� �������Է�â�� ����
		jp.add(jll); // jp�гο� ���̵���� ����
		jp.add(jid); // jp�гο� ���̵��Է�â�� ����

		find.addActionListener(this); // ã���ư�׼��̺�Ʈ

		this.setLayout(new BorderLayout()); // �����г��� �������̾ƿ����� ����
		this.add(find, "South"); // �����гο� ã���ư�� ���ʿ� ����
		this.add(jp, "North"); // �����гο� jp�г��� ���ʿ� ����
		jip.setText(ip);
		jip.setEnabled(false);
	}

	// ��ư �׼��̺�Ʈ
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand() == "ã��") { // ã���ư �׼��̺�Ʈ
			if (!jip.getText().equals("")) {
				try {
					bw.write("findidpw/" + jid.getText().toString() + "/" + ip
							+ "\n");
					bw.flush();
					message = br.readLine();
					split = message.split("/");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				if (split[0].equals("true")) {
					JOptionPane.showConfirmDialog(this, "ã���ô� ��й�ȣ��  "
							+ split[1], "��й�ȣã��", JOptionPane.CLOSED_OPTION,
							JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showConfirmDialog(this,
							"ID �Ǵ� IP�� ȸ�����Խ� ������ �ʾҽ��ϴ�.  ", "��й�ȣ ã�� ERROR",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.WARNING_MESSAGE);
				}

			} else {
				JOptionPane.showConfirmDialog(this, "ID�� �Էµ��� �ʾҽ��ϴ�. ",
						"���� ����", JOptionPane.CLOSED_OPTION,
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}