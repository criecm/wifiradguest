# application de provisioning guest

Utiliée pour créer des comptes guest temporaires (un par un ou en batch)

Authn/Authz cas/ldap

Les comptes sont crés dans une base postgresql, qui sert de base à un serveur freeradius (auth+acct).

Le serveur freeradius (acct) met à jour l'heure de première connexion et vérifie la validité du compte.

L'application a été développée avec netbeans.

## Connexions

La base postgresql est définie dans src/conf/persistence.xml

Les réglages des accès LDAP et CAS sont dans WEB-INF/web.xml

**ATTENTION:** il reste un logout cas "en dur" dans ./web/protected/ajax.js

## RADIUS

L'application est fonctionelle avec un freeradius 3.0.16 sous FreeBSD avec le diff ci-joint appliqué sur la conf
 (modulo l'acces pgsql)
