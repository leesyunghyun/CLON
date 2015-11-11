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
	Dimension dm = Toolkit.getDefaultToolkit().getScreenSize(); // 화면위치 중앙으로

	private String ip;
	private Socket c; // 셋팅하기 위한 작업

	public FindipUI(LoginSocket login) {
		this.login = login;
		int x, y; // 화면위치를 중앙으로 셋팅하기 위한 변수
		x = (int) ((dm.getWidth() / 2) - (this.getWidth() / 2));
		y = (int) ((dm.getHeight() / 2) - (this.getHeight() / 2));

		findid fiid = new findid(login); // id찾기 패널
		findpw fipw = new findpw(login); // 비밀번호 찾기 패널

		JTabbedPane jtp = new JTabbedPane(); // 패널들을 담을 탭

		jtp.add("ID찾기", fiid); // 탭에 id 찾는 패널을 담음
		jtp.add("PW찾기", fipw); // 탭에 비밀번호 찾는 패널을 담음

		this.getContentPane().add(jtp); // 다이얼로그에 현재 탭을 담음
		this.setSize(250, 140); // 크기설정

		this.setLocation(new Point(x, y)); // 화면위치설정
		this.setTitle("ID/PW 찾기"); // 타이틀설정
		this.setResizable(false);
		this.setVisible(true); // 투명도 설정
	}

}

// 아이디찾기는 아이피를 통해서 찾을 수 있음
class findid extends JPanel implements ActionListener {
	private JButton find; // 찾기버튼
	private JTextField jip; // 아이피입력창
	private JLabel jl; // 아이피라벨
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
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER)); // 패널을
																	// 플로우레이아웃으로
																	// 설정

		find = new JButton("찾기"); // 찾기 버튼
		jip = new JTextField(15); // 아이피입력창
		jl = new JLabel("아이피 : ", JLabel.CENTER); // 아이피 라벨

		jp.add(jl); // 패널에 아이디라벨을 넣음
		jp.add(jip); // 패널에 아이피입력창을 넣음

		find.addActionListener(this); // 찾기버튼액션이벤트

		this.setLayout(new BorderLayout()); // 현재 패널을 보더레이아웃으로 설정
		this.add(find, "South"); // 찾기버튼을 현재 패널 남쪽에 담음
		this.add(jp, "Center"); // jp패널을 현재 패널 중앙에 담음
		jip.setText(ip);
		jip.setEnabled(false);
	}

	// 버튼 액션이벤트
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand() == "찾기") { // 찾기 버튼에 대한 액션
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
					JOptionPane.showConfirmDialog(this, "찾으시는 아이디는  "
							+ split[1], "ID 찾기", JOptionPane.CLOSED_OPTION,
							JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showConfirmDialog(this,
							"회원가입시 사용된 ID가 아닙니다.  ", "아이디 찾기ERROR",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.WARNING_MESSAGE);
				}

			}
		}
	}
}

// ///////////////

// 비밀번호 찾기는 아이디와 아이피필요
class findpw extends JPanel implements ActionListener {
	private JButton find; // 찾기버튼 선언
	private JTextField jip, jid; // 아이디와 아이피입력창
	private JLabel jl; // 아이피라벨
	private JLabel jll; // 아이디라벨
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
		JPanel jp = new JPanel(new GridLayout(2, 2)); // jp패널을 그리드 레이아웃으로 설정

		find = new JButton("찾기"); // 찾기버튼
		jip = new JTextField(10); // ip입력창
		jid = new JTextField(10); // id입력창
		jl = new JLabel("아이피 : ", JLabel.CENTER); // 아이피라벨
		jll = new JLabel("아이디 : ", JLabel.CENTER); // 아이디라벨

		jp.add(jl); // jp패널에 아이피라벨을 담음
		jp.add(jip); // jp패널에 아이피입력창을 담음
		jp.add(jll); // jp패널에 아이디라벨을 담음
		jp.add(jid); // jp패널에 아이디입력창을 담음

		find.addActionListener(this); // 찾기버튼액션이벤트

		this.setLayout(new BorderLayout()); // 현재패널을 보더레이아웃으로 설정
		this.add(find, "South"); // 현재패널에 찾기버튼을 남쪽에 담음
		this.add(jp, "North"); // 현재패널에 jp패널을 북쪽에 담음
		jip.setText(ip);
		jip.setEnabled(false);
	}

	// 버튼 액션이벤트
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand() == "찾기") { // 찾기버튼 액션이벤트
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
					JOptionPane.showConfirmDialog(this, "찾으시는 비밀번호는  "
							+ split[1], "비밀번호찾기", JOptionPane.CLOSED_OPTION,
							JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showConfirmDialog(this,
							"ID 또는 IP가 회원가입시 사용되지 않았습니다.  ", "비밀번호 찾기 ERROR",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.WARNING_MESSAGE);
				}

			} else {
				JOptionPane.showConfirmDialog(this, "ID가 입력되지 않았습니다. ",
						"공백 에러", JOptionPane.CLOSED_OPTION,
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}