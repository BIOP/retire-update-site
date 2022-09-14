package ch.epfl.biop.service.retire;

import com.google.gson.Gson;
import net.imglib2.realtransform.AffineTransform3D;
import net.imglib2.realtransform.RealTransform;
import org.scijava.command.CommandService;
import org.scijava.log.LogService;
import org.scijava.platform.PlatformService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.service.AbstractService;
import org.scijava.service.SciJavaService;
import org.scijava.service.Service;
import org.scijava.ui.UIService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

@Plugin(type = Service.class, headless = true)
public class RetireUpdateSitesService extends AbstractService implements
        SciJavaService
{

    @Parameter
    LogService logService;

    @Parameter
    UIService uiService;

    @Parameter
    PlatformService platformService;

    @Parameter
    CommandService commandService;
    /**
     * Service initialization
     */
    @Override
    public void initialize() {

        logService.warn(" --- Oh, oh, it looks like you have deprecated update sites in your configuration. ");

        String message = "Error during net connection, you'll have to find by yourself which are the update sites responsible for this message.";
        try {
            URL url = new URL("https://go.epfl.ch/biop-deprecated-update-site-message");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            message = "";
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.startsWith("#")) {
                    message += inputLine+"\n";
                }
            }
            in.close();
        } catch (Exception e) {
            logService.warn(e.getMessage());
            logService.warn("Error during connection, you have to find by yourself which are the update sites responsible for this message.");
        }

        if (!uiService.isHeadless()) {
            logService.warn("uiService detected -  a message to the user is displayed");
            // try to fetch information from the web
            // if not, display a general message
            System.out.println(message);
        } else {
            System.out.println(message);
            logService.warn(message);
        }

        System.out.println("There we go.");
    }

}
