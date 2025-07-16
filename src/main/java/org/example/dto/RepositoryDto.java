package org.example.dto;

/**
 * Reprezentuje pojedyncze repozytorium pobrane z GitHub API.
 * Używane do mapowania danych przychodzących z zewnętrznego serwisu.
 */
public class RepositoryDto
{
    /** String: Nazwa repozytorium */
    public String name;

    /** boolean: Czy repozytorium jest forkiem ? */
    public boolean fork;

    /** Owner: Obiekt zawierający login właściciela repozytorium */
    public Owner owner;

    /**
     * Reprezentuje właściciela repozytorium – użytkownika GitHub.
     */
    public static class Owner
    {
        /** String: Login użytkownika GitHub (np. "octocat") */
        public String login;
    }
}

