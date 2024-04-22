<img width="884" alt="Zrzut ekranu 2024-04-21 o 12 25 51" src="https://github.com/Olaf2001T/VoiceBot/assets/126798783/f41f80a4-fd4b-4669-8661-082c07b805e1">

## Description

The project involves a web application where users can record voice messages through a browser. These recordings are uploaded to a server based on Spring, which handles the implementation of appropriate business logic, manages database connections, and supports the MVC pattern. The entire backend is written in Java. On the server, the recording is converted from audio to text using Google Cloud's Speech-to-Text technology.

The text, along with the user's communication history, is sent to the OpenAI API to obtain a contextual response. After receiving the response, the text is sent back to Google Cloud, where it is converted back into speech using Text-to-Speech technology and then played back to the user.

User authentication is managed using Auth0, ensuring secure access to the application.

## Team Members

- Jakub Szczękulski
- Olaf Teperek

## Tech Stack

### GUI:
- HTML5
- CSS3
- JavaScript
- Thymeleaf 

### Backend:
- Java
- Spring
- Google TTS/STT
- OpenAI API
- MySQL

## Installation

To set up the project environment, please follow the instructions below:

1. **Configure Environment**: Refer to the [Environment Configuration Guide](https://cloud.google.com/speech-to-text/docs/before-you-begin) provided by Google Cloud for setting up the necessary environment configurations.

2. **Clone the Repository**: Clone the Voice Bot repository to your local machine using Git:
    ```
    git clone https://github.com/your-username/voice-bot.git
    ```

3. **Install Dependencies**: Navigate to the project directory and install the required dependencies for both frontend and backend components.

4. **Database Setup**: Set up the MySQL database by running the provided SQL scripts to create the necessary tables and schemas.

5. **Configuration**: Configure the project settings, including API keys for Google TTS/STT and OpenAI API. Ensure all necessary configurations are correctly set up for seamless integration.

6. **Build and Run**: Build the project and start the server to launch the Voice Bot application.

7. **Testing**: Test the application by interacting with the chatbot using voice commands. Ensure that both input and output responses are working as expected.

## User Interface (UI) of the application.

<img width="1440" alt="Zrzut ekranu 2024-04-21 o 13 17 15" src="https://github.com/Olaf2001T/VoiceBot/assets/126798783/fcce0009-6945-45b5-9bbf-31ffc41e892a">

