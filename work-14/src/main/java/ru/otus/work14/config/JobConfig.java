package ru.otus.work14.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.work14.domain.Book;
import ru.otus.work14.domain.Comment;
import ru.otus.work14.domain.h2.H2Author;
import ru.otus.work14.domain.h2.H2Book;
import ru.otus.work14.domain.h2.H2Comment;
import ru.otus.work14.domain.h2.H2Genre;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JobConfig {
    private final StepBuilderFactory stepBuilderFactory;

    private final JobBuilderFactory jobBuilderFactory;
    private final Logger logger = LoggerFactory.getLogger("Batch");

    private static final int CHUNK_SIZE = 5;
    public static final String IMPORT_LIBRARY_JOB_NAME = "IMPORT_LIBRARY_JOB_NAME";
    public static final String STEP_IMPORT_AUTHORS = "STEP_IMPORT_AUTHORS";
    public static final String STEP_IMPORT_GENRES = "STEP_IMPORT_GENRES";
    public static final String STEP_IMPORT_BOOKS = "STEP_IMPORT_BOOKS";
    public static final String STEP_IMPORT_COMMENTS = "STEP_IMPORT_COMMENTS";

    @Bean
    public Job importUserJob(@Qualifier(STEP_IMPORT_AUTHORS) Step ImportAuthors
                           , @Qualifier(STEP_IMPORT_GENRES) Step ImportGenres
                           , @Qualifier(STEP_IMPORT_BOOKS)Step ImportBooks
                           , @Qualifier(STEP_IMPORT_COMMENTS)Step ImportComments
    ) {
        return jobBuilderFactory.get(IMPORT_LIBRARY_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(ImportAuthors)
                .next(ImportGenres)
                .next(ImportBooks)
                .next(ImportComments)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) { logger.info("Начало job"); }
                    @Override
                    public void afterJob(JobExecution jobExecution) { logger.info("Конец job"); }
                })
                .build();
    }

    @Bean(STEP_IMPORT_AUTHORS)
    public Step authorsImportStep(ItemReader<Book> reader, ItemProcessor<Book, H2Author> processor, ItemWriter<H2Author> writer) {
        return stepBuilderFactory.get(STEP_IMPORT_AUTHORS)
                .<Book, H2Author>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new ItemReadListener<Book>() {
                    public void beforeRead() { log.info("Начало чтения");}
                    public void afterRead(Book book) { log.info("Конец чтения");}
                    public void onReadError(Exception e) { log.error("Ошибка чтения");}
                })
                .listener(new ItemWriteListener<H2Author>() {
                    public void beforeWrite(List list) { log.info("Начало записи");}
                    public void afterWrite(List list) { log.info("Конец записи"); }
                    public void onWriteError(Exception e, List list) { log.error("Ошибка записи");}
                })
                .listener(new ItemProcessListener<Book, H2Author>() {
                    public void beforeProcess(Book book) { log.info("Начало обработки");}
                    public void afterProcess(Book book, H2Author author) { log.info("Конец обработки");}
                    public void onProcessError(Book book, Exception e) { log.error("Ошибка обработки"); }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }

    @Bean(STEP_IMPORT_GENRES)
    public Step genresImportStep(ItemReader<Book> reader, ItemProcessor<Book, H2Genre> processor, ItemWriter<H2Genre> writer) {
        return stepBuilderFactory.get(STEP_IMPORT_GENRES)
                .<Book, H2Genre>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new ItemReadListener<Book>() {
                    public void beforeRead() {
                        log.info("Начало чтения");
                    }
                    public void afterRead(Book book) {
                        log.info("Конец чтения");
                    }
                    public void onReadError(Exception e) {
                        log.error("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<H2Genre>() {
                    public void beforeWrite(List list) {
                        log.info("Начало записи");
                    }
                    public void afterWrite(List list) {
                        log.info("Конец записи");
                    }
                    public void onWriteError(Exception e, List list) { log.error("Ошибка записи");}
                })
                .listener(new ItemProcessListener<Book, H2Genre>() {
                    public void beforeProcess(Book book) {
                        log.info("Начало обработки");
                    }
                    public void afterProcess(Book book, H2Genre genre) { log.info("Конец обработки");}
                    public void onProcessError(Book book, Exception e) {
                        log.error("Ошибка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }

    @Bean(STEP_IMPORT_BOOKS)
    public Step booksImportStep(ItemReader<Book> reader, ItemProcessor<Book, H2Book> processor, ItemWriter<H2Book> writer) {
        return stepBuilderFactory.get(STEP_IMPORT_BOOKS)
                .<Book, H2Book>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new ItemReadListener<Book>() {
                    public void beforeRead() {
                        log.info("Начало чтения");
                    }
                    public void afterRead(Book book) {
                        log.info("Конец чтения");
                    }
                    public void onReadError(Exception e) {
                        log.error("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<H2Book>() {
                    public void beforeWrite(List list) {
                        log.info("Начало записи");
                    }
                    public void afterWrite(List list) {
                        log.info("Конец записи");
                    }
                    public void onWriteError(Exception e, List list) { log.error("Ошибка записи");}
                })
                .listener(new ItemProcessListener<Book, H2Book>() {
                    public void beforeProcess(Book book) {
                        log.info("Начало обработки");
                    }
                    public void afterProcess(Book book, H2Book h2book) { log.info("Конец обработки");}
                    public void onProcessError(Book book, Exception e) {
                        log.error("Ошибка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }

    @Bean(STEP_IMPORT_COMMENTS)
    public Step commentsImportStep(ItemReader<Comment> reader, ItemProcessor<Comment, H2Comment> processor, ItemWriter<H2Comment> writer) {
        return stepBuilderFactory.get(STEP_IMPORT_COMMENTS)
                .<Comment, H2Comment>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new ItemReadListener<Comment>() {
                    public void beforeRead() {
                        log.info("Начало чтения");
                    }
                    public void afterRead(Comment comment) {
                        log.info("Конец чтения");
                    }
                    public void onReadError(Exception e) {
                        log.error("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<H2Comment>() {
                    public void beforeWrite(List list) {
                        log.info("Начало записи");
                    }
                    public void afterWrite(List list) {
                        log.info("Конец записи");
                    }
                    public void onWriteError(Exception e, List list) { log.error("Ошибка записи");}
                })
                .listener(new ItemProcessListener<Comment, H2Comment>() {
                    public void beforeProcess(Comment comment) {
                        log.info("Начало обработки");
                    }
                    public void afterProcess(Comment comment, H2Comment h2comment) { log.info("Конец обработки");}
                    public void onProcessError(Comment comment, Exception e) {
                        log.error("Ошибка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }

}
