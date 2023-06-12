import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConvertAVIFtoPNG {
    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("GRAMADO_AVIF.avif"));
            ImageIO.write(image, "PNG", new File("fundo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
