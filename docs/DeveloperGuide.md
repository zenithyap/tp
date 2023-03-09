## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Consumer of comics and web novels
* Has a need to manage the large number of books he consumes
* Prefer desktop apps over other types
* Can type fast
* Prefers typing to mouse interactions
* Is reasonably comfortable using CLI apps

**Value proposition**:
* Manage books for the user.
* Keeps track of books they have not started, is currently reading, has finished reading.
* Manage books faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                     | So that I can…​                                                      |
| -------- | ------------------------------------------ | ------------------------------ | ---------------------------------------------------------------------- |
| `* * *`  | new user                                   | see usage instructions         | refer to instructions when I forget how to use the App                 |
| `* * *`  | user                                       | add a new bookmark             |   start tracking a book                                                |
| `* * *`  | user                                       | delete a bookmark              | remove entries that I no longer need                                   |
| `* * *`  | user with many bookmarks | find bookmarks by book title   | locate bookmarsk without having to go through the entire list |
| `* * *`    | user                                       | edit a bookmark                 | update the information in my bookmark               |
| `* *`  | user | view the details of a single bookmark | see information about a particular book I am tracking
| `* *` | user with mostly unnecessary bookmarks | clear all bookmarks |  not delete each bookmark one by one |
| `* *` | user with many bookmarks | find bookmarks by book type | view bookmarks of only a certain type|
| `* *` | user with many bookmarks | find bookmarks by book genre | sview bookmarks of only a certain genre|
| `* *` | user  | rate a book through its bookmark | remember how much I enjoyed the book|
| `* *`      | user with many bookmarks | sort bookmarks by book title           | locate a bookmark easily             |
| `* *` | user with many bookmarks | sort bookmarks by rating | locate bookmarks of books I enjoyed easily|
| `* *` | user who likes detail | add tags to bookmark | give additional labels to a bookmark|
| `* *` | user with many bookmarks | find bookmarks by their tags | view only bookmarks who have certain tags|
| `* *` | user with many bookmarks | find bookmarks by book author | view bookmarks of books written by a specific author|
| `* *` | user | add hyperlinks to bookmarks | link the website where I am reading the book's chapters from|
| `*` | user | add book characters to a bookmark | store noteworthy characters which I remember the book by|
| `* ` | user | find bookmarks using title of characters | locate books with certain characters easily|
| `* *` | user with many bookmarks | sort bookmarks by date of creation | view bookmarks in order of creation |
| `* *` | user with many bookmarks | find bookmarks by last modified date | view bookmarks in order of most recently updated|
| `* *` | user  | add last read chapter to a bookmark | know where I last left off with a certain book|
| `* *` | user | find bookmarks based on their status | view only bookmarks of a certain status easily|

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `MyLib` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Delete a bookmark**

**MSS**

1.  User requests to list bookmarks
2.  MyLib shows a list of bookmarks
3.  User requests to delete a specific bookmark in the list
4.  MyLib deletes the bookmark

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. MyLib shows an error message.

      Use case resumes at step 2.

**Use case: Edit a bookmark**

**MSS**

1.  User requests to list bookmarks
2.  MyLib shows a list of bookmarks
3.  User requests to edit a specific bookmark in the list
4.  MyLib edits the bookmark

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. MyLib shows an error message.

      Use case resumes at step 2.
      
* 3b. Less than one field to edit provided.

    * 3b1. MyLib shows an error message.
    
      Use case resumes at step 2

* 3c. New value is invalid for any field.

    * 3c1. MyLib shows an error message.
    
      Use case resumes at step 2.

**Use case: Add a bookmark**

**MSS**

1.  User requests to add a bookmark
2.  MyLib adds a bookmark

    Use case ends.

**Extensions**

* 1a. The given index is invalid.

    * 1a1. MyLib shows an error message.

      Use case resumes at step 2.
      
* 1b. Not all compulsory fields provided.

    * 1b1. MyLib shows an error message.
    
      Use case resumes at step 2

* 1c. Value is invalid for any field.

    * 1c1. MyLib shows an error message.
    
      Use case resumes at step 2.
      
*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 bookmarks without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Saved data should be stored locally and in a human editable text file to allow advanced users to manipulate the data by editing the file.
5.  The software should work without requiring an installer.
6.  The Product JAR/ZIP file should not exceed 100MB.
7.  Documents (i.e. PDF files) should not exceed 15MB per file.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Bookmark**: Tracker for a book that contains information about the book and a user's reading progress

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a bookmark

1. Deleting a bookmark while all bookmarks are being shown

   1. Prerequisites: List all bookmarks using the `list` command. 

   1. Test case: `delete 1`<br>
      Expected: First bookmark is deleted from the list. Details of the deleted bookmark shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No bookmark is deleted. prompts user to enter valid index in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size or x is not an integer)<br>
      Expected: No bookmark is deleted. prompts user to enter valid index in the status message. Status bar remains the same.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
