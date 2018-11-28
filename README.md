# fdpoc

This simple project, based on Apache Iginte, reads all tickets from an existing FreshDesk account using FreshDesk's public REST API and builds an in-memory database of the data. It then can be reached via jdbc, so SQL notebooks like Apache Zeppelin can generate charts using simple SQL.

FreshDesk has built-in reposrts, but in case a custome query is needed, this is a possible way to quickly add one.

Some restrictions apply to this REST interface:
- Only 100 tickets can be read at once
- More tickets must be loaded using further calls

[Example screenshot of the data (anonymized)](https://photos.app.goo.gl/ZRSv9wF7wC5GfAJu8)
