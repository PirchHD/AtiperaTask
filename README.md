✅ Wymagania
- Java 21
- Spring Boot 3.2+ (użyto 3.5.x zgodnie z wymaganiami)
- Maven

📦 Funkcjonalności
- Pobranie listy repozytoriów danego użytkownika GitHub (pomijając forkowane)
- Dla każdego repozytorium:
  + nazwa repozytorium
  + login właściciela
  + lista gałęzi z nazwą i ostatnim SHA commita
  + Obsługa błędu 404 dla nieistniejących użytkowników w określonym formacie


🔗 Endpointy

GET /api/users/{username}/repositories   - Zwraca listę publicznych, nie-forkowanych repozytoriów użytkownika.
Przykład: GET /api/users/PirchHD/repositories
