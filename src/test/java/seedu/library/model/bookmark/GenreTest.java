package seedu.library.model.bookmark;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.library.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GenreTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Genre(null));
    }

    @Test
    public void constructor_invalidGenre_throwsIllegalArgumentException() {
        String invalidGenre = "";
        assertThrows(IllegalArgumentException.class, () -> new Genre(invalidGenre));
    }

    @Test
    public void isValidGenre() {
        // null genre
        assertThrows(NullPointerException.class, () -> Genre.isValidGenre(null));

        // blank genre
        assertFalse(Genre.isValidGenre("")); // empty string
        assertFalse(Genre.isValidGenre(" ")); // spaces only

        // missing parts
        assertFalse(Genre.isValidGenre("@example.com")); // missing local part
        assertFalse(Genre.isValidGenre("peterjackexample.com")); // missing '@' symbol
        assertFalse(Genre.isValidGenre("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Genre.isValidGenre("peterjack@-")); // invalid domain name
        assertFalse(Genre.isValidGenre("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Genre.isValidGenre("peter jack@example.com")); // spaces in local part
        assertFalse(Genre.isValidGenre("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Genre.isValidGenre(" peterjack@example.com")); // leading space
        assertFalse(Genre.isValidGenre("peterjack@example.com ")); // trailing space
        assertFalse(Genre.isValidGenre("peterjack@@example.com")); // double '@' symbol
        assertFalse(Genre.isValidGenre("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Genre.isValidGenre("-peterjack@example.com")); // local part starts with a hyphen
        assertFalse(Genre.isValidGenre("peterjack-@example.com")); // local part ends with a hyphen
        assertFalse(Genre.isValidGenre("peter..jack@example.com")); // local part has two consecutive periods
        assertFalse(Genre.isValidGenre("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Genre.isValidGenre("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Genre.isValidGenre("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Genre.isValidGenre("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Genre.isValidGenre("peterjack@example.com-")); // domain name ends with a hyphen
        assertFalse(Genre.isValidGenre("peterjack@example.c")); // top level domain has less than two chars

        // valid genre
        assertTrue(Genre.isValidGenre("PeterJack_1190@example.com")); // underscore in local part
        assertTrue(Genre.isValidGenre("PeterJack.1190@example.com")); // period in local part
        assertTrue(Genre.isValidGenre("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(Genre.isValidGenre("PeterJack-1190@example.com")); // hyphen in local part
        assertTrue(Genre.isValidGenre("a@bc")); // minimal
        assertTrue(Genre.isValidGenre("test@localhost")); // alphabets only
        assertTrue(Genre.isValidGenre("123@145")); // numeric local part and domain name
        assertTrue(Genre.isValidGenre("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Genre.isValidGenre("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Genre.isValidGenre("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(Genre.isValidGenre("e1234567@u.nus.edu")); // more than one period in domain
    }
}
