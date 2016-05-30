# application de provisioning guest

Utiliée pour créer des comptes guest temporaires (un par un ou en batch)

Les comptes sont crés dans une base postgresql, qui sert de base à un serveur freeradius.

L'application a été développée avec netbeans.

## Connexions

La base postgresql est définie dans src/conf/persistence.xml

Les réglages des accès LDAP et CAS sont dans WEB-INF/web.xml

**ATTENTION:** il reste un logout cas "en dur" dans ./web/protected/ajax.js

## RADIUS

L'application est fonctionelle avec un freeradius 2.2.9 sous FreeBSD avec le diff ci-joint appliqué sur la conf
 (modulo l'acces pgsql)
