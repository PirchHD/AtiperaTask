package org.example;

import org.example.dto.BranchDto;
import org.example.dto.RepositoryDto;
import org.example.model.Branch;
import org.example.model.Repository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service
{
    /** RestTemplate: służącego do wykonywania zapytań HTTP **/
    private final RestTemplate restTemplate;

    /**
     * Konstruktor serwisu
     * */
    public Service(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }


    /**
     * Pobiera listę nie-forkowanych repozytoriów użytkownika GitHub, a następnie dla każdego repozytorium pobiera listę gałęzi z ostatnimi commitami.
     *
     * @param username login użytkownika GitHub
     *
     * @return lista repozytoriów (nieforkowanych) z gałęziami i SHA commitów
     */
    public List<Repository> getRepositories(String username)
    {
        String url = "https://api.github.com/users/" + username + "/repos";
        try
        {
            // Wysyłamy GET do GitHub API, pobierając wszystkie repozytoria danego użytkownika
            ResponseEntity<RepositoryDto[]> response = restTemplate.getForEntity(url, RepositoryDto[].class);

            RepositoryDto[] repos = response.getBody();
            List<Repository> result = new ArrayList<>();
            for (RepositoryDto repo : repos)
            {
                if (!repo.fork)
                {
                    List<Branch> branches = getBranches(username, repo.name);
                    result.add(new Repository(repo.name, repo.owner.login, branches));
                }
            }

            return result;
        }
        catch (HttpClientErrorException.NotFound e)
        {
            throw new UserNotFoundException("User '" + username + "' not found", 404);
        }
        catch (HttpClientErrorException e)
        {
            throw new RuntimeException("GitHub API error: " + e.getMessage());
        }
    }

    /**
     * Pobiera listę gałęzi dla danego repozytorium użytkownika,
     * oraz SHA ostatniego commita w każdej gałęzi.
     *
     * @param username login użytkownika GitHub
     * @param repoName nazwa repozytorium
     *
     * @return lista gałęzi z SHA ostatniego commita
     */
    private List<Branch> getBranches(String username, String repoName)
    {
        String url = "https://api.github.com/repos/" + username + "/" + repoName + "/branches";

        // Wysyłamy GET do GitHub API, pobierając wszystkie gałęzie danego repozytorium
        ResponseEntity<BranchDto[]> response = restTemplate.getForEntity(url, BranchDto[].class);

        List<Branch> branches = new ArrayList<>();
        // Dla każdej gałęzi zapisujemy nazwę i SHA ostatniego commita
        for (BranchDto branch : response.getBody())
            branches.add(new Branch(branch.name, branch.commit.sha));

        return branches;
    }

}
