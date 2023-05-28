import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Decision} from "../../../model/decision";
import {NeuroloskiPregled} from "../../../model/NeuroloskiPregled";
import {DecisionStatus} from "../../../model/decision-status";


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
  @Output() neuroloskiPregledEvent: EventEmitter<NeuroloskiPregled> = new EventEmitter<NeuroloskiPregled>();
  @Input() odlukaOTrombolizi: Decision | null;

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
    const izabraneBolesti = this.bolesti.filter(bolest => bolest.checked);
    const modifikovaniBolesti = izabraneBolesti.map((bolest) => {
      return {
        ...bolest,
        ime: bolest.ime.toUpperCase().replace(/ /g, "_")
      };
    });
    const neuroloskiPregled = new NeuroloskiPregled(modifikovaniBolesti.map(
      bolest => bolest.ime),
      modifikovaniBolesti.map(bolest => this.adjustTimeZoneOffset(bolest.datum)),
      this.odlukaOTrombolizi?.id);
    this.neuroloskiPregledEvent.emit(neuroloskiPregled);
  }

  private adjustTimeZoneOffset(date: Date | null): Date {
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    const timezoneOffset = date.getTimezoneOffset();
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    date.setMinutes(date.getMinutes() - timezoneOffset);
    return <Date>date;
  }

  protected readonly DecisionStatus = DecisionStatus;
}
