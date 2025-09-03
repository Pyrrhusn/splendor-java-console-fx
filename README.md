#### Splendor - Software Development Project I - Group project - Year 1
This project contains the source code of a simplified digital adaption of the board game Splendor, programmed in Java as a console application and a desktop application built with JavaFX. *The JavaFX desktop application is missing some gui elements and therefore not fully completed.*

The `/doc` folder contains the javadoc generated documentation of the domain entities, methods and their use-cases.
There is no authentication system, but the users/players are stored and retrieved from the database using their name and year of birth. Furthermore, the application supports multiple languages, English (EN) and Dutch (NL), that are loaded with ResourceBundles.

Communication between the domain and UI layers is provided through Data Transfer Objects (DTOs), which can be found under the `/src/dto` directory. Objects mapping to/from database is done or simulated with the corresponding mapper classed found under `src/persistentie`, the database connection configuration can be foud here as well.

Notable code worth metioning:
  - The crads are built

To start the application, go to `src/main`:
  -  run `StartUp.java` for the console/terminal application
  -  run `StartUpGUI.java` for the JavaFX application
