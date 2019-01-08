package sample;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.beans.XMLEncoder;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class Controller implements Initializable
{
    Dictionary dictionary;
    Node selected;
    dbUtil.dbConnection dbConnection = new dbConnection();
    @FXML
        private TextField key_field;
    @FXML
        private TextField key_field2;
    @FXML
        private TextField key_field3;
    @FXML
        private Label msg_label;
    @FXML
        private TextField value_field;
    @FXML
        private TableView<Node> table;
    @FXML
        private TableColumn<Node, String> key_column;
    @FXML
        private TableColumn<Node, String> def_column;

    public Controller() throws SQLException
    {
        System.out.println("Default controller constructor...");
    }
    @FXML
    void Add() throws SQLException
    {
        dictionary.add(key_field.getText(), value_field.getText());
        key_field.setText("");
        value_field.setText("");
        Update();
    }
    @FXML
    void Delete() throws SQLException
    {
        if(key_field2.getText().isEmpty()) //jesli pole jest puste czyli usuwamy przez wybor
        {
            if(selected == null)
            {
                System.out.println("Nothing selected!");
                return;
            }
            dictionary.delete(selected.getKey());
        }
        else //usuwanie poprzez podanie klucza
        {
            dictionary.delete(key_field2.getText());
        }
        Update();
    }
    @FXML
    void Search() throws SQLException
    {
        Object obj;
        System.out.println(key_field3.getText());
        if(key_field3.getText().isEmpty()) key_field3.setText("Enter the key!");
        else
        {
            obj = dictionary.get(key_field3.getText());
            if(obj == null)
            {
                msg_label.setText("Element not found!\n");
                msg_label.setVisible(true);
            }
            else
            {
                msg_label.setText("Element found!\n" + obj);
                msg_label.setVisible(true);
            }
        }
    }
    @FXML
    void Save() throws SQLException
    {
        Stage stage = new Stage();
        System.out.println("Saving a binary file...");
        FileChooser fileChooser = new FileChooser();
        File f = fileChooser.showSaveDialog(stage);
        if(f != null) dictionary.save(f);
    }
    @FXML
    void Save_as_XML() throws FileNotFoundException, SQLException
    {
        System.out.println("Saving as .xml...");

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File f = fileChooser.showSaveDialog(stage);
        if(f != null)
        {
            XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(f)));
            xmlEncoder.writeObject(dictionary.getList());
            xmlEncoder.close();
        }
    }
    @FXML
    void Load() throws SQLException
    {
        Stage stage = new Stage();
        System.out.println("Loading a binary file...");
        FileChooser fileChooser = new FileChooser();
        File f = fileChooser.showOpenDialog(stage);
        if(f != null) dictionary.load(f);
        Update();
    }
    @FXML
    void Load_as_XML() throws IOException, SQLException
    {
        Stage stage = new Stage();
        System.out.println("Loading a xml file...");
        FileChooser fileChooser = new FileChooser();
        File f = fileChooser.showOpenDialog(stage);
        if(f != null) dictionary.load_as_XML(f);
        Update();
    }
    @FXML
    void getSelected()
    {
        selected = table.getSelectionModel().getSelectedItem();
    }
    private ObservableList<Node> getElements() throws SQLException
    {
        ObservableList<Node> elements = FXCollections.observableArrayList();
        elements.addAll(dictionary.getList());
        return elements;
    }
    private void Update() throws SQLException
    {
        table.setItems(getElements());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try { dictionary = new Dictionary(); }
        catch (SQLException | ClassNotFoundException e) { e.printStackTrace(); }
        key_column.setText("Key");
        key_column.setCellValueFactory(new PropertyValueFactory<>("key"));
        def_column.setText("Definition");
        def_column.setCellValueFactory(new PropertyValueFactory<>("value"));
        try {
            Update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

