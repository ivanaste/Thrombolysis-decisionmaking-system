import { Component } from '@angular/core';


export interface Bolest {
  ime: string;
  checked: boolean;
  datum: Date | null;
}
@Component({
  selector: 'app-neuroloski-pregled',
  templateUrl: './neuroloski-pregled.component.html',
  styleUrls: ['./neuroloski-pregled.component.scss']
})
export class NeuroloskiPregledComponent {
  idOdluke: string = ''
  bolesti: Bolest[] = [{ime: "Operativna intervencija", checked: false, datum: null}, {
    ime: "Intrakranijalna hemoragija",
    checked: false,
    datum: null
  },
    {
      ime: "Gastrointestinalno krvarenje", checked: false, datum: null
    }, {ime: "Urogenitalno krvarenje", checked: false, datum: null},
    {ime: "Arterijska punkcija", checked: false, datum: null}, {
      ime: "Lumbalna punkcija",
      checked: false, datum: null
    }, {ime: "Akutni infarkt miokarda", checked: false, datum: null}]

  proveriOdluku() {
    var checkedBolesti = this.bolesti.filter(bolest => bolest.checked);
    const modifiedBolesti = checkedBolesti.map((bolest) => {
      return {
        ...bolest,
        ime: bolest.ime.toUpperCase().replace(/ /g, "_")
      };
    });
    let backendObject = {
      "idOdluke": this.idOdluke,
      "kontraindikacije": modifiedBolesti.map(bolest => bolest.ime),
      "datumiDesavanjaKontraindikacija": modifiedBolesti.map(bolest => bolest.datum)
    }
    console.log(backendObject)
  }

}
