<img width="884" alt="Zrzut ekranu 2024-04-21 o 12 25 51" src="https://github.com/Olaf2001T/VoiceBot/assets/126798783/f41f80a4-fd4b-4669-8661-082c07b805e1">

The project involves a web application where users can record voice messages through a browser. These recordings are uploaded to a server based on Spring, which handles the implementation of appropriate business logic, manages database connections, and supports the MVC pattern. The entire backend is written in Java. On the server, the recording is converted from audio to text using Google Cloud's Speech-to-Text technology.

The text, along with the user's communication history, is sent to the OpenAI API to obtain a contextual response. After receiving the response, the text is sent back to Google Cloud, where it is converted back into speech using Text-to-Speech technology and then played back to the user.

User authentication is managed using Auth0, ensuring secure access to the application.

Frontend:

HTML
CSS
JavaScript
Thymeleaf
Backend and other technologies:

Spring (backend framework written in Java)
Hibernate (ORM for database management)
Google Cloud, Speech-to-Text, and Text-to-Speech
Auth0 (authorization management)
MySQL (database)


User Interface (UI) of the application.

<img width="1440" alt="Zrzut ekranu 2024-04-21 o 13 17 15" src="https://github.com/Olaf2001T/VoiceBot/assets/126798783/fcce0009-6945-45b5-9bbf-31ffc41e892a">

