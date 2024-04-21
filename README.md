<img width="884" alt="Zrzut ekranu 2024-04-21 o 12 25 51" src="https://github.com/Olaf2001T/VoiceBot/assets/126798783/f41f80a4-fd4b-4669-8661-082c07b805e1">

Projekt obejmuje aplikację internetową, gdzie użytkownik nagrywa wiadomości głosowe przez przeglądarkę. Nagrania te są przesyłane na serwer oparty na Spring, który odpowiada za implementację odpowiedniej logiki biznesowej, zarządzanie połączeniami z bazą danych oraz obsługę wzorca MVC. Cały backend został napisany w języku Java. Na serwerze nagranie jest przekształcane z dźwięku na tekst, używając technologii Speech to Text dostępnej w Google Cloud.

Tekst wraz z historią komunikacji użytkownika jest wysyłany do API OpenAI w celu uzyskania kontekstualnej odpowiedzi. Po otrzymaniu odpowiedzi, tekst jest ponownie wysyłany do Google Cloud, gdzie za pomocą Text to Speech jest konwertowany na mowę, a następnie odtwarzany użytkownikowi.

Autoryzacja użytkowników odbywa się za pomocą Auth0, zapewniając bezpieczny dostęp do aplikacji.

Frontend:

HTML
CSS
JavaScript
Thymeleaf

Backend i inne technologie:

Spring (Framework backendowy, napisany w Java)
Hibernate (ORM do zarządzania bazą danych)
Google Cloud,Speech-to-Text i Text-to-Speech
Auth0 (Zarządzanie autoryzacją)
MySQL (Baza danych)
