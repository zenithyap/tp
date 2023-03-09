package seedu.library.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.library.model.Library;
import seedu.library.model.ReadOnlyLibrary;
import seedu.library.model.bookmark.Author;
import seedu.library.model.bookmark.Genre;
import seedu.library.model.bookmark.Title;
import seedu.library.model.bookmark.Bookmark;
import seedu.library.model.bookmark.Phone;
import seedu.library.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Library} with sample data.
 */
public class SampleDataUtil {
    public static Bookmark[] getSampleBookmarks() {
        return new Bookmark[] {
            new Bookmark(new Title("Alex Yeoh"), new Phone("87438807"), new Genre("alexyeoh@example.com"),
                new Author("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Bookmark(new Title("Bernice Yu"), new Phone("99272758"), new Genre("berniceyu@example.com"),
                new Author("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Bookmark(new Title("Charlotte Oliveiro"), new Phone("93210283"), new Genre("charlotte@example.com"),
                new Author("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Bookmark(new Title("David Li"), new Phone("91031282"), new Genre("lidavid@example.com"),
                new Author("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Bookmark(new Title("Irfan Ibrahim"), new Phone("92492021"), new Genre("irfan@example.com"),
                new Author("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Bookmark(new Title("Roy Balakrishnan"), new Phone("92624417"), new Genre("royb@example.com"),
                new Author("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyLibrary getSampleLibrary() {
        Library sampleAb = new Library();
        for (Bookmark sampleBookmark : getSampleBookmarks()) {
            sampleAb.addBookmark(sampleBookmark);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
