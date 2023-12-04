package com.ara.todayoutfit;

import com.ara.todayoutfit.user.repository.UserRepository;
import com.ara.todayoutfit.user.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;

@Slf4j
@EnableCaching
@EnableJpaAuditing
@RequiredArgsConstructor
@SpringBootApplication
public class TodayOutfitApplication {

    private final UserRepository userRepository;

    public static void main(String[] args) {
        log.info("TodayOutfitApplication.main");
        SpringApplication.run(TodayOutfitApplication.class, args);
    }

    @PostConstruct
    public void init() {
        UserCreateRequest request = UserCreateRequest.builder()
                .id("admin")
                .pw("admin")
                .isAdmin("true")
                .build();
        userRepository.saveUser(request.toUser());
    }

}
