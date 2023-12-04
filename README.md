# CSVMagic

This is a simple application that presents users a menu of choices:

(1)	Read customers from a CSV file in order to populate the information to a database.
(2)	Display all customer records currently in the database.
(3)	Display a particular customer based on a user-supplied customer reference.

I used OpenCSV’s CSVReader library to help parse the CSV file.  

I used the builder pattern to construct a Customer object from each line of the CSV.  This object can then be serialised into JSON and passed directly to the POST endpoint on Customer Manager.

I stored the key parameters for CSV file path/name, and the various endpoint names in an application.properties file to minimise the need to touch the java code between any environments.
