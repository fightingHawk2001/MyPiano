package piano;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class AddImage {
    private BufferedImage image = null;

    public AddImage(String path) {
        BufferedImage image = null;
        InputStream is = AddImage.class.getResourceAsStream(path);
        try {
            image = ImageIO.read(is);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.image = image;
    }

    public BufferedImage addImage() {
        return this.image;
    }
}
