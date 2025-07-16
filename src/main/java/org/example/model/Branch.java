package org.example.model;


/**
 * Model gałęzi (branch) repozytorium GitHub.
 * Zawiera nazwę gałęzi oraz SHA ostatniego commita.
 *
 * @param name           - nazwa gałęzi (np. "main")
 * @param lastCommitSha  - SHA ostatniego commita w tej gałęzi
 */
public record Branch(String name, String lastCommitSha) { }
