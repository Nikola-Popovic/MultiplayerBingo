import express from 'express'
const router = express.Router();
import Joueur from "../models/joueur";

router.post("/", (req : any, res : any, next : any) => {
    let nomJoueur = req.body.username;
    if(nomJoueur === "" || nomJoueur === undefined){
      res.status(400);
      res.send("Veuillez envoyer un username.");
    }
    else {
      const joueur = new Joueur(nomJoueur);
      if (joueur !== undefined) {
        res.send(joueur.toJSON());
      } else {
        res.status(400);
        res.send("Il est impossible de creer un joueur. Veuillez reesayer plus tard.");
      }
    }
  })

export default router;
