package tw.com.bruce.game.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ExtendWith(SpringExtension.class)
public class GamesControllerTest {

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @AfterAll
    public static void stopExecutorService() {
        executorService.shutdown();
    }

    @Test
    public void restAPIShouldSearchGamesByTheirNames() throws IOException, InterruptedException {
    }

    @Test
    public void exceptionShouldBePropagatedToCaller() throws IOException, InterruptedException {
    }
}
