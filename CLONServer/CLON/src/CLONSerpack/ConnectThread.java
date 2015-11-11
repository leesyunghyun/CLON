package CLONSerpack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;

public class ConnectThread extends Thread {

	private Socket c;
	private BufferedReader br;
	private BufferedWriter bw;
	String message = null;
	String split[];
	Database data = new Database();
	Vector v;
	Vector v2;
	ServerUi ui;
	String ip;

	public ConnectThread(Socket c, Vector v, Vector v2, ServerUi ui) {
		this.c = c;
		this.v = v;
		this.v2 = v2;
		this.ui = ui;
	}

	@Override
	public void run() {
		jump: while (true) {
			try {
				ip = c.getInetAddress().getHostAddress();
				br = new BufferedReader(new InputStreamReader(
						c.getInputStream(),"EUC-KR"));
				bw = new BufferedWriter(new OutputStreamWriter(
						c.getOutputStream(),"EUC-KR"));
				message = br.readLine();
			} catch (IOException e) {
				break jump;
			}

			split = message.split("/");

			switch (split[0]) {
			case "applogin":
				data.setData(split[1], split[2]);
				
				if(data.appconnect())
				{
					String str = "";
					str = data.getnic();
					ServerInfo info = new ServerInfo(c, ip, split[1], str, data);
					v.add(info);
					v2.add(info.getname());
					ui.setinfo(v, c, v2);
					ServerThread th = new ServerThread(c, info, v, v2, br, bw,
							data);
					th.start();

					try {
						bw.write("loginconnect/" + str + "\n");
						bw.flush();
						ServerUi.memberc.setText(" CLON 접속회원수 : " + v.size());
						ServerUi.jlist.setListData(v2);
						// br.close();
						// bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break jump;
					}

					break jump;
				} else {
					if (data.logincheck.equals("no")) {
						try {
							bw.write("loginconnect/false/no" + "\n");
							bw.flush();
						} catch (IOException e) {
							break jump;
						}
					} else if (data.logincheck.equals("ban")) {
						try {
							bw.write("loginconnect/false/ban" + "\n");
							bw.flush();
						} catch (IOException e) {
							break jump;
						}
					} else if (data.logincheck.equals("appfalse")) {
						try {
							bw.write("loginconnect/false/appfalse" + "\n");
							bw.flush();
						} catch (IOException e) {
							break jump;
						}
					}
					else {
						try {
							bw.write("loginconnect/false/test" + "\n");
							bw.flush();
						} catch (IOException e) {
							break jump;
						}
					}
					try {
						br.close();
						bw.close();
						c.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break jump;
				}
				
			case "connect":
				data.setData(split[1], split[2], ip);

				if (data.connect()) {
					String str = "";
					String str2 = "";
					str = data.getnic();
					str2 = data.getappcheck();
					ServerInfo info = new ServerInfo(c, ip, split[1], str, data);
					v.add(info);
					v2.add(info.getname());
					ui.setinfo(v, c, v2);
					ServerThread th = new ServerThread(c, info, v, v2, br, bw,
							data);
					th.start();

					try {
						bw.write("loginconnect/" + str + "/" + str2 + "\n");
						bw.flush();
						ServerUi.memberc.setText(" CLON 접속회원수 : " + v.size());
						ServerUi.jlist.setListData(v2);
						// br.close();
						// bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break jump;
					}

					break jump;
				} else {
					if (data.logincheck.equals("no")) {
						try {
							bw.write("loginconnect/false/no" + "\n");
							bw.flush();
						} catch (IOException e) {
							continue jump;
						}
					} else if (data.logincheck.equals("ban")) {
						try {
							bw.write("loginconnect/false/ban" + "\n");
							bw.flush();
						} catch (IOException e) {
							continue jump;
						}
					} else {
						try {
							bw.write("loginconnect/false/test" + "\n");
							bw.flush();
						} catch (IOException e) {
							continue jump;
						}
					}
					continue jump;

				}
			case "new":
				data.setData(split[1], split[2], split[3], ip, split[4]);

				if (data.Newmemconnect()) {

					try {
						bw.write("newmemberconnect" + "\n");
						bw.flush();
						// br.close();
						// bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block

						e.printStackTrace();
						continue jump;
					}
					continue jump;
				} else {
					try {
						bw.write("false/false" + "\n");
						bw.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue jump;
					}
					continue jump;
				}
			case "check":
				data.setData(split[1]);
				if (data.Checkconnect()) {
					try {
						bw.write("checkconnect" + "\n");
						bw.flush();
						// br.close();
						// bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block

						e.printStackTrace();
						continue jump;
					}
					continue jump;
				} else {
					try {
						bw.write("false/false" + "\n");
						bw.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue jump;
					}
					continue jump;
				}

			case "findid":
				data.setDataip(split[1]);
				if (data.Findidconnect()) {
					try {
						String str = data.getid();
						bw.write("true/" + str + "\n");
						bw.flush();
						// br.close();
						// bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block

						e.printStackTrace();
						continue jump;
					}
					continue jump;
				} else {
					try {
						bw.write("false/false" + "\n");
						bw.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					continue jump;
				}
			case "findidpw":
				data.setDataip(split[1], split[2]);
				if (data.Findidpwconnect()) {
					try {
						String str = data.getpw();
						bw.write("true/" + str + "\n");
						bw.flush();
						// br.close();
						// bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue jump;
					}
					continue jump;
				} else {
					try {
						bw.write("false/false" + "\n");
						bw.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					continue jump;
				}
			}

		}
	}
}
