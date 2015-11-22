package shagold.wifwaf.dataBase;

/**
 * Created by jimmy on 22/11/15.
 */
public class Walk {

    private String title;
    private String description;
    private WalkDifficulty difficulty;

    public Walk(String title, String description, WalkDifficulty difficulty) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public WalkDifficulty getDifficulty() {
        return difficulty;
    }
}
