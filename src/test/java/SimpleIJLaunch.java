
import net.imagej.ImageJ;

public class SimpleIJLaunch {

    public static void main(String... args) {
        // Arrange
        // create the ImageJ application context with all available services
        final ImageJ ij = new ImageJ();
        ij.ui().showUI();
    }
}