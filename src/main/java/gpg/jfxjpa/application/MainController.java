package gpg.jfxjpa.application;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import gpg.jfxjpa.application.language.Messages;
import gpg.jfxjpa.application.model.Student;
import gpg.jfxjpa.application.service.JPAService;
import gpg.jfxjpa.db.utils.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MainController implements Initializable {

	private static final Logger logger = Logger.getLogger(MainController.class.getName());

	private JPAService jpaService;
	private DBUtils dbUtils;

	@FXML
	public Button clickMeButton;

	@FXML
	public TextArea outputTextArea;

	@FXML
	public ListView studentList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		print(Messages.GetBundle().getString("MainController.helloText"));
		initilzeDatabase(studentList);
	}

	private void initilzeDatabase(ListView listView) {

		jpaService = new JPAService();
		jpaService.initilizeEntityManager();
		dbUtils = new DBUtils( jpaService );

		if(!dbUtils.checkSeedData()){
			dbUtils.populateSeedData();
		}

		try {
			listView.setItems(fetchStudents( ));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void print(String text) {
		outputTextArea.appendText(text + System.lineSeparator());
		System.out.println(text);
	}

	private Optional<ButtonType> showAlert(final Alert.AlertType alertType, final String message) {
		Alert alert = new Alert(alertType);
		alert.setHeaderText(Messages.GetBundle().getString("Dialog.information.header"));
		alert.setContentText(message);
		return alert.showAndWait();
	}

	public void setStage(Stage stage) {
		// Hot key Ctrl + H for clickMe button
		stage.addEventFilter(KeyEvent.KEY_PRESSED, (event -> {
			if (new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN).match(event)) {
				clickMeAction();
				event.consume();
			}
		}));

		// Stage is closing
		stage.setOnCloseRequest(e -> {
			try {
				print(Messages.GetBundle().getString("MainController.goodbyeText"));
				// dbUtils.dropSchema(dbUtils.getConnection());
				jpaService.closeConnection();

			} catch (Throwable t) {
				t.printStackTrace();
				System.exit(-1);
			}
		});
	}

	public void clickMeAction() {
		Optional<ButtonType> result = showAlert(Alert.AlertType.INFORMATION,
				Messages.GetBundle().getString("MainController.dialog.information.contentText"));
		result.ifPresent(buttonType -> print(buttonType.getText()));
	}

	private ObservableList<String> fetchStudents() throws SQLException {
		logger.info("Fetching Students from database");
		ObservableList<String> students = FXCollections.observableArrayList();

		List<Student> sList = jpaService.getEntityManager().createNamedQuery("Student.All").getResultList();
		Long studentCount = (Long) jpaService.getEntityManager().createNamedQuery("Student.Count").getSingleResult();
		for (Student s : sList) {
			students.add(s.getName());
		}

		logger.info("Found " + students.size() + " names");

		logger.info("studentCount : " + studentCount);

		return students;
	}
}
