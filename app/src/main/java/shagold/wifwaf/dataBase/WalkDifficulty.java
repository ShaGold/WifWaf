package shagold.wifwaf.dataBase;

import android.graphics.Color;

/**
 * Created by jimmy on 22/11/15.
 */
public enum WalkDifficulty {

    VERY_EASY (Color.WHITE),
    EASY (Color.GREEN),
    MEDIUM (Color.YELLOW),
    HARD (Color.RED),
    VERY_HARD (Color.BLACK);

    private int template;

    WalkDifficulty(int template) {
        this.template = template;
    }

    public int getTemplate() {
        return template;
    }
}
