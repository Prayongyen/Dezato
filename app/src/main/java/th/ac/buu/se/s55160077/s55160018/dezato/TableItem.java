package th.ac.buu.se.s55160077.s55160018.dezato;

import android.graphics.drawable.Drawable;

/**
 * Created by prayong on 18/4/2558.
 */
public class TableItem {
    public String getTxtTableStatus() {
        return TxtTableStatus;
    }

    public void setTxtTableStatus(String setTxtTableStatus) {
        this.TxtTableStatus = setTxtTableStatus;
    }

    private Drawable imgTable;       // the drawable for the ListView item ImageView
    private String txtTableNo;        // the text for the GridView item title
    private String txtTableMessage;
    private String TxtTableStatus;

    public Drawable getImgTable() {
        return imgTable;
    }

    public void setImgTable(Drawable imgTable) {
        this.imgTable = imgTable;
    }

    public String getTxtTableNo() {
        return txtTableNo;
    }

    public void setTxtTableNo(String txtTableNo) {
        this.txtTableNo = txtTableNo;
    }

    public String getTxtTableMessage() {
        return txtTableMessage;
    }

    public void setTxtTableMessage(String txtTableMessage) {
        this.txtTableMessage = txtTableMessage;
    }

    public TableItem() {
    }

    public TableItem(Drawable imgTable, String txtTableNo,String txtTableMessage,String TxtTableStatus) {
        this.imgTable = imgTable;
        this.txtTableNo = txtTableNo;
        this.txtTableMessage = txtTableMessage;
        this.TxtTableStatus = TxtTableStatus;
    }
}
