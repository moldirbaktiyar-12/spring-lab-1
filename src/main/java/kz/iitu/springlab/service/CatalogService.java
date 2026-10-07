package kz.iitu.springlab.service;

import kz.iitu.springlab.aspect.RetryOnFailure;
import kz.iitu.springlab.audit.Audited;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.concurrent.atomic.AtomicInteger;



@Service
public class CatalogService {

    private final AtomicInteger flakyCounter = new AtomicInteger(0);
    public String findById(long id) {
        sleep(50);
        return "Item no. " + id;
    }

    @Audited(action = "CATALOG_LIST", logArguments = true)
    public List<String> findAll(int limit) {
        sleep(300);
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "Item no. " + i)
                .toList();
    }

    @Audited(action = "CATALOG_REMOVE")
    public String remove(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid identifier: " + id);
        }
        return "Removed item no. " + id;
    }

    // Task 4: самовызов
    public String removeTwice(long id) {
        String first  = remove(id);
        String second = remove(id + 1);
        return first + "; " + second;
    }

    // Вариант 3: падает 2 раза из 3, успешен с третьей попытки
    @RetryOnFailure(attempts = 3)
    public String flaky() {
        int n = flakyCounter.incrementAndGet();
        if (n % 3 != 0) {
            throw new IllegalStateException("Temporary failure, call #" + n);
        }
        return "OK on call #" + n;
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}