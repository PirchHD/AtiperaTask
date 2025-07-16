package org.example;

import org.example.model.Repository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller
{

    /** Service: serwis **/
    private final Service service;

    /**
     * Konstruktor kontrolera
     * */
    public Controller(Service service)
    {
        this.service = service;
    }

    /**
     * Endpoint GET: /api/{username}
     * Zwraca listę nie-forkowanych repozytoriów podanego użytkownika GitHub wraz z gałęziami i SHA ostatniego commita.
     *
     * @param username  - (String) Nazwa użytkownika (np. Najlepszy programista "PirchHD")
     *
     * @return Lista repozytoriów w formacie JSON
     */
    @GetMapping("/{username}")
    public ResponseEntity<List<Repository>> getRepository(@PathVariable String username)
    {
        return ResponseEntity.ok(service.getRepositories(username));
    }


}
