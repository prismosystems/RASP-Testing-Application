# RASP-Testing-Application

Prismo RASP Vulnerable Web Application

This is a vulnerable java web application developed by Prismo Systems. This app is intended for testing Web Application Vulnerability detections. 

Following exploits can be tested using this application:

* SQL Injection

* SQL Injection in Stored Procedure

* SQL Injection in Prepared Statement

* Server Side Request Forgery

* Insecure Deserialization

* WebShell Command Execution

* OS Command Execution

* Reflective Cross Site Scripting

* Persistence Cross Site Scripting

How to Setup:
------------

To build the WAR file, import the project in Eclipse. Once the project is imported, resolve the Maven Dependencies. Build the package using the following command: mvn package, which will create a WAR file in the target folder. Deploy this WAR file in Apache Tomcat Server's web application folder.
