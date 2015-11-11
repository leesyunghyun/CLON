package CLONpack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

public class ClientTh extends Thread {
	private BufferedWriter bw;
	private BufferedReader in;
	private LoginSocket c;
	private String message;
	private Vector<String> vc;
	public static int LoginAppcheck = 0;
	public static String lettername;
	String message2;

	public ClientTh(LoginSocket c) {
		this.c = c;

	}

	public LoginSocket getLogin() {
		return this.c;
	}

	@Override
	public void run() {
		bw = c.getBw();
		in = c.getBr();
		String split[];
		String split2[];

		while (true) {
			try {

				message = in.readLine();
				split = message.split("/");
				if (split[0].equals("appletter")) {
					if (split[1].equals("false")) {
						new Option();
					}
				} else if (split[0].equals("letter")) {
					lettername = split[1];
					String str = split[3];
					split2 = str.split("@");
					message2 = split2[0] + "\n";
					for (int i = 1; i < split2.length; i++) {
						message2 += split2[i] + "\n";
					}

					new CreateDialog(this, message2);
				} else if (split[0].equals("alram")) {
					ClientUi.content.append(">>>>>서버가 공지를 변경하였습니다.<<<<<\n");

					ClientUi.singleAlram.setText(split[1]);
				}

				else if (split[0].equals("alram1")) {
					ClientUi.content
							.append(">>>>>CLON 메신저에 오신것을 환영합니다.<<<<<\n");
					ClientUi.singleAlram.setText(split[1]);
					if (LoginSocket.app.equals("o")) {
						ClientUi.item2.setSelected(true);
					} else {
						ClientUi.item2.setSelected(false);
					}
				} else if (split[0].equals("countmember")) {
					Vector<String> vc = new Vector<String>();
					for (int i = 1; i < split.length; i++) {
						vc.add(split[i]);
					}

					ClientUi.jlist.setListData(vc);
					ClientUi.memberc.setText(" CLON 접속회원수 : " + vc.size());
				} else {

					ClientUi.content.append(message + "\n");
				}
				ClientUi.content.setCaretPosition(ClientUi.content
						.getDocument().getLength());
			} catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
				break;

			}
		}
	}

	public void write(String message) {
		try {
			bw.write(message + "\n");
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
