package fr.eseo.e3a.myminifarm.view.UI.modules;

import fr.eseo.e3a.myminifarm.controller.GameLoop;
import fr.eseo.e3a.myminifarm.utils.GameTime;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TimeInfoUI {
    @FXML private Label dayLabel;
    @FXML private Label hourLabel;

    @FXML
    private void initialize(){
        GameLoop.getInstance().setTimeInfoUI(this);
    }

    public void setDayLabel(GameTime gameTime){
        String dayText = String.format("%02d:%02d", gameTime.gethour(), gameTime.getminute());
        hourLabel.setText(dayText);
        int dayInMonth = gameTime.getday() % GameTime.DAY_IN_MONTH;
        String dayString = getDayString(gameTime.getday());
        String monthString = getMonthString(gameTime.getday());
        String dayLabelText = String.format("%s.  %02d  %s.", dayString, dayInMonth, monthString);
        dayLabel.setText(dayLabelText);
    }

    public String getDayString(int nbDay){
        int dayValue = (nbDay-1) % 7;
        String day = "";
        switch (dayValue) {
            case 0 -> day = "Mon";
            case 1 -> day = "Tue";
            case 2 -> day = "Wed";
            case 3 -> day = "Thu";
            case 4 -> day = "Fri";
            case 5 -> day = "Sat";
            case 6 -> day = "Sun";
        }
        return day;
    }

    public String getMonthString(int nbDay){
        int monthValue = (nbDay / 30)%12;
        String month = "";
        switch (monthValue) {
            case 0 -> month = "Jan";
            case 1 -> month = "Feb";
            case 2 -> month = "Mar";
            case 3 -> month = "Apr";
            case 4 -> month = "May";
            case 5 -> month = "Jun";
            case 6 -> month = "Jul";
            case 7 -> month = "Aug";
            case 8 -> month = "Sep";
            case 9 -> month = "Oct";
            case 10 -> month = "Nov";
            case 11 -> month = "Dec";
        }
        return month;
    }
}
