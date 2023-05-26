import { Component } from '@angular/core';

@Component({
  selector: 'app-nihhsskor',
  templateUrl: './nihhsskor.component.html',
  styleUrls: ['./nihhsskor.component.scss']
})
export class NIHHSSkorComponent {
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
        // @ts-ignore
        const index = element.opcije.indexOf(element.izabranaOpcija);
        element.skor = index;
      });
    });

    const scores = this.elementiNIHHSSkale.flatMap((row) => row.map((element) => element.skor));
    if (scores.includes(-1)) console.log("Sranje")
    else {
      const requestObject = {
        stanjeSvesti: scores[0],
        stanjeSvestiPitanja: scores[1],
        stanjeSvestiNalozi: scores[2],
        pokretiBulbusa: scores[3],
        sirinaVidnogPolja: scores[4],
        mimicnaPareza: scores[5],
        motorikaLevaRuka: scores[6],
        motorikaDesnaRuka: scores[7],
        motorikaLevaNoga: scores[8],
        motorikaDesnaNoga: scores[9],
        ataksijaEkstremiteta: scores[10],
        senzibilitet: scores[11],
        govor: scores[12],
        dizartrija: scores[13],
        fenomenNeglekta: scores[14]
      };
      console.log(requestObject);
    }

  }

}
