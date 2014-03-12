liferay-client-remote-ws-simple-library
=======================================

I have been trying to find an easy way to use the remote web services of Liferay CE 6.1.2 GA3. Therefore, I came up with the idea of creating a SIMPLE LIBRARY that I would later insert to my project in order to make it possible for me to manage the Documents Library.

This library could be imported on a java project and you can initialize with liferay.properties (included to the new project that you will create).


liferay.properties
=================================================================================================

The default parameters that you can set are the following:

LIFERAY_PUBLISH_SITE = Guest

LIFERAY_PROTOCOL = http://

LIFERAY_DN = Domain Name or IP

LIFERAY_TCP_PORT = Port

LIFERAY_AXIS_PATH = /api/secure/axis/

LIFERAY_USER_NAME = Screen Name or Email

LIFERAY_USER_PASSWORD = Password


1) First initialize propertie by call:
=================================================================================================

ResourceBundle resourceBundle = ResourceBundle.getBundle("liferay");

LiferayProperties.initResourceBundle(resourceBundle);

2) Available Services
=================================================================================================

From file Services.java you can call:

Services._initServices();

and then choose service to start with:

1) Services._startUserService();

2) Services._startCompanyService();

3) Services._startGroupService();

4) Services._startDLAppService();

=================================================================================================

If you want to manage the Document Library of Liferay and your portal is in staging mode, be carefull with staging modes (Uncheck Documents and Media).


