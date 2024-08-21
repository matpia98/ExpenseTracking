package feature;

import com.example.expensetracking.ExpenseTrackingApplication;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import com.example.expensetracking.domain.reporting.dto.ReportDto;
import com.example.expensetracking.infrastructure.crud.controller.category.dto.GetAllCategoriesResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.category.dto.GetExpensesByCategoryResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.GetAllExpensesResponseDto;
import com.example.expensetracking.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(classes = ExpenseTrackingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class TypicalPathIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private WebTestClient webTestClient;

    @DynamicPropertySource
    static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void should_user_register_and_be_able_to_manage_expenses_budgets_and_reports() {
        // Step 1: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        webTestClient.post().uri("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "username": "someUser",
                            "password": "somePassword"
                        }
                        """)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Bad Credentials")
                .jsonPath("$.status").isEqualTo("UNAUTHORIZED");

        // Step 2: user made GET /offers with no jwt token and system returned UNAUTHORIZED(401)
        webTestClient.get().uri("/expenses")
                .exchange()
                .expectStatus().isUnauthorized();

        // Step 3: step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status CREATED(201)
        webTestClient.post().uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "username": "someUser",
                            "password": "somePassword"
                        }
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.username").isEqualTo("someUser")
                .jsonPath("$.created").isEqualTo(true)
                .jsonPath("$.id").isNotEmpty();

        // Step 4: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        // given, when, then
        JwtResponseDto jwtResponse = webTestClient.post().uri("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "username": "someUser",
                            "password": "somePassword"
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtResponseDto.class)
                .returnResult()
                .getResponseBody();
        String token = jwtResponse.token();

        assertAll(
                () -> assertThat(jwtResponse.username()).isEqualTo("someUser"),
                () -> assertThat(token).matches("^[A-Za-z0-9-_]+?\\.[A-Za-z0-9-_]+?\\.[A-Za-z0-9-_]+$")
        );

        // Step 5: user made GET /expenses with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned no expenses
        webTestClient.get().uri("/expenses")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.message").isEqualTo("No expenses found")
                .jsonPath("$.status").isEqualTo("NOT_FOUND");

        // Step 6: user made GET /categories and system returned 14 categories
        // given & when
        GetAllCategoriesResponseDto categoriesResponse = webTestClient.get().uri("/categories")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetAllCategoriesResponseDto.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(categoriesResponse).isNotNull();
        assertThat(categoriesResponse.categories()).hasSize(14);
        assertThat(categoriesResponse.categories())
                .extracting("name")
                .containsExactly(
                        "Groceries", "Transportation", "Housing", "Utilities", "Healthcare",
                        "Entertainment", "Dining Out", "Education", "Clothing", "Personal Care",
                        "Gifts", "Savings", "Debt Payments", "Miscellaneous"
                );

        // step 7. user made GET /reports/weekly and system returned a report with no expenses
        // given & when
        ReportDto initialReport = webTestClient.get().uri("/reports/weekly")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReportDto.class)
                .returnResult()
                .getResponseBody();
        // then
        assertThat(initialReport).isNotNull();
        assertThat(initialReport.totalExpenses()).isEqualTo(BigDecimal.ZERO);
        assertThat(initialReport.categorySummaries()).isEmpty();
        assertThat(initialReport.topExpenses()).isEmpty();

        // step 8. user made GET /reports/monthly/2024/8 and system returned a report with no expenses
        // given & when
        ReportDto initialMonthlyReport = webTestClient.get().uri("reports/monthly/{year}/{month}", 2024, 8)
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReportDto.class)
                .returnResult()
                .getResponseBody();
        // then
        assertThat(initialMonthlyReport).isNotNull();
        assertThat(initialMonthlyReport.totalExpenses()).isEqualTo(BigDecimal.ZERO);
        assertThat(initialMonthlyReport.categorySummaries()).isEmpty();
        assertThat(initialMonthlyReport.topExpenses()).isEmpty();

        // step 9. user made GET /reports/custom?startDate=2024-08-01&endDate=2024-08-07 and system returned a report with no expenses
        // given & when
        ReportDto initialCustomReport = webTestClient.get().uri("/reports/custom?startDate={startDate}&endDate={endDate}",
                        LocalDate.now().minusDays(7), LocalDate.now())
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ReportDto.class)
                .returnResult()
                .getResponseBody();
        // then
        assertThat(initialCustomReport).isNotNull();
        assertThat(initialCustomReport.totalExpenses()).isEqualTo(BigDecimal.ZERO);
        assertThat(initialCustomReport.categorySummaries()).isEmpty();
        assertThat(initialCustomReport.topExpenses()).isEmpty();

        // step 10. user made POST /expenses with a new expense and system returned the new expense with id 1
        // given & when
        webTestClient.post().uri("/expenses")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "title": "Groceries",
                            "description": "Weekly groceries",
                            "amount": 100.0,
                            "categoryId": 1
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.title").isEqualTo("Groceries")
                .jsonPath("$.description").isEqualTo("Weekly groceries")
                .jsonPath("$.amount").isEqualTo(100.0)
                .jsonPath("$.date").isNotEmpty()
                .jsonPath("$.categoryId").isEqualTo(1)
                .jsonPath("$.categoryName").isEqualTo("Groceries");

        // step 11. user made GET /categories/5 and system returned the category with id 5
        // given & when & then
        webTestClient.get().uri("/categories/5")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(5)
                .jsonPath("$.name").isEqualTo("Healthcare");

        // step 12: user made POST /expenses with a new expense and system returned the new expense with id 2
        webTestClient.post().uri("/expenses")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "title": "Dinner",
                            "description": "Restaurant meal",
                            "amount": 50.0,
                            "categoryId": 7
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(2)
                .jsonPath("$.title").isEqualTo("Dinner")
                .jsonPath("$.description").isEqualTo("Restaurant meal")
                .jsonPath("$.amount").isEqualTo(50.0)
                .jsonPath("$.date").isNotEmpty()
                .jsonPath("$.categoryId").isEqualTo(7)
                .jsonPath("$.categoryName").isEqualTo("Dining Out");

        // step 13: user made POST /expenses with a new expense and system returned the new expense with id 3
        webTestClient.post().uri("/expenses")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "title": "Breakfast",
                            "description": "Fast breakfast in a cafe",
                            "amount": 15.0,
                            "categoryId": 7
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(3)
                .jsonPath("$.title").isEqualTo("Breakfast")
                .jsonPath("$.description").isEqualTo("Fast breakfast in a cafe")
                .jsonPath("$.amount").isEqualTo(15.0)
                .jsonPath("$.date").isNotEmpty()
                .jsonPath("$.categoryId").isEqualTo(7)
                .jsonPath("$.categoryName").isEqualTo("Dining Out");

        // step 14: user made GET /expenses and system returned 3 expenses
        // given & when
        GetAllExpensesResponseDto getThreeExpensesResponseDto = webTestClient.get()
                .uri("/expenses")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetAllExpensesResponseDto.class)
                .returnResult()
                .getResponseBody();
        // then
        assertThat(getThreeExpensesResponseDto).isNotNull();
        assertThat(getThreeExpensesResponseDto.expenses()).hasSize(3);
        assertThat(getThreeExpensesResponseDto.expenses())
                .extracting(ExpenseResponseDto::id)
                .containsExactly(1L, 2L, 3L);
        assertThat(getThreeExpensesResponseDto.expenses())
                .extracting(ExpenseResponseDto::title)
                .containsExactly("Groceries", "Dinner", "Breakfast");

        // step 15: user made GET /categories/7/expenses and system returned 2 expenses
        // given & when
        GetExpensesByCategoryResponseDto getTwoExpensesByCategoryResponseDto = webTestClient.get()
                .uri("/categories/7/expenses")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetExpensesByCategoryResponseDto.class)
                .returnResult()
                .getResponseBody();

        // then
        assertThat(getTwoExpensesByCategoryResponseDto).isNotNull();
        assertThat(getTwoExpensesByCategoryResponseDto.expenses()).hasSize(2);
        assertThat(getTwoExpensesByCategoryResponseDto.expenses())
                .extracting(ExpenseResponseDto::id)
                .containsExactly(2L, 3L);
        assertThat(getTwoExpensesByCategoryResponseDto.expenses())
                .extracting(ExpenseResponseDto::title)
                .containsExactly("Dinner", "Breakfast");


        // Step 16: user made POST /budgets with a new budget and system returned the new budget with id 1
        // given & when
        webTestClient.post().uri("/budgets")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .bodyValue("""
                        {
                            "startDate": "2024-08-17",
                            "endDate": "2024-08-31",
                            "initialAmount": 1000.00
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.startDate").isEqualTo("2024-08-17")
                .jsonPath("$.endDate").isEqualTo("2024-08-31")
                .jsonPath("$.spent").isEqualTo(0)
                .jsonPath("$.remaining").isEqualTo(1000.00)
                .jsonPath("$.expenses").isEmpty();

        // Step 17: user made GET /budgets and system returned the budget with id 1
        webTestClient.get().uri("/budgets")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.activeBudgets").isArray()
                .jsonPath("$.activeBudgets.length()").isEqualTo(1)
                .jsonPath("$.activeBudgets[0].startDate").isEqualTo("2024-08-17")
                .jsonPath("$.activeBudgets[0].endDate").isEqualTo("2024-08-31")
                .jsonPath("$.activeBudgets[0].summary[0].name").isEqualTo("Total")
                .jsonPath("$.activeBudgets[0].summary[0].limit").isEqualTo(1000.00)
                .jsonPath("$.activeBudgets[0].summary[0].spent").isEqualTo(0)
                .jsonPath("$.activeBudgets[0].summary[0].remaining").isEqualTo(1000.00)
                .jsonPath("$.activeBudgets[0].expenses").isEmpty();

        // Step 18: user made PUT /budgets/1/add-existing-expense/1 and system returned the updated budget with id 1 with the added expense with id 1
        webTestClient.put().uri("/budgets/1/add-existing-expense/1")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.budgetId").isEqualTo(1)
                .jsonPath("$.expenseId").isEqualTo(1)
                .jsonPath("$.updatedSpent").isEqualTo(100.00)
                .jsonPath("$.updatedRemaining").isEqualTo(900.00)
                .jsonPath("$.addedExpense.id").isEqualTo(1)
                .jsonPath("$.addedExpense.title").isEqualTo("Groceries")
                .jsonPath("$.addedExpense.amount").isEqualTo(100.00);

        // Step 19: user made GET /budgets and system returned the budget with id 1 with the added expense with id 1
        webTestClient.get().uri("/budgets")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.activeBudgets[0].summary[0].spent").isEqualTo(100.00)
                .jsonPath("$.activeBudgets[0].summary[0].remaining").isEqualTo(900.00)
                .jsonPath("$.activeBudgets[0].expenses.length()").isEqualTo(1)
                .jsonPath("$.activeBudgets[0].expenses[0].id").isEqualTo(1)
                .jsonPath("$.activeBudgets[0].expenses[0].title").isEqualTo("Groceries")
                .jsonPath("$.activeBudgets[0].expenses[0].amount").isEqualTo(100.00);

        // Step 20: user made PUT /budgets/1/add-existing-expense/2 and system returned the updated budget with id 1 with the added expense with id 2
        webTestClient.put().uri("/budgets/1/add-existing-expense/2")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.budgetId").isEqualTo(1)
                .jsonPath("$.expenseId").isEqualTo(2)
                .jsonPath("$.updatedSpent").isEqualTo(150.00)
                .jsonPath("$.updatedRemaining").isEqualTo(850.00)
                .jsonPath("$.addedExpense.id").isEqualTo(2)
                .jsonPath("$.addedExpense.title").isEqualTo("Dinner")
                .jsonPath("$.addedExpense.amount").isEqualTo(50.00);

        // Step 21: user made GET /budgets and system returned the budget with id
        webTestClient.get().uri("/budgets")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.activeBudgets[0].summary[0].spent").isEqualTo(150.00)
                .jsonPath("$.activeBudgets[0].summary[0].remaining").isEqualTo(850.00)
                .jsonPath("$.activeBudgets[0].expenses.length()").isEqualTo(2)
                .jsonPath("$.activeBudgets[0].expenses[1].id").isEqualTo(2)
                .jsonPath("$.activeBudgets[0].expenses[1].title").isEqualTo("Dinner")
                .jsonPath("$.activeBudgets[0].expenses[1].amount").isEqualTo(50.00);

        // Step 22: user made POST /budgets with a new budget and system returned the new budget with id 2
        webTestClient.post().uri("/budgets")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "startDate": "2024-09-01",
                            "endDate": "2024-09-30",
                            "initialAmount": 1500.00
                        }
                        """)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(2)
                .jsonPath("$.startDate").isEqualTo("2024-09-01")
                .jsonPath("$.endDate").isEqualTo("2024-09-30")
                .jsonPath("$.spent").isEqualTo(0)
                .jsonPath("$.remaining").isEqualTo(1500.00)
                .jsonPath("$.expenses").isEmpty();

        // Step 23: user made GET /budgets and system returned the budget with id 1 and 2
        webTestClient.get().uri("/budgets")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.activeBudgets").isArray()
                .jsonPath("$.activeBudgets.length()").isEqualTo(2)
                .jsonPath("$.activeBudgets[1].startDate").isEqualTo("2024-09-01")
                .jsonPath("$.activeBudgets[1].endDate").isEqualTo("2024-09-30")
                .jsonPath("$.activeBudgets[1].summary[0].limit").isEqualTo(1500.00)
                .jsonPath("$.activeBudgets[1].summary[0].spent").isEqualTo(0)
                .jsonPath("$.activeBudgets[1].summary[0].remaining").isEqualTo(1500.00);


    }
}