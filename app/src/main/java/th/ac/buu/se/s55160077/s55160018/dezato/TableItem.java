package th.ac.buu.se.s55160077.s55160018.dezato;

import android.graphics.drawable.Drawable;

/**
 * Created by prayong on 18/4/2558.
 */
public class TableItem {
    private Drawable icon;       // the drawable for the ListView item ImageView
    private String title;        // the text for the GridView item title

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TableItem(Drawable icon, String title) {
        this.icon = icon;
        this.title = title;
    }
}
