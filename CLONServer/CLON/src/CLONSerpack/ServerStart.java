package CLONSerpack;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServerStart {
	public static String memberlist = "countmember";
	public static void main(String[] args) {
		ServerUi ui = new ServerUi();
		Vector v = new Vector();
		Vector v2 = new Vector();
		ServerSocket sc = null;

		try {
			sc = new ServerSocket(8282);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (true) {
			Socket c = null;
			try {
				c = sc.accept();
				System.out.println(c.getInetAddress().getHostAddress()
						+ " 로그인 시도중. . . ");
			} catch (IOException e) {
				Database db = new Database();
				db.setalloff();
				e.printStackTrace();
			}

			ConnectThread th = new ConnectThread(c, v, v2, ui);
			th.start();

		}

	}
}
