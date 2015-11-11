package CLONpack;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CreateDialog {
	JDialog jsend;
	JPanel jp01;
	JPanel jp02;
	JLabel jp01_jl1;
	JLabel jp01_jl2;
	JLabel jp02_jl;
	JTextArea jp02_ja;
	JButton jbsend;
	int x, y;
	Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();;
	ClientTh ct;
	String content;
	boolean flag = true;

	public CreateDialog(final ClientTh ct, String content) {
		this.ct = ct;
		this.content = content;
		jsend = new JDialog();
		jsend.setTitle("쪽지보내기");
		jp01 = new JPanel();
		jp02 = new JPanel();
		jp01_jl1 = new JLabel("보낸사람 : ", JLabel.RIGHT);
		jp01_jl2 = new JLabel(ct.lettername, JLabel.CENTER);
		jp02_jl = new JLabel("내용 : ");
		jp02_ja = new JTextArea(content);
		jp02_ja.setEditable(false);
		jbsend = new JButton("답장");
		jsend.setLayout(new BorderLayout());
		jsend.setSize(200, 200);
		jp01.setLayout(new FlowLayout());
		jp02.setLayout(new BorderLayout());
		jsend.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				jsend.dispose();
				super.windowClosing(e);
			}

		});
		jp01.add(jp01_jl1);
		jp01.add(jp01_jl2);
		jp02.add(jp02_jl, "West");
		jp02.add(new JScrollPane(jp02_ja), "Center");
		jsend.add(jp01, "North");
		jsend.add(jp02, "Center");
		jsend.add(jbsend, "South");
		x = (int) ((dm.getWidth() / 2) - (jsend.getWidth() / 2));
		y = (int) ((dm.getHeight() / 2) - (jsend.getHeight() / 2));
		jsend.setLocation(new Point(x, y));
		jsend.setVisible(true);
		jbsend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exit();
				resend();
			}
		});

	}

	public void resend() {
		jsend = new JDialog();
		jsend.setTitle("쪽지보내기");
		jp01 = new JPanel();
		jp02 = new JPanel();
		jp01_jl1 = new JLabel("받는사람 : ", JLabel.RIGHT);
		jp01_jl2 = new JLabel(ct.lettername, JLabel.CENTER);
		jp02_jl = new JLabel("내용 : ");
		jp02_ja = new JTextArea("");
		jbsend = new JButton("보내기");
		jsend.setLayout(new BorderLayout());
		jsend.setSize(200, 200);
		jp01.setLayout(new FlowLayout());
		jp02.setLayout(new BorderLayout());

		jp01.add(jp01_jl1);
		jp01.add(jp01_jl2);
		jp02.add(jp02_jl, "West");
		jp02.add(new JScrollPane(jp02_ja), "Center");
		jsend.add(jp01, "North");
		jsend.add(jp02, "Center");
		jsend.add(jbsend, "South");
		jsend.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				jsend.dispose();
				super.windowClosing(e);
			}

		});
		x = (int) ((dm.getWidth() / 2) - (jsend.getWidth() / 2));
		y = (int) ((dm.getHeight() / 2) - (jsend.getHeight() / 2));
		jsend.setLocation(new Point(x, y));
		jsend.setVisible(true);
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
					ct.write("letter/" + LoginSocket.name + "/" + ct.lettername
							+ "/" + str2);
				}
				exit();
			}
		});

	}

	public void exit() {
		jsend.dispose();
	}
}
