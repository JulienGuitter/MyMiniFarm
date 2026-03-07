package fr.eseo.e3a.myminifarm.view.UI.modules;

import fr.eseo.e3a.myminifarm.controller.GameLoop;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserInfoUI {
    @FXML private Label moneyLabel;

    @FXML
    private void initialize(){
        GameLoop.getInstance().setUserInfoUI(this);
    }

    public void setMoneyLabel(int money){
        moneyLabel.setText(money + " $");
    }
}
