package Utillity;

import PageLocators.LoginPageLocators;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static Utillity.HelperFunction.waitForPageToLoad;

public class BrowserControl {

    private static final Logger logger = LogManager.getLogger(BrowserControl.class.getName());
    public static BrowserControl INSTANCE = new BrowserControl();
    public static String environment;
    public static String ChromeBrowser;
    public static String Firebox;
    public static String Edge;
    public static String IE;
    static Properties prop = new Properties();

    public static void loadPropertiesFile() {

        try {
            //String filename = "src//test//resources//Browser.properties";
            String filename = "src/test/resources/Browser.properties";
            BrowserControl.class.getClassLoader().getResourceAsStream(filename);
            prop.load(new FileInputStream(filename));
            ChromeBrowser = prop.getProperty("ChromeBrowser");
            Firebox = prop.getProperty("FirefoxBrowser");
            IE = prop.getProperty("InternetExplorer");

            Edge = prop.getProperty("EdgeBrowser");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openChromeBrowser() {


        //Load the properties file
        loadPropertiesFile();
        if (ChromeBrowser.compareToIgnoreCase("Yes") == 0) {
            logger.debug("Driver initiated ");

            logger.debug("Chrome Browser lanuched Successfully");

            WebDriverManager.chromedriver().setup();
            //System.setProperty("webdriver.chrome.driver", getDriverPath());
            WebDriverFactory.getInstance().driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            environment = prop.getProperty("URl");
            logger.debug("URL" + environment);
            WebDriverFactory.getInstance().driver.get(environment);


        }
    }

    public static void openedgeBrowser() {
        PageFactory.initElements(WebDriverFactory.driver, LoginPageLocators.class);

        loadPropertiesFile();

        if (Edge.compareToIgnoreCase("Yes") == 0) {
            logger.debug("Driver initiated ");
            EdgeOptions edgeOptions = new EdgeOptions();

            // Add options
            edgeOptions.addArguments("--start-maximized");  // Launch Edge maximized
            edgeOptions.addArguments("--inprivate");

            // options.addArguments("--headless");        // Run in headless mode
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setPlatform(Platform.WINDOWS);
            desiredCapabilities.setBrowserName(Edge);
            desiredCapabilities.setBrowserName("MicrosoftEdge");

            Map<String, Object> edgePrefs = new HashMap<String, Object>();
            edgePrefs.put("profile.default_content_settings.popups", 0);
            edgePrefs.put("download.prompt_for_download", false);


            edgeOptions.setCapability("ms:inPrivate", true);
            edgeOptions.setCapability("prefs", edgePrefs);
            edgeOptions.setCapability("useAutomationExtension", false);


            edgeOptions.merge(desiredCapabilities);

            edgeOptions.setCapability("ms:inPrivate", true);
            edgeOptions.setCapability("useAutomationExtension", false);

            WebDriverFactory.driver = new EdgeDriver();
            WebDriverFactory.driver.manage().window().maximize();

            logger.debug("Edge Browser lanuched Successfully");
            WebDriverFactory.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebDriverFactory.driver.manage().window().maximize();
            environment = prop.getProperty("uat_URl");
            logger.debug("URL" + environment);
            WebDriverFactory.driver.get(environment);
            waitForPageToLoad(WebDriverFactory.getInstance().driver, Duration.ofSeconds(50));

        }
    }

    public static void openfirboxBrowser() {

        loadPropertiesFile();

        if (Edge.compareToIgnoreCase("Yes") == 0) {
            logger.debug("Driver initiated ");
            WebDriverManager.edgedriver().setup();
            WebDriverFactory.getInstance().driver = new EdgeDriver();
            logger.debug("Edge Browser lanuched Successfully");
            WebDriverFactory.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebDriverFactory.driver.manage().window().maximize();
            environment = prop.getProperty("URl");
            logger.debug("URL" + environment);
            WebDriverFactory.driver.get(environment);


        }

    }

    public static BrowserControl getInstance() {
        return INSTANCE;
    }

    public static void driverBrowser(String browserType) {
        switch (browserType.toLowerCase()) {
            case "chrome":
                openChromeBrowser();
                logger.info("Chrome Driver lanched");
                break;

            case "firefox":
                openfirboxBrowser();
                logger.info("firbox Driver lanched");

                break;

            case "edge":
                openedgeBrowser();
                logger.info("edge Driver lanched");

                break;

            default:
                throw new IllegalArgumentException("Invalid browser name: " + browserType);
        }


    }

    private String getDriverPath() {
        String driverPath = "drivers/%s";
        if (SystemUtils.IS_OS_MAC) {
            return String.format(driverPath, "chromedriver");
        } else {
            return String.format(driverPath, "chromedriver.exe");
        }

    }
}
