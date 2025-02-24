package com.algaworks.algafood.config.oap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AsyncThreadLocalTest {

    private ThreadPoolTaskExecutor taskExecutor;

    @BeforeEach
    void setUp() {
        // Inicializando o ThreadPoolTaskExecutor manualmente
        taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);  // Número de threads no pool
        taskExecutor.setMaxPoolSize(50);   // Máximo de threads
        taskExecutor.setQueueCapacity(50); // Capacidade da fila de tarefas
        taskExecutor.initialize();  // Inicializa o executor
    }

    @Test
    void testThreadLocalWithMultipleAsyncCalls() throws Exception {
        // Usando um Stream para criar 10 tarefas assíncronas
        Future<Void>[] futures = new Future[50];

        // Enviando 10 tarefas para o pool de threads
        IntStream.range(0, 50).forEach(i -> {
            futures[i] = taskExecutor.submit(() -> {
                // Simulando o processamento em cada thread
                LogContext.put("thread", String.valueOf(i));
                LogContext.put("start_time", String.valueOf(System.currentTimeMillis()));
                Thread.sleep(100);  // Simulando uma operação
                LogContext.put("end_time", String.valueOf(System.currentTimeMillis()));

                // Verifica se o start_time e o end_time são diferentes, garantindo o isolamento
                assertNotEquals(LogContext.get("start_time"), LogContext.get("end_time"));
                assertEquals(LogContext.get("thread"), String.valueOf(i));
                //System.out.println("Async Thread " + i + ": " + LogContext.get("start_time"));
                System.out.println("Async Thread " + i + ": " + LogContext.get("thread"));

                LogContext.clear();  // Limpa o contexto após o uso
                return null;
            });
        });

        // Aguardando a conclusão de todas as tarefas
        for (Future<Void> future : futures) {
            future.get();
        }
    }
}
