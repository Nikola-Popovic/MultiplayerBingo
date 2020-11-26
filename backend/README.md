IFT604-Project

#Routes faites:
- /bingo/lobby/carte/:id GET (generer carte, renvoie "carte") 
- /bingo/lobby GET (avoir acces a toutes les parties, renvoie "lobbies" qui est un array de lobby)
- /bingo/lobby POST (creer partie, s'attend à recevoir un "hostId" et un "nom" et retourne "lobby")
- /bingo/lobby/joueur/:id PUT (ajouter un joueur a une partie, "success", s'attend a un "joueurId")
- /bingo/lobby/joueur/:id DELETE (enlever un joueur, "success", s'attend a un "joueurId")
- /bingo/lobby/:id GET (pour avoir les infos sur une partie,"lobby")
- /bingo/lobby/joueur POST (ajoute un joueur au systeme a besoin de "username", retourne "joueur")


#Routes à faire:
- /bingo/lobby/:id POST (starter la partie)
- /bingo/lobby/win/:id POST (valider, retourne "success", "valide" qui est boolean, s'attend a un "carteId")

