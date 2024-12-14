package Utillity;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.json.support.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CustomReportGen {

    private static final Logger logger = LogManager.getLogger(CustomReportGen.class.getName());

    public static void generateHtmlReport() {
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

        // Add customizations
        try {
            injectCustomHtml(reportOutputDirectory);
        } catch (IOException e) {
            logger.error("Error injecting custom HTML: ", e);
        }
    }

    private static void injectCustomHtml(File reportDirectory) throws IOException {
        // Target report file
        File reportFile = new File(reportDirectory, "cucumber-html-reports/overview-features.html");

        // Read the report file content
        Path path = Paths.get(reportFile.getPath());
        String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

        // Add custom CSS for the table (apply background to entire table)
        String customCSS = """
            <style>
                /* General table styling */
                .table {
                    border-collapse: collapse;
                    width: 100%;
                    margin: 20px 0;
                    background-color: #007bff;  /* Set table background to blue */
                }

                /* Table cell styling */
                .table th, .table td {
                    border: 1px solid #dee2e6;
                    padding: 10px;
                    text-align: left;
                    color: black; /* Set text color to white */
                }

                /* Set the background color of the header cells */
                .table th {
                    background-color: #007bff;  /* Set header background to blue */
                }

                /* Set the background color for all table cells */
                .table td {
                    background-color: #007bff;  /* Set data cells background to blue */
                    color: black;  /* Ensure text is white */
                }

            </style>
        """;

        // Add branding
        String brandingHtml = """
            <header style="text-align: center; margin-bottom: 20px;">
                <!-- Using an absolute path for local testing (not recommended for sharing reports) -->
                <img src="/images/logo.png" alt="Company Logo" style="width: 150px;"/>
                <h1>Automation Test Report</h1>
                <p>Generated by KeenMinds Automation Team</p>
            </header>
        """;

        // Add footer
        String footerHtml = """
            <footer>
                <p>&copy; 2024 MindSprint. All Rights Reserved.</p>
            </footer>
        """;

        // Insert custom HTML and CSS into the report
        content = content.replace("</head>", customCSS + "</head>");
        content = content.replace("<body>", brandingHtml + "<body>");
        content = content.replace("</body>", footerHtml + "</body>");

        // Write the updated content back to the file
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));

        logger.info("Custom table styles and branding injected successfully.");
    }
}
