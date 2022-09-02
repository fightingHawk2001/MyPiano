package piano;

import util.Audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Start {
    private static final String newVersion = "6.4.7 Debug";

    public static String getNewVersion() {
        return newVersion;
    }

    public Start(){
        new Audio("audio/test.mp3").start();
        File file = new File("C:\\Users\\" + System.getenv("USERNAME") + "\\Documents", "MyPiano.cfg");
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                String version = "version = " + newVersion + " [version]";
                fos.write(version.getBytes());
                fos.write("\n".getBytes());
                fos.write("isShowUpdate = 1 [isShowUpdate]".getBytes());
                fos.write("\n".getBytes());
                fos.write("isShowHelp = 1 [isShowHelp]".getBytes());
                fos.write("\n".getBytes());
                fos.write("model = 2 [model]".getBytes());
                fos.write("\n".getBytes());
                fos.write("size = 20".getBytes());
                fos.write("\n".getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] byt = new byte[256];
                int len = fis.read(byt);
                String read = new String(byt, 0, len);
                fis.close();
                String oldVersion = read.substring
                        (read.indexOf("version = ") + "version = ".length(), read.indexOf(" [version]"));
                if (!newVersion.equals(oldVersion)) {
                    FileOutputStream fos = new FileOutputStream(file);
                    String version = "version = " + newVersion + " [version]";
                    fos.write(version.getBytes());
                    fos.write("\n".getBytes());
                    fos.write("isShowUpdate = 1 [isShowUpdate]".getBytes());
                    fos.write("\n".getBytes());
                    fos.write("isShowHelp = 1 [isShowHelp]".getBytes());
                    fos.write("\n".getBytes());
                    fos.write("model = 2 [model]".getBytes());
                    fos.write("\n".getBytes());
                    fos.write("size = 20".getBytes());
                    fos.write("\n".getBytes());
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Piano piano = new Piano();
        piano.startFrame();
    }

    public static void main(String[] args) {
        new Start();
    }

}
