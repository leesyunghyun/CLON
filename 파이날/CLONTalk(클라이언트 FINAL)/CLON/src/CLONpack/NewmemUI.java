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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewmemUI extends JDialog implements ActionListener, ItemListener {

	private JButton makee; // ������ư����
	private JButton closee; // �ݱ��ư ����
	private JButton check1; // �ߺ�Ȯ�ι�ư ����

	private JLabel jl[] = new JLabel[3]; // ���̵� ��й�ȣ �г��� �� ����

	private JTextField id; // ���̵� ��й�ȣ �г��� �Է�â ����
	private JTextField pw;
	private JTextField name;
	private JCheckBox appcheck = new JCheckBox("���ø����̼� ���");
	private JPanel jp[] = new JPanel[4]; // ���� ���� �г� ����
	private JPanel appjp = new JPanel();
	private JPanel container = new JPanel();
	private JPanel fjp;
	private JPanel kjp;
	private LoginSocket login;
	private Dimension dm = Toolkit.getDefaultToolkit().getScreenSize(); // ȭ����ġ
	private boolean resultipcheck = false;// �߾�
	private boolean resultcheck = false;
	private boolean flag = false;

	private int idResult;
	public static String appCheckResult;
	
	
	public NewmemUI(LoginSocket login) {
		this.login = login;
		int x, y; // ȭ����ġ �߾� ����
		x = (int) ((dm.getWidth() / 2) - (this.getWidth() / 2));
		y = (int) ((dm.getHeight() / 2) - (this.getHeight() / 2));

		makee = new JButton("�����");
		closee = new JButton("�ݱ�"); // ��ư
		check1 = new JButton("�ߺ�Ȯ��");

		id = new JTextField(10);
		pw = new JTextField(10); // �Է�â
		name = new JTextField(10);

		jl[0] = new JLabel("���̵� : ", JLabel.RIGHT);
		jl[1] = new JLabel("��й�ȣ : ", JLabel.RIGHT); // �Է�â���� ��
		jl[2] = new JLabel("�г��� : ", JLabel.RIGHT);

		for (int i = 0; i < 4; i++) {
			jp[i] = new JPanel(new GridLayout(1, 2)); // �г��� ���� �׸��巹�̾ƿ�

		}
		fjp = new JPanel(new BorderLayout()); // jp0�� kjp ���� �г�
		kjp = new JPanel(new BorderLayout()); // jp1�� jp2�� ���� �г�

		jp[0].add(jl[0]);
		jp[0].add(id); // jp0�� �󺧰� ���̵� �ߺ�Ȯ�ι�ư
		jp[0].add(check1);

		jp[1].add(jl[1]); // jp1�� �󺧰� ��й�ȣ
		jp[1].add(pw);

		jp[2].add(jl[2]); // jp2�� �󺧰� �г���
		jp[2].add(name);

		jp[3].add(makee); // jp3�� ����� ��ư�� �ݱ� ��ư
		jp[3].add(closee);

		appjp.add(appcheck);
		container.setLayout(new BorderLayout());
		container.add(appjp, "Center");
		container.add(jp[3], "South");

		kjp.add(jp[1], "Center"); // jp1�гΰ� jp2�г��� kjp�гο� ����
		kjp.add(jp[2], "South");

		fjp.add(jp[0], "North"); // jp0�� kjp�г��� fjp�гο� ����
		fjp.add(kjp, "West");

		makee.addActionListener(this);
		closee.addActionListener(this); // ��ư �׼�
		check1.addActionListener(this);
		appcheck.addItemListener(this);
		this.setLayout(new BorderLayout()); // ���̾�α� ���̾ƿ��� �������̾ƿ����� ������
		this.setSize(348, 174); // ���̾�α� ũ�� ����
		this.setTitle("ȸ������"); // �ٵ��α� Ÿ��Ʋ
		this.setLocation(new Point(x, y)); // ���̾�α� ������� ��ġ
		this.setResizable(false); // ũ�⼳���Ұ���
		this.add(fjp, "Center"); // fjp�г��� ���Ϳ� ��
		this.add(container, "South"); // jp3�� ���ʿ� ��
		this.setVisible(true); // ����

	}

	// ��ư �׼��̺�Ʈ
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand() == "�����") { // ����� ��ư�� ���� �׼�
			if (!(id.getText().toString().equals("")
					|| pw.getText().toString().equals("") || name.getText()
					.toString().equals(""))) {
				resultipcheck = login.Newmemconnect(id.getText().toString(), pw
						.getText().toString(), name.getText().toString(),appCheckResult.toString());
				int result = JOptionPane.showConfirmDialog(this, "���� ����ðڽ��ϱ�?",
						"���", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);

				if (resultipcheck && result == 0) {
					JOptionPane.showConfirmDialog(this,
							"�����մϴ�!!\nȸ�������� �Ǽ̽��ϴ�.", "ȸ�����ԿϷ�",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.WARNING_MESSAGE);
					this.dispose();

				} else if (result == 1) {

				} else if (!resultipcheck) {
					JOptionPane.showConfirmDialog(this,
							"IP �ߺ��Դϴ�.\n������ �Ѱ��� ���̵� �ϳ� �Դϴ�.", "IP�ߺ�",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showConfirmDialog(this, "���ĭ�� �Է����ּ���", "��� �Է��ϼ���",
						JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getActionCommand() == "�ߺ�Ȯ��") { // �ߺ�Ȯ�ι�ư�� ���� �׼�
			if (!id.getText().equals("")) {
				System.out.println("�̺�Ʈ");
				resultcheck = login.Checkconnect(id.getText().toString());

				if (resultcheck) {
					idResult = JOptionPane.showConfirmDialog(this,
							"����� �� �ִ� ���̵� �Դϴ�.\n����Ͻðڽ��ϱ�?", "ID��� ����",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);
					if (idResult == 0) {
						id.setEnabled(false);
						check1.setEnabled(false);
					}
				} else {
					JOptionPane.showConfirmDialog(this,
							"�̹� ���ǰ� �ִ� ���̵� �Դϴ�.\n�ٽ� �Է����ּ���!", "ID��� �Ұ�",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.WARNING_MESSAGE);
				}

			} else {
				JOptionPane.showConfirmDialog(this, "���̵� �Է����ּ���!!!!",
						"ID �Է��ϼ���", JOptionPane.CLOSED_OPTION,
						JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getActionCommand() == "�ݱ�") { // �ݱ� ��ư�� ���� �׼�
			this.dispose();
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			appCheckResult = "o";
		}else {
			appCheckResult = "x";
		}
		}

	}

