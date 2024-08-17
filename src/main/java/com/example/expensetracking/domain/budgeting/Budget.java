package com.example.expensetracking.domain.budgeting;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(precision = 10, scale = 2)
    private BigDecimal spent;

    @Column(precision = 10, scale = 2)
    private BigDecimal remaining;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "budget_id")
    @Builder.Default
    private List<BudgetExpense> expenses = new ArrayList<>();

    public void addExpense(BudgetExpense expense) {
        if (this.remaining.compareTo(expense.getAmount()) < 0) {
            throw new InsufficientBudgetException("Insufficient budget remaining");
        }
        this.expenses.add(expense);
        this.spent = this.spent.add(expense.getAmount());
        this.remaining = this.remaining.subtract(expense.getAmount());
    }

    public void removeExpense(BudgetExpense expense) {
        if (this.expenses.remove(expense)) {
            this.spent = this.spent.subtract(expense.getAmount());
            this.remaining = this.remaining.add(expense.getAmount());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BudgetExpense that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}