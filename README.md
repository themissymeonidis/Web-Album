# Web-Album
Java Web Application Album With Metadata Extraction

A University Project

A Web Application build with Java.

ABOUT THE PROJECT

The goal was to make a web application that acts as an album. The user will be able to upload any image and categorize it as well.
Any image contains information called "Metadata", the application must be able to read that data, display it and also use google map for geolocate.
The user must also be able to search through the ablums database by using any filter he wants (such as category, description, Title, location etc.)

WORKING ON THE PROJECT

First I had to create a database (used MariaDB) and connect that database to the java web app (used Netbeans).
After the connections was establised, the next step was to create a landing page where the user can upload an image to the database.
I choose to save the images on my computer and only use the path as a referance for the database (That way i saved space for the database)
The next step was to create a page where the user can see all the photos, filter through them and also click on them to see additional information (metadata + pin on google maps)
The page had to render html dynamicly in order for the user to properly see all the images and filter through them without causing any error on the server.
Finally, I added some style on the pages with css.

ISSUES I HAD WHILE WORKING ON THE PROJECT

i) Showing all the images at the user was challlenging, I had to find a way to render html dynamicly through a java servlet
I easily solved that by implementing the writer.println functionaly of java combined with a complicated if statement that build the page dynamicly
ii) The filters had to be complex, meaning that the user can choose from a category and also from description & maybe title as well or location
Created a complecated if statement with a string builder, that way every time the user selected a new filter the string builder would add another variable to the sql.
iii) Fetching the Images was complicated because I wanted to save space from the database and thus i didn't save the images there, but on my local hard drive.
To solve this I created a servlet (Fetch_Image) that would take the path from the database and use Output Stream to render the image as and object (example: <img src="http://localhost:8080/mavenproject2/Fetch_Image?path=/home/muvox/NetBeansProjects/Images/IMG_20210603_102052.jpg" class="image">)
That way I could have as many images as I wanted and the speed of the app was far ahead from the competition( Because I could implement a method where i only load 20 images and then load another 10 as the user scrolled down)

OVERALL
The project was rated (9/10) and was the top project on the class, the code was uploaded on e-learning so students from next year could take notes out of it.
The project itself was challenging, but challenges are always welcome here.
