/**
 * Copyright 2021 American Well Systems
 * All rights reserved.
 *
 * It is illegal to use, reproduce or distribute
 * any part of this Intellectual Property without
 * prior written authorization from American Well.
 */
package com.americanwell.autotest.soa.petstore;

import com.americanwell.autotest.soa.petstore.utils.Config;
import com.perfecto.reportium.exception.ReportiumException;
import com.perfecto.reportium.imports.client.ReportiumImportClient;
import com.perfecto.reportium.imports.client.ReportiumImportClientFactory;
import com.perfecto.reportium.imports.client.connection.Connection;
import com.perfecto.reportium.imports.model.ImportExecutionContext;
import com.perfecto.reportium.model.CustomField;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.WordUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Test class to set up reporting to Perfecto server
 *
 * @author chan.suom on 7/28/21
 */
@Slf4j
public class PerfectoTest {

	protected ReportiumImportClient reportiumClient;
	private static final String TIMESTAMP = String.valueOf(System.currentTimeMillis() % 100000);

	@BeforeClass(alwaysRun = true)
	public void baseBeforeClass() throws IOException, InterruptedException, URISyntaxException {

		reportiumClient = getReportiumClient();

	}

	@AfterClass(alwaysRun = true)
	public void baseAfterClass() {

		log.debug("Report url for {}: {}", this.getClass().getSimpleName(), reportiumClient.getReportUrl());

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTest(Method method) {

		try {
			Class testClass = method.getDeclaringClass();
			String testName = testClass.getSimpleName() + "." + method.getName();
			TestContext testContext = new TestContext.Builder().withCustomFields(new CustomField("Link", System.getenv("CI_JOB_URL"))).build();
			reportiumClient.testStart(testName, testContext);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}

	}

	@AfterMethod(alwaysRun = true)
	public void afterTest(ITestResult testResult) {

		int status = testResult.getStatus();
		switch (status) {
		case ITestResult.FAILURE:
			reportiumClient.testStop(TestResultFactory.createFailure("An error occurred", testResult.getThrowable()));
			break;
		case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
		case ITestResult.SUCCESS:
			reportiumClient.testStop(TestResultFactory.createSuccess());
			break;
		case ITestResult.SKIP:
			// Ignore
			break;
		default:
			throw new ReportiumException("Unexpected status: " + status);
		}

	}

	/**
	 * Get the Reportium Client
	 *
	 * @return ReportiumImportClient
	 * @throws IOException exception
	 * @throws InterruptedException exception
	 * @throws URISyntaxException exception
	 */
	private static ReportiumImportClient getReportiumClient()
			throws IOException, InterruptedException, URISyntaxException {

		log.debug("Creating an API Test Perfecto Reportium Client...");
		String ciJobIdStr = System.getenv("CI_JOB_ID");
		int ciJobId = ciJobIdStr == null ? Integer.parseInt(TIMESTAMP) : Integer.parseInt(ciJobIdStr);

		ImportExecutionContext executionContext = new ImportExecutionContext.Builder()
				.withProject(new Project(getPerfectoProjectName(), System.getenv("CI_COMMIT_SHORT_SHA")))
				.withJob(new Job(getPerfectoProjectName() + " " + getTestEnv() + " API Tests", ciJobId)
						.withBranch(getGitBranchName()))
				.withContextTags("API_Tests")
				.withCustomFields(new CustomField("Link", System.getenv("CI_JOB_URL")))
				.build();

		return new ReportiumImportClientFactory().createReportiumImportClient(getConnection(), executionContext);

	}

	/**
	 * Get the Perfecto server connection
	 *
	 * @return Connection
	 * @throws URISyntaxException exception
	 */
	private static Connection getConnection() throws URISyntaxException, IOException {

		log.debug("Getting the Perfecto server connection...");
		return new Connection(new URI(
				String.format("https://%s.app.perfectomobile.com", Config.getInstance().getPerfectoHost())),
				Config.getInstance().getPerfectoSecurityToken());

	}

	/**
	 * Get the test environment
	 *
	 * @return String
	 */
	private static String getTestEnv() {

		log.debug("Getting the test environment...");
		String env = "Undefined Env";

		if (System.getenv("TEST_ENV") != null && System.getenv("ENV_NAME") != null) {

			//I know... WordUtils is deprecated but still works and there isn't an easier option to title case this string :|
			env = WordUtils.capitalizeFully(
					String.format("%s %s", System.getenv("TEST_ENV"), System.getenv("ENV_NAME")).toLowerCase());

		}

		return env;

	}

	/**
	 * Get the current Git branch name
	 *
	 * @return String
	 * @throws IOException exception
	 * @throws InterruptedException exception
	 */
	private static String getGitBranchName() throws IOException, InterruptedException {

		log.debug("Getting the current Git branch name...");
		String branchName = System.getenv("CI_COMMIT_BRANCH");

		if (branchName == null) {

			Process process = Runtime.getRuntime().exec("git rev-parse --abbrev-ref HEAD");
			process.waitFor();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			branchName = reader.readLine();

		}

		log.debug("Git branch name: {}", branchName);
		return branchName;

	}

	/**
	 * Get the Perfecto project name
	 *
	 * @return String
	 * @throws IOException exception
	 * @throws InterruptedException exception
	 */
	private static String getPerfectoProjectName() throws IOException, InterruptedException {

		log.debug("Getting the Perfecto project name...");
		String name = System.getenv("CI_PROJECT_NAME");

		if (name == null) {

			Process process = Runtime.getRuntime().exec("git config user.name");
			process.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String username = reader.readLine().replace(" ", "-");

			process = Runtime.getRuntime().exec("git rev-parse --show-toplevel");
			process.waitFor();

			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String project = reader.readLine();

			name =  String.format("LOCAL-%s-%s", username, project.substring(project.lastIndexOf("/") + 1));

		}

		log.debug("Perfecto project name: {}", name);

		return name;

	}

}
