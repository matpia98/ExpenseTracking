package feature;

import com.example.expensetracking.ExpenseTrackingApplication;
import com.example.expensetracking.domain.crud.dto.ExpenseResponseDto;
import com.example.expensetracking.domain.reporting.dto.ReportDto;
import com.example.expensetracking.infrastructure.crud.controller.category.dto.GetAllCategoriesResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.category.dto.GetExpensesByCategoryResponseDto;
import com.example.expensetracking.infrastructure.crud.controller.expense.dto.GetAllExpensesResponseDto;
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

@SpringBootTest(classes = ExpenseTrackingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class HappyPathIntegrationTest {

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
    void happyPathTest() {
        // Step 1: when I go to /expenses, I can see no expenses
        // given & when & then
        webTestClient.get().uri("/expenses")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.message").isEqualTo("No expenses found")
                .jsonPath("$.status").isEqualTo("NOT_FOUND");

        // Step 2: when I go to /categories, I can see list of 14 basic categories
        // given & when
        GetAllCategoriesResponseDto categoriesResponse = webTestClient.get().uri("/categories")
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

        // step 3. when I go to /reports/weekly, I can see a report with no expenses
        // given & when
        ReportDto initialReport = webTestClient.get().uri("/reports/weekly?date={date}", LocalDate.now())
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

        // step 4. when I go to /reports/monthly, I can see a report with no expenses
        // given & when
        ReportDto initialMonthlyReport = webTestClient.get().uri("reports/monthly/{year}/{month}", 2024, 8)
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

        // step 5. when I go to /reports/custom, I can see a report with no expenses
        // given & when
        ReportDto initialCustomReport = webTestClient.get().uri("/reports/custom?startDate={startDate}&endDate={endDate}",
                        LocalDate.now().minusDays(7), LocalDate.now())
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

        // step 6. when I post to /expenses, I can see new expense with id 1
        // given & when
        webTestClient.post().uri("/expenses")
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

        // step 7. when I go to /categories/5, I can see the category Healthcare with id 5
        // given & when & then
        webTestClient.get().uri("/categories/5")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(5)
                .jsonPath("$.name").isEqualTo("Healthcare");

        // step 8: when I post to /expenses, I can see new expense with id 2
        webTestClient.post().uri("/expenses")
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

        // step 9: when I go post to /expenses, I can see new expense with id 3
        webTestClient.post().uri("/expenses")
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

        // step 10: when I go to /expenses, I can see 3 expenses
        // given & when
        GetAllExpensesResponseDto getThreeExpensesResponseDto = webTestClient.get()
                .uri("/expenses")
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

        // step 11: when I go to /categories/7/expenses, I can see 2 expenses
        // given & when
        GetExpensesByCategoryResponseDto getTwoExpensesByCategoryResponseDto = webTestClient.get()
                .uri("/categories/7/expenses")
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



    }
}