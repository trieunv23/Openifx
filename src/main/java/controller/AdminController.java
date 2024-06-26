package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Gender;
import Entity.InforAge;
import Entity.InforAsset;
import Entity.InforGender;
import Entity.User;
import Exception.AdminException;
import dao.UserDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.MouseEvent;

public class AdminController implements Initializable{
	
	@FXML
    private Label numberuser ; 
	
	@FXML
    private TableView<User> InforuserTb;

    @FXML
    private TableColumn<User, Float> assetcl;

    @FXML
    private TableColumn<User, LocalDate> dayofbrithcl;
    
    @FXML
    private TextField findUser ; 

    @FXML
    private TableColumn<User, String> emailcl;

    @FXML
    private TableColumn<User, String> fullnamecl;

    @FXML
    private TableColumn<User, Gender> gendercl;

    @FXML
    private TableColumn<User, String> passwordcl;

    @FXML
    private TableColumn<User, String> phonenumbercl;

    @FXML
    private TableColumn<User, String> usernamecl;
    
    private ObservableList<User> list ;
    
    @FXML
    private PieChart genderpc ; 
    
    private ObservableList<PieChart.Data> pieDate ;
    
    @FXML
    private BarChart<String, Number> barChart ;
    
    @FXML
    private CategoryAxis xAxis ; 
    
    @FXML
    private NumberAxis yAxis ;
    
    @FXML
    private BarChart<String , Number> barChart2 ; 
    
    @FXML
    private CategoryAxis xAxis2 ; 
    
    @FXML
    private NumberAxis yAxis2 ;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		displayListUser("");
		pcGender();
		bcAge();
		bcAsset();
		
		findUser.setVisible(false);
		findUser.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				displayListUser(findUser.getText());
			}
		});
	}
	
	private double xOffset = 0;
    private double yOffset = 0;
	
	@FXML
    public void add(ActionEvent event) throws IOException {
		URL url = getClass().getResource("/view/Add_Admin.fxml") ;
		FXMLLoader loader = new FXMLLoader(url); 
		Parent root = loader.load();
		Add_AdminController aac = loader.getController();
		aac.setParentController(this);
		Scene scene = new Scene(root , 500 , 305) ;
		
		scene.setFill(Color.TRANSPARENT);
		Stage window = new Stage() ;
		window.initStyle(StageStyle.TRANSPARENT);
		window.setResizable(false);
		window.setScene(scene);
		window.setTitle("Add User"); 
		
		root.setOnMousePressed((MouseEvent mouseEvent) -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent mouseEvent) -> {
            window.setX(mouseEvent.getScreenX() - xOffset);
            window.setY(mouseEvent.getScreenY() - yOffset);
        });
        
		window.show(); 
    }

    @FXML
    public void change(ActionEvent event) throws AdminException, IOException{
    	User user = InforuserTb.getSelectionModel().getSelectedItem();
    	try {
    		checkSelectUser(user);
        	URL url = getClass().getResource("/view/Change_Admin.fxml") ; 
        	FXMLLoader loader = new FXMLLoader(url);
        	
        	Parent root = loader.load();
        	Change_AdminController cac = loader.getController();
        	cac.setParentController(this);
        	cac.setRoleUser(user);
        	Scene scene = new Scene(root , 500 , 312 ) ; 
        	scene.setFill(Color.TRANSPARENT);
        	Stage window = new Stage() ;
        	window.initStyle(StageStyle.TRANSPARENT);
        	window.setScene(scene);
        	window.setTitle("Change Infor User");
        	
        	root.setOnMousePressed((MouseEvent mouseEvent) -> {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent mouseEvent) -> {
                window.setX(mouseEvent.getScreenX() - xOffset);
                window.setY(mouseEvent.getScreenY() - yOffset);
            });
    		
        	window.centerOnScreen() ; 
        	window.show();
        	
    	} catch (AdminException ae) {
    		Alert alert = new Alert(Alert.AlertType.ERROR) ; 
    		alert.setHeaderText("Error");
    		alert.setContentText(ae.getMessage());
    		alert.showAndWait();
    	}
    }

    @FXML
    public void delete(ActionEvent event) throws AdminException {
    	User user = InforuserTb.getSelectionModel().getSelectedItem();
    	try {
    		checkSelectUser(user);
    		
    		Dialog<Boolean> dialog = new Dialog<>();
    		dialog.setTitle("Confirm");
    		dialog.setHeaderText("Do you want to delete this account ?");
    		
    		ButtonType btok = new ButtonType("OK" , ButtonBar.ButtonData.OK_DONE); 
    		ButtonType btcancel = new ButtonType("Cancel" , ButtonBar.ButtonData.CANCEL_CLOSE);
    		dialog.getDialogPane().getButtonTypes().addAll(btok , btcancel );
    		dialog.setResultConverter(choice -> {
    			if (choice == btok) {
    				int result = UserDao.getInstance().deleteUser(user); 
    	        	if (result != 0 ) {
    	        		reset();
    	        		Alert alert = new Alert(Alert.AlertType.CONFIRMATION) ; 
    	        		alert.setHeaderText("Notification");
    	        		alert.setContentText("Delete user successfully !");
    	        		alert.show();
    	        	}
    				return true ; 
    			} else {
    				return false ;
    			}
    		});
    		dialog.showAndWait();
    		
    	} catch (AdminException ae) {
    		Alert alert = new Alert(Alert.AlertType.ERROR) ; 
    		alert.setHeaderText("Error");
    		alert.setContentText(ae.getMessage());
    		alert.showAndWait();
    	}
    }
    
    @FXML
    public void reset(ActionEvent event) {
    	reset() ; 
    }
    
    public void reset() {
    	displayListUser("");
    	pcGender();
    	bcAge();
    	bcAsset();
    }
    
    @FXML
    public void find(ActionEvent event) {
    	if (findUser.isVisible()) {
    		findUser.setVisible(false);
    		findUser.setText("");
    	} else {
    		findUser.setVisible(true);
    	}
    }
    
    @FXML
    public void logout(ActionEvent event) {
    	Dialog<Boolean> dialog = new Dialog<>();
		dialog.setTitle("Confirm");
		dialog.setHeaderText("Do you want to log out ?");
		
		ButtonType btok = new ButtonType("OK" , ButtonBar.ButtonData.OK_DONE); 
		ButtonType btcancel = new ButtonType("Cancel" , ButtonBar.ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(btok , btcancel );
		dialog.setResultConverter(choice -> {
			if (choice == btok) {
				Stage window = (Stage)((Node)event.getSource()).getScene().getWindow() ; 
		    	window.close();
				return true ; 
			} else {
				return false ;
			}
		});
		dialog.showAndWait();
    }
    
    public void displayListUser(String first) {
    	list = FXCollections.observableArrayList(UserDao.getInstance().getListUser(first)); 
		usernamecl.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
		passwordcl.setCellValueFactory(new PropertyValueFactory<User, String>("passWord"));
		fullnamecl.setCellValueFactory(new PropertyValueFactory<User, String>("fullName"));
		phonenumbercl.setCellValueFactory(new PropertyValueFactory<User, String>("phoneNumber"));
		emailcl.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		dayofbrithcl.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("dayOfBirth"));
		gendercl.setCellValueFactory(new PropertyValueFactory<User, Gender>("gender"));
		assetcl.setCellValueFactory(new PropertyValueFactory<User, Float>("asset"));
		InforuserTb.setItems(list);
		
		numberuser.setText(String.valueOf(UserDao.getInstance().getNumberUser()));
    }
    
    public void checkSelectUser(User user) throws AdminException {
    	if (user == null ) {
    		 throw new AdminException("Plese select user !");
    	} 
    }
    
    public void pcGender() {
    	genderpc.setTitle("Gender");
    	List<InforGender> list = UserDao.getInstance().getListGender();
    	
    	pieDate = FXCollections.observableArrayList();
    	
    	for (InforGender typeGender : list ) {
    		pieDate.add(new PieChart.Data(typeGender.getGender(), typeGender.getQuantity()));
    	}
    	
    	genderpc.setData(pieDate);
    }
    
    public void bcAge() {
    	barChart.setTitle("Age");
    	List<InforAge> list = UserDao.getInstance().getListAge();
    	
    	xAxis.setLabel("Age");
        yAxis.setLabel("Count");
        
        barChart.getData().clear();
        
        for (InforAge ia : list) {
        	XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        	dataSeries.setName(ia.getAge());
        	dataSeries.getData().add(new XYChart.Data<>(ia.getAge() , ia.getCountAge()));
        	barChart.getData().add(dataSeries);
        }
    }
    
    public void bcAsset() {
    	barChart2.setTitle("Asset");
    	List<InforAsset> list = UserDao.getInstance().groupAsset();
    	
    	xAxis2.setLabel("Over Value");
        yAxis2.setLabel("Count");
        
        barChart2.getData().clear();
        for (InforAsset ia : list) {
        	XYChart.Series<String, Number> dataSeries = new XYChart.Series<String, Number>();
        	dataSeries.getData().add(new XYChart.Data<>(ia.getValue() , ia.getQuantity()));
        	barChart2.getData().add(dataSeries);
        }
        barChart2.setLegendVisible(false);
    } 
}
