package Utillity;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.json.support.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ReportGen {
    private static Logger logger = LogManager.getLogger(ReportGen.class.getName());

    public static void htmlReportBuilder()
    {

                logger.info("###############################################################");

                logger.debug("Report Generation called");
                Date d = new Date();
                File reportOutputDirectory = new File("reports");
                List<String> jsonFiles = new ArrayList<>();
                jsonFiles.add("target/cucumber.json");
                String projectName = "POC Procon";
                String company = "Test";
                Configuration configuration = new Configuration(reportOutputDirectory, projectName);

                configuration.setNotFailingStatuses(Collections.singleton(Status.SKIPPED));

                configuration.addClassifications("Team Name:", "POC");
                configuration.addClassifications("Report Directory:", "reports");
                configuration.addClassifications("Run Date:", d.toString());
                configuration.addClassifications("Platform", "Windows");
                //configuration.addClassifications("Browser", "Chrome Browser");
                //configuration.addClassifications("Branch", "release/1.0");
                configuration.addClassifications("User Name:", System.getProperty("user.name"));
                configuration.addClassifications("Environment:", BrowserControl.environment);
                configuration.addClassifications("ChromeBrowser:", BrowserControl.ChromeBrowser);
                configuration.addClassifications("Firebox:", BrowserControl.Firebox);
                configuration.addClassifications("Internet Exp:", BrowserControl.IE);
                configuration.addClassifications("Edge Browser:", BrowserControl.Edge);
                ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
                Reportable result = reportBuilder.generateReports();
                logger.debug("Report Generated");
                logger.info("###############################################################");




    }
}
