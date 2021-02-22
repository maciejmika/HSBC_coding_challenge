package HSBC.customersPage;

import HSBC.TestData;
import HSBC.basePage.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersPage extends BasePage {

    @FindBy(xpath = "//h3[text() = 'Customers']")
    private WebElement customersHeader;

    @FindBy(id = "search-input")
    private WebElement searchInputField;

    @FindBy(id = "search-column")
    private WebElement filtersDropdown;

    @FindBy(id = "match-case")
    private WebElement matchCaseCheckbox;

    @FindBy(id = "clear-button")
    private WebElement clearAllFiltersButton;

    @FindBy(id = "search-slogan")
    private WebElement searchInfo;

    private By resultsTableRow = By.cssSelector("tr[ng-repeat='customer in filteredCustomers']");


    CustomersPage() {
        PageFactory.initElements(driver, this);
    }

    public void getCustomersPage() {
        Path testPagePath = Paths.get(TestData.CUSTOMERS_PAGE_URL);
        driver.get(testPagePath.toUri().toString());
        waitUntilVisibilityOfElement(customersHeader);
    }

    public void enterSearchText(String text) {
        if (!isSearchFieldEmpty()) {
            clearSearchField();
        }
        searchInputField.sendKeys(text);
    }

    public void selectFilter(String filter) {
        waitUntilVisibilityOfElement(filtersDropdown);
        Select filtersSelect = new Select(filtersDropdown);
        filtersSelect.selectByValue(filter);
    }

    public List<Map<String, String>> getSearchResults() {
        List<WebElement> resultsRows = driver.findElements(resultsTableRow);
        if (!resultsRows.isEmpty()) {
            List<Map<String, String>> searchResults = new ArrayList<>();
            for (WebElement singleRow : resultsRows) {
                Map<String, String> singleCustomerData = new HashMap<>();
                singleCustomerData.put("Id", singleRow.findElement(By.xpath("td")).getText());
                singleCustomerData.put("Name", singleRow.findElement(By.xpath("td[2]")).getText());
                singleCustomerData.put("Email", singleRow.findElement(By.xpath("td/a")).getText());
                singleCustomerData.put("City", singleRow.findElement(By.xpath("td[4]")).getText());
                searchResults.add(singleCustomerData);
            }
            return searchResults;
        }
        return new ArrayList<>();
    }

    public List<String> getDataFromColumn(List<Map<String, String>> searchResults, String column) {
        List<String> dataFromColumn = new ArrayList<>();
        for (Map<String, String> singleResultsRow : searchResults) {
            dataFromColumn.add(singleResultsRow.get(column));
        }
        return dataFromColumn;
    }

    public boolean isSearchFieldEmpty() {
        waitUntilVisibilityOfElement(searchInputField);
        if (searchInputField.getAttribute("value").isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isSearchInfoDisplayed() {
        if (elementContainsAttributeValue(searchInfo, "class", "ng-hide")) {
            return false;
        }
        return true;
    }

    public void clearSearchField() {
        waitUntilVisibilityOfElement(searchInputField);
        searchInputField.clear();
    }

    public void enableMatchCase() {
        waitUntilElementIsClickable(matchCaseCheckbox);
        if (!matchCaseCheckbox.isSelected()) {
            matchCaseCheckbox.click();
            waitUntilElementIsSelected(matchCaseCheckbox);
        }
    }

    public void clickClearAllFilters() {
        waitUntilElementIsClickable(clearAllFiltersButton);
        clearAllFiltersButton.click();
    }
}