import {Component} from '@angular/core';


interface Bolest {
  ime: string;
  checked: boolean;
  datum: Date;
}

@Component({
  selector: 'app-odluka-otrombolizi',
  templateUrl: './odluka-otrombolizi.component.html',
  styleUrls: ['./odluka-otrombolizi.component.scss']
})
export class OdlukaOTromboliziComponent {
  filters: string[] = [];
  bolesti: Bolest[] = [{ime: "Operativna intervencija", checked: false, datum: new Date()}, {
    ime: "Intrakranijalna hemoragija",
    checked: true,
    datum: new Date()
  },
    {
      ime: "Gastrointestinalno krvarenje", checked: false, datum: new Date()
    }, {ime: "Urogenitalno krvarenje", checked: false, datum: new Date()},
    {ime: "Arterijska punkcija", checked: false, datum: new Date()}, {
      ime: "Lumbalna punkcija",
      checked: false, datum: new Date()
    }, {ime: "Akutni infarkt miokarda", checked: true, datum: new Date()}]

  op() {
    console.log(this.bolesti)
  }
}
