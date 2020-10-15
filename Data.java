public class Data {

    // Initialize private class variables
    private final String key;
    private final int score, level;

    public Data(String key, int score, int level){
        this.key = key;
        this.score=score;
        this.level = level;
    }
    // Gets key
    public String getKey(){
        return this.key;
    }
    // Gets score
    public int getScore(){
        return this.score;
    }
    //Gets level
    public int getLevel(){
        return this.level;
    }

}
