
# Movie Booking System

## Introduction
**Movie Booking System** is a Java application that allows users to book movie tickets at theaters. Users can choose movies, check showtimes, select seats, and make payments online. The project is built using **Java Swing** for the graphical user interface, with planned AI features for enhanced user experience such as comment analysis, movie recommendations, and film reviews.

## Features
1. **Movie List Display**: Users can view the list of movies currently showing in theaters.
2. **Movie Selection and Details**: Users can select a movie and view detailed information (showtimes, theater details, etc.).
3. **Ticket Booking**: Allows users to select seats and proceed with ticket booking.
4. **Payment**: Users can enter payment details to complete the ticket purchase.
5. **AI Comment Analysis**: AI analyzes and classifies user comments (positive, negative, neutral).
6. **AI Movie Recommendations**: Based on showtimes and user preferences, AI will suggest appropriate movies.
7. **AI Movie Reviews**: Based on user feedback, AI generates an overall rating and review for each movie.

## System Requirements
- **Java 8** or higher
- **Maven** (for dependency management if needed)
- **External Libraries**:
  - **Java Swing**: Used for building the user interface.
  - **JavaFX** (optional): If you want to switch to JavaFX for a better UI experience.
  - **Gson/Jackson**: For JSON data processing (if required).
  - **AI APIs**: Integration of AI models for comment analysis, recommendations, and movie reviews (can use APIs like **Google Cloud NLP**, **IBM Watson**, or custom-built models).

## Installation
1. **Clone the project from GitHub**:
   ```bash
   git clone 
   ```

2. **Compile and run the application**:
   - Open the project in an IDE such as **IntelliJ IDEA**, **Eclipse**, or **NetBeans**.
   - Compile the project (if using Maven, run `mvn install`).
   - Run the application by executing the `MovieBookingSystemGUI.java` file.

3. **Run the program**:
   ```bash
   javac MovieBookingSystemGUI.java
   java MovieBookingSystemGUI
   ```

## Usage Guide
1. **Open the application**: After launching the program, the main window of the system will appear.
2. **Select a movie**: From the dropdown menu, you can choose a movie from the list.
3. **Movie information**: Click the "Select Movie" button to view detailed information about the selected movie.
4. **Book tickets**: Click the "Book Tickets" button to proceed with seat selection and payment.
5. **Payment**: Enter payment information to complete the transaction.

## System Architecture
- **MovieBookingSystemGUI.java**: The main class managing the user interface, including components like the movie list, select movie button, and book ticket button.
- **AI Modules** (planned integration): 
  - **ReviewAnalyzer.java**: Handles customer comment classification.
  - **ShowtimeQueryAI.java**: Analyzes and answers questions related to showtimes.
  - **MovieReviewerAI.java**: Automatically generates movie reviews and ratings.

## Planned AI Features
1. **Comment Analysis**: Classifies customer comments on movies into sentiment categories.
2. **Movie Recommendations**: Suggests appropriate movies to users based on their viewing habits and showtimes.
3. **Movie Reviews**: AI will generate an overall movie review based on aggregated user feedback.

## Author
- **OOP_JAVA_PTIT**: Developer of the Movie Booking System.
- **Email**: 

## License
This project is licensed under the **MIT License**.
