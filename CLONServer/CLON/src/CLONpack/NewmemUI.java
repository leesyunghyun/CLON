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

	private JButton makee; // 만들기버튼선언
	private JButton closee; // 닫기버튼 선언
	private JButton check1; // 중복확인버튼 선언

	private JLabel jl[] = new JLabel[3]; // 아이디 비밀번호 닉네임 라벨 선언

	private JTextField id; // 아이디 비밀번호 닉네임 입력창 선언
	private JTextField pw;
	private JTextField name;
	private JCheckBox appcheck = new JCheckBox("애플리케이션 사용");
	private JPanel jp[] = new JPanel[4]; // 각각 담을 패널 선언
	private JPanel appjp = new JPanel();
	private JPanel container = new JPanel();
	private JPanel fjp;
	private JPanel kjp;
	private LoginSocket login;
	private Dimension dm = Toolkit.getDefaultToolkit().getScreenSize(); // 화면위치
	private boolean resultipcheck = false;// 중앙
	private boolean resultcheck = false;
	private boolean flag = false;

	private int idResult;
	public static String appCheckResult;
	
	
	public NewmemUI(LoginSocket login) {
		this.login = login;
		int x, y; // 화면위치 중앙 변수
		x = (int) ((dm.getWidth() / 2) - (this.getWidth() / 2));
		y = (int) ((dm.getHeight() / 2) - (this.getHeight() / 2));

		makee = new JButton("만들기");
		closee = new JButton("닫기"); // 버튼
		check1 = new JButton("중복확인");

		id = new JTextField(10);
		pw = new JTextField(10); // 입력창
		name = new JTextField(10);

		jl[0] = new JLabel("아이디 : ", JLabel.RIGHT);
		jl[1] = new JLabel("비밀번호 : ", JLabel.RIGHT); // 입력창설명 라벨
		jl[2] = new JLabel("닉네임 : ", JLabel.RIGHT);

		for (int i = 0; i < 4; i++) {
			jp[i] = new JPanel(new GridLayout(1, 2)); // 패널을 전부 그리드레이아웃

		}
		fjp = new JPanel(new BorderLayout()); // jp0과 kjp 담을 패널
		kjp = new JPanel(new BorderLayout()); // jp1과 jp2를 담을 패널

		jp[0].add(jl[0]);
		jp[0].add(id); // jp0에 라벨과 아이디 중복확인버튼
		jp[0].add(check1);

		jp[1].add(jl[1]); // jp1에 라벨과 비밀번호
		jp[1].add(pw);

		jp[2].add(jl[2]); // jp2에 라벨과 닉네임
		jp[2].add(name);

		jp[3].add(makee); // jp3에 만들기 버튼과 닫기 버튼
		jp[3].add(closee);

		appjp.add(appcheck);
		container.setLayout(new BorderLayout());
		container.add(appjp, "Center");
		container.add(jp[3], "South");

		kjp.add(jp[1], "Center"); // jp1패널과 jp2패널을 kjp패널에 담음
		kjp.add(jp[2], "South");

		fjp.add(jp[0], "North"); // jp0과 kjp패널을 fjp패널에 담음
		fjp.add(kjp, "West");

		makee.addActionListener(this);
		closee.addActionListener(this); // 버튼 액션
		check1.addActionListener(this);
		appcheck.addItemListener(this);
		this.setLayout(new BorderLayout()); // 다이얼로그 레이아웃을 보더레이아웃으로 설정함
		this.setSize(348, 174); // 다이얼로그 크기 설정
		this.setTitle("회원가입"); // 다디얼로그 타이틀
		this.setLocation(new Point(x, y)); // 다이얼로그 띄워지는 위치
		this.setResizable(false); // 크기설정불가능
		this.add(fjp, "Center"); // fjp패널을 센터에 둠
		this.add(container, "South"); // jp3을 남쪽에 둠
		this.setVisible(true); // 투명도

	}

	// 버튼 액션이벤트
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand() == "만들기") { // 만들기 버튼에 대한 액션
			if (!(id.getText().toString().equals("")
					|| pw.getText().toString().equals("") || name.getText()
					.toString().equals(""))) {
				resultipcheck = login.Newmemconnect(id.getText().toString(), pw
						.getText().toString(), name.getText().toString(),appCheckResult.toString());
				int result = JOptionPane.showConfirmDialog(this, "정말 만드시겠습니까?",
						"경고", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);

				if (resultipcheck && result == 0) {
					JOptionPane.showConfirmDialog(this,
							"축하합니다!!\n회원가입이 되셨습니다.", "회원가입완룐",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.WARNING_MESSAGE);
					this.dispose();

				} else if (result == 1) {

				} else if (!resultipcheck) {
					JOptionPane.showConfirmDialog(this,
							"IP 중복입니다.\n아이피 한개당 아이디 하나 입니다.", "IP중복",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showConfirmDialog(this, "모든칸을 입력해주세요", "모두 입력하세요",
						JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getActionCommand() == "중복확인") { // 중복확인버튼에 대한 액션
			if (!id.getText().equals("")) {
				System.out.println("이벤트");
				resultcheck = login.Checkconnect(id.getText().toString());

				if (resultcheck) {
					idResult = JOptionPane.showConfirmDialog(this,
							"사용할 수 있는 아이디 입니다.\n사용하시겠습니까?", "ID사용 가능",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);
					if (idResult == 0) {
						id.setEnabled(false);
						check1.setEnabled(false);
					}
				} else {
					JOptionPane.showConfirmDialog(this,
							"이미 사용되고 있는 아이디 입니다.\n다시 입력해주세요!", "ID사용 불가",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.WARNING_MESSAGE);
				}

			} else {
				JOptionPane.showConfirmDialog(this, "아이디를 입력해주세요!!!!",
						"ID 입력하세요", JOptionPane.CLOSED_OPTION,
						JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getActionCommand() == "닫기") { // 닫기 버튼에 대한 액션
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

