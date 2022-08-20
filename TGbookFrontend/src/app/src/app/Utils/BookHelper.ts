import {Injectable} from "@angular/core";

@Injectable()
export class BookHelper {
   
    public shuffleArray(array: any[]): any[] {
        const arrLength = array.length-1;

        for(let i = 0; i < arrLength; i++) {
            const randomIndex = this.getRandomNumber(0, arrLength);
            const tempValue = array[i];

            array[i] = array[randomIndex];
            array[randomIndex] = tempValue;
        } return array;
    }
   
    private getRandomNumber(min: number, max: number): number {
        return Math.floor(Math.random() * (max-min + 1) + min);
    }
}
