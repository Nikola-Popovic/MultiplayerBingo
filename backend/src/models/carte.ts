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

    getRow(rowId : number) : number[] {
        return this.cases[rowId];
    }

    getColumn(colId : number) : number[] {
        let cols : number[] = [];
        for (let i = 0; i < Util.TAILLE_BINGO; i++) {
            cols.push(this.cases[i][colId]);
        }
        return cols;
    }

    getFirstDiag() : number[] {
        let diag = [];
        for (let i = 0; i < Util.TAILLE_BINGO; i++) {
            diag.push(this.cases[i][i]);
        }
        return diag;
    }

    getSecondDiag() : number[] {
        let diag = [];
        for (let i = 0; i < Util.TAILLE_BINGO; i++) {
            for (let j = Util.TAILLE_BINGO - 1; j >= 0; j--) {
                diag.push(this.cases[i][j]);
            }
        }
        return diag;
    }

    toJSON(){
        return {
            "id" : this.id,
            "lobbyId" : this.lobbyId,
            "cases": this.cases
        };

    }

}
