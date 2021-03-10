package nasretdinovir.example.dodgeGame.Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Cache {

    private static final String SCORE_FILE_NAME = "score.bin";
    private File cacheDir;

    public Cache(File cacheDir) {
        this.cacheDir = cacheDir;
    }

    public int readScoreFromCache() {
        int score = 0;
        File cacheFile = new File(cacheDir, SCORE_FILE_NAME);

        FileInputStream fileIn;
        ObjectInputStream in;
        try {
            fileIn = new FileInputStream(cacheFile);
            in = new ObjectInputStream(fileIn);

            score = (int) in.readObject();

            in.close();
            fileIn.close();

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
        return score;
    }


    public void writeScoreToCache(int score) {
        FileOutputStream fileOut;
        ObjectOutputStream out;
        try {
            File cacheFile = new File(cacheDir, SCORE_FILE_NAME);

            fileOut = new FileOutputStream(cacheFile);
            out = new ObjectOutputStream(fileOut);

            out.writeObject(score);

            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
