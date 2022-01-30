package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager{

    private static ExtentReports extentReports;

    /**
     * This method configures the appearance of the extent report
     */
    public synchronized static ExtentReports createReportInstance() {
        String reportFileName =
                new SimpleDateFormat("hh-mm-ss dd-MM-yyyy").format(new Date()) + "reports.html";
        String reportFilePath =
                System.getProperty("user.dir") + File.separator + "reports" + File.separator
                        + reportFileName;
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportFilePath);
        htmlReporter.config().setTheme(Theme.DARK); //Theme.STANDARD
        htmlReporter.config().setDocumentTitle("automation run report");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("");


        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
        return extentReports;
    }

    public synchronized static ExtentReports createReportInstance(String suiteName) {
        Date date = new Date();
        String reportFileName = new SimpleDateFormat("dd-MM-yyyy").format(date) + "_" + suiteName + "_"
                + new SimpleDateFormat("hh-mm-ss").format(date) + "_run_report.html";
        String reportFilePath =
                System.getProperty("user.dir") + File.separator + "reports" + File.separator
                        + reportFileName;
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportFilePath);
        htmlReporter.config().setTheme(Theme.DARK); //Theme.STANDARD
        htmlReporter.config().setDocumentTitle("automation run report");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("");

        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
        return extentReports;
    }
}
