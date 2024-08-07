package com.example.expensetracking.domain.expense.crud;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

class InMemoryExpenseRepository implements ExpenseRepository {
    private final Map<Long, Expense> expenses = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong();

    @Override
    public <S extends Expense> S save(S expense) {
        if (expense.getId() == null) {
            expense.setId(index.incrementAndGet());
        }
        expenses.put(expense.getId(), expense);
        return expense;
    }

    @Override
    public Optional<Expense> findById(Long id) {
        return Optional.ofNullable(expenses.get(id));
    }

    @Override
    public boolean existsById(Long id) {
        return expenses.containsKey(id);
    }

    @Override
    public List<Expense> findAllByCategoryId(Long categoryId) {
        return expenses.values().stream()
                .filter(expense -> Objects.equals(expense.getCategoryId(), categoryId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Expense> findAll() {
        return new ArrayList<>(expenses.values());
    }
    @Override
    public void deleteById(Long id) {
        expenses.remove(id);
    }

    @Override
    public boolean existsByCategoryId(Long categoryId) {
        return expenses.values().stream()
                .anyMatch(expense -> Objects.equals(expense.getCategoryId(), categoryId));
    }

    @Override
    public <S extends Expense> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public List<Expense> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Expense entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Expense> entities) {

    }

    @Override
    public void deleteAll() {

    }



    @Override
    public void flush() {

    }

    @Override
    public <S extends Expense> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Expense> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Expense> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Expense getOne(Long aLong) {
        return null;
    }

    @Override
    public Expense getById(Long aLong) {
        return null;
    }

    @Override
    public Expense getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Expense> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Expense> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Expense> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Expense> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Expense> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Expense> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Expense, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Expense> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Expense> findAll(Pageable pageable) {
        return null;
    }

}