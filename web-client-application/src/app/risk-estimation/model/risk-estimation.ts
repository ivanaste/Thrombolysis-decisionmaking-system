import {NivoRizikaMu} from "./nivo-rizika-mu";
import {Patient} from "../../shared/model/patient";

export interface RiskEstimation {
  id: string,
  nivoRizika: NivoRizikaMu,
  pacijent: Patient,
  createdAt: Date
}
