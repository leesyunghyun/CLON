package CLONpack;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class InterruptThread extends Thread {
	private Socket c;
	private String ip;
	private int port;

	public InterruptThread(Socket c, String ip, int port) {
		this.c = c;
		this.ip = ip;
		this.port = port;

	}

	@Override
	public void run() {
		try {
			this.c = new Socket(this.ip, this.port);
		} catch (UnknownHostException e) {
			try {
				c.close();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			try {
				c.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			LoginSocket.checked = false;
		}
		super.run();
	}

	public Socket getc() {
		return this.c;
	}
}
