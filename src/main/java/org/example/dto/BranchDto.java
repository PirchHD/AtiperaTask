package org.example.dto;


/**
 * Reprezentuje jedną gałąź (branch) repozytorium otrzymaną z GitHub API.
 * Zawiera nazwę gałęzi oraz obiekt z SHA ostatniego commita.
 */
public class BranchDto
{
    /** String: Nazwa gałęzi */
    public String name;
    /** Commit: Obiekt commit który ma sha ostatniego commita */
    public Commit commit;

    public class Commit
    {
        /** String: sha */
        public String sha;
    }
}
