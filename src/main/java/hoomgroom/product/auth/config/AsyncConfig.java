package hoomgroom.product.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@EnableWebSecurity
public class AsyncConfig {
    @Bean(name = "promoTaskExecutor")
    public Executor promoTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("promoThread-");
        executor.initialize();
        return executor;
    }
}
