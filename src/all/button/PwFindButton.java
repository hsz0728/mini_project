package all.button;

import javafx.scene.Parent;

public interface PwFindButton {

	void pwFindProc(Parent root);
	void pwFindOkProc(Parent root);
	void findPwResult(Parent root, String pw, String findName);

}