package org.clon.apps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.Vector;

import android.os.Handler;
import android.view.View;

public class ChatThread extends Thread implements Serializable {
	static Socket c;
	static BufferedWriter bw;
	static BufferedReader in;
	public static String nicname;
	private String split[];
	private String message;
	String str;
	private Handler handler;
	static Vector<String> vc;

	public ChatThread(Socket c, BufferedWriter bw, BufferedReader in,
			String nicname, Handler handler) {
		this.c = c;
		this.bw = bw;
		this.in = in;
		this.nicname = nicname;
		this.handler = handler;
	}

	@Override
	public void run() {
		while (true) {
			try {
				message = in.readLine();
				split = message.split("/");
				if (split[0].equals("letter")) {
					this.writed("appletter/" + split[2] + "/" + split[1]
							+ "/false");
				} else if (split[0].equals("alram1")) {
					str = split[1];
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							ClienttChat.chattext
									.append(">>>>>CLON 메신저에 오신것을 환영합니다.<<<<<\n");
							ClienttChat.alramtext.setText(str.toString());
						}
					}, 30);

				} else if (split[0].equals("countmember")) {
					vc = null;
					vc = new Vector<String>();
					for (int i = 1; i < split.length; i++) {
						vc.add(split[i]);
					}
				} else if (split[0].equals("alram")) {
					str = split[1];
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							ClienttChat.chattext
									.append(">>>>>서버가 공지를 변경하였습니다.<<<<<\n");
							ClienttChat.alramtext.setText(str.toString());

						}
					}, 30);
				} else {
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							ClienttChat.chattext.append(message + "\n");
							ClienttChat.scroll.fullScroll(View.FOCUS_DOWN);

						}
					}, 30);
				}
			} catch (IOException e) {
				try {
					// ClienttChat.chattext.append(message + "\n");
					bw.close();
					in.close();
					c.close();
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				break;
			}
		}
	}

	public void writed(String str) {
		try {
			bw.write(str + "\n");
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public BufferedReader getin() {
		return this.in;
	}

	public BufferedWriter getout() {
		return this.bw;
	}

}
