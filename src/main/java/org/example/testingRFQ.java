package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Alert;
import org.openqa.selenium.interactions.Actions;
import java.util.Scanner;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import java.time.Duration;
public class testingRFQ {
    WebDriver driver;
    WebDriverWait wait;
    Scanner scanner = new Scanner(System.in);
    Actions actions;
    Alert alert;
    ChromeOptions options = new ChromeOptions();
    String adminPortal = "https://vipmalldemo.erpca.shop/login";
    String vendorPortal = "https://vipmalldemovendor.erpca.shop/login";

    @BeforeClass
    public void setUp() {
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(59));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
    }
    public void checkStatus() {
        WebElement status = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr[1]/td[7]")));
        String statusText = status.getText().strip();
        System.out.println("Status: " + statusText);

    }
    public void vendorUpdatesWorkProgress() {
        WebElement wipButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[normalize-space()='Work Progress'])[1]")));
        actions.moveToElement(wipButton).click().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='work_progress']"))).sendKeys("100");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }
    public void vendorAddsSupportingDocument() {
        WebElement fileInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Supporting Documents']")));
        actions.moveToElement(fileInput).click().perform();
        WebElement uploadDoc = driver.findElement(By.xpath("//input[@type='file']"));
        uploadDoc.sendKeys("C:\\Users\\Admin\\Pictures\\Saved Pictures\\Testing.jpg");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }
    @Test(priority = 1)
    public void loginToAdminPortal() {
        driver.get(adminPortal);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("login_username"))).sendKeys("admin");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys("Admin@123");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Log In']"))).click();
    }

    @Test(priority = 2)
    public void adminNavigatingToRfqForm() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Project Management']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='All RFQ']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Add RFQ']"))).click();
    }

    @Test(priority = 3)
    public void adminFillsRfqForm() {
        WebElement projectid = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='project_id']")));
        Select projectSelect = new Select(projectid);
        projectSelect.selectByValue("16");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='rfq_title']"))).sendKeys("RFQ title will be here");

        WebElement goodsAndServicesHead = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='good_service_head_id']")));
        Select goodsAndServices = new Select(goodsAndServicesHead);
        goodsAndServices.selectByValue("1");

        WebElement goodsAndServicesSubHead = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='good_service_subhead_id']")));
        Select goodsAndServicesSub = new Select(goodsAndServicesSubHead);
        goodsAndServicesSub.selectByValue("3");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='budget']"))).sendKeys("10000");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='issue_date']"))).sendKeys("19062025");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='valid_to']"))).sendKeys("14122025");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='security_deposit_amount']"))).sendKeys("10");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter Task']"))).sendKeys("Task 01");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter Document details']"))).sendKeys("Doc 01");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Add Row']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@placeholder='Enter Task'])[2]"))).sendKeys("Task 02");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@placeholder='Enter Document details'])[2]"))).sendKeys("Doc 02");
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//input[@id='tender_document']"))));
        fileInput.sendKeys("C:\\Users\\Admin\\Pictures\\Saved Pictures\\Testing.jpg");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@id='request-textarea']"))).sendKeys("Requirement Specification will be here");
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Save']")));
        actions.moveToElement(saveButton).click().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        checkStatus();
    }

    @Test(priority = 4)
    public void adminInvitesVendor() throws InterruptedException {
        WebElement action = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr[1]/td[8]/div[1]/select[1]")));
        Select actionSelect = new Select(action);
        actionSelect.selectByVisibleText("Send invitations");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Invite Vendors']"))).click();
        WebElement vendorType = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@placeholder='Enter Your Vendor Type']")));
        Select actionSelects = new Select(vendorType);
        actionSelects.selectByValue("16");
        WebElement vendorSelect = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'css-19bb58m') and @data-value=\"\"]")));
        vendorSelect.click();
        WebElement inputBox = driver.findElement(By.id("react-select-2-input"));
        inputBox.sendKeys("Demo vendor");
        inputBox.sendKeys(Keys.ENTER);
        inputBox.sendKeys("MockVendor");
        inputBox.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='subject']"))).sendKeys("Subject will be here");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label='rdw-editor']"))).sendKeys("You are invited to an RFQ");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Send Invitations']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 5)
    public void vendorNavigatesToRFQ() throws InterruptedException {
        driver.get(vendorPortal);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("login_username"))).sendKeys("vendor");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys("Admin@123");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Log In']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Proceed to Dashboard']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Project Management']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='All RFQ']"))).click();
        checkStatus();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[contains(@href, '/project-management/view-rfq-details/') and .//i[contains(@class,'mdi-chevron-right')]])[1]"))).click();
    }

    @Test(priority = 6, dependsOnMethods = "vendorNavigatesToRFQ")
    public void vendorAcceptsBid() throws InterruptedException {
        Thread.sleep(2000);
        WebElement acceptButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Accept & Submit Bid']")));
        actions.moveToElement(acceptButton).click().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='brand_name']"))).sendKeys("Brand name ill be here");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='work_detail']"))).sendKeys("Work details will be here");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='proposal_amount']"))).sendKeys("10000");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='gst_amount']"))).sendKeys("500");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='work_timeline']"))).sendKeys("12122025");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='payment_term']"))).sendKeys("Payment terms will be here");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='work_warranty']"))).sendKeys("365");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit Bid']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 7)
    public void vendorRejectRFQ() throws InterruptedException {
        driver.get(vendorPortal);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("login_username"))).sendKeys("anurag");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys("Admin@1234");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Log In']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Proceed to Dashboard']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Project Management']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='All RFQ']"))).click();
        checkStatus();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[contains(@href, '/project-management/view-rfq-details/') and .//i[contains(@class,'mdi-chevron-right')]])[1]"))).click();
        WebElement rejectButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Reject']")));
        actions.moveToElement(rejectButton).click().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='reject_reason']"))).sendKeys("Reject Reason will be displayed here");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    public void adminNavigateToViewRFQ() throws InterruptedException {
        loginToAdminPortal();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Project Management']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='All RFQ']"))).click();
        checkStatus();
        WebElement actionButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr[1]/td[8]/div[1]/select[1]")));
        Select actionSelects = new Select(actionButton);
        actionSelects.selectByVisibleText("View RFQ Detail");
    }

    @Test(priority = 8)
    public void negotiateByAdmin() throws InterruptedException {
        adminNavigateToViewRFQ();
        WebElement negotiateButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Negotiate']")));
        actions.moveToElement(negotiateButton).click().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='negotiation_cost']"))).sendKeys("5000");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='negotiate_end_date']"))).sendKeys("12232025");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 9, dependsOnMethods = "negotiateByAdmin")
    public void vendorNegotiatesBid() throws InterruptedException {
        vendorNavigatesToRFQ();
        WebElement negotiateB = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Negotiate']")));
        actions.moveToElement(negotiateB).click().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Negotiate Bid']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 10)
    public void adminAcceptsBid() throws InterruptedException {
        adminNavigateToViewRFQ();
        WebElement acceptButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Accept']")));
        actions.moveToElement(acceptButton).click().perform();
        WebElement okay = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']")));
        actions.moveToElement(okay).click().perform();
    }

    @Test(priority = 11, dependsOnMethods = "adminAcceptsBid")
    public void vendorProvideEstimatedTime() throws InterruptedException {
        vendorNavigatesToRFQ();
        WebElement estimate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Add Estimated Time']")));
        actions.moveToElement(estimate).click().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@name='tasks[0].estimated_completion_date'])[1]"))).sendKeys("12122025");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@name='tasks[1].estimated_completion_date'])[1]"))).sendKeys("12122025");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Save Changes']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 12)
    public void adminAddsMilestone() throws InterruptedException {
        Thread.sleep(2000);
        adminNavigateToViewRFQ();
        WebElement milestone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space(text())='Milestones']")));
        actions.moveToElement(milestone).click().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='title-0']"))).sendKeys("Milestone 01");
        WebElement taskSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'css-19bb58m')]")));
        taskSelect.click();
        WebElement inputB = driver.findElement(By.id("react-select-2-input"));
        inputB.sendKeys("Task 01");
        inputB.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='payment-0']"))).sendKeys("5250");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='advance_payment-0']"))).sendKeys("250");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Add Row']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='title-1']"))).sendKeys("Milestone 02");
        taskSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'css-19bb58m')]")));
        taskSelect.click();
        WebElement inputBb = driver.findElement(By.id("react-select-3-input"));
        inputBb.sendKeys("Task 02");
        inputBb.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='payment-1']"))).sendKeys("5250");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='advance_payment-1']"))).sendKeys("250");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Save Changes']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 13)
    public void addExtraMilestone() throws InterruptedException {
        adminNavigateToViewRFQ();
        WebElement addMilestoneButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Add Milestone']")));
        actions.moveToElement(addMilestoneButton).click().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='milestone_title']"))).sendKeys("Milestone 03");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='milestone_amount']"))).sendKeys("5250");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='advance_payment']"))).sendKeys("250");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter Task']"))).sendKeys("Task 03");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter Document details']"))).sendKeys("Doc 03");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter Estimated Completion Date']"))).sendKeys("12122025");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Save Changes']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 14)
    public void vendorStartsWork() throws InterruptedException {
        vendorNavigatesToRFQ();
        WebElement actionButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@type='button' and contains(@class, 'dropdown-toggle')])[1]")));
        System.out.println(actionButton1);
        actions.moveToElement(actionButton1).click().perform();
        WebElement startButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dropdown-menu show']//button[@role='menuitem'][normalize-space()='Start']")));
        actions.moveToElement(startButton).click().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        WebElement actionButton2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@type='button' and contains(@class, 'dropdown-toggle')])[2]")));
        actions.moveToElement(actionButton2).click().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dropdown-menu show']//button[@role='menuitem'][normalize-space()='Start']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        WebElement actionButton3 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@type='button' and contains(@class, 'dropdown-toggle')])[3]")));
        actions.moveToElement(actionButton3).click().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dropdown-menu show']//button[@role='menuitem'][normalize-space()='Start']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 15)
    public void addingProgressAndSupportingDocForFirstMileStone() throws InterruptedException {
        WebElement actionButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@type='button' and contains(@class, 'dropdown-toggle')])[1]")));
        actions.moveToElement(actionButton1).click().perform();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Work Progress']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='work_progress']"))).sendKeys("50");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='work_description']"))).sendKeys("Work desc will appear here");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        //Adding Document now
        driver.navigate().refresh();
        actionButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@type='button' and contains(@class, 'dropdown-toggle')])[1]")));
        actions.moveToElement(actionButton1).click().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Supporting Documents']"))).click();
        WebElement pushImage = driver.findElement(By.xpath("//input[@type='file']"));
        pushImage.sendKeys("C:\\Users\\Admin\\Pictures\\Saved Pictures\\Testing.jpg");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 16)
    public void vendorRequestsAmount() throws InterruptedException {
        driver.navigate().refresh();
        WebElement requestForAmount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Request for Amount?']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", requestForAmount);
        Thread.sleep(500);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", requestForAmount);
        WebElement milestoneElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='milestone_id']")));
        Select milestone = new Select(milestoneElement);
        milestone.selectByVisibleText("Milestone 01 (Pending Amount: 5000)");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='amount_requested']"))).sendKeys("2500");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='request_advance_desc']"))).sendKeys("Desc will appear here");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        driver.navigate().refresh();
        WebElement requestForAmount1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Request for Amount?']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", requestForAmount1);
        Thread.sleep(500);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", requestForAmount1);
        milestoneElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='milestone_id']")));
        milestone = new Select(milestoneElement);
        milestone.selectByVisibleText("Milestone 02 (Pending Amount: 5000)");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='amount_requested']"))).sendKeys("2500");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='request_advance_desc']"))).sendKeys("Desc will appear here");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        driver.navigate().refresh();
        WebElement requestForAmount2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Request for Amount?']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", requestForAmount2);
        Thread.sleep(500);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", requestForAmount2);
        milestoneElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='milestone_id']")));
        milestone = new Select(milestoneElement);
        milestone.selectByVisibleText("Milestone 03 (Pending Amount: 5000)");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='amount_requested']"))).sendKeys("2500");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='request_advance_desc']"))).sendKeys("Desc will appear here");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 17)
    public void adminAcceptsTheAmount() throws InterruptedException {
        adminNavigateToViewRFQ();
        
        WebElement acbtn01 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'][normalize-space()='Accept'])[1]")));
        actions.scrollToElement(acbtn01);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", acbtn01);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='paid_amount']"))).sendKeys("2500");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        WebElement acbtn02 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'][normalize-space()='Accept'])[2]")));
        actions.moveToElement(acbtn02);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", acbtn02);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='paid_amount']"))).sendKeys("2500");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        WebElement acbtn03 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'][normalize-space()='Accept'])[3]")));
        actions.moveToElement(acbtn03);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", acbtn03);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='paid_amount']"))).sendKeys("2500");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 18)
    public void adminAddsPayment() throws InterruptedException {
        driver.navigate().refresh();
        WebElement addPayemntButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Add Payment']")));
        actions.moveToElement(addPayemntButton).click().perform();
        WebElement milestoneElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='milestone_id']")));
        Select milestone = new Select(milestoneElement);
        milestone.selectByVisibleText("Milestone 01");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='paid_amount']"))).sendKeys("2500");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='request_advance_desc']"))).sendKeys("Desc will appear here");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        addPayemntButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Add Payment']")));
        actions.moveToElement(addPayemntButton).click().perform();
        milestone.selectByVisibleText("Milestone 02");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='paid_amount']"))).sendKeys("2500");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='request_advance_desc']"))).sendKeys("Desc will appear here");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        addPayemntButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Add Payment']")));
        actions.moveToElement(addPayemntButton).click().perform();
        milestone.selectByVisibleText("Milestone 03");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='paid_amount']"))).sendKeys("2500");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='request_advance_desc']"))).sendKeys("Desc will appear here");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 19)
    public void vendorCompletesMilestone() throws InterruptedException {
        vendorNavigatesToRFQ();
        WebElement actionButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@type='button'])[2]")));
        actionButton1.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Complete']"))).click();
        WebElement pushImage = driver.findElement(By.xpath("//input[@type='file']"));
        pushImage.sendKeys("C:\\Users\\Admin\\Pictures\\Saved Pictures\\Testing.jpg");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        WebElement actionButton2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@type='button'])[4]")));
        actionButton2.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Complete']"))).click();
        WebElement pushhImage = driver.findElement(By.xpath("//input[@type='file']"));
        pushhImage.sendKeys("C:\\Users\\Admin\\Pictures\\Saved Pictures\\Testing.jpg");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        WebElement actionButton3 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@type='button'])[6]")));
        actionButton3.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Complete']"))).click();
        WebElement pushhhImage = driver.findElement(By.xpath("//input[@type='file']"));
        pushhhImage.sendKeys("C:\\Users\\Admin\\Pictures\\Saved Pictures\\Testing.jpg");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 20)
    public void vendorSendsForReview() throws InterruptedException {
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'][normalize-space()='Send For Review'])[1]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'][normalize-space()='Send For Review'])[2]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'][normalize-space()='Send For Review'])[3]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 21)
    public void adminApprovesMilestoneexceptFirstMilestone() throws InterruptedException {
        adminNavigateToViewRFQ();
        // Rejecting first milestone
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'][normalize-space()='Reject'])[1]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='rework_reason']"))).sendKeys("Rework reason will appear here");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Rework']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@class='swal2-confirm swal2-styled'])[1]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        //Accepting 2nd and 3rd
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'][normalize-space()='Approve'])[1]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'][normalize-space()='Approve'])[2]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        // Approving Milestone
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'][normalize-space()='Approve'])[1]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'][normalize-space()='Approve'])[2]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 22)
    public void vendorSubmitsJustification() throws InterruptedException {
        vendorNavigatesToRFQ();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn-sm dropdown-toggle btn btn-primary']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Justify']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='rework_reject_justification']"))).sendKeys("Justification will appear here");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        // Sends for review
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'][normalize-space()='Send For Review'])[1]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 23)
    public void adminSendsForRework() throws InterruptedException {
        adminNavigateToViewRFQ();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'][normalize-space()='Reject'])[1]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='rework_reason']"))).sendKeys("Rework reason will appear here");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Rework']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@class='swal2-confirm swal2-styled'])[1]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 24)
    public void vendorDoRework() throws InterruptedException {
        vendorNavigatesToRFQ();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn-sm dropdown-toggle btn btn-primary']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Start']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn-sm dropdown-toggle btn btn-primary']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Complete']"))).click();
        WebElement pushImage = driver.findElement(By.xpath("//input[@type='file']"));
        pushImage.sendKeys("C:\\Users\\Admin\\Pictures\\Saved Pictures\\Testing.jpg");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Submit']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        // Send for review
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Send For Review']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 25)
    public void adminApprovesRework() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'][normalize-space()='Approve'])[1]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
        // Approves Milestone
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'][normalize-space()='Approve'])[1]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @Test(priority = 26)
    public void adminClosesRFQ() throws InterruptedException {
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Close']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@class='swal2-confirm swal2-styled'])[1]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='OK']"))).click();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}