package com.qa.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    @Given("user is on login page")
    public void user_is_on_login_page(){
        System.out.println("User is on login page");
    }
    @When("user enters valid username and password")
    public void user_enters_credentials(){
        System.out.println("User enters credentials");
    }
    @Then("user should login successfully")
    public void user_should_login(){
        System.out.println("Login successful");
    }
}
