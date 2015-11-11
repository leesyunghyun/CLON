package CLONpack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginSocket {

	private BufferedWriter bw;
	private BufferedReader in;
	private int port;
	private String id;
	private String pw;
	private String ip;
	private String result = null;
	private String ip2;
	private Socket c2;
	public static String name;
	public static String app;
	String split[];
	private Socket c = null;
	private LoginUI ui;
	public static boolean checked = false;
	private boolean flag = false;

	public LoginSocket(String ip, String port, LoginUI ui) {
		this.ui = ui;
		this.ip = ip;

		this.port = Integer.parseInt(port);
		System.out.println(this.ip);
		System.out.println(this.port);
		try {
			InterruptThread inter = new InterruptThread(this.c, this.ip,
					this.port);
			try {
				inter.start();
				inter.join(2000);
				this.c = inter.getc();
			} catch (InterruptedException e) {

				c.close();

			}

			bw = new BufferedWriter(new OutputStreamWriter(c.getOutputStream(),
					"EUC-KR"));
			in = new BufferedReader(new InputStreamReader(c.getInputStream(),
					"EUC-KR"));
			checked = true;
		} catch (Exception e) {
			try {
				c.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			checked = false;
			BeginSettingFile
					.writeData((this.ip + "/" + this.port + "/false/x\n"));
		}

	}

	public void setting(String id, String pwd) {
		this.id = id;
		this.pw = pwd;

	}

	public boolean connect() {
		try {
			bw.write("connect/" + id + "/" + pw + "\n");
			bw.flush();
			String nic = "loginconnect";
			result = in.readLine();
			split = result.split("/");
			if (nic.equals(split[0])) {
				if (split[1].equals("false")) {
					if (split[2].equals("no")) {
						name = "no";
						return false;
					} else if (split[2].equals("ban")) {
						name = "ban";
						return false;
					}
					name = "false";
					return false;
				} else {
					name = split[1];
					app = split[2];
					return true;
				}
			}
		} catch (IOException e) {
			try {
				name = "Serverexit";
				this.flag = true;
				System.out.println(flag);
				c.close();
				return false;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		return false;
	}

	public boolean Newmemconnect(String id, String pwd, String name,
			String appchecked) {
		try {
			bw.write("new/" + id + "/" + pwd + "/" + name + "/" + appchecked
					+ "\n");
			bw.flush();
			result = in.readLine();
			if (result.equals("newmemberconnect")) {
				return true;

			}
		} catch (IOException e) {
			return false;

		}
		return false;
	}

	public boolean Checkconnect(String id) {
		try {
			bw.write("check/" + id + "\n");
			bw.flush();
			result = in.readLine();
			if (result.equals("checkconnect")) {
				return true;
			}

		} catch (IOException e) {
			return false;
		}
		return false;
	}

	public BufferedWriter getBw() {
		return this.bw;
	}

	public BufferedReader getBr() {
		return this.in;
	}

	public Socket getc() {
		return this.c;
	}

	public String getip() {
		return this.ip;
	}

	public String getPort() {
		return String.valueOf(this.port);
	}

	public boolean getFlag() {
		return this.flag;
	}
}
