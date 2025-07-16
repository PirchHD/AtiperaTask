package org.example.model;

import java.util.List;

/**
 * Model repozytorium GitHub (nieforkowanego), zawierający podstawowe informacje potrzebne w odpowiedzi API.
 *
 * @param name         - nazwa repozytorium (np. "my-app")
 * @param ownerLogin   - login właściciela repozytorium (np. "octocat")
 * @param branches     - lista gałęzi wraz z SHA ostatnich commitów
 */
public record Repository(String name, String ownerLogin, List<Branch> branches) { }
