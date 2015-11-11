package CLONSerpack;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerInfo {

	private Socket c;
	private String ip;
	private String name;
	private String id;
	private String password;
	private BufferedWriter bw;
	private Database data;
	public ServerInfo(Socket c, String ip, String id, String name,Database data) {
		this.c = c;
		this.ip = ip;
		this.id = id;
		this.name = name;
		this.data = data;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(c.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean writed(String message) {
		try {
			bw.write(message + "\n");
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean serwrite(String message) {
		try {
			bw.write(message + "\n");
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public Socket getc() {
		return this.c;
	}

	public String getip() {
		return this.ip;
	}

	public String getname() {
		return this.name;
	}

	public String getid() {

		return this.id;
	}
	public Database getdata(){
		return this.data;
	}
}
