package logging;

import login.Authentication;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoggingTest {

    @Test
    public void loggingTest () {
        Authentication login = Authentication.getInstance ();
        login.logout ();
        Logging logging = Logging.getInstance ();

        String actualLogString = logging.getLogString ("test");
        String expectedLogString = "";

        if (logging.logFileExists()) {
            expectedLogString = String.format ("%-19s %-20s %s%n", "Date", "Gebruikersnaam", "Logging");
        }

        expectedLogString += String.format ("%-19s %-20s %s%n", logging.getFormattedDateAndTime (), "unknown", "test");
        assertEquals (expectedLogString, actualLogString);

        login.authenticate ("user1", "1");
        actualLogString = logging.getLogString ("test");
        expectedLogString = "";

        if (logging.logFileExists()) {
            expectedLogString = String.format ("%-19s %-20s %s%n", "Date", "Gebruikersnaam", "Logging");
        }

        expectedLogString += String.format ("%-19s %-20s %s%n", logging.getFormattedDateAndTime (), "user1", "test");
        assertEquals (expectedLogString, actualLogString);
    }
}