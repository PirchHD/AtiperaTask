
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
     * Testuje szczęśliwą ścieżkę dla istniejącego użytkownika GitHub.
     * Sprawdza czy lista nie-forkowanych repozytoriów zawiera wymagane dane.
     */
    @Test
    void shouldReturnRepositoriesWithBranchesForExistingUser()
    {
        /**----------------------------------- Przygotowanie danych -------------------------------------**/
        String username = "PirchHD";
        /**----------------------------------- Odpalenie merody ------------------------------------------**/
        ResponseEntity<Repository[]> response = restTemplate.getForEntity("/api/" + username, Repository[].class);
        /**----------------------------------- Sprawdzenie danych -------------------------------------**/
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Repository[] repos = response.getBody();
        assertThat(repos).isNotNull();
        assertThat(repos.length).isGreaterThan(0);

        Repository firstRepo = repos[0];
        assertThat(firstRepo.name()).isNotNull();
        assertThat(firstRepo.ownerLogin()).isEqualToIgnoringCase("octocat");
        assertThat(firstRepo.branches()).isNotNull().isNotEmpty();

        Branch firstBranch = firstRepo.branches().get(0);
        assertThat(firstBranch.name()).isNotEmpty();
        assertThat(firstBranch.lastCommitSha()).isNotEmpty();
    }
}
