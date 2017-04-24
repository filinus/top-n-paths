Project demonstrates capabilities of Java 8 Stream API and lambdas
Task to find top most N-pages path in log like
   U1 /
   U2 /
   U1 /page
   U1 /filter
   U1 /catalog
   U3 / 
   U4 /
   U2 /new
   U2 /catalog
   U1 /pageZ
U* is a id of user session, second column is visit page from a log file

Examples of N-page-paths, where N=3:
  / -> /new -> /catalog
  / -> /page -> /filter
  /page -> /filter -> /catalog
  /filter -> /catalog ->pageZ  
