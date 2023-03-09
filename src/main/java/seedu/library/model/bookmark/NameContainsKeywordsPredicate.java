package seedu.library.model.bookmark;

import java.util.List;
import java.util.function.Predicate;

import seedu.library.commons.util.StringUtil;

/**
 * Tests that a {@code Bookmark}'s {@code Title} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Bookmark> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Bookmark bookmark) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(bookmark.getTitle().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
