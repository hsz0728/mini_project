package all.boardService;

import java.io.IOException;
import java.util.List;

import all.Controller;
import all.button.common.CommonService;
import all.button.common.CommonServiceImp;
import all.databaseDAO.DatabaseDAO;
import all.databaseDAO.DatabaseDAOImp;
import all.boardService.tableViewService.TableViewService;
import all.boardService.tableViewService.TableViewServiceImp;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BoardServiceImp implements BoardService {
	DatabaseDAO dao = new DatabaseDAOImp();
	CommonService cs = new CommonServiceImp();
	TableViewService tvs;
	
	public BoardServiceImp() {
		tvs = new TableViewServiceImp();
	}

	// 메인화면
	@Override
	public void mainView(Parent root) {
		// TODO Auto-generated method stub

		Stage s = (Stage) root.getScene().getWindow();
		s.close();

		Stage rootStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/main.fxml"));

		root = null;
		try {
			root = loader.load();
			rootStage.setScene(new Scene(root));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Controller ctrl = loader.getController();
		ctrl.setRoot(root);

		// 검색창 콤보박스
		mainCombo(root);
		// 메인화면에 띄울 전체 게시판 테이블뷰
		createAllListView(root);

		rootStage.setTitle("중고거래 커뮤니티");
		rootStage.show();
		// 사이즈 변경 불가
		rootStage.setResizable(false);
	}

	// 전체 게시판의 테이블 뷰
	@Override
	public void createAllListView(Parent root) {
		TableView<Board> listView = (TableView<Board>) root.lookup("#ListView");
		listView.getItems().clear();
		listView.getColumns().clear();
		List<Board> boardList = dao.selectAll();
		if (boardList != null) {
			tvs.configureBoardTableView(listView);
			listView.setItems(FXCollections.observableArrayList(boardList));
		} else {
			System.out.println("게시판 목록을 가져올 수 없습니다.");
		}

		// 게시물을 더블 클릭 했을 때 회원이면 게시물 내용 표시하는 창 출력, 비회원이면 로그인 오류창 출력
		listView.setOnMouseClicked(event -> {
			// 회원인 지 비회원인 지 확인하는 레이블 
			Label logChk = (Label) root.lookup("#logChk");
		    if (event.getClickCount() > 1) {
		        Board b = listView.getSelectionModel().getSelectedItem();
		        if (b != null) {
		            if (logChk.getText().equals("비회원")) {
		                // 로그인 안내창
		                cs.errorView1(root);
		            } else if (logChk.getText().equals("회원") || logChk.getText().equals("관리자")) {
		                // 해당 게시물 보기
		                openBoardDetailWindow(b);
		            }
		        }
		    }
		});

	}

	// 선택한 카테고리 <자유, 구매, 판매, 나눔> 의 테이블 뷰
	@Override
	public void createCategoryListView(Parent root, String Category) {
		TableView<Board> listView = (TableView<Board>) root.lookup("#ListView");
		listView.getItems().clear();
		listView.getColumns().clear();
		List<Board> boardList = dao.categoryBoardAll(Category);
		if (boardList != null) {
			tvs.configureBoardTableView(listView);
			listView.setItems(FXCollections.observableArrayList(boardList));
		} else {
			System.out.println("게시판 목록을 가져올 수 없습니다.");
		}

		// 게시물을 더블 클릭 했을 때 회원이면 게시물 내용 표시하는 창 출력, 비회원이면 로그인 오류창 출력
		listView.setOnMouseClicked(event -> {
			if (event.getClickCount() > 1) {
				Board b = listView.getSelectionModel().getSelectedItem();
				if (b != null) {
					// 해당 게시물 보기
					openBoardDetailWindow(b);
				}
			}
		});
		
	}

	// 검색 결과의 테이블 뷰
	@Override
	public void serchResultListView(Parent root, String text1, String text2) {
		TableView<Board> listView = (TableView<Board>) root.lookup("#ListView");
		listView.getItems().clear();
		listView.getColumns().clear();
		List<Board> boardList = dao.searchResultAll(text1, text2);
		if (boardList != null) {
			tvs.configureBoardTableView(listView);
			listView.setItems(FXCollections.observableArrayList(boardList));

		} else {
			System.out.println("게시판 목록을 가져올 수 없습니다.");
		}

		// 게시물을 더블 클릭 했을 때 회원이면 게시물 내용 표시하는 창 출력, 비회원이면 로그인 오류창 출력
		listView.setOnMouseClicked(event -> {
			if (event.getClickCount() > 1) {
				Board b = listView.getSelectionModel().getSelectedItem();
				if (b != null) {
					// 해당 게시물 보기
					openBoardDetailWindow(b);
				}
			}
		});
		
	}

	// 신고화면 에서 입력받은 콤보박스 + 입력 내용 값 테이블 뷰
	public void reportSerchResultListView(Parent root, String text1, String text2) {
		TableView<Board> listView = (TableView<Board>) root.lookup("#ListView");
		listView.getItems().clear();
		listView.getColumns().clear();
		List<Board> boardList = dao.searchResultAll(text1, text2);
		if (boardList != null) {
			tvs.configureBoardTableView(listView);
			listView.setItems(FXCollections.observableArrayList(boardList));

		} else {
			System.out.println("게시판 목록을 가져올 수 없습니다.");
		}

		// 게시물을 더블 클릭 했을 때 회원이면 게시물 내용 표시하는 창 출력, 비회원이면 로그인 오류창 출력
		listView.setOnMouseClicked(event -> {
			if (event.getClickCount() > 1) {
				Board b = listView.getSelectionModel().getSelectedItem();
				if (b != null) {
					// 해당 게시물 보기
					openBoardDetailWindow(b);
				}
			}
		});
	}

	// 메인화면 콤보 박스
	@Override
	public String mainCombo(Parent root) {
		ComboBox<String> combo = (ComboBox<String>) root.lookup("#searchCombo");
		String str[] = { "제목", "닉네임", "카테고리" };

		// 메인화면 검색창에 있는 콤보박스에 배열값이 들어있지 않을 때만 넣어주기 - 이거 안 넣으면 카테고리 값이 중복 추가됨
		if (combo.getItems().isEmpty()) {
			combo.getItems().addAll(FXCollections.observableArrayList(str));
		}

		String selectedValue = combo.getValue(); // 선택된 콤보박스의 값 가져오기
		return selectedValue;
	}

	// 신고화면 콤보 박스 1
	@Override
	public String reportCombo1(Parent root) {
		ComboBox<String> combo = (ComboBox<String>) root.lookup("#searchReportCombo1");
		String str[] = { "게시물 이름", "닉네임", "아이디" };

		// 신고화면 검색창에 있는 콤보박스에 배열값이 들어있지 않을 때만 넣어주기 - 이거 안 넣으면 카테고리 값이 중복 추가됨
		if (combo.getItems().isEmpty()) {
			combo.getItems().addAll(FXCollections.observableArrayList(str));
		}

		String selectedValue = combo.getValue(); // 선택된 콤보박스의 값 가져오기
		return selectedValue;
	}
	
	// 신고화면 콤보 박스 2
	@Override
	public String reportCombo2(Parent root) {
		ComboBox<String> combo = (ComboBox<String>) root.lookup("#searchReportCombo2");
		String str[] = { "게시물 신고", "유저 신고", "닉네임 신고" };

		// 신고화면 검색창에 있는 콤보박스에 배열값이 들어있지 않을 때만 넣어주기 - 이거 안 넣으면 카테고리 값이 중복 추가됨
		if (combo.getItems().isEmpty()) {
			combo.getItems().addAll(FXCollections.observableArrayList(str));
		}

		String selectedValue = combo.getValue(); // 선택된 콤보박스의 값 가져오기
		return selectedValue;
	}

	// 로그인 화면 체크 박스 (새 창 띄우기, 안 띄우기)
	@Override
	public boolean chk1(Parent root) {
		CheckBox chk1 = (CheckBox) root.lookup("#newViewBoolean");
		if (chk1.isSelected()) {
			return true;
		} else {
			return false;
		}
	}
	
	// 게시물 목록에서 클릭하면 해당 게시물 내용이 포함된 창이 출력되는 메서드 . 스크롤페인
	@Override
	public void openBoardDetailWindow(Board selectedBoard) {
		try {
			FXMLLoader detailLoader = new FXMLLoader(getClass().getResource("../fxml/boardDetail.fxml"));
			Parent detailRoot = detailLoader.load();

			// 직접 FXML 요소에 접근
			Text nicknameText = (Text) detailRoot.lookup("#nicknameText");
			Label titleText = (Label) detailRoot.lookup("#titleText");
			Text dateText = (Text) detailRoot.lookup("#dateText");

			nicknameText.setText(selectedBoard.getNicName());
			dateText.setText(selectedBoard.getUploadDate());
			titleText.setText(selectedBoard.getTitle());

			Stage detailStage = new Stage();
			detailStage.setScene(new Scene(detailRoot));

			// ScrollPane
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setContent(detailRoot);

			// Pannable.
			scrollPane.setPannable(true);

			Scene scene = new Scene(scrollPane, 980, 891);
			detailStage.setScene(scene);

			detailStage.setTitle("게시물 상세 정보");
			detailStage.setResizable(false);
			detailStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			showAlert("Error", "Error opening detail window.");
		}
	}

	// ▲ ▲ ▲ 부속 메서드
	@Override
	public void showAlert(String title, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.showAndWait();
	}

}
