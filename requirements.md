
## Wymagania funkcjonalne

**Zarządzanie użytkownikami:**
- Użytkownik może się zarejestrować, podając email, hasło i podstawowe dane osobowe.
- Użytkownik może się zalogować, używając emaila i hasła.
- System generuje token JWT po udanym logowaniu.
- Użytkownik musi używać ważnego tokena JWT do autoryzacji wszystkich chroniony ch endpointów API.

**Zarządzanie wydatkami:**
~~- Użytkownik może dodać nowy wydatek, podając kwotę, datę, opis i opcjonalnie kategorię.~~
- ~~Użytkownik może edytować istniejący wydatek.~~
- ~~Użytkownik może usunąć wydatek.~~
- Użytkownik może przeglądać listę swoich wydatków z opcjami filtrowania i sortowania.

**Zarządzanie kategoriami:**
- ~~Użytkownik może tworzyć własne kategorie wydatków.~~
- ~~Użytkownik może edytować nazwy istniejących kategorii.~~
~~- Użytkownik może usuwać kategorie, jeśli nie są przypisane do żadnych wydatków.~~
- Użytkownik może przypisać kategorię do wydatku podczas tworzenia lub edycji wydatku.

**Raportowanie:**
- Użytkownik może generować miesięczne i roczne raporty wydatków.
- Użytkownik może filtrować raporty według zakresu dat, kategorii lub kwoty.
- System umożliwia eksport raportów do formatów PDF i CSV.

**Analityka:**
- System automatycznie oblicza sumę wydatków dla każdej kategorii.
- System generuje wykresy trendów wydatków w czasie.

**Zarządzanie budżetem:**
- Użytkownik może ustawić miesięczny budżet dla różnych kategorii.
- System wysyła powiadomienie, gdy wydatki zbliżają się do lub przekraczają limit budżetowy.

**Obsługa wielu walut:**
- Użytkownik może dodawać wydatki w różnych walutach.
- System automatycznie przelicza wszystkie wydatki na walutę bazową użytkownika dla celów raportowania.

## Wymagania niefunkcjonalne

**Bezpieczeństwo:**
- Wszystkie hasła muszą być przechowywane w formie zaszyfrowanej.
- Komunikacja między klientem a serwerem musi być szyfrowana (HTTPS).

**Wydajność:**
- API musi obsługiwać co najmniej 100 równoczesnych żądań na sekundę.
- Czas odpowiedzi dla większości endpointów nie powinien przekraczać 200ms.

**Skalowalność:**
- System musi być zaprojektowany z myślą o łatwym skalowaniu horyzontalnym.

**Dostępność:**
- System powinien być dostępny 99,9% czasu (downtime nie większy niż 8,76 godzin rocznie).

**Dokumentacja:**
- Wszystkie endpointy API muszą być udokumentowane za pomocą Swagger/OpenAPI.

**Testowanie:**
- Kod musi być pokryty testami jednostkowymi w co najmniej 80%.
- Muszą istnieć testy integracyjne dla kluczowych ścieżek biznesowych.

**Zgodność:**
- System musi być zgodny z RODO w zakresie przetwarzania danych osobowych.

