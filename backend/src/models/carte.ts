import Util from "../util";
import * as database from '../database'
import Case from "./case";

export default class Carte{
    readonly cases : number[][];
    readonly id : number;
    readonly lobbyId : number;
    static nextId : number = 0;

    constructor (lobbyId : number){
        this.lobbyId = lobbyId;
        this.cases = [];
        const generatedNumbers : number[] = [];
        for(let i = 0; i < Util.TAILLE_BINGO; i++){
            this.cases[i] = []
            for(let j = 0; j < Util.TAILLE_BINGO; j++){
                let chiffre = 0;
                do {
                    chiffre = Math.ceil(Math.random() * Util.LIMIT_PAR_COLONNE) + Util.LIMIT_PAR_COLONNE * i;
                } while(generatedNumbers.includes(chiffre));
                this.cases[i][j] = chiffre;
                generatedNumbers.push(chiffre);
            }
        }
        database.cartes.push(this);
        this.id = Carte.nextId++;
    }

    valider(lobbyId : number, numeroGagnants : Case[] ){
        if (lobbyId !== this.lobbyId )
            return false;

        numeroGagnants.forEach(num => {
            if (!this.contientLaCase(num)) {
                return false;
            }
        });

        return true;
    }

    contientLaCase(caseBingo : Case) {
        return this.cases[caseBingo.posX][caseBingo.posY] !== caseBingo.valeur
    }

    toJSON(){
        return {
            "id" : this.id,
            "lobbyId" : this.lobbyId,
            "cases": this.cases
        };

    }

}
