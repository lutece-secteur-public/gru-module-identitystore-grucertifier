#Module IdentityStore pour certification GRU (module-identitystore-fccertifier)

##Introduction

Ce plugin permet d'étendre le bean de certification offert par IdentityStore, afin de fournir la possibilité de notifier la GRU.

Les plugins tels que 

* le module de certification FranceConnect
* le module de certification du numéro de mobile

peuvent surcharger le bean GruCertifier; afin de personnaliser les messages de certification vers Mon Compte, la Vue 360 ou encore l'email de l'usager

##Configuration

Sont à configurer dans le fichier contexte :
 
* Le paramétrage du module de notification
** l'url de l'APIManager protégant le reverse proxy en charge du dispatch des notifications (ESB)
** l'url du webservice de GruSupply (plugin gru supply) dans le cas d'une mono-instance sans APIManager.

Deux implémentation du service de notification sont offertes afin de respecter ces deux configurations possibles. 
