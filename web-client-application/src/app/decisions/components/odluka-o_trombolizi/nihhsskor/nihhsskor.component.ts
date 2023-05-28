import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NeuroloskiPregled} from "../../../model/NeuroloskiPregled";
import {Decision} from "../../../model/decision";
import {NIHHS} from "../../../model/NIHHS";
import {DecisionStatus} from "../../../model/decision-status";

@Component({
  selector: 'app-nihhsskor',
  templateUrl: './nihhsskor.component.html',
  styleUrls: ['./nihhsskor.component.scss']
})
export class NIHHSSkorComponent {
  @Output() nihhsSkorEvent: EventEmitter<NIHHS> = new EventEmitter<NIHHS>();
  @Input() odlukaOTrombolizi: Decision | null;

  elementiNIHHSSkale = [
    [{ime: 'Stanje svesti', opcije: ['Budan', 'Somnolentan', 'Sopor', 'Koma'], skor: 0, izabranaOpcija: ''}, {ime: "Stanje svesti - pitanja", opcije: ['Tacno odgovara', 'Na 1 tacno odgovara', 'Netacno odgovara'], skor: 0, izabranaOpcija: ''}, {ime: 'Stanje svesti - nalozi', opcije: ['Tacno izvrsava', 'Jedan tacno izvrsava', 'Netacno izvrsava oba naloga', 'koma'], skor: 0, izabranaOpcija: ''}],
    [{ime: "Pokreti bulbusa", opcije: ['Normalni', 'Paraliza pogleda', 'Paraliza i devijacija pogleda'], skor: 0, izabranaOpcija: ''}, {ime: "Sirina vidnog polja", opcije: ['Normalno', 'Parcijalna hemianopsija', 'Potpuna hemianopsija', 'Slepilo'], skor: 0, izabranaOpcija: ''}, {ime: "Mimicna motorika", opcije: ['Normalna', 'Minimalna pareza', 'Parcijalna pareza', 'Kompletna pareza'], skor: 0, izabranaOpcija: ''}],
    [{ime: "Motorika - leva ruka", opcije: ['Bez tonjenja posle 10s', 'Pronacija i tonjenje u 10s, ali ne dotice krevet', 'Ruka tone do kreveta, ali moguć otpor', 'Ruka pada, nema otpora, minimalan pokret', 'Bez pokreta'], skor: 0, izabranaOpcija: ''}, {ime: "Motorika - desna ruka", opcije: ['Bez tonjenja posle 10s', 'Pronacija i tonjenje u 10s, ali ne dotice krevet', 'Ruka tone do kreveta, ali moguć otpor', 'Ruka pada, nema otpora, minimalan pokret', 'Bez pokreta'], skor: 0, izabranaOpcija: ''}, {ime: "Motorika - leva noga", opcije: ['Bez tonjenja posle 10s', 'Pronacija i tonjenje u 10s, ali ne dotice krevet', 'Ruka tone do kreveta, ali moguć otpor', 'Ruka pada, nema otpora, minimalan pokret', 'Bez pokreta'], skor: 0, izabranaOpcija: ''}],
    [{ime: "Motorika - desna noga", opcije: ['Bez tonjenja posle 10s', 'Pronacija i tonjenje u 10s, ali ne dotice krevet', 'Ruka tone do kreveta, ali moguć otpor', 'Ruka pada, nema otpora, minimalan pokret', 'Bez pokreta'], skor: 0, izabranaOpcija: ''}, {ime: 'Ataksija ekstremiteta', opcije: ['Ne postoji', 'Postoji na ruci ili nozi', 'Postoji i na ruci i na nozi'], skor: 0, izabranaOpcija: ''}, {ime: 'Senzibilitet', opcije: ["Normalan", "Laksi ispad, spontana utrnulost", 'Tezi ispad, postoji hipestezija'], skor: 0, izabranaOpcija: ''}],
    [{ime: 'Govor', opcije: ['Nema afazije', 'Blaga do umerena disfazija', 'Teska disfazija', 'Globalna afazija'], skor: 0, izabranaOpcija: ''}, {ime: 'Dizartrija', opcije: ['Ne postoji', 'Blaga do umerena', "Teska - govor nerazumljiv", 'Koma'], skor: 0, izabranaOpcija: ''}, {ime: 'Fenomen neglekta', opcije: ['Ne postoji', 'Parcijalni neglekt', 'Potpuni neglekt za sve modalitete']}]
  ]

  proveriOdluku() {
    this.elementiNIHHSSkale.forEach((row) => {
      row.forEach((element) => {
        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-ignore
        const index = element.opcije.indexOf(element.izabranaOpcija);
        element.skor = index;
      });
    });

    const scores = this.elementiNIHHSSkale.flatMap((row) => row.map((element) => element.skor));
    // if (scores.includes(-1)) {
    //   console.log("Obavestenje za neispravne unose")
    // }
    //else {
      const nihhsRequest = new NIHHS(
        this.odlukaOTrombolizi?.pacijent?.jmbg,
        this.odlukaOTrombolizi?.id,
        scores[0],
        scores[1],
        scores[2],
        scores[3],
        scores[4],
        scores[5],
        scores[6],
        scores[7],
        scores[8],
        scores[9],
        scores[10],
        scores[11],
        scores[12],
        scores[13],
        scores[14]
      );
      this.nihhsSkorEvent.emit(nihhsRequest);
   // }

  }

  protected readonly DecisionStatus = DecisionStatus;
}
