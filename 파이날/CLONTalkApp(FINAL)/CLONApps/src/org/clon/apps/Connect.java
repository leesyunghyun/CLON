package org.clon.apps;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clonapps.R;

public class Connect extends Activity {
	private ListView list;
	private TextView count;
	private ArrayAdapter<String> memberlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connectui);
		list = (ListView) findViewById(R.id.list);
		count = (TextView) findViewById(R.id.countmember);
		memberlist = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, ChatThread.vc);
		memberlist.notifyDataSetChanged();
		list.setAdapter(memberlist);
		count.setText(" CLON 접속회원수 : " + ChatThread.vc.size());
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				ClienttChat.selectedName = ChatThread.vc.get(arg2);
				ClienttChat.inputText.setText("@w/" + ClienttChat.selectedName
						+ "/");
				finish();
				return false;
			}

		});
	}

	@Override
	protected void onResume() {
		memberlist.notifyDataSetChanged();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		memberlist.notifyDataSetChanged();
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		memberlist.notifyDataSetChanged();
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.connect, menu);
		return true;
	}

}
