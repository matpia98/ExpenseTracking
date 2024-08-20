package com.example.expensetracking.domain.budgeting;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

class InMemoryBudgetRepository implements BudgetRepository {
    private final Map<Long, Budget> budgets = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(0);

    @Override
    public <S extends Budget> S save(S budget) {
        if (budget.getId() == null) {
            budget.setId(index.incrementAndGet());
        }
        budgets.put(budget.getId(), budget);
        return budget;
    }

    @Override
    public Optional<Budget> findById(Long id) {
        return Optional.ofNullable(budgets.get(id));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Budget> findAllByEndDateAfter(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return budgets.values().stream()
                .filter(budget -> budget.getEndDate() != null && budget.getEndDate().isAfter(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Budget> findAll() {
        return new ArrayList<>(budgets.values());
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Budget> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Budget> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Budget> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Budget getOne(Long aLong) {
        return null;
    }

    @Override
    public Budget getById(Long aLong) {
        return null;
    }

    @Override
    public Budget getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Budget> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Budget> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Budget> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Budget> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Budget> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Budget> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Budget, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Budget> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public List<Budget> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Budget entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Budget> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Budget> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Budget> findAll(Pageable pageable) {
        return null;
    }

}