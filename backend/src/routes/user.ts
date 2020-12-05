import express from 'express'
const router = express.Router();
import Joueur from "../models/joueur";
import * as database from '../database'

router.post("/", (req : any, res : any, next : any) => {
    let nomJoueur = req.body.username;
    let token = req.body.token;
    if(nomJoueur === "" || nomJoueur === undefined){
      res.status(400);
      res.send("Veuillez envoyer un username.");
    }
    if(token === "" || token === undefined) {
      res.status(400);
      res.send("Veuillez envoyer un token.");
    }
    else {
      const joueur = new Joueur(nomJoueur, token);
      res.send(joueur.toJSON());
    }
  })

router.put("/:id", (req : any, res : any, next : any) => {
  const joueur = database.getJoueurById(parseInt(req.params.id, 10));

  if (joueur === null) {
    res.status(400);
    res.send(`Le joueur ${req.params.id} n'existe pas.`);
  }
  
  let nomJoueur = req.body.username;
  let token = req.body.token;
  if(nomJoueur === "" || nomJoueur === undefined){
    res.status(400);
    res.send("Veuillez envoyer un username.");
  }
  if(token === "" || token === undefined) {
    res.status(400);
    res.send("Veuillez envoyer un token.");
  }

  joueur.updateToken(token);
  joueur.updateUsername(nomJoueur);
  res.status(204).end();
})

export default router;
