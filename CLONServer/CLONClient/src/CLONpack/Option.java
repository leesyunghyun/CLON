package CLONpack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Option extends JFrame {
	public Option() {
		JOptionPane.showConfirmDialog(this, "모바일 사용자에게는 쪽지보내기가 불가능합니다.\n"
				+ "귓속말을 이용해요주세요", "경고", JOptionPane.CLOSED_OPTION,
				JOptionPane.WARNING_MESSAGE);
	}
}
