package org.clon.apps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class ConnectThread extends Thread {
	private Socket c = null;
	private BufferedWriter bw;
	private BufferedReader in;
	private String message = null;
	private String split[];
	private String nicname;
	private Context context;
	private Handler handler;
	private ChatThread ct;
	private Database data;

	public ConnectThread(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
	}

	@Override
	public void run() {
		try {
			data = Database.getData(context);
			data.open();
			Cursor c1 = data.rawQuery("select * from setting;");
			c1.moveToFirst();
			c = new Socket(c1.getString(0), c1.getInt(1));
			if (c.equals(null)) {
				Log.d("�׽�Ʈ", "���͵���");
			}
			c1.close();
			data.close();
			bw = new BufferedWriter(new OutputStreamWriter(c.getOutputStream(),
					"EUC-KR"));
			in = new BufferedReader(new InputStreamReader(c.getInputStream(),
					"EUC-KR"));
			this.writed("applogin/"
					+ StartActivity.idinput.getText().toString() + "/"
					+ StartActivity.pwdinput.getText().toString() + "\n");

			message = in.readLine();
			split = message.split("/");
			if (split[0].equals("loginconnect")) {
				if (split[1].equals("false")) {
					if (split[2].equals("no")) {
						handler.post(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(context, "���� PC���� �������Դϴ�.",
										Toast.LENGTH_SHORT).show();
								try {
									bw.close();
									in.close();
									c.close();

								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						});
					} else if (split[2].equals("ban")) {
						handler.post(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(context, "�ش�ID�� IP�� ���ܵǾ����ϴ�.",
										Toast.LENGTH_SHORT).show();
								try {
									bw.close();
									in.close();
									c.close();

								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						});

					} else if (split[2].equals("appfalse")) {
						handler.post(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(context, "PC���� ���ø����̼��� ������ּ���",
										Toast.LENGTH_SHORT).show();
								try {
									bw.close();
									in.close();
									c.close();

								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						});

					} else {
						handler.post(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(context, "ID��PW�� �߸��Ǿ����ϴ�.",
										Toast.LENGTH_SHORT).show();
								try {
									bw.close();
									in.close();
									c.close();

								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						});

					}

				} else {
					nicname = split[1];
					ct = new ChatThread(c, bw, in, nicname, handler);

					Intent intent = new Intent(context, ClienttChat.class);
					context.startActivity(intent);
					ct.start();

				}
			}

		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		super.run();
	}

	public void writed(String str) {
		try {
			bw.write(str);
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
