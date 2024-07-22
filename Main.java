import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String csvFile = "spotify_data.csv";
        String prueba = scanner.nextLine();
        try{
            File archivo = new File(csvFile);
            Scanner sc = new Scanner(archivo);
            while (sc.hasNextLine()){
                String siguiente = sc.nextLine();
                String[] values = siguiente.split(",");
                if(values[2].equals(prueba)){
                  for (String value : values){
                      System.out.print(value + " ");
                  }
                  System.out.println();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}