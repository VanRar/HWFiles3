import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        openZip("E:\\Games\\zip.zip", "E:\\Games\\savegames");
        //можно создать дирректори и форичем пройтись по ней, если потребуется вывод всех сохранений, по заданию достаточно этого:
        System.out.println(openProgress("E:\\Games\\savegames\\gamer1.dat").toString());

    }

    public static void openZip(String pathFileZip, String pathSaveFile) {
        try (ZipInputStream zin = new ZipInputStream(new
                FileInputStream(pathFileZip))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName(); // получим название файла
                // распаковка
                FileOutputStream fout = new FileOutputStream((pathSaveFile + "\\" + name));
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String pathSaveFile) {
        GameProgress gameProgress = null;
            // откроем входной поток для чтения файла
        try (FileInputStream fis = new FileInputStream(pathSaveFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            // десериализуем объект и скастим его в класс
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }
}
