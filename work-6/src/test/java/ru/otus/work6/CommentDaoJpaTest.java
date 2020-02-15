package ru.otus.work6;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.work6.dao.CommentDaoJpa;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import(CommentDaoJpa.class)
public class CommentDaoJpaTest {
    private static final int EXPECTED_NUMBER_OF_COMMENTS = 2;
    private static final long FIRST_BOOK_ID = 1L;

    @Autowired
    private CommentDaoJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать список всех комментариев о книге")
    @Test
    void shouldReturnCorrectBooksListWithAllInfo() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        val students = repositoryJpa.listByBookId(FIRST_BOOK_ID);
        assertThat(students).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .allMatch(s -> !s.getText().equals(""));
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
    }
}
