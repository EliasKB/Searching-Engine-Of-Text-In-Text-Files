
public class FileScore {
    int score;
    String fileName;
    int scoreInPercent;

    FileScore(int score, String fileName, int numberOfWords) {
        this.fileName = fileName;
        this.score = score;
        this.scoreInPercent = (int) (((double) score / numberOfWords) * 100);
    }

    public String toString() {
        return fileName + " : " + scoreInPercent + "% ";
    }



}
