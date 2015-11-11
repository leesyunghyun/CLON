package org.clon.apps;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.clonapps.R;

public class ClienttChat extends Activity {
	public static TextView alramtext;
	public static TextView chattext;
	private Button sendbtn;
	public static EditText inputText;
	public static ScrollView scroll;
	public static String selectedName;
	private String str;
	private String split[] = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chattingui);
		alramtext = (TextView) findViewById(R.id.alramtext);
		chattext = (TextView) findViewById(R.id.chattext);
		sendbtn = (Button) findViewById(R.id.sendbtn);
		inputText = (EditText) findViewById(R.id.inputText);
		scroll = (ScrollView) findViewById(R.id.scrollView1);

		sendbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				str = inputText.getText().toString();

				if (str.equals("@w/" + ClienttChat.selectedName + "/")) {
					return;
				} else {
					split = str.split("/");
					try {
						if (!(inputText.getText().toString().equals(""))) {
							if (split[0].equals("@w")) {
								ChatThread.bw.write("@w/" + ChatThread.nicname
										+ "/" + split[1].toString() + "/"
										+ split[2].toString() + "\n");
								ChatThread.bw.flush();
								chattext.append("<" + ChatThread.nicname
										+ ">:(귓속말 → " + split[1].toString()
										+ ") " + split[2].toString() + "\n");
								inputText.requestFocus();
								inputText.setText("@w/"
										+ ClienttChat.selectedName + "/");

							} else {
								ChatThread.bw
										.write("<"
												+ ChatThread.nicname
												+ ">(모바일) : "
												+ inputText.getText()
														.toString() + "\n");
								ChatThread.bw.flush();
								inputText.requestFocus();
								inputText.setText("");
							}
						}

					} catch (IOException e) { // TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		});
	}

	@Override
	protected void onStop() {
		try {
			ChatThread.bw.close();
			ChatThread.in.close();
			ChatThread.c.close();
		} catch (IOException e) {
			try {
				ChatThread.bw.close();
				ChatThread.in.close();
				ChatThread.c.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			e.printStackTrace();
		}
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		try {
			ChatThread.bw.close();
			ChatThread.in.close();
			ChatThread.c.close();
			finish();
		} catch (IOException e) {
			try {
				ChatThread.bw.close();
				ChatThread.in.close();
				ChatThread.c.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.clientt_thread, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.listmember) {
			startActivity(new Intent(ClienttChat.this, Connect.class));
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {

		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			try {
				ChatThread.bw.write("finish\n");
				ChatThread.bw.flush();
				ChatThread.bw.close();
				ChatThread.in.close();
				ChatThread.c.close();
				finish();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return super.dispatchKeyEvent(event);
	}
}
