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
1. To build the WAR file following steps have to be followed.
	Import the project in Eclispe. Resolve the Maven Dependencies. Build using following command: mvn package
	It will create a WAR file in target folder. Deploy the WAR file in Apache Tomcat Server.
