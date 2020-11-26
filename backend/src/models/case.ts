export default class Case{
    readonly valeur : number;
    readonly posX : number;
    readonly posY : number;

    constructor (valeur : number, posX : number, posY : number){
        this.valeur = valeur;
        this.posX = posX;
        this.posY = posY;
    }
}
