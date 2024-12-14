package Utillity;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.json.support.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ReportGen {

    private static final Logger logger = LogManager.getLogger(ReportGen.class.getName());

    public static void generateHtmlReport() {
        logger.info("##################### Starting Report Generation #####################");

        try {
            // Get current project directory and derive project name
            String projectDirectory = System.getProperty("user.dir");
            String projectName = new File(projectDirectory).getName();

            // Set the output directory and JSON files for the report
            Date runDate = new Date();
            File reportOutputDirectory = new File("reports");
            List<String> jsonFiles = new ArrayList<>();
            jsonFiles.add("target/cucumber.json");

            // Configuration setup
            Configuration configuration = new Configuration(reportOutputDirectory, projectName);
            configuration.setNotFailingStatuses(Collections.singleton(Status.SKIPPED)); // Non-failing statuses

            // Add metadata for the report
            configuration.addClassifications("Project Name", projectName);
            configuration.addClassifications("Team Name", "POC");
            configuration.addClassifications("Report Directory", "reports");
            configuration.addClassifications("Execution Date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            configuration.addClassifications("Platform", "Windows");
            configuration.addClassifications("Java Version", System.getProperty("java.version"));
            configuration.addClassifications("Git Branch", System.getenv("BRANCH_NAME") != null ? System.getenv("BRANCH_NAME") : "master");
            configuration.addClassifications("Git Commit", System.getenv("GIT_COMMIT") != null ? System.getenv("GIT_COMMIT") : "N/A");

            configuration.addClassifications("User Name", System.getProperty("user.name"));
            configuration.addClassifications("Environment", BrowserControl.environment);
            configuration.addClassifications("Chrome Browser", BrowserControl.ChromeBrowser);
            configuration.addClassifications("Firefox", BrowserControl.Firebox);
            configuration.addClassifications("Internet Explorer", BrowserControl.IE);
            configuration.addClassifications("Edge Browser", BrowserControl.Edge);

            // Generate the report
            logger.debug("Report generation initiated...");
            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
            Reportable result = reportBuilder.generateReports();

            // Log the success/failure of the report generation
            if (result != null && result.getFailedFeatures() == 0) {
                logger.info("Report successfully generated. No failed features.");
            } else {
                logger.warn("Report generated with some failed features.");
            }

        } catch (Exception e) {
            logger.error("Error occurred while generating the report: ", e);
        } finally {
            logger.info("##################### Report Generation Completed #####################");
        }
    }
}