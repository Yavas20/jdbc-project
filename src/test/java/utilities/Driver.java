package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Driver {

    private Driver(){}

    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();

    //private static WebDriver driver;

    public static WebDriver getDriver(){

        if(driverPool.get() == null){

            String browserType = ConfigurationReader.getProperty("browser");

            switch (browserType){
                case "remote-chrome":
                    // assign your grid server address
                    String gridAdress = "54.172.24.30"; // put your own Linux grid IP here
                    try {
                        URL url = new URL("http://"+gridAdress+":4444/wd/hub");
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName("chrome");
                        driverPool.set(new RemoteWebDriver(url,desiredCapabilities));
                        driverPool.get().manage().window().maximize();
                        driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driverPool.set( new ChromeDriver());
                    driverPool.get() .manage().window().maximize();
                    driverPool.get() .manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set( new FirefoxDriver());
                    driverPool.get() .manage().window().maximize();
                    driverPool.get() .manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                    break;

            }


        }

        return driverPool.get() ;
    }

    /*
    This method will make sure our driver value is always null after using quit() method
     */
    public static void closeDriver(){
        if (driverPool.get()  != null){
            driverPool.get().quit(); ; // this line will terminate the existing session. value will not even be null
           driverPool.remove();
        }
    }



}
