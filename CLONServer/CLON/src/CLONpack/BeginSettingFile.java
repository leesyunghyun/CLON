package CLONpack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class BeginSettingFile {

	private BufferedReader in;
	public static BufferedWriter out;
	private String message;
	private String split[];
	private boolean result;
	public static boolean selected = false;
	private File file = new File("setting.txt");
	public static boolean savecheck = false;

	public BeginSettingFile() {
		try {
			if (file.exists()) {

			} else {
				out = new BufferedWriter(new FileWriter("setting.txt"));
				this.writeData("192.168.0.8/8282/false/o/x\n");
			}

			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					"setting.txt"), "utf-8"));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = readData();
		if (result) {
			selected = true;
			new LoginUI(split[0], split[1]);
		} else {
			selected = false;
			SettingUi ui = new SettingUi(this);
			SettingUi.ip.setText(split[0]);
			SettingUi.port.setText(split[1]);
		}
	}

	public boolean readData() {
		try {
			message = in.readLine();
			System.out.println(message);

			split = message.split("/");
			if (split[2].equals("false")) {

				return false;
			} else {
				if (split[3].equals("x")) {
					return false;
				}
				return true;
			}

		} catch (IOException e) {
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		}

	}

	public static void writeData(String str) {
		try {
			out = new BufferedWriter(new FileWriter("setting.txt"));
			out.write(str);
			out.flush();
		} catch (IOException e) {
			try {
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
