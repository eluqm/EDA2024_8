import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SongManager {
    private HashMap<String, List<Song>> artistMap;
    private HashMap<String, List<Song>> songNameMap;

    public SongManager() {
        artistMap = new HashMap<>();
        songNameMap = new HashMap<>();
    }

}
