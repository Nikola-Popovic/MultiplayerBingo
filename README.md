# It's bingo time

Pour démarrer l'application, il suffit de lancer la commande `docker-compose up --build`. Cette commande va lancer le serveur web et il sera disponible à l'addresse
http://localhost:3000.

À noter : puisque l'application utilise un instance de Firebase, on ne peut pas faire rouler plusieurs instances du serveur, car les ids des parties vont 
se chevaucher ce qui va envoyer des mauvais messages aux mauvaises parties (ex: 2 serveurs qui ont une partie 1 qui roule en même temps).
