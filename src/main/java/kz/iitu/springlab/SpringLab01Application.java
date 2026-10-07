package kz.iitu.springlab;

import kz.iitu.springlab.config.AppProperties; // Импорт вашего AppProperties
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties; // Импорт аннотации
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class) // <--- Добавьте эту строку
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class SpringLab01Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringLab01Application.class, args);
	}

}