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
	public static JTextField ip; // 아이디 입력필드
	public static JTextField port; // 패스워드 입력필드
	private JButton logincon; // 채팅접속버튼
	private JButton exit; // 닫기 버튼
	private JPanel jp1; // IP입력 패널(아이디)
	private JPanel jp2; // 버튼 패널
	private JPanel jp3; // PORT입력 패널
	private JPanel jp4; // 로그인전체 패널
	private JPanel jp5; // 체크 박스
	private JCheckBox ch;
	private Dimension dm = Toolkit.getDefaultToolkit().getScreenSize(); // 화면
	private BeginSettingFile bg;

	public SettingUi(BeginSettingFile bg) {
		this.bg = bg;
		this.setLayout(new BorderLayout()); // 레이아웃
		// 패널정의
		jp1 = new JPanel(new FlowLayout()); // 서버IP입력 패널
		jp2 = new JPanel(new GridLayout(1, 3)); // 버튼 패널 3개
		jp3 = new JPanel(new FlowLayout()); // 서버 PORT 입력패널
		jp4 = new JPanel(new BorderLayout()); // 전체를 담을 패널
		jp5 = new JPanel(new FlowLayout()); // 자동저장 체크박스
		//

		// 텍스트필드 및 버튼 정의
		ip = new JTextField(10); // ip 입력창
		port = new JTextField(10); // port 입력창
		logincon = new JButton("설정완료"); // 로그인연결버튼
		exit = new JButton("닫기"); // 닫기버튼
		ch = new JCheckBox("자동 저장"); // 체크박스
		//
		// 체크박스 이벤트
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
		// 체크박스 저장
		jp5.add(ch);
		//

		// 이벤트 버튼
		logincon.addActionListener(this);
		exit.addActionListener(this);
		//

		// 각 패널에 버튼 등을 삽입
		jp1.add(new JLabel(" ServerIp : ")); // 서버ip
		jp1.add(ip);

		jp2.add(logincon);
		jp2.add(exit); // 버튼패널에 버튼 2개

		jp3.add(new JLabel(" ClonPort : ")); // 서버 port
		jp3.add(port);

		jp4.add(jp1, "North"); //
		jp4.add(jp3, "Center");
		jp4.add(jp5, "South");

		//
		this.add(jp4, "Center");
		this.add(jp2, "South");

		// 다이얼로그 셋팅
		this.setTitle("초기 설정");// 설정의 타이틀
		this.setSize(230, 155); // 크기
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
				super.windowClosing(e);
			}
		});
		int x = 0, y = 0; // 화면 초기값
		// 화면설정해주기
		x = (int) ((dm.getWidth() / 2) - (this.getWidth() / 2));
		y = (int) ((dm.getHeight() / 2) - (this.getHeight() / 2));
		//

		this.setLocation(new Point(x, y)); // 화면위치를 가운데로 이동시킴
		this.setResizable(false); // 크기변경불가능
		this.setVisible(true); // 투명도

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "설정완료") {
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
