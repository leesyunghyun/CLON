package CLONSerpack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Vector;

public class ServerThread extends Thread {

	private Socket c;
	private ServerInfo info;
	private BufferedReader br;
	private BufferedWriter bw;
	private String message;
	private Vector<ServerInfo> v;
	private Database data;
	private Vector v2;
	private Vector v3;
	String split[];

	public ServerThread(Socket c, ServerInfo info, Vector v, Vector v2,
			BufferedReader br, BufferedWriter bw, Database data) {
		this.c = c;
		this.info = info;
		this.v = v;
		this.v2 = v2;
		this.br = br;
		this.bw = bw;
		this.data = data;
	}

	@Override
	public void run() {
		ServerStart.memberlist = "countmember";
		for (ServerInfo info : v) {

			ServerStart.memberlist += "/" + info.getname();
		}
		for (ServerInfo info : v) {
			info.writed(ServerStart.memberlist);
		}

		try {
			bw.write("alram1/" + ServerUi.notice + "\n");
			bw.flush();

			ServerUi.content
					.append("## " + data.getnic() + " ## ´ÔÀÌ Á¢¼ÓÇÏ¼Ì½À´Ï´Ù.\n");
			for (ServerInfo info : v) {
				info.writed("## " + data.getnic() + " ## ´ÔÀÌ Á¢¼ÓÇÏ¼Ì½À´Ï´Ù.");
			}

		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		jump: while (true) {
			try {
				message = br.readLine();
				split = message.split("/");

				if (split[0].equals("@w")) {
					for (ServerInfo info : v) {
						if (info.getname().equals(split[2].toString()))
						{
							info.writed(split[1].toString() + "´ÔÀÇ ±Ó¼Ó¸» : "
									+ split[3].toString());
							ServerUi.secretja.append("±Ó¼Ó¸» (" + split[1].toString() + "->" + split[2].toString() + ") : " + split[3].toString() + "\n");
							ServerUi.secretja.setCaretPosition(ServerUi.secretja.getDocument().getLength());
							continue jump;
						}
					}

				}
				else if(split[0].equals("letter"))
				{
					for (ServerInfo info : v)
					{
						if(info.getname().equals(split[2]))
						{
							info.writed("letter/" + split[1].toString() + "/" + split[2].toString() + "/" + split[3].toString());
							ServerUi.secretja.append("ÂÊÁö (" + split[1].toString() + "->" + split[2].toString() + ") : " + split[3].toString() + "\n");
							ServerUi.secretja.setCaretPosition(ServerUi.secretja.getDocument().getLength());
							continue jump;
						}
					}
					
				}
				else if(split[0].equals("appletter"))
				{
					for(ServerInfo info : v)
					{
						if(info.getname().equals(split[2]))
						{
							info.writed("appletter/false");
							ServerUi.secretja.append("[[ÂÊÁö½ÇÆÐ]] (" + split[1].toString() + "->" + split[2].toString() + ") : " + split[3].toString() + "\n");
							ServerUi.secretja.setCaretPosition(ServerUi.secretja.getDocument().getLength());
							continue jump;
						}
					}
				}
				
				if (message.equals("appcheck/o")) {
					data.setappcheck("o");
					ServerUi.content.append(data.getnic() + "´ÔÀÇ ¾îÇÃ»ç¿ë : O \n");
					ServerUi.content.setCaretPosition(ServerUi.content
							.getDocument().getLength());
					continue jump;
				} else if (message.equals("appcheck/x")) {
					data.setappcheck("x");
					ServerUi.content.append(data.getnic() + " ´ÔÀÇ ¾îÇÃ»ç¿ë : X \n");
					ServerUi.content.setCaretPosition(ServerUi.content
							.getDocument().getLength());
					continue jump;
				} else if (message.equals("finish")) {
					try {
						String str2 = info.getname();
						String str3 = info.getid();
						data.setonoff(str3);
						v.remove(info);
						v2.remove(str2);

						ServerStart.memberlist = "countmember";
						for (ServerInfo info : v) {

							ServerStart.memberlist += "/" + info.getname();
						}

						for (ServerInfo info : v) {
							info.writed(ServerStart.memberlist);
						}

						ServerUi.memberc.setText(" CLON Á¢¼ÓÈ¸¿ø¼ö : " + v.size());

						ServerUi.content.append("## " + str2
								+ " ## ´ÔÀÌ Á¢¼ÓÀ» ²÷À¸¼Ì½À´Ï´Ù.\n");
						ServerUi.content.setCaretPosition(ServerUi.content
								.getDocument().getLength());
						for (ServerInfo info : v) {
							info.writed("## " + str2 + " ## ´ÔÀÌ Á¢¼ÓÀ» ²÷À¸¼Ì½À´Ï´Ù.");
						}
						ServerUi.jlist.setListData(v2);

						br.close();
						bw.close();
						c.close();
						break;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						data.setalloff();
						e1.printStackTrace();
					}
				}
				ServerUi.content.append(message + "\n");
				ServerUi.content.setCaretPosition(ServerUi.content
						.getDocument().getLength());
				for (ServerInfo info : v) {
					info.writed(message);
				}
			} catch (IOException e) {

				try {
					String str2 = info.getname();
					String str3 = info.getid();
					data.setonoff(str3);
					v.remove(info);
					v2.remove(str2);

					ServerStart.memberlist = "countmember";
					for (ServerInfo info : v) {

						ServerStart.memberlist += "/" + info.getname();
					}

					for (ServerInfo info : v) {
						info.writed(ServerStart.memberlist);
					}

					ServerUi.memberc.setText(" CLON Á¢¼ÓÈ¸¿ø¼ö : " + v.size());

					ServerUi.content.append("## " + str2
							+ " ## ´ÔÀÌ Á¢¼ÓÀ» ²÷À¸¼Ì½À´Ï´Ù.\n");
					ServerUi.content.setCaretPosition(ServerUi.content
							.getDocument().getLength());
					for (ServerInfo info : v) {
						info.writed("## " + str2 + " ## ´ÔÀÌ Á¢¼ÓÀ» ²÷À¸¼Ì½À´Ï´Ù.");
					}
					ServerUi.jlist.setListData(v2);

					br.close();
					bw.close();
					c.close();
					break;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					data.setalloff();
					e1.printStackTrace();
				}

			}

		}
	}

}
