import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Main{
    public static void main(String[] args){
        String csvFile = "spotify_data.csv";
        try{
            File archivo = new File(csvFile);
            Scanner sc = new Scanner(archivo);
            int fila = 0;
            while (sc.hasNextLine() && fila < 50){
                String siguiente = sc.nextLine();
                String[] values = siguiente.split(",");
                for (String value : values){
                    System.out.print(value + " ");
                }
                System.out.println();
                fila++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}