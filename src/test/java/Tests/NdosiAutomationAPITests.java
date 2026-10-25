package Tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.annotations.Test;

import static Common.CommonTestData.*;
import static Common.CommonTestData.badRequest;
import static Common.ContentType.contentType;
import static Common.GenerateTestData.*;
import static Common.GenerateTestData.confirmPassword;
import static io.restassured.RestAssured.given;
import static requestBuilder.RequestBuilder.*;
import static requestBuilder.RequestBuilder.createUserResponse;

    public class NdosiAutomationAPITests {

        @Test
        @Description("As an api user i want to create the new user")
        @Severity(SeverityLevel.BLOCKER)
        public void registerUserTests() {
            createUserResponse(firstName, lastName, email, password, confirmPassword, groupId).
                    then().
                    assertThat().
                    statusCode(Creation_Success);
        }

        @Test(dependsOnMethods = "registerUserTests")
        @Description("As an api user i want to login as admin user")
        @Severity(SeverityLevel.BLOCKER)
        public void loginAsAdminUserTests() {
            loginrResponse(adminUsername, adminPassword).
                    then().
                    assertThat().
                    statusCode(Success_Status);
        }

        @Test(dependsOnMethods = "loginAsAdminUserTests")
        @Description("As an api user i want to approve the user")
        @Severity(SeverityLevel.BLOCKER)
        public void approveUserTests() {
            approveUser().
                    then().
                    assertThat().
                    statusCode(Success_Status);
        }

        @Test(dependsOnMethods = "registerUserTests")
        @Description("As an api user i want to enroll a registered user to a course ")
        public void enrollUserCourseTest() {
           enrollUserCourse().
                    then().
                    assertThat().
                    statusCode(Success_Status);
        }




    }
