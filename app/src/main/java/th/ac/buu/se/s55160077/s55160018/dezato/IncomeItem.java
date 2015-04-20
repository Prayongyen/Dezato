package th.ac.buu.se.s55160077.s55160018.dezato;

import android.graphics.drawable.Drawable;

/**
 * Created by LUKHINNN on 19/04/2015.
 */
public class IncomeItem {
    private String txtDate;
    private String txtMoney;

    public IncomeItem(String txtDate,String txtMoney)
    {
        this.txtDate = txtDate;
        this.txtMoney = txtMoney;
    }

    public String getTxtDate() {
        return txtDate;
    }

    public void setTxtDate(String txtDate) {
        this.txtDate = txtDate;
    }

    public String getTxtMoney() {
        return txtMoney;
    }

    public void getTxtMoney(String txtMoney) {
        this.txtMoney = txtMoney;
    }
}
