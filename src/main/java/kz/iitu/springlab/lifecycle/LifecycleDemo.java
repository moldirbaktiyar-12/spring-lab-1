package kz.iitu.springlab.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class LifecycleDemo implements BeanNameAware {

    private static final Logger log = LoggerFactory.getLogger(LifecycleDemo.class);

    private final List<String> events = new CopyOnWriteArrayList<>();
    private String beanName;

    public LifecycleDemo() {
        record("1. constructor called");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        record("2. BeanNameAware: bean name = " + name);
    }

    @PostConstruct
    public void init() {
        record("3. @PostConstruct: bean is ready to use");
    }

    @PreDestroy
    public void cleanup() {
        record("4. @PreDestroy: bean is being destroyed");
    }

    public Map<String, Object> state() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("beanName", beanName);
        result.put("events", List.copyOf(events));
        return result;
    }

    private void record(String event) {
        events.add(event);
        log.info("LifecycleDemo: {}", event);
    }
}