
import org.example.Main;
import org.example.model.Branch;
import org.example.model.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest
{

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Metoda powinna zadziałać poprawnie
     * -------------------------------------------------------------
     *
     * Dostaniemy repo oraz branche użytkownika PirchHD
     */
    @Test
    void integrationTest1()
    {
        /**----------------------------------- Przygotowanie danych -------------------------------------**/
        String username = "PirchHD";
        /**----------------------------------- Wywołanie metody ------------------------------------------**/
        ResponseEntity<Repository[]> response = restTemplate.getForEntity("/api/" + username, Repository[].class);
        /**----------------------------------- Sprawdzenie danych -------------------------------------**/
        Repository[] repos = response.getBody();
        assertThat(repos).as("Odpowiedź API powinna zdziałać").isNotNull();
        assertThat(repos.length).as("Lista repozytoriów nie powinna być pusta").isGreaterThan(0);
        for (Repository repo : repos)
            assertValidRepository(repo, username);
    }

    /**
     * Metoda sprawdzająca poprawność obiektu Repository.
     *
     * @param repo              - (Repository) repozytorium do sprawdzenia
     * @param expectedUsername  - (String) oczekiwany właściciel repozytorium
     */
    private void assertValidRepository(Repository repo, String expectedUsername)
    {
        assertThat(repo).as("Repozytorium nie powinno być nullem").isNotNull();
        assertThat(repo.name()).as("Repozytorium nie ma nazwy").isNotNull().isNotEmpty();
        assertThat(repo.ownerLogin()).as("Repozytorium ma nieprawidłowego właściciela").isEqualToIgnoringCase(expectedUsername);
        List<Branch> branches = repo.branches();
        assertThat(branches).as("Repozytorium nie zawiera gałęzi").isNotNull().isNotEmpty();
        for (Branch branch : branches)
            assertValidBranch(branch);

    }

    /**
     * Metoda sprawdzająca poprawność obiektu Branch.
     *
     * @param branch   - (Branch) gałąź do sprawdzenia
     */
    private void assertValidBranch(Branch branch)
    {
        assertThat(branch.name()).as("Gałąź nie ma nazwy").isNotNull().isNotEmpty();
        assertThat(branch.lastCommitSha()).as("Gałąź nie zawiera SHA ostatniego commita").isNotNull().isNotEmpty();
    }
}
