package all.button.find_idpw;

import javafx.scene.Parent;

public interface IdFindButton {

	void idFindProc(Parent root);
	void idFindOkProc(Parent root);
	void findIdResult(Parent root, String id, String findName);
	
}