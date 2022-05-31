package edu.uw.tcss450.ckald.team1tcss450.utils;

import android.app.Activity;

import edu.uw.tcss450.ckald.team1tcss450.MainActivity;
import edu.uw.tcss450.ckald.team1tcss450.R;

public class Change {

    private static int sTheme;
    public final static int STANDARD_THEME = 0;
    public final static int PURPLE_THEME  = 1;
    public final static int TEAL_THEME = 2;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     *
     * @param activity
     * @param theme
     */
    public static void changeTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.recreate();
    }

    /**
     * Set the theme
     *
     * @param activity
     */
    public static void onActivityCreateSetTheme(MainActivity activity) {
        switch (sTheme) {
            default:
            case STANDARD_THEME:
                activity.getTheme().applyStyle(R.style.AppTheme, true);
                break;
            case PURPLE_THEME:
                activity.getTheme().applyStyle(R.style.ThemePurple, true);
                break;
            case TEAL_THEME:
                activity.getTheme().applyStyle(R.style.ThemeTeal, true);
                break;

        }
    }
}
