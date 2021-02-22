package HSBC.customersPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomersPageSteps {

    private CustomersPage customersPage;

    public CustomersPageSteps() {
        this.customersPage = new CustomersPage();
    }

    @Before
    public void openCustomersPage() {
        customersPage.getCustomersPage();
    }

    @Given("no filters are applied")
    public void clearFilterIfApplied() {
        if (!customersPage.isSearchFieldEmpty()) {
            customersPage.clearSearchField();
        }
    }

    @Given("user searched for case swapped {string} using {string} filter")
    public void verifyResultsAreShownForSwappedCaseDataAndFilter(String data, String filter) {
        customersPage.enterSearchText(StringUtils.swapCase(data));
        customersPage.selectFilter(filter);
    }

    @Given("user searched for {string} using {string} filter")
    @When("user searches for {string} using {string} filter")
    public void verifyResultsAreShownForDataAndFilter(String data, String filter) {
        customersPage.enterSearchText(data);
        customersPage.selectFilter(filter);
    }

    @When("user enables 'Match case' function")
    public void enableMatchCase() {
        customersPage.enableMatchCase();
    }

    @When("user clicks \"click to clear all filters\" button")
    public void clickClearAllFilters() {
        customersPage.clickClearAllFilters();
    }

    @Then("result with {string} in {string} column should be shown")
    public void verifyGivenDataIsDisplayedInColumn(String data, String column) {
        List<Map<String, String>> searchResults = customersPage.getSearchResults();
        assertTrue(customersPage.getDataFromColumn(searchResults, column).contains(data));
    }

    @Then("all filters are removed")
    public void verifyAllFiltersRemoved() {
        assertTrue(customersPage.isSearchFieldEmpty());
        assertFalse(customersPage.isSearchInfoDisplayed());
    }

    @Then("result with {string} in {string} column is not shown")
    @But("result with {string} in {string} column should not be shown")
    public void verifyGivenDataIsNotDisplayedInColumn(String data, String column) {
        List<Map<String, String>> searchResults = customersPage.getSearchResults();
        assertFalse(customersPage.getDataFromColumn(searchResults, column).contains(data));
    }

    @After
    public void quit() {
        customersPage.driver.quit();
    }

}
