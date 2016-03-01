# date-service

A Clojure based rest api to serve current time.

## Getting Started

1. Start the application: `lein run-dev` \*
2. Go to [localhost:2057](http://localhost:2057/date) to see a response simillar to: 
   ```
   {"time_of_day":"February 28, 2016 6:00:34 PM EST"}
   ```
3. App's source code is at src/date_service/service.clj. Explore the docs of functions
   that define routes and responses.

\* `lein run-dev` automatically detects code changes. Alternatively, you can run in production mode
with `lein run`.

