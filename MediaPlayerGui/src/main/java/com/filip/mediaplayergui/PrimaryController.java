/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.filip.mediaplayergui;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author 19par
 */
public class PrimaryController implements Initializable {

    @FXML
    private ImageView imgPrev;
    @FXML
    private ImageView imgPause;
    @FXML
    private ImageView imgNext;
    @FXML
    private Label lblPlayed;
    @FXML
    private Slider slider;
    @FXML
    private Label lblFulltime;
    @FXML
    private ImageView exitClicked;
    @FXML
    private ImageView imgSong;
    @FXML
    private VBox vBoxLbls;
    @FXML
    private Label lblPlaylistName;
    @FXML
    private Label lblPlaylistLength;
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane videoPane;
    @FXML
    private MediaView mediaView;
    @FXML
    private AnchorPane audioPane;
    @FXML
    private AnchorPane controlsPane;
    @FXML
    private VBox vBoxSideBar;
    @FXML
    private HBox controlButtonsHBox;
    @FXML
    private ImageView exitClicked1;
    @FXML
    private AnchorPane mainAnchor;

    String filePath = "C:\\Users\\19par\\Desktop\\video.mp4";
    MediaPlayer mediaPlayer;
    @FXML
    private Slider volume;
    @FXML
    private StackPane stackPane;
    ArrayList<Media> list;

    int currentSongIndex = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        stackPane.prefWidthProperty().bind(mainAnchor.widthProperty());
        stackPane.prefHeightProperty().bind(mainAnchor.heightProperty());

        volume.setValue(volume.getMax());

        slider.valueProperty().addListener(this::sliderValueChanged);
        volume.valueProperty().addListener(this::volumeValueChanged);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), this::updateSlider));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        imgPause.setImage(new Image("play.png"));

    }

    private void videoPaneMouseMoved(MouseEvent t) {
        Platform.runLater(() -> vBoxSideBar.setVisible(true));
    }

    @FXML
    private void repeatClicked(MouseEvent event) {
    }

    @FXML
    private void shuffleClicked(MouseEvent event) {
    }

    @FXML
    private void addClicked(MouseEvent event) {

    }

    @FXML
    private void playlistClicked(MouseEvent event) {

        DirectoryChooser dc = new DirectoryChooser();
        File selectedDirectory = dc.showDialog(App.getStage());

        if (selectedDirectory != null) {

            list = new ArrayList<>();

            File[] pjesme = selectedDirectory.listFiles();
            for (File f : pjesme) {
                if (f.getName().endsWith(".mp3")) {
                    list.add(new Media(f.toURI().toString()));
                }
            }
            currentSongIndex = 0;
            fillList();
            mediaPlayer = new MediaPlayer(list.get(currentSongIndex));
            mediaView.setPreserveRatio(false);
            mediaView.setMediaPlayer(mediaPlayer);

        }

    }

    @FXML
    private void hideClicked(MouseEvent event) {
        controlsPane.setVisible(false);
    }

    @FXML
    private void exitClicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void sideBarEntered(MouseEvent event) {
        vBoxSideBar.setVisible(true);
    }

    public void updateSlider(ActionEvent event) {
        if (mediaPlayer != null) {
            slider.setValue(mediaPlayer.getCurrentTime().toSeconds());
        }

    }

    public void sliderValueChanged(Observable ov) {
        if (mediaPlayer == null) {
            return;
        }
        if (slider.isValueChanging()) {
            mediaPlayer.seek(new Duration(slider.getValue() * 1000));
        }
    }

    public void volumeValueChanged(Observable ov) {
        if (mediaPlayer == null) {
            return;
        }
        if (volume.isValueChanging()) {
            mediaPlayer.setVolume(volume.getValue() / 100.0);
        }
    }

    @FXML
    private void pauseClicked(MouseEvent event) {
        if (mediaPlayer == null) {
            return;
        }
        if (MediaPlayer.Status.READY == mediaPlayer.getStatus() || MediaPlayer.Status.PAUSED == mediaPlayer.getStatus()) {
            imgPause.setImage(new Image("pause.png"));
            mediaPlayer.play();
        } else {
            mediaPlayer.pause();
            imgPause.setImage(new Image("play.png"));
        }
    }

    @FXML
    private void sliderClicked(MouseEvent event) {
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.seek(new Duration(slider.getValue() * 1000));
    }

    @FXML
    private void volumeClicked(MouseEvent event) {
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.setVolume(volume.getValue() / 100.0);
    }

    private void fillList() {

        gridPane.getChildren().forEach((t) -> {
            Label temp = (Label) t;
            temp.setText("");
        });

        int x = list.size() < 5 ? list.size() : 5;

        for (int i = currentSongIndex; i < x + currentSongIndex; i++) {
            Label label1 = new Label("" + (i + 1));
            Label label2 = new Label(getName(list.get(i).getSource()));
            Label label3 = new Label();
            label3.setText("" + list.get(i).getDuration().toSeconds());
            label1.setTextFill(Color.WHITE);
            label2.setTextFill(Color.WHITE);
            label3.setTextFill(Color.WHITE);
            label1.setFont(Font.font(18));
            label2.setFont(Font.font(18));
            label3.setFont(Font.font(18));

            label1.setOnMouseClicked(this::labelClicked);
            label2.setOnMouseClicked(this::labelNameClicked);

            gridPane.add(label1, 0, i - currentSongIndex);
            gridPane.add(label2, 1, i - currentSongIndex);
            gridPane.add(label3, 2, i - currentSongIndex);

        }
    }

    public String getName(String s) {
        if (s.contains("%20")) {
            s = s.replace("%20", " ");
        }
        return s.substring(s.lastIndexOf("/") + 1, s.length() - 4);
    }

    @FXML
    private void upClicked(MouseEvent event) {
        if (list == null || list.size() < 5 || currentSongIndex < 1) {
            return;
        }
        currentSongIndex--;
        fillList();
    }

    @FXML
    private void downClicked(MouseEvent event) {
        if (list == null || list.size() < 5 || currentSongIndex > list.size() - 6) {
            return;
        }
        currentSongIndex++;
        fillList();
    }

    private void labelClicked(MouseEvent t) {
        Label temp = (Label) t.getSource();
        int indeks = Integer.parseInt(temp.getText());
        indeks--;
        startMediaPlayer(indeks);
    }

    private void labelNameClicked(MouseEvent t) {
        Label source = (Label) t.getSource();
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        colIndex--;

        Label temp = (Label) gridPane.getChildren().get(rowIndex * 3 + colIndex);
        int indeks = Integer.parseInt(temp.getText());
        indeks--;
        startMediaPlayer(indeks);
    }

    private void startMediaPlayer(int indeks) {
        mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(list.get(indeks));
        mediaView.setPreserveRatio(false);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
    }
}
