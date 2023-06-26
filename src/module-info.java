module jUno 
{
	requires javafx.controls;
	requires javafx.media;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.base;
	requires javafx.graphics;
	
	opens view to javafx.graphics, javafx.fxml;
	opens controller to javafx.fxml;
}
