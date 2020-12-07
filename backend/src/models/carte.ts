import Util from "../util";
import * as database from '../database'
import range from 'lodash.range';
import shuffle from 'lodash.shuffle';

export default class Carte{
    readonly cases : number[][];
    readonly id : number;
    readonly lobbyId : number;
    static nextId : number = 0;

    constructor (lobbyId : number){
        this.lobbyId = lobbyId;
        this.cases = Carte.generateCases();
        database.cartes.push(this);
        this.id = Carte.nextId++;
    }

    private static generateCases() : number[][] {
        let generatedNumbers : number[][] = Array(5);
        
        for (let index = 0; index < Util.TAILLE_BINGO; index++) {
            generatedNumbers[index] = shuffle(range(index * Util.LIMIT_PAR_COLONNE + 1, index * Util.LIMIT_PAR_COLONNE + 15)).slice(0, Util.TAILLE_BINGO);
        }

        // Case free
        generatedNumbers[2][2] = 0;
        
        return generatedNumbers;
    }

    toJSON(){
        return {
            "id" : this.id,
            "lobbyId" : this.lobbyId,
            "cases": this.cases
        };

    }

}
