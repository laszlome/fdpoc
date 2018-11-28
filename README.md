# fdpoc

This simple project reads all tickets from your FreshDesk account using its REST interface and builds an in-memory database of the data.
It then can be reached via jdbc, so SQL notebooks like Zeppelin can generate charts using simple SQL.

Some restrictons apply to this REST interface:
- only 100 tickets can be read at once
- More tickets must be loaded using further calls

Some images of the UI (Zeppelin) can be found here (to added).
